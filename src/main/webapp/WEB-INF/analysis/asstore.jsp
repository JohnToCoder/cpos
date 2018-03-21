<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/3/19
  Time: 下午4:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>门店销售统计</title>
	</head>
	<body>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">销售数据分析（门店）</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
					</button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div class="row">
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box">
							<span class="info-box-icon bg-aqua"><i class="fa fa-thumbs-o-up" style="margin-top: 15px"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">销售额最高的门店</span>
								<span id ="asstoretopname" class="info-box-text">南山茂业</span>
								<span id ="asstoretopnum" class="info-box-number">1,410</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box">
							<span class="info-box-icon bg-orange"><i class="fa fa-flag-o" style="margin-top: 15px"></i></span>

							<div class="info-box-content">
								<span class="info-box-text">销售额最低的门店</span>
								<span id = "asstorelastname" class="info-box-text"></span>
								<span id = "asstorelastnum" class="info-box-number">410</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box">
							<span class="info-box-icon bg-green"><i class="fa fa-paper-plane-o" style="margin-top: 15px"></i></span>

							<div class="info-box-content">
								<span class="info-box-text">销量增长最快门店</span>
								<span id ="asstoreinstop" class="info-box-text">东门天虹简逸</span>
								<span id ="asstoreinspertop" class="info-box-number">18%</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box">
							<span class="info-box-icon bg-red"><i class="fa fa-star-o" style="margin-top: 15px"></i></span>

							<div class="info-box-content">
								<span class="info-box-text">销量增长最低门店</span>
								<span id="asstoreinslast" class="info-box-text">龙华天虹</span>
								<span id="asstoreinsperlast" class="info-box-number">0</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
				</div>
				<div id="assalesstore" class="chart col-md-6" style="height:480px "></div>
			</div>
			<div class="box">
				<div class="box-header with-border">
					<h4 class="box-title">门店销售详情</h4>
					<div class="box-tools pull-right">
						<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
						</button>
						<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<table id="tabasstoredtl" style="font-size: 10px">
					</table>
				</div>
			</div>
		</div>
		<script>
            $(document).ready(function(){
                $('#tabasstoredtl').bootstrapTable({
                    striped: true,   //是否显示行间隔色
                    cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true,   //是否显示分页（*）
                    sortable: false,   //是否启用排序
                    strictSearch: true,
                    clickToSelect: true,  //是否启用点击选中行
                    showColumns:false,
                    height:260,
                    cardView: false,   //是否显示详细视图
                    detailView: false,   //是否显示父子表
                    showExport: false, //是否显示导出
                    exportDataType: "all", //basic', 'all', 'selected'.
                    columns: [{
                        field: 'asStore',align: 'center', valign: 'middle', title: '门店'
                    },{
                        field: 'asTotalPrice',align: 'center', valign: 'middle', title: '销售额'
                    },{
                        field: 'asTotalNum', align: 'center', valign: 'middle', title: '销售量'
                    },{
                        field: 'asTotalBill',align: 'center', valign: 'middle', title: '销售单数'
                    },{
                        field: 'asNumpercent',align: 'center', valign: 'middle', title: '销量占比'
                    },{
                        field: 'asPricepercent',align: 'center', valign: 'middle', title: '销售额占比'
                    }]
                });
                loadassalesstore();
                loadtabasstoredtl();
            });
		</script>
	</body>
</html>
