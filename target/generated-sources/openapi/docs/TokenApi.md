# TokenApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getManagedEntityInfo**](TokenApi.md#getManagedEntityInfo) | **GET** /token | Returns info about the entity managed by current token |


<a name="getManagedEntityInfo"></a>
# **getManagedEntityInfo**
> GetManagedEntityInfo200Response getManagedEntityInfo(xCustomerCode)

Returns info about the entity managed by current token

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TokenApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/api/v1");
    
    // Configure API key authorization: header-api-key
    ApiKeyAuth header-api-key = (ApiKeyAuth) defaultClient.getAuthentication("header-api-key");
    header-api-key.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //header-api-key.setApiKeyPrefix("Token");

    TokenApi apiInstance = new TokenApi(defaultClient);
    String xCustomerCode = "xCustomerCode_example"; // String | Company Profile ID, it can be found on JetBrains Account portal near the company name
    try {
      GetManagedEntityInfo200Response result = apiInstance.getManagedEntityInfo(xCustomerCode);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TokenApi#getManagedEntityInfo");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **xCustomerCode** | **String**| Company Profile ID, it can be found on JetBrains Account portal near the company name | [optional] |

### Return type

[**GetManagedEntityInfo200Response**](GetManagedEntityInfo200Response.md)

### Authorization

[header-api-key](../README.md#header-api-key)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  -  |

