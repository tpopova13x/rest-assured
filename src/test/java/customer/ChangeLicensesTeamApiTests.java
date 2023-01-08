package customer;

import lombok.val;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.LicensesApi;
import org.openapitools.client.model.ChangeTeamRequest;
import org.openapitools.client.model.LicenseResponse;

import java.util.ArrayList;

import static data.PropertiesSupplier.prop;
import static java.lang.Thread.currentThread;
public class ChangeLicensesTeamApiTests extends BaseTest {
    private final Log log = LogFactory.getLog(ChangeLicensesTeamApiTests.class);

    @AfterEach
    void dismissLicenses(){
        helper.getPostconditions().dismissLicensesChangeTeam();
    }

    @Test
    public void testOneLicenseIdWithoutAssignee200() throws Exception {
        val licensesToTransfer = helper.getLicenseSupplierForChangeTeamCall()
                .fromOneTeam(true,false,1);
        val teamToTransfer = helper.getPreconditionsChangeTeam().getTeamWithoutLicenses(licensesToTransfer);

        val response = helper.getChangeLicensesTeam().asOrgAdmin(teamToTransfer, licensesToTransfer);

        softly.assertThat(response.getStatusCode()).isEqualTo(RESPONSE_CODE_200);
        val expectedLicenses = helper.getCustomerHelper().setTeamForLicenses(licensesToTransfer, teamToTransfer);
        val newLicenseResponse = helper.getCustomerHelper().getLicenseResponseById(licensesToTransfer);
        softly.assertThat(newLicenseResponse).isEqualTo(expectedLicenses);
    }

    @Test
    public void testOneLicenseIdWithAssignee200() throws Exception {
        val licensesToTransfer = helper.getLicenseSupplierForChangeTeamCall()
                .fromDifferentTeams(true, true, 1);
        val teamToTransfer = helper.getPreconditionsChangeTeam().getTeamWithoutLicenses(licensesToTransfer);

        val response = helper.getChangeLicensesTeam().asOrgAdmin(teamToTransfer, licensesToTransfer);

        softly.assertThat(response.getStatusCode()).isEqualTo(RESPONSE_CODE_200);
        val expectedLicenses = helper.getCustomerHelper().setTeamForLicenses(licensesToTransfer, teamToTransfer);
        val newLicenseResponse = helper.getCustomerHelper().getLicenseResponseById(licensesToTransfer);
        softly.assertThat(newLicenseResponse).isEqualTo(expectedLicenses);
    }

    @Test
    public void testSeveralLicensesFromOneTeam200() throws Exception {
        val licenses = helper.getLicenseSupplierForChangeTeamCall()
                .fromOneTeam(true, null, 2);
        val teamResponse = helper.getPreconditionsChangeTeam().getTeamWithoutLicenses(licenses);

        val response = helper.getChangeLicensesTeam().asOrgAdmin(teamResponse, licenses);

        softly.assertThat(response.getStatusCode()).isEqualTo(RESPONSE_CODE_200);
        val expectedLicenses = helper.getCustomerHelper().setTeamForLicenses(licenses, teamResponse);
        val newLicenses = helper.getCustomerHelper().getLicenseResponseById(licenses);
        softly.assertThat(newLicenses).isEqualTo(expectedLicenses);
    }

    @Test
    public void testSeveralLicensesFromDifferentTeams200() throws Exception {
        val licenses = helper.getLicenseSupplierForChangeTeamCall()
                .fromDifferentTeams(true, false, 2);
        val teamResponse = helper.getPreconditionsChangeTeam().getTeamWithoutLicenses(licenses);

        val response = helper.getChangeLicensesTeam().asOrgAdmin(teamResponse, licenses);

        softly.assertThat(response.getStatusCode()).isEqualTo(RESPONSE_CODE_200);
        val expectedLicenses = helper.getCustomerHelper().setTeamForLicenses(licenses, teamResponse);
        val newLicenses = helper.getCustomerHelper().getLicenseResponseById(licenses);
        softly.assertThat(newLicenses).isEqualTo(expectedLicenses);
    }

    @Test
    public void testMaxNumberLicensesTeams200() throws Exception {
        val licensesToTransfer = helper.getLicenseSupplierForChangeTeamCall()
                .fromOneTeam(true, null, 10);
        val teamToTransfer = helper.getPreconditionsChangeTeam().getTeamWithoutLicenses(licensesToTransfer);

        val response = helper.getChangeLicensesTeam().asOrgAdmin(teamToTransfer, licensesToTransfer);

        softly.assertThat(response.getStatusCode()).isEqualTo(RESPONSE_CODE_200);
        val expectedLicenses = helper.getCustomerHelper().setTeamForLicenses(licensesToTransfer, teamToTransfer);
        val newLicenseResponse = helper.getCustomerHelper().getLicenseResponseById(licensesToTransfer);
        softly.assertThat(newLicenseResponse).isEqualTo(expectedLicenses);
    }

    @Test
    public void testTargetTeamNotExists400() throws Exception {
        val licenseToTransfer = helper.getLicenseSupplierForChangeTeamCall().fromOneTeam(true, false, 1);
        val teamToTransfer = helper.getPreconditionsChangeTeam().getTeamWithoutLicenses(licenseToTransfer);
        teamToTransfer.setId(NOT_EXISTING_TEAM_ID);

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getChangeLicensesTeam().asOrgAdmin(teamToTransfer, licenseToTransfer);
        }, API_EXCEPTION_WAS_EXPECTED);
        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_400);
    }

    @Test
    public void testTargetTeamIsNull400() throws Exception {
        val licensesToTransfer = helper.getLicenseSupplierForChangeTeamCall()
                .fromOneTeam(true, null, 1);
        val teamToTransfer = helper.getPreconditionsChangeTeam().getTeamWithoutLicenses(licensesToTransfer);
        teamToTransfer.setId(NULL_INTEGER_VALUE);

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getChangeLicensesTeam().asOrgAdmin(teamToTransfer, licensesToTransfer);
        }, API_EXCEPTION_WAS_EXPECTED);
        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_400);
    }

    @Test
    public void testXCustomerCodeNotExists400() throws Exception {
        val licensesToTransfer = helper.getLicenseSupplierForChangeTeamCall()
                .fromOneTeam(true, false,1);
        val teamToTransfer = helper.getPreconditionsChangeTeam().getTeamWithoutLicenses(licensesToTransfer);

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getChangeLicensesTeam().asOrgAdmin(teamToTransfer, licensesToTransfer, NOT_EXISTING_CUSTOMER_CODE);
        }, API_EXCEPTION_WAS_EXPECTED);

        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_400);
        softly.assertThat(thrown.getResponseBody()).contains("Customer");
    }

    @Test
    public void testLicenseIdValueNotExists200() throws Exception {
        val licensesToTransfer = helper.getLicenseSupplierForChangeTeamCall()
                .fromOneTeam(true,false,2);
        val teamToTransfer = helper.getPreconditionsChangeTeam().getTeamWithoutLicenses(licensesToTransfer);
        licensesToTransfer.get(0).setLicenseId(NOT_EXISTING_LICENSE_ID);

        val response = helper.getChangeLicensesTeam().asOrgAdmin(teamToTransfer, licensesToTransfer);

        softly.assertThat(response.getStatusCode()).isEqualTo(RESPONSE_CODE_200);
        licensesToTransfer.get(1).setTeam(teamToTransfer);
        val expectedLicensesToTransfer = new ArrayList<LicenseResponse>(){{add(licensesToTransfer.get(1));}};
        val newLicenseResponse = helper.getCustomerHelper().getLicenseResponseById(expectedLicensesToTransfer);
        softly.assertThat(newLicenseResponse).isEqualTo(expectedLicensesToTransfer);
    }

    @Test
    public void testLicenseIdAlreadyBelongsToTheTeam200() throws Exception {
        val licensesToTransfer = helper.getLicenseSupplierForChangeTeamCall()
                .fromDifferentTeams(true, false, 2);
        val teamToTransfer = licensesToTransfer.get(0).getTeam();

        val response = helper.getChangeLicensesTeam().asOrgAdmin(teamToTransfer, licensesToTransfer);

        softly.assertThat(response.getStatusCode()).isEqualTo(RESPONSE_CODE_200);
        licensesToTransfer.get(1).setTeam(teamToTransfer);
        val newLicenseResponse = helper.getCustomerHelper().getLicenseResponseById(licensesToTransfer);
        softly.assertThat(newLicenseResponse).isEqualTo(licensesToTransfer);
    }

    @Test
    public void testLicenseIdIsNull400() throws Exception {
        val licensesToTransfer = helper.getLicenseSupplierForChangeTeamCall()
                .fromOneTeam(true, false, 2);
        val teamToTransfer = helper.getPreconditionsChangeTeam().getTeamWithoutLicenses(licensesToTransfer);
        licensesToTransfer.get(0).setLicenseId(NULL_STRING_VALUE);

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getChangeLicensesTeam().asOrgAdmin(teamToTransfer, licensesToTransfer);
        }, API_EXCEPTION_WAS_EXPECTED);

        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_400);
        val expectedLicensesToTransfer = new ArrayList<LicenseResponse>(){{add(licensesToTransfer.get(1));}};
        val newLicenseResponse = helper.getCustomerHelper().getLicenseResponseById(expectedLicensesToTransfer);
        softly.assertThat(newLicenseResponse).isEqualTo(expectedLicensesToTransfer);
    }

    @Test
    public void testLicenseIdArrayIsEmpty200() throws Exception {
        val licenseToTransfer = new LicenseResponse();
        val teamToTransfer = helper.getPreconditionsChangeTeam().getTeamResponse().get(0);
        licenseToTransfer.setLicenseId(EMPTY_STRING_VALUE);

        val response = helper.getChangeLicensesTeam().asOrgAdmin(teamToTransfer, licenseToTransfer);

        softly.assertThat(response.getStatusCode()).isEqualTo(RESPONSE_CODE_200);
    }

    @Test
    public void testWrongToken400() throws Exception {
        ApiClient apiClient = new ApiClient();
        apiClient.setApiKey(NOT_EXISTING_TOKEN_ORG);
        LicensesApi licensesApi = new LicensesApi(apiClient);
        licensesApi.setCustomBaseUrl(prop.getProperty("custom.base.url"));

        val licenseToTransfer = helper.getLicenseSupplierForChangeTeamCall()
                .fromOneTeam(true,false,1);
        val teamToTransfer = helper.getPreconditionsChangeTeam().getTeamWithoutLicenses(licenseToTransfer);
        val changeTeamRequest = new ChangeTeamRequest();
        changeTeamRequest.setTargetTeamId(teamToTransfer.getId());
        changeTeamRequest.setLicenseIds(licenseToTransfer.stream().map(l->l.getLicenseId()).toList());
        log.info(currentThread().getId() + ": changeTeamRequest \n" + changeTeamRequest);
        
        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            licensesApi.changeTeamWithHttpInfo(changeTeamRequest,prop.getProperty("x.customer.code"));
        }, API_EXCEPTION_WAS_EXPECTED);
        log.info(thrown.getMessage());
        
        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_400);
        val licenseToTransferAfter = helper.getCustomerHelper()
                .getLicenseResponseById(licenseToTransfer);
        softly.assertThat(licenseToTransferAfter).isEqualTo(licenseToTransfer);
    }

    @Test
    public void testChangeForeignLicenseTeamAsTeamAdmin403() throws Exception {
        val notMyTeamLicense = helper.getLicenseSupplierForChangeTeamCall()
                .fromTeam(false,true,null);
        val myTeamId = helper.getPreconditionsChangeTeam().getMyTeamId();

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getChangeLicensesTeam().asTeamAdmin(myTeamId, notMyTeamLicense);
        }, API_EXCEPTION_WAS_EXPECTED);

        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_403);
        val notMyTeamLicenseAfterRequest = helper.getCustomerHelper()
                .getLicenseResponseById(notMyTeamLicense);
        softly.assertThat(notMyTeamLicense).isEqualTo(notMyTeamLicenseAfterRequest.get(0));
    }

    @Test
    public void testChangeMyTeamLicenseAsTeamAdmin403() throws Exception {
        val myTeamLicense = helper.getLicenseSupplierForChangeTeamCall()
                .fromTeam(true,true,null);
        val teamIdToTransfer = helper.getPreconditionsChangeTeam()
                .getTeamWithoutLicenses(myTeamLicense).getId();

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getChangeLicensesTeam().asTeamAdmin(teamIdToTransfer, myTeamLicense);
        }, API_EXCEPTION_WAS_EXPECTED);

        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_403);
        val myTeamLicenseAfterRequest = helper.getCustomerHelper().getLicenseResponseById(myTeamLicense);
        softly.assertThat(myTeamLicense).isEqualTo(myTeamLicenseAfterRequest.get(0));
    }

    @Test
    public void testChangeMyTeamLicenseAsViewer403() throws Exception {
        val myTeamLicense = helper.getLicenseSupplierForChangeTeamCall()
                .fromTeam(true,true,null);
        val teamIdToTransfer = helper.getPreconditionsChangeTeam().getTeamWithoutLicenses(myTeamLicense).getId();

        ApiException thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getChangeLicensesTeam().asTeamViewer(teamIdToTransfer, myTeamLicense);
        }, API_EXCEPTION_WAS_EXPECTED);

        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_403);
        val myTeamLicenseAfterRequest = helper.getCustomerHelper().getLicenseResponseById(myTeamLicense);
        softly.assertThat(myTeamLicense).isEqualTo(myTeamLicenseAfterRequest.get(0));

    }
}
