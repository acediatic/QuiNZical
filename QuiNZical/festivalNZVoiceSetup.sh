#!/bin/bash

if [ ! -d "/usr/share/festival/voices/english/akl_nz_jdt_diphone" ]
then
	if [ -e "$pwd/akl_nz_jdt_diphone.zip" ]
	then
		sudo unzip akl_nz_jdt_diphone.zip -d /usr/share/festival/voices/english
	fi	
fi
if [ ! -d "/usr/share/festival/voices/english/akl_nz_cw_cg_cg" ]
then
	if [ -e "$pwd/akl_nz_cw_cg_cg.zip" ]
	then
		sudo unzip akl_nz_cw_cg_cg.zip -d /usr/share/festival/voices/english
	fi
fi

