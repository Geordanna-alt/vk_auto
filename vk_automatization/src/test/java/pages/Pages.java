package pages;

import com.microsoft.playwright.Page;

/** Фабрика для всех объектов страниц */
public final class Pages {

  private Pages() {}

  /** Создаёт главную страницу */
  public static HomePage home(Page page) {
    return new HomePage(page);
  }

  /** Создаёт страницу регистрации */
  public static SignUpPage signUp(Page page) {
    return new SignUpPage(page);
  }

  /** Выполняет вход */
  public static LoginPage login(Page page) {
    return new LoginPage(page);
  }

  /** Создаёт каталог */
  public static InventoryPage inventory(Page page) {
    return new InventoryPage(page);
  }

  /** Создаёт корзину */
  public static CartPage cart(Page page) {
    return new CartPage(page);
  }

  /** Создаёт страницу оформления заказа */
  public static CheckoutPage checkout(Page page) {
    return new CheckoutPage(page);
  }
}
