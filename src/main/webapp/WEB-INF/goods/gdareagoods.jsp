<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/3/17
  Time: 上午9:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>地区配货表</title>
	</head>
	<body>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">地区配货单</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div  class="box-body">
				<div class="row">
					<div class="col-md-1"><label>配货单号</label></div>
					<div class="col-md-3 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-file-archive-o"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="配货单号" id="txbagcodesearch" >
						</div>
					</div>
					<div class="col-md-1 ">
						<button id="btnsearchag" type="button" class="btn btn-info" style="height: 25px;padding: 2px" onclick="searchagbill()">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
						</button>
					</div>
				</div>
				<hr size="1" style="margin: 5px" color="#999999"/>
				<table id="tabagbilllist" style="font-size: 10px">
				</table>
			</div>
		</div>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">新建地区配货单</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div class="row ">
					<div class="col-md-1" ><label>发货仓库</label></div>
					<div class="col-md-3 " >
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px ;padding: 3px 6px"><i class="fa fa-cubes"> </i></span>
							<select class="form-control select2 select2-hidden-accessible" style="width:100%; height: 25px;border-radius: 0px" tabindex="-1" aria-hidden="true"
							        id="slcagwarecode" data-toggle="tooltip" data-placement="bottom" title=""></select>
						</div>
					</div>
					<div class="col-md-1 "><label>收货门店</label></div>
					<div class="col-md-3 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-institution"> </i></span>
							<select class="form-control select2 select2-hidden-accessible" style="width:100%; height: 25px;border-radius: 0px" tabindex="-1" aria-hidden="true"
							        id="slcagstorecode" data-toggle="tooltip" data-placement="bottom" title=""></select>
						</div>
					</div>
					<div class="col-md-1 ">
						<button id="btnaddnewag" type="button" class="btn btn-info" style="height: 25px;padding: 2px" onclick="doaddnewag()">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>新建门店配货单
						</button>
					</div>
				</div>
				<hr size="1" style="margin: 5px" color="#999999"/>
				<div class="row ">
					<div class="col-md-1"><label>配货单号</label></div>
					<div class="col-md-3 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-file-archive-o"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="配货单号" id="txbagcode" readonly="readonly">
						</div>
					</div>
					<div class="col-md-1 "><label>配送总数</label></div>
					<div class="col-md-3 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-cc"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="配送总数" id="txbagcount" readonly="readonly">
						</div>
					</div>
				</div>
				<hr size="1" style="margin: 5px" color="#999999"/>
				<div id="tipaglist" class="alert-warning" ></div>
				<table id="tabaregoodslist" style="font-size: 10px">
				</table>
			</div>
			<div class="box-footer">
				<button type="submit" class="btn btn-info pull-right" onclick="doqueryagbill()"><span class="glyphicon glyphicon-save" aria-hidden="true"></span>确认配货单</button>
			</div>
		</div>
		<div class="box">
			<div class="box-header with-border">
				<h4 class="box-title">货品查询配货</h4>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
					<button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
				</div>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<div class="row ">

					<div class="col-md-1" ><label>货品SKU</label></div>
					<div class="col-md-2 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-barcode"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="按SKU查询" id="txballagsku" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
					<div class="col-md-1" ><label>货品款号</label></div>
					<div class="col-md-2 " >
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px ;padding: 3px 6px"><i class="fa fa-cubes"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="查询款号" id="txballagstyle" data-toggle="tooltip" data-placement="bottom" title="">						</div>
					</div>
					<div class="col-md-1 "><label>货品尺码</label></div>
					<div class="col-md-2 ">
						<div class="input-group">
							<span class="input-group-addon" style="height: 25px;padding: 3px 6px"><i class="fa fa-sort-numeric-asc"> </i></span>
							<input class="form-control" style="height: 25px" type="text" placeholder="查询尺码" id="txballagsize" data-toggle="tooltip" data-placement="bottom" title="">
						</div>
					</div>
					<div class="col-md-1 ">
						<button id="btnallswquery" type="button" class="btn btn-info" style="height: 25px;padding: 2px" onclick="querytabaregoods()">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
						</button>
					</div>
				</div>
				<hr size="1" style="margin: 5px" color="#999999"/>
				<div id="tipag" class="alert-warning" ></div>
				<table id="tabaregoods" style="font-size: 10px">
				</table>
			</div>
			<div class="box-footer">
				<button type="submit" class="btn btn-info pull-right" onclick="doaddnewagdtl()"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增调货货品</button>
			</div>
		</div>
		<script>
            $('#tabagbilllist').bootstrapTable({
                toolbar:'#agbilltoolbar',
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
                showColumns:false,
                cardView: false,   //是否显示详细视图
                detailView: false,   //是否显示父子表
                showExport: false, //是否显示导出
                exportDataType: "all", //basic', 'all', 'selected'.
                columns: [{
                    checkbox: true,align: 'center', valign: 'middle',
                },{
                    field: 'agCode',align: 'center', valign: 'middle', title: '调货单号'
                },{
                    field: 'wareName',align: 'center', valign: 'middle', title: '收货门店'
                },{
                    field: 'storeName', align: 'center', valign: 'middle', title: '发货门店'
                },{
                    field: 'agCount',align: 'center', valign: 'middle', title: '调货总数'
                },{
                    field: 'empName',align: 'center', valign: 'middle', title: '申请人'
                },{
                    field: 'gmtCreat',align: 'center', valign: 'middle', title: '创建时间'
                },{
                    field: 'gmtModify',align: 'center', valign: 'middle', title: '发货时间'
                },{
                    field:'isverify',align:'center',valign:'middle',title:'审核',
                    formatter:function (value,row,index) {
                        if(row.isverify =='0'){
                            return ['<span style="color:#ffa200">待审核</span>',].join('');
                        }else{
                            return ['<span style="color:#04ae00">已审核</span>',].join('');
                        }
                    }
                },{
                    field:'isstorage',align:'center',valign:'middle',title:'配送',
                    formatter:function (value,row,index) {
                        if(row.isstorage =='0'){
                            return ['<span style="color:#ffa200">待配送</span>',].join('');
                        }else if(row.isstorage =='1'){
                            return ['<span style="color:#04ae00">已配送</span>',].join('');
                        }
                    }
                },{
                    title:'编辑',align: 'center', valign: 'middle',formatter:function (value,row,index) {
                        if(row.isverify == '0') {
                            return ['<button type="button" class="btn btn-xs btn-link" onclick=editagbill(\'' + row.agCode + '\') >编辑</button>',].join('');
                        }else{
                            return ['<span style="color:#04ae00">已建单</span>',].join('');
                        }
                    }
                },{
                    title:'删除',align: 'center', valign: 'middle',formatter:function (value,row,index) {
                        if(row.isverify == '0') {
                            return ['<button type="button" class="btn btn-xs btn-link" style="color:#ffa200" onclick=delagbilllist(\'' + row.agCode + '\') >删除</button>',].join('');
                        }else{
                            return ['<span style="color:#04ae00">已建单</span>',].join('');
                        }
                    }
                }],
                onExpandRow: function (index, row, $detail) {
                    oInit.InitSubTableag(index, row, $detail);
                }
            });
            var oInit = new Object();
            oInit.InitSubTableag=function(index,row,$detail){
                var parentid = row.id;
                var cur_table = $detail.html('<table style="font-size: 10px"></table>').find('table');
                $(cur_table).bootstrapTable({
                    clickToSelect: true,
                    pageSize: 10,
                    pageList: [10, 25],
                    columns: [{
                        checkbox: true,align: 'center', valign: 'middle',
                    },{
                        field: 'agSku',align: 'center', valign: 'middle', title: '货品SKU'
                    },{
                        field: 'agName',align: 'center', valign: 'middle', title: '货品名称'
                    },{
                        field: 'agStyle', align: 'center', valign: 'middle', title: '货品款号'
                    },{
                        field: 'agColor',align: 'center', valign: 'middle', title: '颜色'
                    },{
                        field: 'agSize',align: 'center', valign: 'middle', title: '尺码'
                    },{
                        field: 'agSizeQty',align: 'center', valign: 'middle', title: '配货数量'
                    }]
                });
                $.ajax({
                    type:'post',
                    url:'goods/getagdtlbycode?agcode='+row.agCode,
                    success:function(result){
                        //alert(result);
                        var jsoOrder = JSON.parse(result);
                        $(cur_table).bootstrapTable("load", jsoOrder.data);
                    }
                });
            }
            $('#tabaregoods').bootstrapTable({
                striped: true,   //是否显示行间隔色
                cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,   //是否显示分页（*）
                sortable: true,   //是否启用排序
                sortOrder: "asc",   //排序方式
                sidePagination: "client",  //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1,   //初始化加载第一页，默认第一页
                pageSize: 10,   //每页的记录行数（*）
                pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                height:360,
                strictSearch: false,
                clickToSelect: true,  //是否启用点击选中行
                showColumns:false,
                cardView: false,   //是否显示详细视图
                detailView: false,   //是否显示父子表
                showExport: false, //是否显示导出
                exportDataType: "all", //basic', 'all', 'selected'.
                columns: [{
                    checkbox: true,align: 'center', valign: 'middle',
                },{
                    field: 'storeCode',align: 'center', valign: 'middle', title: '货品仓库'
                },{
                    field: 'gSku',align: 'center', valign: 'middle', title: '货品SKU'
                },{
                    field: 'gName',align: 'center', valign: 'middle', title: '货品名称'
                },{
                    field: 'gStyle', align: 'center', valign: 'middle', title: '货品款号'
                },{
                    field: 'gColor',align: 'center', valign: 'middle', title: '颜色'
                },{
                    field: 'gSize',align: 'center', valign: 'middle', title: '尺码'
                },{
                    field: 'gCount',align: 'center', valign: 'middle', title: '库存数量'
                },{
                    field: 'storeIndt',align: 'center', valign: 'middle', title: '入库时间'
                },{
                    field:'isstate',align:'center',valign:'middle',title:'状态',
                    formatter:function (value,row,index) {
                        if(row.isstate =='1'){
                            return ['<span class="label label-info">在库存</span>',].join('');
                        }else{
                            return ['<span class="label label-success">已出库</span>',].join('');
                        }
                    }
                },{
                    field: 'agsizeqty',align: 'center', valign: 'middle', title: '配货数量',
                    formatter:function (value,row,index) {
                        return ['<input type="text" class="pull-left" style="height: 20px;width: 40px;font-size: 10px" data-toggle="tooltip" data-placement="bottom" title="" onchange="rowchangesizeqty(\''+row.gSku+'\',\''+row.gCount+'\',this)">',].join('');
                    }
                }]
            });
            $('#tabaregoodslist').bootstrapTable({
                striped: true,   //是否显示行间隔色
                cache: false,   //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,   //是否显示分页（*）
                sortable: true,   //是否启用排序
                sortOrder: "asc",   //排序方式
                sidePagination: "client",  //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1,   //初始化加载第一页，默认第一页
                pageSize: 10,   //每页的记录行数（*）
                pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                height:360,
                strictSearch: false,
                clickToSelect: true,  //是否启用点击选中行
                showColumns:false,
                cardView: false,   //是否显示详细视图
                detailView: false,   //是否显示父子表
                showExport: false, //是否显示导出
                exportDataType: "all", //basic', 'all', 'selected'.
                columns: [{
                    checkbox: true,align: 'center', valign: 'middle',
                },{
                    field: 'agSku',align: 'center', valign: 'middle', title: '货品SKU'
                },{
                    field: 'agName',align: 'center', valign: 'middle', title: '货品名称'
                },{
                    field: 'agStyle', align: 'center', valign: 'middle', title: '货品款号'
                },{
                    field: 'agColor',align: 'center', valign: 'middle', title: '颜色'
                },{
                    field: 'agSize',align: 'center', valign: 'middle', title: '尺码'
                },{
                    field: 'agSizeQty',align: 'center', valign: 'middle', title: '配货数量'
                },{
                    title:'删除',align: 'center', valign: 'middle',formatter:function (value,row,index) {
                        return ['<button type="button" class="btn btn-xs btn-link" onclick=delaglist(\''+row.agSku+'\',\''+row.agSizeQty+'\') >删除</button>',].join('');
                    }
                }]
            });
            loadtabagbill();
            loadslcagwarecode();
            loadslcagstorecode();
		</script>
	</body>
</html>
