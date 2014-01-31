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
-- Name: follower; Type: TABLE; Schema: public; Owner: db_user; Tablespace: 
--

CREATE TABLE follower (
    src_uid integer NOT NULL,
    tgt_uid integer NOT NULL
);


ALTER TABLE public.follower OWNER TO db_user;

--
-- Name: following; Type: TABLE; Schema: public; Owner: db_user; Tablespace: 
--

CREATE TABLE following (
    src_uid integer NOT NULL,
    tgt_uid integer NOT NULL
);


ALTER TABLE public.following OWNER TO db_user;

--
-- Name: profile; Type: TABLE; Schema: public; Owner: db_user; Tablespace: 
--

CREATE TABLE profile (
    id integer NOT NULL,
    handle character varying(50) NOT NULL,
    full_name character varying(255) NOT NULL,
    location character varying(255) NOT NULL,
    password character varying(255) NOT NULL
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
-- Data for Name: follower; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY follower (src_uid, tgt_uid) FROM stdin;
1	3
\.


--
-- Data for Name: following; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY following (src_uid, tgt_uid) FROM stdin;
3	1
\.


--
-- Data for Name: profile; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY profile (id, handle, full_name, location, password) FROM stdin;
1	test	Shishir Prasad	Madison	test
3	skp	Mohan	Madison	skp
\.


--
-- Data for Name: trend; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY trend (keyword, count) FROM stdin;
f283d990-1156-4d42-983b-115020be8abc	1
hello	2
13367f46-966b-46ec-b417-b46c30c8cae6	1
#test	3
world	2
97078c6e-a8ee-4236-9f23-71fcb4c028c6	1
test	11
from	11
class	11
tweet	11
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
-- Name: users_followed_idx; Type: INDEX; Schema: public; Owner: db_user; Tablespace: 
--

CREATE INDEX users_followed_idx ON follower USING btree (src_uid);


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
-- Name: user_fk4; Type: FK CONSTRAINT; Schema: public; Owner: db_user
--

ALTER TABLE ONLY follower
    ADD CONSTRAINT user_fk4 FOREIGN KEY (src_uid) REFERENCES profile(id) ON DELETE CASCADE;


--
-- Name: user_fk5; Type: FK CONSTRAINT; Schema: public; Owner: db_user
--

ALTER TABLE ONLY follower
    ADD CONSTRAINT user_fk5 FOREIGN KEY (tgt_uid) REFERENCES profile(id) ON DELETE CASCADE;


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

