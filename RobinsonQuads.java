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
    * findTriangles - a method to find correct order of the three vertices for each triangle
    * @param args is an array of strings that has the A.1.ele file needed for finding the order of the three vertices for each triangle
    * @return an ArrayList of arrays of ints that consists of the order of the three vertices for each triangle
    */
   public static ArrayList<int[]> findTriangles( String[] args ) throws IOException {
      
      FileInputStream fis = null;
      Scanner inputStream = null;
      
      try {
         fis = new FileInputStream(args[(args.length - 1)]);
         inputStream = new Scanner(fis);      
      }
      
      catch (FileNotFoundException e) { //Checks for file not found exception
          System.err.println("Problem opening files");
          System.err.println(e.getMessage()); 
          throw e;
      }  
    
      int[] temp = {0, 0, 0};
      ArrayList<int[]> triangles = new ArrayList<int[]>(); //The ArrayList that will hold the points         
      triangles.add(temp); //Sets the points for the 0 index, since there is nothing given for 0
      int numTriangles = inputStream.nextInt(); //The first number is the amount of triangles  
      inputStream.nextLine();
      int a;
      int b;
      int c;
      int tempTriangle = inputStream.nextInt();
      
      while ( tempTriangle <= numTriangles ) {
         if ( inputStream.hasNextInt() ) {
            a = inputStream.nextInt();
            if ( inputStream.hasNextInt() ) {
               b = inputStream.nextInt();
               if ( inputStream.hasNextInt() ) {
                  c = inputStream.nextInt(); 
                  int arr[] = {a, b, c};
                  triangles.add(arr);
                  if ( inputStream.hasNextLine() ) {
                     inputStream.nextLine();
                     if ( inputStream.hasNextInt() ) { 
                        tempTriangle = inputStream.nextInt();   
                     }
                     else {
                        tempTriangle = numTriangles + 1;
                     }
                  }
               }
            }
         }
      } 
      inputStream.close();
      return triangles;
   } //End findTriangles


   /**
    * printVertices - a method to print the vertex values in the node file
    * @param args is the array of strings that holds the node and ele file
    */
   public static void printVertices( String[] args ) throws IOException {
      
      ArrayList<double[]> list = findVertices( args );
      for ( int i = 1; i < list.size(); i++ ) {
         System.out.println("Vertex " + i + ": " + list.get(i)[0] + " " + list.get(i)[1]);
      }

   }
   
   /**
    * printVertices - a method to print the vertex points for each of the triangles in the ele file
    * @param args is the array of strings that holds the node and ele file
    */
   public static void printTriangles( String[] args ) throws IOException {
      
      ArrayList<int[]> list = findTriangles( args );
      for ( int i = 1; i < list.size(); i++ ) {
         System.out.println("Triangle " + i + ": " + list.get(i)[0] + " " + list.get(i)[1] + " " + list.get(i)[2]);
      }

   }   
   
   /**
    * printTriVertex - a method to print the vertex points and their values for each of the triangles
    * @param args is the array of strings that holds the node and ele file
    */
   public static void printTriVertex( String[] args ) throws IOException {
      
      ArrayList<double[]> vertices = findVertices( args );
      ArrayList<int[]> triangles = findTriangles( args );
      
      for ( int i = 1; i < triangles.size(); i++ ) {
         System.out.println("Triangle " + i);
         for ( int p = 0; p < 3; p++ ) {
            for ( int x = 1; x < vertices.size(); x++ ) {
               if ( x == triangles.get(i)[p] ) { 
                  System.out.println("P" + (p+1) + ": " + vertices.get(x)[0] + " " + vertices.get(x)[1]);
               }
            }
         }
         System.out.println();
      }      
   }   
   
   public static void main( String[] args ) throws IOException {
   
      printTriVertex(args);
   
   }
}
