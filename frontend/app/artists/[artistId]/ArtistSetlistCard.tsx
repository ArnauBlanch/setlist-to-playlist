import { Setlist } from '../../../models'

export default function ArtistSetlistCard({
	setlist: { date, venue, songs },
}: {
	setlist: Setlist
}) {
	const available = songs.length > 0
	return (
		<div className="group overflow-hidden rounded-2xl bg-gradient-to-r from-pink-600 via-red-500 to-amber-400 text-white shadow-xl">
			<div
				className={`h-full bg-black p-4 sm:p-4 ${
					available
						? 'bg-opacity-0 group-hover:bg-opacity-30'
						: 'bg-opacity-70 text-opacity-25'
				}`}
			>
				{available && (
					<div className="ml-auto flex max-w-fit flex-row items-center whitespace-nowrap rounded-full bg-amber-300 px-2 py-1 text-xs text-amber-900">
						<svg
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 24 24"
							strokeWidth="1.5"
							stroke="currentColor"
							className="h-4 w-4"
						>
							<path
								strokeLinecap="round"
								strokeLinejoin="round"
								d="M9 9l10.5-3m0 6.553v3.75a2.25 2.25 0 01-1.632 2.163l-1.32.377a1.803 1.803 0 11-.99-3.467l2.31-.66a2.25 2.25 0 001.632-2.163zm0 0V2.25L9 5.25v10.303m0 0v3.75a2.25 2.25 0 01-1.632 2.163l-1.32.377a1.803 1.803 0 01-.99-3.467l2.31-.66A2.25 2.25 0 009 15.553z"
							/>
						</svg>

						<span className="ml-1">23 songs</span>
					</div>
				)}

				{!available && (
					<div className="flew-row ml-auto flex max-w-fit items-center whitespace-nowrap rounded-full bg-red-600 px-2 py-1 text-xs">
						<svg
							xmlns="http://www.w3.org/2000/svg"
							fill="none"
							viewBox="0 0 24 24"
							strokeWidth="1.5"
							stroke="currentColor"
							className="h-4 w-4"
						>
							<path
								strokeLinecap="round"
								strokeLinejoin="round"
								d="M18.364 18.364A9 9 0 005.636 5.636m12.728 12.728A9 9 0 015.636 5.636m12.728 12.728L5.636 5.636"
							/>
						</svg>

						<span className="ml-1">Unavailable</span>
					</div>
				)}

				<div
					className={`mt-12 flex flex-row items-center gap-1 text-xs font-normal md:text-sm ${
						available ? 'text-pink-100' : 'text-gray-400'
					}`}
				>
					<svg
						xmlns="http://www.w3.org/2000/svg"
						fill="none"
						viewBox="0 0 24 24"
						strokeWidth="1.5"
						stroke="currentColor"
						className="-ml-0.5 h-4 w-4"
					>
						<path
							strokeLinecap="round"
							strokeLinejoin="round"
							d="M15 10.5a3 3 0 11-6 0 3 3 0 016 0z"
						/>
						<path
							strokeLinecap="round"
							strokeLinejoin="round"
							d="M19.5 10.5c0 7.142-7.5 11.25-7.5 11.25S4.5 17.642 4.5 10.5a7.5 7.5 0 1115 0z"
						/>
					</svg>
					<span>Sant Adrià de Besòs, Spain</span>
				</div>

				<a href="#">
					<h3 className="text-2xl font-extrabold">{venue.name}</h3>
				</a>

				<div className="my-auto -mt-0.5 flex flex-row items-center gap-1 text-sm font-bold">
					June 10th, 2022
				</div>
			</div>
		</div>
	)
}
