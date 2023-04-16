// 購物車商品清單
let cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
// 載入未結帳商品數量
$(document).ready(() => {
  cartCount();
})
$('#product-incart').click(function () {addCartByCount()});
$('#buyNow').click(function () {
  addCartByCount();
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
});
// 計算目前商品數量
function cartCount(){
  let cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
  // 設置初始購物車商品數量為 0
  let count = 0;
  if (cartItems.length !== 0) {
    for (item of cartItems) {
      count += Number(item.quantity);
    }
  }
  $('.badge').text(count);
}
// 添加事件監聽器
function addCart(e) {
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
      cartCount();
      addMsg();
    });
  // 刷新購物車數量
}

function buyNow(e) {
  addCart(e);
  swal({
    title: "是否前往購物車結帳?",
    icon: "warning",
    buttons: true,
  }).then((confirm) => {
    if (confirm) {
      window.location.href = 'front-product-shopping_cart.html';
    } else {
      return Promise.reject('取消操作');
    }
  })
}

function addCartByCount() {
  let cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
  let qty = document.querySelector('#count').value;
  let cartqty = Number($('.badge').text());
  $('.badge').text(Number(cartqty + qty));
  let itemqty;
  for (item of cartItems) {
    if (item.data.productno == productno) {
      itemqty = item;
    }
  }
  if (itemqty) {
    itemqty.quantity += parseInt(qty);
    localStorage.setItem('cartItems', JSON.stringify(cartItems));
  } else {
    addCart(productno);
  }
  addMsg();
  cartCount();
}
function addMsg(){
  swal("加入購物車成功", {
    icon: "success",
    buttons: false,
    timer: 1000,
  });
}