--liquibase formatted sql

--changeset jean.tapias:auth.schema_changelog.20240819_1850.1 context:test,dev,prod

CREATE SCHEMA IF NOT EXISTS auth;

--changeset jean.tapias:auth.app_user_changelog.20240819_1850.1 context:test,dev,prod

CREATE TABLE auth.app_user
(
	id             BIGSERIAL NOT NULL,
	email          CHARACTER VARYING(255) NOT NULL,
	password       TEXT NOT NULL,
	name           CHARACTER VARYING(127) NOT NULL,
	is_active      BOOLEAN NOT NULL DEFAULT true,
	last_login     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	created_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	updated_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	CONSTRAINT pk_app_user PRIMARY KEY (id)
);

--changeset jean.tapias:auth.phone_changelog.20240819_1850.1 context:test,dev,prod

CREATE TABLE auth.phone
(
	id              BIGSERIAL NOT NULL,
	app_user_id     BIGINT NOT NULL,
	phone           CHARACTER VARYING (15) NOT NULL,
	created_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	updated_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	CONSTRAINT pk_phone PRIMARY KEY (id)
);

ALTER TABLE auth.phone ADD CONSTRAINT fk_phone_app_user_id FOREIGN KEY (app_user_id) REFERENCES auth.app_user (id);

--changeset zlab:auth.param_changelog.20240819_1906.1 context:test,dev,prod

CREATE TABLE auth.param
(
	id             BIGSERIAL NOT NULL,
	value          TEXT NOT NULL,
	created_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	updated_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	CONSTRAINT pk_param PRIMARY KEY (id)
);

--changeset zlab:auth.param_changelog.0.4 context:test,dev,prod

INSERT INTO auth.param (id, value) VALUES (1, 'L09_1lxZ0N3t21');
INSERT INTO auth.param (id, value) VALUES (2, '25');

--changeset zlab:auth.phone_changelog.20240820_0040.6 context:test,dev,prod

ALTER TABLE auth.phone ADD COLUMN city_code text NOT NULL;

--changeset zlab:auth.phone_changelog.20240820_0040.7 context:test,dev,prod

ALTER TABLE auth.phone ADD COLUMN country_code text NOT NULL;

--changeset zlab:auth.phone_changelog.20240820_0040.8 context:test,dev,prod

ALTER TABLE auth.phone RENAME COLUMN phone TO number;