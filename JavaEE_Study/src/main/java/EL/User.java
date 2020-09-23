package EL;

import java.util.Hashtable;
import java.util.Map;


// 사용자에 대한 정보를 저장하는 POJO
public class User {
    // EL 식을 이용하여 접근하고자 하는 private 필드
    private long userId;
    private String username;
    private String firstName;
    private String lastName;
    private Map<String, Boolean> permissions = new Hashtable<>();


    // 생성자 추가
    public User(){}
    public User(long userId, String username, String firstName, String lastName){
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // EL 에서 private 필드에 접근하기 위한 public getter, setter

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Map<String, Boolean> getPermissions() {
        return permissions;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPermissions(Map<String, Boolean> permissions) {
        this.permissions = permissions;
    }
}
