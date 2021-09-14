<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>排班查询</title>
    <!--必须-->
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui-for-work.min.css">
    <script src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <script src="../js/zepto.min.js"></script>
</head>
<body>
<div class="page" style="text-align: center">
    <h2>排班记录</h2>
</div>
<div class="weui-cells" id="celllist">
    <c:forEach items="${worksch}" var="worksch" varStatus="i">
        <a class="weui-cell weui-cell_access">
            <div class="weui-cell__hd"></div>
            <div class="weui-cell__bd">
                <p> ${worksch.wkcalendar} &nbsp ${worksch.weekday} </p></div>
            <div class="weui-cell__ft"><p>${worksch.shift}</p></div>
        </a>
    </c:forEach>
</div>
</body>
</html>