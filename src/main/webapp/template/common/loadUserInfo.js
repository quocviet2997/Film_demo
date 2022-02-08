function loadUserInfo(){
    var url = window.location.origin;
    var name = localStorage.getItem("userName");
    var temp;
    if(name!= null && name != '' && typeof name !== 'undefined'){
      temp = `<li class="nav-item">
                <a class="nav-link">Welcome, `+name+`</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="`+url+`/login" onclick="logout()">Logout</a>
              </li>`;
    }
    else {
      temp = `<li class="nav-item">
                <a class="nav-link" href="`+url+`/login">Login</a>
              </li>`;
    }
    $('#navbarResponsive>ul').append(temp);
}

function logout() {
  localStorage.removeItem("userName");
  localStorage.removeItem("userId");
};