$(function () {
    //数据请求
    $.ajax({
        //请求方式
        type: "GET",
        //请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        url: "/shandong-admin/sys/malfunction/getProcessingStatus",
        dataType: "json",
        success: function (result) {
            if (result.code == 0) {
                //数据填充
                for (var i = 0; i < result.data.data.length; i++) {
                    $('tbody').append("<tr><td>" + result.data.data[i].power_supply + "</td><td>" + result.data.data[i].tai_area_name + "</td><td>" + (result.data.data[i].pending + result.data.data[i].processing + result.data.data[i].processed) + "</td><td>" + result.data.data[i].pending + "</td><td>" + result.data.data[i].processing + "</td><td>" + result.data.data[i].processed + "</td></tr>");
                }
                //设置高度，进行滚动
                if($('tbody').height()>110){
                    $('tbody').height(110);
                }
                var total = 0, pendingCount = 0, processingCount = 0, processedCount = 0;
                for (var i = 0; i < $('tbody').children().size(); i++) {
                    var obj = $('tbody').children()[i];
                    total += parseInt(obj.children[2].innerHTML);
                    pendingCount += parseInt(obj.children[3].innerHTML);
                    processingCount += parseInt(obj.children[4].innerHTML);
                    processedCount += parseInt(obj.children[5].innerHTML);
                }
                $('thead tr:eq(2)').children()[1].innerHTML = total;
                $('thead tr:eq(2)').children()[2].innerHTML = pendingCount;
                $('thead tr:eq(2)').children()[3].innerHTML = processingCount;
                $('thead tr:eq(2)').children()[4].innerHTML = processedCount;
                $('tfoot').append("<tr><th colspan=6 style='text-align:right;vertical-align:middle'>统计时间："+result.data.date.startTime.substring(0,10) + " 至 " + result.data.date.endTime.substring(0,10)+"</th></tr>");
                //默认加载第一行数据
                var legendData = ['待处理','处理中','处理完成'];
                var seriesData = [];
                var obj=$('tbody').children()[0];
                var selected = {};
                for (var i = 0; i < legendData.length; i++) {
                    name=legendData[i];
                    count=obj.children[3+i].innerHTML;
                    seriesData.push({
                        name: name,
                        value: count
                    });

                    selected[name] =  3;
                }
                var myChart = echarts.init(document.getElementById("container_buttom"));
                var app = {};
                option = null;

                option = {
                    title: {
                        text: '处理状态分布统计',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c}"
                    },
                    legend: {
                        type: 'scroll',
                        orient: 'vertical',
                        right: 250,
                        top: 20,
                        bottom: 20,
                        data: legendData,
                        selected: selected
                    },
                    series: [
                        {
                            name: '处理状态',
                            type: 'pie',
                            radius: '55%',
                            center: ['40%', '50%'],
                            data: seriesData,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                if (option && typeof option === "object") {
                    myChart.setOption(option, true);
                }
                $('tbody').children().on("click", function () {
                    var legendData = ['待处理','处理中','处理完成'];
                    var seriesData = [];
                    var selected = {};
                    for (var i = 0; i < legendData.length; i++) {
                        name=legendData[i];
                        count=$(this).children()[i+3].innerHTML;
                        seriesData.push({
                            name: name,
                            value: count
                        });

                        selected[name] =  3;
                    }
                    var myChart = echarts.init(document.getElementById("container_buttom"));
                    var app = {};
                    option = null;

                    option = {
                        title: {
                            text: '缺陷等级分布统计',
                            x: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c}"
                        },
                        legend: {
                            type: 'scroll',
                            orient: 'vertical',
                            right: 250,
                            top: 20,
                            bottom: 20,
                            data: legendData,
                            selected: selected
                        },
                        series: [
                            {
                                name: '缺陷类型',
                                type: 'pie',
                                radius: '55%',
                                center: ['40%', '50%'],
                                data: seriesData,
                                itemStyle: {
                                    emphasis: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    };
                    if (option && typeof option === "object") {
                        myChart.setOption(option, true);
                    }
                });
            }
        },
        //请求失败，包含具体的错误信息
        error: function (e) {
            console.log("请求失败！");
        }
    })
});