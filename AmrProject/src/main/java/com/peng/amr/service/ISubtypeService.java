package com.peng.amr.service;

import java.util.List;

import com.peng.amr.vo.Subtype;

public interface ISubtypeService {
	/**
	 * ��Ʒ�ķ����б���������б�
	 * @param tid	�������
	 * @return
	 * @throws Exception
	 */
	public List<Subtype> list(Integer tid) throws Exception;
}
