package com.java4.sd20302.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java4.sd20302.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class UserServices {

	public static Map<String, String> register(User user) {
		Map<String, String> errorMap = new HashMap<String, String>();
//		Đọc dữ liệu được config sẵn ở file persistence.xml
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
//		Khởi tạo kết nối db
		EntityManager manager = managerFactory.createEntityManager();
		try {
			String sql = "SELECT * FROM users WHERE username=?1 OR email=?2 OR phone=?3";

			Query query = manager.createNativeQuery(sql, User.class);
			query.setParameter(1, user.getUsername());
			query.setParameter(2, user.getEmail());
			query.setParameter(3, user.getPhone());

			List<User> usersDB = query.getResultList();

			for (User item : usersDB) {
				if (item.getUsername().equals(user.getUsername())) {
					errorMap.put("errUsername", "Tên đăng nhập đã tồn tại");
				}

				if (item.getEmail().equals(user.getEmail())) {
					errorMap.put("errEmail", "Email đã tồn tại");
				}

				if (item.getPhone().equals(user.getPhone())) {
					errorMap.put("errPhone", "Số điện thoại đã tồn tại");
				}
			}

			if (errorMap.isEmpty()) {
//				Thực hiện insert vào db 

				if (!manager.getTransaction().isActive()) {
					manager.getTransaction().begin();
				}

				manager.persist(user);

				manager.getTransaction().commit();
			}

		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
		}

//		Ngắt kết nối
		manager.close();
		return errorMap;
	}
//	Có thể trả về các lỗi
//	errUsername => username đã tồn tại
//	errEmail => email đã tồn tại
//	errPhone => phone đã tồn tại
//	errRegister => Đăng ký thất bại

//	empty => đăng ký thành công 

//	Services Login (bằng username hoặc email )
//  INPUT: (username || email) && password
//	(String usernameOrEmail, String password)

	public static User login(String usernameOrEmail, String password) {

		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		try {
			String sql = "SELECT * FROM users WHERE username=?1 OR email=?2";
//			? WHERE cả username, email, password
//			=> WHERE  username=usernameOrEmail ?? email=usernameOrEmail ?? password=password
//			(... OR... ) AND... => true  => 1 đối tượng ?? n đối tượng => 1 
//			? WHERE username, email => ... OR ... => 1 

			Query query = manager.createNativeQuery(sql, User.class);
			query.setParameter(1, usernameOrEmail);
			query.setParameter(2, usernameOrEmail);

			User user = (User) query.getSingleResult();

//			if (user.getPassword().equals(password)) { // #1

			if (password.equals(user.getPassword())) { // #2
				manager.close();
				return user;
			}

//			String role; // => DB
//			if ("1".equals(role)) {
//
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		manager.close();
		return null;
	}

//	getUserInfoByUsernameOrEmail ? => SELECT WHER => 1tr user

	public static User getUserInfoById(int id) {
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		try {
			User user = manager.find(User.class, id);

//			user.getVideos(); ds video 
//			user.getFavorites().get(0).getVideo().getTitle() => tên video yêu thích đầu tiên trong ds
//			user.getFavorites().get(0).getVideo().getComments()

			manager.close();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}

		manager.close();
		return null;
	}
}
