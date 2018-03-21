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
            timer=setTimeout("doanalysisdata()", 120000);
        }
    });
}
function loadassalestotal(){
    $.ajax({
        type:'post',
        url:'analysis/getassalestotal?asque=moth',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            var ssdata = jsoOrder.data;
            for(var i =0;i<ssdata.length;i++){
                $("#astotalprice").html(ssdata[i].asTotalPrice);
                $("#astotalbill").html(ssdata[i].asTotalBill);
                $("#astotalnum").html(ssdata[i].asTotalNum);
                $("#asprocess").html(ssdata[i].asProcess);
            }

        }
    });
}
function loadassales() {
    var listnum = [];
    var listprice = [];
    $.ajax({
        type:'post',
        url:'analysis/getassales',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            $('#tabassalesmoth').bootstrapTable("load", jsoOrder);
            if(jsoOrder.length>0){
                for(var i=0;i<jsoOrder.length;i++){
                    listnum.push(parseInt(jsoOrder[i].asTotalNum));
                    listprice.push(parseFloat(jsoOrder[i].asTotalPrice));
                }
            }
            var myChartassales = echarts.init(document.getElementById('assales'),'macarons');
            var option = {
                title : {
                    'text':'月度销售数量/销售额'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['销量','销售额']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        data:['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']
                    }
                ],
                yAxis : [

                    {
                        type : 'value',
                        name : '金额',
                        axisLabel : {
                            formatter: '{value} ￥'
                        }
                    },
                    {
                        type : 'value',
                        name : '数量',
                        axisLabel : {
                            formatter: '{value} '
                        }
                    }
                ],
                series : [
                    {
                        name:'销量',
                        type:'bar',
                        yAxisIndex: 1,
                        data:listnum,
                        barGap:'1%',
                        markPoint : {
                            data : [{type : 'max', name: '最大值'}, {type : 'min', name: '最小值'}]
                        },
                        markLine : {
                            data : [{type : 'average', name: '平均值'}]
                        }
                    },
                    {
                        name:'销售额',
                        type:'bar',
                        data:listprice,
                        markPoint : {
                            data : [{type : 'max', name: '最大值'}, {type : 'min', name: '最小值'}]
                        },
                        markLine : {
                            data : [{type : 'average', name : '平均值'}]
                        }
                    }
                ]
            };
            myChartassales.setOption(option);
            window.onresize = function(){
                myChartassales.resize();
            }

        }
    });

}
function loadassalespie(){
    var listmoth = [];
    var listnum = [];
    $.ajax({
        type:'post',
        url:'analysis/getassales',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            $('#tabassalesmoth').bootstrapTable("load", jsoOrder);
            if(jsoOrder.length>0){
                for(var i=0;i<jsoOrder.length;i++){
                    var listv = {value:'',name:''};
                    listmoth.push(jsoOrder[i].asMoth);
                    listv.value=parseInt(jsoOrder[i].asTotalNum);
                    listv.name=jsoOrder[i].asMoth;
                    listnum.push(listv);
                }
            }
            var myChartassalespie = echarts.init(document.getElementById('assalespie'),'macarons');
            var optionpie = {
                title : {
                    'text':'月度销量占比',x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient : 'vertical',
                    x : 'right',
                    data:listmoth
                },
                toolbox: {
                    show : false,
                    feature : {
                        mark : {show: false},
                        dataView : {show: false, readOnly: false},
                        magicType : {
                            show: false,
                            type: ['pie', 'funnel'],
                            option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'left',
                                    max: 1548
                                }
                            }
                        },
                        restore : {show: false},
                        saveAsImage : {show: false}
                    }
                },
                calculable : true,
                series : [{
                    name: '月度销量',
                    type: 'pie',
                    radius: '50%',
                    center: ['50%', '55%'],
                    data: listnum
                }]
            };
            myChartassalespie.setOption(optionpie);
            window.onresize = function(){
                myChartassalespie.resize();
            }
        }
    });
}
function loadassalesstore() {
    var liststore = [];
    var listnum = [];
    var listprice = [];

    $.ajax({
        type:'post',
        url:'analysis/getassalesstore',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            if(jsoOrder.length>0){
                for(var i=0;i<jsoOrder.length;i++){
                    liststore.push(jsoOrder[i].gStyle);
                    listnum.push(parseInt(jsoOrder[i].gSalesNum));
                    listprice.push(parseFloat(jsoOrder[i].gSalesPrice));
                }
            }
            var myChartasstore = echarts.init(document.getElementById('assalesstore'),'macarons');
            var optionasstore = {
                title : {
                    'text':'各门店销售数量/销售额'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['销量','销售额']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        data:liststore
                    }
                ],
                yAxis : [

                    {
                        type : 'value',
                        name : '金额',
                        axisLabel : {
                            formatter: '{value} ￥'
                        }
                    },
                    {
                        type : 'value',
                        name : '数量',
                        axisLabel : {
                            formatter: '{value} '
                        }
                    }
                ],
                series : [
                    {
                        name:'销量',
                        type:'bar',
                        yAxisIndex: 1,
                        data:listnum,
                        barGap:'1%',
                        markPoint : {
                            data : [{type : 'max', name: '最大值'}, {type : 'min', name: '最小值'}]
                        },
                        markLine : {
                            data : [{type : 'average', name: '平均值'}]
                        }
                    },
                    {
                        name:'销售额',
                        type:'bar',
                        data:listprice,
                        markPoint : {
                            data : [{type : 'max', name: '最大值'}, {type : 'min', name: '最小值'}]
                        },
                        markLine : {
                            data : [{type : 'average', name : '平均值'}]
                        }
                    }
                ]
            };
            myChartasstore.setOption(optionasstore);
            window.onresize = function(){
                myChartasstore.resize();
            }
        }
    });
}
function loadtabasstoredtl(){
    $.ajax({
        type:'post',
        url:'analysis/getasstoredtl',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            $('#tabasstoredtl').bootstrapTable("load", jsoOrder);
            var i = jsoOrder.length;
            $("#asstoretopname").html(jsoOrder[0].asStore);
            $("#asstoretopnum").html(jsoOrder[0].asTotalPrice);
            $("#asstorelastname").html(jsoOrder[i-1].asStore);
            $("#asstorelastnum").html(jsoOrder[i-1].asTotalPrice);
        }
    });
}
function loadslcasstylestore() {
    $.ajax({
        type:'post',
        url:'goods/getstorelist?storetype=02',
        success:function(result) {
            var oplist = JSON.parse(result);
            $("#slcasstylestore").empty();
            $("#slcasstylestore").append("<option value='0'>--所有门店--</option>");
            for (var i = 0; i < oplist.length; i++) {
                var dep = oplist[i];
                var opt = "";
                opt += '<option value="' + dep.storeCode + '">' + dep.storeName + '</option>';
                $("#slcasstylestore").append(opt);
            }
        }
    });
}
function loadassalesstyle(){
    var liststyle = [];
    var listnum = [];
    var sdays = $("#slcasstyletime").val();
    var sstore =$("#slcasstylestore").val();
    $.ajax({
        type:'post',
        url:'analysis/getassalesstyle?sdays='+sdays+'&store='+sstore,
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            if(jsoOrder.length>0){
                for(var i=0;i<jsoOrder.length;i++){
                    liststyle.push(jsoOrder[i].gStyle);
                    listnum.push(parseInt(jsoOrder[i].gSalesNum));
                }
                var nu = jsoOrder.length;
                $("#asstyletopname").html(jsoOrder[0].gStyle);
                $("#asstyletopnum").html(jsoOrder[0].gSalesNum);
                $("#asstylelastname").html(jsoOrder[nu-1].gStyle);
                $("#asstylelastnum").html(jsoOrder[nu-1].gSalesNum);
            }
            var myChartastyle = echarts.init(document.getElementById('assalesstyle'),'macarons');
            var optionasstyle = {
                title : {
                    'text':'款式销量统计'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['销量']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        data:liststyle
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        name : '数量',
                        axisLabel : {
                            formatter: '{value} '
                        }
                    }
                ],
                series : [
                    {
                        name:'销量',
                        type:'bar',
                        data:listnum,
                        markPoint : {
                            data : [{type : 'max', name: '最大值'}, {type : 'min', name: '最小值'}]
                        },
                        markLine : {
                            data : [{type : 'average', name: '平均值'}]
                        }
                    }
                ]
            };
            myChartastyle.setOption(optionasstyle);
            window.onresize = function(){
                myChartastyle.resize();
            }
        }
    });
}
function loadasstyletop(){
    var liststyle = [];
    var listnums = [];
    $.ajax({
        type:'post',
        url:'analysis/getasstylenum?type=Desc',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            if(jsoOrder.length>0){
                for(var i=jsoOrder.length-1;i>=0;i--){
                    liststyle.push(jsoOrder[i].asStyle);
                    listnums.push(parseInt(jsoOrder[i].asNums));
                }
            }
            var myChartasstyletop = echarts.init(document.getElementById('asstyletop'),'macarons');
            var optionasstyletop = {
                title : {
                    'text':'款式销量 畅销款 TOP10'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['销量(件)']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'value',
                        name : '销量',
                        axisLabel : {
                            formatter: '{value} 件'
                        }
                    }

                ],
                yAxis : [
                    {
                        type : 'category',
                        data:liststyle
                    }
                ],
                series : [
                    {
                        name:'销量(件)',
                        type:'bar',
                        data:listnums,
                        markPoint : {
                            data : [{type : 'max', name: '最大值'}, {type : 'min', name: '最小值'}]
                        },
                        markLine : {
                            data : [{type : 'average', name : '平均值'}]
                        }
                    }
                ]
            };
            myChartasstyletop.setOption(optionasstyletop);
            window.onresize = function(){
                myChartasstyletop.resize();
            }
        }
    });
}
function loadasstylelast(){
    var liststyle = [];
    var listnums = [];
    $.ajax({
        type:'post',
        url:'analysis/getasstylenum?type=ASC',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            if(jsoOrder.length>0){
                for(var i=jsoOrder.length-1;i>=0;i--){
                    liststyle.push(jsoOrder[i].asStyle);
                    listnums.push(parseInt(jsoOrder[i].asNums));
                }
            }
            var myChartasstylelast = echarts.init(document.getElementById('asstylelast'),'macarons');
            var optionasstylelast = {
                title : {
                    'text':'款式销量 滞销款 LAST 10'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['销量(件)']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'value',
                        name : '销量',
                        axisLabel : {
                            formatter: '{value} 件'
                        }
                    }

                ],
                yAxis : [
                    {
                        type : 'category',
                        data:liststyle
                    }
                ],
                series : [
                    {
                        name:'销量(件)',
                        type:'bar',
                        data:listnums,
                        itemStyle: {
                            normal: {
                                color: '#FF8C00'
                            }
                        },
                        markPoint : {
                            data : [{type : 'max', name: '最大值'}, {type : 'min', name: '最小值'}]
                        },
                        markLine : {
                            data : [{type : 'average', name : '平均值'}]
                        }
                    }
                ]
            };
            myChartasstylelast.setOption(optionasstylelast);
            window.onresize = function(){
                myChartasstylelast.resize();
            }
        }
    });
}
function loadastry(){
    var liststyle = [];
    var listtimes = [];
    var listaveraget = [];
    $.ajax({
        type:'post',
        url:'analysis/getastry',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            if(jsoOrder.length>0){
                for(var i=0;i<jsoOrder.length;i++){
                    liststyle.push(jsoOrder[i].gStyle);
                    listtimes.push(parseInt(jsoOrder[i].gTimes));
                    listaveraget.push(parseFloat(jsoOrder[i].gAvg));
                }
            }
            var num = jsoOrder.length;
            $("#atstylemax").html(liststyle[0]);
            $("#atstylemaxnum").html(listtimes[0]);
            $("#atstylemin").html(liststyle[num-1]);
            $("#atstyleminnum").html(listtimes[num-1]);

            var maxstyle ;
            var minstyle ;
            var maxtim = listaveraget[0];
            var mintim = listaveraget[0];

            for(var i=1;i<listaveraget.length;i++){
                if(listaveraget[i]>maxtim){
                    maxstyle = liststyle[i];
                    maxtim = listaveraget[i];
                }
                if(listaveraget[i]<mintim){
                    minstyle = liststyle[i];
                    mintim = listaveraget[i];
                }
            }

            $("#attimemax").html(maxstyle);
            $("#attimemaxavg").html(maxtim);
            $("#attimemin").html(minstyle);
            $("#attimeminavg").html(mintim);

            var myChart = echarts.init(document.getElementById('tryanalysis'),'macarons');
            var option = {
                title : {
                    'text':'各款试穿次数/试穿时长'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['试穿次数(次)', '试穿时长(秒)']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        data:liststyle
                    }
                ],
                yAxis : [

                    {
                        type : 'value',
                        name : '次数',
                        axisLabel : {
                            formatter: '{value} t'
                        }
                    },
                    {
                        type : 'value',
                        name : '时长',
                        axisLabel : {
                            formatter: '{value} s'
                        }
                    }
                ],
                series : [
                    {
                        name:'试穿时长(秒)',
                        type:'bar',
                        yAxisIndex: 1,
                        data:listaveraget,
                        barGap:'1%',
                        markPoint : {
                            data : [{type : 'max', name: '最大值'}, {type : 'min', name: '最小值'}]
                        },
                        markLine : {
                            data : [{type : 'average', name: '平均值'}]
                        }
                    },
                    {
                        name:'试穿次数(次)',
                        type:'bar',
                        data:listtimes,
                        markPoint : {
                            data : [{type : 'max', name: '最大值'}, {type : 'min', name: '最小值'}]
                        },
                        markLine : {
                            data : [{type : 'average', name : '平均值'}]
                        }
                    }
                ]
            };
            myChart.setOption(option);
            window.onresize = function(){
                myChart.resize();
            }
        }
    });

}
function loadastrysales(){
    var liststyle = [];
    var listtimes = [];
    var listaveraget = [];
    var listnum = [];
    var listprice = [];
    $.ajax({
        type:'post',
        url:'analysis/getastrysales',
        success:function(result) {
            //alert(result);
            var jsoOrder = JSON.parse(result);
            if (jsoOrder.length > 0) {
                for (var i = 0; i < jsoOrder.length; i++) {
                    liststyle.push(jsoOrder[i].gStyle);
                    listtimes.push(parseInt(jsoOrder[i].gTimes));
                    listaveraget.push(parseFloat(jsoOrder[i].gAvg));
                    listnum.push(parseInt(jsoOrder[i].gSalesNum));
                    listprice.push(parseFloat(jsoOrder[i].gSalesPrice));
                }
            }
            var myChart = echarts.init(document.getElementById('trysalesanalysis'), 'macarons');
            var option = {
                title : {
                    'text':'各款销量试穿比 销量/试穿次数'
                },
                tooltip: {
                    trigger: 'axis'
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        magicType: {show: true, type: ['line', 'bar']},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                calculable: true,
                legend: {
                    data: ['销量',  '试穿次数(次)']
                },
                xAxis: [
                    {
                        type: 'category',
                        data: liststyle
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        name: '数量',
                        axisLabel: {
                            formatter: '{value} n'
                        }
                    }

                ],
                series: [
                    {
                        name: '销量',
                        type: 'bar',

                        data: listnum,
                        barGap:'1%',
                        markPoint: {
                            data: [
                                {type: 'max', name: '最大值'},
                                {type: 'min', name: '最小值'}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'}
                            ]
                        }
                    },
                    {
                        name: '试穿次数(次)',
                        type: 'bar',

                        stack: '总量',

                        data: listtimes
                    }
                ]
            };

            myChart.setOption(option);
            window.onresize = function(){
                myChart.resize();
            }
        }
    });
}
function loadattimes(){
    var listtimes = [];
    var listnums = [];
    $.ajax({
        type:'post',
        url:'analysis/getattimes',
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            if(jsoOrder.length>0){
                for(var i=0;i<jsoOrder.length;i++){
                    listtimes.push(jsoOrder[i].atHour);
                    listnums.push(parseInt(jsoOrder[i].attimes));
                }
            }
            var myChartattimes = echarts.init(document.getElementById('attimes'),'macarons');
            var optionattimes = {
                title : {
                    'text':'时间段内试穿流量'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['试穿(次)']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',

                        data:listtimes
                    }

                ],
                yAxis : [
                    {
                        type : 'value',
                        name : '试穿',
                        axisLabel : {
                            formatter: '{value} 次'
                        }
                    }
                ],
                series : [
                    {
                        name:'试穿(次)',
                        type:'line',
                        smooth:true,
                        itemStyle: {normal: {areaStyle: {type: 'default'}}},
                        data:listnums,
                        markPoint : {
                            data : [{type : 'max', name: '最大值'}, {type : 'min', name: '最小值'}]
                        },
                        markLine : {
                            data : [{type : 'average', name : '平均值'}]
                        }
                    }
                ]
            };
            myChartattimes.setOption(optionattimes);
            window.onresize = function(){
                myChartattimes.resize();
            }
        }
    });
}
function loadasstylestore(){
    var sdays = $("#slcasst").val();
    $.ajax({
        type:'post',
        url:'analysis/getasstylestoretop?sdays='+sdays,
        success:function(result){
            //alert(result);
            var jsoOrder = JSON.parse(result);
            $('#tabasstylestoretop').bootstrapTable("load", jsoOrder);
        }
    });
    $.ajax({
        type:'post',
        url:'analysis/getasstylestorelast?sdays='+sdays,
        success:function(result){
            //alert(result);
            var jsoOrder2 = JSON.parse(result);
            $('#tabasstylestorelast').bootstrapTable("load", jsoOrder2);
        }
    });
}