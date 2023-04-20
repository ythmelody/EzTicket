//登出按鍵 ->Session失效
$(".logOut").click(() => {
    fetch('backuser/logout', {
        method: "GET",
    }).then(response => {
        if (response.ok) {
            window.location.href = "front-users-bu-sign-in.html";
        } else {
            console.log("登出失敗")
        }
    }).catch(error => {
        console.log('發生錯誤:', err);
    });
})

fetch("backuser/getBackuserInfo", {
    method: 'GET',
})
    .then(response => response.json())
    .then(response => {
        //測試檢視拿到的資料
        // console.log(response);
    
        //姓名
        $(".D-baname").text(`${response.baname}`);
        $(".D-baname").val(`${response.baname}`);
        //Email
        $(".D-baemail").text(`${response.baemail}`);
        $(".D-baemail").val(`${response.baemail}`);
        //大頭貼
        if(response.baimg === null){
            $('.D-baimg').attr('src', "images/profile-imgs/img-13.jpg");
        }else{
            const url = `data:image/png;base64,${response.baimg}`;
            $('.D-baimg').attr('src', url);

        }
        
    
        
    
    
    })

fetch("backuser/getBackuserAuthInfo", {
        method: 'GET',
})
    .then(response => response.json())
    .then(response => {

        //角色
        $(".D-barole").text(`${response.rolename}`);
        $(".D-barole").val(`${response.rolename}`);

        //抓出該角色沒有的權限刪除
        const keys = Object.keys(response.nofuncs);
        for (let key of keys) {
            // console.log(key)
            $(`.${key}`).remove();
        }
    })