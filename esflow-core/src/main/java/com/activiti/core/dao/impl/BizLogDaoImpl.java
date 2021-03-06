package com.activiti.core.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.activiti.core.bean.BizLog;
import com.activiti.core.common.dao.BaseDaoImpl;
import com.activiti.core.dao.IBizLogDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class BizLogDaoImpl extends BaseDaoImpl<BizLog> implements IBizLogDao {

	@Override
	public List<BizLog> loadLogByBizId(String bizId)  {
		return this.find("FROM BizLog bean WHERE bean.bizInfo.id=? ORDER BY bean.createTime ASC", new Object[] { bizId });
	}

	@Override
	public List<Map<String,Object>> findBizInfoIds(String handleUser){
		
		StringBuilder hql = new StringBuilder("select distinct new map( bean.bizInfo.id as bizId ) FROM BizLog bean WHERE bean.handleUser =? ");
		return this.find(hql.toString(),new Object[]{handleUser}); 
	}
	
	@Override
	public List<Map<String,String>> findBizInfoIds(){
		
		StringBuilder hql = new StringBuilder("select distinct new map( bean.bizInfo.id as bizId ) FROM BizLog bean WHERE ");
		hql.append(" (bean.handleName = ? and bean.handleResult = ? ) or bean.handleName = ? ");
		return this.find(hql.toString(),new Object[]{"服务台处理","解决","厂商处理"}); 
	}
	
	@Override
	public String findCompleteBizlogs(String bizId,String handleResult){
		
		StringBuilder hql = new StringBuilder("select new map(bean.taskID as taskId) FROM BizLog bean WHERE bean.handleResult =? ");
		hql.append(" and bean.bizInfo.id = ? and bean.createTime = (select max(createTime) from BizLog b  ");
		hql.append(" where b.handleResult =? and b.bizInfo.id = ?)");
		List<Map<String,String>> list = this.find(hql.toString(),new Object[]{handleResult,bizId,handleResult,bizId}); 
		if(list == null || list.isEmpty())
			return null;
		Map<String,String> map = list.get(0);
		return map.get("taskId");
	}
	
	@Override
	public List<BizLog> findBizLogs(BizLog bizLog){
		
		
		List<Object> list = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder("FROM BizLog bean WHERE 1=1 ");
		if(StringUtils.isNotBlank(bizLog.getHandleResult())){
			hql.append(" and bean.handleResult =? ");
			list.add(bizLog.getHandleResult());
		}
		if(bizLog.getBizInfo()!= null && StringUtils.isNotBlank(bizLog.getBizInfo().getId())){
			hql.append(" and bean.bizInfo.id = ? ");
			list.add(bizLog.getBizInfo().getId());
		}
		if(StringUtils.isNotBlank(bizLog.getTaskName())){
			hql.append(" and bean.taskName =? ");
			list.add(bizLog.getTaskName());
		}
		hql.append(" order by bean.createTime asc ");
		return this.find(hql.toString(),list.toArray()); 
	}
}
