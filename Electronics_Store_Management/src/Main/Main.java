package Main;

import DataStructure.LinkedListc;
import ESMS.*;
import java.sql.*;
import java.util.*;

class InValidChoiceException extends Exception {
    InValidChoiceException(String s) {
        super(s);
    }
}

public class Main extends InValidChoiceException {
    Main(String s) {
        super(s);
    }

    static boolean checkValidPCode(int a) throws Exception {
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

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");
        // Scanner Declaration
        Scanner sc = new Scanner(System.in);
        // DataStructure declaration
        LinkedListc customer_List = new LinkedListc();
        ArrayList<Order> ordered_items = new ArrayList<>();
        PriorityQueue<Product> productList = new PriorityQueue<>();
        // Object Generations
        Customer c = new Customer();
        Inventory i = new Inventory();
        Product pro = new Product();
        Billing bil = new Billing();
        Order o = new Order();
        // Other Variables
        double Total_amount = 0.0;
        int b_no = 0;
        boolean b = true;
        // Will Add all Past Customer_details in LinkedList of Customer from database
        String sql21 = "Select * from customer_details";
        PreparedStatement ps2t1 = con.prepareStatement(sql21);
        ResultSet rs21 = ps2t1.executeQuery();
        while (rs21.next()) {
            Customer cw = new Customer(rs21.getInt(1), rs21.getString(2), rs21.getString(3));
            customer_List.addLast(cw);

        }
        // Will Add all Past Product_details in priority queue from database
        String sql1 = "Select * from product_details";
        PreparedStatement pst1 = con.prepareStatement(sql1);
        ResultSet rs1 = pst1.executeQuery();
        while (rs1.next()) {
            Product p1 = new Product(rs1.getInt(1), rs1.getString(2), rs1.getDouble(3), rs1.getString(4));
            productList.add(p1);
        }

        System.out.println("Welcome to Electronics Store Management system.");
        while (b == true) {
            System.out.println("1. to Manage Products");
            System.out.println("2. to Manage Inventory");
            System.out.println("3. to Manage Customers");
            System.out.println("4. to Order and Billing");
            System.out.println("5. to Get Analytics");
            System.out.println("6. to Exit");
            System.out.println();
            try {
                System.out.print("Enter your choice: ");
                String choice = sc.next();
                if (!Character.isDigit(choice.charAt(0)) || Integer.parseInt(choice) > 6
                        || Integer.parseInt(choice) < 0) {
                    throw new InValidChoiceException("Enter valid choice");
                }
                switch (Integer.parseInt(choice)) {
                    case 1:
                        boolean b1 = true;
                        while (b1) {
                            System.out.println("1. to Add product");
                            System.out.println("2. to Remove product");
                            System.out.println("3. to Display product by product code");
                            System.out.println("4. to Exit");
                            System.out.println("Enter your choice: ");
                            try {
                                String choice1 = sc.next();
                                if (!Character.isDigit(choice1.charAt(0)) || Integer.parseInt(choice1) > 4
                                        || Integer.parseInt(choice1) < 0) {
                                    throw new InValidChoiceException("Enter valid choice");
                                }
                                switch (Integer.parseInt(choice1)) {
                                    case 1:
                                        // Add product
                                        System.out.print("Enter Product code: ");
                                        int pCode = sc.nextInt();
                                        System.out.print("Enter Product Name: ");
                                        sc.nextLine();
                                        String pName = sc.nextLine();
                                        System.out.print("Enter Price:");
                                        double pPrice = sc.nextDouble();
                                        System.out.print("Enter Product type: ");
                                        sc.nextLine();
                                        String pType = sc.nextLine();
                                        pro.addProducts(pCode, pName, pPrice, pType);
                                        Product pq = new Product(pCode, pName, pPrice, pType);
                                        productList.add(pq);
                                        System.out.println();
                                        break;
                                    case 2:
                                        // Remove product
                                        System.out.println("Enter Product code: ");
                                        int pc;
                                        while (true) {
                                            pc = sc.nextInt();
                                            if (checkValidPCode(pc)) {
                                                pro.deleteProduct(pc);
                                                System.out.println("Product With Id : " + pc + " Is deleted.");
                                                break;
                                            } else
                                                System.out.println("Enter valid product code");
                                        }
                                        // Will remove a particular product from priority queue
                                        for (Product p : productList) {
                                            if (p.p_code == pc) {
                                                productList.remove(p);
                                                break;
                                            }
                                        }
                                        System.out.println();
                                        break;
                                    case 3:
                                        // Display product by product code
                                        int pc1;
                                        while (true) {
                                            System.out.println("Enter Product code : ");
                                            pc1 = sc.nextInt();
                                            if (checkValidPCode(pc1))
                                                break;
                                            else
                                                System.out.println("Enter valid product code");
                                        }
                                        // Will Display a product by calling display method of Product class
                                        for (Product p : productList) {
                                            if (p.p_code == pc1) {
                                                p.display();
                                                break;
                                            }
                                        }
                                        System.out.println();
                                        break;

                                    case 4:
                                        b1 = false;
                                        System.out.println("Returning...");
                                        break;
                                }
                            } catch (InValidChoiceException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        break;
                    case 2:
                        boolean b2 = true;
                        while (b2) {
                            System.out.println("1. to Add Stock");
                            System.out.println("2. to Display Inventory By its Product Code");
                            System.out.println("3. to Exit");
                            try {
                                System.out.print("Enter your choice: ");
                                String choice2 = sc.next();
                                if (!Character.isDigit(choice2.charAt(0)) || Integer.parseInt(choice) > 3
                                        || Integer.parseInt(choice2) < 0) {
                                    throw new InValidChoiceException("Enter valid choice");
                                }
                                switch (Integer.parseInt(choice2)) {
                                    case 1:
                                        // Add Stock
                                        int pc3;
                                        while (true) {
                                            System.out.println("Enter Product code : ");
                                            pc3 = sc.nextInt();
                                            if (checkValidPCode(pc3))
                                                break;
                                            else
                                                System.out.println("Enter valid product code");
                                        }
                                        System.out.println("Enter number of units : ");
                                        int u1 = sc.nextInt();
                                        i.addStock(pc3, u1);
                                        System.out.println();
                                        break;
                                    case 2:
                                        // Display Inventory By its Product Code
                                        int pc4;
                                        while (true) {
                                            System.out.println("Enter Product code : ");
                                            pc4 = sc.nextInt();
                                            if (checkValidPCode(pc4))
                                                break;
                                            else
                                                System.out.println("Enter valid product code");
                                        }
                                        i.DisplayByPCode(pc4);
                                        System.out.println();
                                        break;
                                    case 3:
                                        System.out.println("Returning...");
                                        b2 = false;
                                        break;
                                }
                            } catch (InValidChoiceException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case 3:
                        boolean b3 = true;
                        while (b3) {
                            System.out.println("1. to Add Customer");
                            System.out.println("2. to Display customer by id");
                            System.out.println("3. to Display all customers");
                            System.out.println("4. to Exit");
                            try {
                                System.out.print("Enter your choice: ");
                                String choice3 = sc.next();
                                if (!Character.isDigit(choice3.charAt(0)) || Integer.parseInt(choice3) > 4
                                        || Integer.parseInt(choice3) < 0) {
                                    throw new InValidChoiceException("Enter valid choice");
                                }
                                switch (Integer.parseInt(choice3)) {
                                    case 1:
                                        // add Customer
                                        int cust_id;
                                        System.out.print("Enter name: ");
                                        sc.nextLine();
                                        String cname = sc.nextLine();
                                        System.out.println("Enter Mobile Number: ");
                                        String cno = sc.next();
                                        c.addCustomer(cname, cno);
                                        String s123 = "{call getCustomerId(?)}";
                                        CallableStatement cs = con.prepareCall(s123);
                                        cs.setString(1, cname);
                                        cs.executeUpdate();
                                        cust_id = cs.getInt(1);
                                        Customer p = new Customer(cust_id, cname, cno);
                                        customer_List.addLast(p);
                                        System.out.println();
                                        break;
                                    case 2:
                                        // display customer by id
                                        System.out.println("Enter Customer id: ");
                                        int uid1 = sc.nextInt();
                                        c.displayByID(uid1);
                                        System.out.println();
                                        break;
                                    case 3:
                                        // display all customer
                                        customer_List.display();
                                        System.out.println();
                                        break;
                                    case 4:
                                        b3 = false;
                                        System.out.println("Returning...");
                                        break;
                                    default:
                                        break;
                                }
                            } catch (InValidChoiceException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case 4:
                        boolean b4 = true;
                        int co = 0;
                        int c1_id = 0;
                        while (b4) {
                            if (co == 0) {
                                System.out.println("1. to Add products to cart for billing");
                                System.out.println("3. to Exit");
                                co++;
                            } else {
                                System.out.println("1. to Add products to cart for billing");
                                System.out.println("2. to Generate bill");
                                System.out.println("3. to Exit");
                            }
                            try {
                                System.out.print("Enter your choice: ");
                                String choice4 = sc.next();
                                if (!Character.isDigit(choice4.charAt(0)) || Integer.parseInt(choice4) > 3
                                        || Integer.parseInt(choice4) < 0) {
                                    throw new InValidChoiceException("Enter valid choice");
                                }
                                switch (Integer.parseInt(choice4)) {
                                    case 1:
                                        // Add products to cart for billing
                                        ordered_items = o.orderitem(productList, ordered_items);
                                        System.out.println();
                                        break;
                                    case 2:
                                        // Generate bill
                                        // Call a procedure which will give as a Bill no.
                                        String s123 = "{call getLastBillNo(?)}";
                                        CallableStatement cst = con.prepareCall(s123);
                                        cst.executeUpdate();
                                        b_no = cst.getInt(1);
                                        System.out.println("Enter Customer id: ");
                                        c1_id = sc.nextInt();
                                        // Will Disaply a Bill on Complier Interrface
                                        Billing bill21 = new Billing(c1_id, ordered_items, b_no);
                                        Total_amount = bill21.bill();
                                        // Will Generate a Txt File of Bill
                                        bill21.generateEbill();
                                        o.setDataOrder(c1_id, Total_amount, b_no, ordered_items);
                                        System.out.println();
                                        break;
                                    case 3:
                                        System.out.println("Returning...");
                                        b4 = false;
                                        break;
                                    default:
                                        break;
                                }
                            } catch (InValidChoiceException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        ordered_items.clear();
                        break;
                    case 5:
                        // Will Show an Analytics
                        bil.getAna();
                        System.out.println();
                        break;
                    case 6:
                        System.out.println("Thank You");
                        b = false;
                        break;
                    default:
                        break;
                }
            } catch (InValidChoiceException e) {
                System.out.println(e.getMessage());
            }
        }
        sc.close();
    }

}