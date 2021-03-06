package com.activiti.core.listener;

import java.util.ArrayList;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.activiti.core.bean.Countersign;
import com.activiti.core.service.CountersignService;
import com.activiti.core.service.impl.CountersignServiceImpl;
import com.activiti.core.util.Constants;
import com.activiti.core.util.context.ContextFactory;


/**
 * 会签
 * Title: esflow <br>
 * Description: <br>
 * Copyright: eastcom Copyright (C) 2009 <br>
 * @author <a href="mailto:lukw@eastcom-sw.com">lukw</a><br>
 * @email:lukw@eastcom-sw.com	<br>
 * @version 1.0 <br>
 * @creatdate 2016年4月23日 上午11:44:42 <br>
 *
 */
@Component("completeCountersignTaskListener")
public class CompleteCountersignTaskListener implements TaskListener {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		
		CountersignService countersignService = (CountersignServiceImpl) ContextFactory.getBeanByType(CountersignServiceImpl.class);
		String handleResult = (String)delegateTask.getVariable("handleResult");
		
		String key  = (String)delegateTask.getProcessDefinitionId()+":"+(String)delegateTask.getProcessInstanceId();
		Integer nrOfInstances = (Integer) delegateTask.getVariable("nrOfInstances");
		Integer nrOfCompletedInstances = (Integer) delegateTask.getVariable("nrOfCompletedInstances");
		ArrayList<String> members = CountersignTaskListener.membersCache.get(key);
		if(nrOfInstances != null && nrOfCompletedInstances != null){
			delegateTask.setVariable("members", members);
			if(!(nrOfCompletedInstances < nrOfInstances-1))
				CountersignTaskListener.membersCache.remove(key);
		}
		
		if(StringUtils.isNotBlank(handleResult)){
			
			Countersign countersign = new Countersign();
			countersign.setProcessDefinitionId(delegateTask.getProcessDefinitionId());
			countersign.setProcessInstanceId(delegateTask.getProcessInstanceId());
			countersign.setTaskAssignee(delegateTask.getAssignee());
			countersign.setTaskId(delegateTask.getId());
			if("同意".equals(handleResult))
				countersign.setResultType(Constants.MEET_YES);
			else
				countersign.setResultType(Constants.MEET_NO);
			countersignService.save(countersign);
		}
	}
}
