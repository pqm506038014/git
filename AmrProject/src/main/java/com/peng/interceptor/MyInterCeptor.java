package com.peng.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.peng.util.MessageUtil;
import com.peng.util.MimeValidator;
import com.peng.util.ValidatorRules;

public class MyInterCeptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.ǿ������ת��
		HandlerMethod hand=(HandlerMethod) handler;
		//2.ȡ����Դ��Ϣ��keyֵ
		String validatorKey=hand.getBeanType().getSimpleName()+"."+hand.getMethod().getName()+".rules";
		//3.ȡ����֤��Ϣ��valueֵ
		String validateContent=MessageUtil.getMessage(hand.getBean(), validatorKey);
		if(validateContent == null||"".equals(validateContent)) {//���û��ֱ�ӷ���
			return true;
		}else {
			String rules[] = validateContent.split("\\|");
			Map<String, String> results=new ValidatorRules(hand.getBean(),rules,request).validate();
			if(results.size()>0) {//�д�������ת���������
				request.setAttribute("errors", results);
				request.getRequestDispatcher("/errors.jsp").forward(request, response);
			}else {//���û�д�������֤�ϴ���Ϣ
				boolean flag = MimeValidator.isMime(hand.getBean(), request);
				if(flag == false) {
					results.put("file", MessageUtil.getMessage(hand.getBean(), "invalidate.file.mime.error.msg"));
					request.setAttribute("errors", results);
					request.getRequestDispatcher("/errors.jsp").forward(request, response);
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
