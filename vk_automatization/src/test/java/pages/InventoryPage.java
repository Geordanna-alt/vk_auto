package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import core.BasePage;
import elements.Element;

/** Объект страницы каталога */
public final class InventoryPage extends BasePage<InventoryPage> {

  private final Element cartLink;

  /** Страница каталога */
  public InventoryPage(Page page) {
    super(page);
    this.cartLink = element(page.getByTestId("shopping-cart-link"), "Cart link");
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

  /** Ссылка на корзину */
  public Locator cartLink() {
    return cartLink.locator();
  }

  /** Счётчик корзины */
  public Locator cartBadge() {
    return page.getByTestId("shopping-cart-badge");
  }

  /** Товар по названию */
  public Locator item(String name) {
    return items().filter(new Locator.FilterOptions().setHasText(name));
  }

  /** Добавляет товар в корзину */
  public InventoryPage addToCart(String name) {
    item(name)
        .getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Add to cart"))
        .click();
    return self();
  }

  /** Открывает товар */
  public InventoryPage openItem(String name) {
    item(name)
        .getByRole(
            AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("View details for " + name))
        .first()
        .click();
    return self();
  }

  /** Открывает корзину */
  public CartPage openCart() {
    cartLink.click();
    waitForUrl("**/cart.html");
    return Pages.cart(page);
  }
}
