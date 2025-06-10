package ESMS;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Billing {
    // Variables
    public int b_no;
    public int c_id;
    public String c_name, c_mobile;
    public ArrayList<Order> o;
    public double sum = 0;
    public double sum1 = 0;

    // Getter,Setter and toString Methods

    @Override
    public String toString() {
        return "Billing []";
    }

    public int getB_no() {
        return b_no;
    }

    public void setB_no(int b_no) {
        this.b_no = b_no;
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

    public ArrayList<Order> getO() {
        return o;
    }

    public void setO(ArrayList<Order> o) {
        this.o = o;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getSum1() {
        return sum1;
    }

    public void setSum1(double sum1) {
        this.sum1 = sum1;
    }

    // Consturcture
    public Billing() {
    }

    public Billing(int c_id, ArrayList<Order> o, int b) throws Exception {
        this.c_id = c_id;
        this.o = o;
        b_no = b + 1;
        setCustomer(c_id);
    }

    // Other Methods
    public void setCustomer(int c_id) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");
        String sql = "Select c_name,c_mobile from customer_details where c_id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, this.c_id);
        ResultSet rs1 = p.executeQuery();
        boolean b82 = rs1.next();
        this.c_name = rs1.getString(1);
        this.c_mobile = rs1.getString(2);
    }

    public double bill() {
        System.out.println("-----------------------------------------------------");
        System.out.println(" Name: " + c_name + "            Bill No: " + b_no);
        System.out.println(" Mobile No: " + c_mobile);
        System.out.println();
        System.out.println("-----------------------------------------------------");
        System.out.println("Product code     Product Name             Price");
        for (Order d : o) {
            System.out.println(d.p_code + "             " + d.name);
            System.out.println("                                          " + d.price);
            System.out.println();
            sum = sum + d.price;
        }
        System.out.println("-----------------------------------------------------");
        System.out.println("                               Total Price: " + sum);
        System.out.println("                                       Gst: " + (int) (0.18 * sum));
        System.out.println("                            Payable Amount: " + (int) (sum + (sum * 0.18)));
        System.out.println("-----------------------------------------------------");
        System.out.println("--------------Thank You & Visit Again----------------");
        System.out.println("-----------------------------------------------------");
        return sum;
    }

    public void generateEbill() throws Exception {
        String fname = "D:\\" + (int) (Math.random() * 3500) + "GP.txt";
        File f = new File(fname);
        f.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        String line = "-----------------------------------------------------------------";
        bw.write(line);
        bw.newLine();
        bw.write(" Name: " + c_name + "            Bill No: " + b_no);
        bw.newLine();
        bw.write(" Mobile No: " + c_mobile);
        bw.newLine();
        bw.write(line);
        bw.newLine();
        bw.write("Product code     Product Name             Price");
        bw.newLine();
        for (Order d : o) {
            bw.write(d.p_code + "             " + d.name);
            bw.newLine();
            bw.write("                                                             " + d.price);
            bw.newLine();
            bw.newLine();
            sum1 = sum1 + d.price;
        }
        bw.write(line);
        bw.newLine();
        bw.write("                               Total Price: " + sum1);
        bw.newLine();
        bw.write("                                       Gst: " + (int) (0.18 * sum1));
        bw.newLine();
        bw.write("                            Payable Amount: " + (int) (sum + (sum1 * 0.18)));
        bw.newLine();
        bw.write(line);
        bw.newLine();
        bw.write("--------------Thank You & Visit Again----------------");
        bw.newLine();
        bw.write(line);
        bw.newLine();
        bw.close();
    }

    public void getAna() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");

        double totalAmount = 0;
        int totalunits = 0;
        int totalTV = 0;
        int totalAC = 0, totalRef = 0, totalMobile = 0, totalSpeakers = 0, totalOven = 0, totalWM = 0,
                totalLaptop = 0, totalHearables = 0;
        String analytics1 = "Select product_code from order_details";
        PreparedStatement pst123 = con.prepareStatement(analytics1);
        ResultSet rs23 = pst123.executeQuery();
        while (rs23.next()) {
            String analytics2 = "{call getAnalytics(?,?,?)}";
            CallableStatement cst1 = con.prepareCall(analytics2);
            cst1.setInt(1, rs23.getInt(1));
            cst1.executeQuery();
            String p_type1 = cst1.getString(2);
            double p_price1 = cst1.getDouble(3);

            totalunits++;
            totalAmount += p_price1;
            if (p_type1.equalsIgnoreCase("TV"))
                totalTV++;
            else if (p_type1.equalsIgnoreCase("A.C."))
                totalAC++;
            else if (p_type1.equalsIgnoreCase("Refrigerator"))
                totalRef++;
            else if (p_type1.equalsIgnoreCase("Mobile Phone"))
                totalMobile++;
            else if (p_type1.equalsIgnoreCase("Speaker"))
                totalSpeakers++;
            else if (p_type1.equalsIgnoreCase("Oven"))
                totalOven++;
            else if (p_type1.equalsIgnoreCase("Washing Machine"))
                totalWM++;
            else if (p_type1.equalsIgnoreCase("LAPTOP"))
                totalLaptop++;
            else
                totalHearables++;

        }
        System.out.println("Total number of T.V sold:" + totalTV);
        System.out.println("Total number of A.C. sold:" + totalAC);
        System.out.println("Total number of Refrigertaor :" + totalRef);
        System.out.println("Total number of Mobile Phones sold :" + totalMobile);
        System.out.println("Total number of Speakers sold:" + totalSpeakers);
        System.out.println("Total number of Ovens Sold:" + totalOven);
        System.out.println("Total number of Washing Machines sold:" + totalWM);
        System.out.println("Total number of Headphones and earpodes sold:" + totalHearables);
        System.out.println("Total number of Laptops sold:" + totalLaptop);
        System.out.println("Total number of units sold:" + totalunits);
        System.out.println("Total revenue generated :" + totalAmount);
        System.out.println();
    }
}
