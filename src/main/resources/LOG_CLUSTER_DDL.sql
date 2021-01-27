 CREATE TABLE if NOT EXISTS `log_cluster` (
  `id` int NOT NULL AUTO_INCREMENT,
  `log_table` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '日志表名称',
  `start_time` datetime DEFAULT NULL COMMENT '服务启动时间',
  `host_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ipv4` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ipv6` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci