export default function Home() {
	return (
		<div>
			<div className="mx-auto mt-4 mb-8 max-w-3xl text-center md:mx-auto md:mb-20 lg:mt-4">
				<h1 className="mb-2 text-4xl font-extrabold text-gray-900 dark:text-white md:text-5xl lg:mb-4 lg:text-7xl">
					Create your new
				</h1>
				<h1 className="mb-6 text-4xl font-extrabold text-gray-900 dark:text-white md:mb-10 md:text-5xl lg:text-7xl">
					<span className="bg-gradient-to-r from-amber-400 to-rose-600 bg-clip-text text-transparent">
						favourite playlist
					</span>
					.
				</h1>
				<p className="mb-4 text-lg font-bold text-rose-600 dark:text-gray-400 md:text-xl lg:text-2xl">
					Create a Spotify playlist from a live concert setlist
				</p>
				<p className="md:text-md mb-6 text-sm font-normal text-gray-500 dark:text-gray-500 lg:text-lg">
					Get ready for the upcoming concerts of your favourite
					artists, so you can practice your singing skills and enjoy
					the experience to the fullest!
				</p>
			</div>

			<form className="mx-auto flex max-w-3xl items-center">
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
						className="text-md block w-full rounded-lg border border-gray-300 bg-gray-50 p-2.5 pl-10 text-gray-900 focus:border-rose-500 focus:ring-rose-500 dark:border-gray-600  dark:bg-gray-700 dark:text-white dark:placeholder-gray-400 dark:focus:border-rose-700 dark:focus:ring-rose-700 lg:text-xl"
						placeholder="Search artist"
						required
					/>
				</div>
				<button
					type="submit"
					className="ml-2 rounded-lg border border-rose-600 bg-rose-600 p-2.5 text-sm font-medium text-white hover:bg-rose-700 focus:outline-none focus:ring-4 focus:ring-rose-300 dark:border-rose-700 dark:bg-rose-700 dark:hover:bg-rose-800 dark:focus:ring-rose-800"
				>
					<svg
						className="h-6 w-6 lg:h-7 lg:w-7"
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
			<div className="mx-auto grid max-w-3xl grid-cols-1 content-start gap-4 rounded-lg py-4 md:grid-cols-2">
				<div className="group flex flex-row items-center rounded-3xl bg-gradient-to-r from-amber-200 to-rose-400 p-4 hover:border-rose-200 hover:bg-rose-100">
					<img
						className="aspect-square h-28 rounded-full object-cover group-hover:opacity-80"
						alt="The Black Keys"
						src="https://ca-times.brightspotcdn.com/dims4/default/3c74377/2147483647/strip/true/crop/6000x4000+0+0/resize/1200x800!/format/webp/quality/80/?url=https%3A%2F%2Fcalifornia-times-brightspot.s3.amazonaws.com%2F46%2Fcb%2F4583686798f1da737b90db957ab0%2Fc3a8c6131aa348d291c9e82450e45ddf"
					/>
					<div className="ml-4 text-sm font-bold text-white group-hover:text-rose-700 md:text-2xl">
						The Black Keys
					</div>
				</div>
			</div>
		</div>
	)
}
