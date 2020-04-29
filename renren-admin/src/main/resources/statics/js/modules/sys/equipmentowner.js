$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/appuser/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'userId', index: 'user_id', width: 50, key: true,hidden: true},
            {label: '名字', name: 'username', index: 'username', width: 80},
            // {label: '密码', name: 'password', index: 'password', width: 80},
            {label: '电话号码', name: 'mobile', index: 'mobile', width: 80},
            {label: '创建时间', name: 'createTime', index: 'create_time', width: 80},
            {label: '座机号', name: 'studioCamera', index: 'studio_camera', width: 80,hidden: true},
            // { label: '职位', name: 'position', index: 'position', width: 80 },
            // {label: '删除状态 ：0：使用 1：删除 ', name: 'deleteStatus', index: 'delete_status', width: 80}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 40,
        autowidth: true,
        multiselect: true,
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
            $("#jqGrid").setGridHeight($(window).height() * 0.7);
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        equipmentOwner: {},
        q: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.equipmentOwner = {};

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

            var i= 0;
            $(".form-horizontal .mustInput").each(function(item){
                var value = $(this).val().trim();
                if(value=='' || value == null){
                    i = 1;
                    $(this).focus();
                }
            });
            if( i==1 ) {
                alert('表单不能为空');
                return false;
            }
            var phone = document.getElementById('mobile').value;
            console.log(phone);
            if(phone != null && phone != ''){
                if (!(/^1[3456789]\d{9}$/.test(phone))) {
                    alert("手机号码有误，请重填");
                    return false;
                }
            }
            // if((phone != null || phone != '') && )){
            //     alert("手机号码有误，请重填");
            //     return false;
            // }

            var url = vm.equipmentOwner.userId == null ? "sys/appuser/save" : "sys/appuser/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.equipmentOwner),
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
                    url: baseURL + "sys/appuser/delete",
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
            $.get(baseURL + "sys/appuser/info/" + id, function (r) {
                vm.equipmentOwner = r.user;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    username:vm.q.username,
                    mobile:vm.q.mobile
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});