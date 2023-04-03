$(document).ready(() => {
  let cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
  const itemlist = document.querySelector('tbody');
  itemlist.innerHTML = "";
  if (cartItems.length !== 0) {
    let index = 0;
    let totalPay = 0;
    const itembody = cartItems.map(item => {
      totalPay += (item.data.pspecialprice * item.quantity);
      return `<tr>
                <td>${++index}</td>
                <td><a href="front-product-product_detail.html?productno=${item.data.productno}" target="_blank">${item.data.pname}</a></td>
                <td class="number-cell">
                  <span class="number-top">$<s>${item.data.pprice}</s></span>
                  <span class="number-bottom">$${item.data.pspecialprice}</span>
                </td>
                <td><input  style="width: 25%;" class="form-control h_50" type="number" value="${item.quantity}" min="1" max="${item.data.pqty}"></td>
                <td>$${item.data.pspecialprice * item.quantity}</td>
              </tr>`
    }).join('');
    const coupon = `<tr>
                      <td colspan="3">
                        <label class="form-label">優惠券代碼</label>
                        <div class="position-relative">
                          <input class="form-control h_50" type="text" placeholder="Code" value="">
                          <button class="apply-btn btn-hover" type="button">使用</button>
                        </div>
                      </td>
                      <td colspan="4">
                        <div class="user_dt_trans text-end pe-xl-4">
                          <div class="totalinv2">總金額 : TWD $${totalPay}</div>
                        </div>
                      </td>
                    </tr>`;
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
      tr.querySelector('td:last-child').innerText = `$${item.data.pspecialprice * item.quantity}`;
    });
    itemlist.querySelector('.totalinv2').innerText = `總金額 : TWD $${totalPay}`;
  });
})