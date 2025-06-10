import java.io.*;
import java.util.*;
import java.sql.*;

class InValidChoiceException extends Exception {
    InValidChoiceException(String s) {
        super(s);
    }
}

class Product {
    int p_code;
    String p_name;
    double p_price;
    String p_type;

    public Product(int p_code, String p_name, double p_price, String p_type) {
        this.p_code = p_code;
        this.p_name = p_name;
        this.p_price = p_price;
        this.p_type = p_type;
    }

    void display() {
        System.out.println("Product Code: " + p_code);
        System.out.println("Product Name: " + p_name);
        System.out.println("Product Price: " + p_price);
        System.out.println("Product Type: " + p_type);
    }

    void displayBilling() throws Exception {
        System.out.println();
        System.out.println("Product code: " + p_code);
        System.out.println("Product Name: " + p_name);
        System.out.println("Price: " + p_price);
        System.out.println("Stock: " + getUnits(p_code));
        System.out.println();
    }

    int getUnits(int values) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");
        String sql1 = "Select units from inventory where p_code = ?";
        PreparedStatement p1 = con.prepareStatement(sql1);
        p1.setInt(1, values);
        ResultSet b = p1.executeQuery();
        boolean b1 = b.next();
        return b.getInt(1);
    }

}

class Inventory {
    int p_code;
    int units;

    public Inventory(int p_code, int units) {
        this.p_code = p_code;
        this.units = units;
    }

    void display() {
        System.out.println("Product code: " + p_code);
        System.out.println("No. of Units: " + units);
    }
}

class Customer {
    int c_id;
    String c_name, c_mobile;

    public Customer(int c_id, String c_name, String c_mobile) {
        this.c_id = c_id;
        this.c_name = c_name;
        this.c_mobile = c_mobile;
    }

    void displayAllCustomers() {
        System.out.println("Customer id: " + c_id + ", Customer Name: " + c_name + ", Mobile No.: " + c_mobile);
    }
}

class Billing {
    static int b_no = 4265;
    int c_id;
    String c_name, c_mobile;
    ArrayList<Order> o;
    double sum = 0;
    double sum1 = 0;

    public Billing(int c_id, ArrayList<Order> o) throws Exception {
        this.c_id = c_id;
        this.o = o;
        setCustomer(c_id);
    }

    void setCustomer(int c_id) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");
        String sql = "Select c_name,c_mobile from customer_details where c_id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, this.c_id);
        ResultSet rs1 = p.executeQuery();
        boolean b82 = rs1.next();
        this.c_name = rs1.getString(1);
        this.c_mobile = rs1.getString(2);
    }

    void bill() {
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
        b_no++;
    }

    void generateEbill() throws Exception {
        String fname =(int)Math.random() * 3500 + ".txt";
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

}

class Order {
    int p_code;
    double price;
    int units;
    String name;

    public Order(int p_code, double price, int units, String name) {
        this.p_code = p_code;
        this.price = price;
        this.units = units;
        this.name = name;
    }

    void display() {
        System.out.println("Name: " + name);
    }
}

class Estore extends InValidChoiceException {
    Estore(String s) {
        super(s);
    }

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/estore", "root", "");
        Scanner sc = new Scanner(System.in);
        boolean b = true;

        LinkedList<Product> productList = new LinkedList<>();
        String sql1 = "Select * from product_details";
        PreparedStatement pst1 = con.prepareStatement(sql1);
        ResultSet rs1 = pst1.executeQuery();
        while (rs1.next()) {
            Product p1 = new Product(rs1.getInt(1), rs1.getString(2), rs1.getDouble(3), rs1.getString(4));
            productList.add(p1);
        }

        ArrayList<Order> ordered_items = new ArrayList<>();
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
                                System.out.print("Enter your choice: ");
                                String choice1 = sc.next();
                                if (!Character.isDigit(choice1.charAt(0)) || Integer.parseInt(choice1) > 4
                                        || Integer.parseInt(choice1) < 0) {
                                    throw new InValidChoiceException("Enter valid choice");
                                }
                                switch (Integer.parseInt(choice1)) {
                                    case 1:
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
                                        String s1 = "Insert into product_details values(?,?,?,?)";
                                        PreparedStatement ps1 = con.prepareStatement(s1);
                                        ps1.setInt(1, pCode);
                                        ps1.setString(2, pName);
                                        ps1.setDouble(3, pPrice);
                                        ps1.setString(4, pType);
                                        ps1.executeUpdate();
                                        Product pq = new Product(pCode, pName, pPrice, pType);
                                        productList.add(pq);
                                        break;
                                    case 2:
                                        String s2 = "Delete from product_details where p_code = ?";
                                        System.out.println("Enter Product code: ");
                                        int pc;

                                        while (true) {
                                            System.out.println("Enter Product code to select: ");
                                            pc = sc.nextInt();
                                            if (checkValidPCode(pc))
                                                break;
                                            else
                                                System.out.println("Enter valid product code");
                                        }
                                        PreparedStatement ps2 = con.prepareStatement(s2);
                                        ps2.setInt(1, pc);
                                        ps2.executeUpdate();
                                        for (Product p : productList) {
                                            if (p.p_code == pc) {
                                                productList.remove(p);
                                                break;
                                            }
                                        }
                                        break;
                                    case 3:
                                        System.out.println("Enter Product code: ");
                                        int pc1;

                                        while (true) {
                                            System.out.println("Enter Product code to select: ");
                                            pc1 = sc.nextInt();
                                            if (checkValidPCode(pc1))
                                                break;
                                            else
                                                System.out.println("Enter valid product code");
                                        }
                                        for (Product p : productList) {
                                            if (p.p_code == pc1) {
                                                p.display();
                                                break;
                                            }
                                        }
                                        break;

                                    case 4:
                                        b1 = false;
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
                            System.out.println("2. to Display");
                            System.out.println("3. to Exit");
                            try {
                                System.out.print("Enter your choice: ");
                                String choice2 = sc.next();
                                if (!Character.isDigit(choice2.charAt(0)) || Integer.parseInt(choice) > 3
                                        || Integer.parseInt(choice2) < 0) {
                                    throw new InValidChoiceException("Enter valid choice");
                                }
                                switch (Integer.parseInt(choice)) {
                                    case 1:
                                        System.out.println("Product code: ");
                                        int pc3;

                                        while (true) {
                                            System.out.println("Enter Product code to select: ");
                                            pc3 = sc.nextInt();
                                            if (checkValidPCode(pc3))
                                                break;
                                            else
                                                System.out.println("Enter valid product code");
                                        }
                                        System.out.println("Enter number of units : ");
                                        int u1 = sc.nextInt();
                                        String s3 = "Select units from inventory where p_code = ?";
                                        PreparedStatement ps2 = con.prepareStatement(s3);
                                        ps2.setInt(1, pc3);
                                        ResultSet rs2 = ps2.executeQuery();
                                        boolean x = rs2.next();
                                        int unit1 = rs2.getInt(1) + u1;
                                        String s4 = "Update inventory set units =" + unit1 + " where p_code = " + pc3;
                                        Statement st1 = con.createStatement();
                                        st1.executeUpdate(s4);
                                        System.out.println("Stock updated");
                                        break;
                                    case 2:
                                        String s5 = "Select p_code,units from inventory where p_code = ?";
                                        System.out.println("Product code: ");
                                        int pc4;

                                        while (true) {
                                            System.out.println("Enter Product code to select: ");
                                            pc4 = sc.nextInt();
                                            if (checkValidPCode(pc4))
                                                break;
                                            else
                                                System.out.println("Enter valid product code");
                                        }
                                        PreparedStatement ps3 = con.prepareStatement(s5);
                                        ps3.setInt(1, pc4);
                                        ResultSet rs3 = ps3.executeQuery();
                                        boolean y = rs3.next();
                                        System.out.println(
                                                "Product Code: " + rs3.getInt(1) + ", Units : " + rs3.getInt(2));
                                        break;
                                    case 3:
                                        b2 = false;
                                        break;
                                }
                            } catch (InValidChoiceException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                    case 3:
                        boolean b3 = true;
                        while (b3) {
                            System.out.println("1. to add Customer");
                            System.out.println("2. to display customer by id");
                            System.out.println("3. to display all customers");
                            System.out.println("4. to exit");
                            try {
                                System.out.print("Enter your choice: ");
                                String choice3 = sc.next();
                                if (!Character.isDigit(choice3.charAt(0)) || Integer.parseInt(choice3) > 4
                                        || Integer.parseInt(choice3) < 0) {
                                    throw new InValidChoiceException("Enter valid choice");
                                }
                                switch (Integer.parseInt(choice)) {
                                    case 1:
                                        System.out.print("Enter name: ");
                                        sc.nextLine();
                                        String cname = sc.nextLine();
                                        System.out.println("Enter Mobile Number: ");
                                        String cno = sc.next();
                                        String s6 = "Insert into customer_details(c_name,c_mobile) values (?,?)";
                                        PreparedStatement ps5 = con.prepareStatement(s6);
                                        ps5.setString(1, cname);
                                        ps5.setString(2, cno);
                                        int a = ps5.executeUpdate();
                                        System.out.println(
                                                (a > 0) ? "Customer added successfully" : "Customer not added");
                                        break;
                                    case 2:
                                        System.out.println("Enter Customer id: ");
                                        int uid1 = sc.nextInt();
                                        Statement st = con.createStatement();
                                        String s4 = "Select * from customer_details where c_id =" + uid1;
                                        ResultSet rs4 = st.executeQuery(s4);
                                        boolean z = rs4.next();
                                        System.out.println("Customer Id: " + rs4.getInt(1) + ", Customer Name: "
                                                + rs4.getString(2) + ", Customer contact no. : " + rs4.getString(3));
                                        break;
                                    case 3:
                                        String s8 = "Select * from customer_details";
                                        Statement st3 = con.createStatement();
                                        ResultSet rs6 = st3.executeQuery(s8);
                                        while (rs6.next()) {
                                            Customer u = new Customer(rs6.getInt(1), rs6.getString(2),
                                                    rs6.getString(3));
                                            u.displayAllCustomers();
                                        }

                                        break;
                                    case 4:
                                        b3 = false;
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
                        int c1_id = 0;
                        while (b4) {
                            System.out.println("1. to Add products to cart for billing");
                            System.out.println("2. to Generate bill");
                            System.out.println("3. to Exit");
                            try {
                                System.out.print("Enter your choice: ");
                                String choice4 = sc.next();
                                if (!Character.isDigit(choice4.charAt(0)) || Integer.parseInt(choice4) > 3
                                        || Integer.parseInt(choice4) < 0) {
                                    throw new InValidChoiceException("Enter valid choice");
                                }
                                switch (Integer.parseInt(choice4)) {
                                    case 1:

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
                                        break;
                                    case 2:
                                        System.out.println("Enter Customer id: ");
                                        c1_id = sc.nextInt();
                                        Billing bill21 = new Billing(c1_id, ordered_items);
                                        bill21.bill();
                                        bill21.generateEbill();
                                        break;
                                    case 3:
                                        b4 = false;
                                        break;
                                    default:
                                        break;
                                }
                            } catch (InValidChoiceException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        String o_history = "Insert into orders values(?,?,?,?)";
                        PreparedStatement pst = con.prepareStatement(o_history);
                        for (Order x : ordered_items) {
                            pst.setInt(1, c1_id);
                            pst.setInt(2, x.p_code);
                            pst.setString(3, x.name);
                            pst.setDouble(4, x.price);
                            pst.executeUpdate();
                        }
                        String s123 = "{call StockUpdate(?)}";
                        for (Order x : ordered_items) {
                            CallableStatement cst = con.prepareCall(s123);
                            cst.setInt(1, x.p_code);
                            cst.executeUpdate();
                        }
                        ordered_items.clear();
                        break;
                    case 5:
                        double totalAmount = 0;
                        int totalunits = 0;
                        int totalTV = 0;
                        int totalAC = 0, totalRef = 0, totalMobile = 0, totalSpeakers = 0, totalOven = 0, totalWM = 0,
                                totalLaptop = 0, totalHearables = 0;
                        String analytics1 = "Select product_code from orders";
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
}