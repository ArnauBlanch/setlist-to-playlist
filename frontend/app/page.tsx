import Hero from './Hero'
import SearchArtist from './SearchArtist'

export default function Home() {
	return (
		<div className="container mx-auto flex h-full w-screen flex-col items-center p-4 2xl:mx-0 2xl:flex-row">
			<Hero />
			<SearchArtist />
		</div>
	)
}
