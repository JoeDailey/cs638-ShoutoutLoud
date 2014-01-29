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
-- Name: profile_id_seq; Type: SEQUENCE; Schema: public; Owner: db_user
--

CREATE SEQUENCE profile_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.profile_id_seq OWNER TO db_user;

--
-- Name: profile_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: db_user
--

ALTER SEQUENCE profile_id_seq OWNED BY profile.id;


--
-- Name: trend; Type: TABLE; Schema: public; Owner: db_user; Tablespace: 
--

CREATE TABLE trend (
    id integer NOT NULL,
    keyword character varying(50) NOT NULL,
    is_hashtag boolean NOT NULL
);


ALTER TABLE public.trend OWNER TO db_user;

--
-- Name: trend_id_seq; Type: SEQUENCE; Schema: public; Owner: db_user
--

CREATE SEQUENCE trend_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.trend_id_seq OWNER TO db_user;

--
-- Name: trend_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: db_user
--

ALTER SEQUENCE trend_id_seq OWNED BY trend.id;


--
-- Name: trend_to_tweet; Type: TABLE; Schema: public; Owner: db_user; Tablespace: 
--

CREATE TABLE trend_to_tweet (
    tid integer NOT NULL,
    tweet_id integer NOT NULL
);


ALTER TABLE public.trend_to_tweet OWNER TO db_user;

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
-- Name: tweet_id_seq; Type: SEQUENCE; Schema: public; Owner: db_user
--

CREATE SEQUENCE tweet_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tweet_id_seq OWNER TO db_user;

--
-- Name: tweet_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: db_user
--

ALTER SEQUENCE tweet_id_seq OWNED BY tweet.id;


--
-- Name: user_to_tweet; Type: TABLE; Schema: public; Owner: db_user; Tablespace: 
--

CREATE TABLE user_to_tweet (
    uid integer NOT NULL,
    tid integer NOT NULL
);


ALTER TABLE public.user_to_tweet OWNER TO db_user;

--
-- Name: id; Type: DEFAULT; Schema: public; Owner: db_user
--

ALTER TABLE ONLY profile ALTER COLUMN id SET DEFAULT nextval('profile_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: db_user
--

ALTER TABLE ONLY trend ALTER COLUMN id SET DEFAULT nextval('trend_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: db_user
--

ALTER TABLE ONLY tweet ALTER COLUMN id SET DEFAULT nextval('tweet_id_seq'::regclass);


--
-- Data for Name: follower; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY follower (src_uid, tgt_uid) FROM stdin;
\.


--
-- Data for Name: following; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY following (src_uid, tgt_uid) FROM stdin;
\.


--
-- Data for Name: profile; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY profile (id, handle, full_name, location, password) FROM stdin;
\.


--
-- Name: profile_id_seq; Type: SEQUENCE SET; Schema: public; Owner: db_user
--

SELECT pg_catalog.setval('profile_id_seq', 1, false);


--
-- Data for Name: trend; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY trend (id, keyword, is_hashtag) FROM stdin;
\.


--
-- Name: trend_id_seq; Type: SEQUENCE SET; Schema: public; Owner: db_user
--

SELECT pg_catalog.setval('trend_id_seq', 1, false);


--
-- Data for Name: trend_to_tweet; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY trend_to_tweet (tid, tweet_id) FROM stdin;
\.


--
-- Data for Name: tweet; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY tweet (id, content, create_time) FROM stdin;
\.


--
-- Name: tweet_id_seq; Type: SEQUENCE SET; Schema: public; Owner: db_user
--

SELECT pg_catalog.setval('tweet_id_seq', 1, false);


--
-- Data for Name: user_to_tweet; Type: TABLE DATA; Schema: public; Owner: db_user
--

COPY user_to_tweet (uid, tid) FROM stdin;
\.


--
-- Name: time_idx; Type: INDEX; Schema: public; Owner: db_user; Tablespace: 
--

CREATE INDEX time_idx ON tweet USING btree (create_time);


--
-- Name: trend_idx; Type: INDEX; Schema: public; Owner: db_user; Tablespace: 
--

CREATE UNIQUE INDEX trend_idx ON trend USING btree (id);


--
-- Name: tweet_idx; Type: INDEX; Schema: public; Owner: db_user; Tablespace: 
--

CREATE UNIQUE INDEX tweet_idx ON tweet USING btree (id);


--
-- Name: tweets_of_trend_idx; Type: INDEX; Schema: public; Owner: db_user; Tablespace: 
--

CREATE INDEX tweets_of_trend_idx ON trend_to_tweet USING btree (tid);


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
-- Name: trend_fk1; Type: FK CONSTRAINT; Schema: public; Owner: db_user
--

ALTER TABLE ONLY trend_to_tweet
    ADD CONSTRAINT trend_fk1 FOREIGN KEY (tid) REFERENCES trend(id) ON DELETE CASCADE;


--
-- Name: tweet_fk1; Type: FK CONSTRAINT; Schema: public; Owner: db_user
--

ALTER TABLE ONLY user_to_tweet
    ADD CONSTRAINT tweet_fk1 FOREIGN KEY (tid) REFERENCES tweet(id) ON DELETE CASCADE;


--
-- Name: tweet_fk2; Type: FK CONSTRAINT; Schema: public; Owner: db_user
--

ALTER TABLE ONLY trend_to_tweet
    ADD CONSTRAINT tweet_fk2 FOREIGN KEY (tweet_id) REFERENCES tweet(id) ON DELETE CASCADE;


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

