$(document).ready(() => {
  fetchPorderList(`/html/porderlist`);
});

$('.tab-link[data-bs-target="#orders-tab"]').click(() => {
  fetchPorderList(`/html/porderlist`);
});

$('.form-control').on('input', function () {
  if ($(this).val() !== "" && parseInt($(this).val())) {
    let memberno = $(this).val();
    fetchPorderList(`/html/getordersbyid?id=${memberno}`);
  } else {
    fetchPorderList(`/html/porderlist`);
  };
});

function fetchPorderList(e) {
  fetch(e, {
    method: 'GET',
  }).then(response => response.json())
    .then(data => {
      data.reverse();
      document.querySelector('.tab-link[data-bs-target="#orders-tab"] span').textContent = data.length;
      const tbody = document.querySelector('#order-ls-tab');
      tbody.innerHTML = "";
      const extraCells = data.map(obj => {
        switch (obj.ppaymentstatus) {
          case 1:
            paystatus = '<span class="status-circle green-circle"></span>已付款';
            break;
          case 2:
            paystatus = '<span class="status-circle blue-circle"></span>已退款';
            break;
          case 0:
            paystatus = '<span class="status-circle red-circle"></span>未付款';
            break;
        }
        switch (obj.pprocessstatus) {
          case 1:
            processstatus = '<span class="status-circle yellow-circle"></span>已出貨';
            break;
          case 2:
            processstatus = '<span class="status-circle green-circle"></span>已結案';
            break;
          case 3:
            processstatus = '<span class="status-circle red-circle"></span>已取消';
            break;
          case 0:
            processstatus = '<span class="status-circle blue-circle"></span>未處理';
            break;
        }

        return `<tr>
                          <td><a href="invoice.html?id=${obj.porderno}">${obj.porderno}</a></td>
                          <td>${obj.memberno}</td>
                          <td>${obj.ptotal}</td>
                          <td>${obj.porderdate}</td>
                          <td>
                            <div class="img-btn" data-bs-toggle="modal" data-bs-target="#DeliveryModal">
                              <img onclick="viewDelivery(this)" src="images/icons/truck2.png" alt="配送資訊">
                            </div>
                          </td>
                          <td>${paystatus}</td>
                          <td>${processstatus}</td>
                          <td>
                            <select class="filter-option" onchange="changeStatus(this)">
                              <option selected disabled>請選擇</option>
                              <option value="0">未處理</option>
                              <option value="1">已出貨</option>
                              <option value="2">已結案</option>
                              <option value="3">已取消</option>
                            </select>
                            <button onclick="saveStatus(this)" class="save-status">儲存</button>
                          </td>
                      </tr>`;
      }).join('');
      tbody.innerHTML = extraCells;
    });
}

function changeStatus(selectElem) {
  const statusTd = selectElem.parentNode.previousElementSibling;
  const selectedValue = selectElem.value;

  switch (selectedValue) {
    case "1":
      statusTd.innerHTML = '<span class="status-circle yellow-circle"></span>配送中';
      break;
    case "2":
      statusTd.innerHTML = '<span class="status-circle green-circle"></span>已結案';
      break;
    case "3":
      statusTd.innerHTML = '<span class="status-circle red-circle"></span>已取消';
      break;
    case "0":
      statusTd.innerHTML = '<span class="status-circle blue-circle"></span>未處理';
      break;
  }
}

function saveStatus(button) {
  const status = button.closest('tr').querySelector('select').value;
  const no = Number(button.closest('tr').querySelector('tr td').textContent);
  if (status !== '請選擇') {
    swal({
      title: "請確認是否修改訂單狀態?",
      icon: "warning",
      buttons: true,
      dangerMode: true
    }).then((confirm) => {
      if (confirm) {
        fetch(`/html/updatestatusbyid`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ porderno: no, pprocessstatus: status }),
        }).then(response => {
          if (response.ok) {
            swal('狀態已更新', { icon: "success" });
          } else {
            swal('狀態更新異常', { icon: "error" });
          }
        });
      } else {
        return Promise.reject('取消操作');
      }
    });
  }
}


function viewDelivery(img) {
  const no = Number(img.closest('tr').querySelector('tr td').textContent);
  fetch(`/html/getporderbyid?id=${no}`, {
    method: 'GET',
  }).then(response => response.json())
    .then(data => {
      const deliverybody = document.querySelector('.form-group');
      deliverybody.innerHTML = '';
      const deliverylist =
        `<label class="form-label">訂單編號</label>
          <p><a href="invoice.html?id=${data.porderno}">${data.porderno}</a></p>
          <label class="form-label">出貨日期</label>
          <p>${data.pshipdate}</p>
          <label class="form-label">收貨人姓名：</label>
          <p>${data.recipient}</p>
          <label class="form-label">收貨人電話：</label>
          <p>${data.rephone}</p>
          <label class="form-label">收貨人地址：</label>
          <p>${data.readdress}</p>`;
      deliverybody.innerHTML += deliverylist;
    });
}