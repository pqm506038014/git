package com.peng.amr.dao;

import java.util.List;

import com.peng.amr.vo.Dept;

public interface IDeptDAO extends IDAO<Integer, Dept> {
	/**
	 * ���ݲ��ŵ������ǽ��в�ѯ
	 * @param sflag	�����ǣ���������Ϊ0����ʾ��ͨ����
	 * @return
	 * @throws Exception
	 */
	public List<Dept> findAllBySflag(Integer sflag) throws Exception;
}
