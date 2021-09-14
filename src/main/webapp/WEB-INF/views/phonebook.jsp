<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>职工电话查询</title>
    <!--必须-->
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui-for-work.min.css">
    <script src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <script src="../js/zepto.min.js"></script>

</head>
<body>
<script type="text/javascript">
    $(document).ready(function () {
            let $searchBar = $('#searchBar'),
                $searchResult = $('#searchResult'),
                $searchText = $('#searchText'),
                $searchInput = $('#searchInput'),
                $searchClear = $('#searchClear'),
                $searchCancel = $('#searchCancel'),
                $searchClick = $('#searchClick'),
                $celllist = $('#celllist');
            $searchClick.hide();

            function hideSearchResult() {
                $searchResult.hide();
                $searchInput.val('');
            }

            function search() {
                $.ajax({
                    url: "getphuser",
                    type: "POST",

                    contentType: "application/x-www-form-urlencoded;charset=utf-8",
                    data: {
                        'querystr': $searchInput.val()
                    },
                    dataType: "json",
                    success: function (result) {
                        <!--方便 下面继续使用result-->
                        $celllist.empty();
                        for (let i = 0; i < result.length; i++) {
                            let td = "<a class=\"weui-cell weui-cell_access\" href=\"javascript:;\"" +
                                " id=\"" + result[i]["mid"] + "\" onclick=\"myFunction(this)\">" +
                                "<div class=\"weui-cell__hd\"><img src=\"\" alt=\"\" style=\"width:20px;margin-right:5px;display:block\"></div>" +
                                "<div class=\"weui-cell__bd\"><p>" + result[i]["name"] + result[i]["mid"] + "</p></div>" +
                                "<div class=\"weui-cell__ft\">" + result[i]["dept"] + " " + result[i]["job"] + "</div></a>";

                            $celllist.append(td);
                        }
                    },
                    error: function (msg) {
                        $celllist.empty();
                        $celllist.append(msg.status);
                    }
                });
            }

            function cancelSearch() {
                hideSearchResult();
                $searchBar.removeClass('weui-search-bar_focusing');
                $searchText.show();
            }

            $searchText.on('click', function () {
                $searchBar.addClass('weui-search-bar_focusing');
                $searchInput.focus();
            });
            $searchInput
                .on('blur', function () {
                })
                .on('input', function () {
                    if (this.value.length > 0) {
                        $searchCancel.hide();
                        $searchClick.show();
                    } else {
                        $searchCancel.show();
                        $searchClick.hide();
                    }
                });
            $searchClear.on('click', function () {
                hideSearchResult();
                $searchInput.focus();
                $searchCancel.show();
                $searchClear.hide();
            });
            $searchCancel.on('click', function () {
                cancelSearch();
                $searchInput.blur();
            });
            $searchClick.on('click', function () {
                search();
            });
            $searchInput.on('keypress', function (e) {
                let keycode = e.keyCode;
                if (keycode === 13) {
                    search();
                }
            })
        }
    );

    function myFunction(element) {
        let $celllist = $('#celllist');
        $celllist.empty();
        $.ajax({
            url: "userindt",
            type: "POST",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            data: {
                'querystr': element.id
            },
            dataType: "json",
            success: function (result) {
                <!--方便 下面继续使用result-->

                let call = "<div class=\"page__hd\">\n" +
                    "<div style=\"text-align:center;font-size:40px\"><text>" + result["name"] + "</text>  </div>\n" +
                    "<div style=\"text-align:center;font-size:25px\" ><text>" + result["job"] + "</text></div>\n" +
                    "<div style=\"text-align:center;font-size:25px\"><text>" + result["dept"] + "</text></div>\n" +
                    "<div style=\"text-align:center;font-size:25px\"><text>" + result["mobile"] + "</text></div>\n" +
                    "</div>\n" +
                    "<div class=\"page__bd page__bd_spacing\">\n" +
                    "<a class=\"weui-btn weui-btn_primary\" href=\"tel:{{mobile}}\" >拨号" + result["mobile"] + "</a>\n" +
                    "</div>";
                $celllist.append(call);

            },
            error: function (result) {
                alert(result.status);
            }
        });

    }
</script>
<div class="page">
    <div class="weui-search-bar" id="searchBar">
        <form class="weui-search-bar__form">
            <div class="weui-search-bar__box">
                <i class="weui-icon-search"></i>
                <label><input type="search" class="weui-search-bar__input" id="searchInput" placeholder="搜索" required/></label>
                <a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
                <label><input type="text" value="" style="display:none"/></label>
                <button type="button" style="display:none">提交</button>
            </div>
            <label class="weui-search-bar__label" id="searchText">
                <i class="weui-icon-search"></i>
                <span>搜索</span>
            </label>
        </form>
        <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
        <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchClick">查询</a>
    </div>
</div>
<div class="weui-cells" id="celllist">
</div>
</body>
</html>