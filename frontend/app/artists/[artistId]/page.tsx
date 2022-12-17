import { Setlist } from '../../../models'
import Image from 'next/image'
import ArtistSetlistCard from './ArtistSetlistCard'

const setlist: Setlist = {
	date: new Date(2022, 12, 6),
	artist: {
		name: 'The Black Keys',
		imageUrl:
			'https://i.scdn.co/image/ab6761610000e5ebae537808bd15be9f7031e99b',
	},
	venue: {
		name: 'Kia Forum',
		city: 'Inglewood',
		country: 'United States',
		countryCode: 'US',
	},
	songs: [
		{
			id: '5G1sTBGbZT5o4PNRc75RKI',
			name: 'Lonely Boy',
			albumName: 'El Camino',
			albumCoverUrl:
				'https://i.scdn.co/image/ab67616d0000b2736a21b97de47168df4f0c1993',
			durationSeconds: 193,
			previewUrl:
				'https://p.scdn.co/mp3-preview/2ad81dc04cb1e29388f918bbf948b5f812632131?cid=ed16feb3c955427dab52122d2515a642',
			originalArtist: 'string',
		},
	],
}

export default function ArtistPage({
	params,
}: {
	params: { artistId: string }
}) {
	return (
		<div className="mx-auto flex w-full flex-col items-center 2xl:flex-row">
			<div className="p-4 text-center">
				<Image
					src="https://ca-times.brightspotcdn.com/dims4/default/3c74377/2147483647/strip/true/crop/6000x4000+0+0/resize/1200x800!/format/webp/quality/80/?url=https%3A%2F%2Fcalifornia-times-brightspot.s3.amazonaws.com%2F46%2Fcb%2F4583686798f1da737b90db957ab0%2Fc3a8c6131aa348d291c9e82450e45ddf"
					width={600}
					height={600}
					alt="Manel"
					className="mx-10 aspect-square h-48 w-48 max-w-none rounded-full object-cover shadow-xl md:h-64 md:w-64 xl:h-80 xl:w-80"
				/>
				<h3 className="mt-6 text-4xl font-bold text-gray-900 dark:text-white">
					The Black Keys
				</h3>
			</div>

			<div className="w-full grow">
				<h1 className="mt-6 mb-4 flex flex-row items-center gap-[0.5rem] text-xl font-semibold text-pink-600 md:text-3xl">
					<svg
						xmlns="http://www.w3.org/2000/svg"
						fill="none"
						viewBox="0 0 24 24"
						strokeWidth="1.5"
						stroke="currentColor"
						className="h-6 w-6 md:h-8 md:w-8"
					>
						<path
							strokeLinecap="round"
							strokeLinejoin="round"
							d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 012.25-2.25h13.5A2.25 2.25 0 0121 7.5v11.25m-18 0A2.25 2.25 0 005.25 21h13.5A2.25 2.25 0 0021 18.75m-18 0v-7.5A2.25 2.25 0 015.25 9h13.5A2.25 2.25 0 0121 11.25v7.5"
						/>
					</svg>
					<span>Concerts</span>
				</h1>
				<div className="my-4 grid w-full auto-cols-max grid-cols-1 content-start gap-4 rounded-lg md:grid-cols-2 xl:grid-cols-3">
					<ArtistSetlistCard setlist={setlist} />
					<ArtistSetlistCard setlist={setlist} />
					<ArtistSetlistCard setlist={setlist} />
					<ArtistSetlistCard setlist={{ ...setlist, songs: [] }} />
					<ArtistSetlistCard setlist={setlist} />
					<ArtistSetlistCard setlist={setlist} />
					<ArtistSetlistCard setlist={{ ...setlist, songs: [] }} />
					<ArtistSetlistCard setlist={setlist} />
					<ArtistSetlistCard setlist={setlist} />
				</div>
				<div className="col-span-3 inline-flex w-full items-center justify-center gap-3 pt-2 md:pt-6">
					<a
						href="#"
						className="group inline-flex h-6 w-6 items-center justify-center rounded-full bg-pink-100 hover:bg-pink-600 dark:bg-gray-600 dark:hover:bg-gray-400"
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							className="h-6 w-6 fill-pink-600 group-hover:fill-white dark:fill-gray-300	dark:group-hover:fill-gray-700"
							viewBox="0 0 20 20"
						>
							<path
								fillRule="evenodd"
								d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z"
								clipRule="evenodd"
							/>
						</svg>
					</a>

					<p className="text-sm font-normal text-pink-700 dark:text-gray-400	dark:group-hover:fill-gray-700">
						<span className="font-bold">1</span>
						<span className="mx-0.25">&nbsp;/&nbsp;</span>
						12
					</p>

					<a
						href="#"
						className="group inline-flex h-6 w-6 items-center justify-center rounded-full bg-pink-100 hover:bg-pink-600 dark:bg-gray-600 dark:hover:bg-gray-400"
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							className="h-6 w-6 fill-pink-600 group-hover:fill-white dark:fill-gray-300"
							viewBox="0 0 20 20"
						>
							<path
								fillRule="evenodd"
								d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z"
								clipRule="evenodd"
							/>
						</svg>
					</a>
				</div>
			</div>
		</div>
	)
}
