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
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">${emp.storeName}: 销售统计</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
					</button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">

				<div class="row" style="margin-top: 10px">
					<div class="col-lg-3 col-xs-6">
				<!-- small box -->
				<div class="small-box bg-aqua">
					<div class="inner">
						<sup style="font-size: 20px">￥</sup><span id="sstotalprice" style="font-size: 35px;margin-left: 3px">41410</span>
						<p>当月销售总额</p>
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
						<span id="sstotalnum" style="font-size: 35px">53</span><sup style="font-size: 16px;margin-left: 3px">件</sup>
						<p>当月销售总数</p>
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
				<div class="small-box bg-yellow">
					<div class="inner">
						<span id="sstotalbill" style="font-size: 35px">41410</span><sup style="font-size: 16px;margin-left: 3px">单</sup>
						<p>当月销售总单数</p>
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
						<span id="ssprocess" style="font-size: 35px">65</span><sup style="font-size: 16px;margin-left: 3px">%</sup>
						<p>当月销售任务完成度</p>
					</div>
					<div class="icon">
						<i class="ion ion-pie-graph" style="margin-top: 15px"></i>
					</div>
					<a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
				</div>
			</div>
					<!-- ./col -->
				</div>
			</div>
		</div>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">销售单详情</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
					</button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div id="sbtoolbar" class="tool-bar">
					<form class="form-inline" role="form" style="float: left; width: 100%" method="post" id="querygdscForm">
						<div id="querygdscTip" class="alert-warning" ></div>
						<div class="form-group">
							<label for="txbsalesbn" >  销售单号:</label>
							<input type="text" class="form-control" style="height: 25px" name="txbsalesbn" id="txbsalesbn"  placeholder="请输入发货单号">
						</div>
						<div class="form-group">
							<label >  销售日期:</label>
							<div class="input-group date">
								<div class="input-group-addon" style="height: 25px;padding: 2px">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" class="form-control pull-right" style="height: 25px" id="sbnstartdt" data-toggle="tooltip" data-placement="bottom" title="">
							</div>
							<label >--</label>
							<div class="input-group date">
								<div class="input-group-addon" style="height: 25px;padding: 2px">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" class="form-control pull-right" style="height: 25px" id="sbnenddt" data-toggle="tooltip" data-placement="bottom" title="">
							</div>
						</div>
						<div class="form-group ">
							<button type="button" id="queryBtn" onclick="doQuerySalesBills();" class="btn btn-primary" style="height: 25px;padding: 2px 6px 2px 6px">查询</button>
						</div>
					</form>
				</div>
				<table id="tabsalesbill" data-search="true" style="font-size: 10px">
				</table>
			</div>
		</div>
		<script>
			$(document).ready(function () {
                $('#sbnstartdt').datepicker({ format: "yyyy-mm-dd",autoclose:true });
                $('#sbnenddt').datepicker({ format: "yyyy-mm-dd",autoclose:true });
                $('#tabsalesbill').bootstrapTable({
                    toolbar:'#sbtoolbar',
                    striped: true,   //是否显示行间隔色
                    cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true,   //是否显示分页（*）
                    sortable: true,   //是否启用排序
                    sortOrder: "asc",   //排序方式
                    sidePagination: "client",  //分页方式：client客户端分页，server服务端分页（*）
                    pageNumber:1,   //初始化加载第一页，默认第一页
                    pageSize: 10,   //每页的记录行数（*）
                    pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                    height:480,
                    strictSearch: true,
                    clickToSelect: true,  //是否启用点击选中行
                    showColumns:true,
                    cardView: false,   //是否显示详细视图
                    detailView: true,   //是否显示父子表
                    showExport: true, //是否显示导出
                    exportDataType: "all", //basic', 'all', 'selected'.
                    columns: [{
                        checkbox: true,align: 'center', valign: 'middle',
                    },{
                        field: 'salesBillNo',align: 'center', valign: 'middle', title: '销售单号'
                    },{
                        field: 'totalPrice',align: 'center', valign: 'middle', title: '销售单总价'
                    },{
                        field: 'totalNum', align: 'center', valign: 'middle', title: '销售单数量'
                    },{
                        field: 'empCode',align: 'center', valign: 'middle', title: '售货员工号'
                    },{
                        field: 'empName',align: 'center', valign: 'middle', title: '售货员'
                    },{
                        field: 'gmtCreat',align: 'center', valign: 'middle', title: '销售时间'
                    }],
                    onExpandRow: function (index, row, $detail) {
                        oInit.InitSubTableS(index, row, $detail);
                    }
                });
                loadstoresalesinfo();
                loadtabsalesbills();
                var oInit = new Object();
                oInit.InitSubTableS=function(index,row,$detail){
                    var parentid = row.id;
                    var cur_table = $detail.html('<table style="font-size: 10px"></table>').find('table');
                    $(cur_table).bootstrapTable({
                        clickToSelect: true,
                        pageSize: 10,
                        pageList: [10, 25],
                        columns: [{
                            field: 'salesBillNo',align: 'center', valign: 'middle', title: '销售单号'
                        },{
                            field: 'sku',align: 'center', valign: 'middle', title: '货号'
                        },{
                            field: 'size',align: 'center', valign: 'middle', title: '尺码'
                        },{
                            field: 'color',align: 'center', valign: 'middle', title: '颜色'
                        },{
                            field: 'proName',align: 'center', valign: 'middle', title: '名称'
                        },{
                            field: 'curPrice',align: 'center', valign: 'middle', title: '价格'
                        }]
                    });
                    $.ajax({
                        type:'post',
                        url:'sales/getsalesdtl?billno='+row.salesBillNo,
                        success:function(result){
                            //alert(result);
                            var jsoOrder = JSON.parse(result);
                            $(cur_table).bootstrapTable("load", jsoOrder.data);
                        }
                    });
                }
            });
		</script>
	</body>
</html>
