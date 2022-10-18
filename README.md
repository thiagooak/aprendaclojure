# Vercel CLJS

## Running in Development

`npm install`

`npm run dev`

## Deploy to Vercel

* Create an account at https://vercel.com/
* Push your code to a github repo
* Create a new Vercel project with the following deploy configuration
  * Framework Preset: `Other`
  * Build Command: `npm run build`
  * Output Directory: `public`
  * Install Command: `npm install`
  * Development Command: None

