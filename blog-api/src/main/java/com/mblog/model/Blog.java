package com.mblog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "blog")
public class Blog {
    @Id
	private Integer id;//文章id
	private String title;//文章标题
	private String firstPicture;//文章首图，用于随机文章展示
	private String content;//文章正文
	private String description;//描述
	private Boolean published;//公开或私密
	private Boolean recommend;//推荐开关
	private Boolean appreciation;//赞赏开关
	private Boolean commentEnabled;//评论开关
	private Boolean top;//是否置顶
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	private Integer views;//浏览次数
	private Integer words;//文章字数
	private Integer readTime;//阅读时长(分钟)
	private String password;//密码保护

//	public Blog(String title, String content) {
//		this.title = title;
//		this.content = content;
//	}
//	private User user;//文章作者(因为是个人博客，也可以不加作者字段，暂且加上)
//	private Category category;//文章分类
//	private List<Tag> tags = new ArrayList<>();//文章标签
}
