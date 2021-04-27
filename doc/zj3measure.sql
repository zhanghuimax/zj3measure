create database zhgd_zj3measure;

create table zhgd_zj3measure.d_measure_item
(
    item_id                 varchar(64) collate utf8_bin     not null comment '测量分项主键id'
        primary key,
    company_id              int                              not null comment '企业id',
    project_id              int                              not null comment '项目id',
    template_id             varchar(64) collate utf8_bin     not null comment '任务模板主键id',
    type_id                 varchar(64) collate utf8_bin     not null comment '测量类型主键id',
    inspect_method_describe varchar(500) collate utf8mb4_bin not null comment '检查方法描述',
    cite_norm_describe      varchar(500) collate utf8mb4_bin not null comment '引用规范描述',
    measure_area_count      int                              not null comment '测量区数量',
    area_point_count        int                              not null comment '每区测量点数量',
    is_forbidden            tinyint                          null comment '是否禁止手工录入：1 禁止 2 非禁止',
    create_id               char(32) collate utf8_bin        not null comment '创建人',
    modify_id               char(32) collate utf8_bin        null comment '修改人',
    gmt_create              datetime(3)                      not null comment '创建时间',
    gmt_modify              datetime(3)                      null comment '修改时间',
    is_delete               tinyint                          null comment '是否删除：1 正常 2 删除'
)
    comment '实测实量--测量分项表';

create index index_company_project_id
    on zhgd_zj3measure.d_measure_item (company_id, project_id);

create index index_template_id
    on zhgd_zj3measure.d_measure_item (template_id);

create index index_type_id
    on zhgd_zj3measure.d_measure_item (type_id);

create table zhgd_zj3measure.d_measure_place
(
    place_id    varchar(64) collate utf8_bin    not null comment '部位主键id'
        primary key,
    company_id  int                             not null comment '企业id',
    project_id  int                             not null comment '项目id',
    template_id varchar(64) collate utf8_bin    not null comment '任务模板id',
    place_one   varchar(20) collate utf8mb4_bin not null comment '区域',
    place_two   varchar(20) collate utf8mb4_bin not null comment '楼栋',
    place_three varchar(20) collate utf8mb4_bin not null comment '单元',
    place_four  varchar(20) collate utf8mb4_bin not null comment '楼层',
    place_five  varchar(20) collate utf8mb4_bin not null comment '部位',
    create_id   char(32) collate utf8_bin       not null comment '创建人',
    modify_id   char(32) collate utf8_bin       null comment '修改人',
    gmt_create  datetime(3)                     not null comment '创建时间',
    gmt_modify  datetime(3)                     null comment '修改时间',
    is_delete   tinyint                         not null comment '是否删除：1 正常 2 删除'
)
    comment '实测实量--测量部位表';

create index index_company_project_id
    on zhgd_zj3measure.d_measure_place (company_id, project_id);

create index index_template_id
    on zhgd_zj3measure.d_measure_place (template_id);

create table zhgd_zj3measure.d_measure_standard
(
    standard_id         varchar(64) collate utf8_bin    not null comment '评判标准主键id'
        primary key,
    company_id          int                             not null comment '企业id',
    project_id          int                             not null comment '项目id',
    item_id             varchar(64) collate utf8_bin    not null comment '测量分项主键id',
    standard_name       varchar(20) collate utf8mb4_bin not null comment '评判标准名称',
    base_value          decimal(9, 2)                   not null comment '基准值',
    judge_start_value   decimal(9, 2)                   not null comment '评判标准开始值',
    judge_end_value     decimal(9, 2)                   not null comment '评判标准结束值',
    is_absolute_value   tinyint                         null comment '是否绝对值：1、是；2、否',
    is_range_arithmetic tinyint                         null comment '是否极差算法：1、是；2、否',
    is_custom           tinyint                         null comment '是否自定义基准值：1、是；2、否',
    create_id           char(32) collate utf8_bin       not null comment '创建人',
    modify_id           char(32) collate utf8_bin       null comment '修改人',
    gmt_create          datetime(3)                     not null comment '创建时间',
    gmt_modify          datetime(3)                     null comment '修改时间',
    is_delete           tinyint                         null comment '是否删除：1 正常 2 删除'
)
    comment '实测实量--测量分项评判标准表';

create index index_company_project_id
    on zhgd_zj3measure.d_measure_standard (company_id, project_id);

create index index_item_id
    on zhgd_zj3measure.d_measure_standard (item_id);

create table zhgd_zj3measure.d_measure_task
(
    task_id           varchar(64) collate utf8_bin     not null comment '任务主键id'
        primary key,
    company_id        int                              not null comment '企业id',
    project_id        int                              not null comment '项目id',
    measure_task_name varchar(64) collate utf8_bin     null comment '测量任务名称',
    measure_task_no   varchar(64) collate utf8_bin     null comment '测量任务编号',
    template_id       varchar(64)                      not null comment '模板主键id',
    member_id         char(32) collate utf8_bin        not null comment '测量人员id',
    type_id           varchar(64) collate utf8_bin     not null comment '测量类型id',
    place_id          varchar(64) collate utf8_bin     null comment '部位主键id',
    project_name      varchar(256) collate utf8mb4_bin null comment '项目名称',
    unit_name         varchar(64) collate utf8_bin     null comment '劳务单位名称',
    status            tinyint                          not null comment '测量任务状态 1指派待测 2指派进行中 3本地进行中 4待整改 5待复测 6已完成 ',
    pass_rate         decimal(9, 2)                    null comment '合格率',
    current_points    int                              not null comment '当前完成测量点数',
    total_points      int                              not null comment '测量点总数',
    total_items       int                              null comment '测量分项总数',
    check_time        datetime                         null comment '提交时间',
    is_read           tinyint                          not null comment '是否已读：1、已读；2、未读',
    is_checked        tinyint default 0                null comment '发起检查 0 未发起 1 已发起',
    check_item        text                             null comment '检查分项json',
    create_id         char(32) collate utf8_bin        not null comment '创建人',
    modify_id         char(32) collate utf8_bin        null comment '修改人',
    gmt_create        datetime(3)                      not null comment '创建时间',
    gmt_modify        datetime(3)                      null comment '修改时间',
    is_delete         tinyint default 1                not null comment '是否删除：1 正常 2 删除'
)
    comment '实测实量--任务表';

create index index_company_project_id
    on zhgd_zj3measure.d_measure_task (company_id, project_id);

create table zhgd_zj3measure.d_measure_task_template
(
    template_id            varchar(64)  not null comment '主键id'
        primary key,
    company_id             int          not null comment '企业id',
    project_id             int          not null comment '项目id',
    one_name               varchar(255) not null comment '一级名称',
    two_name               varchar(255) null comment '二级名称',
    org_name               varchar(255) null comment '所属单位',
    type_id                varchar(64)  not null comment '测量类型id',
    check_item_count       int          not null comment '检查分项数量',
    measure_position_count int          not null comment '测量部位数量',
    create_id              varchar(32)  not null comment '创建人',
    modify_id              varchar(32)  null comment '修改人',
    gmt_create             datetime(3)  not null comment '创建时间',
    gmt_modify             datetime(3)  null comment '修改时间',
    is_delete              tinyint      not null comment '是否删除：1、正常；2、删除'
)
    comment '实测指标--测量任务模板';

create index idx_company_id_project_id
    on zhgd_zj3measure.d_measure_task_template (company_id, project_id);

create index idx_is_delete
    on zhgd_zj3measure.d_measure_task_template (is_delete);

create index idx_type_id
    on zhgd_zj3measure.d_measure_task_template (type_id);

create table zhgd_zj3measure.s_measure_common_setting
(
    pass_id         varchar(64)   not null comment '主键id'
        primary key,
    company_id      int           not null comment '企业id',
    project_id      int           not null comment '项目id',
    percent_pass    decimal(9, 2) not null comment '合格率',
    is_input_forbid tinyint       null comment '1 非禁止 2禁止',
    create_id       varchar(32)   not null comment '创建人',
    modify_id       varchar(32)   null comment '修改人',
    gmt_create      datetime      not null comment '创建时间',
    gmt_modify      datetime      null comment '修改时间',
    org_name        varchar(200)  null comment '所属单位'
)
    comment '实测实量--通用设置';

create table zhgd_zj3measure.s_measure_type
(
    type_id     varchar(64)   not null comment '主键id'
        primary key,
    name        varchar(255)  not null comment '类型名称',
    sort_number double(10, 2) null,
    parent_id   varchar(64)   null comment '上级id',
    create_id   varchar(32)   null comment '创建人',
    modify_id   varchar(32)   null comment '修改人',
    gmt_create  datetime      not null comment '创建时间',
    gmt_modify  datetime      not null comment '修改时间',
    is_delete   tinyint       not null comment '是否删除：1、正常；2、删除'
)
    comment '实测实量--测量类型表';

create index idx_parent_id
    on zhgd_zj3measure.s_measure_type (parent_id);

