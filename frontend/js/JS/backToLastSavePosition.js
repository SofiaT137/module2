document.addEventListener('DOMContentLoaded', function(event) {
var scrollPosition = localStorage.getItem("scrollPosition");
  if(scrollPosition){
    window.scrollTo(0, scrollPosition);
  }
});

window.onscroll = function (e) {
    localStorage.setItem("scrollPosition", window.scrollY);
}





