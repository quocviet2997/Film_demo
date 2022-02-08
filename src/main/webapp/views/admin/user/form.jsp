<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="AddUrl" value="/api-user/new"/>
<c:url var ="NewURL" value="/api-user"/>

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
                    <h1 class="text-center">User Form</h2>
                </caption>
                <form id="formSubmit">
                    <input type="hidden" name="id" value="<c:out value='${id}' />" id="id"/>
                    <fieldset class="form-group">
                        <label>Username</label> 
                        <input type="text" class="form-control" name="userName" id="userName" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Password</label> 
                        <input type="text" class="form-control" name="password" id="password" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Role</label> 
                        <input type="text" class="form-control" name="role" id="role" required="required">
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
                userInfo(id);
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
                addUser(data);
            } else {
                updateUser(data);
            }
        });
        function addUser(data) {
            $.ajax({
                url: '${AddUrl}',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (result) {
                    window.location.href = "<c:url value='/admin-api-user'/>";
                },
                error: function (error) {
                    alert("Insert failed");
                }
            });
        }

        function updateUser(data) {
            $.ajax({
                url: '${NewURL}/'+$('#id').val(),
                method: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (result) {
                    window.location.href = "<c:url value='/admin-api-user'/>";
                },
                error: function (error) {
                    alert("Update failed");
                }
            });
        }
        function userInfo(id) {
             $.ajax({
                url: '${NewURL}/'+id,
                method: 'GET',
                contentType: 'application/json',
                dataType: 'json',
                success: function (result) {
                    $('#userName').val(result.data.userName);
                    $('#password').val(result.data.password);
                    $('#role').val(result.data.role);
                },
                error: function (error) {
                    alert("Update failed");
                }
            });
        }

    </script>
</body>
</html>