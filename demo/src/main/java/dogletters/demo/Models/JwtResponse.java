package dogletters.demo.Models;

public class JwtResponse {
    // declare string token
    private String jwt;

    // constructor
    public JwtResponse(String jwt) {
        this.jwt = jwt;
    }

    // get token for authentication purposes
    public String getToken() {
        return this.jwt;
    }
}
