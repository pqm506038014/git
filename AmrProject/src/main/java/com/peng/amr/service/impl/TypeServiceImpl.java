package com.peng.amr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.peng.amr.dao.ITypeDAO;
import com.peng.amr.service.ITypeService;
import com.peng.amr.service.abs.AbstractService;
import com.peng.amr.vo.Type;
@Service
public class TypeServiceImpl extends AbstractService implements ITypeService{
	@Resource
	private ITypeDAO typeDAO;
	@Override
	public List<Type> list() throws Exception {
		return this.typeDAO.findAll();
	}

}
