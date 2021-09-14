<%@page contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WeUI</title>
    <!--必须-->
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui-for-work.min.css">
    <script src="../js/zepto.min.js"></script>
    <script src="../js/jquery.qrcode.min.js"></script>
    <script src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
</head>
<script>
    $(document).ready(function () {

        $('#tlimqrcode').qrcode({width: 125, height: 125, text: "${md5}"});

    });

</script>
<body>
<div class="page">
    <div class="page__bd page__bd_spacing">
        <div id="tx" style="text-align: center;height: 200px;overflow: hidden;padding-top: 10px; ">
            <img id="tximg" src="${user.tximg}" alt=""
                 style="height: 200px;width: 200px;border-radius: 100px;text-align: center">
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item" style="text-align: center" id="tlimqrcode">

            </div>
            <div class="weui-flex__item" style="text-align: center">
                <div id="info" style="padding-top: 15px;display: block">
                    姓名：${user.name}<br/>
                    工号：${user.mid}<br/>
                    科室：${user.dept}<br/>
                    职位：${user.job}<br/>
                </div>
            </div>
        </div>
    </div>
    <div class="weui-grids">


        <a href="phonebook" id="grid1" class="weui-grid js_grid">
            <div class="weui-grid__icon">
                <img alt="" src="../image/icon_nav_search_bar.png">
            </div>
            <p class="weui-grid__label">
                职工电话查询
            </p>
        </a>

        <a href="salary" id="grid2" class="weui-grid js_grid">
            <div class="weui-grid__icon">
                <img alt="" src="../image/icon_nav_cell.png">
            </div>
            <p class="weui-grid__label">
                本人工资查询
            </p>
        </a>

        <a href="signining" id="grid3" class="weui-grid js_grid">
            <div class="weui-grid__icon">
                <img alt="" src="../image/icon_nav_datetime.png">
            </div>
            <p class="weui-grid__label">
                考勤打卡查询
            </p>
        </a>


        <a href="opsch" id="grid4" class="weui-grid js_grid">
            <div class="weui-grid__icon">
                <img alt="" src="../image/icon_nav_icons.png">
            </div>
            <p class="weui-grid__label">
                手术排班查询
            </p>
        </a>


        <a href="worksch" id="grid5" class="weui-grid js_grid">
            <div class="weui-grid__icon">
                <img alt="" src="../image/icon_nav_panel.png">
            </div>
            <p class="weui-grid__label">
                人事排班查询
            </p>
        </a>

        <a href="mzsch" id="grid6" class="weui-grid js_grid">
            <div class="weui-grid__icon">
                <img alt="" src="../image/icon_nav_article.png">
            </div>
            <p class="weui-grid__label">
                门诊排班查询
            </p>
        </a>



    <%--        <a href="meeting" id="grid7" class="weui-grid js_grid">--%>
        <%--            <div class="weui-grid__icon">--%>
        <%--                <img alt="" src="../image/icon_nav_city.png">--%>
        <%--            </div>--%>
        <%--            <p class="weui-grid__label">--%>
        <%--                会议扫码签到--%>
        <%--            </p>--%>
        <%--        </a>--%>

    </div>
</div>
</body>
</html>
