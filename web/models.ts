export interface Artist {
	id: string
	musicPlatformId: string
	name: string
	imageUrl: string
}

export interface Venue {
	name: string
	city: string
	country: string
	countryCode: string
}

export interface Song {
	id: string
	name: string
	albumName: string
	albumCoverUrl: string
	durationSeconds: number
	previewUrl: string
	originalArtist?: string
}

export interface Setlist {
	date: Date
	artist: Artist
	venue: Venue
	songs: Song[]
}
