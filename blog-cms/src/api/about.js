import axios from '@/util/request'

export function getAbout() {
	return axios({
		url: 'getAbout',
		method: 'GET'
	})
}

export function updateAbout(form) {
	return axios({
		url: 'updateAbout',
		method: 'POST',
		data: {
			...form
		}
	})
}

export function getTodoList() {
	return axios({
		url: 'getTodoList',
		method: 'GET'
	})
}

export function updateTodoList(form) {
	return axios({
		url: 'updateTodoList',
		method: 'POST',
		data: {
			...form
		}
	})
}
