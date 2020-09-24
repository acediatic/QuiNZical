#!/bin/bash

echo $1 | text2wave -o spokenText.wav
play -q spokenText.wav tempo $2 > /dev/null 2>&1
rm spokenText.wav
