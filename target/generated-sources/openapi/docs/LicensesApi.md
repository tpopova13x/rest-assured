# LicensesApi

All URIs are relative to */api/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**assignLicense**](LicensesApi.md#assignLicense) | **POST** /customer/licenses/assign | Assigns a license to a user |
| [**changeTeam**](LicensesApi.md#changeTeam) | **POST** /customer/changeLicensesTeam | Change team for licenses |
| [**getAllForCustomer**](LicensesApi.md#getAllForCustomer) | **GET** /customer/licenses | Returns all the licenses of a current customer |
| [**getAllForTeam**](LicensesApi.md#getAllForTeam) | **GET** /customer/teams/{teamId}/licenses | Returns all the licenses of a current team |
| [**getByLicenseId**](LicensesApi.md#getByLicenseId) | **GET** /customer/licenses/{licenseId} | Returns a license by ID |
| [**revokeAsset**](LicensesApi.md#revokeAsset) | **POST** /customer/licenses/revoke | Revokes a license from a user |


<a name="assignLicense"></a>
# **assignLicense**
> assignLicense(assignLicenseRequest, xCustomerCode)

Assigns a license to a user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.LicensesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/api/v1");
    
    // Configure API key authorization: header-api-key
    ApiKeyAuth header-api-key = (ApiKeyAuth) defaultClient.getAuthentication("header-api-key");
    header-api-key.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //header-api-key.setApiKeyPrefix("Token");

    LicensesApi apiInstance = new LicensesApi(defaultClient);
    AssignLicenseRequest assignLicenseRequest = new AssignLicenseRequest(); // AssignLicenseRequest | 
    String xCustomerCode = "xCustomerCode_example"; // String | Company Profile ID, it can be found on JetBrains Account portal near the company name
    try {
      apiInstance.assignLicense(assignLicenseRequest, xCustomerCode);
    } catch (ApiException e) {
      System.err.println("Exception when calling LicensesApi#assignLicense");
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
| **assignLicenseRequest** | [**AssignLicenseRequest**](AssignLicenseRequest.md)|  | |
| **xCustomerCode** | **String**| Company Profile ID, it can be found on JetBrains Account portal near the company name | [optional] |

### Return type

null (empty response body)

### Authorization

[header-api-key](../README.md#header-api-key)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  -  |
| **400** | bad request |  -  |
| **403** | forbidden |  -  |

<a name="changeTeam"></a>
# **changeTeam**
> changeTeam(changeTeamRequest, xCustomerCode)

Change team for licenses

Changes team for provided licenses. Only licenses that are transferable between teams will be transferred.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.LicensesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/api/v1");
    
    // Configure API key authorization: header-api-key
    ApiKeyAuth header-api-key = (ApiKeyAuth) defaultClient.getAuthentication("header-api-key");
    header-api-key.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //header-api-key.setApiKeyPrefix("Token");

    LicensesApi apiInstance = new LicensesApi(defaultClient);
    ChangeTeamRequest changeTeamRequest = new ChangeTeamRequest(); // ChangeTeamRequest | 
    String xCustomerCode = "xCustomerCode_example"; // String | Company Profile ID, it can be found on JetBrains Account portal near the company name
    try {
      apiInstance.changeTeam(changeTeamRequest, xCustomerCode);
    } catch (ApiException e) {
      System.err.println("Exception when calling LicensesApi#changeTeam");
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
| **changeTeamRequest** | [**ChangeTeamRequest**](ChangeTeamRequest.md)|  | |
| **xCustomerCode** | **String**| Company Profile ID, it can be found on JetBrains Account portal near the company name | [optional] |

### Return type

null (empty response body)

### Authorization

[header-api-key](../README.md#header-api-key)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  -  |
| **400** | bad request |  -  |
| **403** | forbidden |  -  |

<a name="getAllForCustomer"></a>
# **getAllForCustomer**
> List&lt;LicenseResponse&gt; getAllForCustomer(xCustomerCode, productCode, page, perPage, assigned, assigneeEmail)

Returns all the licenses of a current customer

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.LicensesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/api/v1");
    
    // Configure API key authorization: header-api-key
    ApiKeyAuth header-api-key = (ApiKeyAuth) defaultClient.getAuthentication("header-api-key");
    header-api-key.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //header-api-key.setApiKeyPrefix("Token");

    LicensesApi apiInstance = new LicensesApi(defaultClient);
    String xCustomerCode = "xCustomerCode_example"; // String | Company Profile ID, it can be found on JetBrains Account portal near the company name
    String productCode = "null"; // String | Product code to filter licenses
    Integer page = 1; // Integer | Current page number (page numbering starts with 1)
    Integer perPage = 100; // Integer | The number of items to return per page
    Boolean assigned = false; // Boolean | Fetches only assigned licenses if set to true and unassigned is set to false. If set to null all licenses will be fetched.
    String assigneeEmail = "null"; // String | Assignee email to filter licenses
    try {
      List<LicenseResponse> result = apiInstance.getAllForCustomer(xCustomerCode, productCode, page, perPage, assigned, assigneeEmail);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LicensesApi#getAllForCustomer");
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
| **productCode** | **String**| Product code to filter licenses | [optional] [default to null] |
| **page** | **Integer**| Current page number (page numbering starts with 1) | [optional] [default to 1] |
| **perPage** | **Integer**| The number of items to return per page | [optional] [default to 100] |
| **assigned** | **Boolean**| Fetches only assigned licenses if set to true and unassigned is set to false. If set to null all licenses will be fetched. | [optional] [default to false] |
| **assigneeEmail** | **String**| Assignee email to filter licenses | [optional] [default to null] |

### Return type

[**List&lt;LicenseResponse&gt;**](LicenseResponse.md)

### Authorization

[header-api-key](../README.md#header-api-key)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  * X-Total-Count - The total entries <br>  * Link - Links for pagination <br>  |

<a name="getAllForTeam"></a>
# **getAllForTeam**
> List&lt;LicenseResponse&gt; getAllForTeam(teamId, xCustomerCode, productCode, page, perPage, assigned, assigneeEmail)

Returns all the licenses of a current team

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.LicensesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/api/v1");
    
    // Configure API key authorization: header-api-key
    ApiKeyAuth header-api-key = (ApiKeyAuth) defaultClient.getAuthentication("header-api-key");
    header-api-key.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //header-api-key.setApiKeyPrefix("Token");

    LicensesApi apiInstance = new LicensesApi(defaultClient);
    Integer teamId = 1; // Integer | Team ID
    String xCustomerCode = "xCustomerCode_example"; // String | Company Profile ID, it can be found on JetBrains Account portal near the company name
    String productCode = "null"; // String | Product code to filter licenses
    Integer page = 1; // Integer | Current page number (page numbering starts with 1)
    Integer perPage = 100; // Integer | The number of items to return per page
    Boolean assigned = false; // Boolean | Fetches only assigned licenses if set to true and unassigned is set to false. If set to null all licenses will be fetched.
    String assigneeEmail = "null"; // String | Assignee email to filter licenses
    try {
      List<LicenseResponse> result = apiInstance.getAllForTeam(teamId, xCustomerCode, productCode, page, perPage, assigned, assigneeEmail);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LicensesApi#getAllForTeam");
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
| **teamId** | **Integer**| Team ID | [default to 1] |
| **xCustomerCode** | **String**| Company Profile ID, it can be found on JetBrains Account portal near the company name | [optional] |
| **productCode** | **String**| Product code to filter licenses | [optional] [default to null] |
| **page** | **Integer**| Current page number (page numbering starts with 1) | [optional] [default to 1] |
| **perPage** | **Integer**| The number of items to return per page | [optional] [default to 100] |
| **assigned** | **Boolean**| Fetches only assigned licenses if set to true and unassigned is set to false. If set to null all licenses will be fetched. | [optional] [default to false] |
| **assigneeEmail** | **String**| Assignee email to filter licenses | [optional] [default to null] |

### Return type

[**List&lt;LicenseResponse&gt;**](LicenseResponse.md)

### Authorization

[header-api-key](../README.md#header-api-key)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  * X-Total-Count - The total entries <br>  * Link - Links for pagination <br>  |

<a name="getByLicenseId"></a>
# **getByLicenseId**
> LicenseResponse getByLicenseId(licenseId, xCustomerCode)

Returns a license by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.LicensesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/api/v1");
    
    // Configure API key authorization: header-api-key
    ApiKeyAuth header-api-key = (ApiKeyAuth) defaultClient.getAuthentication("header-api-key");
    header-api-key.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //header-api-key.setApiKeyPrefix("Token");

    LicensesApi apiInstance = new LicensesApi(defaultClient);
    String licenseId = "ABC1234567"; // String | ID of a license
    String xCustomerCode = "xCustomerCode_example"; // String | Company Profile ID, it can be found on JetBrains Account portal near the company name
    try {
      LicenseResponse result = apiInstance.getByLicenseId(licenseId, xCustomerCode);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LicensesApi#getByLicenseId");
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
| **licenseId** | **String**| ID of a license | |
| **xCustomerCode** | **String**| Company Profile ID, it can be found on JetBrains Account portal near the company name | [optional] |

### Return type

[**LicenseResponse**](LicenseResponse.md)

### Authorization

[header-api-key](../README.md#header-api-key)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | successful operation |  -  |

<a name="revokeAsset"></a>
# **revokeAsset**
> RevokedLicensesResponse revokeAsset(xCustomerCode, licenseId, email, productCode)

Revokes a license from a user

If licenseId is provided, a single license will be revoked if found. Other parameters are ignored in this case. &lt;br /&gt;If he licenseId is not provided then the licenses will be revoked by the assignee&#39;s email. &lt;br /&gt;In addition to email, productCode might be provided to revoke licenses for specified products only for the defined email. &lt;br /&gt;JetBrains IDE licenses are distributed on a per-user basis and must be statically assigned to developers. Therefore, the revoke operation via the JetBrains Account API is available only 30 days after the license has been assigned. For the automated license distribution you can use the JetBrains License Vault: https://www.jetbrains.com/license-server &lt;br /&gt;If the licenses are being revoked by assignee&#39;s email within 30 days after assignment (for at least 1 assignee&#39;s license found), RECENTLY_ASSIGNED_LICENSE_IS_NOT_AVAILABLE_FOR_REVOKE error will be returned and no licenses will be revoked.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.LicensesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/api/v1");
    
    // Configure API key authorization: header-api-key
    ApiKeyAuth header-api-key = (ApiKeyAuth) defaultClient.getAuthentication("header-api-key");
    header-api-key.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //header-api-key.setApiKeyPrefix("Token");

    LicensesApi apiInstance = new LicensesApi(defaultClient);
    String xCustomerCode = "xCustomerCode_example"; // String | Company Profile ID, it can be found on JetBrains Account portal near the company name
    String licenseId = "ABC1234567"; // String | ID of a license
    String email = "example@domain.com"; // String | Assignee's email
    String productCode = "II"; // String | Product code to filter licenses
    try {
      RevokedLicensesResponse result = apiInstance.revokeAsset(xCustomerCode, licenseId, email, productCode);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LicensesApi#revokeAsset");
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
| **licenseId** | **String**| ID of a license | [optional] |
| **email** | **String**| Assignee&#39;s email | [optional] |
| **productCode** | **String**| Product code to filter licenses | [optional] |

### Return type

[**RevokedLicensesResponse**](RevokedLicensesResponse.md)

### Authorization

[header-api-key](../README.md#header-api-key)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | licenseIds of revoked licenses |  -  |
| **400** | bad request |  -  |
| **403** | forbidden |  -  |

