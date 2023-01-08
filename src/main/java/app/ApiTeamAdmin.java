package app;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiTeamAdmin extends ApiBaseClass{
    private static ApiTeamAdmin INSTANCE;
    public ApiTeamAdmin(){
        super("token.team.admin");
    }
    public static synchronized ApiTeamAdmin getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ApiTeamAdmin();
        }
        return INSTANCE;
    }
}
