<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2017/8/3
  Time: 上午11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>软件更新</title>
		<script type="text/css">
			.file {
				position: relative;
				display: inline-block;
				background: #D0EEFF;
				border: 1px solid #99D3F5;
				border-radius: 4px;
				padding: 4px 12px;
				overflow: hidden;
				color: #1E88C7;
				text-decoration: none;
				text-indent: 0;
				line-height: 20px;
			}
			.file input {
				position: absolute;
				font-size: 100px;
				right: 0;
				top: 0;
				opacity: 0;
			}
			.file:hover {
				background: #AADFFD;
				border-color: #78C3F3;
				color: #004974;
				text-decoration: none;
			}
		</script>
	</head>
	<body>
		<div class="panel panel-default">
			<div class="panel-body container" id="query">
				<ol class="breadcrumb">
					<li><strong><span style="color: #27a0d7">客户端更新</span></strong></li>
				</ol>
				<form class="form-inline" role="form" style="float: left; width: 100%"  id="upappform">
					<div id="upappTip" class="alert-warning" ></div>
					<div class="form-group">
						<label for="versioncode"> 客户端类型:</label>
						<select class="form-control" name="apptype" id="apptype">
							<option value="app">手机端</option>
							<option value="pad">平板端</option>
						</select>
					</div>
					<div class="form-group">
						<label for="versioncode"> 客户端版本号:</label>
						<input type="text" class="form-control" name="versioncode" id="versioncode"  placeholder="请输入版本号">
					</div>
					<div class="form-group">
						<label for="appmessages"> 客户端信息:</label>
						<input type="text" class="form-control" name="appmessages" id="appmessages"  placeholder="请输入客户端信息">
					</div>
					<div class="form-group">
						<label for="filename"> 选择上传APP:</label>
						<input type="file" class="form-control" name="file" id="filename" >
					</div>
					<div class="form-group ">
						<button id="queryBtn"  class="btn btn-primary" onclick="upapp()">上传</button>
					</div>

				</form>

			</div>
		</div>
		<div class="modal fade" data-backdrop="static" tabindex="-1" id="progressbar">
			<!--窗口声明：-->
			<div class="modal-dialog modal-lg">
				<!-- 内容声明 -->
				<div class="modal-content">
					<!-- 主体 -->
					<div class="modal-body">
						<p>正在上传中,请耐心等待!</p>
						<div class="progress progress-striped active">
							<div id="pronum" class="progress-bar progress-bar-success" role="progressbar" style="width: 1%;">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel container" style="width: 100%">
			<div id="toolbar" class="btn-group">
				<button id="btn_delete" type="button" class="btn btn-danger" onclick="dltUpApp()">
					<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
				</button>
			</div>

			<table id="applist" data-search="true" style="font-size: 10px">
			</table>
		</div>

		<script>

			$(document).ready(function () {
				$('#applist').bootstrapTable({
					url: '<%=request.getContextPath() %>/pages/getappversion',  //请求后台的URL（*）
					method: 'post',   //请求方式（*）
					toolbar:'#toolbar',
					striped: true,   //是否显示行间隔色
					cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination: true,   //是否显示分页（*）
					sortable: true,   //是否启用排序
					sortOrder: "asc",   //排序方式
					sidePagination: "client",  //分页方式：client客户端分页，server服务端分页（*）
					pageNumber:1,   //初始化加载第一页，默认第一页
					pageSize: 50,   //每页的记录行数（*）
					pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
					strictSearch: true,
					clickToSelect: true,  //是否启用点击选中行
					showColumns:true,
					cardView: false,   //是否显示详细视图
					detailView: false,   //是否显示父子表
					showExport: true, //是否显示导出
					exportDataType: "all", //basic', 'all', 'selected'.
					columns: [{
						checkbox: true,align: 'center',
						valign: 'middle',
					}, {
						field: 'apptype',align: 'center',
						valign: 'middle',
						title: '客户端类型'
					},{
						field: 'versionCode',align: 'center',
						valign: 'middle',
						title: '客户端版本号'
					}, {
						field: 'versionName',
						align: 'center',
						valign: 'middle',
						title: '客户端名称'
					}, {
						field: 'appmessages',align: 'center',
						valign: 'middle',
						title: '版本信息'
					}, {
						field: 'gmtCreat',align: 'center',
						valign: 'middle',
						title: '上传时间'
					}, {
						field: 'appurl',align: 'center',
						valign: 'middle',
						title: '下载链接'
					}]
				});

			}).submit(function(){return false;});

		</script>
	</body>
</html>
