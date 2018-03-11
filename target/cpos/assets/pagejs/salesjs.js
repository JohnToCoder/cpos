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