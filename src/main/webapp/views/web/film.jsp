<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<c:url var="APIurl" value="/api-film"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Film Demo</title>
<link href="<c:url value='/template/web/bootstrap/css/bootstrap.min.css' />" rel="stylesheet" type="text/css" media="all"/>
<link href="<c:url value='/template/web/css/style.css' />" rel="stylesheet" type="text/css" media="all"/>
</head>
<body onload="loadFilmDetail();">
  <%@ include file="/common/web/header.jsp"%>
  <div class="container">
    <p hidden id="id">${id}</p>
    <h1 class="my-4">Page Heading</h1>
    <div class="row" id="filmDetail">
    </div>
  </div>

    <div class="container">
      <div class="post-comments">
        <form>
          <div class="form-group">
            <label for="comment">Your Comment</label>
            <textarea name="comment" class="form-control" rows="3"></textarea>
          </div>
          <button type="submit" class="btn btn-default" onclick="postComment(event)">Send</button>
        </form>
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
      fillData(id);        
    }

    function fillData(id) {
      $.ajax({
        url:'${APIurl}/' + id,
        method: 'get',
        contentType: 'application/json',
        success: function (result) {
          const filmDetail = `<div class="col-md-8">
                                <img class="img-fluid" src="https://via.placeholder.com/750x500" alt="`+result.data.poster+`">
                                <h3 class="my-3">Film Details</h3>
                                <ul>
                                  <li>Year: `+result.data.year+`</li>
                                  <li>Genre: `+result.data.genre+`</li>
                                  <li>IMDB: `+result.data.avgVote+`</li>
                                </ul>
                              </div>
                              <div class="col-md-4">
                                <h3 class="my-3">Film Description</h3>
                                <p>`+result.data.description+`</p>
                              </div>`;
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
                                                <textarea name="comment" id="comment" class="form-control" rows="3"></textarea>
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
                                        <textarea name="comment" class="form-control" rows="3"></textarea>
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
      var idstring = '#replyComment_'+subStr+ ">p";
      var t = $(idstring).text();
      var film_id = $('#id').text();
      alert(t + " " + film_id);
    }

  </script>
</body>
</html>