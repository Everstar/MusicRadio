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

<button id="btn">clickMe</button>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<%--<script src="../js/jquery-3.1.1.js"></script>--%>

<script type="text/javascript">

    $('#btn').click(function () {
        $("#log").html('clicked');
        var uri = "/admin/users";
        var postData={user_id:1};
        $.ajax({
            type: "POST",
            url: uri,
            contentType:"application/json",
            data:JSON.stringify(postData),
            async: false,
            success: function (data) {
                console.log("success");
                var dataJson = JSON.stringify(data);
                var jsonInfo = JSON.parse(dataJson);
                alert(jsonInfo.passwd);
            }
        });
    });
    console.log("???");
</script>

</body>
</html>
