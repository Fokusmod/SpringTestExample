## Решение тестового задания.

#### Для запуска приложения нужно выполнить несколько команд:

1. Перейти в директорию приложения, выполнив команду:
   `cd C:\Users\YOUR_USER\Desktop\CLONED_REPOSITORY`
2. Далее выполяем команды:
   `docker compose build && docker compose up`

#### Обратиться к эндпоинтам можно по адресам:

Для получения кошелька (GET запрос):
`http://localhost:8080/Test-job/api/v1/wallets/EXISTS_UUID`
Где EXISTS_UUID - существующий UUID, который можно узнать в базе данных.

Для проведения операций по кошельку (POST запрос):
`http://localhost:8080/Test-job/api/v1/wallet`
В качестве тела запроса нужно отправить JSON в формате:
{
valletId: UUID,
operationType: DEPOSIT or WITHDRAW,
amount: 1000
}

АЛЁ ПУЛЫ ПОТОВ В БД!!!!


