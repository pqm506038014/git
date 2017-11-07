package com.peng.amr.dao;

import java.util.List;

import com.peng.amr.vo.Action;

public interface IActionDAO extends IDAO<Integer, Action> {
	/**
	 * ����Ȩ�����Ų�ѯȨ����Ϣ
	 * @param gid Ȩ������
	 * @return
	 * @throws Exception
	 */
	public List<Action> findAllByGroups(Integer gid) throws Exception;
	/**
	 * ���ݲ��ź�Ȩ�ޱ�Ų�ѯָ����Ȩ������
	 * @param did ���ŵı��
	 * @param actid Ȩ�޵ı��
	 * @return
	 * @throws Exception
	 */
	public Action findByIdAndDept(Integer did,Integer actid) throws Exception;
}
