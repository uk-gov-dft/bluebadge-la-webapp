#!/usr/bin/env bash

# Override the version with the branch name
export LA_VERSION=$(cat VERSION-computed)
export BB_VERSION=v0.18.0-feature_BBB-1273-add-issued-date-and-rejected-reason-to-GET-badges