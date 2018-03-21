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
		<title>试穿结果</title>
	</head>
	<body>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">试穿数据分析</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus" ></i>
					</button>
					<div class="btn-group">
						<button type="button" class="btn btn-box-tool dropdown-toggle" data-toggle="dropdown">
							<i class="fa fa-wrench"></i></button>
					</div>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div class="row">
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box">
							<span class="info-box-icon bg-aqua"><i class="fa fa-envelope-o" style="margin-top: 15px"></i></span>

							<div class="info-box-content">
								<span class="info-box-text">试穿次数最多款</span>
								<span id="atstylemax" class="info-box-text" style="font-size: 30px"></span>
								<span id="atstylemaxnum" class="info-box-number">1,410</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box">
							<span class="info-box-icon bg-green"><i class="fa fa-flag-o" style="margin-top: 15px"></i></span>

							<div class="info-box-content">
								<span class="info-box-text">试穿次数最少款</span>
								<span id="atstylemin" class="info-box-text" style="font-size: 30px"></span>
								<span id="atstyleminnum" class="info-box-number">1,410</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box">
							<span class="info-box-icon bg-yellow"><i class="fa fa-files-o" style="margin-top: 15px"></i></span>

							<div class="info-box-content">
								<span class="info-box-text">试穿平均时间最长款</span>
								<span id="attimemax" class="info-box-text" style="font-size: 30px"></span>
								<span id="attimemaxavg" class="info-box-number">1,410</span>
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
								<span class="info-box-text">试穿平均时间最短款</span>
								<span id="attimemin" class="info-box-text" style="font-size: 30px"></span>
								<span id="attimeminavg" class="info-box-number">1,410</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					<!-- /.col -->
				</div>
				<div id="trysalesanalysis" class="chart col-md-6" style="height:480px "></div>

			</div>

		</div>

		<script>
            $(document).ready(function(){
                loadastrysales();

            });
		</script>
	</body>
</html>
