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
					14/12/2018		Akash Kalynakar 	With Document search based on description
----------------------------------------------------------------------------------------------------------------------------
	
	This class file is used to populated a picklist on the form for document type code and description

---------------------------------------------------------------------------------------------------------------------------*/

package com.newgen.omniforms.user;

import javax.activation.FileTypeMap;
import javax.faces.event.ActionEvent;

import com.newgen.omniforms.FormConfig;
import com.newgen.omniforms.FormReference;
import com.newgen.omniforms.component.ComboBox;
import com.newgen.omniforms.component.ListBox;
import com.newgen.omniforms.component.PickList;
import com.newgen.omniforms.component.behavior.EventListenerImplementor;
import com.newgen.omniforms.context.FormContext;
import com.newgen.omniforms.util.Constant.EVENT;

public class NG_CLM_PickListListener extends EventListenerImplementor{
	
	public NG_CLM_PickListListener(String pickListId) {
		// TODO Auto-generated constructor stub
		super(pickListId);
	}
	public NG_CLM_PickListListener(String pickListId, EVENT compId) {
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
		FormConfig formConfigObject = FormContext.getCurrentInstance().getFormConfig();
		
		if(formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Claim_Event_Intimation")
				||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Claim_Registration")
				||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Error_Bucket")
				||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Closed_Cases_Bucket")
				||formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Requirement_Scanning"))
			{
				formObject.setNGValue("Text70",pickListObject.getSelectedValue().get(0));
				formObject.setNGValue("Text104",pickListObject.getSelectedValue().get(1));
				formObject.setNGValue("Text49", pickListObject.getSelectedValue().get(2));
			}	
		
		if(formConfigObject.getConfigElement("ActivityName").equalsIgnoreCase("Assessment"))
		{
			formObject.setNGValue("Text135",pickListObject.getSelectedValue().get(0));
			formObject.setNGValue("Text227",pickListObject.getSelectedValue().get(1));
			formObject.setNGValue("Text155", pickListObject.getSelectedValue().get(2));
		}
		
		super.btnOk_Clicked(ae);
	}

	@Override
	public void btnPrev_Clicked(ActionEvent ae) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void btnSearch_Clicked(ActionEvent ae) {
		// TODO Auto-generated method stub
		PickList pickListObject = FormContext.getCurrentInstance().getDefaultPickList();
		String filtetValue = pickListObject.getSearchFilterValue();
		//Select DocumentCode,DescriptionDocument,Mandatory,StatusDocument,ReceivedDate,Remarks from testpicklist
		
		pickListObject.populateData("select distinct documentcode, documentdescription,mandatory  from ng_clm_claimintimation_picklist WITH(NOLOCK) where documentdescription  like '%"+filtetValue.toLowerCase()+"%'");
		
	
	};
	
	
}
