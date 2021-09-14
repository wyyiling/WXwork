<%@page contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>会议签到查询</title>
    <!--必须-->
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui-for-work.min.css">
    <script src="../js/zepto.min.js"></script>
    <script src="../js/jquery.qrcode.min.js"></script>
    <script src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>

    <style type="text/css">
        .weui_tab_bd_item {
            height: 100%;
            overflow: auto;
            display: none;
        }

        .weui_tab_bd_item.weui_tab_bd_item_active {
            display: block;
        }
    </style>
</head>
<script type="text/javascript">
    $(document).ready(function () {

        let $nav1 = $('#nav1'),
            $nav2 = $('#nav2'),
            $show1 = $('#show1'),
            $show2 = $('#show2'),

            $scanmt = $('#scanmt'),
            $mtqrcode = $('#mtqrcode'),
            $meetpassword = $('#meetpassword'),
            $newattd = $('#newattd'),
            $getattender = $('#getattender'),
            $adminlist = $('#adminlist');

        $('.weui-navbar__item').on('click', function () {
            $(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
            let index = $(this).index();
            $(".weui-tab__panel .weui_tab_bd_item").eq(index).addClass("weui_tab_bd_item_active").siblings().removeClass("weui_tab_bd_item_active");

        });

        checkadmin(${maper.level});

        $.ajax({
            type: "POST",
            url: "jspurl",
            data: {
                'url': location.href.split('#')[0]
            },
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            dataType: "json",
            success: function (result) {
                wx.config({
                    beta: true,
                    debug: false,
                    appId: "wwd63d766f2bf188b9",
                    timestamp: result["timestamp"],
                    nonceStr: result['noncestr'],
                    signature: result['signature'],
                    jsApiList: ['scanQRCode']
                });
            },
            error: function (result) {
                alert(result.status);
            }
        });
        //mySign
        $nav1.on('click', function () {
            $show1.empty();
            $show2.empty();
            $.ajax({
                type: "POST",
                url: "meeting_myAttendance",
                data: {},
                dataType: "json",
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                success: function (result) {
                    $show1.empty();
                    for (let i = 0; i < result.length; i++) {
                        let td = "<a class=\"weui-cell weui-cell_access\" href=\"javascript:;\""
                            + " id=\"" + result[i]["password"] + "\" "
                            + "onclick=\"meeting_mySign(this)\">"
                            + "<div class=\"weui-cell__hd\"><img src=\"\" alt=\"\" style=\"width:20px;margin-right:5px;display:block\"></div>"
                            + "<div class=\"weui-cell__bd\" style='text-align:left'><p>" + result[i]["title"] + "</p></div>"
                            + "<div class=\"weui-cell__ft\">" + result[i]["addr"] + "</div></a>";
                        $show1.append(td);
                    }
                },
                error: function (result) {
                    alert(result.status);
                }
            });
        });
        //my_meeting
        $nav2.on('click', function () {
            $show1.empty();
            $show2.empty();
            $.ajax({
                type: "POST",
                url: "meeting_myMeeting",
                data: {},
                dataType: "json",
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                success: function (result) {
                    $show2.empty();
                    for (let i = 0; i < result.length; i++) {
                        let td = "<a class=\"weui-cell weui-cell_access\" href=\"javascript:;\""
                            + " id=\"" + result[i]["password"] + "\" "
                            + "onclick=\"meeting_mtInfo(this)\">"
                            + "<div class=\"weui-cell__hd\"><img src=\"\" alt=\"\" style=\"width:20px;margin-right:5px;display:block\"></div>"
                            + "<div class=\"weui-cell__bd\" style='text-align:left'><p>" + result[i]["title"] + "</p></div>"
                            + "<div class=\"weui-cell__ft\">" + result[i]["addr"] + "</div></a>";
                        $show2.append(td);
                    }
                },
                error: function (result) {
                    alert(result.status);
                }
            });
        });
        //扫码scan
        $scanmt.on('click', function () {
            wx.scanQRCode({
                desc: 'scanQRCode desc',
                needResult: 1, // 默认为0，扫描结果由企业微信处理，1则直接返回扫描结果，
                scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是条形码（一维码），默认二者都有
                success: function (res) {
                    let result = res.resultStr,
                        time = getNowFormatDate();
                    meeting_scan(time, result);
                },
                error: function (result) {
                    if (result.errMsg.indexOf('function_not_exist') > 0) {
                        alert('版本过低请升级');
                    }
                }
            });
        });
        //新建一个会议对应 meeting_submit
        $newattd.on('click', function () {

            $('#show2').empty();
            let name = "${maper.name}";
            let form = "<div class=\"weui-cells__title\">(全部必填，勿随意填写)</div>"
                + "<div class=\"weui-cell\">"
                + "<div class=\"weui-cell__hd\"><label class=\"weui-label\">创建人: </label></div>"
                + "<div class=\"weui-cell__bd\">"
                + "<input class=\"weui-input\" value=\"" + name + "\" disabled=\"disabled\"/>"
                + "</div></div>"
                + "<div class=\"weui-cell\">"
                + "<div class=\"weui-cell__hd\"><label class=\"weui-label\">会议标题</label></div>"
                + "<div class=\"weui-cell__bd\">"
                + "<input class=\"weui-input\" id=\"title\" placeholder=\"会议标题\"/>"
                + "</div></div>"
                + "<div class=\"weui-cell\">"
                + "<div class=\"weui-cell__hd\"><label class=\"weui-label\">地址</label></div>"
                + "<div class=\"weui-cell__bd\">"
                + "<input class=\"weui-input\" id=\"addr\" placeholder=\"地址\"/>"
                + "</div></div>"
                + "<div class=\"weui-cell\">"
                + "<div class=\"weui-cell__hd\"><label class=\"weui-label\">签到开始：</label></div>"
                + "<div class=\"weui-cell__bd\">"
                + "<input class=\"weui-input\" type=\"datetime-local\" id=\"starttime\" value=\"\" placeholder=\"\"/>"
                + "</div></div>"
                + "<div class=\"weui-cell\">"
                + "<div class=\"weui-cell__hd\"><label class=\"weui-label\">签到结束：</label></div>"
                + "<div class=\"weui-cell__bd\">"
                + "<input class=\"weui-input\" type=\"datetime-local\" id=\"endtime\" value=\"\" placeholder=\"\"/>"
                + "</div></div>"
                + "<div class=\"weui-btn-area\">"
                + "<a class=\"weui-btn weui-btn_primary\" onclick=\"meeting_submit()\">提交</a></div>"
                + "</div>";
            $show2.append(form);
            $("#title").empty();
            $("#addr").empty();
            $("#starttime").empty();
            $("#endtime").empty();


        });
        //生成会议二维码
        $mtqrcode.on('click', function () {
            let meetpassword = $meetpassword.val().trim(),
                text = "";
            if (meetpassword === "") {
                text = "请填写会议编号!"
                dialogshow(text);
            } else {
                $show2.empty();
                let crqrcode = "<div id=\"qrarea\" style=\"text-align: center\"></div>",
                    time = getNowFormatDate();
                $show2.append(crqrcode);
                let $qrcode = $('#qrarea');
                $qrcode.empty();
                $.ajax({
                    type: "POST",
                    url: "meeting_gnQr",
                    data: JSON.stringify({
                        'time': time,
                        'meetpassword': meetpassword
                    }),
                    dataType: "json",
                    contentType: "application/json;charset=utf-8",
                    success: function (result) {
                        if (result.indexOf("error") !== -1) {
                            text = result.split(",")[1];
                            dialogshow(text);
                        } else {
                            $qrcode.qrcode(result);
                        }
                    },
                    error: function (result) {
                        alert(result.status);
                    }
                });

            }

        });
        //获取签到人员
        $getattender.on('click', function () {
            let meetpassword = $meetpassword.val().trim(),
                text = "";
            $show2.empty();
            if (meetpassword === "") {
                text = "请填写会议编号!";
                dialogshow(text);
            } else {

                $.ajax({
                    type: "POST",
                    url: "meeting_attender",
                    data: {
                        'password': meetpassword
                    },
                    dataType: "json",
                    contentType: "application/x-www-form-urlencoded;charset=utf-8",
                    success: function (result) {
                        $show2.empty();
                        if (result[0]["mtpassword"] === "error") {
                            text = "错误的编号或者暂无人签到!";
                            dialogshow(text);
                        } else {
                            for (let i = 0; i < result.length; i++) {
                                let td = "<a class=\"weui-cell weui-cell_access\" href=\"javascript:;\">" +
                                    "<div class=\"weui-cell__hd\"><img src=\"\" alt=\"\" style=\"width:20px;margin-right:5px;display:block\"></div>" +
                                    "<div class=\"weui-cell__bd\" style='text-align:left'><p>" + result[i]["name"] + result[i]["mid"] + "</p></div>" +
                                    "<div class=\"weui-cell__ft\">" + result[i]["signdate"] + "</div></a>";
                                $show2.append(td);
                            }
                        }
                    },
                    error: function (result) {
                        alert(result.status);
                    }
                });
            }


        });
        //获取管理员信息
        $adminlist.on('click', function () {
            $.ajax({
                type: "POST",
                url: "meeting_adminList",
                data: {},
                dataType: "json",
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                success: function (result) {
                    $show2.empty();

                    for (let i = 0; i < result.length; i++) {
                        let td = "<a class=\"weui-cell weui-cell_access\" href=\"javascript:;\""
                            + " id=\"" + result[i]["mid"] + "\" "
                            + "<div class=\"weui-cell__hd\"><img src=\"\" alt=\"\" style=\"width:20px;margin-right:5px;display:block\"></div>"
                            + "<div class=\"weui-cell__bd\" style='text-align:left'><p>" + result[i]["name"] + "</p></div>"
                            + "<div class=\"weui-cell__ft\">" + result[i]["mid"] + "</div></a>";
                        $show2.append(td);
                    }
                },
                error: function (result) {
                    alert(result.status);
                }
            });
        });

    });

    //检查权限
    function checkadmin(level) {
        if (level === 0) {
            let $navv = $('#navv');
            $navv.hide();
        }
        return level;
    }

    //提交新的meeting
    function meeting_submit() {
        let title = $("#title").val(),
            addr = $("#addr").val(),
            stt = $("#starttime").val().toString(),
            edt = $("#endtime").val().toString(),
            mid = '${maper.mid}',
            text = "";
        if (title === "" || addr === "" || stt === "" || edt === "") {
            text = "信息不完整!"
            dialogshow(text);
        } else if (stt > edt) {
            text = "开始时间早于结束时间!";
            dialogshow(text);
        } else {
            $.ajax({
                type: "POST",
                url: "meeting_submit",
                data: JSON.stringify({
                    'creemp': mid,
                    'title': title,
                    'addr': addr,
                    'starttime': stt,
                    'endtime': edt
                }),
                dataType: "text",
                contentType: "application/json;charset=utf-8",
                success: function (result) {
                    $('#show2').empty();
                    text = "创建成功！ 会议编号 =  " + result;
                    dialogshow(text);
                },
                error: function (result) {
                    alert(result.status);
                }
            });
        }
    }

    //nav1我参与签到的会议的详情
    function meeting_mySign(element) {
        $.ajax({
            url: "meeting_mySign",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                'signid': element.id,
                'mid': '${maper.mid}'
            }),
            dataType: "text",
            success: function (result) {
                let text = " 签到 时间 为  " + result + "  ";
                dialogshow(text);

            },
            error: function (result) {
                alert(result.status);
            }
        });

    }

    //nav2我创建的会议的详情
    function meeting_mtInfo(element) {
        $.ajax({
            url: "meeting_mtInfo",
            type: "POST",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            data: {
                'info': element.id
            },
            dataType: "json",
            success: function (result) {

                let text = "会议  地址  为 " + result["addr"] + "<br><br>"
                    + "会议  编号  为 " + result["password"] + "<br><br>"
                    + "签到 开始时间  " + result["starttime"] + "<br><br>"
                    + "签到 结束时间  " + result["endtime"];
                dialogshow(text);

            },
            error: function (result) {
                alert(result.status);
            }
        });


    }

    //扫码功能实现
    function meeting_scan(time, resultStr) {
        $.ajax({
            type: "POST",
            url: "meeting_scan",
            data: JSON.stringify({
                'mid': '${maper.mid}',
                'name': '${maper.name}',
                'time': time,
                'mtpassword': resultStr
            }),
            dataType: "text",
            contentType: "application/json;charset=utf-8",
            success: function (result) {
                dialogshow(result);
            },
            error: function (result) {
                alert(result.status);
            }
        });
    }

    //获取当前时间
    function getNowFormatDate() {
        let date = new Date(),
            separator1 = "-",
            separator2 = ":",
            month = date.getMonth() + 1,
            strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        return date.getFullYear() + separator1 + month + separator1 + strDate
            + " " + date.getHours() + separator2 + date.getMinutes()
            + separator2 + date.getSeconds();
    }

    //显示dialog
    function dialogshow(text) {
        let attd = $('#attdtext'),
            dialog = $('#Dialog');
        attd.html(text);
        dialog.show();
    }

    //关闭dialog
    function dialogexit() {
        let dialog = $('#Dialog');
        dialog.hide();
    }

</script>
<body>

<div class="page">
    <div class="page__bd" style="height: 100%;">
        <div class="weui-tab">
            <div class="weui-navbar" id="navv">
                <div class="weui-navbar__item weui-bar__item_on" id="nav1">
                    我的签到
                </div>
                <div class="weui-navbar__item" id="nav2">
                    管理功能
                </div>
            </div>
            <div class="weui-tab__panel">
                <div id="tab1" class="weui_tab_bd_item weui_tab_bd_item_active">

                    <div>
                        <a class="weui-btn  weui-btn_primary" href="javascript:" id="scanmt">
                            签到扫码
                        </a>
                    </div>
                </div>
                <div id="tab2" class="weui_tab_bd_item">

                    <div>
                        <a class="weui-btn weui-btn_default" href="javascript:" id="newattd">
                            新建签到进程
                        </a>
                    </div>
                    <div>
                        <a class="weui-btn  weui-btn_default" href="javascript:" id="adminlist">
                            管理员信息
                        </a>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><label class="weui-label">会议编号</label></div>
                        <div class=\"weui-cell__bd\">
                            <label><input class="weui-input" id="meetpassword" placeholder="会议编号"/></label>
                        </div>
                    </div>
                    <div>
                        <a class="weui-btn  weui-btn_default" href="javascript:" id="mtqrcode">
                            新建会议二维码
                        </a>
                    </div>
                    <div>
                        <a class="weui-btn  weui-btn_default" href="javascript:" id="getattender">
                            查询签到名单
                        </a>

                    </div>

                </div>
                <br>
            </div>
        </div>

        <div id="show1" style="text-align: center">
        </div>
        <div id="show2" style="text-align: center">
        </div>
    </div>
</div>

<div class="js_dialog" id="Dialog" style="display: none;">
    <div class="weui-mask"></div>
    <div class="weui-dialog">
        <div class="weui-dialog__bd" id="attdtext"></div>
        <div class="weui-dialog__ft">
            <a class="weui-dialog__btn weui-dialog__btn_primary" onclick="dialogexit()">确定！</a>
        </div>
    </div>
</div>

</body>
</html>

