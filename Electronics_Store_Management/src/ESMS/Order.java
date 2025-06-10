package ESMS;

import static ESMS.Product.checkValidPCode;
import java.sql.*;
import java.util.*;

public class Order {
    // Variables
    Product p = new Product();
    public int p_code;
    public double price;
    public int units;
    public String name;
    // Getter,Setter and toString Methods

    @Override
    public String toString() {
        return "Order []";
    }

    public int getP_code() {
        return p_code;
    }

    public void setP_code(int p_code) {
        this.p_code = p_code;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getP() {
        return p;
    }

    public void setP(Product p) {
        this.p = p;
    }

    // Constructor
    public Order(int p_code, double price, int units, String name) {
        this.p_code = p_code;
        this.price = price;
        this.units = units;
        this.name = name;
    }

    public Order() {
    }

    // Other Methods
    public void display() {
        System.out.println("Name: " + name);
    }

    public ArrayList<Order> orderitem(PriorityQueue<Product> productList, ArrayList<Order> ordered_items)
            throws Exception {
        Scanner sc = new Scanner(System.in);
        boolean b67 = true;
        while (b67 == true) {
            System.out.println("Enter 1 to view Laptops");
            System.out.println("Enter 2 to view Speakers");
            System.out.println("Enter 3 to view Refrigerator");
            System.out.println("Enter 4 to view Mobile Phone");
            System.out.println("Enter 5 to view A.C.");
            System.out.println("Enter 6 to view TV");
            System.out.println("Enter 7 to view Washing Machine");
            System.out.println("Enter 8 to view Oven");
            System.out.println("Enter 9 to view Hearables( Headphones& earpodes)");
            System.out.println("Enter 10 to exit from catalog");
            System.out.print("Enter your choice: ");
            int catach = sc.nextInt();
            switch (catach) {
                case 1:
                    for (Product x : productList) {
                        if (x.p_type.equalsIgnoreCase("Laptop")) {
                            x.displayBilling();
                        }
                    }
                    int p1_code;
                    while (true) {
                        System.out.println("Enter Product code to select: ");
                        p1_code = sc.nextInt();
                        if (checkValidPCode(p1_code))
                            break;
                        else
                            System.out.println("Enter valid product code");
                    }
                    for (Product p : productList) {
                        if (p.p_code == p1_code) {
                            Order o1 = new Order(p1_code, p.p_price,
                                    p.getUnits(p1_code),
                                    p.p_name);
                            ordered_items.add(o1);
                        }
                    }

                    break;
                case 2:
                    for (Product x : productList) {
                        if (x.p_type.equalsIgnoreCase("Speaker")) {
                            x.displayBilling();
                            System.out.println();
                        }
                    }
                    int p2_code;
                    while (true) {
                        System.out.println("Enter Product code to select: ");
                        p2_code = sc.nextInt();
                        if (checkValidPCode(p2_code))
                            break;
                        else
                            System.out.println("Enter valid product code");
                    }
                    for (Product p : productList) {
                        if (p.p_code == p2_code) {
                            Order o1 = new Order(p.p_code, p.p_price,
                                    p.getUnits(p2_code),
                                    p.p_name);
                            ordered_items.add(o1);
                        }
                    }
                    break;
                case 3:
                    for (Product x : productList) {
                        if (x.p_type.equalsIgnoreCase("Refrigerator")) {
                            x.displayBilling();
                            System.out.println();
                        }

                    }
                    int p3_code;
                    while (true) {
                        System.out.println("Enter Product code to select: ");
                        p3_code = sc.nextInt();
                        if (checkValidPCode(p3_code))
                            break;
                        else
                            System.out.println("Enter valid product code");
                    }
                    for (Product p : productList) {
                        if (p.p_code == p3_code) {
                            Order o1 = new Order(p.p_code, p.p_price,
                                    p.getUnits(p3_code),
                                    p.p_name);
                            ordered_items.add(o1);
                        }
                    }
                    break;
                case 4:
                    for (Product x : productList) {
                        if (x.p_type.equalsIgnoreCase("Mobile Phone")) {
                            x.displayBilling();
                            System.out.println();
                        }
                    }
                    int p4_code;
                    while (true) {
                        System.out.println("Enter Product code to select: ");
                        p4_code = sc.nextInt();
                        if (checkValidPCode(p4_code))
                            break;
                        else
                            System.out.println("Enter valid product code");
                    }
                    for (Product p : productList) {
                        if (p.p_code == p4_code) {
                            Order o1 = new Order(p.p_code, p.p_price,
                                    p.getUnits(p4_code),
                                    p.p_name);
                            ordered_items.add(o1);
                        }
                    }
                    break;
                case 5:
                    for (Product x : productList) {
                        if (x.p_type.equalsIgnoreCase("A.C.")) {
                            x.displayBilling();
                            System.out.println();
                        }
                    }
                    int p5_code;
                    while (true) {
                        System.out.println("Enter Product code to select: ");
                        p5_code = sc.nextInt();
                        if (checkValidPCode(p5_code))
                            break;
                        else
                            System.out.println("Enter valid product code");
                    }
                    for (Product p : productList) {
                        if (p.p_code == p5_code) {
                            Order o1 = new Order(p.p_code, p.p_price,
                                    p.getUnits(p5_code),
                                    p.p_name);
                            ordered_items.add(o1);
                        }
                    }
                    break;
                case 6:
                    for (Product x : productList) {
                        if (x.p_type.equalsIgnoreCase("TV")) {
                            x.displayBilling();
                            System.out.println();
                        }
                    }
                    int p6_code;
                    while (true) {
                        System.out.println("Enter Product code to select: ");
                        p6_code = sc.nextInt();
                        if (checkValidPCode(p6_code))
                            break;
                        else
                            System.out.println("Enter valid product code");
                    }
                    for (Product p : productList) {
                        if (p.p_code == p6_code) {
                            Order o1 = new Order(p.p_code, p.p_price,
                                    p.getUnits(p6_code),
                                    p.p_name);
                            ordered_items.add(o1);
                        }
                    }
                    break;
                case 7:
                    for (Product x : productList) {
                        if (x.p_type.equalsIgnoreCase("Washing Machine")) {
                            x.displayBilling();
                            System.out.println();
                        }
                    }
                    int p7_code;
                    while (true) {
                        System.out.println("Enter Product code to select: ");
                        p7_code = sc.nextInt();
                        if (checkValidPCode(p7_code))
                            break;
                        else
                            System.out.println("Enter valid product code");
                    }
                    for (Product p : productList) {
                        if (p.p_code == p7_code) {
                            Order o1 = new Order(p.p_code, p.p_price,
                                    p.getUnits(p7_code),
                                    p.p_name);
                            ordered_items.add(o1);
                        }
                    }
                    break;
                case 8:
                    for (Product x : productList) {
                        if (x.p_type.equalsIgnoreCase("Oven")) {
                            x.displayBilling();
                            System.out.println();
                        }
                    }
                    int p8_code;
                    while (true) {
                        System.out.println("Enter Product code to select: ");
                        p8_code = sc.nextInt();
                        if (checkValidPCode(p8_code))
                            break;
                        else
                            System.out.println("Enter valid product code");
                    }
                    for (Product p : productList) {
                        if (p.p_code == p8_code) {
                            Order o1 = new Order(p.p_code, p.p_price,
                                    p.getUnits(p8_code),
                                    p.p_name);
                            ordered_items.add(o1);
                        }
                    }
                    break;
                case 9:
                    for (Product x : productList) {
                        if (x.p_type.equalsIgnoreCase("Headphone")
                                || x.p_type.equalsIgnoreCase("earpodes")) {
                            x.displayBilling();
                            System.out.println();
                        }
                    }
                    int p9_code;
                    while (true) {
                        System.out.println("Enter Product code to select: ");
                        p9_code = sc.nextInt();
                        if (checkValidPCode(p9_code))
                            break;
                        else
                            System.out.println("Enter valid product code");
                    }

                    for (Product p : productList) {
                        if (p.p_code == p9_code) {
                            Order o1 = new Order(p.p_code, p.p_price,
                                    p.getUnits(p9_code),
                                    p.p_name);
                            ordered_items.add(o1);
                        }
                    }
                    break;
                case 10:
                    b67 = false;
                    break;
            }
        }
        System.out.println();
        return ordered_items;
    }

    public void setDataOrder(int c1_id, double Total_amount, int b_no, ArrayList<Order> ordered_items)
            throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");

        String o_history = "Insert into orders (customer_id,Amount,Bill_no)values(?,?,?)";
        PreparedStatement pst = con.prepareStatement(o_history);

        pst.setInt(1, c1_id);
        pst.setDouble(2, Total_amount);
        pst.setInt(3, b_no + 1);
        pst.executeUpdate();
        String getOid = "Select order_id from orders where bill_no=?";
        PreparedStatement cs = con.prepareStatement(getOid);
        cs.setInt(1, b_no);
        ResultSet r = cs.executeQuery();
        boolean y = r.next();
        String o_details = "Insert into order_details (Product_code,Product_name,Bill_no,price,Order_id)values(?,?,?,?,?)";
        for (Order x : ordered_items) {
            PreparedStatement cst = con.prepareStatement(o_details);
            cst.setInt(1, x.p_code);
            cst.setString(2, x.name);
            cst.setInt(3, b_no + 1);
            cst.setDouble(4, x.price);
            cst.setInt(5, r.getInt(1));
            cst.executeUpdate();
        }
    }
}
