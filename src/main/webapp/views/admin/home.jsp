<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Film demo</title>
<link href="<c:url value='/template/web/bootstrap/css/bootstrap.min.css' />" rel="stylesheet" type="text/css" media="all"/>
<link href="<c:url value='/template/web/css/style.css' />" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
  <%@ include file="/common/web/admin-header.jsp"%>
	<div class="row">
    <div class="col-lg-3">
      <h1 class="my-4">Home Page</h1>
      <div class="list-group">
        <a href="<c:url value='/admin-api-film'/>" class="list-group-item">List Film</a>
        <a href="<c:url value='/admin-api-comment'/>" class="list-group-item">List Comment</a>
        <a href="<c:url value='/admin-api-user'/>" class="list-group-item">List User</a>
        <a href="<c:url value='/admin-api-category'/>" class="list-group-item">List Category</a>
        <a href="<c:url value='/admin-api-category-film'/>" class="list-group-item">List Category_Film</a>
      </div>
    </div>
  </div>
  <%@ include file="/common/web/footer.jsp"%>
  <script type="text/javascript" src="<c:url value='/template/web/jquery/jquery.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/template/web/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/template/common/loadUserInfo.js'/>"></script>
  <script>
    $(function() {
      loadUserInfo();
    });
  </script>
</body>
</html>