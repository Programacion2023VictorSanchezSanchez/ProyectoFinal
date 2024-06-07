# Proyecto Final

### ¿Donde encontrar los componentes del proyecto?
Principalmente, en la carpeta de resources, habrán varios directorios...

dbfiles: Esquema E/R de la base de datos, script para generar la base de datos.

diagrams: diagramas tanto de clase como de casos de uso

### ¿Como debería de funcionar el programa?
Por como esta hecho y estructurada la base de datos, si intentamos crear, por ejemplo, un prestamo nuevo solo nos dejará insertar en los campos que son foreign keys de otras tablas, entidades que ya existan.

Con lo cual si quieres añadir un prestamo que va a ser asignado a un socio nuevo, primero debes irte a socios, crear el socio, volver a prestamos, crear el prestamo etc...

Lo correcto quizá habría sido que si detectase que ese socio (o campo asignado de otra tabla) no existiese, te lanzase una opción de crearlo, cosa que se me complico por como había planteado el proyecto...

Habría sido mejor que la forma de añadir, borrar, modificar en las tablas hubiesen sido pantallas individuales externas que pudiese llamar desde cualquier otra pantalla principal, pudiendo así arreglar este "problema"

Realmente funciona bien pero es tedioso tener que estar cambiando de pantallas y añadiendo de manera individual los campos que necesitas, si necesitas como en el ejemplo que dije, crear un prestamo con un socio que no existe, un ejemplar que tampoco existe etc..
