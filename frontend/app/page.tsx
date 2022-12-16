import ArtistCard from './ArtistCard'

export default function Home() {
	return (
		<div className="container mx-auto flex h-full w-screen flex-col items-center p-4 2xl:mx-0 2xl:flex-row">
			<div className="mx-auto mt-4 mb-8 w-full grow-0 text-center md:mb-12 2xl:mx-0 2xl:mt-4 2xl:mr-16 2xl:w-auto">
				<h1 className="mb-2 whitespace-nowrap text-4xl font-extrabold text-gray-900 dark:text-white md:text-6xl xl:mb-4 2xl:text-7xl">
					Create your new
				</h1>

				<h1 className="mb-6 whitespace-nowrap text-4xl font-extrabold text-gray-900 dark:text-white md:mb-10 md:text-6xl 2xl:mb-16 2xl:text-7xl">
					<span className="bg-gradient-to-r from-pink-500 via-red-500 to-yellow-500 bg-clip-text text-transparent">
						favourite playlist
					</span>
					.
				</h1>
				<p className="text-sm font-extrabold text-pink-700 dark:text-gray-300 md:text-xl 2xl:whitespace-nowrap 2xl:text-2xl">
					Create a Spotify playlist from a live concert setlist
				</p>
				<p className="text-sm font-normal text-gray-500 dark:text-gray-400 md:text-lg 2xl:whitespace-nowrap 2xl:text-xl">
					and get ready for the upcoming concerts of your favourite
					artists!
				</p>
			</div>

			<div className="mb-12 w-full max-w-3xl grow 2xl:mb-0">
				<form className="mb-2 flex w-full items-center">
					<label htmlFor="simple-search" className="sr-only">
						Search artist
					</label>
					<div className="relative w-full">
						<div className="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
							<svg
								aria-hidden="true"
								className="h-6 w-6 text-gray-500 dark:text-gray-400"
								fill="currentColor"
								viewBox="0 0 20 20"
								xmlns="http://www.w3.org/2000/svg"
							>
								<path
									fillRule="evenodd"
									d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
									clipRule="evenodd"
								></path>
							</svg>
						</div>
						<input
							type="text"
							id="simple-search"
							className="text-md h-14.5 block w-full rounded-full border-2 border-gray-200 bg-gray-100 p-2.5 pl-10 text-lg text-gray-900 focus:border-rose-500  focus:ring-rose-500 dark:border-gray-600 dark:bg-gray-700 dark:text-white dark:placeholder-gray-400 dark:focus:border-rose-700 dark:focus:ring-rose-700 xl:text-xl"
							placeholder="Search artist"
							required
						/>
					</div>
					<button
						type="submit"
						className="ml-2 rounded-full bg-gradient-to-r from-pink-500 via-red-500 to-yellow-500 p-3.5 text-sm font-medium text-white hover:bg-rose-700 focus:outline-none focus:ring-4 focus:ring-rose-300 dark:border-rose-700 dark:bg-rose-700 dark:hover:bg-rose-800 dark:focus:ring-rose-800"
					>
						<svg
							className="h-6 w-6 xl:h-7 xl:w-7"
							fill="none"
							stroke="currentColor"
							viewBox="0 0 24 24"
							xmlns="http://www.w3.org/2000/svg"
						>
							<path
								strokeLinecap="round"
								strokeLinejoin="round"
								strokeWidth="2"
								d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
							></path>
						</svg>
						<span className="sr-only">Search artist</span>
					</button>
				</form>

				<div className="mx-auto">
					<div className="grid-auto-cols grid h-full auto-cols-max grid-cols-1 content-start gap-4 rounded-lg py-4 md:grid-cols-2">
						<ArtistCard
							name="The Black Keys"
							imageUrl="https://ca-times.brightspotcdn.com/dims4/default/3c74377/2147483647/strip/true/crop/6000x4000+0+0/resize/1200x800!/format/webp/quality/80/?url=https%3A%2F%2Fcalifornia-times-brightspot.s3.amazonaws.com%2F46%2Fcb%2F4583686798f1da737b90db957ab0%2Fc3a8c6131aa348d291c9e82450e45ddf"
						/>
						<ArtistCard
							name="Arctic Monkeys"
							imageUrl="https://m.media-amazon.com/images/I/9108EhcTlTL._SL1500_.jpg"
						/>
						<ArtistCard
							name="Manel"
							imageUrl="https://e00-elmundo.uecdn.es/assets/multimedia/imagenes/2019/10/31/15725389077883.jpg"
						/>
						<ArtistCard
							name="Maria Arnal"
							imageUrl="https://www.binaural.es/wp-content/uploads/2021/03/maria1-1.jpg"
						/>
					</div>
				</div>
			</div>
		</div>
	)
}
