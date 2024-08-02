CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP TABLE IF EXISTS t_rack_asset;
DROP TABLE IF EXISTS t_rack;
DROP TABLE IF EXISTS t_team;
DROP TABLE IF EXISTS t_team_member;
DROP TABLE IF EXISTS t_booking;
DROP TYPE IF EXISTS STATUS CASCADE;
DROP TYPE IF EXISTS DEFAULT_LOCATION CASCADE;

CREATE TABLE IF NOT EXISTS T_TEAM (
    id uuid DEFAULT gen_random_uuid(),
    name VARCHAR(20),
    product VARCHAR(20),
    created_at TIMESTAMP DEFAULT now(),
    modified_at TIMESTAMP DEFAULT now(),
    default_location VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS T_TEAM_MEMBER (
    id uuid DEFAULT gen_random_uuid(),
    team_id uuid NOT NULL,
    ctw_id VARCHAR(20) NOT NULL,
    name VARCHAR (30),
    created_at TIMESTAMP DEFAULT now(),
    modified_at TIMESTAMP DEFAULT now(),
    PRIMARY KEY (id),
    FOREIGN KEY(team_id) REFERENCES t_team(id)
    );

CREATE TABLE IF NOT EXISTS T_RACK (
    id uuid DEFAULT gen_random_uuid(),
    serial_number VARCHAR(25) NOT NULL UNIQUE,
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    team_id uuid NOT NULL,
    default_location VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    modified_at TIMESTAMP DEFAULT now(),
    PRIMARY KEY (id),
    FOREIGN KEY(team_id) REFERENCES t_team(id)
    );

CREATE TABLE IF NOT EXISTS T_RACK_ASSET (
    id uuid DEFAULT gen_random_uuid(),
    asset_tag VARCHAR(25) NOT NULL,
    rack_id uuid NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(rack_id) REFERENCES t_rack(id)
    );

CREATE TABLE IF NOT EXISTS T_BOOKING (
    id uuid DEFAULT gen_random_uuid(),
    rack_id uuid NOT NULL,
    requester_id uuid NOT NULL,
    book_from TIMESTAMP,
    book_to TIMESTAMP,
    created_at TIMESTAMP DEFAULT now(),
    modified_at TIMESTAMP DEFAULT now(),
    PRIMARY KEY (id),
    FOREIGN KEY(requester_id) REFERENCES t_team_member(id),
    FOREIGN KEY (rack_id) REFERENCES t_rack(id)
    );






