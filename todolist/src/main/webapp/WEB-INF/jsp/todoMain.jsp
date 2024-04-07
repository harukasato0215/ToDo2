<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="model.Todo, java.util.List, java.util.Date,java.text.SimpleDateFormat" %>
        <% User user=(User)application.getAttribute("user",user);%>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>main</title>
            </head>
            <h1>
                <%=user.getName()%> さんのタスク一覧
                    <form action="TodoMain" method="post">
                        <input type="date" name="timeLimit">
                        <input type="text" name="title" placeholder="タスクを入力してください">
                        <input type="submit" value="登録">
                    </form>

                    <table>
                        <tr>
                            <th>タスク内容</th>
                            <th>期限</th>
                            <th>削除</th>

                        </tr>



                    </table>
            </h1>

            <body>

            </body>

            </html>