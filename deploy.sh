#!/bin/bash -ex
cd $(dirname $0)
mvn clean package
git checkout gh-pages
rm -rf index.html pokertimer resources
mv target/pokertimer/index.html target/pokertimer/pokertimer target/pokertimer/resources ./
git add index.html pokertimer resources
git commit -a --allow-empty-message -m ''
git push
git checkout master
