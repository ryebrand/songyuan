/**
 * Wewin PC 打印服务
 */

var WewinPrintService = function () {

    function WewinPrintService() {
        this.initParams();
    }

    WewinPrintService.prototype = {
        /**
         * 初始化原始参数
         */
        initParams: function () {
            this.printername = "";
            this.Isp30 = -1;
            this.fontname = "宋体";
            this.data = [];
            this.dots = 0;
        },
        /**
         * 初始化点击事件
         */
        initClickEvent: function () {
            $(".view").click(function () {
                $(this).remove();
            });
            $(".view .main").click(function () {
                window.event.stopPropagation();
            });
        }
    }

    /**
     * 测量字符串长度
     * @param {String} str 
     * @param {Number} size 
     */
    WewinPrintService.prototype.getLen = function (str, size) {
        var webType = this.isIE();
        if (webType) {
            //IE浏览器
            var div = document.createElement("div");
            div.id = "divgetlen";
            div.style.fontSize = size + "px";
            div.style.position = "absolute";
            div.style.fontFamily = this.fontname;
            var length = 0;
            var texts = [];
            if (str.length != 1) {
                texts = str.split("");
            } else {
                texts[0] = str;
            }
            document.body.appendChild(div);
            var dom = document.getElementById('divgetlen');
            for (var i = 0; i < texts.length; i++) {
                div.innerHTML = texts[i];
                if (dom.clientWidth == 0) {
                    length += dom.scrollWidth;
                } else {
                    length += dom.clientWidth;
                }
            }
            document.body.removeChild(dom);
            return length;
        } else {
            //非IE浏览器
            var canvas = document.getElementById('wewincanvas');
            var ctx = canvas.getContext("2d");
            ctx.font = size + "px " + this.fontname;
            var length = ctx.measureText(str).width;
            return length;
        }
    }

    /**
     * json入口，获取用户传入的json数据
     * @param {String} json 
     */
    WewinPrintService.prototype.LabelPrint = function (data) {
        this.data = data;
    }

    /**
     * 预览窗口加载
     */
    WewinPrintService.prototype.Load = function () {
        this.CreateView();
        this.Getperinterlist();
    }

    /**
     * 创建预览窗口
     */
    WewinPrintService.prototype.CreateView = function () {
        var html = "";
        html += "<div class=\"wewinview noselect\">";
        html += "    <div class=\"wewinmain\">";
        html += "        <div class=\"bigtitle\" id=\"btitle\">";
        html += "            重庆品胜 - 资管打印 - 打印预览";
        html += "        <\/div>";
        html += "        <div class=\"wewinsplit\"><\/div>";
        html += "        <div class=\"choose\">";
        html += "            <div class=\"title\" id=\"printnamediv\">";
        html += "                <div class=\"line\">";
        html += "                    <div class=\"left\">选择打印机：<\/div>";
        html += "                    <div class=\"right\">";
        html += "                        <select id=\"printername\" onchange=\"\"><\/select>";
        html += "                    <\/div>";
        html += "                <\/div>";
        html += "                <div class=\"line\">";
        html += "                    <div class=\"left\">标签型号：<\/div>";
        html += "                    <div class=\"right\">";
        html += "                        <select id=\"labelname\" onchange=\"changetype()\"><\/select>";
        html += "                    <\/div>";
        html += "                <\/div>";
        html += "            <\/div>";
        html += "            <div class=\"wewininfo\">";
        html += "                该标签支持的打印机型号：";
        html += "                <span id=\"printtype\"><\/span>";
        html += "            <\/div>";
        html += "        <\/div>";
        html += "        <div class=\"wewinbtns\">";
        html += "            <button class=\"wewinbtn\" type=\"button\" name=\"print\" onclick=\"Print()\">";
        html += "                打印";
        html += "            <\/button>";
        html += "            <button class=\"wewinbtn\" type=\"button\" name=\"print\" onclick=\"lookXml()\">";
        html += "                查看报文";
        html += "            <\/button>";
        html += "            <button class=\"wewinbtn\" type=\"button\" name=\"print\" onclick=\"lookHelp()\">";
        html += "                帮助";
        html += "            <\/button>";
        html += "            <button class=\"wewinbtn\" type=\"button\" name=\"close\" onclick=\"closeWindows()\">";
        html += "                关闭";
        html += "            <\/button>";
        html += "        <\/div>";
        html += "        <div class=\"wewinsplit2\"><\/div>";
        html += "        <div class=\"tags\">";
        html += "            <div id=\"preview\"><\/div>";
        html += "        <\/div>";
        html += "        <div class=\"wewindown\">";
        html += "            <div class=\"left version\">版本号：";
        html += "                <span id=\"versionnum\"><\/span>";
        html += "            <\/div>";
        html += "            <div class=\"right\">";
        html += "                <a href=\".\/plug(pop)_V1.0.4.rar\" target=\"_blank\">wewin打印服务插件下载<\/a>";
        html += "            <\/div>";
        html += "        <\/div>";
        html += "        <div class=\"cha\" onclick=\"closeWindows()\">&#10006<\/div>";
        html += "    <\/div>";
        html += "    <canvas id=\"wewincanvas\" style=\"display: none\"><\/canvas>";
        html += "<\/div>";
        $(document.body).append(html);
        // this.initClickEvent();
    }

    /**
     * 获取打印机列表，并添加到下拉选择框
     */
    WewinPrintService.prototype.Getperinterlist = function () {
        //查询打印机数据
        var rawData = {
            "handleType": "0",
            "printer": "",
            "hasDrive": "0",
            "copyNum": "1",
            "labels": [
                {
                    "labelWidth": "0",
                    "labelHeight": "0",
                    "rfid": "",
                    "ddfLength": "0",
                    "cutOption": "0",
                    "blocks": []
                }
            ]
        }
        var sendData = "";
        sendData = this.resolveData(rawData);
        var url = this.getTrueUrl();
        this.Ajax('post', url, sendData, function (data) {
            var printers = [];
            var jsonData = JSON.parse(data);
            printers = jsonData.content;
            var pelement = document.getElementById("printername");
            if (pelement != null) {
                pelement.innerHTML = "";
            }

            var temp = true;
            for (var i = 0; i < printers.length; i++) {
                var pname = printers[i];
                if (pname.printer.toLowerCase().indexOf("wewin") != -1 || pname.printer.indexOf("HiTi") != -1) {
                    temp = false;
                }
            }
            if (temp) {
                pelement.options.add(new Option("当前无WEWIN打印机，请接入", "-1"));
            }

            for (var i = 0; i < printers.length; i++) {
                var pname = printers[i];
                if (pname.printer.toLowerCase().indexOf("wewin") != -1 || pname.printer.indexOf("HiTi") != -1) {
                    pelement.options.add(new Option(pname.printer, pname.printer + "&&" + pname.dots + "&&" + pname.hasDrive));
                }
            }
        }, function (error) {
            console.log('error' + error);
        });
    }

    /**
     * 打印预览函数
     * @param {Function} viewPrintFunc 
     */
    WewinPrintService.prototype.ViewPrint = function (viewPrintFunc) {
        viewPrintFunc(this.data);
    }

    /**
     * 查看报文
     */
    WewinPrintService.prototype.lookXml = function () {
        alert(JSON.stringify(this.data));
    }

    /**
     * 查看帮助
     */
    WewinPrintService.prototype.lookHelp = function () {
        var text = "";
        text += "————————重庆品胜科技有限公司————————\n";
        text += "帮助：\n";
        text += "* H50plus\/P1200\/268系列打印机需安装驱动；\n";
        text += "* 请在页面底部下载最新的wewin打印服务插件；\n";
        text += "* 若使用IE浏览器版本为IE9及以下，点击IE浏览器的的“工具->Internet 选项->安全->自定义级别” 将 “其他” 选项中的 “通过域访问数据源” 选中为 “启用” 或者 “提示” ，点击确定就可以了。\n";
        text += "* 服务热线：4000238080";
        alert(text);
    }

    WewinPrintService.prototype.parseJsonElement = function (ele) {
        if (typeof (ele) == "string") {
            var arr = [];
            arr.push(ele);
            return arr;
        } else if (typeof (ele) == "object") {
            return ele;
        }
    }

    /**
     * 打印
     */
    WewinPrintService.prototype.GetPrinter = function () {
        this.printername = document.getElementById("printername").value;
        if (this.printername == "-1") {
            alert("当前无WEWIN打印机，请接入");
            return;
        }
    }

    /**
     * 打印函数
     * @param {Function} doLabelPrintFunc 
     */
    WewinPrintService.prototype.DoLabelPrint = function (doLabelPrintFunc) {
        doLabelPrintFunc(this.data);
    }

    /**
     * 多个标签全选
     */
    WewinPrintService.prototype.chooseAllCheckbox = function () {
        var allchebox = document.getElementById("allcb");
        var data = this.data;

        if (typeof (data) == "string") {
            var xmldoc = loadXML(this.data);
            data = xmldoc.getElementsByTagName("Print");
        }

        if (allchebox.checked) {
            for (var i = 0; i < data.length; i++) {
                document.getElementById("cb" + i).checked = true;
            }
        } else {
            for (var i = 0; i < data.length; i++) {
                document.getElementById("cb" + i).checked = false;
            }
        }
    }

    /**
     * 编码转换
     * @param {String} str 
     */
    WewinPrintService.prototype.utf16to8 = function (str) {
        var out, i, len, c;
        out = "";
        len = str.length;
        for (i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                out += str.charAt(i);
            } else if (c > 0x07FF) {
                out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            } else {
                out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            }
        }
        return out;
    }

    /**
     * 打印方法
     * @param {Object} rawData 
     */
    WewinPrintService.prototype.Print = function (rawData) {
        this.printername = "wewin P70+ 免驱模式&&8&&0";
        if (this.printername != -1) {
            var parr = this.printername.split("&&");
            var pname = parr[0];
            var dots = parr[1];
            var hasDrive = parr[2];
            rawData.printer = pname;
            rawData.hasDrive = hasDrive;
            var sendData = "";
            sendData = this.resolveData(rawData);
            console.log("打印前的数据(json)：", JSON.parse(sendData));
            console.log("打印前的数据(string)：", sendData);
            var url = this.getTrueUrl();
            this.Ajax('post', url, sendData, function (data) {
                console.log('success' + data);
            }, function (error) {
                console.log('error' + error);
            });
            return sendData;
        } else {
            return "-1";
        }
    }

    /**
     * 解析原始数据为打印数据
     * @param {Object} rawData 
     */
    WewinPrintService.prototype.resolveData = function (rawData) {
        var sendData = "";
        sendData = JSON.parse(JSON.stringify(rawData), function (key, value) {
            if (typeof (value) == "number") {
                return value.toString();
            } else {
                return value;
            }
        });
        sendData = JSON.stringify(sendData);
        // console.log("resolveData: " + sendData);
        return sendData;
    }

    /**
     * Ajax请求封装
     * @param {String} type 
     * @param {String} url 
     * @param {String} data 
     * @param {Function} success 
     * @param {Function} failed 
     */
    WewinPrintService.prototype.Ajax = function (type, url, data, success, failed) {
        // 创建ajax对象
        var xhr = null;
        if (window.XMLHttpRequest) {
            xhr = new XMLHttpRequest();
        } else {
            xhr = new ActiveXObject('Microsoft.XMLHTTP')
        }

        var type = type.toUpperCase();
        // 用于清除缓存
        var random = Math.random();

        if (typeof data == 'object') {
            var str = '';
            for (var key in data) {
                str += key + '=' + data[key] + '&';
            }
            data = str.replace(/&$/, '');
        }

        if (type == 'GET') {
            if (data) {
                xhr.open('GET', url + '?' + data, true);
            } else {
                xhr.open('GET', url + '?t=' + random, true);
            }
            xhr.send();

        } else if (type == 'POST') {
            xhr.open('POST', url, true);
            // 如果需要像 html 表单那样 POST 数据，请使用 setRequestHeader() 来添加 http 头。
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.send(data);
        }

        // 处理返回数据
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                    success(xhr.responseText);
                } else {
                    if (failed) {
                        failed(xhr.status);
                    }
                }
            }
        }
    }

    WewinPrintService.prototype.addArr = function (arr1, arr2) {
        for (var i = 0; i < arr2.length; i++) {
            arr1.push(arr2[i]);
        }
        return arr1;
    }

    /**
     * 解析xml
     * @param {String} xmlString 
     */
    WewinPrintService.prototype.loadXML = function (xmlString) {
        var xmlDoc = null;
        if (!window.DOMParser && window.ActiveXObject) {
            var xmlDomVersions = ['MSXML.2.DOMDocument.6.0',
                'MSXML.2.DOMDocument.3.0', 'Microsoft.XMLDOM'
            ];
            for (var i = 0; i < xmlDomVersions.length; i++) {
                try {
                    xmlDoc = new ActiveXObject(xmlDomVersions[i]);
                    xmlDoc.async = false;
                    xmlDoc.loadXML(xmlString);
                    break;
                } catch (e) { }
            }
        } else if (window.DOMParser && document.implementation &&
            document.implementation.createDocument) {
            try {
                domParser = new DOMParser();
                xmlDoc = domParser.parseFromString(xmlString, 'text/xml');
            } catch (e) { }
        } else {
            return null;
        }
        return xmlDoc;
    }

    /**
     * rfid解析
     * @param {Object} obj 
     */
    WewinPrintService.prototype.rfidParse = function (obj) {
        var rfidType = obj.rfidType;
        var rfidWay = obj.rfidWay;
        var rfidData = obj.rfidData;
        var rfidResult = {
            "EPC": "01",
            "USER": "03"
        }
        var rfid = rfidResult[rfidType] + rfidWay + rfidData;

        return rfid;
    }

    /**
     * 判断浏览器是否为IE浏览器
     */
    WewinPrintService.prototype.isIE = function () {
        if (!!window.ActiveXObject || "ActiveXObject" in window) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前协议对应的请求服务地址
     */
    WewinPrintService.prototype.getTrueUrl = function () {
        var url = "http://127.0.0.1:18188";
        var protocol = window.location.protocol.replace(":", "");
        var webType = this.isIE();

        if (webType) {
            if (protocol == "https") {
                url = "https://127.0.0.1:18189";
            }
        }

        console.log("请求服务地址：" + url);

        return url;
    }

    //-------------------------------打印方法------------------------------

    /**
     * 打印预览
     * @param {String} str - 需要打印的字符串
     * @param {Number} fontHeight - 字体高度
     * @param {Number} printWidth - 换行宽度
     * @return {Array}
     */
    WewinPrintService.prototype.PrintTextView = function (obj, type) {
        var str = obj.str;
        var fontHeight = obj.fontHeight;
        var printWidth = obj.printWidth;
        var backstr = [];
        var strLen = 0;
        var temp = 0;
        var strs = str.split("");
        var tLen = 0;
        var letter = [];
        for (var i = 0; i < strs.length; i++) {
            // if (this.isChinese(strs[i])) {
            //     tLen = fontHeight / 2;
            //     letter.push(strs[i]);
            // } else {
            //     tLen = this.getLen(strs[i], fontHeight);
            // }
            tLen = this.getLen(strs[i], fontHeight);
            strLen += tLen;
            if ((strLen - printWidth) > 0) {
                // if (letter.length != 0 && letter.length % 2 == 0) {
                //     i -= 1;
                // }
                backstr.push(str.substring(temp, i));
                temp = i;
                strLen = 0;
                i--;
                tArr = [];
                letter = [];
            }
            if (i == str.length - 1) {
                backstr.push(str.substring(temp, i + 1));
            }
        }
        if (type == 1) {
            return backstr.join("\n");
        } else if (type == 0) {
            return backstr.join("<br/>");
        }
    }

    /**
     * 打印预览自适应
     * @param {String} str - 需要打印的字符串
     * @param {Number} fontHeight - 字体高度
     * @param {Number} printWidth - 换行宽度
     * @param {Number} y - y坐标
     * @param {Number} rotate - 旋转
     * @param {Number} xoffset - Text内部行间距
     * @param {Number} loffset - Text外部行间距
     * @param {Number} maxH - 换行间距
     * @return {Number} - 返回字体高度
     */
    WewinPrintService.prototype.AutoPrintTextView = function (obj) {
        var str = obj.str;
        var fontHeight = obj.fontHeight;
        var printWidth = obj.printWidth;
        var y = obj.y;
        var xoffset = obj.xoffset;
        var loffset = obj.loffset;
        var backstr = [];
        var flag = true;

        var maxY = y + obj.maxH * 8;
        var newY = y;
        while (flag) {
            for (var i = 0; i < str.length; i++) {
                backstr = this.autoSplit(fontHeight, str[i], printWidth);
                for (var j = 0; j < backstr.length; j++) {
                    newY += fontHeight + xoffset;
                }
                newY += loffset;
            }
            if (newY > maxY) {
                newY = y;
                fontHeight--;
            } else {
                flag = false;
            }
        }
        return fontHeight;
    }

    /**
     * 文本打印函数
     * @param {String} str - 需要打印的字符串
     * @param {Number} fontHeight - 字体高度
     * @param {Number} fontWeight - 字体黑度
     * @param {Number} printWidth - 换行宽度
     * @param {Number} x - x坐标
     * @param {Number} y - y坐标
     * @param {Number} rotate - 旋转
     * @param {Number} xoffset - Text内部行间距
     * @param {Number} loffset - Text外部行间距
     * @param {Number} maxH - 换行间距
     * @param {Number} ptype - 打印方式（0：靠左打印；1：居中打印；2：靠右打印）
     * @param {Number} startPos - 垂直居中打印的起始坐标
     * @param {Number} centerThick - 垂直居中打印的限制高度
     * @param {Number} height - 标签高度
     * @return {Number} - 返回下一行打印的x坐标
     */
    WewinPrintService.prototype.PrintText = function (obj) {
        var str = obj.str;
        var fontHeight = obj.fontHeight;
        var fontWidth = fontHeight / 2;
        var fontWeight = obj.fontWeight;
        var printWidth = obj.printWidth;
        var x = obj.x;
        var y = obj.y;
        var rotate = obj.rotate;
        var xoffset = obj.xoffset;
        var loffset = obj.loffset;
        var ptype = obj.ptype;

        var backstr = [];
        var flag = true;

        var startPos = obj.startPos;
        var centerThick = obj.centerThick;
        var height = obj.height;

        var resultArr = [];

        //获取打印机分辨率(8 | 12)
        var printername = this.printername;
        var parr = this.printername.split("&&");
        var dots = parr[1];

        var pname = this.printername;
        if (rotate == 0) {
            //正竖向打印(y递增)
            var maxY = y + obj.maxH * dots;
            var newY = y;
            while (flag) {
                for (var i = 0; i < str.length; i++) {
                    backstr = this.autoSplit(fontHeight, str[i], printWidth);
                    for (var j = 0; j < backstr.length; j++) {
                        newY += fontHeight + xoffset;
                    }
                    newY += loffset;
                }
                if (newY > maxY) {
                    newY = y;
                    fontHeight--;
                } else {
                    flag = false;
                }
            }

            fontWidth = fontHeight / 2;
            var tempH = y;
            if (startPos != undefined && centerThick != undefined && height != undefined) {
                startPos *= dots;
                centerThick *= dots;
                for (var i = 0; i < str.length; i++) {
                    backstr = this.autoSplit(fontHeight, str[i], printWidth);
                    for (var j = 0; j < backstr.length; j++) {
                        y += fontHeight + xoffset;
                    }
                    y += loffset;
                }
                y = startPos + (centerThick / 2 - Math.abs(y - loffset - tempH) / 2);
            }
            for (var i = 0; i < str.length; i++) {
                backstr = this.autoSplit(fontHeight, str[i], printWidth);
                for (var j = 0; j < backstr.length; j++) {
                    var tempX = x;
                    tempX = this.returnPtype(backstr[j], ptype, tempX, fontHeight, printWidth, "+");
                    var obj = {};
                    obj.type = 0;
                    obj.x = tempX;
                    obj.y = y;
                    obj.fontWidth = fontWidth;
                    obj.fontHeight = fontHeight;
                    obj.fontWeight = fontWeight;
                    obj.fontName = this.fontname;
                    obj.content = backstr[j];
                    obj.oritention = rotate;
                    resultArr.push(obj);
                    y += fontHeight + xoffset;
                }
                y += loffset;
            }
        } else if (rotate == 1) {
            //右横向打印(x递增)
            var maxX = x + obj.maxH * dots;
            var newX = x;
            while (flag) {
                for (var i = 0; i < str.length; i++) {
                    backstr = this.autoSplit(fontHeight, str[i], printWidth);
                    for (var j = 0; j < backstr.length; j++) {
                        newX += fontHeight + xoffset;
                    }
                    newX += loffset;
                }
                if (newX > maxX) {
                    newX = x;
                    fontHeight--;
                } else {
                    flag = false;
                }
            }

            fontWidth = fontHeight / 2;
            var tempH = x;
            if (startPos != undefined && centerThick != undefined && height != undefined) {
                startPos *= dots;
                centerThick *= dots;
                for (var i = 0; i < str.length; i++) {
                    backstr = this.autoSplit(fontHeight, str[i], printWidth);
                    for (var j = 0; j < backstr.length; j++) {
                        x += fontHeight + xoffset;
                    }
                    x += loffset;
                }
                x = (height - startPos) + (centerThick / 2 - Math.abs(x - loffset - tempH) / 2);
            }
            for (var i = 0; i < str.length; i++) {
                backstr = this.autoSplit(fontHeight, str[i], printWidth);
                for (var j = 0; j < backstr.length; j++) {
                    var tempY = y;
                    tempY = this.returnPtype(backstr[j], ptype, tempY, fontHeight, printWidth, "-");
                    var obj = {};
                    obj.type = 0;
                    obj.x = x;
                    obj.y = tempY;
                    obj.fontWidth = fontWidth;
                    obj.fontHeight = fontHeight;
                    obj.fontWeight = fontWeight;
                    obj.fontName = this.fontname;
                    obj.content = backstr[j];
                    obj.oritention = rotate;
                    resultArr.push(obj);
                    x += fontHeight + xoffset;
                }
                x += loffset;
            }
        } else if (rotate == 2) {
            //反竖向打印(y递减)
            var minY = y - obj.maxH * dots;
            var newY = y;
            while (flag) {
                for (var i = 0; i < str.length; i++) {
                    backstr = this.autoSplit(fontHeight, str[i], printWidth);
                    for (var j = 0; j < backstr.length; j++) {
                        newY -= fontHeight + xoffset;
                    }
                    newY -= loffset;
                }
                if (newY < minY) {
                    newY = y;
                    fontHeight--;
                } else {
                    flag = false;
                }
            }

            fontWidth = fontHeight / 2;
            var tempH = y;
            if (startPos != undefined && centerThick != undefined && height != undefined) {
                startPos *= dots;
                centerThick *= dots;
                for (var i = 0; i < str.length; i++) {
                    backstr = this.autoSplit(fontHeight, str[i], printWidth);
                    for (var j = 0; j < backstr.length; j++) {
                        y -= fontHeight + xoffset;
                    }
                    y -= loffset;
                }
                y = startPos - (centerThick / 2 - Math.abs(y + loffset - tempH) / 2);
            }
            for (var i = 0; i < str.length; i++) {
                backstr = this.autoSplit(fontHeight, str[i], printWidth);
                for (var j = 0; j < backstr.length; j++) {
                    var tempX = x;
                    tempX = this.returnPtype(backstr[j], ptype, tempX, fontHeight, printWidth, "-");
                    var obj = {};
                    obj.type = 0;
                    obj.x = tempX;
                    obj.y = y;
                    obj.fontWidth = fontWidth;
                    obj.fontHeight = fontHeight;
                    obj.fontWeight = fontWeight;
                    obj.fontName = this.fontname;
                    obj.content = backstr[j];
                    obj.oritention = rotate;
                    resultArr.push(obj);
                    y -= fontHeight + xoffset;
                }
                y -= loffset;
            }
        } else if (rotate == 3) {
            //左横向打印(x递减)
            var minX = x - obj.maxH * dots;
            var newX = x;
            while (flag) {
                for (var i = 0; i < str.length; i++) {
                    backstr = this.autoSplit(fontHeight, str[i], printWidth);
                    for (var j = 0; j < backstr.length; j++) {
                        newX -= fontHeight + xoffset;
                    }
                    newX -= loffset;
                }
                if (newX < minX) {
                    newX = x;
                    fontHeight--;
                } else {
                    flag = false;
                }
            }

            fontWidth = fontHeight / 2;
            var tempH = x;
            if (startPos != undefined && centerThick != undefined && height != undefined) {
                startPos *= dots;
                centerThick *= dots;
                for (var i = 0; i < str.length; i++) {
                    backstr = this.autoSplit(fontHeight, str[i], printWidth);
                    for (var j = 0; j < backstr.length; j++) {
                        x -= fontHeight + xoffset;
                    }
                    x -= loffset;
                }
                x = Math.abs(startPos - centerThick) - (centerThick / 2 - Math.abs(x + loffset - tempH) / 2);
            }
            for (var i = 0; i < str.length; i++) {
                backstr = this.autoSplit(fontHeight, str[i], printWidth);
                for (var j = 0; j < backstr.length; j++) {
                    var tempY = y;
                    tempY = this.returnPtype(backstr[j], ptype, tempY, fontHeight, printWidth, "+");
                    var obj = {};
                    obj.type = 0;
                    obj.x = x;
                    obj.y = tempY;
                    obj.fontWidth = fontWidth;
                    obj.fontHeight = fontHeight;
                    obj.fontWeight = fontWeight;
                    obj.fontName = this.fontname;
                    obj.content = backstr[j];
                    obj.oritention = rotate;
                    resultArr.push(obj);
                    x -= fontHeight + xoffset;
                }
                x -= loffset;
            }
        }

        return resultArr;
    }

    /**
     * 二维码打印函数
     * @param {String} str - 需要生成二维码的字符串
     * @param {Number} x - x坐标
     * @param {Number} y - y坐标
     * @param {Number} width - 二维码宽度
     * @param {Number} rotate - 旋转
     */
    WewinPrintService.prototype.PrintQrcode = function (obj) {
        var x = obj.x;
        var y = obj.y;
        var width = obj.width;
        var str = obj.str;
        var rotate = obj.rotate;

        var CodesArr = [{
            "type": "2",
            "x": x,
            "y": y,
            "width": width,
            "content": str,
            "oritention": rotate
        }];

        return CodesArr;
    }

    /**
     * 条形码打印函数
     * @param {String} str - 需要生成条形码的字符串
     * @param {Number} x - x坐标
     * @param {Number} y - y坐标
     * @param {Number} rotate - 旋转
     * @param {Number} height - 条码高度
     * @param {Number} width - 条码单元宽度
     */
    WewinPrintService.prototype.PrintBarcode = function (obj) {
        var codeType = obj.codeType;
        var x = obj.x;
        var y = obj.y;
        var pwidth = obj.pwidth;
        var height = obj.height;
        var str = obj.str;
        var rotate = obj.rotate;

        var BarcodesArr = [{
            "type": "1",
            "codeType": codeType,
            "x": x,
            "y": y,
            "pwidth": pwidth,
            "height": height,
            "content": str,
            "oritention": rotate
        }];

        return BarcodesArr;
    }

    /**
     * 线条打印函数
     * @param {Number} px - x坐标
     * @param {Number} py - y坐标
     * @param {Number} pex - 旋转
     * @param {Number} pey - 条码高度
     * @param {Number} lwidth - 条码单元宽度
     */
    WewinPrintService.prototype.PrintLine = function (obj) {
        var x = obj.x;
        var y = obj.y;
        var thickness = obj.thickness;
        var ex = obj.ex;
        var ey = obj.ey;

        var LinesArr = [{
            "type": "4",
            "x": x,
            "y": y,
            "thickness": thickness,
            "ex": ex,
            "ey": ey
        }];

        return LinesArr;
    }

    /**
     * 图片打印函数
     * @param {Number} px - x坐标
     * @param {Number} py - y坐标
     * @param {Number} pex - 旋转
     * @param {Number} pey - 条码高度
     * @param {Number} lwidth - 条码单元宽度
     */
    WewinPrintService.prototype.PrintLogo = function (obj) {
        var x = obj.x;
        var y = obj.y;
        var width = obj.width;
        var height = obj.height;
        var path = obj.path;
        var rotate = obj.rotate;

        var ImagesArr = [{
            "type": "3",
            "x": x,
            "y": y,
            "width": width,
            "height": height,
            "path": path,
            "oritention": rotate
        }];

        return ImagesArr;
    }

    /**
     * 返回打印方式的坐标
     * @param {String} str 
     * @param {Number} ptype 
     * @param {Number} xy 
     * @param {Number} fontHeight 
     * @param {Number} printWidth 
     * @param {String} operator 
     * @return {Number}  
     */
    WewinPrintService.prototype.returnPtype = function (str, ptype, xy, fontHeight, printWidth, operator) {
        var newXY = xy;
        var strLen = this.getLen(str, fontHeight);
        if (ptype == 0) {
            //靠左打印
            newXY = xy;
        } else if (ptype == 1) {
            //居中打印
            if (operator == "+") {
                newXY += printWidth / 2 - strLen / 2;
            } else if (operator == "-") {
                newXY -= printWidth / 2 - strLen / 2;
            }
        } else if (ptype == 2) {
            //靠右打印
            if (operator == "+") {
                newXY += printWidth - strLen;
            } else if (operator == "-") {
                newXY -= printWidth - strLen;
            }
        }
        return newXY;
    }

    /**
     * 分割字符串
     * @param {Number} fontHeight - 字体高度
     * @param {String} str - 字符串
     * @param {Number} printWidth - 换行宽度
     * @return {Array} backstr
     */
    WewinPrintService.prototype.autoSplit = function (fontHeight, str, printWidth) {
        var strLen = 0;
        var temp = 0;
        var backstr = [];
        var strs = str.split("");
        var tLen = 0;
        var letter = [];
        for (var i = 0; i < strs.length; i++) {
            // if (this.isChinese(strs[i])) {
            //     tLen = fontHeight / 2;
            //     letter.push(strs[i]);
            // } else {
            //     tLen = this.getLen(strs[i], fontHeight);
            // }
            tLen = this.getLen(strs[i], fontHeight);
            strLen += tLen;
            if ((strLen - printWidth) > 0) {
                // if (letter.length != 0 && letter.length % 2 == 0) {
                //     i -= 1;
                // }
                backstr.push(str.substring(temp, i));
                temp = i;
                strLen = 0;
                i--;
                tArr = [];
                letter = [];
            }
            if (i == str.length - 1) {
                backstr.push(str.substring(temp, i + 1));
            }
        }
        return backstr;
    }

    WewinPrintService.prototype.isChinese = function (temp) {
        var re = /^[\u4E00-\u9FA5]/;
        if (re.test(temp)) return false;
        return true;
    }

    /**
     * 解析xml节点对象
     * @param {Object} ele xml解析的节点对象 
     */
    WewinPrintService.prototype.parseXmlElement = function (ele) {
        var eles = [];
        if (ele[0] != undefined && ele[0].childNodes[0] != undefined) {
            for (var j = 0; j < ele.length; j++) {
                if (ele[j].firstChild != null) {
                    eles[j] = ele[j].firstChild.nodeValue;
                } else {
                    eles[j] = "";
                }
            }
        }
        return eles;
    }

    return new WewinPrintService();
}

//兼容IE8一下console报错问题
window._console = window.console;//将原始console对象缓存
window.console = (function (orgConsole) {
    return {//构造的新console对象
        log: getConsoleFn("log"),
        debug: getConsoleFn("debug"),
        info: getConsoleFn("info"),
        warn: getConsoleFn("warn"),
        exception: getConsoleFn("exception"),
        assert: getConsoleFn("assert"),
        dir: getConsoleFn("dir"),
        dirxml: getConsoleFn("dirxml"),
        trace: getConsoleFn("trace"),
        group: getConsoleFn("group"),
        groupCollapsed: getConsoleFn("groupCollapsed"),
        groupEnd: getConsoleFn("groupEnd"),
        profile: getConsoleFn("profile"),
        profileEnd: getConsoleFn("profileEnd"),
        count: getConsoleFn("count"),
        clear: getConsoleFn("clear"),
        time: getConsoleFn("time"),
        timeEnd: getConsoleFn("timeEnd"),
        timeStamp: getConsoleFn("timeStamp"),
        table: getConsoleFn("table"),
        error: getConsoleFn("error"),
        memory: getConsoleFn("memory"),
        markTimeline: getConsoleFn("markTimeline"),
        timeline: getConsoleFn("timeline"),
        timelineEnd: getConsoleFn("timelineEnd")
    };
    function getConsoleFn(name) {
        return function actionConsole() {
            if (typeof (orgConsole) !== "object") return;
            if (typeof (orgConsole[name]) !== "function") return;//判断原始console对象中是否含有此方法，若没有则直接返回
            return orgConsole[name].apply(orgConsole, Array.prototype.slice.call(arguments));//调用原始函数
        };
    }
}(window._console));