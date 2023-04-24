//追蹤商品清單呈現&移除
$(document).ready(function () {
    followproductList();
});

function followproductList() {
    $("#productFollowing").empty();
    fetch("FollowproductController", {
        method: "POST",
        body: new URLSearchParams({
            'action': "getFollowProductList"
        })
    }).then(resp => resp.json())
        .then(list => {
            console.log(list.length);
            $(".ff-block").children("a").children("span").text(list.length);
            $("#pills-following-tab").children("span").text("(" + list.length + ")");
            for (let item of list) {

                fetch("ProductInfoServlet", {
                    method: "POST",
                    body: new URLSearchParams({
                        'productno': item.followproductPK.productno,
                        'action': "singleProduct"
                    })
                }).then(resp => resp.json())
                    .then(product => {
                        let image = product.pimgts && product.pimgts[0] && product.pimgts[0].pimgno;
                        if (image === undefined) { image = 70001; }
                        $("#productFollowing").append(`<div class="user-follow-card mb-4">
                                    <div class="follow-card-left">
                                        <div class="follow-avatar">
                                            <img src="ProductImg?pimgno=${image}" alt="">
                                        </div>
                                        <div class="follow-name">
                                            <h5>${product.pname}</h5>
                                            <span style="text-decoration: line-through">${product.pprice}</span>
                                            <span style="font-size: 20px;">${product.pspecialprice}</span>
                                        </div>
                                    </div>
                                    <div class="follow-card-btn">
                                        <button name ="${product.productno}" class="follow-btn" onclick="unfollow(this.name)" >取消追蹤</button>
                                    </div>
                                    <div class="follow-card-btn">
                                        <button class="follow-btn" onclick="addCart(${product.productno})">加入購物車<i class="fa-solid fa-cart-shopping"></i></button>
                                    </div>
                                </div>`);
                    })
            }
        })
}

//追蹤商品移除
function unfollow(productno) {
    fetch("FollowproductController", {
        method: 'POST',
        body: new URLSearchParams({
            'productno': productno,
            'action': "unfollowOneProduct"
        })
    }).then(resp => resp.json())
        .then(item => {
            if (item) {
                followproductList();
            }
        })
}

//移除所有追蹤商品
function unfollowAllproduct(memberno){
fetch("FollowproductController", {
  method: "POST",
  body: new URLSearchParams({
    // memberno: info.memberNo,
    // productno: productno,
    action: "unfollowAllproduct",
  }),
})
  .then((resp) => resp.json())
  .then((item) => {
    if (item) {
      followproductList();
    }
  });

}