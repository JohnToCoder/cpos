var mgemptag = 0;
var mgstoretag = 0;
function loadGeerEmployee() {
    $.ajax({
        type:'post',
        url:'geer/getemplist',

        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            $('#geerEmployee').bootstrapTable("load", jsoOrder);
        }
    });
};
function initSelectStore() {
    $.ajax({
        url:'geer/getstorelist',
        type:'post',
        success:function (result) {
            var oplist = JSON.parse(result);
            $("#selectstore").empty();
            $("#selectstore").append("<option value='0'>--请选择--</option>");
            for(var i=0;i<oplist.length;i++){
                var dep = oplist[i];
                var opt="";
                opt+='<option value="'+dep.storeCode+'">'+dep.storeName+'</option>';
                $("#selectstore").append(opt);
            }
        }
    });
};
function initSelectDuty() {
    $.ajax({
        url:'geer/getdutylist',
        type:'post',
        success:function (result) {
            var oplist = JSON.parse(result);
            $("#selectduty").empty();
            $("#selectduty").append("<option value='0'>--请选择--</option>");
            for(var i=0;i<oplist.length;i++){
                var dep = oplist[i];
                var opt="";
                opt+='<option value="'+dep.dutyCode+'">'+dep.dutyName+'</option>';
                $("#selectduty").append(opt);
            }
        }
    });
};
function doEditEmp() {
    var queryrows = $('#geerEmployee').bootstrapTable("getSelections");
    if(queryrows.length == 0){
        alert("请选择要编辑的员工!");
        return;
    }else if(queryrows.length>1){
        alert("请选择一个员工编辑!");
        return;
    }
    mgemptag = 1;
    $("#selectstore").val( queryrows[0].storeCode);
    $("#selectduty").val( queryrows[0].empDuty);
    $("#empmgcode").val(queryrows[0].empCode);
    $("#empmgname").val(queryrows[0].empName);
    $("#empmgtel").val(queryrows[0].empTel);
    $("#empmgemail").val(queryrows[0].empMail);
};
function doSaveEmp(){
    var empjson = {"empCode":'',"empName":'',"storeCode":'',"empDuty":'',"empMail":'',"empTel":'',"isquery":'0'};
    var strstorecode = $("#selectstore").val();
    if(strstorecode == '0'){
        $("#selectstore").attr('title',"请选择员工所在单位").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strempduty = $("#selectduty").val();
    if(strempduty == '0'){
        $("#selectduty").attr('title',"请选择员工职能").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strempcode = $("#empmgcode").val();
    var strempname = $("#empmgname").val();
    if(strempname == ""){
        $("#empmgname").attr('title',"请输入员工姓名").tooltip('fixTitle').tooltip('show');
        return;
    }
    var stremptel = $("#empmgtel").val();
    if(stremptel == ""){
        $("#empmgtel").attr('title',"输入手机号方便联系呢").tooltip('fixTitle').tooltip('show');
        return;
    }
    var regtel =new RegExp("^[1][3,4,5,7,8][0-9]{9}$");
    if (!regtel.test(stremptel)) {
        $("#empmgtel").attr('title',"手机号不符合11位规则").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strempmail = $("#empmgemail").val();
    var regmail = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
    if(strempmail!=""){
    if(!regmail.test(strempmail)){
        $("#empmgemail").attr('title',"邮箱不符合规则，少了必要的@ ").tooltip('fixTitle').tooltip('show');
        return;
    }}

    empjson.empCode = strempcode;
    empjson.empName = strempname;
    empjson.storeCode = strstorecode;
    empjson.empDuty = strempduty;
    empjson.empMail = strempmail;
    empjson.empTel = stremptel;

    if(mgemptag == 1){
        $.ajax({
            url:'geer/doupdateemp',
            type:'post',
            contentType:'application/json; charset=UTF-8',
            data:JSON.stringify({"empinfo":JSON.stringify(empjson)}),
            async:false,
            error: function(es){
                alert("提交失败!\n"+es);
            },
            success:function(data){
                var strre = JSON.parse(data);
                if(strre.code = '0'){
                    loadGeerEmployee();
                    $("#selectstore").val(0);
                    $("#selectduty").val(0);
                    $("#empmgcode").val("");
                    $("#empmgname").val("");
                    $("#empmgtel").val("");
                    $("#empmgemail").val("");
                }
                alert(strre.data);
            }
        });
        mgemptag = 0;
    }else{
        $.ajax({
            url:'geer/doupempinfo',
            type:'post',
            contentType:'application/json; charset=UTF-8',
            data:JSON.stringify({"empinfo":JSON.stringify(empjson)}),
            async:false,
            error: function(es){
                alert("提交失败!\n"+es);
            },
            success:function(data){
                var strre = JSON.parse(data);
                if(strre.code = '0'){
                    loadGeerEmployee();
                    $("#selectstore").val(0);
                    $("#selectduty").val(0);
                    $("#empmgcode").val("");
                    $("#empmgname").val("");
                    $("#empmgtel").val("");
                    $("#empmgemail").val("");
                }
                alert(strre.data);
            }
        });
    }

};
function doCancelEmp() {
    $("#selectstore").val(0);
    $("#selectduty").val(0);
    $("#empmgcode").val("");
    $("#empmgname").val("");
    $("#empmgtel").val("");
    $("#empmgemail").val("");
    $('#geerEmployee').bootstrapTable("resetView");
};
function doQueryEmp() {
    var queryrows = $('#geerEmployee').bootstrapTable("getSelections");
    if(queryrows.length == 0){
        alert("请选择要审核的员工信息列表!");
        return;
    }
    var confirlist = "";
    var confirmmsg = "确认审核通过以下员工:\n";
    var confirmmsgl = "以下员工已审核过:\n";
    for(var i = 0 ;i<queryrows.length;i++){
        if(queryrows[i].isquery == '0'){
            confirmmsg +="   --  "+queryrows[i].empCode +"  --  "+queryrows[i].empName+"   \n";
            confirlist +=queryrows[i].empCode+",";
        }else{
            confirmmsgl += "   --  "+queryrows[i].empCode +"  --  "+queryrows[i].empName+"   \n";
        }
    }
    var cfm = confirm(confirmmsg+confirmmsgl);
    if(cfm == true){
        $.ajax({
            url:'geer/doqueryemps',
            type:'post',
            contentType:'application/json; charset=UTF-8',
            data:JSON.stringify({'emplist':confirlist}),
            success:function (data) {
                var re = JSON.parse(data);
                if(re.code == '0'){
                    loadGeerEmployee();
                }else{
                    alert(re.data);
                }
            }
        });
    }
};
function doDeleteEmp() {
    var queryrows = $('#geerEmployee').bootstrapTable("getSelections");
    if(queryrows.length == 0){
        alert("请选择要删除的员工信息列表!");
        return;
    }
    var confirlist = "";
    var confirmmsg = "确认删除以下员工:\n";

    for(var i = 0 ;i<queryrows.length;i++){
        confirmmsg +="   --  "+queryrows[i].empCode +"  --  "+queryrows[i].empName+"   \n";
        confirlist +=queryrows[i].empCode+",";
    }
    var cfm = confirm(confirmmsg);
    if(cfm == true){
        $.ajax({
            url:'geer/delempinfo',
            type:'post',
            contentType:'application/json; charset=UTF-8',
            data:JSON.stringify({'emplist':confirlist}),
            success:function (data) {
                var re = JSON.parse(data);
                if(re.code == '0'){
                    loadGeerEmployee();
                    alert(re.data);
                }else{
                    alert(re.data);
                }
            }
        });
    }
};
function loadGeerStoreTab() {
    $.ajax({
        type:'post',
        url:'geer/getstoretab',

        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            $('#geerStore').bootstrapTable("load", jsoOrder.data);
        }
    });
};
function initSelectUpStore() {
    $.ajax({
        url:'geer/getupstorelist',
        type:'post',
        success:function (result) {
            var oplist = JSON.parse(result);
            $("#selectupstore").empty();
            $("#selectupstore").append("<option value='0'>--请选择--</option>");
            for(var i=0;i<oplist.length;i++){
                var dep = oplist[i];
                var opt="";
                opt+='<option value="'+dep.storeCode+'">'+dep.storeName+'</option>';
                $("#selectupstore").append(opt);
            }
        }
    });
};
function initSelectStype() {
    $.ajax({
        url:'geer/getstypelist',
        type:'post',
        success:function (result) {
            var oplist = JSON.parse(result);
            $("#selectstype").empty();
            $("#selectstype").append("<option value='0'>--请选择--</option>");
            for(var i=0;i<oplist.length;i++){
                var dep = oplist[i];
                var opt="";
                opt+='<option value="'+dep.ctCode+'">'+dep.ctDesc+'</option>';
                $("#selectstype").append(opt);
            }
        }
    });
};

function doEditStore() {
    var queryrows = $('#geerStore').bootstrapTable("getSelections");
    if(queryrows.length == 0){
        alert("请选择要编辑的单位!");
        return;
    }else if(queryrows.length>1){
        alert("请选择一个单位编辑!");
        return;
    }
    mgstoretag = 1;
    $("#mgscode").val(queryrows[0].storeCode);
    $("#selectupstore").val( queryrows[0].storePcode);
    $("#selectstype").val( queryrows[0].storeType);
    $("#mgsname").val(queryrows[0].storeName);
    $("#mgsarea").val(queryrows[0].storeArea);
    $("#mgsaddr").val(queryrows[0].storeAddr);
    $("#mgstel").val(queryrows[0].storeTel);
};
function doCancelmgs() {
    $("#selectupstore").val(0);
    $("#selectstype").val(0);
    $("#mgscode").val("");
    $("#mgsname").val("");
    $("#mgsarea").val("");
    $("#mgsaddr").val("");
    $("#mgstel").val("");
};
function doSavemgs(){
    var mgsjson = {"storeCode":'',"storeName":'',"storePcode":'',"storeArea":'',"storeAddr":'',"storeTel":'',"storeType":''};
    var strStoreCode = $("#mgscode").val();
    if(strStoreCode ==""){
        $("#mgscode").attr('title',"输入单位编号").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strStoreName = $("#mgsname").val();
    if(strStoreName == ""){
        $("#mgsname").attr('title',"输入单位名称").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strStorePcode = $("#selectupstore").val();
    if(strStorePcode == '0'){
        $("#selectupstore").attr('title',"请选择上级单位").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strStoreType = $("#selectstype").val();
    if(strStoreType == '0'){
        $("#selectstype").attr('title',"请选择单位类型").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strStoreAddr = $("#mgsaddr").val();
    if(strStoreAddr == ""){
        $("#mgsaddr").attr('title',"请输入单位地址").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strStoreTel = $("#mgstel").val();
    if(strStoreTel == ""){
        $("#mgstel").attr('title',"请输入单位电话").tooltip('fixTitle').tooltip('show');
        return;
    }
    var strStoreArea = $("#mgsarea").val();
    if(strStoreArea == ""){
        $("#mgsarea").attr('title',"请输入单位区域").tooltip('fixTitle').tooltip('show');
        return;
    }
    mgsjson.storeCode = strStoreCode;
    mgsjson.storeName =strStoreName;
    mgsjson.storePcode = strStorePcode;
    mgsjson.storeArea = strStoreArea;
    mgsjson.storeAddr = strStoreAddr;
    mgsjson.storeTel = strStoreTel;
    mgsjson.storeType = strStoreType;

    if(mgstoretag == 1){
        $.ajax({
            url:'geer/doupdatestore',
            type:'post',
            contentType:'application/json; charset=UTF-8',
            data:JSON.stringify({"mgsinfo":JSON.stringify(mgsjson)}),
            async:false,
            error: function(es){
                alert("提交失败!\n"+es.toString());
            },
            success:function(data){
                var strre = JSON.parse(data);
                if(strre.code = '0'){
                    loadGeerStoreTab();
                    $("#selectupstore").val(0);
                    $("#selectstype").val(0);
                    $("#mgscode").val("");
                    $("#mgsname").val("");
                    $("#mgsarea").val("");
                    $("#mgsaddr").val("");
                    $("#mgstel").val("");
                }
                alert(strre.data);
            }
        });
        mgstoretag == 0;
    }else if(mgstoretag == 0){
        $.ajax({
            url:'geer/doupstoretab',
            type:'post',
            contentType:'application/json; charset=UTF-8',
            data:JSON.stringify({"mgsinfo":JSON.stringify(mgsjson)}),
            async:false,
            error: function(es){
                alert("提交失败!\n"+es.toString());
            },
            success:function(data){
                var strre = JSON.parse(data);
                if(strre.code = '0'){
                    loadGeerStoreTab();
                    $("#selectupstore").val(0);
                    $("#selectstype").val(0);
                    $("#mgscode").val("");
                    $("#mgsname").val("");
                    $("#mgsarea").val("");
                    $("#mgsaddr").val("");
                    $("#mgstel").val("");
                }
                alert(strre.data);
            }
        });
    }
};