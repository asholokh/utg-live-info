$.ajaxSetup({
  cache : false
//Disable caching of AJAX responses
});

function showProgress(container) {
    container.innerHTML = "<div style='border-width: 0;' class='panel-body text-center'><img src='ajax-loader.gif' /> <span>Дані завантажуються. Зачекайте, будь-ласка...</span></div>";
}

function prepareAndDrawChart(dataset) {
    showProgress(document.getElementById(dataset));
    console.log("Drawing chart for all data...");
    createAndDrawChart(document.getElementById(dataset), dataset);
}

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
        prepareAndDrawChart('all');
    });
    $('a[href="#thisYear"]').on('shown.bs.tab', function (e) {
        prepareAndDrawChart('thisYear');
    });
    $('a[href="#thisMonth"]').on('shown.bs.tab', function (e) {
        prepareAndDrawChart('thisMonth');
    });
});


function activateTab(tab){
    $('.nav-tabs a[href="#' + tab + '"]').tab('show');
};