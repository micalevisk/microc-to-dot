#!/bin/bash
set -e
[ -e "Compilador.jar" ] || ./build.sh
set +e
for filename in tests/*.uc
do
  output_filename="${filename}.dot"
  echo "--- Executando para '${filename}'"
  java -jar Compilador.jar "$filename" "$output_filename" &&
  echo "------ arquivo gerado: '${output_filename}'"
  # dot -Tpng "$output_filename" > "outputs/${filename}.dot.png"
done
