const API_URL = process.env.NEXT_PUBLIC_API_URL

export function getTopArtists() {
	return fetch(API_URL + '/artists/top?count=4')
}

export function searchArtists(query: string) {
	return fetch(API_URL + '/artists?name=' + encodeURIComponent(query))
}
