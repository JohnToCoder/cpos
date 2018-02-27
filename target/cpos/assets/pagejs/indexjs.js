/**
 * Created by john on 2017/4/21.
 */
function initpages() {
    addTabs({id: '0', title: '首页', close: true, url: 'firstpages'});
}
function pageWareInvoice() {
    addTabs({id:'21',title:'仓库发货',close: true,url:'goods/gdwareinvoice'});
}
function pageStoreRecipe() {
    addTabs({id:'22',title:'门店收货',close: true,url:'goods/gdstorerecipe'});
}
function pageStoreWare() {
    addTabs({id:'23',title:'门店库存',close: true,url:'goods/gdstoreware'});
}
function pageStoreInvoice() {
    addTabs({id:'24',title:'店转货单',close: true,url:'goods/gdstoredeliver'});
}
function pageStoreCheck() {
    addTabs({id:'25',title:'门店盘点',close: true,url:'goods/gdstorecheck'});
}
function pageStoreMag() {
    addTabs({id:'31',title:'单位管理',close:true,url:'geers/pgstoremag'});
}
function pageEmployeeMag(){
    addTabs({id:'32',title:'人员管理',close:true,url:'geers/pgempmag'});
}
function pageGeersMag() {
    addTabs({id:'33',title:'基础配置',close:true,url:'geers/pggeersmag'});
}
function pageSalesBill() {
    addTabs({id:'41',title:'销售开单',close:true,url:'sales/pgsalesbill'});
}
function pageSalesCount() {
    addTabs({id:'42',title:'销售统计',close:true,url:'sales/pgsalescount'});
}
function pageSalesGeer() {
    addTabs({id:'43',title:'销售配置',close:true,url:'sales/pgsalesgeer'});
}
function PagesUpdateAPP() {
    addTabs({id:'9',title:'上传APP',close: true,url:'pages/updateapp'});
}

function dltUpApp() {
    var dltrows = $('#applist').bootstrapTable("getSelections");
    if(dltrows.length == 0){
        alert("请先选择需要删除的APP!");
        return;
    }
    var ids = '';
    for(var i=0;i<dltrows.length;i++){
        ids += dltrows[i]['id']+',';
    }
    ids = ids.substring(0, ids.length - 1);
    var msg="您确定要删除选定APP吗?";
    if (confirm(msg) == true) {
        $.ajax({
            url:'cpos/dltapp',
            type:'post',
            contentType:'application/json; charset=UTF-8',
            data:JSON.stringify({ids:ids}),
            success: function (returndata) {
                $('#applist').bootstrapTable('refresh', {url: 'pages/getappversion'});
                alert(returndata);
            }
        });
    }
}
var progre = 1;
var total = 100;
var initstr = '';
function upapp(){
    $('#upappTip').hide();
    var strAppType = $('#apptype').val();
    var strVersionCode = $('#versioncode').val();
    var strVersionMess = $('#appmessages').val();
    if(strAppType =="" || strAppType ==null){
        $('#upappTip').html("Tip:请输入APP类型包括,手机端,平板端").show();
        return ;
    }
    if(strVersionCode=="" || strVersionCode ==null){

        $('#upappTip').html("Tip:版本号不能为空!").show();
        return;
    }
    if(strVersionMess =="" || strVersionMess ==null){

        $('#upappTip').html("Tip:更新信息不能为空!").show();
        return;
    }

    var formData = new FormData($('#upappform')[0]);
    $.ajax({
        url: 'cpos/uploadapp',
        type: 'POST',
        data: formData,
        async: true,
        cache: false,
        contentType: false,
        processData: false,
        beforeSend:function(){
            $('#progressbar').modal("show");
            initstr = setInterval("changed('pronum')",300);
        },
        success: function (returndata) {

            $('#applist').bootstrapTable('refresh', {url: 'pages/getappversion'});
            alert(returndata);
        },
        complete:function(){
            $('#progressbar').modal('hide');
            $('#pronum').css({width:'1%'});
        },
        error: function (returndata) {
            alert(returndata);
        }  });
}
//进度条的控制
function changed(id) {
    $('#'+id).css({width:progre+'%'});
    if(progre ==total){
        clearInterval(initstr);
        progre = 1;
    }
    progre= progre+1;
}
