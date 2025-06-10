package ESMS;

import java.sql.*;
import java.util.*;

public class Customer {
    // Variables
    Scanner sc = new Scanner(System.in);
    public int c_id;
    public String c_name, c_mobile;

    // Constructure
    public Customer() {
    }

    public Customer(int c_id, String c_name, String c_mobile) {
        this.c_id = c_id;
        this.c_name = c_name;
        this.c_mobile = c_mobile;
    }

    // Getter,Setter and toString Methods
    @Override
    public String toString() {
        return "Customer []";
    }

    public Scanner getSc() {
        return sc;
    }

    public void setSc(Scanner sc) {
        this.sc = sc;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_mobile() {
        return c_mobile;
    }

    public void setC_mobile(String c_mobile) {
        this.c_mobile = c_mobile;
    }

    // Other Methods
    public void addCustomer(String cname, String cno) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");
        String s6 = "Insert into customer_details(c_name,c_mobile) values (?,?)";

        PreparedStatement ps5 = con.prepareStatement(s6);
        ps5.setString(1, cname);
        ps5.setString(2, cno);
        int a = ps5.executeUpdate();
        System.out.println(
                (a > 0) ? "Customer added successfully" : "Customer not added");
    }

    public void displayByID(int uid1) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");
        Statement st = con.createStatement();
        String s4 = "Select * from customer_details where c_id =" + uid1;
        ResultSet rs4 = st.executeQuery(s4);
        boolean z = rs4.next();
        System.out.println("Customer Id: " + rs4.getInt(1) + ", Customer Name: "
                + rs4.getString(2) + ", Customer contact no. : " + rs4.getString(3));
        System.out.println();
    }

    public void displayAllCustomers() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");
        String s8 = "Select * from customer_details";
        Statement st3 = con.createStatement();
        ResultSet rs6 = st3.executeQuery(s8);
        while (rs6.next()) {
            System.out.println("Customer Id: " + rs6.getInt(1) + ",\t Customer Name: "
                    + rs6.getString(2) + ",\t Customer contact no. : " + rs6.getString(3));
        }
    }
}
