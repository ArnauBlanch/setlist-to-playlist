import Image from 'next/image'

export default function ArtistCard({
	name,
	imageUrl,
}: {
	name: string
	imageUrl?: string
}) {
	return (
		<div className="rounded-2xl bg-gradient-to-r from-pink-500 via-red-500 to-yellow-500 p-1 shadow-xl">
			<a
				href="#"
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
			</a>
		</div>
	)
}
