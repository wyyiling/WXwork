<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>预检分诊</title>
    <!--必须-->
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui-for-work.min.css">
    <script src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <script src="../../js/zepto.min.js"></script>
    <style type="text/css">
        .circleb {
            height: 160px;
            width: 160px;
            border-radius: 80px;
            text-align: center;
        }
    </style>
</head>
<script>
    $(document).ready(function () {

        let $scanpt = $('#scanpt'),
            $register = $('#register'),
            $guide = $('#guide'),
            $jkk = $('#jkk'),
            $div1 = $('#div1'),
            $showinfo = $('#showinfo'),
            $info = $('#info'),
            $div2 = $('#div2'),
            $div3 = $('#div3'),
            $3cancel = $('#3cancel'),
            $3sub = $('#3sub'),
            $blno = $('#blno'),
            $ptname = $('#ptname'),
            $cardno = $('#cardno'),
            $mobile = $('#mobile'),
            $addr = $('#addr'),
            $nation = $('#nation'),
            $country = $('#country'),
            $birthday = $('#birthday'),
            $xb = $('#xb'),
            $brly = $('#brly'),
            $lstd = $('#lstd'),
            $fzfj = $('#fzfj'),
            $fzzt = $('#fzzt'),
            $bq = $('#bq'),
            $mstime = $('#mestime'),
            $xy = $('#xy'),
            $szy = $('#szy'),
            $ssy = $('#ssy'),
            $tw = $('#tw'),
            $twz = $('#twz'),
            $hx = $('#hx'),
            $xl = $('#xl'),
            $3name = $('#3name'),
            $3idcard = $('#3idcard'),
            $3cellp = $('#3cellphone'),
            $subfz = $('#subfz'),
            Dialog = $('#Dialog'),
            sDialog = $('#sDialog'),
            $3Dialog = $('#3Dialog'),
            cancelfz = $('#cancelfz'),
            conffz = $('#conffz'),
            $fztext = $('#fztext'),
            $3canfz = $('#3cancelfz'),
            $3conffz = $('#3conffz');

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
                    jsApiList: ['scanQRCode', 'openLocation', 'getLocation', 'onLocationChange', 'startAutoLBS']
                });
            },
            error: function (result) {
                alert(result.status);
            }
        });

        $scanpt.on('click', function () {

            wx.scanQRCode({
                desc: 'scanQRCode desc',
                needResult: 1, // 默认为0，扫描结果由企业微信处理，1则直接返回扫描结果，
                scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是条形码（一维码），默认二者都有
                success: function (res) {
                    let cardno = (res.resultStr).split(":")[0]
                    $.ajax({
                        type: "POST",
                        url: "fzgetinfo",
                        data: {
                            'cardno': cardno
                        },
                        contentType: "application/x-www-form-urlencoded;charset=utf-8",
                        dataType: "json",
                        success: function (result) {
                            if (result["minis"] === "success") {
                                $div1.hide();
                                $div2.show();
                                $jkk.text(cardno);
                                $blno.text(result["PID"]);
                                $ptname.text(result["PatientName"]);
                                $cardno.text(result["zlcard"]);
                                $addr.val(result["home"]);
                                $mobile.val(result["mobile"]);
                                $birthday.val(result["DateOfBirth"]);
                                $xb.val(result["SexCode"]);

                                let text = "成功获取信息";
                                $fztext.text(text);
                                sDialog.show();
                            } else {
                                let text = "获取信息失败";
                                $fztext.text(text);
                                sDialog.show();
                            }
                        },
                        error: function (result) {
                            alert(result.status);
                        }
                    });

                },
                error: function (result) {
                    if (result.errMsg.indexOf('function_not_exist') > 0) {
                        alert('版本过低请升级');
                    }
                }
            });
        });

        $register.on('click', function () {
            $div1.hide();
            $div3.show();
        });

        $3cancel.on('click', function () {
            $('.f3form').empty();
            $div3.hide();
            $div1.show();
        });

        $guide.on('click', function () {

            wx.getLocation({
                type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                success: function (res) {
                    let latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                    let longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                    //        let speed = res.speed; // 速度，以米/每秒计
                    //        let accuracy = res.accuracy; // 位置精度

                    wx.openLocation({
                        latitude: latitude, // 纬度，浮点数，范围为90 ~ -90
                        longitude: longitude, // 经度，浮点数，范围为180 ~ -180。
                        name: '郴州市第一人民医院急救中心', // 位置名
                        address: '', // 地址详情说明
                        scale: 16, // 地图缩放级别,整形值,范围从1~28。默认为16
                    });
                    // wx.invoke('startAutoLBS',{
                    //         type: 'gcj02', // wgs84是gps坐标，gcj02是火星坐标
                    //     },
                    //     function(res) {
                    //         if(res.err_msg === "startAutoLBS:ok"){
                    //             wx.onLocationChange(
                    //                 function(resfa) {
                    //                     alert(resfa.errMsg);
                    //                     if(resfa.errMsg === "auto:location:report:location:report:ok"){
                    //                         wx.openLocation({
                    //                             latitude: resfa.latitude, // 纬度，浮点数，范围为90 ~ -90
                    //                             longitude: resfa.longitude, // 经度，浮点数，范围为180 ~ -180。
                    //                             name: '', // 位置名
                    //                             address: '', // 地址详情说明
                    //                             scale: 18, // 地图缩放级别,整形值,范围从1~28。默认为16
                    //                         });
                    //
                    //                     }else {
                    //                         //错误处理
                    //                     }
                    //                 }
                    //             );
                    //         }else {
                    //             //错误处理
                    //         }
                    //     });
                }
            });

        });

        $showinfo.on('click', function () {
            $info.toggle(300);
        });

        $subfz.on('click', function () {
            Dialog.show();
        });

        cancelfz.on('click', function () {
            Dialog.hide();
        });

        conffz.on('click', function () {
            $.ajax({
                type: "POST",
                url: "fzsub",
                data: JSON.stringify({
                    'healthcn': $jkk.text(),
                    'pid': $blno.text(),
                    'zlc': $cardno.text(),
                    'name': $ptname.text(),
                    'sexcd': $xb.val(),
                    'sexnm': $('#xb option:selected').text(),
                    'birthday': $birthday.val(),
                    'nation': $nation.val(),
                    'country': $country.val(),
                    'address': $addr.val(),
                    'mobile': $mobile.val(),
                    'ptsourcecd': $brly.val(),
                    'ptsourcenm': $('#brly option:selected').text(),
                    'lstdcd': $lstd.val(),
                    'lstdnm': $('#lstd option:selected').text(),
                    'fzfjcd': $fzfj.val(),
                    'fzfjnm': $('#fzfj option:selected').text(),
                    'fzzt': $fzzt.val(),
                    'bqcd': $bq.val(),
                    'bqnm': $('#bq option:selected').text(),
                    'mstime': $mstime.val(),
                    'xy': $xy.val(),
                    'szy': $szy.val(),
                    'ssy': $ssy.val(),
                    'twcd': $tw.val(),
                    'twz': $twz.val(),
                    'hx': $hx.val(),
                    'xl': $xl.val(),
                    'creator': '${empcode}',
                    'now': getNowFormatDate()
                }),
                contentType: "application/json;charset=utf-8",
                dataType: "text",
                success: function (result) {
                    if (result === "success") {
                        $div1.show();
                        $div2.hide();
                        $('.clear').empty();
                        let text = "提交成功";
                        $('#fztext').text(text);
                        sDialog.show();

                    } else {
                        let text = "提交失败";
                        $('#fztext').text(text);
                        sDialog.show();
                    }
                },
                error: function (result) {
                    alert(result.status);
                }
            });
            Dialog.hide();
        });

        $3sub.on('click', function () {
            $3Dialog.show();
        });

        $3canfz.on('click', function () {
            $3Dialog.hide();
        });

        $3conffz.on('click', function () {
            $.ajax({
                type: "POST",
                url: "fznewcard",
                data: JSON.stringify({
                        '3name': $3name.val(),
                        '3idc': $3idcard.val(),
                        '3cell': $3cellp.val()
                    }
                ),
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                success: function (result) {
                    if (result["minis"] === "success") {
                        $div3.hide();
                        $div2.show();
                        $jkk.text(result["elcard"]);
                        $blno.text(result["PID"]);
                        $ptname.text(result["PatientName"]);
                        $cardno.text(result["zlcard"]);
                        $addr.val(result["home"]);
                        $mobile.val(result["mobile"]);
                        $birthday.val(result["DateOfBirth"]);
                        $xb.val(result["SexCode"]);

                        let text = "成功获取信息";
                        $fztext.text(text);
                        sDialog.show();
                    } else {
                        let text = "获取信息失败";
                        $fztext.text(text);
                        sDialog.show();
                    }
                },
                error: function (result) {
                    alert(result.status);
                }
            });
            $3Dialog.hide();

        });

    });

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

    function dialogexit() {
        let dialog = $('#sDialog');
        dialog.hide();
    }
</script>
<body>
<div id="div1">
    <div class="weui-cell" style="padding-top: 50px;text-align: center">

        <div style="margin: auto">
            <a class="weui-btn weui-btn_primary circleb" href="javascript:" id="register">
                <p style="padding-top: 60px"> 手动录入 </p>
            </a>
        </div>
        <div style="margin: auto">
            <a class="weui-btn weui-btn_primary circleb" href="javascript:" id="scanpt" style="margin-left: 25px">
                <p style="padding-top: 60px"> 扫码录入 </p>
            </a>

        </div>
    </div>
    <div>
        <a class="weui-btn  weui-btn_primary" style="margin-top: 30px" href="javascript:" id="guide">
            <p> 地图 </p>
        </a>
    </div>
</div>

<div id="div2" style="padding-top: 10px; display: none">
    <div class="weui-cell" style="display: none">
        <div class="weui-cell__hd"><label class="weui-label">健康卡号</label></div>
        <div class="weui-cell__bd">
            <label>
                <input class="weui-input" type="text" id="jkk">
            </label>
        </div>
    </div>
    <div class="weui-cell" id="showinfo" style="background-color: darkgrey">
        <div class="weui-cell__hd"><label class="weui-label">病人姓名</label></div>
        <div class="weui-cell__bd">
            <p class="weui-input" id="ptname"></p>
        </div>
    </div>
    <div id="info" style="display: none;background-color: darkgrey">
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">病历号</label></div>
            <div class="weui-cell__bd">
                <p class="weui-input" id="blno"></p>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">诊疗卡号</label></div>
            <div class="weui-cell__bd">
                <p class="weui-input" id="cardno"></p>
            </div>
        </div>
        <div class="weui-cell weui-cell_select weui-cell_select-after">
            <div class="weui-cell__hd">
                <label class="weui-label">性别</label>
            </div>
            <div class="weui-cell__bd">
                <label>
                    <select class="weui-select" name="xb" id="xb">
                        <option value="1">男</option>
                        <option value="2">女</option>
                    </select>
                </label>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">出生日期</label></div>
            <div class="weui-cell__bd">
                <label>
                    <input class="weui-input" type="text" id="birthday">
                </label>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">民族</label></div>
            <div class="weui-cell__bd">
                <label>
                    <input class="weui-input" type="text" id="nation" value="汉族">
                </label>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">国籍</label></div>
            <div class="weui-cell__bd">
                <label>
                    <input class="weui-input" type="text" id="country" value="中国">
                </label>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">家庭地址</label></div>
            <div class="weui-cell__bd">
                <label>
                    <input class="weui-input" type="text" id="addr">
                </label>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">手机号码</label></div>
            <div class="weui-cell__bd">
                <label>
                    <input class="weui-input" type="number" id="mobile">
                </label>
            </div>
        </div>
    </div>
    <div class="weui-cell weui-cell_select weui-cell_select-after">
        <div class="weui-cell__hd">
            <label class="weui-label">病人来源</label>
        </div>
        <div class="weui-cell__bd">
            <label>
                <select class="weui-select" name="brly" id="brly">
                    <option value="001">步行</option>
                    <option value="002">轮椅</option>
                    <option value="003" selected="selected">120</option>
                    <option value="004">扶走</option>
                    <option value="005">平车</option>
                    <option value="006">110</option>
                    <option value="007">他院转送</option>
                    <option value="008">抱走</option>
                </select>
            </label>
        </div>
    </div>
    <div class="weui-cell weui-cell_select weui-cell_select-after">
        <div class="weui-cell__hd">
            <label class="weui-label">绿色通道名称</label>
        </div>
        <div class="weui-cell__bd">
            <label>
                <select class="weui-select" name="lstd" id="lstd">
                    <option value="001">急性创伤</option>
                    <option value="002">急性心梗</option>
                    <option value="003">急性心衰</option>
                    <option value="004">急性呼衰</option>
                    <option value="005">急性脑卒中</option>
                    <option value="006">急性颅脑损伤</option>
                    <option value="007">高危孕产妇</option>
                </select>
            </label>
        </div>
    </div>
    <div class="weui-cell weui-cell_select weui-cell_select-after">
        <div class="weui-cell__hd">
            <label class="weui-label">分诊分级</label>
        </div>
        <div class="weui-cell__bd">
            <label>
                <select class="weui-select" name="fzfj" id="fzfj">
                    <option value="001">Ⅰ级</option>
                    <option value="002">II级</option>
                    <option value="003">III级</option>
                    <option value="004">Ⅳ级</option>

                </select>
            </label>
        </div>
    </div>
    <div class="weui-cell weui-cell_select weui-cell_select-after">
        <div class="weui-cell__hd">
            <label class="weui-label">分诊状态</label>
        </div>
        <div class="weui-cell__bd">
            <label>
                <select class="weui-select" name="fzzt" id="fzzt">
                    <option value="10">普通急诊</option>
                    <option value="50">留观</option>
                    <option value="90">抢救</option>

                </select>
            </label>
        </div>
    </div>
    <div class="weui-cell weui-cell_select weui-cell_select-after">
        <div class="weui-cell__hd">
            <label class="weui-label">病区名称</label>
        </div>
        <div class="weui-cell__bd">
            <label>
                <select class="weui-select" name="bq" id="bq">
                    <option value="jz001">留观区</option>
                    <option value="jz002">抢救区</option>
                    <option value="jz007">急诊流水</option>
                </select>
            </label>
        </div>
    </div>
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">测量时间</label></div>
        <div class="weui-cell__bd">
            <label>
                <input class="weui-input clear" type="datetime-local" id="mestime">
            </label>
        </div>
    </div>
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">血氧饱和度</label></div>
        <div class="weui-cell__bd">
            <label>
                <input class="weui-input clear" type="number" id="xy">
            </label>
        </div>
    </div>
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">舒张压</label></div>
        <div class="weui-cell__bd">
            <label>
                <input class="weui-input clear" type="number" id="szy">
            </label>
        </div>
        <div class="weui-cell__hd"><label class="weui-label">收缩压</label></div>
        <div class="weui-cell__bd">
            <label>
                <input class="weui-input clear" type="number" id="ssy">
            </label>
        </div>
    </div>
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">体温</label></div>
        <div class="weui-cell__bd">
            <label>
                <input class="weui-input clear" type="number" style="width: 75px" id="twz">
            </label>
        </div>

        <div class="weui-cell weui-cell_select weui-cell_select-after">
            <div class="weui-cell__hd"><label class="weui-label">体温类型</label></div>
            <div class="weui-cell__bd">
                <label>
                    <select class="weui-select" name="tw" id="tw">
                        <option value="E">耳温</option>
                        <option value="G">肛温</option>
                        <option value="K">口温</option>
                        <option value="Y">腋温</option>
                    </select>
                </label>
            </div>
        </div>

    </div>
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">呼吸</label></div>
        <div class="weui-cell__bd">
            <label>
                <input class="weui-input clear" type="number" id="hx">
            </label>
        </div>

    </div>
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">心率</label></div>
        <div class="weui-cell__bd">
            <label>
                <input class="weui-input clear" type="number" id="xl">
            </label>
        </div>

    </div>
    <div>
        <a class="weui-btn weui-btn_primary" id="subfz">提交</a>
    </div>
</div>
<div id="div3" style="padding-top: 10px; display: none">
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">姓名</label></div>
        <div class="weui-cell__bd">
            <label>
                <input class="weui-input f3form" type="text" id="3name">
            </label>
        </div>
    </div>
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">身份证号</label></div>
        <div class="weui-cell__bd">
            <label>
                <input class="weui-input f3form" type="text" id="3idcard" maxlength="18">
            </label>
        </div>
    </div>
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">手机号码</label></div>
        <div class="weui-cell__bd">
            <label>
                <input class="weui-input f3form" type="number" id="3cellphone" maxlength="11">
            </label>
        </div>
    </div>
    <div>

        <a class="weui-btn weui-btn_default" id="3cancel">返回</a>

        <a class="weui-btn weui-btn_primary" id="3sub">提交</a>

    </div>
</div>
<div class="js_dialog" id="Dialog" style="display: none;">
    <div class="weui-mask"></div>
    <div class="weui-dialog">
        <div class="weui-dialog__bd">确认信息填写完整后提交。</div>
        <div class="weui-dialog__ft">
            <a class="weui-dialog__btn weui-dialog__btn_default" id="cancelfz">取消</a>
            <a class="weui-dialog__btn weui-dialog__btn_primary" id="conffz">确认</a>
        </div>
    </div>
</div>
<div class="js_dialog" id="3Dialog" style="display: none;">
    <div class="weui-mask"></div>
    <div class="weui-dialog">
        <div class="weui-dialog__bd">确认信息填写完整后提交。</div>
        <div class="weui-dialog__ft">
            <a class="weui-dialog__btn weui-dialog__btn_default" id="3cancelfz">取消</a>
            <a class="weui-dialog__btn weui-dialog__btn_primary" id="3conffz">确认</a>
        </div>
    </div>
</div>
<div class="js_dialog" id="sDialog" style="display: none;">
    <div class="weui-mask"></div>
    <div class="weui-dialog">
        <div class="weui-dialog__bd" id="fztext"></div>
        <div class="weui-dialog__ft">
            <a class="weui-dialog__btn weui-dialog__btn_primary" onclick="dialogexit()">确定！</a>
        </div>
    </div>
</div>
</body>
</html>
