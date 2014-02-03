--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: following; Type: TABLE; Schema: public; Owner: db_user; Tablespace: 
--

CREATE TABLE following (
    src_uid integer NOT NULL,
    tgt_uid integer NOT NULL
);


ALTER TABLE public.following OWNER TO db_user;

--
-- Name: follower; Type: VIEW; Schema: public; Owner: db_user
--

CREATE VIEW follower AS
    SELECT following.tgt_uid AS src_uid, following.src_uid AS tgt_uid FROM following;


ALTER TABLE public.follower OWNER TO db_user;

--
-- Name: profile; Type: TABLE; Schema: public; Owner: db_user; Tablespace: 
--

CREATE TABLE profile (
    id integer NOT NULL,
    handle character varying(50) NOT NULL,
    full_name character varying(255) NOT NULL,
    location character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    email character varying(255)
);


ALTER TABLE public.profile OWNER TO db_user;

--
-- Name: trend; Type: TABLE; Schema: public; Owner: db_user; Tablespace: 
--

CREATE TABLE trend (
    keyword character varying(255),
    count integer
);


ALTER TABLE public.trend OWNER TO db_user;

--
-- Name: tweet; Type: TABLE; Schema: public; Owner: db_user; Tablespace: 
--

CREATE TABLE tweet (
    id integer NOT NULL,
    content character varying(255) NOT NULL,
    create_time timestamp without time zone NOT NULL
);


ALTER TABLE public.tweet OWNER TO db_user;

--
-- Name: user_to_tweet; Type: TABLE; Schema: public; Owner: db_user; Tablespace: 
--

CREATE TABLE user_to_tweet (
    uid integer NOT NULL,
    tid integer NOT NULL
);


ALTER TABLE public.user_to_tweet OWNER TO db_user;

--
-- Data for Name: following; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY following (src_uid, tgt_uid) FROM stdin;
3	1
1	3
7	6
1	6
1	7
7	1
7	6
\.


--
-- Data for Name: profile; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY profile (id, handle, full_name, location, password, email) FROM stdin;
1	test	Shishir Prasad	Madison	test	test@gmail.com
3	skp	Mohan	Madison	skp	skp@gmail.com
4	tHandle	tName	tLocation	tPass	tEmail
5	test	test	test	Madison	test@ds
6	shout	shout	shout	Madison	shout@ds
7	tHandle	tName	tEmail	tPass	tLocation
8	6ac5d61a-320b-4962-9019-137c8ba79203	tName	tEmail	tPass	tLocation
9	911c82ce-83ba-4b7a-8cd1-3798b7645a12	tName	tEmail	tPass	tLocation
10	5517ea1f-d28b-4d57-b40d-034d13f71ccb	tName	tEmail	tPass	tLocation
11	b0e2e669-4878-4790-9571-d8d40ea72a99	tName	tEmail	tPass	tLocation
\.


--
-- Data for Name: trend; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY trend (keyword, count) FROM stdin;
ada10ee7-e481-4bd7-83b9-6eb415952419	1
b2027c26-33d8-4844-a7fd-e0a098936954	1
order	1
7ad2f419-105d-4e5a-b6e5-b83a5ef820d8	1
test	9
ce5869dd-a134-47a6-bbf6-2819fa3a469c	1
tweet	9
b74be388-8b08-4305-b487-7b59b2551700	1
class	9
from	9
\.


--
-- Data for Name: tweet; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY tweet (id, content, create_time) FROM stdin;
1	hello world #test	2014-01-30 09:11:21.781771
2	hello world again #test	2014-01-30 09:11:40.657855
3	shout out loud .. #test	2014-01-30 09:11:49.379036
4	tweet from test class ..	2014-01-30 13:02:45.076379
5	tweet from test class 6efd886d-fcad-4ded-a8e9-4670c52a167d	2014-01-30 14:48:19.660757
6	tweet from test class 0544b6ae-a884-43fc-b15e-2aa0f72a384f	2014-01-30 14:56:52.079621
7	tweet from test class fde0ed39-612b-4f50-913e-8dbf68f1bbdc	2014-01-30 15:05:26.554975
8	tweet from test class 8edc7043-b64e-4f36-a2a0-eaaf2150c385	2014-01-30 15:09:53.068273
9	tweet from test class f283d990-1156-4d42-983b-115020be8abc	2014-01-30 15:13:37.491079
10	tweet from test class ed99f00c-1780-470c-8b4b-a0e05ef6463b	2014-01-30 17:18:31.191366
11	tweet from test class 935c58a7-134a-4306-a265-2eb91fe7b0da	2014-01-30 17:21:14.313129
12	tweet from test class 254a9659-6a4e-45c1-92fd-15af754aac80	2014-01-30 17:23:17.0586
13	tweet from test class 13367f46-966b-46ec-b417-b46c30c8cae6	2014-01-30 17:26:16.610851
14	tweet from test class 97078c6e-a8ee-4236-9f23-71fcb4c028c6	2014-01-30 17:27:06.760846
15	tweet from test class 2ff3836f-8164-4ff0-a5d9-635acbe55171	2014-02-02 13:15:55.392655
16	tweet from test class 5226dddb-42f6-4785-a2fe-d571551582e5	2014-02-02 13:17:31.580497
17	tweet from test class b74be388-8b08-4305-b487-7b59b2551700	2014-02-02 13:18:47.766819
18	tweet from test class 7ad2f419-105d-4e5a-b6e5-b83a5ef820d8	2014-02-02 13:20:35.799334
19	a new world order #uw	2014-02-02 18:16:37.810429
20	tweet from test class ce5869dd-a134-47a6-bbf6-2819fa3a469c	2014-02-02 18:26:29.631323
21	tweet from test class bce62ba0-0ff5-4d18-b7d4-0cd32a5d3e1d	2014-02-02 18:29:23.118998
22	tweet from test class ada10ee7-e481-4bd7-83b9-6eb415952419	2014-02-02 18:30:20.992571
23	tweet from test class b2027c26-33d8-4844-a7fd-e0a098936954	2014-02-02 18:48:33.398925
24	tweet from test class 1235e298-3dd5-4c42-b3e7-643179f1c9f0	2014-02-02 19:02:16.28988
\.


--
-- Data for Name: user_to_tweet; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY user_to_tweet (uid, tid) FROM stdin;
1	1
1	2
1	3
1	4
1	5
1	6
1	7
1	8
1	9
1	10
1	11
1	12
1	13
1	14
1	15
1	16
1	17
1	18
3	19
5	20
5	21
5	22
5	23
5	24
\.


--
-- Name: time_idx; Type: INDEX; Schema: public; Owner: db_user; Tablespace: 
--

CREATE INDEX time_idx ON tweet USING btree (create_time);


--
-- Name: tweet_idx; Type: INDEX; Schema: public; Owner: db_user; Tablespace: 
--

CREATE UNIQUE INDEX tweet_idx ON tweet USING btree (id);


--
-- Name: user_idx; Type: INDEX; Schema: public; Owner: db_user; Tablespace: 
--

CREATE UNIQUE INDEX user_idx ON profile USING btree (id);


--
-- Name: user_idx1; Type: INDEX; Schema: public; Owner: db_user; Tablespace: 
--

CREATE INDEX user_idx1 ON user_to_tweet USING btree (uid);


--
-- Name: users_following_idx; Type: INDEX; Schema: public; Owner: db_user; Tablespace: 
--

CREATE INDEX users_following_idx ON following USING btree (src_uid);


--
-- Name: tweet_fk1; Type: FK CONSTRAINT; Schema: public; Owner: db_user
--

ALTER TABLE ONLY user_to_tweet
    ADD CONSTRAINT tweet_fk1 FOREIGN KEY (tid) REFERENCES tweet(id) ON DELETE CASCADE;


--
-- Name: user_fk1; Type: FK CONSTRAINT; Schema: public; Owner: db_user
--

ALTER TABLE ONLY user_to_tweet
    ADD CONSTRAINT user_fk1 FOREIGN KEY (uid) REFERENCES profile(id) ON DELETE CASCADE;


--
-- Name: user_fk2; Type: FK CONSTRAINT; Schema: public; Owner: db_user
--

ALTER TABLE ONLY following
    ADD CONSTRAINT user_fk2 FOREIGN KEY (src_uid) REFERENCES profile(id) ON DELETE CASCADE;


--
-- Name: user_fk3; Type: FK CONSTRAINT; Schema: public; Owner: db_user
--

ALTER TABLE ONLY following
    ADD CONSTRAINT user_fk3 FOREIGN KEY (tgt_uid) REFERENCES profile(id) ON DELETE CASCADE;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

