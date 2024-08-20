import axios from '@/plugins/axios'

export function getAbout() {
	return axios({
		url: 'getAbout',
		method: 'GET'
	})
}