Инструкция по использованию API
1)запустить jar-файл(сервер автоматически развертывается на localhost:8080)
2)направлять запросы на адрес api/v1/
3)получить ответ в формате JSON
_____________________________________________________________
I)Названия и значения переменных входных данных
    1)name - имя человека или записи в телефонной книге
    2)id - индивидуальный номер человека
    3)recordId - индивидуальный номер записи в телефонной книге
    4)phone - номер телефона в телефонной книге, передается без знака "+"
II)Допустимые запросы и их синтаксис к списку людей:
    1)Получение списка людей с их телефонными книгами
        GET persons
    2)Получение человека с его телефонной книгой по id
        GET persons/(id)
    3)Создание человека(его телефонная книга создается отдельно, смотрите п. III)
        POST persons?name=(имя)
    4)Обновление информации о человеке.
        PUT persons/(id)?name=(имя)
    5)Удаление человека. Id - индивидуальный номер изменяемого человека
        DELETE persons/(id)
    6)Поиск людей по имени
        GET persons/search?name=(имя)
III)Допустимые запросы к телефонной книге
    1)Получение записей телефонной книги
        GET persons/(id)/records
    2)Получение записи в телефонной книге человека по ее recordId
        GET persons/(id)/records/(recordId)
    3)Создание записи в телефонной книге
        POST persons/(id)/records?name=(имя)&phone=(номер телефона)
    4)Обновление информации о записи в телефонной книге человека
        PUT persons/(id)/records/(recordId)?name=(имя)&phone=(номер телефона)
    5)Удаление записи из телефонной книги по ее recordId
        DELETE persons/(id)/records/(recordId)
    6)Поиск записей по номеру телефона
        GET persons/records/search?phone=(номер телефона)