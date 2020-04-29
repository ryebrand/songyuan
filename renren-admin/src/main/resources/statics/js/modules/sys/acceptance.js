$(function () {
    laydate.render({
        elem: '#time'
        , type: 'datetime'
        , range: true
        , done: function (value, date, endDate) {//控件选择完毕后的回调---点击日期、清空、现在、确定均会触发。
            vm.q.buildDate = value;
        }
    });

    laydate.render({
        elem: '#time2'
        , type: 'datetime'
        , range: true
        , done: function (value, date, endDate) {//控件选择完毕后的回调---点击日期、清空、现在、确定均会触发。
            vm.q.completionDate = value;
        }
    });

    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/acceptance/list',
        datatype: "json",
        colModel: [
            // { label: 'id', name: 'id', index: 'id', width: 50, key: true },
            // { label: '类型：0 ：电表箱   ## 1：电能表', name: 'type', index: 'type', width: 80 },
            {label: '计量箱资产号', name: 'assetNumber', index: 'asset_number', width: 80},
            {
                label: '验收类型',
                name: 'acceptanceType',
                index: 'acceptance_type',
                width: 80,
                formatter: function (value, options, row) {
                    var str = "";
                    switch (value) {
                        case 0:
                            str = "新装";
                            break;
                        case 1:
                            str = "改造";
                            break;
                        case 2:
                            str = "更换";
                            break;
                        case 3:
                            str = "消缺";
                            break;
                    }
                    return str;
                }
            },
            {
                label: '缺陷描述',
                name: 'defectDescription',
                index: 'defect_description',
                width: 80,
                formatter: function (value, options, row) {
                    if (value === null || value === "") {
                        return "无";
                    } else {
                        return value;
                    }
                }
            },
            {label: '生成验收记录时间', name: 'buildDate', index: 'build_date', width: 80},
            {
                label: '完成验收时间',
                name: 'completionDate',
                index: 'completion_date',
                width: 80,
                formatter: function (value, options, row) {
                    if (value === null || value === "") {
                        return "未完成";
                    } else {
                        return value;
                    }
                }
            },
            {
                label: '验收状态',
                name: 'step',
                index: 'step',
                width: 80,
                formatter: function (value, options, row) {
                    var str = "";
                    console.log(value);
                    var step = Number(value);
                    switch (value) {
                        case 0:
                            str = '<button type="button" class="btn btn-secondary btn-xs">待验收</button>';
                            break;
                        case 1:
                            str = '<button type="button" class="btn btn-danger btn-xs">存在缺陷</button>';
                            break;
                        case 2:
                            str = '<button type="button" class="btn btn-success btn-xs">验收完成</button>';
                            break;
                    }
                    return str;
                }
            },
            {
                label: '操作',
                name: 'id',
                index: 'id',
                width: 80,
                formatter: function (value, options, row) {
                    var str = '<a class="btn btn-primary btn-xs"  onclick="vm.details(' + value + ')"  ><i class="fa fa-info"></i>&nbsp;验收详情</a> &nbsp;';
                    return str;
                }
            },
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        // rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        // multiselect: true,
        pager: "#jqGridPager",
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
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            $("#jqGrid").setGridHeight($(window).height() * 0.8);
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        acceptance: {},
        q: {},
        //待验收块显示
        pendingShow: false,
        //验收完成或缺陷块显示
        successShow: false,
        acceptanceInfo: {}
    },
    methods: {
        details: function (id) {
            $.get(baseURL + "sys/acceptance/details/" + id, function (r) {
                console.log(r.data);
                vm.acceptanceInfo = r.data;
                console.log(vm.acceptanceInfo)
                var step = Number(vm.acceptanceInfo.step);
                if (step === 1 || step === 2) {
                    vm.showList = false;
                    vm.successShow = true;
                }
                if (step === 0) {
                    vm.showList = false;
                    vm.pendingShow = true;
                }
                vm.dataProcessing();
            });
        },
        dataProcessing: function () {
            // vm.acceptanceInfo;
            var step = "";
            switch (vm.acceptanceInfo.step) {
                case 0:
                    step = '<button type="button" class="btn btn-secondary btn-xs">待验收</button>';
                    break;
                case 1:
                    step = '<button type="button" class="btn btn-danger btn-xs">存在缺陷</button>';
                    break;
                case 2:
                    step = '<button type="button" class="btn btn-success btn-xs">验收完成</button>';
                    break;
            }
            vm.acceptanceInfo.step = step;

            var defectLevel = "";
            switch (vm.acceptanceInfo.defectLevel) {
                case 0:
                    defectLevel = '<button type="button" class="btn btn-default btn-xs">无缺陷</button>';
                    break;
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
            vm.acceptanceInfo.defectLevel = defectLevel;
            if (vm.acceptanceInfo.faultDescription === null || vm.acceptanceInfo.faultDescription === "") {
                vm.acceptanceInfo.faultDescription = "无";
            }

            if (vm.acceptanceInfo.otherFaultDescription === null || vm.acceptanceInfo.otherFaultDescription === "") {
                vm.acceptanceInfo.otherFaultDescription = "无";
            }
            console.log(vm.acceptanceInfo.image);
            if (vm.acceptanceInfo.image !== null && vm.acceptanceInfo.image !== "") {
                var images = vm.acceptanceInfo.image.split(",");
                var str = "";
                for (var i = 0; i < images.length; i++) {
                    if (images[i] !== "") {
                        str += "<img src='../../" + images[i] + "' style='height: 200px;width: 200px;margin-left: 20px'>";
                    }
                }
                vm.acceptanceInfo.image = str;
            }

            var processingStatus = "";
            switch (vm.acceptanceInfo.processingStatus) {
                case 0:
                    processingStatus = '<button type="button" class="btn btn-secondary btn-xs">待处理</button>';
                    break;
                case 1:
                    processingStatus = '<button type="button" class="btn btn-danger btn-xs">处理中</button>';
                    break;
                case 2:
                    processingStatus = '<button type="button" class="btn btn-success btn-xs">处理完成</button>';
                    break;
                default:
                    processingStatus = "无缺陷"
            }
            vm.acceptanceInfo.processingStatus = processingStatus;
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.acceptance = {};
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
            var url = vm.acceptance.id == null ? "sys/acceptance/save" : "sys/acceptance/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.acceptance),
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
                    url: baseURL + "sys/acceptance/delete",
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
            $.get(baseURL + "sys/acceptance/info/" + id, function (r) {
                vm.acceptance = r.acceptance;
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.successShow = false;
            vm.pendingShow = false;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    }
});