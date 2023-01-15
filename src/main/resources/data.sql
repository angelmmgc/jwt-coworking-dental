
--CREATE TABLE IF NOT EXISTS public.usuarios
--(
--    id bigint NOT NULL DEFAULT nextval('usuarios_id_seq'::regclass),
--    apellidos character varying(255) COLLATE pg_catalog."default" NOT NULL,
--    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
--    nombre character varying(255) COLLATE pg_catalog."default" NOT NULL,
--    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
--    telefono character varying(255) COLLATE pg_catalog."default" NOT NULL,
--    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
--    CONSTRAINT usuarios_pkey PRIMARY KEY (id)
--)
--
--TABLESPACE pg_default;
--
--ALTER TABLE IF EXISTS public.usuarios
--    OWNER to postgres;

-- Table: public.administra


--ROLES
INSERT INTO rol (rol_nombre) VALUES ('ROLE_USER');
INSERT INTO rol (rol_nombre) VALUES ('ROLE_ADMIN');


--USUARIOS Y ROLES
INSERT INTO usuarios (username,nombre,apellidos,email,telefono,password) values ('texas','angel','martin','angel@gmail.com','11111111','$2a$10$BfkpmlJzfQty8N9S5uq/BeQFcaK86fJgDv9AhptEhspjQPjAm42Ji');
INSERT INTO user_roles (user_id,role_id) VALUES (1,1);

--  "nombre":"angel",
--    "apellidos":"martin",
--    "email":"angel@gmail.com",
--    "telefono":"666454545",
--    "nombreUsuario":"texas",
--    "password":"1111"
----
INSERT INTO usuarios (username,nombre,apellidos, email,telefono,password) values ('josecrack','jose','toledo', 'jose@gmail.com','666454545','$2a$10$YOMu4NnvPpcukn/LkNh8Euj6S4c0IyOS4YfPQ/MxT3TcaDINWF7cG');
INSERT INTO user_roles (user_id,role_id) VALUES (2,1);

--  "nombre":"jose",
--    "apellidos":"toledo",
--    "email":"jose@gmail.com",
--    "telefono":"666454545",
--    "nombreUsuario":"josecrack",
--    "password":"0000"

INSERT INTO usuarios (username,nombre,apellidos,email,telefono,password) values ('admin','administrador','principal', 'admin@admin.edu','111111111','$2a$10$3eUsqxugHY75yB5FjZTU.etPeBK0Fw/GeIZOIBnj9cOp0QEkax3.O');
INSERT INTO user_roles (user_id,role_id) VALUES (3,2);

--  "nombre":"administrador",
--    "apellidos":"admin principal",
--    "email":"admin@admin.edu",
--    "telefono":"666454545",
--    "nombreUsuario":"admin",
--    "password":"3333"


--CLINICAS
INSERT INTO clinicas (nombre,email,telefono,direccion,num_gabinetes) VALUES ('clinica1','clinica1@clinica.com','99999999','heroe de sostoa',2);
INSERT INTO clinicas (nombre,email,telefono,direccion,num_gabinetes) VALUES ('clinica2','clinica2@clinica.com','111111111','eduardo de palacios',1);

--ADMINISTRA
INSERT INTO ADMINISTRA (clinica_id, user_id) VALUES (1,3);
--

