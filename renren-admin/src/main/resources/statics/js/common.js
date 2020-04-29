//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

var baseURL = "../../";

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
};
T.p = url;

function getRealPath() {

    //获取当前网址，如： http://localhost:8083/myproj/view/my.jsp

    var curWwwPath = window.document.location.href;

    //获取主机地址之后的目录，如： myproj/view/my.jsp

    var pathName = window.document.location.pathname;

    var pos = curWwwPath.indexOf(pathName);

    //获取主机地址，如： http://localhost:8083

    var localhostPaht = curWwwPath.substring(0, pos);

    //获取带"/"的项目名，如：/myproj

    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

    //得到了 http://localhost:8083/myproj

    var realPath = localhostPaht + projectName;

    return realPath;

}

//全局配置
$.ajaxSetup({
    dataType: "json",
    cache: false
});

//重写alert
window.alert = function (msg, callback) {
    parent.layer.alert(msg, function (index) {
        parent.layer.close(index);
        if (typeof (callback) === "function") {
            callback("ok");
        }
    });
}

//重写confirm式样框
window.confirm = function (msg, callback) {
    parent.layer.confirm(msg, {btn: ['确定', '取消']},
        function (index, layero) {//确定事件
            parent.layer.close(index);
            if (typeof (callback) === "function") {
                callback("ok");
            }
        });
}

//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        alert("只能选择一条记录");
        return;
    }

    return selectedIDs[0];
}


/**
 *  选择行 主键值
 * @param id 选择器 id
 * @returns {*}
 */
function getSelectedRowById(id) {
    var grid = $("#" + id);
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        alert("只能选择一条记录");
        return;
    }

    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    return grid.getGridParam("selarrrow");
}

/**
 *
 */
function getSelectedRowsData() {
    var ids = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
    var selectRow = [];
    for (var i = 0; i < ids.length; i++) {
        selectRow[i] = ($("#jqGrid").jqGrid('getRowData', ids[i]));
    }
    return selectRow;
}

/**
 * 获取选择的多行数据
 * @param id 选择元素的ID
 * @returns {Array}
 */
function getSelectedRowsData1(id) {
    var ids = $("#" + id + "").jqGrid('getGridParam', 'selarrrow');
    var selectRow = [];
    for (var i = 0; i < ids.length; i++) {
        selectRow[i] = ($("#" + id + "").jqGrid('getRowData', ids[i]));
    }
    return selectRow;
}

//判断是否为空
function isBlank(value) {
    return !value || !/\S/.test(value)
}

/*日期格式正则表达式*/
var DateReg = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/;

/*字符串转dom对象*/
function loadXMLString(txt) {
    try //Internet Explorer
    {
        xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
        xmlDoc.async = "false";
        xmlDoc.loadXML(txt);
        //alert('IE');
        return (xmlDoc);
    } catch (e) {
        try //Firefox, Mozilla, Opera, etc.
        {
            parser = new DOMParser();
            xmlDoc = parser.parseFromString(txt, "text/xml");
            //alert('FMO');
            return (xmlDoc);
        } catch (e) {
            alert(e.message)
        }
    }
    return (null);
}

/**
 * 一维数组去重
 * @param array
 * @returns {Array}
 */
function uniq(array) {
    var temp = []; //一个新的临时数组
    for (var i = 0; i < array.length; i++) {
        if (temp.indexOf(array[i]) == -1) {
            temp.push(array[i]);
        }
    }
    return temp;
}

/**
 * 二维数组去重
 * @param arr
 * @returns {any[]}
 */
function duplicate(arr) {
    // let res = {}
    // arr.forEach(item => {
    //     item.sort((a, b) => a - b);
    //     res[item] = item;
    // });
    // return Object.values(res)
    var i = 0,
        j = arr.length,
        cache = {},
        key, result = [];
    for (; i < j; i++) {
        key = arr[i][0];
        key = typeof (key) + key;
        if (!cache[key]) {
            cache[key] = 1;
            result.push(arr[i]);
        }
    }
    return result;
}

/**
 * 字符串子串添加分隔符
 * @param source 原始字符串
 * @param count 子串长度
 * @param flag 分割标志符
 * @returns {newStr}
 */
function strJoinSeparator(source, count, flag) {
    if (source === null) {
        return "";
    }
    // console.log("source------>" + source);
    var len = source.length;
    // console.log("len------->" + len);
    var start = 0;
    // console.log("len / count-------->" + len / count);
    var j = len % count;
    var k = len / count;
    if (j > 0) {
        k = k + 1;
    }
    // console.log("j---->" + j);
    // console.log("k--------->" + k);
    var newStr = "";
    if (len > count) {
        for (var i = 0; i < k; i++) {
            var str = "";
            // console.log("start---------->" + start);
            if (start > len || count > len) {
                str = source.substr(start, len - 1);
            } else {
                str = source.substr(start, count);
            }
            // console.log("str------------>" + str);
            start = start + count;
            newStr = newStr + str + flag;
        }
        return newStr;
    } else {
        return source;
    }

}

/**
 * iframe 弹出层
 * @param title
 * @param url
 */
function iframe(title, url) {
    layer.open({
        type: 2,
        title: title,
        maxmin: true,
        shadeClose: true,
        shade: 0.5,
        area: ['98vw', '98vh'],
        content: url
    });

}

/**
 * 页面层
 * @param conent 显示内容
 */
function pageLayer(title, conent) {
    layer.open({
        title: title,
        type: 1,
        // skin: 'layui-layer-rim', //加上边框
        area: ['50vw', '50vh'], //宽高
        content: conent
    });
}

/**
 * 图片放大遮罩
 * @param url
 */
function showPhoto(url) {
    var img = "<!DOCTYPE html><html><body class=\"gray-bg\">";
    img += "<img src=\"../../" + url + "\" style='width: 300px;300px;'>";
    img += "</body></html>";
    layer.open({
        type: 3,//Page层类型
        area: ['400px', '400px'],
        shade: 0.2,//遮罩透明度
        shadeClose: true, // 点击遮罩关闭层
        maxmin: true,//允许全屏最小化
        anim: 0,//0-6的动画形式，-1不开启
        content: img
    });
}

/**
 *  获取当前日期前后的日期
 * @param aa
 * @returns {string}
 */
function fun_date(aa) {
    var date1 = new Date(),
        time1 = date1.getFullYear() + "-" + (date1.getMonth() + 1) + "-" + date1.getDate();//time1表示当前时间
    var date2 = new Date(date1);
    date2.setDate(date1.getDate() + aa);
    var time2 = date2.getFullYear() + "-" + (date2.getMonth() + 1) + "-" + date2.getDate();
    return time2;
}

/**
 * 获取当前日期
 * @returns {string}
 * @constructor
 */
function nowDate() {
    var date1 = new Date(),
        time1 = date1.getFullYear() + "-" + (date1.getMonth() + 1) + "-" + date1.getDate();//time1表示当前时间
    return time1;
}
