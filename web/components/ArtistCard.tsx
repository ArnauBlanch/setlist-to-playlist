import Image from 'next/image'
import Link from 'next/link'
import { Artist } from '../models'

export default function ArtistCard({
	artist: { name, imageUrl },
}: {
	artist: Artist
}) {
	return (
		<div className="rounded-2xl bg-gradient-to-r from-pink-500 via-red-500 to-amber-400 p-1 shadow-xl">
			<Link
				href="/artists/1"
				className="group relative block h-44 overflow-hidden rounded-xl md:h-40 2xl:h-60"
			>
				{imageUrl && (
					<Image
						src={imageUrl}
						alt={name}
						fill={true}
						className="w-full object-cover group-hover:opacity-90"
					/>
				)}

				<div className="relative flex h-full items-end bg-black bg-opacity-40 p-6 text-white group-hover:bg-pink-800 group-hover:bg-opacity-40 md:p-8">
					<h3 className="text-xl font-bold md:text-2xl">{name}</h3>
				</div>
			</Link>
		</div>
	)
}
