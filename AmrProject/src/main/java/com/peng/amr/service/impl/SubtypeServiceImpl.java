package com.peng.amr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.peng.amr.dao.ISubtypeDAO;
import com.peng.amr.service.ISubtypeService;
import com.peng.amr.service.abs.AbstractService;
import com.peng.amr.vo.Subtype;
@Service
public class SubtypeServiceImpl extends AbstractService implements ISubtypeService {
	@Resource
	private ISubtypeDAO subtypeDAO;
	@Override
	public List<Subtype> list(Integer tid) throws Exception {
		return this.subtypeDAO.findAllByType(tid);
	}

}
