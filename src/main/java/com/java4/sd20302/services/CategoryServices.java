package com.java4.sd20302.services;

import java.util.ArrayList;
import java.util.List;

import com.java4.sd20302.entities.Category;
import com.java4.sd20302.utils.Utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class CategoryServices {
	public static List<Category> getAll() {
		List<Category> categories = new ArrayList<Category>();

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dbConnect");

		EntityManager manager = factory.createEntityManager();

		try {
			String sql = "SELECT * FROM categories";

			Query query = manager.createNativeQuery(sql, Category.class);

			categories = query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		manager.close();
		return categories;
	}

	public static String addCategory(Category category) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dbConnect");

		EntityManager manager = factory.createEntityManager();

		try {
			String sql = "SELECT * FROM categories WHERE LOWER(name)=?1";

			Query query = manager.createNativeQuery(sql, Category.class);
			query.setParameter(1, category.getName().toLowerCase().trim());

			Category categoryCheck = (Category) query.getSingleResult();

			if (categoryCheck != null) {
				return "Tên danh mục đã tồn tại";
			}

			if (!manager.getTransaction().isActive()) {
				manager.getTransaction().begin();
			}

			category.setName(Utils.capitalizeString(category.getName()));

			manager.persist(category);

			manager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();

			manager.getTransaction().rollback();

			return "Có lỗi khi thêm danh mục";
		}

		return null;
	}

//	String sql = "SELECT * FROM categories WHERE LOWER(name)=?1";

//	1, name 1
//	2, name 2

//	User chọn cập nhật category id = 1
//	- Nếu nhập name 2 => Thông báo trùng
//	- Nếu nhập là name 1 => Cho cập nhật vào báo thành công
//	- Nếu nhập giá trị khác bất kỳ => Cho cập nhật vào báo thành công

	public static String updateCategory(Category category) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dbConnect");

		EntityManager manager = factory.createEntityManager();

		try {
			String sql = "SELECT * FROM categories WHERE LOWER(name) = ?1 AND id != ?2";
//			<>
			Query query = manager.createNativeQuery(sql, Category.class);
			query.setParameter(1, category.getName().toLowerCase().trim());
			query.setParameter(2, category.getId());

			Category categoryCheck = (Category) query.getSingleResult();

			if (categoryCheck != null) {
				return "Tên danh mục đã tồn tại";
			}

			if (!manager.getTransaction().isActive()) {
				manager.getTransaction().begin();
			}

			category.setName(Utils.capitalizeString(category.getName()));

			manager.merge(category);

			manager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();

			manager.getTransaction().rollback();

			return "Có lỗi khi cập nhật danh mục";
		}

		return null;
	}

	public static String deleteCategory(int id) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = factory.createEntityManager();
		try {
			Category category = getInfoById(id);
			if (category.getVideos().size() > 0) {
				return "Có video trong danh mục không thể xoá";
			}
			if (!manager.getTransaction().isActive()) {
				manager.getTransaction().begin();
			}
			manager.remove(category);
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
			return "Có lỗi khi xoá danh mục";
		}

		return null;
	}

	public static Category getInfoById(int id) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dbConnect");

		EntityManager manager = factory.createEntityManager();

		try {
			Category category = manager.find(Category.class, id);

			manager.close();
			return category;
		} catch (Exception e) {
			e.printStackTrace();
		}

		manager.close();
		return null;
	}
}
