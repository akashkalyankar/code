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
	Problem No.		Changed Date	Changed By 				Description				
					
					31/10/2018		Akash Kalynakar		tab named communication details need to be hidden in some forms
					22/12/2018		Akash Kalynakar 	With Primary Claimant name address email field in communication pending
					23/12/2018		Akash Kalynakar		countStrproline query updation	
					24/12/2018		Akash Kalynakar		With Change in claimtype query for submit form completed validation			
					11/01/2019		Akash Kalynakar		Workstep TAT procedure call on submit button and Assessor From To Change
					12/01/2019		Akash Kalynakar		With Unpaid Premium Durations Quries in Communication Pending
					14/01/2019		Akash Kalynakar		With Policy Status as ON DOE
					16/01/2019		Akash Kalynakar		With Unpaid premium premium and duration changes in communication pending
					17/01/2019		Akash Kalynakar		With Change in PolicyStatusASOnDOE if condtion of null in claim Number
					18/01/2019		Akash Kalynakar		With Date Of Event and respective changes
					
----------------------------------------------------------------------------------------------------------------------------

	Functions with there usages

---------------------------------------------------------------------------------------------------------------------------*/

package com.newgen.omniforms.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

import com.newgen.omniforms.FormConfig;
import com.newgen.omniforms.FormReference;
import com.newgen.omniforms.context.FormContext;
import com.newgen.omniforms.event.ComponentEvent;
import com.newgen.omniforms.event.FormEvent;
import com.newgen.omniforms.listener.FormListener;

public class NG_CLM_CommunicationPending implements FormListener {
	WSDemo WS = new WSDemo();
	NG_CLM_Logger log;
	int webServiceGridCount=0;

	@Override
	public void continueExecution(String arg0, HashMap<String, String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventDispatched(ComponentEvent pEvent) throws ValidatorException {
		// TODO Auto-generated method stub
		FormReference formObject = FormContext.getCurrentInstance().getFormReference();
		FormConfig formConfigObject = FormContext.getCurrentInstance().getFormConfig();
		log = new NG_CLM_Logger(formObject.getWFWorkitemName());
		String WI_NAME = formObject.getWFWorkitemName();
		String claimNumber = formObject.getNGValue("CLM_NUMBER");
		
		switch(pEvent.getType())
		{
		case MOUSE_CLICKED:
		{
			if(pEvent.getSource().getName().equalsIgnoreCase("Save"))
			{
				if(formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Communication_pending")||
					formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Supervisor"))
				{
					String claimsDecision = formObject.getNGValue("CLAIMS_DECISION");
					
					if(claimsDecision.equalsIgnoreCase("Claim Rejected"))
					{
						String rejectionReason = formObject.getNGValue("Combo6");
						String rejectionReasonUpdateQuery = "update NG_CLM_Ext_Table set REJECTION_REASON ='"+rejectionReason+"' where WI_NAME ='"+WI_NAME+"'";
						log.WriteToLog("Rejection Reason Update Query : "+rejectionReasonUpdateQuery);
						formObject.saveDataIntoDataSource(rejectionReasonUpdateQuery);
					}					
				}

				formObject.RaiseEvent("WFSave",true);
				
				//This code is resetting new_doc_indicator flag to 'N'
				String newDocIndicatorUpdateQuery = "update NG_CLM_Ext_Table set NEW_DOC_INDICATOR='N' WHERE WI_NAME = '"+WI_NAME+"'"; 
				log.WriteToLog("New Doc Indicator Flag Update Query : "+newDocIndicatorUpdateQuery);
				formObject.saveDataIntoDataSource(newDocIndicatorUpdateQuery);		
				
				
			}//End of Save
			
			if(pEvent.getSource().getName().equalsIgnoreCase("Command12"))
			{
				WS.setClaimNumber(formObject.getNGValue("CLM_NUMBER"));
				log.WriteToLog("Claim Number "+formObject.getNGValue("CLM_NUMBER"));
				if(webServiceGridCount==0)
				{
					webServiceGridCount++;
					WS.setClaimNumber(formObject.getNGValue("CLM_NUMBER"));
					
					List<List<String>> statusWiseBenefitList = WS.getStatusWiseBenefitList(formObject.getNGValue("CLM_NUMBER"));
					log.WriteToLog("Adding Item in The Status Wise Benefit "+statusWiseBenefitList);
					if(!statusWiseBenefitList.isEmpty())
					{
						for(List<String> i :statusWiseBenefitList)
						{
							log.WriteToLog("Adding Item in The Status Wise Benefit "+i);
							formObject.addItem("ListView8", i);
						}
					}
					
				}
			}//End of Command12	
			break;
		}//End of MOUSE_CLICKED

		}//End of Switch
	}//end of Event Dispatched

	@Override
	public void formLoaded(FormEvent arg0) {
		
		log.WriteToLog("In Communication_pending Form Loaded");
	}

	@Override
	public void formPopulated(FormEvent arg0) {
	
		log.WriteToLog("Start of form Populated");
		log.WriteToLog("In Communication_pending Form Populated.");
		
		FormReference formObject = FormContext.getCurrentInstance().getFormReference();
		FormConfig formConfigObject = FormContext.getCurrentInstance().getFormConfig();
		String WI_NAME = formObject.getWFWorkitemName();
		log.WriteToLog("Before Claim Number");
		String claimNumber = formObject.getNGValue("CLM_NUMBER");
		log.WriteToLog("Claim Number : "+ claimNumber);
	
		
		log.WriteToLog("Acitivity Name is : "+formConfigObject.getConfigElement("ActivityName"));
		
		if(formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Communication_pending")||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Final_Approver")||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Final_Decision_Bucket")||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Supervisor"))
		{
			if(formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Final_Approver")||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Final_Decision_Bucket")||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Supervisor"))
			{
				log.WriteToLog("Hiding Communication Pending tab");
				formObject.setSheetVisible("Tab1",2, false);
			}
			
			
					String claimsDecision = formObject.getNGValue("CLAIMS_DECISION");
					if(claimsDecision.equalsIgnoreCase("Claim Rejected"))
					{
						
						String rejectionReasonUpdateQuery = "SELECT REJECTION_REASON FROM NG_CLM_Ext_Table where WI_NAME ='"+WI_NAME+"'";
						List<List<String>>rejectionReasonResult = formObject.getDataFromDataSource(rejectionReasonUpdateQuery);
						log.WriteToLog("Rejection Reason Update Query : "+rejectionReasonUpdateQuery);
						String rejectionReason = rejectionReasonResult.get(0).get(0);
						formObject.setNGValue("Combo6", rejectionReason);
					}					
				
			
			//DTPOLREVIVAL = dateOfReinstatement
			//DTPOLRISKCOMC = dateOfPolicyRisk

			String policyNumber = formObject.getNGValue("Policy_No");		
			
//			String dateAndCauseOfEvent = "select format(DATE_OF_DEATH,'yyyy/MM/dd'),format(DATE_OF_ACCIDENT,'yyyy/MM/dd'),"
//					+ " format(DATE_OF_DIAGNOSIS,'yyyy/MM/dd'),format(CLM_VALID_NOTIF_DATE,'yyyy/MM/dd'),"
//					+ " format(DATE_OF_DISABLEMENT,'yyyy/MM/dd'),PRI_CAUSE_OF_DEATH,NATURE_OF_DEATH,MAJOR_DISEASE_TYPE,"
//					+ " CAUSE_OF_ILLNESS, CAUSE_OF_DISABLEMENT,CLM_ON,POLICY_STATUS from NG_CLM_Ext_Table "
//					+ " WITH(NOLOCK) WHERE WI_NAME = '"+WI_NAME+"'";
			
		//	String dateAndCauseOfEvent = "CLM_ON from NG_CLM_Ext_Table "
		//			+ " WITH(NOLOCK) WHERE WI_NAME = '"+WI_NAME+"'";
			
		//	log.WriteToLog("");
			
		//	log.WriteToLog("Date Of Event Query : "+dateAndCauseOfEvent);
		//	List<List<String>> dateAndCauseOfEventResult = formObject.getDataFromDataSource(dateAndCauseOfEvent);
		//	log.WriteToLog("Date of Event Result : "+dateAndCauseOfEventResult);
			
			String claimOn = formObject.getNGValue("CLM_ON");
			
		//	claimOn = dateAndCauseOfEventResult.get(0).get(2);
			
			//String policyStatus = dateAndCauseOfEventResult.get(0).get(11);
			String totalClaimAmount = formObject.getNGValue("TOTAL_CLM_AMT");
			
			formObject.setNGValue("Text10",totalClaimAmount);
			//formObject.setNGValue("Text16",policyStatus);
			
			formObject.setNGValue("Text131", formObject.getNGValue("PRI_CLMT_NAME"));
			formObject.setNGValue("Text138", formObject.getNGValue("PRI_CLMT_CONTACT_NUM"));
			formObject.setNGValue("Text132", formObject.getNGValue("PRI_CONTACT_EMAIL"));
			
			//formObject.setNGValue("Text45", claimOn);
			String ClaimOnIdQuery= "Select CLAIM_ON_ID from NG_CLM_ClaimOnID_Master WITH(NOLOCK) where CLAIM_ON='"+claimOn+"' and WI_NAME = '"+WI_NAME+"'";  
			
			log.WriteToLog("Claim ON ID Query"+ClaimOnIdQuery);
			List<List<String>> ClaimOnIdResult = formObject.getDataFromDataSource(ClaimOnIdQuery);
			
			log.WriteToLog("Claim On Id Result : "+ClaimOnIdResult);
			String claimOnId=null;
			if(!ClaimOnIdResult.isEmpty())
			{
				claimOnId = ClaimOnIdResult.get(0).get(0);
				log.WriteToLog("Claim ON id : "+claimOnId);
			}
			
			
			
			String reinstatementRCDDateQuery = "select isnull(convert(nvarchar(100),convert(date,DTPOLREVIVAL)),''),isnull(convert(nvarchar(100),convert(date,DTPOLRISKCOMC)),'') from STAGNEW2.Staging_claim.dbo.COM_POLICY_M WHERE STRPOLNBR = '"+policyNumber+"'";
			log.WriteToLog("ReinstatementRCD Date Query : "+reinstatementRCDDateQuery);
	
			
			List<List<String>> reinstatementRCDDateResult = formObject.getDataFromDataSource(reinstatementRCDDateQuery);
			
			log.WriteToLog("Reinstatement RCD Date Result : "+reinstatementRCDDateResult);
			String dateOfReinstatementString=null;
			String dateOfPolicyRiskString  =null;
			if(!reinstatementRCDDateResult.isEmpty())
			{
				dateOfReinstatementString = reinstatementRCDDateResult.get(0).get(0);			
				log.WriteToLog("Date of Reinstatement String : "+dateOfReinstatementString);
				
				dateOfPolicyRiskString = reinstatementRCDDateResult.get(0).get(1);
				log.WriteToLog("Date of Policy Risk String : "+dateOfPolicyRiskString);
				
			}
			
		
			String dateOfEventString = formObject.getNGValue("DATE_OF_EVENT");
			
			//dateOfEventString = dateAndCauseOfEventResult.get(0).get(0);
			//formObject.setNGValue("Text41", dateAndCauseOfEventResult.get(0).get(1));
			
			datePopulation(dateOfEventString,dateOfReinstatementString,dateOfPolicyRiskString);	
	
		/*	
			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim"))
			{
				dateOfEventString = dateAndCauseOfEventResult.get(0).get(0);
				//Cause of Event
				formObject.setNGValue("Text41", dateAndCauseOfEventResult.get(0).get(5));
				log.WriteToLog("Date of Event String : "+dateOfEventString);
				datePopulation(dateOfEventString,dateOfReinstatementString,dateOfPolicyRiskString);	
			}
			
			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Accidental Death Claim"))
			{
				dateOfEventString = dateAndCauseOfEventResult.get(0).get(1);
				//Cause of Event
				formObject.setNGValue("Text41", dateAndCauseOfEventResult.get(0).get(6));
				log.WriteToLog("Date of Event String : "+dateOfEventString);
				datePopulation(dateOfEventString,dateOfReinstatementString,dateOfPolicyRiskString);				
			}
			
			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Death Claim Waiver of Premium"))
			{
				dateOfEventString = dateAndCauseOfEventResult.get(0).get(0);
				//Cause of Event
				formObject.setNGValue("Text41", dateAndCauseOfEventResult.get(0).get(5));
				log.WriteToLog("Date of Event String : "+dateOfEventString);
				datePopulation(dateOfEventString,dateOfReinstatementString,dateOfPolicyRiskString);					
			}
			
			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Critical Illness/Dreaded Disease Claim"))
			{
				dateOfEventString = dateAndCauseOfEventResult.get(0).get(2);
				//Cause of Event
				formObject.setNGValue("Text41", dateAndCauseOfEventResult.get(0).get(7));
				log.WriteToLog("Date of Event String : "+dateOfEventString);
				datePopulation(dateOfEventString,dateOfReinstatementString,dateOfPolicyRiskString);
			}	
			if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("Hospitalization Claim"))
			{
				dateOfEventString = dateAndCauseOfEventResult.get(0).get(3);
				//Cause of Event
				formObject.setNGValue("Text41", dateAndCauseOfEventResult.get(0).get(8));
				log.WriteToLog("Date of Event String : "+dateOfEventString);		
				datePopulation(dateOfEventString,dateOfReinstatementString,dateOfPolicyRiskString);
			}
		if(formObject.getNGValue("CLM_TYPE").equalsIgnoreCase("TPDB Claim"))
			{
				dateOfEventString = dateAndCauseOfEventResult.get(0).get(4);
				//Cause of Event
				formObject.setNGValue("Text41", dateAndCauseOfEventResult.get(0).get(9));
				log.WriteToLog("Date of Event String : "+dateOfEventString);
				datePopulation(dateOfEventString,dateOfReinstatementString,dateOfPolicyRiskString);		
			}
		*/
		
		//Product Name 
				String productNameQuery = "select STRPRODCD from STAGNEW2.Staging_claim.dbo.COM_POL_PROD_DTL where STRPOLNBR='"+policyNumber+"' and NPRODVER='1'";
				log.WriteToLog("Product Name Query : "+productNameQuery);
				
				List<List<String>> productNameResult = formObject.getDataFromDataSource(productNameQuery);
				log.WriteToLog("Product Name Result : "+productNameResult);
				if(!productNameResult.isEmpty())
				{
					String productName = productNameResult.get(0).get(0);
					log.WriteToLog("Product Name  : "+productName);
					formObject.setNGValue("Text26",productName);
				}
				
				
		//Name of the life insured
				String nameOfTheLifeInsuredQuery = "select concat (b.STRFIRSTNAME,' ',b.STRMIDDLENAME,' ',b.STRLASTNAME)  from STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK a ,STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME b  where a.STRCLIENTCD= b.STRCLIENTCD and (NCLIENTTYPE='2' or NCLIENTTYPE='3') and a.STRPOLNBR='"+policyNumber+"'";
				log.WriteToLog("Name of The Life Insured Query : "+nameOfTheLifeInsuredQuery);
				
				List<List<String>> nameOfTheLifeInsuredResult = formObject.getDataFromDataSource(nameOfTheLifeInsuredQuery);
				log.WriteToLog("Name of the Life Insured Result : "+nameOfTheLifeInsuredResult);
				if(!nameOfTheLifeInsuredResult.isEmpty())
				{
					String nameofTheLifeInsured = nameOfTheLifeInsuredResult.get(0).get(0);
					formObject.setNGValue("Text18", nameofTheLifeInsured);
					
				}
				
				
				
		//Name of the Proposer 
				String nameOfTheProposerQuery = "select concat (b.STRFIRSTNAME,' ',b.STRMIDDLENAME,' ',b.STRLASTNAME)  "
						+ " from STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK a ,STAGNEW2.Staging_claim.dbo.COM_CLIENT_NAME b  "
						+ " where a.STRCLIENTCD= b.STRCLIENTCD and (NCLIENTTYPE='1' or NCLIENTTYPE='3') "
						+ " and a.STRPOLNBR='"+policyNumber+"'";
				log.WriteToLog("Name of The Proposer Query : "+nameOfTheProposerQuery);
				List<List<String>> nameOfTheProposerResult = formObject.getDataFromDataSource(nameOfTheProposerQuery);
				log.WriteToLog("Name of the Proposer Result : "+nameOfTheProposerResult);
				if(!nameOfTheProposerResult.isEmpty())
				{
					String nameOfTheProposer = nameOfTheProposerResult.get(0).get(0);
					log.WriteToLog("Name of The Propser : "+nameOfTheProposer);
					formObject.setNGValue("Text27", nameOfTheProposer);
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
				if(!nomineeAssigneeResult.isEmpty())
				{
					for(List<String> i :nomineeAssigneeResult){
						log.WriteToLog("Nominee Assignee Grid Values "+i);
							formObject.addItem("ListView3", i);	
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
					formObject.setNGValue("Text17", sourcingBranch);	
				}
				
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
					formObject.setNGValue("Text25", unpaidPremiumifAnyResult.get(0).get(0));
				}
				else
				{
					formObject.setNGValue("Text25","0.000");
				}
				
				//Proposal Sign Date 
				String proposalSignDateQuery = "select format(a.DTPROPSIGNED,'yyyy/MM/dd') as ProposalSignDate from STAGNEW2.Staging_claim.dbo.NB_BASE_PROP_DTL a,STAGNEW2.Staging_claim.dbo.NB_PROP_POL_LNK b where b.STRPOLNBR='"+policyNumber+"' and a.STRPROPNBR=b.STRPROPNBR";
				log.WriteToLog("Proposal Sign Date Query : "+proposalSignDateQuery);
				List<List<String>> proposalSignDateResult = formObject.getDataFromDataSource(proposalSignDateQuery);
				log.WriteToLog("Proposal Sign Date Result : "+proposalSignDateResult);
				formObject.setNGValue("DatePicker2", proposalSignDateResult.get(0).get(0));

				if(!ClaimOnIdResult.isEmpty()){		
//Age
				String ageQuery = "select convert(INT,DATEDIFF (d,b.DTBIRTH, Convert(date,GETDATE(),108))/365.25) as Age from STAGNEW2.Staging_claim.dbo.com_client_m b where STRCLIENTCD='"+claimOnId+"'";
				log.WriteToLog("Age Query : ");
				List<List<String>> ageResult = formObject.getDataFromDataSource(ageQuery);
				log.WriteToLog("Age Result : "+ageResult);
				if(!ageResult.isEmpty())
				{
					String age = ageResult.get(0).get(0);
					log.WriteToLog("Age : "+age);
					formObject.setNGValue("Text14", age);
				}
				
//Occupation
				
				String OccupationQuery = "select b.STROCCUPNAME from STAGNEW2.Staging_claim.dbo.com_client_m a, "
						+ "ODSSTAGGING.Odsstaging.dbo.COM_OCCUPATION_M b where a.STROCCUPCD=b.STROCCUPCD and a.STRCLIENTCD ='"+claimOnId+"'";
				log.WriteToLog("OccupationQuery : "+OccupationQuery);
				List<List<String>> occupationResult = formObject.getDataFromDataSource(OccupationQuery);
				log.WriteToLog("occupationResult : "+occupationResult);
				if(!occupationResult.isEmpty())
				{
					formObject.setNGValue("Text21", occupationResult.get(0).get(0));
				}
				

//Annual Income
				String annualIncomeQuery = "select DTOTANNLINCM from STAGNEW2.Staging_claim.dbo.com_client_m where STRCLIENTCD='"+claimOnId+"'";
				log.WriteToLog("Annual Income Query : "+annualIncomeQuery);
				List<List<String>> annualIncomeResult = formObject.getDataFromDataSource(annualIncomeQuery);
				log.WriteToLog("annualIncomeResult : "+annualIncomeResult);
				if(!annualIncomeResult.isEmpty())
				{
					String annualIncome = annualIncomeResult.get(0).get(0);
					
					formObject.setNGValue("Text29", annualIncome);
				}
				
}		
//Last Paid Due Date
				String lastPaidDueDateQuery = "select isnull(convert(nvarchar(100),convert(date,DTLASTUNPAIDDUE)),'') from  STAGNEW2.Staging_claim.dbo.COM_POLICY_M where STRPOLNBR='"+policyNumber+"'";
				log.WriteToLog("Last Paid Due Date Query: "+lastPaidDueDateQuery);
				List<List<String>> lastPaidDueDateResult = formObject.getDataFromDataSource(lastPaidDueDateQuery);
				log.WriteToLog("Last Paid Due Date Result : "+lastPaidDueDateResult);
				if(!lastPaidDueDateResult.isEmpty())
				{
					String lastPaidDueDate = lastPaidDueDateResult.get(0).get(0);
					log.WriteToLog("Last Paid Due Date : "+lastPaidDueDate);
				}
								
				

				
//UW Decision
				String UWDecisionQuery = "select c.STRCDDESC from STAGNEW2.Staging_claim.dbo.NB_BASE_PROP_DTL a,"
						+ " STAGNEW2.Staging_claim.dbo.NB_PROP_POL_LNK b,STAGNEW2.Staging_claim.dbo.COM_PARAM_SYSTEM_M c "
						+ " where b.STRPOLNBR='4000037120' and a.STRPROPNBR=b.STRPROPNBR and a.NUWDECISIONCD=c.NPARAMCD "
						+ " and c.IPARAMTYPECD='2003'";
				log.WriteToLog("UWDecisionQuery : "+UWDecisionQuery);
				List<List<String>> UWDecisionResult = formObject.getDataFromDataSource(UWDecisionQuery);
				log.WriteToLog("UWDecisionResult : "+UWDecisionResult);
				if(!UWDecisionResult.isEmpty())
				{
					formObject.setNGValue("Text42", UWDecisionResult.get(0).get(0));
					
				}
				else
				{
					formObject.setNGValue("Text42", "N.A.");
				}
				
//				Refund Policies
				

				String refundPolicieGridQuery =  "select DTTRANSDATE,DREFUNDAMOUNT from STAGNEW2.Staging_claim.dbo.COM_REFUND_DTL where STRREFNBR='"+policyNumber+"'";
				log.WriteToLog("refundPolicieGridQuery : "+refundPolicieGridQuery);
				List<List<String>> refundPolicieGridResult =formObject.getDataFromDataSource(refundPolicieGridQuery);
				log.WriteToLog("refundPolicieGridResult : "+refundPolicieGridResult );
				if(!refundPolicieGridResult.isEmpty())
				{
					for(List<String> i :refundPolicieGridResult){
					log.WriteToLog("refundPolicieGridResult "+i);
					formObject.addItem("ListView7", i);	
					}
					
				}

			//Premium q_premium_grid
				
				String premiumReceivedDatesQuery = "SELECT dtdue,dtpaid  FROM  ODSSTAGGING.Odsstaging.dbo.com_pol_due WHERE strpolnbr = '"+policyNumber+"' and NDUETYPE='1' ";
				log.WriteToLog("premiumReceivedDatesQuery : "+premiumReceivedDatesQuery);
				List<List<String>> premiumReceivedDatesResult =formObject.getDataFromDataSource(premiumReceivedDatesQuery);

						if(!premiumReceivedDatesResult.isEmpty())
						{
							for(List<String> i :premiumReceivedDatesResult){
							log.WriteToLog("premiumReceivedDatesResult "+i);
							formObject.addItem("ListView5", i);	
							}
							
						}		
				

					
			//Alteration Date and Alteration Type Grid
					String alterationDateTypeQuery = "select c.STRCDDESC,isnull(convert(nvarchar(100),convert(date,pam.DTALTEFF)),'')  "
							+ " as DTALTEFF FROM  STAGNEW2.Staging_claim.dbo.PS_ALT_HDR pah,  STAGNEW2.Staging_claim.dbo.PS_ALT_MAP pam,"
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
							formObject.addItem("ListView6", i);	
							}
					}
									
				
		//Channel 
				String channelQuery = "select b.STRCHANNELNAME,a.STRSERVAGENTCD, a.DTOTSA,a.DTOTPRMAMNT,c.STRCDDESC,"
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
					formObject.setNGValue("Text13", channel);
		
					//Status of Agent
					formObject.setNGValue("Text11", channelResult.get(0).get(1));
					//Basic Sum Insured
					formObject.setNGValue("Text31",channelResult.get(0).get(2));		
					
					//Premium
					formObject.setNGValue("Text44", channelResult.get(0).get(3));
					
					//Mode 
					formObject.setNGValue("Text30",channelResult.get(0).get(4));
					//Commencement Date		
					formObject.setNGValue("DatePicker4", channelResult.get(0).get(5).replaceAll("-", "/"));	
				}		
						
		}
		log.WriteToLog("End of form Populated");
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
			
			//log.WriteToLog("Setting Date of Event in DatePicker5 ");
		//	formObject.setNGValue("DatePicker5", dateOfEventString);
			
			log.WriteToLog("Before Date Of Event Format");
			dateOfEvent = formatter.parse(dateOfEventString); 
			
			log.WriteToLog("After date of Event format : "+dateOfEvent);
			if(dateOfReinstatementString==null || dateOfReinstatementString.equalsIgnoreCase(""))
			{
				log.WriteToLog("dateOfReinstatementString is NULL");
				formObject.setNGValue("Text22", "N.A.");
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
				
				diffMiliSeconds = dateOfEvent.getTime() - dateOfReinstatement.getTime();
				log.WriteToLog("Difference in MiliSeconds is "+diffMiliSeconds);
				diffMonths = diffMiliSeconds / (24*60*60*1000)/30;
				log.WriteToLog("Difference in Months is : "+diffMonths);
				diffDays = diffMiliSeconds / (24 *60*60*1000) % 30;
//				log.WriteToLog("Difference in Days is : "+diffDays);
				log.WriteToLog("Difference in Months : "+diffMonths +" Diffrence in Days : "+diffDays);
				log.WriteToLog("Setting Text22 as Months and Days ");
				formObject.setNGValue("Text22", diffMonths + " Months " + diffDays +" Days ");
				
			}
			if(dateOfPolicyRiskString==null || dateOfPolicyRiskString.equalsIgnoreCase(""))
			{
				log.WriteToLog("dateOfPolicyRiskString is NULL");
				formObject.setNGValue("Text15", "N.A.");

			}
			else
			{
				log.WriteToLog("Before policy risk replace all :  "+dateOfPolicyRiskString);
				dateOfPolicyRiskString = dateOfPolicyRiskString.replaceAll("-", "/");
				log.WriteToLog("after date of policy risk replace all  : "+dateOfPolicyRiskString);
				
				dateOfPolicyRisk = formatter.parse(dateOfPolicyRiskString);
				log.WriteToLog("After date of policy risk format parse : "+dateOfPolicyRisk);
				
				diffMiliSeconds = dateOfEvent.getTime() - dateOfPolicyRisk.getTime();
				diffMonths = diffMiliSeconds / (24*60*60*1000)/30;
				diffDays = diffMiliSeconds / (24 *60*60*1000) % 30;
				log.WriteToLog("Difference in Months : "+diffMonths +" Diffrence in Days : "+diffDays);
				formObject.setNGValue("Text15", diffMonths + " Months " + diffDays +" Days ");
				
			}
			
		} catch (Exception e) {
			log.WriteToLog("Java Exception in Date Population "+e.toString());
		}
	}
	
	@Override
	public void saveFormCompleted(FormEvent arg0) throws ValidatorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveFormStarted(FormEvent arg0) throws ValidatorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitFormCompleted(FormEvent arg0) throws ValidatorException {
		// TODO Auto-generated method stub
		
		log.WriteToLog("Start of SubmitFormCompleted.");
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
		
		
		log.WriteToLog("End of SubmitFormCompleted.");
		
	}
	@Override
	public void submitFormStarted(FormEvent arg0) throws ValidatorException {
		// TODO Auto-generated method stub
		
		log.WriteToLog("Start of SubmitFormStarted.");
		FormReference formObject = FormContext.getCurrentInstance().getFormReference();
		String WI_NAME = formObject.getWFWorkitemName();
		
		//This code is to give a pop that document has been added to give a popup that document has been added.

//		String newDocIndicatorQuery = "SELECT NEW_DOC_INDICATOR FROM NG_CLM_Ext_Table with(NOLOCK) WHERE WI_NAME = '"+WI_NAME+"'"; 
//		
//		String newDocIndicator = formObject.getDataFromDataSource(newDocIndicatorQuery).get(0).get(0);
//		
//		
//		if(newDocIndicator.equalsIgnoreCase("Y"))
//		{
			String newDocIndicatorUpdateQuery = "update NG_CLM_Ext_Table set NEW_DOC_INDICATOR='N' WHERE WI_NAME = '"+WI_NAME+"'"; 
			formObject.saveDataIntoDataSource(newDocIndicatorUpdateQuery);		
			log.WriteToLog("New Doc Indicator Flag Update Query : "+newDocIndicatorUpdateQuery);
//			throw new ValidatorException(new FacesMessage("New Document has been fetched press ok to continue"));
//		
//		}
				
		log.WriteToLog("End of SubmitFormStarted.");
	}
}


