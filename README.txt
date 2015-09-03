2015-08-31 1.4.0-snapshot
* IE8上的bug fix
* 修改extjs的版本为4.2.1
* 登录页面用户名和密码输入框垂直居中

2015-08-18 1.3.0-snapshot
* 登录递归方式修改,增加系统常量Constants,枚举整理
* 增加PortalConfig配置HttpMessageConverters
* logback修改c3p0连接池为HikariCP

2015-08-12 1.2.0-snapshot
* 数据源配置方式修改
* logback修改c3p0连接池为HikariCP

2015-08-05 1.2.0-snapshot
* 维度信息设置combo下拉框设置修改
* 去除触发器，改成使用序列实现主键ID自增
* HikariCP连接池设置修改，登录用户名trim()

2015-08-03 1.2.0-snapshot
* 代码格式化UsersBO
* 丹东银行增加查询案件的接口

2015-07-24 1.2.0-snapshot
* 系统配置管理列表页面按钮选中
* 用户和角色新增或修改名称时的空格验证

2015-07-20 1.2.0-snapshot
* Service去除私有的构造方法
* Service 请求AOP方法修改
* 增加用户权限发生错误时的异常

2015-07-17 1.2.0-snapshot
* 添加丹东银行图标
* ResourcesBO增加递归构造角色树选中
* 代码格式化，登录多次请求tabView和menu

2015-07-07 1.2.0-snapshot
* 其他一些后台问题的修复
* 登录查询多次数据库的问题修复
* 新建和编辑角色发送多次AJAX的问题修复

2015-07-01 1.1.0-snapshot
* 合并FRMS_PORTAL和LOGBACK数据库，并备份建表语句
* 线程池修改，增加XML，JSON和JAVA BEAN之间的转换工具
* 增加RedisBO和RulesBO接口，UserFilter从缓存获取数据修改

2015-06-19 1.1.0-snapshot
* 拆分ds数据库
* redis和规则调用的修改，代码的优化和bug fix

2015-06-15 1.0.0-snapshot
* 异常的处理，代码的优化和bug fix
* 将aml分支合并到master分支，并将登录样式和主页样式改为动态配置