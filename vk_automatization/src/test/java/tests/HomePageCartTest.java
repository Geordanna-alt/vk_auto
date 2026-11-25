package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.Pages;

/** Тесты корзины на главной странице */
@DisplayName("Главная страница: корзина")
@Tag("cart")
@Timeout(120)
class HomePageCartTest extends BaseTest {

  @Test
  @DisplayName("Пустая корзина показывает сообщение")
  void emptyCart() {
    var homePage = Pages.home(page).open();

    homePage.cartLink().click();

    assertThat(homePage.emptyCartMessage()).isVisible();
  }
}
