<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>SpringMVC Demo 首页</title>

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<h1>这里是SpringMVC Demo首页</h1>

<h3 id="log">出现此页面，说明配置成功。</h3>

<button id="btn1">signup</button>
<br/>
<button id="btn2">signin</button>
<br/>
<input type="number" id="music_id" value=""/>
<button id="btn3">getLyric</button>
<br/>
<button id="btn4">search</button>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<%--<script src="../js/jquery-3.1.1.js"></script>--%>

<script type="text/javascript">

    $('#btn1').click(function () {
        var postData = {"username": "许迪文", "password": "123", "gender": "M"};

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
    $('#btn2').click(function () {
        var postData = {"username": "许迪文", "password": "123"};

        $.ajax({
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(postData),
            url: "http://localhost:8080/signin",
            success: function (data) {
                ajaxobj = eval("(" + data + ")");
                alert(ajaxobj.result + "\n" + ajaxobj.token);
            }
        });
    });

    $('#btn3').click(function () {
        var uri ="http://localhost:8080/api/lyric?id=" + $('#music_id').val();
        $.ajax({
            type : "get",
            dataType : "json",
            url :uri,
            success:function (data) {
                alert("getLyric Success");
            }
        });
    });

    $('#btn4').click(function () {
        var postData = {"s" : "River", "offset" : 0, "limit" : 10, "type" : "1"};
        $.ajax({
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(postData),
            url: "http://music.163.com/api/search/pc",
            success: function (data) {
                ajaxobj = eval("(" + data + ")");
                alert(ajaxobj);
            },
            error : function (data){
                alert("error");
            }
        });
    });

</script>

</body>
</html>
