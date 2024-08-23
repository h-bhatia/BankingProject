package com.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.security.auth.login.AccountException;

public class Acoounts 
{
	private Connection connection;
	private Scanner scanner;
	public Acoounts(Connection conn,Scanner scan) {
		this.connection=conn;
		this.scanner=scan;
		// TODO Auto-generated constructor stub
	}
	
public long open_account(String email)
{
	if(!account_exist(email))
	{
	String open_account_query="insert into Accounts(account_number,full_name,email,balance,security_pin) values (?,?,?,?,?)";
	scanner.nextLine();
	System.out.print("Enter Full Name: ");
    String full_name = scanner.nextLine();
    System.out.print("Enter Initial Amount: ");
    double balance = scanner.nextDouble();
    scanner.nextLine();
    System.out.print("Enter Security Pin: ");
    String security_pin = scanner.nextLine();
	try {
		long account_number=generateAccountNumber();
		PreparedStatement pst=connection.prepareStatement(open_account_query);
		pst.setLong(1, account_number);
		pst.setString(2, full_name);
		pst.setString(3, email);
		pst.setDouble(4, balance);
		pst.setString(5, security_pin);
		int rowsAffected=pst.executeUpdate();
		if(rowsAffected>0)
		{
			return account_number;
		}
		else {
			throw new RuntimeException("Account credential failed");
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	}
	throw new RuntimeException("Account already exist");
}

public long getAccount_number(String email)
{
	String query="Select account_number from accounts where email=?";
	try {
		PreparedStatement pst=connection.prepareStatement(query);
		pst.setString(1, email);
		ResultSet resultSet=pst.executeQuery();
		if(resultSet.next())
		{
			return resultSet.getLong("account_number");
		}
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	throw new RuntimeException("Account Number does not exist");
}
private long generateAccountNumber()
{
	try {
	Statement statement=connection.createStatement();
	ResultSet resultSet=statement.executeQuery("Select account_number from Accounts order by account_number desc limit 1");
	if(resultSet.next())
	{
		long last_account_number=resultSet.getLong("account_number");
		return last_account_number+1;
	}
	else {
		return 10000100;
		// agar koi account number nhi hai to 1st account number  yahi rehega
	}
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return 10000100;
}
public boolean account_exist(String email)
{
	String query="select account_number from accounts  where email=?";
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
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	return false;
}





}
