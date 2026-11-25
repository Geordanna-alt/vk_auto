package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import data.Credentials;
import data.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.InventoryPage;
import pages.Pages;

/** Тесты страницы входа */
@DisplayName("Страница логина")
@Tag("login")
@Timeout(120)
class LoginPageTest extends BaseTest {

  @Test
  @Tag("smoke")
  @DisplayName("Открывается форма входа")
  void pageOpens() {
    var loginPage = Pages.login(page).open();

    assertAll(
        () -> assertEquals("Swag Labs", page.title()),
        () -> assertThat(loginPage.usernameInput()).isVisible(),
        () -> assertThat(loginPage.passwordInput()).isVisible(),
        () -> assertThat(loginPage.loginButton()).isVisible());
  }

  @Test
  @DisplayName("Успешный вход открывает каталог")
  void successfulLogin() {
    InventoryPage inventoryPage = Pages.login(page).open().login(TestData.STANDARD_USER);

    assertThat(inventoryPage.title()).hasText("Products");
  }

  @ParameterizedTest
  @MethodSource("data.TestData#invalidCredentials")
  @DisplayName("Неверные данные показывают ошибку")
  void invalidCredentialsShowError(Credentials credentials, String expectedError) {
    var loginPage = Pages.login(page).open();

    loginPage.fillCredentials(credentials).submit();

    assertThat(loginPage.errorMessage()).hasText(expectedError);
  }

  @Test
  @DisplayName("Пустые поля показывают ошибку")
  void emptyCredentialsShowError() {
    var loginPage = Pages.login(page).open();

    loginPage.submit();

    assertThat(loginPage.errorMessage()).hasText("Epic sadface: Username is required");
  }
}
