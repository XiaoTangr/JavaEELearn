package site.icefox.javaeelearn.learn1.target2;

public class HelloSpring {
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void show() {
        System.out.println(userName + ":欢迎来到Spring");
    }
}
