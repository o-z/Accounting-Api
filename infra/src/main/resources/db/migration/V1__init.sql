create table BILLING
(
    ID              bigint auto_increment
        primary key,
    CREATE_DATE     datetime(6) null,
    UPDATE_DATE     datetime(6) null,
    BILL_NO         varchar(255)   not null,
    CURRENCY        enum ('TRY') not null,
    MANAGER_ID      bigint         not null,
    PRICE           decimal(38, 2) not null,
    PRODUCT_NAME    varchar(255)   not null,
    PROGRESS_STATUS enum ('NOT_ACCEPTED', 'SUCCESS') not null
);

create table MANAGER
(
    ID                bigint auto_increment
        primary key,
    CREATE_DATE       datetime(6) null,
    UPDATE_DATE       datetime(6) null,
    EMAIL             varchar(255) null,
    FIRST_NAME        varchar(255) null,
    LAST_NAME         varchar(255) null,
    MAX_CREDIT_LIMIT  decimal(38, 2) null,
    USED_CREDIT_LIMIT decimal(19, 2) default 0.00 not null,
    constraint UKl9schu8un4tiypc1dpj2gbpec
        unique (FIRST_NAME, LAST_NAME, EMAIL)
);

create index ACCOUNTING_MANAGER_EMAIL_INDEX
    on MANAGER (EMAIL);



INSERT INTO ACCOUNTING.BILLING (ID, CREATE_DATE, UPDATE_DATE, BILL_NO, CURRENCY, MANAGER_ID, PRICE,
                                PRODUCT_NAME, PROGRESS_STATUS)
VALUES (1, '2024-11-19 01:32:14.382030', '2024-11-19 01:32:14.382030', 'string', 'TRY', 1, 10.00,
        'string', 'SUCCESS');
INSERT INTO ACCOUNTING.BILLING (ID, CREATE_DATE, UPDATE_DATE, BILL_NO, CURRENCY, MANAGER_ID, PRICE,
                                PRODUCT_NAME, PROGRESS_STATUS)
VALUES (2, '2024-11-19 01:32:22.377005', '2024-11-19 01:32:22.377005', 'string', 'TRY', 1, 100.00,
        'string', 'SUCCESS');
INSERT INTO ACCOUNTING.BILLING (ID, CREATE_DATE, UPDATE_DATE, BILL_NO, CURRENCY, MANAGER_ID, PRICE,
                                PRODUCT_NAME, PROGRESS_STATUS)
VALUES (3, '2024-11-19 01:32:27.732006', '2024-11-19 01:32:27.732006', 'string', 'TRY', 1, 100.00,
        'string', 'NOT_ACCEPTED');


INSERT INTO ACCOUNTING.MANAGER (ID, CREATE_DATE, UPDATE_DATE, EMAIL, FIRST_NAME, LAST_NAME,
                                MAX_CREDIT_LIMIT, USED_CREDIT_LIMIT)
VALUES (1, '2024-11-19 01:31:03.867331', '2024-11-19 01:32:22.389087', 'example@example.com',
        'Oguz', 'Zeyveli', 201.00, 110.00);
