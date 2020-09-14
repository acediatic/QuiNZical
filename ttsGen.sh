#!/bin/bash

echo $0 | text2wave -o
ffmpeg -i ./Ensoniq-ZR-76-01-Dope-77.wav -filter:a "atempo=0.5" 0.5song.wav

