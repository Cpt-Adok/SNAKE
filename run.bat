@echo off

set "error_file=error.txt"

chcp 65001
make 2> %error_file%

for %%A in ("%error_file%") do set "errror_size=%%~zA"

if %errror_size% gtr 0 (
    @echo Vous avez besoin d'installer make ou allez sur le repertoire ou contient Makefile pour lancer correctement le programme.
)

del /Q %error_file%

pause > null
exit