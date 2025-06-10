package ESMS;

import java.sql.*;
import java.util.*;

public class Product implements Comparable<Product> {
    Scanner sc = new Scanner(System.in);
    public int p_code;
    public String p_name;
    public double p_price;
    public String p_type;

    // Getter,Setter and toString Methods
    public Scanner getSc() {
        return sc;
    }

    @Override
    public String toString() {
        return "Product []";
    }

    public void setSc(Scanner sc) {
        this.sc = sc;
    }

    public int getP_code() {
        return p_code;
    }

    public void setP_code(int p_code) {
        this.p_code = p_code;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public double getP_price() {
        return p_price;
    }

    public void setP_price(double p_price) {
        this.p_price = p_price;
    }

    public String getP_type() {
        return p_type;
    }

    public void setP_type(String p_type) {
        this.p_type = p_type;
    }

    // Constructors
    public Product() {

    }

    public Product(int p_code, String p_name, double p_price, String p_type) {
        this.p_code = p_code;
        this.p_name = p_name;
        this.p_price = p_price;
        this.p_type = p_type;
    }

    // Other Methods
    public void display() {
        System.out.println("Product Code: " + p_code);
        System.out.println("Product Name: " + p_name);
        System.out.println("Product Price: " + p_price);
        System.out.println("Product Type: " + p_type);
        System.out.println();
    }

    public void addProducts(int pCode, String pName, double pPrice, String pType) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");
        String s1 = "Insert into product_details values(?,?,?,?)";
        PreparedStatement ps1 = con.prepareStatement(s1);
        ps1.setInt(1, pCode);
        ps1.setString(2, pName);
        ps1.setDouble(3, pPrice);
        ps1.setString(4, pType);
        ps1.executeUpdate();
    }

    public static boolean checkValidPCode(int a) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql123 = "Select p_code from product_details";
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");
        Statement st = con.createStatement();
        ResultSet rs1 = st.executeQuery(sql123);
        while (rs1.next()) {
            if (a == rs1.getInt(1)) {
                return true;
            }
        }
        return false;
    }

    public void deleteProduct(int pc) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");
        String s2 = "Delete from product_details where p_code = ?";
        PreparedStatement ps2 = con.prepareStatement(s2);
        ps2.setInt(1, pc);
        ps2.executeUpdate();
        System.out.println();
    }

    public void displayBilling() throws Exception {
        System.out.println();
        System.out.println("Product code: " + p_code);
        System.out.println("Product Name: " + p_name);
        System.out.println("Price: " + p_price);
        System.out.println("Stock: " + getUnits(p_code));
        System.out.println();
    }

    public int getUnits(int values) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");
        String sql1 = "Select units from inventory where p_code = ?";
        PreparedStatement p1 = con.prepareStatement(sql1);
        p1.setInt(1, values);
        ResultSet b = p1.executeQuery();
        boolean b1 = b.next();
        return b.getInt(1);
    }

    @Override
    public int compareTo(Product o) {
        return 1;
    }

}
