package com.mblog.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private String nickname;
	private String avatar;
	private String email;
	private Date createTime;
	private Date updateTime;
	private String role;

}
