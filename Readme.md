# Дипломный проект по профессии «Тестировщик»


Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.


## Документы
* [План автоматизации](https://github.com/KulinichevaKarina/Diploma/blob/master/Plan.md) 
* [Отчет по итогам тестирования](https://github.com/KulinichevaKarina/Diploma/blob/master/Report.md) 
* [Отчет по итогам автоматизации](https://github.com/KulinichevaKarina/Diploma/blob/master/Summary.md) 


## Описание приложения

Приложение — это веб-сервис, который предлагает купить тур по определенной цене двумя способами:


1. Обычная оплата по дебетовой карте.
2. Уникальная технология: выдача кредита по данным банковской карты.




<img width="705" alt="service" src="https://github.com/KulinichevaKarina/Diploma/assets/128910247/583b48e4-82f2-4303-ad1f-9a3561328c1b">





Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:
* сервису платежей, далее Payment Gate;
* кредитному сервису, далее Credit Gate.


 Приложение в собственной СУБД должно сохранять информацию о том, успешно ли был совершён платёж и каким способом. Данные карт при этом сохранять не допускается.


 ## Предварительные условия для запуска автотестов


1. Для запуска проекта и тестов на локальном компьютере потребуются установленные 
* Git
* JDK 11
* IntelliJ IDEA
* Docker
* Gradle 7.4.2

## Запуск тестов


* Склонировать репозиторий https://github.com/KulinichevaKarina/Diploma командой `git clone`
* Запустить IntelliJ IDEA и открыть проект
* Для запуска контейнеров с MySql, PostgreSQL и Node.js использовать команду `docker-compose up -d`
* Запуск приложения осуществляется во втором терминале:
    * для запуска под MySQL использовать команду 
    ```
    java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar ./artifacts/aqa-shop.jar
    ```
    * для запуска под PostgreSQL использовать команду 
    ```
    java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar ./artifacts/aqa-shop.jar
    ```
* Запуск тестов осуществляется в третьем терминале:
   * для запуска под MySQL использовать команду 
   ```
   gradle -Ddb.url=jdbc:mysql://localhost:3306/app clean test
   ```
   * для запуска под PostgreSQL использовать команду 
   ```
   gradle -Ddb.url=jdbc:postgresql://localhost:5432/app clean test
   ```
    
* Для получения отчета (Allure) использовать команду `./gradlew allureServe`
* После окончания тестов завершить работу приложения (Ctrl+C)
* Остановить контейнеры командой `docker-compose down`
