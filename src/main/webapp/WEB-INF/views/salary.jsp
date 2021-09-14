<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>本人工资查询</title>
    <!--必须-->
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui-for-work.min.css">
    <script src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <script src="../js/zepto.min.js"></script>
</head>
<script>

    function saldetail(element) {
        let el = element.id;
        let year = el.split("y")[0];
        let month = el.split("y")[1];
        let state = el.split("y")[2];
        $.ajax({
            url: "getgz",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                'year': year,
                'month': month,
                'state': state,
                'mid': '${mid}'

            }),
            dataType: "json",
            success: function (result) {
                let elshow = element.id + "show";
                let $dtid = $('#' + elshow);
                $dtid.empty();
                for (let i = 0; i < result.length; i++) {

                    if (result[i]["money"] !== "0.00" && result[i]["money"] !== "" && result[i]["money"] !== null) {
                        let td = "<div class=\"weui-cell\">\n" +
                            "<div class=\"weui-cell__hd\"></div>\n" +
                            "<div class=\"weui-cell__bd\">\n" +
                            "<p> " + result[i]["name"] + " </p></div>\n" +
                            "<div class=\"weui-cell__ft\"><p>" + result[i]["money"] + "</p></div>\n" +
                            "</div>";
                        $dtid.append(td);
                    }
                }
                $dtid.toggle(300);
            }, error: function (msg) {
                alert(msg.status);
            }
        })
    }

</script>
<body>


<div class="page" style="text-align: center"><h2>最近一年工资明细(2021年6月起)</h2></div>
<div class="weui-cells" id="celllist">

    <c:forEach items="${salary}" var="sal" varStatus="i">
        <div class="weui-panel">
            <div class="weui-panel__hd">${sal.getString("year")}年${sal.getString("month")}月</div>
            <div class="weui-panel__bd">
                <div class="weui-media-box weui-media-box_small-appmsg">
                    <div class="weui-cells">
                        <a class="weui-cell weui-cell_access" id="${sal.getString("year")}y${sal.getString("month")}ygz"
                           onclick="saldetail(this)">
                            <div class="weui-cell__hd"><img
                                    src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAAuCAMAAABgZ9sFAAAAVFBMVEXx8fHMzMzr6+vn5+fv7+/t7e3d3d2+vr7W1tbHx8eysrKdnZ3p6enk5OTR0dG7u7u3t7ejo6PY2Njh4eHf39/T09PExMSvr6+goKCqqqqnp6e4uLgcLY/OAAAAnklEQVRIx+3RSRLDIAxE0QYhAbGZPNu5/z0zrXHiqiz5W72FqhqtVuuXAl3iOV7iPV/iSsAqZa9BS7YOmMXnNNX4TWGxRMn3R6SxRNgy0bzXOW8EBO8SAClsPdB3psqlvG+Lw7ONXg/pTld52BjgSSkA3PV2OOemjIDcZQWgVvONw60q7sIpR38EnHPSMDQ4MjDjLPozhAkGrVbr/z0ANjAF4AcbXmYAAAAASUVORK5CYII="
                                    alt="" style="width:20px;margin-right:5px;display:block"></div>
                            <div class="weui-cell__bd weui-cell_primary">
                                <p>基本工资</p>
                            </div>
                            <div class="weui-cell__ft"><p></p></div>
                        </a>
                        <div id="${sal.getString("year")}y${sal.getString("month")}ygzshow" style="display: none">
                        </div>

                        <a class="weui-cell weui-cell_access" id="${sal.getString("year")}y${sal.getString("month")}yjx"
                           onclick="saldetail(this)">
                            <div class="weui-cell__hd"><img
                                    src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAAuCAMAAABgZ9sFAAAAVFBMVEXx8fHMzMzr6+vn5+fv7+/t7e3d3d2+vr7W1tbHx8eysrKdnZ3p6enk5OTR0dG7u7u3t7ejo6PY2Njh4eHf39/T09PExMSvr6+goKCqqqqnp6e4uLgcLY/OAAAAnklEQVRIx+3RSRLDIAxE0QYhAbGZPNu5/z0zrXHiqiz5W72FqhqtVuuXAl3iOV7iPV/iSsAqZa9BS7YOmMXnNNX4TWGxRMn3R6SxRNgy0bzXOW8EBO8SAClsPdB3psqlvG+Lw7ONXg/pTld52BjgSSkA3PV2OOemjIDcZQWgVvONw60q7sIpR38EnHPSMDQ4MjDjLPozhAkGrVbr/z0ANjAF4AcbXmYAAAAASUVORK5CYII="
                                    alt="" style="width:20px;margin-right:5px;display:block"></div>
                            <div class="weui-cell__bd weui-cell_primary">
                                <p>绩效工资</p>
                            </div>
                            <span class="weui-cell__ft"></span>
                        </a>
                        <div id="${sal.getString("year")}y${sal.getString("month")}yjxshow" style="display: none">

                        </div>
                    </div>
                </div>
            </div>
        </div>

    </c:forEach>
</div>

</body>
</html>
