$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/region/list',
        datatype: "json",
        colModel: [

            {label: '区域名称', name: 'name', index: 'name', width: 80},
            {label: '分配用户姓名', name: 'tbUserName', index: 'tbUserName', width: 80},
            {label: '分配用户账户名', name: 'username', index: 'username', width: 80},
            {label: '电话号码', name: 'mobile', index: 'mobile', width: 80},
            {
                label: '操作', name: 'id', index: 'id', width: 50, key: true, formatter: function (value, options, row) {
                    var str = '<a class="btn btn-warning btn-xs"  onclick="vm.updateUser(' + value + ')"  ><i class="fa fa-pencil-square-o"></i>&nbsp;区域管理员更改</a>';
                    return str;
                }
            },
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            // root: "page.list",
            // page: "page.currPage",
            // total: "page.totalPage",
            // records: "page.totalCount"
            root: "page.records",//列表数据
            page: "page.currPage", //当前页数
            total: "page.pages",//总页数
            records: "page.total" //总记录数
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            $("#jqGrid").setGridHeight($(window).height() * 0.8);
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        userShowList: false,
        title: null,
        region: {},
        q: {},
        uq: {},
        isCreateTbed: false,
        id: null,
    },
    methods: {
        distribution: function () {
            var ids = getSelectedRowById("userjqGrid");
            if (ids == null) {
                return;
            }
            confirm('确定分配选择用户吗？', function () {
                $.get(baseURL + "sys/region/distribution/" + vm.id + "/" + ids, function (r) {
                    if (r.code === 0) {
                        alert("分配成功！");
                    } else {
                        alert(r.message);
                    }
                });
            });

        },
        uQuery: function () {
            var page = $("#userjqGrid").jqGrid('getGridParam', 'page');
            $("#userjqGrid").jqGrid('setGridParam', {
                postData: {
                    username: vm.uq.username,
                    mobile: vm.uq.mobile,
                    name: vm.uq.name,
                },
                page: page
            }).trigger("reloadGrid");
        },
        updateUser: function (id) {
            vm.id = id;
            if (vm.isCreateTbed === true) {
                var page = $("#userjqGrid").jqGrid('getGridParam', 'page');
                $("#userjqGrid").jqGrid('setGridParam', {
                    postData: {
                        username: vm.uq.username,
                        mobile: vm.uq.mobile,
                        name: vm.uq.name,
                    },
                    page: page
                }).trigger("reloadGrid");
            } else {
                vm.createUserTb();
            }
            layer.open({
                type: 1,
                title: "用户列表",
                maxmin: true,
                shadeClose: true,
                shade: 0.5,
                area: ['80vw', '80vh'],
                content: $("#userShowList")
            });
            // iframe("用户列表", $("#userShowList").html());
            // vm.showList = false;
            // vm.userShowList = true;

        },
        createUserTb: function () {
            $("#userjqGrid").jqGrid({
                url: baseURL + 'sys/appuser/list',
                datatype: "json",
                colModel: [
                    {label: 'userId', name: 'userId', index: 'user_id', width: 50, key: true, hidden: true},
                    {label: '用户名', name: 'username', index: 'username', width: 80},
                    {label: '手机号', name: 'mobile', index: 'mobile', width: 80},
                    // {label: '密码', name: 'password', index: 'password', width: 80},
                    // {label: '创建时间', name: 'createTime', index: 'create_time', width: 80},
                    {label: '姓名', name: 'name', index: 'name', width: 80}
                ],
                viewrecords: true,
                height: 385,
                rowNum: 10,
                rowList: [10, 30, 50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth: true,
                multiselect: true,
                pager: "#userjqGridPager",
                jsonReader: {
                    root: "page.list",
                    page: "page.currPage",
                    total: "page.totalPage",
                    records: "page.totalCount"
                },
                prmNames: {
                    page: "page",
                    rows: "limit",
                    order: "order"
                },
                gridComplete: function () {
                    //隐藏grid底部滚动条
                    $("#userjqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                    $("#userjqGrid").setGridHeight($(window).height() * 0.5);
                    $("#userjqGrid").setGridWidth($(window).width() * 0.75);
                }
            });
            vm.isCreateTbed = true;
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.region = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.region.id == null ? "sys/region/save" : "sys/region/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.region),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/region/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (id) {
            $.get(baseURL + "sys/region/info/" + id, function (r) {
                vm.region = r.region;
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.userShowList = false;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    name: vm.q.name,
                    username: vm.q.username,
                    tbUserName: vm.q.tbUserName
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});