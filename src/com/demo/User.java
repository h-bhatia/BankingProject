package com.demo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User 
{
 private Connection connection;	    
	private Scanner scanner;
	public User(Connection connection, Scanner scanner) {
		super();
		this.connection = connection;
		this.scanner = scanner;
	}
	
	public void register()
	{
		scanner.nextLine();
		System.out.println("Full name:");
		String full_name=scanner.nextLine();
		System.out.println("Email");
		String email=scanner.nextLine();
		System.out.println("Password");
		String password=scanner.nextLine();
		if(user_exist(email))
		{
			System.out.println("User Already Exists for this Email Address!!");
			return;
		}
		String register_query="insert into  user(full_name,email,password) values(?,?,?)";
		try {
		PreparedStatement pst=connection.prepareStatement(register_query);
		pst.setString(1, full_name);
		pst.setString(2, email);
		pst.setString(3, password);
		int affectedRows=pst.executeUpdate();
		if(affectedRows>0)
		{
			System.out.println("registration successfull");
		}
		else {
			System.out.println("Registration failed");
		}
			
			
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
	}
	
public  String login()
{
	scanner.nextLine();
	System.out.println("Email:  ");
	String email=scanner.nextLine();
	System.out.println("Password:  ");
	String pswd=scanner.nextLine();
	String login_query="Select * from User  where email=? and password=?";
	try {
		PreparedStatement pst=connection.prepareStatement(login_query);
			pst.setString(1, email);
			pst.setString(2, pswd);
			ResultSet resultSet=pst.executeQuery();
			if(resultSet.next())
			{
				return email;
			}
			else {
				return null;
			}
		
		
		
	} catch (SQLException e) {
		// TODO: handle exception
	e.printStackTrace();
	}
	return null;
}
	
public boolean user_exist(String email)
{
	String query="select *from user where email=?";
	try {
		PreparedStatement pst=connection.prepareStatement(query);
		pst.setString(1, email);
		ResultSet resultSet=pst.executeQuery();
		if(resultSet.next())
		{
			return true;
		}
		else {
			return false;
		}
			
		
	} catch (SQLException e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return false;
}
	

	
}
