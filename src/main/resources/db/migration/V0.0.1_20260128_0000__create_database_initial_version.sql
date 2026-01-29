-- Initial database creation script
create table short_url (
    id bigserial primary key,
    short_code varchar(4) not null,
    original_url varchar(255) not null,
    created_at timestamp without time zone,
    expires_at timestamp WITHOUT time zone,
    --
    unique(short_code)
);
