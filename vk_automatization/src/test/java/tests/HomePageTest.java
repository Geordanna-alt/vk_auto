package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.Pages;

/** Тесты главной страницы */
@DisplayName("Главная страница SauceDemo")
@Tag("visibility")
@Timeout(120)
class HomePageTest extends BaseTest {

  @Test
  @DisplayName("Ссылка на корзину видна")
  void cartVisibilityTest() {
    Pages.home(page).open().cartLinkElement().shouldBeVisible();
  }
}
