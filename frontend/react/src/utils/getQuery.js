export const getQuery = (input, itemPart) => {
    var tagString = "";
    var itemPartString = "";
    var array = input.split(" ");
    array.forEach((element) => {
      if (element.startsWith("#", 0)) {
        tagString += "&tagName=" + element.slice(1);
      } else {
        itemPartString += "&part"+ itemPart +"="+ element;
      }
    });
    let finalQuery =
      tagString === ""
        ? itemPartString
        : tagString + itemPartString;
    return finalQuery;
}