# client-info
Процесс запуска приложения:
1. клониурете проект себе локально git clone https://github.com/BogdanRakovchen/client-info.git
2. открываете проект в IDE или вводите команду <b>mvn spring-boot:run</b> в терминале и запускаете приложение
3. заходите в терминале в контейнер postgres и подключаетесь к базе: <d>docker exec -it postgres_clients psql -d clients -U bogdan</b>
4. переходите по адресу: http://localhost:8080/swagger-ui/index.html#/ для тестирования приложения
