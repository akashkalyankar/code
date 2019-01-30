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
----------------------------------------------------------------------------------------------------------------------------
	Problem No.		Changed Date 		Changed By 			Description
					01/11/2018		Akash Kalynakar		same classes are called for same form attached to  multiple workstep 	
----------------------------------------------------------------------------------------------------------------------------
		
		This class file is used to call another respective classes for different forms according to worksteps 

---------------------------------------------------------------------------------------------------------------------------*/

package com.newgen.omniforms.user;

import com.newgen.omniforms.FormConfig;
import com.newgen.omniforms.context.FormContext;
import com.newgen.omniforms.listener.FormListener;



public class ClaimProcess implements IFormListenerFactory {
	NG_CLM_Logger log = new NG_CLM_Logger("In IForm Listener Factory ");
	@Override
	public FormListener getListener() {
		// TODO Auto-generated method stub
		
		System.out.println("In Claim Process Class");
		FormConfig formConfigObject = FormContext.getCurrentInstance().getFormConfig();
		if(formConfigObject.getConfigElement("ProcessName").equalsIgnoreCase("ClaimProcess"))
				{
				
						if(formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Assessment"))
						{
							return new NG_CLM_Assessment();
						}
						if(	formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Claim_Event_Intimation")
								||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Claim_Registration")
								||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Error_Bucket")
								||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Closed_Cases_Bucket")
								||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Requirement_Scanning"))
						{
							return new NG_CLM_ClaimEventIntimation();
						}
						if(formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Communication_pending")||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Final_Approver")||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Final_Decision_Bucket")||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Supervisor"))
						{
						//	log.WriteToLog("Returning to Communication Pending");
							return new NG_CLM_CommunicationPending();
						}
						if(formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Case_Initiation"))
						{
					//		log.WriteToLog("Calling NG_CLM_CaseInitiation Class.");
							return new NG_CLM_CaseInitiation();
						}
				

				}//End of Claim Process
		return null;
	}
}
