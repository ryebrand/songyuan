$(function () {


    $(window).on('resize', function () {
        var $content = $('#larry-tab .layui-tab-content');
        $content.height($(this).height() - 140);
        $content.find('iframe').each(function () {
            $(this).height($content.height());
        });
    }).resize();
})
var inspectorCount = 0;

function setTimeInspectorCount() {
    if (inspectorCount === 0) {
        $.get("sys/inspector/count", function (r) {
            inspectorCount = r.count;
        });
    } else {
        var newInspectorCount = 0;
        $.get("sys/inspector/count", function (r) {
            newInspectorCount = r.count;
            if (newInspectorCount > inspectorCount) {
                inspectorCount = newInspectorCount;
                tips();
            }
        });

    }
}

function tips() {
    announcement();
    // console.log("tips");
    // layer.msg('有新巡检信息上报，请注意查看！', {
    //     time: 10000, icon: 6, btn: ['确认'], yes: function (index, layero) {
    //         layer.close(index);
    //     }
    // },);
}

/**
 * 轮询巡检记录
 */
// setInterval(function () {
//     setTimeInspectorCount()
// }, 10000);

//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props: {item: {}},
    template: [
        '<li class="layui-nav-item" >',
        '<a v-if="item.type === 0" href="javascript:;">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        '<span>{{item.name}}</span>',
        '<em class="layui-nav-more"></em>',
        '</a>',
        '<dl v-if="item.type === 0" class="layui-nav-child">',
        '<dd v-for="item in item.list" >',
        '<a v-if="item.type === 1" href="javascript:;" :data-url="item.url"><i v-if="item.icon != null" :class="item.icon" :data-icon="item.icon"></i> <span>{{item.name}}</span></a>',
        '</dd>',
        '</dl>',
        '<a v-if="item.type === 1" href="javascript:;" :data-url="item.url"><i v-if="item.icon != null" :class="item.icon" :data-icon="item.icon"></i> <span>{{item.name}}</span></a>',
        '</li>'
    ].join('')
});

//注册菜单组件
Vue.component('menuItem', menuItem);
isquery = true;
var vm = new Vue({
    el: '#layui_layout',
    data: {
        user: {},
        menuList: {},
        password: '',
        newPassword: '',
        navTitle: "首页"
    },
    methods: {
        playAudio: function () {
            this.$refs.msgTipAudio.play().catch(this.catchException);
        },
        catchException: function (exceptionMsg) {
            var _self = this;
            console.error(exceptionMsg);
            _self.$message({
                type: 'error',
                message: '该浏览器不支持播放声音！解决方案：1、点击该弹出框。或者 1、打开chrome；' +
                    '2、输入 chrome://flags/#autoplay-policy；3、点击default，选择 Setting No user gesture is required；' +
                    '4、重启chrome；',
                duration: 0
            });
        },
        getMenuList: function () {
            $.getJSON("sys/menu/nav", function (r) {
                vm.menuList = r.menuList;
            });
        },
        getUser: function () {
            $.getJSON("sys/user/info?_" + $.now(), function (r) {
                vm.user = r.user;
            });
        },
        updatePassword: function () {
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "修改密码",
                area: ['550px', '270px'],
                shadeClose: false,
                content: jQuery("#passwordLayer"),
                btn: ['修改', '取消'],
                btn1: function (index) {
                    var data = "password=" + vm.password + "&newPassword=" + vm.newPassword;
                    $.ajax({
                        type: "POST",
                        url: "sys/user/password",
                        data: data,
                        dataType: "json",
                        success: function (result) {
                            if (result.code == 0) {
                                layer.close(index);
                                layer.alert('修改成功', function (index) {
                                    location.reload();
                                });
                            } else {
                                layer.alert(result.msg);
                            }
                        }
                    });
                }
            });
        },
        donate: function () {
            layer.open({
                type: 2,
                title: false,
                area: ['806px', '467px'],
                closeBtn: 1,
                shadeClose: false,
                content: ['http://cdn.renren.io/donate.jpg', 'no']
            });
        }
    },
    created: function () {
        this.getMenuList();
        this.getUser();
    }, updated: function () {

        if ($("#larry-side .layui-nav-item>a").length == 0 || !isquery) {
            return;
        }
        console.log("执行")
        isquery = false;
        layui.config({
            base: 'statics/js/',
        }).use(['navtab', 'layer'], function () {
            window.jQuery = window.$ = layui.jquery;
            window.layer = layui.layer;
            var element = layui.element();
            var navtab = layui.navtab({
                elem: '.larry-tab-box',
                closed: false
            });
            $('#larry-nav-side').children('ul').find('li').each(function () {
                var $this = $(this);
                if ($this.find('dl').length > 0) {

                    var $dd = $this.find('dd').each(function () {
                        $(this).on('click', function () {
                            var $a = $(this).children('a');
                            var href = $a.data('url');
                            var icon = $a.children('i:first').data('icon');
                            var title = $a.children('span').text();
                            var data = {
                                href: href,
                                icon: icon,
                                title: title
                            }
                            navtab.tabAdd(data);
                        });
                    });
                } else {

                    $this.on('click', function () {
                        var $a = $(this).children('a');
                        var href = $a.data('url');
                        var icon = $a.children('i:first').data('icon');
                        var title = $a.children('span').text();
                        var data = {
                            href: href,
                            icon: icon,
                            title: title
                        }
                        navtab.tabAdd(data);
                    });
                }
            });
            $('.larry-side-menu').click(function () {
                var sideWidth = $('#larry-side').width();
                if (sideWidth === 200) {
                    $('#larry-body').animate({
                        left: '0'
                    });
                    $('#larry-footer').animate({
                        left: '0'
                    });
                    $('#larry-side').animate({
                        width: '0'
                    });
                } else {
                    $('#larry-body').animate({
                        left: '200px'
                    });
                    $('#larry-footer').animate({
                        left: '200px'
                    });
                    $('#larry-side').animate({
                        width: '200px'
                    });
                }
            });

        });
    }
});

var websocket = null;

function openWebsocket(path) {
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        var url = "ws://" + path + "/demo-api/socket";
        // console.log(url);
        websocket = new WebSocket(url);
    } else {
        alert('Not support websocket')
    }
    //连接发生错误的回调方法
    websocket.onerror = function () {
        console.log("websocket发生错误");
    };

//连接成功建立的回调方法
    websocket.onopen = function (event) {
        console.log("连接成功！")
    }

//接收到消息的回调方法
    websocket.onmessage = function (event) {
        if (event.data === "true") {
            announcement();
            vm.playAudio();
        }
    }


//连接关闭的回调方法
    websocket.onclose = function () {
        console.log("关闭连接")
    }
    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        websocket.close();
    }
}


// //关闭连接
// function closeWebSocket() {
//     websocket.close();
// }

//发送消息
function send() {
    websocket.send(message);
}


function announcement() {
    layer.open({
        type: 1
        ,
        title: false //不显示标题栏
        ,
        closeBtn: false
        ,
        area: ['350px', '200px']
        ,
        shade: 0
        ,
        id: 'LAY_layuipro' //设定一个id，防止重复弹出
        ,
        resize: false
        ,
        btn: ['确定']
        ,
        btnAlign: 'c'
        ,
        moveType: 1 //拖拽模式，0或者1
        ,
        time: 5000
        ,
        content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">有新巡检记录提交，请注意查看。</div>'
        ,
        offset: 'rb'
        ,
        yes: function (index, layero) {
            layer.closeAll();
        }
    });

}