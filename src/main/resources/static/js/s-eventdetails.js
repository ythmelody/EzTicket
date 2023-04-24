let memberNo;

const commentcontainer = document.querySelector('#commentcontainer');

// 從 Redis 撈出會員所對應的按讚評論編號並存到一個陣列以供後續比對
let commentArr = [];

function comment(commentNo) {
    verifyMember();

    if(memberNo === null){
        document.getElementsByName(`${commentNo}`)[0].removeAttribute("data-bs-target");
        return;
    }

    $('#aCommentNo').val(commentNo);
    $('#memberNo').val(memberNo);
}

function submitComment() {

    if ($('#aWhy').val() === null) {
        Swal.fire({
            icon: 'error',
            title: '檢舉失敗',
            text: '請選擇一個檢舉原因'
        })
        return;
    }

    let reqbody = {
        "memberNo": $('#memberNo').val(),
        "aCommentNo": $('#aCommentNo').val(),
        "aWhy": $('#aWhy').val()
    }

    fetch('areport/insert', {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(reqbody)
    })
        .then(resp => resp.json())
        .then((jsondata) => {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: '檢舉成功',
                showConfirmButton: false,
                timer: 1500
            })
        })

}

$(document).ready(function () {
        localStorage.removeItem('currentUrl');

        $.ajax({
            async: false,
            type: 'GET',
            url: "/member/getMemberInfo",
            dataType: "json",
            success: function (jsondata) {
                memberNo = jsondata['memberno'];
            }
        })

        fetch("/Activity/findByactivityNo" + `?activityNo=${activityNo}`)
            .then(resp => resp.json())
            .then(jsondata => {
                if (jsondata.arateQty < 1) {
                    $("#activityRate").children("h4").eq(0).text('尚無評星');
                } else if (jsondata.arateQty >= 1) {
                    //.toFixed(2) 呈現至小數點後兩位數
                    $("#activityRate").children("h4").eq(0).text("節目評星：" + ((jsondata.arateTotal / jsondata.arateQty)).toFixed(1)).append(`&nbsp;<i class="fa-solid fa-star" style="color: #ffad21!important;"></i>`);
                }
            })


        $.ajax({
            async: false,
            type: 'GET',
            url: 'acomment/ActAComments' + `?actNo=${activityNo}`,
            dataType: "json",
            success: function (jsondata) {
                for (let j of jsondata) {

                    commentArr.push(j.acommentNo);

                    commentcontainer.innerHTML += `
                            <div class="commentclass item">
                                <div class="main-card">
                                    <div class=" testimonial-content">
                                        <div class="testimonial-text">
                                            <p>${j.acommentCont}</p>
                                        </div>
                                        <div class="testimonial-user-dt">
                                            <h5>${j.member.mname}</h5>
                                            <ul id="starContainer_${j.acommentNo}">
                                            </ul>

                                            <!-- 點讚/檢舉 -->
                                            <table>
                                                <tr style="height: 40px; ">
                                                    <td style="padding-top: 4px; width: 35px;">
                                                        <button
                                                            style="border: none; background-color: transparent;">
                                                            <img id="thumb_${j.acommentNo}" name="thumbbtn"
                                                                 style="width: 30px;"
                                                                 src="images/thumbup_blank.png"
                                                                 onClick="chgthumbimg()">
                                                        </button>
                                                    </td>
                                                    <td id="alike_${j.acommentNo}" style="width: 30px;">${j.alike}</td>
                                                    <td>
                                                        <button name="${j.acommentNo}" data-bs-toggle="modal"
                                                                data-bs-target="#couponModal"
                                                                style="border: none; background-color: transparent" onmousedown="comment(this.name)">
                                                            <img style="padding-top: 4px; width: 30px;"
                                                                 src="images/comment.png">
                                                        </button>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                        <span class="quote-icon"><i
                                            class="fa-solid fa-quote-right"></i></span>
                                    </div>
                                </div>
                            </div>`;

                    // 動態產生星星
                    for (let star = 0; star < j.arate; star++) {
                        $(`#starContainer_${j.acommentNo}`).append(`<li><i class="fa-solid fa-star"></i></li>`);
                    }
                }

                // Document load 的時候顯示按過的讚，$(`#thumb_${i}`) 要新增在空讚的圖片中
                $.ajax({
                        async: false,
                        type: 'GET',
                        url: 'acomment/getThumbUpCmtNos' + `?memberNo=${memberNo}`,
                        dataType: "json",
                        success: function (jsondata) {
                            checkUpArr = Object.values(jsondata)

                            for (let i of commentArr) {
                                if (checkUpArr.includes(i)) {
                                    $(`#thumb_${i}`).attr('src', 'images/thumbup_clicked.png');
                                    $(`#thumb_${i}`).addClass("thumbbed");
                                }
                            }

                        }
                    }
                )
            }
        }).then(function () {
            $('.testimonial-slider').owlCarousel({
                items: 10,
                loop: true,
                margin: 20,
                nav: false,
                dots: true,
                smartSpeed: 800,
                autoplay: true,
                autoplayTimeout: 3000,
                autoplayHoverPause: true,
                responsive: {
                    0: {
                        items: 1
                    },
                    600: {
                        items: 1
                    },
                    1000: {
                        items: 2
                    },
                    1200: {
                        items: 2
                    },
                    1400: {
                        items: 2
                    }
                }
            })


        })
    }
)

function chgthumbimg() {
    verifyMember();

    if(memberNo === null){
        return;
    }

    const btnClass = event.target.className

    if (btnClass === '') {
        event.target.src = 'images/thumbup_clicked.png';
        event.target.classList.add("thumbbed");
        $.ajax({
            async: false,
            type: 'GET',
            url: 'acomment/thumbUpCmtNo' + `?memberNo=${memberNo}&aCommentNo=${event.target.id.replace("thumb_", "")}`,
            dataType: "json"
        })

        let after_thumb = Number($(`#alike_${event.target.id.replace("thumb_", "")}`).text()) + 1;
        $(`#alike_${event.target.id.replace("thumb_", "")}`).text(`${after_thumb}`);

    } else {
        event.target.src = 'images/thumbup_blank.png';
        event.target.classList.remove("thumbbed");
        $.ajax({
            async: false,
            type: 'GET',
            url: 'acomment/thumbDownCmtNo' + `?memberNo=${memberNo}&aCommentNo=${event.target.id.replace("thumb_", "")}`,
            dataType: "json"
        })

        let after_thumb = Number($(`#alike_${event.target.id.replace("thumb_", "")}`).text()) - 1;
        $(`#alike_${event.target.id.replace("thumb_", "")}`).text(`${after_thumb}`);
    }
}

function verifyMember(){
    if (memberNo === null) {
        Swal.fire({
            title: "點讚需要登入，是否前往登入？",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: "是",
            cancelButtonText: "否"
        }).then((result) => {
            if (result.isConfirmed) {
                localStorage.setItem('currentUrl', window.location.href);
                window.location.href = "front-users-mem-sign-in.html";
            }
        })
    }
}