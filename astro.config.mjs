// @ts-check
import { defineConfig, fontProviders } from 'astro/config';
import tailwindcss from '@tailwindcss/vite';

export default defineConfig({
  site: 'https://martinklepsch.com',
  // Emit ctxs.html rather than ctxs/index.html so Pages serves /ctxs without a redirect.
  build: {
    format: 'file',
  },
  fonts: [
    {
      provider: fontProviders.fontsource(),
      name: 'Inter',
      cssVariable: '--font-inter',
      weights: ['400 700'],
      styles: ['normal', 'italic'],
      subsets: ['latin'],
      fallbacks: ['system-ui', 'Avenir', 'Helvetica', 'Arial', 'sans-serif'],
    },
  ],
  vite: {
    plugins: [tailwindcss()],
  },
});
