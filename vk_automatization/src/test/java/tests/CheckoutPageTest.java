package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import data.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.CartPage;
import pages.CheckoutPage;

/** Тесты оформления заказа */
@DisplayName("Оформление заказа SauceDemo")
@Tag("checkout")
@Timeout(120)
class CheckoutPageTest extends SauceDemoBaseTest {

  private CheckoutPage checkoutPage;

  @BeforeEach
  void openCheckout() {
    checkoutPage = inventoryPage.addToCart("Sauce Labs Backpack").openCart().checkout();
  }

  @Test
  @DisplayName("Пустые поля формы показывают ошибки")
  void requiredFieldsShowErrors() {
    checkoutPage.continueToOverview();
    assertThat(checkoutPage.errorMessage()).hasText("Error: First Name is required");

    checkoutPage.firstNameInput().fill("John");
    checkoutPage.continueToOverview();
    assertThat(checkoutPage.errorMessage()).hasText("Error: Last Name is required");

    checkoutPage.lastNameInput().fill("Doe");
    checkoutPage.continueToOverview();
    assertThat(checkoutPage.errorMessage()).hasText("Error: Postal Code is required");
  }

  @Test
  @DisplayName("Успешное оформление заказа")
  void successfulCheckout() {
    checkoutPage.fillInfo(TestData.CUSTOMER).continueToOverview();

    assertAll(
        () -> assertThat(checkoutPage.title()).hasText("Checkout: Overview"),
        () -> assertThat(checkoutPage.total()).hasText("Total: $32.39"));

    checkoutPage.finish();

    assertAll(
        () -> assertThat(checkoutPage.completeHeader()).hasText("Thank you for your order!"),
        () ->
            assertThat(checkoutPage.completeText()).containsText("Your order has been dispatched"));
  }

  @Test
  @DisplayName("Cancel возвращает в корзину")
  void cancelReturnsToCart() {
    CartPage cartPage = checkoutPage.cancel();

    assertThat(cartPage.title()).hasText("Your Cart");
  }
}
