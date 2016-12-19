function validation(field,n)

{

	switch(n)
	{
		case 1:
			var valid = "0123456789"
			var ok = "yes";
			var temp;

			for (var i=0; i<field.value.length; i++) 
				{
					temp = "" + field.value.substring(i, i+1);
					if (valid.indexOf(temp) == "-1") ok = "no";
				}
			//if(field.value.length>6)
			//ok="no"
			if (ok == "no") 
				{
				alert("This field cannot Contain special Character!");

				field.focus();
				field.select();
				}
			break
		case 2:
			missinginfo = "";
			if(document.form.txtMail.value!="")	
				{
					if ((document.form.txtMail.value.indexOf('@') == -1) || 
					(document.form.txtMail.value.indexOf('.') == -1)) 
						{
						missinginfo += "\n     -  Email address";
						}
				}		
				if (missinginfo != "")
			 	{
					missinginfo ="_____________________________\n" +
					"You failed to correctly fill in your:\n" +
					missinginfo + "\n_____________________________" +
					"\nPlease re-enter and submit again!";
					alert(missinginfo);

					document.form.txtMail.focus();
					return false;
				}
				else
			 return true;
			
			break
	case 3:
			
			var valid = "`~&'^"
			var ok = "yes";
			var temp;
			var temp1;
			for (var i=0; i<field.value.length; i++)
			 {
				temp = "" + field.value.substring(i, i+1);
				if (valid.indexOf(temp) >= 0) 
				{
				ok = "no";
				temp1=temp;
				}
				
			}

				if (ok == "no") 
				{
	
					alert("This field cannot Contain special Character!");
					field.focus();
					field.select();
		   		}
			break
			
		case 4:
				
			var valid = "`~&'^0123456789@#%*+]\/<>"
			var ok = "yes";
			var temp;
			var temp1;
			for (var i=0; i<field.value.length; i++)
			 {
				temp = "" + field.value.substring(i, i+1);
				if (valid.indexOf(temp) >= 0) 
				{
				ok = "no";
				temp1=temp;
				}
				
			}

				if (ok == "no") 
				{
	
					alert("This field cannot Contain special Character!");
					field.focus();
					field.select();
		   		}
			break
			
		case 5:
				var valid = "0123456789."
				var ok = "yes";
				var temp;
				for (var i=0; i<field.value.length; i++)
			 	{
					temp = "" + field.value.substring(i, i+1);
					if (valid.indexOf(temp) == "-1") ok = "no";
				}
					
					if (ok == "no") 
					{
						alert("This field cannot Contain special Character!");
						field.focus();
						field.select();
		   			}
			break

			case 6:
			
				var valid = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ,0123456789"
				var ok = "yes";
				var temp;
				for (var i=0; i<field.value.length; i++)
			 	{
					temp = "" + field.value.substring(i, i+1);
					if (valid.indexOf(temp) == "-1") ok = "no";
				}
				
				if (ok == "no") 
				{
						alert("This field cannot Contain special Character!");
					field.focus();
					field.select();
		   		}
			break
			
			case 7:
			
			var valid = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-/"
				var ok = "yes";
				var temp;
				for (var i=0; i<field.value.length; i++)
			 	{
					temp = "" + field.value.substring(i, i+1);
					if (valid.indexOf(temp) == "-1") ok = "no";
				}
				
				if (ok == "no") 
				{
					alert("This field cannot Contain special Character!");
					field.focus();
					field.select();
		   		}
			break

		case 8:
		
			
			var valid = "01234.56789"
		var ok = "yes";
		var temp,c=0;
		for (var i=0; i<field.value.length; i++) 
		{
		temp = "" + field.value.substring(i, i+1);
		if (valid.indexOf(temp) == "-1") ok = "no";
	
		if (temp==".")
		c=c+1; 
		}
	
		if (c>=2)
		ok="no"
		if (ok == "no") 
		{
		alert("Please enter numeric data with the correct number of decimal places!");
		field.focus();
		field.select();
	   }
	
		break
			
	case 9:
			 var valid = "`~&'^"
			var ok = "yes";
			var temp;
			var temp1;
			for (var i=0; i<field.value.length; i++)
			 {
				temp = "" + field.value.substring(i, i+1);
				if (valid.indexOf(temp) >= 0) 
				{
				ok = "no";
				temp1=temp;
				}
				
			}
				
				missinginfo = "";
			if(field.value!="")	
				{
					if ((field.value.indexOf('@') == -1) || 
					(field.value.indexOf('.') == -1)) 
						{
						missinginfo += "\n     -  Email address";
						}
				}		
					if (missinginfo != "")
			 			{
							missinginfo ="_____________________________\n" +
							"You failed to correctly fill in your:\n" +
							missinginfo + "\n_____________________________" +
							"\nPlease re-enter and submit again!";
							alert(missinginfo);

							field.focus();
							
						} 
			 	 			else	

								if (ok == "no") 
								{
	
								alert("This field cannot Contain special Character!");
								field.focus();
								field.select();
								
		   						}
		   					

			break
	 case 10:
	 		var valid = "`~&'^@#$%!?/\|+*"
			var ok = "yes";
			var temp;
			var temp1;
			for (var i=0; i<field.value.length; i++)
			 {
				temp = "" + field.value.substring(i, i+1);
				if (valid.indexOf(temp) >= 0) 
				{
				ok = "no";
				temp1=temp;
				}
				
			}

				if (ok == "no") 
				{
	
					alert("This field cannot Contain special Character!");
					field.focus();
					field.select();
		   		}
			break
case 11:
	 		var valid = "`~&'^@#$%!?\|+*."
			var ok = "yes";
			var temp;
			var temp1;
			for (var i=0; i<field.value.length; i++)
			 {
				temp = "" + field.value.substring(i, i+1);
				if (valid.indexOf(temp) >= 0) 
				{
				ok = "no";
				temp1=temp;
				}
				
			}

				if (ok == "no") 
				{
	
					alert("This field cannot Contain special Character!");
					field.focus();
					field.select();
		   		}
			break

		case 12:
	 		var valid = "`~'^@#$%!?\|+*"
			var ok = "yes";
			var temp;
			var temp1;
			for (var i=0; i<field.value.length; i++)
			 {
				temp = "" + field.value.substring(i, i+1);
				if (valid.indexOf(temp) >= 0) 
				{
				ok = "no";
				temp1=temp;
				}
				
			}

				if (ok == "no") 
				{
	
					alert("This field cannot Contain special Character!");
					field.focus();
					field.select();
		   		}
			break
	case 13:
			var valid = "0123456789-"
			var ok = "yes";
			var temp;

			for (var i=0; i<field.value.length; i++) 
				{
					temp = "" + field.value.substring(i, i+1);
					if (valid.indexOf(temp) == "-1") ok = "no";
				}
			//if(field.value.length>6)
			//ok="no"
			if (ok == "no") 
				{
				alert("Enter only numeric data along with '-' !");

				field.focus();
				field.select();
				}
		break
		
		case 14:
			var valid = "0123456789/"
			var ok = "yes";
			var temp;

			for (var i=0; i<field.value.length; i++) 
				{
					temp = "" + field.value.substring(i, i+1);
					if (valid.indexOf(temp) == "-1") ok = "no";
				}
			//if(field.value.length>6)
			//ok="no"
			if (ok == "no") 
				{
				alert("This field cannot Contain special Character!");

				field.focus();
				field.select();
				}
		break

 case 15:
	 		var valid = "`~&'^@#$%!?/\|+*.ABCDEFGHIJKLMNOPQRSTUVWXYZ "
			var ok = "yes";
			var temp;
			var temp1;
			for (var i=0; i<field.value.length; i++)
			 {
				temp = "" + field.value.substring(i, i+1);
				if (valid.indexOf(temp) >= 0) 
				{
				ok = "no";
				temp1=temp;
				}
				
			}

				if (ok == "no") 
				{
	
					alert("( " + temp1 + " )" + " not allowed. Please enter correct data in small latters!."); 
					field.focus();
					field.select();
		   		}
			break

			case 16:
			
				var valid = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				var ok = "yes";
				var temp;
				for (var i=0; i<field.value.length; i++)
			 	{
					temp = "" + field.value.substring(i, i+1);
					if (valid.indexOf(temp) == "-1") ok = "no";
				}
				
				if (ok == "no") 
				{
					alert("This field cannot Contain special Character!");
					field.focus();
					field.select();
		   		}
			break
			
		case 17:
	 		var valid = "`~&'^#$%!?/\|+*"
			var ok = "yes";
			var temp;
			var temp1;
			for (var i=0; i<field.value.length; i++)
			 {
				temp = "" + field.value.substring(i, i+1);
				if (valid.indexOf(temp) >= 0) 
				{
				ok = "no";
				temp1=temp;
				}
				
			}

				if (ok == "no") 
				{
	
					alert("This field cannot Contain special Character!");
					field.focus();
					field.select();
		   		}
			break
			
			case 18:
			
				var valid = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ-_0123456789"
				var ok = "yes";
				var temp;
				for (var i=0; i<field.value.length; i++)
			 	{
					temp = "" + field.value.substring(i, i+1);
					if (valid.indexOf(temp) == "-1") ok = "no";
				}
				
				if (ok == "no") 
				{
					alert("This field cannot Contain special Character!");
					field.focus();
					field.select();
		   		}
			break
			 case 19:
			 		var valid = '!@#$^%&(~)|/\+?`/",{}[];:<>=.'
					var ok = "yes";
					var temp;
					var temp1;
					for (var i=0; i<field.value.length; i++)
					 {
						temp = "" + field.value.substring(i, i+1);
						if (valid.indexOf(temp) >= 0) 
						{
						ok = "no";
						temp1=temp;
						}
						
					}
		
						if (ok == "no") 
						{
			
							alert("This field cannot Contain special Character!");
							field.focus();
							field.select();
				   		}
			break

			case 20:
			
				var valid = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ-_&"
				var ok = "yes";
				var temp;
				for (var i=0; i<field.value.length; i++)
			 	{
					temp = "" + field.value.substring(i, i+1);
					if (valid.indexOf(temp) == "-1") ok = "no";
				}
				
				if (ok == "no") 
				{
					alert("This field cannot Contain special Character!");
					field.focus();
					field.select();
		   		}
			break			
			
		}//switch
}//function