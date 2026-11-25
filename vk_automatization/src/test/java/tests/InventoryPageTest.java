package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/** Тесты каталога */
@DisplayName("Каталог")
@Tag("inventory")
@Timeout(120)
class InventoryPageTest extends SauceDemoBaseTest {

  @Test
  @Tag("smoke")
  @DisplayName("Каталог открывается и содержит шесть товаров")
  void catalogOpens() {
    assertAll(
        () -> assertThat(inventoryPage.title()).hasText("Products"),
        () -> assertEquals(6, inventoryPage.items().count()),
        () -> assertThat(inventoryPage.cartLink()).isVisible());
  }

  @Test
  @DisplayName("У товаров правильные названия и цены")
  void productNamesAndPrices() {
    assertAll(
        () ->
            assertEquals(
                List.of(
                    "Sauce Labs Backpack",
                    "Sauce Labs Bike Light",
                    "Sauce Labs Bolt T-Shirt",
                    "Sauce Labs Fleece Jacket",
                    "Sauce Labs Onesie",
                    "Test.allTheThings() T-Shirt (Red)"),
                inventoryPage.itemNames().allInnerTexts()),
        () ->
            assertEquals(
                List.of("$29.99", "$9.99", "$15.99", "$49.99", "$7.99", "$15.99"),
                inventoryPage.itemPrices().allInnerTexts()));
  }

  @Test
  @DisplayName("Клик по товару открывает страницу товара")
  void openProductDetails() {
    inventoryPage.openItem("Sauce Labs Backpack");

    assertThat(page).hasURL(Pattern.compile(".*/inventory-item\\.html\\?id=\\d+"));
    assertAll(
        () -> assertThat(page.getByTestId("inventory-item-name")).hasText("Sauce Labs Backpack"),
        () -> assertThat(page.getByTestId("inventory-item-price")).hasText("$29.99"));
  }

  @Test
  @DisplayName("Кнопка корзины открывает страницу корзины")
  void cartLinkOpensCart() {
    inventoryPage.cartLink().click();

    page.waitForURL(
        "**/cart.html", new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

    assertThat(page.getByTestId("title")).hasText("Your Cart");
  }
}
