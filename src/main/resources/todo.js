
/*
TODO 1: lo que hay que hacer es un api para cada parte del crud de cada una de las siguientes modulos 
de que manera funcionarian estos crud?

para el trabajo de la api se usara el framework de spring boot
para la api se debe pedir una llave para cada acceso, esto se hara con un token, el token se pedira en cada peticion.
se debe generar un token general, que se usara al momento de crar el organigrama, este token se usara para crear los demas tokens

    - organigrama api
        -- el organigrama es un arbol de usuarios con sus respectivos roles y nivel/ no se va a crear usuarios 
            --- el nivel es un numero que indica la profundidad del arbol
            --- el rol es un numero que indica el rol del usuario
            --- el organigrama es un arbol de usuarios
            --- el usuario puede tener un solo jefe
            --- el usuario puede tener muchos subordinados
            --- el usuario puede tener muchos roles
            --- el usuario puede tener muchos niveles
            --- el usuario puede tener muchos permisos
            --- el usuario puede tener muchos documentos
            --- el usuario puede tener muchos historiales
            --- el usuario puede tener muchos documentos compartidos
            --- el usuario puede tener muchos historiales compartidos
            --- el usuario puede tener muchos documentos recibidos
            --- el usuario puede tener muchos historiales recibidos
            --- el usuario puede tener muchos documentos enviados
            --- el usuario puede tener muchos historiales enviados
            --- el usuario puede tener muchos documentos eliminados
            --- el usuario puede tener muchos historiales eliminados
        c: crear (usuario ya debe existir)
            - crear organigrama
            - crear rol
            - crear nivel
            - crear permiso
            - debe existir el usuario
            - debe ser enviado por un xml o json
        r: leer
            - leer organigrama
            - leer rol
            - leer nivel
            - leer permiso
            - se enviaran todos los usuarios? o uno a uno?
            - debe ser enviado por un xml o json
        u: actualizar
            - actualizar organigrama 
            - con modificar el json se cambiara todo?
            - json o xml
        d: eliminar
            - dos tipos de eliminar
            - el fisico que elimina todo
            - el logico que lo marca como eliminado
            - json o xml
    - parametros - parametros api
        -- aqui iran los datos de la bd a conectar
        -- los parametros son los datos que se usaran en los documentos
        -- 
    - documentos - plantillas api
        -- en las plantillas se generaran a partir de parametros obtenidos, se generaran documentos
        -- estas plantillas se deben poder usar en documentos api
        -- todo es parametro
        -- se debe poder crear, leer, actualizar, eliminar
            --- crear plantilla
            --- leer plantilla
            --- actualizar plantilla
            --- eliminar plantilla
        
    - documentos - documentos api
        -- los documentos son los que se generan a partir de las plantillas
        -- se deben poder crear, leer, actualizar, eliminar
            --- crear documento
            --- leer documento
            --- actualizar documento
            --- eliminar documento
    - documentos - historial api
        -- el historial es el registro de los documentos
        -- se debe poder revisar todos los registros de los documentos por usuario
        -- los usuarios de mayor nivel pueden ver sus registros previos y los de sus subordinados
        -- los usuarios de menor nivel solo pueden ver sus registros previos
        -- tener en cuenta que los documentos se pueden compartir
        -- tener en cuanta que se debe ver de quien es el documento
        -- tener en cuenta que se debe ver a quien se le compartio el documento
        -- tener en cuenta que se debe ver versiones anteriores
        -- tener en cuenta que se debe ver versiones posteriores
*/
