$(function() {

    var categories = [
        { category: "COMMON" }
    ];

    var rowSize = Math.ceil($(document).width() / 130);

    $("#jsGrid").jsGrid({
        width: "60%",
        height: "55%",

        filtering: true,
        inserting: true,
        editing: true,
        sorting: true,
        paging: true,
        autoload: true,

        pageSize: rowSize,
        pageButtonCount: 5,

        deleteConfirm: "Do you really want to delete the word?",

        controller: {
            loadData: function(filter) {
                return $.ajax({
                    type: "POST",
                    url: "/words",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify(filter)
                });
            },
            insertItem: function(item) {
                return $.ajax({
                    type: "POST",
                    url: "/word",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify(item)
                });
            },
            updateItem: function(item) {
                return $.ajax({
                    type: "POST",
                    url: "/word",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify(item)
                });
            },
            deleteItem: function(item) {
                return $.ajax({
                    type: "DELETE",
                    url: "/word",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify(item)
                });
            },
        },

        fields: [
            { name: "word", type: "text", width: 150, validate: "required" },
            { name: "translation", type: "text", width: 200, validate: "required" },
            { name: "category", type: "select", items: categories, valueField: "category", textField: "category" },
            { type: "control" }
        ]
    });
});