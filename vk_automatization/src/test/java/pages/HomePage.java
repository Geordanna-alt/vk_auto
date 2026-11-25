package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import core.Loadable;
import data.TestData;
import elements.Element;

/** Объект главной страницы https://sauce-demo.myshopify.com */
public final class HomePage extends Loadable<HomePage> {

  private final Element searchInput;
  private final Element searchButton;
  private final Element cartLink;

  /** Главная страница */
  public HomePage(Page page) {
    super(page, TestData.SHOP_URL, DEFAULT_TIMEOUT);
    this.searchInput = element(page.getByPlaceholder("Search"), "Search input");
    this.searchButton =
        element(
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")),
            "Search button");
    this.cartLink =
        element(
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("My Cart"))
                .filter(new Locator.FilterOptions().setVisible(true)),
            "Cart link");
  }

  /** Проверяет загрузку страницы */
  @Override
  protected boolean isLoaded() {
    return page.url().startsWith(TestData.SHOP_URL) && searchInput.isVisible();
  }

  /** Элемент поля поиска */
  public Element searchInputElement() {
    return searchInput;
  }

  /** Элемент кнопки поиска */
  public Element searchButtonElement() {
    return searchButton;
  }

  /** Элемент ссылки на корзину */
  public Element cartLinkElement() {
    return cartLink;
  }

  /** Поле поиска */
  public Locator searchInput() {
    return searchInput.locator();
  }

  /** Кнопка поиска */
  public Locator searchButton() {
    return searchButton.locator();
  }

  /** Ссылка входа в шапке */
  public Locator loginLink() {
    return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Log In"));
  }

  /** Ссылка на корзину */
  public Locator cartLink() {
    return cartLink.locator();
  }

  /** Карточка товара по названию */
  public Locator productCard(String productName) {
    return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(productName));
  }

  /** Все карточки товаров */
  public Locator productCards() {
    return page.locator("a[id^='product-']");
  }

  /** Названия товаров на главной */
  public Locator productNames() {
    return productCards().getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(3));
  }

  /** Цены товаров на главной */
  public Locator productPrices() {
    return productCards().getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(4));
  }

  /** Ссылка в меню или шапке по доступному имени */
  public Locator navLink(String name) {
    return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(name)).first();
  }

  /** Ссылка оформления заказа в шапке */
  public Locator checkoutLink() {
    return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Check Out"));
  }

  /** Подвал страницы */
  public Locator footer() {
    return page.getByRole(AriaRole.CONTENTINFO);
  }

  /** Сообщение пустой корзины в выдвижной панели */
  public Locator emptyCartMessage() {
    return page.getByText("Your cart is empty");
  }

  /** Ввести поисковый запрос и отправить форму */
  public HomePage search(String query) {
    searchInput.fill(query);
    searchInput.press("Enter");
    return self();
  }
}
