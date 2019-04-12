package model;

public class User {
    private int id;
    private String login;
    private int type;

    public User(int id, String login, int type) {
        this.id = id;
        this.login = login;
        this.type = type;
    }

    public User(TokenInfo token){
        this.id = Integer.parseInt(token.getUserId());
        this.type = Integer.parseInt(token.getUserType());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
