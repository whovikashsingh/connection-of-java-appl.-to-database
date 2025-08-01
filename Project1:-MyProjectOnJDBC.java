package myprojectonjdbc;

import java.sql.*;
import java.util.*;

public class MyProjectOnJDBC {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Scanner sc = new Scanner(System.in);
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Query_Management_System", "root", "khushi@01");
        System.out.println("\nWELCOME TO QUERY MANAGEMENT SYSTEM!!!!!\n");
        while (true) {
            System.out.println("\n1. EXECUTE (CREATE,INSERT,UPDATE,DROP) command\n2. SHOW all Tables\n3. CREATE Table\n4. INSERT Data\n5. DISPLAY Table Data\n6. UPDATE Data\n7. DROP Table\n8. EXIT\n");
            System.out.print("Enter your choice: ");
            int c = sc.nextInt();

            switch (c) {
                case 1://Throgh this case, user can enter any non-select command and execute it
                    sc.nextLine(); //for avoiding error 

                    Statement stmt1 = conn.createStatement(); //create statement
                    System.out.println("\nEnter Your MySQL Command: ");
                    String command = sc.nextLine(); //for input "non-select command" from user
                    stmt1.executeUpdate(command);//execute statement
                    System.out.println("MySQL Command Executed.......");

                    break;
                case 2://showing tables in database
                    sc.nextLine(); //for avoiding error 

                    Statement stmt6 = conn.createStatement(); //create statement
                    ResultSet rs6 = stmt6.executeQuery("show tables");
                    System.out.println("\nAll tables");
                    System.out.println("--------------");
                    while (rs6.next()) {
                        System.out.println(rs6.getString(1));
                    }

                    break;

                case 3://for creating table....
                    sc.nextLine();

                    Statement stmt2 = conn.createStatement();
                    System.out.print("\nEnter Table name: ");
                    String name = sc.next();
                    System.out.println("Enter no. of columns you want to enter:");
                    int col = sc.nextInt(); //col is no. of columns in new table
                    String A[] = new String[col]; //creating array for storing attribute names of new table
                    String D[] = new String[col]; //creating array for storing datatypes of their respective attributes
                    for (int i = 0; i <= col - 1; i++) {
                        System.out.println("Enter your attribute: ");
                        A[i] = sc.next();
                        System.out.println("Enter your datatype: ");
                        D[i] = sc.next();
                    }

                    String att_dtype = ""; //String initialised for storing attribute and datatype pair

                    for (int i = 0; i <= col - 1; i++) //for taking n no. of attributes
                    {
                        if (col == 1) //for single attribute table
                        {
                            if (D[i].equalsIgnoreCase("varchar")) {
                                att_dtype += A[i] + " " + D[i] + "(30) ";
                            } else {
                                att_dtype += A[i] + " " + D[i];
                            }
                        } else if (i == col - 1) //for avoiding comma at last attribute 
                        {
                            if (D[i].equalsIgnoreCase("varchar")) {
                                att_dtype += A[i] + " " + D[i] + "(30) ";
                            } else {
                                att_dtype += A[i] + " " + D[i];
                            }
                        } else {
                            if (D[i].equalsIgnoreCase("varchar")) {
                                att_dtype += A[i] + " " + D[i] + "(30), ";
                            } else {
                                att_dtype += A[i] + " " + D[i] + ",";
                            }
                        }

                    }
                    stmt2.executeUpdate("Create table " + name + "( " + att_dtype + " )");
                    System.out.println("Table created successfully...");

                    break;

                case 4:   //Inserting data....
                    sc.nextLine();

                    System.out.println("\nEnter your table name:");
                    String table_name = sc.next();
                    Statement stmt3 = conn.createStatement();
                    ResultSet rs3a = stmt3.executeQuery("desc " + table_name); //execute desc query
                    int num1 = 0;
                    String values = "";
                    while (rs3a.next()) //counting no. of rows of description to get no. of columns in table
                    {
                        num1++;

                    }
                    for (int i = 0; i <= num1 - 1; i++) //for making string of ?..
                    {
                        if (num1 == 1) //for single attribute table
                        {
                            values += "?";
                        } else if (i == num1 - 1) //for avoiding comma at last attribute 
                        {
                            values += "?";
                        } else {
                            values += "?, ";
                        }

                    }
                    ResultSet rs3b = stmt3.executeQuery("desc " + table_name);
                    String ar3[] = new String[num1]; //creating 1D array to store names of columns in table
                    String dar[] = new String[num1];//creating 1d array to store datatypes of their respective attributes
                    for (int i = 0; i <= num1 - 1; i++) {
                        if (rs3b.next()) {
                            ar3[i] = rs3b.getString(1); //taking attribute's names 
                            dar[i] = rs3b.getString(2);//inserting datatypes

                        }
                    }
                    PreparedStatement pstmt = conn.prepareStatement("insert into " + table_name + " values(" + values + ")");//insert syntax
                    for (int i = 0; i <= num1 - 1; i++) //taking values from user
                    {
                        if (dar[i].equalsIgnoreCase("date")) {
                            System.out.println("enter " + ar3[i] + " " + dar[i] + " (YYYY-MM-DD): ");
                        } else {
                            System.out.println("enter " + ar3[i] + " " + dar[i] + ": ");
                        }
                        if (dar[i].equalsIgnoreCase("int")) {
                            pstmt.setInt(i + 1, sc.nextInt());
                        } else {
                            pstmt.setString(i + 1, sc.next());
                        }

                    }
                    pstmt.executeUpdate();
                    System.out.println("Data inserted.....");
                    break;

                case 5://Displaying table data with column names
                    sc.nextLine(); //for avoiding error

                    Statement stmt4 = conn.createStatement(); //create statement
                    System.out.print("Enter tablename: ");
                    String tablename = sc.next(); //for input "tablename" from user

                    ResultSet rsa = stmt4.executeQuery("desc " + tablename); //executing desc query
                    int num = 0;
                    while (rsa.next()) //counting no. of rows of description to get no. of columns in table
                    {
                        num++;
                    }

                    ResultSet rsb = stmt4.executeQuery("desc " + tablename);
                    String ar[] = new String[num]; //creating 1D array to store names of columns in table
                    for (int i = 0; i <= num - 1; i++) {
                        if (rsb.next()) {
                            ar[i] = rsb.getString(1);
                        }
                    }

                    System.out.println("\nTABLE: " + tablename + "\n");
                    for (int i = 0; i <= num - 1; i++) {
                        System.out.print(ar[i] + "\t\t");
                    }
                    System.out.println("\n-------------------------------------------------------\n");
                    ResultSet rsc = stmt4.executeQuery("select * from " + tablename);

                    while (rsc.next()) //for displaying table data
                    {
                        for (int i = 1; i <= num; i++) {
                            System.out.print(rsc.getString(i) + "\t\t");
                        }
                        System.out.println("");
                    }

                    break;
                case 6://updating data 
                    sc.nextLine(); //for avoiding error 

                    Statement stmt5 = conn.createStatement(); //create statement
                    System.out.println("\nEnter table_name:");

                    String tableName = sc.nextLine();
                    System.out.println("enter attribute which you want to update:");
                    String attribute1 = sc.nextLine();
                    System.out.println("enter value1 (if value is string or date write value inside quotes:-' '):");
                    String value1 = sc.nextLine();
                    System.out.println("enter that attributes where you want to change:");
                    String attribute2 = sc.nextLine();
                    System.out.println("enter value1 (if value is string or date write value inside quotes:-' ')");
                    String value2 = sc.nextLine();
                    stmt5.executeUpdate("update " + tableName + " set " + attribute1 + "=" + value1 + " where " + attribute2 + "=" + value2);//execute statement
                    System.out.println("data updated.....");

                    break;

                case 7://dropping tables from database..
                    sc.nextLine(); //for avoiding error 

                    Statement stmt7 = conn.createStatement(); //create statement
                    System.out.println("Enter your table name which you want to drop: ");
                    String tablename1 = sc.nextLine();
                    stmt7.executeUpdate("drop table " + tablename1);//execute statement
                    System.out.println("table dropped successfully.......");

                    break;
                case 8://exit
                    System.out.println("Exiting QUERY MANAGEMENT SYSTEM!!!!!....");
                    break;
                default:
                    System.out.println("Invalid Choice....");
                    break;
            }
            if (c == 8) {
                break;
            }
            System.out.println("\nDo you want to continue......?(yes/no)");
            String abc = sc.next();
            if (abc.equalsIgnoreCase("no")) {
                break;

            }

        }
        conn.close();

    }

}
