package com.tap;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmployeeListServlet extends HttpServlet {
    private static final String SELECT_QUERY = "SELECT id, name, email, dept, salary FROM employee";
    private static final String URL = "jdbc:mysql://localhost:3306/jdbcclasses";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Seenu@171936";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {
            // Load the database driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create the connection
            try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 PreparedStatement ps = con.prepareStatement(SELECT_QUERY);
                 ResultSet rs = ps.executeQuery()) {


                out.println("<html><head>");
                out.println("<title>Employee List</title>");
                out.println("<style>");
                out.println("body { font-family: 'Arial', sans-serif; background-color: #232D3F; margin: 0; padding: 0; }");
                out.println("h2 { color: #ccc; }");
                out.println("div.employee-container { display: inline-block; width: 200px; margin: 20px; border: 1px solid #ccc; background-color: #fff; padding: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);}");
                out.println("p { margin: 0; }");
                out.println("</style>");
                out.println("</head><body>");
                out.println("<h2 style='text-align: center;'>Employee List</h2>");


                // Iterate through the result set and display employee data
                while (rs.next()) {
                    out.println("<div class='employee-container'>");
                    out.println("<p style='font-weight: bold; color: #007bff;'>ID: " + rs.getString("id") + "</p>");
                    out.println("<p><strong>Name:</strong> " + rs.getString("name") + "</p>");
                    out.println("<p><strong>Email:</strong> " + rs.getString("email") + "</p>");
                    out.println("<p><strong>Department:</strong> " + rs.getString("dept") + "</p>");
                    out.println("<p><strong>Salary:</strong>" + String.format("%,.2f", rs.getDouble("salary")) + "</p>");
                    out.println("</div>");
                }

                out.println("</body></html>");

            } catch (SQLException se) {
                // Log the exception for debugging
                se.printStackTrace();
                out.println("An error occurred while processing your request. Please try again later.");
            }
        } catch (ClassNotFoundException e) {
            // Log the exception for debugging
            e.printStackTrace();
            out.println("An error occurred while processing your request. Please try again later.");
        }
    }
}
