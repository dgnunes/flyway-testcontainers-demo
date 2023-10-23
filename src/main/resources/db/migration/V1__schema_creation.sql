CREATE TABLE IF NOT EXISTS author (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid (),
  name VARCHAR(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS book (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid (),
  author_id uuid NOT NULL,
  name VARCHAR(256) NOT NULL,
  CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES author(id)
);

