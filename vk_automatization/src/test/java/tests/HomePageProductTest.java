package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitUntilState;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.Pages;

/** Тесты товаров на главной странице */
@DisplayName("Главная страница: товары")
@Tag("products")
@Timeout(120)
class HomePageProductTest extends BaseTest {

  @Test
  @DisplayName("Отображаются три товара с названиями и ценами")
  void productCards() {
    var homePage = Pages.home(page).open();

    assertAll(
        () -> assertEquals(3, homePage.productCards().count()),
        () ->
            assertEquals(
                List.of("Grey jacket", "Noir jacket", "Striped top"),
                homePage.productNames().allInnerTexts()),
        () ->
            assertEquals(
                List.of("£55.00", "£60.00", "£50.00"), homePage.productPrices().allInnerTexts()));
  }

  @Test
  @DisplayName("Клик по товару открывает страницу товара")
  void openProduct() {
    var homePage = Pages.home(page).open();

    homePage.productCard("Grey jacket").click();
    page.waitForURL(
        "**/products/grey-jacket**",
        new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

    assertThat(
            page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Grey jacket"))
                .first())
        .isVisible();
  }
}
