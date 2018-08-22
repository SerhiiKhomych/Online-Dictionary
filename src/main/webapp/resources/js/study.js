$(document).ready(function() {
    getWord();
});

$(function() {
    $("input:radio").change(function () {
        studyWordWithAttempt();
    });

    $("#translation").keyup(function(event){
        if(event.keyCode == 13){
            studyWord();
        }
    });
});

function studyWord() {
    $("#word").hide();
    $('#loading').show();
    $('#loading_margin').show();

    jQuery.ajax({
        type: "POST",
        url: "/study/word",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            'word': $('#word').text(),
            'translation': $('#translation').val()
        }),
        dataType: "json",
        success: function (data, status, jqXHR) {
            $('#loading').hide();
            $('#loading_margin').hide();
            if (data.success == true) {
                $('#smile').show();
                $('#translation').hide();
                $("#studyButton").hide();

                setTimeout(function () {
                    getWord();
                }, 400);
            } else {
                $("#word").show();
                replaceDiv();
            }
        },
        error: function (jqXHR, status) {
        }
    });
}

function studyWordWithAttempt() {
    jQuery.ajax({
        async: false,
        type: "POST",
        url: "/study/word",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            'word': $('#word').text(),
            'translation': getRadioButtonCheckedValue()
        }),
        dataType: "json",
        success: function (data, status, jqXHR) {
            for (i = 0; i < 3; i++) {
                if ($("#result".concat(i+1)).text() == '-') {
                    $("#translation".concat(i+1)).css("color", "red")
                } else {
                    $("#translation".concat(i+1)).css("color", "green")
                }
            }

            setTimeout(function () {
                replaceDiv();
                getWord();
            }, 100);
        },
        error: function (jqXHR, status) {
        }
    });
}

function getWord() {
    $('#smile').hide();
    $('#loading').show();
    $('#loading_margin').show();
    $("#word").hide();
    $("#studyButton").show();
    $('#translation').show().val('').focus();

    for (i = 0; i < 3; i++) {
        $("#translation".concat(i+1)).css("color", "black");
    }

    setRadioButtonCheckedValue();

    jQuery.ajax({
        type: "GET",
        url: "/word",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, status, jqXHR) {
            $('#loading').hide();
            $('#loading_margin').hide();
            $("#word").show().css("color", "black").text(data.wordToStudy.word);

            var arr = data.additionalWords;
            arr.forEach(function(item, i, arr) {
                $("#translation".concat(i+1)).text(item.word.translation);
                if (item.correct == false) {
                    $("#result".concat(i+1)).css("color", "red").css("font-size", "20pt").text('-').hide();
                } else {
                    $("#result".concat(i+1)).css("color", "green").css("font-size", "20pt").text('+').hide();
                }
            });
        },
        error: function (jqXHR, status) {
            // error handler
        }
    });
}

function replaceDiv() {
    if($('#basic').css('display')!='none'){
        $('#extended').show();
        $('#basic').hide();
    }else if($('#extended').css('display')!='none'){
        $('#basic').show();
        $('#extended').hide();
    }
}

function getRadioButtonCheckedValue() {
    var checked_rb = $("input[type='radio'].radioBtnClass:checked").val();
    return $("#translation".concat(checked_rb)).text();
}

function setRadioButtonCheckedValue() {
    $("input[type=radio][class=radioBtnClass]").prop("checked",false);
}