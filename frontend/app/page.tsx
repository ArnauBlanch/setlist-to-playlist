export default function Home() {
	return (
		<div>
			<div className="max-w-3xl mx-auto mt-4 lg:mt-4 text-center md:mx-auto mb-8 md:mb-20">
				<h1 className="mb-2 lg:mb-4 text-4xl font-extrabold text-gray-900 dark:text-white md:text-5xl lg:text-7xl">
					Create your new
				</h1>
				<h1 className="mb-6 md:mb-10 text-4xl font-extrabold text-gray-900 dark:text-white md:text-5xl lg:text-7xl">
					<span className="text-transparent bg-clip-text bg-gradient-to-r from-amber-400 to-rose-600">
						favourite playlist
					</span>
					.
				</h1>
				<p className="mb-4 text-lg font-bold text-rose-600 md:text-xl lg:text-2xl dark:text-gray-400">
					Create a Spotify playlist from a live concert setlist
				</p>
				<p className="mb-6 text-sm font-normal text-gray-500 md:text-md lg:text-lg dark:text-gray-500">
					Get ready for the upcoming concerts of your favourite
					artists, so you can practice your singing skills and enjoy
					the experience to the fullest!
				</p>
			</div>

			<form className="flex items-center max-w-3xl mx-auto">
				<label htmlFor="simple-search" className="sr-only">
					Search artist
				</label>
				<div className="relative w-full">
					<div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
						<svg
							aria-hidden="true"
							className="w-6 h-6 text-gray-500 dark:text-gray-400"
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
						className="bg-gray-50 border border-gray-300 text-gray-900 text-md lg:text-xl rounded-lg focus:ring-rose-500 focus:border-rose-500 block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-rose-700 dark:focus:border-rose-700"
						placeholder="Search artist"
						required
					/>
				</div>
				<button
					type="submit"
					className="p-2.5 ml-2 text-sm font-medium text-white bg-rose-600 rounded-lg border border-rose-600 hover:bg-rose-700 focus:ring-4 focus:outline-none focus:ring-rose-300 dark:bg-rose-700 dark:border-rose-700 dark:hover:bg-rose-800 dark:focus:ring-rose-800"
				>
					<svg
						className="w-6 h-6 lg:w-7 lg:h-7"
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
			<div className="max-w-3xl mx-auto rounded-lg py-4 grid content-start grid-cols-1 md:grid-cols-2 gap-4">
				<div className="group bg-gradient-to-r from-amber-200 to-rose-400 hover:bg-rose-100 hover:border-rose-200 rounded-3xl flex flex-row items-center p-4">
					<img
						className="group-hover:opacity-80 object-cover rounded-full aspect-square h-28"
						alt="The Black Keys"
						src="https://ca-times.brightspotcdn.com/dims4/default/3c74377/2147483647/strip/true/crop/6000x4000+0+0/resize/1200x800!/format/webp/quality/80/?url=https%3A%2F%2Fcalifornia-times-brightspot.s3.amazonaws.com%2F46%2Fcb%2F4583686798f1da737b90db957ab0%2Fc3a8c6131aa348d291c9e82450e45ddf"
					/>
					<div className="font-bold text-white group-hover:text-rose-700 text-sm md:text-2xl ml-4">
						The Black Keys
					</div>
				</div>
			</div>
		</div>
	)
}
