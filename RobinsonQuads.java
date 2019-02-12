/**
 * ReadTriangle reads in points from a triangle file
 * @author Nicholas Senatore
 * @since 2/10/2019
 */

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileNotFoundException;

public class RobinsonQuads {
   /**
    * findVertices - a method to find the vertices for each of the triangles
    * @param args is an array of strings that has the A.1.node file needed for finding the vertices
    * @return an ArrayList of arrays of doubles that consists of the vertices for each triangle
    */
   public static ArrayList<double[]> findVertices( String[] args ) throws IOException {
      
      FileInputStream fis = null;
      Scanner inputStream = null;
      
      try {
         fis = new FileInputStream(args[(args.length - 2)]);
         inputStream = new Scanner(fis);  
      }
      
      catch (FileNotFoundException e) { //Checks for file not found exception
          System.err.println("Problem opening files");
          System.err.println(e.getMessage()); 
          throw e;
      }   
           
      double[] temp = {0.0, 0.0};
      ArrayList<double[]> list = new ArrayList<double[]>(); //The ArrayList that will hold the points      
      list.add(temp); //Sets the points for the 0 index, since there is nothing given for 0
      int numVertices = inputStream.nextInt(); //The first number is the amount of vertices   
      inputStream.nextLine();
      double a;
      double b;
      int tempVertex = inputStream.nextInt();
      
      while ( tempVertex <= numVertices ) {
         if ( inputStream.hasNextDouble() ) {
            a = inputStream.nextDouble();
            if ( inputStream.hasNextDouble() ) {
               b = inputStream.nextDouble();
               double arr[] = {a, b};
               list.add(arr);
               if ( inputStream.hasNextLine() ) {
                  inputStream.nextLine();
                  if ( inputStream.hasNextInt() ) { 
                     tempVertex = inputStream.nextInt();   
                  }
                  else {
                     tempVertex = numVertices + 1;
                  }
               }
            }
         }
      }
      inputStream.close();
      return list;
   } //End findVertices

   /**
    * printVertices - a method to print the vertices for each of the triangles
    * @param list is the list of vertices
    */
   public static void printVertices( String[] args ) throws IOException {
      
      ArrayList<double[]> list = findVertices( args );
      for ( int i = 1; i < list.size(); i++ ) {
         System.out.println("Vertex " + i + ": " + list.get(i)[0] + " " + list.get(i)[1]);
      }

   }
   
   public static void main( String[] args ) throws IOException {
   
      printVertices(args);
   
   }
}
