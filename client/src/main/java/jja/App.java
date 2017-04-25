package jja;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Veuillez saisir un mot :");
    	String str = sc.nextLine();
    	System.out.println("Vous avez saisi : " + str);    }
}
