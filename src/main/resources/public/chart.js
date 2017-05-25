var chart = null;
var months = ['Січень','Лютий','Березень','Квітень','Травень','Червень','Липень','Серпень','Вересень','Жовтень','Листопад','Грудень'];
var monthsCased = ['Січня','Лютого','Березня','Квітня','Травня','Червня','Липня','Серпня','Вересня','Жовтня','Листопада','Грудня'];

/**
 * Creates and populates a data table, instantiates the chart, passes the data into it.
 */
function createAndDrawChart(chartContainer, groupBy) {
    var arr = new Array(['Year', 'УГВ', 'Укрнафта', 'Інші', { role: 'annotation' }]);
    $.ajax({
        type: 'GET',
        url: '/utg-live-info/allData',
        dataType: 'json',
        success: function (data) {
            var dataGroupedByMonth = {};
            var dates = new Array();
            $.each(data, function(index, element) {
                var date = new Date(element.date);
                //FIXME: Consider ability in JavaScript to use Enumeration here instead of just String
                if (groupBy == "year") {
                    date.setDate(1);
                }
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
                if (groupBy == "year") {
                    arr.push([months[element.getMonth()]+' '+element.getFullYear(), dataGroupedByMonth[element].ugvData, dataGroupedByMonth[element].ukrnaftaData, dataGroupedByMonth[element].othersData, '']);
                } else if (groupBy == "month") {
                    arr.push([element.getDate() + ' ' + monthsCased[element.getMonth()], dataGroupedByMonth[element].ugvData, dataGroupedByMonth[element].ukrnaftaData, dataGroupedByMonth[element].othersData, '']);
                }
            });

            var dataForChart = google.visualization.arrayToDataTable(arr);

            drawChart(dataForChart, getChartOptions(), chartContainer);
            console.log("Chart is drawn.")
        },
        error: function(errorResult) {
         //   showError();
        }
    });
}

/**
 * Creates and populates a data table, instantiates the chart, passes the data into it.
 */
function createAndDrawChartForThisMonth(chartContainer, groupBy) {
    var arr = new Array(['Month', 'УГВ', 'Укрнафта', 'Інші', { role: 'annotation' }]);
    $.ajax({
        type: 'GET',
        url: '/utg-live-info/monthData',
        dataType: 'json',
        success: function (data) {
            var dataGroupedByMonth = {};
            var dates = new Array();
            $.each(data, function(index, element) {
                var date = new Date(element.date);
                //FIXME: Consider ability in JavaScript to use Enumeration here instead of just String
                if (groupBy == "year") {
                    date.setDate(1);
                }
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
                if (groupBy == "year") {
                    arr.push([months[element.getMonth()]+' '+element.getFullYear(), dataGroupedByMonth[element].ugvData, dataGroupedByMonth[element].ukrnaftaData, dataGroupedByMonth[element].othersData, '']);
                } else if (groupBy == "month") {
                    arr.push([element.getDate() + ' ' + monthsCased[element.getMonth()], dataGroupedByMonth[element].ugvData, dataGroupedByMonth[element].ukrnaftaData, dataGroupedByMonth[element].othersData, '']);
                }
            });

            var dataForChart = google.visualization.arrayToDataTable(arr);

            drawChart(dataForChart, getChartOptions(), chartContainer);
            console.log("Chart is drawn.")
        },
        error: function(errorResult) {
            //   showError();
        }
    });
}

function getChartOptions() {
    return {
        //FIXME: Here should not display current date - but two days before. Because UTG does not provide data update as of today but with two-days delay.
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

function drawChart(data, options, chartContainer) {
    // Instantiate and draw our chart, passing in some options.
    chart = new google.visualization.ColumnChart(chartContainer);
    chart.draw(data, options);
}

function getCurrentDate() {
    var today = new Date();
    var day = today.getDate();
    var month = today.getMonth();
    var year = today.getFullYear();

    return day + " " + monthsCased[month] + " " + year;
}