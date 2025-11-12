package com.java4.sd20302.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.java4.sd20302.beans.LoginBean;
import com.java4.sd20302.entities.User;
import com.java4.sd20302.services.UserServices;
import com.java4.sd20302.utils.Utils;

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
				Utils.setCookie(Utils.COOKIE_KEY_USER_ID, String.valueOf(user.getId()), resp);
				Utils.setCookie(Utils.COOKIE_KEY_ROLE, String.valueOf(user.getRole()), resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		req.getRequestDispatcher("/login.jsp").forward(req, resp);
	}
}
