# lazyDay
## 技术栈
- Spring Boot
- Spring Cloud
- Spring Cloud Openfeign
- Spring Security
- Spring Web
- Mybatis-plus
- redis
- rabbitMQ
- 
## 任务列表
- security相关
  - 登录、注册 √
  - jwt无状态认证、授权 √
  - 角色权限访问控制
  - 限制用户同时在线数量
  - 跨域相关
- 完善工具包
    - 数据传输：ftp、sftp、socket
    - 加解密：DES、AES
- 部署相关
  - 打包docker镜像
  - 搭建jenkins、docker镜像仓库
  - 搭建gitlab私库、或使用github仓库配置jenkins
- 进阶扩展
  - Elasticsearch
- 测试相关
  - 搭建sftp、ftp测试工具类

## 模块功能
  - api 访问接口
  - auth 认证登录
  - common
    - core 核心包
    - log 记录日志 √
  - gateway 网关过滤
    - authFilter 过滤无令牌请求 √
    - xssFilter xss 数据安全过滤 √
  - modules （实际数据操作模块）
    - family 家庭功能模块
    - system 系统功能模块（用户、权限等基础功能）
      - 权限管理