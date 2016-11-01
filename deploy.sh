#!/bin/bash -ex
cd $(dirname $0)
mvn clean package
git checkout gh-pages
rm -rf index.html pokertimer
mv target/pokertimer/index.html target/pokertimer/pokertimer ./
git add index.html pokertimer
git commit -a --allow-empty-message -m ''
git push
git checkout master
