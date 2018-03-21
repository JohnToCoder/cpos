<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/3/21
  Time: 上午10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>款式销售情况统计</title>
	</head>
	<body>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">销售数据分析(款式）</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
					</button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div class="row">
					<div class=" pull-right">
						<button id="btnaddnewag" type="button" class="btn btn-info" style="height: 25px;padding: 2px" onclick="loadassalesstyle()">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
						</button>
					</div>
					<div class="col-md-2 pull-right">
						<select class="form-control select2 select2-hidden-accessible" style="width:100%; height: 25px;border-radius: 0px" tabindex="-1" aria-hidden="true"
						        id="slcasstylestore"></select>

					</div>
					<div class="col-md-2 pull-right" >
						<select class="form-control select2 select2-hidden-accessible" style="width:100%; height: 25px;border-radius: 0px" tabindex="-1" aria-hidden="true"
						        id="slcasstyletime" >
								<option value="all">--查询所有--</option>
								<option value="week">最近七天销量</option>
								<option value="moth">最近一个月销量</option>
							</select>
					</div>
				</div>
				<hr size="1" style="margin: 5px" color="#999999"/>
				<div class="row">
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box">
							<span class="info-box-icon bg-aqua"><i class="fa fa-thumbs-o-up" style="margin-top: 15px"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">销量最高的款式</span>
								<span id ="asstyletopname" class="info-box-text">9YD01</span>
								<span id ="asstyletopnum" class="info-box-number">1,410</span>
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
								<span class="info-box-text">销量最低的款式</span>
								<span id = "asstylelastname" class="info-box-text">9YD01</span>
								<span id = "asstylelastnum" class="info-box-number">410</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
				</div>
				<div id="assalesstyle" class="chart col-md-6" style="height:480px "></div>
			</div>

		</div>
		<script>
            $(document).ready(function(){
                loadslcasstylestore();
                loadassalesstyle();
            });
		</script>
	</body>
</html>
