$.ajaxSetup({
    // Disable caching of AJAX responses - so every time will have actual weather data.
    cache: false
});

/**
 * Shows progress panel.
 * Data and error panels are hidden.
 */
function showProgress() {
    $("#panelProgress").css("display", "block");
    $("#panelError").css("display", "none");
    $("#panelData").css("display", "none");
}

/**
 * Shows Error panel.
 * Data and progress panels are hidden.
 */
function showError() {
    $("#panelProgress").css("display", "none");
    $("#panelError").css("display", "block");
    $("#panelData").css("display", "none");
}

/**
 * Shows Data panel.
 * Progress and error panels are hidden.
 */
function showData() {
    $("#panelProgress").css("display", "none");
    $("#panelError").css("display", "none");
    $("#panelData").css("display", "block");
}

/**
 * Initialization after DOM is built.
 */
$(document).ready(function () {
    createAndDrawChart();
});
