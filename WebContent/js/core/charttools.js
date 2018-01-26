function ChartDataObj(yVal,colorVal){
	this.y = yVal;
	this.color=colorVal;
};
/*同一纬度颜色相同的方法*/
function getCommonChart(xAixs,seriesName,xAixsData,params,url,chartId,chartType,isLengend,infoId,colors){
		if($("#"+chartId).highcharts()!=undefined){
			$("#"+chartId).highcharts().destroy();
		}
		$("#"+infoId).hide();
		AjaxCommonTools(params,url,'json',true,
			function(json){
			if("success" == json.operStatus){
				var chart = getColumuChart(chartId,chartType,isLengend,colors);
				for(var key in json.data){
					var columnDetailText = [];
					var colorsText = [];
					for(var i = 0 ; i < xAixsData.length; i++){
						var data = json.data[key];
						var index = xAixsData[i];
						columnDetailText[i] = 1*(data[index]==undefined?0:data[index]);
						colorsText[i] = colors[index];
					}
					chart.addSeries({name:seriesName[key],data:columnDetailText});
					//chart.series[key].name=seriesName[key];
					//chart.series[key].setData(columnDetailText);
	            }
	            chart.xAxis[0].setCategories(xAixs);
	         }else{
	         	$("#"+infoId).show();
	         }
		});
}
/*组装数据*/
function asseCommonChart(xAixs,seriesName,xAixsData,params,url,chartId,chartType,flag,infoId,colors){
	if($("#"+chartId).highcharts()!=undefined){
		$("#"+chartId).highcharts().destroy();
	}
	$("#"+infoId).hide();
	AjaxCommonTools(params,url,'json',true,
			function(json){
			if("success" == json.operStatus){
				for(var key in json.data){
					var charData = new Array();
					var jsonStr = "[";
					var columnDetailText = [];
					for(var i = 0 ; i < xAixsData.length; i++){
						var data = json.data[key];
						var index = xAixsData[i];
						var yData = 1*(data[index]==undefined?0:data[index]);
						var tempChartData = new ChartDataObj(yData,colors[i]);
						charData[i] = tempChartData;
					}
					asseChart(chartId,chartType,xAixs,seriesName,charData);
	            }
	         }else{
	         	$("#"+infoId).show();
	         }
	});
}
/*生成chart表格*/
function asseChart(chartId,chartType,xAixs,seriesName,charData){
	var chart = $('#'+chartId).highcharts({
        chart: {
            type: chartType
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: xAixs
        },
        yAxis: {
            title: {
                text: ''
            }
        },
        plotOptions: {
            column: {
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#9C9Efc',
                    style: {
                        fontWeight: 'bold'
                    },
                    formatter: function() {
                        return this.y +'%';
                    }
                }
            }
        },
        tooltip: {
            formatter: function() {
                var point = this.point,
                    s = this.x +':<b>'+ this.y +'% market share</b><br/>';
                if (point.drilldown) {
                    s += 'Click to view '+ point.category +' versions';
                } else {
                    s += 'Click to return to browser brands';
                }
                return s;
            }
        },
        series: [{
            name: seriesName,
            data: charData,
            color: 'white'
        }],
        exporting: {
            enabled: false
        }
    })
    .highcharts();
}

function getCombineChart(reportTitle,xAixs,yAxisName,seriesName,xAixsData,params,url,chartId,chartType,isLengend,infoId,colors,isCount){
	if($("#"+chartId).highcharts()!=undefined){
		$("#"+chartId).highcharts().destroy();
	}
	$("#tablelist").remove();
	$("#"+infoId).hide();
	AjaxCommonTools(params,url,'json',true,
		function(json){
		if("success" == json.operStatus){
			var table=$("<table id='tablelist' class='tablelist' style='width:90%;margin:5px auto;'>");
			var thead = $("<thead>");
			var thr = $("<tr>");
			thead.append(thr);
			for(var i = 0; i < reportTitle.length; i++){
				var th = $("<th>");
				th.append(reportTitle[i]);
				thr.append(th);
			}
			if(isCount){
				var th = $("<th>");
				th.append("总计");
				thr.append(th);
			}
			table.append(thead);
			var chart = getCombinationChart(chartId,isLengend,colors,yAxisName[0]);
			var tbody = $("<tbody>");//创建表格tbody节点
			for(var key in json.data){
				var columnDetailText = [];
				var tbr = $("<tr>");//创建表格tr节点
				var tdContent = $("<td>").append(seriesName[key]);
				tbr.append(tdContent);
				var totalCount = 0;
				for(var i = 0 ; i < xAixsData.length; i++){
					var data = json.data[key];
					var index = xAixsData[i];
					columnDetailText[i] = 1*(data[index]==undefined?0:data[index]);
					//开始绘制表格数据
					var td = $("<td>");//创建表格td节点
					td.append(columnDetailText[i]);//添加文本内容
					tbr.append(td);
					totalCount+=columnDetailText[i];
				}
				if(isCount){
					var td = $("<td>");//创建表格td节点
					td.append(totalCount);//添加文本内容
					tbr.append(td);
				}
				chart.addSeries({type:chartType[key],name:seriesName[key],data:columnDetailText});
				tbody.append(tbr);
            }
			if(yAxisName[1]!=undefined){
				chart.addAxis({
		            title: {
		                text: yAxisName[1]
		            },
		            opposite: true
		        });
			}
            chart.xAxis[0].setCategories(xAixs);
            //获取tbody创建真个表格
            tbody.insertAfter(thead);
            table.insertAfter($("#sjtj"));
         }else{
         	$("#"+infoId).show();
         }
	});
}

function getPieChart(chartId){
	var chart = new Highcharts.Chart({
        chart:{
            renderTo:chartId,
            type:'pie', //显示类型 饼图
            margin:[0,0,0,0],
            spacingTop:0,
            spacingBottom:0
        },
        colors: ['#7cb5ec',  '#f15c80', '#90ed7d', '#f7a35c', '#8085e9', 
   '#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
        title:{
            text:'',
            floating:true,
            margin:0,
            style:{fontSize:'8px'}
        },
        tooltip: {
            pointFormat: '',
            valueSuffix: ''
        },
        subtitle:{
        	floationg:true
        },
        plotOptions: {
            pie: {
                dataLabels: {
                	distance:2,
                    enabled: true,
                    verticalAlign:'bottom',
                    format: '{point.y:.1f}%',
                    style:{"fontSize": "10px"}
                },
                minSize:50,
                innerSize: 150
            }
    	},
        series: [{
            name: "MOA",
            data: []
    	}]
    });
	return chart;
}


/*多列同色展示数据*/
function getColumuChart(chartId,chartType,isLegend){
	var chart = new Highcharts.Chart({
        chart: {
    		renderTo:chartId,
    		 type: chartType,
            marginBottom: 30
        },
        title: {
            text: '',
            floating:true,
            margin:0,
            style:{fontSize:'1px'}
        },
        legend: {
        	layout: 'vertical',
            align: 'right',
            verticalAlign: 'right',
            borderWidth: 0,
        	enabled: isLegend,
        	margin:0,
        	style: { fontWeight: 'lighter' }
    	},
        xAxis: {
            labels:{
    			rotation: -30
    		},
            crosshair: true
        },
        yAxis: {
            min: 0,
            floor: 0,
            title: {
                text: '',
                margin:15,
                style:{
        			fontSize:10
        		}
            },
            labels:{
            	 align: 'center',
            	 x:0,
            	 y:10
            }
        },
        tooltip: {
        	borderWidth: 0,
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.0f}%</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    style: {
                        fontWeight: 'bold'
                    },
                    formatter: function() {
                        return this.y +'%';
                    }
                }
            }
        }
        
    });
	return chart;
}


function getCombinationChart(chartId,isLengend,colors,yAxisName){
	var chart = new Highcharts.Chart({
        chart: {
        	renderTo:chartId
        },
        title: {
            text: ''
        },
        colors:colors,
        xAxis: [{
            crosshair: true
        }],
        yAxis: [{ // Primary yAxis
        	floor: 0,
            title: {
                text: yAxisName
            }
        }],
        tooltip: {
            shared: true,
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.0f}</b></td></tr>',
            footerFormat: '</table>',
            borderWidth: 0
        },
        legend: {
            //layout: 'vertical',
            align: 'center',
            enabled: isLengend,
            //x: 120,
            verticalAlign: 'bottom',
            margin:0,
            //y: 100,
            floating: false
        }
    });
	return chart;
}