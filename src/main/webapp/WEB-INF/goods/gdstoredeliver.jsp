<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/2/3
  Time: 下午3:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>门店转货</title>
	</head>
	<body>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">新建调货请求单</h4>
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
				<div class="row ">

					<div class="col-md-1 "><label>调货门店</label></div>
					<div class="col-md-3 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-institution"> </i></span>
							<select class="form-control select2 select2-hidden-accessible" style="width:100%; height: 25px;border-radius: 0px" tabindex="-1" aria-hidden="true"
							        id="slcdeliverstorecode" data-toggle="tooltip" data-placement="bottom" title=""></select>
						</div>
					</div>
					<div class="col-md-1 "><label>调货方式</label></div>
					<div class="col-md-3 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-retweet"> </i></span>
							<select class="form-control select2 select2-hidden-accessible" style="width:100%; height: 25px;border-radius: 0px" tabindex="-1" aria-hidden="true"
							        id="slcdelivermethod" data-toggle="tooltip" data-placement="bottom" title=""></select>
						</div>
					</div>
					<div class="col-md-1 "><label>配送总数</label></div>
					<div class="col-md-3 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-cc"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="配送总数" id="txbdelivercount" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
				</div>
				<hr size="1" style="margin: 5px" color="#999999"/>
				<div id="tipdeliver" class="alert-warning" ></div>
				<table class="table table-striped table-bordered table-hover" style="font-size: 10px" id="newdeliver">
					<thead>
						<tr bgcolor="#8DB6CD">
							<th style="text-align:center" width="20%">货品标识码</th>
							<th style="text-align:center" width="15%">款号</th>
							<th style="text-align:center" width="15%">颜色</th>
							<th style="text-align:center" width="15%">配码</th>
							<th style="text-align:center" width="15%">配码数量</th>
							<th style="text-align:center" width="10%">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td style="text-align:center; " onclick="tdclickd(this,'1')"></td>
							<td style="text-align:center; " onclick="tdclickd(this,'1')"></td>
							<td style="text-align:center; " onclick="tdclickd(this,'1')"></td>
							<td style="text-align:center; " onclick="tdclickd(this,'1')"></td>
							<td style="text-align:center; " onclick="tdclickd(this,'0')"></td>
							<td style="text-align:center; " onclick="deletetrd(this)">
								<button type="button" class="btn btn-xs btn-link">删除</button></td>
						</tr>
					</tbody>
				</table>

			</div>
			<div class="box-footer">
				<button type="submit" class="btn btn-default" onclick="dosavedeliver()"><span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span>保 存</button>
				<button type="submit" class="btn btn-info pull-right" onclick="doadddeliver()"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新 增</button>
			</div>
		</div>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">调货请求单审核</h4>
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
			<div class="box-body">
				<div id="delivertoolbar" class="tool-bar">
					<button id="btn_query" type="button" class="btn btn-info" onclick="doVerifyDeliver()">
						<span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>审核
					</button>
					<button id="btn_" type="button" class="btn btn-success" onclick="doVerifyDeliver()">
						<span class="glyphicon glyphicon-save" aria-hidden="true"></span>确认收货
					</button>
					<button id="btn_delete" type="button" class="btn btn-danger" onclick="dltDeliverBill()">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
					</button>
				</div>
				<table id="tabverifydeliver" data-search="true" style="font-size: 10px">
				</table>
			</div>
		</div>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">本店调货通知单</h4>
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
			<div class="box-body">
				<div id="notestoolbar" class="tool-bar">
					<button id="btnnewinvoice" type="button" class="btn btn-info" onclick="doaddnewinvoice()">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新建发货单
					</button>
					<button id="btnsureinvoice" type="button" class="btn btn-success" onclick="dosureinvoice()">
						<span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>确认发货
					</button>
				</div>
				<table id="tabnotesdeliver" data-search="true" style="font-size: 10px">
				</table>
				<hr size="1" style="margin: 5px" color="#999999"/>
				<div class="row ">
					<div class="col-md-1" ><label>调货单号</label></div>
					<div class="col-md-2 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-barcode"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="调货单号" id="txbdelivercode" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
					<div class="col-md-1 "><label>配送方式</label></div>
					<div class="col-md-2 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-retweet"> </i></span>
							<select class="form-control select2 select2-hidden-accessible" style="width:100%; height: 25px;border-radius: 0px" tabindex="-1" aria-hidden="true"
							        id="slcdwlmethod" data-toggle="tooltip" data-placement="bottom" title=""></select>
						</div>
					</div>
					<div class="col-md-1" ><label>物流单号</label></div>
					<div class="col-md-2 " >
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px ;padding: 3px 6px"><i class="fa fa-list-alt"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="物流单号" id="txbdwlcode" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
					<div class="col-md-1 ">
						<button id="btnswquery" type="button" class="btn btn-info" style="height: 25px;padding: 2px 4px" onclick="doCreatInvoice()">
							<span class="glyphicon glyphicon-check" aria-hidden="true"></span>创建
						</button>
					</div>

				</div>
			</div>
		</div>
	</body>
	<script>
		$(document).ready(function(){
		    loadslcdeliverstorecode();
		    loadslcdelivermethod();
            $('#tabverifydeliver').bootstrapTable({
                toolbar:'#delivertoolbar',
                striped: true,   //是否显示行间隔色
                cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,   //是否显示分页（*）
                sortable: true,   //是否启用排序
                sortOrder: "asc",   //排序方式
                sidePagination: "client",  //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1,   //初始化加载第一页，默认第一页
                pageSize: 5,   //每页的记录行数（*）
                pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                height:342,
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
                    field: 'gdbCode',align: 'center', valign: 'middle', title: '调货单号'
                },{
                    field: 'recipeName',align: 'center', valign: 'middle', title: '收货门店'
                },{
                    field: 'invoiceName', align: 'center', valign: 'middle', title: '发货门店'
                },{
                    field: 'gdbCount',align: 'center', valign: 'middle', title: '调货总数'
                },{
                    field: 'cargodesc',align: 'center', valign: 'middle', title: '调货方式'
                },{
                    field: 'gdbEmp',align: 'center', valign: 'middle', title: '申请人'
                },{
                    field: 'gmtCreat',align: 'center', valign: 'middle', title: '创建时间'
                },{
                    field: 'gmtModify',align: 'center', valign: 'middle', title: '发货时间'
                },{
                    field:'isverify',align:'center',valign:'middle',title:'审核',
                    formatter:function (value,row,index) {
                        if(row.isverify =='0'){
                            return ['<span class="label label-danger">待审核</span>',].join('');
                        }else{
                            return ['<span class="label label-success">已审核</span>',].join('');
                        }
                    }
                },{
                    field:'isinvoice',align:'center',valign:'middle',title:'发货',
                    formatter:function (value,row,index) {
                        if(row.isinvoice =='0'){
                            return ['<span class="label label-danger">待发货</span>',].join('');
                        }else if(row.isinvoice =='1'){
                            return ['<span class="label label-info">配货中</span>',].join('');
                        }else if(row.isinvoice =='2'){
                            return ['<span class="label label-success">已发货</span>',].join('');
                        }
                    }
                },{
                    field:'isstorage',align:'center',valign:'middle',title:'收货',
                    formatter:function (value,row,index) {
                        if(row.isstorage =='0'){
                            return ['<span class="label label-danger">待收货</span>',].join('');
                        }else if(row.isstorage =='1'){
                            return ['<span class="label label-info">校验中</span>',].join('');
                        }else if(row.isstorage =='2'){
                            return ['<span class="label label-success">已收货</span>',].join('');
                        }
                    }
                }],
                onExpandRow: function (index, row, $detail) {
                    oInit.InitSubTableD(index, row, $detail);
                }
            });
            $('#tabnotesdeliver').bootstrapTable({
                toolbar:'#notestoolbar',
                striped: true,   //是否显示行间隔色
                cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,   //是否显示分页（*）
                sortable: true,   //是否启用排序
                sortOrder: "asc",   //排序方式
                sidePagination: "client",  //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1,   //初始化加载第一页，默认第一页
                pageSize: 5,   //每页的记录行数（*）
                pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                height:342,
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
                    field: 'gdbCode',align: 'center', valign: 'middle', title: '调货单号'
                },{
                    field: 'recipeName',align: 'center', valign: 'middle', title: '收货门店'
                },{
                    field: 'invoiceName', align: 'center', valign: 'middle', title: '发货门店'
                },{
                    field: 'gdbCount',align: 'center', valign: 'middle', title: '调货总数'
                },{
                    field: 'cargodesc',align: 'center', valign: 'middle', title: '调货方式'
                },{
                    field: 'gdbEmp',align: 'center', valign: 'middle', title: '申请人'
                },{
                    field: 'gmtCreat',align: 'center', valign: 'middle', title: '申请时间'
                },{
                    field: 'gmtModify',align: 'center', valign: 'middle', title: '发货时间'
                },{
                    field:'isverify',align:'center',valign:'middle',title:'审核',
                    formatter:function (value,row,index) {
                        if(row.isverify =='0'){
                            return ['<span class="label label-danger">待审核</span>',].join('');
                        }else{
                            return ['<span class="label label-success">已审核</span>',].join('');
                        }
                    }
                },{
                    field:'isinvoice',align:'center',valign:'middle',title:'发货',
                    formatter:function (value,row,index) {
                        if(row.isinvoice =='0'){
                            return ['<span class="label label-danger">待发货</span>',].join('');
                        }else if(row.isinvoice =='1'){
                            return ['<span class="label label-info">配货中</span>',].join('');
                        }else if(row.isinvoice =='2'){
                            return ['<span class="label label-success">已发货</span>',].join('');
                        }
                    }
                }],
                onExpandRow: function (index, row, $detail) {
                    oInit.InitSubTableD(index, row, $detail);
                }
            });
            loadtabverifydeliver();
            loadtabnotesdeliver();
            var oInit = new Object();
            oInit.InitSubTableD=function(index,row,$detail){
                var parentid = row.id;
                var cur_table = $detail.html('<table style="font-size: 10px"></table>').find('table');
                $(cur_table).bootstrapTable({
                    clickToSelect: true,
                    pageSize: 10,
                    pageList: [10, 25],
                    columns: [{
                        checkbox: true,align: 'center', valign: 'middle',
                    },{
                        field: 'gdbSku',align: 'center', valign: 'middle', title: '货号'
                    },{
                        field: 'gdbName',align: 'center', valign: 'middle', title: '名称'
                    },{
                        field: 'gdbStyle',align: 'center', valign: 'middle', title: '款号'
                    },{
                        field: 'gdbColor',align: 'center', valign: 'middle', title: '颜色'
                    },{
                        field: 'gdbSize',align: 'center', valign: 'middle', title: '尺码'
                    },{
                        field: 'gdbSizeQty',align: 'center', valign: 'middle', title: '配码数量'
                    }]
                });
                $.ajax({
                    type:'post',
                    url:'goods/gettabdeliverdtl?gdbcode='+row.gdbCode,
                    success:function(result){
                        //alert(result);
                        var jsoOrder = JSON.parse(result);
                        $(cur_table).bootstrapTable("load", jsoOrder.data);
                    }
                });
            }
		});
	</script>
</html>
