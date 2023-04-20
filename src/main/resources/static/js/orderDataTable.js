$(document).ready(function() {
  const orderTable = $('#orderTable').DataTable({
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
        return data;
        return moment(data).format('YYYY-MM-DD HH:mm:ss');
      }},
      {data: null, render: function(data) {
        return '<div class="img-btn" data-bs-toggle="modal" data-bs-target="#DeliveryModal"><img onclick="viewDelivery(this)" src="images/icons/truck2.png" alt="配送資訊"></div>';
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
        let changeButton = '';
        switch (data) {
          case 1:
            processstatus = '<span class="status-circle yellow-circle"></span>配送中';
            changeButton = '<select class="filter-option" onchange="changeStatus(this)">';
            changeButton += '<option value="1">配送中</option>';
            changeButton += '<option value="2">已送達</option>';
            changeButton += '<option value="3">取消訂單</option>';
            changeButton += '</select>';
            return processstatus + changeButton;
          case 2:
            processstatus = '<span class="status-circle green-circle"></span>已送達';
            break;
          case 3:
            processstatus = '<span class="status-circle red-circle"></span>已取消';
            break;
          default:
            processstatus = '<span class="status-circle red-circle"></span>未知狀態';
            break;
        }
        return processstatus;
      }}
    ]
  });
  });
  
  // Function to view delivery information
  window.viewDelivery = function(img) {
    const rowData = orderTable.row($(img).parents('tr')).data();
    const deliveryInfo = {
      name: rowData.pname,
      phone: rowData.pphone,
      address: rowData.paddress
    };
    $('#deliveryModalTitle').text(`訂單編號 #${rowData.porderno}`);
    $('#deliveryName').text(deliveryInfo.name);
    $('#deliveryPhone').text(deliveryInfo.phone);
    $('#deliveryAddress').text(deliveryInfo.address);
  }
  
// Function to view delivery information
  function viewDelivery(img) {
    const rowData = table.row($(img).parents('tr')).data();
    const deliveryInfo = {
      name: rowData.pname,
      phone: rowData.pphone,
      address: rowData.paddress
    };
    $('#deliveryModalTitle').text(`訂單編號 #${rowData.porderno}`);
    $('#deliveryName').text(deliveryInfo.name);
    $('#deliveryPhone').text(deliveryInfo.phone);
    $('#deliveryAddress').text(deliveryInfo.address);
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
});
