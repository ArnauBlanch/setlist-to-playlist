export default function Hero() {
	return (
		<div className="mx-auto mt-4 mb-8 w-full text-center md:mb-12 2xl:mx-0 2xl:mt-4 2xl:mr-16 2xl:w-auto">
			<h1 className="mb-2 whitespace-nowrap text-4xl font-extrabold text-gray-900 dark:text-white md:text-6xl xl:mb-4 2xl:text-7xl">
				Create your new
			</h1>

			<h1 className="mb-6 whitespace-nowrap text-4xl font-extrabold text-gray-900 dark:text-white md:mb-10 md:text-6xl 2xl:mb-20 2xl:text-7xl">
				<span className="bg-gradient-to-r from-pink-500 via-red-500 to-amber-400 bg-clip-text text-transparent">
					favourite playlist
				</span>
				.
			</h1>
			<p className="text-sm font-extrabold text-gray-600 dark:text-gray-300 md:text-xl 2xl:mb-2 2xl:whitespace-nowrap 2xl:text-2xl">
				Create a Spotify playlist from a live concert setlist
			</p>
			<p className="text-sm font-normal text-gray-500 dark:text-gray-400 md:text-lg 2xl:whitespace-nowrap 2xl:text-xl">
				and get ready for the upcoming concerts of your favourite
				artists!
			</p>
		</div>
	)
}
