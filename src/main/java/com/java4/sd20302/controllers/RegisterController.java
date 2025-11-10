package com.java4.sd20302.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.java4.sd20302.beans.RegisterBean;
import com.java4.sd20302.entities.User;
import com.java4.sd20302.services.UserServices;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.getRequestDispatcher("/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");

		try {
			RegisterBean bean = new RegisterBean();

			BeanUtils.populate(bean, req.getParameterMap());

			req.setAttribute("bean", bean);

			if (bean.getErrors().isEmpty()) {
//				thực hiện đăng ký tài khoản 
//				convert bean to entity 
				User user = new User();
				user.setUsername(bean.getUsername());
				user.setPassword(bean.getPassword());
				user.setEmail(bean.getEmail());
				user.setPhone(bean.getPhone());
				user.setName(bean.getName());

				Map<String, String> errDB = UserServices.register(user);

				if (errDB.isEmpty()) {
					System.out.println("Register Success");
				} else {
					req.setAttribute("errDB", errDB);
					System.out.println("Register Fail");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		req.getRequestDispatcher("/register.jsp").forward(req, resp);
	}
}
