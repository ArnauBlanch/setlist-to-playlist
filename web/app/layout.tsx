import './globals.css'
import AppThemeProvider from '../components/AppThemeProvider'
import NavBar from './NavBar'
import Footer from './Footer'

export default function RootLayout({
	children,
}: {
	children: React.ReactNode
}) {
	return (
		<html lang="en">
			{/*
        <head /> will contain the components returned by the nearest parent
        head.tsx. Find out more at https://beta.nextjs.org/docs/api-reference/file-conventions/head
      */}
			<head />
			<body className="flex min-h-screen flex-col bg-gray-50 dark:bg-gray-900">
				<AppThemeProvider>
					<NavBar />
					<div className="container mx-auto flex h-full w-full grow flex-col items-center px-4">
						{children}
					</div>
					<Footer />
				</AppThemeProvider>
			</body>
		</html>
	)
}
