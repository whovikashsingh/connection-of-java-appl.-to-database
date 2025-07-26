package myprojectonjdbc;

import java.sql.*;
import java.util.*;

public class MyProjectOnJDBC {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Scanner sc = new Scanner(System.in);
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Query_Management_System", "root",
                "khushi@01");
        System.out.println("Connected with Database\nWELCOME TO QUERY MANAGEMENT SYSTEM!!!!!");
        System.out.println(
                "1.Execute any command except select\n2.Create Table\n3.Insert Data through Static query\n3.Insert Data through Dynamic Query\n4.Display Data\n5.Update Data6.Delete Table\n7.Exit");
        System.out.print("Enter your choice");
        int c = sc.nextInt();

        switch (c) {
            case 1:
                Statement stmt1 = conn.createStatement();
                sc.nextLine();
                System.out.println("Enter Command:");
                String command = sc.nextLine();
                String sqlQuery1 = command;
                stmt1.executeUpdate(sqlQuery1);
                conn.close();
                break;

            case 2:
                sc.nextLine();
                Statement stmt = conn.createStatement();
                String tablename = sc.next();
                String sqlQuery2 = "select * from " + tablename;
                ResultSet rs = stmt.executeQuery(sqlQuery2);
                while (rs.next()) {
                    System.out.println("Student name:" + rs.getString(1));
                    // System.out.println("Student rollno:" + rs.getString(2));

                }
                conn.close();
                break;
            case 3:
                sc.nextLine();
                Statement stmt3 = conn.createStatement();
                System.out.println("Enter your Command to create table");

                System.out.print("Enter Table name: ");
                String name = sc.next();
                System.out.println("Enter your attribute: ");
                String a = sc.next();
                System.out.println("Enter your datatype: ");
                String b = sc.next();
                String d = "create table " + name + "(" + a + " " + b + "(30))";
                String sqlQuery3 = d;
                stmt3.executeUpdate(sqlQuery3);
                conn.close();
                break;

        }

    }

}
