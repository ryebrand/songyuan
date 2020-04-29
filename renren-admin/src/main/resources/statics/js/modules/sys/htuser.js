$(function () {

	laydate.render({
		elem: '#checkDateBefore'
		, type: 'datetime'
		, range: false
		, done: function (value, date, endDate) {//控件选择完毕后的回调---点击日期、清空、现在、确定均会触发。
			vm.htUser.checkDateBefore = value;
		}
	});

	new AjaxUpload("#importtable", {
		action: baseURL + "sys/htuser/upload",
		name: 'file',
		autoSubmit: true,
		responseType: "json",
		onSubmit: function (file, extension) {
			if (!(extension && /^(xls|xlsx)$/.test(extension.toLowerCase()))) {
				alert('只支持.xls、.xlsx格式的excel文件！');
				return false;
			}
			layer.load();
		},
		onComplete: function (file, r) {
			if (r.code == 0) {
				vm.reload();
				alert("导入数据成功！");
			} else {
				alert(r.msg);
			}
			layer.closeAll('loading');
		}
	});

    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/htuser/list',
        datatype: "json",
        colModel: [

			{ label: '用户编号', name: 'userId', index: 'user_id', width: 100, key: true,
				formatter: function (value, options, row) {
					return '<a style="cursor: pointer" onclick="vm.htUserDetail(\'' + value + '\')">' + value + '</a>'
				}
			},
			{ label: '用户名称', name: 'userName', index: 'user_name', width: 80 },
			{ label: '用电类别', name: 'elecCategory', index: 'elec_category', width: 80 },
			{ label: '用户分类', name: 'userCategory', index: 'user_category', width: 80 },
			{ label: '负荷性质', name: 'loadProperty', index: 'load_property', width: 80 },
			{ label: '用电地址信息', name: 'elecAddress', index: 'elec_address', width: 80 },
			{ label: '检察人员', name: 'checkUserName', index: 'check_user_name', width: 80 },
			{ label: '检查周期(月)', name: 'checkCycle', index: 'check_cycle', width: 80 },
			{ label: '上次检查日期', name: 'checkDateBefore', index: 'check_date_before', width: 80 }, 			
			{ label: '下次检查日期', name: 'checkDateAfter', index: 'check_date_after', width: 80 },
			{ label: '电能表条码', name: 'elecCode', index: 'elec_code', width: 80 }, 			
			{ label: '采集点编号', name: 'collectCode', index: 'collect_code', width: 80 }, 			
			{ label: '终端资产编号', name: 'assetsCode', index: 'assets_code', width: 80 },
			{
				label: '操作',
				name: 'userId',
				index: 'user_id',
				align: 'left',
				width: 200,
				key: true,
				formatter: function (value, options, row) {
					var str = '<a class="btn btn-primary btn-xs"  onclick="vm.showifram(1,\'' + value + '\')"  ><i class="fa fa-list-ol"></i>&nbsp;互感器列表</a> ' +
						'&nbsp;&nbsp;&nbsp;<a class="btn btn-primary btn-xs" onclick="vm.showifram(2,\'' + value + '\')" ><i class="fa fa-search-plus"></i>&nbsp;巡检记录</a>' +
						// '<br><br> <a class="btn btn-primary btn-xs" onclick="vm.transformation(\'' + row.meterBoxAssetNumber + '\')" ><i class="fa fa-exchange"></i>&nbsp;提交验收</a>' +
						'<br><br><a class="btn btn-primary btn-xs 11" onclick="vm.updateOwner(\'' + value + '\')" ><i class="fa fa-pencil-square-o"></i>&nbsp;更改责任人</a>';
					return str;
				}
			},
        ],
		viewrecords: true,
        height: 200,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 40,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.records",
            page: "page.current",
            total: "page.pages",
            records: "page.total"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
			$("#jqGrid").setGridHeight($(window).height() * 0.7);
        	//隐藏grid底部滚动条
        	// $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		htUser: {},
		q: {},
		isCreateTbed: false,
		details: false,
		addOrUpdate: false,
		showNameAdd: false,
		htUserDet: {
			htUserDetNumber:null
		},
		changeOwnerShow: false,
		oq: {},
		acceptanceType: null,
		id: null

	},
	methods: {

		htUserDetail: function (assetNumber) {
			console.log(assetNumber);
			$.get(baseURL + "sys/htuser/info/" + assetNumber, function (r) {
				vm.htUserDet = r.htUser;
			});
			// this.$forceUpdate();
			vm.showList = false;
			vm.addOrUpdate = false;
			vm.showNameAdd = false;
			vm.details = true;

		},

		/**
		 * 弹出层
		 * @param type
		 * @param id
		 */
		showifram: function (type, id) {
			var title = type === 1 ? "互感器列表" : "巡检记录";
			console.log(id);
			var url = type === 1 ? 'transformer.html?htUserId=' + id : 'inspection.html?boxId=' + id;
			iframe(title, url);
		},

		updateOwner: function (id) {
			vm.htUser.userId = id;
			if (vm.isCreateTbed === true) {
				var page = $("#ownerjqGrid").jqGrid('getGridParam', 'page');
				$("#ownerjqGrid").jqGrid('setGridParam', {
					postData: {
						// username: vm.oq.username,
						// mobile: vm.oq.mobile
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
				content: $("#changeOwnerShow"),
				// cancel:function(){
				// 	$("#oQMobile").val('');
				// 	$("#oQUserName").val('');
				// 	// createOwnerTb();
				// }
			});
		},
		changeOwner: function () {
			var ids = getSelectedRowById("ownerjqGrid");
			if (ids == null) {
				return;
			}

			confirm('确定更改责任人吗？', function () {
				console.log(vm.htUser.userId + "," + ids);
				$.get(baseURL + "sys/htuser/changeOwner/" + vm.htUser.userId + "/" + ids, function (r) {
					if (r.code === 0) {
						vm.reload();
						alert("更改责任人成功！");
					} else {
						alert(r.message);
					}
				});
			});
		},
		oQuery: function () {
			var page = $("#ownerjqGrid").jqGrid('getGridParam', 'page');
			$("#ownerjqGrid").jqGrid('setGridParam', {
				postData: {
					username: vm.oq.username,
					mobile: vm.oq.mobile
				},
				page: page
			}).trigger("reloadGrid");
		},
		createOwnerTb: function () {
			$("#ownerjqGrid").jqGrid({
				url: baseURL + 'sys/appuser/list',
				datatype: "json",
				colModel: [
					{label: 'id', name: 'userId', index: 'user_id', width: 50, key: true, hidden: true},
					{label: '名字', name: 'username', index: 'username', width: 80},
					{label: '电话号码', name: 'mobile', index: 'mobile', width: 80},
					{label: '创建时间', name: 'createTime', index: 'create_time', width: 80},
					{label: '创建时间', name: 'createTime', index: 'create_time', width: 80,hidden: true},
				],
				viewrecords: true,
				// height: 385,
				rowNum: 10,
				rowList: [10, 30, 50],
				rownumbers: true,
				rownumWidth: 40,
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

		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.addOrUpdate = true;
			vm.showNameAdd = true;
			vm.details = false;
			vm.title = "新增";
			vm.htUser = {};

			//填充至下拉菜单
			$("#ownerNameSelect").empty();
			$.ajax({
				type: "POST",
				url: baseURL + "sys/appuser/users",
				contentType: "application/json",
				success: function(r){
					if(r.code === 0){
						var ownerNames = r.data;
						console.log(ownerNames);
						// $("#ownerNameSelect").append("<option value='-1'>--请选择--</option>");
						for (var i = 0; i < ownerNames.length; i++) {
							$('#ownerNameSelect').append("<option value="+ownerNames[i].userId+">"+ownerNames[i].username+"</option>");
						}
					}else{
						layer.alert(r.msg);
						$('#btnSaveOrUpdate').button('reset');
						$('#btnSaveOrUpdate').dequeue();
					}
				}
			});

		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showNameAdd = false;
			vm.showList = false;
			vm.addOrUpdate = true;
			vm.details = false;
            vm.title = "修改";
            
            vm.getInfo(id);

		},
		saveOrUpdate: function (event) {

			var userName = $("input[name='userName']");
			var nameVal = $.trim(userName.val());
			if(nameVal=='' || nameVal == null) {
				alert("用户编号不能为空");
				return false;
			}
			var elecCategory = $("input[name='elecCategory']");
			var elecCategoryVal = $.trim(elecCategory.val());
			if(elecCategoryVal=='' || elecCategoryVal == null) {
				alert("用电类别不能为空");
				return false;
			}
			var userCategory = $("input[name='userCategory']");
			var userCategoryVal = $.trim(userCategory.val());
			if(userCategoryVal=='' || userCategoryVal == null) {
				alert("用户分类不能为空");
				return false;
			}
			var loadProperty = $("input[name='loadProperty']");
			var loadPropertyVal = $.trim(loadProperty.val());
			console.log(loadPropertyVal);
			if(loadPropertyVal=='' || loadPropertyVal == null) {
				alert("负荷性质不能为空");
				return false;
			}
			var elecAddress = $("input[name='elecAddress']");
			var elecAddressVal = $.trim(elecAddress.val());
			if(elecAddressVal=='' || elecAddressVal == null) {
				alert("用电地址信息不能为空");
				return false;
			}

			var checkDateBefore = $("input[name='checkDateBefore']");
			var checkDateBeforeVal = $.trim(checkDateBefore.val());
			if(checkDateBeforeVal=='' || checkDateBeforeVal == null) {
				alert("上次检查日期不能为空");
				return false;
			}

			var elecCode = $("input[name='elecCode']");
			var elecCodeVal = $.trim(elecCode.val());
			if(elecCodeVal=='' || elecCodeVal == null) {
				alert("电能表条码不能为空");
				return false;
			}
			var collectCode = $("input[name='collectCode']");
			var collectCodeVal = $.trim(collectCode.val());
			if(collectCodeVal=='' || collectCodeVal == null) {
				alert("采集点编号不能为空");
				return false;
			}
			var assetsCode = $("input[name='assetsCode']");
			var assetsCodeVal = $.trim(assetsCode.val());
			if(assetsCodeVal=='' || assetsCodeVal == null) {
				alert("终端资产编号不能为空");
				return false;
			}

			var url = vm.htUser.id == null ? "sys/htuser/save" : "sys/htuser/update";
			//在添加的时候设置ownerName为选中的下拉菜单值
			// if(vm.htUser.checkUser == null){
				vm.htUser.checkUser = $("#ownerNameSelect option:selected").val();
			// }
			// if(vm.htUser.checkCycle == null){
				vm.htUser.checkCycle = $("#checkCycleSelect option:selected").val();
			// }
			console.log(vm.htUser);
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.htUser),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/htuser/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "sys/htuser/info/"+id, function(r){
                vm.htUser = r.htUser;
				//填充至下拉菜单
				// console.log(vm.htUser.checkCycle);
				// $("#checkCycleSelect").find("option[value='"+vm.htUser.checkCycle+"']").attr("selected","selected");
				$("#checkCycleSelect").val(vm.htUser.checkCycle);

				$("#ownerNameSelect").empty();
				$.ajax({
					type: "POST",
					url: baseURL + "sys/appuser/users",
					contentType: "application/json",
					success: function(r){
						if(r.code === 0){
							var ownerNames = r.data;
							// $("#ownerNameSelect").append("<option value='-1'>--请选择--</option>");
							for (var i = 0; i < ownerNames.length; i++) {
								if(ownerNames[i].userId == vm.htUser.checkUser){
									$('#ownerNameSelect').append("<option selected value="+ownerNames[i].userId+">"+ownerNames[i].username+"</option>");
								} else {
									$('#ownerNameSelect').append("<option value="+ownerNames[i].userId+">"+ownerNames[i].username+"</option>");
								}
							}

						}else{
							layer.alert(r.msg);
							$('#btnSaveOrUpdate').button('reset');
							$('#btnSaveOrUpdate').dequeue();
						}
					}
				});
            });
		},
		reload: function (event) {
			vm.showList = true;
			vm.addOrUpdate = false;
			vm.showNameAdd = false;
			vm.details = false;

			var page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
				postData: {
					// page: 1,
					htUserId: vm.q.htUserId,
					address: vm.q.address,
					checkUserName: vm.q.checkUserName
				},
				page: 1
			}).trigger("reloadGrid");
		}
	}
});