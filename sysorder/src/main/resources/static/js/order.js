$('#tbOrders').DataTable({
    dom: 'Brt'
});

function selectOrder(id) {
    $('#inputSelectedOrderId').val(id);
    $('#btOpenOrder').click();
}

function saveOrder() {
    if ($('#contactName').val() == null || $('#contactName').val() === "" ||
        $('#contactPhone').val() == null || $('#contactPhone').val() === "" ||
        $('#category').val() == null || $('#category').val() === "" ||
        $('#deadline').val() == null || $('#deadline').val() === "" ) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Contact Name, Contact Phone, Category and Deadline are required!'
        })
        return;
    }

    var json = {
        contactName: $('#contactName').val(),
        contactPhone: $('#contactPhone').val(),
        realEstateAgency: $('#realEstateAgency').val(),
        description: $('#orderDescription').val(),
        company: $('#company').val(),
        categoryId: $('#category').val(),
        deadline: $('#deadline').val()
    };
    $.ajax({
        type: "POST",
        contentType : 'application/json; charset=utf-8',
        data: JSON.stringify(json),
        url: '/orders',
        success :function(response) {
            Swal.fire({
                icon: 'success',
                title: 'Ok',
                text: 'Order save successfully!'
            }).then((result) => {
              window.open('/', '_self');
          })
        },
        error: function (jqXHR, exception) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'The order didn\'t be saved!'
            })
        },
        fail :function(result) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'The order didn\'t be saved!'
            })
        }
    })
}