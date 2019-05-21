package poly;

import java.io.IOException;
import java.util.Scanner;


/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	//private static Node addBack(float coeff, int degree, Node front)
	
	public static Node add(Node poly1, Node poly2) {
		
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		if(poly1 == null && poly2 == null) {
			return null;
		}else if(poly1 == null) {
			return poly2;
		}else if(poly2 == null) {
			return poly1;
		}
		
		Node pointer1 = poly1;
		Node pointer2 = poly2;
		Node newLL = null;
		while(pointer1 != null) {
			if(pointer2 == null) {
				newLL = addToFront(pointer1.term.coeff, pointer1.term.degree, newLL);
				pointer1 = pointer1.next;
			}else if(pointer1.term.degree == pointer2.term.degree) {
				newLL = addToFront(pointer1.term.coeff + pointer2.term.coeff, pointer1.term.degree, newLL);
				pointer1 = pointer1.next;
				pointer2 = pointer2.next;
			}else if(pointer2.term.degree < pointer1.term.degree) {
				newLL = addToFront(pointer2.term.coeff, pointer2.term.degree, newLL);
				pointer2 = pointer2.next;
			}else if(pointer1.term.degree < pointer2.term.degree) {
				newLL = addToFront(pointer1.term.coeff, pointer1.term.degree, newLL);
				pointer1 = pointer1.next;
			}
		}
		while(pointer2 != null) {
			if(pointer1 == null) {
				newLL = addToFront(pointer2.term.coeff, pointer2.term.degree, newLL);
				pointer2 = pointer2.next;
			}
		}
		newLL = flipNode(newLL);
		return newLL;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		if(poly1 == null || poly2 == null) {
			return null;
		}
		Node starter1 = poly1;
		Node starter2 = poly2;
		Node finalAns = null;
		Node newNode = null;
		while(starter1 != null) {
			starter2 = poly2;
			newNode = null;
			while(starter2 != null) {
				newNode = addToFront(starter1.term.coeff*starter2.term.coeff,starter1.term.degree+starter2.term.degree , newNode);
				starter2 = starter2.next;
			}
			newNode = flipNode(newNode);
			finalAns = add(finalAns, newNode);
			starter1 = starter1.next;
		}
		return finalAns;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		float sum = 0;
		Node ptr = poly;
		while(ptr != null) {
			sum = sum + ptr.term.coeff * (float) Math.pow(x, ptr.term.degree);
			ptr = ptr.next;
		}
		return sum;
	}
	
	/**-
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */

	private static Node addToFront(float coeff, int degree, Node list) {
		if(coeff == 0.0) {
			return list;
		}
		Node front = new Node(coeff, degree, list);
		return front;
	}
	
	private static Node flipNode(Node list) {
		Node newNode = null;
		Node currNode = list;
		if(list == null) {
			return null;
		}
		while(currNode != null) {
			newNode = addToFront(currNode.term.coeff, currNode.term.degree, newNode);
			currNode = currNode.next;
		}
		return newNode;
	}
	
	
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
