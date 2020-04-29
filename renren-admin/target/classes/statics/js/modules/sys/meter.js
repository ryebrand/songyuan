$(function () {

    var id = T.p("id");


    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/meter/list',
        datatype: "json",
        postData: {
            id: id === null ? "" : id
        },
        colModel: [

            // { label: '电表箱id', name: 'meterBoxId', index: 'meter_box_id', width: 80 },
            {label: '电能表资产号', name: 'energyMeterAssetNumber', index: 'energy_meter_asset_number', width: 80},
            {label: '户名', name: 'accountName', index: 'account_name', width: 80},
            {label: '户号', name: 'accountNumber', index: 'account_number', width: 80},
            {label: '用户电话', name: 'userPhone', index: 'user_phone', width: 80},
            {label: '箱表关系', name: 'boxRelationship', index: 'box_relationship', width: 80},
            // {label: '隔离开关资产号', name: 'isolationSwitchAssetNumber', index: 'isolation_switch_asset_number', width: 80},
            // {label: '断路器资产号', name: 'circuitBreakerAssetNumber', index: 'circuit_breaker_asset_number', width: 80},
            // {label: '互感器资产号', name: 'transformerAssetNumber', index: 'transformer_asset_number', width: 80},
            // {label: '电能表封印', name: 'electricEnergyMeterSeal', index: 'electric_energy_meter_seal', width: 80},
            // { label: '删除状态 ：0：使用 1：删除 ', name: 'deleteStatus', index: 'delete_status', width: 80 }
            // { label: 'id', name: 'id', index: 'id', width: 50, key: true },
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
            $("#jqGrid").setGridHeight($(window).height() * 0.85);
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        meter: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.meter = {};
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
            var url = vm.meter.id == null ? "sys/meter/save" : "sys/meter/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.meter),
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
                    url: baseURL + "sys/meter/delete",
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
            $.get(baseURL + "sys/meter/info/" + id, function (r) {
                vm.meter = r.meter;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    }
});