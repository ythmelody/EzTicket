// 定義會員編號
let memberno = "85345";

$(document).ready(() => {
  let cartItems = JSON.parse(localStorage.getItem("cartItems")) || [];
  const itemlist = document.querySelector("tbody");
  itemlist.innerHTML = "";
  if (cartItems.length !== 0) {
    let index = 0;
    let totalPay = 0;
    const itembody = cartItems
      .map((item) => {
        totalPay += item.data.pspecialprice * item.quantity;
        return `<tr>
                <td>${++index}</td>
                <td><a href="front-product-product_detail.html?productno=${
                  item.data.productno
                }" target="_blank">${item.data.pname}</a></td>
                <td class="number-cell">
                  <span class="number-top">$<s>${item.data.pprice}</s></span>
                  <span class="number-bottom">$${item.data.pspecialprice}</span>
                </td>
                <td><input style="width: 25%;" class="form-control h_50" type="number" value="${
                  item.quantity
                }" min="1" max="${item.data.pqty}"></td>
                <td>$${item.data.pspecialprice * item.quantity}</td>
                <td><a href="#" onClick="removeItem(${index - 1})">移除</a></td>
              </tr>`;
      })
      .join("");
    const coupon = `<tr>
                      <td colspan="3">
                        <label class="form-label">優惠券代碼</label>
                        <div class="position-relative">
                          <input id="couponCode"class="form-control h_50" type="text" placeholder="Code">
                          <button class="apply-btn btn-hover" type="button">使用</button>
                        </div>
                      </td>
                      <td colspan="4">
                        <div class="user_dt_trans text-end pe-xl-4">
                          <div class="totalinv2">總金額 : TWD $${totalPay}</div>
                        </div>
                      </td>
                    </tr>`;
    const card = document.querySelector("#TWD");
    card.textContent = `應支付總金額 : TWD $${totalPay}`;
    itemlist.innerHTML += itembody;
    itemlist.innerHTML += coupon;
  }
  // 監聽 input 數量更改事件
  $('input[type="number"]').on("change", function () {
    const idx = $(this).closest("tr").index();
    const qty = $(this).val();
    cartItems[idx].quantity = qty;
    localStorage.setItem("cartItems", JSON.stringify(cartItems));
    totalPay = 0;
    itemlist.querySelectorAll("tr:not(:last-child)").forEach((tr) => {
      const idx = tr.querySelector("td:first-child").innerText - 1;
      const item = cartItems[idx];
      item.quantity = parseInt(tr.querySelector('input[type="number"]').value);
      totalPay += item.data.pspecialprice * item.quantity;
      tr.querySelector("td:nth-last-child(2)").innerText = `$${
        item.data.pspecialprice * item.quantity
      }`;
    });
    itemlist.querySelector(
      ".totalinv2"
    ).innerText = `總金額 : TWD $${totalPay}`;
    const card = document.querySelector("#TWD");
    card.textContent = `應支付總金額 : TWD $${totalPay}`;
  });
});

function addPorder() {
  let cartItems = JSON.parse(localStorage.getItem("cartItems")) || [];
  let ptotal = 0;
  let pdiscounttotal = 0;
  let products = [];
  for (item of cartItems) {
    ptotal += item.data.pprice * item.quantity;
    pdiscounttotal += item.data.pspecialprice * item.quantity;
    products.push({
      productno: item.data.productno,
      quantity: item.quantity,
      pprice: item.data.pspecialprice * item.quantity,
    });
  }
  let pcoupontotal = 0;
  let pcouponno;
  let pchecktotal = pdiscounttotal - pcoupontotal;
  const porderbody = {
    memberno: memberno,
    ptotal: ptotal,
    pdiscounttotal: pdiscounttotal,
    pcoupontotal: pcoupontotal,
    pchecktotal: pchecktotal,
    pcouponno: pcouponno ?? null,
    recipient: $("#recipient").val(),
    rephone: $("#rephone").val(),
    readdress:
      $("#readdress1").val() +
      "," +
      $("#readdress2").val() +
      $("#readdress3").val(),
    orderProducts: products,
  };
  swal({
    title: "是否建立訂單?",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  }).then((confirm) => {
    if (confirm) {
      fetch("/porder/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(porderbody),
      }).then((response) => {
        if (response.ok) {
          response.json().then((porderno) => {
            swal({
              title: "建立成功",
              icon: "success",
              closeOnClickOutside: false,
            }).then(() => {
              localStorage.clear();
              window.location.href = `front-product-order_confirmed.html?id=${porderno}`;
            });
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
      return Promise.reject("取消操作");
    }
  });
}

function removeItem(e) {
  // 從 localStorage 取出 item 的值
  let itemData = JSON.parse(localStorage.getItem("cartItems"));
  // 刪除指定的項目
  itemData.splice(e, 1);
  // 更新索引
  itemData = itemData.map((item, index) => ({ ...item, index }));
  // 將修改後的資料存回 localStorage
  localStorage.setItem("cartItems", JSON.stringify(itemData));
  // 重新載入頁面
  location.reload();
}
document.querySelector("#recipientWarn");

const recipient = document.querySelector("#recipient");
recipient.addEventListener("blur", () => {
  if (!recipient.value.match(/^([\u4E00-\u9FFFa-zA-Z]{1,10}\s?){1,3}$/)) {
    document.querySelector("#recipientWarn").innerHTML = "(請輸入中文)";
  } else {
    document.querySelector("#recipientWarn").innerHTML = "";
  }
});
