/**
 * Created by happyfarmer on 2016/12/4.
 */


$('btn').click(function () {
    $("log").html('clicked');
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/admin/users",
        success: function (data) {
            var dataJson = JSON.stringify(data);
            var jsonInfo = JSON.parse(dataJson);
            alert(jsonInfo.length);
        }
    });
});