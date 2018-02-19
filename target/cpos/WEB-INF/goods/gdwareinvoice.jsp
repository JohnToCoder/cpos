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
				<h4 class="box-title">新建发货指令单</h4>
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
					<div class="col-md-1" ><label>发货仓库</label></div>
					<div class="col-md-3 " >
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px ;padding: 3px 6px"><i class="fa fa-cubes"> </i></span>
							<select class="form-control select2 select2-hidden-accessible" style="width:100%; height: 25px;border-radius: 0px" tabindex="-1" aria-hidden="true"
							        id="slcwarecode" data-toggle="tooltip" data-placement="bottom" title=""></select>
						</div>
					</div>
					<div class="col-md-1 "><label>收货门店</label></div>
					<div class="col-md-3 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-institution"> </i></span>
							<select class="form-control select2 select2-hidden-accessible" style="width:100%; height: 25px;border-radius: 0px" tabindex="-1" aria-hidden="true"
							        id="slcstorecode" data-toggle="tooltip" data-placement="bottom" title=""></select>
						</div>
					</div>
					<div class="col-md-1 "><label>配送方式</label></div>
					<div class="col-md-3 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-retweet"> </i></span>
							<select class="form-control select2 select2-hidden-accessible" style="width:100%; height: 25px;border-radius: 0px" tabindex="-1" aria-hidden="true"
							        id="slcwimethod" data-toggle="tooltip" data-placement="bottom" title=""></select>
						</div>
					</div>
				</div>
				<hr size="1" style="margin: 5px" color="#999999"/>
				<div class="row ">
					<div class="col-md-1" ><label>物流单号</label></div>
					<div class="col-md-3 " >
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px ;padding: 3px 6px"><i class="fa fa-list-alt"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="物流单号" id="txbwlcode" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
					<div class="col-md-1 "><label>配送总数</label></div>
					<div class="col-md-3 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-cc"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="配送总数" id="txbwicount" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
				</div>
				<hr size="1" style="margin: 5px" color="#999999"/>
				<div id="tipwidtl" class="alert-warning" ></div>
				<table class="table table-striped table-bordered table-hover" style="font-size: 10px" id="newwidtl">
					<thead>
						<tr bgcolor="#8DB6CD">
							<th style="text-align:center" width="20%">货品标识码</th>
							<th style="text-align:center" width="15%">款号</th>
							<th style="text-align:center" width="15%">颜色</th>
							<th style="text-align:center" width="15%">配码</th>
							<th style="text-align:center" width="15%">配码数量</th>
							<th style="text-align:center" width="10%">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td style="text-align:center; " onclick="tdclick(this,'1')"></td>
							<td style="text-align:center; " onclick="tdclick(this,'1')"></td>
							<td style="text-align:center; " onclick="tdclick(this,'1')"></td>
							<td style="text-align:center; " onclick="tdclick(this,'1')"></td>
							<td style="text-align:center; " onclick="tdclick(this,'0')"></td>
							<td style="text-align:center; " onclick="deletetr(this)">
								<button type="button" class="btn btn-xs btn-link">删除</button></td>
						</tr>
					</tbody>
				</table>

			</div>
			<div class="box-footer">
				<button type="submit" class="btn btn-default" onclick="dosavenwi()"><span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span>保 存</button>
				<button type="submit" class="btn btn-info pull-right" onclick="doaddnwi()"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新 增</button>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-body"style="font-size: 14px" id="query">
				<form class="form-inline" role="form" style="float: left; width: 100%" method="post" id="queryForm">
					<div id="queryTip" class="alert-warning" ></div>
					<div class="form-group">
						<label for="orderNum" >  仓库发货单号:</label>
						<input type="text" class="form-control" name="orderNum" id="orderNum"  placeholder="请输入派工单号">
					</div>
					<div class="form-group">
						<label for="empDepart">  收货地区:</label>
						<input type="text" class="form-control" name="orderNum" id="empDepart"  placeholder="请输入部门">
					</div>
					<div class="form-group">
						<label for="querystore" >  收货门店:</label>
						<select class="form-control" name="querystore" id="querystore" >
							<option value="">选择部门</option>
						</select>
					</div>
					<div class="form-group">
						<label >  日期:</label>
						<div class="input-group date">
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
							<input type="text" class="form-control pull-right" id="wistartdate">
						</div>
						<label >--</label>
						<div class="input-group date">
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
							<input type="text" class="form-control pull-right" id="wienddate">
						</div>
					</div>
					<div class="form-group ">
						<button type="button" id="queryBtn" onclick="doQueryOrder();" class="btn btn-primary">查询</button>
					</div>
				</form>
			</div>
		</div>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">已生成发货指令单</h4>
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
				<div id="witoolbar" class="tool-bar">
					<button id="btn_query" type="button" class="btn btn-info" onclick="doVerifyWareInvoices()">
						<span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>审核
					</button>
					<button id="btn_delete" type="button" class="btn btn-danger" onclick="dltWareInvoices()">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					</button>

				</div>
				<table id="wareinvoicetab" data-search="true" style="font-size: 10px">
				</table>
			</div>
		</div>
		<script>
			$(document).ready(function () {
                $('#wistartdate').datepicker({ format: "yyyy-mm-dd",autoclose:true });
                $('#wienddate').datepicker({ format: "yyyy-mm-dd",autoclose:true });
                $('#wareinvoicetab').bootstrapTable({
	                toolbar:'#witoolbar',
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
                loadwareinvoicetab();
                loadslcwarecode();
                loadslcstorecode();
                loadslcwimethod();
                loadquerystore();
			});
		</script>
	</body>
</html>
