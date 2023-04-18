// 定義會員編號
let memberno;

$(document).ready(async () => {
  const response = await fetch('member/getMemberInfo', {
    method: 'GET',
  });
  const data = await response.json();
  memberno = data.memberno;
  if(memberno){
    fetchPorderList(`/porder/getordersbyid?id=${memberno}`);
    fetchPcouponHoldingList(`/pcouponholding/byMemberno?memberno=${memberno}`);
    fetchPcouponList(`/pcoupon/list`);
  } else {
    // 請前往登入
    window.location.href = 'front-users-mem-sign-in.html';
  }
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
            let imagesrc = '';
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
                    <span>
                    <a onclick="cancelPorder(${obj.porderno})">取消訂單</a>
                    </span>
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
          body: JSON.stringify({ porderno: orderno, pprocessstatus: 4 }),
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

function fetchPcouponHoldingList(e) {
  fetch(e, {
    method: 'GET',
  }).then(response => response.json())
    .then(data => {
      data.reverse();
      const followers = document.querySelector('#coupons-01');
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

function fetchPcouponList(e) {
	fetch(e, {
		method: 'GET',
	}).then(response => response.json())
		.then(data => {
			data.reverse();
			const couponlist = document.querySelector('#coupons-02');
			couponlist.innerHTML = "";
			const couponbody = data.map(obj => {
				return `<div class="main-card mt-4">
                  <div class="contact-list coupon-active">
                    <div class="top d-flex flex-wrap justify-content-between align-items-center p-4 border_bottom">
                      <div class="icon-box">
                        <span class="icon-big rotate-icon icon icon-purple">
                          <i class="fa-solid fa-ticket"></i>
                        </span>
                        <h5 class="font-18 mb-1 mt-1 f-weight-medium">${obj.pcouponno}<span class="font-weight-normal"> -
                        ${obj.pcouponname}</span></h5>
                        <p class="text-gray-50 m-0"><span class="visitor-date-time">${moment(obj.pcoupnsdate).format('YYYY-MM-DD HH:mm:ss')}
                          </span> - <span class="visitor-date-time">${moment(obj.pcoupnedate).format('YYYY-MM-DD HH:mm:ss')}</span></p>
                      </div>
                      <div class="d-flex align-items-center">
                        <div class="rs ms-auto mt_r4">
                        <button class="main-btn btn-hover h_40 w-100" onclick="takeCoupon(${memberno},${obj.pcouponno})">領取優惠券</button>
                        </div>
                      </div>
                    </div>
                    <div class="bottom d-flex flex-wrap justify-content-between align-items-center p-4">
                      <div class="icon-box ">
                        <span class="icon">
                          <i class="fa-regular fa-circle-dot"></i>
                        </span>
                        <p>狀態</p>
                        <h6 class="coupon-status">${obj.pcouponstatus === 1 ? '可使用' : '不可使用'}</h6>
                      </div>
                      <div class="icon-box">
                        <span class="icon">
                          <i class="fa-solid fa-tag"></i>
                        </span>
                        <p>適用商品編號</p>
                        <h6 class="coupon-status">${obj.pfitcoupons && obj.pfitcoupons[0] && obj.pfitcoupons[0].pfitcouponNo.productno}</h6>
                      </div>
                      <div class="icon-box">
                        <span class="icon">
                          <i class="fa-solid fa-money-bill"></i>
                        </span>
                        <p>消費金額</p>
                        <h6 class="coupon-status">${obj.preachprice}</h6>
                      </div>
                      <div class="icon-box">
                        <span class="icon">
                          <i class="fa-solid fa-money-bill"></i>
                        </span>
                        <p>折扣</p>
                        <h6 class="coupon-status">${obj.pdiscount}</h6>
                      </div>
                    </div>
                  </div>
                </div>`;
			}).join('');
			couponlist.innerHTML += couponbody;
		})
}

function takeCoupon(memberno,pcouponno){
  fetch(`/pcouponholding/take?memberno=${memberno}&pcouponno=${pcouponno}`,{
      method: 'GET',
    }).then(result => result.json())
      .then(result =>{
      if (result == true){
        swal("領取成功", {
          buttons: false,
          timer: 500,
        });
      } else {
        swal("您已經領取過!!!", {
          background: "blue",
          buttons: false,
          timer: 500,
        });
      };
    });
}