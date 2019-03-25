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
    listener -> filter1 （filterChain.doFilter()之前代码;filterChain.doFilter()之后代码不会执行)
    -> filter2 （filterChain.doFilter()之前代码)
    -> interceptor1 (preExcute()) 
    -> interceptor2 (preExcute())
    -> controller
    -> interceptor2 (postHandle())
    -> interceptor1 (postHandle()) 
    -> interceptor2 (afterCompletion())
    -> interceptor1 (afterCompletion()) 
    -> filter2 （只有最后一个filter的filterChain.doFilter()之后代码会执行)
 
 