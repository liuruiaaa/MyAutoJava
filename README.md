# java_forward  
java进步一：开始自己用ssm套一个留言板  环境是jdk1.8的 idea编辑器 创建好项目后直接进去简单修改一下idea的配置就可以用了。  
三层架构说明：其实就是配置复杂一点其实，也没那么多的难度。  
然后有个spring.xml的是web.xml 加载 Controller层的spring_web.xml(表现层)然后这个 spring_web.xml加载spring_biz.xml(逻辑层)  然后这个 spring_biz.xml加载  spring_dao.xml ( 持久层)   
还有上层的可以调用下层的方法，下层的不能调用上层的方法。这是个问题，不过很多mvc的框架都是这样的。  
同时配置方便的东西还需要多多学习，要搞清楚。这是下一步要学习的东西。  



