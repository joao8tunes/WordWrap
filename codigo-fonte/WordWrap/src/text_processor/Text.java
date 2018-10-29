package text_processor;

import java.util.ArrayList;

public class Text
{
	
	public static int INF = Integer.MAX_VALUE;
	
	public static String dpWrap(String text, int width)
	{
		String[] words = text.replaceAll("\n", " ").split(" ");
		ArrayList<String> processedWords = new ArrayList<String>();
		
		for (int i = 0; i < words.length; ++i) {
			words[i] = words[i].trim();
			if (!words[i].isEmpty()) processedWords.add(words[i]);    //Ignoring the extra whitespaces.
		}
		words = new String[processedWords.size()];
		
		int n = words.length, space = 0;
		int[] lengths = new int[words.length];
		int[][] extras = new int[words.length][words.length];
		int[] c = new int[words.length], substringRanges = new int[words.length];
		
		for (int i = 0; i < words.length; ++i) {
			words[i] = processedWords.get(i);
			lengths[i] = words[i].length();
		}
		
		for (int i = 0; i < n; ++i) {
			int curlen = lengths[i];

			space = width-curlen;
			extras[i][i] = space < 0 ? INF : space*space;
			
			for (int j = i+1; j < n; ++j) {
				curlen += lengths[j];

				if (extras[i][j-1] == INF || curlen >= width) extras[i][j] = INF;
				else {
					space = width-curlen-1;
					extras[i][j] = space*space;
				}
			}
		}
			
		c[n-1] = extras[n-1][n-1];
		substringRanges[n-1] = n;

		for (int i = n-2; i >= 0; --i) {
			c[i] = extras[i][n-1];
			substringRanges[i] = n;

			for (int j = n-1; j > i; --j) {
				if (extras[i][j-1] != INF && c[j]+extras[i][j-1] < c[i]) {
					c[i] = c[j]+extras[i][j-1];
					substringRanges[i] = j;
				}
			}
		}
		
		return getWrappedString(words, substringRanges);
	}
	
	public static int getExtra(int i, int j, int[] lengths, int width)
	{
		int value = extras(i, j, lengths, width);
		
		return (value >= 0 ? (int) Math.pow(value, 2.0) : INF);
	}
	
	public static int extras(int i, int j, int[] lengths, int width)
	{
		int value = 0;
		
		if (i == j) value = width-lengths[j];
		else if (i < j) {
			value = extras(i+1, j, lengths, width)-lengths[i]-1;
//			value = extras(i, j-1, lengths, width)-lengths[j]-1;
			System.out.println(i+","+j+" = " + value);
		}
		
		return value;
	}
	
	public static String getWrappedString(String[] words, int[] substringRanges)
	{
		String wrappedString = "";
		int limit, index = 0;
		
		while (index < substringRanges.length) {
			limit = substringRanges[index];
			
			for (int j = index; j < limit; ++j) {
				wrappedString += words[j] + " ";
			}
			
			if (limit < substringRanges.length) wrappedString += "\n";
			index = limit;
		}
		
		return wrappedString;
	}
	
	public static String greedyWrap(String text, int width)
	{
		String[] words = text.replaceAll("\n", " ").split(" ");
		ArrayList<String> processedWords = new ArrayList<String>();
		
		for (int i = 0; i < words.length; ++i) {
			words[i] = words[i].trim();
			if (!words[i].isEmpty()) processedWords.add(words[i]);    //Ignoring the extra whitespaces.
		}
		words = new String[processedWords.size()];
		
		int n = words.length, count;
		int[] lengths = new int[words.length];
		
		for (int i = 0; i < words.length; ++i) {
			words[i] = processedWords.get(i);
			lengths[i] = words[i].length();
		}
		
		String wrappedText = words[0];
		count = lengths[0];
		
		for (int i = 1; i < n; ++i) {
			if (count+lengths[i]+1 <= width) {
				count += lengths[i]+1;
				wrappedText += " " + words[i];
			}
			else {
				wrappedText += "\n";    //End of the current line.
				count = lengths[i];
				wrappedText += words[i];
			}
		}
			
		return wrappedText;
	}
	
//	public static void main(String args[])
//	{
//		Text.dpWrap("aaa bb cc ddddd", 6);
//	}
	
}