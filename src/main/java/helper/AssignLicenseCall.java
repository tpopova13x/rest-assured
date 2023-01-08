package helper;

import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.openapitools.client.ApiException;
import org.openapitools.client.ApiResponse;
import org.openapitools.client.api.LicensesApi;
import org.openapitools.client.model.AssignLicenseRequest;
import org.openapitools.client.model.AssigneeContactRequest;
import org.openapitools.client.model.LicenseResponse;

import static data.PropertiesSupplier.prop;

public class AssignLicenseCall extends BaseHelper{
    private Log log = LogFactory.getLog(AssignLicenseCall.class);
    private  AssignLicenseRequest getAssignLicenseRequest(String licenseId, Integer teamId, String productCode, String firstName,
                                                         String lastName, String email) {
        val assignLicenseRequest = new AssignLicenseRequest();
        if (teamId!=null || productCode!=null) {
            val assignFromTeamRequest = new org.openapitools.client.model.AssignFromTeamRequest();
            if (teamId!=null) assignFromTeamRequest.setTeam(teamId);
            if (productCode!=null) assignFromTeamRequest.setProductCode(productCode);
            assignLicenseRequest.setLicense(assignFromTeamRequest);
        }
        if (licenseId!=null) {assignLicenseRequest.setLicenseId(licenseId);}

        val assigneeContactRequest = new AssigneeContactRequest();
        assigneeContactRequest.setFirstName(firstName);
        assigneeContactRequest.setLastName(lastName);
        assigneeContactRequest.setEmail(email);
        assignLicenseRequest.setContact(assigneeContactRequest);

        assignLicenseRequest.setSendEmail(false);
        assignLicenseRequest.setIncludeOfflineActivationCode(false);
        return assignLicenseRequest;
    }
    public AssignLicenseRequest getAssignLicenseRequest(LicenseResponse license) {
        return getAssignLicenseRequest(license, FIRST_NAME, LAST_NAME, EMAIL);
    }
    public AssignLicenseRequest getAssignLicenseRequest(LicenseResponse license, String firstName, String lastName, String email) {
        return getAssignLicenseRequest(license.getLicenseId(), license.getTeam().getId(), license.getProduct().getCode(),
                firstName, lastName, email);
    }
    public AssignLicenseRequest getAssignLicenseRequestNoLicenseId(LicenseResponse license) {
        return getAssignLicenseRequest(null, license.getTeam().getId(), license.getProduct().getCode(),
                FIRST_NAME, LAST_NAME, EMAIL);
    }
    public AssignLicenseRequest getAssignLicenseRequestNeitherLicenseNorLicenseId() {
        return getAssignLicenseRequest(null, null, null, FIRST_NAME, LAST_NAME, EMAIL);
    }
    @NotNull
    private ApiResponse<Void> getAssignLicense(LicensesApi api, AssignLicenseRequest assignLicenseRequest, String xCustomerCode) throws ApiException {
        log.info(assignLicenseRequest);
        try {
            ApiResponse<Void> apiResponse = api.assignLicenseWithHttpInfo(assignLicenseRequest, xCustomerCode);
            log.info(apiResponse.getStatusCode());
            log.info(apiResponse.getData());
            log.info(apiResponse.getHeaders());
            return apiResponse;
        } catch (ApiException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    public ApiResponse<Void> getAssignLicense(LicensesApi api, AssignLicenseRequest assignLicenseRequest) throws ApiException {
        return getAssignLicense(api, assignLicenseRequest, prop.getProperty("x.customer.code"));
    }
    public ApiResponse<Void> getAssignLicense(LicensesApi licensesApi, LicenseResponse licenseToAssign) throws ApiException {
        val assignLicenseRequest = getAssignLicenseRequestNoLicenseId(licenseToAssign);
        return getAssignLicense(licensesApi, assignLicenseRequest);
    }
    public ApiResponse<Void> getAssignLicenseAsTeamAdmin(LicenseResponse licenseToAssign) throws ApiException {
        val assignLicenseRequest = getAssignLicenseRequestNoLicenseId(licenseToAssign);
        return getAssignLicense(apiTeamAdmin.getLicensesApi(), assignLicenseRequest);
    }
    public ApiResponse<Void> getAssignLicenseAsTeamViewer(LicenseResponse licenseToAssign) throws ApiException {
        val assignLicenseRequest = getAssignLicenseRequestNoLicenseId(licenseToAssign);
        return getAssignLicense(apiTeamViewer.getLicensesApi(), assignLicenseRequest);
    }
    public ApiResponse<Void> getAssignLicenseAsOrgAdmin(AssignLicenseRequest assignLicenseRequest) throws ApiException {
        return getAssignLicense(apiOrgAdmin.getLicensesApi(), assignLicenseRequest);
    }
    public ApiResponse<Void> getAssignLicense(LicenseResponse licenseToAssign, String xCustomerCode) throws ApiException {
        val assignLicenseRequest = getAssignLicenseRequest(licenseToAssign);
        return getAssignLicense(apiOrgAdmin.getLicensesApi(), assignLicenseRequest, xCustomerCode);
    }
    public ApiResponse<Void> getAssignLicenseWithoutLicense(LicenseResponse licenseToAssign) throws ApiException {
        val assignLicenseRequest = getAssignLicenseRequest(licenseToAssign.getLicenseId(),
                null,null, FIRST_NAME, LAST_NAME, EMAIL);
        return getAssignLicenseAsOrgAdmin(assignLicenseRequest);
    }
    public ApiResponse<Void> getAssignLicenseWithoutLicenseId(LicenseResponse licenseToAssign) throws ApiException {
        val assignLicenseRequest = getAssignLicenseRequest(null,
                licenseToAssign.getTeam().getId(),licenseToAssign.getProduct().getCode(), FIRST_NAME, LAST_NAME, EMAIL);
        return getAssignLicenseAsOrgAdmin(assignLicenseRequest);
    }
    public ApiResponse<Void> getAssignLicenseNeitherLicenseNorLicenseId() throws ApiException {
        val assignLicenseRequest = getAssignLicenseRequestNeitherLicenseNorLicenseId();
        return getAssignLicenseAsOrgAdmin(assignLicenseRequest);
    }
    public ApiResponse<Void> getAssignLicenseToAssignee(LicenseResponse licenseToAssign, LicenseResponse licenseWithAssignee) throws ApiException {
        val names = licenseWithAssignee.getAssignee().getUser().getName().split("\s");
        val assignLicenseRequest = getAssignLicenseRequest(licenseToAssign, names[0], names[1],
                licenseWithAssignee.getAssignee().getUser().getEmail());
        return getAssignLicenseAsOrgAdmin(assignLicenseRequest);
    }
}
