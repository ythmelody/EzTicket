// 定義會員編號
let memberno;
// 定義持有優惠券
let fitlist;


// $(document).ready(function() {
//   $('#twcode').twzipcode({
//   // 設定初始化時的縣市選項樣式
//   css: ['form-control', 'h_50'],
//   // 是否顯示縣市選項的下拉式選單
//   countySel: true,
//   // 是否顯示鄉鎮市區選項的下拉式選單
//   districtSel: true,
//   // 是否顯示郵遞區號的欄位
//   zipcodeSel: true,
//   // 設定縣市選項的預設值
//   countyName: '台北市',
//   // 設定鄉鎮市區選項的預設值
//   districtName: '中正區',
//   // 設定郵遞區號欄位的預設值
//   zipcode: '100',
//   // 設定選項欄位的名稱
//   countyName: 'county',
//   districtName: 'district',
//   zipcodeName: 'zipcode',
//   // 設定縣市選項的 class 名稱
//   countyClass: 'county',
//   // 設定鄉鎮市區選項的 class 名稱
//   districtClass: 'district',
//   // 設定郵遞區號欄位的 class 名稱
//   zipcodeClass: 'zipcode',
//   // 設定縣市選項的 placeholder
//   countyPlaceholder: '請選擇縣市',
//   // 設定鄉鎮市區選項的 placeholder
//   districtPlaceholder: '請選擇鄉鎮市區',
//   // 設定郵遞區號欄位的 placeholder
//   zipcodePlaceholder: '郵遞區號'
// });
// });

$(document).ready(async () => {
  const response = await fetch('member/getMemberInfo', {
    method: 'GET',
  });
  const data = await response.json();
  memberno = data.memberno;
  // $('#recipient').val(data.comrecipient);
  // $('#rephone').val(data.comrephone);
  // $('#email').val(data.memail);
  // $('#readdress1').val(data.memail);
  // $('#readdress2').val(data.comrecipient);
  // $('#readdress3').val(data.comrecipient);
  if(memberno){
    getcart();
  } else {
    // 請前往登入
    window.location.href = 'front-users-mem-sign-in.html';
  }
});

async function getcart() {
  let cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
  const itemlist = document.querySelector('tbody');
  itemlist.innerHTML = "";
  if(cartItems.length === 0 || !cartItems.length){
    const body = document.querySelector('.event-dt-block');
    body.innerHTML = "";
    body.innerHTML = `
    <div class="d-flex justify-content-center">
      <div class="col-lg-6 col-md-6">
        <h1 class="text-center">你的購物車還是空的!!</h1>
        <h3 class="text-center">對自己好一點!!</h3>
        <button class="main-btn btn-hover h_50 w-100 mt-5" onclick="location.href='front-product-explore_products.html'" type="button">
          立即前往購物
        </button>
      </div>
    </div>
  `;
  return;
  } else {
    let index = 0;
    let totalPay = 0;
    let imagesrc = '';
    const itembody = cartItems.map(item => {
      totalPay += (item.data.pspecialprice * item.quantity);
      imagesrc = `data:image/png;base64,${item.data.pimgts[0].pimg}`;
      return `<tr>
                <td>${++index}</td>
                <td><img src="${imagesrc}" width="100" height="100" alt=""></td>
                <td><a href="front-product-product_detail.html?productno=${item.data.productno}" target="_blank">${item.data.pname}</a></td>
                <td>
                  <span class="number-top">$<s>${item.data.pprice}</s></span>
                  <span class="number-bottom">$${item.data.pspecialprice}</span>
                </td>
                <td><input style="width: 25%;" class="form-control h_50" type="number" value="${item.quantity}" min="1" max="${item.data.pqty}"></td>
                <td>$${item.data.pspecialprice * item.quantity}</td>
                <td><a href="#" onClick="removeItem(${(index - 1)})">移除</a></td>
              </tr>`
    }).join('');
    const couponuse = await getpcouponlist(memberno);
    // 定義運費 滿499免運
    let delivery = totalPay > 499 ? 0 : 100;
    let pdiscount = 0;
    const coupon = `<tr>
                      <td colspan="3">
                        <label class="form-label">優惠券</label>
                        <div class="position-relative">
                          <select id="couponCode" class="form-select h_50" onchange="disCoupon()">
                            <option value="">請選擇</option>
                            ${couponuse}
                          </select>
                          <button class="apply-btn btn-hover" type="button" onClick="useCoupon()">使用</button>
                        </div>                        
                      </td>
                      <td colspan="4">
                        <div class="user_dt_trans text-end pe-xl-4">
                          <div class="pdiscount-fee">優惠券折扣 : -$<span>${pdiscount}</span></div>
                          <div class="delivery-fee">運費(滿499免運) : $<span>${delivery}</span></div>
                          <div class="product-fee">商品金額 : $<span>${totalPay}</span></div>
                          <div class="totalinv2">結帳金額 : $<span>${totalPay + delivery}</span></div>
                        </div>
                      </td>
                    </tr>`;
    const card = document.querySelector('#TWD');
    card.textContent = `應支付總金額 : $${totalPay}`;
    itemlist.innerHTML += itembody;
    itemlist.innerHTML += coupon;
  }
  // 監聽 input 數量更改事件
  $('input[type="number"]').on('change', function () {
    const idx = $(this).closest('tr').index();
    const qty = $(this).val();
    cartItems[idx].quantity = qty;
    localStorage.setItem('cartItems', JSON.stringify(cartItems));
    totalPay = 0;
    itemlist.querySelectorAll('tr:not(:last-child)').forEach(tr => {
      const idx = tr.querySelector('td:first-child').innerText - 1;
      const item = cartItems[idx];
      item.quantity = parseInt(tr.querySelector('input[type="number"]').value);
      totalPay += item.data.pspecialprice * item.quantity;
      tr.querySelector('td:nth-last-child(2)').innerText = `$${item.data.pspecialprice * item.quantity}`;
    });
    // 定義運費 滿499免運
    let delivery = totalPay > 499 ? 0 : 100;
    itemlist.querySelector('.product-fee span').innerText = totalPay;
    $('.delivery-fee span').text(delivery);
    $('.totalinv2 span').text(totalPay + delivery);
    $('#TWD').text(`應支付總金額 : $${totalPay + delivery}`);
    $('#couponCode').val('');
  });
}

function disCoupon() {
  const productpay = Number($('.product-fee span').text());
  const couponCode = $('#couponCode option:selected');
  const discount = +couponCode.data('discount');
  const minimum = +couponCode.data('minimum');
  let delivery = Number(productpay > 499 ? 0 : 100);
  if(productpay > minimum){
    $('.pdiscount-fee span').text(discount);
    $('.totalinv2 span').text(productpay + delivery - discount);
    $('#TWD').text(`應支付總金額 : $${productpay + delivery - discount}`);
  }
  else{
    $('.pdiscount-fee span').text(0);
    $('.totalinv2 span').text(productpay + delivery);
    $('#TWD').text(`應支付總金額 : $${productpay + delivery}`);
  }
}

function useCoupon(){
  // 获取下拉选项框
  const couponCode = $('#couponCode');
  // 点击按钮时切换下拉选项框的禁用状态
  if (couponCode.prop('disabled')) {
    couponCode.prop('disabled', false);
  } else {
    couponCode.prop('disabled', true);
  };
}

function addPorder() {
  let cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
  let ptotal = 0; let pdiscounttotal = 0;
  let products = [];
  for (item of cartItems) {
    ptotal += item.data.pprice * item.quantity;
    pdiscounttotal += item.data.pspecialprice * item.quantity;
    products.push({
      'productno': item.data.productno,
      'quantity': item.quantity,
      'pprice': item.data.pspecialprice * item.quantity
    });
  }
  let delivery = pdiscounttotal > 499 ? 0 : 100;
  let pcoupontotal = +$('.pdiscount-fee span').text();
  let pcouponno = $('#couponCode').val() ? +$('#couponCode').val() : null;  
  let pchecktotal = (pdiscounttotal - pcoupontotal + delivery);
  const porderbody = {
    'memberno': memberno,
    'ptotal': ptotal,
    'pdiscounttotal': pdiscounttotal,
    'pcoupontotal': pcoupontotal,
    'pchecktotal': pchecktotal,
    'pcouponno': pcouponno,
    'recipient': $("#recipient").val(),
    'rephone': $("#rephone").val(),
    'readdress': ($("#readdress1").val() + ',' + $("#readdress2").val() + $("#readdress3").val()),
    'orderProducts': products,
  }
  swal({
    title: "是否建立訂單?",
    icon: "warning",
    buttons: true,
    dangerMode: true
  }).then((confirm) => {
    if (confirm) {
      fetch('/porder/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(porderbody)
      }).then(response => {
        if (response.ok) {
          response.json().then(porderno => {
            swal({
              title: "建立成功",
              icon: "success",
              closeOnClickOutside: false,
            }).then(() => {
              localStorage.clear();
              window.location.href = `front-product-order_confirmed.html?id=${porderno}`;
            })
          });
        } else {
          swal({
            title: "建立失敗",
            icon: "error",
            closeOnClickOutside: false,
          });
        }
      });
    } else {
      return Promise.reject('取消操作');
    }
  });
}

function removeItem(e) {
  // 從 localStorage 取出 item 的值
  let itemData = JSON.parse(localStorage.getItem('cartItems'));
  if (!e) {
    // 若未傳入參數，則清除所有項目
    itemData = [];
  } else {
    // 刪除指定的項目
    itemData.splice(e, 1);
    // 更新索引
    itemData = itemData.map((item, index) => ({ ...item, index }));
  }
  // 將修改後的資料存回 localStorage
  localStorage.setItem('cartItems', JSON.stringify(itemData));
  // 重新載入頁面
  location.reload();
}

async function getpcouponlist(memberno) {
  return new Promise((resolve, reject) => {
    let cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
    const productlistno = cartItems.map(item => {
      return item.data.productno;
    })
    fetch(`/pcouponholding/byMemberno?memberno=${memberno}`, {
        method: 'GET',
      })
      .then(response => response.json())
      .then(data => {
        const fitpcouponlist = data.map(item => {
          for (no of productlistno) {
            if (no == item.pcoupon.pfitcoupons[0].pfitcouponNo.productno &&
              item.pcouponstatus == 0 && item.pcoupon.pcouponstatus == 1) {
                fitlist = item.pcoupon;
              return `<option value="${item.pcoupon.pcouponno}" data-discount="${item.pcoupon.pdiscount}" data-minimum="${item.pcoupon.preachprice}">
              ${item.pcoupon.pcouponname}(消費滿$${item.pcoupon.preachprice})(折扣$${item.pcoupon.pdiscount})
              </option>`;
            }
          }
        })
        resolve(fitpcouponlist);
      })
      .catch(error => {
        reject(error);
      });
  });
}