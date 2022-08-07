const container = document.querySelector(".container");
const imageUrl = "./../images/cat1.jpg";
let URL1;
const mainURL =
  "http://localhost:8085/module2/gift_certificates/filter/?sortByCreationDate=desc&pageSize=10&pageNumber=";
let count = 0;
let storage = [];

start();

function drawScreen(URL) {
  if (typeof URL !== "undefined") {
    URL1 = URL;
    count = 0;
  }
  var totalPages1;
  fetch(URL1 + count)
    .then((response) => response.json())
    .then((data) => {
      let massive = JSON.parse(localStorage.getItem("data"));
      massive.push(data);
      localStorage.setItem("data", JSON.stringify(massive));
      loadCards(data);
      totalPages1 = data.totalPages;
      if (count >= totalPages1) {
        count = 0;
      }
    });
  count += 1;
  localStorage.setItem("count", count);
}

function createCard([img, couponName, tags, expires, price]) {
  let code = ` <div class="card">
                  <img class="cardImage" src = "${img}">
                  <div class="addToFavorite">
                      <h3 class="couponName">${couponName}</h3>
                      <button type="submit" class="addToFavorite">
                          <span class="material-icons">
                              favorite i98
                          </span>
                      </button>
                  </div>
                  <div class="descriptionExp">
                      <p class="linkBox">${getTagNames(tags)}</p>
                      <p class="expires">expires in ${expires} days</p>
                  </div>
                  <hr>
                  <div class="price">
                      <h4>$${price}</h4>
                      <button type="submit" class="addToCart">Add to cart</button>
                  </div>
              </div>
    `;
  container.innerHTML += code;
}

function loadCards(data1) {
  for (let i = 0; i < 10; i++) {
    let name = data1.content[i].giftCertificateName;
    let tags = data1.content[i].tags;
    let price = data1.content[i].price;
    createCard([imageUrl, name, tags, 10, price]);
  }
}

function getTagNames(tags) {
  var result = new Array(3);
  if (tags.length !== 0) {
    for (let i = 0; i < 3; i++) {
      var link = tags[i].name;
      result[i] = '<a class = "links">' + link + "</a>";
    }
  }
  return result;
}

function debounce(func, timeout = 300) {
  let timer;
  return (...args) => {
    clearTimeout(timer);
    timer = setTimeout(() => {
      func.apply(this, args);
    }, timeout);
  };
}

function saveInput() {
  if (
    window.scrollY + window.innerHeight >=
    document.documentElement.scrollHeight
  )
    drawScreen();
}

const processChange = debounce(() => saveInput());

window.addEventListener("scroll", processChange);

function start() {
  if (
    localStorage.getItem("data") == null ||
    JSON.parse(localStorage.getItem("data")).length == 0
  ) {
    localStorage.setItem("data", JSON.stringify(storage));
    localStorage.setItem("count", 0);
    drawScreen(mainURL);
  } else {
    let count1 = localStorage.getItem("count");
    count = parseInt(count1);
    URL1 = mainURL;
    let massive1 = JSON.parse(localStorage.getItem("data"));
    massive1.forEach((element) => {
      loadCards(element);
    });
  }
}

function deleteItems() {
  var deleteElement = container.querySelectorAll(".card");
  for (let i = 0; i < deleteElement.length; i++) {
    deleteElement[i].remove();
  }
}
