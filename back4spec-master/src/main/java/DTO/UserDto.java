package DTO;

public class UserDto {
    private String nickname;
    private String pswrd;
    private Long id;

    // Конструктор по умолчанию
    public UserDto() {
    }

    // Конструктор с параметрами
    public UserDto(String nickname, String pswrd) {
        this.nickname = nickname;
        this.pswrd = pswrd;
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPswrd() {
        return pswrd;
    }
    public void setPswrd(String pswrd) {
        this.pswrd = pswrd;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
}
