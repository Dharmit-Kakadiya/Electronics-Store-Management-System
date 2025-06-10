package DataStructure;

import ESMS.Customer;

// Linked list class
public class LinkedListc {
    class Node {
        Customer p;
        Node next;

        // Constructor to initialize a node
        Node(Customer p) {
            this.p = p;
            this.next = null;
        }
    }

    public Node head = null; // Head of the list

    /*
     * Method to add a new node with
     * given data at the end of the list
     */
    public void addLast(Customer p) {
        Node newNode = new Node(p);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // Method to display all details of customer class
    public void display() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
            System.out.println("Customer Code: " + temp.p.c_id);
            System.out.println("Customer Name: " + temp.p.c_name);
            System.out.println("Customer Moblie No. : " + temp.p.c_mobile);
            System.out.println();
        }

    }

}