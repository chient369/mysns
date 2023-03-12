package com.mySns;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class PageAccessFilter implements Filter {

	private List<String> permitURL = Arrays.asList("/home", "/edit", "/profile", "/post");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest rq = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = rq.getSession();
		
		if (permitURL.contains(rq.getRequestURI()) && session.getAttribute("user") == null) {
			String path = rq.getRequestURL().substring(0, rq.getRequestURL().indexOf(rq.getRequestURI()));
			res.sendRedirect(path + "/login");
		}else {
			chain.doFilter(rq, res);
		}
		
	}

}
