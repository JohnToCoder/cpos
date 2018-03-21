<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/3/19
  Time: 下午4:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>滞销款统计</title>
	</head>
	<body>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">销售数据分析（滞销款）</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
					</button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div id="asstylelast" class="chart col-md-6 col-xs-6" style="height:360px "></div>
			</div>

		</div>
		<script>
            $(document).ready(function(){
                loadasstylelast();
            });

		</script>
	</body>
</html>
