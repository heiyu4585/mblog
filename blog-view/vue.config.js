module.exports = {
	outputDir: "view",
	devServer: {
		port: "8082",
		open: true,
		overlay: {
			warnings: false,
			errors: true
		},
		proxy: {
			'/api': {
				target: 'http://localhost:8080/',
				pathRewrite: { '^/api': '' },
				changeOrigin: true,     // target是域名的话，需要这个参数，
				secure: false,          // 设置支持https协议的代理
			}
		}
	},
	configureWebpack: {
		resolve: {
			alias: {
				'assets': '@/assets',
				'common': '@/common',
				'components': '@/components',
				'api': '@/api',
				'views': '@/views',
				'plugins': '@/plugins'
			}
		}
	}
}