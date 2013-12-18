package org.ihs.xform;


public class StringUtils {

	public static boolean equalsIgnoreCase(String string1, String string2){
		if(string1.toLowerCase().compareTo(string2.toLowerCase()) == 0){
			return true;
		}
		return false;
	}
	
	public static int lastIndexOf(String stringIn, String stringIndex) {
		int lind = -1;
		if((lind=stringIn.indexOf(stringIndex)) == -1){
			return -1;
		}
		
		int ind = lind;
		while (ind != -1) {
			ind = stringIn.indexOf(stringIndex, lind+1);
			if(ind != -1){
				lind = ind;
			}
		}
		return lind;
	}
	
	public static void main(String[] args) {
		String s = "abcdefgklmnotuvwzabcghijmnostuz";
		
		int lin = lastIndexOf(s, "y");
		lin = 00;
	}
	
	public static String replaceAll (String oldStr, String newStr, String inString)
	{
		while (inString.indexOf (oldStr) != -1)
			inString = replace (oldStr, newStr, inString);
		return inString;
	}

	public static String replace (String oldStr, String newStr, String inString)
	{
		int start = inString.indexOf (oldStr);
		if (start == -1)
		{
			return inString;
		}
		StringBuffer sb = new StringBuffer ();
		sb.append (inString.substring (0, start));
		sb.append (newStr);
		sb.append (inString.substring (start + oldStr.length ()));
		return sb.toString ();
	}
}
