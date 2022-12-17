export default function SearchBox() {
	return (
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
					className="text-md h-14.5 block w-full rounded-full border-2 border-gray-200 bg-white p-2.5 pl-10 text-lg text-gray-900 focus:border-rose-500  focus:ring-rose-500 dark:border-gray-600 dark:bg-gray-700 dark:text-white dark:placeholder-gray-400 dark:focus:border-rose-700 dark:focus:ring-rose-700 xl:text-xl"
					placeholder="Search artist"
					required
				/>
			</div>
			<button
				type="submit"
				className="ml-2 rounded-full bg-gradient-to-r from-pink-500 via-red-500 to-amber-400 p-3.5 text-sm font-medium text-white hover:bg-rose-700 focus:outline-none focus:ring-4 focus:ring-rose-300 dark:border-rose-700 dark:bg-rose-700 dark:hover:bg-rose-800 dark:focus:ring-rose-800"
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
	)
}
