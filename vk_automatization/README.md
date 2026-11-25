# Автотесты SauceDemo

UI-автотесты для SauceDemo, написанные на **Playwright** с использованием
**Java**, **JUnit 5** и **axe-core**.

Покрыты главная страница и регистрация на `https://sauce-demo.myshopify.com/`,
а также логин, каталог, корзина и оформление заказа на
`https://www.saucedemo.com/`.

## Требования

- JDK 25
- Maven 3.8+
- Linux/macOS/Windows (для Linux могут понадобиться системные зависимости Playwright)

## Установка

1. Установить браузеры Playwright (достаточно один раз):

   ```bash
   mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"
   ```

   Чтобы установить все поддерживаемые браузеры, выполните:

   ```bash
   mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
   ```

   Для Linux при необходимости установите системные зависимости:

   ```bash
   sudo env "PATH=$PATH" mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install-deps chromium"
   ```
## Стенды и тестовые пользователи

- `https://sauce-demo.myshopify.com/` — главная страница и регистрация.
- `https://www.saucedemo.com/` — логин, каталог, корзина и checkout.
  - `standard_user` / `secret_sauce` — успешный вход.
  - `locked_out_user` / `secret_sauce` — проверка заблокированного пользователя.

## Запуск тестов

Все тесты:

```bash
mvn test
```

Один тестовый класс:

```bash
mvn test -Dtest=LoginPageTest
mvn test -Dtest=InventoryPageTest
```

Один тестовый метод:

```bash
mvn test -Dtest=LoginPageTest#successfulLogin
```

Все тесты пакета:

```bash
mvn test -Dtest="tests.*Test"
```

По тегу:

```bash
mvn test -Dgroups=smoke
mvn test -Dgroups=login
mvn test -Dgroups=inventory
mvn test -Dgroups=cart
mvn test -Dgroups=checkout
mvn test -Dgroups=accessibility
mvn test -Dgroups=products
mvn test -Dgroups=search
mvn test -Dgroups=navigation
mvn test -Dgroups=footer
mvn test -Dgroups=visibility
mvn test -Dgroups=signup
```

## Паттерны проектирования

| Паттерн | Где используется |
| --- | --- |
| Page Object | `pages/*Page.java` |
| Page Element / Decorator / Wrapper | `elements/Element.java`, `elements/PageElement.java`, `elements/HighlightedElementDecorator.java` |
| Loadable Component | `core/Loadable.java`, `open()` в `LoginPage`, `HomePage`, `SignUpPage` |
| Chain of Invocations | `Pages.home(page).open().search(...)`, `Pages.login(page).open().login(credentials)` |
| Value Object | `data/Credentials.java`, `data/Customer.java`, `data/Registration.java` |
| Data Provider | `data/TestData.java` + `@MethodSource` в `LoginPageTest`, `HomePageSearchTest` |
| Factory | `pages/Pages.java` |
| Data Registry | `data/TestData.java` (URL, пользователи, ссылки меню) |

Пример цепочки из `LoginPageTest`:

```java
InventoryPage inventory = Pages.login(page).open().login(TestData.STANDARD_USER);
```

## Отчёты и проверки качества кода

- Результаты тестов: `target/surefire-reports/`
- Форматирование кода: `mvn spotless:apply`
- Проверка стиля: `mvn checkstyle:check`