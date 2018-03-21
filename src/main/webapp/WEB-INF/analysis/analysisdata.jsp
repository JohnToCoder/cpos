<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/3/3
  Time: 上午9:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>试穿数据分析</title>
	</head>
	<body>
		<div class="box">
			<div class="box-body">
				<div class="row" style="margin-top: 10px">
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box bg-aqua">
							<span class="info-box-icon" ><i class="fa fa-bookmark-o" style="margin-top: 15px"></i></span>

							<div class="info-box-content">
								<span class="info-box-text">Bookmarks</span>
								<span class="info-box-number">41,410</span>

								<div class="progress">
									<div class="progress-bar" style="width: 70%"></div>
								</div>
								<span class="progress-description">70% Increase in 30 Days</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box bg-green">
							<span class="info-box-icon"><i class="fa fa-thumbs-o-up" style="margin-top: 15px"></i></span>

							<div class="info-box-content">
								<span class="info-box-text">Likes</span>
								<span class="info-box-number">41,410</span>

								<div class="progress">
									<div class="progress-bar" style="width: 70%"></div>
								</div>
								<span class="progress-description">70% Increase in 30 Days</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box bg-yellow">
							<span class="info-box-icon"><i class="fa fa-calendar" style="margin-top: 15px"></i></span>

							<div class="info-box-content">
								<span class="info-box-text">Events</span>
								<span class="info-box-number">41,410</span>

								<div class="progress">
									<div class="progress-bar" style="width: 70%"></div>
								</div>
								<span class="progress-description">70% Increase in 30 Days</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box bg-red">
							<span class="info-box-icon"><i class="fa fa-comments-o" style="margin-top: 15px"></i></span>

							<div class="info-box-content">
								<span class="info-box-text">Comments</span>
								<span class="info-box-number">41,410</span>

								<div class="progress">
									<div class="progress-bar" style="width: 70%"></div>
								</div>
								<span class="progress-description">70% Increase in 30 Days</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
				</div>
			</div>
		</div>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">试穿数据统计</h4>
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

				<table id="tabtrydata"  style="font-size: 10px">
				</table>
			</div>

		</div>
		<script>
			$(document).ready(function () {
                $('#tabtrydata').bootstrapTable({
                    striped: true,   //是否显示行间隔色
                    cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true,   //是否显示分页（*）
                    sortable: true,   //是否启用排序
                    sortOrder: "asc",   //排序方式
                    sidePagination: "client",  //分页方式：client客户端分页，server服务端分页（*）
                    pageNumber:1,   //初始化加载第一页，默认第一页
                    pageSize: 10,   //每页的记录行数（*）
                    pageList: [15, 25, 50, 100], //可供选择的每页的行数（*）
                    height:380,
                    strictSearch: true,
                    clickToSelect: true,  //是否启用点击选中行
                    showColumns:false,
	                showFooter:true,
                    cardView: false,   //是否显示详细视图
                    detailView: false,   //是否显示父子表
                    showExport: false, //是否显示导出
                    exportDataType: "all", //basic', 'all', 'selected'.
                    columns: [{
                        checkbox: true,align: 'center', valign: 'middle',
                    },{
                        field: 'storeCode',align: 'center', valign: 'middle', title: '门店'
                    },{
                        field: 'ant',align: 'center', valign: 'middle', title: '位置'
                    },{
                        field: 'sku', align: 'center', valign: 'middle', title: '货品款号'
                    },{
                        field: 'color',align: 'center', valign: 'middle', title: '颜色'
                    },{
                        field: 'size',align: 'center', valign: 'middle', title: '尺码'
                    },{
                        field: 'proName',align: 'center', valign: 'middle', title: '名称'
                    },{
                        field: 'difftime',align: 'center', valign: 'middle', title: '试穿时长'
                    },{
                        field:'readTime',align:'center',valign:'middle',title:'试穿开始'
                    },{
                        field:'gmtCreat',align:'center',valign:'middle',title:'试穿结束'
                    }]
                });
                loadtabtrydata();
                doanalysisdata();
               // setInterval(doanalysisdata,20000);

            });
		</script>
	</body>
</html>
