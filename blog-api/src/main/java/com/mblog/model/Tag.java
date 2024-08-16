package com.mblog.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity // This tells Hibernate to make a table out of this class
public class Tag {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String name;//标签名称
	private String color;//标签颜色(与Semantic UI提供的颜色对应，可选)
//	@OneToMany(mappedBy = "id",cascade= CascadeType.ALL,fetch=FetchType.LAZY)
//	private List<Blog> blogs = new ArrayList<>();//该标签下的博客文章
//	@ManyToMany(mappedBy = "tagList")
//	private List<Blog> blogs = new ArrayList<>();//该分类下的博客文章

}
