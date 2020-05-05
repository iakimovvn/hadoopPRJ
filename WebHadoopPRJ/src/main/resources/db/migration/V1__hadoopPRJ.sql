CREATE SCHEMA IF NOT EXISTS public;


DROP TABLE IF EXISTS wfl_column;

create table wfl_column
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_column_pkey primary key,
    version integer not null,
    name    varchar(255),
    type    varchar(255)
);


insert into wfl_column (id, version, name, type) values ('2bd18073-9183-4957-9f1e-ad1009d02d71', 0, 'id', 'uuid');


DROP TABLE IF EXISTS wfl_type;

create table wfl_type
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_type_pkey primary key,
    version    integer not null,
    class_name varchar(255) not null unique,
    title      varchar(255) not null unique
);

insert into wfl_type(id, version ,class_name, title) values ('bc76edae-bc3a-4e39-9964-389c02e953ea', 0, 'ru.yakimov.mysqlToDir.SqoopMysqlTpDir', 'SqoopMysqlTpDir');
insert into wfl_type(id, version ,class_name, title) values ('a9f72115-493d-406e-b73b-0e17a46b9a9d', 0, 'ru.yakimov.deleteFromTable.DeleteFromTable', 'DeleteFromTable');



DROP TABLE IF EXISTS wfl_database;

create table wfl_database
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_database_pkey primary key,
    version  integer not null,
    url      varchar(255),
    username varchar(255),
    password varchar(255)
);

insert into wfl_database (id, version, url, username, password) values ('c5ce6a3d-ccd0-405b-8ac4-e11d2b4764dd', 0, 'jdbc:postgresql://192.168.0.10:5432/hadoopprj', 'user', 'pass');


DROP TABLE IF EXISTS wfl_directory;

create table wfl_directory
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_directory_pkey primary key,
    version integer not null,
    path    varchar(255)
);

insert into wfl_directory (id, version, path) values ('42f37b77-c88e-453c-8fe2-09d6b4ec28b7', 0, '/my/folder');
insert into wfl_directory (id, version, path) values ('596aceeb-840c-4218-9ed9-dc87b753c923', 0, '/my/not');
insert into wfl_directory (id, version, path) values ('05c19168-c052-4074-925c-100e59fc05bb', 0, '/my/older');

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

insert into wfl_table (id, version, name, database) values ('8f6a5bab-5b5a-4725-8a67-cbfb262a1104', 0, 'mytable', 'c5ce6a3d-ccd0-405b-8ac4-e11d2b4764dd');

DROP TABLE IF EXISTS wfl_config;

create table wfl_config
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_config_pkey primary key,
    version          integer not null,
    wfl_directory_to uuid
        constraint FK_config_directory_to
            references wfl_directory
);

insert into wfl_config (id, version, wfl_directory_to) values ('3658f925-2f59-4517-a047-64edbcb61fb9', 0, '42f37b77-c88e-453c-8fe2-09d6b4ec28b7');

DROP TABLE IF EXISTS wfl_user;

create table wfl_user
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT  wfl_user_pkey primary key,
    version    integer not null,
    login      varchar(255) UNIQUE NOT NULL,
    password   varchar(255) NOT NULL,
    first_name varchar(255),
    last_name  varchar(255),
    phone      varchar(255),
    email      varchar(255) UNIQUE NOT NULL,
    role       varchar(255) NOT NULL
);


INSERT INTO wfl_user (id, version,login ,phone, password, email, first_name, last_name, role) VALUES ('6b718067-e1e4-4202-a7e2-7339ea0d6cb4', 0,'anonymous','anonymous', 'anonymous', 'anonymous@supershop.com', 'anonymous', 'anonymous', 'ROLE_CUSTOMER');
INSERT INTO wfl_user (id, version,login,  phone, password, email, first_name, last_name, role) VALUES ('fbe5a8e7-8555-4ee8-bff2-c572447e5f25', 0,'11111111','11111111', '$2a$10$5rAOMKmVsh9.NlzXTLLbq.XwouGdg3dwohvb5/HDn692YfdrLthO2', 'admin@supershop.com', 'Admin', 'Admin','ROLE_ADMIN');
INSERT INTO wfl_user (id, version,login, phone, password, email, first_name, last_name, role) VALUES ('04c8bd30-ba4e-4e82-b996-db907e37a2c6', 0,'22222222','22222222', '$2a$10$5rAOMKmVsh9.NlzXTLLbq.XwouGdg3dwohvb5/HDn692YfdrLthO2', 'user@supershop.com', 'User', 'User', 'ROLE_ADMIN');



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
        references wfl_user,
    deleted boolean default false

);

insert into workflow (id, version, create_date, last_run_date, title, wfl_config, wfl_type, wfl_user,deleted) values ('b7fb3e6d-ea5f-481d-81ee-e9ad663afcb5', 0 , '2012-08-04', '2020-03-03', 'Table', '3658f925-2f59-4517-a047-64edbcb61fb9', 'bc76edae-bc3a-4e39-9964-389c02e953ea', 'fbe5a8e7-8555-4ee8-bff2-c572447e5f25', false);
insert into workflow (id, version, create_date, last_run_date, title, wfl_config, wfl_type, wfl_user, deleted) values ('16531daf-76a7-47c2-ae6a-1bb45b034efd', 0 , '2000-08-04', '2001-03-03', 'Pork', '3658f925-2f59-4517-a047-64edbcb61fb9', 'bc76edae-bc3a-4e39-9964-389c02e953ea', 'fbe5a8e7-8555-4ee8-bff2-c572447e5f25', false);
insert into workflow (id, version, create_date, last_run_date, title, wfl_config, wfl_type, wfl_user, deleted) values ('a026ed4a-ce71-4610-93be-f66251ecc973', 0 , '2018-08-04', '2019-03-03', 'Cou for me', '3658f925-2f59-4517-a047-64edbcb61fb9', 'a9f72115-493d-406e-b73b-0e17a46b9a9d', 'fbe5a8e7-8555-4ee8-bff2-c572447e5f25', false);


DROP TABLE IF EXISTS wfl_logfile;

create table wfl_logfile
(
    id uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_logfile_pkey primary key,
    version     integer not null,
    date        date not null default now(),
    file        varchar(255),
    workflow_id uuid not null
        constraint FK_logfile_workflow
            references workflow
            ON UPDATE CASCADE ON DELETE CASCADE
);

insert into wfl_logfile (id, version, date, file, workflow_id) VALUES ('b72be988-776b-4084-9739-867f4f87cfb5', 0,'2018-12-12', 'folder.log', 'b7fb3e6d-ea5f-481d-81ee-e9ad663afcb5');
insert into wfl_logfile (id, version, date, file, workflow_id) VALUES ('804b3065-991b-4a60-94b8-b6bb6609df47', 0, '2020-12-12','folder1.log', '16531daf-76a7-47c2-ae6a-1bb45b034efd');
insert into wfl_logfile (id, version, date, file, workflow_id) VALUES ('701c34a9-fc36-4fce-a4cc-e4a46eb57935', 0, '2020-11-03', 'folder2.log', 'a026ed4a-ce71-4610-93be-f66251ecc973');
insert into wfl_logfile (id, version, date, file, workflow_id) VALUES ('17a89612-e590-4c9e-b08b-1c42b3a671d3', 0, '2020-11-12', 'folder3.log', 'a026ed4a-ce71-4610-93be-f66251ecc973');


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


insert into wfl_config_directory_from (wfl_config, wfl_directory) VALUES ('3658f925-2f59-4517-a047-64edbcb61fb9','596aceeb-840c-4218-9ed9-dc87b753c923');
insert into wfl_config_directory_from (wfl_config, wfl_directory) VALUES ('3658f925-2f59-4517-a047-64edbcb61fb9','05c19168-c052-4074-925c-100e59fc05bb');


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

insert into wfl_config_partitions (wfl_config, wfl_column) VALUES ('3658f925-2f59-4517-a047-64edbcb61fb9', '2bd18073-9183-4957-9f1e-ad1009d02d71');



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


insert into wfl_config_table (wfl_config, wfl_table) VALUES ('3658f925-2f59-4517-a047-64edbcb61fb9', '8f6a5bab-5b5a-4725-8a67-cbfb262a1104');



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

insert into wfl_table_primaries (wfl_table, wfl_column) VALUES ('8f6a5bab-5b5a-4725-8a67-cbfb262a1104', '2bd18073-9183-4957-9f1e-ad1009d02d71');