## CORS

Spring MVC配置CORS（解决跨域请求）
https://blog.csdn.net/qq_36582604/article/details/80500245

### DefaultCorsProcessor

Spring MVC 中对 CORS 规则的校验，都是通过委托给 DefaultCorsProcessor 实现的。

DefaultCorsProcessor 处理过程如下：

 1. 判断依据是 Header 中是否包含 Origin。如果包含则说明为 CORS 请求，转到 2；否则，说明不是 CORS 请求，不作任何处理。
 2. 判断 response 的 Header 是否已经包含 Access-Control-Allow-Origin，如果包含，证明已经被处理过了, 转到 3，否则不再处理。
 3. 判断是否同源，如果是则转交给负责该请求的类处理
 4. 是否配置了 CORS 规则，如果没有配置，且是预检请求，则拒绝该请求；如果没有配置，且不是预检请求，则交给负责该请求的类处理。如果配置了，则对该请求进行校验。

校验就是根据 CorsConfiguration 这个类的配置进行判断：

 1. 判断 origin 是否合法
 2. 判断 method 是否合法
 3. 判断 header 是否合法
 4. 如果全部合法，则在 response header 中添加响应的字段，并交给负责该请求的类处理，如果不合法，则拒绝该请求。


DispatcherServlet#doService() --> DispatcherServlet#doDispatch() --> HttpRequestHandlerAdapter#handle() --> PreFlightHandler#handleRequest() --> DefaultCorsProcessor#processRequest() --> DefaultCorsProcessor#handleInternal()

```java
/**
@see org.springframework.web.cors.DefaultCorsProcessor#handleInternal
**/
```