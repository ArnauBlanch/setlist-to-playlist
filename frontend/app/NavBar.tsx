import Link from 'next/link'

export default function NavBar() {
	return (
		<nav className="sticky top-0 left-0 z-20 h-12 w-full bg-white px-4 py-2.5 dark:bg-gray-900 sm:px-4 md:h-16">
			<div className="container mx-auto flex flex-wrap items-center justify-between">
				<Link href="/" className="flex items-center">
					<svg
						xmlns="http://www.w3.org/2000/svg"
						version="1.1"
						x="0px"
						y="0px"
						viewBox="0 0 60 60"
						xmlSpace="preserve"
						className="mr-3 h-8 w-8 bg-gradient-to-r from-pink-500 via-red-500 to-yellow-500 fill-white dark:fill-gray-900"
					>
						<path
							id="path180"
							d="M -0.234375,-0.234375 V 60.058594 H 60.466797 V -0.234375 Z M 30,0 C 46.541983,0 60,13.458017 60,30 60,46.541983 46.541983,60 30,60 13.458017,60 0,46.541983 0,30 0,13.458017 13.458017,0 30,0 Z m -6.943359,14.001953 c -0.178375,-0.01025 -0.359438,0.02628 -0.523438,0.113281 C 22.205203,14.288234 22,14.629 22,15 v 30 c 0,0.371 0.205203,0.711766 0.533203,0.884766 C 22.679203,45.962766 22.84,46 23,46 c 0.197,0 0.3935,-0.05883 0.5625,-0.173828 l 22,-15 C 45.8355,30.640172 46,30.331 46,30 46,29.669 45.836453,29.359828 45.564453,29.173828 l -22,-15 c -0.1535,-0.104 -0.329438,-0.161625 -0.507812,-0.171875 z"
						/>
					</svg>
					<span className="self-center whitespace-nowrap text-xl font-semibold dark:text-white">
						Setlist to Playlist
					</span>
				</Link>
			</div>
		</nav>
	)
}
