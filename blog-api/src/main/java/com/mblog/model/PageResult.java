package com.mblog.model;

import lombok.Data;
import java.util.List;

@Data
public class PageResult<T> {
	private Integer totalPage;//总页数
	private List<T> list;//数据

	public PageResult(Integer totalPage, List<T> list) {
		this.totalPage = totalPage;
		this.list = list;
	}
}
