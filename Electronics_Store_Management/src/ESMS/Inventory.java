package ESMS;

import java.sql.*;
import java.util.*;

public class Inventory {
    // Variables
    Scanner sc = new Scanner(System.in);
    public int p_code;
    public int units;
    Product p = new Product();
    // Getter,Setter and toString Methods

    @Override
    public String toString() {
        return "Inventory []";
    }

    public Scanner getSc() {
        return sc;
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

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public Product getP() {
        return p;
    }

    public void setP(Product p) {
        this.p = p;
    }

    // Constructure
    public Inventory() {
        super();
    }

    public Inventory(int p_code, int units) {
        this.p_code = p_code;
        this.units = units;
    }

    // Other Methods
    public void addStock(int pc3, int u1) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");
        String sql = "{call insertInInventory(?,?)}";
        CallableStatement cst = con.prepareCall(sql);
        cst.setInt(1, pc3);
        cst.setInt(2, u1);
        cst.execute();
        System.out.println("Stock updated");
        System.out.println();
    }

    public void DisplayByPCode(int pc4) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");
        String s5 = "Select p_code,units from inventory where p_code = ?";
        PreparedStatement ps3 = con.prepareStatement(s5);
        ps3.setInt(1, pc4);
        ResultSet rs3 = ps3.executeQuery();
        boolean y = rs3.next();
        System.out.println(
                "Product Code: " + rs3.getInt(1) + ", Units : " + rs3.getInt(2));

        System.out.println();
    }

    public void display() {
        System.out.println("Product code: " + p_code);
        System.out.println("No. of Units: " + units);
    }
}
