/**
 * Created by happyfarmer on 2016/12/4.
 */


$('btn').click(function () {
    $("log").html('clicked');
    var postData = {"username" : "许迪文", "password" : "123", "gender" : "M"};

    $.ajax({
        type: "GET",
        dataType:"json",
        contentType:"application/json",
        data:JSON.stringify(postData),
        url: "http://localhost:8080/signup",
        success: function (data) {
            console.log("signup success");
        }
    });
});