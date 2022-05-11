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

--
-- Data for Name: owners; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.owners VALUES (2, 'Betty', 'Davis', 'bd@email.com', '6085551749', 'Sun Prairie', '638 Cardinal Ave.');
INSERT INTO public.owners VALUES (4, 'Harold', 'Davis', 'hd@email.com', '6085553198', 'Windsor', '563 Friendly St.');
INSERT INTO public.owners VALUES (5, 'Peter', 'McTavish', 'pmt@email.com', '6085552765', 'Madison', '2387 S. Fair Way');
INSERT INTO public.owners VALUES (6, 'Jean', 'Coleman', 'jc@email.com', '6085552654', 'Monona', '105 N. Lake St.');
INSERT INTO public.owners VALUES (7, 'Jeff', 'Black', 'jb@email.com', '6085555387', 'Monona', '1450 Oak Blvd.');
INSERT INTO public.owners VALUES (8, 'Maria', 'Escobito', 'me@email.com', '6085557683', 'Madison', '345 Maple St.');
INSERT INTO public.owners VALUES (10, 'Carlos', 'Estaban', 'ce@email.com', '6085555487', 'Waunakee', '2335 Independence La.');
INSERT INTO public.owners VALUES (19, 'Carl', 'Satan', 'carl@mailbox.com', '1666266636', 'Hell', '666 Hell St.');
INSERT INTO public.owners VALUES (16, 'Diego', 'La Vega', 'diego@gmail.com', '2355876492', 'Madrid', '555 Back Street');
INSERT INTO public.owners VALUES (22, 'Bob', 'Ford', 'bff@gmail.com', '0987654321', 'Pasadena', '433 Ford St.');
INSERT INTO public.owners VALUES (25, 'Travis', 'Smith', 'travis.s@gmail.com', '7766567833', 'Barcelona', '5423 Anywhere Ave');
INSERT INTO public.owners VALUES (26, 'Пётр', 'Смирнов', 'smirnoff@mail.ru', '9501234567', 'Москва', 'ул. Вмоскве д.1 к.2 кв.17');
INSERT INTO public.owners VALUES (1, 'Richard', 'Franklin', 'gf@email.com', '6085551023', 'Madison', '110 W. Liberty St.');
INSERT INTO public.owners VALUES (3, 'Pedro', 'Rodriquez', 'er@email.com', '6085558764', 'McFarland', '2693 Commerce St.');
INSERT INTO public.owners VALUES (28, 'Michael', 'Sport', 'sportrules@gmail.com', '9500231213', 'New London', '1234 Streetname St');
INSERT INTO public.owners VALUES (29, 'Диван', 'Тестов', 'test@gmail.com', '9232645725', 'Город', 'Адресная ул. д.6 к.6 кв.6');
INSERT INTO public.owners VALUES (27, 'Daniel', 'Boss', 'boss@gmail.com', '1323434223', 'Paris', '21314 Rebel St');


--
-- Data for Name: types; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.types VALUES (1, 'cat');
INSERT INTO public.types VALUES (2, 'dog');
INSERT INTO public.types VALUES (3, 'lizard');
INSERT INTO public.types VALUES (4, 'snake');
INSERT INTO public.types VALUES (5, 'bird');
INSERT INTO public.types VALUES (6, 'hamster');


--
-- Data for Name: pets; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pets VALUES (4, 'Basil', '2002-08-06', 6, 2);
INSERT INTO public.pets VALUES (5, 'Iggy', '2000-11-30', 3, 4);
INSERT INTO public.pets VALUES (6, 'George', '2000-01-20', 4, 5);
INSERT INTO public.pets VALUES (7, 'Samantha', '1995-09-04', 1, 6);
INSERT INTO public.pets VALUES (8, 'Max', '1995-09-04', 1, 6);
INSERT INTO public.pets VALUES (9, 'Lucky', '1999-08-06', 5, 7);
INSERT INTO public.pets VALUES (10, 'Mulligan', '1997-02-24', 2, 8);
INSERT INTO public.pets VALUES (12, 'Lucky', '2000-06-24', 2, 10);
INSERT INTO public.pets VALUES (13, 'Sly', '2002-06-08', 1, 10);
INSERT INTO public.pets VALUES (17, 'Deimos', '2020-11-23', 1, 16);
INSERT INTO public.pets VALUES (18, 'Phobos', '2018-05-17', 1, 16);
INSERT INTO public.pets VALUES (21, 'Beelzebub', '2012-12-12', 5, 19);
INSERT INTO public.pets VALUES (22, 'Lucifer', '2006-06-06', 6, 19);
INSERT INTO public.pets VALUES (25, 'Jenkins', '2021-12-23', 1, 22);
INSERT INTO public.pets VALUES (28, 'Snuggles', '2019-11-23', 3, 25);
INSERT INTO public.pets VALUES (29, 'Кузя', '2015-11-23', 5, 26);
INSERT INTO public.pets VALUES (30, 'Picasso', '2014-11-23', 1, 27);
INSERT INTO public.pets VALUES (3, 'Leo', '2000-09-07', 1, 1);
INSERT INTO public.pets VALUES (35, 'Puck', '2015-11-23', 1, 28);
INSERT INTO public.pets VALUES (36, 'Тест', '2020-12-23', 5, 29);


--
-- Data for Name: specialties; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.specialties VALUES (1, 'radiology');
INSERT INTO public.specialties VALUES (2, 'surgery');
INSERT INTO public.specialties VALUES (3, 'dentistry');


--
-- Data for Name: vets; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.vets VALUES (1, 'James', 'Carter');
INSERT INTO public.vets VALUES (2, 'Helen', 'Leary');
INSERT INTO public.vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO public.vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO public.vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO public.vets VALUES (6, 'Sharon', 'Jenkins');


--
-- Data for Name: vet_specialties; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.vet_specialties VALUES (2, 1);
INSERT INTO public.vet_specialties VALUES (3, 2);
INSERT INTO public.vet_specialties VALUES (3, 3);
INSERT INTO public.vet_specialties VALUES (4, 2);
INSERT INTO public.vet_specialties VALUES (5, 1);


--
-- Data for Name: visits; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.visits VALUES (1, 7, '2010-03-04', 'rabies shot');
INSERT INTO public.visits VALUES (2, 8, '2011-03-04', 'rabies shot');
INSERT INTO public.visits VALUES (3, 8, '2009-06-04', 'neutered');
INSERT INTO public.visits VALUES (4, 7, '2008-09-04', 'spayed');
INSERT INTO public.visits VALUES (5, 3, '2020-11-21', 'tick removal');


--
-- Name: owners_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.owners_id_seq', 29, true);


--
-- Name: pets_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pets_id_seq', 36, true);


--
-- Name: specialties_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.specialties_id_seq', 3, true);


--
-- Name: types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.types_id_seq', 6, true);


--
-- Name: vets_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vets_id_seq', 6, true);


--
-- Name: visits_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visits_id_seq', 7, true);


--
-- PostgreSQL database dump complete
--

