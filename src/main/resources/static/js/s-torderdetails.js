let memberNo;
let activityNo;
$(document).ready(function () {
        localStorage.removeItem('currentUrl');
        $.ajax({
            async: false,
            type: 'GET',
            url: "/member/getMemberInfo",
            dataType: "json",
            success: function (jsondata) {
                memberNo = jsondata['memberno'];
                if (memberNo === null) {
                    localStorage.setItem('currentUrl', window.location.href);
                    window.location.href = "/front-users-mem-sign-in.html";
                }
            }
        })
    }
)

$('.starRating span').click(function () {
    $(this).siblings().removeClass('active');
    $(this).addClass('active');
    $(this).parent().addClass('starRated');

    let rating = $(this).index() + 1;
    $('#currentRating').html("<small>Rating: <b>" + rating + "</b></small>");

    $('#aRate').val(rating);
});

function submitComment() {

    if ($('#acommentNo').val() !== '') {
        Swal.fire({
            title: "是否更改節目評星及評論？",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: "是",
            cancelButtonText: "否"
        }).then((result) => {
            if (result.isConfirmed) {
                let reqbody = {
                    aCommentNo: $('#acommentNo').val(),
                    memberNo: memberNo,
                    activityNo: activityNo,
                    aRate: $('#aRate').val(),
                    aCommentCont: $('#aCommentCont').val(),
                }

                if (reqbody.aRate === '' || reqbody.aRate === null || reqbody.aRate === 0 ||
                    reqbody.aCommentCont === '' || reqbody.aCommentCont === null ){
                    Swal.fire({
                        icon: 'error',
                        title: '更改評論失敗',
                        text: '評星或評論不得為空'
                    })

                    return;
                }

                fetch('acomment/update', {
                    method: "POST",
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(reqbody)
                })

                $(`.commentContainer_${activityNo}`).html('');
                $(`.commentContainer_${activityNo}`).html(`<a id="editbtn" data-bs-toggle="modal" data-bs-target="#couponModal">已評論</a>`);

                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: '更改評論成功',
                    showConfirmButton: false,
                    timer: 1500
                })

                // location.reload();
            }
        })
    } else {
        Swal.fire({
            title: "是否新增節目評星及評論？",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: "是",
            cancelButtonText: "否"
        }).then((result) => {
            if (result.isConfirmed) {
                let reqbody = {
                    memberNo: memberNo,
                    activityNo: activityNo,
                    aRate: $('#aRate').val(),
                    aCommentCont: $('#aCommentCont').val(),
                }

                if (reqbody.aRate === '' || reqbody.aRate === null || reqbody.aRate === 0 ||
                    reqbody.aCommentCont === '' || reqbody.aCommentCont === null ){
                    Swal.fire({
                        icon: 'error',
                        title: '新增評論失敗',
                        text: '評星或評論不得為空'
                    })

                    return;
                }

                fetch('acomment/insert', {
                    method: "POST",
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(reqbody)
                })

                $(`.commentContainer_${activityNo}`).html('');
                $(`.commentContainer_${activityNo}`).html(`<a id="editbtn" data-bs-toggle="modal" data-bs-target="#couponModal">已評論</a>`);

                Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: '新增評論成功',
                    showConfirmButton: false,
                    timer: 1500
                })

                // location.reload();
            }
        })
    }
}

function setKV(target) {
    activityNo = target.className.replace("commentContainer_", "");

    if (target.children[0].className.includes('cmt')) {
        $('.starRating').removeClass('starRated');
        $('#aCommentCont').val('');
    } else {
        fetch("/acomment/getByMemberAndActNo" + `?memberNo=${memberNo}&activityNo=${activityNo}`)
            .then(resp => resp.json())
            .then((jsondata) => {
                $('#acommentNo').val(`${jsondata.acommentNo}`);

                $('#aRate').val(`${jsondata.arate}`);

                $('.starRating').addClass('starRated');
                for (let i = 0; i < jsondata.arate; i++) {
                    $(`.starRating span[${i}]`).addClass('active');
                }

                $('#aCommentCont').val(`${jsondata.acommentCont}`);
            })
    }
}