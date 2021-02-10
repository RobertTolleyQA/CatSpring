drop table if exists cat_domain CASCADE;
drop table if exists house_domain CASCADE;
create table cat_domain (id bigint generated by default as identity, age integer not null, meow_volume integer not null, name varchar(255), my_house_id bigint, primary key (id));
create table house_domain (id bigint generated by default as identity, address varchar(255), primary key (id));
alter table cat_domain add constraint FKg2wia8mwluf94kesqlkqggrhq foreign key (my_house_id) references house_domain on delete cascade;