1. 先启动注册中心: eureka
   指定启动配置文件: --spring.profiles.active=eureka8761
2. 网址输入检查服务中心是否启动成功: http://localhost:8761/ 和 http://localhost:8762/
   (配置映射代理: C:\Windows\System32\drivers\etc\hosts): 
	127.0.0.1 eureka8761
	127.0.0.1 eureka8762
3. 再启动服务提供者和服务消费者
4. 熔断仪表盘和健康检查(Dashboard)
5. 声明式客户端: Fegin

问题: gateway网关
启动注册中心: eureka8761，与eureka8762
启动服务提供者: 端口8089
启动Feign
启动Gateway
访问-报错: http://localhost:8111/api-wkcto/hello?name=小酒馆
错误信息: 说是版本不匹配: https://www.cnblogs.com/lhr123/p/16583820.html
ZuulHandlerMapping.lookupHandler NoSuchMethodError: org.springframework.boot.web.servlet.error.ErrorController.getErrorPath()