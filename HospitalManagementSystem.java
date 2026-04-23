package hms;

import java.sql.Connection;
import java.util.Scanner;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HospitalManagementSystem {
	 
	private static final String url="jdbc:mysql://localhost:3306/hospital";
	private static final String username="root";
	private static final String password="Ruksana@789";
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    try {
    	Class.forName("com.mysql.cj.jdbc.Driver");
    }catch(ClassNotFoundException e)
    {
    	e.printStackTrace();
    }
    Scanner scanner=new Scanner(System.in);
    try {
       Connection connection=DriverManager.getConnection(url,username,password);
       patient patient=new patient(connection,scanner);
       Doctor doctor=new Doctor(connection,scanner);
       while(true)
       {
    	   System.out.println("Hospital Management System");
    	   System.out.println("1.Add patient");
    	   System.out.println("2.view patients");
    	   System.out.println("3.view Doctors");
    	   System.out.println("4.Book Appointment");
    	   System.out.println("5.addDoctor");
    	   System.out.println("6.Exit");
    	   System.out.println("please Enter your choice");
    	   int choice=scanner.nextInt();
  
	   switch(choice)
       {
       case 1:
    	   patient.addpatient();
    	   System.out.println();
    	   break;
       case 2:
    	   patient.viewpatients();
    	   System.out.println();
    	   break;
       case 3:
    	   doctor.viewDoctors();
    	   System.out.println();
       case 4:
    	   bookAppointment(patient,doctor,connection,scanner);
    	   System.out.println();
    	   break;
       case 5:
    	   doctor.addDoctor();
    	   System.out.println();
       case 6:
       System.out.println("THANK YOU FOR USING MANAGEMENT SYSTEM");
       return;
       default:
    	   System.out.println("Please Enter Valid choice");
       break;
       
       }
       }
}catch(SQLException e) {
	e.printStackTrace();
}
	}
	public static void bookAppointment(patient patient,Doctor doctor,Connection connection,Scanner scanner)
	{
		System.out.println("Please Enter Patient ID:");
		int patientID=scanner.nextInt();
		System.out.println("Please Enter Doctor ID:");
		int doctorID= scanner.nextInt();
		System.out.println("Please Enter Appointment Date(YYYY-MM-DD");
		String appointmentDate=scanner.next();

 if(patient.getPatientByID(patientID)&&doctor.getDoctorByID(doctorID))
 {
	 if(checkDoctorAvailability(doctorID,appointmentDate,connection)) {
		 String appointmentQuery="INSERT INTO  appointments(patient_id,doctor_id,appointment_date)values(?,?,?)";
		 try {
			 PreparedStatement preparedStatement=connection.prepareStatement(appointmentQuery);
			 preparedStatement.setInt(1, patientID);
			 preparedStatement.setInt(2, doctorID);
			 preparedStatement.setString(3,appointmentDate);
			 int rowsAffected=preparedStatement.executeUpdate();
			 if(rowsAffected>0) {
				 System.out.println("Appointment Booked");
			 }
			 else
			 {
				 System.out.println("Appointment could not be booked");
			 }
		 }catch(SQLException e)
		 {
			 e.printStackTrace();
		 }
	 }
	 else {
		 System.out.println("Doctor not  available on this date!");
	 }
 }
 else {
	 System.out.println("Either doctor or patient doesn't exist!");
 }
	}
 public static boolean checkDoctorAvailability(int doctorID,String appointmentDate,Connection connection)
 {
	 String query="SELECT * FROM appointments WHERE doctor id=? AND appointment_date=?";
	 try {
		 PreparedStatement preparedStatement=connection.prepareStatement(query);
		 preparedStatement.setInt(1, doctorID);
		 preparedStatement.setString(2,appointmentDate);
		 ResultSet resultset=preparedStatement.executeQuery();
		 if (resultset.next()) {
			 int count=resultset.getInt(1);
			 if(count==0)
			 {
				 return true;
			 }
			 else {
				 return false;
			 }
		 }
	 }
	 catch(SQLException e)
	 {
		 e.printStackTrace();
	 }
	 return false;
 }
}
