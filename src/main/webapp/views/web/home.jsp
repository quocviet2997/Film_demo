<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<c:url var="APIurl" value="/api-film"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Film demo</title>
<link href="<c:url value='/template/web/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css" media="all"/>
<link href="<c:url value='/template/web/css/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
</head>
<body onload="loadFilm()">
  <%@ include file="/common/web/header.jsp"%>
	<div class="row">
    <div class="col-lg-3">
      <h1 class="my-4">Film Demo</h1>
      <!--
      <div class="list-group">
        <a href="#" class="list-group-item">Category 1</a>
        <a href="#" class="list-group-item">Category 2</a>
        <a href="#" class="list-group-item">Category 3</a>
      </div>-->
    </div> 
    <!-- /.col-lg-3 -->

    <div class="col-lg-9">
      <div class="container">
        <div id="carouselExampleIndicators" class="carousel slide my-4 w-75" data-ride="carousel">
          <ol class="carousel-indicators">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
          </ol>
          <div class="carousel-inner" role="listbox">
            <div class="carousel-item active">
              <a href="<c:url value='/1'/>">
                <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="First film">
              </a>
            </div>
            <div class="carousel-item">
              <a href="<c:url value='/2'/>">
                <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Second film">
              </a>
            </div>
            <div class="carousel-item">
              <a href="<c:url value='/3'/>">
                <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Third film">
              </a>
            </div>
          </div>
          <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
          </a>
          <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
          </a>
        </div>
      
        <form action="<c:url value=''/>" id="formSubmit" method="get">
          <div class="row" id="rowsPage">
          </div>
          <ul class="pagination justify-content-center" id="pagination"></ul>
          <input type="hidden" value="<c:out value='${page}' />" id="page" name="page"/>
          <input type="hidden" value="<c:out value='${limit}' />" id="limit" name="limit"/>
        </form>
      </div>
      <!-- /.row -->
    </div>
    <!-- /.col-lg-9 -->
  </div>
  <!-- /.row -->
  <%@ include file="/common/web/footer.jsp"%>
  
  <script type="text/javascript" src="<c:url value='/template/web/jquery/jquery.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/template/web/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
  <script src="<c:url value='/template/paging/js/jquery.twbsPagination.js' />"></script>
  <script type="text/javascript" src="<c:url value='/template/common/loadUserInfo.js'/>"></script>
  <script>
    function loadFilm(){
      var currentPage = $('#page').val();
      var limit = $('#limit').val();
      loadUserInfo();
      showList(currentPage, limit);
    }

    function showList(currentPage, limit) {
      $.ajax({
        url: "${APIurl}",
        contentType: "application/json",
        method: "get",
        data: {page: currentPage, limit: limit},
        success: function (result) {
          $.each(result.data, (index, row)  => {
            const rowContent = `<div class="col-lg-6 col-md-6 mb-5">
                                  <a href="<c:url value='/`+row.id+`'/>">
                                    <div class="card h-100">
                                      <img class="card-img-top" src="http://placehold.it/400x500" alt="`+row.poster+`">
                                      <div class="card-body">
                                        <h4 class="card-title text-center">`+row.title+`</h4>
                                      </div>
                                    </div>
                                  </a>
                                </div>`;
            $('#rowsPage').append(rowContent);
          });
          var totalPages = result.totalPage;
          var visiblePages = 5;
          if(visiblePages > totalPages){
            visiblePages = totalPages;
          }
          window.pagObj = $('#pagination').twbsPagination({
            totalPages: totalPages,
            visiblePages: visiblePages,
            startPage: currentPage,
            onPageClick: function(event, page){
              if(currentPage != page){
                $('#limit').val(limit);
								$('#page').val(page);
								$('#formSubmit').submit();
              }
            }
          });
        },
        error: function(error) {
          alert("Something failed");
        }
      })
    }
  </script>
</body>
</html>