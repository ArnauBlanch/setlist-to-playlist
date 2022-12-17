import Hero from './Hero'
import SearchArtist from './SearchArtist'

export default function Home() {
	return (
		<div className="flex w-full grow flex-col items-center 2xl:flex-row">
			<Hero />
			<SearchArtist />
		</div>
	)
}
