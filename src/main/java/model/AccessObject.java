package model;

public class AccessObject {
    private String accessToken;
    private String role;
    private String result;

    public AccessObject(String accessToken, String role, String result) {
        this.accessToken = accessToken;
        this.role = role;
        this.result = result;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
