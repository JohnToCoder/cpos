<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/2/24
  Time: 下午10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>盘点</title>
	</head>
	<body>

		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">门店盘点单详情</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
					</button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div id="sctoolbar" class="tool-bar">
					<form class="form-inline" role="form" style="float: left; width: 100%" method="post" id="querygdscForm">
						<div id="querygdscTip" class="alert-warning" ></div>
						<div class="form-group">
							<label for="txbsccode" >  盘点单号:</label>
							<input type="text" class="form-control" style="height: 25px" name="txbwibill" id="txbsccode"  placeholder="请输入发货单号">
						</div>
						<div class="form-group">
							<label >  盘点日期:</label>
							<div class="input-group date">
								<div class="input-group-addon" style="height: 25px;padding: 2px">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" class="form-control pull-right" style="height: 25px" id="scstartdt">
							</div>
							<label >--</label>
							<div class="input-group date">
								<div class="input-group-addon" style="height: 25px;padding: 2px">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" class="form-control pull-right" style="height: 25px" id="scenddt">
							</div>
						</div>
						<div class="form-group ">
							<button type="button" id="queryBtn" onclick="doQueryStoreCheck();" class="btn btn-primary" style="height: 25px;padding: 2px 6px 2px 6px">查询</button>
						</div>
					</form>
				</div>
				<table id="tabstorecheck" data-search="true" style="font-size: 10px">
				</table>
			</div>
		</div>
		<script>
            $(document).ready(function(){
                $('#scstartdt').datepicker({ format: "yyyy-mm-dd",autoclose:true });
                $('#scenddt').datepicker({ format: "yyyy-mm-dd",autoclose:true });

                $('#tabstorecheck').bootstrapTable({
                    toolbar:'#sctoolbar',
                    striped: true,   //是否显示行间隔色
                    cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true,   //是否显示分页（*）
                    sortable: true,   //是否启用排序
                    sortOrder: "asc",   //排序方式
                    sidePagination: "client",  //分页方式：client客户端分页，server服务端分页（*）
                    pageNumber:1,   //初始化加载第一页，默认第一页
                    pageSize: 15,   //每页的记录行数（*）
                    pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                    height:600,
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
                        field: 'shopCode',align: 'center', valign: 'middle', title: '盘点门店'
                    },{
                        field: 'scbCode',align: 'center', valign: 'middle', title: '盘点单号'
                    },{
                        field: 'scbType', align: 'center', valign: 'middle', title: '盘点类型',
                        formatter:function (value,row,index) {
                            if(row.scbType =='1'){
                                return ['<span class="label label-info">全盘</span>',].join('');
                            }else{
                                return ['<span class="label label-success">抽盘</span>',].join('');
                            }
                        }
                    },{
                        field: 'storeCount',align: 'center', valign: 'middle', title: '计划盘点数'
                    },{
                        field: 'scbCount',align: 'center', valign: 'middle', title: '实际盘点数'
                    },{
                        field: 'scbDiff',align: 'center', valign: 'middle', title: '盘点差异数'
                    },{
                        field: 'scbDes',align: 'center', valign: 'middle', title: '备注信息'
                    },{
                        field: 'gmtCreat',align: 'center', valign: 'middle', title: '盘点建单时间'
                    },{
                        field: 'gmtModify',align: 'center', valign: 'middle', title: '盘点完成时间'
                    }],
                    onExpandRow: function (index, row, $detail) {
                        oInit.InitSubTableCheck(index, row, $detail);
                    }
                });
                doQueryStoreCheck();
                var oInit = new Object();
                oInit.InitSubTableCheck=function(index,row,$detail){
                    var cur_table = $detail.html('<table style="font-size: 8px"></table>').find('table');
                    $(cur_table).bootstrapTable({
                        clickToSelect: true,
                        pageSize: 10,
                        pageList: [10, 25],
                        columns: [{
                            checkbox: true,align: 'center', valign: 'middle',
                        },{
                            field: 'scbCode',align: 'center', valign: 'middle', title: '盘点单号'
                        },{
                            field: 'sku',align: 'center', valign: 'middle', title: '货号'
                        },{
                            field: 'size',align: 'center', valign: 'middle', title: '尺码'
                        },{
                            field: 'epc',align: 'center', valign: 'middle', title: 'EPC'
                        }]
                    });
                    $.ajax({
                        type:'post',
                        url:'goods/gettabcheckdtl?scbcode='+row.scbCode,
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
