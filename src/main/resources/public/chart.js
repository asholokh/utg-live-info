var googleChartsApiLoaded = false;
var chart = null;
// Load the Visualization API and the corechart package.
google.charts.load('visualization', {'packages':['corechart', 'bar']});

var months = ['Січень','Лютий','Березень','Квітень','Травень','Червень','Липень','Серпень','Вересень','Жовтень','Листопад','Грудень'];
var monthsCased = ['Січня','Лютого','Березня','Квітеня','Травеня','Червеня','Липеня','Серпеня','Вересеня','Жовтеня','Листопада','Грудня'];
var arr = new Array(['Year', 'УГВ', 'Укрнафта', 'Інші', { role: 'annotation' }]);

/**
 * Creates and populates a data table, instantiates the chart, passes the data into it.
 */
function createAndDrawChart() {
    showProgress();
    $.ajax({
        type: 'GET',
        url: '/utg-live-info/allData',
        dataType: 'json',
        success: function (data) {
            var dataGroupedByMonth = {};
            var dates = new Array();
            $.each(data, function(index, element) {
                var date = new Date(element.date);
                date.setDate(1);
                date.setHours(0);
                date.setMinutes(0);
                date.setSeconds(0);
                date.setMilliseconds(0);
                var ugvData = element.ukrgazvydobuvannya;
                var ukrnaftaData = element.ukrnafta;
                var othersData = element.others;
                if (dataGroupedByMonth[date] == null) {
                    dataGroupedByMonth[date] = {ugvData, ukrnaftaData, othersData};
                    dates.push(date);
                } else {
                    var dataByMonth = dataGroupedByMonth[date];
                    ugvData = dataByMonth.ugvData+element.ukrgazvydobuvannya;
                    ukrnaftaData = dataByMonth.ukrnaftaData+element.ukrnafta;
                    othersData = dataByMonth.othersData+element.others;
                    dataGroupedByMonth[date] = {ugvData, ukrnaftaData, othersData};
                }
            });

            dates.sort(function(a,b) {
                return a - b;
            });
            $.each(dates, function(index, element) {
                arr.push([months[element.getMonth()]+' '+element.getFullYear(), dataGroupedByMonth[element].ugvData, dataGroupedByMonth[element].ukrnaftaData, dataGroupedByMonth[element].othersData, '']);
            });

            var dataForChart = google.visualization.arrayToDataTable(arr);

            drawChart(dataForChart, getChartOptions());

            showData();
        },
        error: function(errorResult) {
            showError();
        }
    });
}

function getChartOptions() {
    return {
        title: "Надходження від газовидобувних підприємств України станом на " + getCurrentDate(),
        height: 500,
        isStacked: true,
        hAxis: {
            title: 'Період'
        },
        vAxis: {
            title: 'Тис. м3',
        },
        bar: {
            groupWidth: '70%',
        }
    };
}

function drawChart(data, options) {
    // Instantiate and draw our chart, passing in some options.
    chart = new google.visualization.ColumnChart(document.getElementById('visualization'));
    chart.draw(data, options);
}

function getCurrentDate() {
    var today = new Date();
    var day = today.getDate();
    var month = today.getMonth();
    var year = today.getFullYear();

    return day + " " + monthsCased[month] + " " + year;
}