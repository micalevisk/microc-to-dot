#!/bin/bash
[ $# -lt 1 ] && {
  echo "$0 <arquivo_alvo> [arquivo_saida]";
  exit;
}
input_filename="$1"
output_filename="$2"
java -jar "./Compilador.jar" "$input_filename" "$output_filename"

## Se tiver o CLI do Graphviz:
#command -v dot >/dev/null 2>&1 &&
[ -z "${output_filename}" ] ||
dot -Tpng -o "${output_filename}.png" "${output_filename}"
