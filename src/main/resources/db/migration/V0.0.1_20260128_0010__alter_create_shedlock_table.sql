-- Alter table short_url to change short_code column size to varchar(32)
alter table short_url
    alter column short_code type varchar(32);