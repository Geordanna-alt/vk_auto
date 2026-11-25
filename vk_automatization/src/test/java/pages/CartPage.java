package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import core.BasePage;
import elements.Element;

/** Объект страницы корзины */
public final class CartPage extends BasePage<CartPage> {

  private final Element checkoutButton;
  private final Element continueShoppingButton;

  /** Страница корзины */
  public CartPage(Page page) {
    super(page);
    this.checkoutButton = element(page.getByTestId("checkout"), "Checkout button");
    this.continueShoppingButton =
        element(page.getByTestId("continue-shopping"), "Continue shopping");
  }

  /** Заголовок */
  public Locator title() {
    return page.getByTestId("title");
  }

  /** Товары */
  public Locator items() {
    return page.getByTestId("inventory-item");
  }

  /** Названия товаров */
  public Locator itemNames() {
    return page.getByTestId("inventory-item-name");
  }

  /** Цены товаров */
  public Locator itemPrices() {
    return page.getByTestId("inventory-item-price");
  }

  /** Кнопка оформления заказа */
  public Locator checkoutButton() {
    return checkoutButton.locator();
  }

  /** Кнопка продолжения покупок */
  public Locator continueShoppingButton() {
    return continueShoppingButton.locator();
  }

  /** Товар в корзине по названию */
  public Locator item(String name) {
    return items().filter(new Locator.FilterOptions().setHasText(name));
  }

  /** Удалить товар из корзины */
  public CartPage remove(String name) {
    item(name).getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Remove")).click();
    return self();
  }

  /** Очистить корзину, чтобы тесты не зависели от состояния стенда */
  public CartPage removeAll() {
    title().waitFor();
    var removeButtons =
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove"));
    while (removeButtons.count() > 0) {
      removeButtons.first().click();
    }
    return self();
  }

  /** Переходит к оформлению заказа */
  public CheckoutPage checkout() {
    checkoutButton.click();
    waitForUrl("**/checkout-step-one.html");
    return Pages.checkout(page);
  }

  /** Возвращает в каталог */
  public InventoryPage continueShopping() {
    continueShoppingButton.click();
    waitForUrl("**/inventory.html");
    InventoryPage inventoryPage = Pages.inventory(page);
    inventoryPage.title().waitFor();
    return inventoryPage;
  }
}
