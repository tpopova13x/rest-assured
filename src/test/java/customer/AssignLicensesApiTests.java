package customer;

import lombok.val;
import org.junit.jupiter.api.*;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.LicensesApi;

import static data.PropertiesSupplier.prop;
import static helper.PreconditionsAssignLicenses.revokeAllLicensesExceptOneInEveryTeam;

public class AssignLicensesApiTests extends BaseTest {

    @BeforeAll
    static void revokeLicenses() {revokeAllLicensesExceptOneInEveryTeam();}

    @AfterEach
    void dismissLicenses(){helper.getPostconditions().dismissLicensesAssignLicense();}

    @Test
    public void testWrongToken400() throws Exception {
        val apiClient = new ApiClient();
        apiClient.setApiKey(NOT_EXISTING_TOKEN_ORG);
        val licensesApi = new LicensesApi(apiClient);
        licensesApi.setCustomBaseUrl(prop.getProperty("custom.base.url"));
        val licenseToAssign = helper.getLicenseSupplierForAssignLicenseCall()
                .getLicense(true,true, true);

        val thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getAssignLicenseCall().getAssignLicense(licensesApi, licenseToAssign);
        }, "ApiException was expected");

        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_400);
        val notMyTeamLicenseAfterRequest = helper.getCustomerHelper()
                .getLicenseResponseById(licenseToAssign);
        softly.assertThat(notMyTeamLicenseAfterRequest.get(0)).isEqualTo(licenseToAssign);
    }
    @Test
    public void testXCustomerNotExists400() throws Exception {
        val licenseToAssign = helper.getLicenseSupplierForAssignLicenseCall().getLicense(true,true, true);

        val thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getAssignLicenseCall().getAssignLicense(licenseToAssign, NOT_EXISTING_CUSTOMER_CODE);
        }, "ApiException was expected");

        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_400);
        val notMyTeamLicenseAfterRequest = helper.getCustomerHelper()
                .getLicenseResponseById(licenseToAssign);
        softly.assertThat(notMyTeamLicenseAfterRequest.get(0)).isEqualTo(licenseToAssign);
    }
    @Test
    public void testLicenseIdValueNotExistsNoLicense400() throws Exception {
        val licenseToAssign = helper.getLicenseSupplierForAssignLicenseCall().getLicense(true,true, true);
        licenseToAssign.setLicenseId(NOT_EXISTING_LICENSE_ID);

        val thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getAssignLicenseCall().getAssignLicenseWithoutLicense(licenseToAssign);
        }, "ApiException was expected");

        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_400);
    }
    @Test
    public void testWrongEmailFormat400() throws Exception {
        val licenseToAssign = helper.getLicenseSupplierForAssignLicenseCall().getLicense(true,true, true);
        val assignLicenseRequest = helper.getAssignLicenseCall().getAssignLicenseRequest(licenseToAssign,FIRST_NAME, EMAIL, LAST_NAME);

        val thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getAssignLicenseCall().getAssignLicenseAsOrgAdmin(assignLicenseRequest);
        }, API_EXCEPTION_WAS_EXPECTED);

        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_400);
        val notMyTeamLicenseAfterRequest = helper.getCustomerHelper()
                .getLicenseResponseById(licenseToAssign);
        softly.assertThat(notMyTeamLicenseAfterRequest.get(0)).isEqualTo(licenseToAssign);
    }
    @Test
    public void testEmptyEmail400() throws Exception {
        val licenseToAssign = helper.getLicenseSupplierForAssignLicenseCall().getLicense(true,true, true);
        val assignLicenseRequest = helper.getAssignLicenseCall().getAssignLicenseRequest(licenseToAssign,FIRST_NAME,
                LAST_NAME, null);

        val thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getAssignLicenseCall().getAssignLicenseAsOrgAdmin(assignLicenseRequest);
        }, API_EXCEPTION_WAS_EXPECTED);

        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_400);
        val notMyTeamLicenseAfterRequest = helper.getCustomerHelper()
                .getLicenseResponseById(licenseToAssign);
        softly.assertThat(notMyTeamLicenseAfterRequest.get(0)).isEqualTo(licenseToAssign);
    }
    @Test
    public void testEmptyLastname400() throws Exception {
        val licenseToAssign = helper.getLicenseSupplierForAssignLicenseCall().getLicense(true,true, true);
        val assignLicenseRequest = helper.getAssignLicenseCall().getAssignLicenseRequest(licenseToAssign,FIRST_NAME,
                        null, EMAIL);

        val thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getAssignLicenseCall().getAssignLicenseAsOrgAdmin(assignLicenseRequest);
        }, API_EXCEPTION_WAS_EXPECTED);

        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_400);
        val notMyTeamLicenseAfterRequest = helper.getCustomerHelper()
                .getLicenseResponseById(licenseToAssign);
        softly.assertThat(notMyTeamLicenseAfterRequest.get(0)).isEqualTo(licenseToAssign);
    }
    @Test
    public void testEmptyFirstname400() throws Exception {
        val licenseToAssign = helper.getLicenseSupplierForAssignLicenseCall().getLicense(true,true, true);
        val assignLicenseRequest = helper.getAssignLicenseCall()
                .getAssignLicenseRequest(licenseToAssign,null, LAST_NAME, EMAIL);

        val thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getAssignLicenseCall().getAssignLicenseAsOrgAdmin(assignLicenseRequest);
        }, API_EXCEPTION_WAS_EXPECTED);

        softly.assertThat(thrown.getCode()).isEqualTo(400);
        val notMyTeamLicenseAfterRequest = helper.getCustomerHelper()
                .getLicenseResponseById(licenseToAssign);
        softly.assertThat(notMyTeamLicenseAfterRequest.get(0)).isEqualTo(licenseToAssign);
    }
    @Test
    public void testNotExistingProductCodeNoLicenseId400() throws Exception {
        val licenseToAssign = helper.getLicenseSupplierForAssignLicenseCall()
                .getLicense(true,true, true);
        licenseToAssign.getProduct().setCode(NOT_EXISTING_PRODUCT_CODE);

        val thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getAssignLicenseCall().getAssignLicenseWithoutLicenseId(licenseToAssign);
        }, API_EXCEPTION_WAS_EXPECTED);

        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_400);
        val notMyTeamLicenseAfterRequest = helper.getCustomerHelper()
                .getLicenseResponseById(licenseToAssign);
        softly.assertThat(notMyTeamLicenseAfterRequest.get(0)).isEqualTo(licenseToAssign);
    }
    @Test
    public void testNotExistingTeamNoLicenseId400() throws Exception {
        val licenseToAssign = helper.getLicenseSupplierForAssignLicenseCall()
                .getLicense(true,true, true);
        licenseToAssign.getTeam().setId(NOT_EXISTING_TEAM_ID);

        val thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getAssignLicenseCall().getAssignLicenseWithoutLicenseId(licenseToAssign);
        }, API_EXCEPTION_WAS_EXPECTED);

        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_400);
        val notMyTeamLicenseAfterRequest = helper.getCustomerHelper()
                .getLicenseResponseById(licenseToAssign);
        softly.assertThat(notMyTeamLicenseAfterRequest.get(0)).isEqualTo(licenseToAssign);
    }

    @Test
    public void testAssignLicenseFromAnotherTeam403() throws Exception {
        val notMyTeamLicense = helper.getLicenseSupplierForAssignLicenseCall()
                .getLicense(true,false, true);

        val thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getAssignLicenseCall().getAssignLicenseAsTeamAdmin(notMyTeamLicense);
        }, API_EXCEPTION_WAS_EXPECTED);

        softly.assertThat(thrown.getCode()).isEqualTo(403);
        val notMyTeamLicenseAfterRequest = helper.getCustomerHelper()
                .getLicenseResponseById(notMyTeamLicense);
        softly.assertThat(notMyTeamLicenseAfterRequest.get(0)).isEqualTo(notMyTeamLicense);
    }

    @Test
    public void testLicencesAreNotOverwritten200() throws Exception {
            val licenseWithAssignee = helper.getLicenseSupplierForAssignLicenseCall()
                    .getLicense(false, true, true);
            val licenseToAssignWebstorm = helper.getLicenseSupplierForAssignLicenseCall()
                    .getLicense(true, true, true);

            val response = helper.getAssignLicenseCall()
                    .getAssignLicenseToAssignee(licenseToAssignWebstorm, licenseWithAssignee);
            softly.assertThat(response.getStatusCode()).isEqualTo(RESPONSE_CODE_200);

            val licenseWithAssigneeAfter = helper.getCustomerHelper()
                    .getLicenseResponseById(licenseWithAssignee);
            val licenseToAssignWebstormAfter = helper.getCustomerHelper()
                    .getLicenseResponseById(licenseToAssignWebstorm);

            softly.assertThat(licenseWithAssigneeAfter.get(0).getAssignee()).isEqualTo(licenseWithAssignee.getAssignee());
            softly.assertThat(licenseToAssignWebstormAfter.get(0).getAssignee()).isEqualTo(licenseWithAssignee.getAssignee());
        }

    @Test
    public void testAssignLicenseAsViewer403() throws Exception {
        val license = helper.getLicenseSupplierForAssignLicenseCall()
                .getLicense(true, true, true);

        val thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getAssignLicenseCall().getAssignLicenseAsTeamViewer(license);
        }, "ApiException was expected: Viewer and Primary contact access tokens allow read-only access to the API: " +
                "https://sales.jetbrains.com/hc/en-gb/articles/4406854152978-JetBrains-Account-API");

        softly.assertThat(thrown.getCode()).isEqualTo(403);
        val licenseAfter = helper.getCustomerHelper().getLicenseResponseById(license);
        softly.assertThat(license).isEqualTo(licenseAfter);
    }
    @Test
    public void testEitherLicenseIdOrLicenseMustBeProvided400() {
        val thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getAssignLicenseCall().getAssignLicenseNeitherLicenseNorLicenseId();
        }, API_EXCEPTION_WAS_EXPECTED);
        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_400);
    }
    @Test
    public void testNoLicense200() throws ApiException {
        val licenseToAssign = helper.getLicenseSupplierForAssignLicenseCall()
                .getLicense(true,true, true);

        val response = helper.getAssignLicenseCall().getAssignLicenseWithoutLicense(licenseToAssign);
        softly.assertThat(response.getStatusCode()).isEqualTo(RESPONSE_CODE_200);
    }
    @Test
    public void testNoLicenseId200() throws ApiException {
        val licenseToAssign = helper.getLicenseSupplierForAssignLicenseCall()
                .getLicense(true,true, true);

        val response = helper.getAssignLicenseCall().getAssignLicenseWithoutLicenseId(licenseToAssign);
        softly.assertThat(response.getStatusCode()).isEqualTo(RESPONSE_CODE_200);
    }
    @Test
    public void testNoAvailableLicenseToAssign400() throws Exception {
        val licenseToAssign = helper.getLicenseSupplierForAssignLicenseCall()
                .getLicense(true, true, true);
        val code = helper.getPreconditionsAssignLicenses().getProductCodeNotInTeam();
        licenseToAssign.getProduct().setCode(code);

        val thrown = Assertions.assertThrows(ApiException.class, () -> {
            helper.getAssignLicenseCall().getAssignLicenseWithoutLicenseId(licenseToAssign);
        }, API_EXCEPTION_WAS_EXPECTED);
        softly.assertThat(thrown.getCode()).isEqualTo(RESPONSE_CODE_400);
    }
}