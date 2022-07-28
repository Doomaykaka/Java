--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4
-- Dumped by pg_dump version 14.4

-- Started on 2022-07-28 04:24:48

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
-- TOC entry 211 (class 1259 OID 16391)
-- Name: Auths; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Auths" (
    id integer NOT NULL,
    login_password text NOT NULL
);


ALTER TABLE public."Auths" OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16388)
-- Name: Folders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Folders" (
    id integer NOT NULL,
    name text NOT NULL,
    parent_folder text,
    author_id integer
);


ALTER TABLE public."Folders" OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16385)
-- Name: Notes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Notes" (
    id integer NOT NULL,
    parent_folder text NOT NULL,
    name text NOT NULL,
    text text NOT NULL,
    author text NOT NULL,
    creation_date text NOT NULL,
    author_id integer,
    status text
);


ALTER TABLE public."Notes" OWNER TO postgres;

--
-- TOC entry 3318 (class 0 OID 16391)
-- Dependencies: 211
-- Data for Name: Auths; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Auths" (id, login_password) FROM stdin;
0	admin 1234
1	test 1234
\.


--
-- TOC entry 3317 (class 0 OID 16388)
-- Dependencies: 210
-- Data for Name: Folders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Folders" (id, name, parent_folder, author_id) FROM stdin;
0	ROOT	\N	0
1	TEST	ROOT	0
2	ROOT	\N	1
3	FOO	\N	0
4	BOO	\N	0
5	TEN	BOO	0
\.


--
-- TOC entry 3316 (class 0 OID 16385)
-- Dependencies: 209
-- Data for Name: Notes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Notes" (id, parent_folder, name, text, author, creation_date, author_id, status) FROM stdin;
2	FOO	NT2	HEWO	IV	2022-07-28T11:11:42.107623800Z	0	notCompleted
\.


--
-- TOC entry 3176 (class 2606 OID 16397)
-- Name: Auths Auths_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Auths"
    ADD CONSTRAINT "Auths_pkey" PRIMARY KEY (id);


--
-- TOC entry 3174 (class 2606 OID 16405)
-- Name: Folders Folders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Folders"
    ADD CONSTRAINT "Folders_pkey" PRIMARY KEY (id);


--
-- TOC entry 3172 (class 2606 OID 16401)
-- Name: Notes Notes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Notes"
    ADD CONSTRAINT "Notes_pkey" PRIMARY KEY (id);


-- Completed on 2022-07-28 04:24:49

--
-- PostgreSQL database dump complete
--

