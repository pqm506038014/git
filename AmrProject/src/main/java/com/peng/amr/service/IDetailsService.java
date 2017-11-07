package com.peng.amr.service;

import java.util.List;
import java.util.Map;

import com.peng.amr.vo.Details;

public interface IDetailsService {
	/**
	 * 增加清单前的准备操作
	 * @param eid	雇员的编号，用于权限验证
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> addPre(Integer eid) throws Exception;
	/**
	 * 进行数据的插入操作
	 * @param vo	保存具体信息的vo类
	 * @param eid	雇员的编号，用于权限验证
	 * @return
	 * @throws Exception
	 */
	public boolean add(Details vo,Integer eid) throws Exception;
	/**
	 * 实现待购清单的查询，调用的是数据层的findAllDetials()方法
	 * @param eid	当前用户的编号
	 * @return
	 * @throws Exception
	 */
	public List<Details> listDetails(Integer eid) throws Exception;
	/**
	 * 实现根据编号进行批量删除，调用了数据层的findByPhoto()方法和doRemoveBatch()方法
	 * @param eid	操作人的编号，进行权限验证
	 * @param ids	要删除的清单编号的集合
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> rm(Integer eid,List<Integer> ids) throws Exception;
	/**
	 * 实现数量的修改，调用的是数据层的doUpdateAmount()和findByPhoto()方法
	 * @param eid	操作人的编号，权限验证
	 * @param dinfo	保存的是编号和数量：key是编号，value是数量
	 * @param ids	保存了要删除的清单的编号的集合
	 * @return	返回的数据 <br>
	 * <li>被删除的清单的信息，目的是返回控制层中进行对应的照片删除</li>
	 * <li>最终修改的标记，true或者false</li>
	 * @throws Exception
	 */
	public Map<String, Object> editAmount(Integer eid,Map<Integer, Integer> dinfo,List<Integer> ids) throws Exception;
	/**
	 *  实现修改前的数据回显，需要回显一级类别信息，二级类别信息，基本信息
	 * @param did	清单编号
	 * @param eid	操作人的编号
	 * @return	返回的数据是一个map集合，集合中的数据有：<br>
	 * <li>key=allTypes,value=ITypeDAO.findAll()</li>
	 * <li>key=allSubtypes,value=ISubtypeDAO.findByType()</li>
	 * <li>key=details,value=IDetailsDAO.findByIdForPrebuy()</li>
	 * @throws Exception
	 */
	public Map<String, Object> editPre(Integer did,Integer eid) throws Exception;
	/**
	 * 实现清单的编辑，调用的是数据层的doUpdatePrebuy()
	 * @param eid	操作人的编号
	 * @param vo	包含了修改信息的vo对象
	 * @return
	 * @throws Exception
	 */
	public boolean edit(Integer eid,Details vo) throws Exception;
}
