package com.java4.sd20302.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.java4.sd20302.beans.LoginBean;
import com.java4.sd20302.entities.User;
import com.java4.sd20302.services.UserServices;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.getRequestDispatcher("/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			LoginBean bean = new LoginBean();

			BeanUtils.populate(bean, req.getParameterMap());

			req.setAttribute("bean", bean);

			if (bean.getErrors().isEmpty()) {
//				Kiểm tra đăng nhập. nếu thành công lưu userId và role vào cookie 
				User user = UserServices.login(bean.getUsernameOrEmail(), bean.getPassword());

//				Lưu vào user_id và role vào cookie với thời hạn là 3 ngày 1h30

//				long day = 60 * 60 * 24 * 3 + 60 * 60 * 6;

				int day = 60 * 60 * 24 * (3 + (6 / 24));

				Cookie cookieUserId = new Cookie("user_id", String.valueOf(user.getId()));
				cookieUserId.setMaxAge(day);

				Cookie cookieRole = new Cookie("role", String.valueOf(user.getRole()));
				cookieRole.setMaxAge(day);

				resp.addCookie(cookieUserId);
				resp.addCookie(cookieRole);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		req.getRequestDispatcher("/login.jsp").forward(req, resp);
	}
}
