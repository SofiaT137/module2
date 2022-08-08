export const getTagsNames = (tags) => {
    let finalString = "";
    tags.forEach((tag) => {
      finalString += tag.name + " ";
    });
    return finalString;
  };