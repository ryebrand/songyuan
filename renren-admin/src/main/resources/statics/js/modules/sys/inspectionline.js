var map;
map = new AMap.Map('container', {
    zoom: 14,//级别
    center: [106.549795,29.55814],//中心点坐标
    viewMode: '3D',//使用3D视图
    resizeEnable: true,
    rotateEnable: true,
    pitchEnable: true,
    rotation: -15,
    pitch: 0,
    buildingAnimation: true,//楼块出现是否带动画
});


// 同时引入工具条插件，比例尺插件和鹰眼插件
AMap.plugin([
    'AMap.ToolBar',
    'AMap.Scale',
    'AMap.OverView',
    'AMap.MapType',
    'AMap.Geolocation',
    'AMap.ControlBar',
], function () {
    // 在图面添加工具条控件，工具条控件集成了缩放、平移、定位等功能按钮在内的组合控件
    map.addControl(new AMap.ToolBar());

    // 在图面添加比例尺控件，展示地图在当前层级和纬度下的比例尺
    map.addControl(new AMap.Scale());

    // 在图面添加鹰眼控件，在地图右下角显示地图的缩略图
    map.addControl(new AMap.OverView({isOpen: true}));

    // 在图面添加类别切换控件，实现默认图层与卫星图、实施交通图层之间切换的控制
    map.addControl(new AMap.MapType());

    // 在图面添加定位控件，用来获取和展示用户主机所在的经纬度位置
    map.addControl(new AMap.Geolocation());
    // 3D切换
    map.addControl(new AMap.ControlBar({
        showZoomBar: false,
        showControlButton: true,
        position: {
            right: '10px',
            top: '100px'
        }
    }))
});
var markers = [];

// 清除所有标记点
map.remove(markers);


AMapUI.loadUI(['overlay/AwesomeMarker', 'overlay/SimpleMarker'],
    function (AwesomeMarker, SimpleMarker) {

        //icon的取值请见  http://fontawesome.io/icons/
        var awIcons = [
            'flag-checkered',
            'building',
        ];

        var iconStyles = SimpleMarker.getBuiltInIconStyles();


        // $.get("sys/watchbox/allGpsAndAddress/" + areaname, function (r) {
        //     $.get("sys/watchbox/allGpsAndAddress" , function (r) {
        //     // console.log(r);
        //     if (r.code === 0) {
        //         // if(r.gpsAndaddress.length===0){
        //         //     layer.msg('当前搜索条件无结果，请重输入！');
        //         //     return;
        //         // }
        //         // console.log(r.gpsAndaddress);
        //         // console.log(r.gpsAndaddress);
        //         $.each(r.gpsAndaddress, function (index, domEle) {
        //             var dot=null;
        //             if (domEle.gps===null){
        //                 // 表箱
        //                 dot = domEle.watchboxgps.split(",");
        //                 var marker = new AwesomeMarker({
        //
        //                     //设置awesomeIcon
        //                     awesomeIcon: awIcons[0],
        //
        //                     iconLabel: {
        //                         style: {
        //                             color: iconStyles[iconStyles.length - 1],
        //                             fontSize: '14px'
        //                         }
        //                     },
        //
        //                     //图标
        //                     iconStyle: iconStyles[1],
        //
        //                     map: map,
        //                     position: [dot[0], dot[1]],
        //                     title: domEle.address
        //
        //                 });
        //                 markers.push(marker);
        //
        //
        //                 var title = '坐标信息';
        //                 var info = [];
        //                 //构建信息窗体中显示的内容
        //                 var url = "modules/sys/electricitymeter.html?watchboxid=" + domEle.watchboxid;
        //                 info.push("<p class='input-item'><b>台区名称 :</b>" + domEle.wtName + " </p>");
        //                 info.push("<p class='input-item'><b>地址 :</b>" + domEle.watchboxaddress + "</p></div></div>");
        //                 info.push("<p class='input-item'><b>台区经理：</b>" + domEle.manager + "</p></div></div>");
        //                 info.push("<p class='input-item'><b>经理电话：</b>" + domEle.mobile + "</p></div></div>");
        //                 info.push("<p class='input-item'><a onclick='iframe(\"坐标详情\",\"" + url + "\")' >查看详情</a></p></div></div>");
        //
        //                 var dot = new AMap.InfoWindow({ //创建信息窗体
        //                     // anchor: "bottom-center",
        //                     isCustom: true,  //使用自定义窗体
        //                     content: createInfoWindow(title, info.join("")), //使用默认信息窗体框样式，显示信息内容
        //                     offset: new AMap.Pixel(16, -45),
        //                 });
        //                 var onMarkerClick = function (e) {
        //                     dot.open(map, e.target.getPosition());//打开信息窗体
        //                 }
        //                 map.add(marker);
        //                 marker.on('click', onMarkerClick);//绑定click事件
        //                 // // 设置点标记的动画效果，此处为弹跳效果
        //                 marker.setAnimation('AMAP_ANIMATION_DROP');
        //
        //             }else {
        //                 // 电杆
        //                 dot = domEle.gps.split(",");
        //                 var marker = new AwesomeMarker({
        //
        //                     //设置awesomeIcon
        //                     awesomeIcon: awIcons[1],
        //
        //                     iconLabel: {
        //                         style: {
        //                             color: iconStyles[iconStyles.length - 1],
        //                             fontSize: '14px'
        //                         }
        //                     },
        //
        //                     //图标
        //                     iconStyle: iconStyles[2],
        //
        //                     map: map,
        //                     position: [dot[0], dot[1]],
        //                     title: domEle.address
        //                 });
        //                 markers.push(marker);
        //
        //                 var title = '坐标信息';
        //                 var info = [];
        //                 //构建信息窗体中显示的内容
        //                 var url = "modules/sys/watchbox.html?poleid=" + domEle.id;
        //                 info.push("<p class='input-item'><b>台区名称 :</b>" + domEle.name + " </p>");
        //                 info.push("<p class='input-item'><b>地址 :</b>" + domEle.address + "</p></div></div>");
        //                 info.push("<p class='input-item'><b>台区经理：</b>" + domEle.manager + "</p></div></div>");
        //                 info.push("<p class='input-item'><b>经理电话：</b>" + domEle.mobile + "</p></div></div>");
        //                 info.push("<p class='input-item'><a onclick='iframe(\"坐标详情\",\"" + url + "\")' >查看详情</a></p></div></div>");
        //
        //                 var dot = new AMap.InfoWindow({ //创建信息窗体
        //                     // anchor: "bottom-center",
        //                     isCustom: true,  //使用自定义窗体
        //                     content: createInfoWindow(title, info.join("")), //使用默认信息窗体框样式，显示信息内容
        //                     offset: new AMap.Pixel(16, -45),
        //                 });
        //                 var onMarkerClick = function (e) {
        //                     dot.open(map, e.target.getPosition());//打开信息窗体
        //                 }
        //                 map.add(marker);
        //                 marker.on('click', onMarkerClick);//绑定click事件
        //                 // // 设置点标记的动画效果，此处为弹跳效果
        //                 marker.setAnimation('AMAP_ANIMATION_DROP');
        //
        //             }
        //
        //
        //             // if (domEle.type === 0) {
        //             //     var marker = new AwesomeMarker({
        //             //
        //             //         //设置awesomeIcon
        //             //         awesomeIcon: awIcons[0],
        //             //
        //             //         iconLabel: {
        //             //             style: {
        //             //                 color: iconStyles[iconStyles.length - 1],
        //             //                 fontSize: '14px'
        //             //             }
        //             //         },
        //             //
        //             //         //图标
        //             //         iconStyle: iconStyles[1],
        //             //
        //             //         map: map,
        //             //         position: [dot[0], dot[1]],
        //             //         title: domEle.address
        //             //
        //             //     });
        //             // } else if (domEle.type === 1) {
        //             //     var marker = new AwesomeMarker({
        //             //
        //             //         //设置awesomeIcon
        //             //         awesomeIcon: awIcons[1],
        //             //
        //             //         iconLabel: {
        //             //             style: {
        //             //                 color: iconStyles[iconStyles.length - 1],
        //             //                 fontSize: '14px'
        //             //             }
        //             //         },
        //             //
        //             //         //图标
        //             //         iconStyle: iconStyles[2],
        //             //
        //             //         map: map,
        //             //         position: [dot[0], dot[1]],
        //             //         title: domEle.address
        //             //     });
        //             // }
        //
        //             // markers.push(marker);
        //
        //             // var marker = new AMap.Marker({
        //             //     position: [dot[0], dot[1]]
        //             // })
        //
        //             // var title = '坐标信息';
        //             // var info = [];
        //             // //构建信息窗体中显示的内容
        //             // // var url = "modules/sys/watchbox.html?poleid=" + domEle.id;
        //             // info.push("<p class='input-item'><b>台区名称 :</b>" + domEle.name + " </p>");
        //             // info.push("<p class='input-item'><b>地址 :</b>" + domEle.address + "</p></div></div>");
        //             // info.push("<p class='input-item'><b>台区经理：</b>" + domEle.manager + "</p></div></div>");
        //             // info.push("<p class='input-item'><b>经理电话：</b>" + domEle.mobile + "</p></div></div>");
        //             // info.push("<p class='input-item'><a onclick='iframe(\"坐标详情\",\"" + url + "\")' >查看详情</a></p></div></div>");
        //             //
        //             // var dot = new AMap.InfoWindow({ //创建信息窗体
        //             //     // anchor: "bottom-center",
        //             //     isCustom: true,  //使用自定义窗体
        //             //     content: createInfoWindow(title, info.join("")), //使用默认信息窗体框样式，显示信息内容
        //             //     offset: new AMap.Pixel(16, -45),
        //             // });
        //             // var onMarkerClick = function (e) {
        //             //     dot.open(map, e.target.getPosition());//打开信息窗体
        //             // }
        //             // map.add(marker);
        //             // marker.on('click', onMarkerClick);//绑定click事件
        //             // // // 设置点标记的动画效果，此处为弹跳效果
        //             // marker.setAnimation('AMAP_ANIMATION_DROP');
        //         });
        //         map.setFitView();
        //     }
        // });

        var pathInfo = {};

        // $.get("sys/watchbox/allGpsAndAddress" , function (r) {
        //     console.log(r);
        //     if (r.code === 0) {
        //         $.each(r.wgps, function (index, domEle) {
        //             var dot=null;
        //             // 电杆
        //             dot = domEle.watchboxgps.split(",");
        //             var marker = new AwesomeMarker({
        //
        //                 //设置awesomeIcon
        //                 awesomeIcon: awIcons[1],
        //
        //                 iconLabel: {
        //                     style: {
        //                         color: iconStyles[iconStyles.length - 1],
        //                         fontSize: '14px'
        //                     }
        //                 },
        //
        //                 //图标
        //                 iconStyle: iconStyles[2],
        //
        //                 map: map,
        //                 position: [dot[0], dot[1]],
        //                 title: domEle.address
        //             });
        //             markers.push(marker);
        //
        //             var title = '坐标信息';
        //             var info = [];
        //             //构建信息窗体中显示的内容
        //             var url = "modules/sys/electricitymeter.html?watchboxid=" + domEle.watchboxid;
        //                             info.push("<p class='input-item'><b>台区名称 :</b>" + domEle.wtName + " </p>");
        //                             info.push("<p class='input-item'><b>地址 :</b>" + domEle.address + "</p></div></div>");
        //                             info.push("<p class='input-item'><b>台区经理：</b>" + domEle.manager + "</p></div></div>");
        //                             info.push("<p class='input-item'><b>经理电话：</b>" + domEle.mobile + "</p></div></div>");
        //                             info.push("<p class='input-item'><a onclick='iframe(\"坐标详情\",\"" + url + "\")' >查看详情</a></p></div></div>");
        //
        //             var dot = new AMap.InfoWindow({ //创建信息窗体
        //                 // anchor: "bottom-center",
        //                 isCustom: true,  //使用自定义窗体
        //                 content: createInfoWindow(title, info.join("")), //使用默认信息窗体框样式，显示信息内容
        //                 offset: new AMap.Pixel(16, -45),
        //             });
        //             var onMarkerClick = function (e) {
        //                 dot.open(map, e.target.getPosition());//打开信息窗体
        //             }
        //             map.add(marker);
        //             marker.on('click', onMarkerClick);//绑定click事件
        //             // // 设置点标记的动画效果，此处为弹跳效果
        //             marker.setAnimation('AMAP_ANIMATION_DROP');
        //         });
        //     }
        // });

        // $.get("sys/watchbox/polegps" , function (r) {
        //     // console.log(r);
        //
        //     if (r.code === 0) {
        //         $.each(r.pgps, function (index, domEle) {
        //             var dot=null;
        //                 // 电杆
        //                 dot = domEle.gps.split(",");
        //                 var marker = new AwesomeMarker({
        //
        //                     //设置awesomeIcon
        //                     awesomeIcon: awIcons[1],
        //
        //                     iconLabel: {
        //                         style: {
        //                             color: iconStyles[iconStyles.length - 1],
        //                             fontSize: '14px'
        //                         }
        //                     },
        //
        //                     //图标
        //                     iconStyle: iconStyles[1],
        //
        //                     map: map,
        //                     position: [dot[0], dot[1]],
        //                     title: domEle.address
        //                 });
        //                 markers.push(marker);
        //
        //                 var title = '坐标信息';
        //                 var info = [];
        //                 //构建信息窗体中显示的内容
        //                 var url = "modules/sys/watchbox.html?poleid=" + domEle.id;
        //                 info.push("<p class='input-item'><b>台区名称 :</b>" + domEle.ptName + " </p>");
        //                 info.push("<p class='input-item'><b>地址 :</b>" + domEle.address + "</p></div></div>");
        //                 info.push("<p class='input-item'><b>台区经理：</b>" + domEle.manager + "</p></div></div>");
        //                 info.push("<p class='input-item'><b>经理电话：</b>" + domEle.mobile + "</p></div></div>");
        //                 info.push("<p class='input-item'><a onclick='iframe(\"坐标详情\",\"" + url + "\")' >查看详情</a></p></div></div>");
        //
        //                 var dot = new AMap.InfoWindow({ //创建信息窗体
        //                     // anchor: "bottom-center",
        //                     isCustom: true,  //使用自定义窗体
        //                     content: createInfoWindow(title, info.join("")), //使用默认信息窗体框样式，显示信息内容
        //                     offset: new AMap.Pixel(16, -45),
        //                 });
        //                 var onMarkerClick = function (e) {
        //                     dot.open(map, e.target.getPosition());//打开信息窗体
        //                 }
        //                 map.add(marker);
        //                 marker.on('click', onMarkerClick);//绑定click事件
        //                 // // 设置点标记的动画效果，此处为弹跳效果
        //                 marker.setAnimation('AMAP_ANIMATION_DROP');
        //         });
        //     }
        // });
        // map.setFitView();


        // var dotMap = {
        //     0: [
        //         [75.96268330202292, 39.4646601642717],
        //         [75.962532, 39.464071],
        //         [75.962532, 39.463806]
        //     ],
        //     1: [
        //         [75.96382726414821, 39.46456284081965],
        //         [75.963658, 39.464059],
        //         [75.963588, 39.463181]
        //     ],
        //     2: [
        //         [75.970318, 39.460317],
        //         [75.971104, 39.460688],
        //         [75.971356, 39.460464],
        //         [75.971705, 39.459905]
        //     ]
        // };
        // // 支线模拟
        // $("#branchline").click(function () {
        //     var polylines = [];
        //     $.each(dotMap, function (index, domEle) {
        //         console.log(domEle)
        //         var dot = [];
        //         $.each(domEle, function (index, domEle) {
        //             console.log(domEle);
        //             // var gpsdot = domEle.split(",");
        //             dot.push([domEle[0], domEle[1]]);
        //             if (index === 0) {
        //                 return true;
        //             } else {
        //                 var marker = new AMap.Marker({
        //                     position: [domEle[0], domEle[1]],
        //                     icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
        //                 });
        //                 // 将 markers 添加到地图
        //                 map.add(marker);
        //             }
        //         })
        //         console.log(dot);
        //         polylines.push(drawRoute(dot));
        //         ;
        //         // drawRoute(dot);
        //     })
        //     var overlayGroups = new AMap.OverlayGroup(polylines);
        //     map.add(overlayGroups);
        //
        //
        // });

        var dt = {
            0: [{
                "address": "重庆市渝北区光电园",
                "gps": "106.504854,29.615536",
                "id": 46,
                "manager": "李华",
                "mobile": "13222222222",
                "name": "明文线1024线路001号公变",
                "no": "001",
                "type": 1
            }, {
                "address": "重庆市渝北区光电园",
                "gps": "106.504854,29.616412",
                "id": 45,
                "manager": "李华",
                "mobile": "13222222222",
                "name": "明文线1024线路001号公变",
                "no": "002",
                "type": 1
            }, {
                "address": "重庆市渝北区光电园",
                "gps": "106.504725,29.616585",
                "id": 44,
                "manager": "李华",
                "mobile": "13666666666",
                "name": "明文线1024线路001号公变",
                "no": "003",
                "type": 1
            }, {
                "address": "重庆市渝北区光电园",
                "gps": "106.503792,29.616389",
                "id": 41,
                "manager": "李华",
                "mobile": "13666666666",
                "name": "明文线1024线路001号公变",
                "no": "004",
                "type": 1
            }, {
                "address": "重庆市渝北区光电园",
                "gps": "106.503121,29.6163",
                "id": 40,
                "manager": "李华",
                "mobile": "13666666666",
                "name": "明文线1024线路001号公变",
                "no": "005",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园",
                "gps": "106.50266,29.616254",
                "id": 38,
                "manager": "李华",
                "mobile": "13666666666",
                "name": "明文线1024线路001号公变",
                "no": "006",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园",
                "gps": "106.501303,29.616189",
                "id": 39,
                "manager": "李华",
                "mobile": "13666666666",
                "name": "明文线1024线路001号公变",
                "no": "007",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园",
                "gps": "106.49987,29.616049",
                "id": 39,
                "manager": "李华",
                "mobile": "13666666666",
                "name": "明文线1024线路001号公变",
                "no": "008",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园西环路13",
                "gps": "106.499452,29.616035",
                "id": 41,
                "manager": "李华",
                "mobile": "13666666666",
                "name": "明文线1024线路001号公变",
                "no": "009",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园西环路",
                "gps": "106.499425,29.615288",
                "id": 42,
                "manager": "李华",
                "mobile": "13666666666",
                "name": "明文线1024线路001号公变",
                "no": "010",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园西环路28鑫福华庭",
                "gps": "106.499677,29.615004",
                "id": 43,
                "manager": "李华",
                "mobile": "13666666666",
                "name": "明文线1024线路001号公变",
                "no": "011",
                "type": 0
            }],
            1: [{
                "address": "重庆市渝北区光电园",
                "gps": "106.499378,29.612573",
                "id": 58,
                "manager": "李华",
                "mobile": "13222222222",
                "name": "明托线1020线路001号公变",
                "no": "001",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园",
                "gps": "106.500279,29.612648",
                "id": 56,
                "manager": "李华",
                "mobile": "13222222222",
                "name": "明托线1020线路001号公变",
                "no": "002",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园",
                "gps": "106.501045,29.612791",
                "id": 57,
                "manager": "李华",
                "mobile": "13222222222",
                "name": "明托线1020线路001号公变",
                "no": "003",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园(西域大道)",
                "gps": "106.50156,29.612763",
                "id": 55,
                "manager": "李华",
                "mobile": "13222222222",
                "name": "明托线1020线路001号公变",
                "no": "004",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园(西域大道)",
                "gps": "106.502107,29.613225",
                "id": 54,
                "manager": "李华",
                "mobile": "13222222222",
                "name": "明托线1020线路001号公变",
                "no": "005",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园-附24号边疆宾馆(西域大道)",
                "gps": "106.501624,29.613309",
                "id": 53,
                "manager": "李华",
                "mobile": "13222222222",
                "name": "明托线1020线路001号公变",
                "no": "006",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园西域大道155-附24号边疆宾馆(西域大道)",
                "gps": "106.500787,29.613761",
                "id": 53,
                "manager": "李华",
                "mobile": "13222222222",
                "name": "明托线1020线路001号公变",
                "no": "008",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园-附24号边疆宾馆(西域大道)",
                "gps": "106.500594,29.613831",
                "id": 52,
                "manager": "李华",
                "mobile": "13222222222",
                "name": "明托线1020线路001号公变",
                "no": "009",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园-附8-9号边疆宾馆(西域大道)",
                "gps": "106.500835,29.6142",
                "id": 49,
                "manager": "李华",
                "mobile": "13222222222",
                "name": "明托线1020线路001号公变",
                "no": "010",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园西域大道155-附8-9号",
                "gps": "106.500803,29.614797",
                "id": 48,
                "manager": "李华",
                "mobile": "13222222222",
                "name": "明托线1020线路001号公变",
                "no": "011",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园",
                "gps": "106.501281,29.614965",
                "id": 51,
                "manager": "李华",
                "mobile": "13222222222",
                "name": "明托线1020线路001号公变",
                "no": "012",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园",
                "gps": "106.502085,29.614335",
                "id": 47,
                "manager": "李华",
                "mobile": "13222222222",
                "name": "明托线1020线路001号公变",
                "no": "013",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园",
                "gps": "106.502584,29.613855",
                "id": 50,
                "manager": "李华",
                "mobile": "13222222222",
                "name": "明托线1020线路001号公变",
                "no": "014",
                "type": 0
            }, {
                "address": "重庆市渝北区光电园",
                "gps": "106.503931,29.613859",
                "id": 40,
                "manager": "李华",
                "mobile": "13666666666",
                "name": "明托线1020线路001号公变",
                "no": "015",
                "type": 0
            }]
        }


        // 划线
        var polylines = [];
        $.each(dt, function (index, domEle) {
            // console.log(domEle);
            var path = [];
            var len = domEle.length;
            $.each(domEle, function (index, domEle) {
                var gpsdot = domEle.gps.split(",");
                // path.push([gpsdot[0], gpsdot[1]]);


                var title = '坐标信息';
                var info = [];
                //构建信息窗体中显示的内容


                info.push("<p class='input-item'><b>台区名称 :</b>" + domEle.name + " </p>");
                info.push("<p class='input-item'><b>地址 :</b>" + domEle.address + "</p></div></div>");
                info.push("<p class='input-item'><b>台区经理：</b>" + domEle.manager + "</p></div></div>");
                info.push("<p class='input-item'><b>经理电话：</b>" + domEle.mobile + "</p></div></div>");

                var dot = new AMap.InfoWindow({ //创建信息窗体
                    anchor: "bottom-center",
                    isCustom: true,  //使用自定义窗体
                    content: createInfoWindow(title, info.join("")), //使用默认信息窗体框样式，显示信息内容
                    offset: new AMap.Pixel(16, -45),
                });
                var onMarkerClick = function (e) {
                    dot.open(map, e.target.getPosition());//打开信息窗体
                }
                path.push([gpsdot[0], gpsdot[1]]);
                if (index != len - 1 && index != 0) {

                    if (domEle.type === 0) {
                        var marker = new AMap.Marker({
                            position: [gpsdot[0], gpsdot[1]],
                            icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
                            // 将 html 传给 content
                            // content: markerContent,
                            // 以 icon 的 [center bottom] 为原点
                            // offset: new AMap.Pixel(-13, -30)
                        });

                        // 将 markers 添加到地图
                        map.add(marker);
                        marker.on('click', onMarkerClick);//绑定click事件
                    } else if (domEle.type === 1) {
                        var marker = new AMap.Marker({
                            position: [gpsdot[0], gpsdot[1]],
                            icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-red.png",
                            // 将 html 传给 content
                            // content: markerContent,
                            // 以 icon 的 [center bottom] 为原点
                            // offset: new AMap.Pixel(-13, -30)
                        });

                        // 将 markers 添加到地图
                        map.add(marker);
                        marker.on('click', onMarkerClick);//绑定click事件
                    }


                } else {

                    if (index === len - 1) {
                        var Marker = new AMap.Marker({
                            position: [gpsdot[0], gpsdot[1]],
                            icon: 'https://webapi.amap.com/theme/v1.3/markers/n/end.png',
                            map: map
                        })

                        map.add(Marker);
                        Marker.on('click', onMarkerClick);//绑定click事件
                    } else {
                        var Marker = new AMap.Marker({
                            position: [gpsdot[0], gpsdot[1]],
                            icon: 'https://webapi.amap.com/theme/v1.3/markers/n/start.png',
                            map: map
                        })

                        map.add(Marker);
                        Marker.on('click', onMarkerClick);//绑定click事件
                    }

                }

            });
            polylines.push(drawRoute(path));
        });
        var overlayGroups = new AMap.OverlayGroup(polylines);
        map.add(overlayGroups);
        map.setFitView();

    })


//
// });
// });


//构建自定义信息窗体
function createInfoWindow(title, content) {
    var info = document.createElement("div");
    info.className = "custom-info input-card content-window-card";

    //可以通过下面的方式修改自定义窗体的宽高
    info.style.width = "250px";
    // 定义顶部标题
    var top = document.createElement("div");
    var titleD = document.createElement("div");
    var closeX = document.createElement("img");
    top.className = "info-top";
    titleD.innerHTML = title;
    closeX.src = "https://webapi.amap.com/images/close2.gif";
    closeX.onclick = closeInfoWindow;

    top.appendChild(titleD);
    top.appendChild(closeX);
    info.appendChild(top);

    // 定义中部内容
    var middle = document.createElement("div");
    middle.className = "info-middle";
    middle.style.backgroundColor = 'white';
    middle.innerHTML = content;
    info.appendChild(middle);

    // 定义底部内容
    var bottom = document.createElement("div");
    bottom.className = "info-bottom";
    bottom.style.position = 'relative';
    bottom.style.top = '0px';
    bottom.style.margin = '0 auto';
    var sharp = document.createElement("img");
    sharp.src = "https://webapi.amap.com/images/sharp.png";
    bottom.appendChild(sharp);
    info.appendChild(bottom);
    return info;
}

//关闭信息窗体
function closeInfoWindow() {
    map.clearInfoWindow();
}

/**
 * 绘线
 * @param path
 */
function drawRoute(path) {

    var routeLine = new AMap.Polyline({
        // path: path,
        // isOutline: true,
        // outlineColor: '#ffeeee',
        // borderWeight: 2,
        // strokeWeight: 5,
        // strokeColor: '#0091ff',
        // lineJoin: 'round'
        path: path,
        strokeWeight: 3,
        strokeColor: '#ffeeff',
        isOutline: true,
        borderWeight: 1,
        outlineColor: '#3366FF'

    })
    // routeLine.setMap(map)
    return routeLine;
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
        area: ['95vw', '95vh'],
        content: url
    });

}






