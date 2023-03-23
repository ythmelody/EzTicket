$(document).ready(() => {
  fetchPorderList();
});

$('.tab-link[data-bs-target="#orders-tab"]').click(() => {
  fetchPorderList();
});


function fetchPorderList() {
  fetch(`/html/porderlist`, {
    method: 'GET',
  }).then(response => response.json())
    .then(data => {
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
          default:
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
          default:
            processstatus = '<span class="status-circle blue-circle"></span>未處理';
            break;
        }

        return `<tr>
                          <td>${obj.porderno}</td>
                          <td>${obj.memberno}</td>
                          <td>${obj.ptotal}</td>
                          <td>${obj.porderdate}</td>
                          <td>
                            <div class="img-btn" data-bs-toggle="modal" data-bs-target="#DeliveryModal">
                              <img src="images/icons/truck2.png" alt="配送資訊">
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
                            <button>儲存</button>
                          </td>
                      </tr>`;
      }).join('');
      tbody.innerHTML = extraCells;
    })
    .catch(error => console.error(error));
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
    default:
      statusTd.innerHTML = '<span class="status-circle blue-circle"></span>未處理';
      break;
  }
}