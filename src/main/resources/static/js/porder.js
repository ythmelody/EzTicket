$(document).ready(() => {
  // $('#orderTable').DataTable({
  //       //屬性區塊,
  //       "searching": false,
  //       "sPaginationType": "full_numbers", 
  //       "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]], 
  //       "processing": true, 
  //       "serverSide": false, 
  //       "stateSave": true,
  //       "destroy": true, 
  //       "info": true,
  //       "autoWidth": false, 
  //       "ordering": true, 
  //       "scrollCollapse": false, 
  //       "scrollX": "500px",
  //       "scrollY": "200px",    
  //       "paging": true, 
  //       "dom": '<"top">rt<"bottom"><"clear">',
  //       //ajax區塊,
  //       //資料欄位區塊(columns),
  //       //語言區塊(language),
  //       //欄位元素定義區塊(columnDefs),
  //       //列元素區塊(rowCallback)
  // });
  fetchPorderList(`/porder/list`);
});
$('#reload').click(() => {
  fetchPorderList(`/porder/list`);
});
$('.tab-link[data-bs-target="#orders-tab"]').click(() => {
  fetchPorderList(`/porder/list`);
});

$('.form-control').on('input', function () {
  if ($(this).val() !== "" && parseInt($(this).val())) {
    let memberno = $(this).val();
    fetchPorderList(`/porder/getordersbyid?id=${memberno}`);
  } else {
    fetchPorderList(`/porder/list`);
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
        let changeButton = "";
        switch (obj.ppaymentstatus) {
          case 1:
            paystatus = '<span class="status-circle green-circle"></span>已付款';
            break;
          case 2:
            paystatus = '<span class="status-circle blue-circle"></span>已退款';
            break;
          case 3:
            paystatus = '<span class="status-circle red-circle"></span>付款失敗';
            break;
          case 0:
            paystatus = '<span class="status-circle red-circle"></span>未付款';
            break;
        }
        switch (obj.pprocessstatus) {
          case 1:
            processstatus = '<span class="status-circle yellow-circle"></span>配送中';
            changeButton = `<select class="filter-option" onchange="changeStatus(this)">
                              <option selected disabled>請選擇</option>
                              <option value="2">已結案</option>
                              <option value="3">已取消</option>
                            </select>
                            <button onclick="saveStatus(this)" class="save-status">儲存</button>`
            break;
          case 2:
            processstatus = '<span class="status-circle green-circle"></span>已結案';
            break;
          case 3:
            processstatus = '<span class="status-circle red-circle"></span>已取消';
            break;
          case 4:
            processstatus = '<span class="status-circle red-circle"></span>取消申請';
            changeButton = `<select class="filter-option" onchange="changeStatus(this)">
                              <option selected disabled>請選擇</option>
                              <option value="3">已取消</option>
                            </select>
                            <button onclick="saveStatus(this)" class="save-status">儲存</button>`
            break;
          case 0:
            processstatus = '<span class="status-circle blue-circle"></span>未處理';
            changeButton = `<select class="filter-option" onchange="changeStatus(this)">
                              <option selected disabled>請選擇</option>
                              <option value="1">配送中</option>
                              <option value="3">已取消</option>
                            </select>
                            <button onclick="saveStatus(this)" class="save-status">儲存</button>`
            break;
        }
        return `<tr>
                          <td><a href="front-product-order_detail.html?id=${obj.porderno}">${obj.porderno}</a></td>
                          <td>${obj.memberno}</td>
                          <td>${obj.pchecktotal}</td>
                          <td>${moment(obj.porderdate).format('YYYY-MM-DD HH:mm:ss')}</td>
                          <td>
                            <div class="img-btn" data-bs-toggle="modal" data-bs-target="#DeliveryModal">
                              <img onclick="viewDelivery(this)" src="images/icons/truck2.png" alt="配送資訊">
                            </div>
                          </td>
                          <td>${paystatus}</td>
                          <td>${processstatus}</td>
                          <td>${changeButton}</td>
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
        fetch(`/porder/updatestatusbyid`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ porderno: no, pprocessstatus: status }),
        }).then(response => {
          if (response.ok) {
            swal('狀態已更新', { icon: "success" });
            fetchPorderList(`/porder/list`);
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
  fetch(`/porder/getporderbyid?id=${no}`, {
    method: 'GET',
  }).then(response => response.json())
    .then(data => {
      const deliverybody = document.querySelector('.form-group');
      deliverybody.innerHTML = '';
      const deliverylist =
        `<label class="form-label">訂單編號</label>
          <p><a href="front-product-order_detail.html?id=${data.porderno}">${data.porderno}</a></p>
          <label class="form-label">出貨日期</label>
          <p>${moment(data.pshipdate).format('YYYY-MM-DD HH:mm:ss')}</p>
          <label class="form-label">收貨人姓名：</label>
          <p>${data.recipient}</p>
          <label class="form-label">收貨人電話：</label>
          <p>${data.rephone}</p>
          <label class="form-label">收貨人地址：</label>
          <p>${data.readdress}</p>`;
      deliverybody.innerHTML += deliverylist;
    });
}


$(document).ready(function() {
  // 當選擇器的值發生變化時觸發過濾功能
  $('#filterToPay, #filterToActive').on('change', function() {
    filterTable();
  });
  
  function filterTable() {
    var filterToPay = $('#filterToPay').val();
    var filterToActive = $('#filterToActive').val();
    
    // 遍歷所有行，檢查是否符合條件，符合則顯示，否則隱藏
    $('#order-ls-tab tr').each(function() {
      var payStatus = $(this).find('td:nth-child(6)').text();
      var orderStatus = $(this).find('td:nth-child(7)').text();
      
      // 檢查付款狀態是否符合選擇器的值
      if (filterToPay == 'all' || (filterToPay == '0' && payStatus.indexOf('未付款') >= 0) || (filterToPay == '1' && payStatus.indexOf('已付款') >= 0) || (filterToPay == '2' && payStatus.indexOf('已退款') >= 0)) {
        // 檢查訂單狀態是否符合選擇器的值
        if (filterToActive == 'all' || (filterToActive == '0' && orderStatus.indexOf('未處理') >= 0) || (filterToActive == '1' && orderStatus.indexOf('配送中') >= 0) || (filterToActive == '2' && orderStatus.indexOf('已結案') >= 0) || (filterToActive == '3' && orderStatus.indexOf('已取消') >= 0) || (filterToActive == '4' && orderStatus.indexOf('取消申請') >= 0)) {
          $(this).show();
        } else {
          $(this).hide();
        }
      } else {
        $(this).hide();
      }
    });
  }
});
