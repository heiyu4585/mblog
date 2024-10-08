import axios from '@/plugins/axios'

export function getBlogById(token, id) {
	return axios({
		url: 'getBlog',
		method: 'GET',
		headers: {
			Authorization: token,
		},
		params: {
			id
		}
	})
}

export function checkBlogPassword(blogPasswordForm) {
	return axios({
		url: 'checkBlogPassword',
		method: 'POST',
		data: {
			...blogPasswordForm
		}
	})
}

export function getSearchBlogList(title) {
	return axios({
		url: 'searchBlog',
		method: 'GET',
		params: {
			title
		}
	})
}