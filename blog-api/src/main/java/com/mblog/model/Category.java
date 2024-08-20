package com.mblog.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity // This tells Hibernate to make a table out of this class
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;//分类名称
//	@OneToMany(mappedBy = "id",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
//	private List<Blog> blogs = new ArrayList<>();//该分类下的博客文章
}
