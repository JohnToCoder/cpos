<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/3/21
  Time: 下午3:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>款式销量门店分布对比</title>
	</head>
	<body>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">款式销量门店分布对比</h4>
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
						<button id="btnaddnewag" type="button" class="btn btn-info" style="height: 25px;padding: 2px" onclick="loadasstylestore()">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
						</button>
					</div>
					<div class="col-md-2 pull-right" >
						<select class="form-control select2 select2-hidden-accessible" style="width:100%; height: 25px;border-radius: 0px" tabindex="-1" aria-hidden="true"
						        id="slcasst" >
							<option value="all">--查询所有--</option>
							<option value="week">最近七天销量</option>
							<option value="moth">最近一个月销量</option>
						</select>
					</div>
				</div>
				<hr size="1" style="margin: 5px" color="#999999"/>
				<h4>销量最高 5 款门店分布</h4>
				<table id="tabasstylestoretop"  style="font-size: 10px">
				</table>
				<hr size="1" style="margin: 5px" color="#999999"/>
				<h4>销量最低 5 款门店分布</h4>
				<table id="tabasstylestorelast"  style="font-size: 10px">
				</table>
			</div>
		</div>
		<script>
            $(document).ready(function(){
                $('#tabasstylestoretop').bootstrapTable({
                    striped: true,   //是否显示行间隔色
                    cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: false,   //是否显示分页（*）
                    sortable: false,   //是否启用排序
                    strictSearch: true,
                    clickToSelect: true,  //是否启用点击选中行
                    showColumns:false,
                    height:198,
                    cardView: false,   //是否显示详细视图
                    detailView: false,   //是否显示父子表
                    showExport: false, //是否显示导出
                    showFooter: false,
                    exportDataType: "all", //basic', 'all', 'selected'.
                    columns: [{
                        field: 'style',align: 'center', valign: 'middle', title: '款号'
                    },{
                        field: 'totalsales',align: 'center', valign: 'middle', title: '总销量'
                    },{
                        field: 'topStoreName', align: 'center', valign: 'middle', title: '销量最高门店'
                    },{
                        field: 'topStoreSales',align: 'center', valign: 'middle', title: '销量'
                    },{
                        field: 'topStoreNum',align: 'center', valign: 'middle', title: '库存',
	                    formatter:function (value,row,index) {
                            return ['<span style="color:#04ae00">'+row.topStoreNum+'</span>',].join('');
                        }
                    },{
                        field: 'lastStoerName', align: 'center', valign: 'middle', title: '销量最低门店'
                    },{
                        field: 'lastStoreSales',align: 'center', valign: 'middle', title: '销量'
                    },{
                        field: 'lastStoreNum',align: 'center', valign: 'middle', title: '库存',
                        formatter:function (value,row,index) {
                            return ['<span style="color:#ffa200">'+row.lastStoreNum+'</span>',].join('');
                        }
                    }]
                });
                $('#tabasstylestorelast').bootstrapTable({
                    striped: true,   //是否显示行间隔色
                    cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: false,   //是否显示分页（*）
                    sortable: false,   //是否启用排序
                    strictSearch: true,
                    clickToSelect: true,  //是否启用点击选中行
                    showColumns:false,
                    height:198,
                    cardView: false,   //是否显示详细视图
                    detailView: false,   //是否显示父子表
                    showExport: false, //是否显示导出
                    showFooter: false,
                    exportDataType: "all", //basic', 'all', 'selected'.
                    columns: [{
                        field: 'style',align: 'center', valign: 'middle', title: '款号'
                    },{
                        field: 'totalsales',align: 'center', valign: 'middle', title: '总销量'
                    },{
                        field: 'topStoreName', align: 'center', valign: 'middle', title: '销量最高门店'
                    },{
                        field: 'topStoreSales',align: 'center', valign: 'middle', title: '销量'
                    },{
                        field: 'topStoreNum',align: 'center', valign: 'middle', title: '库存',
                        formatter:function (value,row,index) {
                            return ['<span style="color:#04ae00">'+row.topStoreNum+'</span>',].join('');
                        }
                    },{
                        field: 'lastStoerName', align: 'center', valign: 'middle', title: '销量最低门店'
                    },{
                        field: 'lastStoreSales',align: 'center', valign: 'middle', title: '销量'
                    },{
                        field: 'lastStoreNum',align: 'center', valign: 'middle', title: '库存',
                        formatter:function (value,row,index) {
                            return ['<span style="color:#ffa200">'+row.lastStoreNum+'</span>',].join('');
                        }
                    }]
                });

                loadasstylestore();
            });
		</script>
	</body>
</html>
