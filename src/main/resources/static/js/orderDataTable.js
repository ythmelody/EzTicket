$(document).ready(function() {
  const orderTable = $('#orderTable').DataTable({
    autoWidth: true,
    "bFilter": true,
    lengthMenu: [[10, 20, 30, -1], [10, 20, 30, '全部']],
    "searching": true,
    language: {
      emptyTable: '無資料...',
      processing: '處理中...',
      loadingRecords: '載入中...',
      lengthMenu: '每頁 MENU 筆資料',
      zeroRecords: '無搜尋結果',
      info: '_START_ 至 END / 共 TOTAL 筆',
      infoEmpty: '尚無資料',
      infoFiltered: '(從 MAX 筆資料過濾)',
      infoPostFix: '',
      search: '搜尋字串:',
      paginate: {
        first: '首頁',
        last: '末頁',
        next: '＞',
        previous: '＜',
      },
    },
    ajax: {
      url: '/porder/list',
      method: 'GET',
      dataSrc: ''
    },
    columns: [
      {data: 'porderno', render: function(data) {
        return '<a href="front-product-order_detail.html?id=' + data + '">' + data + '</a>';
      }},
      {data: 'memberno'},
      {data: 'pchecktotal'},
      {data: 'porderdate', render: function(data) {
        return moment(data).format('YYYY-MM-DD HH:mm:ss');
      }},
      {data: 'porderno', render: function(data) {
        return '<div class="img-btn" data-bs-toggle="modal" data-bs-target="#DeliveryModal"><img onclick="viewDelivery(' + data + ')" src="images/icons/truck2.png" alt="配送資訊"></div>';
      }},
      {data: 'ppaymentstatus', render: function(data) {
        let paystatus = '';
        switch (data) {
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
        return paystatus;
      }},
      {data: 'pprocessstatus', render: function(data) {
        let processstatus = '';
        switch (data) {
          case 1:
            processstatus = '<span class="status-circle yellow-circle"></span>配送中';
            break;
          case 2:
            processstatus = '<span class="status-circle green-circle"></span>已結案';
            break;
          case 3:
            processstatus = '<span class="status-circle red-circle"></span>已取消';
            break;
          case 4:
            processstatus = '<span class="status-circle red-circle"></span>取消申請';
            break;
          default:
            processstatus = '<span class="status-circle red-circle"></span>未處理';
            break;
        }
        return processstatus;
      }},
      {data: 'pprocessstatus', render: function(data) {
        let changeButton = '';
        switch (data) {
          case 1:
            changeButton = '<select class="selectpicker" data-size="5" onchange="changeStatus(this)">';
            changeButton += '<option selected disabled>請選擇</option>';
            changeButton += '<option value="2">已結案</option>';
            changeButton += '<option value="3">取消訂單</option>';
            changeButton += '</select>';
            changeButton += '<button onclick="saveStatus(this)" class="save-status">儲存</button>';
            return changeButton;
          case 2:
            break;
          case 3:
            break;
          case 4:
            changeButton = '<select class="selectpicker" data-size="5" onchange="changeStatus(this)">';
            changeButton += '<option selected disabled>請選擇</option>';
            changeButton += '<option value="3">取消訂單</option>';
            changeButton += '</select>';
            changeButton += '<button onclick="saveStatus(this)" class="save-status">儲存</button>';
            break;
          default:
            changeButton = '<select class="selectpicker" data-style="btn-primary" onchange="changeStatus(this)">';
            changeButton += '<option selected disabled>請選擇</option>';
            changeButton += '<option value="1">配送中</option>';
            changeButton += '<option value="3">取消訂單</option>';
            changeButton += '</select>';
            changeButton += '<button onclick="saveStatus(this)" class="save-status">儲存</button>';
            break;
        }
        return changeButton;
      }}
    ]
  });

  $('#orderInput').on('keyup', function () {
    orderTable.search(this.value).draw();
  });

  });
  
  // Function to view delivery information
  // window.viewDelivery = function(img) {
  //   const rowData = orderTable.row($(img).parents('tr')).data();
  //   const deliveryInfo = {
  //     name: rowData.pname,
  //     phone: rowData.pphone,
  //     address: rowData.paddress
  //   };
  //   $('#deliveryModalTitle').text(`訂單編號 #${rowData.porderno}`);
  //   $('#deliveryName').text(deliveryInfo.name);
  //   $('#deliveryPhone').text(deliveryInfo.phone);
  //   $('#deliveryAddress').text(deliveryInfo.address);
  // }
  
// // Function to view delivery information
//   function viewDelivery(img) {
//     const rowData = table.row($(img).parents('tr')).data();
//     const deliveryInfo = {
//       name: rowData.pname,
//       phone: rowData.pphone,
//       address: rowData.paddress
//     };
//     $('#deliveryModalTitle').text(`訂單編號 #${rowData.porderno}`);
//     $('#deliveryName').text(deliveryInfo.name);
//     $('#deliveryPhone').text(deliveryInfo.phone);
//     $('#deliveryAddress').text(deliveryInfo.address);
//   }

function viewDelivery(no) {
  fetch(`/porder/getporderbyid?id=${no}`, {
    method: 'GET',
  }).then(response => response.json())
    .then(data => {
      console.log(data);
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

// Function to change order process status
$('#orderTable').on('change', '.filter-option', function() {
  const selectValue = $(this).val();
  const rowData = table.row($(this).parents('tr')).data();
  const orderNo = rowData.porderno;
  $.ajax({
    url: '/porder/updatestatusbyid',
    method: 'POST',
    data: {
      orderNo: orderNo,
      processStatus: selectValue
    },
    success: function(data) {
      alert('訂單狀態已更改');
      table.ajax.reload();
    },
    error: function() {
      alert('訂單狀態更改失敗');
    }
  });
})