<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: happyfarmer
  Date: 2016/12/3
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
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
    <![endif]-->
</head>
<body>
<div class="container">
    <h1>用户管理</h1>
    <hr/>

    <%--<c:if test="${empty userList}">--%>
        <%--<div class="alert alert-warning" role="alert">--%>
            <%--<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>--%>
            <%--用户表为空--%>
        <%--</div>--%>
    <%--</c:if>--%>

    <%--<c:if test="${!empty userList}">--%>
        <%--<table class="table table-bordered table-striped">--%>
            <%--<tr>--%>
                <%--<th>ID</th>--%>
                <%--<th>用户名</th>--%>
                <%--<th>密码</th>--%>
                <%--<th>头像ID</th>--%>
            <%--</tr>--%>

            <%--<c:forEach items="${userList}" var="user">--%>
                <%--<tr>--%>
                    <%--<td>${user.userId}</td>--%>
                    <%--<td>${user.userName}</td>--%>
                    <%--<td>${user.passwd}</td>--%>
                    <%--<td>${user.imageId}</td>--%>
                <%--</tr>--%>
            <%--</c:forEach>--%>
        <%--</table>--%>
    <%--</c:if>--%>
</div>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>

