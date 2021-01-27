 CREATE TABLE if NOT EXISTS `%s` (
  `id` int NOT NULL AUTO_INCREMENT,
  `log_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '日志时间',
  `thread` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '线程',
  `log_level` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '日志等级',
  `package_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '包路径',
  `msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '具体输出信息 ',
  PRIMARY KEY (`id`),
  KEY `log_level` (`log_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci