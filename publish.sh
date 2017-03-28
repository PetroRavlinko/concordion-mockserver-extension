#!/usr/bin/env bash

cp -r ./build/reports/concordion ./docs
cp ./src/test/resources/concordion/mockserver/Overview.html ./docs/Overview.html
cp ./src/main/resources/index.html ./docs/index.html

git remote rm origin
git remote add origin https://concordion_mockserver-ci-pages:$GH_TOKEN@github.com/PetroRavlinko/concordion-mockserver-extension.git
git add ./docs
git commit --amend --no-edit
git push origin $TRAVIS_BRANCH -f