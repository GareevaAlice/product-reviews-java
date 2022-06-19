> Индивидуальный проект для курса "Промышленное программирование на языке Java".
>  
> июнь 2022
___

# Отзывы на товары

Написать сервис, позволяющий добавлять, изменять товары, а так же добавлять отзывы с оценками.

## Требования

Реализовать сервис в духе «слоистой архитектуры» - rest-контроллер, сервис с бизнес-логикой и слой хранения данных (в качестве базы данных можно использовать h2).

Контроллер должен предоставить следующие эндпоинты:

- `POST /product` - создение продукта
- `GET /product/{product_id}` - получение продукта с комментариями. В ответе сервера должен присутствовать средний рейтинг продукта основанный на всех отзывах
- `DELETE /product/{product_id}` - удаление продукта
- `POST /product/{product_id}/review` - добавление отзыва

# Сборка и тестирование плагина:
В артефактах успешных сборок [Java CI](https://github.com/GareevaAlice/product-reviews-java/actions/workflows/java.yaml) лежат архивы:
* **ProductReviews.jar**  - сам плагин
* **tests coverage** - html-ки с результатами покрытия тестами
* **tests results** - результаты тестов (сохраняется и при неудачной сборке)

Если возникают проблемы, можно собрать локально:
```
mvn clean package
```
* должен создаться плагин: **./target/ProductReviews-1.0.jar**
* html-ки с результатами покрытия лежат в **./target/site/jacoco**
* результаты тестов лежат в:  **./target/surefire-reports**

# Запуск сервиса
```
java -jar .\target\ProductReviews-1.0.jar
```

Сервер работает по адресу **http://localhost:8080**

Можно посмотреть базу данных по вдрессу **http://localhost:8080/h2-console** (**JDBC URL: jdbc:h2:mem:ProductReviews**)

# Примеры запросов:
```
curl -H "Content-Type: application/json" -X POST http://localhost:8080/product/ -d "{\"description\":"\"description\"}"
curl -H GET http://localhost:8080/product/1
curl -H "Content-Type: application/json" -X POST http://localhost:8080/product/1/review -d "{\"message\":\"message\", \"rating\":5}"
curl -X DELETE http://localhost:8080/product/1
```