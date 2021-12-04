-- Database: OMS

-- DROP DATABASE IF EXISTS "OMS";

CREATE DATABASE "OMS"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
    
-- Table: public.order

-- DROP TABLE IF EXISTS public."order";
 
CREATE TABLE IF NOT EXISTS public."order_info"
(
    id bigint NOT NULL PRIMARY KEY,
    order_name character varying COLLATE pg_catalog."default"
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."order_info"
    OWNER to postgres;
