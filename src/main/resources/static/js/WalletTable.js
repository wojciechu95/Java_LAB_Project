// const swalWithBootstrapButtons = swal.mixin({
//     customClass: {
//         confirmButton: 'btn btn-success',
//         cancelButton: 'btn btn-danger'
//     },
//     buttonsStyling: false
// })

let dataTable;

$(document).ready(function () {
    loadDataTable();
});

function loadDataTable() {
    let isD = false;
    dataTable = $('#DT_load').DataTable({
        "ajax": {
            "url": "pwallet/getall",
            "type": "GET",
            "datatype": "json",
        },
        "columns": [
            {
                data: "isDone",
                "render": function (data) {
                    let td = data
                        ? `<td class="text-center"><i class="text-success h3 fas fa-clipboard-check"></i></td>`
                        : `<td class="text-center"><i class="text-warning h3 fas fa-clipboard-check"></i></td>`;
                    isD = data;
                    return td;
                }, "width": "15%"
            },
            {data: "userName", "width": "10%"},
            {
                data: "description",
                "render": function (data) {
                    let td = !isD
                        ? `<td class="text-center">${data}</td>`
                        : `<td class="text-center"><del>${data}</del></td>`;
                    return td;
                }, "width": "30%"
            },
            {
                data: "targetDate",
                "render": function (data) {
                    let td = !isD
                        ? `<td class="text-center">${data}</td>`
                        : `<td class="text-center"><del>${data}</del></td>`;
                    return td;
                }, "width": "20%"
            },
            {
                data: "id",
                "render": function (data) {
                    return `<div class="text-center">
                        <a href="/pwallet/update-todo?id=${data}" class='btn btn-success text-white'
                            style='cursor:pointer;'>
                            Update
                        </a>
                        &nbsp;
                        <a class='btn btn-danger text-white' style='cursor:pointer;'
                            onclick=Delete('/pwallet/delete?id=${data}')>
                            Delete
                        </a>
                        </div>`;
                }, "width": "25%"
            }
        ],
        "language": {
            "emptyTable": "Nie znaleziono danych"
        },
        "width": "100%"
    });
}

function Delete(url) {
    swal.fire({
        title: "Czy jesteś pewien?",
        text: "Po usunięciu nie będzie można odzyskać",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: '#40be2d',
        cancelButtonColor: '#d33',
        cancelButtonText: 'Nie',
        confirmButtonText: 'Tak',
        reverseButtons: true
    }).then((result) => {
        if (result.value) {
            if (result.value) {
                $.ajax({
                    type: "DELETE",
                    url: url,
                    success: function (data) {
                        if (data.success) {
                            swal.fire(
                                'Usunięto',
                                'Element został usunięty.',
                                'success'
                            )
                            dataTable.ajax.reload();
                        } else {
                            toastr["error"]("", "Coś poszło nie tak");
                        }
                    }
                });
            }
        } else if (
            /* Read more about handling dismissals below */
            result.dismiss === Swal.DismissReason.cancel
        ) {
            swal.fire(
                'Anulowano',
                'Twoje dane są bezpieczne :)',
                'error'
            )
        }
    });
}