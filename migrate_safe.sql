-- 安全的数据库迁移脚本
-- 使用存储过程避免字段已存在时报错

USE ai_learning_helper;

-- 创建临时存储过程来安全添加字段
DELIMITER //

DROP PROCEDURE IF EXISTS add_column_if_not_exists//

CREATE PROCEDURE add_column_if_not_exists(
    IN table_name VARCHAR(64),
    IN column_name VARCHAR(64),
    IN column_definition TEXT
)
BEGIN
    DECLARE column_count INT;
    
    SELECT COUNT(*) INTO column_count
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = table_name
      AND column_name = column_name;
    
    IF column_count = 0 THEN
        SET @sql = CONCAT('ALTER TABLE `', table_name, '` ADD COLUMN `', column_name, '` ', column_definition);
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
        SELECT CONCAT('Added column `', column_name, '` to table `', table_name, '`') AS result;
    ELSE
        SELECT CONCAT('Column `', column_name, '` already exists in table `', table_name, '`') AS result;
    END IF;
END//

DELIMITER ;

-- 更新 user 表
CALL add_column_if_not_exists('user', 'phone', 'VARCHAR(20) DEFAULT NULL COMMENT \'手机号\' AFTER `avatar`');
CALL add_column_if_not_exists('user', 'deleted', 'TINYINT NOT NULL DEFAULT 0 COMMENT \'删除标记：0-未删除，1-已删除\' AFTER `status`');

-- 更新 study_material 表
CALL add_column_if_not_exists('study_material', 'deleted', 'TINYINT NOT NULL DEFAULT 0 COMMENT \'删除标记：0-未删除，1-已删除\' AFTER `status`');

-- 更新 study_plan 表
CALL add_column_if_not_exists('study_plan', 'deleted', 'TINYINT NOT NULL DEFAULT 0 COMMENT \'删除标记：0-未删除，1-已删除\' AFTER `status`');

-- 更新 study_reminder 表
CALL add_column_if_not_exists('study_reminder', 'deleted', 'TINYINT NOT NULL DEFAULT 0 COMMENT \'删除标记：0-未删除，1-已删除\' AFTER `status`');

-- 更新 study_checkin 表
CALL add_column_if_not_exists('study_checkin', 'deleted', 'TINYINT NOT NULL DEFAULT 0 COMMENT \'删除标记：0-未删除，1-已删除\' AFTER `update_time`');

-- 清理临时存储过程
DROP PROCEDURE IF EXISTS add_column_if_not_exists;
