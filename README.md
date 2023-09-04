# API user contacts
Процесс запуска приложения:
1. клониурете проект себе локально <b> git clone https://github.com/BogdanRakovchen/client-info.git </b>
2. открываете проект в IDE или вводите команду <b>mvn spring-boot:run</b> в терминале в папке проекта и запускаете приложение
3. заходите в терминале в контейнер postgres и подключаетесь к базе: <b> docker exec -it postgres_clients psql -d clients -U bogdan </b>
4. переходите по адресу: <b> http://localhost:8080/swagger-ui/index.html#/ </b> для тестирования приложения
