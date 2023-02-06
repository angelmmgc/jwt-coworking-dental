
#consulta para actualizar num_gabinetes de la tabla clinicas
#contando el numero de gabinetes que hay en la tabla gabinetes
#con el mismo numero de clinica_id.

UPDATE clinicas
SET num_gabinetes = (SELECT count(gabinetes.clinica_id)
FROM gabinetes
INNER JOIN clinicas ON clinicas.id = gabinetes.clinica_id)
WHERE clinicas.id = (SELECT gabinetes.clinica_id
FROM gabinetes
INNER JOIN clinicas ON clinicas.id = gabinetes.clinica_id group by gabinetes.clinica_id);



#consulta para insertar num_gabinetes de la tabla clinicas
#contando el numero de gabinetes que hay en la tabla gabinetes
#con el mismo numero de clinica_id.
insert into clinicas (num_gabinetes)
SELECT count(gabinetes.clinica_id)
FROM gabinetes
INNER JOIN clinicas ON clinicas.id = gabinetes.clinica_id
WHERE clinicas.id = (SELECT gabinetes.clinica_id
FROM gabinetes
INNER JOIN clinicas ON clinicas.id = gabinetes.clinica_id group by gabinetes.clinica_id);

#ejemplo uso de inner join
SELECT column_name(s)
FROM table1
INNER JOIN table2
ON table1.column_name = table2.column_name;


SELECT count(gabinetes.clinica_id)
FROM gabinetes
INNER JOIN clinicas ON clinicas.id = gabinetes.clinica_id;