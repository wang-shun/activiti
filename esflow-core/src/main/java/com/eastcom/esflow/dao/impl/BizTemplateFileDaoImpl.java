package com.eastcom.esflow.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.eastcom.esflow.bean.BizTemplateFile;
import com.eastcom.esflow.common.dao.BaseDaoImpl;
import com.eastcom.esflow.common.utils.PageHelper;
import com.eastcom.esflow.dao.BizTemplateFileDao;

@Repository
public class BizTemplateFileDaoImpl extends BaseDaoImpl<BizTemplateFile> implements BizTemplateFileDao{

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
