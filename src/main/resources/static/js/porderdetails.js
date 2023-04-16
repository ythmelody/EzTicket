
$(document).ready(() => {
  let urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get('id');
  let couponCount = 0;
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
      couponCount = data.pcoupontotal;
      for (let i = 0; i < data.products.length; i++) {
        //判斷是否已評論過
        let imagesrc;
        if (data.products[i].pimgts && data.products[i].pimgts[0]) {
          imagesrc = `data:image/png;base64,${data.products[i].pimgts[0].pimg}`;
        }
        if (data.pdetails[i].pcommentstatus === 0) {
          const detailslist = `<tr>
                              <td>${i + 1}</td>
                              <td><img src="${imagesrc}" width="100" height="100" alt=""></td>
                              <td><a href="front-product-product_detail.html?productno=${data.products[i].productno}" target="_blank">${data.products[i].pname}</a></td>
                              <td>${data.pdetails[i].porderqty}</td>
                              <td><s>$${data.products[i].pprice}</s></td>
                              <td>$${data.products[i].pspecialprice}</td>
                              <td>$${data.pdetails[i].pprice}</td>
                              <td>
                              <div style="text-align:center" id="commentContainer">
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
                            <td><img src="${imagesrc}" width="100" height="100" alt=""></td>
                            <td><a href="front-product-product_detail.html?productno=${data.products[i].productno}" target="_blank">${data.products[i].pname}</a></td>
                            <td>${data.pdetails[i].porderqty}</td>
                            <td>$<s>${data.products[i].pprice}</s></td>
                            <td>$${data.products[i].pspecialprice}</td>
                            <td>$${data.pdetails[i].pprice}</td>
                            <td> <div style="text-align:center" id="commentContainer">
                            <button style="border: none;  background-color:transparent" data-bs-toggle="modal"
                            data-bs-target="#couponModal_update" onclick="showcomment(${data.pdetails[i].pcommentstatus})">查看評論</button></td>
       </tr>`;
          total += data.pdetails[i].pprice;
          detailsbody.innerHTML += detailslist;
        }
      }
      let delivery = +total > 499 ? 0 : 100;
      const detailstotal = `<tr>
                              <td colspan="1"></td>
                              <td colspan="5">
                                  <div class="user_dt_trans text-end pe-xl-4">
                                  <div class="pdiscount-fee">優惠券折扣 : -$<span>${couponCount}</span></div>
                                  <div class="delivery-fee">運費(滿499免運) : $<span>${delivery}</span></div>
                                  <div class="product-fee">商品金額 : $<span>${total}</span></div>
                                  <div class="totalinv2">結帳金額 : $<span>${total + delivery - couponCount}</span></div>
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

// 點擊單一評論並顯示
function showcomment(commentno) {
  fetch('ProductCommentServlet', {
    method: 'POST',
    body: new URLSearchParams({
      'pcommentno': commentno,
      'action': "getOneproductComment"
    })
  }).then(resp => resp.json())
    .then(item => {
      console.log(item);
      //把前面有的值先清空避免吃到舊值
      $('#pcommentno').val("");
      $('#ccontent').text("");
      $('#title_oldRate').html("");

      $('#pcommentno').val(item.pcommentno);
      $('#title_oldRate').html("原始商品評星");
      for (let star = 0; star < item.prate; star++) {
        $('#title_oldRate').append(`&nbsp;<i class="fa-solid fa-star" style="color: #ffad21!important;"></i>`);
      }

      $('#pcommentcont').text(item.pcommentcont);

      if (item.pcommentstatus === -1) {
        $("#cstatus>option[value='-1']").prop("selected", true);
      } else if (item.pcommentstatus === 0) {
        $("#cstatus>option[value='0']").prop("selected", true);
      } else {
        $("#cstatus>option[value='1']").prop("selected", true);

      }
    })
  return commentno;
}

// 修改單一評論狀態
function confirm_update() {
  fetch('ProductCommentServlet', {
    method: 'POST',
    body: new URLSearchParams({
      'pcommentno': $('#pcommentno').val(),
      'prate': $("#prate").val(),
      'pcommentcont': $('#pcommentcont').val(),
      'action': "updateOneproductComment"
    })
  }).then((resp) => resp.json())
    .then((item) => {
      console.log(item);
      if (item) {
        swal({
          title: "更新成功",
          icon: "success",
          closeOnClickOutside: true,
        }).then(() => {
          window.location.reload();
        })
      } else {
        swal({
          title: "更新失敗",
          icon: "error",
          closeOnClickOutside: true,
        });
      }
    })
}