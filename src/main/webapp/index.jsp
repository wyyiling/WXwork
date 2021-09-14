<%@page contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <title>WeUI</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.3/weui.min.css">
    <script src="https://res.wx.qq.com/open/libs/weuijs/1.1.4/weui.min.js"></script>
    <script src="js/zepto.min.js"></script>
</head>
<script>

    function getCodeInUrl(variable) {


        let query = window.location.search.substring(1);
        let vars = query.split("&");
        for (let i = 0; i < vars.length; i++) {
            let pair = vars[i].split("=");
            if (pair[0] === variable) {
                return pair[1];
            }
        }
        return false;
    }

    window.onload = function () {
        let code = getCodeInUrl("code");
        if (code !== false) {
            document.getElementById("MAIN").style.display = "none";
            window.location.href = "index?code=" + code;
        }
    };

</script>

<body>
<%--<button onclick="faceme()">abcdefg</button>--%>
<br><br>
<div id="MAIN" style="text-align: center">
    <h1>Test Window</h1>
    <br><br>
    <form action="index" method="post">
        <label><input type="text" name="userid"/></label><br><br>
        <label><input type="password" name="pass"/></label><br>
        <label><input type="password" name="code" style="display: none"/></label><br>
        <label><input type="submit" value="" style="width: 150px"></label>
    </form>
    <br><br>

    <br>
</div>

</body>
</html>
