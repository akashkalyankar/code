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
	Problem No.		Changed Date 	Changed By 			Description
				
					05/11/2018		Akash Kalynakar		code added to fetch related policies and product details
					14/11/2018		Akash Kalynakar		with Type of surgery modification
					15/11/2018		Akash Kalynakar		date of event modification
					16/11/2018		Akash Kalynakar		Is loan Covered Modification
					19/11/2018		Akash Kalynakar		with Loansurance Modification
					20/11/2018		Akash Kalynakar		with Suicide Modification			
					26/11/2018		Akash Kalynakar		with Grid Changes and WOP Premium Hiding
					27/11/2018		Akash Kalynakar		No Requirement Date	
					03/12/2018		Akash Kalynakar		Value Changed Final Code resolve save form started issue
					04/12/2018		Akash Kalynakar		With NAVDATE Hiding (EOD)
					05/12/2018		Akash Kalynakar		With Or Condition of Reinstatement handling java parse error
					06/12/2018		Akash Kalynakar		Update Client Details Grid Query,Update Client Nominee Frame
					11/12/2018		Akash Kalynakar 	document grid population change,Try catch in RCD Date
					13/12/2018		Akash Kalynakar 	Some Changes in Date Population
					17/12/2018		Akash Kalynakar 	In Hospitalization frame time from and to combos added
					20/12/2018		Akash Kalynakar 	On clicking proceed the frame should be hidden changed
					21/12/2018		Akash Kalynakar 	throwing java exception while parsing Date of Reinstatement
					09/01/2019		Akash Kalynakar		combo added in form for Rejection Reason
			
----------------------------------------------------------------------------------------------------------------------------

	Functions with there usages


---------------------------------------------------------------------------------------------------------------------------*/

package com.newgen.omniforms.user;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

import com.newgen.omniforms.FormConfig;
import com.newgen.omniforms.FormReference;
import com.newgen.omniforms.component.ListView;
import com.newgen.omniforms.component.PickList;
import com.newgen.omniforms.context.FormContext;
import com.newgen.omniforms.event.ComponentEvent;
import com.newgen.omniforms.event.FormEvent;
import com.newgen.omniforms.listener.FormListener;

public class NG_CLM_ClaimEventIntimation implements FormListener {
	NG_CLM_Logger log;
	int claimOnComboCount=0;
	int countRelatedPolicy=0;
	int counthospital=0;
	Date currentDate = new Date();
	Date hospitalizedAdmissionDate = null ;
	Date hospitalizedDischargeDate=null;
	Date ICUDischarge=null;
	Date AssessorFrom=null ;
	Date AssessorTo=null;
	Date ICUAdmission=null;
	
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
	
	@Override
	public void continueExecution(String arg0, HashMap<String, String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventDispatched(ComponentEvent pEvent) throws ValidatorException {
		// TODO Auto-generated method stub
		FormReference formObject = FormContext.getCurrentInstance().getFormReference();
		formObject.getWFWorkitemName();
		log = new NG_CLM_Logger(formObject.getWFWorkitemName());
	//	log.WriteToLog("In Event Dispatched");
		String WI_NAME = formObject.getWFWorkitemName();
//		if(counthospital==0)
//		{
//		
//		
//		try {
//			currentDate = formatter.parse(formatter.format(currentDate));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			log.WriteToLog("Java Exception in Current Date : "+e.toString());
//		}
//		currentDate.setHours(0);
//		currentDate.setMinutes(0);
//		currentDate.setSeconds(0);
//		
//		
//		try {
//
//			if(!formObject.getNGValue("HOSP_ADMISSION_DATE").equalsIgnoreCase(""))
//			{
//				hospitalizedAdmissionDate = formatter.parse(formObject.getNGValue("HOSP_ADMISSION_DATE"));
//
//			}
//			if(!formObject.getNGValue("HOSP_DISCHARGE_DATE").equalsIgnoreCase(""))
//			{
//				hospitalizedDischargeDate = formatter.parse(formObject.getNGValue("HOSP_DISCHARGE_DATE"));
//			}
//			if(!formObject.getNGValue("ICU_ADMISSION").equalsIgnoreCase(""))
//			{
//				ICUAdmission = formatter.parse(formObject.getNGValue("ICU_ADMISSION"));
//			}
//			if(!formObject.getNGValue("ICU_DISCHARGE").equalsIgnoreCase(""))
//			{
//				ICUDischarge = formatter.parse(formObject.getNGValue("ICU_DISCHARGE"));
//			}
//			
//			if(!formObject.getNGValue("ASSESSOR_FROM").equalsIgnoreCase(""))
//			{
//				AssessorFrom = formatter.parse(formObject.getNGValue("ASSESSOR_FROM"));
//			}
//			if(!formObject.getNGValue("ASSESSOR_TO").equalsIgnoreCase(""))
//			{
//				AssessorTo = formatter.parse(formObject.getNGValue("ASSESSOR_TO"));
//			}
//			
//
//			
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			log.WriteToLog("Java Excetption in Hospital Claim Frame "+e1.toString());
//		} 
//		counthospital++;
//		}
		
		switch(pEvent.getType())

		{
		
			case MOUSE_CLICKED:
			{
				if(pEvent.getSource().getName().equalsIgnoreCase("Save"))
					
				{
					formObject.RaiseEvent("WFSave",true);
					
					
					//formObject.RaiseEvent("WFDone", true);

					log.WriteToLog("Before Save");
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim"))
					{
						log.WriteToLog("In Death Claim Frame :" );
						
						//formObject.setNGValue("Text21","DC Check");
						String DC_NAVDate = formObject.getNGValue("DatePicker4");
						if(DC_NAVDate.equalsIgnoreCase(""))
						{
							DC_NAVDate=null;
						}
						else
						{
							DC_NAVDate=" '"+DC_NAVDate+"' ";	
						}	
						String DC_ClaimOn = formObject.getNGValue("Combo12");
						String DC_DateOfDeath = formObject.getNGValue("DatePicker5");
						if(DC_DateOfDeath.equalsIgnoreCase(""))
						{
							DC_DateOfDeath = null;
						}
						else
						{
							DC_DateOfDeath = " '"+DC_DateOfDeath+"'";
						}
						
					
						String DC_ClaimValid = formObject.getNGValue("DatePicker6");
						
						if(DC_ClaimValid.equalsIgnoreCase(""))
						{
							DC_ClaimValid=null;
						}
						else
						{
							DC_ClaimValid=" '"+DC_ClaimValid+"' ";
						}	
						String DC_NatureOfDeath = formObject.getNGValue("Combo4");
						String DC_Suicide = formObject.getNGValue("Option1");
						String DC_PrimaryCause = formObject.getNGValue("Combo27");
						String DC_IsLoanCovered = formObject.getNGValue("Combo1");
						String DC_ClaimIntimationSource = formObject.getNGValue("Combo24");
						String DC_CustomerSign = formObject.getNGValue("Combo30");
						String DC_DeathCertificateFrom = formObject.getNGValue("Combo25");
						String DC_DeathCerificateType = formObject.getNGValue("Combo31");
						String DC_RegNumOfDeathCert = formObject.getNGValue("Text91");
						String DC_InsuranceDateDeath = formObject.getNGValue("DatePicker38");
						if(DC_InsuranceDateDeath.equalsIgnoreCase(""))
						{
							DC_InsuranceDateDeath=null;
						}
						else
						{
							DC_InsuranceDateDeath =" '"+DC_InsuranceDateDeath+"' ";	
						}	
						String DC_CerifyingDoctor = formObject.getNGValue("Text95");
						String DC_PrimaryHealthStmt = formObject.getNGValue("Combo33");
						String DC_ExactCauseDeath = formObject.getNGValue("Combo28");
						String DC_UnderlyingCauseDeath = formObject.getNGValue("Text30");
						String DC_Agent = formObject.getNGValue("Combo29");
						String DC_DeathLocation = formObject.getNGValue("Combo26");
						String DC_HosptitalName = formObject.getNGValue("Text92");
						String DC_HospitalAdmission = formObject.getNGValue("DatePicker40");
						if(DC_HospitalAdmission.equalsIgnoreCase(""))
						{
							DC_HospitalAdmission=null;
						}
						else
						{
							DC_HospitalAdmission = " '"+DC_HospitalAdmission+"' ";
						}	
						
						String DC_HospitalDischarge = formObject.getNGValue("DatePicker39");
						
						if(DC_HospitalDischarge.equalsIgnoreCase(""))
						{
							DC_HospitalDischarge=null;
						}
						else
						{
							DC_HospitalDischarge=" '"+ DC_HospitalDischarge +"' ";
						}	
						String DC_ICCode1=formObject.getNGValue("Text12");
						String DC_ICCode2=formObject.getNGValue("Text13");
						String DC_ICCode3=formObject.getNGValue("Text23");
						String DC_ICCode4=formObject.getNGValue("Text24");
						String DC_ICCode5=formObject.getNGValue("Text26");
						String DC_ICCode6=formObject.getNGValue("Text25");
						
						String ClaimOnIdQuery= "Select CLAIM_ON_ID from NG_CLM_ClaimOnID_Master WITH(NOLOCK) where CLAIM_ON='"+DC_ClaimOn+"' and WI_NAME = '"+WI_NAME+"'";  
						log.WriteToLog("Claim ON ID Query"+ClaimOnIdQuery);
						
						List<List<String>> ClaimOnIdResult = formObject.getDataFromDataSource(ClaimOnIdQuery);
						log.WriteToLog("Claim On Id Result : "+ClaimOnIdResult);
						String claimOnId ="";
						
						if(!ClaimOnIdResult.isEmpty())
						{
							claimOnId = ClaimOnIdResult.get(0).get(0);
						}
						
						log.WriteToLog("Claim ON id : "+claimOnId);	
						
						String Query = "update NG_CLM_Ext_Table set NAV_DATE = "+DC_NAVDate
								+" ,CLM_ON = '"+DC_ClaimOn									+"' ,claim_on_id = '"+claimOnId		
								+"' ,DATE_OF_DEATH = "+DC_DateOfDeath						+", CLM_VALID_NOTIF_DATE = "+DC_ClaimValid					
								+", NATURE_OF_DEATH = '"+DC_NatureOfDeath				
								+"' ,SUICIDE = '"+DC_Suicide								+"' ,PRI_CAUSE_OF_DEATH = '"+DC_PrimaryCause			
								+"' ,IS_LOAN_COVERED = '"+DC_IsLoanCovered					+"' ,CLM_INTIMATION_SRC = '"+DC_ClaimIntimationSource		
								+"' ,CUSTOMER_SIGN = '"+DC_CustomerSign						+"' ,DEATH_CER_ISSUED_FROM = '"+DC_DeathCertificateFrom
								+"' ,DEATH_CER_ISSUED_TYPE = '"+DC_DeathCerificateType		+"' ,REG_NUM_OF_DEATH_CER = '"+DC_RegNumOfDeathCert	
								+"' ,DATE_OF_DEATH_CER = "+DC_InsuranceDateDeath			+" ,CERTIFYING_DOCTOR = '"+DC_CerifyingDoctor			
								+"' ,PRI_HEALTH_STMT = '"+DC_PrimaryHealthStmt				+"' ,EXACT_CAUSE_OF_DEATH = '"+DC_ExactCauseDeath		
								+"' ,UNDERLYING_DEATH_CAUSE = '"+DC_UnderlyingCauseDeath	+"' ,AGENT = '"+DC_Agent			
								+"' ,DEATH_LOCATION = '"+DC_DeathLocation					+"' ,HOSP_NAME = '"+DC_HosptitalName			
								+"' ,HOSP_ADMISSION_DATE = "+DC_HospitalAdmission			+" ,HOSP_DISCHARGE_DATE = "+DC_HospitalDischarge
								+"  ,IC_CODE_1 = '"+DC_ICCode1								+"' ,IC_CODE_2 = '"+DC_ICCode2		
								+"' ,IC_CODE_3 = '"+DC_ICCode3								+"' ,IC_CODE_4 = '"+DC_ICCode4		
								+"' ,IC_CODE_5 = '"+DC_ICCode5								+"' ,IC_CODE_6 = '"+DC_ICCode6	
								+"' ,DATE_OF_EVENT = "+ DC_DateOfDeath						+" ,CAUSE_OF_EVENT = '"+DC_PrimaryCause
								+"' where  WI_NAME = '"+WI_NAME+"'";
						
					log.WriteToLog("In Death Claim Frame Update Query : "+Query);
					
						//formObject.saveDataIntoDataSource("update NG_CLM_Ext_Table set IC_CODE_6 ='dc6'" );
						formObject.saveDataIntoDataSource(Query);
					}
					
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Accidental Death Claim"))
					{
						log.WriteToLog("In Accidental Death Claim Frame : ");
						String ACD_ICCode1=formObject.getNGValue("Text34");
						String ACD_ICCode2=formObject.getNGValue("Text35");
						String ACD_ICCode3=formObject.getNGValue("Text39");
						String ACD_ICCode4=formObject.getNGValue("Text40");
						String ACD_ICCode5=formObject.getNGValue("Text42");
						String ACD_ICCode6=formObject.getNGValue("Text41");
						String ACD_Suicide = formObject.getNGValue("Option4");
						String ACD_NAVDate = formObject.getNGValue("DatePicker11");
						
						if(ACD_NAVDate.equalsIgnoreCase(""))
						{
							ACD_NAVDate=null;
							
						}
						else
						{
							ACD_NAVDate= " '"+ ACD_NAVDate +"' ";
						}	
					
						String ACD_ClaimOn = formObject.getNGValue("Combo70");
						
						String ACD_DateOfDeath = formObject.getNGValue("DatePicker12");
						if(ACD_DateOfDeath.equalsIgnoreCase(""))
						{
							ACD_DateOfDeath=null;
						}
						else
						{
							ACD_DateOfDeath = " '"+ACD_DateOfDeath +"' ";
						}	
						
						String ACD_ClaimValid = formObject.getNGValue("DatePicker13");
					
						
						if(ACD_ClaimValid.equalsIgnoreCase(""))
						{
							ACD_ClaimValid=null;
						}
						else
						{
							ACD_ClaimValid = " '"+ACD_ClaimValid+"' ";
						}
						
						
						String ACD_NatureOfDeath = formObject.getNGValue("Text36");
						
						String ACD_DateOfAccident = formObject.getNGValue("DATE_OF_ACCIDENT");
						
						if(ACD_DateOfAccident.equalsIgnoreCase(""))
						{
							ACD_DateOfAccident = null;
						}
						else
						{
							ACD_DateOfAccident = " '"+ACD_DateOfAccident+"' ";
						}
						
						String ACD_ClaimIntimation = formObject.getNGValue("Combo46");
						String ACD_CustomerSign = formObject.getNGValue("Combo50");		
						String ACD_DeathCertificateIssued = formObject.getNGValue("Combo47");
						String ACD_DeathCertificateType = formObject.getNGValue("Combo51");
						String ACD_RegistrationNumberDeath = formObject.getNGValue("Text43");
						String ACD_InsuranceDateDeath = formObject.getNGValue("DatePicker14");
						
						if(ACD_InsuranceDateDeath.equalsIgnoreCase(""))
						{
							ACD_InsuranceDateDeath = null;
						}
						else
						{
							ACD_InsuranceDateDeath = " '"+ACD_InsuranceDateDeath+"' ";
						}
						String ACD_CerifyingDoctor = formObject.getNGValue("Text100");
						String ACD_PrimaryHealthStmt = formObject.getNGValue("Combo52");
						String ACD_ExactCauseDeath = formObject.getNGValue("Combo48");
						String ACD_UnderlyingCauseDeath = formObject.getNGValue("Text32");
						String ACD_Agent = formObject.getNGValue("Combo49");
						String ACD_DeathLocation = formObject.getNGValue("Combo54");
						String ACD_HosptitalName = formObject.getNGValue("Text99");
						String ACD_HospitalAdmission = formObject.getNGValue("DatePicker45");
						
						if(ACD_HospitalAdmission.equalsIgnoreCase(""))
						{
							ACD_HospitalAdmission=null;
						}
						else
						{
							ACD_HospitalAdmission = " '"+ACD_HospitalAdmission + "' ";
						}	
						
						String ACD_HospitalDischarge = formObject.getNGValue("DatePicker44");
						
						if(ACD_HospitalDischarge.equalsIgnoreCase(""))
						{
							ACD_HospitalDischarge=null;
						}
						else
						{
							ACD_HospitalDischarge = " '"+ACD_HospitalDischarge +"' ";
						}	
						//IF ACD_Suicide is true then Suicide is Yes
					
						String ClaimOnIdQuery= "Select CLAIM_ON_ID from NG_CLM_ClaimOnID_Master WITH(NOLOCK) where CLAIM_ON='"+ACD_ClaimOn+"' and WI_NAME = '"+WI_NAME+"'";  
						log.WriteToLog("Claim ON ID Query"+ClaimOnIdQuery);
						
						List<List<String>> ClaimOnIdResult = formObject.getDataFromDataSource(ClaimOnIdQuery);
						log.WriteToLog("Claim On Id Result : "+ClaimOnIdResult);
						String claimOnId ="";
						
						if(!ClaimOnIdResult.isEmpty())
						{
							claimOnId = ClaimOnIdResult.get(0).get(0);
						}
						
						
						String Query = "update NG_CLM_Ext_Table set IC_CODE_1 = '"+ACD_ICCode1
								+"' ,claim_on_id = '"+claimOnId		+"' , IC_CODE_2 = '"+ACD_ICCode2	+"',IC_CODE_3 = '"+ACD_ICCode3
								+"' ,IC_CODE_4 = '"+ACD_ICCode4		+"' ,IC_CODE_5 = '"+ACD_ICCode5
								+"' ,IC_CODE_6 = '"+ACD_ICCode6		+"',SUICIDE = '"+ACD_Suicide
								+"' ,NAV_DATE = "+ ACD_NAVDate 									+" ,CLM_ON = '"+ACD_ClaimOn
								+"' ,CLM_VALID_NOTIF_DATE = "+ACD_ClaimValid					+" ,NATURE_OF_DEATH = '"+ACD_NatureOfDeath
								+"' ,DATE_OF_DEATH = "+ACD_DateOfDeath							+" ,CLM_INTIMATION_SRC = '"+ACD_ClaimIntimation		+"' ,CUSTOMER_SIGN = '"+ACD_CustomerSign	
								+"' ,DEATH_CER_ISSUED_FROM = '"+ACD_DeathCertificateIssued		+"' ,DEATH_CER_ISSUED_TYPE = '"+ACD_DeathCertificateType	
								+"' ,REG_NUM_OF_DEATH_CER = '"+ACD_RegistrationNumberDeath		+"' ,DATE_OF_DEATH_CER = "+ACD_InsuranceDateDeath	
								+" ,CERTIFYING_DOCTOR = '"+ACD_CerifyingDoctor					+"' ,PRI_HEALTH_STMT = '"+ACD_PrimaryHealthStmt	
								+"' ,EXACT_CAUSE_OF_DEATH = '"+ACD_ExactCauseDeath				+"' ,UNDERLYING_DEATH_CAUSE = '"+ACD_UnderlyingCauseDeath	
								+"' ,AGENT = '"+ACD_Agent										+"' ,DEATH_LOCATION = '"+ACD_DeathLocation	
								+"' ,HOSP_NAME = '"+ACD_HosptitalName							+"' ,HOSP_ADMISSION_DATE = "+ACD_HospitalAdmission	
								+" ,HOSP_DISCHARGE_DATE = "+ACD_HospitalDischarge				+" ,DATE_OF_EVENT = "+ ACD_DateOfAccident						
								+" ,CAUSE_OF_EVENT = '"+ACD_NatureOfDeath						+"' where  WI_NAME = '"+WI_NAME+"'";	
						log.WriteToLog("In Accidental Death Claim Frame Update Query : "+Query);
					
						formObject.saveDataIntoDataSource(Query);
					}
					
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim Waiver of Premium"))
					{
						log.WriteToLog("In Death Claim Waiver of Premium Frame");
						String DCW_NAVDate = formObject.getNGValue("DatePicker43");
						if(DCW_NAVDate.equalsIgnoreCase(""))
						{
							DCW_NAVDate=null;
						}
						else
						{
							DCW_NAVDate =" '"+DCW_NAVDate+"' ";
						}
						
						String DCW_ClaimOn = formObject.getNGValue("Combo69");
						String DCW_DateOfDeath = formObject.getNGValue("DatePicker42");
						if(DCW_DateOfDeath.equalsIgnoreCase(""))
						{
							DCW_DateOfDeath=null;
						}
						else
						{
							DCW_DateOfDeath = " '"+DCW_DateOfDeath+"' ";
						}	
						String DCW_ClaimValid = formObject.getNGValue("DatePicker41");
						if(DCW_ClaimValid.equalsIgnoreCase(""))
						{
							DCW_ClaimValid=null;
						}
						else
						{
							DCW_ClaimValid = " '"+DCW_ClaimValid+"' ";
						}	
						String DCW_NatureOfDeath = formObject.getNGValue("Combo5");
						String DCW_Suicide = formObject.getNGValue("Option3");
						String DCW_PrimaryCause = formObject.getNGValue("Combo32");
						String DCW_IsLoanCovered = formObject.getNGValue("Combo44");
						String DCW_ClaimIntimationSource = formObject.getNGValue("Combo43");
						String DCW_CustomerSign = formObject.getNGValue("Combo39");
						String DCW_DeathCertificateFrom = formObject.getNGValue("Combo42");
						String DCW_DeathCerificateType = formObject.getNGValue("Combo38");
						String DCW_RegNumOfDeathCert = formObject.getNGValue("Text19");
						String DCW_InsuranceDateDeath = formObject.getNGValue("DatePicker10");
						if(DCW_InsuranceDateDeath.equalsIgnoreCase(""))
						{
							DCW_InsuranceDateDeath=null;
						}
						else
						{
							DCW_InsuranceDateDeath = " '"+DCW_InsuranceDateDeath +"' ";
						}	
						String DCW_CerifyingDoctor = formObject.getNGValue("Text15");
						String DCW_PrimaryHealthStmt = formObject.getNGValue("Combo37");
						String DCW_ExactCauseDeath = formObject.getNGValue("Combo41");
						String DCW_UnderlyingCauseDeath = formObject.getNGValue("Text14");
						String DCW_Agent = formObject.getNGValue("Combo40");
						String DCW_DeathLocation = formObject.getNGValue("Combo35");
						String DCW_HosptitalName = formObject.getNGValue("Text17");
						String DCW_HospitalAdmission = formObject.getNGValue("DatePicker8");
						if(DCW_HospitalAdmission.equalsIgnoreCase(""))
						{
							DCW_HospitalAdmission=null;
						}
						else
						{
							DCW_HospitalAdmission = " '"+DCW_HospitalAdmission+"' ";
						}	
						String DCW_HospitalDischarge = formObject.getNGValue("DatePicker9");
						
						if(DCW_HospitalDischarge.equalsIgnoreCase(""))
						{
							DCW_HospitalDischarge=null;
						}
						else
						{
							DCW_HospitalDischarge = " '"+DCW_HospitalDischarge+"' ";
						}	
						String DCW_ICCode1=formObject.getNGValue("Text98");
						String DCW_ICCode2=formObject.getNGValue("Text33");
						String DCW_ICCode3=formObject.getNGValue("Text29");
						String DCW_ICCode4=formObject.getNGValue("Text28");
						String DCW_ICCode5=formObject.getNGValue("Text20");
						String DCW_ICCode6=formObject.getNGValue("Text27");
						
						String ClaimOnIdQuery= "Select CLAIM_ON_ID from NG_CLM_ClaimOnID_Master WITH(NOLOCK) where CLAIM_ON='"+DCW_ClaimOn+"' and WI_NAME = '"+WI_NAME+"'";  
						log.WriteToLog("Claim ON ID Query"+ClaimOnIdQuery);
						
						List<List<String>> ClaimOnIdResult = formObject.getDataFromDataSource(ClaimOnIdQuery);
						log.WriteToLog("Claim On Id Result : "+ClaimOnIdResult);
						String claimOnId ="";
						
						if(!ClaimOnIdResult.isEmpty())
						{
							claimOnId = ClaimOnIdResult.get(0).get(0);
						}
						
						

						String Query = "update NG_CLM_Ext_Table set NAV_DATE = "+DCW_NAVDate
								+" ,CLM_ON = '"+DCW_ClaimOn								+"' ,claim_on_id = '"+claimOnId		
								+"' ,DATE_OF_DEATH = "+DCW_DateOfDeath					
								+" ,CLM_VALID_NOTIF_DATE = "+DCW_ClaimValid				+" ,NATURE_OF_DEATH = '"+DCW_NatureOfDeath				
								+"' ,SUICIDE = '"+DCW_Suicide								+"' ,PRI_CAUSE_OF_DEATH = '"+DCW_PrimaryCause			
								+"' ,IS_LOAN_COVERED = '"+DCW_IsLoanCovered					+"' ,CLM_INTIMATION_SRC = '"+DCW_ClaimIntimationSource		
								+"' ,CUSTOMER_SIGN = '"+DCW_CustomerSign					+"' ,DEATH_CER_ISSUED_FROM = '"+DCW_DeathCertificateFrom
								+"' ,DEATH_CER_ISSUED_TYPE = '"+DCW_DeathCerificateType		+"' ,REG_NUM_OF_DEATH_CER = '"+DCW_RegNumOfDeathCert	
								+"' ,DATE_OF_DEATH_CER = "+DCW_InsuranceDateDeath			+" ,CERTIFYING_DOCTOR = '"+DCW_CerifyingDoctor			
								+"' ,PRI_HEALTH_STMT = '"+DCW_PrimaryHealthStmt				+"' ,EXACT_CAUSE_OF_DEATH = '"+DCW_ExactCauseDeath		
								+"' ,UNDERLYING_DEATH_CAUSE = '"+DCW_UnderlyingCauseDeath	+"' ,AGENT = '"+DCW_Agent			
								+"' ,DEATH_LOCATION = '"+DCW_DeathLocation					+"' ,HOSP_NAME = '"+DCW_HosptitalName			
								+"' ,HOSP_ADMISSION_DATE = "+DCW_HospitalAdmission			+" ,HOSP_DISCHARGE_DATE = "+DCW_HospitalDischarge
								+"  ,IC_CODE_1 = '"+DCW_ICCode1								+"' ,IC_CODE_2 = '"+DCW_ICCode2		
								+"' ,IC_CODE_3 = '"+DCW_ICCode3								+"' ,IC_CODE_4 = '"+DCW_ICCode4		
								+"' ,IC_CODE_5 = '"+DCW_ICCode5								+"' ,IC_CODE_6 = '"+DCW_ICCode6	
								+"' ,DATE_OF_EVENT = "+ DCW_DateOfDeath						+" ,CAUSE_OF_EVENT = '"+DCW_PrimaryCause
								+"'  where WI_NAME = '"+WI_NAME+"'";
						log.WriteToLog("In Death Claim Waiver of Premium Frame Update Query "+ Query);
						
						formObject.saveDataIntoDataSource(Query);
					}
					
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Critical Illness/Dreaded Disease Claim"))
					{
						
						
						log.WriteToLog("Critical Illness/Dreaded Disease Claim : ");
						String CIC_ClaimOn = formObject.getNGValue("Combo71");
						String CIC_ClaimValid = formObject.getNGValue("DatePicker16");
						if(CIC_ClaimValid.equalsIgnoreCase(""))
						{
							CIC_ClaimValid=null;
						}
						else
						{
							CIC_ClaimValid = " '"+CIC_ClaimValid+"' ";
						}	
						
						String CIC_MajorDiseaseType = formObject.getNGValue("MAJOR_DISEASE_TYPE");
						
						String CIC_ClaimIntimationSource = formObject.getNGValue("Combo57");
						String CIC_DateOfDiagnosis = formObject.getNGValue("DATE_OF_DIAGNOSIS");
						if(CIC_DateOfDiagnosis.equalsIgnoreCase(""))
						{
							CIC_DateOfDiagnosis=null;
						}
						else
						{
							CIC_DateOfDiagnosis = " '"+CIC_DateOfDiagnosis+"' ";
						}
						String CIC_CustomerSign = formObject.getNGValue("Combo56");
						String CIC_Agent = formObject.getNGValue("Combo45");
						String CIC_PrimaryHealthStmt = formObject.getNGValue("Combo55");
						String CIC_HosptitalName = formObject.getNGValue("Text101");
						String CIC_HospitalAdmission = formObject.getNGValue("DatePicker48");
						
						
						if(CIC_HospitalAdmission.equalsIgnoreCase(""))
						{
							CIC_HospitalAdmission=null;
						}
						else
						{
							CIC_HospitalAdmission = " '"+CIC_HospitalAdmission+"' ";
						}	
						String CIC_HospitalDischarge = formObject.getNGValue("DatePicker47");
						if(CIC_HospitalDischarge.equalsIgnoreCase(""))
						{
							CIC_HospitalDischarge=null;
						}
						else
						{
							CIC_HospitalDischarge = " '"+CIC_HospitalDischarge+"' ";
						}	

						String CIC_ICCode1=formObject.getNGValue("Text53");
						String CIC_ICCode2=formObject.getNGValue("Text52");
						String CIC_ICCode3=formObject.getNGValue("Text47");
						String CIC_ICCode4=formObject.getNGValue("Text46");
						String CIC_ICCode5=formObject.getNGValue("Text44");
						String CIC_ICCode6=formObject.getNGValue("Text45");
						
						String ClaimOnIdQuery= "Select CLAIM_ON_ID from NG_CLM_ClaimOnID_Master WITH(NOLOCK) where CLAIM_ON='"+CIC_ClaimOn+"' and WI_NAME = '"+WI_NAME+"'";  
						log.WriteToLog("Claim ON ID Query"+ClaimOnIdQuery);
						
						List<List<String>> ClaimOnIdResult = formObject.getDataFromDataSource(ClaimOnIdQuery);
						log.WriteToLog("Claim On Id Result : "+ClaimOnIdResult);
						String claimOnId ="";
						
						if(!ClaimOnIdResult.isEmpty())
						{
							claimOnId = ClaimOnIdResult.get(0).get(0);
						}
						
						
						String Query = "update NG_CLM_Ext_Table set CLM_ON = '"+CIC_ClaimOn	+"' ,claim_on_id = '"+claimOnId	
								+"' ,CLM_VALID_NOTIF_DATE = "+CIC_ClaimValid				+",CLM_INTIMATION_SRC = '"+CIC_ClaimIntimationSource		
								+"' ,CUSTOMER_SIGN = '"+CIC_CustomerSign	
								+"',AGENT = '"+CIC_Agent									+"' ,PRI_HEALTH_STMT = '"+CIC_PrimaryHealthStmt	
								+"',HOSP_NAME = '"+CIC_HosptitalName						+"' ,HOSP_ADMISSION_DATE = "+CIC_HospitalAdmission	
								+",HOSP_DISCHARGE_DATE = "+CIC_HospitalDischarge			+"	,IC_CODE_1 = '"+CIC_ICCode1
								+"',IC_CODE_2 = '"+CIC_ICCode2								+"'	,IC_CODE_3 = '"+CIC_ICCode3
								+"',IC_CODE_4 = '"+CIC_ICCode4								+"' ,IC_CODE_5 = '"+CIC_ICCode5
								+"',IC_CODE_6 = '"+CIC_ICCode6								+"', DATE_OF_EVENT = "+CIC_DateOfDiagnosis					
								+",CAUSE_OF_EVENT = '"+	CIC_MajorDiseaseType		
								+"' where WI_NAME = '"+WI_NAME+"'";
						log.WriteToLog("Critical Illness/Dreaded Disease Claim Frame Update Query: "+Query);
						
						formObject.saveDataIntoDataSource(Query);
					}
					
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Hospitalization Claim"))
					{
						
							log.WriteToLog("In Hospitalization Claim Frame ");
							String HC_ClaimOn = formObject.getNGValue("Combo72");
							String HC_ClaimValid = formObject.getNGValue("DatePicker23");
							
							if(HC_ClaimValid.equalsIgnoreCase(""))
							{
								HC_ClaimValid=null;
							}
							else
							{
								HC_ClaimValid = " '"+HC_ClaimValid+"' ";
							}	
							
							String HC_ClaimIntimationSource = formObject.getNGValue("Combo75");
							String HC_CustomerSign = formObject.getNGValue("Combo53");
							String HC_Agent = formObject.getNGValue("Combo34");
							String HC_PrimaryHealthStmt = formObject.getNGValue("Combo36");
							
							
							String HC_HospitalAdmission = formObject.getNGValue("HOSP_ADMISSION_DATE");
							if(HC_HospitalAdmission.equalsIgnoreCase(""))
							{
								HC_HospitalAdmission=null;
								
							}
							else
							{
								String HA_Hour = formObject.getNGValue("HA_Hour");
								String HA_Minute = formObject.getNGValue("HA_Minute");
								HC_HospitalAdmission = " '"+HC_HospitalAdmission + ' '+ HA_Hour + ':' + HA_Minute+"' ";
								//HC_HospitalAdmission = " '"+HC_HospitalAdmission+"' ";
							}	
							String HC_HospitalDischarge = formObject.getNGValue("HOSP_DISCHARGE_DATE");
							if(HC_HospitalDischarge.equalsIgnoreCase(""))
							{
								HC_HospitalDischarge=null;
							}
							else
							{
								String HD_Hour = formObject.getNGValue("HD_Hour");
								String HD_Minute = formObject.getNGValue("HD_Minute");
								HC_HospitalDischarge = " '"+HC_HospitalDischarge + ' '+ HD_Hour + ':'+HD_Minute+"' ";
								//HC_HospitalDischarge = " '"+HC_HospitalDischarge+"' ";
							}
							
							String HC_ICU_Admission = formObject.getNGValue("ICU_ADMISSION");
							if(HC_ICU_Admission.equalsIgnoreCase(""))
							{
								HC_ICU_Admission=null;
							}
							else
							{
								String IA_Hour= formObject.getNGValue("IA_Hour");
								String IA_Minute= formObject.getNGValue("IA_Minute");
								HC_ICU_Admission = " '"+HC_ICU_Admission + ' '+ IA_Hour + ':' + IA_Minute+"' ";
								//HC_ICU_Admission = " '"+HC_ICU_Admission+"' ";
							}
							
							String HC_ICU_Discharge = formObject.getNGValue("ICU_DISCHARGE");
							
							if(HC_ICU_Discharge.equalsIgnoreCase(""))
							{
								HC_ICU_Discharge=null;
							}
							else
							{
								String ID_Hour = formObject.getNGValue("ID_Hour");
								String ID_Minute = formObject.getNGValue("ID_Minute");
								HC_ICU_Discharge = " '"+HC_ICU_Discharge + ' ' + ID_Hour +':' + ID_Minute+"' ";
								
								//HC_ICU_Discharge = " '"+HC_ICU_Discharge+"' ";
							}
							
							String HC_CauseOfIllenss = formObject.getNGValue("CAUSE_OF_ILLNESS");
							
							String HC_ICCode1=formObject.getNGValue("Text50");
							String HC_ICCode2=formObject.getNGValue("Text51");
							String HC_ICCode3=formObject.getNGValue("Text56");
							String HC_ICCode4=formObject.getNGValue("Text57");
							String HC_ICCode5=formObject.getNGValue("Text59");
							String HC_ICCode6=formObject.getNGValue("Text58");
							
							String ClaimOnIdQuery= "Select CLAIM_ON_ID from NG_CLM_ClaimOnID_Master WITH(NOLOCK) where CLAIM_ON='"+HC_ClaimOn+"' and WI_NAME = '"+WI_NAME+"'";  
							log.WriteToLog("Claim ON ID Query"+ClaimOnIdQuery);
							
							List<List<String>> ClaimOnIdResult = formObject.getDataFromDataSource(ClaimOnIdQuery);
							log.WriteToLog("Claim On Id Result : "+ClaimOnIdResult);
							String claimOnId ="";
							
							if(!ClaimOnIdResult.isEmpty())
							{
								claimOnId = ClaimOnIdResult.get(0).get(0);
							}
							
						String Query = "update NG_CLM_Ext_Table set CLM_ON = '"+HC_ClaimOn 
								+"' ,claim_on_id = '"+claimOnId	
								+"',CLM_VALID_NOTIF_DATE = "+HC_ClaimValid				+" ,HOSP_ADMISSION_DATE = "+HC_HospitalAdmission			
								+",CLM_INTIMATION_SRC = '"+HC_ClaimIntimationSource		+"' ,CUSTOMER_SIGN = '"+HC_CustomerSign	
								+"',AGENT = '"+HC_Agent									+"' ,PRI_HEALTH_STMT = '"+HC_PrimaryHealthStmt	
								+"',HOSP_DISCHARGE_DATE = "+HC_HospitalDischarge		+",ICU_ADMISSION = "+HC_ICU_Admission
								+",ICU_DISCHARGE = "+HC_ICU_Discharge					+",IC_CODE_1 = '"+HC_ICCode1			
								+"',IC_CODE_2 = '"+HC_ICCode2							+"',IC_CODE_3 = '"+HC_ICCode3			
								+"',IC_CODE_4 = '"+HC_ICCode4							+"',IC_CODE_5 = '"+HC_ICCode5			
								+"',IC_CODE_6 = '"+HC_ICCode6							+"', DATE_OF_EVENT = "+HC_ClaimValid						
								+",CAUSE_OF_EVENT = '"+	HC_CauseOfIllenss		
								+"' where  WI_NAME = '"+WI_NAME+"'";
						log.WriteToLog("In Hospitalization Claim Frame Update Query"+Query);
						
						formObject.saveDataIntoDataSource(Query);
					}
					
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("TPDB Claim"))
					{
					
						log.WriteToLog("In TPDB Claim Frame" );
						String TPDB_NAVDate = formObject.getNGValue("DatePicker24");
						if(TPDB_NAVDate.equalsIgnoreCase(""))
						{
							TPDB_NAVDate=null;
						}
						else
						{
							TPDB_NAVDate = " '"+TPDB_NAVDate+"' ";
						}	
						String TPDB_ClaimOn = formObject.getNGValue("Combo73");
						String TPDB_ClaimValid = formObject.getNGValue("DatePicker21");
						if(TPDB_ClaimValid.equalsIgnoreCase(""))
						{
							TPDB_ClaimValid=null;
						}
						else
						{
							TPDB_ClaimValid = " '"+TPDB_ClaimValid+"' ";
						}
						
						String TPDB_DateOfDisblement = formObject.getNGValue("DATE_OF_DISABLEMENT");
						
						if(TPDB_DateOfDisblement.equalsIgnoreCase(""))
						{
							TPDB_DateOfDisblement=null;
						}
						else
						{
							TPDB_DateOfDisblement = " '"+TPDB_DateOfDisblement+"' ";
						}
						
						String TPDB_CauseOfDisblement = formObject.getNGValue("CAUSE_OF_DISABLEMENT");
						
						String TPDB_ClaimIntimationSource = formObject.getNGValue("Combo61");
						String TPDB_CustomerSign = formObject.getNGValue("Combo60");
						String TPDB_Agent = formObject.getNGValue("Combo58");
						String TPDB_PrimaryHealthStmt = formObject.getNGValue("Combo59");
						String TPDB_HosptitalName = formObject.getNGValue("Text102");
						String TPDB_HospitalAdmission = formObject.getNGValue("DatePicker50");
						if(TPDB_HospitalAdmission.equalsIgnoreCase(""))
						{
							TPDB_HospitalAdmission=null;
						}
						else
						{
							TPDB_HospitalAdmission=" '"+TPDB_HospitalAdmission+"' ";
						}	
						
						String TPDB_HospitalDischarge = formObject.getNGValue("DatePicker49");
						if(TPDB_HospitalDischarge.equalsIgnoreCase(""))
						{
							TPDB_HospitalDischarge=null;
						}
						else
						{
							TPDB_HospitalDischarge = " '"+TPDB_HospitalDischarge+"' ";
						}	
						String TPDB_ICCode1=formObject.getNGValue("Text64");
						String TPDB_ICCode2=formObject.getNGValue("Text65");
						String TPDB_ICCode3=formObject.getNGValue("Text67");
						String TPDB_ICCode4=formObject.getNGValue("Text66");
						String TPDB_ICCode5=formObject.getNGValue("Text73");
						String TPDB_ICCode6=formObject.getNGValue("Text72");
						
						String ClaimOnIdQuery= "Select CLAIM_ON_ID from NG_CLM_ClaimOnID_Master WITH(NOLOCK) where CLAIM_ON='"+TPDB_ClaimOn+"' and WI_NAME = '"+WI_NAME+"'";  
						log.WriteToLog("Claim ON ID Query"+ClaimOnIdQuery);
						
						List<List<String>> ClaimOnIdResult = formObject.getDataFromDataSource(ClaimOnIdQuery);
						log.WriteToLog("Claim On Id Result : "+ClaimOnIdResult);
						String claimOnId ="";
						
						if(!ClaimOnIdResult.isEmpty())
						{
							claimOnId = ClaimOnIdResult.get(0).get(0);
						}
						
						String Query = "update NG_CLM_Ext_Table set NAV_DATE = "+TPDB_NAVDate		
								+" ,CLM_ON = '"+TPDB_ClaimOn							+"' ,claim_on_id = '"+claimOnId	
								+"' ,CLM_VALID_NOTIF_DATE = "+TPDB_ClaimValid		
								+" ,CLM_INTIMATION_SRC = '"+TPDB_ClaimIntimationSource	+"' ,CUSTOMER_SIGN = '"+TPDB_CustomerSign			
								+"' ,AGENT = '"+TPDB_Agent								+"' ,PRI_HEALTH_STMT = '"+TPDB_PrimaryHealthStmt	
								+"' ,HOSP_NAME = '"+TPDB_HosptitalName					+"' ,HOSP_ADMISSION_DATE = "+TPDB_HospitalAdmission	
								+" ,HOSP_DISCHARGE_DATE = "+TPDB_HospitalDischarge		+" ,IC_CODE_1 = '"+TPDB_ICCode1
								+"' ,IC_CODE_2 = '"+TPDB_ICCode2						+"' ,IC_CODE_3 = '"+TPDB_ICCode3
								+"' ,IC_CODE_4 = '"+TPDB_ICCode4						+"' ,IC_CODE_5 = '"+TPDB_ICCode5	
								+"' ,IC_CODE_6 = '"+TPDB_ICCode6						+"', DATE_OF_EVENT = "+TPDB_DateOfDisblement				
								+",CAUSE_OF_EVENT = '"+	TPDB_CauseOfDisblement		
								+"'  where  WI_NAME = '"+WI_NAME+"'";
						log.WriteToLog("In TPDB Claim Frame Update Query"+Query);
					
						formObject.saveDataIntoDataSource(Query);
						
					}
					
					//This code is resetting new_doc_indicator flag to 'N'
					String newDocIndicatorUpdateQuery = "update NG_CLM_Ext_Table set NEW_DOC_INDICATOR='N' WHERE WI_NAME = '"+WI_NAME+"'"; 
					log.WriteToLog("New Doc Indicator Flag Update Query : "+newDocIndicatorUpdateQuery);
					formObject.saveDataIntoDataSource(newDocIndicatorUpdateQuery);		
				
				
				}//End of Save 
				
				if(pEvent.getSource().getName().equalsIgnoreCase("Proceed"))
				{
					log.WriteToLog("Clickd on Proceed Button : ");
					formObject.setNGValue("Option5", true);
					
					if(formObject.getNGValue("Policy_No").equalsIgnoreCase(""))
					{
						throw new ValidatorException(new FacesMessage("Policy Number Cannot be Blank"));				
					}
					else
					{
						if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("---Select---")
								||formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("")
								||formObject.getNGValue("CLM_TYPE")==null)
							
						{
							throw new ValidatorException(new FacesMessage("Please select a claim type"));
						}
						else
						{
							if(formObject.getNGValue("INTIMATION_TYPE").equalsIgnoreCase("---Select---")
									||formObject.getNGValue("INTIMATION_TYPE").equalsIgnoreCase("")
									||formObject.getNGValue("INTIMATION_TYPE")==null)
							{
								throw new ValidatorException(new FacesMessage("Please select a intimation type"));
							}
	
	//This code if for adding claim on values in combos but they get added multiple times by proceed button and form populate also						
						String claimOnComboQuery = "Select CLAIM_ON from NG_CLM_ClaimOnID_Master WITH(NOLOCK) where WI_NAME='"+WI_NAME+"'";
						List<List<String>>claimOnComboResult = formObject.getDataFromDataSource(claimOnComboQuery);
						
						
						if(claimOnComboCount==0)
						{
							if(!claimOnComboResult.isEmpty())
							{
								log.WriteToLog("Claim on List "+ claimOnComboResult);
								List<String> claimOnId = new ArrayList<String>();
								int k=0;
								
								for(List<String> i : claimOnComboResult)
								{
									log.WriteToLog("Claim on List "+i);
									claimOnId.add(claimOnComboResult.get(k).get(0)); 
									log.WriteToLog("Claim on Value "+claimOnComboResult.get(k).get(0));
									k++;
								}
//								formObject.addItem("Combo12", claimOnId);
//								formObject.addItem("Combo69", claimOnId);
//								formObject.addItem("Combo70", claimOnId);
//								formObject.addItem("Combo71", claimOnId);
//								formObject.addItem("Combo72", claimOnId);
//								formObject.addItem("Combo73", claimOnId);
							}
							claimOnComboCount++;
						}
						
						

						//To get Claim On Selected Value From the Combobox 
						
						
						
						String policyNumber = formObject.getNGValue("Policy_No");
						log.WriteToLog("Policy Nubmer : "+ policyNumber);
						String claimType = formObject.getNGValue("CLM_TYPE");
						log.WriteToLog("Claim Type : "+claimType);
						
						String documentGridQuery = "select documentcode, documentdescription,mandatory,'Pending','','','' from ng_clm_claimintimation_picklist where autopopulatefor = '"+claimType+"'";
						log.WriteToLog("Document Grid Query : "+ documentGridQuery);	
						List<List<String>> documentQueryResult = formObject.getDataFromDataSource(documentGridQuery);
						log.WriteToLog("Document Grid Query Result : "+documentQueryResult);
				
						ListView ListViewObject = formObject.getComponent("q_document_grid");
						log.WriteToLog("Clearing the q_document_grid");
					//	ListViewObject.clear();
						if(!documentQueryResult.isEmpty())
						{
							ListViewObject.clear();
							for(List<String> i :documentQueryResult)
							{
								log.WriteToLog("Adding document to Grid : "+ i);
								formObject.addItem("q_document_grid", i);
						//	formObject.addItem("ListView2",i);
							}
						}
					
						//Query Staging DB
						//String claimTypeSelectQuery= " select STRCDDESC,NPARAMCD from STAGNEW2.Staging_claim.dbo.COM_PARAM_SYSTEM_M where IPARAMTYPECD='3501'and DPARAMNBR1='1' and STRCDDESC = '"+claimType+"'";
						//Query Elixir DB
						String claimTypeSelectQuery= " select STRCDDESC,NPARAMCD from STAGNEW2.Staging_claim.dbo.COM_PARAM_SYSTEM_M where IPARAMTYPECD='3501'and DPARAMNBR1='1' and STRCDDESC = '"+claimType+"'";
					
						log.WriteToLog("Claim Type Query : "+claimTypeSelectQuery);
						List<List<String>>  claimTypeSelectResult = formObject.getDataFromDataSource(claimTypeSelectQuery);			
						log.WriteToLog("Claim Type Select Result : "+ claimTypeSelectResult);
						
						String NClaimType = claimTypeSelectResult.get(0).get(1);
						
						String claimTypeCountQuery = "select count(*) from  STAGNEW2.Staging_claim.dbo.COM_POL_PROD_DTL a,STAGNEW2.Staging_claim.dbo.CLM_BENEFIT_MASTER b where a.STRPRODCD = b.STRPRODCD and a.STRPOLNBR='"+policyNumber+"' and b.NCLAIMTYPE='"+NClaimType+"'";
						log.WriteToLog("Claim Type Count Query : "+claimTypeCountQuery);
						List<List<String>>  claimTypeCountResult = formObject.getDataFromDataSource(claimTypeCountQuery);
						log.WriteToLog("Claim Type Count Result : "+ claimTypeCountResult);
						int countType=0;
						if(!claimTypeCountResult.isEmpty())
						{
							countType = Integer.parseInt(claimTypeCountResult.get(0).get(0));		
							log.WriteToLog("Count Type : "+countType);
						}
							
						
						String prodlineCountQuery = "select count(*) from  STAGNEW2.Staging_claim.dbo.COM_POLICY_M a,STAGNEW2.Staging_claim.dbo.CLM_BENEFIT_MASTER b where a.STRPRODLINE =b.STRPRODLINE and a.STRPOLNBR='"+policyNumber+"' and b.NCLAIMTYPE='"+NClaimType+"'";
						log.WriteToLog("Prodline Query : "+prodlineCountQuery);
						List<List<String>> prodlineResult = formObject.getDataFromDataSource(prodlineCountQuery);
						log.WriteToLog("Prodline Result : "+prodlineResult);
						
						int prodlineCount;
						
						if(prodlineResult.isEmpty())
						{
							prodlineCount=0;
						}
						else
						{
							prodlineCount = Integer.parseInt(prodlineResult.get(0).get(0));
						}
						
						log.WriteToLog("Prodline Result : "+prodlineCount);
						

						if(countType==0 && prodlineCount==0)
							{
								log.WriteToLog("Count Type : "+countType);
								
								//This code is used to hide frame if user click on proceed and the claim type is
								//not applicable to that policy number to use this uncomment the below code
								
//								formObject.setVisible("frHospital", false); 
//								formObject.setVisible("frDeathClaimDetails", false);
//								formObject.setVisible("frCritical", false);
//								formObject.setVisible("frAccidentalDeath", false);
//								formObject.setVisible("frDeathClaimWaiver", false);
//							
								formObject.setNGValue("CLM_TYPE", "---Select---");
								
								throw new ValidatorException(new FacesMessage("The selected claim type is not applicable for the policy number : "+ policyNumber));
							}
						
//						if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim")
//							||formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim Waiver of Premium"))
//						{			
//							String loansuranceCountQuery = "SELECT COUNT(*) NLOANSURANCECNT FROM PRD_KEY_INFO PKI,"+ 
//									" STAGNEW2.Staging_claim.dbo.COM_POL_PROD_DTL cppd"+ 
//									" WHERE pki.STRPRODCD=cppd.STRPRODCD"+ 
//									" and pki.NPRODVER = cppd.NPRODVER"+ 
//									" and pki.NVALGRP=2 and cppd.STRPOLNBR='"+policyNumber+"'";
//							log.WriteToLog("LoansuranceCountQuery : "+loansuranceCountQuery);
//							List<List<String>> loansuranceCountResult = formObject.getDataFromDataSource(loansuranceCountQuery);
//							log.WriteToLog("Loansurance Count Result : "+loansuranceCountResult);
//							int loansureanceCount = Integer.parseInt(loansuranceCountResult.get(0).get(0));
//							log.WriteToLog("Loansurance Count : "+loansureanceCount);
//							if(loansureanceCount==1)
//							{
//								formObject.setVisible("Command22",true);
//								formObject.setVisible("Command5", true);	
//							}
//							else
//							{
//								formObject.setVisible("Command22",false);
//								formObject.setVisible("Command5", false);
//							}
//							
//							String isLoanCoveredQuery = "SELECT COUNT(*) nHomesuranceCnt FROM PRD_KEY_INFO pki, STAGNEW2.Staging_claim.dbo.COM_POL_PROD_DTL cppd"+ 
//									" where pki.STRPRODCD = cppd.STRPRODCD"+
//									" and pki.NPRODVER = cppd.NPRODVER"+ 
//									" and pki.NPRDCATG=1"+ 
//									" and pki.NISRTA=1"+
//									" and cppd.STRPOLNBR='"+policyNumber+"'";
//							log.WriteToLog("IsLoanCoveredQuery : "+isLoanCoveredQuery);
//							List<List<String>> isLoanCoveredResult = formObject.getDataFromDataSource(isLoanCoveredQuery);
//							if(!isLoanCoveredResult.isEmpty())
//							{
//								log.WriteToLog("IsLoadCoveredResult : "+isLoanCoveredResult);
//								int isLoanCovered = Integer.parseInt(isLoanCoveredResult.get(0).get(0));
//								log.WriteToLog("IsLoanCovered Count :"+isLoanCovered);
//								if(isLoanCovered==0){
//									formObject.setVisible("Label76", false);
//									formObject.setVisible("Combo1", false);
//									formObject.setVisible("Label174", false);
//									formObject.setVisible("Combo44", false);
//								}
//								else
//								{
//									formObject.setVisible("Label76", true);
//									formObject.setVisible("Combo1", true);
//									formObject.setVisible("Label174", true);
//									formObject.setVisible("Combo44", true);
//								}	
//							}
//							
//						}
						
								
						String Query = "SELECT NPOLSTATCD,isnull(convert(nvarchar(100),convert(date,DTPOLISSUE)),''),"
								+ " isnull(convert(nvarchar(100),convert(date,DTPOLCOMC)),''),"
								+ " isnull(convert(nvarchar(100),convert(date,DTPOLMAT)),''),"
								+ " isnull(convert(nvarchar(100),convert(date,DTPOLSTATCHANGE)),'')"
								+ " FROM STAGNEW2.Staging_claim.dbo.COM_POLICY_M WHERE STRPOLNBR =  '" +policyNumber+"'" ;
					
						log.WriteToLog("Select Query for fetching Policy Status Date of Issue Commencement Date Change and Last Status Change Date "+Query);
						List<List<String>> ELIXIR_Data = formObject.getDataFromDataSource(Query);
						log.WriteToLog("Elixir Data : "+ELIXIR_Data);
						if(ELIXIR_Data.isEmpty())
						{
							throw new ValidatorException(new FacesMessage("Policy number is not valid "));
						}
						else
						{
							String StatusDescQuery = "select StatusDesc from NG_CLM_Policy_Status_Desc_Master WITH(NOLOCK) where StatusCode = '"+ELIXIR_Data.get(0).get(0)+"'";
							log.WriteToLog("Executing Select Query for Status Desciptiong from master tabel "+ StatusDescQuery);
							String StatusDescription = formObject.getDataFromDataSource(StatusDescQuery).get(0).get(0);
							log.WriteToLog("Status Description : "+StatusDescription);
							formObject.setNGValue("POLICY_STATUS", StatusDescription);
								//just for Example 
							//formObject.setNGValue("POLICY_MATURITY_DATE", ELIXIR_Data.get(0).get(3).replaceAll("-", "/").substring(0, 10));
							formObject.setNGValue("ISSUE_DATE", ELIXIR_Data.get(0).get(1).replaceAll("-", "/").substring(0, 10));
							formObject.setNGValue("COMMEC_DATE", ELIXIR_Data.get(0).get(2).replaceAll("-", "/").substring(0, 10));
							formObject.setNGValue("POLICY_MATURITY_DATE", ELIXIR_Data.get(0).get(3).replaceAll("-", "/").substring(0, 10));
							
							
							
							if(ELIXIR_Data.get(0).get(4).equalsIgnoreCase(""))
							{
								log.WriteToLog("Checking Last Status Change date as blank.");
								formObject.setNGValue("LAST_STATUS_CHANGE_DATE","");	
							}
							else
							{
								formObject.setNGValue("LAST_STATUS_CHANGE_DATE", ELIXIR_Data.get(0).get(4).replaceAll("-", "/").substring(0, 10));
							}	
							
							
							formObject.setNGValue("Combo64", "Accidental Death");
							formObject.setLocked("Combo64", true);
							
							//Reinsurer Name Query
								
							String nameOfTheReinsurerQuery = "select isnull(nbpd.STRRICD,'') from STAGNEW2.Staging_claim.dbo.NB_BASE_PROP_DTL nbpd, "
									+ " STAGNEW2.Staging_claim.dbo.NB_PROP_POL_LNK nppl where nppl.STRPROPNBR= nbpd.STRPROPNBR "
									+ " and nppl.STRPOLNBR='"+policyNumber+"'";
							List<List<String>> nameOfTheReinsurerResult = formObject.getDataFromDataSource(nameOfTheReinsurerQuery);
							formObject.setNGValue("REINSURER_NAME", nameOfTheReinsurerResult.get(0).get(0));
							
//							log.WriteToLog("Executing Claim On Query.");
//							
//							String ClaimOn = "select a.STRFIRSTNAME from STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME a, "
//									+ " STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK b, STAGNEW2.Staging_claim.dbo.COM_CLIENT_M c "
//									+ " where b.STRPOLNBR='"+policyNumber+"' and b.NCLIENTTYPE in ('1','2','3') and a.STRCLIENTCD=b.STRCLIENTCD and b.STRCLIENTCD=c.STRCLIENTCD and c.nisdeceased = 0";
//							
//							log.WriteToLog("Claim on Query : "+ClaimOn);
//							List<List<String>> claimOnList = formObject.getDataFromDataSource(ClaimOn);
//							log.WriteToLog("Claim on List "+ claimOnList);
//							List<String> claimOn = new ArrayList<String>();
//							int j=0;
//							if(claimOnCount==0){
//							for(List<String> i : claimOnList)
//							{
//								log.WriteToLog("Claim on List "+i);
//								claimOn.add(claimOnList.get(j).get(0)); 
//								log.WriteToLog("Claim on Value "+claimOnList.get(j).get(0));
//								j++;
//							}
//							formObject.addItem("Combo12", claimOn);
//							formObject.addItem("Combo69", claimOn);
//							formObject.addItem("Combo70", claimOn);
//							formObject.addItem("Combo71", claimOn);
//							formObject.addItem("Combo72", claimOn);
//							formObject.addItem("Combo73", claimOn);
//							claimOnCount++;
//							}
							
						}	
					}
				 }
				}//End of Proceed
				
				// Old Claims Details Grid
				if(pEvent.getSource().getName().equalsIgnoreCase("Command7"))
				{
					log.WriteToLog("In Command 7 Executing WebService");
					WSDemo WS = new WSDemo();
					String policyNumber = formObject.getNGValue("Policy_No");
					WS.setPolicyNumber(formObject.getNGValue("Policy_No"));
					ArrayList<String> arr = WS.getHospitalClaimDetails();
					formObject.setNGValue("Text97", arr.get(0));
					formObject.setNGValue("Text96", arr.get(1));
					formObject.setNGValue("Text93", arr.get(2));
					formObject.setNGValue("Text94", arr.get(3));
					
					String oldClaimsDetailsGrid = "select STRCLAIMNBR,isnull(convert(nvarchar(100),convert(date,DTADMISSION)),'') as DTADMISSION, "
							+ " isnull(convert(nvarchar(100),convert(date,DTDISCHARGE)),'') as DTDISCHARGE ,isnull(convert(nvarchar(100),convert(date,DTCLAIMNOTI)),'') as DTCLAIMNOTI,"
							+ " isnull(convert(nvarchar(100),convert(date,DTAPPROVAL)),'') as DTAPPROVAL ,DNETCLAIMAMNT from STAGNEW2.Staging_claim.dbo.CLM_APPLC_DTL WHERE STRPOLNBR = '" +policyNumber+"'" ;
					log.WriteToLog("Starting Old Claim Details Grid "+oldClaimsDetailsGrid);
					List<List<String>> oldClaimDetailsGrid = formObject.getDataFromDataSource(oldClaimsDetailsGrid);
				
					log.WriteToLog("Executed Old Claim Details : "+oldClaimDetailsGrid);
					
					ListView ListViewObject = formObject.getComponent("ListView7");
					ListViewObject.clear();
					
						if(!oldClaimDetailsGrid.isEmpty())
						{
						for(List<String> i :oldClaimDetailsGrid){
							log.WriteToLog("Old Claim Details in Grid"+i);
						
							formObject.addItem("ListView7", i);	
							
							}
						}
				}// End of Command7
				
				
				if(pEvent.getSource().getName().equalsIgnoreCase("Button7"))
				{
					/*DocumentCode	DescriptionDocument	Mandatory	StatusDocument	ReceivedDate	Remarks
						CLME1		Death Claim form	Yes	Pending	NULL	NULL
						CLME10		Original Policy Document	Yes	Pending	NULL	NULL
						CLME7		Original Death Certificate	Yes	Pending	NULL	NULL
						CLME8		Copy of Identity Proof of the claimant	Yes	Pending	NULL	NULL
						CLME9		Copy of the residence proof of the claimant	Yes	Pending	NULL	NULL
						CLME11		Name of the Bank and account no of the beneficiary	Yes	Pending	NULL	NULL
						CLME5		Waiver of Premium of Death Claim Form	Yes	Pending	NULL	NULL 
					 */
					
					String query ="select distinct documentcode, documentdescription,mandatory from ng_clm_claimintimation_picklist";
					log.WriteToLog("Executing Document List Query for PickList: "+ query);
					PickList pickListObject = formObject.getNGPickList("Button7", "DocumentCode,DescriptionDocument,Mandatory", true, 10,true);
					pickListObject.populateData(query);
				
					pickListObject.setVisible(true);
					pickListObject.setWindowTitle("Documents");
					pickListObject.setBatchSize(10);
					pickListObject.setSearchEnabled(true);
					Color c1 = Color.blue;
					Color c2 = Color.white;
					pickListObject.setPicklistHeaderFGColor(c2);
					pickListObject.setPicklistHeaderBGColor(c1);
					pickListObject.addPickListListener(new NG_CLM_PickListListener(pickListObject.getClientId()));
				}
				
				if(pEvent.getSource().getName().equalsIgnoreCase("Button9"))
				{
					String query ="Select STRCDDESC from STAGNEW2.Staging_claim.dbo.COM_PARAM_USER_M WHERE IPARAMTYPECD=1024";
					log.WriteToLog("Executing Document List Query for Type of surgery "+ query);
					PickList pickListObject = formObject.getNGPickList("Button7", "Type of Surgery", true, 10,true);
					pickListObject.populateData(query);
					pickListObject.setVisible(true);
					pickListObject.setWindowTitle("Type of Surgery");
					pickListObject.setBatchSize(10);
					pickListObject.setSearchEnabled(true);
					Color c1 = Color.blue;
					Color c2 = Color.white;
					pickListObject.setPicklistHeaderFGColor(c2);
					pickListObject.setPicklistHeaderBGColor(c1);
					pickListObject.addPickListListener(new NG_CLM_PickListListenerTypeOfSurgery(pickListObject.getClientId()));
				}
				
				
				
				if(pEvent.getSource().getName().equalsIgnoreCase("Button4"))
				{
					log.WriteToLog("Clicked on Button4 to add row in the document grid");
				//	FormReference formObject2 = FormContext.getCurrentInstance().getFormReference();
				//	formObject.setNGValue("Text37","Hi There 2");
					formObject.setEnabled("q_document_grid", true);
			//		formObject2.ExecuteExternalCommand("NGAddRow", "q_document_grid_table");
			//		formObject2.ExecuteExternalCommand("", arg1);
					if(formObject.getNGValue("Combo62").equalsIgnoreCase("---Select---"))
					{
						throw new ValidatorException(new FacesMessage("Please select status of document"));
					}
					else
					{
						formObject.ExecuteExternalCommand("NGAddRow", "q_document_grid");	
					}
					
			//		formObject2.ExecuteExternalCommand("NGInsertRow", "q_document_grid");
			//		formObject2.ExecuteExternalCommand("NGRefreshInterface", "q_document_grid");

				}
				//command 19,25,6,23,24,26
				if(pEvent.getSource().getName().equalsIgnoreCase("Command19")||
						pEvent.getSource().getName().equalsIgnoreCase("Command25")||
						pEvent.getSource().getName().equalsIgnoreCase("Command6")||
						pEvent.getSource().getName().equalsIgnoreCase("Command23")||
						pEvent.getSource().getName().equalsIgnoreCase("Command24")||
						pEvent.getSource().getName().equalsIgnoreCase("Command26"))
				{
				//	FormReference formObject2 = FormContext.getCurrentInstance().getFormReference();
				log.WriteToLog("Clicked on Related Policies Button.");
					//String PolicyNumber = formObject.getNGValue("Policy_No");
					//new ValidatorException(new FacesMessage("Policy Number "+PolicyNumber));
//					String Query = "select b.NCLIENTTYPE, a.STRPOLNBR,a.NPOLSTATCD,a.DTOTSA from"
//							+ "STAGNEW2.Staging_claim.dbo.COM_POLICY_M a ,STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK b"
//							+ "WHERE a.STRPOLNBR = b.STRPOLNBR and a.STRPOLNBR ='"+PolicyNumber+"'";
//					
//					List<List<String>> NG_CLM_Ext_Table_Data = formObject.getDataFromDataSource(Query);
//					//if(formObject.getNGValue("ListView6")==null)
//					{
//						if(1==1)
//						{
//							new ValidatorException(new FacesMessage("Policy Number "+PolicyNumber));
//						}
//						for(List<String> i :NG_CLM_Ext_Table_Data){
//							
//							formObject.addItem("ListView6", i);	
//					
//							}
//						}
					
				//Previous Query 	
//					String Query = "select a.STRPOLNBR,b.NCLIENTTYPE,a.NPOLSTATCD,a.DTOTSA from"
//							+ " STAGNEW2.Staging_claim.dbo.COM_POLICY_M a ,STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK b"
//							+ " WHERE a.STRPOLNBR = b.STRPOLNBR and a.STRPOLNBR ='"+PolicyNumber+"'";
			//Modified Query with policy status code description
					
				//	formObject.getNGSelectedItemText(arg0)
				
				String ClaimOn = null;
				
//					ListView ListViewObject = formObject.getComponent("ListView6");
//					ComboBox ComboBoxObject = formObject.getComponent("Combo12");
//					ComboBoxObject.getValue();
					
//				
//					if(ClaimOn.equalsIgnoreCase(""))
//					{
//						log.WriteToLog("Claim On is Blank");
//					}
//					if(ClaimOn.equalsIgnoreCase(null))
//					{
//						log.WriteToLog("Claim On is Null in equalIgnoreCase");
//					}
//					if(ClaimOn==null)
//					{
//						log.WriteToLog("Claim On dead code ");
//					}
					
						
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim"))
					{
						log.WriteToLog("After Getting CLM_TYPE Value as Death Claim");
						ClaimOn = formObject.getNGValue("Combo12");
						log.WriteToLog("Death Claim ClaimOn ComboBox Value : "+ClaimOn);
							
					}
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Accidental Death Claim"))
					{			

						ClaimOn = formObject.getNGValue("Combo70");
						log.WriteToLog("Accidental Death Claim ClaimOn ComboBox Value : "+ClaimOn);
					}
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim Waiver of Premium"))
					{
						ClaimOn = formObject.getNGValue("Combo69");
						log.WriteToLog("Death Claim Waiver of Premium Claim On ComboBox Value : "+ClaimOn);

					}
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Critical Illness/Dreaded Disease Claim"))
					{
						
						ClaimOn = formObject.getNGValue("Combo71");
						log.WriteToLog("Critical Illness/Dreaded Disease Claim ClaimOn ComboBox Value : "+ClaimOn);
					}
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Hospitalization Claim"))
					{
						
						
						ClaimOn = formObject.getNGValue("Combo72");
						log.WriteToLog("Hospitalization Claim ClaimOn ComboBox Value : "+ClaimOn);

						
					}
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("TPDB Claim"))
					{
						ClaimOn = formObject.getNGValue("Combo73");
						log.WriteToLog("TPDB Claim ClaimOn ComboBox Value : "+ClaimOn);
						
					 }
					
					
					log.WriteToLog("Claim On : "+ClaimOn);
					String ClaimOnIdQuery= "Select CLAIM_ON_ID from NG_CLM_ClaimOnID_Master WITH(NOLOCK) where CLAIM_ON='"+ClaimOn+"' and WI_NAME = '"+WI_NAME+"'";  
					log.WriteToLog("Claim ON ID Query"+ClaimOnIdQuery);
					List<List<String>> ClaimOnIdResult = formObject.getDataFromDataSource(ClaimOnIdQuery);
					log.WriteToLog("Claim On Id Result : "+ClaimOnIdResult);
					String claimOnId = ClaimOnIdResult.get(0).get(0);
					log.WriteToLog("Claim ON id : "+claimOnId);
					
					String Query = "select t1.STRPOLNBR,t2.STRCDDESC,t4.StatusDesc,t1.DTOTSA from" 
					+" STAGNEW2.Staging_claim.dbo.COM_POLICY_M t1,"
					+" STAGNEW2.Staging_claim.dbo.COM_PARAM_SYSTEM_M t2 ,"
					+" STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK t3,"
					+" NG_CLM_Policy_Status_Desc_Master t4"
					+" where t3.STRCLIENTCD= '"+claimOnId+"'" 
					+" and t3.NCLIENTTYPE = t2.NPARAMCD" 
					+" and t2.IPARAMTYPECD='2001'" 
					+" and t3.STRPOLNBR=t1.STRPOLNBR"	
					+" and convert(nvarchar(100),t1.NPOLSTATCD) = t4.StatusCode";
				
					log.WriteToLog("Related Policies Grid Query : "+Query);
//				String Query = "select t1.STRPOLNBR,t1.NCLIENTTYPE,t2.StatusDesc,t1.DTOTSA"
//						+ " from (select a.STRPOLNBR,b.NCLIENTTYPE,a.NPOLSTATCD,a.DTOTSA from "
//						+ " STAGNEW2.Staging_claim.dbo.COM_POLICY_M a ,STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK b "
//						+ " WHERE a.STRPOLNBR = b.STRPOLNBR and a.STRPOLNBR ='"+PolicyNumber+"') as t1,"
//						+ " NG_CLM_Policy_Status_Desc_Master t2  where convert(nvarchar(100),t1.NPOLSTATCD) = t2.StatusCode ";
//				
			///	log.WriteToLog("Clicked on Policy Details to get policy number, client type, last status change date, date of issue"+Query);
			
				List<List<String>> RelatedPoliciesGridQuery = formObject.getDataFromDataSource(Query);
				log.WriteToLog("Realted Policies Grid Query Result : "+RelatedPoliciesGridQuery);
				//String StatusDescQuery = "select StatusDesc from NG_CLM_Policy_Status_Desc_Master where StatusCode = '"+NG_CLM_Ext_Table_Data.get(0).get(2)+"'";
				//List<List<String>> StatusDesc = formObject.getDataFromDataSource(StatusDescQuery);
				
				//log.WriteToLog("Status Desciption Query "+ StatusDesc.get(0).get(0));
				
				
				
				if(countRelatedPolicy!=1){
					if(!RelatedPoliciesGridQuery.isEmpty())
					{
					for(List<String> i :RelatedPoliciesGridQuery){
						log.WriteToLog("Adding the result in the listview "+i);
						
						formObject.addItem("ListView6", i);	
						
						}
					}
					countRelatedPolicy++;
				}
				
				//	List<List<String>> Dummy_Values = new ArrayList<List<String>>();
				//	List<String> Dummy_Values2= new ArrayList<String>();
					
				//	Dummy_Values2.add(index, element);
					
					
//					if(countRelatedPolicy!=1)
//					{
//						countRelatedPolicy++;
//						Dummy_Values2.add(PolicyNumber);
//						Dummy_Values2.add("Life Assured");
//						Dummy_Values2.add("Paid Up");
//						Dummy_Values2.add("500000");
//						Dummy_Values.add(0, Dummy_Values2);
//						
//						List<String> Dummy_Values3= new ArrayList<String>();
	//
//						formObject.addItem("ListView6", Dummy_Values.get(0));
//						
//						Dummy_Values3.add("400002310");
//						Dummy_Values3.add("Proposer");
//						Dummy_Values3.add("Paid Up");
//						Dummy_Values3.add("200000");
//						Dummy_Values.add(1, Dummy_Values3);
//						
	//
//						formObject.addItem("ListView6", Dummy_Values.get(1));
//						
//					}
//					
				}
				if(pEvent.getSource().getName().equalsIgnoreCase("Button1"))
				{
					log.WriteToLog("Clicked on Button1 to add row in the document grid");
					formObject.ExecuteExternalCommand("NGAddRow", "q_requirement_grid");
					
				}
				if(pEvent.getSource().getName().equalsIgnoreCase("Button2"))
				{
					log.WriteToLog("Clicked on Button2 to modify row in the document grid");
					formObject.ExecuteExternalCommand("NGModifyRow", "q_requirement_grid");
					
				}
				
				if(pEvent.getSource().getName().equalsIgnoreCase("Command3"))
				{
					//String Query = "select Policy_No from NG_CLM_Ext_Table where WI_NAME = '"+ WI_NAME +"'";
		//	List<List<String>> PolicyNumber = formObject.getDataFromDataSource(Query);
					String PolicyNumber = formObject.getNGValue("Policy_No");
					
					
					
					String PolicyDetailsQuery = "select a.STRPOLNBR,concat (d.STRFIRSTNAME,' ',d.STRMIDDLENAME,' ',d.STRLASTNAME), "
							+ " isnull(convert(nvarchar(100), convert(date,a.DTPOLCOMC)),''),"
							+ " isnull(convert(nvarchar(100),convert(date,a.DTPOLRISKCOMC)),''),b.STRCDDESC, "
							+ " isnull(convert(nvarchar(100),convert(date,a.DTPOLSTATCHANGE)),''),"
							+ " isnull(convert(nvarchar(100),convert(date,a.DTPOLEXPIRY)),''),"
							+ " isnull(convert(nvarchar(100),convert(date,a.DTPOLMAT)),''),a.DTOTSA,"
							+ "	isnull(convert(nvarchar(100),convert(date,a.DTLASTDUEPAID)),'')"
							+ " from STAGNEW2.Staging_claim.dbo.COM_POLICY_M a,"
							+ " STAGNEW2.Staging_claim.dbo.COM_PARAM_SYSTEM_M b ,"
							+ " STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK c ,"
							+ "	STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME d	"
							+ " where	  a.STRPOLNBR ='"+PolicyNumber+"'"
							+ " and NPOLSTATCD=b.NPARAMCD and b.IPARAMTYPECD = 3002"
							+ " and	 c.STRCLIENTCD= d.STRCLIENTCD"
							+ " and (c.NCLIENTTYPE='2' or c.NCLIENTTYPE='3')"
							+ " and	 a.STRPOLNBR=c.STRPOLNBR";
					
					log.WriteToLog("Executing Policy Details Query to fetch data from elixir "+PolicyDetailsQuery);
					List<List<String>> RelatedPolicyDetails = formObject.getDataFromDataSource(PolicyDetailsQuery);
					
//					String StatusDescQuery = "select StatusDesc from NG_CLM_Policy_Status_Desc_Master where StatusCode = '"+RelatedPolicyDetails.get(0).get(4)+"'";
//					List<List<String>> StatusDesc = formObject.getDataFromDataSource(StatusDescQuery);
					if(!RelatedPolicyDetails.isEmpty())
					{
					 formObject.setNGValue("Text75",RelatedPolicyDetails.get(0).get(0));
					 formObject.setNGValue("Text76", RelatedPolicyDetails.get(0).get(1));
					 formObject.setNGValue("DatePicker32", RelatedPolicyDetails.get(0).get(2).replaceAll("-", "/").substring(0, 10));
					 formObject.setNGValue("DatePicker33", RelatedPolicyDetails.get(0).get(3).replaceAll("-", "/").substring(0, 10));
					// formObject.setNGValue("Text77", RelatedPolicyDetails.get(0).get(4));
					 formObject.setNGValue("Text77", RelatedPolicyDetails.get(0).get(4));
					 formObject.setNGValue("DatePicker34", RelatedPolicyDetails.get(0).get(5).replaceAll("-", "/").substring(0, 10));
					 formObject.setNGValue("DatePicker35", RelatedPolicyDetails.get(0).get(6).replaceAll("-", "/").substring(0, 10));
					 formObject.setNGValue("DatePicker36",RelatedPolicyDetails.get(0).get(7).replaceAll("-", "/").substring(0, 10));
					 formObject.setNGValue("Text79",RelatedPolicyDetails.get(0).get(8));
					 formObject.setNGValue("DatePicker37",RelatedPolicyDetails.get(0).get(9).replaceAll("-", "/").substring(0, 10));
					 formObject.setNGValue("Text80","Y");
					}
					 //format(t1.DTPOLSTATCHANGE,'yyyy-MM-dd')
					 
					 String relatedPolicyGridQuery = "select d.STRCDDESC, concat(a.STRFIRSTNAME,' ',a.STRLASTNAME) As STRNAME, "
					 		+ " e.STRCDDESC,isnull(convert(nvarchar(100),convert(date,c.DTBIRTH)),'') from "
					 		+ " STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME a,  STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK b,"
					 		+ " STAGNEW2.Staging_claim.dbo.COM_CLIENT_M c,STAGNEW2.Staging_claim.dbo.com_param_user_m d,"
					 		+ " STAGNEW2.Staging_claim.dbo.com_param_system_m e WHERE a.STRCLIENTCD = b.STRCLIENTCD	"
					 		+ " and b.STRCLIENTCD = c.STRCLIENTCD and  b.STRPOLNBR ='"+PolicyNumber+"'	and d.IPARAMTYPECD='5502'"
					 		+ " and a.STRTITLECD=d.STRPARAMCD	and e.IPARAMTYPECD='2001' and e.NPARAMCD=b.NCLIENTTYPE	";				 
					 
					 
					 String productDetailQuery = "SELECT concat(a.STRPRODCD,' ' ,a.NPRODVER) AS BASICRIDER ,isnull(convert(nvarchar(100),convert(date,a.DTPRODCOMC)),''), "
					 		+ "a.NPRODTERM,a.DSA, c.STRCDDESC,isnull(convert(nvarchar(100),convert(date,a.DTTERMN)),''), "
					 		+ "concat(a.DMODALPRMAMNT,' / ',d.STRCDDESC) As MODELPREMIUM from "
					 		+ "STAGNEW2.Staging_claim.dbo.COM_POL_PROD_DTL a, STAGNEW2.Staging_claim.dbo.COM_POLICY_M b,"
					 		+ "STAGNEW2.Staging_claim.dbo.COM_PARAM_SYSTEM_M c,STAGNEW2.Staging_claim.dbo.COM_PARAM_SYSTEM_M d "
					 		+ "where a.STRPOLNBR=b.STRPOLNBR and a.STRPOLNBR ='"+PolicyNumber+"' and a.NPRODSTATCD=c.NPARAMCD "
					 		+ "and c.IPARAMTYPECD='3002' and d.IPARAMTYPECD='5503' and b.NPMTMODE=d.NPARAMCD"; 
					 
				//	 if(countPolicyDetails!=1)
					 {
					 log.WriteToLog("Executing Related Policy Grid Query to fetch data from elixir "+relatedPolicyGridQuery);
					 List<List<String>> PolicyDetails = formObject.getDataFromDataSource(relatedPolicyGridQuery);
					 log.WriteToLog("Executing Product Details Grid Query to fetch data from elixir "+productDetailQuery);
					 List<List<String>> productDetails = formObject.getDataFromDataSource(productDetailQuery);
					 ListView ListViewObject = formObject.getComponent("ListView4");
					 ListViewObject.clear();
					 ListView ListViewObject2 = formObject.getComponent("ListView5");
					 ListViewObject2.clear();
					 
					 log.WriteToLog(String.valueOf(formObject.getNGListIndex("ListView4")));
							
					 	if(!PolicyDetails.isEmpty())
							{
								 
								for(List<String> i :PolicyDetails){
									log.WriteToLog("The Policy Deatails "+i);
									formObject.addItem("ListView4", i);	
									
									}
							}
					 		
							log.WriteToLog(String.valueOf(formObject.getNGListIndex("ListView5")));
							if(!productDetails.isEmpty())
							{
								for(List<String> i :productDetails){
									log.WriteToLog("The Product Deatails "+i);
									formObject.addItem("ListView5", i);	
									
									}
							}
							
						//	countPolicyDetails++;
						}
				}
				
				if(pEvent.getSource().getName().equalsIgnoreCase("Button8"))
				{

					String PolicyNumber = new String();
				
					ListView ListViewObject = formObject.getComponent("ListView6");
					int rowIndex = ListViewObject.getSelectedRowIndex();
					int columnIndex = 0;
					
					log.WriteToLog("Row Index : "+rowIndex +"Column Index : "+columnIndex);
					PolicyNumber=ListViewObject.getValueAt(rowIndex, columnIndex);
					
					log.WriteToLog("Policy Number : "+PolicyNumber);
					
					
					String PolicyDetailsQuery = "select a.STRPOLNBR,concat (d.STRFIRSTNAME,' ',d.STRMIDDLENAME,' ',d.STRLASTNAME), "
							+ " isnull(convert(nvarchar(100), convert(date,a.DTPOLCOMC)),''),"
							+ " isnull(convert(nvarchar(100),convert(date,a.DTPOLRISKCOMC)),''),b.STRCDDESC, "
							+ " isnull(convert(nvarchar(100),convert(date,a.DTPOLSTATCHANGE)),''),"
							+ " isnull(convert(nvarchar(100),convert(date,a.DTPOLEXPIRY)),''),"
							+ " isnull(convert(nvarchar(100),convert(date,a.DTPOLMAT)),''),a.DTOTSA,"
							+ "	isnull(convert(nvarchar(100),convert(date,a.DTLASTDUEPAID)),'')"
							+ " from STAGNEW2.Staging_claim.dbo.COM_POLICY_M a,"
							+ " STAGNEW2.Staging_claim.dbo.COM_PARAM_SYSTEM_M b ,"
							+ " STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK c ,"
							+ "	STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME d	"
							+ " where	  a.STRPOLNBR ='"+PolicyNumber+"'"
							+ " and NPOLSTATCD=b.NPARAMCD and b.IPARAMTYPECD = 3002"
							+ " and	 c.STRCLIENTCD= d.STRCLIENTCD"
							+ " and (c.NCLIENTTYPE='2' or c.NCLIENTTYPE='3')"
							+ " and	 a.STRPOLNBR=c.STRPOLNBR";
					
					log.WriteToLog("Executing Policy Details Query to fetch data from elixir "+PolicyDetailsQuery);
					
					List<List<String>> RelatedPolicyDetails = formObject.getDataFromDataSource(PolicyDetailsQuery);
					 
					//String StatusDescQuery = "select StatusDesc from NG_CLM_Policy_Status_Desc_Master where StatusCode = '"+RelatedPolicyDetails.get(0).get(4)+"'";
					//List<List<String>> StatusDesc = formObject.getDataFromDataSource(StatusDescQuery);
					
					 if(!RelatedPolicyDetails.isEmpty())
					 {
						 formObject.setNGValue("Text75",RelatedPolicyDetails.get(0).get(0));
						 formObject.setNGValue("Text76", RelatedPolicyDetails.get(0).get(1));
						 formObject.setNGValue("DatePicker32", RelatedPolicyDetails.get(0).get(2).replaceAll("-", "/").substring(0, 10));
						 formObject.setNGValue("DatePicker33", RelatedPolicyDetails.get(0).get(3).replaceAll("-", "/").substring(0, 10));
						 formObject.setNGValue("Text77", RelatedPolicyDetails.get(0).get(4));
						// formObject.setNGValue("Text77", StatusDesc.get(0).get(0));
						 formObject.setNGValue("DatePicker34", RelatedPolicyDetails.get(0).get(5).replaceAll("-", "/").substring(0, 10));
						 formObject.setNGValue("DatePicker35", RelatedPolicyDetails.get(0).get(6).replaceAll("-", "/").substring(0, 10));
						 formObject.setNGValue("DatePicker36",RelatedPolicyDetails.get(0).get(7).replaceAll("-", "/").substring(0, 10));
						 formObject.setNGValue("Text79",RelatedPolicyDetails.get(0).get(8));
						 formObject.setNGValue("DatePicker37",RelatedPolicyDetails.get(0).get(9).replaceAll("-", "/").substring(0, 10));
						 formObject.setNGValue("Text80","Y");
					 }
					
					 
					 //format(t1.DTPOLSTATCHANGE,'yyyy-MM-dd')
					 
					 String relatedPolicyGridQuery = "select d.STRCDDESC, concat(a.STRFIRSTNAME,' ',a.STRLASTNAME) As STRNAME, "
						 		+ " e.STRCDDESC,isnull(convert(nvarchar(100),convert(date,c.DTBIRTH)),'') from "
						 		+ " STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME a,  STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK b,"
						 		+ " STAGNEW2.Staging_claim.dbo.COM_CLIENT_M c,STAGNEW2.Staging_claim.dbo.com_param_user_m d,"
						 		+ " STAGNEW2.Staging_claim.dbo.com_param_system_m e WHERE a.STRCLIENTCD = b.STRCLIENTCD	"
						 		+ " and b.STRCLIENTCD = c.STRCLIENTCD and  b.STRPOLNBR ='"+PolicyNumber+"'	and d.IPARAMTYPECD='5502'"
						 		+ " and a.STRTITLECD=d.STRPARAMCD	and e.IPARAMTYPECD='2001' and e.NPARAMCD=b.NCLIENTTYPE	";				 
						 
						 
						 String productDetailQuery = "SELECT concat(a.STRPRODCD,' ' ,a.NPRODVER) AS BASICRIDER ,isnull(convert(nvarchar(100),convert(date,a.DTPRODCOMC)),''), "
						 		+ "a.NPRODTERM,a.DSA, c.STRCDDESC,isnull(convert(nvarchar(100),convert(date,a.DTTERMN)),''), "
						 		+ "concat(a.DMODALPRMAMNT,' / ',d.STRCDDESC) As MODELPREMIUM from "
						 		+ "STAGNEW2.Staging_claim.dbo.COM_POL_PROD_DTL a, STAGNEW2.Staging_claim.dbo.COM_POLICY_M b,"
						 		+ "STAGNEW2.Staging_claim.dbo.COM_PARAM_SYSTEM_M c,STAGNEW2.Staging_claim.dbo.COM_PARAM_SYSTEM_M d "
						 		+ "where a.STRPOLNBR=b.STRPOLNBR and a.STRPOLNBR ='"+PolicyNumber+"' and a.NPRODSTATCD=c.NPARAMCD "
						 		+ "and c.IPARAMTYPECD='3002' and d.IPARAMTYPECD='5503' and b.NPMTMODE=d.NPARAMCD"; 
					// countPolicyDetails=0;
					
			//			 if(countPolicyDetails!=1)
						 {
					 log.WriteToLog("Executing Related Policy Grid Query to fetch data from elixir "+relatedPolicyGridQuery);
					 List<List<String>> PolicyDetails = formObject.getDataFromDataSource(relatedPolicyGridQuery);
					 log.WriteToLog("Executing Product Details Grid Query to fetch data from elixir "+productDetailQuery);
					 List<List<String>> productDetails = formObject.getDataFromDataSource(productDetailQuery);
					 ListView ListViewObject2 = formObject.getComponent("ListView4");
					 ListViewObject2.clear();
					 ListView ListViewObject3 = formObject.getComponent("ListView5");
					 ListViewObject3.clear();
					 
					 log.WriteToLog(String.valueOf(formObject.getNGListIndex("ListView4")));
							if(!PolicyDetails.isEmpty())
							{
								 ListViewObject2.clear();
								for(List<String> i :PolicyDetails){
									log.WriteToLog("The Policy Deatails "+i);
									
									formObject.addItem("ListView4", i);	
									
									}
							}
					 	
							log.WriteToLog(String.valueOf(formObject.getNGListIndex("ListView5")));
							if(!productDetails.isEmpty())
							{
								for(List<String> i :productDetails){
									log.WriteToLog("The Product Deatails "+i);
									formObject.addItem("ListView5", i);	
									
									}
							}
							
						//	countPolicyDetails++;
						}
				}//End of Button
				
				if(pEvent.getSource().getName().equalsIgnoreCase("Command9"))
				{
					log.WriteToLog("Loansurance Submit button is Clicked");
					String OutstandingBalanceLoan = formObject.getNGValue("Text81");
					String LoanAmountSanctioned = formObject.getNGValue("Text82");
					String IsNonPerformingAsset = formObject.getNGValue("Combo16");
					String IsSuretyGurantor = formObject.getNGValue("Combo17");
					
					String loansuranceCountQuery = "select count(WI_NAME) from NG_CLM_Loansurance_Details  WITH(NOLOCK) where WI_NAME ='"+WI_NAME+"'"; 
					
					int count = Integer.parseInt(formObject.getDataFromDataSource(loansuranceCountQuery).get(0).get(0));
					
					if(count==0)
					{
						String loansuranceInsertQuery = "insert into NG_CLM_Loansurance_Details (BALANCE_LOAN_AMT, LOAN_AMT_SANC, IS_NON_PERFORMING,IS_SURETY_GUARANTOR, WI_NAME) values ('"+OutstandingBalanceLoan+"','"+LoanAmountSanctioned+"','"+IsNonPerformingAsset+"','"+IsSuretyGurantor+"','"+WI_NAME+"')";
						log.WriteToLog("loansuranceInsertQuery : "+loansuranceInsertQuery);
						formObject.saveDataIntoDataSource(loansuranceInsertQuery);
					
					}
					else
					{
						String loansuranceUpdateQuery = "update NG_CLM_Loansurance_Details set BALANCE_LOAN_AMT='"+OutstandingBalanceLoan+"',"
								+ " LOAN_AMT_SANC = '"+LoanAmountSanctioned+"', IS_NON_PERFORMING='"+IsNonPerformingAsset+"',"
								+ " IS_SURETY_GUARANTOR='"+IsSuretyGurantor+"' where WI_NAME='"+WI_NAME+"'";
				
						log.WriteToLog("loansuranceUpdateQuery : "+loansuranceUpdateQuery);
						formObject.saveDataIntoDataSource(loansuranceUpdateQuery);
					}							
					
				}//End of Command9
				
				if(pEvent.getSource().getName().equalsIgnoreCase("Command17"))
				{
					
					log.WriteToLog("Bank Details Submit buttons is Clicked");
					String LoanAmountSanctioned = formObject.getNGValue("Text84");
					String rateOfInsterest= formObject.getNGValue("Text85");
					String loanTenure = formObject.getNGValue("Text89");
					String outstandingBalanceLoan= formObject.getNGValue("Text88");
					String deathIncOverdue= formObject.getNGValue("Combo21");
					String overdueInterestAmount = formObject.getNGValue("Text86");
					String EMIPaid = formObject.getNGValue("Combo22");
					String UnpaidPenaltyAmount= formObject.getNGValue("Text87");
					String DrawingPower = formObject.getNGValue("Text90");
					
					String bankDetailsCountQuery= "select count(WI_NAME) from NG_CLM_CEI_Bank_Details WITH(NOLOCK) where WI_NAME='"+WI_NAME+"'";
					int count = Integer.parseInt(formObject.getDataFromDataSource(bankDetailsCountQuery).get(0).get(0));
					if(count==0)
					{
						String bankDetailsInsertQuery = "insert into NG_CLM_CEI_Bank_Details (LOAN_AMT_SANC,RATE_OF_INTEREST,LOAN_TENURE,"
								+ " OUT_BALANCE_LOAN,DEATH_INC_OVERDUE,OVERDUE_INTEREST_AMT,EMI_PAID,UNPAID_PEN_AMT,"
								+ " DRAWING_POWER,WI_NAME) values "
								+ " ('"+LoanAmountSanctioned+"','"+rateOfInsterest+"','"+loanTenure+"', "
								+ "'"+outstandingBalanceLoan+"','"+deathIncOverdue+"','"+overdueInterestAmount+"', "
								+ "'"+EMIPaid+"','"+UnpaidPenaltyAmount+"','"+DrawingPower+"','"+WI_NAME+"')" ;
						log.WriteToLog("bankDetailsInsertQuery : "+bankDetailsInsertQuery);
						formObject.saveDataIntoDataSource(bankDetailsInsertQuery);
					}
					else
					{
						String bankDetailsUpdateQuery = "update NG_CLM_CEI_Bank_Details set LOAN_AMT_SANC='"+LoanAmountSanctioned+"',"
								+ " RATE_OF_INTEREST='"+rateOfInsterest+"',LOAN_TENURE='"+loanTenure+"',"
								+ " OUT_BALANCE_LOAN='"+outstandingBalanceLoan+"',DEATH_INC_OVERDUE='"+deathIncOverdue+"',"
								+ " OVERDUE_INTEREST_AMT='"+overdueInterestAmount+"',EMI_PAID='"+EMIPaid+"',"
								+ " UNPAID_PEN_AMT='"+UnpaidPenaltyAmount+"',DRAWING_POWER='"+DrawingPower+"' "
								+ " where WI_NAME='"+WI_NAME+"'";
						
						log.WriteToLog("bankDetailsUpdateQuery : "+bankDetailsUpdateQuery);
						
						formObject.saveDataIntoDataSource(bankDetailsUpdateQuery);
					}	
					
				}//End of Command17
			}
			break;
		
		case VALUE_CHANGED:
		{
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker56"))
			{ 
				log.WriteToLog("In Requirment Date Value Changed Event");
				Date requirementDate = null;
				
				Date date;
				
				String requirementDateString = formObject.getNGValue("DatePicker56");
				log.WriteToLog("In requirementDateString : "+requirementDateString);
				
				try{
						if(requirementDateString!=null)
						{
							requirementDate = formatter.parse(requirementDateString); 
							requirementDate.setHours(00);
							requirementDate.setMinutes(00);
							requirementDate.setSeconds(00);	
						}					
					}
				catch(Exception E)
					{
						log.WriteToLog("Java Exception in Requirement Date "+ E.toString());
					}
				log.WriteToLog("Before If Condition Comaparing Current Date "+currentDate +" and Requirement Date "+requirementDate);
//				if(currentDate.compareTo(requirementDate)==0)
//				{
//					throw new ValidatorException(new FacesMessage("Requirement Date Cannot be Equal to Current Date"));
//				}
				if(currentDate!=null && requirementDate !=null && currentDate.compareTo(requirementDate)>0)
				{
					log.WriteToLog("Comaparing Current Date "+currentDate +" and Requirement Date "+requirementDate);
					formObject.setNGValue("DatePicker56", "");
					formObject.setNGValue("DatePicker53", "");
					formObject.setNGValue("DatePicker54", "");
					formObject.setNGValue("DatePicker55", "");
	
					throw new ValidatorException(new FacesMessage("Requirement Date Cannot be Less than Current Date"));
				}
			
				if(!formObject.getNGValue("DatePicker56").equalsIgnoreCase(""))
				{
					
					String dateArr[] = requirementDateString.split("/");
					int year = Integer.parseInt(dateArr[0]);
					int month = Integer.parseInt(dateArr[1]);
					int day = Integer.parseInt(dateArr[2]);
					date = new Date(year-1900,month-1,day);
					date.setHours(00);
					date.setMinutes(00);
					date.setSeconds(00)	;
					log.WriteToLog("Date of DatePicker56 : "+date);
					
					Calendar c = Calendar.getInstance();
					
					
					c.setTime(date);
//					log.WriteToLog("Todays Date : "+formatter.format(c.getTime()));
					
					c.add(Calendar.DATE, 7);
					formObject.setNGValue("DatePicker55", formatter.format(c.getTime()));
//					log.WriteToLog("After 7 Days : "+formatter.format(c.getTime()));
					
					c.add(Calendar.DATE, 7);
					formObject.setNGValue("DatePicker53", formatter.format(c.getTime()));
//					log.WriteToLog("After 14 Days : "+formatter.format(c.getTime()));
					
					c.add(Calendar.DATE, 7);
					formObject.setNGValue("DatePicker54", formatter.format(c.getTime()));
//					log.WriteToLog("After 21 Days : "+formatter.format(c.getTime()));
				}	

			}
			
			if(pEvent.getSource().getName().equalsIgnoreCase("HOSP_ADMISSION_DATE"))
			{
				try {
					if(!formObject.getNGValue("HOSP_ADMISSION_DATE").equalsIgnoreCase(""))
					{
						hospitalizedAdmissionDate = formatter.parse(formObject.getNGValue("HOSP_ADMISSION_DATE"));
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					log.WriteToLog("Java Exception in Hospital Admission Date : "+e.toString());
				}
				if(currentDate!=null && hospitalizedAdmissionDate!=null && currentDate.compareTo(hospitalizedAdmissionDate)<0)
				{
					formObject.setNGValue("HOSP_ADMISSION_DATE", "");
					log.WriteToLog("Hospitalized Admissiong Date :" + hospitalizedAdmissionDate);
					throw new ValidatorException(new FacesMessage("Hospitalized admission date cannot be greater than current date"));
				}				
			}
				
			if(pEvent.getSource().getName().equalsIgnoreCase("HOSP_DISCHARGE_DATE"))
			{
				try {
					if(!formObject.getNGValue("HOSP_DISCHARGE_DATE").equalsIgnoreCase(""))
					{
						hospitalizedDischargeDate = formatter.parse(formObject.getNGValue("HOSP_DISCHARGE_DATE"));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					log.WriteToLog("Java Excetpion in Hospital Discharge Date : "+e.toString());
					
				}
				if(formObject.getNGValue("HOSP_ADMISSION_DATE").equalsIgnoreCase(""))
				{
					formObject.setNGValue("HOSP_DISCHARGE_DATE", "");
					throw new ValidatorException(new FacesMessage("First you have to select admission date."));
				}
				
				log.WriteToLog("Comaparing Current Date "+currentDate +"And Hospitalization Discharge Date :"+hospitalizedDischargeDate +"\n And Hospitalized Admission Date "+hospitalizedAdmissionDate +"Hospitalized Discharge Date " +hospitalizedDischargeDate);
				if(currentDate !=null && hospitalizedAdmissionDate != null && hospitalizedDischargeDate != null && (currentDate.compareTo(hospitalizedDischargeDate)<0 || hospitalizedAdmissionDate.compareTo(hospitalizedDischargeDate)>0))
				{
					
					formObject.setNGValue("HOSP_DISCHARGE_DATE", "");
					log.WriteToLog("Hospitalized Discharge Date :" + hospitalizedDischargeDate);
					throw new ValidatorException(new FacesMessage("Hospitalized discharge cannot be greater than current date or hospitalized admission date"));
				}
				
				
			}
				
			if(pEvent.getSource().getName().equalsIgnoreCase("ICU_ADMISSION"))
				
			{
				try {
					if(!formObject.getNGValue("ICU_ADMISSION").equalsIgnoreCase(""))
					{
						ICUAdmission = formatter.parse(formObject.getNGValue("ICU_ADMISSION"));
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					log.WriteToLog("Java Exception in ICU_Admission "+e.toString());
				}
				if(currentDate != null && ICUAdmission !=null && ICUDischarge !=null && 
						hospitalizedAdmissionDate!=null && hospitalizedDischargeDate!=null && 
						(currentDate.compareTo(ICUAdmission)<=0 || 
						hospitalizedAdmissionDate.compareTo(ICUAdmission)>0
						||hospitalizedDischargeDate.compareTo(ICUAdmission)<0))
				{
					formObject.setNGValue("ICU_ADMISSION","");
					log.WriteToLog("ICU Admission Date : "+ ICUAdmission);
					throw new ValidatorException(new FacesMessage("ICU Admission date cannot be greater than than current date and it should between hospitalized admission date and hospitalized discharge date"));
				}
			}	
				
			if(pEvent.getSource().getName().equalsIgnoreCase("ICU_DISCHARGE"))	
			{
				ICUDischarge = null;
				try {
					if(formObject.getNGValue("ICU_DISCHARGE")!=null)
					{
						ICUDischarge = formatter.parse(formObject.getNGValue("ICU_DISCHARGE"));
					}
					
				} catch (ParseException e) {
					log.WriteToLog(e.toString());
				}
//				if(formObject.getNGValue("ICU_ADMISSION").equalsIgnoreCase(""))
//				{
//					formObject.setNGValue("ICU_DISCHARGE", "");
//					throw new ValidatorException(new FacesMessage("First you have to select ICU admission date."));
//				}
				
				
				if(currentDate !=null && ICUAdmission !=null && ICUDischarge!=null && 
						(currentDate.compareTo(ICUDischarge)<0 || hospitalizedAdmissionDate.compareTo(ICUDischarge)>0 ||hospitalizedDischargeDate.compareTo(ICUDischarge)<0 || ICUDischarge.compareTo(ICUAdmission)<0))
				{
					log.WriteToLog("ICU Discharge Date : " + ICUDischarge);
					formObject.setNGValue("ICU_DISCHARGE", "");
					throw new ValidatorException(new FacesMessage("ICU discharge date cannot be greater than current date or ICU admission date and it should be between hospitalized admission and discharge date"));
				}
				
				
				
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("ASSESSOR_FROM"))
			{
				AssessorFrom = null;
				try {
					if(!formObject.getNGValue("ASSESSOR_FROM").equalsIgnoreCase(""))
					{
						AssessorFrom = formatter.parse(formObject.getNGValue("ASSESSOR_FROM"));
					}
				
				} catch (ParseException e) {
					log.WriteToLog("Java Exception in Assessor from date "+e.toString());
					
				}
				
				if(currentDate!=null && AssessorFrom!=null && hospitalizedAdmissionDate !=null  && hospitalizedDischargeDate!=null && AssessorFrom !=null &&
						(currentDate.compareTo(AssessorFrom)<0 || hospitalizedAdmissionDate.compareTo(AssessorFrom)>0 ||hospitalizedDischargeDate.compareTo(AssessorFrom)<0 ))
				{
					log.WriteToLog("Assessor From Date : "+ AssessorFrom);
					formObject.setNGValue("ASSESSOR_FROM", "");
					throw new ValidatorException(new FacesMessage("Assessor from cannot be greater than date should between hospitalized admission and discharge date"));
					
				}
			}
			
			if(pEvent.getSource().getName().equalsIgnoreCase("ASSESSOR_TO"))
			{
				try {
					if(!formObject.getNGValue("ASSESSOR_TO").equalsIgnoreCase(""))
					{
						AssessorTo = formatter.parse(formObject.getNGValue("ASSESSOR_TO"));
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					log.WriteToLog("Java Excepiton in Assessor to Date : "+e.toString());
				}
//				if(formObject.getNGValue("ASSESSOR_FROM").equalsIgnoreCase(""))
//				{
//					log.WriteToLog("Assessor From Date ::::::::------ "+formObject.getNGValue("ASSESSOR_FROM"));
//					
//					formObject.setNGValue("ASSESSOR_TO", "");
//					throw new ValidatorException(new FacesMessage("First you have to select assessor from date."));
//				}
//				
				if(currentDate !=null && AssessorFrom!=null && AssessorTo!=null && 
						hospitalizedAdmissionDate!=null && hospitalizedDischargeDate!=null&&
						(currentDate.compareTo(AssessorTo)<0 || hospitalizedAdmissionDate.compareTo(AssessorTo)>0
						|| hospitalizedDischargeDate.compareTo(AssessorTo)<0 || AssessorFrom.compareTo(AssessorTo)>0))
				{
					log.WriteToLog("Assessor From Date : "+ AssessorTo);
					formObject.setNGValue("ASSESSOR_TO", "");
					throw new ValidatorException(new FacesMessage("Assessor to date should be less than assesor from date"));
				}
				
		}
			
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker4"))
			{
				checkFutureDate("DatePicker4");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker5"))
			{
				checkFutureDate("DatePicker5");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker6"))
			{
				checkFutureDate("DatePicker6");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker38"))
			{
				checkFutureDate("DatePicker38");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker40"))
			{
				checkFutureDate("DatePicker40");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker39"))
			{
				checkFutureDate("DatePicker39");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker43"))
			{
				checkFutureDate("DatePicker43");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker42"))
			{
				checkFutureDate("DatePicker42");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker41"))
			{
				checkFutureDate("DatePicker41");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker10"))
			{
				checkFutureDate("DatePicker10");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker8"))
			{
				checkFutureDate("DatePicker8");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker9"))
			{
				checkFutureDate("DatePicker9");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker11"))
			{
				checkFutureDate("DatePicker11");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker12"))
			{
				checkFutureDate("DatePicker12");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker13"))
			{
				checkFutureDate("DatePicker13");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DATE_OF_ACCIDENT"))
			{
				checkFutureDate("DATE_OF_ACCIDENT");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker14"))
			{
				checkFutureDate("DatePicker14");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker45"))
			{
				checkFutureDate("DatePicker45");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker44"))
			{
				checkFutureDate("DatePicker44");
			}
			
			if(pEvent.getSource().getName().equalsIgnoreCase("DATE_OF_DIAGNOSIS"))
			{
				checkFutureDate("DATE_OF_DIAGNOSIS");
			}
			
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker16"))
			{
				checkFutureDate("DatePicker16");
			}
			
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker48"))
			{
				checkFutureDate("DatePicker48");
			}
			
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker47"))
			{
				checkFutureDate("DatePicker47");
			}
			
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker23"))
			{
				checkFutureDate("DatePicker23");
			}
			
			if(pEvent.getSource().getName().equalsIgnoreCase("DATE_OF_DISABLEMENT"))
			{
				checkFutureDate("DATE_OF_DISABLEMENT");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker21"))
			{
				checkFutureDate("DatePicker21");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker50"))
			{
				checkFutureDate("DatePicker50");
			}
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker49"))
			{
				checkFutureDate("DatePicker49");
			}
		}
			break;
		default:
			break;
		
		}
		
		
	}

	@Override
	public void formLoaded(FormEvent pEvent) {
		// TODO Auto-generated method stub
		log.WriteToLog("In Form Loaded");
	}

	@Override
	public void formPopulated(FormEvent pEvent) {
		//Today Change
	//	NG_CLM_Logger log =new NG_CLM_Logger("");
		log.WriteToLog("Start of form populated");
		FormConfig formConfigObject = FormContext.getCurrentInstance().getFormConfig();
		FormReference formObject = FormContext.getCurrentInstance().getFormReference();
		String policyNumber = formObject.getNGValue("Policy_No");
		log.WriteToLog("Policy Number :- "+policyNumber);
		String WI_NAME= formObject.getWFWorkitemName();
		formObject.setLocked("POLICY_STATUS", true);
		if(counthospital==0)
		{
		
		
		try {
			currentDate = formatter.parse(formatter.format(currentDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.WriteToLog("Java Exception in Current Date : "+e.toString());
		}
		currentDate.setHours(0);
		currentDate.setMinutes(0);
		currentDate.setSeconds(0);
		
		
		try {

			if(!formObject.getNGValue("HOSP_ADMISSION_DATE").equalsIgnoreCase(""))
			{
				hospitalizedAdmissionDate = formatter.parse(formObject.getNGValue("HOSP_ADMISSION_DATE"));

			}
			if(!formObject.getNGValue("HOSP_DISCHARGE_DATE").equalsIgnoreCase(""))
			{
				hospitalizedDischargeDate = formatter.parse(formObject.getNGValue("HOSP_DISCHARGE_DATE"));
			}
			if(!formObject.getNGValue("ICU_ADMISSION").equalsIgnoreCase(""))
			{
				ICUAdmission = formatter.parse(formObject.getNGValue("ICU_ADMISSION"));
			}
			if(!formObject.getNGValue("ICU_DISCHARGE").equalsIgnoreCase(""))
			{
				ICUDischarge = formatter.parse(formObject.getNGValue("ICU_DISCHARGE"));
			}
			
			if(!formObject.getNGValue("ASSESSOR_FROM").equalsIgnoreCase(""))
			{
				AssessorFrom = formatter.parse(formObject.getNGValue("ASSESSOR_FROM"));
			}
			if(!formObject.getNGValue("ASSESSOR_TO").equalsIgnoreCase(""))
			{
				AssessorTo = formatter.parse(formObject.getNGValue("ASSESSOR_TO"));
			}
			

			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			log.WriteToLog("Java Excetption in Hospital Claim Frame "+e1.toString());
		} 
		counthospital++;
		}
		
		
//		formObject.setLocked("ISSUE_DATE", true);
//		formObject.setLocked("COMMEC_DATE", true);
//		formObject.setLocked("POLICY_MATURITY_DATE", true);
//		formObject.setLocked("LAST_STATUS_CHANGE_DATE", true);
		
//		formObject.setBackcolor("ISSUE_DATE",Color.getColor("#e5e5e5"));
//		formObject.setBackcolor("COMMEC_DATE",Color.getColor("#e5e5e5"));
//		formObject.setBackcolor("POLICY_MATURITY_DATE",Color.getColor("#e5e5e5"));
//		formObject.setBackcolor("LAST_STATUS_CHANGE_DATE",Color.getColor("#e5e5e5"));

		formObject.setNGValue("Text22", "");
		//Death Claim 
		formObject.setNGValue("OptionNo","true");
		//Death Claim waiver
		formObject.setNGValue("Option2","true");
		//Accident Claim
		formObject.setNGValue("Option5","true");
		
		
		Date requirementDate = new Date();
		String requirementDateString = formObject.getNGValue("DatePicker56");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		try{
			if(requirementDateString!=null)
			{
				requirementDate = formatter.parse(requirementDateString); 
				requirementDate.setHours(00);
				requirementDate.setMinutes(00);
				requirementDate.setSeconds(00);
				
				String dateArr[] = requirementDateString.split("/");
				int year = Integer.parseInt(dateArr[0]);
				int month = Integer.parseInt(dateArr[1]);
				int day = Integer.parseInt(dateArr[2]);
				Date date;
				date = new Date(year-1900,month-1,day);
			
				Calendar c = Calendar.getInstance();
				
				
				c.setTime(date);
				
				log.WriteToLog("Todays Date : "+formatter.format(c.getTime()));
				
				c.add(Calendar.DATE, 7);
				formObject.setNGValue("DatePicker55", formatter.format(c.getTime()));
				log.WriteToLog("After 7 Days : "+formatter.format(c.getTime()));
				
				c.add(Calendar.DATE, 7);
				formObject.setNGValue("DatePicker53", formatter.format(c.getTime()));
				log.WriteToLog("After 14 Days : "+formatter.format(c.getTime()));
				
				c.add(Calendar.DATE, 7);
				formObject.setNGValue("DatePicker54", formatter.format(c.getTime()));
				log.WriteToLog("After 21 Days : "+formatter.format(c.getTime()));
						
				
			}
		}
		catch(Exception E)
			{
				log.WriteToLog("Java Exception in Requirment Date : "+requirementDateString);
			}
				
		if((formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Claim_Event_Intimation")
				||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Claim_Registration")
				||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Error_Bucket")
				||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Closed_Cases_Bucket")
				||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Requirement_Scanning")))
		{
			
			String claimOnComboQuery = "Select CLAIM_ON from NG_CLM_ClaimOnID_Master WITH(NOLOCK) where WI_NAME='"+WI_NAME+"'";
			List<List<String>>claimOnComboResult = formObject.getDataFromDataSource(claimOnComboQuery);
			
			if(!claimOnComboResult.isEmpty()){
			log.WriteToLog("Claim on List "+ claimOnComboResult);
			List<String> claimOnId = new ArrayList<String>();
			int k=0;
			
			for(List<String> i : claimOnComboResult)
			{
				log.WriteToLog("Claim on List "+i);
				claimOnId.add(claimOnComboResult.get(k).get(0)); 
				log.WriteToLog("Claim on Value "+claimOnComboResult.get(k).get(0));
				k++;
			}
			formObject.addItem("Combo12", claimOnId);
			formObject.addItem("Combo69", claimOnId);
			formObject.addItem("Combo70", claimOnId);
			formObject.addItem("Combo71", claimOnId);
			formObject.addItem("Combo72", claimOnId);
			formObject.addItem("Combo73", claimOnId);
			}
			String NG_EXT_CLM_DataQuery = "select POLICY_STATUS,format(ISSUE_DATE,'yyyy/MM/dd'),format(COMMEC_DATE,'yyyy/MM/dd'),format(POLICY_MATURITY_DATE,'yyyy/MM/dd'),format(LAST_STATUS_CHANGE_DATE,'yyyy/MM/dd') from NG_CLM_EXT_TABLE WITH(NOLOCK) where WI_NAME='"+WI_NAME+"'" ;
			List<List<String>> NG_EXT_CLM_DataQueryResult = formObject.getDataFromDataSource(NG_EXT_CLM_DataQuery);
			
		//	POLICY_STATUS,ISSUE_DATE,COMMEC_DATE,POLICY_MATURITY_DATE,LAST_STATUS_CHANGE_DATE
			
			formObject.setNGValue("POLICY_STATUS", NG_EXT_CLM_DataQueryResult.get(0).get(0));
			formObject.setNGValue("ISSUE_DATE", NG_EXT_CLM_DataQueryResult.get(0).get(1));
			formObject.setNGValue("COMMEC_DATE", NG_EXT_CLM_DataQueryResult.get(0).get(2));
			formObject.setNGValue("POLICY_MATURITY_DATE", NG_EXT_CLM_DataQueryResult.get(0).get(3));
			formObject.setNGValue("LAST_STATUS_CHANGE_DATE", NG_EXT_CLM_DataQueryResult.get(0).get(4));
		
	//Code for Hiding LoansuranceCount for all the Worksteps		
			String loansuranceCountQuery = "SELECT COUNT(*) NLOANSURANCECNT FROM ODSSTAGGING.Odsstaging.dbo.PRD_KEY_INFO PKI,"+ 
					" STAGNEW2.Staging_claim.dbo.COM_POL_PROD_DTL cppd"+ 
					" WHERE pki.STRPRODCD=cppd.STRPRODCD"+ 
					" and pki.NPRODVER = cppd.NPRODVER"+ 
					" and pki.NVALGRP=2 and cppd.STRPOLNBR='"+policyNumber+"'";
			log.WriteToLog("LoansuranceCountQuery : "+loansuranceCountQuery);
			List<List<String>> loansuranceCountResult = formObject.getDataFromDataSource(loansuranceCountQuery);
			log.WriteToLog("Loansurance Count Result : "+loansuranceCountResult);
			int loansureanceCount = Integer.parseInt(loansuranceCountResult.get(0).get(0));
			log.WriteToLog("Loansurance Count : "+loansureanceCount);
			if(loansureanceCount==1)
			{
				formObject.setVisible("Command22",true);
				formObject.setVisible("Command5", true);	
			}
			else
			{
				formObject.setVisible("Command22",false);
				formObject.setVisible("Command5", false);
			}
			
	//Code for Hiding is Loan Covered
			String isLoanCoveredQuery = "SELECT COUNT(*) nHomesuranceCnt FROM ODSSTAGGING.Odsstaging.dbo.PRD_KEY_INFO pki, STAGNEW2.Staging_claim.dbo.COM_POL_PROD_DTL cppd"+ 
					" where pki.STRPRODCD = cppd.STRPRODCD"+
					" and pki.NPRODVER = cppd.NPRODVER"+ 
					" and pki.NPRDCATG=1"+ 
					" and pki.NISRTA=1"+
					" and cppd.STRPOLNBR='"+policyNumber+"'";
			log.WriteToLog("IsLoanCoveredQuery : "+isLoanCoveredQuery);
			List<List<String>> isLoanCoveredResult = formObject.getDataFromDataSource(isLoanCoveredQuery);
			if(!isLoanCoveredResult.isEmpty())
			{
				log.WriteToLog("IsLoadCoveredResult : "+isLoanCoveredResult);
				int isLoanCovered = Integer.parseInt(isLoanCoveredResult.get(0).get(0));
				log.WriteToLog("IsLoanCovered Count :"+isLoanCovered);
				if(isLoanCovered==0){
					formObject.setVisible("Label76", false);
					formObject.setVisible("Combo1", false);
					formObject.setVisible("Label174", false);
					formObject.setVisible("Combo44", false);
				}
				else
				{
					formObject.setVisible("Label76", true);
					formObject.setVisible("Combo1", true);
					formObject.setVisible("Label174", true);
					formObject.setVisible("Combo44", true);
				}	
			}
			
			String prodlineQuery = "select count(STRPRODLINE) from  STAGNEW2.Staging_claim.dbo.COM_POLICY_M where STRPRODLINE in('U1','UL') and STRPOLNBR='"+policyNumber+"'";
			log.WriteToLog("prodlineQuery : "+prodlineQuery);
			
			//"select STRPRODLINE from  STAGNEW2.Staging_claim.dbo.COM_POLICY_M where STRPRODLINE in('U1','UL') and STRPOLNBR='"+policyNumber+"'";
			
		//Code for Hiding NAVDATE		
			List<List<String>> prodlineResult = formObject.getDataFromDataSource(prodlineQuery);
			log.WriteToLog("Prodline Result : "+prodlineResult);
			
			int countProdline = Integer.parseInt(prodlineResult.get(0).get(0));
			
			if(!prodlineResult.isEmpty())
			{
				if(countProdline==0)
				{
					//Death Claim
					formObject.setVisible("Label31",false);
					formObject.setVisible("DatePicker4", false);
					
					//Death Claim Waiver
					formObject.setVisible("Label179", false);
					formObject.setVisible("DatePicker43", false);
					
					//Accident 
					formObject.setVisible("Label60", false);
					formObject.setVisible("DatePicker11", false);
					
					//TPDB
					formObject.setVisible("Label110", false);
					formObject.setVisible("DatePicker24",false);
					
					
				}
				else
				{
					//Death Claim
					formObject.setVisible("Label31",true);
					formObject.setVisible("DatePicker4", true);
					
					//Death Claim Waiver
					formObject.setVisible("Label179", true);
					formObject.setVisible("DatePicker43", true);
					
					//Accident 
					formObject.setVisible("Label60", true);
					formObject.setVisible("DatePicker11", true);
					
					//TPDB
					formObject.setVisible("Label110", true);
					formObject.setVisible("DatePicker24",true);
					
				}
			}
		
			
			
			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim"))
			{
				log.WriteToLog("In Death Claim Frame for Select Query");
			//	String WI_NAME = formObject.getNGValue("WI_NAME");
				String Query = "SELECT NAV_DATE, CLM_ON, DATE_OF_DEATH, CLM_VALID_NOTIF_DATE, NATURE_OF_DEATH,"
						+ " SUICIDE, PRI_CAUSE_OF_DEATH ,IS_LOAN_COVERED ,CLM_INTIMATION_SRC, CUSTOMER_SIGN, "
						+ "DEATH_CER_ISSUED_FROM, DEATH_CER_ISSUED_TYPE, REG_NUM_OF_DEATH_CER, DATE_OF_DEATH_CER,"
						+ "CERTIFYING_DOCTOR, PRI_HEALTH_STMT, EXACT_CAUSE_OF_DEATH, UNDERLYING_DEATH_CAUSE, "
						+ "AGENT, DEATH_LOCATION, HOSP_NAME ,HOSP_ADMISSION_DATE, HOSP_DISCHARGE_DATE, "
						+ "IC_CODE_1, IC_CODE_2, IC_CODE_3, IC_CODE_4, IC_CODE_5, IC_CODE_6 "
						+ "FROM NG_CLM_Ext_Table WITH(NOLOCK) where  WI_NAME = '"+WI_NAME+"'";
				log.WriteToLog("In Death Claim Frame Executing Select Query"+ Query);
				List<List<String>> NG_CLM_Ext_Table_Data = formObject.getDataFromDataSource(Query);
				log.WriteToLog("Death Claim Frame Select Query Result: "+NG_CLM_Ext_Table_Data);
				
				if(NG_CLM_Ext_Table_Data.get(0).get(0)!=null)
				{
					formObject.setNGValue("DatePicker4",NG_CLM_Ext_Table_Data.get(0).get(0).replaceAll("-", "/").substring(0, 10));
				}
			
				
				formObject.setNGValue("Combo12",NG_CLM_Ext_Table_Data.get(0).get(1));
				if(NG_CLM_Ext_Table_Data.get(0).get(2)!=null)
				{
					formObject.setNGValue("DatePicker5",NG_CLM_Ext_Table_Data.get(0).get(2).replaceAll("-", "/").substring(0, 10));
				}
				
				if(NG_CLM_Ext_Table_Data.get(0).get(3)!=null)
				{
					formObject.setNGValue("DatePicker6",NG_CLM_Ext_Table_Data.get(0).get(3).replaceAll("-", "/").substring(0, 10));
				}
				
				formObject.setNGValue("Combo4",NG_CLM_Ext_Table_Data.get(0).get(4));
				formObject.setNGValue("Option1",NG_CLM_Ext_Table_Data.get(0).get(5));
				formObject.setNGValue("Combo27",NG_CLM_Ext_Table_Data.get(0).get(6));
				formObject.setNGValue("Combo1",NG_CLM_Ext_Table_Data.get(0).get(7));
				formObject.setNGValue("Combo24",NG_CLM_Ext_Table_Data.get(0).get(8));
				formObject.setNGValue("Combo30",NG_CLM_Ext_Table_Data.get(0).get(9));
				formObject.setNGValue("Combo25",NG_CLM_Ext_Table_Data.get(0).get(10));
				formObject.setNGValue("Combo31",NG_CLM_Ext_Table_Data.get(0).get(11));
				formObject.setNGValue("Text91",NG_CLM_Ext_Table_Data.get(0).get(12));
			
				if(NG_CLM_Ext_Table_Data.get(0).get(13)!=null)
				{
					formObject.setNGValue("DatePicker38",NG_CLM_Ext_Table_Data.get(0).get(13).replaceAll("-", "/").substring(0, 10));
				}
				
				formObject.setNGValue("Text95",NG_CLM_Ext_Table_Data.get(0).get(14));
				formObject.setNGValue("Combo33",NG_CLM_Ext_Table_Data.get(0).get(15));
				formObject.setNGValue("Combo28",NG_CLM_Ext_Table_Data.get(0).get(16));
				formObject.setNGValue("Text30",NG_CLM_Ext_Table_Data.get(0).get(17));
				formObject.setNGValue("Combo29",NG_CLM_Ext_Table_Data.get(0).get(18));
				formObject.setNGValue("Combo26",NG_CLM_Ext_Table_Data.get(0).get(19));
				formObject.setNGValue("Text92",NG_CLM_Ext_Table_Data.get(0).get(20));
				if(NG_CLM_Ext_Table_Data.get(0).get(21)!=null)
				{
					formObject.setNGValue("DatePicker40",NG_CLM_Ext_Table_Data.get(0).get(21).replaceAll("-", "/").substring(0, 10));	
				}
				
				if(NG_CLM_Ext_Table_Data.get(0).get(22)!=null)
				{
					formObject.setNGValue("DatePicker39",NG_CLM_Ext_Table_Data.get(0).get(22).replaceAll("-", "/").substring(0, 10));	
				}
				
				formObject.setNGValue("Text12",NG_CLM_Ext_Table_Data.get(0).get(23));
				formObject.setNGValue("Text13",NG_CLM_Ext_Table_Data.get(0).get(24));
				formObject.setNGValue("Text23",NG_CLM_Ext_Table_Data.get(0).get(25));
				formObject.setNGValue("Text24",NG_CLM_Ext_Table_Data.get(0).get(26));
				formObject.setNGValue("Text26",NG_CLM_Ext_Table_Data.get(0).get(27));
				formObject.setNGValue("Text25",NG_CLM_Ext_Table_Data.get(0).get(28));		
			}
			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Accidental Death Claim"))
			{			
				//String WI_NAME = formObject.getNGValue("WI_NAME");
				log.WriteToLog("In Accident Frame : ");
				
				String Query = "SELECT NAV_DATE,CLM_ON,DATE_OF_DEATH, CLM_VALID_NOTIF_DATE, "
						+ "NATURE_OF_DEATH,SUICIDE,CLM_INTIMATION_SRC,DEATH_CER_ISSUED_FROM, "
						+ "DEATH_CER_ISSUED_TYPE,REG_NUM_OF_DEATH_CER,DATE_OF_DEATH_CER,CERTIFYING_DOCTOR,"
						+ "PRI_HEALTH_STMT,EXACT_CAUSE_OF_DEATH,UNDERLYING_DEATH_CAUSE, AGENT, "
						+ "DEATH_LOCATION, HOSP_NAME,HOSP_ADMISSION_DATE, HOSP_DISCHARGE_DATE,CUSTOMER_SIGN,"
						+ "IC_CODE_1,IC_CODE_2, IC_CODE_3,IC_CODE_4, IC_CODE_5, IC_CODE_6 FROM NG_CLM_Ext_Table "
						+ "WITH(NOLOCK) where WI_NAME = '"+WI_NAME+"'";
				log.WriteToLog("In Accident Frame Excuting Select Query: "+Query);
				List<List<String>> NG_CLM_Ext_Table_Data = formObject.getDataFromDataSource(Query);
				log.WriteToLog("Accidental Death Claim Select Query Result : "+NG_CLM_Ext_Table_Data);
				if(NG_CLM_Ext_Table_Data.get(0).get(0)!=null)
				{
					formObject.setNGValue("DatePicker11",NG_CLM_Ext_Table_Data.get(0).get(0).replaceAll("-", "/").substring(0, 10)); //	ACD_NAVDate
				}
				
				formObject.setNGValue("Combo70",NG_CLM_Ext_Table_Data.get(0).get(1));//	ACD_ClaimOn	
				if(NG_CLM_Ext_Table_Data.get(0).get(2)!=null)
				{
					formObject.setNGValue("DatePicker12",NG_CLM_Ext_Table_Data.get(0).get(2).replaceAll("-", "/").substring(0, 10));//ACD_DateOfDeath
				}
				
				if(NG_CLM_Ext_Table_Data.get(0).get(3)!=null)
				{
					formObject.setNGValue("DatePicker13",NG_CLM_Ext_Table_Data.get(0).get(3).replaceAll("-", "/").substring(0, 10));//ACD_ClaimValid	
				}
			
				formObject.setNGValue("Text36",NG_CLM_Ext_Table_Data.get(0).get(4));//ACD_NatureOfDeath
				formObject.setNGValue("Option4",NG_CLM_Ext_Table_Data.get(0).get(5));		//Suicide	
				formObject.setNGValue("Combo46",NG_CLM_Ext_Table_Data.get(0).get(6));//ACD_ClaimIntimation	
				formObject.setNGValue("Combo47",NG_CLM_Ext_Table_Data.get(0).get(7));//ACD_DeathCertificateIssued	
				formObject.setNGValue("Combo51",NG_CLM_Ext_Table_Data.get(0).get(8));//ACD_DeathCertificateType	
				formObject.setNGValue("Text43",NG_CLM_Ext_Table_Data.get(0).get(9));//ACD_RegistrationNumberDeath	
				if(NG_CLM_Ext_Table_Data.get(0).get(10)!=null)
				{
					formObject.setNGValue("DatePicker14",NG_CLM_Ext_Table_Data.get(0).get(10).replaceAll("-", "/").substring(0, 10));//ACD_InsuranceDateDeath		
				}
				
				formObject.setNGValue("Text100",NG_CLM_Ext_Table_Data.get(0).get(11));//ACD_CerifyingDoctor
				formObject.setNGValue("Combo52",NG_CLM_Ext_Table_Data.get(0).get(12));//ACD_PrimaryHealthStmt	
				formObject.setNGValue("Combo48",NG_CLM_Ext_Table_Data.get(0).get(13));//ACD_ExactCauseDeath	
				formObject.setNGValue("Text32",NG_CLM_Ext_Table_Data.get(0).get(14));//ACD_UnderlyingCauseDeath	
				formObject.setNGValue("Combo49",NG_CLM_Ext_Table_Data.get(0).get(15));//ACD_Agent	
				formObject.setNGValue("Combo54",NG_CLM_Ext_Table_Data.get(0).get(16));//	ACD_DeathLocation	
				formObject.setNGValue("Text99",NG_CLM_Ext_Table_Data.get(0).get(17));//ACD_HosptitalName
				
				if(NG_CLM_Ext_Table_Data.get(0).get(18)!=null)
				{
					formObject.setNGValue("DatePicker45",NG_CLM_Ext_Table_Data.get(0).get(18).replaceAll("-", "/").substring(0, 10));//ACD_HospitalAdmission
				}
				
				if(NG_CLM_Ext_Table_Data.get(0).get(19)!=null)
				{
					formObject.setNGValue("DatePicker44",NG_CLM_Ext_Table_Data.get(0).get(19).replaceAll("-", "/").substring(0, 10));//ACD_HospitalDischarge		
				}
				
				formObject.setNGValue("Combo50",NG_CLM_Ext_Table_Data.get(0).get(20));//ACD_CustomerSign						
				formObject.setNGValue("Text34",NG_CLM_Ext_Table_Data.get(0).get(21)); // IC Code 1
				formObject.setNGValue("Text35",NG_CLM_Ext_Table_Data.get(0).get(22)); // IC Code 2
				formObject.setNGValue("Text39",NG_CLM_Ext_Table_Data.get(0).get(23)); // IC Code 3
				formObject.setNGValue("Text40",NG_CLM_Ext_Table_Data.get(0).get(24)); // IC Code 4
				formObject.setNGValue("Text42",NG_CLM_Ext_Table_Data.get(0).get(25)); // IC Code 5
				formObject.setNGValue("Text41",NG_CLM_Ext_Table_Data.get(0).get(26)); // IC Code 6
			}
			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim Waiver of Premium"))
			{
				log.WriteToLog("In Death Claim Waiver of Premium frame for Select Query");
			//	String WI_NAME = formObject.getNGValue("WI_NAME");
				String Query = "SELECT NAV_DATE, CLM_ON, DATE_OF_DEATH, CLM_VALID_NOTIF_DATE, NATURE_OF_DEATH,"
						+ " SUICIDE, PRI_CAUSE_OF_DEATH ,IS_LOAN_COVERED ,CLM_INTIMATION_SRC, CUSTOMER_SIGN, "
						+ "DEATH_CER_ISSUED_FROM, DEATH_CER_ISSUED_TYPE, REG_NUM_OF_DEATH_CER, DATE_OF_DEATH_CER,"
						+ "CERTIFYING_DOCTOR, PRI_HEALTH_STMT, EXACT_CAUSE_OF_DEATH, UNDERLYING_DEATH_CAUSE, "
						+ "AGENT, DEATH_LOCATION, HOSP_NAME ,HOSP_ADMISSION_DATE, HOSP_DISCHARGE_DATE, "
						+ "IC_CODE_1, IC_CODE_2, IC_CODE_3, IC_CODE_4, IC_CODE_5, IC_CODE_6 "
						+ "FROM NG_CLM_Ext_Table WITH(NOLOCK) where  WI_NAME = '"+WI_NAME+"'";
			
				log.WriteToLog("In Death Claim Waiver of Premium frame for Select Query" + Query);
				List<List<String>> NG_CLM_Ext_Table_Data = formObject.getDataFromDataSource(Query);
				log.WriteToLog("Death Claim Waiver of Premium Frame Select Query Result : "+NG_CLM_Ext_Table_Data);
				if(NG_CLM_Ext_Table_Data.get(0).get(0)!=null)
				{
					formObject.setNGValue("DatePicker43",NG_CLM_Ext_Table_Data.get(0).get(0).replaceAll("-", "/").substring(0, 10));//NAV_DATE
				}
				
				formObject.setNGValue("Combo69",NG_CLM_Ext_Table_Data.get(0).get(1));//CLM_ON
				
				if(NG_CLM_Ext_Table_Data.get(0).get(2)!=null)
				{
					formObject.setNGValue("DatePicker42",NG_CLM_Ext_Table_Data.get(0).get(2).replaceAll("-", "/").substring(0, 10));//DATE_OF_DEATH	
				}
				
				if(NG_CLM_Ext_Table_Data.get(0).get(3)!=null)
				{
					formObject.setNGValue("DatePicker41",NG_CLM_Ext_Table_Data.get(0).get(3).replaceAll("-", "/").substring(0, 10));//CLM_VALID_NOTIF_DATE
				}
			
				formObject.setNGValue("Combo5",NG_CLM_Ext_Table_Data.get(0).get(4));//NATURE_OF_DEATH
				formObject.setNGValue("Option3",NG_CLM_Ext_Table_Data.get(0).get(5));//SUICIDE
				formObject.setNGValue("Combo32",NG_CLM_Ext_Table_Data.get(0).get(6));//PRI_CAUSE_OF_DEATH
				formObject.setNGValue("Combo44",NG_CLM_Ext_Table_Data.get(0).get(7));//IS_LOAN_COVERED
				formObject.setNGValue("Combo43",NG_CLM_Ext_Table_Data.get(0).get(8));//CLM_INTIMATION_SRC
				formObject.setNGValue("Combo39",NG_CLM_Ext_Table_Data.get(0).get(9));//CUSTOMER_SIGN
				formObject.setNGValue("Combo42",NG_CLM_Ext_Table_Data.get(0).get(10));//DEATH_CER_ISSUED_FROM
				formObject.setNGValue("Combo38",NG_CLM_Ext_Table_Data.get(0).get(11));//DEATH_CER_ISSUED_TYPE
				formObject.setNGValue("Text19",NG_CLM_Ext_Table_Data.get(0).get(12));//REG_NUM_OF_DEATH_CER
				
				if(NG_CLM_Ext_Table_Data.get(0).get(13)!=null)
				{
					formObject.setNGValue("DatePicker10",NG_CLM_Ext_Table_Data.get(0).get(13).replaceAll("-", "/").substring(0, 10));//DATE_OF_DEATH_CER
				}
				
				formObject.setNGValue("Text15",NG_CLM_Ext_Table_Data.get(0).get(14));//CERTIFYING_DOCTOR
				formObject.setNGValue("Combo37",NG_CLM_Ext_Table_Data.get(0).get(15));//PRI_HEALTH_STMT
				formObject.setNGValue("Combo41",NG_CLM_Ext_Table_Data.get(0).get(16));//EXACT_CAUSE_OF_DEATH
				formObject.setNGValue("Text14",NG_CLM_Ext_Table_Data.get(0).get(17));//UNDERLYING_DEATH_CAUSE
				formObject.setNGValue("Combo40",NG_CLM_Ext_Table_Data.get(0).get(18));//AGENT
				formObject.setNGValue("Combo35",NG_CLM_Ext_Table_Data.get(0).get(19));//DEATH_LOCATION
				formObject.setNGValue("Text17",NG_CLM_Ext_Table_Data.get(0).get(20));//HOSP_NAME
				if(NG_CLM_Ext_Table_Data.get(0).get(21)!=null)
				{
					formObject.setNGValue("DatePicker8",NG_CLM_Ext_Table_Data.get(0).get(21).replaceAll("-", "/").substring(0, 10));//HOSP_ADMISSION_DATE
				}
				if(NG_CLM_Ext_Table_Data.get(0).get(22)!=null)
				{
					formObject.setNGValue("DatePicker9",NG_CLM_Ext_Table_Data.get(0).get(22).replaceAll("-", "/").substring(0, 10));//HOSP_DISCHARGE_DATE
				}
			
				
				formObject.setNGValue("Text98",NG_CLM_Ext_Table_Data.get(0).get(23)); //IC Code 1
				formObject.setNGValue("Text33",NG_CLM_Ext_Table_Data.get(0).get(24)); //IC Code 2
				formObject.setNGValue("Text29",NG_CLM_Ext_Table_Data.get(0).get(25)); //IC Code 3
				formObject.setNGValue("Text28",NG_CLM_Ext_Table_Data.get(0).get(26)); //IC Code 4
				formObject.setNGValue("Text20",NG_CLM_Ext_Table_Data.get(0).get(27)); //IC Code 5
				formObject.setNGValue("Text27",NG_CLM_Ext_Table_Data.get(0).get(28)); //IC Code 6
			}
			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Critical Illness/Dreaded Disease Claim"))
			{
		//		String WI_NAME = formObject.getNGValue("WI_NAME");
				
				log.WriteToLog("In Critical Illness/Dreaded Disease Claim Frame for executing select query");
				
				String Query = "SELECT CLM_ON, CLM_VALID_NOTIF_DATE, CLM_INTIMATION_SRC, "
						+ "CUSTOMER_SIGN, AGENT, PRI_HEALTH_STMT, HOSP_NAME, HOSP_ADMISSION_DATE, "
						+ "HOSP_DISCHARGE_DATE, IC_CODE_1, IC_CODE_2, IC_CODE_3, IC_CODE_4, "
						+ "IC_CODE_5,IC_CODE_6 FROM NG_CLM_Ext_Table WITH(NOLOCK) where  WI_NAME = '"+WI_NAME+"'";
				
				log.WriteToLog("In Critical Illness/Dreaded Disease Claim Frame Select Query : "+ Query);
				
				List<List<String>> NG_CLM_Ext_Table_Data = formObject.getDataFromDataSource(Query);
				log.WriteToLog("In Critical Illness/Dreaded Disease Claim Frame Select Query Result : "+NG_CLM_Ext_Table_Data);
				formObject.setNGValue("Combo71",NG_CLM_Ext_Table_Data.get(0).get(0));
				
				if(NG_CLM_Ext_Table_Data.get(0).get(1)!=null)
				{
					formObject.setNGValue("DatePicker16",NG_CLM_Ext_Table_Data.get(0).get(1).replaceAll("-", "/").substring(0, 10));	
				}
				
				formObject.setNGValue("Combo57",NG_CLM_Ext_Table_Data.get(0).get(2));
				formObject.setNGValue("Combo56",NG_CLM_Ext_Table_Data.get(0).get(3));
				formObject.setNGValue("Combo45",NG_CLM_Ext_Table_Data.get(0).get(4));
				formObject.setNGValue("Combo55",NG_CLM_Ext_Table_Data.get(0).get(5));
				formObject.setNGValue("Text101",NG_CLM_Ext_Table_Data.get(0).get(6));
				if(NG_CLM_Ext_Table_Data.get(0).get(7)!=null)
				{
					formObject.setNGValue("DatePicker48",NG_CLM_Ext_Table_Data.get(0).get(7).replaceAll("-", "/").substring(0, 10));
				}
			
				if(NG_CLM_Ext_Table_Data.get(0).get(8)!=null)
				{
					formObject.setNGValue("DatePicker47",NG_CLM_Ext_Table_Data.get(0).get(8).replaceAll("-", "/").substring(0, 10));	
				}
				
				formObject.setNGValue("Text53",NG_CLM_Ext_Table_Data.get(0).get(9)); //IC Code 1
				formObject.setNGValue("Text52",NG_CLM_Ext_Table_Data.get(0).get(10));//IC Code 2
				formObject.setNGValue("Text47",NG_CLM_Ext_Table_Data.get(0).get(11));//IC Code 3
				formObject.setNGValue("Text46",NG_CLM_Ext_Table_Data.get(0).get(12));//IC Code 4
				formObject.setNGValue("Text44",NG_CLM_Ext_Table_Data.get(0).get(13));//IC Code 5
				formObject.setNGValue("Text45",NG_CLM_Ext_Table_Data.get(0).get(14));//IC Code 6
			}
			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Hospitalization Claim"))
			{
				
				
				
				log.WriteToLog("In Hospitalization Claim Frame for select Query: ");
			//	String WI_NAME = formObject.getNGValue("WI_NAME");
				
			
				String Query = "SELECT CLM_ON,CLM_VALID_NOTIF_DATE,CLM_INTIMATION_SRC,CUSTOMER_SIGN,"
						+ " AGENT,PRI_HEALTH_STMT, HOSP_ADMISSION_DATE, HOSP_DISCHARGE_DATE,ICU_ADMISSION,"
						+ " ICU_DISCHARGE, IC_CODE_1, IC_CODE_2, IC_CODE_3, IC_CODE_4, IC_CODE_5, IC_CODE_6 "
						+ " FROM NG_CLM_Ext_Table WITH(NOLOCK) where WI_NAME = '"+WI_NAME+"'";
				
				log.WriteToLog("In Hospitalization Claim Frame Executing select Query: "+Query);
				List<List<String>> NG_CLM_Ext_Table_Data = formObject.getDataFromDataSource(Query);
				log.WriteToLog("In Hospitalization Claim Frame Executing select Query Result :"+NG_CLM_Ext_Table_Data);
				formObject.setNGValue("Combo72",NG_CLM_Ext_Table_Data.get(0).get(0));
				formObject.setNGValue("DatePicker23",NG_CLM_Ext_Table_Data.get(0).get(1).replaceAll("-", "/").substring(0, 10));
				

				formObject.setNGValue("Combo75",NG_CLM_Ext_Table_Data.get(0).get(2));
				formObject.setNGValue("Combo53",NG_CLM_Ext_Table_Data.get(0).get(3));
				formObject.setNGValue("Combo34",NG_CLM_Ext_Table_Data.get(0).get(4));
				formObject.setNGValue("Combo36",NG_CLM_Ext_Table_Data.get(0).get(5));
				
				if(NG_CLM_Ext_Table_Data.get(0).get(6)!=null)
				{
					formObject.setNGValue("HOSP_ADMISSION_DATE",NG_CLM_Ext_Table_Data.get(0).get(6).replaceAll("-", "/").substring(0, 10));
					formObject.setNGValue("HA_Hour", NG_CLM_Ext_Table_Data.get(0).get(6).replaceAll("-", "/").substring(11, 13));
					formObject.setNGValue("HA_Minute", NG_CLM_Ext_Table_Data.get(0).get(6).replaceAll("-", "/").substring(14, 16));
				}
				
				if(NG_CLM_Ext_Table_Data.get(0).get(7)!=null)
				{
					formObject.setNGValue("HOSP_DISCHARGE_DATE",NG_CLM_Ext_Table_Data.get(0).get(7).replaceAll("-", "/").substring(0, 10));
					formObject.setNGValue("HD_Hour", NG_CLM_Ext_Table_Data.get(0).get(7).replaceAll("-", "/").substring(11, 13));
					formObject.setNGValue("HD_Minute", NG_CLM_Ext_Table_Data.get(0).get(7).replaceAll("-", "/").substring(14, 16));
				}
				
				if(NG_CLM_Ext_Table_Data.get(0).get(8)!=null)
				{
					formObject.setNGValue("ICU_ADMISSION",NG_CLM_Ext_Table_Data.get(0).get(8).replaceAll("-", "/").substring(0, 10));
					formObject.setNGValue("IA_Hour", NG_CLM_Ext_Table_Data.get(0).get(8).replaceAll("-", "/").substring(11, 13));
					formObject.setNGValue("IA_Minute", NG_CLM_Ext_Table_Data.get(0).get(8).replaceAll("-", "/").substring(14, 16));
				}
				
				if(NG_CLM_Ext_Table_Data.get(0).get(9)!=null)
				{
					formObject.setNGValue("ICU_DISCHARGE",NG_CLM_Ext_Table_Data.get(0).get(9).replaceAll("-", "/").substring(0, 10));
					formObject.setNGValue("ID_Hour", NG_CLM_Ext_Table_Data.get(0).get(9).replaceAll("-", "/").substring(11, 13));
					formObject.setNGValue("ID_Minute", NG_CLM_Ext_Table_Data.get(0).get(9).replaceAll("-", "/").substring(14, 16));
				}
				
				
				formObject.setNGValue("Text50",NG_CLM_Ext_Table_Data.get(0).get(10)); //IC Code 1
				formObject.setNGValue("Text51",NG_CLM_Ext_Table_Data.get(0).get(11)); //IC Code 2
				formObject.setNGValue("Text56",NG_CLM_Ext_Table_Data.get(0).get(12)); //IC Code 3
				formObject.setNGValue("Text57",NG_CLM_Ext_Table_Data.get(0).get(13)); //IC Code 4
				formObject.setNGValue("Text59",NG_CLM_Ext_Table_Data.get(0).get(14)); //IC Code 5
				formObject.setNGValue("Text58",NG_CLM_Ext_Table_Data.get(0).get(15)); //IC Code 6
			}
			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("TPDB Claim"))
			{
				log.WriteToLog("In TPDB Claim Frame");
		//		String WI_NAME = formObject.getNGValue("WI_NAME");
				String Query = "SELECT NAV_DATE,CLM_ON,CLM_VALID_NOTIF_DATE,CLM_INTIMATION_SRC,"
						+ "CUSTOMER_SIGN,AGENT,PRI_HEALTH_STMT,HOSP_NAME,HOSP_ADMISSION_DATE,"
						+ "HOSP_DISCHARGE_DATE,IC_CODE_1,IC_CODE_2,IC_CODE_3,IC_CODE_4,IC_CODE_5,"
						+ "IC_CODE_6 FROM NG_CLM_Ext_Table WITH(NOLOCK) where WI_NAME = '"+WI_NAME+"'";
				
				log.WriteToLog("In TPDB Claim Frame executing select Query : "+Query);
				List<List<String>> NG_CLM_Ext_Table_Data = formObject.getDataFromDataSource(Query);
				log.WriteToLog("In TPDB Claim Frame Select Query Result : "+NG_CLM_Ext_Table_Data);
				if(NG_CLM_Ext_Table_Data.get(0).get(0)!=null)
				{
					formObject.setNGValue("DatePicker24",NG_CLM_Ext_Table_Data.get(0).get(0).replaceAll("-", "/").substring(0, 10));
				}
				
				formObject.setNGValue("Combo73",NG_CLM_Ext_Table_Data.get(0).get(1));
				
				if(NG_CLM_Ext_Table_Data.get(0).get(2)!=null)
				{
					formObject.setNGValue("DatePicker21",NG_CLM_Ext_Table_Data.get(0).get(2).replaceAll("-", "/").substring(0, 10));
				}
				
				formObject.setNGValue("Combo61",NG_CLM_Ext_Table_Data.get(0).get(3));
				formObject.setNGValue("Combo60",NG_CLM_Ext_Table_Data.get(0).get(4));
				formObject.setNGValue("Combo58",NG_CLM_Ext_Table_Data.get(0).get(5));
				formObject.setNGValue("Combo59",NG_CLM_Ext_Table_Data.get(0).get(6));
				formObject.setNGValue("Text102",NG_CLM_Ext_Table_Data.get(0).get(7));
				
				if(NG_CLM_Ext_Table_Data.get(0).get(8)!=null)
				{
					formObject.setNGValue("DatePicker50",NG_CLM_Ext_Table_Data.get(0).get(8).replaceAll("-", "/").substring(0, 10));
				}
			
				if(NG_CLM_Ext_Table_Data.get(0).get(9)!=null)
				{
					formObject.setNGValue("DatePicker49",NG_CLM_Ext_Table_Data.get(0).get(9).replaceAll("-", "/").substring(0, 10));
				}
				
				formObject.setNGValue("Text64",NG_CLM_Ext_Table_Data.get(0).get(10));
				formObject.setNGValue("Text65",NG_CLM_Ext_Table_Data.get(0).get(11));
				formObject.setNGValue("Text67",NG_CLM_Ext_Table_Data.get(0).get(12));
				formObject.setNGValue("Text66",NG_CLM_Ext_Table_Data.get(0).get(13));
				formObject.setNGValue("Text73",NG_CLM_Ext_Table_Data.get(0).get(14));
				formObject.setNGValue("Text72",NG_CLM_Ext_Table_Data.get(0).get(15));
			 }
			
		//Loansurance Details
			
			String LoansuranceDetailsSelectQuery = "select BALANCE_LOAN_AMT,LOAN_AMT_SANC,IS_NON_PERFORMING,"
					+ " IS_SURETY_GUARANTOR from NG_CLM_Loansurance_Details WITH(NOLOCK) where WI_NAME='"+WI_NAME+"'";
			log.WriteToLog("LoansuranceDetailsSelectQuery : "+LoansuranceDetailsSelectQuery);
			List<List<String>> LoansuranceDetailsResult = formObject.getDataFromDataSource(LoansuranceDetailsSelectQuery);
			if(!LoansuranceDetailsResult.isEmpty())
			{
			log.WriteToLog("LoansuranceDetailsResult : "+LoansuranceDetailsResult);
			formObject.setNGValue("Text81",LoansuranceDetailsResult.get(0).get(0));
			formObject.setNGValue("Text82",LoansuranceDetailsResult.get(0).get(1));
			formObject.setNGValue("Combo16",LoansuranceDetailsResult.get(0).get(2));
			formObject.setNGValue("Combo17",LoansuranceDetailsResult.get(0).get(3));
			}
			
			//Bank Details 

			String bankDetailsUpdateQuery = "select LOAN_AMT_SANC, RATE_OF_INTEREST,LOAN_TENURE, OUT_BALANCE_LOAN,"
					+ " DEATH_INC_OVERDUE,OVERDUE_INTEREST_AMT,	EMI_PAID ,UNPAID_PEN_AMT,"
					+ " DRAWING_POWER from NG_CLM_CEI_Bank_Details WITH(NOLOCK) where WI_NAME='"+WI_NAME+"'";
			log.WriteToLog("bankDetailsUpdateQuery : "+bankDetailsUpdateQuery);
			List<List<String>>bankDetailsResult = formObject.getDataFromDataSource(bankDetailsUpdateQuery);
			log.WriteToLog("bankDetailsResult : "+bankDetailsResult);
			if(!bankDetailsResult.isEmpty())
			{
				formObject.setNGValue("Text84",bankDetailsResult.get(0).get(0));
				formObject.setNGValue("Text85",bankDetailsResult.get(0).get(1));
				formObject.setNGValue("Text89",bankDetailsResult.get(0).get(2));
				formObject.setNGValue("Text88",bankDetailsResult.get(0).get(3));
				formObject.setNGValue("Combo21",bankDetailsResult.get(0).get(4));
				formObject.setNGValue("Text86",bankDetailsResult.get(0).get(5));
				formObject.setNGValue("Combo22",bankDetailsResult.get(0).get(6));
				formObject.setNGValue("Text87",bankDetailsResult.get(0).get(7));
				formObject.setNGValue("Text90",bankDetailsResult.get(0).get(8));
			}
			
			if(formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Error_Bucket")||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Closed_Cases_Bucket"))
			{
				log.WriteToLog("Error and Closed Cases Bucket tab Hiding");
				formObject.setSheetEnable("Tab1", 1, false);
				
			}
			
			if(formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Claim_Registration"))
			{
				// claim number is done by the production team this is just for testing purpose I am getting top claimnumber
				//Claim Number
				
				
				String claimNumberExtQuery ="select CLM_NUMBER from NG_CLM_Ext_Table WITH(NOLOCK) where WI_NAME='"+WI_NAME+"'";
				log.WriteToLog("Claim Number Ext Query : "+claimNumberExtQuery);
				List<List<String>>claimNubmerResultExt = formObject.getDataFromDataSource(claimNumberExtQuery);
				log.WriteToLog("claimNubmerResultExt : "+claimNubmerResultExt);	
				
				String claimNumberStaging = "select top 1 STRCLAIMNBR from STAGNEW2.Staging_claim.dbo.CLM_APPLC_DTL WHERE STRPOLNBR = '" +policyNumber+"'" ;
				log.WriteToLog(" claimNumberStaging ---> " + claimNumberStaging);
				List<List<String>> claimNumberResult= formObject.getDataFromDataSource(claimNumberStaging);
				log.WriteToLog(" claimNumberResult ---> " + claimNumberResult);
				
				//String claimNumber = claimNumberResult.get(0).get(0);
				String claimNumber = claimNubmerResultExt.get(0).get(0);
				
				log.WriteToLog("Claim Number "+ claimNumber);
				formObject.setNGValue("CLM_NUMBER",claimNumber );
			
				
				
				//WOP Amount
				String countWOPAmountQuery = "SELECT count(1) FROM ( SELECT 1 as 'col1' FROM STAGNEW2.Staging_claim.dbo.COM_POL_PROD_DTL cppd, "
						+ " STAGNEW2.Staging_claim.dbo.CLM_APPLC_DTL cad, STAGNEW2.Staging_claim.dbo.COM_FEATURE_PROD_MAP cfpm WHERE cppd.STRPOLNBR = '"+policyNumber+"' "
						+ " AND cad.NCLAIMTYPE IN(5,6,13) AND cppd.STRPRODCD=cfpm.STRPRODCD  AND cppd.NPRODVER= cfpm.NPRODVER  "
						+ " AND ((cppd.NPRODTYPE=1 AND CFPM.NFEATURECD in(4,19)) OR (cppd.NPRODTYPE = 2 AND cppd.STRPRODCD LIKE 'WOP\\_%'ESCAPE'\\')) "
						+ " UNION SELECT 1 as 'col1' FROM STAGNEW2.Staging_claim.dbo.COM_POL_PROD_DTL CPPD,STAGNEW2.Staging_claim.dbo.PRD_EVENT_MAP PEM, "
						+ " STAGNEW2.Staging_claim.dbo.PRD_EVENT_BEN_MAP PEBM	WHERE CPPD.STRPOLNBR = '"+policyNumber+"'	AND CPPD.NPRODTYPE=1 AND "
						+ " CPPD.STRPRODCD=PEM.STRPRODCD	AND CPPD.NPRODVER = PEM.NPRODVER AND PEM.LEVENTMAPSEQ = PEBM.LEVENTMAPSEQ AND PEBM.NBENTYPE=11	"
						+ " AND PEM.NCLIENTTYPE=1  AND PEM.NDOESPOLTERMN=0 AND PEM.NDOESPRODTERMN=0	AND PEM.NDOESPRMCEASE=1	) as t";
				
				List<List<String>> countWOPAmountResult = formObject.getDataFromDataSource(countWOPAmountQuery);
				log.WriteToLog("Count WOP Result-----> "+countWOPAmountResult);
				if(!countWOPAmountResult.isEmpty())
				{
					int countWOPAmount = Integer.parseInt(countWOPAmountResult.get(0).get(0));
					log.WriteToLog("Count WOP Amount----> "+countWOPAmountQuery);
					if(countWOPAmount==1)
					{
						String WOPAmountQuery = "select DWOPPRMAMNT from STAGNEW2.Staging_claim.dbo.CLM_WOP_PRM  where STRCLAIMNBR='"+claimNumber+"'"; 
						List<List<String>> WOPAmountQueryResult = formObject.getDataFromDataSource(WOPAmountQuery);
						log.WriteToLog("WOPAmountQuery " + WOPAmountQuery + "WOPAmountResult "+WOPAmountQueryResult );
						if(WOPAmountQueryResult.isEmpty())
						{
							log.WriteToLog("Setting WOP Amount as 0.000");
							formObject.setNGValue("WOP_PREMIUM_AMT", "0.000");
						}
						else
						{
							String WOPAmount = WOPAmountQueryResult.get(0).get(0);
							formObject.setNGValue("WOP_PREMIUM_AMT", WOPAmount);
						}	
					}
					else
					{
						formObject.setVisible("WOP_PREMIUM_AMT", false);
						formObject.setVisible("Label17", false);
					}
				}
				
				
				log.WriteToLog("In Claim Form Populated --> Claim Registration if  --->");
				formObject.setLocked("CLM_NUMBER", true);
				formObject.setBackcolor("CLM_NUMBER",Color.getColor("#e5e5e5"));
				
					
				//Benefit Amount
				String BenefitAmountQuery = "select DBENAMNT from STAGNEW2.Staging_claim.dbo.CLM_WS_PAYABLE_DTL where STRCLAIMNBR='"+claimNumber+"'"; 
				List<List<String>> BenefitAmountQueryResult = formObject.getDataFromDataSource(BenefitAmountQuery);
				if(BenefitAmountQueryResult.isEmpty())
				{
					log.WriteToLog("Setting Benfit Amount as 0");
					formObject.setNGValue("BENEFIT_AMT", "0.000");
				}
				else
				{
					String BenefitAmount = BenefitAmountQueryResult.get(0).get(0);
					formObject.setNGValue("BENEFIT_AMT", BenefitAmount);
					log.WriteToLog(" Benefit Amount ---> " + BenefitAmount);
				}
				//Due Amount is recoverable amount
				String DueAmountQuery = "select sum(DDUEAMNT) from STAGNEW2.Staging_claim.dbo.CLM_WS_RECOVER_DTL where STRCLAIMNBR='"+claimNumber+"'"; 
				List<List<String>> DueAmountResult = formObject.getDataFromDataSource(DueAmountQuery);
				log.WriteToLog("Due Amount Result " +DueAmountResult);
			
				if(DueAmountResult.isEmpty()||DueAmountResult.get(0).get(0)==null)
				{
					log.WriteToLog("Setting Due Amount as 0");
					formObject.setNGValue("RECOVERABLE_AMT", "0.000");
				}
				else
				{
					String DueAmount = DueAmountResult.get(0).get(0);
					formObject.setNGValue("RECOVERABLE_AMT", DueAmount);
					log.WriteToLog(" Due Amount ---> " + DueAmount);
				}
				
				
				//Refund amount is payable amount
				String RefundAmountQuery = "select sum(DREFUNDAMNT) from STAGNEW2.Staging_claim.dbo.CLM_WS_PAYABLE_HDR  where STRCLAIMNBR='"+claimNumber+"'"; 
				List<List<String>> RefundAmountResult = formObject.getDataFromDataSource(RefundAmountQuery);
				if(RefundAmountResult.isEmpty())
				{
					log.WriteToLog("Setting Refund Amount as 0.000");
					formObject.setNGValue("PAYABLE_AMT","0.000");
				}
				else
				{
					
					String RefundAmount = RefundAmountResult.get(0).get(0);
					formObject.setNGValue("PAYABLE_AMT",RefundAmount);
					log.WriteToLog(" Refund Amount ---> " + RefundAmount);
				}
				
				
				//Other Details
				String OtherDetailsQuery  = "select DNETCLAIMAMNT,STRREINSUREDNAME,DREINSUREDAMNT from STAGNEW2.Staging_claim.dbo.CLM_APPLC_DTL  where STRCLAIMNBR='"+claimNumber+"'"; 
				List<List<String>> OtherDetailsResult = formObject.getDataFromDataSource(OtherDetailsQuery);
				if(OtherDetailsResult.isEmpty())
				{
					log.WriteToLog("Setting Total Claim Amount, Primary Claimant Name,Primary Claimant Address, Reinsurer Name, Reinsured Amount as Empty or 0.0");
					formObject.setNGValue("TOTAL_CLM_AMT","0.000");
					formObject.setNGValue("Text21","");
					formObject.setNGValue("Text22", "");
					
				}
				
				else
				{
					if(OtherDetailsResult.get(0).get(0)==null)
					{
						formObject.setNGValue("TOTAL_CLM_AMT", "0.000");
					}
					formObject.setNGValue("TOTAL_CLM_AMT", OtherDetailsResult.get(0).get(0));
					//formObject.setNGValue("PRI_CLMT_NAME", OtherDetailsResult.get(0).get(1));
					//formObject.setNGValue("PRI_CLMT_ADDRESS", OtherDetailsResult.get(0).get(2));
					formObject.setNGValue("Text21",OtherDetailsResult.get(0).get(1));
					formObject.setNGValue("Text22", OtherDetailsResult.get(0).get(2));
				}
				
				log.WriteToLog(	" Total Claim Amount ---> " + OtherDetailsResult.get(0).get(0) +
								"\nName of the Reinsurer ---> " +OtherDetailsResult.get(0).get(1)+
								"\nReinsured Amount ---> "+OtherDetailsResult.get(0).get(2));
			}
		}	
		log.WriteToLog("End of form Populated.");
	}

	@Override
	public void saveFormCompleted(FormEvent pEvent) throws ValidatorException {
		// TODO Auto-generated method stub
		log.WriteToLog("In Save Form Completed");
	}

	@Override
	public void saveFormStarted(FormEvent pEvent) throws ValidatorException {
		// TODO Auto-generated method stub
		log.WriteToLog("In Save Form Started");
	//	FormReference formObject = FormContext.getCurrentInstance().getFormReference();
		//	NG_CLM_Logger log =new NG_CLM_Logger("");
			
//		String WI_NAME =formObject.getWFWorkitemName();
//		FormConfig formConfigObject = FormContext.getCurrentInstance().getFormConfig();
//		String ClaimOn=null;
//		if(formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Claim_Event_Intimation")
//				||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Claim_Registration"))
//		{
//			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim"))
//			{
//				log.WriteToLog("After Getting CLM_TYPE Value as Death Claim");
//				ClaimOn = formObject.getNGValue("Combo12");
//				log.WriteToLog("Death Claim ClaimOn ComboBox Value : "+ClaimOn);
//					
//			}
//			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Accidental Death Claim"))
//			{			
//
//				ClaimOn = formObject.getNGValue("Combo70");
//				log.WriteToLog("Accidental Death Claim ClaimOn ComboBox Value : "+ClaimOn);
//			}
//			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim Waiver of Premium"))
//			{
//				ClaimOn = formObject.getNGValue("Combo69");
//				log.WriteToLog("Death Claim Waiver of Premium Claim On ComboBox Value : "+ClaimOn);
//
//			}
//			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Critical Illness/Dreaded Disease Claim"))
//			{
//				
//				ClaimOn = formObject.getNGValue("Combo71");
//				log.WriteToLog("Critical Illness/Dreaded Disease Claim ClaimOn ComboBox Value : "+ClaimOn);
//			}
//			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Hospitalization Claim"))
//			{
//				
//				
//				ClaimOn = formObject.getNGValue("Combo72");
//				log.WriteToLog("Hospitalization Claim ClaimOn ComboBox Value : "+ClaimOn);
//
//				
//			}
//			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("TPDB Claim"))
//			{
//				ClaimOn = formObject.getNGValue("Combo73");
//				log.WriteToLog("TPDB Claim ClaimOn ComboBox Value : "+ClaimOn);
//				
//			 }
//			String ClaimOnIdQuery= "Select CLAIM_ON_ID from NG_CLM_ClaimOnID_Master WITH(NOLOCK) where CLAIM_ON='"+ClaimOn+"' and WI_NAME = '"+WI_NAME+"'";  
//			log.WriteToLog("Claim ON ID Query"+ClaimOnIdQuery);
//			
//			List<List<String>> ClaimOnIdResult = formObject.getDataFromDataSource(ClaimOnIdQuery);
//			log.WriteToLog("Claim On Id Result : "+ClaimOnIdResult);
//			
//			String claimOnId = ClaimOnIdResult.get(0).get(0);
//			log.WriteToLog("Claim ON id : "+claimOnId);
//			
//			String claimOnIdQuery = "update NG_CLM_Ext_Table set claim_on_id ='"+claimOnId+"' where WI_NAME='"+WI_NAME+"'";
//			formObject.saveDataIntoDataSource(claimOnIdQuery);
//			log.WriteToLog("End of Save form Started");
//			}
	}

	@Override
	public void submitFormCompleted(FormEvent arg0) throws ValidatorException {
	//	NG_CLM_Logger log =new NG_CLM_Logger("");
		log.WriteToLog("Start of Submit Form Completed");
		FormReference formObject = FormContext.getCurrentInstance().getFormReference();
		log.WriteToLog("Activity Name : "+formObject.getWFActivityName());
		log.WriteToLog("In submit form completed");
		
		
		String workItemName = formObject.getWFWorkitemName();
		String decision = formObject.getNGValue("CEI_DECISION");
		
		String remarks = formObject.getNGValue("Remarks");
		String userName = formObject.getUserName();
		String activityName = formObject.getWFActivityName();
		
		log.WriteToLog("WorkItem Name : "+workItemName
						+"\nDecision : "+decision
						+"\nRemarks : "+remarks
						+"\nUsername : "+ userName
						+"\nActivity Name : "+activityName);
		
		
		String procedureName = "NG_CLM_Insert_Dec_History";
		List <String> decisionHistoryParameters = new ArrayList<String>();
		
		decisionHistoryParameters.add("text:"+workItemName);
		decisionHistoryParameters.add("text:"+decision);
		decisionHistoryParameters.add("text:"+remarks);
		decisionHistoryParameters.add("text:"+userName);
		decisionHistoryParameters.add("text:"+activityName);
		log.WriteToLog("Decision History Parameters : "+decisionHistoryParameters);
		formObject.getDataFromStoredProcedure(procedureName,decisionHistoryParameters);
		
		
		log.WriteToLog("Executing NG_CLM_Workstep_TAT_Procedure");
		String workstepTATProcedureName = "NG_CLM_Workstep_TAT_Procedure";
		List <String> workstepTATProcedureParameters = new ArrayList<String>();
		workstepTATProcedureParameters.add("text:"+workItemName);
		workstepTATProcedureParameters.add("text:"+activityName);
		log.WriteToLog("workstepTATProcedureParameters : "+workstepTATProcedureParameters);
		formObject.getDataFromStoredProcedure(workstepTATProcedureName,workstepTATProcedureParameters);
		
		log.WriteToLog("End of Submit Form Completed");
		
		
		
	}

	@Override
	public void submitFormStarted(FormEvent arg0) throws ValidatorException {
	//	NG_CLM_Logger log =new NG_CLM_Logger("");
		log.WriteToLog("Start of Submit Form Started");
		
	
		FormConfig formConfigObject = FormContext.getCurrentInstance().getFormConfig();
		if(formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Claim_Event_Intimation")
				||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Claim_Registration"))
		{
			FormReference formObject = FormContext.getCurrentInstance().getFormReference();
			String WI_NAME = formObject.getWFWorkitemName();
			String policyNumber = formObject.getNGValue("Policy_No");	
			String claimType = formObject.getNGValue("CLM_TYPE");			
			
			log.WriteToLog("Claim Type : "+claimType);
			
			String claimTypeSelectQuery= " select STRCDDESC,NPARAMCD from STAGNEW2.Staging_claim.dbo.COM_PARAM_SYSTEM_M "
					+ " where IPARAMTYPECD='3501'and DPARAMNBR1='1' and STRCDDESC = '"+claimType+"'";
			log.WriteToLog("Claim Type Query : "+claimTypeSelectQuery);
			List<List<String>>  claimTypeSelectResult = formObject.getDataFromDataSource(claimTypeSelectQuery);			
			
			log.WriteToLog("Claim Type Select Result : "+ claimTypeSelectResult);
			
			String claimTypeCode = claimTypeSelectResult.get(0).get(1);
			log.WriteToLog("Claim Type Code : "+claimTypeCode);
			
			
			
			String clientTypeQuery ="select CLIENT_TYPE from NG_CLM_ClaimOnID_Master a,NG_CLM_EXT_TABLE b "
					+ " where a.CLAIM_ON_ID=b.CLAIM_ON_ID and a.WI_NAME=b.WI_NAME and b.WI_NAME = '"+WI_NAME + "'";
//			
			//String clientTypeQuery ="select b.NCLIENTTYPE from STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME a,"
//					+ " STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK b where a.STRCLIENTCD = b.STRCLIENTCD "
//					+ " and b.STRPOLNBR= '"+policyNumber+"'";
			
			
			log.WriteToLog("Client Type Query : "+ clientTypeQuery);
			List<List<String>> clientTypeResult = formObject.getDataFromDataSource(clientTypeQuery);
			log.WriteToLog("Client Type Result :"+clientTypeResult);
			int clientType = Integer.parseInt(clientTypeResult.get(0).get(0));
			log.WriteToLog("Client Type :"+clientType);
			
//			String policyStatusQuery = "select NPOLSTATCD from  STAGNEW2.Staging_claim.dbo.COM_POLICY_M "
//					+ " where STRPOLNBR='"+policyNumber+"'";
			
			List<List<String>> DateOfEventClaimNumberResult = formObject.getDataFromDataSource("SELECT DATE_OF_EVENT,CLM_NUMBER FROM NG_CLM_EXT_TABLE WITH(NOLOCK) WHERE WI_NAME = '"+WI_NAME+"'");
			String DateOfEvent = DateOfEventClaimNumberResult.get(0).get(0);
			
			String ClaimNumber = DateOfEventClaimNumberResult.get(0).get(0);
			if(ClaimNumber!=null)
			{
				ClaimNumber = "'"+ClaimNumber+"'";
			}
			
			String policyStatusAsOnDateofEventQuery = "SELECT dbo.ng_clm_get_polstatus('"+policyNumber+"',"+ClaimNumber+",'"+DateOfEvent+"')";			
			
			log.WriteToLog("Date Of Event : "+DateOfEvent);
						
			log.WriteToLog("policyStatusAsOnDateofEventQuery to call function :"+policyStatusAsOnDateofEventQuery);
			List<List<String>> policyStatusASOnDOEResult = formObject.getDataFromDataSource(policyStatusAsOnDateofEventQuery);
			
			log.WriteToLog("Policy Status Result : " +policyStatusASOnDOEResult);
			
			int policyStatusAsOnDOE = Integer.parseInt(policyStatusASOnDOEResult.get(0).get(0));
			log.WriteToLog("Policy Status : "+policyStatusAsOnDOE);
			
			//Update the Policy Status as On date of Event in External table
			
			String PolicyStatusAsOnDOEDesc = formObject.getDataFromDataSource("select StatusDesc from NG_CLM_Policy_Status_Desc_Master WITH(NOLOCK) where StatusCode = '"+policyStatusAsOnDOE+"'").get(0).get(0);
			log.WriteToLog("PolicyStatusAsOnDOEDesc : "+PolicyStatusAsOnDOEDesc);
			
			String updatePolicyStatusAsOnDOE = "UPDATE NG_CLM_EXT_TABLE SET POLICY_STATUS_AS_ON_DOE = '"+PolicyStatusAsOnDOEDesc+"' where WI_NAME = '"+WI_NAME+"'";
			log.WriteToLog("updatePolicyStatusAsOnDOE : "+updatePolicyStatusAsOnDOE);
			formObject.saveDataIntoDataSource(updatePolicyStatusAsOnDOE);
		
			String countProductQuery= "select count(*) from  STAGNEW2.Staging_claim.dbo.COM_POL_PROD_DTL a,"
					+ " STAGNEW2.Staging_claim.dbo.CLM_BENEFIT_MASTER b where a.STRPRODCD = b.STRPRODCD and "
					+ " a.STRPOLNBR='"+policyNumber+"' and b.NCLAIMTYPE='"+claimTypeCode+"' "
					+ " and b.NCLIENTTYPE='"+clientType+"' and b.NPOLSTATCD='"+policyStatusAsOnDOE+"'";
			log.WriteToLog("Count Product Query :"+countProductQuery);
			
			List<List<String>> countProductCodeResult = formObject.getDataFromDataSource(countProductQuery);
			log.WriteToLog("Count Product Result : "+countProductCodeResult);
			int countProduct =0;
			if(!countProductCodeResult.isEmpty())
			{
				countProduct = Integer.parseInt(countProductCodeResult.get(0).get(0));
				log.WriteToLog("Count Product : "+countProduct);
			}
			
		
			String countProdlineQuery="select count(*) from  STAGNEW2.Staging_claim.dbo.COM_POLICY_M a,"
					+ " STAGNEW2.Staging_claim.dbo.CLM_BENEFIT_MASTER b where a.STRPRODLINE =b.STRPRODLINE "
					+ " and a.STRPOLNBR='"+policyNumber+"' and b.NCLAIMTYPE='"+claimTypeCode+"' "
					+ " and b.NCLIENTTYPE='"+clientType+"' and b.NPOLSTATCD='"+policyStatusAsOnDOE+"'";
			log.WriteToLog("Count Prodline Query : "+countProdlineQuery);
			
			List<List<String>> countProdlineResult = formObject.getDataFromDataSource(countProdlineQuery);
			log.WriteToLog("Prodline Result : "+countProdlineResult);
			int countProdline=0;
			if(!countProdlineResult.isEmpty())
			{
				 countProdline=Integer.parseInt(countProdlineResult.get(0).get(0));
				log.WriteToLog("Count Prodline : "+countProdline);
			}
							
			if(countProduct==0 && countProdline==0 ) 
			{
					throw new ValidatorException(new FacesMessage("The selected workitem cannot be submitted for "
							+ " policy number:  "+policyNumber + " Client Type : "+clientType +" Claim Type : "+claimType));
			}

			//This code is to give a pop that document has been added to give a popup that document has been added.
//			String newDocIndicatorQuery = "SELECT NEW_DOC_INDICATOR FROM NG_CLM_Ext_Table with(NOLOCK) WHERE WI_NAME = '"+WI_NAME+"'"; 
//			
//			String newDocIndicator = formObject.getDataFromDataSource(newDocIndicatorQuery).get(0).get(0);
//			
//			
//			if(newDocIndicator.equalsIgnoreCase("Y"))
//			{
//				String newDocIndicatorUpdateQuery = "update NG_CLM_Ext_Table set NEW_DOC_INDICATOR='N' WHERE WI_NAME = '"+WI_NAME+"'"; 
//				formObject.saveDataIntoDataSource(newDocIndicatorUpdateQuery);		
//				throw new ValidatorException(new FacesMessage("New Document has been fetched press ok to continue"));
//			
//			}
		}
		log.WriteToLog("End of Submit Form Started");
		}
	
	public void checkFutureDate(String datepickerId)
	{
		FormReference formObject = FormContext.getCurrentInstance().getFormReference();
	//	NG_CLM_Logger log =new NG_CLM_Logger("");	
		if(!formObject.getNGValue(datepickerId).equalsIgnoreCase(""))
			{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				Date currentDate = new Date(); 
				currentDate.setHours(0);
				currentDate.setMinutes(0);
				currentDate.setSeconds(0);
				
				Date tempDate=null;
				try {
					tempDate = formatter.parse(formObject.getNGValue(datepickerId));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					log.WriteToLog("Java Exception :" + e.toString());
				}
				if(currentDate!=null && tempDate !=null && currentDate.compareTo(tempDate)<0)
				{
					formObject.setNGValue(datepickerId,"");
					throw new ValidatorException(new FacesMessage("Cannot be greater than current date.")); 
				}

			}
		
	}

}
