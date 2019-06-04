# Getting Started

### Guides
The following guides illustrates how to use certain features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

##导言
本服务主要用于熟练Springboot 一些实用点：
 1、 Filter(过滤器)、Interceptor(拦截器)、监听器(Listener)的使用。
   Filter、Interceptor、Listener的执行顺序：
    listener(启动时执行) -> filter1 （filterChain.doFilter()之前代码;filterChain.doFilter()之后代码不会执行)
    -> filter2 （filterChain.doFilter()之前代码)
    -> interceptor1 (preExcute()) 
    -> interceptor2 (preExcute())
    -> controller
    -> interceptor2 (postHandle())
    -> interceptor1 (postHandle()) 
    -> interceptor2 (afterCompletion())
    -> interceptor1 (afterCompletion()) 
    -> filter2 （只有最后一个filter的filterChain.doFilter()之后代码会执行)
 
   GZipResponseFilter: 判断过滤器是否对数据请求response执行压缩操作;
   RequestLimitFilter: 对请求进行限流：通过redis计数方式实现;
   ParamHandleFilter:  通过过滤器操作实现对请求参数的逻辑处理(主要是对post请求body处理,get请求可以通过切面处理);
   UnzipRequestFilter: 判断过滤器是否对数据调请求request执行解压缩操作.
   
 2.threadLocal使用案例
   CommonThreadLocal ——> 测试案例CommonThreadLocalTest
  
 3.redis与springboot整合:  springboot StringRedisTemplate的使用 
   handler包->redis包->IRedisHandler
   
 4.切面@Aspect的用法
   ControllerAspect
   ResponseTimeLogAspect

 5.统一异常处理(其实也是切面的一种实现方式)
   WholeExceptionHandler