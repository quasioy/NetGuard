# Is this not a Pull Request?
if [ "$TRAVIS_PULL_REQUEST" = false ]; then

  # Is this not a build which was triggered by setting a new tag?
  if [ -z "$TRAVIS_TAG" ]; then
    echo -e "Starting to tag commit.\n"

    git config --global user.email "travis@travis-ci.com"
    git config --global user.name "Travis"

    # Add tag and push to master.
    git tag -a ${TRAVIS_BRANCH}-build${TRAVIS_BUILD_NUMBER} -m "Travis pushed tag for build $TRAVIS_BUILD_NUMBER."
    git push origin --tags
    git fetch origin

    echo -e "Done magic with tags.\n"
  fi
fi
