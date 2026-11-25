package tests;

import data.TestData;
import org.junit.jupiter.api.BeforeEach;
import pages.CartPage;
import pages.InventoryPage;
import pages.Pages;

/** База для тестов, которым нужен уже выполненный вход и пустая корзина */
public abstract class SauceDemoBaseTest extends BaseTest {

  protected InventoryPage inventoryPage;

  @BeforeEach
  void loginAsStandardUser() {
    inventoryPage = Pages.login(page).open().login(TestData.STANDARD_USER);

    // Состояние корзины сохраняется на стенде между прогонами — очищаем его
    CartPage cartPage = inventoryPage.openCart().removeAll();
    inventoryPage = cartPage.continueShopping();
  }
}
