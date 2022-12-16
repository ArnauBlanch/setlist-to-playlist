import ArtistCard from './ArtistCard'
import SearchBox from './SearchBox'

export default function SearchArtist() {
	return (
		<div className="w-full max-w-3xl grow 2xl:mb-0">
			<SearchBox />
			<div className="mx-auto">
				<div className="grid-auto-cols my-4 grid auto-cols-max grid-cols-1 content-start gap-4 rounded-lg md:grid-cols-2 2xl:h-[32rem]">
					<ArtistCard
						name="The Black Keys"
						imageUrl="https://ca-times.brightspotcdn.com/dims4/default/3c74377/2147483647/strip/true/crop/6000x4000+0+0/resize/1200x800!/format/webp/quality/80/?url=https%3A%2F%2Fcalifornia-times-brightspot.s3.amazonaws.com%2F46%2Fcb%2F4583686798f1da737b90db957ab0%2Fc3a8c6131aa348d291c9e82450e45ddf"
					/>
					<ArtistCard
						name="Arctic Monkeys"
						imageUrl="https://m.media-amazon.com/images/I/9108EhcTlTL._SL1500_.jpg"
					/>
					<ArtistCard
						name="Manel"
						imageUrl="https://e00-elmundo.uecdn.es/assets/multimedia/imagenes/2019/10/31/15725389077883.jpg"
					/>
					<ArtistCard
						name="Maria Arnal i Marcel Bagés"
						imageUrl="https://www.binaural.es/wp-content/uploads/2021/03/maria1-1.jpg"
					/>
				</div>
			</div>
		</div>
	)
}
