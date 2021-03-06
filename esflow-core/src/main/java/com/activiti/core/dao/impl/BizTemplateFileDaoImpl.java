package com.activiti.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.activiti.core.dao.BizTemplateFileDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.activiti.core.bean.BizTemplateFile;
import com.activiti.core.common.dao.BaseDaoImpl;
import com.activiti.core.common.utils.PageHelper;

@Repository
public class BizTemplateFileDaoImpl extends BaseDaoImpl<BizTemplateFile> implements BizTemplateFileDao {

	@Override
	public PageHelper<BizTemplateFile> findTemplateFlies(PageHelper<BizTemplateFile> page,BizTemplateFile file,boolean islike){
		
		List<Object> list = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(" from BizTemplateFile file where 1=1 ");
		if(StringUtils.isNotBlank(file.getCreateUser())){
			hql.append(" and file.createUser like ? ");
			list.add("%" + file.getCreateUser()+"%");
		}
		if(StringUtils.isNotBlank(file.getFileName())){
			if(islike){
				hql.append(" and file.fileName like ? ");
				list.add("%" + file.getFileName()+"%");
			}else{
				hql.append(" and file.fileName = ? ");
				list.add(file.getFileName());
			}
		}
		if(StringUtils.isNotBlank(file.getFlowName())){
			hql.append( " and file.flowName = ? ");
			list.add(file.getFlowName());
		}
		hql.append(" order by file.createTime desc");
		return this.find(page,hql.toString(),list.toArray());
	}
}
