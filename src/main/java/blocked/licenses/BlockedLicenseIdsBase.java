package blocked.licenses;

import lombok.Getter;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openapitools.client.model.LicenseResponse;

import java.util.*;

import static java.lang.Thread.currentThread;
@Getter
public class BlockedLicenseIdsBase {
    private final Log log = LogFactory.getLog(BlockedLicenseIdsBase.class);
    private final Map<Long, List<String>> map = new HashMap<>();
    private final Object block = new Object();
    public void addLicense(LicenseResponse licenseResponse){
        val id = currentThread().getId();
        val licenseId = licenseResponse.getLicenseId();
        ArrayList<String> value = new ArrayList<>() {{add(licenseId);}};
        map.merge(id, value, (v1, v2) -> {
            v1.addAll(value);
            return v1;
        });
        this.log.info("License %s was blocked".formatted(licenseId));
    }
    public void dismissLicenses() {
        val key= currentThread().getId();
        this.log.info("License %s is free now".formatted(map.get(key)));
        map.remove(key);
    }
    public boolean checkIsBlocked(String licenseId) {
        return map.values().stream()
                .flatMap(Collection::stream)
                .toList()
                .contains(licenseId);
    }
}
