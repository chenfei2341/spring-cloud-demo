使用该脚手架工程需要修改成自己的工程名(要改彻底)！！！
spring-cloud 框架，基于 Greenwich.SR2 和 spring-boot 2.1.7.RELEASE 版本

一、规范说明：
1. 开发工具请设置成全局utf-8编码格式
2. 无关文件请不要上传提交,比如.dea 、.iml、.target等文件
3. 可根据业务创建目录，文件放置位置可读性
4. 目录结构层次根据自己的业务定
5. 所有的方法名或者sql语句请添加相关注释
6. northbound-api 下 com.znv.peim.northbound.controller.ManageController 是demo示例，示例请最后删除！

二、脚手架工程说明：

admin-server: 端口 9510，统一监控，监控所有的 spring-cloud 服务
auth-server: 端口 9610，统一权限认证服务，没有集成 oauth2.0，可以不使用
config-server: 端口 9520，配置中心，配置文件读取本地
config-storage: 配置中心的资源文件存储地址
eureka-server: 端口 9500，注册中心服务
northbound-api: 端口 9620，接口，真正的服务模块
zuul-getway: 端口 9600，zuul网关

northbound-api 配置：
1. bin目录下是linux环境部署下启动暂停脚本(需修改成自己的工程名)
2. NorthboundApiApplication目录是启动入口
3. com.znv.peim.northbound.common.interceptor 是拦截器配置(不需要可注释掉拦截器注解)
4. datasource目录下是配置的连接mysql源
5. log4j2.xml是日志配置，启动时传入参数 --boot.home 可以指定日志位置，默认最多生成20个文件每个文件20M
8. common目录下是公共部分：
until 目录是工具类，
interceptor 目录下有拦截器、控制层结果集封装、控制层异常统一处理
exception 目录下是自定义异常类、结果集错误码
bean 目录下是控制层结果集对象
Constant 文件中存储常量
9. configuration 目录下是 springboot 配置，以及配置文件注入对象
10. resources下是配置文件，
mabatis文件在mapper下(目录结构根据自己业务定)

三、脚手架功能说明：
约束了rest请求返回的对象，控制层只需要确保返回结果是对象即可！
自定义异常码，错误信息等请向上抛出！(抛到控制层)
示例：
{
  "code": 200,  --返回状态码  默认
  "message": "ok",  --描述  默认
  "data": {}  --返回结果集对象
}

1. 控制层全局异常捕获，控制层不需要捕获异常  --其他层自行捕获异常
2. 控制层接口入参拦截器全局打印不需要单独打印入参日志，
控制层返回结果对象全局封装  --不需要单独封装返回结果集
3. 拦截器 --打印所有请求参数，请求耗时时间等信息；可添加需要拦截的事物，比如鉴权等
4. 统一的日志管理slf4j(打印日志行号等)  --需要修改输出日志文件名！！
5. mysql数据源连接   --参数过多建议使用map/对象传参
6. 错误码定义配置，自定义异常封装
7. 配置文件注入解析(或者使用@Value等)
8. swagger 组件
9. 一键打包
10. 集成kafka
11. redis
12. mybasis 分页组件

四、ResultCode类下是自定义的错误码枚举类，说明如下：
错误码的使用是返回给前端，便于快速定位后端问题
1.200 正常   --默认
2.500 系统异常/报错  --默认
3.http协议异常码  --使用公有协议异常码

自定义异常规则如下(自定义异常错误码请使用5位) 
1. 参数类异常定义  400XX
2. 登录/连接/权限/数据库等类 异常定义 5XXXX
登录类异常定义：51XXX
权限类异常定义：52XXX
连接类异常定义：53XXX
数据库异常定义：54XXX
3. 业务类自定义异常定义  6XXXX
公安业务类：61XXX
城运业务类：62XXX
连接管理平台业务类：63XXX
其他业务类：64XXX
4. 对接第三方接口自定义异常类 7XXXX
5. 其他类异常定义  9XXXX

