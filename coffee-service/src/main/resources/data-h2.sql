/**
 * Author:  Armin
 * Created: Dec 20, 2018
 */
delete from user_authority;
delete from coffee;
delete from image_store;
delete from application_user where id = 1;
delete from authority;


insert into authority(name) values( 'ROLE_ADMIN'), ('ROLE_ENGINEER'), ('ROLE_BAD_MANAGER');

INSERT INTO application_user(
    id,
    created_by,
    created_date,
    email,
    first_name,
    last_name,
    password_hash,
    username )
VALUES
    (1,
    'system',
    now(),
    'admin@localhost',
    'Glavni',
    'Admin',
    '$2a$10$4TQgu3egClCaGHYB3rA72uEcs0X1w.dEhPse0QIoJsYb4Z8Lca0FK',
    'admin' );
   
insert into user_authority( user_id, authority_name ) values ( 1, 'ROLE_ADMIN' );