-- Created by Andryev Lemes
CREATE USER postgresuser SUPERUSER PASSWORD 'postgrespass';
CREATE DATABASE "letscode_db"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.utf8'
       LC_CTYPE = 'en_US.utf8'
       CONNECTION LIMIT = -1;
GRANT ALL PRIVILEGES ON DATABASE letscode_db TO postgresuser;