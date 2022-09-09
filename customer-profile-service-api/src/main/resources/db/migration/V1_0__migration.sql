CREATE TABLE public.profile
(
    name character(100) COLLATE pg_catalog."default" NOT NULL,
    email character(100) COLLATE pg_catalog."default",
    birthday date,
    primary_phone character(10) COLLATE pg_catalog."default",
    sex character(10) COLLATE pg_catalog."default",
    CONSTRAINT name_pkey PRIMARY KEY (name)
);

CREATE TABLE public.address
(
    alley character(50) COLLATE pg_catalog."default",
    lane character(50) COLLATE pg_catalog."default",
    id integer NOT NULL,
    road character(50) COLLATE pg_catalog."default",
    district character(50) COLLATE pg_catalog."default",
    sub_district character(50) COLLATE pg_catalog."default",
    province character(50) COLLATE pg_catalog."default",
    postal_code character(10) COLLATE pg_catalog."default",
    village_no character(50) COLLATE pg_catalog."default",
    house_no character(50) COLLATE pg_catalog."default",
    user_name character(100) COLLATE pg_catalog."default"
);

CREATE SEQUENCE public.hibernate_sequence
    INCREMENT 2
    START 2
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.hibernate_sequence
    OWNER TO postgres;