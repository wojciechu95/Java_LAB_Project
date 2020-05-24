showInPopup = (url, title) => {
    $.ajax({
        type: 'GET',
        url: url,
        success: function (todo) {

            let isD = todo.isDone
                ?`checked="checked" `
                : ``;

            $('#form-modal .modal-body').html(`         
            <form action="/pwallet/add-todo" method="post">
                <input hidden value="${todo.id}"/>

                <div class="form-group row">
                    <div class="col-3">
                        <label path="description">Description</label>
                    </div>
                    <div class="col-6">
                        <input type="text" name="description" id="description" value="${todo.description}" class="form-control"
                               required="required"/>
                    </div>
                </div>

                <div class="form-group row">
                    <div class="col-3">
                        <label path="targetDate">Target Date</label>
                    </div>
                    <div class="col-6">
                        <input type="text" name="targetDate" id="targetDate" value="${todo.targetDate}" class="form-control"
                               required="required"/>
                    </div>
                </div>

                <div class="form-group row">
                    <div class="col-3">
                        <label path="targetDate">Checked </label>
                    </div>
                    <div class="col-6">
                        <input class="display-3" type="checkbox" name="isDone" id="isDone" value="${todo.isDone}" 
                        ${isD} /> <i class="text-success h3 fas fa-clipboard-check"></i>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-3 offset-3">
                        <button type="submit" class="btn btn-primary form-control">
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
        $.ajax({
            type: 'POST',
            url: form.action,
            data: new FormData(form),
            contentType: false,
            processData: false,
            success: function (res) {
                if (res.isValid) {
                    $('#view-all').html(res.html)
                    $('#form-modal .modal-body').html('');
                    $('#form-modal .modal-title').html('');
                    $('#form-modal').modal('hide');
                }
                else
                    $('#form-modal .modal-body').html(res.html);
            },
            error: function (err) {
                console.log(err)
            }
        })
        //to prevent default form submit event
        return false;
    } catch (ex) {
        console.log(ex)
    }
}