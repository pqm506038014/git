package com.peng.amr.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.peng.amr.adapter.AbstractActionAdapter;
import com.peng.amr.service.IAdminService;
import com.peng.amr.vo.Emp;
import com.peng.util.MD5Code;

@Controller
public class LoginAction extends AbstractActionAdapter{
	@Resource
	private IAdminService adminService;
	@RequestMapping("/login")
	public ModelAndView login(Emp emp,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		//将用户输入的密码进行加密处理之后再进行查询
		emp.setPassword(new MD5Code().getMD5ofStr(emp.getPassword()));
		try {
			if(this.adminService.login(emp)) {//如果有数据
				//将用户的所有信息保存到session，用户的信息包括了姓名、编号、照片、部门信息（保存了权限组信息，权限组信息又包括了权限信息）
				request.getSession().setAttribute("emp", emp);
				//设置session的生命周期，即每次登陆可以操作的时间
				request.getSession().setMaxInactiveInterval(1800);
				//设置跳转路径信息和提示信息
				super.setMsgAndUrl("login.success", "index.page", mav);
			}else {//失败则跳转到登陆页面继续登陆
				super.setMsgAndUrl("login.failure", "login.page", mav);
			}
			mav.setViewName(super.getMsg("forward.page"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(Emp emp,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		//销毁session对象
		request.getSession().invalidate();
		//设置跳转路径和提示信息
		super.setMsgAndUrl("logout.success", "login.page", mav);
		//跳转到forward.jsp页面url
		mav.setViewName(super.getMsg("forward.page"));
		return mav;
	}
}
