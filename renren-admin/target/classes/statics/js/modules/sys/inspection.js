$(function () {

    // var boxId = T.p("boxId");
    laydate.render({
        elem: '#time'
        , type: 'datetime'
        , range: true
        , done: function (value, date, endDate) {//控件选择完毕后的回调---点击日期、清空、现在、确定均会触发。
            vm.q.timeRange = value;
        }
    });
    boxId = T.p("boxId");
    console.log(boxId);
    vm.boxId = boxId;
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/inspection/list',
        datatype: "json",
        postData: {
            boxId: boxId === null ? -1 : boxId
        },
        colModel: [
            // {label: 'id', name: 'id', index: 'id', width: 50, key: true},
            {label: '用户编号', name: 'htUserId', index: 'ht_user_id', width: 80},
            {label: '用户名称', name: 'htUserName', index: 'ht_user_name', width: 80},
            {label: '巡检时间', name: 'createTime', index: 'create_time', width: 80},
            {label: '巡检周期', name: 'checkCycle', index: 'check_cycle', width: 30},
            {label: '地址', name: 'elecAddress', index: 'elec_address', width: 80},
            // {
            //     label: '现场拍摄照片', name: 'image', index: 'image', width: 80, formatter: function (value, options, row) {
            //         var images = value.split(",");
            //         var str = "";
            //         for (var i = 0; i < images.length; i++) {
            //             if (images[i] !== "") {
            //                 str += "<img src='../../" + images[i] + "' style='height: 150px;width: 150px;'><br>";
            //             }
            //         }
            //         return str;
            //     }
            // },
            // {label: '运行状态', name: 'meterBoxStatus', index: 'meter_box_status', width: 80},
            {label: '检查人', name: 'username', index: 'username', width: 60},
            {label: '联系电话', name: 'mobile', index: 'mobile', width: 80},
            {
                label: '操作',
                name: 'malfunctionId',
                index: 'malfunction_id',
                width: 80,
                formatter: function (value, options, row) {
                    var str = '<a class="btn btn-success btn-xs"  onclick="vm.details(' + value + ')"  ><i class="fa fa-share-square-o"></i>&nbsp;巡检详情</a>';
                    return str;
                }
            }
        ],
        viewrecords: true,
        height: 450,
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
        inspection: {},
        q: {},
        boxId: null,
        malfunction: {
            defectLevel: null,
            faultDescription: null,
            otherFaultDescription: null,
            processingStatus: null,
            image: null,
        },
    },
    methods: {
        details: function (id) {
            vm.getMalfunction(id);
            // pageLayer("巡检详情", $("#details"));
        },
        getMalfunction: function (id) {
            $.get(baseURL + "sys/malfunction/info/" + id, function (r) {
                vm.showList = false;
                // console.log(r.malfunction.defectLevel)
                switch (r.malfunction.defectLevel) {
                    case 0:
                        vm.malfunction.defectLevel = '<button type="button" class="btn btn-default btn-xs">无缺陷</button>';
                        break;
                    case 1:
                        vm.malfunction.defectLevel ='<button type="button" class="btn btn-danger btn-xs">危急缺陷</button>';
                        break;
                    case 2:
                        vm.malfunction.defectLevel = '<button type="button" class="btn btn-warning btn-xs">严重缺陷</button>';
                        break;
                    case 3:
                        vm.malfunction.defectLevel = '<button type="button" class="btn btn-info btn-xs">一般缺陷(轻度)</button>';
                        break;
                    case 4:
                        vm.malfunction.defectLevel = '<button type="button" class="btn btn-primary btn-xs">一般缺陷(轻微)</button>';
                        break;
                }
                if (r.malfunction.faultDescription===""||r.malfunction.faultDescription===null){
                    vm.malfunction.faultDescription = "无";
                }else{
                    vm.malfunction.faultDescription = r.malfunction.faultDescription;
                }

                if (r.malfunction.otherFaultDescription===""||r.malfunction.otherFaultDescription===null){
                    vm.malfunction.otherFaultDescription = "无";
                }else{
                    vm.malfunction.otherFaultDescription = r.malfunction.otherFaultDescription;
                }
                switch (r.malfunction.processingStatus) {
                    case 0:
                        vm.malfunction.processingStatus = '<button type="button" class="btn btn-secondary btn-xs">待处理</button>'
                        break;
                    case 1:
                        vm.malfunction.processingStatus = '<button type="button" class="btn btn-info btn-xs">处理待确认</button>'
                        break;
                    case 2:
                        vm.malfunction.processingStatus = '<button type="button" class="btn btn-success btn-xs">处理完成</button>'
                        break;
                }
                var images = r.malfunction.image.split(",");
                var str = "";
                for (var i = 0; i < images.length; i++) {
                    if (images[i] !== "") {
                        str += "<img src='../../" + images[i] + "' style='height: 200px;width: 200px;margin-left: 20px'>";
                    }
                }
                vm.malfunction.image = str;
            });

        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.inspection = {};
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
            var url = vm.inspection.id == null ? "sys/inspection/save" : "sys/inspection/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.inspection),
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
                    url: baseURL + "sys/inspection/delete",
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
            $.get(baseURL + "sys/inspection/info/" + id, function (r) {
                vm.inspection = r.inspection;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    boxId: vm.boxId === null ? -1 : boxId,
                    timeRange: vm.q.timeRange,
                    ownerName: vm.q.ownerName
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});