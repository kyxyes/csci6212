import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Proj1 {
   private static boolean cont = true;
   public static long STARTTIME;
   public static long ENDTIME;
   public static void main(String args[]) throws IOException{
	   
	   while(cont){
	   System.out.println("What would you like to search on? (F,L,P,C)");
	   BufferedReader breader = new BufferedReader(new InputStreamReader(System.in));
	   String str = breader.readLine();
	   SearchCSV searchCSV = new SearchCSV();
	   try {
		searchCSV.doLoadingCSV();
		searchCSV.doSortlist();
		STARTTIME = System.currentTimeMillis();
		searchCSV.getCol(str);
		ENDTIME = System.currentTimeMillis();
		System.out.println(ENDTIME-STARTTIME+"ms");
		System.out.println("Another Search? (Y,N)");
		BufferedReader breader2 = new BufferedReader(new InputStreamReader(System.in));
		String strYN = breader2.readLine();
		if(strYN.equals("N")){
		   	cont = false;
		   	System.out.println("exit system");
		}		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   }
}
