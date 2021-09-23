# search_files

## Funcionalidad
Search files, como su propio nombre indica, consiste en la búsqueda de ficheros. Su funcionalidad reside en la búsqueda de ficheros por patrones en una ruta dada.

## Requisitos
Este programa requiere de dos parámetros, ambos obligatorios:

- **Primer parámetro**: El primer parámetro está destinado a la ruta, pudiendo ser la absoluta o la relativa. En el caso de no querer ingresar una ruta concreta, se puede ingresar como valor del parámentro la palabra *"this"*, para así ejecutar una búsqueda dentro del directorio actual.

- **Segundo parámetro**: El segundo parámetro es el patrón a buscar. El patrón se analizará como una expresión regular. En caso de tener dudas de como funcionan las expresiones regulares, se puede ingresar en la siguiente página para hacer pruebas o ver las explicaciones que se ofrecen dentro de ella: https://regex101.com/

## Ejemplos de expresiones regulares
- Que contenga el caracter 'a', 'b' o 'c': [abc]
- Cualquier caracter menos 'a', 'b' o 'c': [^abc]
- Que contenga un dígito: \d
- Que contenga tres 'a' seguidas: a{3}
- Que comience por log: ^log
- Que termine por log: log$
- Que contenga la palabra file: file

## Configuración
El programa requiere de una configuración para poder ejecutarse correctamete. Dicha configuración ha de estar en la misma ubicación que el ejecutable o el proyecto, en una carpeta llamada *"config"*:

```
 - *Ejecutable.jar* o raiz del proyecto 
 - config
	- application.yml
```
	
El contenido ha de tener la siguente estructura:

```yaml
searchfiles:
  csv:
    export_path:
    export_name:
    export_extension:
    export_date_format:
  search:
    this_path:
    default_path:
    none_extension_value:
```
	
Actualmente, al descargar el proyecto, ya se dispone de esa carpeta de configuración, pero por información para el usuario, el contenido del archivo *application.yml* que hay actualmente es el siguiente:

```yaml
searchfiles:
  csv:
    export_path: .\csv_export\
    export_name: export_files_
    export_extension: .csv
    export_date_format: dd_MM_yyyy-HH_mm_ss_SSS
  search:
    this_path: this
    default_path: .\
    none_extension_value: -- None --
```