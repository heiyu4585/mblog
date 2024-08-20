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