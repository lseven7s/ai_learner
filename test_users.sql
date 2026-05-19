-- 测试用户数据
-- 注意：密码已经过 BCrypt 加密，所有用户密码都是: 123456

USE ai_learning_helper;

-- 插入测试用户（密码都是 123456）
INSERT INTO `user` (`username`, `password`, `nickname`, `email`, `phone`, `status`, `deleted`) VALUES
('admin', '$2a$10$37IkAhtUSp8YlixXgkUc1uBgJH0EXzEFeGA2eqsYcYWGzrhVuLgq6', '管理员', 'admin@example.com', '13800138000', 1, 0),
('test', '$2a$10$37IkAhtUSp8YlixXgkUc1uBgJH0EXzEFeGA2eqsYcYWGzrhVuLgq6', '测试用户', 'test@example.com', '13800138001', 1, 0),
('zhangsan', '$2a$10$37IkAhtUSp8YlixXgkUc1uBgJH0EXzEFeGA2eqsYcYWGzrhVuLgq6', '张三', 'zhangsan@example.com', '13800138002', 1, 0),
('lisi', '$2a$10$37IkAhtUSp8YlixXgkUc1uBgJH0EXzEFeGA2eqsYcYWGzrhVuLgq6', '李四', 'lisi@example.com', '13800138003', 1, 0),
('wangwu', '$2a$10$37IkAhtUSp8YlixXgkUc1uBgJH0EXzEFeGA2eqsYcYWGzrhVuLgq6', '王五', 'wangwu@example.com', '13800138004', 1, 0);

