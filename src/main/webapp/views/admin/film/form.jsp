<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="AddUrl" value="/api-film/new"/>
<c:url var ="NewURL" value="/api-film"/>

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
                    <h1 class="text-center">Film Form</h2>
                </caption>
                <form id="formSubmit" enctype="multipart/form-data">
                    <input type="hidden" name="id" value="<c:out value='${id}' />" id="id"/>
                    <fieldset class="form-group">
                        <label>Title</label> 
                        <input type="text" class="form-control" name="title" id="title" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Poster</label> 
                        <input type="file" class="form-control" name="poster" id="poster" required="required">
                    </fieldset>
                    <div class="container" style="text-align: center;" id="imgContainer">
                    </div>

                    <fieldset class="form-group">
                        <label>Year</label>
                            <input type="number" step="1" class="form-control" name="year" id="year">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>ShortDescription</label> <input type="text" class="form-control" name="shortDescription" id="shortDescription" required="required">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>Description</label> <input type="text" class="form-control" name="description" id="description" required="required">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>AvgVote</label> <input type="number" step="0.1" class="form-control" name="avgVote" id="avgVote">
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
                filmInfo(id);
            }
        }
        $('#btnAddOrUpdateNew').click(function (e) {
            e.preventDefault();
            var data = new FormData();
            var image = document.getElementById('poster').files;
            if(image!= null && image.length>0){
                data.append('file', image[0]);
            }
            var formData = $('#formSubmit').serializeArray();
            $.each(formData, function (i, v) {
                data.append(v.name, v.value);
            });
            var id = $('#id').val();
            if (id == "" || id == null || typeof id === 'undefined') {
                addFilm(data);
            } else {
                updateFilm(data);
            }
        });
        function addFilm(data) {
            $.ajax({
                url: '${AddUrl}',
                method: 'POST',
                cache: false,
                contentType: false,
                processData: false,
                data: data,
                success: function (result) {
                    window.location.href = "<c:url value='/admin-api-film'/>";
                },
                error: function (error) {
                    alert("Insert failed");
                    // window.location.href = "${NewURL}";
                }
            });
        }

        function updateFilm(data) {
            $.ajax({
                url: '${NewURL}/'+$('#id').val(),
                method: 'PUT',
                cache: false,
                contentType: false,
                processData: false,
                data: data,
                success: function (result) {
                    window.location.href = "<c:url value='/admin-api-film'/>";
                },
                error: function (error) {
                    alert("Update failed");
                    // window.location.href = "${NewURL}";
                }
            });
        }

        function filmInfo(id) {
             $.ajax({
                url: '${NewURL}/'+id,
                method: 'GET',
                contentType: 'application/json',
                dataType: 'json',
                success: function (result) {
                    $('#title').val(result.data.title);
                    $('#imgContainer').append(`<img class="card-img-top" style="width:80%; height:80%;" src="<c:url value='/resources/images/`+result.data.poster+`' />" alt="`+result.data.title+`">`);
                    $('#year').val(result.data.year);
                    $('#shortDescription').val(result.data.shortDescription);
                    $('#description').val(result.data.description);
                    $('#avgVote').val(result.data.avgVote);
                },
                error: function (error) {
                    alert("Update failed");
                    // window.location.href = "${NewURL}";
                }
            });
        }

    </script>
</body>
</html>