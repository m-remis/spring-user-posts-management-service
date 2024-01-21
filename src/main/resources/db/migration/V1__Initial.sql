create table "user_post"
(
    "id"      int primary key,
    "user_id" int          not null,
    "title"   varchar(100) not null,
    "body"    varchar      not null
);