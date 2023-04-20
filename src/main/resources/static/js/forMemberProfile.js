//確認瀏覽者在此頁面的狀態是否已登入,未登入的話就導向登入頁面
fetch('member/getMemberInfo')
    .then(response => {
        return response.json();
    })
    .then(data => {
        if (data.successful) {
            console.log("已登入狀態")

            $('li.profile-link').empty();
            $('li.profile-link').append('<a class="dropdown-item pe-5" href="index.html">回到首頁</a>');
            $('li.profile-link').append('<a class="dropdown-item pe-5" href="front-users-mem-sign-in.html.html">登出</a>');
            $('a:contains("登出")').click(() => {
                fetch('member/logout', {
                    method: "GET",
                }).then(response => {
                    if (response.ok) {
                        location.reload();
                    } else {
                        console.log("登出失敗")
                    }
                }).catch(error => {
                    console.log('發生錯誤:', err);
                });
            })


        } else {
            console.log("未登入狀態")
            window.location.href="front-users-mem-sign-in.html";
        }
    })
    .catch(error => {
        console.error(error);
    });