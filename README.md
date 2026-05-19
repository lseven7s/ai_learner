# AI智能学习助手

## 项目简介

AI智能学习助手（ai-learning-helper）是一套完整的全栈学习管理系统，支持用户学习资料管理、AI生成学习计划、学习打卡等功能。
纯AI生成，使用工具 TRAE + Cursor，目前仍在开发中。


## 技术栈

### 后端
- Java 17
- Spring Boot 3.2.5
- MyBatis-Plus 3.5.5
- MySQL 8
- JWT
- Spring Scheduled
- Maven

### 前端
- React 18
- TypeScript
- Vite
- Ant Design
- Axios
- Zustand
- React Router

## 项目结构

```
ai_learner/
├── ai-learning-helper-backend/  # 后端项目
├── ai-learning-helper-frontend/ # 前端项目
├── ai_learning_helper.sql       # 完整数据库建表语句
├── migrate_database.sql         # 简单数据库迁移脚本
├── migrate_safe.sql             # 安全数据库迁移脚本（推荐）
└── README.md                    # 项目说明
```

## 快速开始

### 1. 数据库配置

#### 首次安装（全新数据库）
在 MySQL 中执行建表脚本：

```bash
mysql -u root -p < ai_learning_helper.sql
```

或在 MySQL 客户端中直接执行 `ai_learning_helper.sql` 文件内容。

#### 已有数据库更新（三种方案）

**方案一：安全迁移（推荐）**
使用存储过程安全地添加缺失的字段，不会因为字段已存在而报错：

```bash
mysql -u root -p < migrate_safe.sql
```

**方案二：直接迁移**
如果确定字段不存在，可以使用简单版本：

```bash
mysql -u root -p < migrate_database.sql
```

**方案三：重建数据库（最彻底）**
如果不介意丢失现有数据，可以重新创建完整的数据库：

```bash
mysql -u root -p < ai_learning_helper.sql
```

### 2. 后端启动

```bash
cd ai-learning-helper-backend

# 修改配置文件（如需要）
# 编辑 src/main/resources/application-dev.yml 中的数据库连接信息

# 使用 Maven 启动
mvn clean install
mvn spring-boot:run
```

后端服务将在 http://localhost:8080 启动

### 3. 前端启动

```bash
cd ai-learning-helper-frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端服务将在 http://localhost:3000 启动

## 功能模块

### 用户模块
- 用户注册
- 账号密码登录
- JWT登录校验
- 个人信息管理

### 学习资料模块
- 资料上传
- 资料分类
- 资料列表查询
- 资料删除

### 学习计划模块
- 填写学习需求
- AI自动生成学习计划（预留接口）
- 计划查看与编辑

### 学习提醒模块
- 定时任务推送学习提醒

### 学习打卡模块
- 每日学习打卡
- 打卡记录查看

## 默认配置

### 后端配置
- 端口：8080
- 数据库：ai_learning_helper
- 用户名：root（可在 application-dev.yml 中修改）
- 密码：123456（可在 application-dev.yml 中修改）

### 前端配置
- 端口：3000
- API 地址：http://localhost:8080

## 问题修复记录

### 数据库字段问题修复
- 修复了 StudyReminder 实体字段与数据库表不匹配的问题
- 为所有表添加了 deleted 字段（用于逻辑删除）
- 统一了实体类、DTO、VO 与数据库表结构的字段映射

### 主要更新的表字段
- `user` 表：添加 phone 和 deleted 字段
- `study_material` 表：调整字段名，添加 deleted 字段
- `study_plan` 表：调整字段名，添加 deleted 字段
- `study_reminder` 表：修正字段名，添加 deleted 字段
- `study_checkin` 表：调整字段名，添加 deleted 字段

## 注意事项

1. 确保已安装 Java 17、Node.js 16+、MySQL 8
2. 启动前请确保 MySQL 服务已启动
3. AI 生成学习计划功能为预留接口，需接入实际大模型 API
4. MinIO 文件存储为预留接口，需配置实际的 MinIO 服务
5. 如遇到数据库字段错误，请确保执行了最新的建表脚本或迁移脚本
