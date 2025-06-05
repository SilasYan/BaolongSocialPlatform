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
    ip_info            JSON                NULL     DEFAULT NULL COMMENT 'IP信息',
    first_login_time   DATETIME            NULL     DEFAULT NULL COMMENT '首次登录时间',
    last_login_time    DATETIME            NULL     DEFAULT NULL COMMENT '最后登录时间',
    last_online_time   DATETIME            NULL     DEFAULT NULL COMMENT '最后上线时间',
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
# 用户表初始数据
INSERT INTO user (id, user_account, user_password, user_email, user_phone, user_name, user_profile, user_sex)
VALUES (1, 'super', 'ba4cdb9687899fcda66dff6048ccffa8', '510132000@qq.com',
        '15279290000', '超级管理员', '用户暂未填写个人简介~', 0);
INSERT INTO user (id, user_account, user_password, user_email, user_phone, user_name, user_profile, user_sex)
VALUES (2, 'admin', 'ba4cdb9687899fcda66dff6048ccffa8', '510132011@qq.com',
        '15279291111', '系统管理员', '用户暂未填写个人简介~', 0);
INSERT INTO user (id, user_account, user_password, user_email, user_phone, user_name, user_profile, user_sex)
VALUES (3, 'user', 'ba4cdb9687899fcda66dff6048ccffa8', '510132022@qq.com',
        '15279292222', '普通用户', '用户暂未填写个人简介~', 0);


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
    id            BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '物品ID',
    item_type     TINYINT             NOT NULL COMMENT '物品类型（0-改名卡, 1-徽章, 2-头像框）',
    item_name     VARCHAR(128)        NULL     DEFAULT NULL COMMENT '物品名称',
    item_desc     VARCHAR(512)        NULL     DEFAULT NULL COMMENT '物品描述',
    item_image    VARCHAR(512)        NULL     DEFAULT NULL COMMENT '物品图片',
    repeat_status TINYINT             NOT NULL DEFAULT 0 COMMENT '允许重复（0-允许, 1-不允许）',
    is_delete     TINYINT(4)          NOT NULL DEFAULT 0 COMMENT '是否删除（0-正常, 1-删除）',
    edit_time     DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    create_time   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    INDEX idx_item_type (item_type ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '物品表'
  ROW_FORMAT = DYNAMIC;
# 物品表初始数据
INSERT INTO items (id, item_type, item_name, item_desc, item_image, repeat_status)
VALUES (1, 0, '改名卡', '用户可以使用改名卡，更改自己的名字。', NULL, 0);
INSERT INTO items (id, item_type, item_name, item_desc, item_image, repeat_status)
VALUES (2, 1, '元老徽章', '前10名注册的用户才能获得的专属徽章。',
        'https://cdn-icons-png.flaticon.com/512/6198/6198527.png', 1);
INSERT INTO items (id, item_type, item_name, item_desc, item_image, repeat_status)
VALUES (3, 1, '前100徽章', '前100名注册的用户才能获得的专属徽章。',
        'https://cdn-icons-png.flaticon.com/512/10232/10232583.png', 1);
INSERT INTO items (id, item_type, item_name, item_desc, item_image, repeat_status)
VALUES (4, 1, '爆赞徽章', '单条消息被点赞超过10次，即可获得。',
        'https://cdn-icons-png.flaticon.com/128/1533/1533913.png', 1);
INSERT INTO items (id, item_type, item_name, item_desc, item_image, repeat_status)
VALUES (5, 1, '星梦徽章', '星梦联盟成员的专属徽章。',
        'https://cdn-icons-png.flaticon.com/128/2909/2909937.png', 1);


# 用户背包表
DROP TABLE IF EXISTS user_backpack;
CREATE TABLE user_backpack
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    user_id     BIGINT(20)          NOT NULL COMMENT '用户ID',
    item_id     BIGINT(20)          NOT NULL COMMENT '物品ID',
    idempotent  VARCHAR(64)         NOT NULL COMMENT '幂等号',
    use_status  TINYINT             NOT NULL DEFAULT 0 COMMENT '使用状态（0-待使用, 1-已使用）',
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


# 黑名单表
DROP TABLE IF EXISTS blacklist;
CREATE TABLE blacklist
(
    id           BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '黑名单ID',
    black_type   TINYINT             NOT NULL COMMENT '拉黑类型（0-用户ID、1-IP地址）',
    black_target VARCHAR(128)        NOT NULL COMMENT '拉黑目标',
    operator     BIGINT              NOT NULL COMMENT '操作人',
    create_time  DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time  DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX idx_black_type_target (black_type, black_target) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '黑名单表'
  ROW_FORMAT = DYNAMIC;

# 系统角色表
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    role_name   VARCHAR(64)         NOT NULL COMMENT '角色名称',
    role_sign   VARCHAR(64)         NOT NULL COMMENT '角色标识',
    role_desc   VARCHAR(256)        NULL     DEFAULT NULL COMMENT '角色描述',
    is_delete   TINYINT(4)          NOT NULL DEFAULT 0 COMMENT '是否删除（0-正常, 1-删除）',
    edit_time   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    create_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX uk_role_sign (role_sign) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '系统角色表'
  ROW_FORMAT = DYNAMIC;
# 系统角色表初始数据
INSERT INTO sys_role(id, role_name, role_sign, role_desc)
VALUES (1, '超级管理员', 'SUPER', '拥有整个系统权限');
INSERT INTO sys_role(id, role_name, role_sign, role_desc)
VALUES (2, '系统管理员', 'ADMIN', '拥有除了[S:]开头的所有权限');
INSERT INTO sys_role(id, role_name, role_sign, role_desc)
VALUES (3, '普通用户', 'USER', '普通用户');

# 用户角色关联表
DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id     BIGINT(20)          NOT NULL COMMENT '用户ID',
    role_id     BIGINT(20)          NOT NULL COMMENT '角色ID',
    create_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    KEY idx_user_id (user_id) USING BTREE,
    KEY idx_role_id (role_id) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户角色关联表'
  ROW_FORMAT = DYNAMIC;
# 用户角色关联表初始数据
INSERT INTO user_role(id, user_id, role_id)
VALUES (1, 1, 1);
INSERT INTO user_role(id, user_id, role_id)
VALUES (2, 2, 2);
INSERT INTO user_role(id, user_id, role_id)
VALUES (3, 3, 3);


# 系统权限表
DROP TABLE IF EXISTS sys_permission;
CREATE TABLE sys_permission
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '权限ID',
    perm_name   VARCHAR(64)         NOT NULL COMMENT '权限名称',
    perm_sign   VARCHAR(64)         NOT NULL COMMENT '权限标识',
    perm_desc   VARCHAR(256)        NULL     DEFAULT NULL COMMENT '权限描述',
    is_delete   TINYINT(4)          NOT NULL DEFAULT 0 COMMENT '是否删除（0-正常, 1-删除）',
    edit_time   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    create_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX uk_perm_sign (perm_sign) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '系统权限表'
  ROW_FORMAT = DYNAMIC;
# 系统权限表初始数据
INSERT INTO sys_permission(id, perm_name, perm_sign, perm_desc)
VALUES (1, '所有权限', 'S:ALL', '拥有所有权限');
INSERT INTO sys_permission(id, perm_name, perm_sign, perm_desc)
VALUES (2, '管理权限', 'A:ALL', '拥有管理权限');
INSERT INTO sys_permission(id, perm_name, perm_sign, perm_desc)
VALUES (3, '用户权限', 'U:ALL', '拥有用户权限');

# 角色权限关联表
DROP TABLE IF EXISTS role_permission;
CREATE TABLE role_permission
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    role_id     BIGINT(20)          NOT NULL COMMENT '角色ID',
    perm_id     BIGINT(20)          NOT NULL COMMENT '权限ID',
    create_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    KEY idx_role_id (role_id) USING BTREE,
    KEY idx_user_id (perm_id) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '角色权限关联表'
  ROW_FORMAT = DYNAMIC;
# 角色权限关联表初始数据
INSERT INTO role_permission(id, role_id, perm_id)
VALUES (1, 1, 1);
INSERT INTO role_permission(id, role_id, perm_id)
VALUES (2, 2, 2);
INSERT INTO role_permission(id, role_id, perm_id)
VALUES (3, 3, 3);


# 房间表
DROP TABLE IF EXISTS room;
CREATE TABLE room
(
    id            BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '房间ID',
    room_type     TINYINT             NOT NULL COMMENT '房间类型（0-单聊、1-群聊）',
    show_type     TINYINT             NOT NULL DEFAULT 0 COMMENT '展示类型（0-非全员展示、1-全员展示）',
    last_msg_id   BIGINT(20)          NULL     DEFAULT NULL COMMENT '房间中最后一条消息ID',
    last_msg_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '房间中最后一条消息时间（全员群用到）',
    extend_info   JSON                NULL     DEFAULT NULL COMMENT '扩展信息（根据不同类型房间存储不同的信息）',
    is_delete     TINYINT(4)          NOT NULL DEFAULT 0 COMMENT '是否删除（0-正常, 1-删除）',
    edit_time     DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    create_time   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    KEY idx_create_time (create_time) USING BTREE,
    KEY idx_update_time (update_time) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '房间表'
  ROW_FORMAT = DYNAMIC;
# 房间表初始数据
INSERT INTO room (id, room_type, show_type)
VALUES (1, 1, 1);


# 单聊房间表
DROP TABLE IF EXISTS room_single;
CREATE TABLE room_single
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '单聊ID',
    room_id     BIGINT(20)          NOT NULL COMMENT '房间ID',
    user_id1    BIGINT(20)          NOT NULL COMMENT '用户ID1（较小的ID）',
    user_id2    BIGINT(20)          NOT NULL COMMENT '用户ID2（较大的ID）',
    room_key    VARCHAR(64)         NOT NULL COMMENT '房间key（由两个用户ID根据逗号拼接, 需要先做排序再拼接）',
    room_status TINYINT             NOT NULL DEFAULT 0 COMMENT '房间状态（0-正常、1-禁用, 删好友了）',
    create_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE KEY room_key (room_key) USING BTREE,
    KEY idx_room_id (room_id) USING BTREE,
    KEY idx_create_time (create_time) USING BTREE,
    KEY idx_update_time (update_time) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '单聊房间表'
  ROW_FORMAT = DYNAMIC;

# 群聊房间表
DROP TABLE IF EXISTS room_group;
CREATE TABLE room_group
(
    id           BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '群聊ID',
    room_id      BIGINT(20)          NOT NULL COMMENT '房间ID',
    leader_id    BIGINT(20)          NOT NULL COMMENT '群主ID',
    group_name   VARCHAR(128)        NOT NULL COMMENT '群名称',
    group_avatar VARCHAR(500)        NOT NULL COMMENT '群头像',
    extend_info  JSON                NULL     DEFAULT NULL COMMENT '扩展信息（根据不同类型房间存储不同的信息）',
    is_delete    TINYINT(4)          NOT NULL DEFAULT 0 COMMENT '是否删除（0-正常, 1-删除）',
    edit_time    DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    create_time  DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time  DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    KEY idx_room_id (room_id) USING BTREE,
    KEY idx_create_time (create_time) USING BTREE,
    KEY idx_update_time (update_time) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '群聊房间表'
  ROW_FORMAT = DYNAMIC;
INSERT INTO room_group (id, room_id, leader_id, group_name, group_avatar)
VALUES (1, 1, 1, '暴龙社交平台全员群', '封面');


# 群聊成员表
DROP TABLE IF EXISTS room_group_member;
CREATE TABLE room_group_member
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    group_id    BIGINT(20)          NOT NULL COMMENT '群聊ID',
    user_id     BIGINT(20)          NOT NULL COMMENT '用户ID',
    member_role TINYINT             NOT NULL COMMENT '成员角色（0-群主、1-管理、2-成员）',
    create_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    KEY idx_group_id_role (group_id, member_role) USING BTREE,
    KEY idx_create_time (create_time) USING BTREE,
    KEY idx_update_time (update_time) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '群聊成员表'
  ROW_FORMAT = DYNAMIC;

# 房间会话表
DROP TABLE IF EXISTS room_contact;
CREATE TABLE room_contact
(
    id            BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '会话ID',
    user_id       BIGINT(20)          NOT NULL COMMENT '用户ID',
    room_id       BIGINT(20)          NOT NULL COMMENT '房间ID',
    read_time     DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '会话读取的时间',
    last_msg_id   BIGINT(20)          NULL     DEFAULT NULL COMMENT '会话中最后一条消息ID',
    last_msg_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '会话中最后一条消息时间（非全员群用到）',
    create_time   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE KEY uniq_user_id_room_id (user_id, room_id) USING BTREE,
    KEY idx_room_id_read_time (room_id, read_time) USING BTREE,
    KEY idx_create_time (create_time) USING BTREE,
    KEY idx_update_time (update_time) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '房间会话表'
  ROW_FORMAT = DYNAMIC;

# 消息表
DROP TABLE IF EXISTS message;
CREATE TABLE message
(
    id             BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '消息ID',
    room_id        BIGINT(20)          NOT NULL COMMENT '房间ID',
    sender_id      BIGINT(20)          NOT NULL COMMENT '发送者ID',
    message_type   TINYINT             NOT NULL DEFAULT 0 COMMENT '消息类型消息类型（1-正常文本、2-撤回消息、3-表情、4-图片、5-视频、6-语音、7-文件、8-系统）',
    content        VARCHAR(1024)       NULL     DEFAULT NULL COMMENT '消息内容',
    reply_id       BIGINT(20)          NULL     DEFAULT NULL COMMENT '回复的消息ID',
    reply_gap      INT(11)             NULL     DEFAULT NULL COMMENT '与回复的消息间隔多少条',
    message_status TINYINT             NOT NULL DEFAULT 0 COMMENT '消息状态（0-正常、1-删除）',
    extend_info    JSON                NULL     DEFAULT NULL COMMENT '扩展信息',
    create_time    DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time    DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    INDEX idx_room_id (room_id) USING BTREE,
    INDEX idx_sender_id (sender_id) USING BTREE,
    INDEX idx_create_time (create_time) USING BTREE,
    INDEX idx_update_time (update_time) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '消息表'
  ROW_FORMAT = DYNAMIC;

# 好友表
DROP TABLE IF EXISTS friend;
CREATE TABLE friend
(
    id          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    my_id       BIGINT(20)          NOT NULL COMMENT '我的ID',
    friend_id   BIGINT(20)          NOT NULL COMMENT '好友ID',
    is_delete   TINYINT(4)          NOT NULL DEFAULT 0 COMMENT '是否删除（0-正常, 1-删除）',
    edit_time   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    create_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    KEY idx_my_id_friend_id (my_id, friend_id) USING BTREE,
    KEY idx_create_time (create_time) USING BTREE,
    KEY idx_update_time (update_time) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '好友表'
  ROW_FORMAT = DYNAMIC;

# 用户申请表
DROP TABLE IF EXISTS user_apply;
CREATE TABLE user_apply
(
    id            BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    apply_user    BIGINT(20)          NOT NULL COMMENT '申请人ID',
    apply_type    TINYINT             NOT NULL COMMENT '申请类型（1-加好友、2-加群聊）',
    target_user   BIGINT(20)          NOT NULL COMMENT '接收人ID',
    apply_content VARCHAR(256)        NOT NULL COMMENT '申请内容',
    apply_status  TINYINT             NOT NULL DEFAULT 0 COMMENT '申请状态（0-待审批、1-同意、2-拒绝）',
    read_status   TINYINT             NOT NULL COMMENT '阅读状态（0-未读、1-已读）',
    is_delete     TINYINT(4)          NOT NULL DEFAULT 0 COMMENT '是否删除（0-正常, 1-删除）',
    edit_time     DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    create_time   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time   DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    KEY idx_apply_user_target_user (apply_user, target_user) USING BTREE,
    KEY idx_target_user_read_status (target_user, read_status) USING BTREE,
    KEY target_user (target_user) USING BTREE,
    KEY idx_create_time (create_time) USING BTREE,
    KEY idx_update_time (update_time) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户申请表'
  ROW_FORMAT = DYNAMIC;


# 消息调用记录（存放所有未执行的消息）
DROP TABLE IF EXISTS message_invoke_record;
CREATE TABLE message_invoke_record
(
    id              BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    snapshot_param  JSON                NOT NULL COMMENT '快照参数（JSON格式）',
    invoke_status   TINYINT             NULL     DEFAULT 0 COMMENT '执行状态（1-待执行、2-已失败）',
    max_retry_count INT                 NOT NULL COMMENT '最大重试次数',
    retry_count     INT                 NOT NULL COMMENT '已经重试次数',
    next_retry_time DATETIME            NOT NULL COMMENT '下一次重试时间',
    fail_reason     TEXT                NULL     DEFAULT NULL COMMENT '执行失败的堆栈信息',
    create_time     DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE,
    KEY idx_next_retry_time (next_retry_time) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '消息调用记录（存放所有未执行的消息）'
  ROW_FORMAT = DYNAMIC;
