:: Запускает приложение в dev профиле.
:: Пользователи сервиса - dev и dev2. 

java -jar bootstrap-0.0.1-SNAPSHOT-exec.jar ua.mai.servs.bootstrap.ModAaaApplication ^
     --server.port=5001 --debug  --spring.profiles.active=dev
::   >_run.log 
::     -v
::     -p DDD 
::     -h 
