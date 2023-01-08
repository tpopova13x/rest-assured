

# AssignLicenseRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**licenseId** | **String** | ID of a license to assign |  [optional] |
|**license** | [**AssignFromTeamRequest**](AssignFromTeamRequest.md) |  |  [optional] |
|**contact** | [**AssigneeContactRequest**](AssigneeContactRequest.md) |  |  |
|**sendEmail** | **Boolean** |  |  |
|**includeOfflineActivationCode** | **Boolean** | Indicates whether an offline activation code should be attached to the email. The offline activation code can be attached only for contacts that do not have JetBrains Account. &#39;includeOfflineActivationCode&#39; will be ignored if the contact has JetBrains Account. Assignee can download offline activation code in their JetBrains Account |  |



