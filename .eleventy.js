module.exports = function (eleventyConfig) {
  eleventyConfig.addPassthroughCopy({"assets": "/"});

  eleventyConfig.addCollection("notes", function (collection) {
    return collection.getFilteredByGlob("pages/notes/**/*.md").sort((a, b) => {
      if (a.data.title > b.data.title) return 1;
      else if (a.data.title < b.data.title) return -1;
      else return 0;
    });
  });

  return {
    dir: {
      input: "pages"
    },
    layout: "pages/_includes/base.njk",

  }
};