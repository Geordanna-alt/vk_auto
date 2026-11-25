package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import core.Loadable;
import data.Credentials;
import data.TestData;
import elements.Element;

/** Объект страницы входа */
public final class LoginPage extends Loadable<LoginPage> {

  private final Element usernameInput;
  private final Element passwordInput;
  private final Element loginButton;
  private final Element errorMessage;

  /** Страница входа */
  public LoginPage(Page page) {
    super(page, TestData.SAUCEDEMO_URL, DEFAULT_TIMEOUT);
    this.usernameInput = element(page.getByLabel("Username"), "Username");
    this.passwordInput = element(page.getByLabel("Password"), "Password");
    this.loginButton =
        element(
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")),
            "Login button");
    this.errorMessage = element(page.getByRole(AriaRole.ALERT), "Error message");
  }

  /** Проверяет загрузку страницы */
  @Override
  protected boolean isLoaded() {
    return page.url().startsWith(TestData.SAUCEDEMO_URL) && usernameInput.isVisible();
  }

  /** Поле логина */
  public Locator usernameInput() {
    return usernameInput.locator();
  }

  /** Поле пароля */
  public Locator passwordInput() {
    return passwordInput.locator();
  }

  /** Кнопка входа */
  public Locator loginButton() {
    return loginButton.locator();
  }

  /** Сообщение об ошибке */
  public Locator errorMessage() {
    return errorMessage.locator();
  }

  /** Заполняет данные входа */
  public LoginPage fillCredentials(Credentials credentials) {
    usernameInput.fill(credentials.username());
    passwordInput.fill(credentials.password());
    return self();
  }

  /** Клик без ожидания перехода: используется и при ошибках валидации */
  public LoginPage submit() {
    loginButton.click();
    return self();
  }

  /** Выполняет вход */
  public InventoryPage login(Credentials credentials) {
    fillCredentials(credentials);
    submit();
    waitForUrl("**/inventory.html");
    InventoryPage inventoryPage = Pages.inventory(page);
    inventoryPage.title().waitFor();
    return inventoryPage;
  }
}
