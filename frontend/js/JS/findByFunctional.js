const searchForm = document.querySelector(".searchForm");
let pageNumber = 2;

searchForm.addEventListener("submit", (e) => {
  e.preventDefault();

  window.removeEventListener("scroll", processChange);

  const formData = new FormData(searchForm);
  const name = formData.get("name");
  const searchBy = formData.get("searchBy");
  let changedURL;

  if (searchBy === "name") {
    changedURL =
      "http://localhost:8085/module2/gift_certificates/filter/?sortByCreationDate=desc&pageSize=10" +
      "&partName=" +
      name +
      "&pageNumber=";
  } else if (searchBy === "description") {
    changedURL =
      "http://localhost:8085/module2/gift_certificates/filter/?sortByCreationDate=desc&pageSize=10" +
      "&partDescription=" +
      name +
      "&pageNumber=";
  } else if (searchBy === "tag") {
    changedURL =
      "http://localhost:8085/module2/gift_certificates/filter/?sortByCreationDate=desc&pageSize=10" +
      "&tagName=" +
      name +
      "&pageNumber=";
  }
  deleteItems();
  localStorage.setItem("data", JSON.stringify([]));
  localStorage.setItem("count", 0);
  localStorage.setItem("scrollPosition", 0);
  getTotalPages(changedURL+0)
  drawScreen1(getAllGiftCertificates(changedURL));
});

function getTotalPages(changedURL) {
  fetch(changedURL)
    .then((response) => response.json())
    .then((data) => {
      pageNumber = data.totalPages;
    });
}

const getAllGiftCertificates = async function (changedURL) {
  let temp1 = [];
  for (let i = 0; i < pageNumber; i++) {
    const response = await fetch(changedURL + i);
    const data = await response.json();
    temp1[i] = data;
  }
  return temp1;
};

function drawScreen1(func) {
  func.then((res) =>
    res.forEach((element) => {
      loadCards(element);
    })
  );
}
