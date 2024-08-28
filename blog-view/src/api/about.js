import axios from '@/plugins/axios'

export function getAbout() {
	return axios({
		url: 'getAbout',
		method: 'GET'
	})
}

export function getTodoList() {
	return axios({
		url: 'getTodoList',
		method: 'GET'
	})
}