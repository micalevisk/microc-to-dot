#!/bin/bash
# Para gerar o compilador em um JAR execuátvel, de nome `Compilador.jar`
COMPILER_JAR_FILENAME="Compilador.jar"

set -e

###################################################
###### Assuming the following directory tree ######
###################################################
: '
.
├── build.sh             <-
├── compile.sh
├── Compilador.jar       (this will be created)
├── compiler_tools
│   ├── Coco.jar
│   ├── microc.atg
│   ├── Parser.frame
│   └── Scanner.frame
├── manifest.mf
└── src
    ├── Compile.java
    ├── Diagrama.java
    ├── EdgeInfo.java
    ├── VertexInfo.java
    └── Graph.java
'
###################################################

exec 3>&1 ## salvar o descritor da stdout
exec 1>/dev/null
exec 4>&2 ## salvar o descritor da stderr
exec 2>/dev/null

while getopts "v" opt; do
  case $opt in
    v) ## enable verbose mode
      exec 1>&3 3>&-
      exec 2>&4 4>&-
    ;;
    \?) exit 1;;
  esac
done

rm -f "${COMPILER_JAR_FILENAME}"

cd compiler_tools/
rm -f *.java # cleanup
java -jar Coco.jar -frames . microc.atg
mv *.java ../src

cd ../src
javac -d . *.java #2>/dev/null
jar cfm "../${COMPILER_JAR_FILENAME}" ../manifest.mf *.class
rm *.class # cleanup
rm Parser.java Scanner.java
