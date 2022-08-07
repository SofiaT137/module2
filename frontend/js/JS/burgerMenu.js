const headerBurger = document.querySelector(".headerBurger");

if (headerBurger) {
const burgerMenu = document.querySelector(".burgerMenu");

headerBurger.addEventListener("click", () => {
    document.body.classList.toggle("lock");
    burgerMenu.classList.toggle("active");
    headerBurger.classList.toggle("active");
});
}
