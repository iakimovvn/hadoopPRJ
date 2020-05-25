CREATE SCHEMA IF NOT EXISTS public;

DROP TABLE IF EXISTS wfl_type;

create table wfl_type
(
    uuid uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_type_pkey primary key,
    version    integer not null,
    class_name varchar(255) not null unique,
    title      varchar(255) not null unique
);

insert into wfl_type(uuid, version ,class_name, title) values ('bc76edae-bc3a-4e39-9964-389c02e953ea', 0, 'ru.yakimov.mysqlToDir.SqoopMysqlTpDir', 'SqoopMysqlTpDir');
insert into wfl_type(uuid, version ,class_name, title) values ('a9f72115-493d-406e-b73b-0e17a46b9a9d', 0, 'ru.yakimov.deleteFromTable.DeleteFromTable', 'DeleteFromTable');



DROP TABLE IF EXISTS wfl_database;

create table wfl_database
(
    uuid uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_database_pkey primary key,
    version  integer not null,
    url      varchar(255),
    username varchar(255),
    password varchar(255)
);

insert into wfl_database (uuid, version, url, username, password) values ('c5ce6a3d-ccd0-405b-8ac4-e11d2b4764dd', 0, 'jdbc:postgresql://192.168.0.10:5432/hadoopprj', 'user', 'pass');
insert into wfl_database (uuid, version, url, username, password) values ('22d6a1e1-924b-484c-ba86-91ef0d91fd39', 0, 'jdbc:mysql://192.168.0.10:5432/hadoopprj', 'user', 'pass');
insert into wfl_database (uuid, version, url, username, password) values ('21806a2d-a762-4b4c-9e11-24f81215fada', 0, 'jdbc:oracledb://192.168.0.10:5432/hadoopprj', 'user', 'pass');


DROP TABLE IF EXISTS wfl_directory_to;

create table wfl_directory_to
(
    uuid uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_directory_to_pkey primary key,
    version integer not null,
    path    varchar(255)
);

insert into wfl_directory_to (uuid, version, path) values ('42f37b77-c88e-453c-8fe2-09d6b4ec28b7', 0, '/my/folder');
insert into wfl_directory_to (uuid, version, path) values ('596aceeb-840c-4218-9ed9-dc87b753c923', 0, '/my/not');
insert into wfl_directory_to (uuid, version, path) values ('05c19168-c052-4074-925c-100e59fc05bb', 0, '/my/older');


DROP TABLE IF EXISTS wfl_config;

create table wfl_config
(
    uuid uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_config_pkey primary key,
    version          integer not null,
    wfl_directory_to uuid
        constraint FK_config_directory_to
            references wfl_directory_to
            ON UPDATE CASCADE ON DELETE CASCADE
);

insert into wfl_config (uuid, version, wfl_directory_to) values ('3658f925-2f59-4517-a047-64edbcb61fb9', 0, '42f37b77-c88e-453c-8fe2-09d6b4ec28b7');
insert into wfl_config (uuid, version, wfl_directory_to) values ('8a37c47c-e21c-4887-8ab0-b7cbe0d43b63', 0, '596aceeb-840c-4218-9ed9-dc87b753c923');
insert into wfl_config (uuid, version, wfl_directory_to) values ('78655856-d0c4-49bc-bde9-c8f9a3200a36', 0, '05c19168-c052-4074-925c-100e59fc05bb');

DROP TABLE IF EXISTS wfl_table;

create table wfl_table
(
    uuid uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_table_pkey primary key,
    version  integer not null,
    name     varchar(255),
    database uuid
        constraint FK_wfl_table_database
        references wfl_database
        ON UPDATE CASCADE ON DELETE CASCADE,
    wfl_config uuid
        constraint FK_wfl_table_config
        references wfl_config
);

insert into wfl_table (uuid, version, name, database, wfl_config) values ('8f6a5bab-5b5a-4725-8a67-cbfb262a1104', 0, 'mytable', 'c5ce6a3d-ccd0-405b-8ac4-e11d2b4764dd','3658f925-2f59-4517-a047-64edbcb61fb9');
insert into wfl_table (uuid, version, name, database, wfl_config) values ('337bb4fd-50d2-4390-b17e-f44613561519', 0, 'mytable', '22d6a1e1-924b-484c-ba86-91ef0d91fd39','8a37c47c-e21c-4887-8ab0-b7cbe0d43b63');
insert into wfl_table (uuid, version, name, database, wfl_config) values ('a773fce1-cdad-448c-9be7-5522a33e45b7', 0, 'mytable', '21806a2d-a762-4b4c-9e11-24f81215fada','78655856-d0c4-49bc-bde9-c8f9a3200a36');

DROP TABLE IF EXISTS wfl_column;

create table wfl_column
(
    uuid uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_column_pkey primary key,
    version integer not null,
    name    varchar(255),
    type    varchar(255),
    wfl_table uuid
        constraint wfl_column_table
            references wfl_table
            ON UPDATE CASCADE ON DELETE CASCADE
);

insert into wfl_column (uuid, version, name, type, wfl_table) values ('2bd18073-9183-4957-9f1e-ad1009d02d71', 0, 'id', 'uuid', '8f6a5bab-5b5a-4725-8a67-cbfb262a1104');
insert into wfl_column (uuid, version, name, type, wfl_table) values ('00893656-4b3b-4a76-a022-ccc8dc8a7320', 0, 'idOne', 'uuid', '337bb4fd-50d2-4390-b17e-f44613561519');
insert into wfl_column (uuid, version, name, type, wfl_table) values ('4e83f8da-e6d7-4720-a91f-e643a6cfd1b5', 0, 'idTwo', 'uuid', 'a773fce1-cdad-448c-9be7-5522a33e45b7');




DROP TABLE IF EXISTS wfl_partition;

create table wfl_partition
(
    uuid uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_partition_pkey primary key,
    version integer not null,
    name    varchar(255),
    type    varchar(255),
    wfl_config uuid
        constraint wfl_partition_config
            references wfl_config
            ON UPDATE CASCADE ON DELETE CASCADE
);

insert into wfl_partition (uuid, version, name, type, wfl_config) values ('76804a8f-76e8-4e8b-907a-d28186317caa', 0, 'id', 'uuid', '3658f925-2f59-4517-a047-64edbcb61fb9');
insert into wfl_partition (uuid, version, name, type, wfl_config) values ('d4fb0a6e-cb67-4939-bbb3-6e7d0061591a', 0, 'idOne', 'uuid', '8a37c47c-e21c-4887-8ab0-b7cbe0d43b63');
insert into wfl_partition (uuid, version, name, type, wfl_config) values ('15ffa311-9bb6-4a94-832f-96323f3efde8', 0, 'idTwo', 'uuid', '78655856-d0c4-49bc-bde9-c8f9a3200a36');





DROP TABLE IF EXISTS wfl_directory_from;

create table wfl_directory_from
(
    uuid uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_directory_pkey primary key,
    version integer not null,
    path    varchar(255),
    wfl_config uuid
        constraint wfl_directory_from_config
            references wfl_config
            ON UPDATE CASCADE ON DELETE CASCADE
);

insert into wfl_directory_from (uuid, version, path, wfl_config) VALUES ('b6823172-fc1d-43ab-adae-28fb76f54653',0, '/folderFromOne','3658f925-2f59-4517-a047-64edbcb61fb9');
insert into wfl_directory_from (uuid, version, path, wfl_config) VALUES ('d2e2ab32-6c84-4c04-b9cc-1d5ca8a0d50a',0, 'folderFromTwo','8a37c47c-e21c-4887-8ab0-b7cbe0d43b63');
insert into wfl_directory_from (uuid, version, path, wfl_config) VALUES ('1f400e26-cfcc-44bb-8398-04c3635c2ae5',0, 'folderFromThree','78655856-d0c4-49bc-bde9-c8f9a3200a36');


DROP TABLE IF EXISTS wfl_user;

create table wfl_user
(
    uuid uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT  wfl_user_pkey primary key,
    version    integer not null,
    login      varchar(255) UNIQUE NOT NULL,
    password   varchar(255) NOT NULL,
    first_name varchar(255),
    last_name  varchar(255),
    phone      varchar(255),
    email      varchar(255) UNIQUE NOT NULL,
    hdfs_folder  varchar(255) NOT NULL,
    log_folder varchar(255) NOT NULL,
    role       varchar(255) NOT NULL
);


INSERT INTO wfl_user (uuid, version,login ,phone, password, email, first_name, last_name,hdfs_folder, log_folder, role) VALUES ('6b718067-e1e4-4202-a7e2-7339ea0d6cb4', 0,'anonymous','anonymous', 'anonymous', 'anonymous@supershop.com', 'anonymous', 'anonymous','/anonymous', '/anonymous','ROLE_CUSTOMER');
INSERT INTO wfl_user (uuid, version,login,  phone, password, email, first_name, last_name, hdfs_folder,log_folder,role) VALUES ('fbe5a8e7-8555-4ee8-bff2-c572447e5f25', 0,'11111111','11111111', '$2a$10$5rAOMKmVsh9.NlzXTLLbq.XwouGdg3dwohvb5/HDn692YfdrLthO2', 'admin@supershop.com', 'Admin', 'Admin','/','/11111111', 'ROLE_ADMIN');
INSERT INTO wfl_user (uuid, version,login, phone, password, email, first_name, last_name, hdfs_folder,log_folder, role) VALUES ('04c8bd30-ba4e-4e82-b996-db907e37a2c6', 0,'22222222','22222222', '$2a$10$5rAOMKmVsh9.NlzXTLLbq.XwouGdg3dwohvb5/HDn692YfdrLthO2', 'user@supershop.com', 'User', 'User','/', '/22222222','ROLE_ADMIN');



DROP TABLE IF EXISTS workflow;

create table workflow
(
    uuid uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT workflow_pkey primary key,
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
    deleted boolean default false,
    run boolean default false


);

insert into workflow (uuid, version, create_date, last_run_date, title, wfl_config, wfl_type, wfl_user,deleted,run) values ('b7fb3e6d-ea5f-481d-81ee-e9ad663afcb5', 0 , '2012-08-04', '2020-03-03', 'Table', '3658f925-2f59-4517-a047-64edbcb61fb9', 'bc76edae-bc3a-4e39-9964-389c02e953ea', 'fbe5a8e7-8555-4ee8-bff2-c572447e5f25', false, false);
insert into workflow (uuid, version, create_date, last_run_date, title, wfl_config, wfl_type, wfl_user, deleted,run) values ('16531daf-76a7-47c2-ae6a-1bb45b034efd', 0 , '2000-08-04', '2001-03-03', 'Pork', '8a37c47c-e21c-4887-8ab0-b7cbe0d43b63', 'bc76edae-bc3a-4e39-9964-389c02e953ea', 'fbe5a8e7-8555-4ee8-bff2-c572447e5f25', false, false);
insert into workflow (uuid, version, create_date, last_run_date, title, wfl_config, wfl_type, wfl_user, deleted,run) values ('a026ed4a-ce71-4610-93be-f66251ecc973', 0 , '2018-08-04', '2019-03-03', 'Cou for me', '78655856-d0c4-49bc-bde9-c8f9a3200a36', 'a9f72115-493d-406e-b73b-0e17a46b9a9d', 'fbe5a8e7-8555-4ee8-bff2-c572447e5f25', false,false);


DROP TABLE IF EXISTS wfl_logfile;

create table wfl_logfile
(
    uuid uuid DEFAULT uuid_generate_v4() UNIQUE NOT NULL CONSTRAINT wfl_logfile_pkey primary key,
    version     integer not null,
    date        date not null default now(),
    file        varchar(255),
    writing boolean default false,

        workflow uuid not null
        constraint FK_logfile_workflow
            references workflow
            ON UPDATE CASCADE ON DELETE CASCADE
);

insert into wfl_logfile (uuid, version, date, file, workflow) VALUES ('b72be988-776b-4084-9739-867f4f87cfb5', 0,'2018-12-12', '/logfile.log', 'b7fb3e6d-ea5f-481d-81ee-e9ad663afcb5');
insert into wfl_logfile (uuid, version, date, file, workflow) VALUES ('804b3065-991b-4a60-94b8-b6bb6609df47', 0, '2020-12-12','folder1.log', '16531daf-76a7-47c2-ae6a-1bb45b034efd');
insert into wfl_logfile (uuid, version, date, file, workflow) VALUES ('701c34a9-fc36-4fce-a4cc-e4a46eb57935', 0, '2020-11-03', 'folder2.log', 'a026ed4a-ce71-4610-93be-f66251ecc973');
insert into wfl_logfile (uuid, version, date, file, workflow) VALUES ('17a89612-e590-4c9e-b08b-1c42b3a671d3', 0, '2020-11-12', 'folder3.log', 'a026ed4a-ce71-4610-93be-f66251ecc973');

