<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/2/26
  Time: 上午11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>销售开单</title>
	</head>
	<body>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">销售清单</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
					</button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<table class="table table-striped table-bordered table-hover" style="font-size: 10px" id="tabsaleslist">
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
				<hr size="1" style="margin: 5px" color="#999999"/>
				<div class="col-md-2">
					<h5>挂单</h5>
				</div>
				<div class="col-md-6">
					<h5 class="pull-right">结算</h5>
				</div>
			</div>
		</div>
	</body>
</html>
