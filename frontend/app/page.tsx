import Image from 'next/image'

export default function Home() {
  return (
    <div className="">
      <div className="mt-4 lg:mt-12 text-center md:mx-32 mb-8 md:mb-20">
        <h1 className="mb-2 lg:mb-4 text-4xl font-extrabold text-gray-900 dark:text-white md:text-5xl lg:text-7xl">
          Create your new
        </h1>
        <h1 className="mb-6 md:mb-10 text-4xl font-extrabold text-gray-900 dark:text-white md:text-5xl lg:text-7xl">
          <span className="text-transparent bg-clip-text bg-gradient-to-r to-emerald-600 from-sky-400">favourite playlist</span>.
        </h1>
        <p className="mb-6 text-md font-normal text-gray-500 md:text-lg lg:text-xl dark:text-gray-400">
          Get ready for the upcoming concerts of your favourite artists, so you can practice your best singing skills and enjoy the experience to the fullest.
          </p>
      </div>
      <form className="flex items-center max-w-3xl mx-auto">   
          <label htmlFor="simple-search" className="sr-only">Search artist</label>
          <div className="relative w-full">
              <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                  <svg aria-hidden="true" className="w-6 h-6 text-gray-500 dark:text-gray-400" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>
              </div>
              <input type="text" id="simple-search" className="bg-gray-50 border border-gray-300 text-gray-900 text-md rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Search artist" required />
          </div>
          <button type="submit" className="p-2.5 ml-2 text-sm font-medium text-white bg-blue-700 rounded-lg border border-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
              <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg>
              <span className="sr-only">Search artist</span>
          </button>
      </form>

      
    </div>
  )
}
