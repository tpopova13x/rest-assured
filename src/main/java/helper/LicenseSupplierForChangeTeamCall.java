package helper;

import exceptions.LicenseNotFoundException;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.LicenseResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Thread.currentThread;

public class LicenseSupplierForChangeTeamCall extends BaseHelper{
    private final Log log = LogFactory.getLog(LicenseSupplierForAssignLicenseCall.class);
    public List<LicenseResponse> fromOneTeam(boolean isTransferable, Boolean hasAssignee, int quantity) throws Exception {
        val myTeamId = getMyTeamId();
        synchronized (blockedLicenseIdsChangeTeam.getBlock()) {
            List<LicenseResponse> licenseResponse = getLicenses(isTransferable, hasAssignee)
                    .filter(lr->!Objects.equals(lr.getTeam().getId(), myTeamId))
                    .collect(Collectors.groupingBy(LicenseResponse::getTeam, Collectors.toList()))
                    .values().stream()
                    .filter(lr -> lr.size() >= quantity)
                    .findFirst().orElseThrow(() ->
                            new LicenseNotFoundException("The required amount of licences is not found in one team"))
                    .stream().limit(quantity)
                    .peek(lr -> log.info(currentThread().getId() + ":" + lr.getLicenseId() +
                            " were chosen to transfer from team: " + lr.getTeam().getId()))
                    .peek(lr -> blockedLicenseIdsChangeTeam.addLicense(lr))
                    .toList();
            return licenseResponse;
        }
    }
    public List<LicenseResponse> fromDifferentTeams(boolean isTransferable, boolean hasAssignee, int quantity) throws Exception {
        val myTeamId = getMyTeamId();
        synchronized (blockedLicenseIdsChangeTeam.getBlock()) {
            List<LicenseResponse> licenseResponse = getLicenses(isTransferable, hasAssignee)
                    .filter(lr->!Objects.equals(lr.getTeam().getId(), myTeamId))
                    .collect(Collectors.groupingBy(LicenseResponse::getTeam, Collectors.toList()))
                    .values().stream()
                    .map(v -> v.get(0))
                    .limit(quantity)
                    .peek(ls -> log.info(currentThread().getId() + ":" + ls.getLicenseId() +
                            " were chosen to transfer from team: " + ls.getTeam().getId()))
                    .peek(lr -> blockedLicenseIdsChangeTeam.addLicense(lr))
                    .toList();
            return licenseResponse;
        }
    }
    private Stream<LicenseResponse> getLicenses(boolean isTransferable, Boolean hasAssignee) throws ApiException {
        return getAllForCustomerAsOrgAdmin().stream()
                .filter(lr -> lr.getLicenseId() != null)
                .filter(lr -> !blockedLicenseIdsChangeTeam.checkIsBlocked(lr.getLicenseId()))
                .filter(lr -> lr.getTeam() != null)
                .filter(lr -> lr.getTeam().getId() != null)
                .filter(lr -> !Objects.equals(lr.getTeam().getId(), OUTDATED_TEAM_ID))
                .filter(lr -> lr.getIsTransferableBetweenTeams() != null)
                .filter(lr -> lr.getIsTransferableBetweenTeams() == isTransferable)
                .filter(lr -> hasAssignee == null || ((lr.getAssignee() != null) == hasAssignee));
    }

    public LicenseResponse fromTeam(boolean isFromMyTeam, boolean isTransferable, Boolean hasAssignee)throws ApiException {
        val myTeamId = getMyTeamId();
        log.info("My team is: "+myTeamId);
        synchronized (blockedLicenseIdsChangeTeam.getBlock()) {
            return getLicenses(isTransferable, hasAssignee)
                    .filter(lr->(Objects.equals(lr.getTeam().getId(), myTeamId))==isFromMyTeam)
                    .filter(lr -> lr.getLicenseId()!=null)
                    .filter(lr -> !blockedLicenseIdsChangeTeam.checkIsBlocked(lr.getLicenseId()))
                    .peek(ls -> log.info(currentThread().getId() + ":" + ls.getLicenseId() +" from the team: " +
                            ls.getTeam().getId() + " were chosen to be transferred"))
                    .peek(lr -> blockedLicenseIdsChangeTeam.addLicense(lr))
                    .findFirst().orElseThrow(() ->
                            new LicenseNotFoundException("Free license was not found"));
        }
    }
}
