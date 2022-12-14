import './globals.css'

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
      <body>
        <nav className="h-12 md:h-16 bg-white px-2 sm:px-4 py-2.5 dark:bg-gray-900 fixed w-full z-20 top-0 left-0">
          <div className="container flex flex-wrap items-center justify-between mx-auto">
          <a href="/" className="flex items-center">
              <img src="https://flowbite.com/docs/images/logo.svg" className="h-6 mr-3 sm:h-9" alt="Flowbite Logo" />
              <span className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">Setlist to Playlist</span>
          </a>
          </div>
        </nav>
      
        <div className="mt-12 md:mt-16 max-w-6xl mx-auto px-4 flex flex-wrap items-center">
            {children}
        </div>
      </body>
    </html>
  )
}
