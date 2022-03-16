CREATE TABLE application_auth (
        idt_application_id BIGSERIAL NOT NULL,
        des_name varchar(255) NOT NULL,
        des_client_id varchar(50) NOT NULL,
        des_secret_key varchar(50) NOT NULL,
        des_owner varchar(50) NOT NULL,
        dat_creation TIMESTAMP DEFAULT NOW() NOT NULL,
        dat_update TIMESTAMP,
        des_user_operation varchar(50) NOT NULL
);

ALTER TABLE application_auth ADD CONSTRAINT idt_application_id_pk PRIMARY KEY (idt_application_id);

comment on table application_auth is 'Table that hold applications client_id and secret_key';
comment on column application_auth.des_client_id is 'Application client_id used to authenticate like a name';
comment on column application_auth.des_secret_key is 'Application secret_key used to authenticate like a password';
