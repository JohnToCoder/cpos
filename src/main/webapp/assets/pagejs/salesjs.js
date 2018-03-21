function loadtabsalesgeer() {
    $.ajax({
        type:'post',
        url:'sales/getsalesprice',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            $('#tabsalesgeer').bootstrapTable("load", jsoOrder.data);
        }
    });
}
function rowchangelistp(strsku,obj) {
    var inputcur = $(obj);
    var patrn = /^[0-9]*$/;
    var nprice = inputcur.val();
    if(!patrn.test(nprice)){
        inputcur.attr('title',"价格为数字").tooltip('fixTitle').tooltip('show');
        return;
    }
    $.ajax({
        url:'sales/doupsalesprice',
        type:'post',
        contentType:'application/json; charset=UTF-8',
        data:JSON.stringify({"ptype":'1',"strsku":strsku,"listprice":nprice,"discount":'1',"curprice":nprice}),
        async:false,
        error: function(es){
            alert("提交失败!\n"+es);
        },
        success:function(data){
            var strre = JSON.parse(data);
            loadtabsalesgeer();

        }
    });
}
function rowchangedisc(strsku,listp,obj){
    var inputcur = $(obj);
    var patrn = /^(1|0+(.[0-9]{2})?)$/;
    var dis = inputcur.val();
    if(listp=="0"){
        alert("请先输入价格再加折扣！");
        return;
    }
    if(!patrn.test(dis)){
        inputcur.attr('title',"折扣数字为小于1的两位小数或者为1").tooltip('fixTitle').tooltip('show');
        return;
    }
    var nprice = listp*dis;
    $.ajax({
        url:'sales/doupsalesprice',
        type:'post',
        contentType:'application/json; charset=UTF-8',
        data:JSON.stringify({"ptype":'2',"strsku":strsku,"listprice":listp,"discount":dis,"curprice":nprice}),
        async:false,
        error: function(es){
            alert("提交失败!\n"+es);
        },
        success:function(data){
            var strre = JSON.parse(data);
            loadtabsalesgeer();

        }
    });
}
function loadtabsalesbills(){
    $.ajax({
        type:'post',
        url:'sales/getsalesbills?billno=&startdt=&enddt=',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            $('#tabsalesbill').bootstrapTable("load", jsoOrder.data);
        }
    });
}
function doQuerySalesBills() {
    var strbillno = $('#txbsalesbn').val();
    var strstartdt = $('#sbnstartdt').val();
    var strenddt = $('#sbnenddt').val();
    if(strenddt<strstartdt){
        $("#sbnenddt").attr('title',"结束时间不能小于开始时间").tooltip('fixTitle').tooltip('show');
        return;
    }
    $.ajax({
        type:'post',
        url:'sales/getsalesbills?billno='+strbillno+'&startdt='+strstartdt+'&enddt='+strenddt,
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            $('#tabsalesbill').bootstrapTable("load", jsoOrder.data);
        }
    });
}
function loadstoresalesinfo(){
    $.ajax({
        type:'post',
        url:'sales/getstoremothreport',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            var ssdata = jsoOrder.data;
            for(var i =0;i<ssdata.length;i++){
                $("#sstotalprice").html(ssdata[i].ssTotalPrice);
                $("#sstotalbill").html(ssdata[i].ssTotalBill);
                $("#sstotalnum").html(ssdata[i].ssTotalNum);
                $("#ssprocess").html(ssdata[i].ssProcess);
            }

        }
    });
}