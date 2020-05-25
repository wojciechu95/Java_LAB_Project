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
            "url": "/apitodos/getall",
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
                }, "width": "10%"
            },
            {data: "userName", "width": "10%"},
            {
                data: "description",
                "render": function (data) {
                    let td = !isD
                        ? `<td class="text-center">${data}</td>`
                        : `<td class="text-center"><del>${data}</del></td>`;
                    return td;
                }, "width": "35%"
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
                        <a class="btn btn-success text-white" style="cursor:pointer;"
                            onclick="Edit('/apitodos/getid?id=${data}','Update Todo')" >
                            Update
                        </a>
                        &nbsp;
                        <a class="btn btn-danger text-white" style="cursor:pointer;"
                            onclick="Delete('/apitodos/delete?id=${data}')">
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

Edit = (url, title) => {
    $.ajax({
        type: 'GET',
        url: url,
        success: function (todo) {

            let idD = todo.id
                ?`<input name="id" value="${todo.id}" hidden />`
                : ``;
            let des = todo.description
                ?`${todo.description}`
                : ``;
            let tD = todo.targetDate
                ?`${todo.targetDate}`
                : ``;
            let isD = todo.isDone
                ?`checked="checked" `
                : ``;

            $('#form-modal .modal-body').html(`         
            <form id="editApi" method="post">
                ${idD}
                <div class="form-group row">
                    <div class="col-3">
                        <label>Description</label>
                    </div>
                    <div class="col-6">
                        <input type="text" name="description" id="description" value="${des}" class="form-control"
                               required="required"/>
                    </div>
                </div>

                <div class="form-group row">
                    <div class="col-3">
                        <label>Target Date</label>
                    </div>
                    <div class="col-6">
                        <input type="text" name="targetDate" id="targetDate" value="${tD}" class="form-control"
                               required="required"/>
                    </div>
                </div>

                <div class="form-group row">
                    <div class="col-3">
                        <label>Checked</label>
                    </div>
                    <div class="col-6">
                        <input class="display-3" type="checkbox" name="isDone" id="isDone" value="true" 
                        ${isD}/> <i class="text-success h3 fas fa-clipboard-check"></i>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-3 offset-3">
                        <button type="submit" onclick="jQueryAjaxPost()" class="btn btn-primary form-control">
                            Save
                        </button>
                    </div>
                    </div>
                </div>
            </form>

            <script>
                $(function () {
                    $("#targetDate").datetimepicker({
                        format: 'd.m.Y H:i'
                    });
                });
            </script>
        </div>
            `);
            $('#form-modal .modal-title').html(title);
            $('#form-modal').modal('show');
        }
    })
}

jQueryAjaxPost = form => {
    try {
        $("#editApi").submit(function(e) {
            form = $(this);
            console.log(form.serialize());

        $.ajax({
            type: 'POST',
            url: '/apitodos/upsert',
            data: form.serialize(),
            success: function (todo) {
                console.log('success')
                if (todo) {
                    $('#view-all').html(todo)
                    $('#form-modal .modal-body').html('');
                    $('#form-modal .modal-title').html('');
                    $('#form-modal').modal('hide');
                    dataTable.ajax.reload();
                }
                else
                    $('#form-modal .modal-body').html(todo.html);
            },
            error: function (err) {
                console.log('Error Ajax')
                console.log(err)
            }
        })
        //to prevent default form submit event
        return false;
        })
    } catch (ex) {

        console.log('Exception')
        console.log(ex)
    }
}