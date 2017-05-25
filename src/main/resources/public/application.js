$.ajaxSetup({
  cache : false
//Disable caching of AJAX responses
});

/**
 * Initialization after DOM is built.
 */
$(document).ready(function() {
    activateTab('all');
    console.log('Document loaded. Loading Google Charts library...');
    google.charts.load('current', {'packages':['corechart', 'bar']});
    google.charts.setOnLoadCallback(function() {
        console.log('Library loaded.');
        $('#myTab a:first').tab('show');
    });
    $('a[href="#all"]').on('shown.bs.tab', function (e) {
        document.getElementById('all').innerHTML = '';
        console.log("Drawing chart for all data...");
        createAndDrawChart(document.getElementById('all'), 'year');
    });
    $('a[href="#thisYear"]').on('shown.bs.tab', function (e) {
        document.getElementById('thisYear').innerHTML = '';
        console.log("Drawing chart for data for this year...");
        createAndDrawChart(document.getElementById('thisYear'), 'year');
    });
    $('a[href="#thisMonth"]').on('shown.bs.tab', function (e) {
        document.getElementById('thisMonth').innerHTML = '';
        console.log("Drawing chart for data for this month...");
        createAndDrawChartForThisMonth(document.getElementById('thisMonth'), 'month');
    });
});


function activateTab(tab){
    $('.nav-tabs a[href="#' + tab + '"]').tab('show');
};