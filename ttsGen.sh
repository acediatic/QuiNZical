#!/bin/bash

echo $1 | text2wave -o spokenText.wav
ffmpeg -y -i spokenText.wav -filter:a "atempo=$2" spokenTextAdj.wav > /dev/null 2>&1
play -q spokenTextAdj.wav > /dev/null 2>&1
rm spokenText.wav spokenTextAdj.wav


