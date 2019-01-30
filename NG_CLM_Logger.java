/*--------------------------------------------------------------------------------------------------------------------------
	Name 				:	Akash Kalyankar,Ankita Panse
	Project Name		:	IDBI FEDERAL LIFE INSURANCE CLAIM PROCESS
	Description			:	Assessment Workstep Validation
	Date of Creation	: 	11/10/2018
	Author 				:
	Group Leader 		: 	Mr.Abhijeet Kumbhar
	Project Manager 	:	Mr.Kishor Marathe

----------------------------------------------------------------------------------------------------------------------------
									CHANGE HISTORY
				This Code is Without Update Client Changes	
----------------------------------------------------------------------------------------------------------------------------
	Problem No.		Changed Date 		Changed By 			Description
					29/11/2018		Akash Kalynakar			this code is added to create custom logs according to workitem 
															names and date wise
----------------------------------------------------------------------------------------------------------------------------

	This class file is used to create custom logs according to Workitem name folders and date wise xml files in path
	jboss/bin/IFLIClaims/NGFLog/<WorkitemName>/<CurrentDate>

---------------------------------------------------------------------------------------------------------------------------*/

package com.newgen.omniforms.user;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;




public class NG_CLM_Logger {
	String WI_NAME;
	StringBuffer str = new StringBuffer();
	NG_CLM_Logger(String WI_Name)
	{
		str.append("\nWorkItem Name : "+WI_Name);
		WI_NAME=WI_Name;
	}
	
	
	public void WriteToLog(String strOutput){
	
		str.append(" \n ");
		str.append(DateFormat.getDateTimeInstance(0,2).format(new java.util.Date()));
		str.append(" \n ");
		str.append(strOutput);
		str.append(" \n ");
		
		StringBuffer strFilePath = null;
		String tempFilePath = "";
		GregorianCalendar calender = new GregorianCalendar();
		String DtString = String.valueOf("" +calender.get(Calendar.DAY_OF_MONTH) + (calender.get(Calendar.MONTH)+1)+calender.get(Calendar.YEAR));
		try{
		strFilePath = new StringBuffer(50);
		strFilePath.append(System.getProperty("user.dir"));
		System.out.println("User Directory : "+strFilePath);
		strFilePath.append(File.separatorChar);
		strFilePath.append("CustomLog");
		System.out.println("My Custom Log Directory : "+strFilePath);
		strFilePath.append(File.separatorChar);
		strFilePath.append("IFLIClaims");
		System.out.println("Claim Process : "+strFilePath);
		strFilePath.append(File.separatorChar);
		strFilePath.append("NGFLog");
		System.out.println("NGFLog : "+strFilePath);
		strFilePath.append(File.separatorChar);
		strFilePath.append(WI_NAME);
		strFilePath.append(File.separatorChar);
		File fBackup = new File(strFilePath.toString());
		if(fBackup==null || !fBackup.isDirectory())
			fBackup.mkdir();			
		strFilePath.append(File.separatorChar);
		System.out.println("IFLIClaims Claims File : "+strFilePath);
		strFilePath.append("IFLIClaims"+ DtString+".xml");
		tempFilePath = strFilePath.toString();
		BufferedWriter out = new BufferedWriter(new FileWriter(tempFilePath,true));
		
		out.write(str.toString());
		out.close();
		}catch(Exception exception) {
			
		}finally{
			strFilePath=null;
		}
		
		
	}
}
