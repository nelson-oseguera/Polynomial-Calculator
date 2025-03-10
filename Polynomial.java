package PolynomialTest;

import java.util.Scanner;

//Node class represents a single term in a polynomial
class Node {
 int coefficient; // Coefficient of the term
 int power;       // Power of the term
 Node next;       // Reference to the next term

 // Constructor to initialize the Node with coefficient and power
 public Node(int coefficient, int power) {
     this.coefficient = coefficient;
     this.power = power;
     this.next = null; // Initially, there's no next term
 }
}

//Polynomial class represents a polynomial and provides methods to manipulate polynomials
public class Polynomial {
 private Node head; // Reference to the first term in the polynomial

 // Default constructor to create an empty polynomial
 public Polynomial() {
     head = null;
 }

 // Constructor to create a polynomial with a single term
 public Polynomial(int coefficient, int power) {
     head = new Node(coefficient, power);
 }

 // Method to add a new term to the polynomial
 public void addTerm(int coefficient, int power) {
     Node newNode = new Node(coefficient, power);
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

 // Method to add two polynomials and return the result
 public Polynomial add(Polynomial p) {
     Polynomial result = new Polynomial();
     Node current1 = this.head;
     Node current2 = p.head;

     // Traverse both polynomials simultaneously
     while (current1 != null && current2 != null) {
         // If powers of current terms are equal, add their coefficients and create a new term
         if (current1.power == current2.power) {
             result.addTerm(current1.coefficient + current2.coefficient, current1.power);
             current1 = current1.next;
             current2 = current2.next;
         } else if (current1.power > current2.power) { // If power of the first polynomial term is greater
             result.addTerm(current1.coefficient, current1.power);
             current1 = current1.next;
         } else { // If power of the second polynomial term is greater
             result.addTerm(current2.coefficient, current2.power);
             current2 = current2.next;
         }
     }

     // Add remaining terms from the first polynomial
     while (current1 != null) {
         result.addTerm(current1.coefficient, current1.power);
         current1 = current1.next;
     }

     // Add remaining terms from the second polynomial
     while (current2 != null) {
         result.addTerm(current2.coefficient, current2.power);
         current2 = current2.next;
     }

     return result;
 }

 // Method to evaluate the polynomial for a given value of x
 public int evaluate(int x) {
     int result = 0;
     Node current = head;
     while (current != null) {
         result += current.coefficient * Math.pow(x, current.power);
         current = current.next;
     }
     return result;
 }

 // Method to represent the polynomial as a string
 public String toString() {
     StringBuilder sb = new StringBuilder();
     Node current = head;
     while (current != null) {
         if (current.coefficient != 0) {
             if (current.coefficient > 0 && sb.length() > 0) {
                 sb.append(" + "); // Add a plus sign if the coefficient is positive
             }
             sb.append(current.coefficient); // Append the coefficient
             if (current.power > 0) {
                 sb.append("x"); // If power is greater than 0, append 'x'
                 if (current.power > 1) {
                     sb.append("^").append(current.power); // If power is greater than 1, append '^' and the power
                 }
             }
         }
         current = current.next;
     }
     return sb.toString();
 }

 // Main method to execute the program
 public static void main(String[] args) {
     Scanner scanner = new Scanner(System.in);

     // Input for first polynomial
     System.out.println("Enter the first polynomial (e.g., 3x^4 + 2x^2 + x):");
     String poly1Input = scanner.nextLine();

     // Input for second polynomial
     System.out.println("Enter the second polynomial (e.g., x^4 + 2x^3 - 4x^2 + 5):");
     String poly2Input = scanner.nextLine();

     // Input for first integer value
     System.out.println("Enter the value for x in the first polynomial:");
     int x1 = scanner.nextInt();

     // Input for second integer value
     System.out.println("Enter the value for x in the second polynomial:");
     int x2 = scanner.nextInt();

     // Parse input strings into Polynomial objects
     Polynomial p1 = parsePolynomial(poly1Input);
     Polynomial p2 = parsePolynomial(poly2Input);

     // Display polynomials
     System.out.println("First polynomial: " + p1);
     System.out.println("Second polynomial: " + p2);

     // Add the polynomials
     Polynomial sum = p1.add(p2);
     System.out.println("Sum of polynomials: " + sum);

     // Evaluate polynomials for the given values of x
     System.out.println("Evaluation of first polynomial at x = " + x1 + ": " + p1.evaluate(x1));
     System.out.println("Evaluation of second polynomial at x = " + x2 + ": " + p2.evaluate(x2));
 }

 	// Method to parse a polynomial string into a Polynomial object
 private static Polynomial parsePolynomial(String input) {
     Polynomial polynomial = new Polynomial();
     // Split input polynomial by '+' or '-'
     String[] terms = input.split("\\s*[+\\-]\\s*");
     for (String term : terms) {
         // Split each term by 'x^?' to get coefficient and power
         String[] parts = term.split("x\\^?");
         int coefficient = 0;
         int power = 0;
         if (parts.length > 0 && !parts[0].isEmpty()) {
             // Handle cases where only 'x' or '-x' is present
             if (parts[0].equals("-")) {
                 coefficient = -1;
             } else if (parts[0].equals("x")) {
                 coefficient = 1;
             } else {
                 coefficient = Integer.parseInt(parts[0]);
             }
         }
         if (parts.length > 1 && !parts[1].isEmpty()) {
             power = Integer.parseInt(parts[1]);
         }
         polynomial.addTerm(coefficient, power);
     }
     return polynomial;
 }
}

