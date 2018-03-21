<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/2/3
  Time: 上午11:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>仓库发货</title>
	</head>
	<body>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">仓库配货通知单</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
					</button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<div class="box-body">
				<div id="tabagtoolbar" class="tool-bar">
					<button id="btnnewinvoice" type="button" class="btn btn-info" onclick="doaddneagi()">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新建发货单
					</button>
				</div>
				<table id="tabagdeliver" data-search="true" style="font-size: 10px">
				</table>
				<hr size="1" style="margin: 5px" color="#999999"/>
				<div class="row ">
					<div class="col-md-1" ><label>调货单号</label></div>
					<div class="col-md-2 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-barcode"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="调货单号" id="txbwiagcode" readonly="readonly">
						</div>
					</div>
					<div class="col-md-1 "><label>配送方式</label></div>
					<div class="col-md-2 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-retweet"> </i></span>
							<select class="form-control select2 select2-hidden-accessible" style="width:100%; height: 25px;border-radius: 0px" tabindex="-1" aria-hidden="true"
							        id="slcagwimethod" data-toggle="tooltip" data-placement="bottom" title=""></select>
						</div>
					</div>
					<div class="col-md-1" ><label>物流单号</label></div>
					<div class="col-md-2 " >
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px ;padding: 3px 6px"><i class="fa fa-list-alt"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="物流单号" id="txbagwlcode" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
					<div class="col-md-1 ">
						<button id="btnswquery" type="button" class="btn btn-info" style="height: 25px;padding: 2px 4px" onclick="doCreatAgInvoice()">
							<span class="glyphicon glyphicon-check" aria-hidden="true"></span>创建
						</button>
					</div>

				</div>
			</div>
		</div>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">已生成发货指令单</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">

				<table id="wareinvoicetab" data-search="true" style="font-size: 10px">
				</table>
			</div>
		</div>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">仓库库存查询</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div class="row ">
					<div class="col-md-1" ><label>货品SKU</label></div>
					<div class="col-md-2 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-barcode"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="按SKU查询" id="txballwhsku" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
					<div class="col-md-1" ><label>货品款号</label></div>
					<div class="col-md-2 " >
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px ;padding: 3px 6px"><i class="fa fa-cubes"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="查询款号" id="txballwhstyle" data-toggle="tooltip" data-placement="bottom" title="">						</div>
					</div>
					<div class="col-md-1 "><label>货品尺码</label></div>
					<div class="col-md-2 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-sort-numeric-asc"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="查询尺码" id="txballwhsize" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
					<div class="col-md-1 ">
						<button id="btnallswquery" type="button" class="btn btn-info" style="height: 25px;padding: 2px" onclick="querytabwaregoods()">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
						</button>
					</div>

				</div>
				<hr size="1" style="margin: 5px" color="#999999"/>

				<div id="tipwg" class="alert-warning" ></div>
				<table id="tabwaregoods" data-search="true" style="font-size: 10px">
				</table>
			</div>
		</div>
		<script>
			$(document).ready(function () {
                $('#tabagdeliver').bootstrapTable({
	                toolbar:'#tabagtoolbar',
                    striped: true,   //是否显示行间隔色
                    cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true,   //是否显示分页（*）
                    sortable: true,   //是否启用排序
                    sortOrder: "asc",   //排序方式
                    sidePagination: "client",  //分页方式：client客户端分页，server服务端分页（*）
                    pageNumber:1,   //初始化加载第一页，默认第一页
                    pageSize: 10,   //每页的记录行数（*）
                    pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                    height:482,
                    strictSearch: true,
                    clickToSelect: true,  //是否启用点击选中行
                    showColumns:true,
                    cardView: false,   //是否显示详细视图
                    detailView: false,   //是否显示父子表
                    showExport: true, //是否显示导出
                    exportDataType: "all", //basic', 'all', 'selected'.
                    columns: [{
                        checkbox: true,align: 'center', valign: 'middle',
                    },{
                        field: 'agCode',align: 'center', valign: 'middle', title: '发货单号'
                    },{
                        field: 'wareName',align: 'center', valign: 'middle', title: '发货仓库'
                    },{
                        field: 'storeName', align: 'center', valign: 'middle', title: '收货门店'
                    },{
                        field: 'agCount',align: 'center', valign: 'middle', title: '配送总数'
                    },{
                        field: 'empName',align: 'center', valign: 'middle', title: '发单人'
                    },{
                        field: 'gmtCreat',align: 'center', valign: 'middle', title: '发货时间'
                    },{
                        field:'isverify',align:'center',valign:'middle',title:'审核',
                        formatter:function (value,row,index) {
                            if(row.isverify =='0'){
                                return ['<span class="label label-danger">待审核</span>',].join('');
                            }else{
                                return ['<span class="label label-success">已审核</span>',].join('');
                            }
                        }
                    },{
                        field:'isstorage',align:'center',valign:'middle',title:'发货',
                        formatter:function (value,row,index) {
                            if(row.isstorage =='0'){
                                return ['<span class="label label-danger">待发货</span>',].join('');
                            }else if(row.isstorage =='1'){
                                return ['<span class="label label-info">校验中</span>',].join('');
                            }else if(row.isstorage =='2'){
                                return ['<span class="label label-success">已发货</span>',].join('');
                            }
                        }
                    }]
                });
                $('#wareinvoicetab').bootstrapTable({
                    striped: true,   //是否显示行间隔色
                    cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true,   //是否显示分页（*）
                    sortable: true,   //是否启用排序
                    sortOrder: "asc",   //排序方式
                    sidePagination: "client",  //分页方式：client客户端分页，server服务端分页（*）
                    pageNumber:1,   //初始化加载第一页，默认第一页
                    pageSize: 10,   //每页的记录行数（*）
                    pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                    height:482,
                    strictSearch: true,
                    clickToSelect: true,  //是否启用点击选中行
                    showColumns:true,
                    cardView: false,   //是否显示详细视图
                    detailView: false,   //是否显示父子表
                    showExport: true, //是否显示导出
                    exportDataType: "all", //basic', 'all', 'selected'.
                    columns: [{
                        checkbox: true,align: 'center', valign: 'middle',
                    },{
                        field: 'wiCode',align: 'center', valign: 'middle', title: '发货单号'
                    },{
                        field: 'wareName',align: 'center', valign: 'middle', title: '发货仓库'
                    },{
                        field: 'storeName', align: 'center', valign: 'middle', title: '收货门店'
                    },{
                        field: 'wlCode',align: 'center', valign: 'middle', title: '物流单号'
                    },{
                        field: 'wilgm',align: 'center', valign: 'middle', title: '配送方式'
                    },{
                        field: 'wiCount',align: 'center', valign: 'middle', title: '配送总数'
                    },{
                        field: 'wiEmp',align: 'center', valign: 'middle', title: '发单人'
                    },{
                        field: 'gmtCreat',align: 'center', valign: 'middle', title: '发货时间'
                    },{
                        field: 'gmtModify',align: 'center', valign: 'middle', title: '收货时间'
                    },{
                        field:'isverify',align:'center',valign:'middle',title:'审核',
                        formatter:function (value,row,index) {
                            if(row.isverify =='0'){
                                return ['<span class="label label-danger">待审核</span>',].join('');
                            }else{
                                return ['<span class="label label-success">已审核</span>',].join('');
                            }
                        }
                    },{
                        field:'isstorage',align:'center',valign:'middle',title:'收货',
                        formatter:function (value,row,index) {
                            if(row.isstorage =='0'){
                                return ['<span class="label label-danger">待收货</span>',].join('');
                            }else if(row.isstorage =='1'){
                                return ['<span class="label label-info">校验中</span>',].join('');
                            }else if(row.isstorage =='2'){
                                return ['<span class="label label-success">已收货</span>',].join('');
                            }
                        }
                    }]
                });
                $('#tabwaregoods').bootstrapTable({
                    striped: true,   //是否显示行间隔色
                    cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true,   //是否显示分页（*）
                    sortable: true,   //是否启用排序
                    sortOrder: "asc",   //排序方式
                    sidePagination: "client",  //分页方式：client客户端分页，server服务端分页（*）
                    pageNumber:1,   //初始化加载第一页，默认第一页
                    pageSize: 10,   //每页的记录行数（*）
                    pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                    height:360,
                    strictSearch: true,
                    clickToSelect: true,  //是否启用点击选中行
                    showColumns:true,
                    cardView: false,   //是否显示详细视图
                    detailView: false,   //是否显示父子表
                    showExport: true, //是否显示导出
                    exportDataType: "all", //basic', 'all', 'selected'.
                    columns: [{
                        checkbox: true,align: 'center', valign: 'middle',
                    },{
                        field: 'storeCode',align: 'center', valign: 'middle', title: '货品仓库'
                    },{
                        field: 'gSku',align: 'center', valign: 'middle', title: '货品SKU'
                    },{
                        field: 'gName',align: 'center', valign: 'middle', title: '货品名称'
                    },{
                        field: 'gStyle', align: 'center', valign: 'middle', title: '货品款号'
                    },{
                        field: 'gColor',align: 'center', valign: 'middle', title: '颜色'
                    },{
                        field: 'gSize',align: 'center', valign: 'middle', title: '尺码'
                    },{
                        field: 'gCount',align: 'center', valign: 'middle', title: '库存数量'
                    },{
                        field: 'storeIndt',align: 'center', valign: 'middle', title: '入库时间'
                    },{
                        field: 'gmtCreat',align: 'center', valign: 'middle', title: '发货时间'
                    },{
                        field:'isstate',align:'center',valign:'middle',title:'状态',
                        formatter:function (value,row,index) {
                            if(row.isstate =='1'){
                                return ['<span class="label label-info">在库存</span>',].join('');
                            }else{
                                return ['<span class="label label-success">已出库</span>',].join('');
                            }
                        }
                    }]
                });
                loadtabagbillw();
                loadwareinvoicetab();
                loadslcwimethod();
                loadquerystore();
			});
		</script>
	</body>
</html>
