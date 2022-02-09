<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="AddUrl" value="/api-category/new"/>
<c:url var ="CategoryUrl" value="/api-category"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Film demo</title>
<link rel="shortcut icon" href="#">
<link href="<c:url value='/template/web/bootstrap/css/bootstrap.min.css' />" rel="stylesheet" type="text/css" media="all"/>
<link href="<c:url value='/template/web/css/style.css' />" rel="stylesheet" type="text/css" media="all"/>
</head>
<body  onload="loadForm()">
    <%@ include file="/common/web/admin-header.jsp"%>
    <div class="container col-md-5">
        <div class="card">
            <div class="card-body">
                <caption>
                    <h1 class="text-center">Category Form</h2>
                </caption>
                <form id="formSubmit">
                    <input type="hidden" name="id" value="<c:out value='${id}' />" id="id"/>

                    <fieldset class="form-group">
                        <label>Category Name</label>
                            <input type="text" class="form-control" name="categoryName" id="categoryName">
                    </fieldset>

                    <button type="submit" class="btn btn-success" id="btnAddOrUpdateNew">Submit</button>
                </form>
            </div>
        </div>
    </div>
    <%@ include file="/common/web/footer.jsp"%>
    <script type="text/javascript" src="<c:url value='/template/web/jquery/jquery.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/template/web/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/template/common/loadUserInfo.js'/>"></script>
    <script>
        function loadForm() {
            var id = $('#id').val();
            loadUserInfo();
            if(id != "" && id != null && typeof id !== 'undefined'){
                categoryInfo(id);
            }
        }
        $('#btnAddOrUpdateNew').click(function (e) {
            e.preventDefault();
            var data = {};
            var formData = $('#formSubmit').serializeArray();
            $.each(formData, function (i, v) {
                data[""+v.name+""] = v.value;
            });
            var id = $('#id').val();
            if (id == "" || id == null || typeof id === 'undefined') {
                addCategory(data);
            } else {
                updateCategory(data);
            }
        });
        function addCategory(data) {
            $.ajax({
                url: '${AddUrl}',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (result) {
                    window.location.href = "<c:url value='/admin-api-category'/>";
                },
                error: function (error) {
                    alert("Insert failed");
                }
            });
        }

        function updateCategory(data) {
            $.ajax({
                url: '${CategoryUrl}/'+$('#id').val(),
                method: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (result) {
                    window.location.href = "<c:url value='/admin-api-category'/>";
                },
                error: function (error) {
                    alert("Update failed");
                }
            });
        }
        function categoryInfo(id) {
             $.ajax({
                url: '${CategoryUrl}/'+id,
                method: 'GET',
                contentType: 'application/json',
                dataType: 'json',
                success: function (result) {
                    $('#categoryName').val(result.data.categoryName);
                },
                error: function (error) {
                    alert("Update failed");
                }
            });
        }

    </script>
</body>
</html>