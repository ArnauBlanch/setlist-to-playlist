import Image from 'next/image'

export default function SetlistPage({
	params,
}: {
	params: { setlistId: string }
}) {
	return (
		<div className="mx-auto flex h-full w-full grow flex-col justify-between p-4 xl:flex-row xl:gap-[3rem]">
			<div className="flex flex-col items-center text-center xl:w-1/2">
				<div className="px-10">
					<Image
						src="/cover.png"
						width={600}
						height={600}
						alt="Manel"
						className="aspect-square h-full w-full rounded-md object-cover shadow-lg md:h-64 md:w-64"
					/>
				</div>
				<h3 className="mt-6 text-4xl font-bold text-gray-900 dark:text-white">
					The Black Keys
				</h3>
			</div>

			<div className="w-1/2 grow">
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
					<span>Setlist</span>
				</h1>
			</div>
		</div>
	)
}
