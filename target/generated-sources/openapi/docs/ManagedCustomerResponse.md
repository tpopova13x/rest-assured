

# ManagedCustomerResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | **ManagedEntityType** |  |  [optional] |
|**code** | **Long** | Code of the managed customer. Use this code for API requests |  [optional] |
|**name** | **String** | Name of the managed customer. |  [optional] |
|**role** | [**RoleEnum**](#RoleEnum) | The role of current account on the customer. |  [optional] |
|**contactFlags** | [**ContactFlagsResponse**](ContactFlagsResponse.md) |  |  [optional] |
|**teams** | [**List&lt;TeamResponseExt&gt;**](TeamResponseExt.md) | List of customer teams. |  [optional] |



## Enum: RoleEnum

| Name | Value |
|---- | -----|
| ADMIN | &quot;ADMIN&quot; |
| VIEWER | &quot;VIEWER&quot; |
| PRIMARY_CONTACT | &quot;PRIMARY_CONTACT&quot; |



