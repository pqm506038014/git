package com.peng.amr.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.peng.amr.dao.ILevelDAO;
import com.peng.amr.service.ILevelService;
import com.peng.amr.service.abs.AbstractService;
import com.peng.amr.vo.Level;
@Service
public class LevelServiceImpl extends AbstractService implements ILevelService {
	@Resource
	private ILevelDAO levelDAO;
	@Override
	public boolean checkSalary(double salary, int lid) throws Exception {
		//根据雇员级别查询出对应的最高工资和最低工资信息
		Level level = this.levelDAO.findById(lid);
		if(salary >= level.getLosal() && salary <= level.getHisal()) {
			return true;
		}
		return false;
	}

}
