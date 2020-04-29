$(function () {

    laydate.render({
        elem: '#createTime'
        , type: 'datetime'
        , range: true
        , done: function (value, date, endDate) {//控件选择完毕后的回调---点击日期、清空、现在、确定均会触发。
            vm.q.createTime = value;
        }
    });

    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/malfunction/list',
        datatype: "json",
        colModel: [
            {label: '用户编号', name: 'htUserId', index: 'ht_user_id', width: 80},
            {label: '用户名称', name: 'userName', index: 'user_name', width: 80},
            // {label: '台区名称', name: 'taiAreaName', index: 'taiAreaName', width: 80},
            {label: '位置', name: 'elecAddress', index: 'elec_address', width: 80},
            /*{
                label: '故障类型',
                name: 'type',
                index: 'type',
                width: 80,
                formatter: function (value, options, row) {
                    var str = "";
                    switch (value) {
                        case 0:
                            str = "巡检故障";
                            break;
                        case 1:
                            str = "验收故障";
                            break;
                    }
                    return str;
                }
            },*/
            {
                label: '缺陷级别',
                name: 'defectLevel',
                index: 'defect_level',
                width: 80,
                formatter: function (value, options, row) {
                    var defectLevel = "";
                    switch (value) {
                        // case 0:
                        //     defectLevel = '<button type="button" class="btn btn-default btn-xs">无缺陷</button>';
                        //     break;
                        case 1:
                            defectLevel = '<button type="button" class="btn btn-danger btn-xs">危急缺陷</button>';
                            break;
                        case 2:
                            defectLevel = '<button type="button" class="btn btn-warning btn-xs">严重缺陷</button>';
                            break;
                        case 3:
                            defectLevel = '<button type="button" class="btn btn-info btn-xs">一般缺陷(轻度)</button>';
                            break;
                        case 4:
                            defectLevel = '<button type="button" class="btn btn-primary btn-xs">一般缺陷(轻微)</button>';
                            break;
                    }
                    return defectLevel;
                }
            },
            // {label: '故障描述', name: 'faultDescription', index: 'fault_description', width: 80},
            {label: '其它故障描述', name: 'otherFaultDescription', index: 'other_fault_description', width: 80, hidden: true},
            {label: '图片上传地址 多个，分割', name: 'image', index: 'image', width: 80, hidden: true},
            {label: '上报时间', name: 'createTime', index: 'create_time', width: 80},
            {
                label: '缺陷处理状态',
                name: 'processingStatus',
                index: 'processingStatus',
                width: 80,
                formatter: function (value, options, row) {
                    var status = "";
                    switch (value) {
                        case 0:
                            status = '<button type="button" class="btn btn-secondary btn-xs">待处理</button>'
                            break;
                        case 1:
                            status = '<button type="button" class="btn btn-info btn-xs">处理待确认</button>'
                            break;
                        case 2:
                            status = '<button type="button" class="btn btn-success btn-xs">处理完成</button>'
                            break;
                    }
                    return status;
                }
            },
            {
                label: '操作',
                name: 'id',
                index: 'id',
                width: 120,
                key: true,
                formatter: function (value, options, row) {
                    var str = '<a class="btn btn-primary btn-xs" onclick="vm.detail(' + value + ')"><i class="fa fa-info-circle"></i>&nbsp;缺陷详情</a>';
                    // if (row.processingStatus === 0) {
                    //     str += '&nbsp;&nbsp;<a class="btn btn-primary btn-xs"  onclick="vm.defectHandling(0,' + value + ')"  ><i class="fa fa-pencil-square-o">确认处理</i>'
                    // } else if (row.processingStatus === 1) {
                    //     str += '&nbsp;&nbsp;<a class="btn btn-primary btn-xs"  onclick="vm.defectHandling(1,' + value + ')"  ><i class="fa fa-pencil-square-o">处理完成</i>'
                    // }

                    if (row.processingStatus === 1) {
                        str += '&nbsp;&nbsp;<a class="btn btn-primary btn-xs"  onclick="vm.defectHandling(1,' + value + ')"  ><i class="fa fa-pencil-square-o">确认处理</i>'
                    }
                    return str;
                }
            },
        ],
        viewrecords: true,
        height: 420,
        rowNum: 10,
        rowList: [10, 30, 50],
        // rownumbers: true,
        rownumWidth: 40,
        autowidth: true,
        // multiselect: true,
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
            $("#jqGrid").setGridHeight($(window).height() * 0.7);
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        malfunction: {},
        q: {},
        type: [],
        level: [],
        status: [],
        details: {
            meterBoxAssetNumber: null,
            taiAreaName: null,
            address: null,
            meterBoxStatus: null,
            type: null,
            createTime: null,
            defectLevel: null,
            faultDescription: null,
            otherFaultDescription: null,
            image: null,
            processingStatus: null,
            name: null,
            phoneNumber: null,
            studioCamera: null
        },
    },
    methods: {
        defectHandling: function (type, id) {
            if (type === 0) {
                // layer.confirm('确认处理缺陷吗？', {
                //     btn: ['确认', '取消'] //按钮
                // }, function (index) {
                //     $.get(baseURL + "sys/malfunction/handler/" + id + "/1", function (r) {
                //         layer.close(index);
                //         if (r.code === 0) {
                //             alert("处理完成！");
                //         } else {
                //             alert(r.message);
                //         }
                //         vm.reload();
                //     });
                // }, function (index) {
                //     layer.close(index);
                // })
            } else if (type === 1) {
                layer.confirm('确实缺陷处理完成？', {
                    btn: ['完成', '取消'] //按钮
                }, function (index) {
                    $.get(baseURL + "sys/malfunction/handler/" + id + "/2", function (r) {
                        layer.close(index);
                        if (r.code === 0) {
                            alert("确认缺陷处理完成！");
                        } else {
                            alert(r.message);
                        }
                        vm.reload();
                    });
                }, function (index) {
                    layer.close(index);
                })
            }
        },
        detail: function (id) {
            // console.log(id);
            $.get(baseURL + "sys/malfunction/details/" + id, function (r) {
                vm.details = r.data;
                switch (vm.details.defectLevel) {
                    case 0:
                        vm.details.defectLevel = '<button type="button" class="btn btn-default btn-xs">无缺陷</button>';
                        break;
                    case 1:
                        vm.details.defectLevel = '<button type="button" class="btn btn-danger btn-xs">危急缺陷</button>';
                        break;
                    case 2:
                        vm.details.defectLevel = '<button type="button" class="btn btn-warning btn-xs">严重缺陷</button>';
                        break;
                    case 3:
                        vm.details.defectLevel = '<button type="button" class="btn btn-info btn-xs">一般缺陷(轻度)</button>';
                        break;
                    case 4:
                        vm.details.defectLevel = '<button type="button" class="btn btn-primary btn-xs">一般缺陷(轻微)</button>';
                        break;
                }
                switch (vm.details.processingStatus) {
                    case 0:
                        vm.details.processingStatus = '<button type="button" class="btn btn-secondary btn-xs">待处理</button>'
                        break;
                    case 1:
                        vm.details.processingStatus = '<button type="button" class="btn btn-info btn-xs">处理待确认</button>'
                        break;
                    case 2:
                        vm.details.processingStatus = '<button type="button" class="btn btn-success btn-xs">处理完成</button>'
                        break;
                }
                if (vm.details.image !== null) {
                    var images = vm.details.image.split(",");
                    var str = "";
                    for (var i = 0; i < images.length; i++) {
                        if (images[i] !== "") {
                            str += "<img src='../../" + images[i] + "' style='height: 200px;width: 200px;margin-left: 20px'>";
                        }
                    }
                    vm.details.image = str;
                }
                if (vm.details.acceptImage !== null) {
                    var images = vm.details.acceptImage.split(",");
                    var str = "";
                    for (var i = 0; i < images.length; i++) {
                        if (images[i] !== "") {
                            str += "<img src='../../" + images[i] + "' style='height: 200px;width: 200px;margin-left: 20px'>";
                        }
                    }
                    vm.details.acceptImage = str;
                }
                vm.details.meterBoxStatus = vm.details.meterBoxStatus === "运行" ? '<button type="button" class="btn btn-success btn-xs">运行</button>' : '<button type="button" class="btn btn-secondary btn-xs">在库</button>';

                vm.showList = false;
            });
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.malfunction = {};
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
            var url = vm.malfunction.id == null ? "sys/malfunction/save" : "sys/malfunction/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.malfunction),
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
                    url: baseURL + "sys/malfunction/delete",
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
            $.get(baseURL + "sys/malfunction/info/" + id, function (r) {
                vm.malfunction = r.malfunction;
            });
        },
        reload: function (event) {
            vm.q.type = vm.type;
            vm.q.level = vm.level;
            vm.q.status = vm.status;
            console.log(vm.q);
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    createTime: vm.q.createTime,
                    type: vm.q.type.toString(),
                    level: vm.q.level.toString(),
                    status: vm.q.status.toString(),
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});