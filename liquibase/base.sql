CREATE DATABASE smj_auth;
CREATE USER smj_auth_user WITH PASSWORD 'X23HJ1Y0';

GRANT smj_auth_user TO postgres;
REVOKE ALL ON DATABASE smj_auth FROM PUBLIC;
ALTER DATABASE "smj_auth" OWNER TO smj_auth_user;

create extension IF NOT EXISTS "uuid-ossp" schema pg_catalog version "1.1";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";