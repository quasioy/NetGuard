
#!/usr/bin/env sh
CURRENTDATE=`date +"%d-%m-%Y %T"`
GIT_COMMIT_LOG="$(git log --format='%s (by %cn)' $TRAVIS_COMMIT_RANGE)"

echo "<b>Build ${MAJOR_MINOR}.${TRAVIS_BUILD_NUMBER}</b>${NEWLINE}"
echo "\tbuilt on <i>$TRAVIS_BRANCH</i> branch${NEWLINE}"
echo "\ton $CURRENTDATE${NEWLINE}${NEWLINE}"
echo "Contains the followning changes:${NEWLINE}"

printf '%s\n' "$GIT_COMMIT_LOG" | while IFS= read -r line
do
  echo "- ${line}"
done
