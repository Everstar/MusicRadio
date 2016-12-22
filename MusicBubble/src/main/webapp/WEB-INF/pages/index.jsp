<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="kiben" content="no-cache">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>SpringMVC Demo 首页</title>

    <%--<!-- 新 Bootstrap 核心 CSS 文件 -->--%>
    <%--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">--%>

    <%--<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->--%>
    <%--<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->--%>
    <%--<!--[if lt IE 9]>--%>
    <%--<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>--%>
    <%--<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>--%>
    <![endif]-->
</head>
<body>
<h1>Music Radio</h1>

<form id="form1" method="post">
    <table>
        <tr>
            <td>
                username
            </td>
            <td>
                <input type="text" id="username"/>
            </td>
        </tr>
        <tr>
            <td>password</td>
            <td><input type="text" id="password"/></td>
        </tr>
        <tr>
            <td>gender</td>
            <td><input type="text" id="gender"></td>
        </tr>
    </table>
</form>
<button id="signup">signup</button>
<br/>

<button id="signin">signin</button>
<br/>

<form name="upload" action="/addsong/upload" method="post" enctype="multipart/form-data">
    file:<input type="file" name="song_file"/><br/>
    songlist_id:<input type="number" name="songlist_id"/><br/>
    language:<input type="text" name="language"/><br/>
    styles:<input type="text" name="styles"/><br/>
    <input type="submit" value="upload"/>
</form>

<form name="changesong" action="/changesong" method="post" enctype="multipart/form-data">
    file:<input type="file" name="image_file"/><br/>
    list_id:<input type="number" name="songlist_id"/><br/>
    song_id:<input type="number" name="song_id"/><br/>
    image_id:<input type="number" name="image_id"/><br/>
    image_url:<input type="text" name="image_url"/><br/>
    <input type="submit" value="upload">
</form>
<%--<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->--%>
<%--<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>--%>

<%--<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->--%>
<%--<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>--%>

<script src="../../jquery-3.1.1.js"></script>

<script type="text/javascript">

    $('#signup').click(function () {
        var postData = {"username": $("#username").val(),
            "password":$("#password").val(),
            "gender": $("#gender").val()};
        console.log(postData);
        $.ajax({
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(postData),
            url: "http://localhost:8080/signup",
            success: function (data) {
                alert("signup success");
            }
        });
    });
    $('#signin').click(function () {
        var postData = {"username": $("#username").val(),
            "password":$("#password").val()};
        console.log(postData);
        $.ajax({
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(postData),
            url: "http://localhost:8080/signin",
            success: function (data) {
                alert("signin success");
            }
        });
    });

</script>

</body>
</html>
