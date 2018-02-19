<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/2/3
  Time: 下午3:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>基础配置</title>
	</head>
	<body>

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
				<div id="wotoolbar" class="tool-bar">
					<button id="btn_add" type="button" class="btn btn-success" onclick="addInvoices()">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新建
					</button>
					<button id="btn_query" type="button" class="btn btn-info" onclick="doQueryInvoices()">
						<span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>审核
					</button>
					<button id="btn_delete" type="button" class="btn btn-danger" onclick="dltInvoices()">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					</button>

				</div>
				<table id="workorder" data-search="true" style="font-size: 10px">
				</table>
			</div>
		</div>
	</body>
</html>
