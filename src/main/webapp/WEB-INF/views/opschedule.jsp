<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>手术排班查询</title>
    <!--必须-->
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui-for-work.min.css">
    <script src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <script src="../js/zepto.min.js"></script>


</head>

<script>
    function opdialog(element) {
        let idd= element.id+'show';
        let optext = $('#'+idd);
        console.log(idd);
        optext.empty();
        <c:forEach items="${opschedule}" var="op" varStatus="i">
        if ((element.id) === ("z${op.patient_id}")) {
            let td = "<div class=\"weui-cell\">\n" +
                "<div class=\"weui-cell__hd\"></div>\n" +
                " <div class=\"weui-cell__bd\">"+
                "<p> 计划时间：${op.sch_dtime}</p></div></div>\n" +

                "<div class=\"weui-cell\">\n" +
                "<div class=\"weui-cell__hd\"></div>\n" +
                "<div class=\"weui-cell__bd\">\n" +
                "<p> 手术科室:${op.opdept} &nbsp  ${op.room}</p></div></div>\n" +

                "<div class=\"weui-cell\">\n" +
                "<div class=\"weui-cell__hd\"></div>\n" +
                "<div class=\"weui-cell__bd\">\n" +
                "<p> 手术台次: ${op.sequence}</p></div></div>\n" +

                "<div class=\"weui-cell\">\n" +
                "<div class=\"weui-cell__hd\"></div>\n" +
                "<div class=\"weui-cell__bd\">\n" +
                "<p> 就诊科室:${op.dept}</p></div></div>\n" +

                "<div class=\"weui-cell\">\n" +
                "<div class=\"weui-cell__hd\"></div>\n" +
                "<div class=\"weui-cell__bd\">\n" +
                "<p> 患者姓名:${op.patient_name}</p></div></div>\n" +

                "<div class=\"weui-cell\">\n" +
                "<div class=\"weui-cell__hd\"></div>\n" +
                "<div class=\"weui-cell__bd\">\n" +
                "<p> 住院号:${op.patient_id}</p></div></div>\n" +

                "<div class=\"weui-cell\">\n" +
                "<div class=\"weui-cell__hd\"></div>\n" +
                "<div class=\"weui-cell__bd\">\n" +
                "<p> 床位:${op.bedno}</p></div></div>\n" +

                "<div class=\"weui-cell\">\n" +
                "<div class=\"weui-cell__hd\"></div>\n" +
                "<div class=\"weui-cell__bd\">\n" +
                "<p> 手术名称:${op.opname}</p></div></div>\n" +

                "<div class=\"weui-cell\">\n" +
                "<div class=\"weui-cell__hd\"></div>\n" +
                "<div class=\"weui-cell__bd\">\n" +
                "<p> 手术主刀:${op.surgeon}</p></div></div>\n" +

                "<div class=\"weui-cell\">\n" +
                "<div class=\"weui-cell__hd\"></div>\n" +
                "<div class=\"weui-cell__bd\">\n" +
                "<p> 手术助手:${op.assistant}</p></div></div>\n" +

                "<div class=\"weui-cell\">\n" +
                "<div class=\"weui-cell__hd\"></div>\n" +
                "<div class=\"weui-cell__bd\">\n" +
                "<p> 巡回护士:${op.supnurse}</p></div></div>\n" +

                "<div class=\"weui-cell\">\n" +
                "<div class=\"weui-cell__hd\"></div>\n" +
                "<div class=\"weui-cell__bd\">\n" +
                "<p> 洗手护士:${op.opnurse}</p></div></div>\n" ;


            optext.html(td);
            optext.toggle(500);
        }
        </c:forEach>

    }


</script>

<body>

<div class="page" style="text-align: center">

    <h2>手术排班列表</h2>

</div>


<div class="page">

    <div class="weui-cells" id="celllist">
        <c:forEach items="${opschedule}" var="op" varStatus="i">
            <a class="weui-cell weui-cell_access" id="z${op.patient_id}" onclick="opdialog(this)">
                <div class="weui-cell"><img src="" alt="" style="width:20px;margin-right:5px;display:block"></div>
                <div class="weui-cell__bd">
                    <p> ${op.sch_dtime} <br> ${op.weekday} </p></div>
                <div class="weui-cell__ft"> ${op.patient_name} </div>
            </a>
            <div id="z${op.patient_id}show" style="display: none">
            </div>
        </c:forEach>
    </div>

</div>

</body>
</html>
