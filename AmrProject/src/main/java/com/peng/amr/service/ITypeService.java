package com.peng.amr.service;

import java.util.List;

import com.peng.amr.vo.Type;

public interface ITypeService {
	/**
	 * ʵ������ȫ����ѯ
	 * @return	���ļ���
	 * @throws Exception
	 */
	public List<Type> list() throws Exception;
}
