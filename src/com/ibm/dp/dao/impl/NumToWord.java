package com.ibm.dp.dao.impl;

public class NumToWord {

	/**
	 * @param args
	 */
	public static String noToWords[][]={{"Zero","One","Two","Three","Four","Five","Six","Seven","Eight","Nine"},
			{"Ten","Twenty","Thirty","Forty","Fifty","Sixty","Seventy","Eighty","Ninety"},
			{"Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen"},
			{"Hundred","Thousand","Lack","Crore"}};
	public synchronized static String mainOfNotoWords(int num) {

		//int num=110101012;
	//	System.out.println(num);
		if(num<0 || num>999999999)
		{
			System.out.println("Error");
		}
		
		StringBuilder sb=new StringBuilder(((Integer)num).toString());
		int length=sb.length();
		int tempNum=num;
		int tempDiv=1000;
		int temp=3;
		int index=0;
		if(num>=1000){
			while(tempNum>99){
				index=index+temp;
				sb=sb.insert(length-index, ',');
				//System.out.println(index);
				//commaCount++;
				//index+=commaCount;
				tempNum=tempNum/tempDiv;
				tempDiv=100;
				temp=2;
				
			}
		}
		index=0;
	//	System.out.println(sb);
	//	String sbRev=(sb.reverse()).toString();
		String nos[]=sb.toString().split(",");
		String nosRev[]=new String[nos.length];
//		System.out.println(nos.length);
		if(nos.length>1){
		while(index<nos.length){
			nosRev[index]=nos[nos.length-index-1];
			index++;
		}
		
		}
		StringBuilder retString=new StringBuilder();
		temp=-1;
		for(int i=0;i<nosRev.length;i++  ){
			Integer numbersBack=Integer.parseInt(nos[i]);
			//System.out.println(temp);
			
			if(i>0 && temp!=0)
				retString=(retString.append(noToWords[3][nosRev.length-i])).append(" ");
			if(i!=0 && numbersBack==0){
				temp=numbersBack;
				continue;
			}
				
			if(numbersBack>99){
				retString=(retString.append(threeDigitWord(numbersBack))).append(" ");
					continue;
			}else{
				retString=(retString.append(convertNos(numbersBack))).append(" ");
				
			}
			
		temp=numbersBack;
		}
		retString=retString.append("only");
		if(retString.indexOf("Thousand")!=-1 && retString.indexOf("Hundred")==-1
				&& (retString.indexOf("only")-retString.indexOf("Thousand")>9))
			retString=retString.insert(retString.indexOf("Thousand")+8, " and ");
		if(retString.indexOf("Lack")!=-1 && retString.indexOf("Thousand")==-1 
				&& retString.indexOf("Hundred")==-1 && (retString.indexOf("only")-retString.indexOf("Lack")>5))
			retString=retString.insert(retString.indexOf("Lack")+4, " and ");
		if(retString.indexOf("Crore")!=-1
			&& retString.indexOf("Lack")==-1
			&& retString.indexOf("Thousand")==-1
			&& retString.indexOf("Hundred")==-1
			&& ( retString.indexOf("only")-retString.indexOf("Crore"))>6)
			retString=retString.insert(retString.indexOf("Crore")+5, " and ");
//		System.out.println();
		System.out.println(retString);
		
		return retString.toString();
	}
		public static String convertNos(int no){
		
		if(no>10 && no <20){
			int ind=no%10;
			return noToWords[2][ind-1];
		}
		
		else if(no<10){
			return noToWords[0][no];
		}
		else if(no%10 == 0 && no <100){
			int ind=no/10;
			return noToWords[1][ind-1];
		}
		else if(no>20 && no <100){
			
			int ind=no%10;
			int ind1=no/10;
			return noToWords[1][ind1-1]+" " +noToWords[0][ind];
			
		}
		
		return "";
		
	}
	public synchronized static String threeDigitWord(int no){
		
		if(no%100==0){
			int ind=no/100;
			return noToWords[0][ind]+" "+noToWords[3][0];
		}else if(no>100 && no<1000){
			int ind=no/100;
			return noToWords[0][ind]+" "+noToWords[3][0]+" and "+convertNos(no%100);
		}
		
		
		return "";
		
	}
}
