'use client'
import { Artist } from '../models'
import ArtistCard from './ArtistCard'
import * as api from '../services/setlistApiService'
import { use } from 'react'

async function searchArtists(query: string): Promise<Artist[]> {
	const res = await api.searchArtists(query)
	if (!res.ok) {
		throw new Error('Failed to fetch top artists')
	}
	return res.json()
}

const promise = Promise.resolve([])

export default function ArtistSearchResults({
	topArtists,
	searchText,
}: {
	searchText?: string
	topArtists: Artist[]
}) {
	console.log('rendering results')
	console.log(topArtists)
	const searchResults = searchText ? use(promise) : null

	return (
		<div className="mx-auto">
			<div className="my-4 grid auto-cols-max grid-cols-1 content-start gap-4 rounded-lg md:grid-cols-2 2xl:h-[32rem]">
				{topArtists.map((a) => (
					<ArtistCard key={a.name} artist={a} />
				))}
			</div>
		</div>
	)
}
