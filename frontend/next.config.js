/** @type {import('next').NextConfig} */
const nextConfig = {
	experimental: {
		appDir: true,
	},
	images: {
		domains: [
			'ca-times.brightspotcdn.com',
			'm.media-amazon.com',
			'e00-elmundo.uecdn.es',
			'www.binaural.es',
		],
	},
}

module.exports = nextConfig
