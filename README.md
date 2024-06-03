# 一个包含很多东西的demo
## 1.国际化i18n
    1.三层实现 静态文件/常量+缓存+数据库
## 2.产品化 DDD强化版的架构思路
    1.参考HandlerAdapter
    2.baseContext 
## 3.mybatis自动赋值初始字段插件
    1.基本数据填充
    2.分页插件 注解@Page 分页参数可以放到baseContext
## 4.全局异常捕获
    1.自动拦截处理异常
    2.返回统一Result类
## 5.工具类
    1.树结构解析工具类
    2.Spring获取请求工具类，Ip，URL，session，cookie等
    3.第三方接口发送工具类
    4.初始化线程池
    4.异步工具类completeFutrue,参考JD AsyncTools
    5.邮件工具类，短信工具类
## 6.API模块 todo
    1.t_api 记录所有的请求url，description，specification，publisher，environment，code(1.通过代码获取地址,代码放到常量或者枚举，2.支持前端配置，保存到数据库)
    2.t_api_subscription 记录订阅人，订阅系统，通行证token，status(待审批,已订阅)
    3.t_api_record 记录所有的调用记录（切面），入参出参，支持重发
## 7.文件管理模块 todo
    1.导入导出
    2.文件上传下载
    3.json转excel，excel转json，json转对象...
    4.ExcelUtils实现简单excel的导出
## 8.字典模块 todo
    1.t_dict(code,label)
    2.t_dict_language关联国际化(t_dict_id,t_language_id)
## 9.配置管理 (通过页面配置实现功能的部分定制化) todo
    1.t_config(company，configCode，description,configInfo)
## 10.权限认证 todo
    1.登录认证
    2.访问拦截
    3.接口权限控制 注解或者配置 实现
    4.数据权限配置 行权限，列权限
    5.角色权限（用户管理，菜单管理，组织管理，角色管理）
## 11.微服务SpringCloudAlibaba(Nacos,Feign) todo
    1.服务调用
    2.注册发现
    3.负载均衡
    4.链路追踪
    5.服务降级
## 12.redis
    1.RedisUtils工具类简化，包含批量，上锁，解锁
## 13.mq todo
## 14.websocket/SSE todo
## 15.swagger todo
## 16.多数据源druid todo
## 17.定时任务xxl-job todo
## 18.流程管理 组件化 可配置 todo
    1.t_process(node,description,提交人，当前审批人，状态)
## 19.支付模块 todo
## 20.节假日管理 todo
    1.自动获取国务院信息，解析存表，生成日历，包含节假日信息
## 21.Timer定时器
    1.用于实现XX时间后执行任务
    2.参照ScheduledExecutorUtils
## 22.规则引擎 todo
## 23.排产引擎 todo
## 24.el表达式 解析 todo
## 25.日志文件自动删除，超过多少天或者多大 todo
## 26.低代码生成功能 
    1.支持JPA/Mybatis低代码生成，先考虑写死代码，再考虑维护到数据库，使用String.format(s1,s2)用s2依次替换s1中%s
    2.输入表名、模块名称、字段名称（是否查询条件、类型、下拉框还是输入框）
    3.选择可选功能（新建、检索、重置、编辑、删除、批量删除、详情、导入、导出等等）
    4.自动生成entity、dao、service、controller、html、js文件以及SQL文件
## 27.echarts图
    1.后端返回echerts结构