<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<c:url var="APIurl" value="/api-film"/>
<c:url var="AddUrl" value="/api-comment/new"/>
<c:url var="Genreurl" value="/api-category"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Film Demo</title>
  <link href="<c:url value='/template/web/bootstrap/css/bootstrap.min.css' />" rel="stylesheet" type="text/css" media="all"/>
  <link href="<c:url value='/template/web/css/style.css'/>" rel="stylesheet" type="text/css"/>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" />
</head>
<body onload="loadFilmDetail();">
  <%@ include file="/common/web/header.jsp"%>
  <div class="container">
    <p hidden id="id">${id}</p>
    <h1 class="my-4" id="filmTitle"></h1>
    <div class="row" id="filmDetail">
    </div>
  </div>

    <div class="container">
      <div class="post-comments">
        <form>
          <div class="form-group">
            <label for="comment">Your Comment</label>
            <textarea id="comment" name="comment" class="form-control" rows="3"></textarea>
          </div>
          <button type="submit" class="btn btn-default" onclick="postComment(event)">Send</button>
        </form>
        <br/>
        <div class="row" id="listComments">
        </div>
      </div>
    </div>
  </div>
  <%@ include file="/common/web/footer.jsp"%>
  <script type="text/javascript" src="<c:url value='/template/web/jquery/jquery.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/template/web/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/template/common/loadUserInfo.js'/>"></script>
  <script>
    function loadFilmDetail(){
      var id = $('#id').text();
      loadUserInfo();
      showListGenre();
      fillData(id);        
    }

    $('#search').click(function(){
      var tempStr = "<c:url value='/home?searchStr='/>";
      window.location.href= tempStr + $('#searchInfo').val();
    })

    function fillData(id) {
      $.ajax({
        url:'${APIurl}/' + id,
        method: 'get',
        contentType: 'application/json',
        success: function (result) {
          const filmDetail = `<div class="col-md-3">
                                <img class="img-fluid" src="<c:url value='/resources/images/`+result.data.poster+`' />" alt="`+result.data.title+`">
                                <h3 class="my-3">Film Details</h3>
                                <ul>
                                  <li>Year: `+result.data.year+`</li>
                                  <li>Genre: `+result.data.genre+`</li>
                                  <li>IMDB: `+result.data.avgVote+`</li>
                                </ul>
                              </div>
                              <div class="col-md-9">
                                <h3 class="my-3">Film Description</h3>
                                <p>`+result.data.description+`</p>
                              </div>`;
          $('#filmTitle').append(result.data.title);
          $('#filmDetail').append(filmDetail);
          $.each(result.data.comments, (index, row) => {
						const commentFill = `<div class="container-fluid">
                                    <div class="media">
                                      <div class="media-heading">
                                        <h5><span class="label label-info" style="font-weight:bold">`+row.userName+`</span></h5>
                                      </div>

                                      <div class="media-body">
                                        <p>`+row.comment+`</p>
                                        <div class="comment-meta">
                                          <span>
                                            <a class="" role="button" data-toggle="collapse" href="#replyComment_`+index+`" aria-expanded="false" aria-controls="collapseExample">reply</a>
                                          </span>
                                          <div class="collapse" id="replyComment_`+index+`">
                                            <p hidden id="replyid">`+row.id+`</p>
                                            <form>
                                              <div class="form-group">
                                                <label for="comment">Your Comment</label>
                                                <textarea name="comment" id="comment_`+index+`" class="form-control" rows="3"></textarea>
                                              </div>
                                              <button type="submit" class="btn btn-default" onclick="postComment(event, '`+index+`')">Send</button>
                                            </form>
                                          </div>
                                        </div>`+childComments(row.childComments, index)+`
                                      </div>
                                    </div>
                                  </div>`;
						$('#listComments').append(commentFill);
					});
        },
        error: function (error) {
          alert("Something failed! " + error); 
        }
      });
    }

    function childComments(comments, outsideIndex){
      var commentFill = '';
       $.each(comments, (index, row) => {
        var temp = outsideIndex + "_"+ index;
						commentFill += `<div class="media">
                              <div class="media-heading">
                                <h5><span class="label label-info" style="font-weight:bold">`+row.userName+`</span></h5>
                              </div>
                              <div class="media-body">
                                <p>`+row.comment+`</p>
                                <div class="comment-meta">
                                  <span>
                                    <a class="" role="button" data-toggle="collapse" href="#replyComment_`+temp+`" aria-expanded="false" aria-controls="collapseExample">reply</a>
                                  </span>
                                  <div class="collapse" id="replyComment_`+temp+`">
                                    <p hidden>`+row.id+`</p>
                                    <form>
                                      <div class="form-group">
                                        <label for="comment">Your Comment</label>
                                        <textarea name="comment" id="comment_`+temp+`" class="form-control" rows="3"></textarea>
                                      </div>
                                      <button type="submit" class="btn btn-default" onclick="postComment(event, '`+temp+`')">Send</button>
                                    </form>
                                  </div>
                                </div>`+childComments(row.childComments, temp)+`
                              </div>
                            </div>`;
					});
      return commentFill;
    }

    function postComment(e, subStr) {
      var reply_id = null;
      var comment = null;
      if(typeof subStr == 'undefined'){
        comment = $('#comment').val();
      }
      else{
        var idString = '#replyComment_'+subStr+ ">p";
        var reply_id = $(idString).text();
        var commentString = '#comment_'+subStr;
        comment = $(commentString).val();
      }
      var film_id = $('#id').text();
      var user_id = localStorage.getItem("userId");
      var data = {};
      data["filmId"] = film_id;
      data["userId"] = user_id;
      data["replyId"] = reply_id;
      data["comment"] = comment;
      addComment(data);
    }

    function showListGenre() {
      $.ajax({
        url: "${Genreurl}",
        contentType: "application/json",
        method: "get",
        success: function (result) {
          $.each(result.data, (index, row)  => {
            const rowContent = `<li><a class="dropdown-item" href="<c:url value='/genre/`+row.id+`'/>">`+row.categoryName+`</a></li>`;
            $('#filmNavItem').append(rowContent);
          });
        },
        error: function(error) {
          alert("Something failed");
        }
      })
    }

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

  </script>
</body>
</html>