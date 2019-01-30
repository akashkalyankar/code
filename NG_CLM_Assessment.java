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
		
					28/11/2018		Akash Kalynakar		Loansurance Hiding,IsLoanCovered Hiding and Add Document is Assessment
					17/12/2018		Akash Kalynakar 	With Hospitalization Fields Added
					18/12/2018		Akash Kalynakar 	With Worksheet status condition in assessment form
					19/12/2018		Akash Kalynakar 	With Document Grid Status pending changes in assessment form
					23/11/2018		Akash Kalynakar		with Assessment Requirement and Received	
					26/12/2018		Akash Kalynakar		With Change in Update Client Tab with Fragmement Approach
					27/12/2018		Akash Kalynakar		With FATCA Frame Code,With FATCA Hide show Filelds
					28/12/2018		Akash Kalynakar		With blank Date changes in intimation
					29/12/2018		Akash Kalynakar		With all blank dates handling
					31/12/2018		Akash Kalynakar		With no combo addition on proceed button
					01/01/2019		Akash Kalynakar		With Requirment date exception handling
					02/01/2019		Akash Kalynakar		FATCA Frame Changes,Fatca dropdowns			
					07/01/2019		Akash Kalynakar		Address Email and Telephone Fragment Codes
					08/01/2019		Akash Kalynakar		qFragCpx and Appointee Details Save
					09/01/2019		Akash Kalynakar		Rejection Reason
					10/01/2019		Akash Kalynakar		With Loansurance Frame Details submit button in assessment
	This Code is Without Update Client Changes				
----------------------------------------------------------------------------------------------------------------------------

	Functions with there usages

	1.	datePopulation(String dateOfEventString, String dateOfReinstatementString, String dateOfPolicyRiskString)

			Description 	: 	This function is used to calculated the duration between two dates in months and days form 
								so that	it can be populated on the form
			Return Value	: 	Void 
			Usage			:	datePopulation(String dateOfEventString, String dateOfReinstatementString, String dateOfPolicyRiskString)
	
	2.	

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

public class NG_CLM_Assessment implements FormListener {
	NG_CLM_Logger log;
	int countPolicyDetails=0;
	int countRelatedPolicy=0;
	int counthospital=0;
	//Date currentDate = new Date();
	Date hospitalizedAdmissionDate = null ;
	Date hospitalizedDischargeDate=null;
	Date ICUDischarge=null;
	Date AssessorFrom=null ;
	Date AssessorTo=null;
	Date ICUAdmission=null;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
	Date currentDate = new Date();
	String itemindex;
	@Override
	public void continueExecution(String arg0, HashMap<String, String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventDispatched(ComponentEvent pEvent) throws ValidatorException {
		// TODO Auto-generated method stub
		FormConfig formConfigObject = FormContext.getCurrentInstance().getFormConfig();
		FormReference formObject = FormContext.getCurrentInstance().getFormReference();
		String WI_NAME = formObject.getWFWorkitemName();
		log = new NG_CLM_Logger(formObject.getWFWorkitemName());
		
//		if(counthospital==0)
//		{
//			log.WriteToLog("Count Hospital : "+counthospital);
//			String itemindexQuery = "SELECT itemindex FROM NG_CLM_EXT_TABLE WITH (NOLOCK) WHERE WI_NAME='" + WI_NAME+ "'";
//	        log.WriteToLog("ItemIndex Query : "+itemindexQuery);
//			List<List<String>> itemindexResult = formObject.getDataFromDataSource(itemindexQuery);
//	        log.WriteToLog("ItemIndex Result :"+itemindexResult);
//			itemindex = itemindexResult.get(0).get(0);
//			log.WriteToLog("itemindex : "+itemindex);
//	
//		try {
//			currentDate = formatter.parse(formatter.format(currentDate));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			log.WriteToLog("Java Exception in Current Date : "+e.toString());
//		}
//		
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
//		//		AssessorTo = formatter.parse(formObject.getNGValue("ASSESSOR_TO"));
//			}
//			
//
//			
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			log.WriteToLog("Java Excetption in Hospital Claim Frame "+e1.toString());
//		} 
//			counthospital++;
//			log.WriteToLog("Count Hospital : "+counthospital);
//		}
		
		switch(pEvent.getType())
		{
		case VALUE_CHANGED:
		{
			if(pEvent.getSource().getName().equalsIgnoreCase("DatePicker57"))
			{
				Date requirementDate = null;
				
				Date date;
				
				String requirementDateString = formObject.getNGValue("DatePicker57");
				
				log.WriteToLog("requirementDateString :- "+requirementDateString);
				try{
					if(requirementDateString!=null)
						{
							log.WriteToLog("if condition requiremnt date"+requirementDateString);
						//	if(requirementDateString!=null)
							{
								requirementDate = formatter.parse(requirementDateString); 
								requirementDate.setHours(00);
								requirementDate.setMinutes(00);
								requirementDate.setSeconds(00);	
							}					
						}
							
					}
				catch(Exception E)
					{
						log.WriteToLog("Java Exception in Requirement Date "+ E.toString());
					}
				log.WriteToLog("Before If Condition Comaparing Current Date "+currentDate +" and Requirement Date "+requirementDate);
////				if(currentDate.compareTo(requirementDate)==0)
////				{
////					throw new ValidatorException(new FacesMessage("Requirement Date Cannot be Equal to Current Date"));
////				}
				if(currentDate!=null && requirementDate !=null && currentDate.compareTo(requirementDate)>0)
				{
					log.WriteToLog("Comaparing Current Date "+currentDate +" and Requirement Date "+requirementDate);
					formObject.setNGValue("DatePicker57", "");
					formObject.setNGValue("DatePicker65", "");
					formObject.setNGValue("DatePicker58", "");
					formObject.setNGValue("DatePicker60", "");
	
					throw new ValidatorException(new FacesMessage("Requirement Date Cannot be Less than Current Date"));
				}
			
				if(!formObject.getNGValue("DatePicker57").equalsIgnoreCase(""))
				{
					String dateArr[] = requirementDateString.split("/");
					int year = Integer.parseInt(dateArr[0]);
					int month = Integer.parseInt(dateArr[1]);
					int day = Integer.parseInt(dateArr[2]);
					date = new Date(year-1900,month-1,day);
				
					Calendar c = Calendar.getInstance();
					
					
					c.setTime(date);
////					log.WriteToLog("Todays Date : "+formatter.format(c.getTime()));
					
					c.add(Calendar.DATE, 7);
					formObject.setNGValue("DatePicker65", formatter.format(c.getTime()));
////					log.WriteToLog("After 7 Days : "+formatter.format(c.getTime()));
					
					c.add(Calendar.DATE, 7);
					formObject.setNGValue("DatePicker58", formatter.format(c.getTime()));
////					log.WriteToLog("After 14 Days : "+formatter.format(c.getTime()));
					
					c.add(Calendar.DATE, 7);
					formObject.setNGValue("DatePicker60", formatter.format(c.getTime()));
////					log.WriteToLog("After 21 Days : "+formatter.format(c.getTime()));
				}
				
					
			}
		break;
		}
	
			case MOUSE_CLICKED:
			{
				
				if(pEvent.getSource().getName().equalsIgnoreCase("Save"))
				{
					formObject.RaiseEvent("WFSave",true);
					String claimsDecision = formObject.getNGValue("CLAIMS_DECISION");
				
					if(claimsDecision.equalsIgnoreCase("Claim Rejected"))
							{
								String rejectionReason = formObject.getNGValue("Combo84");
								String rejectionReasonUpdateQuery = "UPDATE NG_CLM_Ext_Table set REJECTION_REASON = '"+rejectionReason+"' FROM NG_CLM_Ext_Table where WI_NAME ='"+WI_NAME+"'";
								List<List<String>>rejectionReasonResult = formObject.getDataFromDataSource(rejectionReasonUpdateQuery);
								log.WriteToLog("Rejection Reason Update Query : "+rejectionReasonUpdateQuery);								
							}					
					
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim"))
					{
						log.WriteToLog("In Death Claim Frame :" );
						
						//formObject.setNGValue("Text21","DC Check");
							
						String DC_NAVDate = formObject.getNGValue("DatePicker16");
						if(DC_NAVDate.equalsIgnoreCase(""))
						{
							DC_NAVDate=null;
						}
						else
						{
							DC_NAVDate=" '"+DC_NAVDate+"' ";
						}	
						String DC_ClaimOn = formObject.getNGValue("Combo121");
						String DC_DateOfDeath = formObject.getNGValue("DatePicker17");
						if(DC_DateOfDeath.equalsIgnoreCase(""))
						{
							DC_DateOfDeath=null;
						}
						else
						{
							DC_DateOfDeath = " '"+DC_DateOfDeath+"' ";
						}	
						String DC_ClaimValid = formObject.getNGValue("DatePicker18");
						if(DC_ClaimValid.equalsIgnoreCase(""))
						{
							DC_ClaimValid=null;
						}
						else
						{
							DC_ClaimValid=" '"+DC_ClaimValid+"' ";
						}	
						String DC_NatureOfDeath = formObject.getNGValue("Combo29");
						String DC_Suicide = formObject.getNGValue("Option1");
						String DC_PrimaryCause = formObject.getNGValue("Combo28");
						String DC_IsLoanCovered = formObject.getNGValue("Combo18");
						String DC_ClaimIntimationSource = formObject.getNGValue("Combo19");
						String DC_CustomerSign = formObject.getNGValue("Combo23");
						String DC_DeathCertificateFrom = formObject.getNGValue("Combo20");
						String DC_DeathCerificateType = formObject.getNGValue("Combo34");
						String DC_RegNumOfDeathCert = formObject.getNGValue("Text56");
						
						String DC_InsuranceDateDeath = formObject.getNGValue("DatePicker19");
						if(DC_InsuranceDateDeath.equalsIgnoreCase(""))
						{
							DC_InsuranceDateDeath=null;
						}
						else
						{
							DC_InsuranceDateDeath = " '"+DC_InsuranceDateDeath+"' ";
						}	
						
						String DC_CerifyingDoctor = formObject.getNGValue("Text58");
						String DC_PrimaryHealthStmt = formObject.getNGValue("Combo25");
						String DC_ExactCauseDeath = formObject.getNGValue("Combo21");
						String DC_UnderlyingCauseDeath = formObject.getNGValue("Text144");
						String DC_Agent = formObject.getNGValue("Combo29");
						String DC_DeathLocation = formObject.getNGValue("Combo27");
						String DC_HosptitalName = formObject.getNGValue("Text57");
						
						String DC_HospitalAdmission = formObject.getNGValue("DatePicker21");
						if(DC_HospitalAdmission.equalsIgnoreCase(""))
						{
							DC_HospitalAdmission=null;
						}
						else
						{
							DC_HospitalAdmission=" '"+DC_HospitalAdmission+"' ";
						}	
						String DC_HospitalDischarge = formObject.getNGValue("DatePicker20");
						if(DC_HospitalDischarge.equalsIgnoreCase(""))
						{
							DC_HospitalDischarge=null;
						}
						else
						{
							DC_HospitalDischarge = " '"+DC_HospitalDischarge+"' ";
						}	
						String DC_ICCode1=formObject.getNGValue("Text46");
						String DC_ICCode2=formObject.getNGValue("Text47");
						String DC_ICCode3=formObject.getNGValue("Text50");
						String DC_ICCode4=formObject.getNGValue("Text51");
						String DC_ICCode5=formObject.getNGValue("Text55");
						String DC_ICCode6=formObject.getNGValue("Text54");
						
						String Query = "update NG_CLM_Ext_Table set NAV_DATE = "+DC_NAVDate
								+" ,CLM_ON = '"+DC_ClaimOn									+"' ,DATE_OF_DEATH = "+DC_DateOfDeath					
								+" ,CLM_VALID_NOTIF_DATE = "+DC_ClaimValid					+" ,NATURE_OF_DEATH = '"+DC_NatureOfDeath				
								+"' ,SUICIDE = '"+DC_Suicide								+"' ,PRI_CAUSE_OF_DEATH = '"+DC_PrimaryCause			
								+"' ,IS_LOAN_COVERED = '"+DC_IsLoanCovered					+"' ,CLM_INTIMATION_SRC = '"+DC_ClaimIntimationSource		
								+"' ,CUSTOMER_SIGN = '"+DC_CustomerSign						+"' ,DEATH_CER_ISSUED_FROM = '"+DC_DeathCertificateFrom
								+"' ,DEATH_CER_ISSUED_TYPE = '"+DC_DeathCerificateType		+"' ,REG_NUM_OF_DEATH_CER = '"+DC_RegNumOfDeathCert	
								+"' ,DATE_OF_DEATH_CER = "+DC_InsuranceDateDeath			+"  ,CERTIFYING_DOCTOR = '"+DC_CerifyingDoctor			
								+"' ,PRI_HEALTH_STMT = '"+DC_PrimaryHealthStmt				+"' ,EXACT_CAUSE_OF_DEATH = '"+DC_ExactCauseDeath		
								+"' ,UNDERLYING_DEATH_CAUSE = '"+DC_UnderlyingCauseDeath	+"' ,AGENT = '"+DC_Agent			
								+"' ,DEATH_LOCATION = '"+DC_DeathLocation					+"' ,HOSP_NAME = '"+DC_HosptitalName			
								+"' ,HOSP_ADMISSION_DATE = "+DC_HospitalAdmission			+" ,HOSP_DISCHARGE_DATE = "+DC_HospitalDischarge
								+" ,IC_CODE_1 = '"+DC_ICCode1								+"' ,IC_CODE_2 = '"+DC_ICCode2		
								+"' ,IC_CODE_3 = '"+DC_ICCode3								+"' ,IC_CODE_4 = '"+DC_ICCode4		
								+"' ,IC_CODE_5 = '"+DC_ICCode5								+"' ,IC_CODE_6 = '"+DC_ICCode6	
								+"' where WI_NAME = '"+WI_NAME+"'";
						log.WriteToLog("In Death Claim Frame Update Query : "+Query);
						
						//formObject.saveDataIntoDataSource("update NG_CLM_Ext_Table set IC_CODE_6 ='dc6'" );
						formObject.saveDataIntoDataSource(Query);
					}
					
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Accidental Death Claim"))
					{
						log.WriteToLog("In Accidental Death Claim Frame : ");
					
						String ACD_NAVDate = formObject.getNGValue("DatePicker28");
						if(ACD_NAVDate.equalsIgnoreCase(""))
						{
							ACD_NAVDate=null;
						}
						else
						{
							ACD_NAVDate=" '"+ACD_NAVDate+"' ";
						}	
						String ACD_ClaimOn = formObject.getNGValue("Combo123");
						
						String ACD_DateOfDeath = formObject.getNGValue("DatePicker29");
						if(ACD_DateOfDeath.equalsIgnoreCase(""))
						{
							ACD_DateOfDeath= null;
						}
						else
						{
							ACD_DateOfDeath=" '"+ACD_DateOfDeath+"' ";
						}	
						
						String ACD_ClaimValid = formObject.getNGValue("DatePicker30");
						if(ACD_ClaimValid.equalsIgnoreCase(""))
						{
							ACD_ClaimValid=null;
						}
						else
						{
							ACD_ClaimValid=" '"+ACD_ClaimValid+"' ";
						}	
						String ACD_NatureOfDeath = formObject.getNGValue("Text51");
						String ACD_Suicide = formObject.getNGValue("Option5");
						String ACD_ClaimIntimation = formObject.getNGValue("Combo42");			
						String ACD_DeathCertificateIssued = formObject.getNGValue("Combo43");
						String ACD_DeathCertificateType = formObject.getNGValue("Combo47");
						String ACD_RegistrationNumberDeath = formObject.getNGValue("Text79");
						String ACD_InsuranceDateDeath = formObject.getNGValue("DatePicker31");
						if(ACD_InsuranceDateDeath.equalsIgnoreCase(""))
						{
							ACD_InsuranceDateDeath=null;
						}
						else
						{
							ACD_InsuranceDateDeath=" '"+ACD_InsuranceDateDeath+"' ";
						}	
						String ACD_CerifyingDoctor = formObject.getNGValue("Text81");
						String ACD_PrimaryHealthStmt = formObject.getNGValue("Combo48");
						String ACD_ExactCauseDeath = formObject.getNGValue("Combo44");
						String ACD_UnderlyingCauseDeath = formObject.getNGValue("Text154");
						String ACD_Agent = formObject.getNGValue("Combo45");
						String ACD_DeathLocation = formObject.getNGValue("Combo50");
						String ACD_HosptitalName = formObject.getNGValue("Text80");
						
						String ACD_HospitalAdmission = formObject.getNGValue("DatePicker33");
						if(ACD_HospitalAdmission.equalsIgnoreCase(""))
						{
							ACD_HospitalAdmission=null;
						}
						else
						{
							ACD_HospitalAdmission=" '"+ACD_HospitalAdmission+"' ";
						}	
						String ACD_HospitalDischarge = formObject.getNGValue("DatePicker32");
						if(ACD_HospitalDischarge.equalsIgnoreCase(""))
						{
							ACD_HospitalDischarge = null;
						}
						else
						{
							ACD_HospitalDischarge = " '"+ACD_HospitalDischarge+"' ";
						}
						String ACD_CustomerSign = formObject.getNGValue("Combo46");
						String ACD_ICCode1=formObject.getNGValue("Text34");
						String ACD_ICCode2=formObject.getNGValue("Text35");
						String ACD_ICCode3=formObject.getNGValue("Text39");
						String ACD_ICCode4=formObject.getNGValue("Text40");
						String ACD_ICCode5=formObject.getNGValue("Text42");
						String ACD_ICCode6=formObject.getNGValue("Text41");
						//IF ACD_Suicide is true then Suicide is Yes
			
						String Query = "update NG_CLM_Ext_Table set IC_CODE_1 = '"+ACD_ICCode1
								+"' , IC_CODE_2 = '"+ACD_ICCode2	+"',IC_CODE_3 = '"+ACD_ICCode3
								+"' ,IC_CODE_4 = '"+ACD_ICCode4		+"' ,IC_CODE_5 = '"+ACD_ICCode5
								+"' ,IC_CODE_6 = '"+ACD_ICCode6		+"',SUICIDE = '"+ACD_Suicide
								+"' ,NAV_DATE = "+ACD_NAVDate		+" ,CLM_ON = '"+ACD_ClaimOn
								+"' ,CLM_VALID_NOTIF_DATE = "+ACD_ClaimValid		+" ,NATURE_OF_DEATH = '"+ACD_NatureOfDeath
								+"' ,DATE_OF_DEATH = "+ACD_DateOfDeath		+" ,CLM_INTIMATION_SRC = '"+ACD_ClaimIntimation
								+"' ,CUSTOMER_SIGN = '"+ACD_CustomerSign	+"' ,DEATH_CER_ISSUED_FROM = '"+ACD_DeathCertificateIssued
								+"' ,DEATH_CER_ISSUED_TYPE = '"+ACD_DeathCertificateType	+"' ,REG_NUM_OF_DEATH_CER = '"+ACD_RegistrationNumberDeath
								+"' ,DATE_OF_DEATH_CER = "+ACD_InsuranceDateDeath	+" ,CERTIFYING_DOCTOR = '"+ACD_CerifyingDoctor
								+"' ,PRI_HEALTH_STMT = '"+ACD_PrimaryHealthStmt	+"' ,EXACT_CAUSE_OF_DEATH = '"+ACD_ExactCauseDeath
								+"' ,UNDERLYING_DEATH_CAUSE = '"+ACD_UnderlyingCauseDeath	+"' ,AGENT = '"+ACD_Agent
								+"' ,DEATH_LOCATION = '"+ACD_DeathLocation	+"' ,HOSP_NAME = '"+ACD_HosptitalName
								+"' ,HOSP_ADMISSION_DATE = "+ACD_HospitalAdmission	+" ,HOSP_DISCHARGE_DATE = "+ACD_HospitalDischarge
								+" where WI_NAME = '"+WI_NAME+"'";	
						log.WriteToLog("In Accidental Death Claim Frame Update Query : "+Query);
						
						formObject.saveDataIntoDataSource(Query);
					}
					
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim Waiver of Premium"))
					{
						log.WriteToLog("In Death Claim Waiver of Premium Frame");
								
						String DCW_NAVDate = formObject.getNGValue("DatePicker27");
						if(DCW_NAVDate.equalsIgnoreCase(""))
						{
							DCW_NAVDate=null;
						}
						else
						{
							DCW_NAVDate=" '"+DCW_NAVDate+"' ";
						}	
						String DCW_ClaimOn = formObject.getNGValue("Combo122");
						
						String DCW_DateOfDeath = formObject.getNGValue("DatePicker26");
						if(DCW_DateOfDeath.equalsIgnoreCase(""))
						{
							DCW_DateOfDeath =null;
						}
						else
						{
							DCW_DateOfDeath = " '"+DCW_DateOfDeath+"' ";
						}
						String DCW_ClaimValid = formObject.getNGValue("DatePicker25");
						if(DCW_ClaimValid.equalsIgnoreCase(""))
						{
							DCW_ClaimValid=null;
						}
						else
						{
							DCW_ClaimValid = " '"+DCW_ClaimValid+"' ";
						}	
						String DCW_NatureOfDeath = formObject.getNGValue("Combo41");
						String DCW_Suicide = formObject.getNGValue("Option4");
						String DCW_PrimaryCause = formObject.getNGValue("Combo30");
						String DCW_IsLoanCovered = formObject.getNGValue("Combo40");
						String DCW_ClaimIntimationSource = formObject.getNGValue("Combo39");
						String DCW_CustomerSign = formObject.getNGValue("Combo35");
						String DCW_DeathCertificateFrom = formObject.getNGValue("Combo38");
						String DCW_DeathCerificateType = formObject.getNGValue("Combo34");
						String DCW_RegNumOfDeathCert = formObject.getNGValue("Text61");
						
						String DCW_InsuranceDateDeath = formObject.getNGValue("DatePicker24");
						if(DCW_InsuranceDateDeath.equalsIgnoreCase(""))
						{
							DCW_InsuranceDateDeath=null;
						}
						else
						{
							DCW_InsuranceDateDeath=" '"+DCW_InsuranceDateDeath+"' ";
						}	
					
						String DCW_CerifyingDoctor = formObject.getNGValue("Text59");
						String DCW_PrimaryHealthStmt = formObject.getNGValue("Combo33");
						String DCW_ExactCauseDeath = formObject.getNGValue("Combo37");
						String DCW_UnderlyingCauseDeath = formObject.getNGValue("Text145");
						String DCW_Agent = formObject.getNGValue("Combo36");
						String DCW_DeathLocation = formObject.getNGValue("Combo31");
						String DCW_HosptitalName = formObject.getNGValue("Text60");
						
						String DCW_HospitalAdmission = formObject.getNGValue("DatePicker22");
						if(DCW_HospitalAdmission.equalsIgnoreCase(""))
						{
							DCW_HospitalAdmission=null;
						}
						else
						{
							DCW_HospitalAdmission=" '"+DCW_HospitalAdmission+"' ";
						}
						
						String DCW_HospitalDischarge = formObject.getNGValue("DatePicker23");
						if(DCW_HospitalDischarge.equalsIgnoreCase(""))
						{
							DCW_HospitalDischarge=null;
						}
						else
						{
							DCW_HospitalDischarge=" '"+DCW_HospitalDischarge+"' ";
						}	
						
						String DCW_ICCode1=formObject.getNGValue("Text68");
						String DCW_ICCode2=formObject.getNGValue("Text67");
						String DCW_ICCode3=formObject.getNGValue("Text65");
						String DCW_ICCode4=formObject.getNGValue("Text64");
						String DCW_ICCode5=formObject.getNGValue("Text62");
						String DCW_ICCode6=formObject.getNGValue("Text63");
						
						String Query = "update NG_CLM_Ext_Table set NAV_DATE = "+DCW_NAVDate
								+" ,CLM_ON = '"+DCW_ClaimOn								+"' ,DATE_OF_DEATH = "+DCW_DateOfDeath					
								+" ,CLM_VALID_NOTIF_DATE = "+DCW_ClaimValid					+" ,NATURE_OF_DEATH = '"+DCW_NatureOfDeath				
								+"' ,SUICIDE = '"+DCW_Suicide								+"' ,PRI_CAUSE_OF_DEATH = '"+DCW_PrimaryCause			
								+"' ,IS_LOAN_COVERED = '"+DCW_IsLoanCovered					+"' ,CLM_INTIMATION_SRC = '"+DCW_ClaimIntimationSource		
								+"' ,CUSTOMER_SIGN = '"+DCW_CustomerSign					+"' ,DEATH_CER_ISSUED_FROM = '"+DCW_DeathCertificateFrom
								+"' ,DEATH_CER_ISSUED_TYPE = '"+DCW_DeathCerificateType		+"' ,REG_NUM_OF_DEATH_CER = '"+DCW_RegNumOfDeathCert	
								+"' ,DATE_OF_DEATH_CER = "+DCW_InsuranceDateDeath			+" ,CERTIFYING_DOCTOR = '"+DCW_CerifyingDoctor			
								+"' ,PRI_HEALTH_STMT = '"+DCW_PrimaryHealthStmt				+"' ,EXACT_CAUSE_OF_DEATH = '"+DCW_ExactCauseDeath		
								+"' ,UNDERLYING_DEATH_CAUSE = '"+DCW_UnderlyingCauseDeath	+"' ,AGENT = '"+DCW_Agent			
								+"' ,DEATH_LOCATION = '"+DCW_DeathLocation					+"' ,HOSP_NAME = '"+DCW_HosptitalName			
								+"' ,HOSP_ADMISSION_DATE = "+DCW_HospitalAdmission			+" ,HOSP_DISCHARGE_DATE = "+DCW_HospitalDischarge
								+" ,IC_CODE_1 = '"+DCW_ICCode1								+"' ,IC_CODE_2 = '"+DCW_ICCode2		
								+"' ,IC_CODE_3 = '"+DCW_ICCode3								+"' ,IC_CODE_4 = '"+DCW_ICCode4		
								+"' ,IC_CODE_5 = '"+DCW_ICCode5								+"' ,IC_CODE_6 = '"+DCW_ICCode6	
								+"' where WI_NAME = '"+WI_NAME+"'";
						log.WriteToLog("In Death Claim Waiver of Premium Frame Update Query "+ Query);
					
						formObject.saveDataIntoDataSource(Query);
					}
					
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Critical Illness/Dreaded Disease Claim"))
					{
						
						
						log.WriteToLog("Critical Illness/Dreaded Disease Claim : ");
					
						String CIC_ClaimOn = formObject.getNGValue("Combo124");
						
						String CIC_ClaimValid = formObject.getNGValue("DatePicker35");
						if(CIC_ClaimValid.equalsIgnoreCase(""))
						{
							CIC_ClaimValid=null;
						}
						else
						{
							CIC_ClaimValid=" '"+CIC_ClaimValid+"' ";
						}	
						String CIC_ClaimIntimationSource = formObject.getNGValue("Combo55");
						String CIC_CustomerSign = formObject.getNGValue("Combo54");
						String CIC_Agent = formObject.getNGValue("Combo52");
						String CIC_PrimaryHealthStmt = formObject.getNGValue("Combo53");
						String CIC_HosptitalName = formObject.getNGValue("Text89");
						String CIC_HospitalAdmission = formObject.getNGValue("DatePicker39");
						if(CIC_HospitalAdmission.equalsIgnoreCase(""))
						{
							CIC_HospitalAdmission=null;
						}
						else
						{
							CIC_HospitalAdmission=" '"+CIC_HospitalAdmission+"' ";
						}	
						String CIC_HospitalDischarge = formObject.getNGValue("DatePicker38");
						if(CIC_HospitalDischarge.equalsIgnoreCase(""))
						{
							CIC_HospitalDischarge=null;
						}
						else
						{
							CIC_HospitalDischarge=" '"+CIC_HospitalDischarge+"' ";
						}
						
						String CIC_ICCode1=formObject.getNGValue("Text88");
						String CIC_ICCode2=formObject.getNGValue("Text87");
						String CIC_ICCode3=formObject.getNGValue("Text85");
						String CIC_ICCode4=formObject.getNGValue("Text84");
						String CIC_ICCode5=formObject.getNGValue("Text82");
						String CIC_ICCode6=formObject.getNGValue("Text83");
						
						String Query = "update NG_CLM_Ext_Table set CLM_ON = '"+CIC_ClaimOn	+"' ,CLM_VALID_NOTIF_DATE = "+CIC_ClaimValid	
								+",CLM_INTIMATION_SRC = '"+CIC_ClaimIntimationSource		+"' ,CUSTOMER_SIGN = '"+CIC_CustomerSign	
								+"',AGENT = '"+CIC_Agent									+"' ,PRI_HEALTH_STMT = '"+CIC_PrimaryHealthStmt	
								+"',HOSP_NAME = '"+CIC_HosptitalName						+"' ,HOSP_ADMISSION_DATE = "+CIC_HospitalAdmission	
								+",HOSP_DISCHARGE_DATE = "+CIC_HospitalDischarge			+"	,IC_CODE_1 = '"+CIC_ICCode1
								+"',IC_CODE_2 = '"+CIC_ICCode2								+"'	,IC_CODE_3 = '"+CIC_ICCode3
								+"',IC_CODE_4 = '"+CIC_ICCode4								+"' ,IC_CODE_5 = '"+CIC_ICCode5
								+"',IC_CODE_6 = '"+CIC_ICCode6				
								+"' where WI_NAME = '"+WI_NAME+"'";
						log.WriteToLog("Critical Illness/Dreaded Disease Claim Frame Update Query: "+Query);
					
						formObject.saveDataIntoDataSource(Query);
					}
					
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Hospitalization Claim"))
					{
						
							log.WriteToLog("In Hospitalization Claim Frame ");
												
							String HC_ClaimOn = formObject.getNGValue("Combo125");
							
							String HC_ClaimValid = formObject.getNGValue("DatePicker40");
							if(HC_ClaimValid.equalsIgnoreCase(""))
							{
								HC_ClaimValid=null;
							}
							else
							{
								HC_ClaimValid=" '"+HC_ClaimValid+"' ";
							}
								
							String HC_ClaimIntimationSource = formObject.getNGValue("Combo49");
							String HC_CustomerSign = formObject.getNGValue("Combo32");
							String HC_Agent = formObject.getNGValue("Combo11");
							String HC_PrimaryHealthStmt = formObject.getNGValue("Combo26");
							
							String HC_HospitalAdmission = formObject.getNGValue("HOSP_ADMISSION_DATE");
							if(HC_HospitalAdmission.equalsIgnoreCase(""))
							{
								HC_HospitalAdmission=null;
							}
							else
							{
								String HA_Hour = formObject.getNGValue("HA_Hour");
								String HA_Minute = formObject.getNGValue("HA_Minute");
								HC_HospitalAdmission = " '" + HC_HospitalAdmission + ' '+ HA_Hour + ':' + HA_Minute +"' ";
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
								HC_HospitalDischarge = " '"+ HC_HospitalDischarge + ' '+ HD_Hour + ':'+HD_Minute+"' ";
							
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
								HC_ICU_Admission = HC_ICU_Admission + ' '+ IA_Hour + ':' + IA_Minute;
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
								HC_ICU_Discharge = HC_ICU_Discharge + ' ' + ID_Hour +':' + ID_Minute;
							}	
															
							String HC_ICCode1=formObject.getNGValue("Text90");
							String HC_ICCode2=formObject.getNGValue("Text91");
							String HC_ICCode3=formObject.getNGValue("Text94");
							String HC_ICCode4=formObject.getNGValue("Text95");
							String HC_ICCode5=formObject.getNGValue("Text97");
							String HC_ICCode6=formObject.getNGValue("Text96");
							
						String Query = "update NG_CLM_Ext_Table set CLM_ON = '"+HC_ClaimOn 
								+"',CLM_VALID_NOTIF_DATE = "+HC_ClaimValid			+" ,HOSP_ADMISSION_DATE = "+HC_HospitalAdmission			
								+",CLM_INTIMATION_SRC = '"+HC_ClaimIntimationSource	+"' ,CUSTOMER_SIGN = '"+HC_CustomerSign	
								+"',AGENT = '"+HC_Agent									+"' ,PRI_HEALTH_STMT = '"+HC_PrimaryHealthStmt	
								+",HOSP_DISCHARGE_DATE = '"+HC_HospitalDischarge	+",ICU_ADMISSION = "+HC_ICU_Admission
								+",ICU_DISCHARGE = "+HC_ICU_Discharge				+",IC_CODE_1 = '"+HC_ICCode1			
								+"',IC_CODE_2 = '"+HC_ICCode2						+"',IC_CODE_3 = '"+HC_ICCode3			
								+"',IC_CODE_4 = '"+HC_ICCode4						+"',IC_CODE_5 = '"+HC_ICCode5			
								+"',IC_CODE_6 = '"+HC_ICCode6						
								+"' where WI_NAME = '"+WI_NAME+"'";
						log.WriteToLog("In Hospitalization Claim Frame Update Query"+Query);
					
						formObject.saveDataIntoDataSource(Query);
					}
					
					if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("TPDB Claim"))
					{
					
						log.WriteToLog("In TPDB Claim Frame" );		
						
						String TPDB_NAVDate = formObject.getNGValue("DatePicker50");
						if(TPDB_NAVDate.equalsIgnoreCase(""))
						{
							TPDB_NAVDate=null;	
						}
						else
						{
							TPDB_NAVDate=" '"+TPDB_NAVDate+"' ";
						}
						String TPDB_ClaimOn = formObject.getNGValue("Combo126");
						
						String TPDB_ClaimValid = formObject.getNGValue("DatePicker47");
						if(TPDB_ClaimValid.equalsIgnoreCase(""))
						{
							TPDB_ClaimValid=null;	
						}
						else
						{
							TPDB_ClaimValid =" '"+TPDB_ClaimValid+"' ";
						}	
						
						String TPDB_ClaimIntimationSource = formObject.getNGValue("Combo116");
						String TPDB_CustomerSign = formObject.getNGValue("Combo115");
						String TPDB_Agent = formObject.getNGValue("Combo113");
						String TPDB_PrimaryHealthStmt = formObject.getNGValue("Combo114");
						String TPDB_HosptitalName = formObject.getNGValue("Text113");
						
						
						String TPDB_HospitalAdmission = formObject.getNGValue("DatePicker56");
						if(TPDB_HospitalAdmission.equalsIgnoreCase(""))
						{
							TPDB_HospitalAdmission=null;
						}
						else
						{
							TPDB_HospitalAdmission=" '"+TPDB_HospitalAdmission+"' ";
						}	
						String TPDB_HospitalDischarge = formObject.getNGValue("DatePicker52");
						if(TPDB_HospitalDischarge.equalsIgnoreCase(""))
						{
							TPDB_HospitalDischarge=null;
						}
						else
						{
							TPDB_HospitalDischarge = " '"+TPDB_HospitalDischarge+"' ";
						}	
						String TPDB_ICCode1=formObject.getNGValue("Text104");
						String TPDB_ICCode2=formObject.getNGValue("Text105");
						String TPDB_ICCode3=formObject.getNGValue("Text108");
						String TPDB_ICCode4=formObject.getNGValue("Text106");
						String TPDB_ICCode5=formObject.getNGValue("Text111");
						String TPDB_ICCode6=formObject.getNGValue("Text110");
						
						String Query = "update NG_CLM_Ext_Table set NAV_DATE = "+TPDB_NAVDate		
								+" ,CLM_ON = '"+TPDB_ClaimOn							+"' ,CLM_VALID_NOTIF_DATE = '"+TPDB_ClaimValid		
								+"' ,CLM_INTIMATION_SRC = '"+TPDB_ClaimIntimationSource	+"' ,CUSTOMER_SIGN = '"+TPDB_CustomerSign			
								+"' ,AGENT = '"+TPDB_Agent								+"' ,PRI_HEALTH_STMT = '"+TPDB_PrimaryHealthStmt	
								+"' ,HOSP_NAME = '"+TPDB_HosptitalName					+"' ,HOSP_ADMISSION_DATE = '"+TPDB_HospitalAdmission	
								+"' ,HOSP_DISCHARGE_DATE = '"+TPDB_HospitalDischarge	+"' ,IC_CODE_1 = '"+TPDB_ICCode1
								+"' ,IC_CODE_2 = '"+TPDB_ICCode2						+"' ,IC_CODE_3 = '"+TPDB_ICCode3
								+"' ,IC_CODE_4 = '"+TPDB_ICCode4						+"' ,IC_CODE_5 = '"+TPDB_ICCode5	
								+"' ,IC_CODE_6 = '"+TPDB_ICCode6
								+"' where WI_NAME = '"+WI_NAME+"'";
						log.WriteToLog("In TPDB Claim Frame Update Query"+Query);
						
						formObject.saveDataIntoDataSource(Query);
						
					}
					
					//This code is resetting new_doc_indicator flag to 'N'
					String newDocIndicatorUpdateQuery = "update NG_CLM_Ext_Table set NEW_DOC_INDICATOR='N' WHERE WI_NAME = '"+WI_NAME+"'"; 
					log.WriteToLog("New Doc Indicator Flag Update Query : "+newDocIndicatorUpdateQuery);
					formObject.saveDataIntoDataSource(newDocIndicatorUpdateQuery);		
					
					
					
				}//end of Save
				
				if(pEvent.getSource().getName().equalsIgnoreCase("Button2"))
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
					PickList pickListObject = formObject.getNGPickList("Button2", "DocumentCode,DescriptionDocument,Mandatory", true, 10,true);
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
				}//End of Button2
				if(pEvent.getSource().getName().equalsIgnoreCase("Type_of_surgery"))
				{
					String query ="Select STRCDDESC from STAGNEW2.Staging_claim.dbo.COM_PARAM_USER_M WHERE IPARAMTYPECD=1024";
					log.WriteToLog("Executing Document List Query for Type of surgery "+ query);
					PickList pickListObject = formObject.getNGPickList("Type_of_surgery", "Type of Surgery", true, 10,true);
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
				}//End of Type of Surgery
				
				
				if(pEvent.getSource().getName().equalsIgnoreCase("Button35"))
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
				
					if(!RelatedPolicyDetails.isEmpty())
					{
						formObject.setNGValue("Text20",RelatedPolicyDetails.get(0).get(0));
						 formObject.setNGValue("Text19", RelatedPolicyDetails.get(0).get(1));
						 formObject.setNGValue("DatePicker11", RelatedPolicyDetails.get(0).get(2).replaceAll("-", "/").substring(0, 10));
						 formObject.setNGValue("DatePicker10", RelatedPolicyDetails.get(0).get(3).replaceAll("-", "/").substring(0, 10));
						 formObject.setNGValue("Text18", RelatedPolicyDetails.get(0).get(4));
						 formObject.setNGValue("DatePicker9", RelatedPolicyDetails.get(0).get(5).replaceAll("-", "/").substring(0, 10));
						 formObject.setNGValue("DatePicker8", RelatedPolicyDetails.get(0).get(6).replaceAll("-", "/").substring(0, 10));
						 formObject.setNGValue("DatePicker7",RelatedPolicyDetails.get(0).get(7).replaceAll("-", "/").substring(0, 10));
						 formObject.setNGValue("Text17",RelatedPolicyDetails.get(0).get(8));
						 formObject.setNGValue("DatePicker6",RelatedPolicyDetails.get(0).get(9).replaceAll("-", "/").substring(0, 10));
						 formObject.setNGValue("Text16","Y");
					}
					 
					 
					 //format(t1.DTPOLSTATCHANGE,'yyyy-MM-dd')
					 
					 String relatedPolicyGridQuery = "select a.STRTITLECD, concat(a.STRFIRSTNAME,' ',a.STRLASTNAME) As STRNAME, b.NCLIENTTYPE,isnull(convert(nvarchar(100),convert(date,c.DTBIRTH)),'') "
					 		+ " from STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME a,  STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK b,  "
					 		+ " STAGNEW2.Staging_claim.dbo.COM_CLIENT_M c WHERE a.STRCLIENTCD = b.STRCLIENTCD "
					 		+ " and b.STRCLIENTCD = c.STRCLIENTCD and  b.STRPOLNBR='" + PolicyNumber +"'";
					 
					 
					 
					 String productDetailQuery = "SELECT concat(a.STRPRODCD,' ' ,a.NPRODVER) AS BASICRIDER ,isnull(convert(nvarchar(100),convert(date,a.DTPRODCOMC)),''), a.NPRODTERM,a.DSA,"
					 		+ " a.NPRODSTATCD,isnull(convert(nvarchar(100),convert(date,a.DTTERMN)),''), concat(a.DMODALPRMAMNT,' / ',b.NPMTMODE) As MODELPREMIUM "
					 		+ " from STAGNEW2.Staging_claim.dbo.COM_POL_PROD_DTL a, STAGNEW2.Staging_claim.dbo.COM_POLICY_M b "
					 		+ " where a.STRPOLNBR=b.STRPOLNBR and a.STRPOLNBR='" + PolicyNumber +"'";
					 
					// if(countPolicyDetails!=1)
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
					 		if (!PolicyDetails.isEmpty()){
							for(List<String> i :PolicyDetails){
								log.WriteToLog("The Policy Deatails "+i);
								formObject.addItem("ListView5", i);	
								}
					 		}
							log.WriteToLog(String.valueOf(formObject.getNGListIndex("ListView5")));
							if(!productDetails.isEmpty()){
							for(List<String> i :productDetails){
								log.WriteToLog("The Product Deatails "+i);
								formObject.addItem("ListView4", i);	
								
								}
							}
				//			countPolicyDetails++;
						}
				}//End of Button35
				if(pEvent.getSource().getName().equalsIgnoreCase("Button38")||
						pEvent.getSource().getName().equalsIgnoreCase("Button39")||
						pEvent.getSource().getName().equalsIgnoreCase("Button42")||
						pEvent.getSource().getName().equalsIgnoreCase("Button43")||
						pEvent.getSource().getName().equalsIgnoreCase("Button45")||
						pEvent.getSource().getName().equalsIgnoreCase("Button46"))
				{

					//	FormReference formObject2 = FormContext.getCurrentInstance().getFormReference();
					log.WriteToLog("Clicked on Related Policies Button.");
				
					

					String ClaimOnQuery = "select CLM_ON from NG_CLM_Ext_Table WITH(NOLOCK) where WI_NAME ='"+WI_NAME+"'";
					log.WriteToLog("Claim On Query : "+ClaimOnQuery);
					List<List<String>> ClaimOnResult = formObject.getDataFromDataSource(ClaimOnQuery); 
					log.WriteToLog("Claim On Result : "+ClaimOnResult);
					String ClaimOn = ClaimOnResult.get(0).get(0);
					log.WriteToLog("Claim On "+ClaimOn);
									
						log.WriteToLog("Claim On : "+ClaimOn);
						String ClaimOnIdQuery= "Select CLAIM_ON_ID from NG_CLM_ClaimOnID_Master where CLAIM_ON='"+ClaimOn+"' and WI_NAME = '"+WI_NAME+"'";  
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
				
					List<List<String>> RelatedPoliciesGridQuery = formObject.getDataFromDataSource(Query);
					log.WriteToLog("Realted Policies Grid Query Result : "+RelatedPoliciesGridQuery);

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
					
					
				}//End of Button 38
				if(pEvent.getSource().getName().equalsIgnoreCase("Button27"))
				{
					//String Query = "select POLICY_NO from NG_CLM_Ext_Table where WI_NAME = '"+ WI_NAME +"'";
		//	List<List<String>> PolicyNumber = formObject.getDataFromDataSource(Query);
					String PolicyNumber = new String();
					
					ListView ListViewObject = formObject.getComponent("ListView6");
					int rowIndex = ListViewObject.getSelectedRowIndex();
					int columnIndex = ListViewObject.getSelectedColumnIndex();
					
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
				
					if(!RelatedPolicyDetails.isEmpty()){				 
					 formObject.setNGValue("Text20",RelatedPolicyDetails.get(0).get(0));
					 formObject.setNGValue("Text19", RelatedPolicyDetails.get(0).get(1));
					 formObject.setNGValue("DatePicker11", RelatedPolicyDetails.get(0).get(2).replaceAll("-", "/").substring(0, 10));
					 formObject.setNGValue("DatePicker10", RelatedPolicyDetails.get(0).get(3).replaceAll("-", "/").substring(0, 10));
					 formObject.setNGValue("Text18", RelatedPolicyDetails.get(0).get(4));
					 formObject.setNGValue("DatePicker9", RelatedPolicyDetails.get(0).get(5).replaceAll("-", "/").substring(0, 10));
					 formObject.setNGValue("DatePicker8", RelatedPolicyDetails.get(0).get(6).replaceAll("-", "/").substring(0, 10));
					 formObject.setNGValue("DatePicker7",RelatedPolicyDetails.get(0).get(7).replaceAll("-", "/").substring(0, 10));
					 formObject.setNGValue("Text17",RelatedPolicyDetails.get(0).get(8));
					 formObject.setNGValue("DatePicker6",RelatedPolicyDetails.get(0).get(9).replaceAll("-", "/").substring(0, 10));
					 formObject.setNGValue("Text16","Y");
					}
					 //format(t1.DTPOLSTATCHANGE,'yyyy-MM-dd')
					 
					 String relatedPolicyGridQuery = "select a.STRTITLECD, concat(a.STRFIRSTNAME,' ',a.STRLASTNAME) As STRNAME, b.NCLIENTTYPE,isnull(convert(nvarchar(100),convert(date,c.DTBIRTH)),'') "
					 		+ " from STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME a,  STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK b,  "
					 		+ " STAGNEW2.Staging_claim.dbo.COM_CLIENT_M c WHERE a.STRCLIENTCD = b.STRCLIENTCD "
					 		+ " and b.STRCLIENTCD = c.STRCLIENTCD and  b.STRPOLNBR='" + PolicyNumber +"'";
					 
					 
					 
					 String productDetailQuery = "SELECT concat(a.STRPRODCD,' ' ,a.NPRODVER) AS BASICRIDER ,isnull(convert(nvarchar(100),convert(date,a.DTPRODCOMC)),''), a.NPRODTERM,a.DSA,"
					 		+ " a.NPRODSTATCD,isnull(convert(nvarchar(100),convert(date,a.DTTERMN)),''), concat(a.DMODALPRMAMNT,' / ',b.NPMTMODE) As MODELPREMIUM "
					 		+ " from STAGNEW2.Staging_claim.dbo.COM_POL_PROD_DTL a, STAGNEW2.Staging_claim.dbo.COM_POLICY_M b "
					 		+ " where a.STRPOLNBR=b.STRPOLNBR and a.STRPOLNBR='" + PolicyNumber +"'";
					 
					// if(countPolicyDetails!=1)
					 
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
					 	if(!PolicyDetails.isEmpty()){
					 	for(List<String> i :PolicyDetails){
								log.WriteToLog("The Policy Deatails "+i);
								formObject.addItem("ListView5", i);	
								
								}
					 }
							log.WriteToLog(String.valueOf(formObject.getNGListIndex("ListView5")));
							if(!productDetails.isEmpty()){
							for(List<String> i :productDetails){
								log.WriteToLog("The Product Deatails "+i);
								formObject.addItem("ListView4", i);	
								
								}
							}
					//		countPolicyDetails++;
						}
				}//End of Button27
					
				
				
				
				if(pEvent.getSource().getName().equalsIgnoreCase("Command27"))
				{
					String policyNumber = formObject.getNGValue("Policy_No");
					log.WriteToLog("Policy Number : "+policyNumber);
					log.WriteToLog("In Command27:");
					
					ListView ListViewObject = formObject.getComponent("ListView13");
					
					int rowIndex = ListViewObject.getSelectedRowIndex();
					int columnIndex = 6;
					log.WriteToLog("Row Index : "+rowIndex+ "Column Index : "+columnIndex);
					String clientCode = ListViewObject.getValueAt(rowIndex, columnIndex	);
					//Client Code
					formObject.setNGValue("Text112", clientCode);
					log.WriteToLog("Client Code is : "+clientCode);
					String DetailsOfNomineeQuery = "select STRFIRSTNAME,STRMIDDLENAME,STRLASTNAME,STRPREFERREDNAME from STAGNEW2.Staging_claim.dbo.com_client_name where STRCLIENTCD='"+clientCode+"'"; 
					log.WriteToLog("DetailsOfNomineeQuery : "+DetailsOfNomineeQuery);
					List<List<String>>DetailsOfNomineeResult = formObject.getDataFromDataSource(DetailsOfNomineeQuery);
					log.WriteToLog("Details of Nominee Result : "+DetailsOfNomineeResult);
					if(!DetailsOfNomineeResult.isEmpty()){
					//First Name 124
					 if(DetailsOfNomineeResult.get(0).get(0)!=null)
					 {
						 formObject.setNGValue("Text124", DetailsOfNomineeResult.get(0).get(0));
					 }
					//Middle Name 119
					if(DetailsOfNomineeResult.get(0).get(1) !=null)
							{
						formObject.setNGValue("Text119", DetailsOfNomineeResult.get(0).get(1));
							}
					//Last Name 117
					if(DetailsOfNomineeResult.get(0).get(2) !=null)
							{
						formObject.setNGValue("Text117", DetailsOfNomineeResult.get(0).get(2));
							}
					//Preferred Name 120
					if(DetailsOfNomineeResult.get(0).get(3) !=null)
							{
						formObject.setNGValue("Text120", DetailsOfNomineeResult.get(0).get(3));
							}
					}
					String TitleQuery = "select STRCDDESC FROM STAGNEW2.Staging_claim.dbo.com_param_user_m, STAGNEW2.Staging_claim.dbo.com_client_name a WHERE " 
					+"Convert(nvarchar(10),STRPARAMCD)=Convert(nvarchar(10),a.STRTITLECD) AND IPARAMTYPECD=5502 and a.STRCLIENTCD ='"+clientCode+"'";
					//Title After
					List<List<String>> TitleResult = formObject.getDataFromDataSource(TitleQuery);
					if(!TitleResult.isEmpty())
					{
						if(TitleResult.get(0).get(0)!=null)
						{
						formObject.setNGValue("Text15", formObject.getDataFromDataSource(TitleQuery).get(0).get(0));
						}
					}
					
					String DoYouHavePanCardQuery = "select NISPANCARD from STAGNEW2.Staging_claim.dbo.com_client_m where STRCLIENTCD ='"+clientCode+"'";
					log.WriteToLog("DoYouHavePanCardQuery : "+DoYouHavePanCardQuery);
					List<List<String>> DoYouHavePANCardResult = formObject.getDataFromDataSource(DoYouHavePanCardQuery);
					
					log.WriteToLog("DoYouHavePANCardResult : "+DoYouHavePANCardResult);
					if(!DoYouHavePANCardResult.isEmpty())
					{
					if(DoYouHavePANCardResult.get(0).get(0)!=null)
					{
						formObject.setNGValue("Combo73", DoYouHavePANCardResult.get(0).get(0));
					}
					}
					String PanCardNumberQuery = "select STRPANCARDNO from STAGNEW2.Staging_claim.dbo.com_client_m where STRCLIENTCD ='"+clientCode+"'";
					log.WriteToLog("PanCardNumberQuery : "+PanCardNumberQuery);
					List<List<String>> PANCardNumberResult = formObject.getDataFromDataSource(DoYouHavePanCardQuery);
					log.WriteToLog("PANCardNumberResult : "+PANCardNumberResult);
					if(!PANCardNumberResult.isEmpty())
					{
						if(PANCardNumberResult.get(0).get(0)!=null)
						{
							formObject.setNGValue("Text121", PANCardNumberResult.get(0).get(0));
						}
					}
					String DetailsOfNomineeFrame = "SELECT  t1.ClientName,t1.IDType,t1.IDNumber,t1.Gender,"
							+ " t1.DateOfBirth,t2.STROCCUPNAME "
							+ " FROM  (select concat(a.STRFIRSTNAME ,' ', a.STRMIDDLENAME,' ',a.STRLASTNAME) as ClientName, "
							+ " c.STRCDDESC as IDType, d.STRNEWICNBR as IDNumber, e.STRCDDESC as Gender, "
							+ " format(d.DTBIRTH,'yyyy/MM/dd') as DateOfBirth, b.STRCLIENTCD "
							+ " from STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME a, "
							+ " STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK b, "
							+ " STAGNEW2.Staging_claim.dbo.com_param_user_m c, "
							+ " STAGNEW2.Staging_claim.dbo.com_client_m d, "
							+ " STAGNEW2.Staging_claim.dbo.com_param_system_m e  "
							+ " where a.STRCLIENTCD = b.STRCLIENTCD	and b.STRCLIENTCD = d.STRCLIENTCD"
							+ " and c.IPARAMTYPECD=2524"
							+ " and Convert(nvarchar(10),c.STRPARAMCD)=Convert(nvarchar(10),d.STRNEWICTYPE) "
							+ " and e.IPARAMTYPECD=5504	and Convert(nvarchar(10),e.NPARAMCD)=Convert(nvarchar(10),d.NGENDERCD)	 "
							+ " and b.NCLIENTTYPE='4'"
							+ " and b.STRPOLNBR ='"+policyNumber+"'"
							+ " and d.STRCLIENTCD='"+clientCode+"') t1"
							+ " LEFT JOIN"
							+ " (select b.STROCCUPNAME,a.STRCLIENTCD "
							+ " from STAGNEW2.Staging_claim.dbo.com_client_m a, "
							+ " ODSSTAGGING.Odsstaging.dbo.COM_OCCUPATION_M b "
							+ " where a.STROCCUPCD=b.STROCCUPCD ) t2 "
							+ " on t1.STRCLIENTCD = t2.STRCLIENTCD ";
				log.WriteToLog("DetailsOfNomineeFrame : "+DetailsOfNomineeFrame);	
				List<List<String>> DetailsOfNomineeResultFrame = formObject.getDataFromDataSource(DetailsOfNomineeFrame);
				log.WriteToLog("DetailsOfNomineeResultFrame : "+DetailsOfNomineeResultFrame);
				//Id Type
				if(!DetailsOfNomineeResultFrame.isEmpty()){
				if(DetailsOfNomineeResultFrame.get(0).get(1)!=null)
				{
					formObject.setNGValue("Combo75", DetailsOfNomineeResultFrame.get(0).get(1));
				}
				
				//ID Number  after ComboBox changed to TextBox
				if(DetailsOfNomineeResultFrame.get(0).get(2)!=null)
				{	
					formObject.setNGValue("Text123", DetailsOfNomineeResultFrame.get(0).get(2));
				}
				//Gender
				if(DetailsOfNomineeResultFrame.get(0).get(3)!=null)
				{
					formObject.setNGValue("Combo76", DetailsOfNomineeResultFrame.get(0).get(3));
				}
				//Date of Birth
				if(DetailsOfNomineeResultFrame.get(0).get(4)!=null)
				{
					formObject.setNGValue("DatePicker61", DetailsOfNomineeResultFrame.get(0).get(4));
				}
				//Occupation
				if(DetailsOfNomineeResultFrame.get(0).get(5)!=null)
				{
					
					formObject.setNGValue("Combo78", DetailsOfNomineeResultFrame.get(0).get(4));
				}
				}
				
				String UIDNumberQuery ="select NUIDNO from STAGNEW2.Staging_claim.dbo.com_client_m where STRCLIENTCD='"+clientCode+"'";
				log.WriteToLog("UIDNumberQuery : "+UIDNumberQuery);
				List<List<String>> UIDNumberResult = formObject.getDataFromDataSource(UIDNumberQuery);
				log.WriteToLog("UID Result : "+UIDNumberResult);
				if(!UIDNumberResult.isEmpty())
				{
					if(UIDNumberResult.get(0).get(0)!=null)
					{
						formObject.setNGValue("Text118",UIDNumberResult.get(0).get(0));
					}	
				}
				
				
				String EducationQuery = "Select a.STRCDDESC from  STAGNEW2.Staging_claim.dbo.com_param_user_m a, STAGNEW2.Staging_claim.dbo.com_client_m b where a.IPARAMTYPECD=9972 and a.STRPARAMCD=b.STREDUQUALFCTN and STRCLIENTCD='"+clientCode+"'";
				log.WriteToLog("EducationResult Query : "+EducationQuery);
				List<List<String>> EducationResult = formObject.getDataFromDataSource(EducationQuery);
				log.WriteToLog("Education Result : "+EducationResult);
				if(!EducationResult.isEmpty())
				{
					if(EducationResult.get(0).get(0)!=null)
					{
						formObject.setNGValue("Combo74", EducationResult.get(0).get(0));
					}
				}
			//HNI STATUS	
				String HNIStatusQuery = "Select b.STRCDDESC from STAGNEW2.Staging_claim.dbo.COM_CLIENT_HNI_STATUS a ,STAGNEW2.Staging_claim.dbo.COM_PARAM_USER_M b where a.STRCLIENTCD='1000002633' and b.STRPARAMCD = a.NHNISTATCD and b.IPARAMTYPECD='9332'";
				log.WriteToLog("HNI Status : "+HNIStatusQuery);
			
				List<List<String>> HNIStatusResult =  formObject.getDataFromDataSource(HNIStatusQuery);
				log.WriteToLog("HNI Status Result : "+HNIStatusResult);
				if(!HNIStatusResult.isEmpty()){
					if(HNIStatusResult.get(0).get(0)!=null)
					{
						formObject.setNGValue("Text125", HNIStatusResult.get(0).get(0));
					}
				}
				
				
				//E-Insurance Account		
				String EInsuranceAccountQuery = "select NEIANBR from STAGNEW2.Staging_claim.dbo.com_client_m where STRCLIENTCD='1000002633'";	
				log.WriteToLog("EInsuranceAccount :  "+EInsuranceAccountQuery);
				List<List<String>>EInsuranceAccountResult = formObject.getDataFromDataSource(EInsuranceAccountQuery);
				log.WriteToLog("EInsuranceAccountResult : "+EInsuranceAccountResult);
				if(!EInsuranceAccountResult.isEmpty())	
				{	
					if(EInsuranceAccountResult.get(0).get(0)!=null)
					{
						formObject.setNGValue("Text122",EInsuranceAccountResult.get(0).get(0));					
					}
				}
				//Insurance Rep Code	
				
				String InsuranceRepCode = "select STRCDDESC from  STAGNEW2.Staging_claim.dbo.COM_PARAM_USER_M a, STAGNEW2.Staging_claim.dbo.com_client_m b where b.STRCLIENTCD='1000002633' and b.NIRCD = a.STRPARAMCD and a.IPARAMTYPECD='7001'";		
				log.WriteToLog("InsuranceRepCode : "+InsuranceRepCode);
				List<List<String>>InsuranceRepResult = formObject.getDataFromDataSource(InsuranceRepCode);
				log.WriteToLog("InsuranceRepResult : "+InsuranceRepResult);
				if(!InsuranceRepResult.isEmpty()){
				if(formObject.getDataFromDataSource(InsuranceRepCode).get(0).get(0)!=null)
				{
					formObject.setNGValue("Combo77", formObject.getDataFromDataSource(InsuranceRepCode).get(0).get(0));
				}
				}
				//Relationship
				String RelationshipQuery="select c.STRCDDESC  from STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK a ,STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME b,COM_PARAM_SYSTEM_M c  where c.IPARAMTYPECD='5522' and a.STRCLIENTCD= b.STRCLIENTCD and a.NCLIENTTYPE in ('4','5') and c.NPARAMCD=a.NRELNWITHFIRSTLA and a.STRPOLNBR='4000352273'";
				log.WriteToLog("Relationship Query : "+RelationshipQuery);
				
				List<List<String>>RelationshipResult = formObject.getDataFromDataSource(RelationshipQuery);
				log.WriteToLog("Relationship Result : "+RelationshipResult);
				if(!RelationshipResult.isEmpty())
				{
					formObject.setNGValue("Combo144", RelationshipResult.get(0).get(0));
				}
				
			}//End of Command 27
				if(pEvent.getSource().getName().equalsIgnoreCase("Button51"))
				{
					log.WriteToLog("Clicked on Button4 to add row in the document grid");
					
						formObject.setEnabled("q_document_grid", true);
				
						if(formObject.getNGValue("Combo119").equalsIgnoreCase("---Select---"))
						{
							throw new ValidatorException(new FacesMessage("Please select status of document"));
						}
						else
						{
							formObject.ExecuteExternalCommand("NGAddRow", "q_document_grid");	
						}
						
				}//End of Button51
				//Assignee	
				
				if(pEvent.getSource().getName().equalsIgnoreCase("Command36"))
				{
					String policyNumber = formObject.getNGValue("Policy_No");
					log.WriteToLog("Policy Number : "+policyNumber);
					log.WriteToLog("In Command36:");
					ListView ListViewObject = formObject.getComponent("ListView14");
					
					int rowIndex = ListViewObject.getSelectedRowIndex();
					int columnIndex = 6;
					log.WriteToLog("Row Index : "+rowIndex+ "Column Index : "+columnIndex);
					String clientCode = ListViewObject.getValueAt(rowIndex, columnIndex	);
					//Client Code
					
					formObject.setNGValue("Text13", clientCode);
					log.WriteToLog("Client Code is : "+clientCode);
					
					String DetailsOfNomineeQuery = "select STRFIRSTNAME,STRMIDDLENAME,STRLASTNAME,STRPREFERREDNAME from STAGNEW2.Staging_claim.dbo.com_client_name where STRCLIENTCD='"+clientCode+"'"; 					
					log.WriteToLog("DetailsOfNomineeQuery : "+DetailsOfNomineeQuery);
					List<List<String>>DetailsOfNomineeResult = formObject.getDataFromDataSource(DetailsOfNomineeQuery);
					
					log.WriteToLog("Details of Nominee Result : "+DetailsOfNomineeResult);
					if(!DetailsOfNomineeResult.isEmpty())
					{
					//First Name 124
					 if(DetailsOfNomineeResult.get(0).get(0)!=null)
					 {
						 formObject.setNGValue("Text7", DetailsOfNomineeResult.get(0).get(0));
					 }
					//Middle Name 119
					if(DetailsOfNomineeResult.get(0).get(1) !=null)
							{
						formObject.setNGValue("Text6", DetailsOfNomineeResult.get(0).get(1));
							}
					//Last Name 117
					if(DetailsOfNomineeResult.get(0).get(2) !=null)
							{
						formObject.setNGValue("Text11", DetailsOfNomineeResult.get(0).get(2));
							}
					//Preferred Name 120
					if(DetailsOfNomineeResult.get(0).get(3) !=null)
							{
						formObject.setNGValue("Text5", DetailsOfNomineeResult.get(0).get(3));
							}
					}
					
					String TitleQuery = "select STRCDDESC FROM STAGNEW2.Staging_claim.dbo.com_param_user_m, STAGNEW2.Staging_claim.dbo.com_client_name a WHERE " 
					+"Convert(nvarchar(10),STRPARAMCD)=Convert(nvarchar(10),a.STRTITLECD) AND IPARAMTYPECD=5502 and a.STRCLIENTCD ='"+clientCode+"'";
					//Title After DatePicker has been Chaged to Text
					List<List<String>> TitleResult = formObject.getDataFromDataSource(TitleQuery);
					if(!TitleResult.isEmpty())
					{
						
						formObject.setNGValue("Text53", TitleResult.get(0).get(0));
					
					}
					
					String DoYouHavePanCardQuery = "select NISPANCARD from STAGNEW2.Staging_claim.dbo.com_client_m where STRCLIENTCD ='"+clientCode+"'";
					log.WriteToLog("DoYouHavePanCardQuery : "+DoYouHavePanCardQuery);
					List<List<String>> DoYouHavePANCardResult = formObject.getDataFromDataSource(DoYouHavePanCardQuery);
					log.WriteToLog("DoYouHavePANCardResult : "+DoYouHavePANCardResult);
					if(!DoYouHavePANCardResult.isEmpty())
					{
						if(DoYouHavePANCardResult.get(0).get(0)!=null)
						{
							formObject.setNGValue("Combo8", DoYouHavePANCardResult.get(0).get(0));
						}
					}
					
					
					String PanCardNumberQuery = "select STRPANCARDNO from STAGNEW2.Staging_claim.dbo.com_client_m where STRCLIENTCD ='"+clientCode+"'";
					log.WriteToLog("PanCardNumberQuery : "+PanCardNumberQuery);
					List<List<String>> PANCardNumberResult = formObject.getDataFromDataSource(DoYouHavePanCardQuery);
					log.WriteToLog("PANCardNumberResult : "+PANCardNumberResult);
					if(!PANCardNumberResult.isEmpty())
					{
						if(PANCardNumberResult.get(0).get(0)!=null)
						{
							formObject.setNGValue("Text4", PANCardNumberResult.get(0).get(0));
						}
					}
					String DetailsOfNomineeFrame = "SELECT  t1.ClientName,t1.IDType,t1.IDNumber,t1.Gender,"
							+ " t1.DateOfBirth,t2.STROCCUPNAME "
							+ " FROM  (select concat(a.STRFIRSTNAME ,' ', a.STRMIDDLENAME,' ',a.STRLASTNAME) as ClientName, "
							+ " c.STRCDDESC as IDType, d.STRNEWICNBR as IDNumber, e.STRCDDESC as Gender, "
							+ " format(d.DTBIRTH,'yyyy/MM/dd') as DateOfBirth, b.STRCLIENTCD "
							+ " from STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME a, "
							+ " STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK b, "
							+ " STAGNEW2.Staging_claim.dbo.com_param_user_m c, "
							+ " STAGNEW2.Staging_claim.dbo.com_client_m d, "
							+ " STAGNEW2.Staging_claim.dbo.com_param_system_m e  "
							+ " where a.STRCLIENTCD = b.STRCLIENTCD	and b.STRCLIENTCD = d.STRCLIENTCD"
							+ " and c.IPARAMTYPECD=2524"
							+ " and Convert(nvarchar(10),c.STRPARAMCD)=Convert(nvarchar(10),d.STRNEWICTYPE) "
							+ " and e.IPARAMTYPECD=5504	and Convert(nvarchar(10),e.NPARAMCD)=Convert(nvarchar(10),d.NGENDERCD)	 "
							+ " and b.NCLIENTTYPE='4'"
							+ " and b.STRPOLNBR ='"+policyNumber+"'"
							+ " and d.STRCLIENTCD='"+clientCode+"') t1"
							+ " LEFT JOIN"
							+ " (select b.STROCCUPNAME,a.STRCLIENTCD "
							+ " from STAGNEW2.Staging_claim.dbo.com_client_m a, "
							+ " ODSSTAGGING.Odsstaging.dbo.COM_OCCUPATION_M b "
							+ " where a.STROCCUPCD=b.STROCCUPCD ) t2 "
							+ " on t1.STRCLIENTCD = t2.STRCLIENTCD ";
					
				List<List<String>> DetailsOfNomineeResultFrame = formObject.getDataFromDataSource(DetailsOfNomineeFrame);
				if(!DetailsOfNomineeResultFrame.isEmpty())
				//Id Type
				{
					if(DetailsOfNomineeResultFrame.get(0).get(1)!=null)
				{
					formObject.setNGValue("Combo6", DetailsOfNomineeResultFrame.get(0).get(1));
				}
				
				//ID Number  after ComboBox changed to TextBox
				if(DetailsOfNomineeResultFrame.get(0).get(2)!=null)
				{	
					formObject.setNGValue("Text8", DetailsOfNomineeResultFrame.get(0).get(2));
				}
				//Gender
				if(DetailsOfNomineeResultFrame.get(0).get(3)!=null)
				{
					formObject.setNGValue("Combo5", DetailsOfNomineeResultFrame.get(0).get(3));
				}
				//Date of Birth
				if(DetailsOfNomineeResultFrame.get(0).get(4)!=null)
				{
					formObject.setNGValue("DatePicker4", DetailsOfNomineeResultFrame.get(0).get(4));
				}
				//Occupation
				if(DetailsOfNomineeResultFrame.get(0).get(5)!=null)
				{
					
					formObject.setNGValue("Combo1", DetailsOfNomineeResultFrame.get(0).get(4));
				}
				}
				String UIDNumberQuery ="select NUIDNO from STAGNEW2.Staging_claim.dbo.com_client_m where STRCLIENTCD='"+clientCode+"'";
				log.WriteToLog("UID Number Query : "+UIDNumberQuery);
				List<List<String>> UIDNumberResult = formObject.getDataFromDataSource(UIDNumberQuery);
				log.WriteToLog("UID Result : "+UIDNumberResult);
				if(!UIDNumberResult.isEmpty())
				{
					if(UIDNumberResult.get(0).get(0)!=null)
					{
						formObject.setNGValue("Text10",UIDNumberResult.get(0).get(0));
					}
				}
				
				String EducationQuery = "Select a.STRCDDESC from  STAGNEW2.Staging_claim.dbo.com_param_user_m a, STAGNEW2.Staging_claim.dbo.com_client_m b where a.IPARAMTYPECD=9972 and a.STRPARAMCD=b.STREDUQUALFCTN and STRCLIENTCD='"+clientCode+"'";
				log.WriteToLog("Education Query : "+EducationQuery);
				List<List<String>> EducationResult = formObject.getDataFromDataSource(EducationQuery);
				log.WriteToLog("Education Result :" +EducationResult);
				if(!EducationResult.isEmpty())
				{
					if(EducationResult.get(0).get(0)!=null)
					{
						formObject.setNGValue("Combo7", EducationResult.get(0).get(0));
					}	
				}
				
			//HNI STATUS	
				String HNIStatus = "Select b.STRCDDESC from STAGNEW2.Staging_claim.dbo.COM_CLIENT_HNI_STATUS a ,STAGNEW2.Staging_claim.dbo.COM_PARAM_USER_M b where a.STRCLIENTCD='"+clientCode+"' and b.STRPARAMCD = a.NHNISTATCD and b.IPARAMTYPECD='9332'";
				log.WriteToLog("HNIStatus :"+HNIStatus);
				List<List<String>>HNIStatusResult = formObject.getDataFromDataSource(HNIStatus);
				log.WriteToLog("HNIStatusResult : "+HNIStatusResult);
				if(!HNIStatusResult.isEmpty())
				{
					if(HNIStatusResult.get(0).get(0)!=null)
					{
						formObject.setNGValue("Text3", formObject.getDataFromDataSource(HNIStatus).get(0).get(0));
					}	
				}
				
				
				//E-Insurance Account		
				String EInsuranceAccount = "select NEIANBR from STAGNEW2.Staging_claim.dbo.com_client_m where STRCLIENTCD='"+clientCode+"'";	
				log.WriteToLog("EInsuranceAccount : "+EInsuranceAccount);
				List<List<String>>EInsuranceAccountResult = formObject.getDataFromDataSource(EInsuranceAccount);
				log.WriteToLog("EInsuranceAccountResult : "+EInsuranceAccountResult);
				if(!EInsuranceAccountResult.isEmpty())
				{
					if(EInsuranceAccountResult.get(0).get(0)!=null)
					{
						formObject.setNGValue("Text9",EInsuranceAccountResult.get(0).get(0));
						
						
					}
				}
				
				//Insurance Rep Code	
				String InsuranceRepCode = "select STRCDDESC from  STAGNEW2.Staging_claim.dbo.COM_PARAM_USER_M a, STAGNEW2.Staging_claim.dbo.com_client_m b where b.STRCLIENTCD='"+clientCode+"' and b.NIRCD = a.STRPARAMCD and a.IPARAMTYPECD='7001'";		
				log.WriteToLog("Insurance Rep Code : "+InsuranceRepCode);
				List<List<String>> InsuranceRepResult = formObject.getDataFromDataSource(InsuranceRepCode);
				log.WriteToLog("InsuranceRepResult : "+InsuranceRepResult);
				if(!InsuranceRepResult.isEmpty())
				{
					if(InsuranceRepResult.get(0).get(0)!=null)
					{
						formObject.setNGValue("Combo4", formObject.getDataFromDataSource(InsuranceRepCode).get(0).get(0));
					}
				}
				//Relationship
				String RelationshipQuery="select c.STRCDDESC  from STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK a ,STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME b,COM_PARAM_SYSTEM_M c  where c.IPARAMTYPECD='5522' and a.STRCLIENTCD= b.STRCLIENTCD and a.NCLIENTTYPE in ('4','5') and c.NPARAMCD=a.NRELNWITHFIRSTLA and a.STRPOLNBR='"+policyNumber+"'";
				log.WriteToLog("Relationship Query : "+RelationshipQuery);
				List<List<String>>RelationshipResult = formObject.getDataFromDataSource(RelationshipQuery);
				log.WriteToLog("RelationshipResult : "+RelationshipResult);
				if(!RelationshipResult.isEmpty())
				{
					if(formObject.getDataFromDataSource(RelationshipQuery).get(0).get(0)!=null)
					{
						formObject.setNGValue("Combo143", RelationshipResult.get(0).get(0));
					}
				}
						
			}//End of Command36
				if(pEvent.getSource().getName().equalsIgnoreCase("Button28"))
				{
					log.WriteToLog("Loansurance Submit button is Clicked");
					String OutstandingBalanceLoan = formObject.getNGValue("Text21");
					String LoanAmountSanctioned = formObject.getNGValue("Text22");
					String IsNonPerformingAsset = formObject.getNGValue("Combo12");
					String IsSuretyGurantor = formObject.getNGValue("Combo14");
					
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
					
				}//End of Button28
				if(	pEvent.getSource().getName().equalsIgnoreCase("Command29"))
				{
					log.WriteToLog("In Command29 to fetchFragment Entry ");
					formObject.fetchFragment("frNewClient", "Contract_Details_New_LA", "qFragCpx");
					frag_combo_populate(formObject);
					formObject.setNGFocus("FragCpx_title");
					formObject.setTop("frNewClient",10);
					formObject.setHeight("frNewClient", 3000);
					formObject.setVisible("frNewClient", true);
					log.WriteToLog("In Command29 to fetchFragment Exit ");
					
				}//End of Command34 and 29
				if(pEvent.getSource().getName().equalsIgnoreCase("Command34"))
					{
						log.WriteToLog("In Command34 to fetchFragment Entry");
						formObject.fetchFragment("frNewClient", "Contract_Details_New_LA", "qFragCpx");
						frag_combo_populate(formObject);
						formObject.setNGFocus("FragCpx_title");
						formObject.setTop("frNewClient",10);
						formObject.setHeight("frNewClient", 3000);
						formObject.setVisible("frNewClient", true);
						log.WriteToLog("In Command34 to fetchFragment Exit");
						
					}//End of Command34 and 29
				
				//This is for appointee fragment
				if(pEvent.getSource().getName().equalsIgnoreCase("Command31"))
				{
					System.out.println("Inside Appointee Button");
                    showHideFields(formObject, "ContractDetails~PersonalDetails~Habits~EducationDetails~FinancialDetails~MiscelleanousInformation~GiftInformation~References~Contract_Details_New_Button1~Contract_Details_New_Button2",false);
                  
                    setAppointeeFrame(formObject);
                  
                    fetchAppointee(formObject);
                 
                    populateAppointeedropdown(formObject);
//                    if (!Appointee_Flag) {
//                        populateNomineeDetails(formObject);
//                    }
                   
                    viewAppointeeFrame(formObject);
                    formObject.setNGFocus("Appointee_Fname");
                    
					
				}//End of Command34 and 29
				
				
				if (pEvent.getSource().getName().trim().equalsIgnoreCase("Appointee_saveBTN"))/*save of Appointee */ {
					log.WriteToLog("Inside Appointee_saveBTN ");
                    /*formObject.RaiseEvent("WFSave");
                    waitfor(3);
                    formObject.setVisible("Appointee_Frame", false);
                    formObject.setVisible("Frame7", true);
                    formObject.setVisible("Frame4", true);*/
					
                    formObject.saveFragment("Appointee_Frame");

//                    hide_fields(formObject, "Contract_Details_New_LA_AppointeeFrame");
//                    show_fields(formObject, "ContractDetails~PersonalDetails~Habits~EducationDetails~FinancialDetails~MiscelleanousInformation~GiftInformation~References~Contract_Details_New_Button1~Contract_Details_New_Button2");
//                    int no_rows1 = formObject.getLVWRowCount("ApointeeCpx");
//                    if (no_rows1 > 0) {
//                        Appointee_Flag = true;
//                    } else {
//                        del_tempAppointeeDet(formObject, itemindex);
//                        Appointee_Flag = false;
//                    }


                } 
				
				if (pEvent.getSource().getName().trim().equalsIgnoreCase("Appointee_AddBTN"))/*Appointee Relation Load */ {
						log.WriteToLog("inside Appointee_AddBTN");
                    int no_rows1 = formObject.getLVWRowCount("ApointeeCpx");
                    if (no_rows1 == 0) {
                        formObject.ExecuteExternalCommand("NGAddRow", "ApointeeCpx");

                    } else {
                        throw new ValidatorException(new FacesMessage("Only one Appointee Can be added to one Nominee"));
                    }


                }
				
				 if (pEvent.getSource().getName().trim().equalsIgnoreCase("Appointee_ModBTN"))/*Appointee Relation Load */ {
                    log.WriteToLog("Inside Appointee_ModBTN");
					 formObject.ExecuteExternalCommand("NGModifyRow", "ApointeeCpx");

                 }
				 
				 if (pEvent.getSource().getName().trim().equalsIgnoreCase("Appointee_DelBTN"))/*Appointee Relation Load */ {
                     log.WriteToLog("Inside Appointee_DelBTN");
					 formObject.ExecuteExternalCommand("NGDeleteRow", "ApointeeCpx");
//                     populateNomineeDetails(formObject);
//
//                     String wi_name = formObject.getWFWorkitemName();
//
//                     String queryitemindex = "SELECT itemindex FROM NG_IDBI_NB_EXT_TABLE WITH (NOLOCK) WHERE wi_name='" + wi_name + "'";
//
//                     List listwiname = formObject.getDataFromDataSource(queryitemindex);
//                     ArrayList custlistliwiname = (ArrayList) listwiname.get(0);
//                     String itemindex3 = String.valueOf(custlistliwiname.get(0)).trim();
//
//
//                     String delQuery = "Delete from NG_NB_CLIENT_APPOINTEE_MASTER where  ItemIndex='" + itemindex3 + "'";
//
//                     String delQuery2 = "Delete from NG_NB_CLIENT_APPOINTEE_MASTER where  ItemIndex='" + itemindex3 + "'";
                }
				 
				 
				 
				
				
				
				
				if(pEvent.getSource().getName().equalsIgnoreCase("Contract_Details_New_Button1"))
				{
					log.WriteToLog("In Contract_Details_New_Button1 Save Fragment.");
					formObject.saveFragment("frNewClient");
				}
				
				if(pEvent.getSource().getName().equalsIgnoreCase("Contract_Details_New_LA_FATCABtn"))
				{
					log.WriteToLog("In Contract_Details_New_LA_FATCABtn");
					String hideFields = "ContractDetails~PersonalDetails~Habits~EducationDetails~FinancialDetails~MiscelleanousInformation~GiftInformation~References~Contract_Details_New_Button1~Contract_Details_New_Button1";
				
					showHideFields(formObject,hideFields,false);
					log.WriteToLog("Showing Hiding Fileds");
					//Contract_Details_New_LA_FATCAFrame
					
					formObject.setVisible("contract_Details_New_LA_FATCAFrame",true);
					log.WriteToLog("Fetching contract_Details_New_LA_FATCAFrame");
					
					formObject.setTop("Contract_Details_New_LA_FATCAFrame",30);
					formObject.setLeft("Contract_Details_New_LA_FATCAFrame",2);
					formObject.setWidth("Contract_Details_New_LA_FATCAFrame",635);
					formObject.setHeight("Contract_Details_New_LA_FATCAFrame",750);
					
					formObject.fetchFragment("Contract_Details_New_LA_FATCAFrame", "FATCA", "qFATCACpx");
					populatefatcadropdown(formObject);
				
					
			//		formObject.fetchFragment("Contract_Details_New_LA_FATCAFrame", "FATCA", "qFATCACpx");
					
					formObject.setEnabled("FATCA_Save", true);
					formObject.setEnabled("FATCA_Close", true);
//				
					
					log.WriteToLog("Fetching Fatca frNewClient");
//					formObject.setTop("frNewClient",30);
//					formObject.setLeft("frNewClient",2);
//					formObject.setWidth("frNewClient",635);
//					formObject.setHeight("frNewClient",750);
					

					log.WriteToLog("margins are set for contract_Details_New_LA_FATCAFrame");
//					//Check whether fragment name is FATCA or FATCA(LDPI)
					
				//	formObject.setVisible("contract_Details_New_LA_FATCAFrame",false);			
					
				}
				if (pEvent.getSource().getName().trim().equalsIgnoreCase("Contract_Details_Button1")) {
                    log.WriteToLog("Inside Contract_Details_Button1 click");
                    if (formObject.getNGValue("Contract_Details_Combo1").equalsIgnoreCase("Permanent Address") || formObject.getNGValue("Contract_Details_Combo1").equalsIgnoreCase("Mailing Address1")) {
                        log.WriteToLog("idbifed , NB,Permanent shashank");
                        formObject.setEnabled("pIdContract_Details_New_LA_CheckBox9", true);
                    } else {
                        log.WriteToLog("");
                        formObject.setEnabled("pIdContract_Details_New_LA_CheckBox9", false);
                    }         

                    chk_add_accFATCA(formObject, itemindex);

                    formObject.ExecuteExternalCommand("NGAddRow", "FragCpx_addressCpx");
                }
				if (pEvent.getSource().getName().trim().equalsIgnoreCase("Contract_Details_Button2"))/*Modify address*/ {
                   log.WriteToLog("Inside Modify button of Contract_Details_Button2");
					chk_add_accFATCA(formObject, itemindex);
                      formObject.ExecuteExternalCommand("NGModifyRow", "FragCpx_addressCpx");

                }
				
				if (pEvent.getSource().getName().trim().equalsIgnoreCase("Contract_Details_Button3")) {
                    log.WriteToLog("Inside Delete Button for Contract_Details_Button3");
                    int j = 0;
                    try {
                        j = formObject.getNGListIndex("FragCpx_addressCpx");
                        if (j == -1) {
                            throw new ValidatorException(new FacesMessage("Please select any row in address grid", "FragCpx_addressCpx"));
                        }
                    } catch (Exception e) {
                        throw new ValidatorException(new FacesMessage("Please select any row in address grid", "FragCpx_addressCpx"));
                    }
                    formObject.ExecuteExternalCommand("NGDeleteRow", "FragCpx_addressCpx");
                }
				
				if (pEvent.getSource().getName().trim().equalsIgnoreCase("Contract_Details_Button4")) {
                    log.WriteToLog("Inside telephone Add Button of Contract_Details_Button4");
					formObject.ExecuteExternalCommand("NGAddRow", "FragCpx_telephoneCpx");
                } 
				
				if (pEvent.getSource().getName().trim().equalsIgnoreCase("Contract_Details_Button6")) {
					log.WriteToLog("Inside telephone Delete button tof Contract_Details_Button6 ");
                    int j = 0;
                    try {
                        j = formObject.getNGListIndex("FragCpx_telephoneCpx");
                        if (j == -1) {
                            throw new ValidatorException(new FacesMessage("Please select any row in telephone grid", "FragCpx_telephoneCpx"));
                        }
                    } catch (Exception e) {
                        throw new ValidatorException(new FacesMessage("Please select any row in telephone grid", "FragCpx_telephoneCpx"));
                    }

                    formObject.ExecuteExternalCommand("NGDeleteRow", "FragCpx_telephoneCpx");
                }
				
				if (pEvent.getSource().getName().trim().equalsIgnoreCase("Fragment_Button1")) {
					log.WriteToLog("Email Add Button of Fragment_Button1");
                    formObject.ExecuteExternalCommand("NGAddRow", "FragCpx_emailCpx");
				}
				
				if (pEvent.getSource().getName().trim().equalsIgnoreCase("Fragment_Button3")) {
					log.WriteToLog("Emaild Delete Button of Fragment_Button3 ");
                    int j = 0;
                    try {
                        j = formObject.getNGListIndex("FragCpx_emailCpx");
                        if (j == -1) {
                            throw new ValidatorException(new FacesMessage("Please select any row in email grid", "FragCpx_emailCpx"));
                        }
                    } catch (Exception e) {
                        throw new ValidatorException(new FacesMessage("Please select any row in telephone grid", "FragCpx_emailCpx"));
                    }

                    formObject.ExecuteExternalCommand("NGDeleteRow", "FragCpx_emailCpx");
                }
				
				
				if(pEvent.getSource().getName().trim().equalsIgnoreCase("FATCA_Save"))
				{
					log.WriteToLog("FATCA_Save");
				//	formObject.saveFragment("contract_Details_New_LA_FATCAFrame");
				//	log.WriteToLog("Executed SaveFragment contract_Details_New_LA_FATCAFrame");
					//formObject.saveFragment("FATCA");
						formObject.saveFragment("Contract_Details_New_LA_FATCAFrame");
					//formObject.saveFragment("Contract_Details_New_LA_FATCAFrame");
//					log.WriteToLog("Executed FrNewClient");
//					String hideFields = "ContractDetails~PersonalDetails~Habits~EducationDetails~FinancialDetails~MiscelleanousInformation~GiftInformation~References~Contract_Details_New_Button1~Contract_Details_New_Button1";
//					showHideFields(formObject,hideFields,t);
					log.WriteToLog("FATCA successfully saved.");
				}
				if(pEvent.getSource().getName().trim().equalsIgnoreCase("FATCA_Close"))
				{
					log.WriteToLog("FATCA_Close");
					//formObject.saveFragment("contract_Details_New_LA_FATCAFrame");
					
					String showFields = "ContractDetails~PersonalDetails~Habits~EducationDetails~FinancialDetails~MiscelleanousInformation~GiftInformation~References~Contract_Details_New_Button1~Contract_Details_New_Button1";
					showHideFields(formObject, showFields, true);	
					formObject.setVisible("Contract_Details_New_LA_FATCAFrame",false);
					log.WriteToLog("FATCA successfully closed.");
				}
				
				
				
//				if(pEvent.getSource().getName().equalsIgnoreCase("Contract_Details_New_Button2"))
//				{
//					log.WriteToLog("In Contract_Details_New_Button2 Close Fragment.");
//					formObject.clear("frNewClient");
//					//formObject.setVisible("frNewClient", false);
//					//formObject.setNGFocus("Command34");
//				}
				
				
				
			break;	
			}//End of MOUSE_CLICKED		
	}//End of swtich(pEvnet.getType())
				
}// End of Event Dispatched
		
	

	@Override
	public void formLoaded(FormEvent arg0) {
		// TODO Auto-generated method stub
		
		log.WriteToLog("In Form Loaded Event");
	}

	@Override
	public void formPopulated(FormEvent arg0) {
		// TODO Auto-generated method stub
		
		log.WriteToLog("Form Populated Event Started");
		FormConfig formConfigObject = FormContext.getCurrentInstance().getFormConfig();
		FormReference formObject = FormContext.getCurrentInstance().getFormReference();
		String WI_NAME = formObject.getWFWorkitemName();
		
		if(counthospital==0)
			{
				log.WriteToLog("Count Hospital : "+counthospital);
				String itemindexQuery = "SELECT itemindex FROM NG_CLM_EXT_TABLE WITH (NOLOCK) WHERE WI_NAME='" + WI_NAME+ "'";
		        log.WriteToLog("ItemIndex Query : "+itemindexQuery);
				List<List<String>> itemindexResult = formObject.getDataFromDataSource(itemindexQuery);
		        log.WriteToLog("ItemIndex Result :"+itemindexResult);
				itemindex = itemindexResult.get(0).get(0);
				log.WriteToLog("itemindex : "+itemindex);
		
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
			//		AssessorTo = formatter.parse(formObject.getNGValue("ASSESSOR_TO"));
				}
				
	
				
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				log.WriteToLog("Java Excetption in Hospital Claim Frame "+e1.toString());
			} 
				counthospital++;
				log.WriteToLog("Count Hospital : "+counthospital);
			}
			
		
		
		//Setting Suicide No as True
		
		//Death Claim
		formObject.setNGValue("Option2", "true");
		//Death Claum waiver
		formObject.setNGValue("Option3","true");
		//Accident Claim
		formObject.setNGValue("Option6", "true");
		String claimsDecision = formObject.getNGValue("CLAIMS_DECISION");
		if(claimsDecision.equalsIgnoreCase("Claim Rejected"))
		{
			String rejectionReasonUpdateQuery = "SELECT REJECTION_REASON FROM NG_CLM_Ext_Table where WI_NAME ='"+WI_NAME+"'";
			List<List<String>>rejectionReasonResult = formObject.getDataFromDataSource(rejectionReasonUpdateQuery);
			log.WriteToLog("Rejection Reason Update Query : "+rejectionReasonUpdateQuery);
			String rejectionReason = rejectionReasonResult.get(0).get(0);
			formObject.setNGValue("Combo84", rejectionReason);
		}
		
		
		
		//formObject.setNGValue("Option3",true);
		//String WI_NAME = formConfigObject.getConfigElement("ProcessInstanceId");
		//log.WriteToLog("Work Item Name : "+WI_NAME);
		
		Date requirementDate = new Date();
		String requirementDateString = formObject.getNGValue("DatePicker57");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		try{
			requirementDate = formatter.parse(requirementDateString); 
			requirementDate.setHours(00);
			requirementDate.setMinutes(00);
			requirementDate.setSeconds(00);
		
			
			}
		catch(Exception E)
			{
				log.WriteToLog("Formpopulation Excetion "+E.toString());
			}
		
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
		formObject.setNGValue("DatePicker65", formatter.format(c.getTime()));
		log.WriteToLog("After 7 Days : "+formatter.format(c.getTime()));
		
		c.add(Calendar.DATE, 7);
		formObject.setNGValue("DatePicker58", formatter.format(c.getTime()));
		log.WriteToLog("After 14 Days : "+formatter.format(c.getTime()));
		
		c.add(Calendar.DATE, 7);
		formObject.setNGValue("DatePicker60", formatter.format(c.getTime()));
		log.WriteToLog("After 21 Days : "+formatter.format(c.getTime()));
		
//LoansuranceDetails Frame Queries 	Dt.16 jan 2019 Defect Issue 51	
String LoansuranceDetailsSelectQuery = "select BALANCE_LOAN_AMT,LOAN_AMT_SANC,IS_NON_PERFORMING,"
				+ " IS_SURETY_GUARANTOR from NG_CLM_Loansurance_Details WITH(NOLOCK) where WI_NAME='"+WI_NAME+"'";
		log.WriteToLog("LoansuranceDetailsSelectQuery : "+LoansuranceDetailsSelectQuery);
		List<List<String>> LoansuranceDetailsResult = formObject.getDataFromDataSource(LoansuranceDetailsSelectQuery);
		if(!LoansuranceDetailsResult.isEmpty())
		{
			log.WriteToLog("LoansuranceDetailsResult : "+LoansuranceDetailsResult);
			formObject.setNGValue("Text21",LoansuranceDetailsResult.get(0).get(0));
			formObject.setNGValue("Text22",LoansuranceDetailsResult.get(0).get(1));
			formObject.setNGValue("Combo12",LoansuranceDetailsResult.get(0).get(2));
			formObject.setNGValue("Combo14",LoansuranceDetailsResult.get(0).get(3));
		}		
		
//Claim Number
		String policyNumber = formObject.getNGValue("Policy_No");
		formObject.setNGValue("Text129",policyNumber);
		log.WriteToLog("Policy Number : "+policyNumber);
		
		String claimType = formObject.getNGValue("CLM_TYPE");
		formObject.setNGValue("Text127",claimType);
		log.WriteToLog("Claim Type "+ claimType);
		
		String claimNumber = formObject.getNGValue("CLM_NUMBER");
		formObject.setNGValue("Text126",claimNumber );
		log.WriteToLog("Claim Number : "+ claimNumber);

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
				formObject.setVisible("Label69", false);
			}
		}		
		
//Product Name 
		String productNameQuery = "select STRPRODCD from STAGNEW2.Staging_claim.dbo.COM_POL_PROD_DTL where STRPOLNBR='"+policyNumber+"' and NPRODVER='1'";
		log.WriteToLog("Product Name Query : "+productNameQuery);
		
		List<List<String>> productNameResult = formObject.getDataFromDataSource(productNameQuery);
		log.WriteToLog("Product Name Result : "+productNameResult);
		if(!productNameResult.isEmpty())
		{
			String productName = productNameResult.get(0).get(0);
			log.WriteToLog("Product Name  : "+productName);
			formObject.setNGValue("Text128",productName);
		}
		
		
//Name of the life insured
		String nameOfTheLifeInsuredQuery = "select concat (b.STRFIRSTNAME,' ',b.STRMIDDLENAME,' ',b.STRLASTNAME)  from STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK a ,STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME b  where a.STRCLIENTCD= b.STRCLIENTCD and (NCLIENTTYPE='2' or NCLIENTTYPE='3') and a.STRPOLNBR='"+policyNumber+"'";
		log.WriteToLog("Name of The Life Insured Query : "+nameOfTheLifeInsuredQuery);
		
		List<List<String>> nameOfTheLifeInsuredResult = formObject.getDataFromDataSource(nameOfTheLifeInsuredQuery);
		log.WriteToLog("Name of the Life Insured Result : "+nameOfTheLifeInsuredResult);
		if(!nameOfTheLifeInsuredResult.isEmpty())
		{
			String nameofTheLifeInsured = nameOfTheLifeInsuredResult.get(0).get(0);
			formObject.setNGValue("Text131", nameofTheLifeInsured);
		}
		
		
//Name of the Proposer 
		String nameOfTheProposerQuery = "select concat (b.STRFIRSTNAME,' ',b.STRMIDDLENAME,' ',b.STRLASTNAME)  from STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK a ,STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME b  where a.STRCLIENTCD= b.STRCLIENTCD and (NCLIENTTYPE='1' or NCLIENTTYPE='3') and a.STRPOLNBR='"+policyNumber+"'";
		log.WriteToLog("Name of The Proposer Query : "+nameOfTheProposerQuery);
		List<List<String>> nameOfTheProposerResult = formObject.getDataFromDataSource(nameOfTheProposerQuery);
		log.WriteToLog("Name of the Proposer Result : "+nameOfTheProposerResult);
		if(!nameOfTheProposerResult.isEmpty())
		{
			String nameOfTheProposer = nameOfTheProposerResult.get(0).get(0);
			log.WriteToLog("Name of The Propser : "+nameOfTheProposer);
			formObject.setNGValue("Text130", nameOfTheProposer);
		}
			

//Name of The Nominee and Assignee
		
		String nomineeAssigneQuery = "select concat (b.STRFIRSTNAME,' ',b.STRMIDDLENAME,' ',b.STRLASTNAME),c.STRCDDESC, "
				+ " case when a.NCLIENTTYPE=4 then 'Nominee' else 'Assignee' end  "
				+ " from STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK a ,STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME b,"
				+ " COM_PARAM_SYSTEM_M c  where c.IPARAMTYPECD='5522' and a.STRCLIENTCD= b.STRCLIENTCD "
				+ " and a.NCLIENTTYPE in ('4','5') and c.NPARAMCD=a.NRELNWITHFIRSTLA and a.STRPOLNBR='"+policyNumber+"'";
		
		log.WriteToLog("Name of The Nominee and Assignee Query : "+nomineeAssigneQuery);
		List<List<String>> nomineeAssigneeResult = formObject.getDataFromDataSource(nomineeAssigneQuery);
		log.WriteToLog("Name of The Nominee and Assignee Grid Result : "+nomineeAssigneeResult);
		if(!nomineeAssigneeResult.isEmpty()){
		for(List<String> i :nomineeAssigneeResult){
			log.WriteToLog("Nominee Assignee Grid Values "+i);
				formObject.addItem("ListView9", i);	
		}
		}
//Sourcing Branch
		String sourcingBrachQuery = "select a.STRPREFERREDNAME from chm_agent_m a, STAGNEW2.Staging_claim.dbo.COM_POLICY_M b where a.STRAGENTCD=b.STRSERVAGENTCD and b.STRPOLNBR='"+policyNumber+"'";
		log.WriteToLog("Sourcing Branch : "+sourcingBrachQuery);
		List<List<String>> sourcingBranchResult = formObject.getDataFromDataSource(sourcingBrachQuery);
		log.WriteToLog("Sourcing Branch Result : "+sourcingBranchResult);
		String sourcingBranch=null;
		if(!sourcingBranchResult.isEmpty())
		{
			sourcingBranch = sourcingBranchResult.get(0).get(0);
			log.WriteToLog("Sourcing Branch : "+sourcingBranch);
			formObject.setNGValue("Text132", sourcingBranch);	
		}
		
//Policy Status
		//formObject.setNGValue("Text134", formObject.getNGValue("POLICY_STATUS"));
			
//Channel 
		String channelQuery = "select b.STRCHANNELNAME,a.STRSERVAGENTCD, a.DTOTSA,a.DTOTPRMAMNT,c.STRCDDESC, "
				+ " isnull(convert(nvarchar(100),convert(date,a.DTPOLCOMC)),'') "
				+ " from STAGNEW2.Staging_claim.dbo.COM_POLICY_M a,chm_channel_m b,"
				+ " STAGNEW2.Staging_claim.dbo.COM_PARAM_SYSTEM_M c where STRPOLNBR ='"+policyNumber+"' "
				+ " and c.IPARAMTYPECD='5503' and NPMTMODE=c.NPARAMCD and a.CCHANNELTYPE=b.CCHANNELTYPE"; 
		
		log.WriteToLog("Channel Query : "+channelQuery);
		List<List<String>> channelResult = formObject.getDataFromDataSource(channelQuery);
		log.WriteToLog("Channel Result : "+channelResult);
		String channel=null;
		if(!channelResult.isEmpty())
		{
			channel = channelResult.get(0).get(0);
			log.WriteToLog("Channel : "+channel);
			formObject.setNGValue("Text136", channel);
			formObject.setNGValue("Text137", channelResult.get(0).get(1));
			//Basic Sum Insured 		
			formObject.setNGValue("Text140",channelResult.get(0).get(2));
			//Premium
			formObject.setNGValue("Text139", channelResult.get(0).get(3));
			//Mode 
			formObject.setNGValue("Text138",channelResult.get(0).get(4));
			//Date of Commencement 
			formObject.setNGValue("DatePicker3", channelResult.get(0).get(5));	
		}	
		
		

//Name Status Code		
		

//Name Status Code
		
//		String nameStatusCodeQuery = "select STRSERVAGENTCD from STAGNEW2.Staging_claim.dbo.COM_POLICY_M where STRPOLNBR='"+policyNumber+"'";
//		log.WriteToLog("Name Status Code Query : "+nameStatusCodeQuery);
//		List<List<String>> nameStatusCodeResult = formObject.getDataFromDataSource(nameStatusCodeQuery);
//		log.WriteToLog("Name Staus Code Result : "+nameStatusCodeResult);
//		String nameStatusCode = nameStatusCodeResult.get(0).get(0);
//		log.WriteToLog("Name Status Code : "+nameStatusCode);


	
//Additional Benefits if Any (Not Done)  	formObject.setNGValue("Text142", "NA");				

//Unpaid Premium if Any formObject.setNGValue("Text141",channelResult.get(0).get(3));
		
		//Unpaid Premium if any
		String unpaidPremiumifAny= "SELECT cpd.DTOTDUEAMNT FROM ODSSTAGGING.Odsstaging.dbo.COM_POL_DUE cpd,"
									+ " STAGNEW2.Staging_Claim.dbo.COM_POLICY_M cpm	"
									+ " WHERE cpd.STRPOLNBR = cpm.STRPOLNBR	"
									+ " AND cpd.NDUETYPE IN (1,8) AND "
									+ " (ISNULL (cpd.DTOTDUEAMNT,0)"
									+ " -(ISNULL (cpd.DTOTPAIDAMNT,0)"
									+ " + ISNULL(cpd.DTOTWAIVEDAMNT,0) "
									+ " + ISNULL(cpd.DTOTAGENTDRAMNT,0) "
									+ " + ISNULL(cpd.DTOTADJUSTAMNT,0))) "
									+ " > 0 AND cpd.STRPOLNBR='"+policyNumber+"'";
		
		List<List<String>>unpaidPremiumifAnyResult = formObject.getDataFromDataSource(unpaidPremiumifAny);
		if(!unpaidPremiumifAnyResult.isEmpty())
		{
			formObject.setNGValue("Text141", unpaidPremiumifAnyResult.get(0).get(0));
		}
		else
		{
			formObject.setNGValue("Text141","0.000");
		}

//Total Claim Amount 
		
		formObject.setNGValue("Text143",formObject.getNGValue("TOTAL_CLM_AMT"));

		
 

//		String premiumQuery = "select DTOTPRMAMNT,DTLASTUNPAIDDUE,DTOTSA from  STAGNEW2.Staging_claim.dbo.COM_POLICY_M where STRPOLNBR='"+policyNumber+"'";
//		log.WriteToLog("Premium Query : "+premiumQuery);
//	
//		List<List<String>> premiumResult = formObject.getDataFromDataSource(premiumQuery);
//		log.WriteToLog("Premium Result : "+premiumResult);
//		//String premium = premiumResult.get(0).get(0);
//		//log.WriteToLog("Premium : "+premium);
	
		

		

		
	
//Proposal Sign Date


		String proposalSignDateQuery = "select format(a.DTPROPSIGNED,'yyyy/MM/dd') as ProposalSignDate from STAGNEW2.Staging_claim.dbo.NB_BASE_PROP_DTL a,STAGNEW2.Staging_claim.dbo.NB_PROP_POL_LNK b where b.STRPOLNBR='"+policyNumber+"' and a.STRPROPNBR=b.STRPROPNBR";
		log.WriteToLog("Proposal Sign Date Query : "+proposalSignDateQuery);
		List<List<String>> proposalSignDateResult = formObject.getDataFromDataSource(proposalSignDateQuery);
		if(!proposalSignDateResult.isEmpty())
		{
			log.WriteToLog("Proposal Sign Date Result : "+proposalSignDateResult);
			formObject.setNGValue("DatePicker1", proposalSignDateResult.get(0).get(0));		
		}
		
			
		String reinstatementRCDDateQuery = "select isnull(convert(nvarchar(100),convert(date,DTPOLREVIVAL)),''),isnull(convert(nvarchar(100),convert(date,DTPOLRISKCOMC)),'') from STAGNEW2.Staging_claim.dbo.COM_POLICY_M WHERE STRPOLNBR = '"+policyNumber+"'";
		log.WriteToLog("ReinstatementRCD Date Query  : "+reinstatementRCDDateQuery);
		List<List<String>> reinstatementRCDDateResult = formObject.getDataFromDataSource(reinstatementRCDDateQuery);
		String dateOfReinstatementString = null;
		String dateOfPolicyRiskString = null;
		log.WriteToLog("Hi");
		
		
		try{
			log.WriteToLog("!reinstatementRCDDateResult.isEmpty() : "+!reinstatementRCDDateResult.isEmpty());
			log.WriteToLog("Reinstatement RCD Date Result : "+reinstatementRCDDateResult);
		
		}catch(Exception E)
		{
			System.out.println("Exception in RCD Date : "+E.toString());
		}
		log.WriteToLog("Hi2");
		if(!reinstatementRCDDateResult.isEmpty())
		{
			dateOfReinstatementString = reinstatementRCDDateResult.get(0).get(0);			
			log.WriteToLog("Date of Reinstatement String : "+dateOfReinstatementString);
			
			dateOfPolicyRiskString = reinstatementRCDDateResult.get(0).get(1);
			log.WriteToLog("Date of Policy Risk String : "+dateOfPolicyRiskString);
		}
		
	
		String dateOfEventString;
		
//		String dateOfEventQuery = "select format(DATE_OF_DEATH,'yyyy/MM/dd'),format(DATE_OF_ACCIDENT,'yyyy/MM/dd'), "
//				+ " format(DATE_OF_DIAGNOSIS,'yyyy/MM/dd'),format(CLM_VALID_NOTIF_DATE,'yyyy/MM/dd'), "
//				+ " format(DATE_OF_DISABLEMENT,'yyyy/MM/dd'),isnull(PRI_CAUSE_OF_DEATH,''),"
//				+ " isnull(NATURE_OF_DEATH,''),isnull(CAUSE_OF_ILLNESS,''), "
//				+ " isnull(MAJOR_DISEASE_TYPE,''),isnull(CAUSE_OF_DISABLEMENT,''),"
//				+ " isnull(CLM_ON,''), isnull(POLICY_STATUS,'') from NG_CLM_Ext_Table "
//				+ " WITH(NOLOCK) WHERE WI_NAME = '"+WI_NAME+"'"; 
				
		String dateOfEventQuery = "select format(COMMEC_DATE,'yyyy/MM/dd') ,isnull(CLM_ON,''),isnull(claim_on_id,'')"
				+ " from NG_CLM_Ext_Table WITH(NOLOCK) " 
				+ " WHERE WI_NAME = '"+WI_NAME+"'";
		
		log.WriteToLog("Date Of Event Query : "+dateOfEventQuery);
		
		
		List<List<String>> dateOfEventResult = formObject.getDataFromDataSource(dateOfEventQuery);
		String claimOn=null;
		String claimOnId=null;
		if(!dateOfEventResult.isEmpty()){
			
			log.WriteToLog("Date of Event Result : "+dateOfEventResult);
			//Commecement Date
			formObject.setNGValue("DatePicker3",dateOfEventResult.get(0).get(0));
			
			claimOn=dateOfEventResult.get(0).get(1);
			log.WriteToLog("Claim On from Exteranl Table : "+claimOn);
			
	//Claims Summary Claim On SetNg

			formObject.setNGValue("Text133", claimOn);
			
			claimOnId = dateOfEventResult.get(0).get(2);
			log.WriteToLog("Claim On ID from Exteranl Table : "+claimOnId);		
		}
		

				
//Loansurance Button Hiding
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
					formObject.setVisible("Button36",true);
					formObject.setVisible("Button41", true);	
				}
				else
				{
					formObject.setVisible("Button36",false);
					formObject.setVisible("Button41", false);
				}
				
//Is Loan Covered Hiding

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
						formObject.setVisible("Label121", false);
						formObject.setVisible("Combo40", false);
						formObject.setVisible("Label89", false);
						formObject.setVisible("Combo18", false);
					}
					else
					{
						formObject.setVisible("Label121", true);
						formObject.setVisible("Combo40", true);
						formObject.setVisible("Label89", true);
						formObject.setVisible("Combo18", true);
					}	
				}
				
				//Code for Hiding NAVDATE	
				String prodlineQuery = "select count(STRPRODLINE) from  STAGNEW2.Staging_claim.dbo.COM_POLICY_M where STRPRODLINE in('U1','UL') and STRPOLNBR='"+policyNumber+"'";
				log.WriteToLog("prodlineQuery : "+prodlineQuery);
				
				List<List<String>> prodlineResult = formObject.getDataFromDataSource(prodlineQuery);
				log.WriteToLog("Prodline Result : "+prodlineResult);
				
				int countProdline = Integer.parseInt(prodlineResult.get(0).get(0));
				
				if(!prodlineResult.isEmpty())
				{
					if(countProdline==0)
					{
						//Death Claim
						formObject.setVisible("Label84",false);
						formObject.setVisible("DatePicker16", false);
						
						//Death Claim Waiver
						formObject.setVisible("Label126", false);
						formObject.setVisible("DatePicker27", false);
						
						//Accident 
						formObject.setVisible("Label147", false);
						formObject.setVisible("DatePicker28", false);
						
						//TPDB
						formObject.setVisible("Label231", false);
						formObject.setVisible("DatePicker50",false);
						
						
					}
					else
					{
						//Death Claim
						formObject.setVisible("Label84",true);
						formObject.setVisible("DatePicker16", true);
						
						//Death Claim Waiver
						formObject.setVisible("Label126", true);
						formObject.setVisible("DatePicker27", true);
						
						//Accident 
						formObject.setVisible("Label147", true);
						formObject.setVisible("DatePicker28", true);
						
						//TPDB
						formObject.setVisible("Label231", true);
						formObject.setVisible("DatePicker50",true);
						
					}
				}	
				
				dateOfEventString = formObject.getNGValue("DATE_OF_EVENT");
		//		formObject.setNGValue("Text148", dateOfEventResult.get(0).get(1));
				
				datePopulation(dateOfEventString,dateOfReinstatementString,dateOfPolicyRiskString);	
		
				
				/*
		if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim"))
			
		{

			//claimOn= formObject.getNGValue("Combo121");
			//Cause of Event in Claims Summary 
			formObject.setNGValue("Text148", dateOfEventResult.get(0).get(8));
			dateOfEventString = dateOfEventResult.get(0).get(0);
			log.WriteToLog("Date of Event String : "+dateOfEventString);
			datePopulation(dateOfEventString,dateOfReinstatementString,dateOfPolicyRiskString);	
		}
		
		if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Accidental Death Claim"))
		{
		//	claimOn= formObject.getNGValue("Combo123");
			//Cause of Event in Claims Summary 
			formObject.setNGValue("Text148", dateOfEventResult.get(0).get(9));
			
			//dateOfEventString = dateOfEventResult.get(0).get(1);
		
			dateOfEventString = dateOfEventResult.get(0).get(1);
			log.WriteToLog("Date of Event String : "+dateOfEventString);
			datePopulation(dateOfEventString,dateOfReinstatementString,dateOfPolicyRiskString);				
		}
		
		if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim Waiver of Premium"))
		{
			//claimOn= formObject.getNGValue("Combo122");
			//Cause of Event in Claims Summary 
			formObject.setNGValue("Text148", dateOfEventResult.get(0).get(8));
			dateOfEventString = dateOfEventResult.get(0).get(0);
			log.WriteToLog("Date of Event String : "+dateOfEventString);
			datePopulation(dateOfEventString,dateOfReinstatementString,dateOfPolicyRiskString);					
		}
		
		if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Critical Illness/Dreaded Disease Claim"))
		{
			//claimOn= formObject.getNGValue("Combo124");
			//Cause of Event in Claims Summary 
			//formObject.setNGValue("Text148", formObject.getNGValue("MAJOR_DISEASE_TYPE"));
			formObject.setNGValue("Text148", dateOfEventResult.get(0).get(10));
			dateOfEventString = dateOfEventResult.get(0).get(2);
			log.WriteToLog("Date of Event String : "+dateOfEventString);
			datePopulation(dateOfEventString,dateOfReinstatementString,dateOfPolicyRiskString);
		}	
		if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Hospitalization Claim"))
		{
		//	claimOn= formObject.getNGValue("Combo125");
			//formObject.setNGValue("Text148", formObject.getNGValue("CAUSE_OF_ILLNESS"));
			formObject.setNGValue("Text148", dateOfEventResult.get(0).get(11));
			dateOfEventString = dateOfEventResult.get(0).get(3);
			log.WriteToLog("Date of Event String : "+dateOfEventString);		
			datePopulation(dateOfEventString,dateOfReinstatementString,dateOfPolicyRiskString);
		}
	if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("TPDB Claim"))
		{
			//claimOn= formObject.getNGValue("Combo126");
			//Cause of Event in Claims Summary 
			
		//	formObject.setNGValue("Text148", formObject.getNGValue("CAUSE_OF_DISABLEMENT"));
			formObject.setNGValue("Text148", dateOfEventResult.get(0).get(12));
			dateOfEventString = dateOfEventResult.get(0).get(4);
			log.WriteToLog("Date of Event String : "+dateOfEventString);
			datePopulation(dateOfEventString,dateOfReinstatementString,dateOfPolicyRiskString);		
		}
		*/
	
	log.WriteToLog("Claim On : "+claimOn);
	//String ClaimOnIdQuery= "Select CLAIM_ON_ID from NG_CLM_ClaimOnID_Master where CLAIM_ON='"+claimOn+"' and WI_NAME = '"+WI_NAME+"'";  
	
	//log.WriteToLog("Claim ON ID Query"+ClaimOnIdQuery);
	//List<List<String>> ClaimOnIdResult = formObject.getDataFromDataSource(ClaimOnIdQuery);
	
//	log.WriteToLog("Claim On Id Result : "+ClaimOnIdResult);
	
//	if(!ClaimOnIdResult.isEmpty())
//	{
//		claimOnId = ClaimOnIdResult.get(0).get(0);
//		log.WriteToLog("Claim ON id : "+claimOnId);
//		
//	}


//Last Paid Due Date
			String lastPaidDueDateQuery = "select isnull(convert(nvarchar(100),convert(date,DTLASTUNPAIDDUE)),'') from  STAGNEW2.Staging_claim.dbo.COM_POLICY_M where STRPOLNBR='"+policyNumber+"'";
			log.WriteToLog("Last Paid Due Date Query: "+lastPaidDueDateQuery);
			List<List<String>> lastPaidDueDateResult = formObject.getDataFromDataSource(lastPaidDueDateQuery);
			log.WriteToLog("Last Paid Due Date Result : "+lastPaidDueDateResult);
			String lastPaidDueDate = lastPaidDueDateResult.get(0).get(0);
			log.WriteToLog("Last Paid Due Date : "+lastPaidDueDate);

	
//Age	formObject.setNGValue("Text152", "48");
	String ageQuery = "select convert(INT,DATEDIFF (d,b.DTBIRTH, Convert(date,GETDATE(),108))/365.25) as Age from STAGNEW2.Staging_claim.dbo.com_client_m b where STRCLIENTCD='"+claimOnId+"'";
	log.WriteToLog("Age Query : "+ageQuery);
	List<List<String>> ageResult = formObject.getDataFromDataSource(ageQuery);
	log.WriteToLog("Age Result : "+ageResult);
	if(!ageResult.isEmpty())
	{
		String age = ageResult.get(0).get(0);
		log.WriteToLog("Age : "+age);
		formObject.setNGValue("Text152", age);
	}
	
	

//Occupation formObject.setNGValue("Text147", "Service");
	String OccupationQuery = "select b.STROCCUPNAME from STAGNEW2.Staging_claim.dbo.com_client_m a, "
			+ "ODSSTAGGING.Odsstaging.dbo.COM_OCCUPATION_M b where a.STROCCUPCD=b.STROCCUPCD and a.STRCLIENTCD ='"+claimOnId+"'";
	log.WriteToLog("Occupation Query : "+OccupationQuery);
	List<List<String>> occupationResult = formObject.getDataFromDataSource(OccupationQuery);
	log.WriteToLog("Occupation Result : "+occupationResult);
	if(!occupationResult.isEmpty())
	{
		formObject.setNGValue("Text147", occupationResult.get(0).get(0));	
	}
	

	
//Annual Income  
	
	String annualIncomeQuery = "select DTOTANNLINCM from STAGNEW2.Staging_claim.dbo.com_client_m where STRCLIENTCD='"+claimOnId+"'";
	log.WriteToLog("Annual Income Query : "+annualIncomeQuery);
	List<List<String>> annualIncomeResult = formObject.getDataFromDataSource(annualIncomeQuery);
	log.WriteToLog("Annual Income Result : "+annualIncomeResult);
	if(!annualIncomeResult.isEmpty())
	{
		String annualIncome = annualIncomeResult.get(0).get(0);
		formObject.setNGValue("Text151", annualIncome);	
	}
	
	
//Cause of Event formObject.setNGValue("Text148", "Natural");

//Medical Non Medical	formObject.setNGValue("Text150", "Medical");
	
	String MedicalNonQuery = "select"
			+ " case when count(a.NUWDECISIONCD)=0 then 'STP' "
			+ " 	else "
			+ " 	(select "
			+ " 		case when a.NISMED=0 then 'Non Medical' "
			+ "				when a.NISMED=1 then 'Medical' "
			+ " 		else 'N.A.' end"
			+ " 		from STAGNEW2.Staging_claim.dbo.NB_BASE_PROP_DTL a,STAGNEW2.Staging_claim.dbo.NB_PROP_POL_LNK b"
			+ "			where b.STRPOLNBR='"+policyNumber+"' and a.STRPROPNBR=b.STRPROPNBR"
			+ "		)"
			+ "	end from STAGNEW2.Staging_claim.dbo.NB_BASE_PROP_DTL a,STAGNEW2.Staging_claim.dbo.NB_PROP_POL_LNK b"
			+ "	where b.STRPOLNBR='4000037120' and a.STRPROPNBR=b.STRPROPNBR";
	log.WriteToLog("Medical Non Medical Query : "+MedicalNonQuery);
	List<List<String>> MedicalNonResult = formObject.getDataFromDataSource(MedicalNonQuery);
	if(!MedicalNonResult.isEmpty())
	{
		log.WriteToLog("Medical Non Medical STP Result :"+MedicalNonResult);
		formObject.setNGValue("Text150", MedicalNonResult.get(0).get(0));
	}
	
	
				
//UW Decision formObject.setNGValue("Text149", "Borderline / Standard");
	String UWDecisionQuery = "select c.STRCDDESC from STAGNEW2.Staging_claim.dbo.NB_BASE_PROP_DTL a,"
			+ " STAGNEW2.Staging_claim.dbo.NB_PROP_POL_LNK b,STAGNEW2.Staging_claim.dbo.COM_PARAM_SYSTEM_M c "
			+ " where b.STRPOLNBR='4000037120' and a.STRPROPNBR=b.STRPROPNBR and a.NUWDECISIONCD=c.NPARAMCD "
			+ " and c.IPARAMTYPECD='2003'";
	List<List<String>> UWDecisionResult = formObject.getDataFromDataSource(UWDecisionQuery);
	if(!UWDecisionResult.isEmpty())
	{
		formObject.setNGValue("Text149", UWDecisionResult.get(0).get(0));
		
	}
	else
	{
		formObject.setNGValue("Text149", "N.A.");
	}
			
	
//	Refund Policies
	

	String refundPoliciesGridQuery =  "select DTTRANSDATE,DREFUNDAMOUNT from STAGNEW2.Staging_claim.dbo.COM_REFUND_DTL where STRREFNBR='"+policyNumber+"'";
	log.WriteToLog("Refund Policies Grid Query : "+refundPoliciesGridQuery);
	List<List<String>> refundPolicieGridResult =formObject.getDataFromDataSource(refundPoliciesGridQuery);
	log.WriteToLog("refundPolicieGridResult : "+refundPolicieGridResult);
	if(!refundPolicieGridResult.isEmpty())
	{
		for(List<String> i :refundPolicieGridResult){
		log.WriteToLog("refundPolicieGridResult "+i);
		formObject.addItem("ListView18", i);	
		}
		
	}

//Premium q_premium_grid
	
	String premiumReceivedDatesQuery = "SELECT dtdue,dtpaid  FROM  ODSSTAGGING.Odsstaging.dbo.com_pol_due WHERE strpolnbr = '"+policyNumber+"' and NDUETYPE='1' ";
	log.WriteToLog("premiumReceivedDatesQuery : "+premiumReceivedDatesQuery);
	
	List<List<String>> premiumReceivedDatesResult =formObject.getDataFromDataSource(premiumReceivedDatesQuery);
	log.WriteToLog("premiumReceivedDatesResult : "+premiumReceivedDatesResult);
			if(!premiumReceivedDatesResult.isEmpty())
			{
				for(List<String> i :premiumReceivedDatesResult){
				log.WriteToLog("premiumReceivedDatesResult "+i);
				formObject.addItem("ListView16", i);	
				}
				
			}		
	

		
//Alteration Date and Alteration Type Grid
			
			String alterationDateTypeQuery = "select c.STRCDDESC,isnull(convert(nvarchar(100),convert(date,pam.DTALTEFF)),'')  "
					+ " as DTALTEFF FROM  STAGNEW2.Staging_claim.dbo.PS_ALT_HDR pah, STAGNEW2.Staging_claim.dbo.PS_ALT_MAP pam,"
					+ " STAGNEW2.Staging_claim.dbo.COM_PARAM_SYSTEM_M c where pah.STRPOLNBR='"+policyNumber+"' "
					+ " and pah.STRALTTRANSHDRNBR = pam.STRALTTRANSHDRNBR and pam.NALTTYPE=c.NPARAMCD "
					+ " and c.IPARAMTYPECD='3001' order by DTALTEFF;";
		
		log.WriteToLog("Alteration Date and alteration type Query : "+alterationDateTypeQuery);
		List<List<String>> alterationDateTypeResult = formObject.getDataFromDataSource(alterationDateTypeQuery);
		log.WriteToLog("Alteration Date and Alteration Type Result : "+alterationDateTypeResult);
		
		if(!alterationDateTypeResult.isEmpty())
		{
			for(List<String> i :alterationDateTypeResult){
				log.WriteToLog("Alteration Date Type Grid Values "+i);
				formObject.addItem("ListView17", i);	
				}
		}
		
		
	// Update Client tab 	
	String updateClientNomineeGridQuery =	
			
			"SELECT  t1.ClientName,t1.IDType,t1.IDNumber,t1.Gender,t1.DateOfBirth,t2.STROCCUPNAME,t1.STRCLIENTCD "
			+ " FROM"
			+ " (select "
			+ " concat(a.STRFIRSTNAME ,' ', a.STRMIDDLENAME,' ',a.STRLASTNAME) as ClientName,"
			+ " c.STRCDDESC as IDType, d.STRNEWICNBR as IDNumber,e.STRCDDESC as Gender, "
			+ " format(d.DTBIRTH,'yyyy/MM/dd') as DateOfBirth, b.STRCLIENTCD"
			+ "	from  STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME a, "
			+ " STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK b,"
			+ " STAGNEW2.Staging_claim.dbo.com_param_user_m c,"
			+ " STAGNEW2.Staging_claim.dbo.com_client_m d,"
			+ " STAGNEW2.Staging_claim.dbo.com_param_system_m e"
			+ " where a.STRCLIENTCD = b.STRCLIENTCD"
			+ "	and b.STRCLIENTCD = d.STRCLIENTCD"
			+ "	and c.IPARAMTYPECD=2524"
			+ "	and Convert(nvarchar(10),c.STRPARAMCD)=Convert(nvarchar(10),d.STRNEWICTYPE)"
			+ "	and e.IPARAMTYPECD=5504 "
			+ " and Convert(nvarchar(10),e.NPARAMCD)=Convert(nvarchar(10),d.NGENDERCD)"
			+ " and b.NCLIENTTYPE='4'"
			+ " and b.STRPOLNBR ='"+policyNumber+"') t1"
			+ " LEFT JOIN (select b.STROCCUPNAME,a.STRCLIENTCD "
			+ " from STAGNEW2.Staging_claim.dbo.com_client_m a, "
			+ " ODSSTAGGING.Odsstaging.dbo.COM_OCCUPATION_M b where a.STROCCUPCD=b.STROCCUPCD ) t2 "
			+ " on t1.STRCLIENTCD = t2.STRCLIENTCD";

	log.WriteToLog("updateClientNomineeGridQuery : "+updateClientNomineeGridQuery);
	List<List<String>>updateClientNomineeGridResult = formObject.getDataFromDataSource(updateClientNomineeGridQuery);
	log.WriteToLog("updateClientNomineeGridResult : "+updateClientNomineeGridResult);
	
	if(!updateClientNomineeGridResult.isEmpty())
	{
		for(List<String> i :updateClientNomineeGridResult){
			log.WriteToLog("updateClientNomineeGrid Values "+i);
			formObject.addItem("ListView13", i);	
			}
	}
	
	
	String updateClientAssigneeGridQuery =	
			"SELECT  t1.ClientName,t1.IDType,t1.IDNumber,t1.Gender,t1.DateOfBirth,t2.STROCCUPNAME,t1.STRCLIENTCD "
					+ " FROM"
					+ " (select "
					+ " concat(a.STRFIRSTNAME ,' ', a.STRMIDDLENAME,' ',a.STRLASTNAME) as ClientName,"
					+ " c.STRCDDESC as IDType, d.STRNEWICNBR as IDNumber,e.STRCDDESC as Gender, "
					+ " format(d.DTBIRTH,'yyyy/MM/dd') as DateOfBirth, b.STRCLIENTCD"
					+ "	from  STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME a, "
					+ " STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK b,"
					+ " STAGNEW2.Staging_claim.dbo.com_param_user_m c,"
					+ " STAGNEW2.Staging_claim.dbo.com_client_m d,"
					+ " STAGNEW2.Staging_claim.dbo.com_param_system_m e"
					+ " where a.STRCLIENTCD = b.STRCLIENTCD"
					+ "	and b.STRCLIENTCD = d.STRCLIENTCD"
					+ "	and c.IPARAMTYPECD=2524"
					+ "	and Convert(nvarchar(10),c.STRPARAMCD)=Convert(nvarchar(10),d.STRNEWICTYPE)"
					+ "	and e.IPARAMTYPECD=5504 "
					+ " and Convert(nvarchar(10),e.NPARAMCD)=Convert(nvarchar(10),d.NGENDERCD)"
					+ " and b.NCLIENTTYPE='5'"
					+ " and b.STRPOLNBR ='"+policyNumber+"') t1"
					+ " LEFT JOIN (select b.STROCCUPNAME,a.STRCLIENTCD "
					+ " from STAGNEW2.Staging_claim.dbo.com_client_m a, "
					+ " ODSSTAGGING.Odsstaging.dbo.COM_OCCUPATION_M b where a.STROCCUPCD=b.STROCCUPCD ) t2 "
					+ " on t1.STRCLIENTCD = t2.STRCLIENTCD ";
	log.WriteToLog("updateClientAssigneeGridQuery : "+updateClientAssigneeGridQuery);
	List<List<String>>updateClientAssigneeGridResult = formObject.getDataFromDataSource(updateClientAssigneeGridQuery);
	log.WriteToLog("updateClientAssigneeGridResult : "+updateClientAssigneeGridResult);
	if(!updateClientAssigneeGridResult.isEmpty())
	{
		for(List<String> i :updateClientAssigneeGridResult){
			log.WriteToLog("updateClientNomineeGrid Values "+i);
			formObject.addItem("ListView14", i);	
			}	
	}

		
		log.WriteToLog("Before Claim Type Check");
	if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim"))
		{
			log.WriteToLog("In Death Claim Frame for Select Query");
			
			String Query = "SELECT NAV_DATE, CLM_ON, DATE_OF_DEATH, CLM_VALID_NOTIF_DATE, NATURE_OF_DEATH,"
					+ " SUICIDE, PRI_CAUSE_OF_DEATH ,IS_LOAN_COVERED ,CLM_INTIMATION_SRC, CUSTOMER_SIGN, "
					+ "DEATH_CER_ISSUED_FROM, DEATH_CER_ISSUED_TYPE, REG_NUM_OF_DEATH_CER, DATE_OF_DEATH_CER,"
					+ "CERTIFYING_DOCTOR, PRI_HEALTH_STMT, EXACT_CAUSE_OF_DEATH, UNDERLYING_DEATH_CAUSE, "
					+ "AGENT, DEATH_LOCATION, HOSP_NAME ,HOSP_ADMISSION_DATE, HOSP_DISCHARGE_DATE, "
					+ "IC_CODE_1, IC_CODE_2, IC_CODE_3, IC_CODE_4, IC_CODE_5, IC_CODE_6 "
					+ "FROM NG_CLM_Ext_Table WITH(NOLOCK) where WI_NAME = '"+WI_NAME+"'";
			log.WriteToLog("In Death Claim Frame Executing Select Query"+ Query);
			
			List<List<String>> NG_CLM_Ext_Table_Data = formObject.getDataFromDataSource(Query);
			if(NG_CLM_Ext_Table_Data.get(0).get(0)!=null)
			{
				formObject.setNGValue("DatePicker16",NG_CLM_Ext_Table_Data.get(0).get(0).replaceAll("-", "/").substring(0, 10)); //NAV_DATE
			}
			
			formObject.setNGValue("Combo121",NG_CLM_Ext_Table_Data.get(0).get(1)); //CLM_ON
		
			if(NG_CLM_Ext_Table_Data.get(0).get(2)!=null)
			{
				formObject.setNGValue("DatePicker17",NG_CLM_Ext_Table_Data.get(0).get(2).replaceAll("-", "/").substring(0, 10)); //DATE_OF_DEATH
			}
		
			if(NG_CLM_Ext_Table_Data.get(0).get(3)!=null)
			{
				formObject.setNGValue("DatePicker18",NG_CLM_Ext_Table_Data.get(0).get(3).replaceAll("-", "/").substring(0, 10)); //CLM_VALID_NOTIF_DATE
			}
		
			formObject.setNGValue("Combo29",NG_CLM_Ext_Table_Data.get(0).get(4)); //NATURE_OF_DEATH
			formObject.setNGValue("Option1",NG_CLM_Ext_Table_Data.get(0).get(5)); //SUICIDE
			formObject.setNGValue("Combo28",NG_CLM_Ext_Table_Data.get(0).get(6));//PRI_CAUSE_OF_DEATH
			formObject.setNGValue("Combo18",NG_CLM_Ext_Table_Data.get(0).get(7));//IS_LOAN_COVERED
			formObject.setNGValue("Combo19",NG_CLM_Ext_Table_Data.get(0).get(8));//CLM_INTIMATION_SRC
			formObject.setNGValue("Combo23",NG_CLM_Ext_Table_Data.get(0).get(9));//CUSTOMER_SIGN
			formObject.setNGValue("Combo20",NG_CLM_Ext_Table_Data.get(0).get(10));//DEATH_CER_ISSUED_FROM
			formObject.setNGValue("Combo34",NG_CLM_Ext_Table_Data.get(0).get(11));//DEATH_CER_ISSUED_TYPE
			formObject.setNGValue("Text56",NG_CLM_Ext_Table_Data.get(0).get(12));//REG_NUM_OF_DEATH_CER
		
			if(NG_CLM_Ext_Table_Data.get(0).get(13)!=null)
			{
				formObject.setNGValue("DatePicker19",NG_CLM_Ext_Table_Data.get(0).get(13).replaceAll("-", "/").substring(0, 10));//DATE_OF_DEATH_CER
			}
			
			
			formObject.setNGValue("Text58",NG_CLM_Ext_Table_Data.get(0).get(14));//CERTIFYING_DOCTOR
			formObject.setNGValue("Combo25",NG_CLM_Ext_Table_Data.get(0).get(15));//PRI_HEALTH_STMT
			formObject.setNGValue("Combo21",NG_CLM_Ext_Table_Data.get(0).get(16));//EXACT_CAUSE_OF_DEATH
			formObject.setNGValue("Text144",NG_CLM_Ext_Table_Data.get(0).get(17));//UNDERLYING_DEATH_CAUSE
			formObject.setNGValue("Combo22",NG_CLM_Ext_Table_Data.get(0).get(18));//AGENT
			formObject.setNGValue("Combo27",NG_CLM_Ext_Table_Data.get(0).get(19));//DEATH_LOCATION
			formObject.setNGValue("Text57",NG_CLM_Ext_Table_Data.get(0).get(20));//HOSP_NAME
		
			if(NG_CLM_Ext_Table_Data.get(0).get(21)!=null)
			{
				formObject.setNGValue("DatePicker21",NG_CLM_Ext_Table_Data.get(0).get(21).replaceAll("-", "/").substring(0, 10));//HOSP_ADMISSION_DATE

			}
			if(NG_CLM_Ext_Table_Data.get(0).get(22)!=null)
			{
				formObject.setNGValue("DatePicker20",NG_CLM_Ext_Table_Data.get(0).get(22).replaceAll("-", "/").substring(0, 10));//HOSP_DISCHARGE_DATE

			}
			
			
			formObject.setNGValue("Text46",NG_CLM_Ext_Table_Data.get(0).get(23));//IC_CODE_1
			formObject.setNGValue("Text47",NG_CLM_Ext_Table_Data.get(0).get(24));//IC_CODE_2
			formObject.setNGValue("Text50",NG_CLM_Ext_Table_Data.get(0).get(25));//IC_CODE_3
			formObject.setNGValue("Text51",NG_CLM_Ext_Table_Data.get(0).get(26));//IC_CODE_4
			formObject.setNGValue("Text55",NG_CLM_Ext_Table_Data.get(0).get(27));//IC_CODE_5
			formObject.setNGValue("Text54",NG_CLM_Ext_Table_Data.get(0).get(28));//IC_CODE_6
		}
		if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Accidental Death Claim"))
		{			
			

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
			if(NG_CLM_Ext_Table_Data.get(0).get(0)!=null)
			{
				formObject.setNGValue("DatePicker28",NG_CLM_Ext_Table_Data.get(0).get(0).replaceAll("-", "/").substring(0, 10)); //	ACD_NAVDate

			}
			
			formObject.setNGValue("Combo123",NG_CLM_Ext_Table_Data.get(0).get(1));//	ACD_ClaimOn	
			
			if(NG_CLM_Ext_Table_Data.get(0).get(2)!=null)
			{
				formObject.setNGValue("DatePicker29",NG_CLM_Ext_Table_Data.get(0).get(2).replaceAll("-", "/").substring(0, 10));//ACD_DateOfDeath			

			}
			
			if(NG_CLM_Ext_Table_Data.get(0).get(3)!=null)
			{
				formObject.setNGValue("DatePicker30",NG_CLM_Ext_Table_Data.get(0).get(3).replaceAll("-", "/").substring(0, 10));//ACD_ClaimValid
			}
		
			formObject.setNGValue("Text51",NG_CLM_Ext_Table_Data.get(0).get(4));//ACD_NatureOfDeath
			formObject.setNGValue("Option5",NG_CLM_Ext_Table_Data.get(0).get(5));		//Suicide	
			formObject.setNGValue("Combo42",NG_CLM_Ext_Table_Data.get(0).get(6));//ACD_ClaimIntimation	
			formObject.setNGValue("Combo43",NG_CLM_Ext_Table_Data.get(0).get(7));//ACD_DeathCertificateIssued	
			formObject.setNGValue("Combo47",NG_CLM_Ext_Table_Data.get(0).get(8));//ACD_DeathCertificateType	
			formObject.setNGValue("Text79",NG_CLM_Ext_Table_Data.get(0).get(9));//ACD_RegistrationNumberDeath	
			
			if(NG_CLM_Ext_Table_Data.get(0).get(10)!=null)
			{
				formObject.setNGValue("DatePicker31",NG_CLM_Ext_Table_Data.get(0).get(10).replaceAll("-", "/").substring(0, 10));//ACD_InsuranceDateDeath	

			}
			
			formObject.setNGValue("Text81",NG_CLM_Ext_Table_Data.get(0).get(11));//ACD_CerifyingDoctor
			formObject.setNGValue("Combo48",NG_CLM_Ext_Table_Data.get(0).get(12));//ACD_PrimaryHealthStmt	
			formObject.setNGValue("Combo44",NG_CLM_Ext_Table_Data.get(0).get(13));//ACD_ExactCauseDeath	
			formObject.setNGValue("Text154",NG_CLM_Ext_Table_Data.get(0).get(14));//ACD_UnderlyingCauseDeath	
			formObject.setNGValue("Combo45",NG_CLM_Ext_Table_Data.get(0).get(15));//ACD_Agent	
			formObject.setNGValue("Combo50",NG_CLM_Ext_Table_Data.get(0).get(16));//	ACD_DeathLocation	
			formObject.setNGValue("Text80",NG_CLM_Ext_Table_Data.get(0).get(17));//ACD_HosptitalName	
			
			if(NG_CLM_Ext_Table_Data.get(0).get(18)!=null)
			{
				formObject.setNGValue("DatePicker33",NG_CLM_Ext_Table_Data.get(0).get(18).replaceAll("-", "/").substring(0, 10));//ACD_HospitalAdmission	

			}
			if(NG_CLM_Ext_Table_Data.get(0).get(19)!=null)
			{
				formObject.setNGValue("DatePicker32",NG_CLM_Ext_Table_Data.get(0).get(19).replaceAll("-", "/").substring(0, 10));//ACD_HospitalDischarge		

			}
			
			
			formObject.setNGValue("Combo46",NG_CLM_Ext_Table_Data.get(0).get(20));//ACD_CustomerSign						
			formObject.setNGValue("Text69",NG_CLM_Ext_Table_Data.get(0).get(21)); // IC Code 1
			formObject.setNGValue("Text72",NG_CLM_Ext_Table_Data.get(0).get(22)); // IC Code 2
			formObject.setNGValue("Text75",NG_CLM_Ext_Table_Data.get(0).get(23)); // IC Code 3
			formObject.setNGValue("Text76",NG_CLM_Ext_Table_Data.get(0).get(24)); // IC Code 4
			formObject.setNGValue("Text78",NG_CLM_Ext_Table_Data.get(0).get(25)); // IC Code 5
			formObject.setNGValue("Text77",NG_CLM_Ext_Table_Data.get(0).get(26)); // IC Code 6
		}
		if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim Waiver of Premium"))
		{
			log.WriteToLog("In Death Claim Waiver of Premium frame for Select Query");
			
			String Query = "SELECT NAV_DATE, CLM_ON, DATE_OF_DEATH, CLM_VALID_NOTIF_DATE, NATURE_OF_DEATH,"
					+ " SUICIDE, PRI_CAUSE_OF_DEATH ,IS_LOAN_COVERED ,CLM_INTIMATION_SRC, CUSTOMER_SIGN, "
					+ "DEATH_CER_ISSUED_FROM, DEATH_CER_ISSUED_TYPE, REG_NUM_OF_DEATH_CER, DATE_OF_DEATH_CER,"
					+ "CERTIFYING_DOCTOR, PRI_HEALTH_STMT, EXACT_CAUSE_OF_DEATH, UNDERLYING_DEATH_CAUSE, "
					+ "AGENT, DEATH_LOCATION, HOSP_NAME ,HOSP_ADMISSION_DATE, HOSP_DISCHARGE_DATE, "
					+ "IC_CODE_1, IC_CODE_2, IC_CODE_3, IC_CODE_4, IC_CODE_5, IC_CODE_6 "
					+ "FROM NG_CLM_Ext_Table WITH(NOLOCK) where WI_NAME = '"+WI_NAME+"'";
			
			log.WriteToLog("In Death Claim Waiver of Premium frame for Select Query" + Query);
			List<List<String>> NG_CLM_Ext_Table_Data = formObject.getDataFromDataSource(Query);
			
			if(NG_CLM_Ext_Table_Data.get(0).get(0)!=null)
			{
				formObject.setNGValue("DatePicker27",NG_CLM_Ext_Table_Data.get(0).get(0).replaceAll("-", "/").substring(0, 10));

			}
			
			formObject.setNGValue("Combo122",NG_CLM_Ext_Table_Data.get(0).get(1));
			
			if(NG_CLM_Ext_Table_Data.get(0).get(2)!=null)
			{
				formObject.setNGValue("DatePicker26",NG_CLM_Ext_Table_Data.get(0).get(2).replaceAll("-", "/").substring(0, 10));

			}
			if(NG_CLM_Ext_Table_Data.get(0).get(3)!=null)
			{
				formObject.setNGValue("DatePicker25",NG_CLM_Ext_Table_Data.get(0).get(3).replaceAll("-", "/").substring(0, 10));

			}
			
			formObject.setNGValue("Combo41",NG_CLM_Ext_Table_Data.get(0).get(4));
			formObject.setNGValue("Option4",NG_CLM_Ext_Table_Data.get(0).get(5));
			formObject.setNGValue("Combo30",NG_CLM_Ext_Table_Data.get(0).get(6));
			formObject.setNGValue("Combo40",NG_CLM_Ext_Table_Data.get(0).get(7));
			formObject.setNGValue("Combo39",NG_CLM_Ext_Table_Data.get(0).get(8));
			formObject.setNGValue("Combo35",NG_CLM_Ext_Table_Data.get(0).get(9));
			formObject.setNGValue("Combo38",NG_CLM_Ext_Table_Data.get(0).get(10));
			formObject.setNGValue("Combo34",NG_CLM_Ext_Table_Data.get(0).get(11));
			formObject.setNGValue("Text61",NG_CLM_Ext_Table_Data.get(0).get(12));
			
			if(NG_CLM_Ext_Table_Data.get(0).get(13)!=null)
			{
				formObject.setNGValue("DatePicker24",NG_CLM_Ext_Table_Data.get(0).get(13).replaceAll("-", "/").substring(0, 10));

			}
			formObject.setNGValue("Text59",NG_CLM_Ext_Table_Data.get(0).get(14));
			formObject.setNGValue("Combo33",NG_CLM_Ext_Table_Data.get(0).get(15));
			formObject.setNGValue("Combo37",NG_CLM_Ext_Table_Data.get(0).get(16));
			formObject.setNGValue("Text145",NG_CLM_Ext_Table_Data.get(0).get(17));
			formObject.setNGValue("Combo36",NG_CLM_Ext_Table_Data.get(0).get(18));
			formObject.setNGValue("Combo31",NG_CLM_Ext_Table_Data.get(0).get(19));
			formObject.setNGValue("Text60",NG_CLM_Ext_Table_Data.get(0).get(20));
			
			if(NG_CLM_Ext_Table_Data.get(0).get(21)!=null)
			{
				formObject.setNGValue("DatePicker22",NG_CLM_Ext_Table_Data.get(0).get(21).replaceAll("-", "/").substring(0, 10));

			}
			if(NG_CLM_Ext_Table_Data.get(0).get(22)!=null)
			{
				formObject.setNGValue("DatePicker23",NG_CLM_Ext_Table_Data.get(0).get(22).replaceAll("-", "/").substring(0, 10));
			}
			
			
			formObject.setNGValue("Text68",NG_CLM_Ext_Table_Data.get(0).get(23)); //IC Code 1
			formObject.setNGValue("Text67",NG_CLM_Ext_Table_Data.get(0).get(24)); //IC Code 2
			formObject.setNGValue("Text65",NG_CLM_Ext_Table_Data.get(0).get(25)); //IC Code 3
			formObject.setNGValue("Text64",NG_CLM_Ext_Table_Data.get(0).get(26)); //IC Code 4
			formObject.setNGValue("Text62",NG_CLM_Ext_Table_Data.get(0).get(27)); //IC Code 5
			formObject.setNGValue("Text63",NG_CLM_Ext_Table_Data.get(0).get(28)); //IC Code 6
		}
		if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Critical Illness/Dreaded Disease Claim"))
		{
			
			log.WriteToLog("In Critical Illness/Dreaded Disease Claim Frame for executing select query");
			String Query = "SELECT CLM_ON, CLM_VALID_NOTIF_DATE, CLM_INTIMATION_SRC, "
					+ "CUSTOMER_SIGN, AGENT, PRI_HEALTH_STMT, HOSP_NAME, HOSP_ADMISSION_DATE, "
					+ "HOSP_DISCHARGE_DATE, IC_CODE_1, IC_CODE_2, IC_CODE_3, IC_CODE_4, "
					+ "IC_CODE_5,IC_CODE_6 FROM NG_CLM_Ext_Table WITH(NOLOCK) where WI_NAME = '"+WI_NAME+"'";
			
			log.WriteToLog("In Critical Illness/Dreaded Disease Claim Frame Select Query : "+ Query);
			List<List<String>> NG_CLM_Ext_Table_Data = formObject.getDataFromDataSource(Query);

			formObject.setNGValue("Combo124",NG_CLM_Ext_Table_Data.get(0).get(0));
			
			if(NG_CLM_Ext_Table_Data.get(0).get(1)!=null)
			{
				formObject.setNGValue("DatePicker35",NG_CLM_Ext_Table_Data.get(0).get(1).replaceAll("-", "/").substring(0, 10));

			}
			
			formObject.setNGValue("Combo55",NG_CLM_Ext_Table_Data.get(0).get(2));
			formObject.setNGValue("Combo54",NG_CLM_Ext_Table_Data.get(0).get(3));
			formObject.setNGValue("Combo52",NG_CLM_Ext_Table_Data.get(0).get(4));
			formObject.setNGValue("Combo53",NG_CLM_Ext_Table_Data.get(0).get(5));
			formObject.setNGValue("Text89",NG_CLM_Ext_Table_Data.get(0).get(6));
			
			if(NG_CLM_Ext_Table_Data.get(0).get(7)!=null)
			{
				formObject.setNGValue("DatePicker39",NG_CLM_Ext_Table_Data.get(0).get(7).replaceAll("-", "/").substring(0, 10));

			}
			
			if(NG_CLM_Ext_Table_Data.get(0).get(8)!=null)
			{
				formObject.setNGValue("DatePicker38",NG_CLM_Ext_Table_Data.get(0).get(8).replaceAll("-", "/").substring(0, 10));
			}
			
			formObject.setNGValue("Text88",NG_CLM_Ext_Table_Data.get(0).get(9)); //IC Code 1
			formObject.setNGValue("Text87",NG_CLM_Ext_Table_Data.get(0).get(10));//IC Code 2
			formObject.setNGValue("Text85",NG_CLM_Ext_Table_Data.get(0).get(11));//IC Code 3
			formObject.setNGValue("Text84",NG_CLM_Ext_Table_Data.get(0).get(12));//IC Code 4
			formObject.setNGValue("Text82",NG_CLM_Ext_Table_Data.get(0).get(13));//IC Code 5
			formObject.setNGValue("Text83",NG_CLM_Ext_Table_Data.get(0).get(14));//IC Code 6
		}
		if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Hospitalization Claim"))
		{
			log.WriteToLog("In Hospitalization Claim Frame for select Query: ");
		
			
			String Query = "SELECT CLM_ON, CLM_VALID_NOTIF_DATE,CLM_INTIMATION_SRC,CUSTOMER_SIGN,AGENT,PRI_HEALTH_STMT,"
					+ " HOSP_ADMISSION_DATE, HOSP_DISCHARGE_DATE,ICU_ADMISSION,ICU_DISCHARGE, IC_CODE_1, "
					+ " IC_CODE_2, IC_CODE_3, IC_CODE_4, IC_CODE_5, IC_CODE_6 "
					+ " FROM NG_CLM_Ext_Table WITH(NOLOCK) where WI_NAME = '"+WI_NAME+"'";
			
	
			
			log.WriteToLog("In Hospitalization Claim Frame Executing select Query: "+Query);
			List<List<String>> NG_CLM_Ext_Table_Data = formObject.getDataFromDataSource(Query);
			log.WriteToLog("In Assessment Hospitalization Claim Frame External Data Result : "+NG_CLM_Ext_Table_Data);
			formObject.setNGValue("Combo125",NG_CLM_Ext_Table_Data.get(0).get(0));
			formObject.setNGValue("DatePicker40",NG_CLM_Ext_Table_Data.get(0).get(1).replaceAll("-", "/").substring(0, 10));
//			formObject.setNGValue("DatePicker",NG_CLM_Ext_Table_Data.get(0).get(2).replaceAll("-", "/").substring(0, 10));
//			formObject.setNGValue("DatePicker26",NG_CLM_Ext_Table_Data.get(0).get(3).replaceAll("-", "/").substring(0, 10));
			formObject.setNGValue("Combo49",NG_CLM_Ext_Table_Data.get(0).get(2));
			formObject.setNGValue("Combo32",NG_CLM_Ext_Table_Data.get(0).get(3));
			formObject.setNGValue("Combo11",NG_CLM_Ext_Table_Data.get(0).get(4));
			formObject.setNGValue("Combo26",NG_CLM_Ext_Table_Data.get(0).get(5));
			
			if(NG_CLM_Ext_Table_Data.get(0).get(6)!=null)
			{
				formObject.setNGValue("HOSP_ADMISSION_DATE",NG_CLM_Ext_Table_Data.get(0).get(6).replaceAll("-", "/").substring(0, 10));
				log.WriteToLog(NG_CLM_Ext_Table_Data.get(0).get(6).replaceAll("-", "/").substring(0, 10));
				formObject.setNGValue("HA_Hour", NG_CLM_Ext_Table_Data.get(0).get(6).replaceAll("-", "/").substring(11, 13));
				log.WriteToLog(NG_CLM_Ext_Table_Data.get(0).get(6).replaceAll("-", "/").substring(11, 13));
				formObject.setNGValue("HA_Minute", NG_CLM_Ext_Table_Data.get(0).get(6).replaceAll("-", "/").substring(14, 16));
				log.WriteToLog(NG_CLM_Ext_Table_Data.get(0).get(6).replaceAll("-", "/").substring(14, 16));
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
			
			formObject.setNGValue("Text90",NG_CLM_Ext_Table_Data.get(0).get(10)); //IC Code 1
			formObject.setNGValue("Text91",NG_CLM_Ext_Table_Data.get(0).get(11)); //IC Code 2
			formObject.setNGValue("Text94",NG_CLM_Ext_Table_Data.get(0).get(12)); //IC Code 3
			formObject.setNGValue("Text95",NG_CLM_Ext_Table_Data.get(0).get(13)); //IC Code 4
			formObject.setNGValue("Text97",NG_CLM_Ext_Table_Data.get(0).get(14)); //IC Code 5
			formObject.setNGValue("Text96",NG_CLM_Ext_Table_Data.get(0).get(15)); //IC Code 6
		}
		if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("TPDB Claim"))
		{
			log.WriteToLog("In TPDB Claim Frame");
			
			String Query = "SELECT NAV_DATE,CLM_ON,CLM_VALID_NOTIF_DATE,CLM_INTIMATION_SRC,"
					+ "CUSTOMER_SIGN,AGENT,PRI_HEALTH_STMT,HOSP_NAME,HOSP_ADMISSION_DATE,"
					+ "HOSP_DISCHARGE_DATE,IC_CODE_1,IC_CODE_2,IC_CODE_3,IC_CODE_4,IC_CODE_5,"
					+ "IC_CODE_6 FROM NG_CLM_Ext_Table WITH(NOLOCK) where WI_NAME = '"+WI_NAME+"'";
			
			log.WriteToLog("In TPDB Claim Frame executing select Query : "+Query);
			List<List<String>> NG_CLM_Ext_Table_Data = formObject.getDataFromDataSource(Query);

			if(NG_CLM_Ext_Table_Data.get(0).get(0)!=null)
			{
				formObject.setNGValue("DatePicker50",NG_CLM_Ext_Table_Data.get(0).get(0).replaceAll("-", "/").substring(0, 10));

			}
			
			formObject.setNGValue("Combo126",NG_CLM_Ext_Table_Data.get(0).get(1));
			
			if(NG_CLM_Ext_Table_Data.get(0).get(2)!=null)
			{
				formObject.setNGValue("DatePicker47",NG_CLM_Ext_Table_Data.get(0).get(2).replaceAll("-", "/").substring(0, 10));
	
			}
			formObject.setNGValue("Combo116",NG_CLM_Ext_Table_Data.get(0).get(3));
			formObject.setNGValue("Combo115",NG_CLM_Ext_Table_Data.get(0).get(4));
			formObject.setNGValue("Combo113",NG_CLM_Ext_Table_Data.get(0).get(5));
			formObject.setNGValue("Combo114",NG_CLM_Ext_Table_Data.get(0).get(6));
			formObject.setNGValue("Text113",NG_CLM_Ext_Table_Data.get(0).get(7));
			
			if(NG_CLM_Ext_Table_Data.get(0).get(8)!=null)
			{
				formObject.setNGValue("DatePicker56",NG_CLM_Ext_Table_Data.get(0).get(8).replaceAll("-", "/").substring(0, 10));

			}
			
			if(NG_CLM_Ext_Table_Data.get(0).get(9)!=null)
			{
				formObject.setNGValue("DatePicker52",NG_CLM_Ext_Table_Data.get(0).get(9).replaceAll("-", "/").substring(0, 10));
			}
			formObject.setNGValue("Text104",NG_CLM_Ext_Table_Data.get(0).get(10));
			formObject.setNGValue("Text105",NG_CLM_Ext_Table_Data.get(0).get(11));
			formObject.setNGValue("Text108",NG_CLM_Ext_Table_Data.get(0).get(12));
			formObject.setNGValue("Text106",NG_CLM_Ext_Table_Data.get(0).get(13));
			formObject.setNGValue("Text111",NG_CLM_Ext_Table_Data.get(0).get(14));
			formObject.setNGValue("Text110",NG_CLM_Ext_Table_Data.get(0).get(15));
			
		 }
		log.WriteToLog("End of Form Populated Event");
	}
	
	public void datePopulation(String dateOfEventString, String dateOfReinstatementString, String dateOfPolicyRiskString)
	{
		
		FormReference formObject = FormContext.getCurrentInstance().getFormReference();
		Date dateOfEvent = null; 
		Date dateOfReinstatement = null;
		Date dateOfPolicyRisk = null;
		long diffMiliSeconds;
		long diffMonths;
		long diffDays;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		
		log.WriteToLog("In Date Population Function : ");
		try {
		//	dateOfEventString=dateOfEventString.replaceAll("-", "/");
			log.WriteToLog("Date of Event String : "+dateOfEventString 
					+"\nDate of Reinstatement String : "+dateOfReinstatementString 
					+"\nDate of Policy Risk String : "+dateOfPolicyRiskString);
			
		//	log.WriteToLog("Setting Date of Event in DatePicker62 ");
		//	formObject.setNGValue("DatePicker62", dateOfEventString);
			
			log.WriteToLog("Before Date Of Event Format");
			dateOfEvent = formatter.parse(dateOfEventString); 
			
			log.WriteToLog("After date of Event format : "+dateOfEvent);
			if(dateOfReinstatementString==null || dateOfReinstatementString.equalsIgnoreCase(""))
			{
				log.WriteToLog("Date of String is NULL");
				formObject.setNGValue("DatePicker3", "N.A.");
			}
			else
			{
				log.WriteToLog("Before Date of Reinstatement String replace all : "+dateOfReinstatementString);
				dateOfReinstatementString=dateOfReinstatementString.replaceAll("-", "/");
				formObject.setNGValue("DatePicker3", dateOfReinstatement);
				log.WriteToLog("after Date of Reinstatement String replace all : "+dateOfReinstatementString);
				dateOfReinstatement = formatter.parse(dateOfReinstatementString);
				log.WriteToLog("After date of reinstatement String parsing : "+dateOfReinstatement);	
				log.WriteToLog("Date of Reinstatment is : "+dateOfReinstatement);
			}	
			log.WriteToLog("Before policy risk replace all :  "+dateOfPolicyRiskString);
			dateOfPolicyRiskString = dateOfPolicyRiskString.replaceAll("-", "/");
			log.WriteToLog("after date of policy risk replace all  : "+dateOfPolicyRiskString);
			
			dateOfPolicyRisk = formatter.parse(dateOfPolicyRiskString);
			log.WriteToLog("After date of policy risk format parse : "+dateOfPolicyRisk);
		} catch (Exception e) {
			log.WriteToLog(e.toString());
		}
		
		
		diffMiliSeconds = dateOfEvent.getTime() - dateOfPolicyRisk.getTime();
		diffMonths = diffMiliSeconds / (24*60*60*1000)/30;
		diffDays = diffMiliSeconds / (24 *60*60*1000) % 30;
		log.WriteToLog("Difference in Months : "+diffMonths +" Diffrence in Days : "+diffDays);
		formObject.setNGValue("Text146", diffMonths + " Months " + diffDays +" Days ");
		
		if(dateOfReinstatementString==null || dateOfReinstatementString.equalsIgnoreCase(""))
		{				
			formObject.setNGValue("Text153", "N.A.");
		}
		else
		{
			diffMiliSeconds = dateOfEvent.getTime() - dateOfReinstatement.getTime();
			log.WriteToLog("Difference in MiliSeconds is "+diffMiliSeconds);
			diffMonths = diffMiliSeconds / (24*60*60*1000)/30;
			log.WriteToLog("Difference in Months is : "+diffMonths);
			diffDays = diffMiliSeconds / (24 *60*60*1000) % 30;
//			log.WriteToLog("Difference in Days is : "+diffDays);
			log.WriteToLog("Difference in Months : "+diffMonths +" Diffrence in Days : "+diffDays);
			log.WriteToLog("Setting Text153 as Months and Days ");
			formObject.setNGValue("Text153", diffMonths + " Months " + diffDays +" Days ");
		}
	}
	
	
	private void showHideFields(FormReference formObject,String fieldList,boolean visibility)
	{
		String field_array[] = fieldList.split("~");
		for(int i=0;i<field_array.length;i++)
		{
			formObject.setVisible(field_array[i], visibility);
		}
	}
	
	
	

	@Override
	public void saveFormCompleted(FormEvent arg0) throws ValidatorException {
		// TODO Auto-generated method stub
		log.WriteToLog("Save Form Completed Event Started");
		log.WriteToLog("End of Save Form Completed Event");
	}

	@Override
	public void saveFormStarted(FormEvent arg0) throws ValidatorException {
		// TODO Auto-generated method stub
		log.WriteToLog("Save Form Started Event Started");
		log.WriteToLog("End of Save Form Started Event");
		

		
	}

	@Override
	public void submitFormCompleted(FormEvent arg0) throws ValidatorException {
		// TODO Auto-generated method stub
		log.WriteToLog("Submit Form Compledted Started");
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
		log.WriteToLog("End of Submit Form Completed");
		
		log.WriteToLog("Executing NG_CLM_Workstep_TAT_Procedure");
		String workstepTATProcedureName = "NG_CLM_Workstep_TAT_Procedure";
		List <String> workstepTATProcedureParameters = new ArrayList<String>();
		workstepTATProcedureParameters.add("text:"+workItemName);
		workstepTATProcedureParameters.add("text:"+activityName);
		log.WriteToLog("workstepTATProcedureParameters : "+workstepTATProcedureParameters);
		formObject.getDataFromStoredProcedure(workstepTATProcedureName,workstepTATProcedureParameters);
		
		
	}

	@Override
	public void submitFormStarted(FormEvent arg0) throws ValidatorException {
		// TODO Auto-generated method stub
		log.WriteToLog("In Save for Started");
		log.WriteToLog("In form loaded event ");
		FormReference formObject = FormContext.getCurrentInstance().getFormReference();
		String WI_NAME = formObject.getNGValue("WI_NAME");
		String CEI_Decision = formObject.getNGValue("CEI_DECISION");

		String workItemName = formObject.getWFWorkitemName();
		String itemIndexQuery= "select ITEMINDEX from NG_CLM_Ext_Table with(NOLOCK) where WI_NAME ='"+workItemName+"'";
		String itemIndex = formObject.getDataFromDataSource(itemIndexQuery).get(0).get(0);
		String countDocQuery = "select count(WI_NAME) from NG_CLM_ClaimIntimation_Doc_Grid Where WI_NAME ='"+itemIndex+"'  and Doc_Status ='Pending'";
		List<List<String>> countDocResult = formObject.getDataFromDataSource(countDocQuery);
		int countDoc;
		if(!countDocResult.isEmpty())
		{
			countDoc=Integer.parseInt(countDocResult.get(0).get(0));
			if(countDoc >0 && CEI_Decision.equalsIgnoreCase("Send To Supervisor"))
			{
				throw new ValidatorException(new FacesMessage("One or more document is pending."));

			}
		}
			
		if(CEI_Decision.equalsIgnoreCase("Send To Supervisor"))
		{
			String worksheetFetchPDFQuery = "select WORKSHEET_FETCH_STATUS from NG_CLM_EXT_TABLE with(NOLOCK) where WI_NAME ='"+WI_NAME+"'";
			String worksheetPDFFlag = formObject.getDataFromDataSource(worksheetFetchPDFQuery).get(0).get(0);
			if(!worksheetPDFFlag.equalsIgnoreCase("Y"))
			{
				throw new ValidatorException(new FacesMessage("Worksheet is not fetched please try after some time."));
			}
		}
		
	//This code is to give a pop that document has been added to give a popup that document has been added.
 	
//		String newDocIndicatorQuery = "SELECT NEW_DOC_INDICATOR FROM NG_CLM_Ext_Table with(NOLOCK) WHERE WI_NAME = '"+WI_NAME+"'"; 
//		String newDocIndicator = formObject.getDataFromDataSource(newDocIndicatorQuery).get(0).get(0);
//		if(newDocIndicator.equalsIgnoreCase("Y"))
//		{
			String newDocIndicatorUpdateQuery = "update NG_CLM_Ext_Table set NEW_DOC_INDICATOR='N' WHERE WI_NAME = '"+WI_NAME+"'"; 
			formObject.saveDataIntoDataSource(newDocIndicatorUpdateQuery);		
			log.WriteToLog("New Doc Indicator Flag Update Query : "+newDocIndicatorUpdateQuery);
//			throw new ValidatorException(new FacesMessage("New Document has been fetched press ok to continue"));
//		
//		}
		
		
		log.WriteToLog("End of Submit Form Started.");
		
	}
	
	 private void populatefatcadropdown(FormReference formObject) {

	        List<String> d1 = new ArrayList<String>();
	        d1.add("FragFATCA_countryTaxResidency1");
	        d1.add("FragFATCA_countryTaxResidency2");
	        d1.add("FragFATCA_countryTaxResidency3");
	        d1.add("FragFATCA_country");
	        d1.add("FragFATCA_country_Birth");

	        List<String> d2 = new ArrayList<String>();
	        d2.add("FragFATCA_identificationType1");
	        d2.add("FragFATCA_identificationType2");
	        d2.add("FragFATCA_identificationType3");

	        List<String> d3 = new ArrayList<String>();
	        d3.add("FragFATCA_address_Type");
	        List<String> d4 = new ArrayList<String>();
	        d4.add("FragFATCA_telephone_Type");
//	        List<String> d5 = new ArrayList<String>();
//	        d5.add("FragFATCA_gender");
	        List<String> d6 = new ArrayList<String>();
	        d6.add("FragFATCA_proof_Identity");


	        formObject.getDataFromDataSource("SELECT strcddesc FROM com_param_user_m WITH(nolock) WHERE IPARAMTYPECD='5518'", d1);
	        formObject.getDataFromDataSource("SELECT strcddesc FROM com_param_user_m WITH(nolock) WHERE IPARAMTYPECD='1021'", d2);
	        formObject.getDataFromDataSource("SELECT strcddesc FROM com_param_user_m WITH(nolock) WHERE IPARAMTYPECD='1022'", d3);
	        formObject.getDataFromDataSource("SELECT strcddesc FROM com_param_user_m WITH(nolock) WHERE IPARAMTYPECD='5521'", d4);
//	        formObject.getDataFromDataSource("SELECT strcddesc FROM com_param_system_m WITH(nolock) WHERE IPARAMTYPECD='5504'", d5);
	        formObject.getDataFromDataSource("SELECT strcddesc FROM com_param_user_m WITH(nolock) WHERE IPARAMTYPECD='1023'", d6);
	    }
	 
	 private void frag_combo_populate(FormReference formObject) {
	        /* Added by AKANCHHA */
	        List<String> d1 = new ArrayList<String>();
	        d1.add("FragCpx_title");
	        List<String> d2 = new ArrayList<String>();
	        d2.add("FragCpx_relationship");
	        List<String> d3 = new ArrayList<String>();
	        d3.add("Contract_Details_New_Combo3");
	        List<String> d4 = new ArrayList<String>();
	        d4.add("Contract_Details_New_Combo5");

	        List<String> tel_typel = new ArrayList<String>();
	        tel_typel.add("Contract_Details_New_Combo2");

	        List<String> d5 = new ArrayList<String>();
	        d5.add("FragCpx_id_Type");
	        List<String> d6 = new ArrayList<String>();
	        d6.add("FragCpx_insurance_Repository_Code");
	        List<String> d7 = new ArrayList<String>();
	        d7.add("FragCpx_id_Type");
	        List<String> d8 = new ArrayList<String>();
	        d8.add("FragCpx_occupation");
	        List<String> d9 = new ArrayList<String>();
	        d9.add("FragCpx_industry");
	        //     List<String> d10 = new ArrayList<String>();
	        //    d10.add("Contract_Details_New_Combo4");
	        //  formObject.clear("FragCpx_education_Qualification");
	        List<String> d11 = new ArrayList<String>();
	        d11.add("FragCpx_education_Qualification");
	        //Added by Shivam wrt Employemnt Status refreshed dynamically
	        List<String> d12 = new ArrayList<String>();
	        d12.add("FragCpx_employment_Status");

	        formObject.getDataFromDataSource("SELECT Strcddesc FROM com_param_user_m WITH (NOLOCK) WHERE IPARAMTYPECD = 5502", d1);
	        formObject.getDataFromDataSource("SELECT Strcddesc FROM com_param_system_m  WITH (NOLOCK) WHERE IPARAMTYPECD = 5522", d2);
	        formObject.getDataFromDataSource("SELECT strcddesc FROM com_param_user_m WITH (NOLOCK) WHERE iparamtypecd=5518 ", d3);
	        formObject.getDataFromDataSource("SELECT Distinct(strstate) FROM com_pin_code_map WITH (NOLOCK)", d4);

	        formObject.getDataFromDataSource("SELECT Distinct(strcddesc) FROM com_param_user_m WITH (NOLOCK)  WHERE IPARAMTYPECD = 5521", tel_typel);

	        formObject.getDataFromDataSource("SELECT Strcddesc FROM com_param_user_m WITH (NOLOCK) WHERE iparamtypecd=2524", d5);
	        formObject.getDataFromDataSource("SELECT Strcddesc FROM com_param_user_m WITH (NOLOCK) WHERE iparamtypecd=7001", d6);
	        formObject.getDataFromDataSource("SELECT Strcddesc FROM com_param_user_m  WITH (NOLOCK) WHERE iparamtypecd=7001", d6);
	        formObject.getDataFromDataSource("SELECT Strcddesc FROM com_param_user_m  WITH (NOLOCK) WHERE iparamtypecd=2524", d7);
	        formObject.getDataFromDataSource("SELECT STROCCUPNAMe FROM com_occupation_m WITH (NOLOCK) ", d8);
	        formObject.getDataFromDataSource("SELECT Strcddesc FROM com_param_user_m WITH (NOLOCK)  WHERE IPARAMTYPECD = 2522", d9);
	        //   formObject.getDataFromDataSource("SELECT Distinct(strcity) FROM com_pin_code_map WITH (NOLOCK)", d10);
	        formObject.getDataFromDataSource("SELECT strcddesc FROM com_param_user_m WITH(nolock) WHERE IPARAMTYPECD=9972", d11);
	        formObject.getDataFromDataSource("SELECT strcddesc FROM com_param_system_m with(nolock) WHERE IPARAMTYPECD=5524", d12);

	        List<String> add_category_list = new ArrayList<String>();
	        add_category_list.add("Contract_Details_Combo1");
	        formObject.clear("Contract_Details_Combo1");

	        formObject.getDataFromDataSource("SELECT strcddesc FROM com_param_user_m WHERE IPARAMTYPECD=5519", add_category_list);

	    }
	 
	 private void chk_add_accFATCA(FormReference formObject, String itemindex) {
	        String isfatcacompliantq = "SELECT a.isfatcacompliant FROM NG_CLM_Client_Details a WITH(nolock) WHERE a.itemindex='" + itemindex + "'";
	        List isfatcalist = formObject.getDataFromDataSource(isfatcacompliantq);
	        if (isfatcalist.size() != 0) {
	            ArrayList is_fatcaarrlist = (ArrayList) isfatcalist.get(0);
	            String isfatcacompliant = String.valueOf(is_fatcaarrlist.get(0)).trim();
	            if (isfatcacompliant.equalsIgnoreCase("No") && !formObject.getNGValue("Contract_Details_New_Combo3").equalsIgnoreCase("India")) {
	                throw new ValidatorException(new FacesMessage("FATCA/CRS declaration details are unavailable for the client. Foreign country address cannot be added ", "Contract_Details_New_Combo3"));
	            }
	            if (isfatcacompliant.equalsIgnoreCase("Yes") && !formObject.getNGValue("Contract_Details_New_Combo3").equalsIgnoreCase("India") && formObject.getNGValue("Contract_Details_New_LA_CheckBox6").equalsIgnoreCase("True")) {
	                throw new ValidatorException(new FacesMessage("Foreign country address cannot be set as correspondence address", "Contract_Details_New_LA_CheckBox6"));
	            }
	        }
	    }
	 
	 private void setAppointeeFrame(FormReference formObject) {
	        formObject.setTop("frAppointeeDetails", 30);
	        formObject.setLeft("frAppointeeDetails", 40);
	        formObject.setWidth("frAppointeeDetails", 750);
	        formObject.setHeight("frAppointeeDetails", 750);
	    }
	 
	 private void fetchAppointee(FormReference formObject) {
	        formObject.fetchFragment("frAppointeeDetails", "Appointee", "qAppointee");//Contract_Details_New_LA_FATCAFrame--qAppointee     
	    }
	 
	 private void populateAppointeedropdown(FormReference formObject) {

	        List<String> d111 = new ArrayList<String>();
	        d111.add("Appointee_Relation");

	        formObject.clear("Appointee_Relation");
	        formObject.getDataFromDataSource("SELECT Strcddesc FROM com_param_system_m  WITH (NOLOCK) WHERE IPARAMTYPECD = 5522 And Strcddesc <> 'Self'", d111);
	        formObject.setEnabled("Appointee_Relation", true);
	        formObject.addItem("Appointee_Relation", "");
	        formObject.setNGValue("Appointee_Relation", "");
	    }
	 
	 private void viewAppointeeFrame(FormReference formObject) {
	        formObject.setVisible("frAppointeeDetails", true);
	    }
	 
}
