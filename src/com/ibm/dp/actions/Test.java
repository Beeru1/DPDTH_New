package com.ibm.dp.actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String amount ="rtyhgfhfhfgh";
		boolean isNumeric = amount.matches("[0-9]+$");	
		System.out.println(isNumeric);
		// Set the amount pattern string
			Pattern p = Pattern.compile("[^0-9]+$");

			// Match the given string with the pattern
			Matcher m = p.matcher(amount);

			// check whether match is found
			if(m.matches()==true)
			{
				System.out.println(" Pattern matches");

			}else{
			   System.out.println(" Pattern  doesn't matches");
			}

	}

}
