package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


public class Customer {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gb_shop?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertCustomer(String name,String address, String date, String pno)  
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
	 
			// create a prepared statement 
			String query = " insert into customerp(`cID`,`cName`,`cAddress`,`cDate`,`cPno`)"
					 + " values (?, ?, ?, ?, ?)";
	 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, name);
			 preparedStmt.setString(3, address);
			 preparedStmt.setString(4, date);
			 preparedStmt.setString(5, pno);
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newCustomer = readCustomer(); 
			output =  "{\"status\":\"success\", \"data\": \"" + newCustomer + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the Payment.\"}";  
			System.err.println(e.getMessage());   
		} 
		
	  return output;  
	} 
	
	
	public String readCustomer()  
	{   
		String output = ""; 
	
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border=\'1\'><tr><th>Customer Name</th><th>Address</th><th>Date</th><th>Phone Number</th><th>Update</th><th>Remove</th></tr>";
	 
			String query = "select * from customerp";    
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				 String cID = Integer.toString(rs.getInt("cID"));
				 String cName = rs.getString("cName");
				 String cAddress = rs.getString("cAddress");
				 String cDate = rs.getString("cDate");
				 String cPno = rs.getString("cPno");
			
	 
				// Add into the html table 
				output += "<tr><td><input id=\'hidCustomerIDUpdate\' name=\'hidCustomerIDUpdate\' type=\'hidden\' value=\'" + cID + "'>" 
							+ cName + "</td>"; 
				output += "<td>" + cAddress + "</td>";
				output += "<td>" + cDate + "</td>";
				output += "<td>" + cPno + "</td>";

				  
	 
				// buttons     
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-customid='" + cID + "'>" + "</td></tr>"; 
			
			}
			con.close(); 
	 
			// Complete the html table    
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the Customer.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
	public String updateCustomer(String cID, String name, String address, String date, String pno)  
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			String query = "UPDATE customerp SET cName=?,cAddress=?,cDate=?,cPno=?" 
					   + "WHERE cID=?";  
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setString(1, name);
			 preparedStmt.setString(2, address);
			 preparedStmt.setString(3, date);
			 preparedStmt.setString(4, pno);
			 preparedStmt.setInt(5, Integer.parseInt(cID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newCustomer = readCustomer();    
			output = "{\"status\":\"success\", \"data\": \"" + newCustomer + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the Customer.\"}";   
			System.err.println(e.getMessage());   
		} 
	 
	  return output;  
	} 
	
	public String deleteCustomer(String cID)   
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{
				return "Error while connecting to the database for deleting."; 
				
			} 
	 
			// create a prepared statement    
			String query = "delete from customerp where cID=?"; 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(cID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newCustomer = readCustomer();    
			output = "{\"status\":\"success\", \"data\": \"" +  newCustomer + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the Customer.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
}
