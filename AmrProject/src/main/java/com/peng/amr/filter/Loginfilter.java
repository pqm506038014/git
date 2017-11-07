package com.peng.amr.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebFilter("/pages/*")
public class Loginfilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//强制类型转换
		HttpServletRequest req = (HttpServletRequest) request;
		if(req.getSession().getAttribute("emp") != null) {
			chain.doFilter(request,response );
		}else {
			req.setAttribute("msg", "你还未登陆，请登陆后进行操作！");
			req.setAttribute("url", "/login.jsp");
			req.getRequestDispatcher("/forward.jsp").forward(request, response);
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
