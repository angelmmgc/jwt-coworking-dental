
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

--CREANDO DATABASE
--CREATE TABLE IF NOT EXISTS public.user_roles
--(
--user_id bigint NOT NULL,
--role_id bigint NOT NULL,
--CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
--CONSTRAINT fk2chxp26bnpqjibydrikgq4t9e FOREIGN KEY (user_id)
--REFERENCES public.usuarios (id) MATCH SIMPLE
--ON UPDATE NO ACTION
--ON DELETE CASCADE,
--CONSTRAINT fk9lasceq82f1y6pltnys5ovctw FOREIGN KEY (role_id)
--REFERENCES public.rol (id) MATCH SIMPLE
--ON UPDATE NO ACTION
--ON DELETE NO ACTION
--)

--
--CREATE TABLE IF NOT EXISTS public.user_roles
--(
--    user_roles bigint NOT NULL,
--    usuario_id bigint NOT NULL,
--    CONSTRAINT pk_cb_pedido PRIMARY KEY (user_roles,usuario_id),
--    CONSTRAINT u_cb_usuario_id FOREIGN KEY (usuario_id)
--    CONSTRAINT u_cb_idusuario FOREIGN KEY (user_roles)
--    REFERENCES public.roles (id) MATCH SIMPLE
--        ON UPDATE NO ACTION
--        ON DELETE CASCADE
--    CONSTRAINT u_cb_usuario_id FOREIGN KEY (usuario_id)
--    REFERENCES public.usuarios (id) MATCH SIMPLE
--        ON UPDATE CASCADE
--        ON DELETE CASCADE
--)
--
--CREATE TABLE IF NOT EXISTS public.administra
--(
--    clinica_id bigint NOT NULL,
--    usuario_id bigint NOT NULL,
--    CONSTRAINT pk_cb_pedido PRIMARY KEY (usuario_id,clinica_id),
--    CONSTRAINT u_cb_usuario_id FOREIGN KEY (usuario_id)
--        REFERENCES public.usuarios (id) MATCH SIMPLE
--        ON UPDATE CASCADE
--        ON DELETE CASCADE
--
--   CONSTRAINT u_cb_clinica_id FOREIGN KEY (clinica_id)
--        REFERENCES public.clinica (id) MATCH SIMPLE
--        ON UPDATE CASCADE
--        ON DELETE CASCADE
--)

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

