package com.cyn.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class StrutsFilter extends StrutsPrepareAndExecuteFilter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String requestURI = request.getRequestURI();
		if (requestURI != null) {
			if (requestURI.indexOf("/ueditor1_3_6") != -1) {
				chain.doFilter(req, res);
			} else {
				super.doFilter(req, res, chain);
			}
		} else {
			super.doFilter(req, res, chain);
		}
	}

}
