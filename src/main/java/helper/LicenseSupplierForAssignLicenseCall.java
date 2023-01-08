package helper;

import exceptions.LicenseNotFoundException;
import exceptions.ProductNotFoundException;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.LicenseResponse;
import org.openapitools.client.model.ProductResponse;

import java.util.Objects;

import static java.lang.Thread.currentThread;

public class LicenseSupplierForAssignLicenseCall extends BaseHelper{
    private final Log log = LogFactory.getLog(LicenseSupplierForAssignLicenseCall.class);
    public LicenseResponse getLicense(boolean isAvailableToAssign, boolean isFromMyTeam, boolean isWebstorm) throws ApiException{
        val myTeamId = getMyTeamId();
        log.info("My team is: "+myTeamId);
        val productCode = apiOrgAdmin.getDictionariesApi().getProducts().stream()
                .filter(p->p.getName()!=null)
                .filter(p->p.getName().equals(WEBSTORM_NAME))
                .map(ProductResponse::getCode)
                .findFirst().orElseThrow(()->new ProductNotFoundException("Product %s is not available"
                        .formatted(WEBSTORM_NAME)));
        synchronized(blockedLicenseIdsAssignLicense.getBlock()) {
            return getAllForCustomerAsOrgAdmin().stream()
                    .filter(lr -> lr.getLicenseId() != null)
                    .filter(lr -> !blockedLicenseIdsAssignLicense.checkIsBlocked(lr.getLicenseId()))
                    .filter(lr -> lr.getTeam() != null)
                    .filter(lr -> lr.getTeam().getId() != null)
                    .filter(lr -> !Objects.equals(lr.getTeam().getId(), OUTDATED_TEAM_ID))
                    .filter(lr -> Objects.equals(lr.getTeam().getId(), myTeamId) == isFromMyTeam)
                    .filter(lr -> lr.getIsAvailableToAssign() != null)
                    .filter(lr -> lr.getIsAvailableToAssign() == isAvailableToAssign)
                    .filter(l -> l.getProduct() != null)
                    .filter(l -> l.getProduct().getCode() != null)
                    .filter(l -> l.getProduct().getCode().equals(productCode) == isWebstorm)
                    .peek(ls -> log.info(currentThread().getId() + ":" + ls.getLicenseId() + " from the team: " +
                            ls.getTeam().getId() + " were chosen to be assigned"))
                    .peek(lr -> blockedLicenseIdsAssignLicense.addLicense(lr))
                    .findFirst().orElseThrow(() ->
                            new LicenseNotFoundException(("Free license was not found for isAvailableToAssign = %s, " +
                                    "isFromMyTeam = %s, isWebstorm = %s")
                                    .formatted(isAvailableToAssign, isFromMyTeam, isWebstorm)));
        }
    }
    public LicenseResponse getLicenseFromMyTeam(boolean isAvailableToAssign, boolean isWebstorm) throws ApiException {
        return getLicense(isAvailableToAssign, true, isWebstorm);
    }
}

