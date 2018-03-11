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
				<div id="tryanalysis" class="chart col-md-6" style="height:480px "></div>
			</div>

		</div>

		<script>
            $(document).ready(function(){
                var myChart = echarts.init(document.getElementById('tryanalysis'),'macarons');
                var option = {
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    legend: {
                        data:['试穿次数(次)', '试穿时长(秒)']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    xAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    yAxis : [
                        {
                            type : 'category',
                            axisTick : {show: false},
                            data : ['9D615', '9D911', '9I211', '9I214', '9I503', '9J104', '9J402', '9J404',
                                '9L214', '9UK20']
                        }
                    ],
                    series : [
	                    {
                            name:'试穿次数(次)',
                            type:'bar',
                            stack: '总量',
                            barWidth : 8,
                            itemStyle: {normal: {
                                    label : {show: true}
                                }},
                            data:[320, 302, 341, 374, 390, 450, 420,57,22,24,12]
                        },
                        {
                            name:'试穿时长(秒)',
                            type:'bar',
                            stack: '总量',
                            itemStyle: {normal: {
                                    label : {show: true, position: 'left'}
                                }},
                            data:[-120, -132, -101, -134, -190, -230, -210,-180,-32,-22,-21]
                        }
                    ]
                };

                myChart.setOption(option);
            });
		</script>
	</body>
</html>
