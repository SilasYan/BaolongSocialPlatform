# 创建表
CREATE DATABASE IF NOT EXISTS baolong_social_platform DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE baolong_social_platform;

# 用户表
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id            BIGINT(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    user_account  VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '账号',
    user_password VARCHAR(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '密码',
    user_email    VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '用户邮箱',
    user_phone    VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '用户手机号',
    user_name     VARCHAR(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '用户名称',
    user_avatar   VARCHAR(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '用户头像',
    user_profile  VARCHAR(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '用户简介',
    user_sex      TINYINT                                                       NULL     DEFAULT NULL COMMENT '用户性别（0-男, 1-女, 2-无）',
    wx_open_id    VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '微信OpenId',
    share_code    VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '分享码',
    is_disabled   TINYINT(4)                                                    NOT NULL DEFAULT 0 COMMENT '是否禁用（0-正常, 1-禁用）',
    is_delete     TINYINT(4)                                                    NOT NULL DEFAULT 0 COMMENT '是否删除（0-正常, 1-删除）',
    edit_time     DATETIME                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    create_time   DATETIME                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time   DATETIME                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX uk_user_account (user_account ASC) USING BTREE,
    UNIQUE INDEX uk_user_email (user_email ASC) USING BTREE,
    UNIQUE INDEX uk_wx_open_id (wx_open_id ASC) USING BTREE,
    INDEX idx_user_name (user_name ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户表'
  ROW_FORMAT = DYNAMIC;

# 用户登录日志表
DROP TABLE IF EXISTS user_login_log;
CREATE TABLE user_login_log
(
    id          BIGINT(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id     BIGINT(20)                                                    NOT NULL COMMENT '用户ID',
    login_ip    VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '登录IP',
    login_time  DATETIME                                                      NOT NULL COMMENT '登录时间',
    user_agent  VARCHAR(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '登录设备信息',
    create_time DATETIME                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    INDEX idx_user_id (user_id ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户登录日志表'
  ROW_FORMAT = DYNAMIC;
