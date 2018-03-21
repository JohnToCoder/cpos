<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/3/20
  Time: 下午6:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>时间段内试穿流量</title>
	</head>
	<body>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">时间段内试穿流量统计</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
					</button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div class="row">
					<div id="attimes" class="chart col-lg-8 clo-xs-8" style="height:480px "></div>
				</div>
			</div>
		</div>
		<script>
            $(document).ready(function(){
                loadattimes();

            });
		</script>
	</body>
</html>
