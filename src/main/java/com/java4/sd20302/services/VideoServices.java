package com.java4.sd20302.services;

import com.java4.sd20302.entities.Category;
import com.java4.sd20302.entities.Comment;
import com.java4.sd20302.entities.Favourites;
import com.java4.sd20302.entities.User;
import com.java4.sd20302.entities.Video;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class VideoServices {

	public static String addVideo(Video video, int userId, int catId) {
		Category category = CategoryServices.getInfoById(catId);
		if (category == null) {
			return "Lỗi";
		}
		User user = UserServices.getUserInfoById(userId);
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		try {
			if (!manager.getTransaction().isActive()) {
				manager.getTransaction().begin();
			}
			Video videoInsert = video;

			videoInsert.setCategory(category); // ??
			videoInsert.setUser(user); // ??

			manager.persist(videoInsert);
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
			manager.close();
			return "Lỗi";
		}
		manager.close();
		return null;
	}

//	- Sửa (Kiểm tra user id) **
//    - Category có tồn tại không?
//    - Video đang sửa có thuộc sở hữu của user đang login không?
	public static String updateVideo(Video video, int userId, int catId) {
		Category category = CategoryServices.getInfoById(catId);
		if (category == null) {
			return "Lỗi";
		}

		Video videoCheck = getInfoByIdAndUserId(video.getId(), userId);
		if (videoCheck == null) {
			return "Lỗi";
		}

		User user = UserServices.getUserInfoById(userId);
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();

		try {
			if (!manager.getTransaction().isActive()) {
				manager.getTransaction().begin();
			}
			Video videoInsert = video;

			videoInsert.setCategory(category);
			videoInsert.setUser(user);

			manager.merge(videoInsert);
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			manager.getTransaction().rollback();
			manager.close();
			return "Lỗi";
		}
		manager.close();
		return null;
	}

	public static Video getInfoByIdAndUserId(int id, int userId) {
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();
		try {
			String sql = "SELECT * FROM videos WHERE id=?1 AND user_id=?2";
			Query query = manager.createNativeQuery(sql, Video.class);
			query.setParameter(1, id);
			query.setParameter(2, userId);

			Video video = (Video) query.getSingleResult();
			manager.close();
			return video;

		} catch (Exception e) {
			e.printStackTrace();
		}
		manager.close();
		return null;
	}

	public static String deleteVideo(int videoId, int userId) {

//		Kiểm tra video có thuộc sở hữu không?

		Video videoCheck = getInfoByIdAndUserId(videoId, userId);
		if (videoCheck == null) {
			return "Lỗi";
		}

		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("dbConnect");
		EntityManager manager = managerFactory.createEntityManager();
		try {

			if (!manager.getTransaction().isActive()) {
				manager.getTransaction().begin();
			}

			for (Favourites favourite : videoCheck.getFavorites()) {
				manager.remove(favourite);
			}

			for (Comment comment : videoCheck.getComments()) {
				for (Comment subComment : comment.getComments()) {
					manager.remove(subComment); // ?????
				}
				manager.remove(comment);
			}
			manager.remove(videoCheck);

//			Xoá yêu thích
//			Xoá comment 
//			Xoá video

			manager.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			manager.getTransaction().rollback();
		}

		return null;
	}
}
