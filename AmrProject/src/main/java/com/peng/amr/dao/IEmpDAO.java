package com.peng.amr.dao;

import java.util.List;
import java.util.Map;

import com.peng.amr.vo.Emp;

public interface IEmpDAO extends IDAO<Integer, Emp> {
	/**
	 * 登陆之后要求取出用户的真实姓名以及用户的照片
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Emp findLogin(Emp vo) throws Exception;
	/**
	 * 实现模糊分页查询管理员
	 * @param paramMap	保存了查询条件的map集合
	 * @return
	 * @throws Exception
	 */
	public List<Emp> findAllAdmin(Map<String, Object> paramMap) throws Exception;
	/**
	 * 统计查询到的管理员的数据量
	 * @param paramMap	保存了查询条件的map集合
	 * @return
	 * @throws Exception
	 */
	public Integer getAllAdminCount(Map<String, Object> paramMap) throws Exception;
	/**
	 * 实现模糊分页查询雇员
	 * @param paramMap	保存了查询条件的map集合
	 * @return
	 * @throws Exception
	 */
	public List<Emp> findAllEmp(Map<String, Object> paramMap) throws Exception;
	/**
	 * 统计查询到的雇员的数据量
	 * @param paramMap	保存了查询条件的map集合
	 * @return
	 * @throws Exception
	 */
	public Integer getAllEmpCount(Map<String, Object> paramMap) throws Exception;
}
