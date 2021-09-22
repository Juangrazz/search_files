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
