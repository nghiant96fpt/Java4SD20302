package com.java4.sd20302.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "username", length = 100, unique = true, nullable = false)
	private String username;

	@Column(name = "password", length = 255, nullable = false)
	private String password; // => varchar

	@Column(name = "name", nullable = false, columnDefinition = "NVARCHAR(100)")
	private String name; // nvarchar

	@Column(name = "email", length = 150, unique = true, nullable = false)
	private String email;

	@Column(name = "phone", length = 12, unique = true, nullable = false)
	private String phone;

	@Column(name = "role", nullable = false)
	private int role = 1;

	@Column(name = "status", nullable = false)
	private int status = 1;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Video> videos;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Favourites> favorites;
}