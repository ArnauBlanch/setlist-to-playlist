import Image from 'next/image'
import { Song } from '../../../models'
export default function SetlistSong({ id, song }: { id: number; song: Song }) {
	return (
		<div className="flex w-full flex-row gap-2 overflow-hidden whitespace-nowrap rounded-lg p-2.5 hover:bg-gray-100 dark:hover:bg-gray-900 md:p-3">
			<div className="relative block h-10 w-10 shrink-0 md:h-12 md:w-12">
				<Image
					src={song.albumCoverUrl}
					alt={song.albumName}
					fill={true}
					className="h-12 w-12 overflow-hidden rounded-md object-cover"
				/>
				<div className="relative flex h-full items-start">
					<span className="!-ml-1.5 !-mt-1.5 flex h-[1.1rem] w-[1.1rem] items-center justify-center rounded-full bg-black text-[10px] font-bold text-white dark:bg-yellow-400 dark:text-yellow-900 md:!-ml-2 md:!-mt-2 md:text-[12px]">
						{id}
					</span>
				</div>
			</div>
			<div className="items-left flex grow flex-col justify-between overflow-hidden whitespace-nowrap py-0.5 pl-1">
				<div className="flex flex-row items-center gap-2">
					<span className="overflow-hidden text-ellipsis whitespace-nowrap text-sm font-bold md:text-base">
						{song.name}
					</span>

					{song.originalArtist && (
						<div className="flex max-h-fit max-w-fit flex-row items-center rounded-full bg-rose-500 px-1.5 py-0.5 text-[10px] text-rose-50 dark:bg-rose-700 md:px-2 md:py-1 md:text-xs">
							<svg
								xmlns="http://www.w3.org/2000/svg"
								fill="none"
								viewBox="0 0 24 24"
								strokeWidth="1.5"
								className="h-2.5 w-2.5 stroke-white md:h-4 md:w-4"
							>
								<path
									strokeLinecap="round"
									strokeLinejoin="round"
									d="M9 9l10.5-3m0 6.553v3.75a2.25 2.25 0 01-1.632 2.163l-1.32.377a1.803 1.803 0 11-.99-3.467l2.31-.66a2.25 2.25 0 001.632-2.163zm0 0V2.25L9 5.25v10.303m0 0v3.75a2.25 2.25 0 01-1.632 2.163l-1.32.377a1.803 1.803 0 01-.99-3.467l2.31-.66A2.25 2.25 0 009 15.553z"
								/>
							</svg>

							<span className="ml-1 font-semibold">Cover</span>
						</div>
					)}
				</div>
				{!song.originalArtist && (
					<span className="overflow-hidden text-ellipsis whitespace-nowrap text-xs font-normal text-gray-700 dark:text-gray-400 md:text-sm">
						{!song.originalArtist && song.albumName}
					</span>
				)}
				{song.originalArtist && (
					<span className="overflow-hidden text-ellipsis whitespace-nowrap text-xs font-normal italic text-rose-500 dark:text-rose-400 md:text-sm">
						by{' '}
						<span className="font-bold">{song.originalArtist}</span>
					</span>
				)}
			</div>
			<div className="flex shrink-0 flex-col items-center self-center">
				<span className="flex h-6 w-6 items-center rounded-full bg-gradient-to-r from-pink-500 via-red-500 to-yellow-500 p-1.5 hover:opacity-80">
					<svg
						xmlns="http://www.w3.org/2000/svg"
						className="-mr-0.5 h-5 w-5 fill-white stroke-white md:h-5 md:w-5"
						viewBox="0 0 24 24"
						strokeWidth="2"
					>
						<path
							strokeLinecap="round"
							strokeLinejoin="round"
							d="M5.25 5.653c0-.856.917-1.398 1.667-.986l11.54 6.348a1.125 1.125 0 010 1.971l-11.54 6.347a1.125 1.125 0 01-1.667-.985V5.653z"
						/>
					</svg>
				</span>
				<span className="mt-1 text-[10px] text-gray-600 dark:text-gray-200 md:mt-1.5 md:text-xs">
					2:30
				</span>
			</div>
		</div>
	)
}
