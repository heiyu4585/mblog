import axios from '@/plugins/axios'

export function getBlogList(pageNum=1) {
	return axios({
		url: 'blogs',
		method: 'GET',
		params: {
			page: pageNum
		}
	})
}