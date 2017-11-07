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
		//���û������������м��ܴ���֮���ٽ��в�ѯ
		emp.setPassword(new MD5Code().getMD5ofStr(emp.getPassword()));
		try {
			if(this.adminService.login(emp)) {//���������
				//���û���������Ϣ���浽session���û�����Ϣ��������������š���Ƭ��������Ϣ��������Ȩ������Ϣ��Ȩ������Ϣ�ְ�����Ȩ����Ϣ��
				request.getSession().setAttribute("emp", emp);
				//����session���������ڣ���ÿ�ε�½���Բ�����ʱ��
				request.getSession().setMaxInactiveInterval(1800);
				//������ת·����Ϣ����ʾ��Ϣ
				super.setMsgAndUrl("login.success", "index.page", mav);
			}else {//ʧ������ת����½ҳ�������½
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
		//����session����
		request.getSession().invalidate();
		//������ת·������ʾ��Ϣ
		super.setMsgAndUrl("logout.success", "login.page", mav);
		//��ת��forward.jspҳ��url
		mav.setViewName(super.getMsg("forward.page"));
		return mav;
	}
}
