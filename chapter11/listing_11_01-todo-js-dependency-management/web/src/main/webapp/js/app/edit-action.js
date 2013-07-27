function editToDoItem(row) {
    $("#toDoItem_" + row).addClass("editing");
    $("#toDoItemName_" + row).focus();
}