/**
 * ReadTriangle reads in points from a triangle file
 * @author Nicholas Senatore
 * @since 4/1/2019
 */

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileNotFoundException; 
import java.math.BigDecimal;

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
   
   /* Holds the aspect ratio values for the Jacobian */
   public static ArrayList<Double> jAspectRatio = new ArrayList<Double>();

   /* Holds the skew values for the Jacobian */
   public static ArrayList<Double> jSkew = new ArrayList<Double>();   
   
   /* Holds the X taper values for the Jacobian */
   public static ArrayList<Double> jtaperX = new ArrayList<Double>();
   
   /* Holds the Y taper values for the Jacobian */
   public static ArrayList<Double> jtaperY = new ArrayList<Double>();   
	
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
   // BEGINNING OF JACOBIAN METHODS
   // ----------------------------------------------------------------------------------------------

   /**
    * calculateJAR - a method to find the Jacobian Aspect Ratio
    * @param i is the index of the quad
    * @param p1 is the original coordinates of p1
    * @param p2 is the original coordinates of p2
    * @param p3 is the original coordinates of p3
    * @param p4 is the original coordinates of p4
    * @return the Jacobian Aspect Ratio
    */
   public static double calculateJAR( int i, double[] p1, double[] p2, double[] p3, double[] p4 ) {    
       
      // 1, -2 / 1.5, -.5 / 1, 0 / .5, -.5  
	         
      double[] e = new double[4];
      double[] f = new double[4];
      
      findEF(p1, p2, p3, p4, e, f);        
      
      /*
      System.out.println("E2: " + e[1]);
      System.out.println("F3: " + f[2]);
      */
      
      assert ( f[2] != 0 && e[1] != 0 );
      
      double aspR1 = e[1]/f[2]; // 0.5
      double aspR2 = f[2]/e[1]; // 2

      /*
      System.out.println("ASP Rats: " + aspR1 + " " + aspR2);
      System.out.println(Math.max(aspR1, aspR2));
      System.out.println();
	  */

      jAspectRatio.add(i, Math.max(aspR1, aspR2));
      
      return Math.max(aspR1, aspR2); //Aspect Ratio is the max of the two given variables
   } //End calculateJAR
   
   /**
    * calculateJSkewAngle - a method to find the Jacobian Skew Angle
    * @param i is the index of the quad
    * @param p1 is the original coordinates of p1
    * @param p2 is the original coordinates of p2
    * @param p3 is the original coordinates of p3
    * @param p4 is the original coordinates of p4
    * @return the Jacobian Skew Angle
    */   
   public static double calculateJSkewAngle( int i, double[] p1, double[] p2, double[] p3, double[] p4 ) { 
            
      double[] e = new double[4];
      double[] f = new double[4];
      
      findEF(p1, p2, p3, p4, e, f);
                 
      assert ( f[2] != 0 );
                       
      jSkew.add(i, e[2] / f[2]);
      
      return (e[2] / f[2]); // -0.5
   } //End calculateJSkewAngle
   
   /**
    * calculateJTapers - a method to find the Jacobian Tapers in X and Y directions
    * @param i is the index of the quad
    * @param p1 is the original coordinates of p1
    * @param p2 is the original coordinates of p2
    * @param p3 is the original coordinates of p3
    * @param p4 is the original coordinates of p4
    * @return the Jacobian Tapers in X and Y directions (saved in an array)
    */      
   public static double[] calculateJTapers( int i, double[] p1, double[] p2, double[] p3, double[] p4 ) {
      
      double[] e = new double[4];
      double[] f = new double[4];

      findEF(p1, p2, p3, p4, e, f);
      
      double[] TapersXY = new double[2];
      
      assert ( f[2] != 0 || e[1] != 0 );
            
      TapersXY[0] = f[3] / f[2]; //Taper in the X direction -0.5
      TapersXY[1] = e[3] / e[1]; //Taper in the Y direction 0
      
      jtaperX.add(i, TapersXY[0]);
      jtaperY.add(i, TapersXY[1]);
      
      return TapersXY;
        
   } //End calculateJTapers   

   /**
    * findEF - a method to find the values for e1, e2, e3, e4, f1, f2, f3, and f4
    * @param i is the index of the quad
    * @param p1 is the original coordinates of p1
    * @param p2 is the original coordinates of p2
    * @param p3 is the original coordinates of p3
    * @param p4 is the original coordinates of p4
    */   
   public static void findEF( double[] p1, double[] p2, double[] p3, double[] p4, double[] e, double[] f ) {
   
      // 1, -2 / 1.5, -.5 / 1, 0 / .5, -.5     
      e[0] = (p1[0] + p2[0] + p3[0] + p4[0]) / 4;               //e1 = 1
      e[1] = (-p1[0] + p2[0] + p3[0] + -p4[0]) / 4; //e2 = 0.25
      e[2] = (-p1[0] + -p2[0] + p3[0] + p4[0]) / 4; //e3 = -0.25       
      e[3] = (p1[0] + -p2[0] + p3[0] + -p4[0]) / 4; //e4 = 0

      f[0] = (p1[1] + p2[1] + p3[1] + p4[1]) / 4;               //f1 = -0.75
      f[1] = (-p1[1] + p2[1] + p3[1] + -p4[1]) / 4; //f2 = 0.5
      f[2] = (-p1[1] + -p2[1] + p3[1] + p4[1]) / 4; //f3 = 0.5
      f[3] = (p1[1] + -p2[1] + p3[1] + -p4[1]) / 4; //f4 = -0.25
   } //End findEF
   
   /**
    * outputARJ - a method to output the aspect ratio values for the Jacobian (MUST TYPE "-jaspectratio")
    * @param fileNameJAR is the file name 
    * @param verbose determines the verbose level
    */
   public static void outputARJ(String fileNameJAR, int verbose) throws FileNotFoundException {
	  String artext = "";
	  BigDecimal min = BigDecimal.valueOf(jAspectRatio.get(0));
	  BigDecimal max = BigDecimal.valueOf(jAspectRatio.get(0));
	  int minpos = 1;
	  int maxpos = 1;
	  if (verbose > 0){
		  artext += "ASPECT RATIOS FOR JACOBIAN" + System.lineSeparator();
	  }
	  for ( int i = 0; i < jAspectRatio.size(); i++ ) {
		  if(Double.isInfinite(jAspectRatio.get(i))){
			  artext = artext + BigDecimal.valueOf(2147483647) + " #" + (i + 1) + ", ";
		  }
		  else{
			  artext = artext + BigDecimal.valueOf(jAspectRatio.get(i)) + " #" + (i + 1) + ", ";
		  }
		  if ((i + 1) % 3 == 0){
			  artext += "triangle " + ((i + 1) / 3) + System.lineSeparator();
		  }
		  else{
			  artext += "triangle " + (((i + 1) / 3) + 1) + System.lineSeparator();
		  }
		  if(Double.isInfinite(jAspectRatio.get(i))){
			  if (BigDecimal.valueOf(2147483647).compareTo(max) > 0){
				  max = BigDecimal.valueOf(2147483647);
				  maxpos = i + 1;
			  }
			  if (BigDecimal.valueOf(2147483647).compareTo(min) < 0){
				  min = BigDecimal.valueOf(2147483647);
				  minpos = i + 1;
			  }
		  }
		  else{
			  if (BigDecimal.valueOf(jAspectRatio.get(i)).compareTo(max) > 0){
				  max = BigDecimal.valueOf(jAspectRatio.get(i));
				  maxpos = i + 1;
			  }
			  if (BigDecimal.valueOf(jAspectRatio.get(i)).compareTo(min) < 0){
				  min = BigDecimal.valueOf(jAspectRatio.get(i));
				  minpos = i + 1;
			  }
		  }
	  }
	  
	  if (verbose > 0) {
		  artext += "#The minimum value is " + min + ", which is in quad " + minpos + " {" + findQuadsCoordinates(minpos - 1) + "}, which is in triangle ";
		  if (minpos % 3 == 0){
			  artext += (minpos / 3) + " {" + findTrianglesCoordinates(minpos / 3) + "}" + System.lineSeparator();
		  }
		  else{
			  artext += ((minpos / 3) + 1) + " {" + findTrianglesCoordinates((minpos / 3) + 1) + "}" + System.lineSeparator();
		  }
		  artext += "#The maximum value is " + max + ", which is in quad " + maxpos + " {" + findQuadsCoordinates(maxpos - 1) + "}, which is in triangle ";
		  if (maxpos % 3 == 0){
			  artext += (maxpos / 3) + " {" + findTrianglesCoordinates(maxpos / 3) + "}" + System.lineSeparator();
		  }
		  else{
			  artext += ((maxpos / 3) + 1) + " {" + findTrianglesCoordinates((maxpos / 3) + 1) + "}" + System.lineSeparator();
		  }
		  artext += "#The mean average is " + findMean(jAspectRatio) + System.lineSeparator();
		  
	  }
	  
	  try (PrintWriter out = new PrintWriter(fileNameJAR + ".txt")){
		  out.println(artext);
	  }
      catch (FileNotFoundException e) { //Checks for file not found exception
          System.err.println("Problem creating files");
          System.err.println(e.getMessage()); 
          throw e;
      }   
   } //End outputARJ        
   
   /**
    * outputSkewJ - a method to output the skew values for the Jacobian (MUST TYPE "-jskew")
    * @param fileNameJS is the file name 
    * @param verbose determines the verbose level
    */
   public static void outputSkewJ(String fileNameJS, int verbose) throws FileNotFoundException {
	  BigDecimal min = BigDecimal.valueOf(jSkew.get(0));
	  BigDecimal max = BigDecimal.valueOf(jSkew.get(0));
	  int minpos = 1;
	  int maxpos = 1;
	   String skewtext = "";
	  if (verbose > 0){
		  skewtext += "SKEWS FOR JACOBIAN" + System.lineSeparator();
	  }
	  for ( int i = 0; i < jSkew.size(); i++ ) {
		  skewtext = skewtext + BigDecimal.valueOf(jSkew.get(i)) + " #" + (i + 1) + ", ";
		  if ((i + 1) % 3 == 0){
			  skewtext += "triangle " + ((i + 1) / 3) + System.lineSeparator();
		  }
		  else{
			  skewtext += "triangle " + (((i + 1) / 3) + 1) + System.lineSeparator();
		  }
		  if (BigDecimal.valueOf(jSkew.get(i)).compareTo(max) > 0){
			  max = BigDecimal.valueOf(jSkew.get(i));
			  maxpos = i + 1;
		  }
		  if (BigDecimal.valueOf(jSkew.get(i)).compareTo(min) < 0){
			  min = BigDecimal.valueOf(jSkew.get(i));
			  minpos = i + 1;
		  }
	  }
	  
	  if (verbose > 0) {
		  skewtext += "#The minimum value is " + min + ", which is in quad " + minpos + " {" + findQuadsCoordinates(minpos - 1) + "}, which is in triangle ";
		  if (minpos % 3 == 0){
			  skewtext += (minpos / 3) + " {" + findTrianglesCoordinates(minpos / 3) + "}" + System.lineSeparator();
		  }
		  else{
			  skewtext += ((minpos / 3) + 1) + " {" + findTrianglesCoordinates((minpos / 3) + 1) + "}" + System.lineSeparator();
		  }
		  skewtext += "#The maximum value is " + max + ", which is in quad " + maxpos + " {" + findQuadsCoordinates(maxpos - 1) + "}, which is in triangle ";
		  if (maxpos % 3 == 0){
			  skewtext += (maxpos / 3) + " {" + findTrianglesCoordinates(maxpos / 3) + "}" + System.lineSeparator();
		  }
		  else{
			  skewtext += ((maxpos / 3) + 1) + " {" + findTrianglesCoordinates((maxpos / 3) + 1) + "}" + System.lineSeparator();
		  }
		  skewtext += "#The mean average is " + findMean(jSkew) + System.lineSeparator();
		  
	  }
	  
	  try (PrintWriter out = new PrintWriter(fileNameJS + ".txt")){
		  out.println(skewtext);
	  }
      catch (FileNotFoundException e) { //Checks for file not found exception
          System.err.println("Problem creating files");
          System.err.println(e.getMessage()); 
          throw e;
      }   
   } //End outputSkewJ   

   /**
    * outputTapersXYJ - a method to output the taper values for the Jacobian (MUST TYPE "-jtaper")
    * @param fileNameJT is the file name 
    * @param verbose determines the verbose level
    */
   public static void outputTapersXYJ(String fileNameJT, int verbose) throws FileNotFoundException {
	  String tapertext = "";
	  BigDecimal minx = BigDecimal.valueOf(jtaperX.get(0));
	  BigDecimal maxx = BigDecimal.valueOf(jtaperX.get(0));
	  BigDecimal miny = BigDecimal.valueOf(jtaperY.get(0));
	  BigDecimal maxy = BigDecimal.valueOf(jtaperY.get(0));
	  int minposx = 1;
	  int maxposx = 1;
	  int minposy = 1;
	  int maxposy = 1;
	  
	  if (verbose > 0){
		  tapertext += "TAPERS FOR JACOBIAN" + System.lineSeparator();
	  }
	  for ( int i = 0; i < jtaperX.size(); i++ ) {
		  tapertext = tapertext + BigDecimal.valueOf(jtaperX.get(i)) + " " + BigDecimal.valueOf(jtaperY.get(i)) + " #" + (i + 1) + ", ";
		  if ((i + 1) % 3 == 0){
			  tapertext += "triangle " + ((i + 1) / 3) + System.lineSeparator();
		  }
		  else{
			  tapertext += "triangle " + (((i + 1) / 3) + 1) + System.lineSeparator();
		  }
		  if (BigDecimal.valueOf(jtaperX.get(i)).compareTo(maxx) > 0){
			  maxx = BigDecimal.valueOf(jtaperX.get(i));
			  maxposx = i + 1;
		  }
		  if (BigDecimal.valueOf(jtaperX.get(i)).compareTo(minx) < 0){
			  minx = BigDecimal.valueOf(jtaperX.get(i));
			  minposx = i + 1;
		  }
		  if (BigDecimal.valueOf(jtaperY.get(i)).compareTo(maxy) > 0){
			  maxy = BigDecimal.valueOf(jtaperY.get(i));
			  maxposy = i + 1;
		  }
		  if (BigDecimal.valueOf(jtaperY.get(i)).compareTo(miny) < 0){
			  miny = BigDecimal.valueOf(jtaperY.get(i));
			  minposy = i + 1;
		  }
	  }
	  
	  if (verbose > 0) {
		  tapertext += "#The minimum x value is " + minx + ", which is in quad " + minposx + " {" + findQuadsCoordinates(minposx - 1) + "}, which is in triangle ";
		  if (minposx % 3 == 0){
			  tapertext += (minposx / 3) + " {" + findTrianglesCoordinates(minposx / 3) + "}" + System.lineSeparator();
		  }
		  else{
			  tapertext += ((minposx / 3) + 1) + " {" + findTrianglesCoordinates((minposx / 3) + 1) + "}" + System.lineSeparator();
		  }
		  tapertext += "#The maximum x value is " + maxx + ", which is in quad " + maxposx + " {" + findQuadsCoordinates(maxposx - 1) + "}, which is in triangle ";
		  if (maxposx % 3 == 0){
			  tapertext += (maxposx / 3) + " {" + findTrianglesCoordinates(maxposx / 3) + "}" + System.lineSeparator();
		  }
		  else{
			  tapertext += ((maxposx / 3) + 1) + " {" + findTrianglesCoordinates((maxposx / 3) + 1) + "}" + System.lineSeparator();
		  }
		  tapertext += "#The minimum y value is " + miny + ", which is in quad " + minposy + " {" + findQuadsCoordinates(minposy - 1) + "}, which is in triangle ";
		  if (minposy % 3 == 0){
			  tapertext += (minposy / 3) + " {" + findTrianglesCoordinates(minposy / 3) + "}" + System.lineSeparator();
		  }
		  else{
			  tapertext += ((minposy / 3) + 1) + " {" + findTrianglesCoordinates((minposy / 3) + 1) + "}" + System.lineSeparator();
		  }
		  tapertext += "#The maximum y value is " + maxy + ", which is in quad " + maxposy + " {" + findQuadsCoordinates(maxposy - 1) + "}, which is in triangle ";
		  if (maxposy % 3 == 0){
			  tapertext += (maxposy / 3) + " {" + findTrianglesCoordinates(maxposy / 3) + "}" + System.lineSeparator();
		  }
		  else{
			  tapertext += ((maxposy / 3) + 1) + " {" + findTrianglesCoordinates((maxposy / 3) + 1) + "}" + System.lineSeparator();
		  }
		  tapertext += "#The mean average for x is " + findMean(jtaperX) + System.lineSeparator();
		  tapertext += "#The mean average for y is " + findMean(jtaperY) + System.lineSeparator();
		  
	  }
	  
	  try (PrintWriter out = new PrintWriter(fileNameJT + ".txt")){
		  out.println(tapertext);
	  }
      catch (FileNotFoundException e) { //Checks for file not found exception
          System.err.println("Problem creating files");
          System.err.println(e.getMessage()); 
          throw e;
      }   
   } //End outputTapersXYJ

   /**
    * printJacobian - a method to call other methods in order to print the AR, Skew Angle, and Tapers for the Jacobian
    * @param i is the index of the quad
    * @param p1 is the original coordinates of p1
    * @param p2 is the original coordinates of p2
    * @param p3 is the original coordinates of p3
    * @param p4 is the original coordinates of p4
    */
   public static void printJacobian( int i, double[] p1, double[] p2, double[] p3, double[] p4) {
         
      double JAR = calculateJAR( i, p1, p2, p3, p4 );
      double JSkewAngle = calculateJSkewAngle( i, p1, p2, p3, p4 );
      double[] TapersXY = calculateJTapers( i, p1, p2, p3, p4 );

      System.out.println("Triangle " + ((i/3)+1) + ", Quad " + i + " || Aspect Ratio: " + JAR + " /// Skew Angle: " + JSkewAngle + " /// X Taper : " + TapersXY[0] + " /// Y Taper : " + TapersXY[1]);
   } //End printJacobian   

   // ----------------------------------------------------------------------------------------------
   // BEGINNING OF SETTING ORDER
   // ----------------------------------------------------------------------------------------------   

   /**
    * putInOrder - a method to coordinate correctly the appropriate points to p1, p2, p3, and p4
    * @param i is the index of the quad
    * @param p1 is the original coordinates of p1
    * @param p2 is the original coordinates of p2
    * @param p3 is the original coordinates of p3
    * @param p4 is the original coordinates of p4
    */
   public static ArrayList<double[]> putInOrder( int i, double[] p1, double[] p2, double[] p3, double[] p4 ) {
	  
	  double quadCenX = ( QuadsX.get(i)[0] + QuadsX.get(i)[1] + QuadsX.get(i)[2] + QuadsX.get(i)[3] ) / 4;
	  double quadCenY = ( QuadsY.get(i)[0] + QuadsY.get(i)[1] + QuadsY.get(i)[2] + QuadsY.get(i)[3] ) / 4;
	  
	  ArrayList<double[]> p1val = new ArrayList<double[]>();
	  ArrayList<double[]> p2val = new ArrayList<double[]>();
	  ArrayList<double[]> p3val = new ArrayList<double[]>();
	  ArrayList<double[]> p4val = new ArrayList<double[]>();
	  ArrayList<double[]> matchXVal = new ArrayList<double[]>();
	  ArrayList<double[]> matchYVal = new ArrayList<double[]>();
	  
	  boolean threein1 = false;
	  boolean threein2 = false;
	  boolean threein3 = false;
	  boolean threein4 = false;
	  
	  for ( int x = 0; x < 4; x++ ) {
		  if ( QuadsX.get(i)[x] < quadCenX && QuadsY.get(i)[x] != quadCenY) {
			  if ( QuadsY.get(i)[x] < quadCenY ) {
				  double[] p1valarr = {QuadsX.get(i)[x], QuadsY.get(i)[x]};
				  //System.out.println("P1 Arr: " + Arrays.toString(p1valarr));
				  p1val.add(p1valarr);
			  }
			  else{
				  double[] p4valarr = {QuadsX.get(i)[x], QuadsY.get(i)[x]};
				  //System.out.println("P4 Arr: " + Arrays.toString(p4valarr));
				  p4val.add(p4valarr);
			  }
		  }
		  else if ( QuadsX.get(i)[x] > quadCenX && QuadsY.get(i)[x] != quadCenY){
			  if ( QuadsY.get(i)[x] < quadCenY ) {
				  double[] p2valarr = {QuadsX.get(i)[x], QuadsY.get(i)[x]};
				  //System.out.println("P2 Arr: " + Arrays.toString(p2valarr));
				  p2val.add(p2valarr);
			  }
			  else{
				  double[] p3valarr = {QuadsX.get(i)[x], QuadsY.get(i)[x]};
				  //System.out.println("P3 Arr: " + Arrays.toString(p3valarr));
				  p3val.add(p3valarr);
			  }
		  }
		  else if ( QuadsX.get(i)[x] == quadCenX && QuadsY.get(i)[x] != quadCenY) {
			  double[] matchingXVals = {QuadsX.get(i)[x], QuadsY.get(i)[x]};
			  matchXVal.add(matchingXVals);
		  }
		  else if ( QuadsX.get(i)[x] != quadCenX && QuadsY.get(i)[x] == quadCenY) {
			  double[] matchingYVals = {QuadsX.get(i)[x], QuadsY.get(i)[x]};
			  matchYVal.add(matchingYVals);
		  }
	  }
	  
     /**
	  //FOR TESTING
	  for (int f = 0; f < p1val.size(); f++){
		  System.out.println("P1 " + Arrays.toString(p1val.get(f)));
	  }
	  for (int f = 0; f < p2val.size(); f++){
		  System.out.println("P2 " + Arrays.toString(p2val.get(f)));
	  }
	  for (int f = 0; f < p3val.size(); f++){
		  System.out.println("P3 " + Arrays.toString(p3val.get(f)));
	  }
	  for (int f = 0; f < p4val.size(); f++){
		  System.out.println("P4 " + Arrays.toString(p4val.get(f)));
	  }
     */
	  
	  //P1 VALS
	  if(p1val.size() > 1) {
		  //Three in III
		  if(p1val.size() == 3){
			  threein1 = true;
		  }
		  //Two in III
		  else if(p1val.size() == 2){
			  //One in I & IV
			  if(p4val.isEmpty() && !p2val.isEmpty()){
				  if(p1val.get(0)[1] > p1val.get(1)[1]){
					  p4 = p1val.get(0);
					  p1 = p1val.get(1);
				  }
				  else if(p1val.get(0)[1] < p1val.get(1)[1]){
					  p4 = p1val.get(1);
					  p1 = p1val.get(0);
				  }
				  else{
					  if(p1val.get(0)[0] > p1val.get(1)[0]){
						  p1 = p1val.get(0);
						  p4 = p1val.get(1);
					  }
					  else{
						  p1 = p1val.get(1);
						  p4 = p1val.get(0);
					  }
				  }
			  }
			  //Two in I & III
			  else if(p4val.isEmpty() && p2val.isEmpty()){
				  if(p1val.get(0)[0] > p1val.get(1)[0]){
					  p2 = p1val.get(0);
					  p1 = p1val.get(1);
				  }
				  else if(p1val.get(0)[0] < p1val.get(1)[0]){
					  p2 = p1val.get(1);
					  p1 = p1val.get(0);
				  }
				  else{
					  if(p1val.get(0)[1] > p1val.get(1)[1]){
						  p2 = p1val.get(0);
						  p1 = p1val.get(1);
					  }
					  else{
						  p2 = p1val.get(1);
						  p1 = p1val.get(0);
					  }
				  }
			  }
			  //One in I & II
			  else{
				  if(p1val.get(0)[0] > p1val.get(1)[0]){
					  p2 = p1val.get(0);
					  p1 = p1val.get(1);
				  }
				  else if(p1val.get(0)[0] < p1val.get(1)[0]){
					  p2 = p1val.get(1);
					  p1 = p1val.get(0);
				  }  
				  else{
					  if(p1val.get(0)[1] > p1val.get(1)[1]){
						  p2 = p1val.get(0);
						  p1 = p1val.get(1);
					  }
					  else{
						  p2 = p1val.get(1);
						  p1 = p1val.get(0);
					  }
				  }
			  }
		  }
	  }
	  //One in I
	  else if(p1val.size() == 1) {
		  if (matchYVal.size() == 2) {
			  if (p2val.isEmpty()) {
				  p2 = p1val.get(0);
			  }
		  }
		  else{
			  p1 = p1val.get(0);
		  }
	  }
	  
	  //P2 VALS
	  if(p2val.size() > 1) {
		  //Three in IV
		  if(p2val.size() == 3){
			  threein2 = true;
		  }
		  //Two in IV
		  else if(p2val.size() == 2){
			  //One in II & III
			  if(p3val.isEmpty() && !p1val.isEmpty()){
				  if(p2val.get(0)[1] > p2val.get(1)[1]){
					  p3 = p2val.get(0);
					  p2 = p2val.get(1);
				  }
				  else if(p2val.get(0)[1] < p2val.get(1)[1]){
					  p3 = p2val.get(1);
					  p2 = p2val.get(0);
				  }
				  else{
					  if(p2val.get(0)[0] > p2val.get(1)[0]){
						  p3 = p2val.get(0);
						  p2 = p2val.get(1);
					  }
					  else{
						  p3 = p2val.get(1);
						  p2 = p2val.get(0);
					  }
				  }
			  }
			  //Two in II & IV
			  else if(p3val.isEmpty() && p1val.isEmpty()){
				  if(p2val.get(0)[0] > p2val.get(1)[0]){
					  p2 = p2val.get(0);
					  p1 = p2val.get(1);
				  }
				  else if(p2val.get(0)[0] < p2val.get(1)[0]){
					  p2 = p2val.get(1);
					  p1 = p2val.get(0);
				  }	  
				  else{
					  if(p2val.get(0)[1] > p2val.get(1)[1]){
						  p2 = p2val.get(0);
						  p1 = p2val.get(1);
					  }
					  else{
						  p2 = p2val.get(1);
						  p1 = p2val.get(0);
					  }
				  }
			  }
			  //One in I & II
			  else{
				  if(p2val.get(0)[0] > p2val.get(1)[0]){
					  p2 = p2val.get(0);
					  p1 = p2val.get(1);
				  }
				  else if(p2val.get(0)[0] < p2val.get(1)[0]){
					  p2 = p2val.get(1);
					  p1 = p2val.get(0);
				  }
				  else{
					  if(p2val.get(0)[1] > p2val.get(1)[1]){
						  p2 = p2val.get(0);
						  p1 = p2val.get(1);
					  }
					  else{
						  p2 = p2val.get(1);
						  p1 = p2val.get(0);
					  }
				  }
			  }
		  }
	  }
	  //One in IV
	  else if(p2val.size() == 1) {
		  if (matchXVal.size() == 2) {
			  if (p3val.isEmpty()) {
				  p3 = p2val.get(0);
			  }
		  }
		  else{
			  p2 = p2val.get(0);
		  }
	  }
	  
	  //P3 VALS
	  if(p3val.size() > 1) {
		  //Three in I
		  if(p3val.size() == 3){
			  threein3 = true;
		  }
		  //Two in I
		  else if(p3val.size() == 2){
			  //One in II & III
			  if(p2val.isEmpty() && !p4val.isEmpty()){
				  if(p3val.get(0)[1] > p3val.get(1)[1]){
					  p3 = p3val.get(0);
					  p2 = p3val.get(1);
				  }
				  else if(p3val.get(0)[1] < p3val.get(1)[1]){
					  p3 = p3val.get(1);
					  p2 = p3val.get(0);
				  }
				  else{
					  if(p3val.get(0)[0] > p3val.get(1)[0]){
						  p2 = p3val.get(0);
						  p3 = p3val.get(1);
					  }
					  else{
						  p2 = p3val.get(0);
						  p3 = p3val.get(1);
					  }
				  }
			  }
			  //Two in I & III
			  else if(p2val.isEmpty() && p4val.isEmpty()){
				  
				  assert(p3val.get(0)[0] - p2[0] != 0);
				  double tempSlope1 = (p3val.get(0)[1] - p2[1]) / (p3val.get(0)[0] - p2[0]);
				  assert (tempSlope1 != 0);
				  double tempB1 = p2[1] - (tempSlope1 * p2[0]);	
				  double tempY1 = (tempSlope1 * p3val.get(1)[0]) + tempB1;
				 				  
				  //Above line
				  if(p3val.get(1)[1] > tempY1){
					  p3 = p3val.get(0);
					  p4 = p3val.get(1);
					  
					  assert(p4[0] - p1[0] != 0);
					  double tempSlope2 = (p4[1] - p1[1]) / (p4[0] - p1[0]);
					  assert (tempSlope2 != 0);
					  double tempB2 = p1[1] - (tempSlope2 * p1[0]);
					  double tempY2 = (tempSlope2 * p2[0]) + tempB2;
					  
					  if(p2[1] > tempY2){
						  double[] tempp2 = {p2[0], p2[1]};
						  double[] tempp3 = {p3[0], p3[1]};
						  double[] tempp4 = {p4[0], p4[1]};
						  p2 = tempp3;
						  p3 = tempp4;
						  p4 = tempp2;
					  }
				  }
				  
				  //Below line
				  else{
					  p3 = p3val.get(1);
					  p4 = p3val.get(0);

					  assert(p4[0] - p1[0] != 0);
					  double tempSlope2 = (p4[1] - p1[1]) / (p4[0] - p1[0]);
					  assert (tempSlope2 != 0);
					  double tempB2 = p1[1] - (tempSlope2 * p1[0]);
					  double tempY2 = (tempSlope2 * p2[0]) + tempB2;
					  
					  if(p2[1] > tempY2){
						  double[] tempp2 = {p2[0], p2[1]};
						  double[] tempp3 = {p3[0], p3[1]};
						  double[] tempp4 = {p4[0], p4[1]};
						  p2 = tempp3;
						  p3 = tempp4;
						  p4 = tempp2;
					  }
				  }
			  }
			  //One in III & IV 
			  else{  
				  if(p3val.get(0)[0] > p3val.get(1)[0]){
					  p3 = p3val.get(0);
					  p4 = p3val.get(1);
				  }
				  else if(p3val.get(0)[0] < p3val.get(1)[0]){
					  p3 = p3val.get(1);
					  p4 = p3val.get(0);
				  }
				  else{
					  if(p3val.get(0)[1] > p3val.get(0)[1]){
						  p4 = p3val.get(0);
						  p3 = p3val.get(1);
					  }
					  else{
						  p4 = p3val.get(1);
						  p3 = p3val.get(0);
					  }
				  }
			  }
		  }
	  }
	  //One in I
	  else if(p3val.size() == 1) {
		  if (matchYVal.size() == 2) {
			  if (p4val.isEmpty()) {
				  p4 = p3val.get(0);
			  }
		  }
		  else{
			  p3 = p3val.get(0);
		  }
	  }
	  
	  //P4 VALS
	  if(p4val.size() > 1) {
		  //Three in II
		  if(p4val.size() == 3){
			  threein4 = true;
		  }
		  //Two in II
		  else if(p4val.size() == 2){
			  //One in I & IV
			  if(p1val.isEmpty() && !p3val.isEmpty()){
				  if(p4val.get(0)[1] > p4val.get(1)[1]){
					  p4 = p4val.get(0);
					  p1 = p4val.get(1);
				  }
				  else if(p4val.get(0)[1] < p4val.get(1)[1]){
					  p4 = p4val.get(1);
					  p1 = p4val.get(0);
				  }
				  else{
					  if(p4val.get(0)[0] > p4val.get(1)[0]){
						  p4 = p4val.get(0);
						  p1 = p4val.get(1);
					  }
					  else{
						  p4 = p4val.get(1);
						  p1 = p4val.get(0);
					  }
				  }
			  }
			  //Two in II & IV
			  else if(p1val.isEmpty() && p3val.isEmpty()){			  

				  assert(p4val.get(0)[0] - p2[0] != 0);
				  double tempSlope1 = (p4val.get(0)[1] - p2[1]) / (p4val.get(0)[0] - p2[0]);
				  assert (tempSlope1 != 0);
				  double tempB1 = p2[1] - (tempSlope1 * p2[0]);	
				  double tempY1 = (tempSlope1 * p4val.get(1)[0]) + tempB1;
				 				  
				  //Above line
				  if(p4val.get(1)[1] > tempY1){
					  p3 = p4val.get(1);
					  p4 = p4val.get(0);
					  
					  assert(p4[0] - p1[0] != 0);
					  double tempSlope2 = (p4[1] - p1[1]) / (p4[0] - p1[0]);
					  assert (tempSlope2 != 0);
					  double tempB2 = p1[1] - (tempSlope2 * p1[0]);
					  double tempY2 = (tempSlope2 * p2[0]) + tempB2;
					  
					  if(p2[1] < tempY2){
						  double[] tempp1 = {p1[0], p1[1]};
						  double[] tempp3 = {p3[0], p3[1]};
						  double[] tempp4 = {p4[0], p4[1]};
						  p1 = tempp4;
						  p3 = tempp1;
						  p4 = tempp3;
					  }				  
				  }
				  
				  //Below line
				  else{
					  p3 = p4val.get(0);
					  p4 = p4val.get(1);
					  
					  assert(p4[0] - p1[0] != 0);
					  double tempSlope2 = (p4[1] - p1[1]) / (p4[0] - p1[0]);
					  assert (tempSlope2 != 0);
					  double tempB2 = p1[1] - (tempSlope2 * p1[0]);
					  double tempY2 = (tempSlope2 * p2[0]) + tempB2;
					  
					  if(p2[1] < tempY2){
						  double[] tempp1 = {p1[0], p1[1]};
						  double[] tempp3 = {p3[0], p3[1]};
						  double[] tempp4 = {p4[0], p4[1]};
						  p1 = tempp4;
						  p3 = tempp1;
						  p4 = tempp3;
					  }

				  }  
			  }
			  //One in III & IV
			  else{  
				  if(p4val.get(0)[0] > p4val.get(1)[0]){
					  p3 = p4val.get(0);
					  p4 = p4val.get(1);
				  }
				  else if(p4val.get(0)[0] < p4val.get(1)[0]){
					  p3 = p4val.get(1);
					  p4 = p4val.get(0);
				  }
				  else{
					  if(p4val.get(0)[1] > p4val.get(1)[1]){
						  p3 = p4val.get(0);
						  p4 = p4val.get(1);
					  }
					  else{
						  p3 = p4val.get(1);
						  p4 = p4val.get(0);
					  }
				  }
			  }
		  }
	  }
	  //One in II
	  else if(p4val.size() == 1) {
		  if (matchXVal.size() == 2) {
			  if (p1val.isEmpty()) {
				  p1 = p4val.get(0);
			  }
		  }
		  else{
			  p4 = p4val.get(0);
		  }
	  }

	  /*
	  USED FOR TESTING
	  double[] tempp1 = {p1[0], p2[1]};
	  double[] tempp2 = {p2[0], p2[1]};
	  double[] tempp3 = {p3[0], p3[1]};
	  double[] tempp4 = {p4[0], p4[1]};
	  p1 = tempp1;
	  p2 = tempp1;
	  p3 = tempp1;
	  p4 = tempp1;
	  */
	  
	  //P3 is already chosen
	  //Three points in III
	  if(threein1){
		  //Line from p3 to one of the other points in III
		  assert(p1val.get(0)[0] - p3[0] != 0);
		  double slope = (p1val.get(0)[1] - p3[1]) / (p1val.get(0)[0] - p3[0]);
		  assert (slope != 0);
		  double b = p3[1] - (slope * p3[0]);	
		  double tempy1 = (slope * p1val.get(1)[0]) + b;
		  double tempy2 = (slope * p1val.get(2)[0]) + b;

		  //Line is above other two points
		  if(p1val.get(1)[1] < tempy1 && p1val.get(2)[1] < tempy2){
			  
			  assert(p1val.get(1)[0] - p3[0] != 0);
			  double slopeBot2 = (p1val.get(1)[1] - p3[1]) / (p1val.get(1)[0] - p3[0]);
			  assert (slopeBot2 != 0);
			  double bBot2 = p3[1] - (slopeBot2 * p3[0]);	
			  double tempy1Bot2 = (slopeBot2 * p1val.get(2)[0]) + bBot2;
			 
			  if(p1val.get(2)[1] > tempy1Bot2){
				  p4 = p1val.get(0);
			      p1 = p1val.get(2);
				  p2 = p1val.get(1);
			  }
			  else if(p1val.get(2)[1] < tempy1Bot2){
				  p4 = p1val.get(0);
				  p1 = p1val.get(1);
				  p2 = p1val.get(2);
			  }
		  }
		  //Line is in-between other two points
		  else if(p1val.get(1)[1] > tempy1 && p1val.get(2)[1] < tempy2){
		  	  p4 = p1val.get(1);
			  p1 = p1val.get(0);
			  p2 = p1val.get(2);
		  }
		  //Line is in-between other two points
		  else if(p1val.get(1)[1] < tempy1 && p1val.get(2)[1] > tempy2){
		  	  p4 = p1val.get(2);
			  p1 = p1val.get(0);
			  p2 = p1val.get(1);
		  }
		  //Line is below other two points
		  else if(p1val.get(1)[1] > tempy1 && p1val.get(2)[1] > tempy2){
			  assert(p1val.get(1)[0] - p3[0] != 0);
			  double slopeTop2 = (p1val.get(1)[1] - p3[1]) / (p1val.get(1)[0] - p3[0]);
			  assert (slopeTop2 != 0);
			  double bTop2 = p3[1] - (slopeTop2 * p3[0]);	
			  double tempy1Top2 = (slopeTop2 * p1val.get(2)[0]) + bTop2;
			  
			  if(p1val.get(2)[1] > tempy1Top2){
				  p4 = p1val.get(2);
			      p1 = p1val.get(1);
				  p2 = p1val.get(0);
			  }
			  else if(p1val.get(2)[1] < tempy1Top2){
				  p4 = p1val.get(1);
				  p1 = p1val.get(2);
				  p2 = p1val.get(0);
			  }
		  }
		  else{
		  	  System.err.println("Point fell on the line in P3");
		  }
	  }
	  
	  //P4 is already chosen
	  //Three points in IV
	  if(threein2){
		  //Line from p4 to one of the other points in IV
		  assert(p2val.get(0)[0] - p4[0] != 0);
		  double slope = (p2val.get(0)[1] - p4[1]) / (p2val.get(0)[0] - p4[0]);
		  assert (slope != 0);
		  double b = p4[1] - (slope * p4[0]);	
		  double tempy1 = (slope * p2val.get(1)[0]) + b;
		  double tempy2 = (slope * p2val.get(2)[0]) + b;

		  //Line is above other two points
		  if(p2val.get(1)[1] < tempy1 && p2val.get(2)[1] < tempy2){
			  
			  assert(p2val.get(1)[0] - p4[0] != 0);
			  double slopeBot2 = (p2val.get(1)[1] - p4[1]) / (p2val.get(1)[0] - p4[0]);
			  assert (slopeBot2 != 0);
			  double bBot2 = p4[1] - (slopeBot2 * p4[0]);	
			  double tempy1Bot2 = (slopeBot2 * p2val.get(2)[0]) + bBot2;
			 
			  if(p2val.get(2)[1] > tempy1Bot2){
				  p1 = p2val.get(0);
			      p3 = p2val.get(2);
				  p2 = p2val.get(1);
			  }
			  else if(p2val.get(2)[1] < tempy1Bot2){
				  p1 = p2val.get(0);
				  p3 = p2val.get(1);
				  p2 = p2val.get(2);
			  }
		  }
		  //Line is in-between other two points
		  else if(p2val.get(1)[1] > tempy1 && p2val.get(2)[1] < tempy2){
		  	  p3 = p2val.get(1);
			  p2 = p2val.get(0);
			  p1 = p2val.get(2);
		  }
		  //Line is in-between other two points
		  else if(p2val.get(1)[1] < tempy1 && p2val.get(2)[1] > tempy2){
		  	  p3 = p2val.get(2);
			  p2 = p2val.get(0);
			  p1 = p2val.get(1);
		  }
		  //Line is below other two points
		  else if(p2val.get(1)[1] > tempy1 && p2val.get(2)[1] > tempy2){
			  assert(p2val.get(1)[0] - p4[0] != 0);
			  double slopeTop2 = (p2val.get(1)[1] - p4[1]) / (p2val.get(1)[0] - p4[0]);
			  assert (slopeTop2 != 0);
			  double bTop2 = p4[1] - (slopeTop2 * p4[0]);	
			  double tempy1Top2 = (slopeTop2 * p2val.get(2)[0]) + bTop2;
			  
			  if(p2val.get(2)[1] > tempy1Top2){
				  p3 = p2val.get(2);
			      p2 = p2val.get(1);
				  p1 = p2val.get(0);
			  }
			  else if(p2val.get(2)[1] < tempy1Top2){
				  p3 = p2val.get(1);
				  p2 = p2val.get(2);
				  p1 = p2val.get(0);
			  }
		  }
		  else{
		  	  System.err.println("Point fell on the line in P4");
		  }
	  }
	  
	  //P1 is already chosen
	  //Three points in I
	  if(threein3){
		  //Line from p1 to one of the other points in I
		  assert(p3val.get(0)[0] - p1[0] != 0);
		  double slope = (p3val.get(0)[1] - p1[1]) / (p3val.get(0)[0] - p1[0]);
		  assert (slope != 0);
		  double b = p1[1] - (slope * p1[0]);	
		  double tempy1 = (slope * p3val.get(1)[0]) + b;
		  double tempy2 = (slope * p3val.get(2)[0]) + b;

		  //Line is above other two points
		  if(p3val.get(1)[1] < tempy1 && p3val.get(2)[1] < tempy2){
			  
			  assert(p3val.get(1)[0] - p1[0] != 0);
			  double slopeBot2 = (p3val.get(1)[1] - p1[1]) / (p3val.get(1)[0] - p1[0]);
			  assert (slopeBot2 != 0);
			  double bBot2 = p1[1] - (slopeBot2 * p1[0]);	
			  double tempy1Bot2 = (slopeBot2 * p3val.get(2)[0]) + bBot2;
			 
			  if(p3val.get(2)[1] > tempy1Bot2){
				  p4 = p3val.get(0);
			      p3 = p3val.get(2);
				  p2 = p3val.get(1);
			  }
			  else if(p3val.get(2)[1] < tempy1Bot2){
				  p4 = p3val.get(0);
				  p3 = p3val.get(1);
				  p2 = p3val.get(2);
			  }
		  }
		  //Line is in-between other two points
		  else if(p3val.get(1)[1] > tempy1 && p3val.get(2)[1] < tempy2){
		  	  p4 = p3val.get(1);
			  p3 = p3val.get(0);
			  p2 = p3val.get(2);
		  }
		  //Line is in-between other two points
		  else if(p3val.get(1)[1] < tempy1 && p3val.get(2)[1] > tempy2){
		  	  p4 = p3val.get(2);
			  p3 = p3val.get(0);
			  p2 = p3val.get(1);
		  }
		  //Line is below other two points
		  else if(p3val.get(1)[1] > tempy1 && p3val.get(2)[1] > tempy2){
			  assert(p3val.get(1)[0] - p1[0] != 0);
			  double slopeTop2 = (p3val.get(1)[1] - p1[1]) / (p3val.get(1)[0] - p1[0]);
			  assert (slopeTop2 != 0);
			  double bTop2 = p1[1] - (slopeTop2 * p1[0]);	
			  double tempy1Top2 = (slopeTop2 * p3val.get(2)[0]) + bTop2;
			  
			  if(p3val.get(2)[1] > tempy1Top2){
				  p4 = p3val.get(2);
			      p3 = p3val.get(1);
				  p2 = p3val.get(0);
			  }
			  else if(p3val.get(2)[1] < tempy1Top2){
				  p4 = p3val.get(1);
				  p3 = p3val.get(2);
				  p2 = p3val.get(0);
			  }
		  }
		  else{
		  	  System.err.println("Point fell on the line in P1");
		  }
	  }
	  
	  //P2 is already chosen
	  //Three points in II
	  if(threein4){
		  //Line from p2 to one of the other points in II
		  assert(p4val.get(0)[0] - p2[0] != 0);
		  double slope = (p4val.get(0)[1] - p2[1]) / (p4val.get(0)[0] - p2[0]);
		  assert (slope != 0);
		  double b = p2[1] - (slope * p2[0]);	
		  double tempy1 = (slope * p4val.get(1)[0]) + b;
		  double tempy2 = (slope * p4val.get(2)[0]) + b;

		  //Line is above other two points
		  if(p4val.get(1)[1] < tempy1 && p4val.get(2)[1] < tempy2){
			  
			  assert(p4val.get(1)[0] - p2[0] != 0);
			  double slopeBot2 = (p4val.get(1)[1] - p2[1]) / (p4val.get(1)[0] - p2[0]);
			  assert (slopeBot2 != 0);
			  double bBot2 = p2[1] - (slopeBot2 * p2[0]);	
			  double tempy1Bot2 = (slopeBot2 * p4val.get(2)[0]) + bBot2;
			 
			  if(p4val.get(2)[1] > tempy1Bot2){
				  p3 = p4val.get(0);
			      p4 = p4val.get(2);
				  p1 = p4val.get(1);
			  }
			  else if(p4val.get(2)[1] < tempy1Bot2){
				  p3 = p4val.get(0);
				  p4 = p3val.get(1);
				  p1 = p3val.get(2);
			  }
		  }
		  //Line is in-between other two points
		  else if(p4val.get(1)[1] > tempy1 && p4val.get(2)[1] < tempy2){
		  	  p3 = p4val.get(1);
			  p4 = p4val.get(0);
			  p1 = p4val.get(2);
		  }
		  //Line is in-between other two points
		  else if(p4val.get(1)[1] < tempy1 && p4val.get(2)[1] > tempy2){
		  	  p3 = p4val.get(2);
			  p4 = p4val.get(0);
			  p1 = p4val.get(1);
		  }
		  //Line is below other two points
		  else if(p4val.get(1)[1] > tempy1 && p4val.get(2)[1] > tempy2){
			  assert(p4val.get(1)[0] - p2[0] != 0);
			  double slopeTop2 = (p4val.get(1)[1] - p2[1]) / (p4val.get(1)[0] - p2[0]);
			  assert (slopeTop2 != 0);
			  double bTop2 = p2[1] - (slopeTop2 * p2[0]);	
			  double tempy1Top2 = (slopeTop2 * p4val.get(2)[0]) + bTop2;
			  
			  if(p4val.get(2)[1] > tempy1Top2){
				  p3 = p4val.get(2);
			      p4 = p4val.get(1);
				  p1 = p4val.get(0);
			  }
			  else if(p4val.get(2)[1] < tempy1Top2){
				  p3 = p4val.get(1);
				  p4 = p4val.get(2);
				  p1 = p4val.get(0);
			  }
		  }
		  else{
		  	  System.err.println("Point fell on the line in P2");
		  }
	  }
	  
	//For values that are the same X as the centroid
	  if(!matchXVal.isEmpty()){
		  for(int x = 0; x < matchXVal.size(); x++){
			  if(matchXVal.get(x)[1] < quadCenY){
				  if(matchXVal.size() == 2){
					  p2 = matchXVal.get(x);
				  }
				  else{
					  if(p1val.isEmpty()){
						  p1 = matchXVal.get(x);
					  }
					  else if(p2val.isEmpty()){
						  p2 = matchXVal.get(x);
					  }
				  }
			  }
			  else if(matchXVal.get(x)[1] > quadCenY){
				  if(matchXVal.size() == 2){
					  p4 = matchXVal.get(x);
				  }
				  else{
					  if(p4val.isEmpty()){
						  p4 = matchXVal.get(x);
					  }
					  else if(p3val.isEmpty()){
						  p3 = matchXVal.get(x);
					  }
				  }
			  }
		  }
	  }
	  
	  //For values that are the same Y as the centroid
	  if(!matchYVal.isEmpty()){
		  for(int x = 0; x < matchYVal.size(); x++){
			  if(matchYVal.get(x)[0] < quadCenX){
				  if (matchYVal.size() == 2){
					  p1 = matchYVal.get(x);
				  }
				  else{
					  if (p1val.isEmpty()){
						  p1 = matchYVal.get(x);
					  }	
					  else if(p4val.isEmpty()){
						  p4 = matchYVal.get(x);
					  }
				  }
			  }
			  else if(matchYVal.get(x)[0] > quadCenX){
				  if(matchYVal.size() == 2){
					  p3 = matchYVal.get(x);
				  }
				  else{
					  if(p3val.isEmpty()){
						  p3 = matchYVal.get(x);
					  }
					  else if(p2val.isEmpty()){
						  p2 = matchYVal.get(x);
					  }
				  }
			  }
		  }
	  }


	  ArrayList<double[]> finalVals = new ArrayList<double[]>();
	  finalVals.add(p1);
	  finalVals.add(p2);
	  finalVals.add(p3);
	  finalVals.add(p4);

      //Places the Quads in order in ArrayLists for printing
      double[] newValsX = {p1[0], p2[0], p3[0], p4[0]};
      QuadsX.set(i, newValsX);

      double[] newValsY = {p1[1], p2[1], p3[1], p4[1]};
      QuadsY.set(i, newValsY);
   
      return finalVals;
   } //End putInOrder      

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
   
   /**
    * setCalculations - a method to run the calculations for the various methods
    * @param i is the index of the quad
    */
   public static void setCalculations(int i) {
	   
	   double[] p1 = {QuadsX.get(i)[0], QuadsY.get(i)[0]};
	   double[] p2 = {QuadsX.get(i)[1], QuadsY.get(i)[1]};
	   double[] p3 = {QuadsX.get(i)[2], QuadsY.get(i)[2]};
	   double[] p4 = {QuadsX.get(i)[3], QuadsY.get(i)[3]};
	  
	   /**
	   System.out.println("Center: " + ((p1[0] + p2[0] + p3[0] + p4[0]) / 4) + " " + ((p1[1] + p2[1] + p3[1] + p4[1]) / 4));
	   System.out.println("Before order");
	   System.out.println("Quad " + (i + 1));
	   System.out.println(p1[0] + " " + p1[1]);
	   System.out.println(p2[0] + " " + p2[1]);
	   System.out.println(p3[0] + " " + p3[1]);
	   System.out.println(p4[0] + " " + p4[1]);	    
      */
      
      ArrayList<double[]> newCoords = putInOrder(i, p1, p2, p3, p4); 
      p1 = newCoords.get(0);
      p2 = newCoords.get(1);
      p3 = newCoords.get(2);
      p4 = newCoords.get(3);
      
      /**
      System.out.println("After order");
      System.out.println("Quad " + (i + 1));
	   System.out.println(p1[0] + " " + p1[1]);
	   System.out.println(p2[0] + " " + p2[1]);
	   System.out.println(p3[0] + " " + p3[1]);
	   System.out.println(p4[0] + " " + p4[1]);
	   System.out.println();   
      */
      
      calculateJAR( i, p1, p2, p3, p4 );
	   calculateJSkewAngle( i, p1, p2, p3, p4 );
      calculateJTapers( i, p1, p2, p3, p4 );

   } //End of setCalculations
   
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
   // HELPER METHODS
   // ----------------------------------------------------------------------------------------------     

   /**
    * findTrianglesCoordinates - a method to find the X and Y coordinates for each of the three points in a certain triangle
    * @param triangle_number is the number of the triangle being observed
    * @return a string that displays the X and Y coordinates for each of the three points in a certain triangle
    */
   public static String findTrianglesCoordinates(int triangle_number){
	   return "(" + x.get(triangle_number)[0] + ", " + y.get(triangle_number)[0] + ")" +
			  "(" + x.get(triangle_number)[1] + ", " + y.get(triangle_number)[1] + ")" +
			  "(" + x.get(triangle_number)[2] + ", " + y.get(triangle_number)[2] + ")";
   } //End findTrianglesCoordinates
   
   /**
    * findQuadsCoordinates - a method to find the X and Y coordinates for each of the four points in a certain quad
    * @param quad_number is the number of the quad being observed
    * @return a string that displays the X and Y coordinates for each of the four points in a certain quad
    */
   public static String findQuadsCoordinates(int quad_number) {
	   return "(" + QuadsX.get(quad_number)[0] + ", " + QuadsY.get(quad_number)[0] + ")" +
			  "(" + QuadsX.get(quad_number)[1] + ", " + QuadsY.get(quad_number)[1] + ")" +
			  "(" + QuadsX.get(quad_number)[2] + ", " + QuadsY.get(quad_number)[2] + ")" +
			  "(" + QuadsX.get(quad_number)[3] + ", " + QuadsY.get(quad_number)[3] + ")";
   } //End findQuadsCoordinates
   
   /**
    * findMean - a method to find the mean of a certain set of values
    * @param values is the ArrayList that contains the values to be averaged
    * @return the mean (average) of the list of values
    */
   public static double findMean(ArrayList<Double> values){
	   double mean = 0;
	   for ( int i = 0; i < values.size(); i++ ) {
		   mean += values.get(i);
	   }
	   return mean / values.size();
   } //End findMean
   
   /**
    * printUsage - a method to print the command prompt commands for this program (MUST TYPE \?)
    */
   public static void printUsage( ) {
      System.out.println("----------------------------------------------------------------------------------------------"); 
      System.out.println("USAGE");
      System.out.println("----------------------------------------------------------------------------------------------"); 
      System.out.println("-? = displays the usage menu");
      System.out.println("-printt = prints the points of the triangles");
      System.out.println("-printq = prints the points of the quads");
      System.out.println("-jaspectratio = outputs the aspect ratio values for the Jacobian into a .txt file");
      System.out.println("-jskew = outputs the skew values for the Jacobian into a .txt file");
      System.out.println("-jtaper = outputs the taper values for the Jacobian into a .txt file");
      System.out.println("-r = outputs the R Code for displaying the quads and triangles into a .txt file");
      System.out.println("-j = displays the jacobian calculations for AR, Skew Angles, and Tapers in X and Y directions");
   } //End printUsage   
   
   // ----------------------------------------------------------------------------------------------
   // MAIN METHOD
   // ----------------------------------------------------------------------------------------------      
   
   /**
   * main - a method that sends the A.1.node and A.1.ele files to the program, calling previous methods
   * @param args is an array of strings that consists of both A.1.node and A.1.ele
   */      
   public static void main( String[] args ) throws IOException {

      //Command Methods
      
      boolean printUsageNeeded = false;
      
	   boolean printTrianglesNeeded = false;
      boolean printQuadsNeeded = false;
      boolean outputRCodeNeeded = false;
      
      boolean outputARJNeeded = false;
      boolean outputSkewJNeeded = false;
      boolean outputTaperJNeeded = false;

      String fileNameR = null;
      String fileNameJAR = null;
      String fileNameJS = null;
      String fileNameJT = null;
      
      boolean printJacobianNeeded = false;
      
      int verbose = 0;
        
      for ( int i = 0; i < args.length; i++ ) {
      
         if ( args[i].equals("-v") ) {
    		   verbose += 1;
    	   }
         
         if ( args[i].equals("-?") ) {
             printUsageNeeded = true;
         }     
         
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
         
         if ( args[i].equals("-jaspectratio") ) {
        	    outputARJNeeded = true;
             fileNameJAR = args[i + 1];
             if (i == args.length - 3 || fileNameJAR.substring(0,1).equals("-") || fileNameJAR.equals(null)){
            	 fileNameJAR = "jaspectratio";
             }    	 
         }         
         if ( args[i].equals("-jskew") ) {
             outputSkewJNeeded = true;
             fileNameJS = args[i + 1];
             if (i == args.length - 3 || fileNameJS.substring(0,1).equals("-") || fileNameJS.equals(null)){
             	fileNameJS = "jskew";
             }        	 
         }
         if ( args[i].equals("-jtaper") ) {
        	 outputTaperJNeeded = true;
        	
             fileNameJT = args[i + 1];
             if (i == args.length - 3 || fileNameJT.substring(0,1).equals("-") || fileNameJT.equals(null)){
             	fileNameJT = "jtaper";
             }      	 
         }         
         if ( args[i].equals("-j") ) {
            printJacobianNeeded = true;    
         }                  
         
      }
	  
      triangle = findTriangles(args); //Holds the triangles
      vertex = findVertices(args); //Holds the vertices
      setQuads(); //Sets the values for the quads            
      for(int i = 0; i < QuadsX.size(); i++){
    	   setCalculations(i); //Sets the values for the calculations
      }

      if ( printUsageNeeded ) {
          printUsage();
          System.exit(0);
      }

      if ( printTrianglesNeeded ) {
         printTriVertex(args);
      }   
      if ( printQuadsNeeded ) {
         printQuads();
      }
      if ( outputRCodeNeeded ) {
         outputRCode(fileNameR);
      }
      
      if ( outputARJNeeded ) {
    	 outputARJ(fileNameJAR, verbose);
      }   
      if ( outputSkewJNeeded ) {
    	 outputSkewJ(fileNameJS, verbose);
      }
      if ( outputTaperJNeeded ) {
    	 outputTapersXYJ(fileNameJT, verbose);
      }   
      
      if ( printJacobianNeeded ) {
         System.out.println("----------------------------------------------------------------------------------------------");      
         System.out.println("JACOBIAN METHODS");
         System.out.println("----------------------------------------------------------------------------------------------");      
         for ( int j = 0; j < QuadsX.size(); j++ ) {
       	  	double[] p1 = {QuadsX.get(j)[0], QuadsY.get(j)[0]};
       	  	double[] p2 = {QuadsX.get(j)[1], QuadsY.get(j)[1]};
       	  	double[] p3 = {QuadsX.get(j)[2], QuadsY.get(j)[2]};
       	  	double[] p4 = {QuadsX.get(j)[3], QuadsY.get(j)[3]};
            printJacobian(j, p1, p2, p3, p4);           
         }
      }      
      
   }
}
