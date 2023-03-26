
$(document).ready(() => {
  fetchPcouponList(`/pcoupon/list`);
});

function fetchPcouponList(e) {
  fetch(e, {
    method: 'GET',
  }).then(response => response.json())
    .then(data => {
      data.reverse();
      const couponlist = document.querySelector('.all-promotion-list');
      couponlist.innerHTML = "";
      document.querySelector('.mb-4').textContent = "優惠券 (" + data.length + ")";
      const couponbody = data.map(obj => {

        return `<div class="main-card mt-4">
                  <div class="contact-list coupon-active">
                    <div class="top d-flex flex-wrap justify-content-between align-items-center p-4 border_bottom">
                      <div class="icon-box">
                        <span class="icon-big rotate-icon icon icon-purple">
                          <i class="fa-solid fa-ticket"></i>
                        </span>
                        <h5 class="font-18 mb-1 mt-1 f-weight-medium">優惠券代碼<span class="font-weight-normal"> - ${obj.pcouponno} -
                        ${obj.pcouponname}</span></h5>
                        <p class="text-gray-50 m-0"><span class="visitor-date-time">${moment(obj.pcoupnsdate).format('YYYY-MM-DD HH:mm:ss')}
                          </span> - <span class="visitor-date-time">${moment(obj.pcoupnedate).format('YYYY-MM-DD HH:mm:ss')}</span></p>
                      </div>
                      <div class="d-flex align-items-center">
                        <label class="btn-switch tfs-8 mb-0 me-4 mt-1">
                          <input type="checkbox" value="" checked>
                          <span class="checkbox-slider"></span>
                        </label>
                        <div class="dropdown dropdown-default dropdown-text dropdown-icon-item">
                          <button class="option-btn-1" type="button" data-bs-toggle="dropdown" aria-haspopup="true"
                            aria-expanded="false">
                            <i class="fa-solid fa-ellipsis-vertical"></i>
                          </button>
                          <div class="dropdown-menu dropdown-menu-right">
                            <a href="#" class="dropdown-item"><i class="fa-solid fa-pen me-3"></i>編輯</a>
                            <a href="#" class="dropdown-item"><i class="fa-solid fa-trash-can me-3"></i>刪除</a>
                          </div>
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
                        <h6 class="coupon-status">10001</h6>
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
