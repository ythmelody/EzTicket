// 定義會員編號
let memberno = "85345";

$(document).ready(() => {
  fetchPorderList(`/porder/getordersbyid?id=${memberno}`);
});

function fetchPorderList(e) {
  fetch(e, {
    method: 'GET',
  }).then(response => response.json())
    .then(data => {
      data.reverse();
      const ordersbody = document.querySelector('#orders');
      ordersbody.innerHTML = "";
      const orders = data.map(obj => {
        return fetch(`/porder/GetPorderDetailsByID?id=${obj.porderno}`, {
          method: 'GET',
        }).then(resp => resp.json())
          .then(data => {
            let imagesrc;
            if (data.products[0].pimgts && data.products[0].pimgts[0]) {
              imagesrc = `data:image/png;base64,${data.products[0].pimgts[0].pimg}`;
            }
            let detailslist = '';
            for (let i = 0; i < data.products.length; i++) {
              detailslist += `<div><span>${data.products[i].pname} x ${data.pdetails[i].porderqty}</span></div>`;
            }
            switch (obj.pprocessstatus) {
              case 1:
                processstatus = '<span class="status-circle yellow-circle"></span>已出貨';
                break;
              case 2:
                processstatus = '<span class="status-circle green-circle"></span>已結案';
                break;
              case 3:
                processstatus = '<span class="status-circle red-circle"></span>已取消';
                break;
              case 0:
                processstatus = '<span class="status-circle blue-circle"></span>未處理';
                break;
            }
            const orderHtml = `<div class="main-card mt-4">
              <div class="card-top p-4">
                <div class="card-event-img">
                  <img src="${imagesrc}" alt="">
                </div>
                <div class="card-event-dt">
                  <h5>訂單編號 : ${obj.porderno}</h5>
                  <div class="invoice-id">
                    ${detailslist}
                  </div>
                </div>
              </div>
              <div class="card-bottom">
                <div class="card-bottom-item">
                  <div class="card-icon">
                    <i class="fa-solid fa-calendar-days"></i>
                  </div>
                  <div class="card-dt-text">
                    <h6>訂單日期</h6>
                    <span>${obj.porderdate}</span>
                  </div>
                </div>
                <div class="card-bottom-item">
                  <div class="card-icon">
                    <i class="fa-solid fa-ticket"></i>
                  </div>
                  <div class="card-dt-text">
                    <h6>訂單狀態</h6>
                    <span>${processstatus}</span>
                    <a onclick="cancelPorder(${obj.porderno})">取消訂單</a>
                  </div>
                </div>
                <div class="card-bottom-item">
                  <div class="card-icon">
                    <i class="fa-solid fa-money-bill"></i>
                  </div>
                  <div class="card-dt-text">
                    <h6>總金額</h6>
                    <span>TWD $${obj.pchecktotal}</span>
                  </div>
                </div>
                <div class="card-bottom-item">
                  <div class="card-icon">
                    <i class="fa-solid fa-money-bill"></i>
                  </div>
                  <div class="card-dt-text">
                    <h6>訂單詳情</h6>
                    <a href="front-product-order_detail.html?id=${obj.porderno}">明細</a>
                  </div>
                </div>
              </div>
            </div>`;
            return orderHtml;
          });
      });
      Promise.all(orders).then(values => {
        ordersbody.innerHTML = values.join('');
      });
    });
}

function cancelPorder(orderno) {
  if (orderno) {
    swal({
      title: "請確認是否取消訂單?",
      icon: "warning",
      buttons: true,
      dangerMode: true
    }).then((confirm) => {
      if (confirm) {
        fetch(`/porder/updatestatusbyid`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ porderno: orderno, pprocessstatus: 3 }),
        }).then(response => {
          if (response.ok) {
            swal('訂單已取消', { icon: "success" });
          } else {
            swal('訂單取消失敗', { icon: "error" });
          }
        });
      } else {
        return Promise.reject('取消操作');
      }
    });
  }
}


$(document).ready(() => {
  fetchPcouponHoldingList(`/pcouponholding/byMemberno?memberno=${memberno}`);
});

function fetchPcouponHoldingList(e) {
  fetch(e, {
    method: 'GET',
  }).then(response => response.json())
    .then(data => {
      const holdingCount1 = document.querySelector('#holding');
      const holdingCount2 = document.querySelector('#couponcount');
      holdingCount1.textContent = '(' + data.length + ')';
      holdingCount2.textContent = data.length;
      const followers = document.querySelector('#followers-empty-state');
      followers.innerHTML = '';
      const couponHolding = data.map(obj => {
        return fetch(`/pcoupon?id=${obj.pcouponholdingPK.pcouponno}`, {
          method: 'GET',
        }).then(resp => resp.json())
          .then(data => {
            const couponHoldingHtml = `
          <div class="main-card mt-4">
            <div class="contact-list coupon-active">
              <div class="top d-flex flex-wrap justify-content-between align-items-center p-4 border_bottom">
                <div class="icon-box">
                  <span class="icon-big rotate-icon icon icon-purple">
                    <i class="fa-solid fa-ticket"></i>
                  </span>
                  <h5 class="font-18 mb-1 mt-1 f-weight-medium" vaule="${data.pcouponno}">${data.pcouponname}</h5>
                </div>
              </div>
              <div class="bottom d-flex flex-wrap justify-content-between align-items-center p-4">
                <div class="icon-box ">
                  <span class="icon">
                    <i class="fa-regular fa-circle-dot"></i>
                  </span>
                  <p>使用狀態</p>
                  <h6 class="coupon-status">${obj.pcouponstatus === 0 ? '未使用' : '已使用'}</h6>
                </div>
                <div class="icon-box ">
                <span class="icon">
                  <i class="fa-regular fa-circle-dot"></i>
                </span>
                <p>票卷狀態</p>
                <h6 class="coupon-status">${obj.pcoupon.pcouponstatus === 1 ? '可用' : '不可用'}</h6>
              </div>
                <div class="icon-box">
                  <span class="icon">
                    <i class="fa-solid fa-tag"></i>
                  </span>
                  <p>折扣</p>
                  <h6 class="coupon-status">${data.pdiscount}</h6>
                </div>
              </div>
            </div>
          </div>`;
            return couponHoldingHtml;
          });
      })
      Promise.all(couponHolding).then(values => {
        followers.innerHTML = values.join('');
      });
    });
}
