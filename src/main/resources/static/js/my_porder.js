// 獲取購物車元素
let cart = document.getElementById('cart');
// 獲取購物車商品數量元素
let badge = cart.querySelector('.badge');
// 設置初始購物車商品數量為 0
let count = 0;
// 載入未結帳商品數量
$(document).ready(() => {
  if (cartItems.length !== 0) {
    for (item of cartItems) {
      count += Number(item.quantity);
    }
    badge.innerHTML = count;
  }
})
// 購物車商品清單
let cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
// 添加事件監聽器
function addCart(e) {
  // 當使用者點擊加入購物車時，執行這個函數
  // 更新購物車商品數量
  count++;
  badge.innerHTML = count;
  // 存入 localStorage
  const productno = e;
  fetch(`/ProductInfoServlet?action=singleProduct&productno=${productno}`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      // 判斷購物車內是否已經有該商品
      let existingItem = cartItems.find(item => item.data.productno === data.productno);
      if (existingItem) {
        existingItem.quantity++;
      } else {
        let newItem = { data: data, quantity: 1 };  // 新增一個商品物件到購物車中
        cartItems.push(newItem);
      }
      localStorage.setItem('cartItems', JSON.stringify(cartItems));
    })
}

function buyNow(e) {
  addCart(e);
  swal({
    title: "是否前往購物車結帳?",
    icon: "warning",
    buttons: true,
    dangerMode: true
  }).then((confirm) => {
    if (confirm) {
      window.location.href = 'front-product-shopping_cart.html';
    } else {
      return Promise.reject('取消操作');
    }
  })
}