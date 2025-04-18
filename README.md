## Решение тестового задания.

#### Для запуска приложения нужно выполнить несколько команд:

1. Перейти в директорию приложения, выполнив команду:
   `cd C:\Users\YOUR_USER\Desktop\CLONED_REPOSITORY`
2. Далее выполяем команды:
   `docker compose build && docker compose up`

#### Обратиться к эндпоинтам можно по адресам:

#### Тестовые кошельки:
```
6b15f4f4-c478-44f3-b026-3abf2dcb08ae
1b15f4f4-c478-44f3-b026-3abf2dcb08ea
```


### Для получения кошелька (GET запрос):

`http://localhost:8080/Test-job/api/v1/wallets/EXISTS_UUID`

Где EXISTS_UUID - один из тестовых кошельков (см. выше).


### Для проведения операций по кошельку (POST запрос):

`http://localhost:8080/Test-job/api/v1/wallet`

В качестве тела запроса нужно отправить JSON в формате:
```
{ 
valletId: UUID,
operationType: DEPOSIT or WITHDRAW,
amount: 1000
}
```





