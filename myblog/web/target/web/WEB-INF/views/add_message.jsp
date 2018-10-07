<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>新建留言</title>
    <link rel="stylesheet" href="../../../css/bootstrap.min.css">
    <link rel="stylesheet" href="../../../css/add.css">
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/message/page">
                慕课网留言板
            </a>
        </div>
    </div>
</nav>
<div class="container">
    <div class="jumbotron">
        <h1>Hello, ${user.getUsername()}!</h1>
        <p>既然来了，就说点什么吧</p>
    </div>
    <div class="page-header">
        <h3><small>新建留言</small></h3>
    </div>

      <form:form action="/message/add_msg" modelAttribute="message"  cssClass="form-horizontal" id="admin-form" name="addForm">
        <div class="form-group">
            <label for="title" class="col-sm-2 control-label">标题 ：</label>
            <div class="col-sm-8">
            <form:input path="title" cssClass="form-control" placeholder="标题"/>

            </div>
        </div>
        <div class="form-group">
            <label for="content" class="col-sm-2 control-label">内容 ：</label>
            <div class="col-sm-8">
                <form:textarea path="content" cssClass="form-control" placeholder="内容"/>

            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary">发布留言</button>&nbsp;&nbsp;&nbsp;
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <a class="btn btn-default" href="/message/page">查看留言</a>
            </div>
        </div>
</form:form>
</div>
<footer class="text-center" >
    copy@imooc
</footer>
</body>
</html>
