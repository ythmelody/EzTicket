
$(document).ready(() => {
	fetchPcouponList(`/pcoupon/list`);
});

$('#search-coupon').on('input', function () {
	if ($(this).val() !== "" && parseInt($(this).val())) {
		let couponno = $(this).val();
		fetchPcouponList(`/pcoupon/getbyno?id=${couponno}`);
	} else {
		fetchPcouponList(`/pcoupon/list`);
	};
});

function fetchPcouponList(e) {
	fetch(e, {
		method: 'GET',
	}).then(response => response.json())
		.then(data => {
			data.reverse();
			const couponlist = document.querySelector('.all-promotion-list');
			couponlist.innerHTML = "";
			document.querySelector('.mb-4').textContent = "優惠券 (" + data.length + ")";
			const couponbody = data.map(obj => {

				return `<div class="main-card mt-4">
                  <div class="contact-list coupon-active">
                    <div class="top d-flex flex-wrap justify-content-between align-items-center p-4 border_bottom">
                      <div class="icon-box">
                        <span class="icon-big rotate-icon icon icon-purple">
                          <i class="fa-solid fa-ticket"></i>
                        </span>
                        <h5 class="font-18 mb-1 mt-1 f-weight-medium">${obj.pcouponno}<span class="font-weight-normal"> -
                        ${obj.pcouponname}</span></h5>
                        <p class="text-gray-50 m-0"><span class="visitor-date-time">${moment(obj.pcoupnsdate).format('YYYY-MM-DD HH:mm:ss')}
                          </span> - <span class="visitor-date-time">${moment(obj.pcoupnedate).format('YYYY-MM-DD HH:mm:ss')}</span></p>
                      </div>
                      <div class="d-flex align-items-center">
                        <label class="btn-switch tfs-8 mb-0 me-4 mt-1">
                        <input type="checkbox" onchange="changeStatus(this)" value="" ${obj.pcouponstatus === 1 ? 'checked' : 'false'}>
                          <span class="checkbox-slider"></span>
                        </label>
                        <div class="dropdown dropdown-default dropdown-text dropdown-icon-item">
                          <button class="option-btn-1" type="button" data-bs-toggle="dropdown" aria-haspopup="true"
                            aria-expanded="false">
                            <i class="fa-solid fa-ellipsis-vertical"></i>
                          </button>
                          <div class="dropdown-menu dropdown-menu-right">
                            <a href="#" class="dropdown-item" onclick="editCoupon(this)"><i class="fa-solid fa-pen me-3"></i>編輯</a>
                            <a href="#" class="dropdown-item" onclick="saveCoupon(this)"><i class="fa-solid fa-floppy-disk me-3"></i>儲存</a>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="bottom d-flex flex-wrap justify-content-between align-items-center p-4">
                      <div class="icon-box ">
                        <span class="icon">
                          <i class="fa-regular fa-circle-dot"></i>
                        </span>
                        <p>狀態</p>
                        <h6 class="coupon-status">${obj.pcouponstatus === 1 ? '可使用' : '不可使用'}</h6>
                      </div>
                      <div class="icon-box">
                        <span class="icon">
                          <i class="fa-solid fa-tag"></i>
                        </span>
                        <p>適用商品編號</p>
                        <h6 class="coupon-status">${obj.pfitcoupons && obj.pfitcoupons[0] && obj.pfitcoupons[0].pfitcouponNo.productno}</h6>
                      </div>
                      <div class="icon-box">
                        <span class="icon">
                          <i class="fa-solid fa-money-bill"></i>
                        </span>
                        <p>消費金額</p>
                        <h6 class="coupon-status">${obj.preachprice}</h6>
                      </div>
                      <div class="icon-box">
                        <span class="icon">
                          <i class="fa-solid fa-money-bill"></i>
                        </span>
                        <p>折扣</p>
                        <h6 class="coupon-status">${obj.pdiscount}</h6>
                      </div>
                    </div>
                  </div>
                </div>`;
			}).join('');
			couponlist.innerHTML += couponbody;
		})
}

function changeStatus(checkbox) {
	const boxstatus = checkbox.checked;
	const status = checkbox.closest('.main-card').querySelector('h6');
	if (boxstatus === false) {
		status.textContent = "不可使用";
	} else {
		status.textContent = "可使用";
	}
}

function addCoupon() {
	const couponbody = {
		'pcouponname': $("#pcouponname").val(),
		'productno': +$("#productno").val(),
		'pdiscount': +$("#pdiscount").val(),
		'preachprice': +$("#preachprice").val(),
		'pcoupnsdate': formatDate($("#pcoupnsdateup").val(), $("#pcoupnsdatedown").val()),
		'pcoupnedate': formatDate($("#pcoupnedateup").val(), $("#pcoupnedatedown").val())
	}
	swal({
		title: "是否新增優惠券?",
		icon: "warning",
		buttons: true,
		dangerMode: true
	}).then((confirm) => {
		if (confirm) {
			fetch('/pcoupon/add', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(couponbody)
			}).then(response => {
				if (response.ok) {
					swal('新增成功', { icon: "success" });
				} else {
					swal('新增失敗', { icon: "error" });
				}
			});
		} else {
			return Promise.reject('取消操作');
		}
	});
}


function formatDate(date, time) {
	const dateTime = new Date(`${date} ${time}`);
	const year = dateTime.getFullYear();
	const month = ('0' + (dateTime.getMonth() + 1)).slice(-2);
	const day = ('0' + dateTime.getDate()).slice(-2);
	const hours = ('0' + dateTime.getHours()).slice(-2);
	const minutes = ('0' + dateTime.getMinutes()).slice(-2);
	const seconds = ('0' + dateTime.getSeconds()).slice(-2);
	return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

function editCoupon(edit) {
	const text = edit.closest('.main-card').querySelector('h5').textContent;
	const couponno = text.substring(0, 5);
	fetch(`/pcoupon/getbyno?id=${couponno}`, {
		method: 'GET',
	}).then(response => response.json())
		.then(data => {
			const couponbody =
				`<!-- Create Coupon Model Start-->
	<div class="modal fade" id="couponModal" tabindex="-1" aria-labelledby="couponModalLabel" aria-hidden="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="couponModalLabel">編輯優惠券</h5>
					<button type="button" class="close-model-btn" data-bs-dismiss="modal" aria-label="Close"><i
							class="uil uil-multiply"></i></button>
				</div>
				<div class="modal-body">
					<div class="model-content main-form">
						<div class="row">
							<div class="col-lg-12 col-md-12">
								<div class="gift-coupon-icon mt-5 pb-5">
									<img src="images/present-box.png" alt="">
								</div>
							</div>
							<div class="col-lg-12 col-md-12">
								<div class="form-group mt-4">
									<label class="form-label">優惠券代碼</label>
									<input class="form-control h_40" type="text" placeholder="40010" value="">
								</div>
							</div>
							<div class="col-lg-6 col-md-12">
								<div class="form-group mt-4">
									<label class="form-label">優惠券名稱</label>
									<input class="form-control h_40" type="text" placeholder="黑皮牛耶卷" value="">
								</div>
							</div>
							<div class="col-lg-6 col-md-12">
								<div class="form-group mt-4">
									<label class="form-label">適用商品編號</label>
									<input class="form-control h_40" type="text" placeholder="10001" value="">
								</div>
							</div>
							<div class="col-lg-6 col-md-12">
								<div class="form-group mt-4">
									<label class="form-label">折扣金額</label>
									<input class="form-control h_40" type="text" placeholder="100" value="">
								</div>
							</div>
							<div class="col-lg-6 col-md-12">
								<div class="form-group main-form mt-4">
									<label class="form-label">最低消費金額</label>
									<input class="form-control h_40" type="text" placeholder="1000" value="">
								</div>
							</div>
							<div class="col-lg-6 col-md-12">
								<div class="form-group mt-4">
									<label class="form-label">折扣開始日期</label>
									<input class="form-control h_40 datepicker-here" data-language="en" data-position="top left"
										type="text" placeholder="MM/DD/YYYY" value="">
								</div>
							</div>
							<div class="col-lg-6 col-md-12">
								<div class="form-group main-form mt-4">
									<label class="form-label">時間</label>
									<select class="selectpicker" data-size="5" data-live-search="true">
										<option value="00:00">12:00 AM</option>
										<option value="00:15">12:15 AM</option>
										<option value="00:30">12:30 AM</option>
										<option value="00:45">12:45 AM</option>
										<option value="01:00">01:00 AM</option>
										<option value="01:15">01:15 AM</option>
										<option value="01:30">01:30 AM</option>
										<option value="01:45">01:45 AM</option>
										<option value="02:00">02:00 AM</option>
										<option value="02:15">02:15 AM</option>
										<option value="02:30">02:30 AM</option>
										<option value="02:45">02:45 AM</option>
										<option value="03:00">03:00 AM</option>
										<option value="03:15">03:15 AM</option>
										<option value="03:30">03:30 AM</option>
										<option value="03:45">03:45 AM</option>
										<option value="04:00">04:00 AM</option>
										<option value="04:15">04:15 AM</option>
										<option value="04:30">04:30 AM</option>
										<option value="04:45">04:45 AM</option>
										<option value="05:00">05:00 AM</option>
										<option value="05:15">05:15 AM</option>
										<option value="05:30">05:30 AM</option>
										<option value="05:45">05:45 AM</option>
										<option value="06:00">06:00 AM</option>
										<option value="06:15">06:15 AM</option>
										<option value="06:30">06:30 AM</option>
										<option value="06:45">06:45 AM</option>
										<option value="07:00">07:00 AM</option>
										<option value="07:15">07:15 AM</option>
										<option value="07:30">07:30 AM</option>
										<option value="07:45">07:45 AM</option>
										<option value="08:00">08:00 AM</option>
										<option value="08:15">08:15 AM</option>
										<option value="08:30">08:30 AM</option>
										<option value="08:45">08:45 AM</option>
										<option value="09:00">09:00 AM</option>
										<option value="09:15">09:15 AM</option>
										<option value="09:30">09:30 AM</option>
										<option value="09:45">09:45 AM</option>
										<option value="10:00" selected>10:00 AM</option>
										<option value="10:15">10:15 AM</option>
										<option value="10:30">10:30 AM</option>
										<option value="10:45">10:45 AM</option>
										<option value="11:00">11:00 AM</option>
										<option value="11:15">11:15 AM</option>
										<option value="11:30">11:30 AM</option>
										<option value="11:45">11:45 AM</option>
										<option value="12:00">12:00 PM</option>
										<option value="12:15">12:15 PM</option>
										<option value="12:30">12:30 PM</option>
										<option value="12:45">12:45 PM</option>
										<option value="13:00">01:00 PM</option>
										<option value="13:15">01:15 PM</option>
										<option value="13:30">01:30 PM</option>
										<option value="13:45">01:45 PM</option>
										<option value="14:00">02:00 PM</option>
										<option value="14:15">02:15 PM</option>
										<option value="14:30">02:30 PM</option>
										<option value="14:45">02:45 PM</option>
										<option value="15:00">03:00 PM</option>
										<option value="15:15">03:15 PM</option>
										<option value="15:30">03:30 PM</option>
										<option value="15:45">03:45 PM</option>
										<option value="16:00">04:00 PM</option>
										<option value="16:15">04:15 PM</option>
										<option value="16:30">04:30 PM</option>
										<option value="16:45">04:45 PM</option>
										<option value="17:00">05:00 PM</option>
										<option value="17:15">05:15 PM</option>
										<option value="17:30">05:30 PM</option>
										<option value="17:45">05:45 PM</option>
										<option value="18:00">06:00 PM</option>
										<option value="18:15">06:15 PM</option>
										<option value="18:30">06:30 PM</option>
										<option value="18:45">06:45 PM</option>
										<option value="19:00">07:00 PM</option>
										<option value="19:15">07:15 PM</option>
										<option value="19:30">07:30 PM</option>
										<option value="19:45">07:45 PM</option>
										<option value="20:00">08:00 PM</option>
										<option value="20:15">08:15 PM</option>
										<option value="20:30">08:30 PM</option>
										<option value="20:45">08:45 PM</option>
										<option value="21:00">09:00 PM</option>
										<option value="21:15">09:15 PM</option>
										<option value="21:30">09:30 PM</option>
										<option value="21:45">09:45 PM</option>
										<option value="22:00">10:00 PM</option>
										<option value="22:15">10:15 PM</option>
										<option value="22:30">10:30 PM</option>
										<option value="22:45">10:45 PM</option>
										<option value="23:00">11:00 PM</option>
										<option value="23:15">11:15 PM</option>
										<option value="23:30">11:30 PM</option>
										<option value="23:45">11:45 PM</option>
									</select>
								</div>
							</div>
							<div class="col-lg-6 col-md-12">
								<div class="form-group mt-4">
									<label class="form-label">折扣結束日期</label>
									<input class="form-control h_40 datepicker-here" data-language="en" data-position="top left"
										type="text" placeholder="MM/DD/YYYY" value="">
								</div>
							</div>
							<div class="col-lg-6 col-md-12">
								<div class="form-group main-form mt-4">
									<label class="form-label">時間</label>
									<select class="selectpicker" data-size="5" data-live-search="true">
										<option value="00:00">12:00 AM</option>
										<option value="00:15">12:15 AM</option>
										<option value="00:30">12:30 AM</option>
										<option value="00:45">12:45 AM</option>
										<option value="01:00">01:00 AM</option>
										<option value="01:15">01:15 AM</option>
										<option value="01:30">01:30 AM</option>
										<option value="01:45">01:45 AM</option>
										<option value="02:00">02:00 AM</option>
										<option value="02:15">02:15 AM</option>
										<option value="02:30">02:30 AM</option>
										<option value="02:45">02:45 AM</option>
										<option value="03:00">03:00 AM</option>
										<option value="03:15">03:15 AM</option>
										<option value="03:30">03:30 AM</option>
										<option value="03:45">03:45 AM</option>
										<option value="04:00">04:00 AM</option>
										<option value="04:15">04:15 AM</option>
										<option value="04:30">04:30 AM</option>
										<option value="04:45">04:45 AM</option>
										<option value="05:00">05:00 AM</option>
										<option value="05:15">05:15 AM</option>
										<option value="05:30">05:30 AM</option>
										<option value="05:45">05:45 AM</option>
										<option value="06:00">06:00 AM</option>
										<option value="06:15">06:15 AM</option>
										<option value="06:30">06:30 AM</option>
										<option value="06:45">06:45 AM</option>
										<option value="07:00">07:00 AM</option>
										<option value="07:15">07:15 AM</option>
										<option value="07:30">07:30 AM</option>
										<option value="07:45">07:45 AM</option>
										<option value="08:00">08:00 AM</option>
										<option value="08:15">08:15 AM</option>
										<option value="08:30">08:30 AM</option>
										<option value="08:45">08:45 AM</option>
										<option value="09:00">09:00 AM</option>
										<option value="09:15">09:15 AM</option>
										<option value="09:30">09:30 AM</option>
										<option value="09:45">09:45 AM</option>
										<option value="10:00" selected>10:00 AM</option>
										<option value="10:15">10:15 AM</option>
										<option value="10:30">10:30 AM</option>
										<option value="10:45">10:45 AM</option>
										<option value="11:00">11:00 AM</option>
										<option value="11:15">11:15 AM</option>
										<option value="11:30">11:30 AM</option>
										<option value="11:45">11:45 AM</option>
										<option value="12:00">12:00 PM</option>
										<option value="12:15">12:15 PM</option>
										<option value="12:30">12:30 PM</option>
										<option value="12:45">12:45 PM</option>
										<option value="13:00">01:00 PM</option>
										<option value="13:15">01:15 PM</option>
										<option value="13:30">01:30 PM</option>
										<option value="13:45">01:45 PM</option>
										<option value="14:00">02:00 PM</option>
										<option value="14:15">02:15 PM</option>
										<option value="14:30">02:30 PM</option>
										<option value="14:45">02:45 PM</option>
										<option value="15:00">03:00 PM</option>
										<option value="15:15">03:15 PM</option>
										<option value="15:30">03:30 PM</option>
										<option value="15:45">03:45 PM</option>
										<option value="16:00">04:00 PM</option>
										<option value="16:15">04:15 PM</option>
										<option value="16:30">04:30 PM</option>
										<option value="16:45">04:45 PM</option>
										<option value="17:00">05:00 PM</option>
										<option value="17:15">05:15 PM</option>
										<option value="17:30">05:30 PM</option>
										<option value="17:45">05:45 PM</option>
										<option value="18:00">06:00 PM</option>
										<option value="18:15">06:15 PM</option>
										<option value="18:30">06:30 PM</option>
										<option value="18:45">06:45 PM</option>
										<option value="19:00">07:00 PM</option>
										<option value="19:15">07:15 PM</option>
										<option value="19:30">07:30 PM</option>
										<option value="19:45">07:45 PM</option>
										<option value="20:00">08:00 PM</option>
										<option value="20:15">08:15 PM</option>
										<option value="20:30">08:30 PM</option>
										<option value="20:45">08:45 PM</option>
										<option value="21:00">09:00 PM</option>
										<option value="21:15">09:15 PM</option>
										<option value="21:30">09:30 PM</option>
										<option value="21:45">09:45 PM</option>
										<option value="22:00">10:00 PM</option>
										<option value="22:15">10:15 PM</option>
										<option value="22:30">10:30 PM</option>
										<option value="22:45">10:45 PM</option>
										<option value="23:00">11:00 PM</option>
										<option value="23:15">11:15 PM</option>
										<option value="23:30">11:30 PM</option>
										<option value="23:45">11:45 PM</option>
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="co-main-btn min-width btn-hover h_40" data-bs-target="#aboutModal"
						data-bs-toggle="modal" data-bs-dismiss="modal">取消</button>
					<button type="button" class="main-btn min-width btn-hover h_40">建立</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Create Coupon Model End-->`;
		})
}