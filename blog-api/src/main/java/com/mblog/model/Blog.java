package com.mblog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.FetchType;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Data
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "blog")
public class Blog {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	private Integer categoryId;

	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "tag", nullable = false)
	private List<String> tagList = new ArrayList<>();
// TODO 构造器作用
	/*public Blog() {

	}*/
}
