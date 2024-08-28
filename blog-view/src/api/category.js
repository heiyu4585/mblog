import axios from '@/plugins/axios'

export function getBlogListByCategoryName(name, page) {
	return axios({
		url: 'getBlogsByCategoryName',
		method: 'GET',
		params: {
			name,
			page
		}
	})
}