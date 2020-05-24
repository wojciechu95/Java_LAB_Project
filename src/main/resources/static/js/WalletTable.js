var dataTable;

$(document).ready(function () {
    loadDataTable();
});

function loadDataTable() {
    var id = 0;

    dataTable = $('#DT_load').DataTable({
        "ajax": {
            "url": "pwallet/getall",
            "type": "GET",
            "dataType": "json"
        },
        "columns": [
            { "isDone": "isDone", "width": "10%" },
            { "userName": "userName", "width": "10%" },
            { "description": "description", "width": "30%"},
            { "targetDate": "targetDate", "width": "30%"},
            { "id": "id", "width": "20%"}
        ],
        "language": {
            "emptyTable": "Nie znaleziono danych"
        },
        "width": "100%"
    });
}

function Delete(url) {

}