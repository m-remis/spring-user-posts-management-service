drop table if exists "user_post";
create table "user_post"
(
    "id"      int primary key,
    "user_id" int         not null,
    "title"   varchar(50) not null,
    "body"    varchar     not null
);