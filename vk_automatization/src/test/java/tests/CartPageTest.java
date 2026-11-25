package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.CartPage;

/** Тесты корзины */
@DisplayName("Корзина")
@Tag("cart")
@Timeout(120)
class CartPageTest extends SauceDemoBaseTest {

  @Test
  @Tag("smoke")
  @DisplayName("Пустая корзина открывается")
  void emptyCart() {
    CartPage cartPage = inventoryPage.openCart();

    assertAll(
        () -> assertThat(cartPage.title()).hasText("Your Cart"),
        () -> assertEquals(0, cartPage.items().count()),
        () -> assertThat(cartPage.checkoutButton()).isVisible());
  }

  @Test
  @DisplayName("Добавленный товар отображается в корзине")
  void addedItemAppearsInCart() {
    CartPage cartPage = inventoryPage.addToCart("Sauce Labs Backpack").openCart();

    assertAll(
        () -> assertEquals(1, cartPage.items().count()),
        () -> assertThat(cartPage.itemNames()).hasText("Sauce Labs Backpack"),
        () -> assertThat(cartPage.itemPrices()).hasText("$29.99"));
  }

  @Test
  @DisplayName("Товар удаляется из корзины")
  void itemCanBeRemoved() {
    CartPage cartPage = inventoryPage.addToCart("Sauce Labs Backpack").openCart();

    cartPage.remove("Sauce Labs Backpack");

    assertThat(cartPage.items()).hasCount(0);
  }

  @Test
  @DisplayName("Continue Shopping возвращает в каталог")
  void continueShoppingReturnsToCatalog() {
    inventoryPage.openCart().continueShopping();

    assertThat(inventoryPage.title()).hasText("Products");
  }
}
