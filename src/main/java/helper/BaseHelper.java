package helper;

import app.ApiOrgAdmin;
import app.ApiTeamAdmin;
import app.ApiTeamViewer;
import blocked.licenses.BlockedLicenseIdsAssignLicense;
import blocked.licenses.BlockedLicenseIdsChangeTeam;
import data.TestData;
import lombok.val;
import org.jetbrains.annotations.Nullable;
import org.openapitools.client.ApiException;
import org.openapitools.client.ApiResponse;
import org.openapitools.client.model.LicenseResponse;
import org.openapitools.client.model.TeamResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static data.PropertiesSupplier.prop;

public class BaseHelper implements TestData {
    private static Integer myTeamId;
    private static final Object block = new Object();
    ApiOrgAdmin apiOrgAdmin = ApiOrgAdmin.getInstance();
    ApiTeamAdmin apiTeamAdmin = ApiTeamAdmin.getInstance();
    ApiTeamViewer apiTeamViewer = ApiTeamViewer.getInstance();
    BlockedLicenseIdsAssignLicense blockedLicenseIdsAssignLicense = BlockedLicenseIdsAssignLicense.getInstance();
    BlockedLicenseIdsChangeTeam blockedLicenseIdsChangeTeam = BlockedLicenseIdsChangeTeam.getInstance();

    List<LicenseResponse> getAllForCustomerAsOrgAdmin() throws ApiException {
        val licenseResponses = apiOrgAdmin.getLicensesApi().getAllForCustomer(
                prop.getProperty("x.customer.code"), null, null,null,null,null);
        Collections.shuffle(licenseResponses, new Random(LocalDateTime.now().atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli()));
        return licenseResponses;
    }

    public ApiResponse<List<LicenseResponse>> getTeamLicenses(TeamResponse teamResponse) throws ApiException {
        return  getTeamLicenses(teamResponse.getId());
    }
    public ApiResponse<List<LicenseResponse>> getTeamLicenses(Integer id) throws ApiException {
        return  apiOrgAdmin.getLicensesApi().getAllForTeamWithHttpInfo(id, prop.getProperty("x.customer.code"),
                null, null, null, null, null);
    }
    @Nullable
    public Integer getMyTeamId() {
        synchronized (block){
            if(myTeamId == null) {
                try {
                    myTeamId = apiTeamAdmin.getTokenApi().getManagedEntityInfo(prop.getProperty("x.customer.code"))
                            .getManagedTeamResponse().getTeam().getId();
                    return myTeamId;
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            }
            return myTeamId;
        }
    }
}
