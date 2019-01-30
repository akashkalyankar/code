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
	
----------------------------------------------------------------------------------------------------------------------------

	This class file is used to populated a picklist on the form for type of surgery

---------------------------------------------------------------------------------------------------------------------------*/

package com.newgen.omniforms.user;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import com.newgen.omniforms.FormReference;
import com.newgen.omniforms.component.PickList;
import com.newgen.omniforms.component.behavior.EventListenerImplementor;
import com.newgen.omniforms.context.FormContext;
import com.newgen.omniforms.util.Constant.EVENT;

public class NG_CLM_PickListListenerTypeOfSurgery extends EventListenerImplementor{
	NG_CLM_Logger log;
	public NG_CLM_PickListListenerTypeOfSurgery(String pickListId) {
		// TODO Auto-generated constructor stub
		super(pickListId);
	}
	public NG_CLM_PickListListenerTypeOfSurgery(String pickListId, EVENT compId) {
		// TODO Auto-generated constructor stub
		super(pickListId,compId);
	}
	@Override
	public void btnNext_Clicked(ActionEvent ae) {
		// TODO Auto-generated method stub
		super.btnNext_Clicked(ae);
	}

	@Override
	public void btnOk_Clicked(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		
		PickList pickListObject = FormContext.getCurrentInstance().getDefaultPickList();
		FormReference formObject = FormContext.getCurrentInstance().getFormReference();
		log = new NG_CLM_Logger(formObject.getWFWorkitemName());
		log.WriteToLog("Ok Button Clicked.");
		List<String> typeOfSurgery = pickListObject.getSelectedValue();
		
		List<List<String>> typeOfSurgeryGrid = formObject.getDataFromDataSource("select type_of_surgery from NG_CLM_Type_Of_Surgery_Grid WITH(NOLOCK)");
		log.WriteToLog("typeOfSurgeryGrid "+typeOfSurgeryGrid);
		
		formObject.addItem("q_new_type_of_surgery", typeOfSurgery);
		log.WriteToLog("Type of Surgery Selected Values : "+typeOfSurgery);
		
//		if(typeOfSurgeryGrid.get(0).get(0)==null){
//			log.WriteToLog("In IF Condition : " + typeOfSurgery);
//			formObject.addItem("q_new_type_of_surgery", typeOfSurgery);
//		}
//		else
//		{
//			for(List<String> i : typeOfSurgeryGrid)
//			{
//				log.WriteToLog("In form Loop : " + i);
//				if(i.get(0).equalsIgnoreCase(typeOfSurgery.get(0)))
//						{
//					log.WriteToLog("Checking Condtion i.get(0)"+i.get(0));
//							
//							throw new ValidatorException(new FacesMessage("The Selected type of surgery already added."));
//						}
//				else
//				{
//					log.WriteToLog("In else Condition : " + typeOfSurgery);
//					formObject.addItem("q_new_type_of_surgery", typeOfSurgery);
//				}
//			}
//			
//		}
	
		//pickListObject.populateData("Select STRCDDESC from elixir_dev2..ELIXIR.COM_PARAM_USER_M WHERE IPARAMTYPECD=1024 ");
		
		super.btnOk_Clicked(ae);
	}

	@Override
	public void btnPrev_Clicked(ActionEvent ae) {
		// TODO Auto-generated method stub
		super.btnPrev_Clicked(ae);
	}

	@Override
	public void btnSearch_Clicked(ActionEvent ae) {
		// TODO Auto-generated method stub
		PickList pickListObject = FormContext.getCurrentInstance().getDefaultPickList();
		String filtetValue = pickListObject.getSearchFilterValue();
		//Select DocumentCode,DescriptionDocument,Mandatory,StatusDocument,ReceivedDate,Remarks from testpicklist
		pickListObject.populateData("Select STRCDDESC from STAGNEW2.Staging_claim.dbo.COM_PARAM_USER_M WHERE IPARAMTYPECD=1024 and STRCDDESC like '%"+filtetValue+"%'");
	
		super.btnSearch_Clicked(ae);
	}

//	@Override
//	public void setEventID(EVENT eventID) {
//		// TODO Auto-generated method stub
//		if(eventID.VALUE_CHANGED != null)
//		{
//			log.WriteToLog("Value Changed Event Called in type of surgery picklist : ");
//			PickList pickListObject = FormContext.getCurrentInstance().getDefaultPickList();
//			String filtetValue = pickListObject.getSearchFilterValue();
//			//Select DocumentCode,DescriptionDocument,Mandatory,StatusDocument,ReceivedDate,Remarks from testpicklist
//			pickListObject.populateData("Select STRCDDESC from elixir_dev2..ELIXIR.COM_PARAM_USER_M WHERE IPARAMTYPECD=1024 and STRCDDESC like '%"+filtetValue+"%'");
//		}
//		super.setEventID(eventID);
//	}
	@Override
	public void setPickListID(String pickListID) {
		// TODO Auto-generated method stub
		super.setPickListID(pickListID);
	}

}
