#!/bin/sh
# Author : Nicolae Suruceanu
# Cleaning the Folders below
echo "Cleaning residual artefacts:"
rm -f screenshots/*.png || true
rm -f screenRecordings/*.mp4 || true
rm -f allure-results/*.json || true
rm -f allure-results/*.mp4 || true
rm -f allure-results/*.png || true
rm -f allure-results/*.txt || true
echo "Cleaning finished."