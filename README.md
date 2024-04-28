# 一个包含很多东西的demo
## 1.国际化i18n
## 2.产品化参数传递，分发实现思路baseContext
## 3.mybatis自动赋值初始字段插件
## 4.全局异常捕获
## 5.工具类
    1.树结构解析工具类
    2.Spring获取请求工具类，Ip，URL，session，cookie等
    3.第三方接口发送工具类
## 6.API模块
    1.t_api 记录所有的请求url，description，specification，publisher，environment，code(1.通过代码获取地址,代码放到常量或者枚举，2.支持前端配置，保存到数据库)
    2.t_api_subscription 记录订阅人，订阅系统，通行证key，status(待审批,已订阅)
    3.t_api_record 记录所有的调用记录（切面），入参出参，支持重发
## 7.文件管理模块
    1.导入导出
    2.文件上传下载
    3.json转excel，excel转json，json转对象...
## 8.字典模块
    1.t_dict(code,label)
    2.t_dict_language关联国际化(t_dict_id,t_language_id)
## 9.配置管理 (通过页面配置实现功能的部分定制化)
    1.t_config(company，configCode，description,configInfo)
## 10.DDD的架构思路


## todo
    1.日志文件自动删除，超过多少天或者多大