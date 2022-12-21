/** @type {import('next').NextConfig} */
const nextConfig = {
	output: 'standalone',
	experimental: {
		appDir: true,
	},
	images: {
		domains: [
			'ca-times.brightspotcdn.com',
			'm.media-amazon.com',
			'e00-elmundo.uecdn.es',
			'www.binaural.es',
			'i.scdn.co',
		],
	},
}

module.exports = nextConfig
