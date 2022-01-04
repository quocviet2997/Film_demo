<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="LoginUrl" value="/api-login"/>
<c:url var="HomeUrl" value="/home"/>
<c:url var="AdminUrl" value="/admin-home"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">    
	<link href="<c:url value='/template/login/css/style.css'/>" rel="stylesheet">
</head>
<body>
    <div class="sidenav">
        <div class="login-main-text">
            <h2>Application<br> Login Page</h2>
            <p>Login or register from here to access.</p>
        </div>
    </div>
    <div class="main">
        <div class="col-md-6 col-sm-12">
            <div class="login-form">
                <form id="formLogin">
                    <fieldset class="form-group">
                        <label>User Name</label>
                        <input type="text" class="form-control" name="userName" id="userName" placeholder="User Name" required="required">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>Password</label>
                        <input type="password" class="form-control" name="password" id="password" placeholder="Password" required="required">
                    </fieldset>
                    <p id="response"></p>
                    <input type="hidden" name="role" id="role" value="user">
                    <button type="submit" class="btn btn-black" id="buttonLogin">Login</button>
                    <button type="submit" class="btn btn-secondary" id="buttonRegister">Register</button>
                </form>
            </div>
        </div>
    </div>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script>	
        $('#buttonLogin').click(function (e) {
            e.preventDefault();
            var data = {};
            var formData = $('#formLogin').serializeArray();
            $.each(formData, function (i, v) {
                data[""+v.name+""] = v.value;
            });
            login(data);
        });

        $('#buttonRegister').click(function (e) {
            e.preventDefault();
            var data = {};
            var formData = $('#formLogin').serializeArray();
            $.each(formData, function (i, v) {
                data[""+v.name+""] = v.value;
            });
            register(data);
        });

        function login(data) {
            $.ajax({
                url: '${LoginUrl}',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (result) {
                    localStorage.setItem("userName", result.data.userName);
                    localStorage.setItem("userId", result.data.id);
                    if(result.data.role == "admin")
                        window.location.href = '${AdminUrl}';
                    else if(result.data.role == "user")
                        window.location.href = '${HomeUrl}';
                },
                error: function (error) {
                    $('#response').text(error.responseJSON.message);
                }
            });
        }
        function register(data) {
            $.ajax({
                url: '${LoginUrl}/new',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (result) {
                    window.location.href = "<c:url value='/login'/>";
                    $('#response').text('Register success');
                },
                error: function (error) {
                    $('#response').text(error.responseJSON.message);
                }
            });
        }
    </script>
</body>
</html>