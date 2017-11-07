package com.peng.amr.dao;

import java.util.List;

import com.peng.amr.vo.Subtype;

public interface ISubtypeDAO extends IDAO<String, Subtype> {
	/**
	 * ʵ�ָ��ݸ�����Ų�ѯ�������Ϣ
	 * @param tid	�������
	 * @return
	 * @throws Exception
	 */
	public List<Subtype> findAllByType(Integer tid) throws Exception;
}
