-- 创建数据库
CREATE DATABASE IF NOT EXISTS ai_learning_helper DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE ai_learning_helper;

-- 用户表
CREATE TABLE `user` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1-正常，0-禁用',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 学习资料表
CREATE TABLE `study_material` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '资料ID',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
  `title` VARCHAR(200) NOT NULL COMMENT '资料标题',
  `description` TEXT COMMENT '资料描述',
  `content` TEXT COMMENT '资料内容',
  `file_url` VARCHAR(255) DEFAULT NULL COMMENT '文件URL',
  `file_type` VARCHAR(50) DEFAULT NULL COMMENT '文件类型',
  `category` VARCHAR(50) DEFAULT NULL COMMENT '资料分类',
  `tags` VARCHAR(255) DEFAULT NULL COMMENT '标签，多个用逗号分隔',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1-正常，0-删除',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学习资料表';

-- 学习计划表
CREATE TABLE `study_plan` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '计划ID',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
  `title` VARCHAR(200) NOT NULL COMMENT '计划标题',
  `description` TEXT COMMENT '计划描述',
  `start_date` DATE NOT NULL COMMENT '开始日期',
  `end_date` DATE NOT NULL COMMENT '结束日期',
  `daily_goal` VARCHAR(500) DEFAULT NULL COMMENT '每日目标',
  `progress` DECIMAL(5,2) NOT NULL DEFAULT 0.00 COMMENT '进度百分比',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1-进行中，0-已完成，2-已取消',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_start_date` (`start_date`),
  KEY `idx_end_date` (`end_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学习计划表';

-- 学习提醒表
CREATE TABLE `study_reminder` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '提醒ID',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
  `plan_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '关联计划ID',
  `title` VARCHAR(200) NOT NULL COMMENT '提醒标题',
  `content` TEXT COMMENT '提醒内容',
  `reminder_time` DATETIME NOT NULL COMMENT '提醒时间',
  `repeat_type` TINYINT NOT NULL DEFAULT 0 COMMENT '重复类型：0-不重复，1-每天，2-每周，3-每月',
  `is_sent` TINYINT NOT NULL DEFAULT 0 COMMENT '是否已发送：0-否，1-是',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_reminder_time` (`reminder_time`),
  KEY `idx_is_sent` (`is_sent`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学习提醒表';

-- 学习打卡表
CREATE TABLE `study_checkin` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '打卡ID',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
  `plan_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '关联计划ID',
  `checkin_date` DATE NOT NULL COMMENT '打卡日期',
  `checkin_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '打卡时间',
  `study_duration` INT DEFAULT NULL COMMENT '学习时长（分钟）',
  `study_content` TEXT COMMENT '学习内容',
  `mood` VARCHAR(20) DEFAULT NULL COMMENT '心情',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_date` (`user_id`, `checkin_date`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_checkin_date` (`checkin_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学习打卡表';