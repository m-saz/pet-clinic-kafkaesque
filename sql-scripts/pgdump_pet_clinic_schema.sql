--
-- PostgreSQL database dump
--

-- Dumped from database version 12.9 (Ubuntu 12.9-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.9 (Ubuntu 12.9-0ubuntu0.20.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: owners; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.owners (
    id integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    email character varying(50) NOT NULL,
    phone_number character varying(50) NOT NULL,
    city character varying(50) NOT NULL,
    address character varying(255) NOT NULL
);


ALTER TABLE public.owners OWNER TO postgres;

--
-- Name: owners_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.owners_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.owners_id_seq OWNER TO postgres;

--
-- Name: owners_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.owners_id_seq OWNED BY public.owners.id;


--
-- Name: pets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pets (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    birth_date date,
    type_id integer NOT NULL,
    owner_id integer NOT NULL
);


ALTER TABLE public.pets OWNER TO postgres;

--
-- Name: pets_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pets_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pets_id_seq OWNER TO postgres;

--
-- Name: pets_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pets_id_seq OWNED BY public.pets.id;


--
-- Name: specialties; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.specialties (
    id integer NOT NULL,
    specialty character varying(50) NOT NULL
);


ALTER TABLE public.specialties OWNER TO postgres;

--
-- Name: specialties_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.specialties_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.specialties_id_seq OWNER TO postgres;

--
-- Name: specialties_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.specialties_id_seq OWNED BY public.specialties.id;


--
-- Name: types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.types (
    id integer NOT NULL,
    type character varying(50) NOT NULL
);


ALTER TABLE public.types OWNER TO postgres;

--
-- Name: types_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.types_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.types_id_seq OWNER TO postgres;

--
-- Name: types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.types_id_seq OWNED BY public.types.id;


--
-- Name: vet_specialties; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vet_specialties (
    vet_id integer NOT NULL,
    specialty_id integer NOT NULL
);


ALTER TABLE public.vet_specialties OWNER TO postgres;

--
-- Name: vets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vets (
    id integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL
);


ALTER TABLE public.vets OWNER TO postgres;

--
-- Name: vets_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vets_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vets_id_seq OWNER TO postgres;

--
-- Name: vets_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vets_id_seq OWNED BY public.vets.id;


--
-- Name: visits; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visits (
    id integer NOT NULL,
    pet_id integer NOT NULL,
    visit_date date NOT NULL,
    description character varying(255)
);


ALTER TABLE public.visits OWNER TO postgres;

--
-- Name: visits_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.visits_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.visits_id_seq OWNER TO postgres;

--
-- Name: visits_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visits_id_seq OWNED BY public.visits.id;


--
-- Name: owners id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.owners ALTER COLUMN id SET DEFAULT nextval('public.owners_id_seq'::regclass);


--
-- Name: pets id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pets ALTER COLUMN id SET DEFAULT nextval('public.pets_id_seq'::regclass);


--
-- Name: specialties id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.specialties ALTER COLUMN id SET DEFAULT nextval('public.specialties_id_seq'::regclass);


--
-- Name: types id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.types ALTER COLUMN id SET DEFAULT nextval('public.types_id_seq'::regclass);


--
-- Name: vets id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vets ALTER COLUMN id SET DEFAULT nextval('public.vets_id_seq'::regclass);


--
-- Name: visits id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visits ALTER COLUMN id SET DEFAULT nextval('public.visits_id_seq'::regclass);


--
-- Name: owners owners_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.owners
    ADD CONSTRAINT owners_pkey PRIMARY KEY (id);


--
-- Name: pets pets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pets
    ADD CONSTRAINT pets_pkey PRIMARY KEY (id);


--
-- Name: specialties specialties_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.specialties
    ADD CONSTRAINT specialties_pkey PRIMARY KEY (id);


--
-- Name: types types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.types
    ADD CONSTRAINT types_pkey PRIMARY KEY (id);


--
-- Name: vet_specialties vet_specialties_vet_id_specialty_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vet_specialties
    ADD CONSTRAINT vet_specialties_vet_id_specialty_id_key UNIQUE (vet_id, specialty_id);


--
-- Name: vets vets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vets
    ADD CONSTRAINT vets_pkey PRIMARY KEY (id);


--
-- Name: visits visits_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visits
    ADD CONSTRAINT visits_pkey PRIMARY KEY (id);


--
-- Name: pets pets_owner_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pets
    ADD CONSTRAINT pets_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES public.owners(id);


--
-- Name: pets pets_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pets
    ADD CONSTRAINT pets_type_id_fkey FOREIGN KEY (type_id) REFERENCES public.types(id);


--
-- Name: vet_specialties vet_specialties_specialty_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vet_specialties
    ADD CONSTRAINT vet_specialties_specialty_id_fkey FOREIGN KEY (specialty_id) REFERENCES public.specialties(id);


--
-- Name: vet_specialties vet_specialties_vet_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vet_specialties
    ADD CONSTRAINT vet_specialties_vet_id_fkey FOREIGN KEY (vet_id) REFERENCES public.vets(id);


--
-- Name: visits visits_pet_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visits
    ADD CONSTRAINT visits_pet_id_fkey FOREIGN KEY (pet_id) REFERENCES public.pets(id);


--
-- PostgreSQL database dump complete
--

