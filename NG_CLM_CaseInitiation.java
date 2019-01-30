/*--------------------------------------------------------------------------------------------------------------------------
	Name 				:	Akash Kalyankar,Ankita Panse
	Project Name		:	IDBI FEDERAL LIFE INSURANCE CLAIM PROCESS
	Description			:	Assessment Workstep Validation
	Date of Creation	: 	11/10/2018
	Author 				:
	Group Leader 		: 	Mr.Abhijeet Kumbhar
	Project Manager 	:	Mr.Kishor Marathe
									CHANGE HISTORY
				This Code is Without Update Client Changes	
----------------------------------------------------------------------------------------------------------------------------
	Problem No.		Changed Date 	Changed By 			Description
					21/11/2018		Akash Kalynakar		this file is added on date for some validations in submitting cases
	
	This Code is Without Update Client Changes				
----------------------------------------------------------------------------------------------------------------------------

	Functions with there usages


---------------------------------------------------------------------------------------------------------------------------*/

package com.newgen.omniforms.user;

import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;

import com.newgen.omniforms.FormConfig;
import com.newgen.omniforms.FormReference;
import com.newgen.omniforms.context.FormContext;
import com.newgen.omniforms.event.ComponentEvent;
import com.newgen.omniforms.event.FormEvent;
import com.newgen.omniforms.listener.FormListener;

public class NG_CLM_CaseInitiation implements FormListener {
	NG_CLM_Logger log ;

	FormConfig formConfigObject = FormContext.getCurrentInstance().getFormConfig();
	
	 
	@Override
	public void continueExecution(String arg0, HashMap<String, String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventDispatched(ComponentEvent arg0) throws ValidatorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void formLoaded(FormEvent arg0) {
		// TODO Auto-generated method stub
		FormReference formObject = FormContext.getCurrentInstance().getFormReference();
		log = new NG_CLM_Logger(formObject.getWFProcessName());
		log.WriteToLog("Case Intiation Form Loaded");
	}

	@Override
	public void formPopulated(FormEvent arg0) {
		// TODO Auto-generated method stub
		log.WriteToLog("Case Intiation Form Populated");
	}

	@Override
	public void saveFormCompleted(FormEvent arg0) throws ValidatorException {
		// TODO Auto-generated method stub
		log.WriteToLog("Case Intiation Form Populated");
	}

	@Override
	public void saveFormStarted(FormEvent arg0) throws ValidatorException {
		// TODO Auto-generated method stub
		log.WriteToLog("Case Intiation Save Form Started");
	}

	@Override
	public void submitFormCompleted(FormEvent arg0) throws ValidatorException {
		// TODO Auto-generated method stub
		log.WriteToLog("Case Initiation SubmitFormCompleted");
	}

	@Override
	public void submitFormStarted(FormEvent arg0) throws ValidatorException {
		// TODO Auto-generated method stub
		
		// if 0 throw validator exception
		
		FormReference formObject = FormContext.getCurrentInstance().getFormReference();
	log.WriteToLog("Start of Submit Form Started.");
	
	String policyNumber = formObject.getNGValue("Policy_No");
	log.WriteToLog("Policy Number "+policyNumber);	
	String validation=" select count(STRPOLNBR) from STAGNEW2.Staging_claim.dbo.COM_POLICY_M where STRPOLNBR='"+policyNumber+"'";
	log.WriteToLog("Validation of Policy Number Query : "+validation);
	List<List<String>>validationResult = formObject.getDataFromDataSource(validation);
	log.WriteToLog("First Validation Result : "+validationResult);
	int count = Integer.parseInt(validationResult.get(0).get(0));
	log.WriteToLog("First Validation Count : "+count);
	if(count==0)
	{
		//First Message
		throw new ValidatorException(new FacesMessage("Entered policy number is not valid."));
	}
	else
	{
		validation= "select count(NPOLSTATCD)from STAGNEW2.Staging_claim.dbo.COM_POLICY_M "
				+ " where NPOLSTATCD in('6','19') and STRPOLNBR='"+policyNumber+"'";
		log.WriteToLog("Second Validation Query : "+validation);
		validationResult = formObject.getDataFromDataSource(validation);
		log.WriteToLog("Second Validation Result : "+validationResult);
		count = Integer.parseInt(validationResult.get(0).get(0));
		log.WriteToLog("Second Validation Count : "+count);
		if(count>0)
		{
			//Second Message
			throw new ValidatorException(new FacesMessage("Case cannot be created as entered policy status is ‘Death’ or 'Death Registered."));
		}
		else
		{	validation= "select count(POLICY_NO) from NG_CLM_Ext_Table "
				+ " where CLM_TYPE in ('Death Claim','Accidental Death Claim') "
				+ " and WS_CURRENT<>'Work Exit' and Policy_No='"+policyNumber+"'";
			log.WriteToLog("Third Validation Query : "+validation);
			validationResult = formObject.getDataFromDataSource(validation);
			log.WriteToLog("Third Validation Result : "+validationResult);
			count = Integer.parseInt(validationResult.get(0).get(0));
			log.WriteToLog("Third Validation Count : "+count);
			if(count>0)
			{
				//Third Message
				throw new ValidatorException(new FacesMessage("Open death case already exists for entered policy number."));
			}
			else
			{
				validation= "Select count(POLICY_NO) from NG_CLM_Ext_table a,"
						+ " STAGNEW2.Staging_claim.dbo.COM_POL_CLIENT_LNK b "
						+ " where a.POLICY_NO=b.STRPOLNBR and a.CLM_TYPE ='Death Claim Waiver of Premium' "
						+ " and a.WS_CURRENT<>'Work Exit' and b.NCLIENTTYPE='3' and POLICY_NO='"+policyNumber+"'";
				log.WriteToLog("Fourth Validation Query : "+validation);
				validationResult = formObject.getDataFromDataSource(validation);
				log.WriteToLog("Fourth Validation Result : "+validationResult);
				count = Integer.parseInt(validationResult.get(0).get(0));
				log.WriteToLog("Fourth Validation Count : "+count);
				if(count>0)
				{
					throw new ValidatorException(new FacesMessage("Open death case already exists for entered policy number."));
				}
			}
		}	
			
	}


	log.WriteToLog("End of Submit Form Started.");
	}

}
