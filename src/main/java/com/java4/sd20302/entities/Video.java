package com.java4.sd20302.entities;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "videos")
public class Video {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "title", nullable = false, columnDefinition = "NVARCHAR(1000)")
	private String title;

	@Column(name = "desc", nullable = false, columnDefinition = "NTEXT")
	private String desc;

	@Column(name = "poster", nullable = false, length = 255)
	private String poster;

	@Column(name = "url_youtube", nullable = false, length = 255)
	private String urlYoutube;

	@Column(name = "create_at", nullable = false)
	private Date createAt;

	@Column(name = "view_count", nullable = false)
	private int viewCount;

	@Column(name = "status", nullable = false)
	private int status;

	@ManyToOne
	@JoinColumn(name = "cat_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "user")
	private List<Favorite> video;
}