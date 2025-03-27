-- 创建 rate_limit_rule 表
CREATE TABLE rate_limit_rule (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	dimension VARCHAR ( 255 ) NOT NULL COMMENT '限流维度，如 IP、用户 ID 等',
	capacity INT NOT NULL COMMENT '令牌桶容量',
	refill_rate DOUBLE NOT NULL COMMENT '令牌补充速率' 
);-- 创建 rate_limit_log 表
CREATE TABLE rate_limit_log (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	ip VARCHAR ( 45 ) NOT NULL COMMENT '请求 IP 地址',
	user_id VARCHAR ( 255 ) COMMENT '用户 ID',
	uri VARCHAR ( 255 ) NOT NULL COMMENT '请求的 URI',
	request_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '请求时间' 
);-- 插入一些示例数据到 rate_limit_rule 表中
INSERT INTO rate_limit_rule ( dimension, capacity, refill_rate )
VALUES-- 针对 IP 为 192.168.1.100 的限流规则
( '127.0.0.1', 100, 1 ),-- 针对接口路径 /api/user/get 的限流规则
( '/blog', 800, 100 ),-- 全局限流规则，适用于所有请求（假设 dimension 为 all 表示全局）
( 'all', 1000, 200 );