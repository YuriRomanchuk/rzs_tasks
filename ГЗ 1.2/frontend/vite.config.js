import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// Dev-сервер Vite на http://localhost:5173.
// Проксі: усі запити на /api та /images йдуть у бекенд (http://localhost:8080),
// тож у коді UI можна писати відносні шляхи ('/api/products') без CORS-клопотів.
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/api': 'http://localhost:8080',
      '/images': 'http://localhost:8080',
    },
  },
})
