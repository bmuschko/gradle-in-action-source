<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>To Do application</title>
    <link rel="stylesheet" href="<c:url value="css/base.css"/>">
</head>
<body>
<section id="todoapp">
    <header id="header">
        <h1>todos</h1>
        <form id="newItemForm" action="<c:url value="insert"/>" method="POST">
            <input type="hidden" name="filter" value="${filter}"/>
            <input id="new-todo" name="name" placeholder="What needs to be done?" autofocus>
        </form>
    </header>
    <section id="main">
        <input id="toggle-all" type="checkbox">
        <label for="toggle-all">Mark all as complete</label>
        <ul id="todo-list">
            <c:forEach var="toDoItem" items="${toDoItems}" varStatus="status">
                <li id="toDoItem_${status.count}" class="<c:if test="${toDoItem.completed}">completed</c:if>" ondblclick="javascript:document.getElementById('toDoItem_${status.count}').className += ' editing';document.getElementById('toDoItemName_${status.count}').focus();">
                    <div class="view">
                        <form id="toggleForm_${status.count}" action="<c:url value="toggleStatus"/>" method="POST">
                            <input type="hidden" name="id" value="${toDoItem.id}"/>
                            <input type="hidden" name="filter" value="${filter}"/>
                            <input class="toggle" name="toggle" type="checkbox" <c:if test="${toDoItem.completed}">checked</c:if> onchange="javascript:document.getElementById('toggleForm_${status.count}').submit();">
                        </form>
                        <label>${toDoItem.name}</label>
                        <form action="<c:url value="delete"/>" method="POST">
                            <input type="hidden" name="id" value="${toDoItem.id}"/>
                            <input type="hidden" name="filter" value="${filter}"/>
                            <button class="destroy"></button>
                        </form>
                    </div>
                    <form id="updateForm_${status.count}" action="<c:url value="update"/>" method="POST">
                        <input type="hidden" name="id" value="${toDoItem.id}"/>
                        <input type="hidden" name="filter" value="${filter}"/>
                        <input class="edit" id="toDoItemName_${status.count}" name="name" value="${toDoItem.name}" onblur="javascript:document.getElementById('updateForm_${status.count}').submit();"/>
                    </form>
                </li>
            </c:forEach>
        </ul>
    </section>
    <footer id="footer">
        <c:if test="${stats.all > 0}">
            <span id="todo-count"><strong><c:out value="${stats.active}" /></strong>
            <c:choose>
                <c:when test="${stats.active == 1}">
                    item
                </c:when>
                <c:otherwise>
                    items
                </c:otherwise>
            </c:choose>
            left</span>
            <ul id="filters">
                <li>
                    <a <c:if test="${filter == 'all'}">class="selected"</c:if> href="<c:url value="/all"/>">All</a>
                </li>
                <li>
                    <a <c:if test="${filter == 'active'}">class="selected"</c:if> href="<c:url value="active"/>">Active</a>
                </li>
                <li>
                    <a <c:if test="${filter == 'completed'}">class="selected"</c:if> href="<c:url value="completed"/>">Completed</a>
                </li>
            </ul>
            <c:if test="${stats.completed > 0}">
                <form action="<c:url value="clearCompleted"/>" method="POST">
                    <input type="hidden" name="filter" value="${filter}"/>
                    <button id="clear-completed">Clear completed (<c:out value="${stats.completed}" />)</button>
                </form>
            </c:if>
        </c:if>
    </footer>
</section>
<div id="info">
    <p>Double-click to edit a todo</p>
    <c:if test="${not empty buildInfo}">
        <p>
            Version <c:out value="${buildInfo.version}"/> (Built on: <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${buildInfo.timestamp}" />)
        </p>
    </c:if>
</div>
</body>
</html>