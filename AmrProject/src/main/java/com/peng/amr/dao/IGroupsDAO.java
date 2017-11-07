package com.peng.amr.dao;

import java.util.List;

import com.peng.amr.vo.Groups;

public interface IGroupsDAO extends IDAO<Integer, Groups> {
	/**
	 * ���ݲ��ŵı�Ų�ѯȨ�������Ϣ
	 * @param did ���ŵı��
	 * @return
	 * @throws Exception
	 */
	public List<Groups> findAllByDept(Integer did) throws Exception;
}
