package com.tap;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/empregister")
public class EmpRegister extends HttpServlet
{
	private static final String Query="INSERT into employee(id,name,email,dept,salary) VALUES(?,?,?,?,?)";
	
	    private static final String url="jdbc:mysql://localhost:3306/jdbcclasses";
	    private static final String userName="root";
	    private static final String Password="Seenu@171936";
	    
	    
	    @Override
	    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	    	
	    	    String employeeId = req.getParameter("employeeId");
	            String name = req.getParameter("name");
	            String email = req.getParameter("email");
	            String department = req.getParameter("department");
	            String salary = req.getParameter("salary");
	            
	            PrintWriter out = resp.getWriter();
	            resp.setContentType("text/html");
	            
	            //Load the database 
	            try 
	            {
	    			Class.forName("com.mysql.cj.jdbc.Driver");
	    		} 
	            catch (ClassNotFoundException e)
	            {
	    			e.printStackTrace();
	    		}
	            
	            //create the connection
	            try(Connection con=DriverManager.getConnection(url,userName,Password);
	            	PreparedStatement ps=con.prepareStatement(Query);	)
	            {
	            	//set the values
	            	    ps.setString(1, employeeId);
	                    ps.setString(2, name);
	                    ps.setString(3, email);
	                    ps.setString(4, department);
	                    ps.setString(5, salary);
	                    
	                  //execute query
	                	int count=ps.executeUpdate();
	                	
	                	if(count==0)
	                	{
	                		out.println("Employee details not registered");
	                	}
	                	else
	                	{
	                		out.println("Employee registration is successful...");
	                		//To view the employee list
	                        out.println("<br/><br/><form action='emp' method='get'>");
	                        out.println("<input type='submit' value='View Employee List'></form>");
	                		
	                	}
	            }catch(SQLException se)
	            {
	            	out.println(se.getMessage());
	            	se.printStackTrace();
	            }
	            catch(Exception e)
	            {
	            	out.println(e.getMessage());
	            	e.printStackTrace();
	            }
	    
	    }
}
