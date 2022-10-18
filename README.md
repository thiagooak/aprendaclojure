# Vercel CLJS

## Run in Development

* `npm install`

* `npm run build-css -- --watch` (in a separate terminal)

* `npm run dev`

* `open http://localhost:8080`

## Deploy to Vercel

* Create an account at https://vercel.com/
* Push your code to a Github repo
* Create a new Vercel project with the following deploy configuration:
  * Framework Preset: `Other`
  * Build Command: `npm run build`
  * Output Directory: `public`
  * Install Command: `npm install`
  * Development Command: None

