-- auto-generated definition
create table user
(
    id           bigint auto_increment,
    username     varchar(256)                        null comment '用户名称',
    userAccount  varchar(256)                        null comment '账号',
    avatarUrl    varchar(1024)                       null comment '头像',
    phone        varchar(128)                        null comment '电话',
    gender       tinyint                             null comment '性别',
    userPassword varchar(512)                        null comment '密码',
    email        varchar(512)                        null comment '电子邮箱',
    userStatus   int       default 0                 not null comment '0 正常',
    createTime   datetime  default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   timestamp default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete     tinyint   default 0                 not null comment '是否删除 0 1',
    userRole     int       default 0                 null comment '用户角色 0 管理员 1 用户',
    planetCode   varchar(512)                        null comment '星球编号',
    constraint user_pk
        unique (id)
)
    comment '用户表';

