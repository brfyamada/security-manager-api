CREATE TABLE user_auth (
    idt_use_id BIGSERIAL NOT NULL,
    des_name varchar(255) NOT NULL,
    des_email varchar(150) NOT NULL,
    des_password varchar(150) NOT NULL,
    des_authorities varchar(255) NOT NULL,
    dat_creation TIMESTAMP DEFAULT NOW() NOT NULL,
    dat_update TIMESTAMP,
    des_user_operation varchar(50) NOT NULL
);

INSERT INTO user_auth VALUES (1,'admin','admin@email.com', '$2a$10$mRhyXS6DO0nt22BYOL/tK.y24hPumeWa71hsvCJqj9PPK1IowMXz6', 'ROLE_USER,ROLE_ADMIN', now(), null, 'customer-service-api');
