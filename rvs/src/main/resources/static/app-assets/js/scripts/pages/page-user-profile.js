/*=========================================================================================
    File Name: page-user-profile.js
    Description: User Profile jQuery Intialization
    --------------------------------------------------------------------------------------
    Item Name: Frest HTML Admin Template
    Version: 1.0
    Author: INFOTRACKIN
    Author URL: http://www.infotrackin.com
==========================================================================================*/

$(document).ready(function () {

  //nav-tabs feed heart color change
  $(".user-profile-like").click(function () {
    $(this).toggleClass("bx-heart bxs-heart").toggleClass("text-danger");
  });

  // stories swipper
  var swiperLength = $(".swiper-slide").length;
  if (swiperLength) {
    swiperLength = Math.floor(swiperLength / 2)
  }
  var mySwiperStories = new Swiper('.user-profile-stories', {
    slidesPerView: 'auto',
    initialSlide: swiperLength,
    centeredSlides: true,
    spaceBetween: 15,
  });
});
