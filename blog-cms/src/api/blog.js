import axios from '@/util/request'

export function getDataByQuery(queryInfo) {
	return axios({
		url: 'blogs',
		method: 'GET',
		params: {
			...queryInfo
		}
	})
}

export function deleteBlogById(id) {
	return axios({
		url: 'deleteBlogById',
		method: 'POST',
		params: {
			id
		}
	})
}

export function getTags(queryInfo) {
	return axios({
		url: 'tags',
		method: 'GET',
		params: {
			...queryInfo
		}
	})
}

export function getCategories() {
	return axios({
		url: 'categories',
		method: 'GET'
	})
}

export function saveBlog(blog) {
	return axios({
		url: 'addBlog',
		method: 'POST',
		data: {
			...blog
		}
	})
}

export function updateTop(id, top) {
	return axios({
		url: 'blog/top',
		method: 'POST',
		params: {
			id,
			top
		}
	})
}

export function updateRecommend(id, recommend) {
	return axios({
		url: 'blog/recommend',
		method: 'POST',
		params: {
			id,
			recommend
		}
	})
}

export function updateVisibility(id, form) {
	return axios({
		url: `blog/${id}/visibility`,
		method: 'POST',
		data: {
			...form
		}
	})
}

export function getBlogById(id) {
	return axios({
		url: 'getBlog',
		method: 'GET',
		params: {
			id
		}
	})
}

export function updateBlog(blog) {
	return axios({
		url: 'addBlog',
		method: 'POST',
		data: {
			...blog
		}
	})
}