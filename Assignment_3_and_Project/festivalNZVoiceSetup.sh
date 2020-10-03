#!/bin/bash

if [ ( ! -d "/usr/share/festival/voices/english/unzip akl_nz_jdt_diphone" ) -a ( -e "$pwd/akl_nz_jdt_diphone.zip" ) ]
then
	sudo unzip akl_nz_jdt_diphone.zip -d /usr/share/festival/voices/english
fi
if [ ( ! -d "/usr/share/festival/voices/english/akl_nz_cw_cg_cg" ) -a ( -e "$pwd/akl_nz_cw_cg_cg.zip" ) ]
then
	sudo unzip akl_nz_cw_cg_cg.zip -d /usr/share/festival/voices/english
fi

