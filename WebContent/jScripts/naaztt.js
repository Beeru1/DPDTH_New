var _TT_main=new Array();
var count=0;
var divs=new Array();
var tabs=new Array();
function TT(id,currdiv,currtab,clH,clN,pageName) {	//alert("TT");
	
	
		
	var ttobj = new Object();
	var tabArr = new Array();

	ttobj.id = id;
	ttobj.currentDiv = currdiv;
	ttobj.currentTab = currtab;
	ttobj.classH = clH;
	ttobj.classN = clN;
	ttobj.tabArr = tabArr;	
	ttobj.pageName=pageName;
	//alert(pageName);
	ttobj.additem = add_TT_item;
	ttobj.showTT = _TT_create;	
	ttobj.print = _TT_print;
	
	return(ttobj);
}
function _TT_item(id) {
	//alert('_TT_item');
	var obj = new Object();
	obj.id = id;
	obj.href = '#';
	obj.oceh = '';
	obj.target = '';
	obj.title = '';
	obj.label = '';
	obj.divToShow = '';
	obj.currentState = 'TTNORMAL';		// TTCURRENT | TTNORMAL
	
	return(obj);
}
function add_TT_item(id,href,oceh,target,title,label,divToShow,cs) {
	//alert('add_TT_item');
	tabs[count]=id;
	divs[count]=divToShow;
	count++;
	var obj = new _TT_item(id);

	obj.href = href;
	obj.oceh = oceh;
	obj.target = target;
	obj.title = title;
	obj.label = label;
	obj.divId = divToShow;
	obj.currentState = cs;
		
	this.tabArr[this.tabArr.length] = obj;
}
function  showTab(a)
{
		_TT_toggle(tabs[a],divs[a],'mainTabTable','');
}
function _TT_toggle(tabId,divId,ttId,page) {
//alert(page.value+" tabId"+tabId);
if(page=='reco') {
var el = document.getElementById("closingStockUploadScreen");

 var recoPeriod = document.getElementById("recoPeriodId").value; }
	var divArr,divId,i;
	var ttobj;
	var _TT_main_i;
	var hhh;

	for (i=0; i<_TT_main.length; i++)
	if (_TT_main[i].id==ttId) {
		//alert(i);
		_TT_main_i=_TT_main[i]; break; 
	}	

	tabobj1 = document.getElementById(_TT_main_i.currentTab);
	tabobj2 = document.getElementById(tabId);	
	divobj  = document.all(divId);
	
	if (_TT_main_i.currentTab != tabId)
	{		
		divArr = _TT_main_i.currentDiv.split('#');
		for (i=0; i<divArr.length; i++)
		document.all(divArr[i]).style.display = 'none';			
		
			//alert(divArr.length);

		tabobj1.className = _TT_main_i.classN;

		divArr = divId.split('#');
		for (i=0; i<divArr.length; i++)
		document.all(divArr[i]).style.display = 'block';
		
		tabobj2.className = _TT_main_i.classH;
		
		_TT_main_i.currentDiv = divId;
		_TT_main_i.currentTab = tabId;
		
	}
	// Adding by RAM
	if(page=='reco') {
	if(document.forms[0].oldOrNewReco.value == 'N')
		{
	if(tabId == 'tab5' && recoPeriod > 0)
		{
		el.style.visibility ="visible";
		}
	else
		{
		el.style.visibility ="hidden";
		}
		}
		// End of Adding by RAM
	}
	}
function _TT_create(pageName) {	//alert('_TT_create');
//alert('_TT_create'+pageName);
//alert('_TT_create'+pageName);
	var tblsrc = "<div class='TTcontainer'><table cellpadding=0 cellspacing=0 border=0><tr>$(DATA)$</tr></table></div>";
	var tdsrc = "<td><a href='$(HREF)$' onMouseDown=\"$(OCEH)$\" target='$(TARGET)$' title='$(TITLE)$'><table id='$(TABID)$' class='$(CLASS)$' cellpadding=0 cellspacing=0 border=0><tr><td class='ltCurve' valign=top><IMG SRC='images/_tt_lt_1.gif' BORDER=0></TD><td class='midBlock' nowrap>$(LABEL)$</td><td class='rtCurve' valign=top><IMG SRC='images/_tt_rt_1.gif' BORDER=0></td><td class='tabseparator'>&nbsp;</td></tr></table></a></td>"
	
	var trsrc='', tmp='';
	var tabClick;
	var iobj,i;

	for (i=0; i<this.tabArr.length; i++) {
		
		iobj = this.tabArr[i];
		tmp = tdsrc;
		tabClick = '';

		if (iobj.divId != '')
		tabClick = "_TT_toggle('" + iobj.id + "', '" + iobj.divId + "', '" + this.id + "','"+pageName+ "');";
		
		tmp = tmp.replace('$(TABID)$',iobj.id);
		tmp = tmp.replace('$(HREF)$',iobj.href);
		tmp = tmp.replace('$(OCEH)$',tabClick + iobj.oceh);
		tmp = tmp.replace('$(TARGET)$',iobj.target);
		tmp = tmp.replace('$(TITLE)$',iobj.title);
		tmp = tmp.replace('$(LABEL)$',iobj.label);		
		
		if (iobj.currentState == 'TTCURRENT')
			tmp = tmp.replace('$(CLASS)$',this.classH);			
		else
			tmp = tmp.replace('$(CLASS)$',this.classN);
			
		trsrc += tmp;
	}

	_TT_main[_TT_main.length] = this;
	tblsrc = tblsrc.replace('$(DATA)$',trsrc);	
	return(tblsrc);
}
function _TT_print() {	
//alert('_TT_print');
	//ITF.document.body.scrollHeight
	var htmSrc="<html><head><title>« Print Version »</title>";
	htmSrc += "<link rel='stylesheet' href='/_ag0/_def/_css/main.css' type='text/css' />";
	htmSrc += "<style>#DIV_ATT {display:none;visibility:hidden}; ";
	htmSrc += "h3 {font-family:Arial; font-size:13px; font-weight:bold; color:#888833; margin-bottom:0px; };";
	htmSrc += "#formbody { position:relative; width:100%; padding:0px; border:1px solid #ABBDCC }; ";
	htmSrc += "select,input,textarea{display:none}; a {font-family:Verdana; font-size:10px; text-decoration; }; a:hover {font-family:Verdana; font-size:10px; text-decoration; }</style>";
	htmSrc += "<style type='text/css' media='screen'>";
	htmSrc += "a.prn {font-family:Verdana; font-size:11px; font-weight:bold; color:#336699; text-decoration:underline}; a.prn:hover {font-family:Verdana; font-size:11px; font-weight:bold; color:#FF0000; text-decoration:none}";
	htmSrc += "</style>";
	htmSrc += "<style type='text/css' media='print'>";
	htmSrc += "a.prn {display:none}"
	htmSrc += "</style>";	
	htmSrc += "<script>function format(){ ";
	htmSrc += "if(document.readyState!='complete') return;";
	htmSrc += "var fldArr = document.getElementsByTagName('input');	var selArr = document.getElementsByTagName('select'); var imgArr = document.getElementsByTagName('img');";
	htmSrc += "for (i=0; i<imgArr.length; i++) if (parseInt(imgArr[i].width)<20) imgArr[i].style.display='none';";
	htmSrc += "for (i=0; i<fldArr.length; i++) if (fldArr[i].type!='hidden') fldArr[i].parentElement.innerHTML = ((fldArr[i].value=='')?'&nbsp;':fldArr[i].value) + fldArr[i].parentElement.innerHTML; ";
	htmSrc += "for (i=0; i<selArr.length; i++) selArr[i].parentElement.innerHTML = selArr[i].options[selArr[i].selectedIndex].text + selArr[i].parentElement.innerHTML; var fldArr = document.getElementsByTagName('textarea');";
	htmSrc += "for (i=0; i<fldArr.length; i++) if (fldArr[i].type!='hidden') fldArr[i].parentElement.innerHTML = ((fldArr[i].value=='')?'&nbsp;':fldArr[i].value) + fldArr[i].parentElement.innerHTML; ";
	ITF = (document.all('ITFRAME'))?ITF:null;
	ADV = (document.all('ITFRAME'))?ITF:null;	
	if (ITF)
	{	
		var h = ITF.document.body.scrollHeight;
		htmSrc += "var ITF = document.all('ITFRAME'); if (ITF) { ITF.height = "+h+"; divITFRAME.style.height="+h+"; divITFRAME.parentElement.parentElement.height="+h+"; }";

		//var ADV = document.all('ADVFRAME');
		//if (ADV) {			
		h = ADV.document.body.scrollHeight + 50;
		htmSrc += "var ADV = document.all('ADVFRAME'); if (ADV) { ADV.height = "+h+"; divADVFRAME.style.height="+h+"; divADVFRAME.parentElement.height="+h+"; }";
		//}	
	}	
	htmSrc += "} document.onreadystatechange=format;</script>";	
	tmp = (document.all('pgtitle').innerHTML).replace(/\r/,"");
	tmp = tmp.replace(/\n/,"");	
	htmSrc += "</head><body style='margin:10px'>";	
	htmSrc += "<table id='pgheader' cellpadding=0 cellspacing=0 border=0 width=100% style='border-bottom:1px solid #888833'>";	
	htmSrc += "<tr><td id='pgtitle'>"+tmp+"</td><td align=right valign=bottom style='padding-bottom:2px'><a class='prn' href='#' onClick='window.print()'>Print</a> | <a class='prn' href='#' onClick='window.close()'>Close</a></td></tr></table>";	
	if (document.all('DIV_WF'))
	htmSrc += "<h3 id='title'>Workflow Details</h3><div id='formbody'>" + document.all("DIV_WF").innerHTML + "</div>";	
	for (i=0; i<this.tabArr.length; i++) {
		iobj = this.tabArr[i];
		htmSrc += "<h3 id='title'>" + iobj.title + "</h3>";
		htmSrc += "<div id='formbody'>" + document.all(iobj.divId).innerHTML + "</div>";
	}
	if (document.all('DIV_ATT') && DIV_ATT.style.display=="")
	htmSrc += "<h3 id='title'>Attachments</h3><div id='formbody'><table id='striped' cellspacing='1' cellpadding='15' border='0'><tr>" + document.all("DIV_ATT").innerHTML + "</tr></table></div>";

	htmSrc += "</body></html>";	
	var wobj = window.open("","print","width:600px,height:500px,resizable=1,scrollbars=1");
	//var wobj = window.open("","print");
	wobj.document.open();
	wobj.document.writeln(htmSrc);	
	wobj.document.close();
}