/**
 * ReadTriangle reads in points from a triangle file
 * @author Nicholas Senatore
 * @since 3/4/2019
 */

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileNotFoundException; 

public class RobinsonQuads {
	
  /* Holds the triangles */
   public static ArrayList<int[]> triangle = new ArrayList<int[]>();
   
   /* Holds the vertices */
   public static ArrayList<double[]> vertex = new ArrayList<double[]>();
   
   /* Holds the x values of the vertices */
   public static ArrayList<double[]> x = new ArrayList<double[]>();
   
   /* Holds the y values of the vertices */
   public static ArrayList<double[]> y = new ArrayList<double[]>();
   
   /* Holds the x values of the midpoints for each line segment */
   public static ArrayList<double[]> midx = new ArrayList<double[]>();
   
   /* Holds the y values of the midpoints for each line segment */
   public static ArrayList<double[]> midy = new ArrayList<double[]>();      
   
   /* Holds the sum of the x values */
   public static ArrayList<Double> totalx = new ArrayList<Double>();
   
   /* Holds the sum of the y values */
   public static ArrayList<Double> totaly = new ArrayList<Double>();    

   /* Holds the x values for each of the quads */
   public static ArrayList<double[]> QuadsX = new ArrayList<double[]>();
   
   /* Holds the y values for each of the quads */
   public static ArrayList<double[]> QuadsY = new ArrayList<double[]>();   
   
   /* Holds the x values for the centroids for each line segment */
   public static ArrayList<Double> CentroidX = new ArrayList<Double>();
   
   /* Holds the y values for the centroids for each line segment */
   public static ArrayList<Double> CentroidY = new ArrayList<Double>();   	
	
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

   // ----------------------------------------------------------------------------------------------
   // BEGINNING OF FINDING QUADS
   // ----------------------------------------------------------------------------------------------   
   
   /**
    * findMidXY - a method to find the x and y values, as well as the midpoints for each triangle, and save them in ArrayLists
    */
   public static void findMidXY( ) {
      
      for ( int i = 0; i < triangle.size(); i++ ) {
      
         int[] tempTr = triangle.get(i);
         int j = 0;
         
         //Calculates the x and y values, as well as their sums
         double a[] = vertex.get(tempTr[j]);
         double b[] = vertex.get(tempTr[j + 1]);
         double c[] = vertex.get(tempTr[j + 2]);
         
         double[] xval = {a[0], b[0], c[0]};
         double[] yval = {a[1], b[1], c[1]};
         
         x.add(xval);
         y.add(yval);
         
         Double sumx = 0.0;
         Double sumy = 0.0;         
         
         sumx += a[0];
         sumy += a[1];
         sumx += b[0];
         sumy += b[1];
         sumx += c[0];
         sumy += c[1];
         
         totalx.add(sumx);
         totaly.add(sumy);         
                            
         //Calculates the midpoint values
         double midx1 = (a[0] + b[0]) / 2;
         double midy1 = (a[1] + b[1]) / 2;   
         
         double midx2 = (a[0] + c[0]) / 2;
         double midy2 = (a[1] + c[1]) / 2;
         
         double midx3 = (b[0] + c[0]) / 2;
         double midy3 = (b[1] + c[1]) / 2;
         
         double[] xmidval = {midx1, midx2, midx3};
         double[] ymidval = {midy1, midy2, midy3};
         
         midx.add(xmidval);
         midy.add(ymidval);

      }
   } //End findMidXY

   /**
    * findCentroid - a method to find centroid values for each triangle and save them in an ArrayList
    */
   public static void findCentroid( ) {
      
      for ( int i = 0; i < triangle.size(); i++ ) {
         Double sumx = totalx.get(i);
         Double sumy = totaly.get(i);
         Double CX = sumx / 3;
         Double CY = sumy / 3;
         CentroidX.add(CX);
         CentroidY.add(CY);
      }      
   } //End findCentroid    

   // ----------------------------------------------------------------------------------------------
   // BEGINNING OF SET METHODS
   // ----------------------------------------------------------------------------------------------      
   
   /**
    * setTriangle - a method necessary for testing, sets the values for the triangles
    * @param triangle is the triangle ArrayList used by the test case
    */
   public void setTriangle(ArrayList<int[]> triangle) {
       RobinsonQuads.triangle = triangle;
   } //End setTriangle
   
   /**
    * setVertices - a method necessary for testing, sets the values for the vertices
    * @param vertex is the vertex ArrayList used by the test case
    */   
   public void setVertices(ArrayList<double[]> vertex) {
       RobinsonQuads.vertex = vertex;
   } //End setVertices
   
   /**
    * setQuads - a method to set the quad values using both the findMidXY and findCentroid methods, and save them in ArrayLists
    */
   public static void setQuads( ) {

      findMidXY(); //Finds the x and y values, as well as their sums
      findCentroid(); //Finds the centroids for each quad

      for ( int i = 1; i < triangle.size(); i++ ) {     
    	  
         double[] xQuadValsTemp1 = {CentroidX.get(i), midx.get(i)[0], x.get(i)[1], midx.get(i)[2]};
         double[] yQuadValsTemp1 = {CentroidY.get(i), midy.get(i)[0], y.get(i)[1], midy.get(i)[2]};
         QuadsX.add(xQuadValsTemp1);
         QuadsY.add(yQuadValsTemp1);            
                            
         double[] xQuadValsTemp2 = {CentroidX.get(i), midx.get(i)[1], x.get(i)[0], midx.get(i)[0]};
         double[] yQuadValsTemp2 = {CentroidY.get(i), midy.get(i)[1], y.get(i)[0], midy.get(i)[0]};                           
         QuadsX.add(xQuadValsTemp2);
         QuadsY.add(yQuadValsTemp2);   
                                           
         double[] xQuadValsTemp3 = {CentroidX.get(i), midx.get(i)[1], x.get(i)[2], midx.get(i)[2]};
         double[] yQuadValsTemp3 = {CentroidY.get(i), midy.get(i)[1], y.get(i)[2], midy.get(i)[2]};                           
         QuadsX.add(xQuadValsTemp3);
         QuadsY.add(yQuadValsTemp3);                
         
      }    
   } //End setQuads   
   
   // ----------------------------------------------------------------------------------------------
   // BEGINNING OF PRINT METHODS
   // ----------------------------------------------------------------------------------------------   
   
   /**
    * printTriVertex - a method to print the vertex points and their values for each of the triangles (MUST TYPE -printt)
    * @param args is the array of strings that holds the node and ele file
    */
   public static void printTriVertex( String[] args ) throws IOException {
      
      ArrayList<double[]> vertices = findVertices( args );
      ArrayList<int[]> triangles = findTriangles( args );
      
      System.out.println("----------------------------------------------------------------------------------------------"); 
      System.out.println("TRIANGLES");
      System.out.println("----------------------------------------------------------------------------------------------"); 	  
	  
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
   
   /**
    * printQuads - a method to print out the quads (MUST TYPE -printq)
    */
   public static void printQuads( ) {   
      System.out.println("----------------------------------------------------------------------------------------------"); 
      System.out.println("QUADS");
      System.out.println("----------------------------------------------------------------------------------------------"); 
      for ( int i = 0; i < QuadsX.size(); i++ ) {     
         System.out.println("Triangle " + ((i/3) + 1) + " Quad " + ((i%3) + 1) );
		 System.out.println("P1: " + QuadsX.get(i)[0] + " " + QuadsY.get(i)[0]);
		 System.out.println("P2: " + QuadsX.get(i)[1] + " " + QuadsY.get(i)[1]);
		 System.out.println("P3: " + QuadsX.get(i)[2] + " " + QuadsY.get(i)[2]);
		 System.out.println("P4: " + QuadsX.get(i)[3] + " " + QuadsY.get(i)[3]); 
		 System.out.println();
      }
   } //End printQuads   
   
   /**
    * outputRCode - a method to output the file that contains the R code to draw a quad / triangle (MUST TYPE -r)
    * @param fileNameR is the file name
    */
   public static void outputRCode(String fileNameR) throws FileNotFoundException {

	  int triNum;
	  String rtext = "";
	  
	  rtext += "centroid = function ( X, Y ) {" + System.lineSeparator()
		   + "cx = (X[1] + X[2] + X[3] + X[4]) / 4" + System.lineSeparator()
		   + "cy = (Y[1] + Y[2] + Y[3] + Y[4]) / 4" + System.lineSeparator()
		   + "data.frame( cx, cy )" + System.lineSeparator()
   		+ "}" + System.lineSeparator() + System.lineSeparator()
   			
	  		+ "show = function ( X, Y, TX=NULL, TY=NULL, title=\"\" ) {" + System.lineSeparator()
	  		+ "TL = c( \"t1\", \"t2\", \"t3\" )  #labels for tri  pts" + System.lineSeparator()
			+ "QL = c( \"p1\", \"p2\", \"p3\", \"p4\" )  #labels for quad pts" + System.lineSeparator()
	  		+ "width = max( abs( X[1] - X[2] )," + System.lineSeparator()
			+ "abs( X[1] - X[3] )," + System.lineSeparator()
			+ "abs( X[1] - X[4] )," + System.lineSeparator()
			+ "abs( X[2] - X[3] )," + System.lineSeparator()
			+ "abs( X[2] - X[4] )," + System.lineSeparator()
         + "abs( X[3] - X[4] )," + System.lineSeparator()
         + "abs( Y[1] - Y[2] )," + System.lineSeparator()
         + "abs( Y[1] - Y[3] )," + System.lineSeparator()
         + "abs( Y[1] - Y[4] )," + System.lineSeparator()
         + "abs( Y[2] - Y[3] )," + System.lineSeparator()
         + "abs( Y[2] - Y[4] )," + System.lineSeparator()
         + "abs( Y[3] - Y[4] ) )" + System.lineSeparator()
         + "plot( TX, TY, asp=1, main=title, col=\"gray\", pch=2, xlab=\"x\", ylab=\"y\" )" + System.lineSeparator()
         + "width = max( width, " + System.lineSeparator()
         + "abs( TX[1] - TX[2] )," + System.lineSeparator()
         + "abs( TX[1] - TX[3] )," + System.lineSeparator()
         + "abs( TX[2] - TX[3] )," + System.lineSeparator()
         + "abs( TY[1] - TY[2] )," + System.lineSeparator()
         + "abs( TY[1] - TY[3] )," + System.lineSeparator()
         + "abs( TY[2] - TY[3] ) )" + System.lineSeparator()
         + "text( TX+0.03*width, TY+0.02*width, labels=TL, col=\"gray\" )" + System.lineSeparator()
         + "segments( TX[1], TY[1], TX[2], TY[2], col=\"gray\", lty=2 )" + System.lineSeparator()
         + "segments( TX[2], TY[2], TX[3], TY[3], col=\"gray\", lty=2 )" + System.lineSeparator()
         + "segments( TX[3], TY[3], TX[1], TY[1], col=\"gray\", lty=2 )" + System.lineSeparator()
         + "points( X, Y, pch=15 )" + System.lineSeparator()
         + "text( X+0.03*width, Y+0.02*width, labels=QL )" + System.lineSeparator()
         + "segments( X[1], Y[1], X[2], Y[2] )" + System.lineSeparator()
         + "segments( X[2], Y[2], X[3], Y[3] )" + System.lineSeparator()
         + "segments( X[3], Y[3], X[4], Y[4] )" + System.lineSeparator()
         + "segments( X[4], Y[4], X[1], Y[1] )" + System.lineSeparator() + System.lineSeparator()
            
         + "#calc centroid" + System.lineSeparator()
        	+ "c = centroid( X, Y )" + System.lineSeparator()
        	+ "#calc limits" + System.lineSeparator()
        	+ "minX = X[1]" + System.lineSeparator()
        	+ "maxX = X[1]" + System.lineSeparator()
        	+ "minY = Y[1]" + System.lineSeparator()
        	+ "maxY = Y[1]" + System.lineSeparator()
        	+ "for (i in c(2, 3, 4)) {" + System.lineSeparator()
        	+ "	if (X[i] < minX)    minX = X[i]" + System.lineSeparator()
        	+ "	if (X[i] > maxX)    maxX = X[i]" + System.lineSeparator()
        	+ "	if (Y[i] < minY)    minY = Y[i]" + System.lineSeparator()
        	+ "	if (Y[i] > maxY)    maxY = Y[i]" + System.lineSeparator()
         + "}" + System.lineSeparator()
   		+ "#draw coordinate system centered at centroid" + System.lineSeparator()
   		+ "points( c, col=\"gray\" )" + System.lineSeparator()
   		+ "segments( minX, c$cy, maxX, c$cy, col=\"gray\", lty=3 )" + System.lineSeparator()
   		+ "segments( c$cx, minY, c$cx, maxY, col=\"gray\", lty=3 )" + System.lineSeparator()
              	
         + "}" + System.lineSeparator() + System.lineSeparator();

	  for ( int i = 0; i < QuadsX.size(); i++ ) {
		  rtext += "X = c(" + QuadsX.get(i)[0] + ", " + QuadsX.get(i)[1] + ", " + QuadsX.get(i)[2] + ", " + QuadsX.get(i)[3] + ")" + System.lineSeparator()
			     + "Y = c(" + QuadsY.get(i)[0] + ", " + QuadsY.get(i)[1] + ", " + QuadsY.get(i)[2] + ", " + QuadsY.get(i)[3] + ")" + System.lineSeparator();
		  
		  if ((i + 1) % 3 == 0){
			  triNum = (i+1) / 3;
		  }
		  else{
			  triNum = ((i+1) / 3) + 1;
		  }
		  
		  rtext += "TX = c(" + x.get(triNum)[0] + ", " + x.get(triNum)[1] + ", " + x.get(triNum)[2] + ")" + System.lineSeparator()
		  		 + "TY = c(" + y.get(triNum)[0] + ", " + y.get(triNum)[1] + ", " + y.get(triNum)[2] + ")" + System.lineSeparator();
		  
		  String title = "Quad " + (i + 1) + ", Triangle " + triNum;
		  rtext += "show( X, Y, TX, TY, title=\"" + title + "\")" + System.lineSeparator() + System.lineSeparator();
	  }
	  
	  try (PrintWriter out = new PrintWriter(fileNameR + ".r")){
		  out.println(rtext);
	  }
      catch (FileNotFoundException e) { //Checks for file not found exception
          System.err.println("Problem creating files");
          System.err.println(e.getMessage()); 
          throw e;
      }   
   } //End outputRCode
   
   // ----------------------------------------------------------------------------------------------
   // MAIN METHOD
   // ----------------------------------------------------------------------------------------------      
   
   /**
   * main - a method that sends the A.1.node and A.1.ele files to the program, calling previous methods
   * @param args is an array of strings that consists of both A.1.node and A.1.ele
   */      
   public static void main( String[] args ) throws IOException {

      //Command Methods
      
      boolean printQuadsNeeded = false;
	   boolean printTrianglesNeeded = false;
      boolean outputRCodeNeeded = false;
      String fileNameR = null;
        
      for ( int i = 0; i < args.length; i++ ) {
         
		   if ( args[i].equals("-printt") ) {
		      printTrianglesNeeded = true; 
		   }
         if ( args[i].equals("-printq") ) {
            printQuadsNeeded = true;
         }
         if ( args[i].equals("-r") ) {
            outputRCodeNeeded = true;
            
        	   fileNameR = args[i + 1];
        	   if (i == args.length - 3 || fileNameR.substring(0,1).equals("-") || fileNameR.equals(null)){
        		   fileNameR = "rcode";
        	   }            
         }
      }
	  
      triangle = findTriangles(args); //Holds the triangles
      vertex = findVertices(args); //Holds the vertices
      setQuads(); //Sets the values for the quads            

      if ( printTrianglesNeeded ) {
         printTriVertex(args);
      }   
      if ( printQuadsNeeded ) {
         printQuads();
      }
      if ( outputRCodeNeeded ) {
         outputRCode(fileNameR);
      }
   }
}
