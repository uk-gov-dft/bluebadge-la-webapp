#!/usr/bin/env bash

REPOS=(
    'authorisation-service::AZ_VERSION'
    'la-webapp::LA_VERSION'
    'usermanagement-service::UM_VERSION'
    'message-service::MG_VERSION'
    'referencedata-service::RD_VERSION'
    'badgemanagement-service::BB_VERSION'
    'applications-service::AP_VERSION'
)

export GITHUB_TOKEN=$(cat ~/.ssh/github_token)

echo "Fetching versions..."
for index in "${REPOS[@]}" ; do
    KEY="${index%%::*}"
    VALUE="${index##*::}"
    URL="https://github-automation-uk-gov-dft:$GITHUB_TOKEN@github.com/uk-gov-dft/$KEY.git"
    version=$(git ls-remote --tags $URL | egrep -o "v[0-9.]+" | sort -t. -k 1,1n -k 2,2n -k 3,3n | uniq | tail -n-1 | cut -c2-)
    export ${VALUE}=$version-SNAPSHOT
    #echo "$KEY, $VALUE - $version-SNAPSHOT"
done

# Override the version with the branch name
export LA_VERSION=$(cat VERSION-computed)

echo 'Setting version stack to:'
for index in "${REPOS[@]}" ; do
    envVar="${index##*::}"
    echo "${envVar} = ${!envVar}"
done
