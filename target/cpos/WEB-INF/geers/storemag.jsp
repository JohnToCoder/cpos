<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/2/3
  Time: 下午3:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>单位管理</title>
	</head>
	<body>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">单位列表</h4>
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
				<div id="storetoolbar" class="tool-bar">
					<button id="btn_add" type="button" class="btn btn-success" onclick="doEditStore()">
						<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>编辑
					</button>

					<button id="btn_delete" type="button" class="btn btn-danger" onclick="doDeleteStore()">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					</button>

				</div>
				<table id="geerStore" data-search="true" style="font-size: 10px">
				</table>
			</div>
		</div>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">单位编辑</h4>
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
					<div class="col-md-1" ><label>单位编号</label></div>
					<div class="col-md-3 " >
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px ;padding: 3px 6px"><i class="fa fa-code"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="单位编号" id="mgscode" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
					<div class="col-md-1 "><label>上级单位</label></div>
					<div class="col-md-3 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-institution"> </i></span>
							<select class="form-control select2 select2-hidden-accessible" style="width:100%; height: 25px;border-radius: 0px" tabindex="-1" aria-hidden="true" id="selectupstore"
							        data-toggle="tooltip" data-placement="bottom" title=""></select>
						</div>
					</div>
					<div class="col-md-1 "><label>单位类型</label></div>
					<div class="col-md-3 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-home"> </i></span>
							<select class="form-control select2 select2-hidden-accessible" style="width:100%; height: 25px;border-radius: 0px" tabindex="-1" aria-hidden="true" id="selectstype"
							        data-toggle="tooltip" data-placement="bottom" title=""></select>
						</div>
					</div>
				</div>
				<hr size="1" style="margin: 5px" color="#999999"/>
				<div class="row">
					<div class="col-md-1 "><label>单位名称</label></div>
					<div class="col-md-3 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-pencil-square-o"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="单位名称" id="mgsname" >
						</div>
					</div>

					<div class="col-md-1" ><label>单位区域</label></div>
					<div class="col-md-3 " >
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px ;padding: 3px 6px"><i class="fa fa-map-o"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="所属区域" id="mgsarea" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
					<div class="col-md-1" ><label>单位电话</label></div>
					<div class="col-md-3 " >
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-phone"> </i></span>
							<input class="form-control" style="height: 25px" type="tel" placeholder="单位电话" id="mgstel" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
				</div>
				<hr size="1" style="margin: 5px" color="#999999"/>
				<div class="row">
					<div class="col-md-1" ><label>单位地址</label></div>
					<div class="col-md-6 " >
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px ;padding: 3px 6px"><i class="fa fa-map-marker"> </i></span>
							<input class="form-control" style="height: 25px" type="email" placeholder="地址" id="mgsaddr" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
				</div>
			</div>
			<div class="box-footer">
				<button type="submit" class="btn btn-default" onclick="doCancelmgs()">取 消</button>
				<button type="submit" class="btn btn-info pull-right" onclick="doSavemgs()">保 存</button>
			</div>
		</div>
		<script>
            $(document).ready(function(){
                $("#geerStore").bootstrapTable({
                    toolbar:'#storetoolbar',
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
                    }, {
                        field: 'storeCode',align: 'center', valign: 'middle', title: '单位编号'
                    }, {
                        field: 'storeName',align: 'center', valign: 'middle', title: '单位名称'
                    }, {
                        field: 'storePname', align: 'center', valign: 'middle', title: '上级单位'
                    }, {
                        field: 'storeArea',align: 'center', valign: 'middle', title: '区域'
                    }, {
                        field: 'storeAddr',align: 'center', valign: 'middle', title: '地址'
                    },{
                        field: 'storeTel',align: 'center', valign: 'middle', title: '电话'
                    }, {
                        field: 'storeTypeDesc',align: 'center', valign: 'middle', title: '类型'
                    }, {
                        field: 'gmtCreat',align: 'center', valign: 'middle', title: '创建时间'
                    },{
                        field: 'gmtModify',align: 'center', valign: 'middle', title: '修改时间'
                    }]
                });
                loadGeerStoreTab();
                initSelectUpStore();
                initSelectStype();
            });
		</script>
	</body>
</html>
