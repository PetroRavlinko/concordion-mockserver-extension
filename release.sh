#!/bin/bash

RELEASECMD='./gradlew bintrayUpload'
PUBLISHDOCS='./gradlew publishGhPages'

if [[ ! -z "$TRAVIS_TAG" ]]
then
  echo "Performing release since this build is for a git tag ($TRAVIS_TAG)."
  echo

  echo "$RELEASECMD"
  eval "$RELEASECMD"

  echo "Publishing pages for a git tag ($TRAVIS_TAG)."
  echo

  echo "$PUBLISHDOCS"
  eval "$PUBLISHDOCS"

else
  echo "Not performing release since this build is not for a git tag."
fi