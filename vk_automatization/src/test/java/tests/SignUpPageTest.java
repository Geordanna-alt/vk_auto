package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.Pages;

/** Тесты страницы регистрации */
@DisplayName("Страница регистрации")
@Tag("signup")
@Timeout(120)
class SignUpPageTest extends BaseTest {

  @Test
  @Tag("smoke")
  @DisplayName("Открывается форма создания аккаунта")
  void pageOpens() {
    var signUpPage = Pages.signUp(page).open();

    assertAll(
        () -> assertEquals("Create Account – Sauce Demo", page.title()),
        () -> assertThat(signUpPage.heading()).isVisible(),
        () -> assertThat(signUpPage.firstNameInput()).isVisible(),
        () -> assertThat(signUpPage.lastNameInput()).isVisible(),
        () -> assertThat(signUpPage.emailInput()).isVisible(),
        () -> assertThat(signUpPage.passwordInput()).isVisible(),
        () -> assertThat(signUpPage.createButton()).isVisible());
  }
}
