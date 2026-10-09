# Telecom - API REST de Ordenes de Servicio (Spring Boot + Oracle + JWT)

1. Levantar Oracle:   docker compose up -d   (esperar a "healthy" con docker ps)
2. Ejecutar db/schema.sql como app_telecom en FREEPDB1
   (si ya tenias las 2 primeras tablas, ejecutar solo db/historial_orden.sql)
3. Ejecutar la app:   mvn spring-boot:run   (o correr TelecomCrudApplication)
4. Probar:            ver pruebas/pruebas.http

Endpoints:
  POST  /api/token                    (publico) genera el JWT
  POST  /api/ordenes                  crear orden
  GET   /api/ordenes                  listar ordenes
  GET   /api/ordenes/{id}             detalle de una orden
  PATCH /api/ordenes/{id}/estado      cambiar estado (valida transiciones)
  GET   /api/ordenes/{id}/historial   historial de transiciones

Usuario de pruebas: admin / Admin123 (application.properties)
