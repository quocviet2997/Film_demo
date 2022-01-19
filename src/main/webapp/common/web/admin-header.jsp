<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand" href="#">Start Bootstrap</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav ml-auto">
        <li class="nav-item active">
          <a class="nav-link" href='<c:url value="/admin-home"/>'>Trang chủ
            <span class="sr-only">(current)</span>
          </a>
        </li>
        <li class="nav-item active">
          <a class="nav-link" href='<c:url value="/admin-api-film"/>'>Film
          </a>
        </li>
        <li class="nav-item active">
          <a class="nav-link" href='<c:url value="/admin-api-comment"/>'>Comment
          </a>
        </li>
        <li class="nav-item active">
          <a class="nav-link" href='<c:url value="/admin-api-user"/>'>User
          </a>
        </li>
        <li class="nav-item active">
          <a class="nav-link" href='<c:url value="/admin-api-category"/>'>Category
          </a>
        </li>
        <li class="nav-item active">
          <a class="nav-link" href='<c:url value="/admin-api-category-film"/>'>Category_Film
          </a>
        </li>
      </ul>
    </div>
  </div>
</nav>