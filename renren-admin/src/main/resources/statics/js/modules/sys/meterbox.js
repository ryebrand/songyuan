$(function () {

    new AjaxUpload("#importtable", {
        action: baseURL + "sys/meterbox/upload",
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(xls|xlsx)$/.test(extension.toLowerCase()))) {
                alert('只支持.xls、.xlsx格式的excel文件！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r.code == 0) {
                alert("导入数据成功！");
                vm.reload();
            } else {
                alert(r.msg);
            }
        }
    });


    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/meterbox/list',
        datatype: "json",
        colModel: [

            {
                label: '计量箱资产号',
                name: 'meterBoxAssetNumber',
                index: 'meter_box_asset_number',
                width: 140,
                formatter: function (value, options, row) {
                    return '<a style="cursor: pointer" onclick="vm.meterBoxDetails(\'' + value + '\')">' + value + '</a>'
                }
            },
            {label: '台区名称', name: 'taiAreaName', index: 'tai_area_name', width: 140},
            // {
            //     label: '台区总表资产号',
            //     name: 'taiAreaTotalAssetNumber',
            //     index: 'tai_area_total_asset_number',
            //     width: 140
            // },
            // {label: '总电能表封印',  name: 'totalEnergyMeterSeal', index: 'total_energy_meter_seal', width: 140},
            {label: '供电所', name: 'powerSupply', index: 'power_supply', width: 140},
            {label: '计量箱规格型号', name: 'meterBoxModel', index: 'meter_box_model', width: 140},
            {label: '地址',  name: 'address', index: 'address', width: 140},
            {label: '倍率', name: 'magnification', index: 'magnification', width: 60},
            {label: '计量箱编号', name: 'meterBoxNumber', index: 'meter_box_number', width: 80},
            // {label: '经纬度',  name: 'meterBoxAddress', index: 'meter_box_address', width: 140},
            // {
            //     label: '采集终端资产号',
            //     name: 'acquisitionTerminalAssetNumber',
            //     index: 'acquisition_terminal_asset_number',
            //     width: 70
            // },
            // {label: '责任人', name: 'name', index: 'name', width: 80},
            // { label: '设备责任人id', name: 'equipmentOwnerId', index: 'equipment_owner_id', width: 80 },
            {
                label: '状态',
                name: 'meterBoxStatus',
                index: 'meter_box_status',
                width: 80,
                formatter: function (value, options, row) {
                    var str = value === "运行" ? '<button type="button" class="btn btn-success btn-xs">运行</button>' : '<button type="button" class="btn btn-secondary btn-xs">在库</button>';
                    return str;
                }
            },
            // { label: '删除状态 ：0：使用 1：删除 ', name: 'deleteStatus', index: 'delete_status', width: 80 }
            {
                label: '操作',
                name: 'id',
                index: 'id',
                width: 200,
                key: true,
                formatter: function (value, options, row) {
                    var str = '<a class="btn btn-primary btn-xs"  onclick="vm.showifram(1,' + value + ')"  ><i class="fa fa-list-ol"></i>&nbsp;电表列表</a> ' +
                        '&nbsp;&nbsp;&nbsp;<a class="btn btn-primary btn-xs" onclick="vm.showifram(2,' + value + ')" ><i class="fa fa-search-plus"></i>&nbsp;巡检记录</a>' +
                        '<br><br> <a class="btn btn-primary btn-xs" onclick="vm.transformation(\'' + row.meterBoxAssetNumber + '\')" ><i class="fa fa-exchange"></i>&nbsp;提交验收</a>' +
                        '&nbsp;&nbsp;<a class="btn btn-primary btn-xs" onclick="vm.updateOwner(' + value + ')" ><i class="fa fa-pencil-square-o"></i>&nbsp;更改责任人</a>';
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
        multiselect: true,
        // shrinkToFit: true,
        // autoScroll: true,
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
            // $("#jqGrid").jqGrid('setFrozenColumns');//设置冻结列生效
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        addAndUpdate: false,
        title: null,
        meterBox: {},
        q: {},
        meterBoxStatus: [],
        changeOwnerShow: false,
        oq: {},
        meterBoxId: null,
        isCreateTbed: false,
        acceptanceType: null,
        id: null,
        details: false,
        meterBoxDet: {
            meterBoxAssetNumber:null
        },
    },
    methods: {
        meterBoxDetails: function (assetNumber) {
            $.get(baseURL + "sys/meterbox/details/" + assetNumber, function (r) {
                vm.meterBoxDet = r.data;
                // console.log(r.data);
                // console.log(vm.meterBoxDet);
            });
            // this.$forceUpdate();
            vm.showList = false;
            vm.details = true;

        },
        transformation: function (id) {
            vm.id = id;
            layer.open({
                type: 1,
                title: '提交验收类型',
                area: ['300px', '180px'],
                resize: false,
                content: $("#changeType"),
                btn: ['提交', '取消'],
                btn1: function (index, layero) {
                    confirm('确定提交验收类型吗？', function () {
                        $.get(baseURL + "sys/meterbox/transformation/" + id + "/" + vm.acceptanceType, function (r) {
                            if (r.code === 0) {
                                layer.close(index);
                                alert("提交成功！");
                            } else {
                                alert(r.message);
                            }
                        });
                    });
                },
                btn2: function (index, layero) {
                    layer.close(index);
                }
            });
        },
        transformationConfirm: function () {

        },
        updateOwner: function (id) {
            vm.meterBoxId = id;
            if (vm.isCreateTbed === true) {
                var page = $("#ownerjqGrid").jqGrid('getGridParam', 'page');
                $("#ownerjqGrid").jqGrid('setGridParam', {
                    postData: {
                        name: vm.oq.name,
                        username: vm.oq.username,
                        tbUserName: vm.oq.tbUserName
                    },
                    page: page
                }).trigger("reloadGrid");
            } else {
                vm.createOwnerTb();
            }
            layer.open({
                type: 1,
                title: "责任人列表",
                maxmin: true,
                shadeClose: true,
                shade: 0.5,
                area: ['80vw', '80vh'],
                content: $("#changeOwnerShow")
            });
        },
        oQuery: function () {
            var page = $("#ownerjqGrid").jqGrid('getGridParam', 'page');
            $("#ownerjqGrid").jqGrid('setGridParam', {
                postData: {
                    name: vm.oq.name,
                    username: vm.oq.username,
                    tbUserName: vm.oq.tbUserName
                },
                page: page
            }).trigger("reloadGrid");
        },
        changeOwner: function () {
            var ids = getSelectedRowById("ownerjqGrid");
            if (ids == null) {
                return;
            }
            confirm('确定更改责任人吗？', function () {
                $.get(baseURL + "sys/meterbox/changeOwner/" + vm.meterBoxId + "/" + ids, function (r) {
                    if (r.code === 0) {
                        alert("更改责任人成功！");
                    } else {
                        alert(r.message);
                    }
                });
            });
        },
        createOwnerTb: function () {
            $("#ownerjqGrid").jqGrid({
                url: baseURL + 'sys/equipmentowner/list',
                datatype: "json",
                colModel: [
                    {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
                    {label: '名字', name: 'name', index: 'name', width: 80},
                    {label: '电话号码', name: 'phoneNumber', index: 'phone_number', width: 80},
                    {label: '座机号', name: 'studioCamera', index: 'studio_camera', width: 80},
                    {label: '创建时间', name: 'createDate', index: 'create_date', width: 80},
                    // { label: '职位', name: 'position', index: 'position', width: 80 },
                ],
                viewrecords: true,
                height: 385,
                rowNum: 10,
                rowList: [10, 30, 50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth: true,
                multiselect: true,
                pager: "#ownerjqGridPager",
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
                    $("#ownerjqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                    $("#ownerjqGrid").setGridHeight($(window).height() * 0.5);
                    $("#ownerjqGrid").setGridWidth($(window).width() * 0.75);
                }
            });
            vm.isCreateTbed = true;
        },
        /**
         * 弹出层
         * @param type
         * @param id
         */
        showifram: function (type, id) {
            var title = type === 1 ? "电表列表" : "巡检记录";
            var url = type === 1 ? 'meter.html?id=' + id : 'inspection.html?boxId=' + id;
            iframe(title, url);
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.addAndUpdate = true;
            vm.title = "新增";
            vm.meterBox = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.addAndUpdate = true;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.meterBox.id == null ? "sys/meterbox/save" : "sys/meterbox/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.meterBox),
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
                    url: baseURL + "sys/meterbox/delete",
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
            $.get(baseURL + "sys/meterbox/info/" + id, function (r) {
                vm.meterBox = r.meterBox;
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.addAndUpdate = false;
            vm.details = false;
            vm.q.meterBoxStatus = vm.meterBoxStatus;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    meterBoxAssetNumber: vm.q.meterBoxAssetNumber,
                    address: vm.q.address,
                    taiAreaName: vm.q.taiAreaName,
                    meterBoxStatus: vm.q.meterBoxStatus.toString()
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});