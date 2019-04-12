import controlers.AuthHelper;
import model.TokenInfo;

public class TestJWT {
    static String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTZWJiaWErQ2FudmFzXHUwMDNkSXNzdWVyIiwiaWF0IjoxNTU0ODg3NDA0LCJleHAiOjE1NTQ5NzM4MDQsImluZm8iOnsidXNlcklkIjoiMSIsInVzZXJUeXBlIjoiMiJ9fQ.caFd0Lj3s0M-tzz4MYmhKaEH84TRErSx7dHUUsOoNo0";
    public static void main(String[] args) {
        //String token = controlers.AuthHelper.createJsonWebToken("1","2",1);
        System.out.println(token);

        TokenInfo tokenInfo = AuthHelper.verifyToken(token);

        System.out.println();
        System.out.println(tokenInfo.getUserId());
        System.out.println(tokenInfo.getUserType());
        System.out.println(tokenInfo.getIssued());
        System.out.println(tokenInfo.getExpires());
    }
}
