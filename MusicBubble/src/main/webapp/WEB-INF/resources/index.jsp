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

<button id="signout">signout</button>
<br/>

<form name="upload" action="/api/addsong/upload" method="post" enctype="multipart/form-data">
    file:<input type="file" name="song_file"/><br/>
    songlist_id:<input type="number" name="songlist_id"/><br/>
    language:<input type="text" name="language"/><br/>
    styles:<input type="text" name="styles"/><br/>
    <input type="submit" value="upload"/>
</form>

<%--<form name="changesong" action="/api/changesong" method="post" enctype="multipart/form-data">--%>
    <%--file:<input type="file" name="image_file"/><br/>--%>
    <%--list_id:<input type="number" name="songlist_id"/><br/>--%>
    <%--song_id:<input type="number" name="song_id"/><br/>--%>
    <%--image_id:<input type="number" name="image_id"/><br/>--%>
    <%--image_url:<input type="text" name="image_url"/><br/>--%>
    <%--<input type="submit" value="upload">--%>
<%--</form>--%>

<div>
    <div id="connect-container">
        <input id="radio1" type="radio" name="group1" onclick="updateUrl('/danmu?id=1&user=1');">
        <label for="radio1">W3C WebSocket</label>
        <br>
        <input id="radio2" type="radio" name="group1" onclick="updateUrl('/danmu?id=1&user=2');">
        <label for="radio2">SockJS</label>

        <div id="sockJsTransportSelect" >
            <span>SockJS transport:</span>
            <select onchange="updateTransport(this.value)">
                <option value="all">all</option>
                <option value="websocket">websocket</option>
                <option value="xhr-polling">xhr-polling</option>
                <option value="jsonp-polling">jsonp-polling</option>
                <option value="xhr-streaming">xhr-streaming</option>
                <option value="iframe-eventsource">iframe-eventsource</option>
                <option value="iframe-htmlfile">iframe-htmlfile</option>
            </select>
        </div>
        <div>
            <button id="connect" onclick="connect();">Connect</button>
            <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
        </div>
        <div>
            <textarea id="message" >Here is a message!</textarea>
        </div>
        <div>
            <button id="echo" onclick="echo();" disabled="disabled">Echo message</button>
        </div>
    </div>
    <div id="console-container">
        <div id="console"></div>
    </div>
</div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script src="http://cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script>

<script type="text/javascript">
//    var domain = "http://60.205.217.210:8080/MusicBubble";
    var domain = "http://localhost:8080"
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
            url: domain + "/api/signup",
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
            url: domain + "/api/signin",
            success: function (data) {
                alert("signin success");
            }
        });
    });

    $('#signout').click(function (){
        $.ajax({
            type : "GET",
            dataType : "json",
            contentType : "application/json",
            data : null,
            url : domain + "/api/signout",
            success : function(data){
                alert("signout success");
            }
        });
    }
    );

    var ws = null;
    var url = null;
    var transports = [];

    function setConnected(connected) {
        document.getElementById('connect').disabled = connected;
        document.getElementById('disconnect').disabled = !connected;
        document.getElementById('echo').disabled = !connected;
    }

    function connect() {

        alert("url:" + url);
        if (!url) {
            alert('Select whether to use W3C WebSocket or SockJS');
            return;
        }
        if ('WebSocket' in window) {
            ws = new WebSocket(url);//ws://localhost:8080/myHandler
        } else if ('MozWebSocket' in window) {
            ws = new MozWebSocket(url);
        } else {
            ws = new SockJS(url);
        }

        ws.onopen = function () {
            setConnected(true);
            log('Info: connection opened.');
        };
        ws.onmessage = function (event) {
            log('Received: ' + event.data);
        };
        ws.onclose = function (event) {
            setConnected(false);
            log('Info: connection closed.');
            log(JSON.stringify(event));
        };
    }

    function disconnect() {
        if (ws != null) {
            ws.close();
            ws = null;
        }
        setConnected(false);
    }

    function echo() {
        if (ws != null) {
            var t = Math.floor(Math.random() * 60);
            var message = document.getElementById('message').value;
            log('Sent: ' + message);
            ws.send(message + "|" + t);
        } else {
            alert('connection not established, please connect.');
        }
    }

    function updateUrl(urlPath) {
        if (urlPath.indexOf('sockjs') != -1) {
            url = urlPath;
            document.getElementById('sockJsTransportSelect').style.visibility = 'visible';
        }
        else {
            if (window.location.protocol == 'http:') {
                url = 'ws://' + window.location.host + urlPath;
            } else {
                url = 'wss://' + window.location.host + urlPath;
            }
            document.getElementById('sockJsTransportSelect').style.visibility = 'hidden';
        }
    }

    function updateTransport(transport) {
        alert(transport);
        transports = (transport == 'all') ? [] : [transport];
    }

    function log(message) {
        var console = document.getElementById('console');
        var p = document.createElement('p');
        p.style.wordWrap = 'break-word';
        p.appendChild(document.createTextNode(message));
        console.appendChild(p);
        while (console.childNodes.length > 25) {
            console.removeChild(console.firstChild);
        }
        console.scrollTop = console.scrollHeight;
    }

</script>

</body>
</html>
