package helper;

import exceptions.TeamNotFoundException;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.LicenseResponse;
import org.openapitools.client.model.TeamResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static data.PropertiesSupplier.prop;
import static java.lang.Thread.currentThread;

public class PreconditionsChangeTeam extends BaseHelper {
    private Log log = LogFactory.getLog(PreconditionsChangeTeam.class);
    public TeamResponse getTeamWithoutLicenses(List<LicenseResponse> licenseResponseToTransfer) throws Exception {
        val teamIdsToTranfer= licenseResponseToTransfer.stream()
                .map(l -> l.getTeam().getId())
                .toList();
        val licensesToTransfer = licenseResponseToTransfer.stream()
                .map(LicenseResponse::getLicenseId)
                .toList();
        val availableTeams = getTeamResponse();
        return availableTeams.stream()
                .filter(t -> !teamIdsToTranfer.contains(t.getId()))
                .peek(t -> log.info(currentThread().getId() + ": " + Arrays.toString(licensesToTransfer.toArray())
                        + " to the team: "+t.getId()))
                .findFirst()
                .orElseThrow(()-> new TeamNotFoundException("No team is available for the transferring. Create one more."));
    }
    @NotNull
    public List<TeamResponse> getTeamResponse() throws ApiException {
        val tokenInfo = apiOrgAdmin.getTokenApi()
                .getManagedEntityInfoWithHttpInfo(prop.getProperty("x.customer.code"));
        val availableTeams = new ArrayList<>(Objects.requireNonNull(tokenInfo.getData()
                .getManagedCustomerResponse()
                .getTeams()).stream()
                .map(t -> {
                    val teamResponse = new TeamResponse();
                    teamResponse.setId(t.getId());
                    teamResponse.setName(t.getName());
                    return teamResponse;
                })
                .filter(Objects::nonNull)
                .filter(t -> t.getId()!=null)
                .filter(t -> !Objects.equals(t.getId(), OUTDATED_TEAM_ID))
                .filter(t -> !Objects.equals(t.getId(), getMyTeamId()))
                .toList());
        Collections.shuffle(availableTeams, new Random(LocalDateTime.now()
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        return availableTeams;
    }
    public TeamResponse getTeamWithoutLicenses(LicenseResponse licenseResponse) throws Exception {
        return getTeamWithoutLicenses(new ArrayList<>(){{add(licenseResponse);}});
    }

}
