<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>我的信息</title>
        <link rel="stylesheet" href="../../../css/bootstrap.min.css">
        <link rel="stylesheet" href="../../../css/add.css">
    </head>
    <body>
        <nav class="navbar navbar-default">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/message/page">
                        返回留言板
                    </a>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="jumbotron">
                <h1>Hello, ${user.getUsername()}!</h1>
                <p>信息都在这里了 ^_^</p>
            </div>
            <div class="page-header">
                <h3><small>个人信息</small></h3>
            </div>

                <form:form modelAttribute="user" id="admin-form" name="addForm"  cssClass="form-horizontal" action="/user/my_info_edit"  >
                    <input type="hidden" id="id" name="id" value="${user.getId()}">
                    <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">用户 ：</label>
                    <div class="col-sm-6">
                          <form:input path="username" cssClass="form-control" placeholder="用户" readonly="false"/>
                      </div>
                    </div>

                <div class="form-group">
                    <label for="realName" class="col-sm-2 control-label">姓名 ：</label>
                    <div class="col-sm-8">
                        <form:input path="realName" cssClass="form-control" placeholder="姓名" readonly="false"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">密码 ：</label>
                    <div class="col-sm-8">
                        <form:input path="password" cssClass="form-control" placeholder="密码" readonly="false"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="birthday" class="col-sm-2 control-label">生日 ：</label>
                    <div class="col-sm-8">
                        <form:input path="birthday" cssClass="form-control" placeholder="生日" readonly="false" rows="3" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="phone" class="col-sm-2 control-label">电话 ：</label>
                    <div class="col-sm-8">
                        <form:input path="phone" cssClass="form-control" placeholder="电话" readonly="false" />

                    </div>
                </div>
                <div class="form-group">
                    <label for="address" class="col-sm-2 control-label">地址 ：</label>
                    <div class="col-sm-8">
                        <form:input path="address" cssClass="form-control" placeholder="地址" readonly="false" />

                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary">修改</button>&nbsp;&nbsp;&nbsp;
                    </div>
                </div>
</form:form>
        </div>
        <footer class="text-center" >
            copy@imooc
        </footer>
    </body>
</html>
