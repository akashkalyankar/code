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
					13/11/2018		Akash Kalynakar		web service was giving wrong output in status wise benefit
					06/11/2018		Akash Kalynakar		code to fetch benefit amount in web service consumption is added
					02/11/2018		Akash Kalynakar		web service consumption code class file has been added
					
----------------------------------------------------------------------------------------------------------------------------

	Functions with there usages
	
1.	getStatusWiseBenefitList( String ClaimNumber )

			Description 	: 	This function is used to fetch the status wise benefits of a particular claim number from the
								webservice URL provided by the elixir team and it takes claim number as a single parameter.
								
			Return Value	: 	List<List<String>> 
			Usage			:	getStatusWiseBenefitList(String dateOfEventString)
			
			
2. getHospitalClaimDetails()

			Description : This function is used to get hospital claim details of a particular policy number to show the 
							details on the form.
							
			Return Value : ArrayList<String> 
			
			Usage : getHospitalClaimDetails();
			
			

---------------------------------------------------------------------------------------------------------------------------*/

package com.newgen.omniforms.user;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.ObjectInputStream.GetField;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import XML.XMLParser;

class StatusWiseBenefit
{
	
	public String status;
	public String reason;
	public String description;
	public String amount;
	public String netClaimAmount;
}


public class WSDemo {
	static int num;
	static String EndPointurl="http://172.18.10.114:7001/eElixirWeb/services/ClaimDetails?wsdl";
	static String policyNumber;
	static String claimNumber;
	NG_CLM_Logger log = new NG_CLM_Logger("In WebService : ");
	public List<List<String>> getStatusWiseBenefitList(String ClaimNumber)
	{
		
		WSDemo WS = new WSDemo();
		
		List<List<String>> statusWiseArrayList = new ArrayList<List<String>>();
		StatusWiseBenefit statusWiseBenefitArray[] = WS.getStatusWiseBenefit(ClaimNumber);
	//	ArrayList<String> arr= WS.getHospitalClaimDetails();
		
	//	System.out.println("Details :"+arr);

		//List<String> statusWiseArrayListSub = new ArrayList<String>()
	
		for(int i=0;i<statusWiseBenefitArray.length;i++)
		{
			List<String> statusWiseArrayListSub = new ArrayList<String>();	
			System.out.println("\n\n\nStatuswise Benefit ");
			System.out.println("Status : " +statusWiseBenefitArray[i].status);
			System.out.println("Reason : " +statusWiseBenefitArray[i].reason);
			System.out.println("Amount : " +statusWiseBenefitArray[i].amount);
			System.out.println("Description : " +statusWiseBenefitArray[i].description);
			System.out.println("Net Claim Amount : "+statusWiseBenefitArray[i].netClaimAmount);
			statusWiseArrayListSub.add(statusWiseBenefitArray[i].status);
			statusWiseArrayListSub.add(statusWiseBenefitArray[i].reason);
			statusWiseArrayListSub.add(statusWiseBenefitArray[i].amount);
			statusWiseArrayListSub.add(statusWiseBenefitArray[i].description);	
			statusWiseArrayListSub.add(statusWiseBenefitArray[i].netClaimAmount);
			statusWiseArrayList.add(statusWiseArrayListSub);
				
		}
		log.WriteToLog("IN WSDEMO " + statusWiseArrayList);
		System.out.println("Statuswise Benefit Array "+statusWiseArrayList);
		return statusWiseArrayList;
	}
		
		
	
	
	//NG_CLM_Logger log = new NG_CLM_Logger();
	
	public StatusWiseBenefit[] getStatusWiseBenefit(String claimNumber)
	{
		String SOAP_inxml_getStatusWiseBenefitRequest =
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cla=\"http://www.idbi.com/elixir/ClaimDetails/\">"
					+"	<soapenv:Header/>"
					+"		<soapenv:Body>"
					+"			<cla:getStatusWiseBenefitRequest>"
					+"				<ClaimNbr>"+claimNumber+"</ClaimNbr>"
					+"			</cla:getStatusWiseBenefitRequest>"
					+"		</soapenv:Body>	"
					+"	</soapenv:Envelope>";		
		//log.WriteToLog("The SOAP_inxml_getStatusWiseBenefitRequest"+SOAP_inxml_getStatusWiseBenefitRequest );
		log.WriteToLog("The SOAP_inxml_getStatusWiseBenefitRequest"+SOAP_inxml_getStatusWiseBenefitRequest );
		String SOAP_outxml_getStatusWiseBenefitResponse = executeWS(SOAP_inxml_getStatusWiseBenefitRequest,EndPointurl);
		
		System.out.println("SOAP Output XML for getStatusWiseBenefitResponse : " + SOAP_outxml_getStatusWiseBenefitResponse );
	//	log.WriteToLog("SOAP Output XML for getStatusWiseBenefitResponse : " + SOAP_outxml_getStatusWiseBenefitResponse);
		
		XMLParser outXML = new XMLParser();
		outXML.setInputXML(SOAP_outxml_getStatusWiseBenefitResponse);
		
		
		
	
	num =outXML.getNoOfFields("ClaimStatusWiseBenefit");
	StatusWiseBenefit StatusWiseBenefitArray[] = new StatusWiseBenefit[num];
	
	System.out.println("Toatl Number of Status Wise Benefits : "+num);
	for(int i=0;num>0;i++)
	{
		
		String tempClaimStatusWiseBenefit = outXML.getNextValueOf("ClaimStatusWiseBenefit");
	//	log.WriteToLog("Executing Web Service Claim Status Wise Benefit "+ tempClaimStatusWiseBenefit);
		
		System.out.println(" \n "+num+" : Temp Status Wise Benefit : "+ tempClaimStatusWiseBenefit );
		XMLParser XML2 = new XMLParser();
		XML2.setInputXML(tempClaimStatusWiseBenefit);
		
		String Status = XML2.getValueOf("Status");
		System.out.println("\tStatus :" + Status);
		String Reason = XML2.getValueOf("Reason");
		System.out.println("\tReason : "+ Reason);
		String NetClaimAmount = XML2.getValueOf("NetClaimAmount");
		System.out.println("\tNet Claim Amount : "+ NetClaimAmount);
		String Description = XML2.getValueOf("Description");
		System.out.println("\tDescription : "+ Description);
		String Amount =  XML2.getValueOf("Amount");
		System.out.println("\tAmount: "+ Amount);
		
		StatusWiseBenefitArray[i] = new StatusWiseBenefit();
		
		StatusWiseBenefitArray[i].status =Status;
		if(Status.equalsIgnoreCase("Claims Approved"))
		{
			StatusWiseBenefitArray[i].reason="N.A.";
		}
		else
		{
			StatusWiseBenefitArray[i].reason=Reason;
		}
		
		StatusWiseBenefitArray[i].amount=Amount;
		StatusWiseBenefitArray[i].description=Description;
		StatusWiseBenefitArray[i].netClaimAmount=NetClaimAmount;
		
		System.out.println("\t"+num+" Claim Status Wise Benefit Array : "+StatusWiseBenefitArray);
			
		num--;
	}
	System.out.println("Claim Status Wise Benefit Array : "+StatusWiseBenefitArray);
		return StatusWiseBenefitArray;
		
	}
	
	public ArrayList<String> getHospitalClaimDetails()
	{
		
		XMLParser XML = new XMLParser();
	
	String SOAP_inxml_getHospitalClaimsRequest =
						"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cla=\"http://www.idbi.com/elixir/ClaimDetails/\"> "
							+"	<soapenv:Header/>"
							+"		<soapenv:Body>"
							+"			<cla:getHospClaimsRequest>"
							+"				<PolicyNumber>"+getPolicyNumber()+"</PolicyNumber>"
							+"				<NotificationDate></NotificationDate>"
							+"		</cla:getHospClaimsRequest>"
							+"	</soapenv:Body>		"
							+"	</soapenv:Envelope>";	
	//log.WriteToLog("The SOAP_inxml_getHospitalClaimsRequest"+ SOAP_inxml_getHospitalClaimsRequest);
	System.out.println(SOAP_inxml_getHospitalClaimsRequest);
	
	String SOAP_outxml_getHospitalClaimsResponse=executeWS(SOAP_inxml_getHospitalClaimsRequest,EndPointurl);    
	   
	
	System.out.println("SOAP Output XML for getHospitalClaimsResponse : " + SOAP_outxml_getHospitalClaimsResponse);
	//log.WriteToLog("SOAP Output XML for getHospitalClaimsResponse : " + SOAP_outxml_getHospitalClaimsResponse);
	
	XML.setInputXML(SOAP_outxml_getHospitalClaimsResponse);
	ArrayList<String> arrList = new ArrayList<String>();
	arrList.add(XML.getValueOf("MaxLifeCashBen"));
	arrList.add(XML.getValueOf("MaxAnnCashBen"));
	arrList.add(XML.getValueOf("BenPaidLifeTime"));
	arrList.add(XML.getValueOf("BenPaidAnnual"));
	
//	log.WriteToLog("MaxLifeCashBen "+XML.getValueOf("MaxLifeCashBen"));
//	log.WriteToLog("MaxAnnCashBen "+XML.getValueOf("MaxAnnCashBen"));
//	log.WriteToLog("BenPaidLifeTime "+XML.getValueOf("BenPaidLifeTime"));
//	log.WriteToLog("BenPaidAnnual "+XML.getValueOf("BenPaidAnnual"));
//	 


	
	
	//System.out.println("ClaimStatusWiseBenefit "+XML.getValueOf("ClaimStatusWiseBenefit"));
	
	
	
	
//	int totalNumofClaims =XML.getNoOfFields("ClaimStatusWiseBenefit");
//	
//	while(totalNumofClaims>0)
//	{
//		
//		System.out.println("ClaimStatusWiseBenefit :"+totalNumofClaims+" is "+XML.getNextValueOf("ClaimStatusWiseBenefit"));
//		
//		totalNumofClaims--;
//	}
	
//	System.out.println("No of Fields : " +XML.getNoOfFields("ClaimStatusWiseBenefit") );
//	log.WriteToLog("No of Fields : " +XML.getNoOfFields("ClaimStatusWiseBenefit"));
	//System.out.println("Reason "+XML.getValueOf("Reason"));
	//System.out.println("NetClaimAmount"+XML.getValueOf("NetClaimAmount"));
	//return XML.getValueOf("ClaimStatusWiseBenefit");
	return arrList;
	}
	
	public void setClaimNumber (String claimNumber)
	{
		this.claimNumber=claimNumber;
	}
	
	public String getClaimNumber()
	{
		return claimNumber;
	}
	
	public void setPolicyNumber(String policyNumber)
	{		
		this.policyNumber = policyNumber;
	}
	
	public String getPolicyNumber()
	{		
		return policyNumber;
	}
	
	
	
	
	public static String executeWS(String envelope,String endPoint)
	{
		//PrintWriter pout = null;
		String outputString = "";
		URL url = null;
	
		URLConnection connection = null;
		//HttpsURLConnection httpConn = null;
		HttpURLConnection httpConn = null;
		ByteArrayOutputStream bout = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		OutputStream out = null;
		
		try
		{               
			url = new URL(endPoint);
			connection = url.openConnection();
			//httpConn = (HttpsURLConnection)connection;
			httpConn = (HttpURLConnection)connection;
			bout = new ByteArrayOutputStream();
			byte[] buffer = new byte[envelope.length()];
			buffer = envelope.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			System.out.println(httpConn.getErrorStream());
			httpConn.getInstanceFollowRedirects();
			String SOAPAction = "";
			httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			httpConn.setRequestProperty("SOAPAction", SOAPAction);
			httpConn.setRequestMethod("POST");
		    httpConn.setDoOutput(true);
		    httpConn.setDoInput(true);
			out = httpConn.getOutputStream();
			out.write(b);
                       
            out.flush();
			isr = new InputStreamReader(httpConn.getInputStream());
			in = new BufferedReader(isr);
			
			String responseString;
			while ((responseString = in.readLine()) != null)
			{
				outputString = outputString + responseString;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			outputString = "";
			try
			{
				httpConn.disconnect();
				bout.close();
				isr.close();
				in.close();
				out.close();
			}
			catch (Exception localException1)
			{
				localException1.printStackTrace();
			}
		}
		finally
		{
			try
			{
				httpConn.disconnect();
				bout.close();
				isr.close();
				in.close();
				out.close();
			}
			catch (Exception localException2)
			{
			}
		}
		return outputString;
	}		
  
} 


