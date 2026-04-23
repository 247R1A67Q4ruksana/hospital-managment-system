package hms;
 import java.sql.Connection;
import java.util.Scanner;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
public class Doctor {
  private Connection connection;
  private Scanner scanner;
  public Doctor(Connection connection,Scanner scanner)
{
	this.connection=connection;
	this.scanner=scanner;
}
public void viewDoctors()
{
	String query="select * from doctors";
	try {
	PreparedStatement preparedStatement =connection.prepareStatement(query);
	ResultSet resultset=preparedStatement.executeQuery();
	System.out.println("Doctor id:");
	System.out.println("+----------+---------------+--------------+");
	System.out.println("|Doctor ID |Name           | specilization|");
	System.out.println("+----------+---------------+--------------+");
   while(resultset.next())
   {
	   int id=resultset.getInt("id");
	   String name=resultset.getString("name");
	   String specilization=resultset.getString("specilization");
	   System.out.printf("|-15s|%-20s|%-18s|\n",id,name,specilization);
	   System.out.println("+---------------+--------------------+------------------+");
   }
	}catch(SQLException e)
	{
		e.printStackTrace();
	}
}
public void addDoctor()
{
	System.out.println("Enter Doctor's Name");
	 String name=scanner.next();
	System.out.println("Enter Doctor  specialization");
	  String specialization=scanner.next();

try {
	String Query="Insert into Doctor(name,specialization) values(?,?)";
	PreparedStatement preparedStatement=connection.prepareStatement(Query);
	preparedStatement.setString(1,name);
	preparedStatement.setString(2,specialization);

	int affectedRows=preparedStatement.executeUpdate();
	if(affectedRows>0)
	{
		System.out.println("Doctor Added Sucessfully");
	}
	else {
		System.out.println("Failed to add Patient!");
	}
}catch(SQLException e)
{
	e.printStackTrace();
}
}
public boolean getDoctorByID(int id)
{
	String query="select * from doctors where ID=?";
	try {
		PreparedStatement preparedstatement = connection.prepareStatement(query);
		preparedstatement.setInt(1, id);
		ResultSet resultset=preparedstatement.executeQuery();
		if(resultset.next())
		{
			return true;
		}
		else {
			return false;
		}
	}catch(SQLException e) {e.printStackTrace();}
	return false;
}	

}
