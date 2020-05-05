CREATE SCHEMA IF NOT EXISTS public;


DROP TABLE IF EXISTS wfl_column;

create table wfl_column
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_column_pkey primary key,
    version integer not null,
    name    varchar(255),
    type    varchar(255)
);


DROP TABLE IF EXISTS wfl_type;

create table wfl_type
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_type_pkey primary key,
    version    integer not null,
    class_name varchar(255),
    title      varchar(255)
);

DROP TABLE IF EXISTS wfl_database;

create table wfl_database
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_database_pkey primary key,
    version  integer not null,
    url      varchar(255),
    username varchar(255),
    password varchar(255)
);


DROP TABLE IF EXISTS wfl_directory;

create table wfl_directory
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_directory_pkey primary key,
    version integer not null,
    path    varchar(255)
);

DROP TABLE IF EXISTS wfl_table;

create table wfl_table
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_table_pkey primary key,
    version  integer not null,
    name     varchar(255),
    database uuid
        constraint FK_wfl_table_database
            references wfl_database
            ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS wfl_config;

create table wfl_config
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_config_pkey primary key,
    version          integer not null,
    wfl_directory_to uuid
        constraint FK_config_directory_to
            references wfl_directory
);

DROP TABLE IF EXISTS wfl_user;

create table wfl_user
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT  wfl_user_pkey primary key,
    version    integer not null,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    phone      varchar(255),
    role       varchar(255)
);


INSERT INTO wfl_user (id, version, phone, password, email, first_name, last_name, role) VALUES ('6b718067-e1e4-4202-a7e2-7339ea0d6cb4', 0,'anonymous', 'anonymous', 'anonymous@supershop.com', 'anonymous', 'anonymous', 'ROLE_CUSTOMER');
INSERT INTO wfl_user (id, version,  phone, password, email, first_name, last_name, role) VALUES ('fbe5a8e7-8555-4ee8-bff2-c572447e5f25', 0,'11111111', '$2a$10$5rAOMKmVsh9.NlzXTLLbq.XwouGdg3dwohvb5/HDn692YfdrLthO2', 'admin@supershop.com', 'Admin', 'Admin','ROLE_ADMIN');
INSERT INTO wfl_user (id, version, phone, password, email, first_name, last_name, role) VALUES ('04c8bd30-ba4e-4e82-b996-db907e37a2c6', 0,'22222222', '$2a$10$5rAOMKmVsh9.NlzXTLLbq.XwouGdg3dwohvb5/HDn692YfdrLthO2', 'user@supershop.com', 'User', 'User', 'ROLE_ADMIN');



DROP TABLE IF EXISTS workflow;

create table workflow
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT workflow_pkey primary key,
    version       integer not null,
    create_date   date,
    last_run_date date,
    title         varchar(255),
    wfl_config    uuid
        constraint FK_workflow_config
            references wfl_config,
    wfl_type      uuid
        constraint FK_workflow_type
            references wfl_type,
    wfl_user      uuid
        constraint FK_workflow_user
        references wfl_user
);

DROP TABLE IF EXISTS wfl_logfile;

create table wfl_logfile
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_logfile_pkey primary key,
    version     integer not null,
    date        date,
    file        varchar(255),
    workflow_id uuid
        constraint FK_logfile_workflow
            references workflow
            ON UPDATE CASCADE ON DELETE CASCADE
);


DROP TABLE IF EXISTS wfl_config_directory_from;


create table wfl_config_directory_from
(
    wfl_config    uuid not null
        constraint FK_config_directory_from_config
            references wfl_config,
    wfl_directory uuid not null
        constraint FK_config_directory_from_directory
            references wfl_directory,
    constraint wfl_config_directory_from_pkey
        primary key (wfl_config, wfl_directory)
);

DROP TABLE IF EXISTS wfl_config_partitions;

create table wfl_config_partitions
(
    wfl_config uuid not null
        constraint FK_wfl_config_partitions_config
            references wfl_config,
    wfl_column uuid not null
        constraint FK_wfl_config_partitions_column
            references wfl_column,
    constraint wfl_config_partitions_pkey
        primary key (wfl_config, wfl_column)
);

DROP TABLE IF EXISTS wfl_config_table;


create table wfl_config_table
(
    wfl_config uuid not null
        constraint FK_wfl_config_table_config
            references wfl_config,
    wfl_table  uuid not null
        constraint FK_wfl_config_table_table
            references wfl_table,
    constraint wfl_config_table_pkey
        primary key (wfl_config, wfl_table)
);

DROP TABLE IF EXISTS wfl_table_primaries;

create table wfl_table_primaries
(
    wfl_table  uuid not null
        constraint FK_wfl_table_primaries_table
            references wfl_table,
    wfl_column uuid not null
        constraint FK_wfl_table_primaries_column
            references wfl_column,
    constraint wfl_table_primaries_pkey
        primary key (wfl_table, wfl_column)
);
