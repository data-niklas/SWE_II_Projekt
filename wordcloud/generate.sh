#!/bin/sh
find .. -type f | grep -F ".java" | xargs cat | sed '/^$/d' > code.txt
cat code.txt| wordcloud_cli --mask marklin.jpg --colormask marklin.jpg --imagefile out.jpg
