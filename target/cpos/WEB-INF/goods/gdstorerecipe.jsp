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
		<title>门店收货单</title>
	</head>
	<body>
		<div class="panel panel-default">
			<div class="panel-body"style="font-size: 14px" id="query">
				<form class="form-inline" role="form" style="float: left; width: 100%" method="post" id="queryForm">
					<div id="queryTip" class="alert-warning" ></div>
					<div class="form-group">
						<label for="txbwibill" >  发货单号:</label>
						<input type="text" class="form-control" style="height: 25px" name="txbwibill" id="txbwibill"  placeholder="请输入发货单号">
					</div>
					<div class="form-group">
						<label for="txbwlcode" >  物流单号:</label>
						<input type="text" class="form-control" style="height: 25px" name="txbwlcode" id="txbwlcode"  placeholder="请输入物流单号">
					</div>
					<div class="form-group">
						<label for="slcstorewcode">  发货单位:</label>
						<select class="form-control select2 select2-hidden-accessible" style=" height: 25px;border-radius: 0px" tabindex="-1" aria-hidden="true"
						        id="slcstorewcode" data-toggle="tooltip" data-placement="bottom" title=""></select>
					</div>

					<div class="form-group">
						<label >  日期:</label>
						<div class="input-group date">
							<div class="input-group-addon" style="height: 25px;padding: 2px">
								<i class="fa fa-calendar"></i>
							</div>
							<input type="text" class="form-control pull-right" style="height: 25px" id="wisstartdt">
						</div>
						<label >--</label>
						<div class="input-group date">
							<div class="input-group-addon" style="height: 25px;padding: 2px">
								<i class="fa fa-calendar"></i>
							</div>
							<input type="text" class="form-control pull-right" style="height: 25px" id="wisenddt">
						</div>
					</div>
					<div class="form-group ">
						<button type="button" id="queryBtn" onclick="doQueryStorewi();" class="btn btn-primary" style="height: 25px;padding: 2px 6px 2px 6px">查询</button>
					</div>
				</form>
			</div>
		</div>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">门店待收货单</h4>
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
				<div id="srecipetoolbar" class="tool-bar">
					<button id="btn_delete" type="button" class="btn btn-info" onclick="doStoreRecipe()">
						<span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>开始到店校验
					</button>
				</div>
				<table id="tabstorerecipe" data-search="true" style="font-size: 10px">
				</table>
			</div>
		</div>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">门店收货确认</h4>
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
				<div id="sreverifytoolbar" class="tool-bar">
					<button id="btnistorage" type="button" class="btn btn-info" onclick="doSRecipeVerify()">
						<span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>确认收货
					</button>
					<button id="btntabreload" type="button" class="btn btn-primary" onclick="loadtabstorereverify()">
						<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>刷新
					</button>
				</div>
				<table id="tabstorereverify" data-search="true" style="font-size: 10px">
				</table>
			</div>
		</div>
		<script>
			$(document).ready(function(){
                $('#wisstartdt').datepicker({ format: "yyyy-mm-dd",autoclose:true });
                $('#wisenddt').datepicker({ format: "yyyy-mm-dd",autoclose:true });
                loadslcstorewcode();
                $('#tabstorerecipe').bootstrapTable({
                    toolbar:'#srecipetoolbar',
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
                        field: 'cargodesc',align: 'center', valign: 'middle', title: '调货类型'
                    },{
                        field: 'gmtCreat',align: 'center', valign: 'middle', title: '发货时间'
                    },{
                        field: 'gmtModify',align: 'center', valign: 'middle', title: '收货时间'
                    },{
                        field:'isstorage',align:'center',valign:'middle',title:'收货校验',
                        formatter:function (value,row,index) {
                            if(row.isstorage =='0'){
                                return ['<span class="label label-danger">待校验</span>',].join('');
                            }else if(row.isstorage == '1'){
                                return ['<span class="label label-info">校验中</span>',].join('');
                            }else if(row.isstorage == '2'){
                                return ['<span class="label label-success">已入库</span>',].join('');
                            }
                        }
                    }]
                });
                $('#tabstorereverify').bootstrapTable({
                    toolbar:'#sreverifytoolbar',
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
                        field: 'wiCount',align: 'center', valign: 'middle', title: '待收货总数'
                    },{
                        field: 'recipeCount',align: 'center', valign: 'middle', title: '已收货总数'
                    },{
                        field: 'recipeDesc',align: 'center', valign: 'middle', title: '收货描述'
                    },{
                        field: 'cargodesc',align: 'center', valign: 'middle', title: '调货类型'
                    },{
                        field: 'gmtCreat',align: 'center', valign: 'middle', title: '发货时间'
                    },{
                        field:'isstorage',align:'center',valign:'middle',title:'收货校验',
                        formatter:function (value,row,index) {
                            if(row.isstorage =='0'){
                                return ['<span class="label label-danger">待校验</span>',].join('');
                            }else if(row.isstorage == '1'){
                                return ['<span class="label label-info">校验中</span>',].join('');
                            }else if(row.isstorage == '2'){
                                return ['<span class="label label-success">已入库</span>',].join('');
                            }
                        }
                    }]
                });
                loadtabstorerecipe();
                loadtabstorereverify();
			});
		</script>
	</body>
</html>
