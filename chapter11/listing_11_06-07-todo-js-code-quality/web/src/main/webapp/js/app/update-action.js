function updateToDoItem(row) {
    var id = $("#toDoItemId_" + row).val();
    var name = $("#toDoItemName_" + row).val();

    $.ajax({
        url: 'update',
        type: 'POST',
        data: 'id=' + id + '&name=' + name,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("Failed to update To Do item: " + errorThrown);
        },
        success: function (data, textStatus) {
            $("#toDoItemLabel_" + row).text(name);
            $("#toDoItem_" + row).removeClass("editing");
        }
    });
}