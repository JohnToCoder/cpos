<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/2/3
  Time: 下午3:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>门店库存</title>
	</head>
	<body>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">本店库存查询</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
					</button>
					<div class="btn-group">
						<button type="button" class="btn btn-box-tool dropdown-toggle" data-toggle="dropdown">
							<i class="fa fa-wrench"></i></button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
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
							<input class="form-control" style="height: 25px" type="text" placeholder="按SKU查询" id="txbswsku" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
					<div class="col-md-1" ><label>货品款号</label></div>
					<div class="col-md-2 " >
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px ;padding: 3px 6px"><i class="fa fa-cubes"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="查询款号" id="txbswstyle" data-toggle="tooltip" data-placement="bottom" title="">						</div>
					</div>
					<div class="col-md-1 "><label>货品尺码</label></div>
					<div class="col-md-2 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-sort-numeric-asc"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="查询尺码" id="txbswsize" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
					<div class="col-md-1 ">
						<button id="btnswquery" type="button" class="btn btn-info" style="height: 25px;padding: 2px" onclick="doQueryStoreWare()">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
						</button>
					</div>

				</div>
				<hr size="1" style="margin: 5px" color="#999999"/>

				<div id="tipsws" class="alert-warning" ></div>
				<table id="tabstoreware" data-search="true" style="font-size: 10px">
				</table>
			</div>

		</div>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">所有门店库存查询</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
					</button>
					<div class="btn-group">
						<button type="button" class="btn btn-box-tool dropdown-toggle" data-toggle="dropdown">
							<i class="fa fa-wrench"></i></button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
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
							<input class="form-control" style="height: 25px" type="text" placeholder="按SKU查询" id="txballswsku" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
					<div class="col-md-1" ><label>货品款号</label></div>
					<div class="col-md-2 " >
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px ;padding: 3px 6px"><i class="fa fa-cubes"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="查询款号" id="txballswstyle" data-toggle="tooltip" data-placement="bottom" title="">						</div>
					</div>
					<div class="col-md-1 "><label>货品尺码</label></div>
					<div class="col-md-2 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-sort-numeric-asc"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="查询尺码" id="txballswsize" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
					<div class="col-md-1 ">
						<button id="btnallswquery" type="button" class="btn btn-info" style="height: 25px;padding: 2px" onclick="doQueryAllStoreWare()">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
						</button>
					</div>

				</div>
				<hr size="1" style="margin: 5px" color="#999999"/>

				<div id="tipallsws" class="alert-warning" ></div>
				<table id="taballstoreware" data-search="true" style="font-size: 10px">
				</table>
			</div>

		</div>
		<script>
			$(document).ready(function(){
                $('#tabstoreware').bootstrapTable({
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
                        field: 'gmtCreat',align: 'center', valign: 'middle', title: '创建时间'
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
                $('#taballstoreware').bootstrapTable({
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
                        field: 'storeName',align: 'center', valign: 'middle', title: '货品门店'
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
			});
		</script>
	</body>
</html>
