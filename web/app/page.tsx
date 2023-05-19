import Hero from './Hero'
import SearchArtist from '../components/SearchArtist'
import * as api from '../services/setlistApiService'
import { Artist } from '../models'

export default async function Home() {
	async function getTopArtists(): Promise<Artist[]> {
		const res = await api.getTopArtists()
		if (!res.ok) {
			throw new Error('Failed to fetch top artists')
		}
		return res.json()
	}

	const topArtists = await getTopArtists()
	return (
		<div className="flex w-full grow flex-col items-center 2xl:flex-row">
			<Hero />
			<SearchArtist topArtists={topArtists} />
		</div>
	)
}
