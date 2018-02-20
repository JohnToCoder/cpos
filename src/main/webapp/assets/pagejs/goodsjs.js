var totalnum =0;
//仓库发货单
function dosavenwi(){
    var wijson = {"wareCode":'',"storeCode":'',"wlCode":'',"wiMethod":'',"wiCount":'',"wiEmp":'',"isverify":'0',"isstorage":'0',"intype":'1',"cargotype":'1'}
    var strwarecode = $("#slcwarecode").val();
    if(strwarecode == '0'){
        $("#slcwarecode").attr('title',"请选择发货仓库").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strstorecode = $("#slcstorecode").val();
    if(strstorecode == '0'){
        $("#slcstorecode").attr('title',"请选择收货门店").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strwimethod = $("#slcwimethod").val();
    if(strwimethod == '0'){
        $("#slcwimethod").attr('title',"请选择配送方式").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strwlcode = $("#txbwlcode").val();
    if(isEmpty(strwlcode)){
        $("#txbwlcode").attr('title',"物流单号要填呢").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strwicount = $("#txbwicount").val();
    if(isEmpty(strwicount)){
        $("#txbwicount").attr('title',"总数要填呢").tooltip('fixTitle').tooltip('show');
        return;
    }
    wijson.wareCode= strwarecode;
    wijson.storeCode = strstorecode;
    wijson.wlCode = strwlcode;
    wijson.wiMethod = strwimethod;
    wijson.wiCount = strwicount;
    var tableinfo = gettableinfo();
    $.ajax({
        url:'goods/doupwibilldtl',
        type:'post',
        contentType:'application/json; charset=UTF-8',
        data:JSON.stringify({"wibill":JSON.stringify(wijson),"widtl":JSON.stringify(tableinfo)}),
        async:false,
        error: function(es){
            alert("提交失败!\n"+es);
        },
        success:function(data){
            var strre = JSON.parse(data);
            if(strre.code == '0'){
                loadwareinvoicetab();
                $("#slcwarecode").val(0);
                $("#slcstorecode").val(0);
                $("#slcwimethod").val(0);
                $("#txbwicount").val("");
                $("#txbwlcode").val("");
                deletetrs();
                totalnum = 0;
                alert(strre.data);
            }else if(strre.code == '2'){
                $('#tipwidtl').html("Tip:配送表:"+strre.data).show();
                return;
            }else{
                alert(strre.data);
            }
        }
    });
}
function deletetrs() {
    var tb = document.getElementById('newwidtl');
    var rowNum=tb.rows.length;
    for (var i=1;i<rowNum;i++)
    {
        tb.deleteRow(i);
        rowNum=rowNum-1;
        i=i-1;
    }
}
//get table infomation
function gettableinfo(){
    $('#tipwidtl').hide();
    var wiSku ="";
    var wiStyle ="";
    var wiColor = "";
    var wiSize = "";
    var wiSizeQty = "";
    var tabledata = "";
    var table = $("#newwidtl");
    var tbody = table.children();
    var trs = tbody.children();
    for(var i=1;i<trs.length;i++){
        var tds = trs.eq(i).children();
        for(var j=0;j<tds.length;j++){
            if(j==0){
                if(isEmpty(tds.eq(j).text())){
                    $('#tipwidtl').html("Tip:配送表SKU必须要有的").show();
                    return ;
                }
                wiSku = "wiSku\":\""+tds.eq(j).text();
            }
            if(j==1){

                wiStyle = "wiStyle\":\""+tds.eq(j).text();
            }
            if(j==2){

                wiColor = "wiColor\":\""+tds.eq(j).text();
            }
            if(j==3){

                wiSize = "wiSize\":\""+tds.eq(j).text();
            }
            if(j==4){
                if(isEmpty(tds.eq(j).text())){
                    $('#tipwidtl').html("Tip:配送数必需要填的").show();
                    return ;
                }
                wiSizeQty = "wiSizeQty\":\""+tds.eq(j).text();
            }
        }
        if(i==trs.length-1){
            tabledata += "{\""+wiSku+"\",\""+wiStyle+"\",\""+wiColor+"\",\""+wiSize+"\",\""+wiSizeQty+"\"}";
        }else{
            tabledata += "{\""+wiSku+"\",\""+wiStyle+"\",\""+wiColor+"\",\""+wiSize+"\",\""+wiSizeQty+"\"},";
        }
    }
    tabledata = "["+tabledata+"]";
    return tabledata;
}
function changetxbnum(trnum) {
    $('#tipwidtl').hide();
    var reg = new RegExp("^[0-9]*$");
    if(reg.test(trnum)){
        totalnum =parseFloat( totalnum) + parseFloat(trnum);
        $("#txbwicount").val(totalnum);
    }else{
        $('#tipwidtl').html("Tip:请输入整数类型的配码数量").show();
    }
}
function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == ""){
        return true;
    }else{
        return false;
    }
}
function tdclick(tdobject,tdnum){
    $('#tipwidtl').hide();
    var s =tdnum;
    var td=$(tdobject);
    var trnum = td.text();
    if(tdnum == '2'){
    if(!isEmpty(trnum)){
        var reg = new RegExp("^[0-9]*$");
        if(reg.test(trnum)){
            totalnum = parseFloat(totalnum)-parseFloat(trnum);
        }
    }}
    td.attr("onclick", "");
    //1,取出当前td中的文本内容保存起来
    var text=td.text();
    //2,清空td里面的内容
    td.html(""); //也可以用td.empty();
    //3，建立一个文本框，也就是input的元素节点
    var input=$("<input>");
    //4，设置文本框的值是保存起来的文本内容
    input.attr("style","width:100%");
    input.attr("value",text);
    if(tdnum == '0'||tdnum == '2'){
        input.attr("onchange","changetxbnum(this.value)");
        input.bind("blur",function(){
            var inputnode=$(this);
            var inputtext=inputnode.val();
            var tdNode=inputnode.parent();
            tdNode.html(inputtext);
            tdNode.click(tdclick);
            td.attr("onclick", "tdclick(this,\"2\")");
        });
    }else{
        input.bind("blur",function(){
            var inputnode=$(this);
            var inputtext=inputnode.val();
            var tdNode=inputnode.parent();
            tdNode.html(inputtext);
            tdNode.click(tdclick);
            td.attr("onclick", "tdclick(this,\"1\")");
        });
    }
    input.keyup(function(event){
        var myEvent =event||window.event;
        var kcode=myEvent.keyCode;
        if(kcode==13){
            var inputnode=$(this);
            var inputtext=inputnode.val();
            var tdNode=inputnode.parent();
            tdNode.html(inputtext);
            tdNode.click(tdclick);
        }
    });

    //5，将文本框加入到td中
    td.append(input);
    var t =input.val();
    input.val("").focus().val(t);
//       input.focus();

    //6,清除点击事件
    td.unbind("click");
}
var row=0;
function doaddnwi(){
    if(row<10){
        row++;
        var table = $("#newwidtl");
        var tr= $("<tr>" +
            "<td style='text-align: center;' onclick='tdclick(this,\"1\")'>"+"</td>" +
            "<td style='text-align: center;' onclick='tdclick(this,\"1\")'>"+"</td>" +
            "<td style='text-align: center;' onclick='tdclick(this,\"1\")'>"+"</td>" +
            "<td style='text-align: center;' onclick='tdclick(this,\"1\")'>"+"</td>" +
            "<td style='text-align: center;' onclick='tdclick(this,\"0\")'>"+"</td>" +
            "<td align='center' onclick='deletetr(this)'><button type='button' class='btn btn-xs btn-link' >"+"删除"+"</button></td></tr>");
        table.append(tr);
    }else{
        alert("已达到发票能开具的最大商品明细行数");
    }
}
function deletetr(tdobject){
    row--;
    var td=$(tdobject);
    var ptr = td.parents("tr").children();
    var trnum = ptr.eq(4).text();
    if(!isEmpty(trnum)){
        var reg = new RegExp("^[0-9]*$");
        if(reg.test(trnum)){
            totalnum = parseFloat(totalnum)-parseFloat(trnum);
        }
    }
    $("#txbwicount").val(totalnum);
    td.parents("tr").remove();
}
function loadwareinvoicetab() {
    $.ajax({
        type:'post',
        url:'goods/getwareinvoicetab',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            $('#wareinvoicetab').bootstrapTable("load", jsoOrder.data);
        }
    });
}
function loadslcwarecode(){
    $.ajax({
        type:'post',
        url:'goods/getstorelist?storetype=03',
        success:function(result){
                var oplist = JSON.parse(result);
                $("#slcwarecode").empty();
                $("#slcwarecode").append("<option value='0'>--请选择--</option>");
                for(var i=0;i<oplist.length;i++){
                    var dep = oplist[i];
                    var opt="";
                    opt+='<option value="'+dep.storeCode+'">'+dep.storeName+'</option>';
                    $("#slcwarecode").append(opt);
                }
        }
    });
}
function loadslcstorecode() {
    $.ajax({
        type:'post',
        url:'goods/getstorelist?storetype=02',
        success:function(result) {
            var oplist = JSON.parse(result);
            $("#slcstorecode").empty();
            $("#slcstorecode").append("<option value='0'>--请选择--</option>");
            for (var i = 0; i < oplist.length; i++) {
                var dep = oplist[i];
                var opt = "";
                opt += '<option value="' + dep.storeCode + '">' + dep.storeName + '</option>';
                $("#slcstorecode").append(opt);
            }
        }
    });
}
function loadquerystore(){
    $.ajax({
        type:'post',
        url:'goods/getstorelist?storetype=02',
        success:function(result) {
            var oplist = JSON.parse(result);
            $("#querystore").empty();
            $("#querystore").append("<option value='0'>--请选择--</option>");
            for (var i = 0; i < oplist.length; i++) {
                var dep = oplist[i];
                var opt = "";
                opt += '<option value="' + dep.storeCode + '">' + dep.storeName + '</option>';
                $("#querystore").append(opt);
            }
        }
    });
}
function loadslcwimethod() {
    $.ajax({
        type:'post',
        url:'goods/getwimethodlist',
        success:function(result) {
            var oplist = JSON.parse(result);
            $("#slcwimethod").empty();
            $("#slcwimethod").append("<option value='0'>--请选择--</option>");
            for (var i = 0; i < oplist.length; i++) {
                var dep = oplist[i];
                var opt = "";
                opt += '<option value="' + dep.wlCode + '">' + dep.wlMethod + '</option>';
                $("#slcwimethod").append(opt);
            }
        }
    });
}
function doVerifyWareInvoices() {
    var queryrows = $("#wareinvoicetab").bootstrapTable("getSelections");
    if(queryrows.length == 0){
        alert("请选择要审核的发货单!");
        return;
    }
    var confirlist = "";
    var confirmmsg = "确认审核通过以下发货单:\n";
    var confirmmsgl = "以下发货单已审核过:\n";
    for(var i = 0 ;i<queryrows.length;i++){
        if(queryrows[i].isverify == '0'){
            confirmmsg +="   --  "+queryrows[i].wiCode +"   \n";
            confirlist +=queryrows[i].wiCode+",";
        }else{
            confirmmsgl += "   --  "+queryrows[i].wiCode +"   \n";
        }
    }
    var cfm = confirm(confirmmsg+confirmmsgl);
    if(cfm == true){
        $.ajax({
            url:'goods/doverifybill',
            type:'post',
            contentType:'application/json; charset=UTF-8',
            data:JSON.stringify({'wicodelist':confirlist}),
            success:function (data) {
                var re = JSON.parse(data);
                if(re.code = "0"){
                    loadwareinvoicetab();
                }else{
                    alert(re.data);
                }
            }
        });
    }
}
//门店查询收货单
function loadslcstorewcode() {

    var slclist = [];
    $.ajax({
        type:'post',
        url:'goods/getstorelist?storetype=03',
        success:function(result){
            var op1 = JSON.parse(result);
            for(var m=0;m<op1.length;m++){
                slclist.push(op1[m]);
            }
        }
    });
    $.ajax({
        type:'post',
        url:'goods/getstorelist?storetype=02',
        success:function(result){
            var op2 = JSON.parse(result);
            for(var n = 0;n<op2.length;n++){
                slclist.push(op2[n]);
            }
            $("#slcstorewcode").empty();
            $("#slcstorewcode").append("<option value='0'>--请选择--</option>");
            for(var i=0;i<slclist.length;i++){
                var dep = slclist[i];
                var opt="";
                opt+='<option value="'+dep.storeCode+'">'+dep.storeName+'</option>';
                $("#slcstorewcode").append(opt);
            }
        }
    });
}
function loadtabstorerecipe() {
    $.ajax({
        type:'post',
        url:'goods/getstorerecipelist',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            $('#tabstorerecipe').bootstrapTable("load", jsoOrder.data);
        }
    });
}
function loadtabstorereverify(){
    $.ajax({
        type:'post',
        url:'goods/getstoreverifylist',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            $('#tabstorereverify').bootstrapTable("load", jsoOrder.data);
        }
    });
}
function doStoreRecipe(){
    var queryrows = $("#tabstorerecipe").bootstrapTable("getSelections");
    if(queryrows.length == 0){
        alert("请选择要校验的发货单!");
        return;
    }
    var rejson ={"wiCode":'',"wareCode":'',"storeCode":'',"wiCount":'',"wiMethod":'',"isstorage":'1',"intype":'',"cargotype":''};
    var confirlist = [];
    var confirmmsg = "确认收货校验以下发货单:\n";
    var confirmmsgl = "以下发货单已校验过:\n";
    for(var i = 0 ;i<queryrows.length;i++){
        if(queryrows[i].isstorage == '0'){
            confirmmsg +="   --  "+queryrows[i].wiCode +"   \n";
            rejson.wiCode = queryrows[i].wiCode;
            rejson.wareCode = queryrows[i].wareCode;
            rejson.storeCode = queryrows[i].storeCode;
            rejson.wiCount = queryrows[i].wiCount;
            rejson.wiMethod = queryrows[i].wiMethod;
            rejson.intype = queryrows[i].intype;
            rejson.cargotype = queryrows[i].cargotype;
            confirlist.push(rejson);
        }else{
            confirmmsgl += "   --  "+queryrows[i].wiCode +"   \n";
        }
    }
    var cfm = confirm(confirmmsg+confirmmsgl);
    if(cfm == true){
        $.ajax({
            url:'goods/doverifyrecipebill',
            type:'post',
            contentType:'application/json; charset=UTF-8',
            data:JSON.stringify({'recipelist':confirlist}),
            success:function (data) {
                var re = JSON.parse(data);
                if(re.code = "0"){
                    loadtabstorerecipe();
                    loadtabstorereverify();
                    alert(re.data);
                }else{
                    alert(re.data);
                }
            }
        });
    }
}
function doSRecipeVerify() {
    var queryrows = $("#tabstorereverify").bootstrapTable("getSelections");
    if(queryrows.length == 0){
        alert("请选择要收货的发货单!");
        return;
    }
    var confirlist = "";
    var confirmmsg = "确认通过以下发货单收货:\n";
    var confirmmsgl = "以下发货单已收货:\n";
    for(var i = 0 ;i<queryrows.length;i++){
        if(queryrows[i].isstorage == '1'){
            confirmmsg +="   --  "+queryrows[i].wiCode +"   \n";
            confirlist +=queryrows[i].wiCode+",";
        }else{
            confirmmsgl += "   --  "+queryrows[i].wiCode +"   \n";
        }
    }
    var cfm = confirm(confirmmsg+confirmmsgl);
    if(cfm == true){
        $.ajax({
            url:'goods/dorecipegoods',
            type:'post',
            contentType:'application/json; charset=UTF-8',
            data:JSON.stringify({'wicodelist':confirlist}),
            success:function (data) {
                var re = JSON.parse(data);
                if(re.code == "0"){
                    loadtabstorerecipe();
                    loadtabstorereverify();
                }else{
                    alert(re.data);
                }
            }
        });
    }
}
function doQueryStoreWare() {
    $('#tipsws').hide();
    var strsku = $("#txbswsku").val();
    var strstyle = $("#txbswstyle").val();
    var strsize = $("#txbswsize").val();
    $.ajax({
        url:'goods/doquertsw',
        type:'post',
        contentType:'application/json; charset=UTF-8',
        data:JSON.stringify({'sku':strsku,'style':strstyle,'size':strsize}),
        success:function (data) {
            var re = JSON.parse(data);
            if(re.code == "0"){
                if(re.count>0){
                    $('#tabstoreware').bootstrapTable("load", re.data);
                }else{
                    $('#tipsws').html("Tip:库存没有找到需要的货品").show();
                }
            }else{
                alert(re.data);
            }
        }
    });
}
function doQueryAllStoreWare() {
    $('#tipallsws').hide();
    var strsku = $("#txballswsku").val();
    var strstyle = $("#txballswstyle").val();
    var strsize = $("#txballswsize").val();
    $.ajax({
        url:'goods/doqueralltsw',
        type:'post',
        contentType:'application/json; charset=UTF-8',
        data:JSON.stringify({'sku':strsku,'style':strstyle,'size':strsize}),
        success:function (data) {
            var re = JSON.parse(data);
            if(re.code == "0"){
                if(re.count>0){
                    $('#taballstoreware').bootstrapTable("load", re.data);
                }else{
                    $('#tipallsws').html("Tip:库存没有找到需要的货品").show();
                }
            }else{
                alert(re.data);
            }
        }
    });
}
//门店调货单
var totaldenum = 0;
function doadddeliver(){
    if(row<10){
        row++;
        var table = $("#newdeliver");
        var tr= $("<tr>" +
            "<td style='text-align: center;' onclick='tdclickd(this,\"1\")'>"+"</td>" +
            "<td style='text-align: center;' onclick='tdclickd(this,\"1\")'>"+"</td>" +
            "<td style='text-align: center;' onclick='tdclickd(this,\"1\")'>"+"</td>" +
            "<td style='text-align: center;' onclick='tdclickd(this,\"1\")'>"+"</td>" +
            "<td style='text-align: center;' onclick='tdclickd(this,\"0\")'>"+"</td>" +
            "<td align='center' onclick='deletetrd(this)'><button type='button' class='btn btn-xs btn-link' >"+"删除"+"</button></td></tr>");
        table.append(tr);
    }else{
        alert("已达到发票能开具的最大商品明细行数");
    }
}
function tdclickd(tdobject,tdnum) {

    $('#tipdeliver').hide();
    var s =tdnum;
    var td=$(tdobject);
    var trnum = td.text();
    if(tdnum == '2'){
        if(!isEmpty(trnum)){
            var reg = new RegExp("^[0-9]*$");
            if(reg.test(trnum)){
                totaldenum = parseFloat(totaldenum)-parseFloat(trnum);
            }
        }}
    td.attr("onclick", "");
    //1,取出当前td中的文本内容保存起来
    var text=td.text();
    //2,清空td里面的内容
    td.html(""); //也可以用td.empty();
    //3，建立一个文本框，也就是input的元素节点
    var input=$("<input>");
    //4，设置文本框的值是保存起来的文本内容
    input.attr("style","width:100%");
    input.attr("value",text);
    if(tdnum == '0'||tdnum == '2'){
        input.attr("onchange","changetxbdenum(this.value)");
        input.bind("blur",function(){
            var inputnode=$(this);
            var inputtext=inputnode.val();
            var tdNode=inputnode.parent();
            tdNode.html(inputtext);
            tdNode.click(tdclick);
            td.attr("onclick", "tdclickd(this,\"2\")");
        });
    }else{
        input.bind("blur",function(){
            var inputnode=$(this);
            var inputtext=inputnode.val();
            var tdNode=inputnode.parent();
            tdNode.html(inputtext);
            tdNode.click(tdclick);
            td.attr("onclick", "tdclickd(this,\"1\")");
        });
    }
    input.keyup(function(event){
        var myEvent =event||window.event;
        var kcode=myEvent.keyCode;
        if(kcode==13){
            var inputnode=$(this);
            var inputtext=inputnode.val();
            var tdNode=inputnode.parent();
            tdNode.html(inputtext);
            tdNode.click(tdclickd);
        }
    });

    //5，将文本框加入到td中
    td.append(input);
    var t =input.val();
    input.val("").focus().val(t);
//       input.focus();

    //6,清除点击事件
    td.unbind("click");
}
function changetxbdenum(trnum) {
    $('#tipdeliver').hide();
    var reg = new RegExp("^[0-9]*$");
    if(reg.test(trnum)){
        totaldenum =parseFloat( totaldenum) + parseFloat(trnum);
        $("#txbdelivercount").val(totaldenum);
    }else{
        $('#tipdeliver').html("Tip:请输入整数类型的配码数量").show();
    }
}
function deletetrd(tdobject){
    row--;
    var td=$(tdobject);
    var ptr = td.parents("tr").children();
    var trnum = ptr.eq(4).text();
    if(!isEmpty(trnum)){
        var reg = new RegExp("^[0-9]*$");
        if(reg.test(trnum)){
            totaldenum = parseFloat(totaldenum)-parseFloat(trnum);
        }
    }
    $("#txbdelivercount").val(totaldenum);
    td.parents("tr").remove();
}
function dosavedeliver(){
    var dejson = {"invoiceStore":'',"gdbCount":'',"cargotype":'',"isverify":'0',"isstorage":'0',"isinvoice":'0',"intype":'2'}
    var strinvoicestore = $("#slcdeliverstorecode").val();
    if(strinvoicestore == '0'){
        $("#slcdeliverstorecode").attr('title',"请选择发货仓库").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strcargotype = $("#slcdelivermethod").val();
    if(strcargotype == '0'){
        $("#slcdelivermethod").attr('title',"请选择收调货方式").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strdecount = $("#txbdelivercount").val();
    if(isEmpty(strdecount)){
        $("#txbdelivercount").attr('title',"总数要填呢").tooltip('fixTitle').tooltip('show');
        return;
    }
    dejson.invoiceStore = strinvoicestore;
    dejson.cargotype = strcargotype;
    dejson.gdbCount = strdecount;
    var tableinfo = getdetableinfo();
    $.ajax({
        url:'goods/doupdeliverbilldtl',
        type:'post',
        contentType:'application/json; charset=UTF-8',
        data:JSON.stringify({"deliverbill":JSON.stringify(dejson),"deliverdtl":JSON.stringify(tableinfo)}),
        async:false,
        error: function(es){
            alert("提交失败!\n"+es);
        },
        success:function(data){
            var strre = JSON.parse(data);
            if(strre.code == '0'){
                loadwareinvoicetab();
                $("#slcdelivermethod").val(0);
                $("#slcdeliverstorecode").val(0);
                $("#txbdelivercount").val("");
                deletedetrs();
                totaldenum = 0;
                loadtabverifydeliver();
                alert(strre.data);
            }else{
                alert(strre.data);
            }
        }
    });
}
function deletedetrs() {
    var tb = document.getElementById('newdeliver');
    var rowNum=tb.rows.length;
    for (var i=1;i<rowNum;i++)
    {
        tb.deleteRow(i);
        rowNum=rowNum-1;
        i=i-1;
    }
}
//get table infomation
function getdetableinfo(){
    $('#tipdeliver').hide();
    var gdbSku ="";
    var gdbStyle ="";
    var gdbColor = "";
    var gdbSize = "";
    var gdbSizeQty = "";
    var tabledata = "";
    var table = $("#newdeliver");
    var tbody = table.children();
    var trs = tbody.children();
    for(var i=1;i<trs.length;i++){
        var tds = trs.eq(i).children();
        for(var j=0;j<tds.length;j++){
            if(j==0){
                if(isEmpty(tds.eq(j).text())){
                    $('#tipdeliver').html("Tip:配送表SKU必须要有的").show();
                    return ;
                }
                gdbSku = "gdbSku\":\""+tds.eq(j).text();
            }
            if(j==1){

                gdbStyle = "gdbStyle\":\""+tds.eq(j).text();
            }
            if(j==2){

                gdbColor = "gdbColor\":\""+tds.eq(j).text();
            }
            if(j==3){

                gdbSize = "gdbSize\":\""+tds.eq(j).text();
            }
            if(j==4){
                if(isEmpty(tds.eq(j).text())){
                    $('#tipdeliver').html("Tip:配送数必需要填的").show();
                    return ;
                }
                gdbSizeQty = "gdbSizeQty\":\""+tds.eq(j).text();
            }
        }
        if(i==trs.length-1){
            tabledata += "{\""+gdbSku+"\",\""+gdbStyle+"\",\""+gdbColor+"\",\""+gdbSize+"\",\""+gdbSizeQty+"\"}";
        }else{
            tabledata += "{\""+gdbSku+"\",\""+gdbStyle+"\",\""+gdbColor+"\",\""+gdbSize+"\",\""+gdbSizeQty+"\"},";
        }
    }
    tabledata = "["+tabledata+"]";
    return tabledata;
}
function loadslcdeliverstorecode() {
    $.ajax({
        type:'post',
        url:'goods/getstorelist?storetype=02',
        success:function(result) {
            var oplist = JSON.parse(result);
            $("#slcdeliverstorecode").empty();
            $("#slcdeliverstorecode").append("<option value='0'>--请选择--</option>");
            for (var i = 0; i < oplist.length; i++) {
                var dep = oplist[i];
                var opt = "";
                opt += '<option value="' + dep.storeCode + '">' + dep.storeName + '</option>';
                $("#slcdeliverstorecode").append(opt);
            }
        }
    });
}
function loadslcdelivermethod() {
    $.ajax({
        type:'post',
        url:'goods/getdelivermethod',
        success:function(result) {
            var oplist = JSON.parse(result);
            $("#slcdelivermethod").empty();
            $("#slcdelivermethod").append("<option value='0'>--请选择--</option>");
            for (var i = 0; i < oplist.length; i++) {
                var dep = oplist[i];
                var opt = "";
                opt += '<option value="' + dep.carCode + '">' + dep.carDesc + '</option>';
                $("#slcdelivermethod").append(opt);
            }
        }
    });
}
function loadtabverifydeliver() {
    $.ajax({
        type:'post',
        url:'goods/gettabdeliverbill',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            $('#tabverifydeliver').bootstrapTable("load", jsoOrder.data);
        }
    });
}
function doVerifyDeliver() {
    var queryrows = $("#tabverifydeliver").bootstrapTable("getSelections");
    if(queryrows.length == 0){
        alert("请选择要审核的调货单!");
        return;
    }
    var confirlist = "";
    var confirmmsg = "确认审核通过以下调货单:\n";
    var confirmmsgl = "以下调货单已审核过:\n";
    for(var i = 0 ;i<queryrows.length;i++){
        if(queryrows[i].isverify == '0'){
            confirmmsg +="   --  "+queryrows[i].gdbCode +"   \n";
            confirlist +=queryrows[i].gdbCode+",";
        }else{
            confirmmsgl += "   --  "+queryrows[i].gdbCode +"   \n";
        }
    }
    var cfm = confirm(confirmmsg+confirmmsgl);
    if(cfm == true){
        $.ajax({
            url:'goods/doverifydeliverbill',
            type:'post',
            contentType:'application/json; charset=UTF-8',
            data:JSON.stringify({'gdblist':confirlist}),
            success:function (data) {
                var re = JSON.parse(data);
                if(re.code = "0"){
                    loadtabverifydeliver();
                }else{
                    alert(re.data);
                }
            }
        });
    }
}
function loadtabnotesdeliver(){
    $.ajax({
        type:'post',
        url:'goods/getnotesdeliverbill',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            $('#tabnotesdeliver').bootstrapTable("load", jsoOrder.data);
        }
    });
}
function doaddnewinvoice() {
    var queryrows = $("#tabnotesdeliver").bootstrapTable("getSelections");
    if(queryrows.length == 0){
        alert("请选择要新建的调货单!");
        return;
    }
    if(queryrows.length>1){
        alert("一次只能新建一个调货发货单!");
        return;
    }
    $("#txbdelivercode").val(queryrows[0].gdbCode);
    $.ajax({
        type:'post',
        url:'goods/getwimethodlist',
        success:function(result) {
            var oplist = JSON.parse(result);
            $("#slcdwlmethod").empty();
            $("#slcdwlmethod").append("<option value='0'>--请选择--</option>");
            for (var i = 0; i < oplist.length; i++) {
                var dep = oplist[i];
                var opt = "";
                opt += '<option value="' + dep.wlCode + '">' + dep.wlMethod + '</option>';
                $("#slcdwlmethod").append(opt);
            }
        }
    });
}
function doCreatInvoice(){
    var strgdbcode = $("#txbdelivercode").val();
    var strwlmethod = $("#slcdwlmethod").val();
    var strwlcode = $("#txbdwlcode").val();
    $.ajax({
        type:'post',
        url:'goods/docreatdeliver',
        contentType:'application/json; charset=UTF-8',
        data:JSON.stringify({"gdbcode":strgdbcode,"wlmethod":strwlmethod,"wlcode":strwlcode}),
        success:function(result) {
            var oplist = JSON.parse(result);
            if(oplist.code == 0) {
                loadtabnotesdeliver();
                alert(oplist.data);
            }
        }
    });
}
function dosureinvoice() {
    $.ajax({
        type:'post',
        url:'goods/dopdaupdeliverinfo',
        contentType:'application/json; charset=UTF-8',
        data:JSON.stringify({"code":0,"data":{"deliverBill":"[{\"wlCode\":\"SF001\",\"wlMethod\":\"1\",\"invoiceName\":\"南山茂业思加图\",\"gmtModify\":\"\",\"gdiEmp\":\"S019JP01\",\"isverify\":\"1\",\"gmtCreat\":\"2018-02-11 10:47:02.0\",\"gdbCount\":\"1\",\"isinvoice\":\"1\",\"recipeStore\":\"S018JP\",\"intype\":\"2\",\"intypedesc\":\"店到店\",\"cargodesc\":\"正常调货\",\"recipeName\":\"华强北天虹思加图\",\"gdbEmp\":\"S018JP01\",\"cargotype\":\"2\",\"lgMethod\":\"快递\",\"gdbCode\":\"S018JP180211001\",\"invoiceStore\":\"S019JP\",\"isstorage\":\"1\"}]","deliverDtl":"[{\"gdbName\":\"棕-油皮牛皮革男皮鞋\",\"gdbSizeQty\":\"1\",\"gdbSize\":\"230\",\"gdbStyle\":\"37501\",\"gdbCode\":\"S018JP180211001\",\"gdbColor\":\"棕\",\"gdbSku\":\"BYW37501DS1AM7230\",\"gmtCreat\":\"\"}]"},"count":1}),
        success:function(result) {
            var oplist = JSON.parse(result);
            if(oplist.code == 0) {

                alert(oplist.data);
            }
        }
    });
}