package com.peng.amr.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.peng.amr.adapter.AbstractActionAdapter;
import com.peng.amr.service.IAdminService;
import com.peng.amr.service.IEmpService;
import com.peng.amr.vo.Emp;
import com.peng.util.MD5Code;
import com.peng.util.SplitUtil;
@Controller
@RequestMapping("/pages/emp/*")
public class EmpAction extends AbstractActionAdapter{
	@Resource
	private IEmpService empService;
	@Resource
	private IAdminService adminService;
	@RequestMapping("addPre")
	public ModelAndView addPre(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(12, request)) {
			try {
				Map<String, Object> map = this.empService.addPre();
				mav.addObject("allLevels",map.get("allLevels"));
				mav.addObject("allDepts",map.get("allDepts"));
				mav.addObject("url", super.getMsg("emp.list.action"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			//跳转页面
			mav.setViewName(super.getMsg("emp.add.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	/**
	 * 增加雇员的方法
	 * @param vo
	 * @param pic
	 * @param requset
	 * @return
	 */
	@RequestMapping("add")
	public ModelAndView add(Emp vo,MultipartFile pic,HttpServletRequest requset) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(12, requset)) {//验证当前用户是否有权限
			vo.setPhoto(super.createSingleFileName(pic));//图片的名称需要保存到数据库
			vo.setHeid(super.getEid(requset));//设置操作人的编号
			vo.setPassword(new MD5Code().getMD5ofStr(vo.getPassword()));//密码的加密处理
			try {
				if(this.empService.add(vo, super.getEid(requset))) {//调用业务层的方法进行数据的增加
					super.saveUploadFile(pic, requset, vo.getPhoto());//添加成功则将照片保存到服务器
					super.setMsgAndUrl("vo.add.success", "emp.add.action", mav);//定义了提示信息和跳转路径
				}else {//定义提示信息和跳转路径
					super.setMsgAndUrl("vo.add.failure", "emp.add.action", mav);
				}
			}catch(Exception e) {
				super.setMsgAndUrl("vo.add.failure", "emp.add.action", mav);
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("forward.page"));
		}else {//没有权限则跳转到错误页面
			mav.setViewName(super.getMsg("errors.page"));
		}
		
		return mav;
	}
	/**
	 * 显示雇员的列表信息
	 * @param request
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(13, request)) {
			SplitUtil split = new SplitUtil(this);
			super.handleSplit(request, split);
			try {
				Map<String, Object> map = this.empService.list(super.getEid(request), split.getColumn(),
						split.getKeyword(), split.getCurrentPage(), split.getLineSize());
				map.put("url", super.getMsg("emp.list.action"));
				super.setPageMsg(map, mav);
			}catch(Exception e) {
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("emp.list.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	
	/**
	 * 实现从菜单跳转到编辑页面，并且将数据回显到编辑页面
	 * @param eid	被修改的雇员的编号
	 * @param request
	 * @return
	 */
	@RequestMapping("editPre")
	public ModelAndView editPre(Integer eid,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(15, request)) {
			try {
				Map<String, Object> map = this.empService.editPre(eid);
				mav.addObject("allLevels",map.get("allLevels"));
				mav.addObject("allDepts",map.get("allDepts"));
				mav.addObject("editEmp",map.get("emp"));
			}catch(Exception e) {
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("emp.edit.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	/**
	 * 实现雇员的编辑
	 * @param vo
	 * @param pic
	 * @param request
	 * @return
	 */
	@RequestMapping("edit")
	public ModelAndView edit(Emp vo,MultipartFile pic,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(15, request)) {//验证权限
			if(pic != null&&pic.getSize() > 0) {//创建一个新的图片的名字
				vo.setPhoto(super.createSingleFileName(pic));
			}
			vo.setHeid(super.getEid(request));
			if(vo.getPassword() == null||"".equals(vo.getPassword())) {
				vo.setPassword(null);
			}else {
				vo.setPassword(new MD5Code().getMD5ofStr(vo.getPassword()));
			}
			try {
				if(this.empService.edit(vo, super.getEid(request))) {
					super.saveUploadFile(pic, request, vo.getPhoto());
					super.setMsgAndUrl("vo.edit.success", "emp.list.action", mav);
				}else {
					super.setMsgAndUrl("vo.edit.failure", "emp.list.action", mav);
				}
			}catch(Exception e) {
				super.setMsgAndUrl("vo.edit.failure", "emp.list.action", mav);
			}
			mav.setViewName(super.getMsg("forward.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	@Override
	public String getSaveFileDiv() {
		return "/upload/emp/";
	}
	@Override
	public Object getFlag() {
		return "雇员";
	}
	@Override
	public String getDefaultColumn() {
		return "name";
	}
	@Override
	public String getColumnData() {
		return "雇员姓名:name|雇员编号:eid|雇员电话:phone";
	}
}
