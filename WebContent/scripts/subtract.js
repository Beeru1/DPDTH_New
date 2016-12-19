var s="";
var mess="";

//rough check of the answer
function checkAnsSub(x,y,ans) {
ok=false;
//use casting out nines
a=Number(digSum(x))-Number(digSum(y));
b=digSum(ans);
//these should be the same, but sometimes a zero is left after casting out nines
//and sometimes not. Also, a negative answer needs to have a 9 added
//if negative or zero, add 9
if (a<=0)
a+=9;
a=a%9;
//if y is bigger, then the answer is negative. We take y from x and call it negative
//the check needs to be subtracted from 9
if (Number(x)<Number(y))
//this won't work when the numbers are the same within the range of javascript
//because they appear equal
// however, we fix this later.
{
a=9-a;
}
//cast out any remaining nines
a=a%9;
//mess+=("a="+a+"; digSum x="+digSum(x)+"; digSum y="+digSum(y)+"; digSum ans="+b);
//now we can do our check
if (a	==b)
ok=true;
if (ok)
return true
else
return false;

}//end of  checkAns(x,y,ans)

//add the digits to get the digital sum for casting out nines
function digSum(num) {
//just make sure the number is a string and doesn't have a minus sign
num=String(num);
num.replace(/-/,"");
//mess+="num="+num+"<br>";
//sum is the sum of the digits
sum=0;
for (i=0;i<num.length;i++)
{
//if there is a decimal, we just ignore it
if (num.charAt(i)==".")
continue
else
sum+=Number(num.charAt(i));
//mess+="charAt"+i+"="+num.charAt(i)+"; sum="+sum+"<br>";
}
//cast out nines
return sum%9;
}//end of  checkAns(x,y,ans)

//used for checking and putting a number in at random
function myRand() {
z="";
s="";
//generate a random number and remove the decimals
//random numbers are between 0 and 1
a=Math.random()* Math.pow(10,16);
b=Math.random()* Math.pow(10,16);
a=String(a).replace(/\./g,"");
b=String(b).replace(/\./g,"");
//join the two strings together
z=String(a)+String(b);
decPoint=Math.floor(Math.random()*Number(z.length));
z=z.substring(0,decPoint)+"."+z.substring(decPoint,z.length);
if (decPoint==0)
z="0"+z;
return z;
}//end of  myRand()

//change sign
function signSwap(x) {
if (x=="+")
x="-";
else
x="+";
return x;
}//end of  signSwap(x)

//if there are any comma separators in the number entered,
//they are removed

function removeCommas(aNum) {
//remove any commas
aNum=aNum.replace(/,/g,"");
//remove any spaces
aNum=aNum.replace(/\s/g,"");
return aNum;
}//end of  removeCommas(aNum)

//this checks whether the number entered does not have several decimals
//or non-numeric characters
function checkNum(aNum) {
var isOK=0;
var aNum=aNum+"";
//if the number has one or none decimal points, lastIndexOf and indexOf
//will give the same answer
if (aNum.lastIndexOf(".")!=aNum.indexOf("."))
isOK=1;
else
//here a regular expression is used to check for numbers and decimals
if (aNum.match(/[^0-9.]/))
isOK=2;
return isOK;
}//end of  checkNum(aNum)

//add commas separating thousands
function addseps(x) {
//make x a new variable
var x=x;
//make x a string
x+="";
//or x=String(x);
//iLen is the number of digits before any decimal point
// for 45.123, iLen is 2
//iLen is the length of the number, if no decimals
iLen=x.length;
pos=x.indexOf(".");
if (pos>-1) //there are decimals
{
iLen=pos;
}
//add the decimal point
temp="";
//add the decimal part to begin
// with 45.123, we add the .123
temp=x.substring(iLen,x.length);
//iLen-1 is the rightmost non-decimal digit (5 in 98745.123)
for (var i=iLen-1;i>=0;i--)
//we add a separator when the expression (iLen-i-1)%3==0 is true...
//except when i is (iLen-1), or the first digit
//eg (98745.12). i is iLen-1, and the digit pos is next the decimal, 
//it is 5. From here, we decrement i...iLen-2, iLen-3, iLen-4 ... when i is a multiple of
//3, (i=iLen-iLen+4-1). This point is just before the number 7
if ((iLen-i-1)%3==0&&i!=iLen-1)
temp=x.charAt(i)+","+temp;
else
temp=x.charAt(i)+temp;
return temp;
}//end of  addseps(x)


//inserts the decimal point 
//as we cannot insert into a javascript string
//we need to re-write it, putting the decimal point in
//at the right place
function insertDec(num,decPlace) {
var num=num;
var decPlace=decPlace;
ans="";
if (decPlace>0)
{
for (var i=0;i<num.length;i++)
{
//when we get to the position we want to 
//insert the decimal point, we add the point to
//our string
if (i==num.length-decPlace)
ans=ans+"."+num.charAt(i);
else
ans=ans+num.charAt(i);
}
return ans;
}
else
return num;
}//end of  insertDec(x,decPlace)

//find decimal point position
function getDec(x) {
var x=x;
x=x+"";

//position of first decimal digit
pos=x.indexOf('.')+1;
//if 0, then no decimals (-1+1)
if (pos>0)
return x.length-pos;
else
return 0;
}//end of  getDec(x)


function Sub(x,y) {
var x=x;
var y=y;
var x0=removeCommas(x);;
var y0=removeCommas(y);
var sign="+";
//sum used for javascript
x=removeCommas(x);
y=removeCommas(y);
if (!((checkNum(x)==0)&&(checkNum(y)==0)))
{

if (checkNum(x)>0)
{

switch (checkNum(x)) {
case 1:
alert("Too many decimal points in number 1.")
break    
case 2:  
alert("Some characters aren't numbers in number 1");  
break       

}//end of switch 

}//end of check number 1

if (checkNum(y)>0)
{

switch (checkNum(y)) {
case 1:
alert("Too many decimal points in number 2.")
break    
case 2:  
alert("Some characters aren't numbers in number 2");  
break       

}//end of switch 

}//end of check number 1

}//end number problem
else //numbers check out
{

jssum=Number(x)-Number(y);
//decXo, and decYo are the orignal decimal positions
//as zeros are added to the numbers, the decimal position will change
decXo=getDec(x);
decYo=getDec(y);
decX=decXo;
decY=decYo;

str="";
//remove any decimal from x
x=x.replace(".","");
//remove any decimal from y
y=y.replace(".","");

//initialise decPlace, which is the number of decimals in 
//the number with the most decimals
//First just assume x is the number with most decimal places
//If decX isn't longer than decY, then the following will be true
var decPlace=decX;
//Then check which is the correct number
if (decX>decY)
{
decPlace=decX;
//add zeros to y
//the number of zeros is just the difference between the number of
//decimal places in each number.
//decYo is the number of decimals in the original y number.
for (var i=0;i<(decPlace-decYo);i++)
{
//add a zero at the end
y+="0";
decY+=1;
//increment decY, so we keep a record of the decimal position
//in the new number. For instance
// if decYo=2, as in 1.12, then the number is really 112/10^2
//When we add zeros, the position becomes one more
//for each zero we add .. 1.120, decimal is now 3 from the end, or
//the number is really 1120/10^3

}

}
//add zeros to x, if y has more decimals
//repeat of the above
else
if (decY>decX)
{
decPlace=decY;
//add zeros to x
for (var i=0;i<(decPlace-decXo);i++)
{
x+="0";
decX+=1;
}
}
//otherwise, decPlace is as above

//swap
//find the length of the numbers, so we can put them in the right
//order for the calculation
xlen=x.length;
ylen=y.length;
//swap the numbers, if xlen isn't the longest
if ((xlen<ylen)||(Number(x)<Number(y)))
{
//sign is used in the answer
sign=signSwap(sign);
//swap values
var temp=y;
y=x;
x=temp;
//swap decimal positions
temp=decX;
decX=decY;
decY=temp;
//new lengths, if x is shorter
//could have just swapped them as above!
xlen=x.length;
ylen=y.length;
}
//end of swapping

//begin subtracting
//set carry to zero.
var carry=0;
//set the string, s, which will contain the answer
var s="";
//now add the two numbers:
//work down from the length of the longest string:
//which is always x
var numx,numy;
for (var i=xlen-1;i>=0;i--)
{
numy=0;
if ((ylen-xlen+i)>-1)
numy=Number(y.charAt(ylen-xlen+i));
//In the above, when ylen-xlen+i=0, we are at the end of the y numbers
//and i=xlen-ylen. For the next i, (i-1), we will be beyond the length of y, and 
//so just add a possible carry, and thereafter, just zeros

//x is always found in the longest string
numx=Number(x.charAt(i));
//add the sum of the numbers and any carry from previously
//we add only the units bit of the number
//10 is the normal base for decimals
if (numx<(numy+carry))
{
numx+=10;
carry1=1;
}
else
carry1=0;
s=(numx-(numy+carry))%10+s;
if (carry1>0)
carry=carry1;
else
carry=0;
//we carry the tens bit of the new number
//carry=Math.floor((numy+numx+carry)/10);
if (s.length>x.length)
{


}
}//end of subtracting
//put the decimals back in the numbers
s=insertDec(s,decPlace);

//now write out the answer:
x=insertDec(x,decX);
y=insertDec(y,decY);
x=addseps(x);
y=addseps(y);

//find the length of the numbers, so we can put them in the right
//order for the calculation
xlen=x.length;
ylen=y.length;
//swap the numbers, if xlen isn't the longest
if (xlen<ylen)
{
sign=signSwap(sign);
//swap values
var temp=y;
y=x;
x=temp;
//swap decimal positions
temp=decX;
decX=decY;
decY=temp;
//new lengths, if x is shorter
//could have just swapped them as above!
xlen=x.length;
ylen=y.length;
}
//end of swapping

// check answer
//mess+="x0="+x0+"; y0="+y0+"; s="+s+"<br>";
aCheck=''
if (checkAnsSub(x0,y0,s))
aCheck="<br>"+"Ans check OK<br>";
else
{
//this is assumed to be when the numbers are so
//similar that javascript can't tell which is the bigger 
//number. As the answer check fails, we assume
//the numbers should be the other way round.
//we work out the answer again, and put it in s2
// we compute the base 10 complement of the 
//number. 
sign=signSwap(sign);
s2=""
for (i=0;i<s.length;i++) {
if (s.charAt(i)==".")
{
s2+=".";
continue;
}
if (i==s.length-1)
s2+=10-Number(s.charAt(i));
else
s2+=9-Number(s.charAt(i));
}//end of for statement
s=s2;
//swap x and y
temp=y;
y=x;
x=temp;
//we give up on checking and honestly say we haven't 
//checked the number. 
aCheck="<br>Answer has not been checked<br>";
}//else number doesn't check
//end check answer

//remove leading zeros from answer:
while (s.charAt(0)=="0")
{
s=s.substring(1,s.length);
}
//we keep a zero if the number is purely decimal
if (s.charAt(0)==".")
s="0"+s;

//align the numbers by putting a space infront of the shortest number
for (var i=0;i<(xlen-ylen);i++)
y="&nbsp;"+y;

//note, we cannot use y or ylen anymore because y contains "&nbsp;
//which is 6 characters, creating one space!
//sign makes s positive or negative, as appropriate
s=sign+addseps(s);
var slen=s.length;
//pad out x and y so they line up with their decimals and the
//answer
for (var i=0;i<(slen-xlen);i++)
{
x="&nbsp;"+x;
y="&nbsp;"+y;
}
//finally, we put the numbers back in the right order,
//if y was bigger than x numerically, and the answer is negative
if (sign=="-")
{
temp=x;
x=y;
y=temp;
}
ans=s;
return ans;
}//end of else (numCheck OK)
}//end of  Sub(x,y)
