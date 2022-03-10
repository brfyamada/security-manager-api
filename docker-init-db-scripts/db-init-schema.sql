DROP SCHEMA IF EXISTS security-manager;
CREATE SCHEMA security-manager;

CREATE EXTENSION pgcrypto SCHEMA security-manager;

CREATE TABLESPACE tsdsecuritymanager01 OWNER postgres LOCATION '/var/lib/postgresql/data/pg_tblspc';
CREATE TABLESPACE tsisecuritymanager01 OWNER postgres LOCATION '/var/lib/postgresql/data/';

