<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="model.Todo,model.User, java.util.List, java.util.Date,java.text.SimpleDateFormat" %>
        <% User user=(User)session.getAttribute("user"); List<Todo> todoList = (List<Todo>)
                application.getAttribute("todoList");
                %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <title>main</title>
                </head>
                <h1>
                    <%=user.getName()%> さんのタスク一覧
                </h1>

                <form action="TodoMain" method="post">
                    <input type="date" name="timeLimit">
                    <input type="text" name="title" placeholder="タスクを入力してください" required="required">
                    <input type="submit" value="登録">
                </form>
                <br>

                <table>
                    <tr>
                        <th>タスク内容</th>
                        <th>期限</th>
                        <th>削除</th>

                    </tr>
                    <% for(Todo todo : todoList){%>
                        <tr>
                            <td>
                                <%=todo.getTitle()%>
                            </td>
                            <td>
                                <%=todo.getTimeLimit()%>
                            </td>
                            <td>
                                <form action="TodoDelete" method="post">
                                    <input type="hidden" name="title" value="<%=todo.getTitle()%>">
                                    <input type="submit" value="削除">
                                </form>
                            </td>
                        </tr>
                        <%}%>

                </table>


                <body>

                </body>

                </html>