package com.peng.amr.service;

import java.util.List;

import com.peng.amr.vo.Groups;

public interface IGroupsService {
	/**
	 * ���ݲ��ű�Ų�ѯȨ������Ϣ�����õ���IGroupsDAO��findAllByDept()����
	 * @param eid	�����˵ı��
	 * @param did	���ű��
	 * @return
	 * @throws Exception
	 */
	public List<Groups> findAllByDept(Integer eid,Integer did) throws Exception;
}
