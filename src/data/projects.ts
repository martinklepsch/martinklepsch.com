export interface Project {
  slug: string;
  title: string;
  years: string;
  desc: string;
  /** Tailwind background classes, shared by the card and the detail page. */
  bg: string;
  /** Browser UI color (`theme-color`) while the project is open. */
  theme: string;
}

export const PROJECTS: Project[] = [
  {
    slug: 'ctxs',
    title: 'ctxs context registry',
    years: '2025',
    desc: 'An open-source registry for LLM context windows, prompts, and other content. I built ctxs.ai to help developers share and discover effective contexts for enhancing AI assistant performance.',
    bg: 'bg-linear-to-br from-indigo-900 to-indigo-800',
    theme: 'rgb(49 46 129)',
  },
  {
    slug: 'gatheround',
    title: 'Gatheround',
    years: '2019-2024',
    desc: 'I joined Gatheround as their first engineering hire and helped build large parts of the product. We raised a Series A and built a unique audio and video platform for human connection.',
    bg: 'bg-[#103523]',
    theme: '#103523',
  },
  {
    slug: 'dynogee',
    title: 'Dynogee',
    years: '2024',
    desc: 'At the start of 2024 I set out to ship a software product end-to-end. Dynogee is a creative automation suite for brand design teams integrating with Figma.',
    bg: 'bg-linear-to-br from-slate-900 to-slate-800',
    theme: 'rgb(15 23 42)',
  },
  {
    slug: 'cljdoc',
    title: 'cljdoc',
    years: '2019',
    desc: 'I conceived and built a fully automated API documentation platform for the entire Clojure/Script language ecosystem.',
    bg: 'bg-[#252a89]',
    theme: '#252a89',
  },
];
