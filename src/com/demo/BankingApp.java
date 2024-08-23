package com.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp 
{
	private static final String url="jdbc:mysql://localhost:3306/banking_system";
	private static final String username="root";
	private static final String password="SYSTEM";
	public static void main(String[] args) throws  ClassNotFoundException, SQLException
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		try {
        Connection conn=DriverManager.getConnection(url,username,password);
		Scanner scanner=new Scanner(System.in);
		User user=new User(conn, scanner);
	Acoounts account=new Acoounts(conn,scanner);
		AccountManager accountManager=new AccountManager(conn,scanner);
		String email;
		long account_number;
		while(true)
		{
			System.out.println("Welcome to banking system");
			System.out.println();
			System.out.println("1. Register");
			System.out.println("2.Login");
			System.out.println("3 Exist");
			System.out.println("Enter your choice");
			int choice1=scanner.nextInt();
			switch(choice1)
			{
			case 1:
				user.register();
				break;
			case 2:
				email=user.login();
				if(email!=null)
				{
					System.out.println();
					System.out.println("User logged in");
					if(!account.account_exist(email))
					{
						System.out.println();
						System.out.println("1. open a new bank account");
						System.out.println("2.Exit");
						if(scanner.nextInt()==1)
						{
							account_number=account.open_account(email);
							System.out.println("Account created successfully");
							System.out.println("your Account  number is" +account_number);
							}
						else {
							break;
						}
							
					}
					account_number=account.getAccount_number(email);
					int choice2=0;
					while(choice2!=5)
					{
						System.out.println();
						 System.out.println("1. Debit Money");
                         System.out.println("2. Credit Money");
                         System.out.println("3. Transfer Money");
                         System.out.println("4. Check Balance");
                         System.out.println("5. Log Out");
                         System.out.println("Enter your choice: ");
                        choice2=scanner.nextInt();
                        switch(choice2)
                        {
                        case 1:
                        	accountManager.debit_money(account_number);
                        	break;
                        
                        case 2:
                        	accountManager.credit_money(account_number);
                        	break;
                        case 3:
                        	accountManager.transfer_money(account_number);
                        	break;
                        case 4:
                        	accountManager.getBalance(account_number);
                        	break;
                        case 5:
                        	break;
                        	default:
                        		System.out.println("Enter valid choice!");
                        		break;
					}
				}
			
			}
				else {
					System.out.println("Incorrect email or password");
				}
			case 3:
				System.out.println("thanks for using banking system");
				System.out.println("existing system");
				return ;
				default:
					System.out.println("enter valid choice ");
					break;
			
		}
        
		}
		}
		catch (SQLException e) {
			// TODO: handle exception
		}
		
	}
}
