<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="AddUrl" value="/api-comment/new"/>
<c:url var ="CommentUrl" value="/api-comment"/>

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
                    <h1 class="text-center">Comment Form</h2>
                </caption>
                <form id="formSubmit">
                    <input type="hidden" name="id" value="<c:out value='${id}' />" id="id"/>
                    <fieldset class="form-group">
                        <label>FilmId</label> 
                        <input type="number" step="1" class="form-control" name="filmId" id="filmId" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>UserId</label> 
                        <input type="number" step="1" class="form-control" name="userId" id="userId" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Comment</label>
                            <input type="text" class="form-control" name="comment" id="comment">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>ReplyId</label> 
                        <input type="number" step="1" class="form-control" name="replyId" id="replyId" required="required">
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
                commentInfo(id);
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
                addComment(data);
            } else {
                updateComment(data);
            }
        });
        function addComment(data) {
            $.ajax({
                url: '${AddUrl}',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (result) {
                    window.location.href = "<c:url value='/admin-api-comment'/>";
                },
                error: function (error) {
                    alert("Insert failed");
                }
            });
        }

        function updateComment(data) {
            $.ajax({
                url: '${CommentUrl}/'+$('#id').val(),
                method: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (result) {
                    window.location.href = "<c:url value='/admin-api-comment'/>";
                },
                error: function (error) {
                    alert("Update failed");
                }
            });
        }
        function commentInfo(id) {
             $.ajax({
                url: '${CommentUrl}/'+id,
                method: 'GET',
                contentType: 'application/json',
                dataType: 'json',
                success: function (result) {
                    $('#filmId').val(result.data.filmId);
                    $('#userId').val(result.data.userId);
                    $('#comment').val(result.data.comment);
                    $('#replyId').val(result.data.replyId);
                },
                error: function (error) {
                    alert("Update failed");
                }
            });
        }

    </script>
</body>
</html>