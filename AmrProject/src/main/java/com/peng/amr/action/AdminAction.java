package com.peng.amr.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.peng.amr.adapter.AbstractActionAdapter;
import com.peng.amr.service.IAdminService;
import com.peng.amr.vo.Emp;
import com.peng.util.MD5Code;
import com.peng.util.SplitUtil;
@Controller
@RequestMapping("/pages/admin/*")
public class AdminAction extends AbstractActionAdapter{
	@Resource
	private IAdminService adminService;
	/**
	 * 当增加管理员之前需要访问该方法，主要的目的是将雇员的级别查询之后保存到request属性范围之后再跳转到编辑页面获取
	 * @param request
	 * @return
	 */
	@RequestMapping("addPre")
	public ModelAndView addPre(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(1, request)) {//进行权限验证，要保证当前的用户有该权限才可以操作（也就是要保证当前操作的用户有1号权限）
			try {
				Map<String, Object> map = this.adminService.addPre();
				//保存的是雇员的级别信息
				mav.addObject("allLevels",map.get("allLevels"));
				mav.addObject("url",super.getMsg("admin.list.action"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			//跳转到/pages/admin/admin_add.jsp
			mav.setViewName(super.getMsg("admin.add.page"));
		}else {
			//失败则跳转到/errors.jsp
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	@RequestMapping("/checkEid")
	public void checkEid(int eid,HttpServletResponse response) {
		try {
			super.print(response, this.adminService.checkEid(eid));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/add")
	public ModelAndView add(Emp vo,MultipartFile pic,HttpServletRequest requset) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(2, requset)) {//验证当前用户是否有权限
			vo.setPhoto(super.createSingleFileName(pic));//图片的名称需要保存到数据库
			vo.setHeid(super.getEid(requset));//设置操作人的编号
			vo.setPassword(new MD5Code().getMD5ofStr(vo.getPassword()));//密码的加密处理
			try {
				if(this.adminService.add(vo, super.getEid(requset))) {//调用业务层的方法进行数据的增加
					super.saveUploadFile(pic, requset, vo.getPhoto());//添加成功则将照片保存到服务器
					super.setMsgAndUrl("vo.add.success", "admin.add.action", mav);//定义了提示信息和跳转路径
				}else {//定义提示信息和跳转路径
					super.setMsgAndUrl("vo.add.failure", "admin.add.action", mav);
				}
			}catch(Exception e) {
				super.setMsgAndUrl("vo.add.failure", "admin.add.action", mav);
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("forward.page"));
		}else {//没有权限则跳转到错误页面
			mav.setViewName(super.getMsg("errors.page"));
		}
		
		return mav;
	}
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(3, request)) {//权限的验证
			//创建一个分页参数处理的工具类对象，this表示当前的对象，传递这个的目的是取得子类中给出的一些信息
			SplitUtil split = new SplitUtil(this);
			//调用父类中的方法进行客户端传递的参数的处理，request负责在父类中取得参数，split在父类中处理参数
			super.handleSplit(request, split);
			try {
				Map<String, Object> map = this.adminService.list(super.getEid(request), split.getColumn(),
						split.getKeyword(),split.getCurrentPage(), split.getLineSize());
				//保存的是查看管理员的路径，需要在分页的时候用到，比如说上一页
				map.put("url", super.getMsg("admin.list.action"));
				super.setPageMsg(map, mav);
			}catch(Exception e) {
				e.printStackTrace();
			}
			//跳转到管理员的列表页面（/pages/admin/admin_list.jsp）
			mav.setViewName(super.getMsg("admin.list.page"));
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
		return "管理员";
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
