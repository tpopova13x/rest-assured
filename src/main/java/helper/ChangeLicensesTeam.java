package helper;

import app.ApiBaseClass;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openapitools.client.ApiException;
import org.openapitools.client.ApiResponse;
import org.openapitools.client.model.ChangeTeamRequest;
import org.openapitools.client.model.LicenseResponse;
import org.openapitools.client.model.TeamResponse;

import java.util.ArrayList;
import java.util.List;

import static data.PropertiesSupplier.prop;
import static java.lang.Thread.currentThread;

public class ChangeLicensesTeam extends BaseHelper{
    private Log log = LogFactory.getLog(ChangeLicensesTeam.class);
    private ApiResponse<Void> call(ApiBaseClass api, Integer teamId, List<LicenseResponse> licenses,String xCustomerCode) throws ApiException {
        val idsToTransfer= licenses.stream()
                .map(LicenseResponse::getLicenseId)
                .toList();
        val changeTeamRequest = new ChangeTeamRequest();
        changeTeamRequest.setTargetTeamId(teamId);
        changeTeamRequest.setLicenseIds(new ArrayList<>(idsToTransfer));
        log.info(currentThread().getId() + ": changeTeamRequest \n" + changeTeamRequest);
        try {
            val apiResponse = api.getLicensesApi()
                    .changeTeamWithHttpInfo(changeTeamRequest, xCustomerCode);
            log.info(apiResponse.getStatusCode());
            log.info(apiResponse.getHeaders());
            log.info(apiResponse.getData());
            return apiResponse;
        } catch (ApiException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    public ApiResponse<Void> asOrgAdmin(TeamResponse team, List<LicenseResponse> licenses) throws ApiException {
        return call(apiOrgAdmin, team.getId(), licenses, prop.getProperty("x.customer.code"));
    }
    public ApiResponse<Void> asOrgAdmin(TeamResponse team, LicenseResponse license) throws ApiException {
        return call(apiOrgAdmin, team.getId(), new ArrayList<>(){{add(license);}}, prop.getProperty("x.customer.code"));
    }
    public ApiResponse<Void> asOrgAdmin(TeamResponse team, List<LicenseResponse> licenses, String xCustomerCode) throws ApiException {
        return call(apiOrgAdmin, team.getId(), licenses, xCustomerCode);
    }
    public ApiResponse<Void> asTeamAdmin(Integer teamId, LicenseResponse licenseResponse) throws ApiException {
        return call(apiTeamAdmin, teamId, new ArrayList<>(){{add(licenseResponse);}}, prop.getProperty("x.customer.code"));
    }
    public ApiResponse<Void> asTeamViewer(Integer teamId, LicenseResponse licenseResponse) throws ApiException {
        return call(apiTeamViewer, teamId, new ArrayList<>(){{add(licenseResponse);}}, prop.getProperty("x.customer.code"));
    }
}
