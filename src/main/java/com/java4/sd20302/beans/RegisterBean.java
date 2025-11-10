package com.java4.sd20302.beans;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterBean {
	private String username;
	private String password;
	private String name;
	private String email;
	private String phone;

	public Map<String, String> getErrors() {
		Map<String, String> map = new HashMap<String, String>();

		if (username.isBlank()) {
			map.put("errUsername", "Tên tài khoản không được bỏ trống");
		}

		if (password.trim().length() < 6) {
			map.put("errPassword", "Mật khẩu có ít nhất 6 ký tự");
		}

		if (name.isBlank()) {
			map.put("errName", "Họ và tên không bỏ trống");
		}

		if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
			map.put("errEmail", "Email không đúng định dạng");
		}

		if (!phone.matches("^0\\d{9}$")) {
			map.put("errPhone", "Số điện thoại không đúng định dạng");
		}

		return map;
	}
}
