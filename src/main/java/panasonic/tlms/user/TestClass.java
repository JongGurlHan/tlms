package panasonic.tlms.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TestClass {

    private int id;
    private String email;
    private String password;
    public String PrettyPrint() {
        return "id: "+this.id+" email: "+this.email+" password: "+this.password;
    }
}
