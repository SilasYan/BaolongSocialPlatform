# 创建表
CREATE DATABASE IF NOT EXISTS baolong_social_platform DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE baolong_social_platform;

# 用户表
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id                 BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    user_account       VARCHAR(50)         NULL     DEFAULT NULL COMMENT '账号',
    user_password      VARCHAR(512)        NULL     DEFAULT NULL COMMENT '密码',
    user_email         VARCHAR(50)         NULL     DEFAULT NULL COMMENT '用户邮箱',
    user_phone         VARCHAR(50)         NULL     DEFAULT NULL COMMENT '用户手机号',
    user_name          VARCHAR(256)        NULL     DEFAULT NULL COMMENT '用户名称',
    user_avatar        VARCHAR(512)        NULL     DEFAULT NULL COMMENT '用户头像',
    user_profile       VARCHAR(512)        NULL     DEFAULT NULL COMMENT '用户简介',
    user_sex           TINYINT             NULL     DEFAULT NULL COMMENT '用户性别（0-男, 1-女, 2-无）',
    user_points        BIGINT              NOT NULL DEFAULT 0 COMMENT '用户积分',
    wx_open_id         VARCHAR(64)         NULL     DEFAULT NULL COMMENT '微信OpenId',
    share_code         VARCHAR(64)         NULL     DEFAULT NULL COMMENT '分享码',
    badge_id           BIGINT              NULL     DEFAULT NULL COMMENT '徽章ID',
    avatar_frame_id    BIGINT              NULL     DEFAULT NULL COMMENT '头像框ID',
    first_login_time   DATETIME            NULL     DEFAULT NULL COMMENT '首次登录时间',
    last_login_time    DATETIME            NULL     DEFAULT NULL COMMENT '最后登录时间',
    check_in_days      INT                 NOT NULL DEFAULT 0 COMMENT '连续签到天数',
    last_check_in_time DATETIME            NULL     DEFAULT NULL COMMENT '最后签到时间',
    is_disabled        TINYINT(4)          NOT NULL DEFAULT 0 COMMENT '是否禁用（0-正常, 1-禁用）',
    is_delete          TINYINT(4)          NOT NULL DEFAULT 0 COMMENT '是否删除（0-正常, 1-删除）',
    edit_time          DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    create_time        DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time        DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX uk_user_account (user_account ASC) USING BTREE,
    UNIQUE INDEX uk_user_email (user_email ASC) USING BTREE,
    UNIQUE INDEX uk_user_phone (user_phone ASC) USING BTREE,
    UNIQUE INDEX uk_user_name (user_name ASC) USING BTREE,
    UNIQUE INDEX uk_wx_open_id (wx_open_id ASC) USING BTREE,
    INDEX share_code (share_code ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户表'
  ROW_FORMAT = DYNAMIC;

# 用户登录日志表
DROP TABLE IF EXISTS user_login_log;
CREATE TABLE user_login_log
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id     BIGINT(20)          NOT NULL COMMENT '用户ID',
    login_ip    VARCHAR(64)         NOT NULL COMMENT '登录IP',
    login_time  DATETIME            NOT NULL COMMENT '登录时间',
    user_agent  VARCHAR(256)        NULL     DEFAULT NULL COMMENT '登录设备信息',
    create_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    INDEX idx_user_id (user_id ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户登录日志表'
  ROW_FORMAT = DYNAMIC;

# 物品表
DROP TABLE IF EXISTS items;
CREATE TABLE items
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '物品ID',
    item_type   TINYINT             NOT NULL COMMENT '物品类型（0-改名卡, 1-徽章, 2-头像框）',
    item_name   VARCHAR(128)        NULL     DEFAULT NULL COMMENT '物品名称',
    item_desc   VARCHAR(512)        NULL     DEFAULT NULL COMMENT '物品描述',
    item_image  VARCHAR(512)        NULL     DEFAULT NULL COMMENT '物品图片',
    is_delete   TINYINT(4)          NOT NULL DEFAULT 0 COMMENT '是否删除（0-正常, 1-删除）',
    edit_time   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    create_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    INDEX idx_item_type (item_type ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '物品表'
  ROW_FORMAT = DYNAMIC;
# 物品表初始数据
INSERT INTO items (item_type, item_name, item_desc, item_image)
VALUES (0, '改名卡', '用户可以使用改名卡，更改自己的名字。', NULL);
INSERT INTO items (item_type, item_name, item_desc, item_image)
VALUES (1, '爆赞徽章', '单条消息被点赞超过10次，即可获得。',
        'https://cdn-icons-png.flaticon.com/128/1533/1533913.png');
INSERT INTO items (item_type, item_name, item_desc, item_image)
VALUES (1, '元老徽章', '前10名注册的用户才能获得的专属徽章。',
        'https://cdn-icons-png.flaticon.com/512/6198/6198527.png');
INSERT INTO items (item_type, item_name, item_desc, item_image)
VALUES (1, '前100徽章', '前100名注册的用户才能获得的专属徽章。',
        'https://cdn-icons-png.flaticon.com/512/10232/10232583.png');
INSERT INTO items (item_type, item_name, item_desc, item_image)
VALUES (1, '星梦徽章', '星梦联盟成员的专属徽章。',
        'https://cdn-icons-png.flaticon.com/128/2909/2909937.png');


# 用户背包表
DROP TABLE IF EXISTS user_backpack;
CREATE TABLE user_backpack
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    user_id     BIGINT(20)          NOT NULL COMMENT '用户ID',
    item_id     BIGINT(20)          NOT NULL COMMENT '物品ID',
    idempotent  VARCHAR(64)         NOT NULL COMMENT '幂等号',
    use_status  TINYINT             NOT NULL COMMENT '使用状态（0-待使用, 1-已使用）',
    use_time    DATETIME            NULL     DEFAULT NULL COMMENT '使用时间',
    is_delete   TINYINT(4)          NOT NULL DEFAULT 0 COMMENT '是否删除（0-正常, 1-删除）',
    edit_time   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    create_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX uk_idempotent (idempotent) USING BTREE,
    INDEX idx_user_id (user_id ASC) USING BTREE,
    INDEX idx_item_id (item_id ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户背包表'
  ROW_FORMAT = DYNAMIC;

# 用户积分日志表
DROP TABLE IF EXISTS user_points_log;
CREATE TABLE user_points_log
(
    id               BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id          BIGINT          NOT NULL COMMENT '用户ID',
    operate_type     TINYINT         NOT NULL COMMENT '操作类型（0-新增, 1-减少）',
    operate_quantity BIGINT          NOT NULL COMMENT '操作数量',
    operate_desc     VARCHAR(64)     NOT NULL COMMENT '操作描述',
    create_time      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户积分日志表'
  ROW_FORMAT = DYNAMIC;

# 系统配置表
DROP TABLE IF EXISTS sys_config;
CREATE TABLE sys_config
(
    id           BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    config_name  VARCHAR(128)        NOT NULL COMMENT '配置名称',
    config_desc  VARCHAR(512)        NULL     DEFAULT NULL COMMENT '配置描述',
    config_type  TINYINT             NOT NULL COMMENT '配置类型（0-值、1-JSON对象、2-JSON数组）',
    config_key   VARCHAR(128)        NOT NULL COMMENT '配置键',
    config_value VARCHAR(512)        NOT NULL COMMENT '配置值',
    status       TINYINT             NOT NULL DEFAULT 0 COMMENT '状态（0-开启, 1-关闭）',
    is_delete    TINYINT(4)          NOT NULL DEFAULT 0 COMMENT '是否删除（0-正常, 1-删除）',
    edit_time    DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    create_time  DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time  DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX uk_config_key (config_key) USING BTREE,
    INDEX idx_config_type (config_type ASC) USING BTREE,
    INDEX idx_status (status ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '系统配置表'
  ROW_FORMAT = DYNAMIC;
