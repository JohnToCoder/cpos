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
				<h4 class="box-title">销售数据分析</h4>
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
				<div id="main" class="chart col-md-6" style="height:480px "></div>
			</div>

		</div>

		<script>
			$(document).ready(function(){
				var myChart = echarts.init(document.getElementById('main'),'macarons');

               var option = {
                    title : {
                        'text':'各款销售数量/销售额',
                        'subtext':'销售门店：南山茂业'
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['销量','销售额']
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
                            type : 'category',
                            data:[
                                '9D615', '9D911', '9I211', '9I214', '9I503', '9J104', '9J402', '9J404',
                                '9L214', '9UK20','9XY03','9YD07','9YR07','9L6101'
                            ]
                        }
                    ],
                    yAxis : [

                        {
                            type : 'value',
                            name : '金额',
                            axisLabel : {
                                formatter: '{value} ￥'
                            }
                        },
                        {
                            type : 'value',
                            name : '数量',
                            axisLabel : {
                                formatter: '{value} '
                            }
                        }
                    ],
                    series : [
                        {
                            name:'销量',
                            type:'line',
                            yAxisIndex: 1,
                            data:[28, 23, 58, 70, 33, 65,78, 64, 88, 45,34,25,36],
                            markPoint : {
                                data : [
                                    {type : 'max', name: '最大值'},
                                    {type : 'min', name: '最小值'}
                                ]
                            },
                            markLine : {
                                data : [
                                    {type : 'average', name: '平均值'}
                                ]
                            }
                        },
                        {
                            name:'销售额',
                            type:'bar',
                            barWidth : 16,
                            data:[8868, 7680, 7380, 8870, 9800, 11200,8750, 9860, 11008, 8790,6400,5200,7200],
                            markPoint : {
                                data : [
                                    {type : 'max', name: '最大值'},
                                    {type : 'min', name: '最小值'}
                                ]
                            },
                            markLine : {
                                data : [
                                    {type : 'average', name : '平均值'}
                                ]
                            }
                        }
                    ]
                };
                myChart.setOption(option);
            });
		</script>
	</body>
</html>
