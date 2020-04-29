$(function () {
//数据请求
    $.ajax({
        //请求方式
        type: "GET",
        //请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        url: "/shandong-admin/sys/malfunction/getDefectLevel",
        dataType: "json",
        success: function (result) {
            if (result.code == 0) {
                //数据填充
                for (var i = 0; i < result.data.data.length; i++) {
                    $('tbody').append("<tr><td>" + result.data.data[i].power_supply + "</td><td>" + result.data.data[i].tai_area_name + "</td><td>" + result.data.data[i].total + "</td><td>" + result.data.data[i].levelTwo + "</td><td>" + result.data.data[i].levelTree + "</td><td>" + result.data.data[i].levelFour + "</td><td>"+result.data.data[i].levelFive+"</td><td></td><td></td></tr>");
                }
                //设置高度，进行滚动
                if($('tbody').height()>110){
                    $('tbody').height(110);
                }
                var boxTotal = 0, levelOne = 0, levelTwo = 0, levelThree = 0, levelFour = 0, defectTotal = 0,inspectedMeterCount=0,
                    defectRate = 0.0;
                for (var i = 0; i < $('tbody').children().size(); i++) {
                    var obj = $('tbody').children()[i].children;
                    obj[7].innerHTML = parseInt(obj[3].innerHTML) + parseInt(obj[4].innerHTML) + parseInt(obj[5].innerHTML) + parseInt(obj[6].innerHTML);
                    //缺陷率
                    if(result.data.data[i].inspectedMeter==0) {
                        obj[8].innerHTML ="0%";
                    }else {
                        obj[8].innerHTML = (parseInt(obj[7].innerHTML) / (result.data.data[i].inspectedMeter)).toFixed(2) * 100 + "%";
                    }
                    boxTotal = boxTotal + parseInt(obj[2].innerHTML);
                    levelOne = levelOne + parseInt(obj[3].innerHTML);
                    levelTwo = levelTwo + parseInt(obj[4].innerHTML);
                    levelThree = levelThree + parseInt(obj[5].innerHTML);
                    levelFour = levelFour + parseInt(obj[6].innerHTML);
                    defectTotal = defectTotal + parseInt(obj[7].innerHTML);
                    inspectedMeterCount+=result.data.data[i].inspectedMeter;
                }
                $('thead tr:eq(2)').children()[1].innerHTML = boxTotal;
                $('thead tr:eq(2)').children()[2].innerHTML = levelOne;
                $('thead tr:eq(2)').children()[3].innerHTML = levelTwo;
                $('thead tr:eq(2)').children()[4].innerHTML = levelThree;
                $('thead tr:eq(2)').children()[5].innerHTML = levelFour;
                $('thead tr:eq(2)').children()[6].innerHTML = defectTotal;
                if(inspectedMeterCount==0){
                    $('thead tr:eq(2)').children()[7].innerHTML ="0%";
                }else {
                    $('thead tr:eq(2)').children()[7].innerHTML = (defectTotal/inspectedMeterCount).toFixed(2)*100+"%";
                }
                $('tfoot').append("<tr><th colspan=9 style='text-align:right;vertical-align:middle'>统计时间：" + result.data.date.startTime.substring(0, 10) + " 至 " + result.data.date.endTime.substring(0, 10) + "</th></tr>");

                //默认加载第一行数据
                var legendData = ['紧急缺陷','严重缺陷','一般缺陷(轻度)','一般缺陷(轻微)'];
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

                    selected[name] =  4;
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

                $('tbody').children().on("click", function () {
                    var legendData = ['紧急缺陷','严重缺陷','一般缺陷(轻度)','一般缺陷(轻微)'];
                    var seriesData = [];
                    var selected = {};
                    for (var i = 0; i < legendData.length; i++) {
                        name=legendData[i];
                        count=$(this).children()[i+3].innerHTML;
                        seriesData.push({
                            name: name,
                            value: count
                        });

                        selected[name] =  4;
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
    });
});