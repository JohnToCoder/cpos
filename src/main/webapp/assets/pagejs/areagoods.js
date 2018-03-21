function querytabaregoods() {
    $('#tipag').hide();
    var strsku = $("#txballagsku").val();
    var strstyle = $("#txballagstyle").val();
    var strsize = $("#txballagsize").val();
    $.ajax({
        url:'goods/doqueryallag',
        type:'post',
        contentType:'application/json; charset=UTF-8',
        data:JSON.stringify({'sku':strsku,'style':strstyle,'size':strsize}),
        success:function (data) {
            var re = JSON.parse(data);
            if(re.code == "0"){
                if(re.count>0){
                    $('#tabaregoods').bootstrapTable("load", re.data);
                }else{
                    $('#tipag').html("Tip:库存没有找到需要的货品").show();
                }
            }else{
                alert(re.data);
            }
        }
    });
}
var modalplsq =eval('([])');
var flag = 0;
function rowchangesizeqty(sku,gCount,obj){
    var jrow = {"agSku":'',"agSizeQty":''};
    var inputcur = $(obj);
    var patrn = /^[0-9]*$/;
    var agnum = inputcur.val();

    if(!patrn.test(agnum)){
        inputcur.css('border','1px solid #ffa200');
        inputcur.attr('title',"配送数字").tooltip('fixTitle').tooltip('show');
        return;
    }
    if(agnum > gCount){
        inputcur.css('border','1px solid #ffa200');
        inputcur.attr('title',"配送数超出范围").tooltip('fixTitle').tooltip('show');
        return;
    }
    inputcur.css('border','1px solid #04ae00');
    inputcur.attr('title',"OK").tooltip('fixTitle').tooltip('show');
    jrow.agSku = sku;
    jrow.agSizeQty = agnum;
    modalplsq.push(jrow);
}
function loadslcagwarecode(){
    $.ajax({
        type:'post',
        url:'goods/getstorelist?storetype=03',
        success:function(result){
            var oplist = JSON.parse(result);
            $("#slcagwarecode").empty();
            $("#slcagwarecode").append("<option value='0'>--请选择--</option>");
            for(var i=0;i<oplist.length;i++){
                var dep = oplist[i];
                var opt="";
                opt+='<option value="'+dep.storeCode+'">'+dep.storeName+'</option>';
                $("#slcagwarecode").append(opt);
            }
        }
    });
}
function loadslcagstorecode() {
    $.ajax({
        type:'post',
        url:'goods/getstorelist?storetype=02',
        success:function(result) {
            var oplist = JSON.parse(result);
            $("#slcagstorecode").empty();
            $("#slcagstorecode").append("<option value='0'>--请选择--</option>");
            for (var i = 0; i < oplist.length; i++) {
                var dep = oplist[i];
                var opt = "";
                opt += '<option value="' + dep.storeCode + '">' + dep.storeName + '</option>';
                $("#slcagstorecode").append(opt);
            }
        }
    });
}
function doaddnewag(){
    var strwarecode = $("#slcagwarecode").val();
    if(strwarecode == '0'){
        $("#slcagwarecode").attr('title',"请选择发货仓库").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strstorecode = $("#slcagstorecode").val();
    if(strstorecode == '0'){
        $("#slcagstorecode").attr('title',"请选择收货门店").tooltip('fixTitle').tooltip('show');
        return;
    }
    $.ajax({
        url:'goods/doaddnewag',
        type:'post',
        contentType:'application/json; charset=UTF-8',
        data:JSON.stringify({"warecode":strwarecode,"storecode":strstorecode}),
        async:false,
        success:function(data){
            var strre = JSON.parse(data);
            if(strre.code == '0'){
                $('#tipaglist').html("Tip:请配置配货单："+strre.data+"货品详情").show();
                $('#txbagcode').val(strre.data);
                $('#txbagcount').val(0);

            }
        }
    });
}
function doaddnewagdtl() {
    var stragcode = $('#txbagcode').val();
    var stragcount = $('#txbagcount').val();
    if(modalplsq.length>0) {
        $.ajax({
            url: 'goods/doaddnewagdtl',
            type: 'post',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify({"agdtl": JSON.stringify(modalplsq), "agcode": stragcode, "agcount": stragcount}),
            async: false,
            success: function (data) {
                var strre = JSON.parse(data);
                if (strre.code == '0') {
                    loadtabagdtl();
                    modalplsq.splice(0, modalplsq.length);
                    flag=1;
                } else {
                    alert(strre.data);
                }
            }
        });
    }
}
function loadtabagdtl(){
    var stragcode = $('#txbagcode').val();
    $.ajax({
        url:'goods/getagdtlbycode?agcode='+stragcode,
        type:'post',
        async:false,
        success:function(data){
            var strre = JSON.parse(data);
            if(strre.code == '0'){
                $('#tabaregoodslist').bootstrapTable("load", strre.data);
                var dtlnum =0;
                for(var i =0;i<strre.data.length;i++){
                    dtlnum = dtlnum+parseInt(strre.data[i].agSizeQty);
                }
                $('#txbagcount').val(dtlnum);
            }else{
                alert(strre.data);
            }
        }
    });
}
function delaglist(strsku,sizqqty) {
    var stragcode = $('#txbagcode').val();
    var agcount = $('#txbagcount').val();
    var newcount = parseInt(agcount)-parseInt(sizqqty);
    $('#txbagcount').val(newcount);
    $.ajax({
        url:'goods/dodelagdtlbysku?agsku='+strsku+'&agcode='+stragcode,
        type:'post',
        async:false,
        success:function(data){
            var strre = JSON.parse(data);
            if(strre.code == '0'){
                loadtabagdtl();
            }else{
                alert(strre.data);
            }
        }
    });
}
function doqueryagbill() {
    var stragcode = $('#txbagcode').val();
    var stragcount = $('#txbagcount').val();
    if(flag == 1) {
        $.ajax({
            url: 'goods/doqueryagbill?agcount=' + stragcount + '&agcode=' + stragcode,
            type: 'post',
            async: false,
            success: function (data) {
                var strre = JSON.parse(data);
                if (strre.code == '0') {
                    $("#txbagcode").val("");
                    $("#txbagcount").val("");
                    $('#tipaglist').hide();
                    loadtabagbill();
                    loadtabagdtl();
                    querytabaregoods();
                    flag = 0;
                } else {
                    alert(strre.data);
                }
            }
        });
    }
}
function loadtabagbill(){
    $.ajax({
        url:'goods/doqueryagbilllist?agcode=',
        type:'post',
        async:false,
        success:function(data){
            var strre = JSON.parse(data);
            if(strre.code == '0'){
                $('#tabagbilllist').bootstrapTable("load", strre.data);
            }else{
                alert(strre.data);
            }
        }
    });
}
function searchagbill(){
    var agcode = $('#txbagcodesearch').val();
    $.ajax({
        url:'goods/doqueryagbilllist?agcode='+agcode,
        type:'post',
        async:false,
        success:function(data){
            var strre = JSON.parse(data);
            if(strre.code == '0'){
                $('#tabagbilllist').bootstrapTable("load", strre.data);
            }else{
                alert(strre.data);
            }
        }
    });
}
function loadtabagbillw(){
    $.ajax({
        url:'goods/doqueryagbillw',
        type:'post',
        async:false,
        success:function(data){
            var strre = JSON.parse(data);
            if(strre.code == '0'){
                $('#tabagdeliver').bootstrapTable("load", strre.data);
            }else{
                alert(strre.data);
            }
        }
    });
}
function doaddneagi(){
    var queryrows = $("#tabagdeliver").bootstrapTable("getSelections");
    if(queryrows.length == 0){
        alert("请选择要新建的配货单!");
        return;
    }
    if(queryrows.length>1){
        alert("一次只能新建一个配货发货单!");
        return;
    }
    $("#txbwiagcode").val(queryrows[0].agCode);
    $.ajax({
        type:'post',
        url:'goods/getwimethodlist',
        success:function(result) {
            var oplist = JSON.parse(result);
            $("#slcagwimethod").empty();
            $("#slcagwimethod").append("<option value='0'>--请选择--</option>");
            for (var i = 0; i < oplist.length; i++) {
                var dep = oplist[i];
                var opt = "";
                opt += '<option value="' + dep.wlCode + '">' + dep.wlMethod + '</option>';
                $("#slcagwimethod").append(opt);
            }
        }
    });
}
function doCreatAgInvoice(){
    var stragcode = $("#txbwiagcode").val();
    var strwimethod = $("#slcagwimethod").val();
    if(strwimethod == '0'){
        $("#slcagwimethod").attr('title',"请选择配送方式").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strwlcode = $("#txbagwlcode").val();
    if(isEmpty(strwlcode)){
        $("#txbagwlcode").attr('title',"物流单号要填呢").tooltip('fixTitle').tooltip('show');
        return;
    }
    $.ajax({
        type:'post',
        url:'goods/doupwiagbill',
        contentType:'application/json; charset=UTF-8',
        data:JSON.stringify({"agcode":stragcode,"wimethod":strwimethod,"wlcode":strwlcode}),
        success:function(result) {
            var oplist = JSON.parse(result);
            if(oplist.code == 0) {
                loadwareinvoicetab();
                alert(oplist.data);
            }
        }
    });

}

function delagbilllist(agcode ) {
    var cfm = confirm("确认删除配货单："+agcode);
    if(cfm == true){
        $.ajax({
            url:'goods/dodelagbillbycode?agcode='+agcode,
            type:'post',
            success:function (data) {
                var strre = JSON.parse(data);
                if(strre.code == '0'){
                    loadtabagbill();
                }else{
                    alert(strre.data);
                }
            }
        });
    }
}
function editagbill(agcode) {
    $('#txbagcode').val(agcode);
    loadtabagdtl();
}