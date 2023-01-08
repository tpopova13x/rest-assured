package helper;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import exceptions.ProductNotFoundException;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.LicenseResponse;
import org.openapitools.client.model.ProductResponse;
import org.openqa.selenium.By;

import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;
import static data.PropertiesSupplier.prop;

public class PreconditionsAssignLicenses extends BaseHelper{
    private final Log log = LogFactory.getLog(PreconditionsAssignLicenses.class);
    public String getProductCodeNotInTeam() throws ApiException {
        val teamId = getMyTeamId();
        val teamProducts = getTeamLicenses(teamId).getData().stream()
                .map(LicenseResponse::getProduct)
                .filter(Objects::nonNull)
                .map(ProductResponse::getCode)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        return getAllForCustomerAsOrgAdmin().stream()
                .map(LicenseResponse::getProduct)
                .filter(Objects::nonNull)
                .map(ProductResponse::getCode)
                .filter(Objects::nonNull)
                .filter(code -> !teamProducts.contains(code))
                .peek(code->log.info(String.format("Product code %s was chosen", code)))
                .findFirst().orElseThrow(()->
                        new ProductNotFoundException("Product code not available in my team not found"));
    }
    public static void revokeAllLicensesExceptOneInEveryTeam() {
        val username = prop.getProperty("username");
        val password = prop.getProperty("password");

        Configuration.headless = true;
            open("https://account.jetbrains.com/login");
        $(By.id("username")).setValue(username);
        $(By.name("password")).setValue(password);
            $(By.xpath("//*[text() = 'Sign In']")).click();
            $(By.xpath("//*[text() = 'MYCOMPANY'][contains(@href, 'organization')]")).click();
            $(By.xpath("//*[contains(text(), 'Teams')]")).click();
            ElementsCollection teams = $$(By.xpath("//*[contains(@href, 'team')][ancestor::td/following-sibling::*[@data-order!=0]]"));
            teams.asDynamicIterable().stream().forEach(team -> {
                team.click();
                $(By.xpath("//*[text() = 'View licenses']")).click();
                val checkboxXpath = "//*[contains(@class, 'license-text')][descendant::*[contains(@class, 'icon-user')]]/descendant::*[@type='checkbox']";
                ElementsCollection licenses = $$(By.xpath(checkboxXpath));
                if (licenses.size()>1){
                    licenses.asDynamicIterable().stream()
                            .limit(licenses.size()-1).forEach(SelenideElement::click);
                    $(By.xpath("//*[text() = 'Manage']")).click();
                    $(By.xpath("//*[contains(@href, 'revoke')]")).click();
                    $(By.xpath("//*[@type ='submit'][contains(text(), 'Revoke')]")).click();
                }
                $(By.xpath("//*[contains(text(), 'Teams')]")).scrollIntoView(false).click();
              }
            );
        }
}
