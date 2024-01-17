insert into USERS(name, email, password, active, created_at, modified_at) values
    ( 'admin', 'admin@gmail.com', '$2a$10$IFZ8J3Y5KbH8bc71kJaL2OY8qwR2H3DCTEpCD5zATucWHd6H959Ym', 1, '1912-05-08', '1912-05-08' ),
    ( 'Petr Petrov', 'petrov@gmail.com', '$2a$10$IFZ8J3Y5KbH8bc71kJaL2OY8qwR2H3DCTEpCD5zATucWHd6H959Ym', 1, '1912-05-08', '1912-05-08' );

insert into ROLES (NAME) values ('ROLE_ADMIN'), ('ROLE_AUTHOR');
insert into USERS_ROLES values(1, 1), (2, 2);

insert into CONTACTS (NAME, URL, ACTIVE)
    values ('GitHub', 'https://github.com', 1),
           ('VK', 'https://vk.com', 1),
           ('Facebook', 'https://facebook.com', 0),
           ('Twitter', 'https://twitter.com', 0),
           ('OK', 'https://ok.ru', 0),
           ('Instagram', 'https://instagram.com', 0),
           ('LinkedIn', 'https://linkedin.com', 0),
           ('Pinterest', 'https://pinterest.com', 0);

insert into CATEGORIES(NAME, ACTIVE)
values ('World', 1), ('Technology', 1), ('Design', 1), ('Culture', 1), ('Business', 1),
       ('Politics', 1), ('Science', 1), ('Health', 1), ('Style', 1), ('Travel', 1);

insert into POSTS(TITLE, CONTENT, AUTHOR_ID, CATEGORY_ID, VIEWED_TIMES, ACTIVE, CREATED_AT, MODIFIED_AT)
    values ('Title 1', 'Content content content', 2, 1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
           ('Title 2', 'Content content content', 1, 1, 2, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
           ('Title 3', 'Content content content', 2, 2, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);