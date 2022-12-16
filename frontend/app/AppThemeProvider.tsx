'use client'
import { ThemeProvider } from 'next-themes'

export default function AppThemeProvider({
	children,
}: {
	children: React.ReactNode
}) {
	return <ThemeProvider attribute="class">{children}</ThemeProvider>
}
