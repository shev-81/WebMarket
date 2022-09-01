create table users (
                       id                    bigserial primary key,
                       username              varchar(30) not null unique,
                       password              varchar(80) not null,
                       fio                   varchar(80) not null,
                       email                 varchar(50) unique,
                       phone                 varchar(20) unique,
                       created_at            timestamp default current_timestamp,
                       updated_at            timestamp default current_timestamp
);

create table roles (
                       id                    serial primary key,
                       name                  varchar(50) not null,
                       created_at            timestamp default current_timestamp,
                       updated_at            timestamp default current_timestamp
);

CREATE TABLE users_roles (
                             user_id               bigint not null,
                             role_id               int not null,
                             primary key (user_id, role_id),
                             foreign key (user_id) references users (id),
                             foreign key (role_id) references roles (id)
);

insert into roles (name)
values
    ('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_MANAGER'), ('READER_BOOK');

insert into users (username, password, fio, email, phone)
values
    ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'Иванов Иван Иванович', 'user1@gmail.com', 8-800-900-3321),
    ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'Сидоров Петр Васильевич', 'user2@gmail.com', 8-903-178-3050),
    ('manager', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'Шевеленко Андрей Александрович', 'user3@gmail.com', 8-801-903-1221);

insert into users_roles (user_id, role_id)
values
    (1, 1),
    (2, 1),
    (2, 2),
    (2, 3),
    (3, 1),
    (3, 3);