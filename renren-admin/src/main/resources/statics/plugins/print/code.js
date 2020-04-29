//初始化WewinPrintService API
var wps = WewinPrintService();

//test.html页面传递json
function LabelPrint(data) {
    wps.LabelPrint(data);
    Load();
}

//加载预览页面
function Load() {
    wps.Load();
    wps.ViewPrint(ViewPrint);
}

//标签尺寸下拉框变化时，刷新预览界面
function changetype() {
    ViewPrint(wps.data, 1);
}

//在预览中点击打印按钮时打印标签
function Print() {
    wps.GetPrinter();
    wps.DoLabelPrint(DoLabelPrint);
}

//查看报文
function lookXml() {
    wps.lookXml();
}

//查看帮助
function lookHelp() {
    wps.lookHelp();
}

//解析XML
function loadXML(xmlString) {
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


function closeWindows() {
    $(".wewinview").remove();
}

//----------------------------------------------------
//修改-标签预览
function ViewPrint(data, temp) {

    var text = "", xmldoc, PrintElements;
    var dataStr = data;

    if (typeof (dataStr) == "string") {
        xmldoc = loadXML(data);
        PrintElements = xmldoc.getElementsByTagName("Print");
        data = PrintElements;
    }

    for (var i = 0; i < data.length; i++) {

        //预览页面标题(修改为：重庆品胜 - 新疆移动 - 打印预览)
        document.getElementById("btitle").innerHTML = "重庆品胜 - 资管打印 - 打印预览";

        //修改版本号
        document.getElementById("versionnum").innerHTML = "TY_123";


        if (typeof (dataStr) == "string") {
            //标签类型
            var LabelTypeElement = data[i].getElementsByTagName("EntityTypeId");
            var labelType = wps.parseXmlElement(LabelTypeElement);
            //Text节点
            var TextElement = data[i].getElementsByTagName("Text");
            var Texts = wps.parseXmlElement(TextElement);
        } else if (typeof (dataStr) == "object") {
            //标签类型
            var labelType = wps.parseJsonElement(data[i].EntityTypeId);
            //Text节点
            var Texts = wps.parseJsonElement(data[i].Text);
        }

        // 标签1(123)
        if (labelType[0] == "123") {

            //标签型号
            var labelname = document.getElementById("labelname");
            if (temp != 1) {
                labelname.innerHTML = "";
                labelname.options.add(new Option("25-25", "0"));
            }
            var selValue = labelname.value;

            if(selValue == 0){
                //支持的打印机型号
                document.getElementById("printtype").innerHTML = "P50";

                if (i == 0 && data.length > 1) {
                    text += "<div style=\"width:60px;height:17px;overflow:hidden;margin: 0 auto;margin-bottom:10px\"><div style=\"height:17px;float:left;line-height:17px;font-size:15px;\">全选</div><input onclick=\"wps.chooseAllCheckbox()\" id=\"allcb\" type=\"checkbox\" style=\"width:17px;height:17px;margin:0;padding:0;float:right\"><\/div>";
                }

                if(i == 0){
                    text += "<div style=\"position: relative;font-family:'" + wps.fontname + "';background-image:url(\'labelimage\/(25-25)(0).png\');background-repeat:no-repeat;width: 200px;height: 200px;display:block;margin:0 auto;padding:0;\">";
                } else {
                    text += "<div style=\"position: relative;font-family:'" + wps.fontname + "';background-image:url(\'labelimage\/(25-25)(0).png\');background-repeat:no-repeat;width: 200px;height: 200px;display:block;margin:0 auto;padding:0;margin-top: 15px\">";
                }

                if (data.length > 1) {
                    if (i == 0) {
                        text += "<input id=\"cb" + i + "\" type=\"checkbox\" checked=\"checked\" style=\"position: absolute;top:10px;left:-30px;width:17px;height:17px;\">";
                    } else {
                        text += "<input id=\"cb" + i + "\" type=\"checkbox\" style=\"position: absolute;top:10px;left:-30px;width:17px;height:17px;\">";
                    }
                }

                //-----------------------文本预览----------------------------
                var fontHeight = 16;//字体大小
                var printWidth = 160;//换行宽度
                var x = 19;//x坐标
                var y = 31;//y坐标
                var maxH = 25;//最大高度
                var xoffset = 4;//Text内部行间距
                var loffset = 2;//Text外部行间距
                var height = 39.05279999999999;//不用修改
                var width = 160;//不用修改

                //计算预览自适应
                var fh = wps.AutoPrintTextView({
                    str: [Texts[0]],
                    fontHeight: fontHeight,
                    printWidth: printWidth,
                    y: y,
                    xoffset: xoffset,
                    loffset: loffset,
                    maxH: maxH
                });
                var lh = parseInt(fh) + 4;

                text += "	<div style=\"";
                text += "		position: absolute;";
                text += "		top: " + y + "px;";
                text += "		left: " + x + "px;";
                // text += "		width: " + width + "px;";
                text += "		text-align: left;";
                text += "		line-height: " + lh + "px;";
                text += "		font-size: " + fh + "px;";
                text += "	\">"

                text += "       <div>";
                text += wps.PrintTextView({ str: Texts[0], fontHeight: fh, printWidth: printWidth }, 0);
                text += "       <\/div>";

                text += "   <\/div>";
                //---------------------------------------------------------

                text += "<\/div>";
            }
        }

    }
    var preview = document.getElementById("preview");
    preview.innerHTML = text;

}

//修改-标签打印
function DoLabelPrint(data) {
    var lablesArr = [];

    var xmldoc, PrintElements;
    var dataStr = data;

    if (typeof (dataStr) == "string") {
        xmldoc = loadXML(data);
        PrintElements = xmldoc.getElementsByTagName("Print");
        data = PrintElements;
    }

    for (var i = 0; i < data.length; i++) {

        if (typeof (dataStr) == "string") {
            //标签类型
            var LabelTypeElement = data[i].getElementsByTagName("EntityTypeId");
            var labelType = wps.parseXmlElement(LabelTypeElement);
            //Text节点
            var TextElement = data[i].getElementsByTagName("Text");
            var Texts = wps.parseXmlElement(TextElement);
        } else if (typeof (dataStr) == "object") {
            //标签类型
            var labelType = wps.parseJsonElement(data[i].EntityTypeId);
            //Text节点
            var Texts = wps.parseJsonElement(data[i].Text);
        }

        // 标签1(123)
        if (labelType[0] == '123') {
            if(data.length == 1){
                lablesArr = print_tag123(lablesArr, Texts);
            } else {
                if (document.getElementById("cb" + i).checked) {
                    lablesArr = print_tag123(lablesArr, Texts);
                }
            }
        }
    }

    var data = {
        "handleType": "1",
        "printer": "",
        "hasDrive": "",
        "copyNum": "1",
        "labels": lablesArr
    }

    wps.Print(data);

}


// 标签1(123)
function print_tag123(lablesArr, Texts) {
    var labelWidth = 0;//标签宽
    var labelHeight = 0;//标签高
    var printTexts = [];
    var resultArr = [];
    var rfid = "";

    //获取标签下拉列表
    var labelname = document.getElementById("labelname");
    var selValue = labelname.value;

    //获取打印机分辨率(8 | 12)
    var printername = wps.printername;
    var parr = wps.printername.split("&&");
    var dots = parr[1];

    if(selValue == 0) {

        labelWidth = 70 * dots;
        labelHeight = 30 * dots;

        //---------------------------------------------------------
        //文字打印
        var x = 163.66666666666666;
        var y = 23;
        var fontHeight = 16;
        var fontWeight = 400;
        var printWidth = 160;
        var rotate = 3;
        var xoffset = 2;
        var loffset = 1;
        var maxH = 25;
        var ptype = 0;

        printTexts = Texts.slice(0, 1);

        var TextsArr = wps.PrintText({
            str: printTexts,
            fontHeight: fontHeight,
            fontWeight: fontWeight,
            printWidth: printWidth,
            x: x,
            y: y,
            rotate: rotate,
            xoffset: xoffset,
            loffset: loffset,
            maxH: maxH,
            ptype: ptype
        });
        resultArr = wps.addArr(resultArr, TextsArr);
        //---------------------------------------------------------

        //RFID写入
        var rfidType = "EPC";//rfid存储区域(EPC、USER)
        var rfidWay = "2";//rfid存储方式(1:单字符 2:双字符)
        var rfidData = "061933957964658777086600";//rfid内容
        rfid = wps.rfidParse({
            rfidType: rfidType,
            rfidWay: rfidWay,
            rfidData: rfidData
        });

    }

    var obj = {
        "labelWidth": labelWidth,
        "labelHeight": labelHeight,
        "rfid": rfid,
        "ddfLength": "0",
        "cutOption": "0",
        "blocks": resultArr
    }
    lablesArr.push(obj);

    return lablesArr;
}
                            