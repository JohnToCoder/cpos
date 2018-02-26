<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/2/22
  Time: 上午9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>首页</title>
	</head>
	<body>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">月度产量报表</h4>

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
				<div class="row">
					<div class="col-md-8">
						<p class="text-center">
							<strong>产量: 1 Jan, 2017 - 30 Dec, 2017</strong>
						</p>

						<div class="chart">
							<!-- Sales Chart Canvas -->
							<canvas id="salesChart" style="height: 180px;"></canvas>
						</div>
						<!-- /.chart-responsive -->
					</div>
					<!-- /.col -->
					<div class="col-md-4">
						<p class="text-center">
							<strong>目标完成度</strong>
						</p>

						<div class="progress-group">
							<span class="progress-text">Add Products to Cart</span>
							<span class="progress-number"><b>160</b>/200</span>

							<div class="progress sm">
								<div class="progress-bar progress-bar-aqua" style="width: 60%"></div>
							</div>
						</div>
						<!-- /.progress-group -->
						<div class="progress-group">
							<span class="progress-text">Complete Purchase</span>
							<span class="progress-number"><b>310</b>/400</span>

							<div class="progress sm">
								<div class="progress-bar progress-bar-red" style="width: 55%"></div>
							</div>
						</div>
						<!-- /.progress-group -->
						<div class="progress-group">
							<span class="progress-text">Visit Premium Page</span>
							<span class="progress-number"><b>480</b>/800</span>

							<div class="progress sm">
								<div class="progress-bar progress-bar-green" style="width: 80%"></div>
							</div>
						</div>
						<!-- /.progress-group -->
						<div class="progress-group">
							<span class="progress-text">Send Inquiries</span>
							<span class="progress-number"><b>250</b>/500</span>

							<div class="progress sm">
								<div class="progress-bar progress-bar-yellow" style="width: 90%"></div>
							</div>
						</div>
						<!-- /.progress-group -->
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- ./box-body -->
			<div class="box-footer">
				<div class="row">
					<div class="col-sm-3 col-xs-6">
						<div class="description-block border-right">
							<span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 17%</span>
							<h5 class="description-header">$35,210.43</h5>
							<span class="description-text">TOTAL REVENUE</span>
						</div>
						<!-- /.description-block -->
					</div>
					<!-- /.col -->
					<div class="col-sm-3 col-xs-6">
						<div class="description-block border-right">
							<span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 0%</span>
							<h5 class="description-header">$10,390.90</h5>
							<span class="description-text">TOTAL COST</span>
						</div>
						<!-- /.description-block -->
					</div>
					<!-- /.col -->
					<div class="col-sm-3 col-xs-6">
						<div class="description-block border-right">
							<span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 20%</span>
							<h5 class="description-header">$24,813.53</h5>
							<span class="description-text">TOTAL PROFIT</span>
						</div>
						<!-- /.description-block -->
					</div>
					<!-- /.col -->
					<div class="col-sm-3 col-xs-6">
						<div class="description-block">
							<span class="description-percentage text-red"><i class="fa fa-caret-down"></i> 18%</span>
							<h5 class="description-header">1200</h5>
							<span class="description-text">GOAL COMPLETIONS</span>
						</div>
						<!-- /.description-block -->
					</div>
				</div>
				<!-- /.row -->
			</div>
			<!-- /.box-footer -->
		</div>
		<script>
            $(document).ready(function(){
                // Get context with jQuery - using jQuery's .get() method.
                var salesChartCanvas = $("#salesChart").get(0).getContext("2d");
                // This will get the first returned node in the jQuery collection.
                var salesChart = new Chart(salesChartCanvas);

                var salesChartData = {
                    labels: ["一月", "二月", "三月", "四月", "五月", "六月", "七月","八月","九月","十月","十一月","十二月"],
                    datasets: [
                        {
                            label: "Electronics",
                            fillColor : "rgba(220,220,220,0.5)",
                            strokeColor : "rgba(220,220,220,1)",
                            pointColor : "rgba(220,220,220,1)",
                            pointStrokeColor : "#fff",
                            pointHighlightFill: "#fff",
                            pointHighlightStroke: "rgb(220,220,220)",
                            data: [65, 59, 80, 81, 56, 55, 40,64,0,0,0,0]
                        },
                        {
                            label: "Digital Goods",
                            fillColor : "rgba(151,187,205,0.5)",
                            strokeColor : "rgba(151,187,205,1)",
                            pointColor : "rgba(151,187,205,1)",
                            pointStrokeColor : "#fff",
                            pointHighlightFill: "#fff",
                            pointHighlightStroke: "rgba(60,141,188,1)",
                            data: [28, 48, 40, 19, 86, 27, 90,80,0,0,0,0]
                        }
                    ]
                };

                var salesChartOptions = {
                    //Boolean - If we should show the scale at all
                    showScale: true,
                    //Boolean - Whether grid lines are shown across the chart
                    scaleShowGridLines: true,
                    //String - Colour of the grid lines
                    scaleGridLineColor: "rgba(0,0,0,.1)",
                    //Number - Width of the grid lines
                    scaleGridLineWidth: 1,
                    //Boolean - Whether to show horizontal lines (except X axis)
                    scaleShowHorizontalLines: true,
                    //Boolean - Whether to show vertical lines (except Y axis)
                    scaleShowVerticalLines: true,
                    //Boolean - Whether the line is curved between points
                    bezierCurve: true,
                    //Number - Tension of the bezier curve between points
                    bezierCurveTension: 0.3,
                    //Boolean - Whether to show a dot for each point
                    pointDot: false,
                    //Number - Radius of each point dot in pixels
                    pointDotRadius: 4,
                    //Number - Pixel width of point dot stroke
                    pointDotStrokeWidth: 1,
                    //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
                    pointHitDetectionRadius: 20,
                    //Boolean - Whether to show a stroke for datasets
                    datasetStroke: true,
                    //Number - Pixel width of dataset stroke
                    datasetStrokeWidth: 2,
                    //Boolean - Whether to fill the dataset with a color
                    datasetFill: true,
                    //String - A legend template
                    //Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
                    maintainAspectRatio: true,
                    //Boolean - whether to make the chart responsive to window resizing
                    responsive: true
                };

                //Create the line chart
                salesChart.Line(salesChartData, salesChartOptions);

                //---------------------------
                //- END MONTHLY SALES CHART -
                //---------------------------
            });
		</script>
	</body>
</html>
