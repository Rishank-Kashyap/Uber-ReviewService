-- Initial schema for UberReviewService
-- Generated from the current JPA entities.
-- JOINED inheritance: `review` is the base table; `booking_review` and
-- `passenger_review` are child tables whose PK is also an FK to `review`.

-- ---------- base / parent tables ----------

create table review (
    id         bigint       not null auto_increment,
    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    content    varchar(255) not null,
    rating     float(53)    not null,
    primary key (id)
) engine=InnoDB;

create table booking_review (
    id bigint not null,
    primary key (id)
) engine=InnoDB;

create table passenger_review (
    id bigint not null,
    primary key (id)
) engine=InnoDB;

create table driver (
    id             bigint       not null auto_increment,
    created_at     datetime(6)  not null,
    updated_at     datetime(6)  not null,
    name           varchar(255),
    email          varchar(255),
    password       varchar(255),
    phone          varchar(255),
    address        varchar(255),
    city           varchar(255),
    state          varchar(255),
    zip            varchar(255),
    country        varchar(255),
    license_number varchar(255) not null,
    primary key (id),
    constraint uk_driver_license_number unique (license_number)
) engine=InnoDB;

create table passenger (
    id           bigint       not null auto_increment,
    created_at   datetime(6)  not null,
    updated_at   datetime(6)  not null,
    name         varchar(255),
    email        varchar(255),
    password     varchar(255),
    phone_number varchar(255),
    address      varchar(255),
    city         varchar(255),
    state        varchar(255),
    zip_code     varchar(255),
    country      varchar(255),
    primary key (id)
) engine=InnoDB;

create table booking (
    id                  bigint      not null auto_increment,
    created_at          datetime(6) not null,
    updated_at          datetime(6) not null,
    booking_status      varchar(255),
    start_time          datetime(6),
    end_time            datetime(6),
    total_distance      bigint,
    booking_review_id   bigint,
    passenger_review_id bigint,
    driver_id           bigint,
    passenger_id        bigint,
    primary key (id)
) engine=InnoDB;

-- ---------- foreign keys ----------

alter table booking_review
    add constraint fk_booking_review_review
    foreign key (id) references review (id);

alter table passenger_review
    add constraint fk_passenger_review_review
    foreign key (id) references review (id);

alter table booking
    add constraint fk_booking_booking_review
    foreign key (booking_review_id) references booking_review (id);

alter table booking
    add constraint fk_booking_passenger_review
    foreign key (passenger_review_id) references passenger_review (id);

alter table booking
    add constraint fk_booking_driver
    foreign key (driver_id) references driver (id);

alter table booking
    add constraint fk_booking_passenger
    foreign key (passenger_id) references passenger (id);
