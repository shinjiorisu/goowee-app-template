set APP_DIR=C:\gooweeapp
set APP_NAME=gooweeapp
set APP_SERVICE_NAME=gooweeapp

"%~dp0nssm.exe" install "%APP_SERVICE_NAME%" "%APP_DIR%\%APP_NAME%-service.bat"
pause
