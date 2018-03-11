function loadtabtrydata() {
    $.ajax({
        type:'post',
        url:'analysis/getanalysisdata',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            $('#tabtrydata').bootstrapTable("load", jsoOrder.data);
        }
    });
}
function doanalysisdata(){
    $.ajax({
        type:'post',
        url:'analysis/doanalysisdata',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            if(jsoOrder.count>0){
                loadtabtrydata();
            }
        },
        complete:function(jqXHR, textStatus){
            timer=setTimeout("doanalysisdata()", 20000);
        }
    });
}