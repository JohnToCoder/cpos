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
		<title>销售数据分析</title>
	</head>
	<body>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">销售统计</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
					</button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div class="row">
					<div class="btn-group pull-right">
						<button type="button" class="btn btn-default">月度</button>
						<button type="button" class="btn btn-default">季度</button>
						<button type="button" class="btn btn-default">年度</button>
					</div>
				</div>
				<div class="row" style="margin-top: 10px">
					<div class="col-lg-3 col-xs-6">
						<!-- small box -->
						<div class="small-box bg-aqua">
							<div class="inner">
								<sup style="font-size: 20px">￥</sup><span id="astotalprice" style="font-size: 35px;margin-left: 3px">41410</span>
								<p>销售总额</p>
							</div>
							<div class="icon">
								<i class="ion ion-bag" style="margin-top: 15px"></i>
							</div>
							<a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
						</div>
					</div>
					<!-- ./col -->
					<div class="col-lg-3 col-xs-6">
						<!-- small box -->
						<div class="small-box bg-green">
							<div class="inner">
								<span id="astotalnum" style="font-size: 35px">53</span><sup style="font-size: 16px;margin-left: 3px">件</sup>
								<p>销售总数</p>
							</div>
							<div class="icon">
								<i class="ion ion-stats-bars" style="margin-top: 15px"></i>
							</div>
							<a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
						</div>
					</div>
					<!-- ./col -->
					<div class="col-lg-3 col-xs-6">
						<!-- small box -->
						<div class="small-box bg-blue-gradient">
							<div class="inner">
								<span id="astotalbill" style="font-size: 35px">41410</span><sup style="font-size: 16px;margin-left: 3px">单</sup>
								<p>销售总单数</p>
							</div>
							<div class="icon">
								<i class="ion ion-person-add" style="margin-top: 15px"></i>
							</div>
							<a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
						</div>
					</div>
					<!-- ./col -->
					<div class="col-lg-3 col-xs-6">
						<!-- small box -->
						<div class="small-box bg-red">
							<div class="inner">
								<span id="asprocess" style="font-size: 35px">65</span><sup style="font-size: 16px;margin-left: 3px">%</sup>
								<p>销售任务完成度</p>
							</div>
							<div class="icon">
								<i class="ion ion-pie-graph" style="margin-top: 15px"></i>
							</div>
							<a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
						</div>
					</div>
					<!-- ./col -->
				</div>
				<hr size="1" style="margin: 5px" color="#999999"/>
				<div class="row">
					<div class="col-lg-8 col-md-8">
						<div id="assales" class="chart col-lg-8 clo-xs-8" style="height:360px "></div>
					</div>
					<div class="col-lg-4 col-md-4">
						<div id="assalespie" class="chart col-lg-8 clo-xs-8" style="height:360px "></div>
					</div>
				</div>
			</div>
		</div>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">月度销量详情</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
					</button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<table id="tabassalesmoth" style="font-size: 10px">
				</table>
			</div>
		</div>
		<script>
            $(document).ready(function(){
                $('#tabassalesmoth').bootstrapTable({
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
                        field: 'asMoth',align: 'center', valign: 'middle', title: '月份'
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
                loadassalestotal();
                loadassales();
                loadassalespie();
            });
		</script>
	</body>
</html>
