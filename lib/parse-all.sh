#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

exe="java -classpath $DIR/LBJChunk.jar:$DIR/LBJ2.jar:$DIR/LBJPOS.jar -Xmx512m edu.illinois.cs.cogcomp.lbj.chunk.ChunksAndPOSTags";

for file in $*
do
    echo "$exe $file > $file.parsed";
    $exe $file > $file.parsed
done
