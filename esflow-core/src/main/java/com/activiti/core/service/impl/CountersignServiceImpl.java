package com.activiti.core.service.impl;

import java.util.List;

import com.activiti.core.common.service.BaseServiceImpl;
import com.activiti.core.dao.CountersignDao;
import com.activiti.core.service.CountersignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activiti.core.bean.Countersign;

@Service
@Transactional
public class CountersignServiceImpl extends BaseServiceImpl<Countersign> implements CountersignService {

	@Autowired
	private CountersignDao countersignDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Countersign> findCountersign(Countersign countersign) {

		return this.countersignDao.findCountersign(countersign);
	}

}
