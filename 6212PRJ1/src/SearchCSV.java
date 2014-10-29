
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;


public class SearchCSV {
   private List lists = new ArrayList();
   private List colList = new ArrayList();
   private List colListFN = new ArrayList();
   private List colListLN = new ArrayList();
   private List colListPN = new ArrayList();
   private List colListCN = new ArrayList();
   private int colNum = 0;
   private int rowNum = 0;
   public void doLoadingCSV() throws IOException{
	   File csv = new File("LargeAddressBook.csv");
	   try {
		BufferedReader br = new BufferedReader(new FileReader(csv));
		String line ="";
		int i =5;
		br.readLine();  //read first line then we can skip the first line
		while((line=br.readLine())!=null){
			lists.add(line);
			colListFN.add(line);
			colListLN.add(line);
			colListPN.add(line);
			colListCN.add(line);
			rowNum++;
//			StringTokenizer st = new StringTokenizer(line,",");
//			while(st.hasMoreTokens()){
//			System.out.print(st.nextToken()+"\t");
//			if(i++%4==0){ 
//				System.out.print("\n");
//			}
//			}
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   public void doSortlist(){
	   ComparatorFN comparatorfn = new ComparatorFN();
	   Collections.sort(colListFN, comparatorfn);
	   ComparatorLN comparatorln = new ComparatorLN();
	   Collections.sort(colListLN,comparatorln);
	   ComparatorPN comparatorpn = new ComparatorPN();
	   Collections.sort(colListPN,comparatorpn);
	   ComparatorCN comparatorcn = new ComparatorCN();
	   Collections.sort(colListCN,comparatorcn);
//	   for(Iterator it = colListFN.iterator();it.hasNext();){
//	   System.out.println(it.next());
//   }
   }
   

   

   
   public int getColNum(){
	   if(lists.get(0).toString().contains(",")){
		   colNum = lists.get(0).toString().split(",").length;
		   return colNum;
	   }else if(lists.get(0).toString().trim().length()!=0){
		   colNum = 1;
		   return colNum;
	   } else return 0;
   }
   
   public void getCol(String st) throws IOException{
	  
	   if (st.equals("F")){
		   System.out.println("Enter the partial First Name:");
		   BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
		   String fname = strin.readLine();
		   System.out.println("You enter "+ fname);
		   this.doBinarySearch(fname, colListFN ,0);

	   }
	   if(st.equals("L")){
		   System.out.println("Enter the partial Last Name:");
		   BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
		   String lname = strin.readLine();
		   System.out.println("You enter "+ lname);
		   this.doBinarySearch(lname,colListLN ,1);
	   }
	   if(st.equals("P")){
		   System.out.println("Enter the partial Phone Number:");
		   BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
		   String phoneNum = strin.readLine();
		   System.out.println("You enter "+ phoneNum);
		   this.doPhoneBinarySearch(phoneNum,colListPN);

	   }
	   if(st.equals("C")){
		   System.out.println("Enter the partial Company Name:");
		   BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
		   String cname = strin.readLine();
		   System.out.println("You enter "+ cname);
		   this.doBinarySearch(cname,colListCN,3);
	   }

   }
   
   public void doRegxSearch(String str){
	   for(int i= 1;i<rowNum; i++){
	   if(Pattern.matches(str+"[a-zA-Z[\\s]]*",colList.get(i).toString())){
		   System.out.println(colList.get(i).toString());
	   }
	   }
   }
   
   public void doPhoneRegxSearch(String phoStr){
	   for(int i= 1; i<rowNum; i++){
		   if(Pattern.matches("\\("+phoStr+"\\d{1,2}\\)[\\s]\\d{1,3}-?\\d{1,4}",colList.get(i).toString())){
			   System.out.println(colList.get(i).toString());
		   }
	   }
   }
   public void doBinarySearch(String str, List list ,int k){
	   int src = 0;
	   int dst = list.size()-1;
	   int p = doBinarySearch(str,src,dst,list,k); 
	   //search begin with str around p
	   int i=p,j=p+1;
	   for(; i>0 ;i--){
		   if(str.compareToIgnoreCase(list.get(i).toString().split(",")[k].substring(0, str.length()))!=0){
 		       break;
		   }
		   
	   }
	   i++;
	   for(;j<list.size();j++){
		   if(str.compareToIgnoreCase(list.get(j).toString().split(",")[k].substring(0, str.length()))!=0){
			   break;
		   }
	   }
//	   for(int m=i;m<j;m++){
//		   System.out.println(list.get(m).toString());
//	   }
	   printout(list,i,j);

   }
   
   
   
   public int doBinarySearch(String str, int src, int dst, List list, int k){   //logn recursive
	   int mid = (src+dst)/2;
	   if(src<dst){
		   if(str.compareToIgnoreCase(list.get(mid).toString().split(",")[k].substring(0, str.length()))<0){
			  return  doBinarySearch(str,src,mid-1,list,k);
		   }
		   else if(str.compareToIgnoreCase(list.get(mid).toString().split(",")[k].substring(0, str.length()))>0){
			  return doBinarySearch(str,mid+1,dst,list,k);
		   }else{
			   return mid;
		   }
	   }else if(src == dst){
		   if(str.compareToIgnoreCase(list.get(src).toString().split(",")[k].substring(0, str.length()))==0){
			   return src;
		   }
	   }
	   return -1;
   }
   
   public void doPhoneBinarySearch(String str, List list){

	   int src =0;
	   int dst =list.size()-1;
	   int p;
	   if(str.length()<0){
		   System.out.println("the phone number you enter is not corrected");
	   }
	   p= doPBS(str,src,dst,list);
	   if(p==-1) {System.out.println("not find the number");return;}
	   //System.out.println(list.get(p).toString());
	   int i =p,j=p+1;
	   for(;i>0;i--){
		   if(str.compareTo(list.get(i).toString().split(",")[2].replaceAll("[\\s()-]", "").substring(0, str.length()))!=0){
			   break;
			   //System.out.println(list.get(i).toString());
		   }
	   }
	   i++;
	   for(;j<list.size();j++){
		   if(str.compareTo(list.get(j).toString().split(",")[2].replaceAll("[\\s()-]", "").substring(0, str.length()))!=0){
			   break;
			   //System.out.println(list.get(j).toString());
		   }
	   }
	   printout(list,i,j);
   }
   
   
   public int doPBS(String str, int src, int dst, List list){
	   int mid = (src+dst)/2;
	   String midStr = list.get(mid).toString().split(",")[2].replaceAll("[\\s()-]", "").substring(0, str.length());  //  \\s means backspace
	  // System.out.println(midStr);
	   if(src<dst){
		   if(str.compareToIgnoreCase(midStr)<0){
			   return doPBS(str,src,mid-1,list);
		   }
		   else if(str.compareToIgnoreCase(midStr)>0){
			   return doPBS(str,mid+1,dst,list);
		   }else{
			  return mid;
		   }
	   }else if(src == dst){
		   if(str.compareToIgnoreCase(list.get(src).toString().split(",")[2].replaceAll("[\\s()-]","").substring(0, str.length()))==0){
			   return src;
		   }
		   
	   }
	   return -1;
   }
   
   public static void printout(List list ,int i, int j){
	 for(int a=i;a<j;a++){
		 System.out.println(list.get(a).toString());
		
	 }
	 System.out.println(j-i+" total matches");
   }
   
   public static class ComparatorFN implements Comparator{
	   public int compare(Object o1, Object o2){
		  String str0 = (String)o1;
		  String str1 = (String)o2;
		  int n = str0.split(",")[0].compareTo(str1.split(",")[0]);
		  return n;
	   }
   }
   
   public static class ComparatorLN implements Comparator{
	   public int compare(Object o1, Object o2){
		   String str0 = (String)o1;
		   String str1 = (String)o2;
		   int n = str0.split(",")[1].compareTo(str1.split(",")[1]);
		   return n;
	   }
   }
   
   
   public static class ComparatorPN implements Comparator{
	   public int compare(Object o1, Object o2){
		   String str0 =(String)o1;
		   String str1 = (String)o2;
		   int n = str0.split(",")[2].compareTo(str1.split(",")[2]);
		   return n;
	   }
   }
   
   
   public static class ComparatorCN implements Comparator{
	   public int compare(Object o1, Object o2){
		   String str0 =(String)o1;
		   String str1 = (String)o2;
		   int n = str0.split(",")[3].compareTo(str1.split(",")[3]);
		   return n;
	   }
   }
   
   
   
   
   
   
   
   
   
   
   
}
