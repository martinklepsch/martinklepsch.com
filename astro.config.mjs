// @ts-check
import { defineConfig } from 'astro/config';

export default defineConfig({
  site: 'https://www.martinklepsch.com',
  build: {
    // The old Boot pipeline inlined CSS and JS into index.html; keep that.
    inlineStylesheets: 'always',
  },
});
