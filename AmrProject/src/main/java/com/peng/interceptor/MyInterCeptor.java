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
		//1.强制类型转换
		HandlerMethod hand=(HandlerMethod) handler;
		//2.取得资源信息的key值
		String validatorKey=hand.getBeanType().getSimpleName()+"."+hand.getMethod().getName()+".rules";
		//3.取得验证信息的value值
		String validateContent=MessageUtil.getMessage(hand.getBean(), validatorKey);
		if(validateContent == null||"".equals(validateContent)) {//如果没有直接放行
			return true;
		}else {
			String rules[] = validateContent.split("\\|");
			Map<String, String> results=new ValidatorRules(hand.getBean(),rules,request).validate();
			if(results.size()>0) {//有错误则跳转到错误界面
				request.setAttribute("errors", results);
				request.getRequestDispatcher("/errors.jsp").forward(request, response);
			}else {//如果没有错误则验证上传信息
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
