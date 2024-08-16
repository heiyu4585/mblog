import axios from '@/util/request'

export function getData(queryInfo) {
	return axios({
		url: 'categories',
		method: 'GET',
		params: {
			...queryInfo
		}
	})
}

export function addCategory(form) {
	return axios({
		url: 'addCategory',
		method: 'POST',
		data: {
			...form
		}
	})
}

export function editCategory(form) {
	return axios({
		url: 'editCategory',
		method: 'POST',
		data: {
			...form
		}
	})
}

export function deleteCategoryById(id) {
	return axios({
		url: 'deleteCategoryById',
		method: 'POST',
		params: {
			id
		}
	})
}