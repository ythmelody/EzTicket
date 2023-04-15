
$(document).ready(() => {
  let urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get('id');
  fetch(`/porder/GetPorderDetailsByID?id=${id}`, {
    method: 'GET',
  }).then(resp => resp.json())
    .then(data => {
      console.log(data);
      document.querySelectorAll('.vdt-list')[0].textContent = "訂單編號 : " + data.porderno;
      document.querySelectorAll('.vdt-list')[1].textContent = "訂單成立時間 : " + (moment(data.porderdate).format('YYYY-MM-DD HH:mm:ss'));
      document.querySelectorAll('.vdt-list')[2].textContent = "訂單付款時間 : " + (moment(data.ppaydate).format('YYYY-MM-DD HH:mm:ss'));
      const detailsbody = document.querySelector('tbody');
      detailsbody.innerHTML = '';
      let total = 0;
      for (let i = 0; i < data.products.length; i++) {
        //判斷是否已評論過
        if (data.pdetails[i].pcommentstatus === 0) {
          const detailslist = `<tr>
                              <td>${i + 1}</td>
                              <td><a href="front-product-product_detail.html?productno=${data.products[i].productno}" target="_blank">${data.products[i].pname}</a></td>
                              <td>${data.pdetails[i].porderqty}</td>
                              <td>$${data.products[i].pprice}</td>
                              <td>$${data.products[i].pprice - data.products[i].pspecialprice}</td>
                              <td>$${data.pdetails[i].pprice}</td>
                              <td>
                              <div style="text-align:center">
                              <button style="border: none;  background-color:transparent" data-bs-toggle="modal"
                              data-bs-target="#couponModal" onclick="renewModal(${data.porderno},${data.products[i].productno})" id="commentButton"><img src="images/cmtbtn.png"></button>
                              </div>
                              </td>
                             </tr>`;
          total += data.pdetails[i].pprice;
          detailsbody.innerHTML += detailslist;
        } else {
          const detailslist = `<tr>
                            <td>${i + 1}</td>
                            <td><a href="front-product-product_detail.html?productno=${data.products[i].productno}" target="_blank">${data.products[i].pname}</a></td>
                            <td>${data.pdetails[i].porderqty}</td>
                            <td>$${data.products[i].pprice}</td>
                            <td>$${data.products[i].pprice - data.products[i].pspecialprice}</td>
                            <td>$${data.pdetails[i].pprice}</td>
                            <td><div style="text-align:center"><a href="front-product-product_detail.html?productno=${data.products[i].productno}" target="_blank">已評論</a></div></td>
       </tr>`;
          total += data.pdetails[i].pprice;
          detailsbody.innerHTML += detailslist;
        }
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

//取得單筆明細資訊(porderno-productno)
function renewModal(porderno, productno) {
  let urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get('id');
  fetch(`/pdetails/byPorderno?porderno=${porderno}&productno=${productno}`, {
    method: 'GET',
  }).then(resp => resp.json())
    .then(item => {
      console.log(item);
      // console.log(item);

      if (item.pcommentstatus === 1) {
        $("#commentButton").html("已評論");
      } else {
        $("#productno").val('');
        $("#porderno").val('');
        $("#pcommentstatus").val('');

        $("#productno").val(item.pdetailsNo.productno);
        $("#porderno").val(item.pdetailsNo.porderno);
        $("#pcommentstatus").val(item.pcommentstatus);
      }
    })
}
