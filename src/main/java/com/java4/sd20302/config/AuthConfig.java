package com.java4.sd20302.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java4.sd20302.entities.User;
import com.java4.sd20302.services.UserServices;
import com.java4.sd20302.utils.Utils;

@WebFilter(urlPatterns = { "/admin/*", "/editer/*", "/user/*" })
public class AuthConfig implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

//		1 == User
//		2 == Editer
//		3 == Admin

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String userID = Utils.getCookieValue(Utils.COOKIE_KEY_USER_ID, req);
		String role = Utils.getCookieValue(Utils.COOKIE_KEY_ROLE, req);

		if (userID == null || role == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
//		Có userid và role => ?làm sao lấy được role mới nhất từ db?

		String path = req.getRequestURI();

		User user = UserServices.getUserInfoById(Integer.parseInt(userID));

		if (user.getStatus() == 0) {
//			Bị khoá 
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		if (!role.equals(String.valueOf(user.getRole()))) {
			Utils.clearCookie(req, resp);

			Utils.setCookie(Utils.COOKIE_KEY_USER_ID, String.valueOf(user.getId()), resp);
			Utils.setCookie(Utils.COOKIE_KEY_ROLE, String.valueOf(user.getRole()), resp);
//			Duy trì đăng nhập khi có tương tác vào hệ thống

			role = String.valueOf(user.getRole());
		}

		if (path.contains("/user/") && !role.equals("1") && !role.equals("2")) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		if (path.contains("/editer/") && !role.equals("2")) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		if (path.contains("/admin/") && !role.equals("3")) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		// cho chạy tiếp qua servlet => Next
		chain.doFilter(request, response);
	}

}
