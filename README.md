# StudentArchive
StudentArchive_ServerSocket
# Description
Клиент-серверное приложение. 
Для реализации сетевого соединения используйте сокеты.
В архиве хранятся Дела (например, студентов). Архив находится на сервере. Клиент, в зависимости от прав, может запросить дело на просмотр, внести в
него изменения, или создать новое дело.

Обычный пользователь:
- просмотр всех дел в архиве,
- поиск дел по году поступления,
- поиск дел по номеру дела,
- сортировка дел.
Обычный пользователь может только просматривать дела студентов.

Администратор:
- добавить дело в архив,
- изменение дела,
- удаление дел по id,
- получение кол-ва дел в архиве и студентов,
- просмотр всех студенто,
- удаление студента из базы студентов,
- добавление студента в базу студентов,
- изменение студента.

Данные пользователя берутся с xml файла и парсятся с помощью SAX.

Новые дела и студенты сохраняются в бд, подключение осуществляется с помощью JDBC.
