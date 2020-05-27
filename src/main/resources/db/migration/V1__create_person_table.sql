CREATE TABLE IF NOT EXISTS person
(
    id         bigserial PRIMARY KEY,
    first_name text,
    surname    text,
    address    xml
);
