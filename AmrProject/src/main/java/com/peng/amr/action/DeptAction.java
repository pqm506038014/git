package com.peng.amr.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.peng.amr.adapter.AbstractActionAdapter;
import com.peng.amr.service.IDeptService;
import com.peng.amr.vo.Dept;
@Controller
@RequestMapping("/pages/dept/*")
public class DeptAction extends AbstractActionAdapter{
	@Resource
	private IDeptService deptService;
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(4, request)) {//权限验证，要有4号权限
			try {
				mav.addObject("allDepts",this.deptService.list(super.getEid(request)));
				//跳转到部门的列表页面(/pages/dept/dept_list.jsp)
				mav.setViewName(super.getMsg("dept.list.page"));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	
	@RequestMapping("edit")
	public void edit(Dept vo,HttpServletRequest request,HttpServletResponse response) {
		if(super.isAuth(7, request)) {
			try {
				//因为这是通过异步请求响应的，所有要调用父类中的print()方法进行响应
				super.print(response, this.deptService.edit(super.getEid(request), vo));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			super.print(response, false);
		}
	}
}
