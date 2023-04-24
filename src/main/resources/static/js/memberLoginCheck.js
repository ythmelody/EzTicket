//確認瀏覽者在此頁面的狀態是否已登入,未登入的話就導向登入頁面
fetch('member/getMemberInfo')
    .then(response => {
        console.log(response);
        return response.json();
    })
    .then(data => {
        console.log(data);
        console.log(data.successful);
        if (data.successful) {
            console.log("已登入狀態")

            $(".D-mname").text(`${data.mname}`);
            $(".D-memail").text(`${data.memail}`);
            if(data.mimg){
                $('.D-mimg').attr('src', `data:image/png;base64,${data.mimg}`);
            }

            $('li.profile-link').empty();
            $('li.profile-link').append('<a class="dropdown-item pe-5" href="front-users-member-profile.html">會員資訊</a>');
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
            $(".D-mname").text("歡迎光臨");
            $(".D-memail").text("趕緊加入會員!");

            $('li.profile-link').empty();
            $('li.profile-link').append('<a class="dropdown-item pe-5" href="front-users-mem-sign-in.html">登入</a>');
            $('li.profile-link').append('<a class="dropdown-item pe-5" href="front-users-sign-up.html">註冊</a>');
            $('li.profile-link').append('<a class="dropdown-item pe-5" href="front-users-forgot-pwd.html">忘記密碼</a>');

        }
    })
    .catch(error => {
        console.error(error);
    });