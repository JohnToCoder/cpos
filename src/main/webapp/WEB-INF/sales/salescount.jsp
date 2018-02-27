<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/2/26
  Time: 下午3:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>销售统计</title>
	</head>
	<body>
		<div class="row" style="margin-top: 10px">
			<div class="col-lg-3 col-xs-6">
				<!-- small box -->
				<div class="small-box bg-aqua">
					<div class="inner">
						<h3>150</h3>

						<p>New Orders</p>
					</div>
					<div class="icon">
						<i class="ion ion-bag"></i>
					</div>
					<a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
				</div>
			</div>
			<!-- ./col -->
			<div class="col-lg-3 col-xs-6">
				<!-- small box -->
				<div class="small-box bg-green">
					<div class="inner">
						<h3>53<sup style="font-size: 20px">%</sup></h3>

						<p>Bounce Rate</p>
					</div>
					<div class="icon">
						<i class="ion ion-stats-bars"></i>
					</div>
					<a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
				</div>
			</div>
			<!-- ./col -->
			<div class="col-lg-3 col-xs-6">
				<!-- small box -->
				<div class="small-box bg-yellow">
					<div class="inner">
						<h3>44</h3>

						<p>User Registrations</p>
					</div>
					<div class="icon">
						<i class="ion ion-person-add"></i>
					</div>
					<a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
				</div>
			</div>
			<!-- ./col -->
			<div class="col-lg-3 col-xs-6">
				<!-- small box -->
				<div class="small-box bg-red">
					<div class="inner">
						<h3>65</h3>

						<p>Unique Visitors</p>
					</div>
					<div class="icon">
						<i class="ion ion-pie-graph"></i>
					</div>
					<a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
				</div>
			</div>
			<!-- ./col -->
		</div>
		<div class="row">
			<div class="box box-solid bg-teal-gradient">
				<div class="box-header">
					<i class="fa fa-th"></i>

					<h3 class="box-title">Sales Graph</h3>

					<div class="box-tools pull-right">
						<button type="button" class="btn bg-teal btn-sm" data-widget="collapse"><i class="fa fa-minus"></i>
						</button>
						<button type="button" class="btn bg-teal btn-sm" data-widget="remove"><i class="fa fa-times"></i>
						</button>
					</div>
				</div>
				<div class="box-body border-radius-none">
					<div class="chart" id="line-chart" style="height: 250px;"></div>
				</div>
				<!-- /.box-body -->
				<div class="box-footer no-border">
					<div class="row">
						<div class="col-xs-4 text-center" style="border-right: 1px solid #f4f4f4">
							<input type="text" class="knob" data-readonly="true" value="20" data-width="60" data-height="60" data-fgColor="#39CCCC">

							<div class="knob-label">销售率</div>
						</div>
						<!-- ./col -->
						<div class="col-xs-4 text-center" style="border-right: 1px solid #f4f4f4">
							<input type="text" class="knob" data-readonly="true" value="50" data-width="60" data-height="60" data-fgColor="#39CCCC">

							<div class="knob-label">调货率</div>
						</div>
						<!-- ./col -->
						<div class="col-xs-4 text-center">
							<input id="knob3" type="text" class="knob" data-readonly="true" value="30" data-width="60" data-height="60" data-fgColor="#39CCCC">

							<div class="knob-label">库存率</div>
						</div>
						<!-- ./col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.box-footer -->
			</div>
		</div>
		<script>
			$(function(){
			    document.getElementById("knob3").value = 90;
                $(".knob").knob();
            var line = new Morris.Line({
                element: 'line-chart',
                resize: true,
                data: [
                    {y: '2018 Q1', item1: 2666},
                    {y: '2018 Q2', item1: 2778},
                    {y: '2018 Q3', item1: 4912},
                    {y: '2018 Q4', item1: 3767},
                    {y: '2018 Q5', item1: 6810},
                    {y: '2018 Q6', item1: 5670},
                    {y: '2018 Q7', item1: 4820},
                    {y: '2018 Q8', item1: 15073},
                    {y: '2018 Q9', item1: 10687},
                    {y: '2018 Q10', item1: 8432}
                ],
                xkey: 'y',
                ykeys: ['item1'],
                labels: ['Item 1'],
                lineColors: ['#efefef'],
                lineWidth: 2,
                hideHover: 'auto',
                gridTextColor: "#fff",
                gridStrokeWidth: 0.4,
                pointSize: 4,
                pointStrokeColors: ["#efefef"],
                gridLineColor: "#efefef",
                gridTextFamily: "Open Sans",
                gridTextSize: 10
            });
            });
		</script>
	</body>
</html>
