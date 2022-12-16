export default function SearchPage({
	params,
}: {
	params: { artistId: string }
}) {
	return <div>Artist ID: {params.artistId}</div>
}
