package app;

import lombok.Data;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.DictionariesApi;
import org.openapitools.client.api.LicensesApi;
import org.openapitools.client.api.TokenApi;

import static data.PropertiesSupplier.prop;
@Data
public class ApiBaseClass {
    private ApiClient apiClient;
    private LicensesApi licensesApi;
    private TokenApi tokenApi;
    private DictionariesApi dictionariesApi;
    ApiBaseClass(String token) {
        apiClient = new ApiClient();
        apiClient.setApiKey(prop.getProperty(token));
        String customBaseUrl = prop.getProperty("custom.base.url");
        licensesApi = new LicensesApi(apiClient);
        licensesApi.setCustomBaseUrl(customBaseUrl);
        tokenApi = new TokenApi(apiClient);
        tokenApi.setCustomBaseUrl(customBaseUrl);
         dictionariesApi = new DictionariesApi(apiClient);
         dictionariesApi.setCustomBaseUrl(customBaseUrl);
    }
}
