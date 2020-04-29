$(function () {

	var htUserId = T.p("htUserId");
	// vm.data.transformer.htUserId = htUserId;

	$("#jqGrid").jqGrid({
		url: baseURL + 'sys/transformer/list',
		datatype: "json",
		postData: {
			htUserId: htUserId === null ? "" : htUserId
		},
		colModel: [
			{label: '互感器编号', name: 'transformCode', index: 'transform_code', width: 50, key: true},
			{label: '互感器类别', name: 'transformCategory', index: 'transform_category', width: 80},
			{label: '电压互感器变比', name: 'voltageVar', index: 'voltage_var', width: 80},
			{label: '电流互感器变比', name: 'currentVal', index: 'current_val', width: 80}
			// { label: '高压用户id', name: 'htUserId', index: 'ht_user_id', width: 80 }
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
		}
	});


	$('#transformCategory').change(function () {

		var value = $(this).children('option:selected').val();
		if (value == "电流互感器") {
			$("#voltage").css("display", "none");
			$("#current").css("display", "block");
		} else {
			$("#current").css("display", "none");
			$("#voltage").css("display", "block");
		}
	});
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		transformer: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.transformer = {};
			vm.transformer.htUserId = T.p("htUserId");

			$("#transformCategory").val("电流互感器");
			$("#voltage").css("display", "none");
			$("#current").css("display", "block");
		},
		update: function (event) {
			var transformCode = getSelectedRow();
			if(transformCode == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(transformCode)
		},
		saveOrUpdate: function (event) {

			//表单参数校验
			var transformCode = $("#transformCode").val().trim();
			if(transformCode =='' || transformCode == null){
				alert('互感器编号不能为空');
				return false;
			}

			var voltageInput = $("#voltageVar").val().trim();
			var currentInput = $("#currentVal").val().trim();
			if((voltageInput =='' || voltageInput == null) && (currentInput == '' || currentInput == null)){
				alert('电流或电压变化不能为空');
				return false;
			}

			var url = vm.transformer.id == null ? "sys/transformer/save" : "sys/transformer/update";
			vm.transformer.transformCategory = $("#transformCategory option:selected").val();

			if (vm.transformer.transformCategory == '电流互感器') {
				vm.transformer.voltageVar = null;
			} else {
				vm.transformer.currentVal = null;
			}
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.transformer),
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
			var transformCodes = getSelectedRows();
			if(transformCodes == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/transformer/delete",
                    contentType: "application/json",
				    data: JSON.stringify(transformCodes),
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
		getInfo: function(transformCode){
			console.log(transformCode);
			$.get(baseURL + "sys/transformer/info/"+transformCode, function(r){
                vm.transformer = r.transformer;
				console.log(vm.transformer.transformCategory);
				$("#transformCategory").val(vm.transformer.transformCategory);

				var categoryValue = vm.transformer.transformCategory;
				if (categoryValue == "电流互感器") {
					$("#voltage").css("display", "none");
					$("#current").css("display", "block");
				} else {
					$("#current").css("display", "none");
					$("#voltage").css("display", "block");
				}
            });

		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});