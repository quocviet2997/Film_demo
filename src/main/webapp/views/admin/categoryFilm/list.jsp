<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<c:url var="APIurl" value="/api-category-film"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Film demo</title>
	<link rel="shortcut icon" href="#">
	<link href="<c:url value='/template/web/css/style.css' />" rel="stylesheet" type="text/css" media="all"/>
	<link href="<c:url value='/template/web/bootstrap/css/bootstrap.min.css' />" rel="stylesheet" type="text/css" media="all"/>
</head>
<body onload="loadCategoryFilm()">
  <%@ include file="/common/web/admin-header.jsp"%>
  <form action="<c:url value='/admin-api-category-film'/>" id="formSubmit" method="get">
  <div class="myContainer">
		<h1 class="text-center">List of Category_Film</h3>
		<hr>
		<div class="text-left">
			<a href="<c:url value='/admin-api-category-film/new' />" class="btn btn-success">Add New Category_Film</a>
		</div>
		<br>
		<table class="table table-bordered" id="categoryFilmTable">
			<thead>
				<tr>
					<th class="text-center">ID</th>
					<th class="text-center">Category</th>
					<th class="text-center">Film</th>
					<th class="text-center">Action</th>
				</tr>
			</thead>

			<tbody>
			</tbody>
		</table>
	</div>
	<ul class="pagination justify-content-center" id="pagination"></ul>
	<input type="hidden" value="<c:out value='${page}' />" id="page" name="page"/>
	<input type="hidden" value="<c:out value='${limit}' />" id="limit" name="limit"/>
	</form>
  <%@ include file="/common/web/footer.jsp"%>
  <script type="text/javascript" src="<c:url value='/template/web/jquery/jquery.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/template/web/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
  <script src="<c:url value='/template/paging/js/jquery.twbsPagination.js' />"></script>
  <script type="text/javascript" src="<c:url value='/template/common/loadUserInfo.js'/>"></script>
  <script type="text/javascript">
		function loadCategoryFilm(){
			var currentPage = $('#page').val();
			var limit = $('#limit').val();
			loadUserInfo();
			showList(currentPage, limit);
		}

		function showList(currentPage, limit) {
			$.ajax({
				url: '${APIurl}',
				method: 'get',
				contentType: 'application/json',
				data: {page: currentPage, limit: limit},
				success: function (result) {
					$.each(result.data, (index, row) => {
						const rowContent 
						= `<tr>
							<td>`+row.id+`</td>
							<td>`+row.categoryName+`</td>
							<td>`+row.filmName+`</td>
							<td><div class="myMargin">
									<input type="button" class="btn btn-secondary btn-block" onclick="location.href='/admin-api-category-film/`+ row.id+`';" value="Edit" />
									<input type="button" class="btn btn-secondary btn-block" onclick="deleteCategoryFilm(`+ row.id+`);" value="Delete" />
								</div>
							</td>
						   </tr>`;
						$('#categoryFilmTable tbody').append(rowContent);
					});
					totalPages = result.totalPage;
					var visiblePages = 5;
					if(visiblePages > totalPages){
						visiblePages = totalPages;
					}
					window.pagObj = $('#pagination').twbsPagination({
						totalPages: totalPages,
						visiblePages: visiblePages,
						startPage: currentPage,
						onPageClick: function (event, page) {
							if (currentPage != page) {
								$('#limit').val(limit);
								$('#page').val(page);
								$('#formSubmit').submit();
							}
						}
					});
				},
				error: function (error) {
					alert("Something failed! " + error);
				}
			});
		}

		function deleteCategoryFilm(id) {
			$.ajax({
				url: '${APIurl}/'+id,
				method: 'delete',
				contentType: 'application/json',
				success: function (result) {
					location.reload();
					console.log("Success");
				},
				error: function (error) {
					alert("Something failed! " + error);
				}
			});
		}
	</script>
</body>
</html>