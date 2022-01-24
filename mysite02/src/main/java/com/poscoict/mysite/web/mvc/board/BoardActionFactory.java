package com.poscoict.mysite.web.mvc.board;

import com.poscoict.mysite.mvc.guestbook.AddAction;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("writeform".equals(actionName)) {
			action = new WriteFormAction();
		} else if("delete".equals(actionName)) {
			action = new DeleteAction();
		} else if("write".equals(actionName)) {
			action = new WriteAction();
		} else if("modify".equals(actionName)) {
			action = new ModifyAction();
		} else if("modifyform".equals(actionName)) {
			action = new ModifyFormAction();
		} else if("replyform".equals(actionName)) {
			action = new ReplyFormAction();
		} else if("reply".equals(actionName)) {
			action = new ReplyAction();
		} else if("view".equals(actionName)) {
			action = new ViewAction();
		} else {
			action = new ListAction();
		}
		
		return action;
	}

}
