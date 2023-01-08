# DictionariesApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getPlugins**](DictionariesApi.md#getPlugins) | **GET** /plugins | Returns a plugins list |
| [**getProducts**](DictionariesApi.md#getProducts) | **GET** /products | Returns a products list |


<a name="getPlugins"></a>
# **getPlugins**
> List&lt;ProductResponse&gt; getPlugins()

Returns a plugins list

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DictionariesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/api/v1");
    
    // Configure API key authorization: header-api-key
    ApiKeyAuth header-api-key = (ApiKeyAuth) defaultClient.getAuthentication("header-api-key");
    header-api-key.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //header-api-key.setApiKeyPrefix("Token");

    DictionariesApi apiInstance = new DictionariesApi(defaultClient);
    try {
      List<ProductResponse> result = apiInstance.getPlugins();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DictionariesApi#getPlugins");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;ProductResponse&gt;**](ProductResponse.md)

### Authorization

[header-api-key](../README.md#header-api-key)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  -  |

<a name="getProducts"></a>
# **getProducts**
> List&lt;ProductResponse&gt; getProducts()

Returns a products list

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DictionariesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/api/v1");
    
    // Configure API key authorization: header-api-key
    ApiKeyAuth header-api-key = (ApiKeyAuth) defaultClient.getAuthentication("header-api-key");
    header-api-key.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //header-api-key.setApiKeyPrefix("Token");

    DictionariesApi apiInstance = new DictionariesApi(defaultClient);
    try {
      List<ProductResponse> result = apiInstance.getProducts();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DictionariesApi#getProducts");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;ProductResponse&gt;**](ProductResponse.md)

### Authorization

[header-api-key](../README.md#header-api-key)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  -  |

