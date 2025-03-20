# Модуль учёта финансов

Этот проект представляет собой консольное приложение для учёта финансов, реализованное на языке Java. Приложение демонстрирует применение принципов SOLID, GRASP, а также нескольких паттернов проектирования GoF (Фабрика, Фасад, Команда, Декоратор, Шаблонный метод, Посетитель, Прокси). Для внедрения зависимостей используется Guice. Модуль позволяет создавать, редактировать и удалять счета, категории и финансовые операции, проводить аналитику, а также осуществлять импорт и экспорт данных в форматах CSV, JSON и YAML.

## Содержание

- [Обзор](#обзор)
- [Особенности](#особенности)
- [Паттерны и архитектура](#паттерны-и-архитектура)
- [Работа приложения](#работа-приложения)


## Обзор

Модуль учёта финансов предназначен для управления личными финансами. Он позволяет:
- Управлять счетами, категориями (доход/расход) и финансовыми операциями.
- Проводить аналитику: подсчитывать чистую разницу между доходами и расходами, группировать операции по категориям за выбранный период.
- Импортировать и экспортировать данные в файлы в форматах CSV, JSON и YAML.
- Пересчитывать баланс счета на основе его начального значения и всех проведённых операций.

## Особенности

- **Доменная модель:**  
  - Классы `BankAccount`, `Category` и `Operation` с необходимыми полями и валидацией.
- **CRUD-операции:**  
  - Создание, редактирование и удаление счетов, категорий и операций.
- **Аналитика:**  
  - Расчёт чистой разницы доходов и расходов, группировка операций по категориям.
- **Импорт/экспорт данных:**  
  - Импорт и экспорт в форматах CSV, JSON и YAML.
- **Пересчет баланса:**  
  - Пересчет баланса счёта на основе его начального значения и проведённых операций.
- **Применяемые паттерны:**  
  - **Фабрика:** Создание доменных объектов с валидацией.  
  - **Фасад:** Упрощённое взаимодействие с функциональностью модуля через `FinanceManager`.  
  - **Команда + Декоратор:** Инкапсуляция действий пользователя и измерение времени их выполнения.  
  - **Шаблонный метод:** Стандартный алгоритм импорта данных для разных форматов.  
  - **Посетитель:** Экспорт данных (например, в CSV).  
  - **Прокси:** Кэширование данных в репозитории.  
- **Внедрение зависимостей:**  
  - Использование Guice для управления зависимостями, что повышает связность кода и облегчает поддержку.
- **Обработка ошибок и логирование:**  
  - Использование `java.util.logging` для логирования важных событий и ошибок.

## Паттерны и архитектура

Проект разделён на несколько логических пакетов:

- **`finance.domain`**  
  Содержит доменные классы (`BankAccount`, `Category`, `Operation`) и фабрику объектов (`DomainFactory`).

- **`finance.repository`**  
  Содержит интерфейс репозитория (`FinanceRepository`) и его реализации (например, `InMemoryFinanceRepository` и `CachedFinanceRepository`).

- **`finance.service`**  
  Содержит класс `FinanceManager`, который инкапсулирует бизнес-логику и действует как фасад для работы с данными.

- **`finance.command`**  
  Содержит интерфейс `Command` и классы команд для работы с консольным интерфейсом (например, `CreateAccountCommand`, `EditCategoryCommand`, `ExportDataCommand` и т.д.).

- **`finance.importexport`**  
  Содержит логику импорта и экспорта данных (импортеры для CSV, JSON, YAML и класс `ExportCSVVisitor`).

- **`finance.di`**  
  Содержит модуль конфигурации DI (например, `FinanceModule` для Guice).

## Проект использует:

- **Guice** для внедрения зависимостей.
- **Jackson** для обработки JSON и YAML, включая модуль **jackson-datatype-jsr310** для поддержки типов даты/времени Java 8.


## Работа приложения:

### Если вы используете Maven, добавьте следующие зависимости в pom.xml:

```xml
<dependencies>
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.18.2</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml -->
        <dependency>
            <groupId>com.fasterxml.jackson.dataformat</groupId>
            <artifactId>jackson-dataformat-xml</artifactId>
            <version>2.18.2</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.google.inject/guice -->
        <dependency>
            <groupId>com.google.inject</groupId>
            <artifactId>guice</artifactId>
            <version>7.0.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-yaml -->
        <dependency>
            <groupId>com.fasterxml.jackson.dataformat</groupId>
            <artifactId>jackson-dataformat-yaml</artifactId>
            <version>2.18.2</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310 -->
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jsr310</artifactId>
            <version>2.18.2</version>
        </dependency>
    </dependencies>
```

### Работа через консоль:

- При запуске приложение покажет меню, где можно:
- Создавать, редактировать и удалять счета, категории и операции.
- Проводить аналитику по финансовым операциям.
- Экспортировать данные в файл в формате CSV, JSON или YAML.
- Импортировать данные из файла.
- Пересчитывать баланс счета.
- Импорт/Экспорт файлов:

**При экспорте укажите путь для сохранения файла, при импорте — путь к существующему файлу. Обратите внимание, что формат файла должен соответствовать выбранному (CSV, JSON или YAML).**
