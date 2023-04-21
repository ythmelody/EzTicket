// 定義Table
let orderable = "";

$(document).ready(function() {
  let payCheckStatus;
    orderTable = $('#orderTable').DataTable({
    "aaSorting": [[ 0, "desc" ]],
    searchDelay: 350,
    autoWidth: true,
    bFilter: true,
    lengthMenu: [[10, 20, 30, -1], [10, 20, 30, '全部']],
    searching: true,
    info: true,
    "language": {
      "processing": "處理中...",
      "loadingRecords": "載入中...",
      "paginate": {
          "first": "第一頁",
          "previous": "上一頁",
          "next": "下一頁",
          "last": "最後一頁"
      },
      "emptyTable": "目前沒有資料",
      "datetime": {
          "previous": "上一頁",
          "next": "下一頁",
          "hours": "時",
          "minutes": "分",
          "seconds": "秒",
          "amPm": [
              "上午",
              "下午"
          ],
          "unknown": "未知",
          "weekdays": [
              "週日",
              "週一",
              "週二",
              "週三",
              "週四",
              "週五",
              "週六"
          ],
          "months": [
              "一月",
              "二月",
              "三月",
              "四月",
              "五月",
              "六月",
              "七月",
              "八月",
              "九月",
              "十月",
              "十一月",
              "十二月"
          ]
      },
      "searchBuilder": {
          "add": "新增條件",
          "condition": "條件",
          "deleteTitle": "刪除過濾條件",
          "button": {
              "_": "複合查詢 (%d)",
              "0": "複合查詢"
          },
          "clearAll": "清空",
          "conditions": {
              "array": {
                  "contains": "含有",
                  "equals": "等於",
                  "empty": "空值",
                  "not": "不等於",
                  "notEmpty": "非空值",
                  "without": "不含"
              },
              "date": {
                  "after": "大於",
                  "before": "小於",
                  "between": "在其中",
                  "empty": "為空",
                  "equals": "等於",
                  "not": "不為",
                  "notBetween": "不在其中",
                  "notEmpty": "不為空"
              },
              "number": {
                  "between": "在其中",
                  "empty": "為空",
                  "equals": "等於",
                  "gt": "大於",
                  "gte": "大於等於",
                  "lt": "小於",
                  "lte": "小於等於",
                  "not": "不為",
                  "notBetween": "不在其中",
                  "notEmpty": "不為空"
              },
              "string": {
                  "contains": "含有",
                  "empty": "為空",
                  "endsWith": "字尾為",
                  "equals": "等於",
                  "not": "不為",
                  "notEmpty": "不為空",
                  "startsWith": "字首為",
                  "notContains": "不含",
                  "notStartsWith": "開頭不是",
                  "notEndsWith": "結尾不是"
              }
          },
          "data": "欄位",
          "leftTitle": "群組條件",
          "logicAnd": "且",
          "logicOr": "或",
          "rightTitle": "取消群組",
          "title": {
              "_": "複合查詢 (%d)",
              "0": "複合查詢"
          },
          "value": "內容"
      },
      "editor": {
          "close": "關閉",
          "create": {
              "button": "新增",
              "title": "新增資料",
              "submit": "送出新增"
          },
          "remove": {
              "button": "刪除",
              "title": "刪除資料",
              "submit": "送出刪除",
              "confirm": {
                  "_": "您確定要刪除您所選取的 %d 筆資料嗎？",
                  "1": "您確定要刪除您所選取的 1 筆資料嗎？"
              }
          },
          "error": {
              "system": "系統發生錯誤(更多資訊)"
          },
          "edit": {
              "button": "修改",
              "title": "修改資料",
              "submit": "送出修改"
          },
          "multi": {
              "title": "多重值",
              "info": "您所選擇的多筆資料中，此欄位包含了不同的值。若您想要將它們都改為同一個值，可以在此輸入，要不然它們會保留各自原本的值。",
              "restore": "復原",
              "noMulti": "此輸入欄需單獨輸入，不容許多筆資料一起修改"
          }
      },
      "autoFill": {
          "cancel": "取消"
      },
      "buttons": {
          "copySuccess": {
              "_": "複製了 %d 筆資料",
              "1": "複製了 1 筆資料"
          },
          "copyTitle": "已經複製到剪貼簿",
          "excel": "Excel",
          "pdf": "PDF",
          "print": "列印",
          "copy": "複製",
          "colvis": "欄位顯示",
          "colvisRestore": "重置欄位顯示",
          "csv": "CSV",
          "pageLength": {
              "-1": "顯示全部",
              "_": "顯示 %d 筆"
          },
          "createState": "建立狀態",
          "removeAllStates": "移除所有狀態",
          "removeState": "移除",
          "renameState": "重新命名",
          "savedStates": "儲存狀態",
          "stateRestore": "狀態 %d",
          "updateState": "更新"
      },
      "searchPanes": {
          "collapse": {
              "_": "搜尋面版 (%d)",
              "0": "搜尋面版"
          },
          "emptyPanes": "沒搜尋面版",
          "loadMessage": "載入搜尋面版中...",
          "clearMessage": "清空",
          "count": "{total}",
          "countFiltered": "{shown} ({total})",
          "title": "過濾條件 - %d",
          "showMessage": "顯示全部",
          "collapseMessage": "摺疊全部"
      },
      "stateRestore": {
          "emptyError": "名稱不能空白。",
          "creationModal": {
              "button": "建立",
              "columns": {
                  "search": "欄位搜尋",
                  "visible": "欄位顯示"
              },
              "name": "名稱：",
              "order": "排序",
              "paging": "分頁",
              "scroller": "卷軸位置",
              "search": "搜尋",
              "searchBuilder": "複合查詢",
              "select": "選擇",
              "title": "建立新狀態",
              "toggleLabel": "包含："
          },
          "duplicateError": "此狀態名稱已經存在。",
          "emptyStates": "名稱不可空白。",
          "removeConfirm": "確定要移除 %s 嗎？",
          "removeError": "移除狀態失敗。",
          "removeJoiner": "和",
          "removeSubmit": "移除",
          "removeTitle": "移除狀態",
          "renameButton": "重新命名",
          "renameLabel": "%s 的新名稱：",
          "renameTitle": "重新命名狀態"
      },
      "select": {
          "columns": {
              "_": "選擇了 %d 欄資料",
              "1": "選擇了 1 欄資料"
          },
          "rows": {
              "1": "選擇了 1 筆資料",
              "_": "選擇了 %d 筆資料"
          },
          "cells": {
              "1": "選擇了 1 格資料",
              "_": "選擇了 %d 格資料"
          }
      },
      "zeroRecords": "沒有符合的資料",
      "aria": {
          "sortAscending": "：升冪排列",
          "sortDescending": "：降冪排列"
      },
      "info": "顯示第 _START_ 至 _END_ 筆結果，共 _TOTAL_ 筆",
      "infoEmpty": "顯示第 0 至 0 筆結果，共 0 筆",
      "infoFiltered": "(從 _MAX_ 筆結果中過濾)",
      "infoThousands": ",",
      "lengthMenu": "顯示 _MENU_ 筆結果",
      "search": "搜尋：",
      "searchPlaceholder": "請輸入關鍵字",
      "thousands": ","
     } ,
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
        payCheckStatus = data;
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
        changeButton = '<select style="display:block!important;" class="selectpicker" onchange="changeStatus(this)">';
        changeButton += '<option selected disabled>請選擇狀態</option>';
        switch (data) {
          case 1:
            changeButton += '<option value="2">已結案</option>';
            changeButton += '<option value="3">取消訂單</option>';
            break;
          case 2:
            break;
          case 3:
            break;
          case 4:
            changeButton += '<option value="3">取消訂單</option>';
            break;
          default:
            if(payCheckStatus == 1){changeButton += '<option value="1">配送中</option>';}
            changeButton += '<option value="3">取消訂單</option>';
            break;
        }
        changeButton += '</select>';
        if(data == 2 || data == 3 ){
          changeButton += '<button disabled class="save-status main-btn btn-hover h_25 w-40">儲存</button>';
        } else {
          changeButton += '<button onclick="saveStatus(this)" class="save-status main-btn btn-hover h_25 w-40">儲存</button>';
        }
        return changeButton;
      }}
    ]
  });
  // 隱藏預設篩選器
  $('#orderTable_filter').hide();
  // 監控自定義搜尋框
  $('#orderInput').on('keyup', function () {
    orderTable.search(this.value).draw();
  });
  });

function viewDelivery(no) {
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

function saveStatus(button) {
  const status = button.closest('tr').querySelector('select').value;
  const no = Number(button.closest('tr').querySelector('tr td').textContent);
  if (status !== '請選擇狀態') {
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
            orderTable.draw(false);
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
$('#reload').on('click', function() {
  let table = $('#orderTable').DataTable();
  table.ajax.reload();
});
