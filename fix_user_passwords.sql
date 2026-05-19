-- 修复测试用户密码（明文密码: 123456）
-- 若已执行过旧版 test_users.sql，请运行本脚本更新密码哈希

USE ai_learning_helper;

UPDATE `user`
SET `password` = '$2a$10$37IkAhtUSp8YlixXgkUc1uBgJH0EXzEFeGA2eqsYcYWGzrhVuLgq6'
WHERE `username` IN ('admin', 'test', 'zhangsan', 'lisi', 'wangwu');
