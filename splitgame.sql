--
-- PostgreSQL database dump
--

\restrict xdbdU9gFocOMxt5h2sCqY2tHo20ZbxbOYfbxFj5fPiVm2RkCwE7DdgU2eQPXvKj

-- Dumped from database version 18.0
-- Dumped by pg_dump version 18.0

-- Started on 2025-10-14 17:34:35

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 4954 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 227 (class 1255 OID 16793)
-- Name: registrar_juego(integer, integer[]); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.registrar_juego(p_ganador_id integer, p_participantes integer[]) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    v_juego_id INTEGER;
    v_persona_id INTEGER;
BEGIN
    -- Validar que el ganador está en la lista de participantes
    IF NOT (p_ganador_id = ANY(p_participantes)) THEN
        RAISE EXCEPTION 'El ganador debe estar en la lista de participantes';
    END IF;
    
    -- Insertar el juego
    INSERT INTO historial_juegos (ganador_id, total_participantes)
    VALUES (p_ganador_id, array_length(p_participantes, 1))
    RETURNING id INTO v_juego_id;
    
    -- Insertar los participantes
    FOREACH v_persona_id IN ARRAY p_participantes
    LOOP
        INSERT INTO participantes_juego (juego_id, persona_id)
        VALUES (v_juego_id, v_persona_id);
    END LOOP;
    
    RETURN v_juego_id;
END;
$$;


ALTER FUNCTION public.registrar_juego(p_ganador_id integer, p_participantes integer[]) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 222 (class 1259 OID 16742)
-- Name: historial_juegos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.historial_juegos (
    id integer NOT NULL,
    ganador_id integer NOT NULL,
    fecha_juego timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    total_participantes integer NOT NULL
);


ALTER TABLE public.historial_juegos OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16741)
-- Name: historial_juegos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.historial_juegos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.historial_juegos_id_seq OWNER TO postgres;

--
-- TOC entry 4955 (class 0 OID 0)
-- Dependencies: 221
-- Name: historial_juegos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.historial_juegos_id_seq OWNED BY public.historial_juegos.id;


--
-- TOC entry 224 (class 1259 OID 16758)
-- Name: participantes_juego; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.participantes_juego (
    id integer NOT NULL,
    juego_id integer NOT NULL,
    persona_id integer NOT NULL
);


ALTER TABLE public.participantes_juego OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16757)
-- Name: participantes_juego_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.participantes_juego_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.participantes_juego_id_seq OWNER TO postgres;

--
-- TOC entry 4956 (class 0 OID 0)
-- Dependencies: 223
-- Name: participantes_juego_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.participantes_juego_id_seq OWNED BY public.participantes_juego.id;


--
-- TOC entry 220 (class 1259 OID 16732)
-- Name: personas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.personas (
    id integer NOT NULL,
    nombre character varying(100) NOT NULL,
    fecha_creacion timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.personas OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16731)
-- Name: personas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.personas_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.personas_id_seq OWNER TO postgres;

--
-- TOC entry 4957 (class 0 OID 0)
-- Dependencies: 219
-- Name: personas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.personas_id_seq OWNED BY public.personas.id;


--
-- TOC entry 225 (class 1259 OID 16783)
-- Name: vista_estadisticas_personas; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.vista_estadisticas_personas AS
 SELECT p.id,
    p.nombre,
    count(DISTINCT pj.juego_id) AS juegos_participados,
    count(DISTINCT h.id) AS veces_perdio,
    COALESCE(round((((count(DISTINCT h.id))::numeric / (NULLIF(count(DISTINCT pj.juego_id), 0))::numeric) * (100)::numeric), 2), (0)::numeric) AS porcentaje_perdidas
   FROM ((public.personas p
     LEFT JOIN public.participantes_juego pj ON ((p.id = pj.persona_id)))
     LEFT JOIN public.historial_juegos h ON ((p.id = h.ganador_id)))
  GROUP BY p.id, p.nombre
  ORDER BY (count(DISTINCT h.id)) DESC;


ALTER VIEW public.vista_estadisticas_personas OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16788)
-- Name: vista_ultimos_juegos; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.vista_ultimos_juegos AS
 SELECT h.id AS juego_id,
    h.fecha_juego,
    p.nombre AS perdedor,
    h.total_participantes,
    string_agg((p2.nombre)::text, ', '::text ORDER BY (p2.nombre)::text) AS participantes
   FROM (((public.historial_juegos h
     JOIN public.personas p ON ((h.ganador_id = p.id)))
     JOIN public.participantes_juego pj ON ((h.id = pj.juego_id)))
     JOIN public.personas p2 ON ((pj.persona_id = p2.id)))
  GROUP BY h.id, h.fecha_juego, p.nombre, h.total_participantes
  ORDER BY h.fecha_juego DESC;


ALTER VIEW public.vista_ultimos_juegos OWNER TO postgres;

--
-- TOC entry 4776 (class 2604 OID 16745)
-- Name: historial_juegos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historial_juegos ALTER COLUMN id SET DEFAULT nextval('public.historial_juegos_id_seq'::regclass);


--
-- TOC entry 4778 (class 2604 OID 16761)
-- Name: participantes_juego id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participantes_juego ALTER COLUMN id SET DEFAULT nextval('public.participantes_juego_id_seq'::regclass);


--
-- TOC entry 4774 (class 2604 OID 16735)
-- Name: personas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas ALTER COLUMN id SET DEFAULT nextval('public.personas_id_seq'::regclass);


--
-- TOC entry 4946 (class 0 OID 16742)
-- Dependencies: 222
-- Data for Name: historial_juegos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.historial_juegos (id, ganador_id, fecha_juego, total_participantes) FROM stdin;
1	1	2025-10-13 23:04:11.535338	4
2	3	2025-10-13 23:04:11.535338	5
3	2	2025-10-13 23:04:11.535338	3
4	1	2025-10-14 00:04:37.572295	4
5	1	2025-10-14 00:05:38.71891	4
6	3	2025-10-14 00:05:53.189823	5
7	3	2025-10-14 16:53:29.708518	4
8	2	2025-10-14 16:53:37.650849	4
9	2	2025-10-14 16:53:38.705251	4
10	1	2025-10-14 16:53:39.689782	4
11	2	2025-10-14 17:29:37.263196	2
12	8	2025-10-14 17:30:00.621132	6
\.


--
-- TOC entry 4948 (class 0 OID 16758)
-- Dependencies: 224
-- Data for Name: participantes_juego; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.participantes_juego (id, juego_id, persona_id) FROM stdin;
1	1	1
2	1	2
3	1	3
4	1	4
5	2	1
6	2	2
7	2	3
8	2	4
9	2	5
10	3	1
11	3	2
12	3	5
13	4	1
14	4	2
15	4	3
16	4	4
17	5	1
18	5	2
19	5	3
20	5	4
21	6	1
22	6	2
23	6	3
24	6	4
25	6	5
26	7	1
27	7	2
28	7	3
29	7	4
30	8	1
31	8	2
32	8	3
33	8	4
34	9	1
35	9	2
36	9	3
37	9	4
38	10	1
39	10	2
40	10	3
41	10	4
42	11	1
43	11	2
44	12	4
45	12	1
46	12	7
47	12	8
48	12	9
49	12	6
\.


--
-- TOC entry 4944 (class 0 OID 16732)
-- Dependencies: 220
-- Data for Name: personas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.personas (id, nombre, fecha_creacion) FROM stdin;
1	Juan Pérez	2025-10-13 23:03:57.068961
2	María García	2025-10-13 23:03:57.068961
3	Pedro López	2025-10-13 23:03:57.068961
4	Ana Martínez	2025-10-13 23:03:57.068961
5	Luis Rodríguez	2025-10-13 23:03:57.068961
6	Juan Pérez	2025-10-14 00:01:31.143346
7	María García	2025-10-14 00:01:55.12853
8	Pedro López	2025-10-14 00:01:59.681189
9	Dean Reyes	2025-10-14 17:29:53.813626
\.


--
-- TOC entry 4958 (class 0 OID 0)
-- Dependencies: 221
-- Name: historial_juegos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.historial_juegos_id_seq', 12, true);


--
-- TOC entry 4959 (class 0 OID 0)
-- Dependencies: 223
-- Name: participantes_juego_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.participantes_juego_id_seq', 49, true);


--
-- TOC entry 4960 (class 0 OID 0)
-- Dependencies: 219
-- Name: personas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.personas_id_seq', 9, true);


--
-- TOC entry 4782 (class 2606 OID 16751)
-- Name: historial_juegos historial_juegos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historial_juegos
    ADD CONSTRAINT historial_juegos_pkey PRIMARY KEY (id);


--
-- TOC entry 4788 (class 2606 OID 16768)
-- Name: participantes_juego participantes_juego_juego_id_persona_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participantes_juego
    ADD CONSTRAINT participantes_juego_juego_id_persona_id_key UNIQUE (juego_id, persona_id);


--
-- TOC entry 4790 (class 2606 OID 16766)
-- Name: participantes_juego participantes_juego_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participantes_juego
    ADD CONSTRAINT participantes_juego_pkey PRIMARY KEY (id);


--
-- TOC entry 4780 (class 2606 OID 16740)
-- Name: personas personas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas
    ADD CONSTRAINT personas_pkey PRIMARY KEY (id);


--
-- TOC entry 4783 (class 1259 OID 16779)
-- Name: idx_historial_fecha; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_historial_fecha ON public.historial_juegos USING btree (fecha_juego DESC);


--
-- TOC entry 4784 (class 1259 OID 16780)
-- Name: idx_historial_ganador; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_historial_ganador ON public.historial_juegos USING btree (ganador_id);


--
-- TOC entry 4785 (class 1259 OID 16781)
-- Name: idx_participantes_juego; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_participantes_juego ON public.participantes_juego USING btree (juego_id);


--
-- TOC entry 4786 (class 1259 OID 16782)
-- Name: idx_participantes_persona; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_participantes_persona ON public.participantes_juego USING btree (persona_id);


--
-- TOC entry 4791 (class 2606 OID 16752)
-- Name: historial_juegos historial_juegos_ganador_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historial_juegos
    ADD CONSTRAINT historial_juegos_ganador_id_fkey FOREIGN KEY (ganador_id) REFERENCES public.personas(id) ON DELETE CASCADE;


--
-- TOC entry 4792 (class 2606 OID 16769)
-- Name: participantes_juego participantes_juego_juego_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participantes_juego
    ADD CONSTRAINT participantes_juego_juego_id_fkey FOREIGN KEY (juego_id) REFERENCES public.historial_juegos(id) ON DELETE CASCADE;


--
-- TOC entry 4793 (class 2606 OID 16774)
-- Name: participantes_juego participantes_juego_persona_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participantes_juego
    ADD CONSTRAINT participantes_juego_persona_id_fkey FOREIGN KEY (persona_id) REFERENCES public.personas(id) ON DELETE CASCADE;


-- Completed on 2025-10-14 17:34:36

--
-- PostgreSQL database dump complete
--

\unrestrict xdbdU9gFocOMxt5h2sCqY2tHo20ZbxbOYfbxFj5fPiVm2RkCwE7DdgU2eQPXvKj

