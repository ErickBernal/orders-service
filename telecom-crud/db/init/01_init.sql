-- Se ejecuta como SYS al crear la BD por primera vez.
-- Con CURRENT_SCHEMA las tablas se crean dentro de app_telecom.
ALTER SESSION SET CURRENT_SCHEMA = app_telecom;
@/schema/schema.sql