$(function () {
    var countData =[];
    $.ajax({
        //请求方式
        type: "GET",
        //请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        url: "/shandong-admin/sys/acceptance/acceptanceCount",
        dataType: "json",
        success: function (result) {
            if (result.code == 0) {
                if (countData != undefined) {
                    json = JSON.parse(result.data);
                    countData.push(parseInt(json.pendingCount));
                    countData.push(parseInt(json.defectCount));
                    countData.push(parseInt(json.passCount));
                    console.log(countData);
                    var myChart = echarts.init(document.getElementById("container_buttom"));
                    option = null;
                    option = {
                        title: {
                            text: '待验收、存在缺陷、验收完成状态统计',
                            x: 'center'
                        },
                        color: ['#3398DB'],
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                            }
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        xAxis: [
                            {
                                type: 'category',
                                data: ['待验收', '存在缺陷', '验收完成'],
                                axisTick: {
                                    alignWithLabel: true
                                }
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value'
                            }
                        ],
                        series: [
                            {
                                name: '数量统计',
                                type: 'bar',
                                barWidth: '10%',
                                data: countData,
                                itemStyle: {
                                    //通常情况下：
                                    normal: {
                                        //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组中的颜色
                                        color: function (params) {
                                            var colorList = [
                                                '#0099FF', '#FF6600', '#A25EA2', '#6600FF', '#00FFFF',
                                                '#5EA279', '#3FAF98', '#00EE30'
                                            ];
                                            return colorList[params.dataIndex];
                                        },
                                        // color: '#70C0F5',  //设置所有柱状图颜色
                                    },
                                    // //鼠标悬停时：
                                    // emphasis: {
                                    //     shadowBlur: 10,
                                    //     shadowOffsetX: 0,
                                    //     shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    // }
                                }
                            },

                        ]
                    };
                    ;
                    if (option && typeof option === "object") {
                        myChart.setOption(option, true);
                    };
                }
            }
        },
        //请求失败，包含具体的错误信息
        error: function (e) {
            console.log("请求失败！");
        }
    });

    //var data = null;
    var legendData = [];
    var seriesData = [];
    var selected = {};
    $.ajax({
        //请求方式
        type: "GET",
        //请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        url: "/shandong-admin/sys/defectnamerecord/getRecords",
        dataType: "json",
        success: function (result) {
            if (result.code == 0) {
                var locatioin=0;
                for(var j=0;j<result.data.length;j++){
                    if(result.data[j].count>0){
                        locatioin=j+1;
                    }
                }
                for (var i = 0; i < result.data.length; i++) {
                    name=result.data[i].name;
                    legendData.push(name);
                    seriesData.push({
                        name: name,
                        value: result.data[i].count
                    });
                    if(locatioin==0){
                        selected[name] = i ;
                    }else{
                        selected[name] = i < locatioin;
                    }
                }
                var myChart = echarts.init(document.getElementById("container_top"));
                var app = {};
                option = null;

                option = {
                    title: {
                        text: '缺陷说明统计',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        type: 'scroll',
                        orient: 'vertical',
                        right: 0,
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
                            center: ['30%', '50%'],
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
            }
        },
        //请求失败，包含具体的错误信息
        error: function (e) {
            console.log("请求失败！");
        }
    });
});