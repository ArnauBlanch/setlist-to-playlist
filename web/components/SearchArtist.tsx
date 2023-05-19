'use client'
import ArtistSearchResults from './ArtistSearchResults'
import { Artist } from '../models'
import { useState } from 'react'
import SearchBox from './SearchBox'

export default function SearchArtist({ topArtists }: { topArtists: Artist[] }) {
	const [searchText, setSearchText] = useState('')
	return (
		<div className="w-full max-w-3xl p-2 2xl:mb-0 2xl:max-w-none">
			<SearchBox onSearchRequested={setSearchText} />
			<ArtistSearchResults
				topArtists={topArtists}
				searchText={searchText}
			/>
		</div>
	)
}
