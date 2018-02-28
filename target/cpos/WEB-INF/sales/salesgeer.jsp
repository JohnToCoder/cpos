<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/2/26
  Time: 下午3:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>销售配置</title>
	</head>
	<body>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">销售价格配置</h4>
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
			<div class="box-body">
				<div id="sgeertoolbar" class="tool-bar">
					<button id="btnsalesgeer" type="button" class="btn btn-info" onclick="doaddsalesgeer()">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>销售价格上传
					</button>
				</div>
				<table id="tabsalesgeer" data-search="true" style="font-size: 10px">
				</table>
				</div>
			</div>
		</div>
		<script>
			$(document).ready(function () {
                $('#tabsalesgeer').bootstrapTable({
                    toolbar:'#sgeertoolbar',
                    striped: true,   //是否显示行间隔色
                    cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true,   //是否显示分页（*）
                    sortable: true,   //是否启用排序
                    sortOrder: "asc",   //排序方式
                    sidePagination: "client",  //分页方式：client客户端分页，server服务端分页（*）
                    pageNumber:1,   //初始化加载第一页，默认第一页
                    pageSize: 10,   //每页的记录行数（*）
                    pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                    height:560,
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
                        field: 'sku',align: 'center', valign: 'middle', title: '货号'
                    },{
                        field: 'size',align: 'center', valign: 'middle', title: '尺码'
                    },{
                        field: 'proName', align: 'center', valign: 'middle', title: '产品名称'
                    },{
                        field: 'style',align: 'center', valign: 'middle', title: '款号'
                    },{
                        field: 'color',align: 'center', valign: 'middle', title: '颜色'
                    },{
                        field: 'listPrice',align: 'center', valign: 'middle', title: '吊牌价格',
                        formatter:function (value,row,index) {
	                        return ['<input type="text" class="form-control pull-right" style="height: 25px;width: 100%" data-toggle="tooltip" data-placement="bottom" title="" value='+row.listPrice+' onchange="rowchangelistp(\''+row.sku+'\',this)">',].join('');
                        }
                    },{
                        field: 'discount',align: 'center', valign: 'middle', title: '折扣率',
                        formatter:function (value,row,index) {
                            return ['<input type="text" class="form-control pull-right" style="height: 25px;width: 100%" data-toggle="tooltip" data-placement="bottom" title="" value='+row.discount+' onchange="rowchangedisc(\''+row.sku+'\',\''+row.listPrice+'\',this)">',].join('');
                        }
                    },{
                        field: 'curPrice',align: 'center', valign: 'middle', title: '折后价格'
                    }]
                });

                loadtabsalesgeer();
            });
		</script>
	</body>
</html>
