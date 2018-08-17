$(document).ready(function() {
    getProfiles();
    getModes();
    getCurrentUser();
});

function getCurrentUser() {
    jQuery.ajax({
        type: "GET",
        url: "/user",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, status, jqXHR) {
            $("#algorithm_select").val(data.profile.name);
            $("#mode_select").val(data.repetitionMode.mode);
            replaceDiv();
        },
        error: function (jqXHR, status) {
            // error handler
        }
    });
}

function getProfiles() {
    jQuery.ajax({
        type: "GET",
        url: "/profiles",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, status, jqXHR) {
            data.forEach(function (item, i, arr) {
                $('#algorithm_select').append($('<option>', {
                    value: item.name,
                    text: item.description
                }));
            });
        },
        error: function (jqXHR, status) {
            // error handler
        }
    });
}

function getModes() {
    jQuery.ajax({
        type: "GET",
        url: "/modes",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, status, jqXHR) {
            data.forEach(function(item, i, arr) {
                $('#mode_select').append($('<option>', {
                    value: item.mode,
                    text : item.value
                }));
            });
        },
        error: function (jqXHR, status) {
            // error handler
        }
    });
}

function saveMetadata() {
    var modeSelect = document.getElementById("mode_select");
    var mode = modeSelect.options[modeSelect.selectedIndex].value;

    var algorithmSelect = document.getElementById("algorithm_select");
    var algorithm = algorithmSelect.options[algorithmSelect.selectedIndex].value;

    jQuery.ajax({
        async: false,
        type: "POST",
        url: "/user",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            'profile': algorithm,
            'repetitionMode': mode
        }),
        dataType: "json",
        success: function (data, status, jqXHR) {
            alert('SUCCESS');
        },
        error: function (jqXHR, status) {
        }
    });

}

function replaceDiv() {
    if($('#basic').css('display')!='none'){
        $('#loading').show();
        $('#basic').hide();
    }else if($('#loading').css('display')!='none'){
        $('#basic').show();
        $('#loading').hide();
    }
}


