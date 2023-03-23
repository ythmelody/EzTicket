
$(document).ready(() => {
  let urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get('id');
  fetch(`/html/GetPorderDetailsByID?id=${id}`, {
    method: 'GET',
  })
});
