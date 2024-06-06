#!/bin/hash

git pull
echo $RANDOM > git.md
git add .
git commit -m "update"
git push origin SJ