package scrabble;
/**
 * A program that reads in the file dictionary.txt. Its output will be the longest words possible
 * from a list of chosen letters.
 * There is also a function that will allow the user to extend onto an existing word on the board.
 * @author alan1g
 * 
 */
import java.util.*;
public class Scrabble 
{
	public static void main(String[] args)
	{
		 FileIO reader = new FileIO();
		 String[] contents = reader.load("C://Users/Alan/workspace/Scrabble/src/scrabble/dictionary");
		 Scanner scan = new Scanner(System.in);
		 System.out.println("Press 1 if you want make a word out of letters");
		 System.out.println("or Press 2 if you want build a word onto an existing word");
		 int selection = scan.nextInt();
		 
		 Comparator<String> com = new MyComparator();//comparator for Priority Queue class

		 switch (selection)
		 {
		 case 1:	System.out.println("Enter your letters");
		 			scan.nextLine();
		 			String letters = scan.nextLine();
		 
		 			PriorityQueue<String> queue = new PriorityQueue<String>(letters.length(), com);//to order words by highest length

		 
					 for(int i=0; i<contents.length; i++)//loop through dictionary.txt
					 {
						 String temp = contents[i];
						 temp=temp.replaceAll("\\s","");//remove all whitespace
						 if(temp.length()<=letters.length())//only words with greater length than input
						 {
							 boolean match=compareWord(temp,letters);//see if word is contained in letters
							 if(match)
							 {
								 queue.add(temp); 
							 }
						 }
					 }
					 System.out.println("Top 10 suggestions:");
					 int counter =0;
					 while(!queue.isEmpty() && counter < 10)
					 {
						 System.out.println(queue.remove());
						 counter++;
					 }
					 break;
					 
		 case 2:	System.out.println("Enter the word to build on");
					scan.nextLine();
		 			String word = scan.nextLine();
		 			System.out.println("Enter your remaining letters");
		 			String letters2 = scan.nextLine();
			 
		 			PriorityQueue<String> queue2 = new PriorityQueue<String>(letters2.length()+word.length(), com);//to order words by highest length

		 			for(int i=0; i<contents.length; i++)//loop through dictionary.txt
					{
						 String temp = contents[i];
						 temp=temp.replaceAll("\\s","");//remove all whitespace
						 if(temp.contains(word))//only word which contain the word
						 {
							 String str[] = temp.split(word);//remove word from temp
							 String wordRemain="";//String to hold remaining letters
							 for(int j=0;j<str.length;j++)
							 {
								 wordRemain+=str[j];
							 }
							 boolean match=compareWord(wordRemain,letters2);//see if word is contained in letters
							 if(match)
							 {
								 queue2.add(temp); 
							 }	
						 }
					}
		 			if(queue2.isEmpty())
		 			{
		 				System.out.println("No word can be made");
		 			}
		 			else
		 			{
			 			while(!queue2.isEmpty())
			 			{
			 				System.out.println(queue2.remove());
			 			}
		 			}
		 			break;
		 				
		 default:	System.out.println("Invalid selection");
		 			break;
		 }
		 scan.close();
	}//end of main
	/**
	 * A method which checks to see if a word is contained in a list of letters.
	 * @param dictionary
	 * @param letters
	 * @return boolean match
	 */
	public static boolean compareWord(String dictionary, String letters)
	{
		boolean match=false;
		
		char lettersArray[] = new char[letters.length()];
		for(int i=0;i<letters.length();i++)//convert given letters into an array
		{
			lettersArray[i]=letters.charAt(i);
		}
		
		for(int i=0;i<dictionary.length();i++)//start with first letter of dictionary word
		{
			char charDict = dictionary.charAt(i);
			match=false;//reassign match to be false
			for(int j=0;j<lettersArray.length;j++)//check to see if charDict is in letters array
			{
				if(charDict==lettersArray[j])
				{
					lettersArray[j]='*';//in case of duplicate
					match=true;
					break;//break inner loop
				}
			}
			if(match==false)//if after one pass of loop match is false, word cannot be made
			{
				break;//break outer loop
			}
		}
		return match;//return to main
	}
}//end of main
/**
 * A class to change the order of the Java Priority Queue Class to that of the length of string
 * @author Alan
 *
 */
class MyComparator implements Comparator<String> 
{
	   public int compare(String x, String y) 
	   {
	      if (x.length() > y.length()) 
	      {
	         return -1;
	      }
	      if (x.length() < y.length()) 
	      {
	         return 1;
	      }
	      return 0;
	   }
}//end of class MyComparator

