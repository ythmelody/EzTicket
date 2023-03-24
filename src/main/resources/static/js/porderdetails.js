
$(document).ready(() => {
  let urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get('id');
  fetch(`/html/GetPorderDetailsByID?id=${id}`, {
    method: 'GET',
  }).then(resp => resp.json())
    .then(data => {
      document.querySelectorAll('.vdt-list')[0].textContent = "訂單編號 : " + data.porderno;
      document.querySelectorAll('.vdt-list')[1].textContent = "訂單成立時間 : " + data.porderdate;
      document.querySelectorAll('.vdt-list')[2].textContent = "訂單付款時間 : " + data.ppaydate;
      const detailsbody = document.querySelector('tbody');
      detailsbody.innerHTML = '';
      let total = 0;
      for (let i = 0; i < data.products.length; i++) {
        const detailslist = `<tr>
                <td>${i + 1}</td>
                <td><a href="online_event_detail_view.html?id=${data.products[i].productno}" target="_blank">${data.products[i].pname}</a></td>
                <td>${data.pdetails[i].porderqty}</td>
                <td>$${data.products[i].pprice}</td>
                <td>$${data.products[i].pprice - data.pdetails[i].pprice}</td>
                <td>$${data.pdetails[i].pprice}</td>
                <td>
                  <div style="text-align:center">
                    <button style="border: none;  background-color:transparent" data-bs-toggle="modal"
                      data-bs-target="#couponModal"><img src="images/cmtbtn.png"></button>
                  </div>
                </td>
                </tr>`;
        total += data.pdetails[i].pprice;
        detailsbody.innerHTML += detailslist;
      }
      const detailstotal = `<tr>
                              <td colspan="1"></td>
                              <td colspan="5">
                                <div class="user_dt_trans text-end pe-xl-4">
                                  <div class="totalinv2">總金額 : TWD $${total}</div>
                                  <p>通過信用卡支付</p>
                                </div>
                              </td>
                            </tr>`;
      detailsbody.innerHTML += detailstotal;
    });
});
