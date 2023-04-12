$(document).ready(() => {
  let urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get('id');
  fetch(`/porder/GetPorderDetailsByID?id=${id}`, {
    method: 'GET',
  }).then(resp => resp.json())
    .then(data => {
      let imagesrc = `data:image/png;base64,${data.products[0].pimgts[0].pimg}`;
      const confirmeBody = document.querySelector('.booking-confirmed-bottom');
      confirmeBody.innerHTML = '';
      let detailslist = '';
      for (let i = 0; i < data.products.length; i++) {
        detailslist += `<div><span>${data.products[i].pname} x ${data.pdetails[i].porderqty}</span></div>`;
      }
      const detailstotal = `<div class="booking-confirmed-bottom-bg p_30">
                              <div class="event-order-dt">
                                <div class="event-thumbnail-img">
                                  <img src="${imagesrc}" alt="">
                                </div>
                                <div class="event-order-dt-content">
                                  <h5>訂單編號 : ${data.porderno}</h5>
                                    ${detailslist}
                                  <div class="booking-total-grand">
                                    總金額 : <span>$${data.pchecktotal}</span>
                                  </div>
                                </div>
                              </div>
                              <a href="front-product-order_detail.html?id=${data.porderno}" class="main-btn btn-hover h_50 w-100 mt-5"><i
                                  class="fa-solid fa-ticket rotate-icon me-3"></i>檢視訂單</a>
                            </div>`;
      confirmeBody.innerHTML += detailstotal;
    });
});