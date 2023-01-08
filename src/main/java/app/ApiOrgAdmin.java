package app;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiOrgAdmin extends ApiBaseClass{
    private static ApiOrgAdmin INSTANCE;
    public ApiOrgAdmin(){
        super("token.org.admin");
    }
    public static synchronized ApiOrgAdmin getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ApiOrgAdmin();
        }
        return INSTANCE;
    }
}
