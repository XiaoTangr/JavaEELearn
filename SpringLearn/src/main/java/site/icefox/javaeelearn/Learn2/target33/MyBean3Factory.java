package site.icefox.javaeelearn.Learn2.target33;

public class MyBean3Factory {
    public MyBean3Factory() {
        System.out.println("bean3工厂实例化中");
    }

    public Bean3 createBean() { //创建Bean3实例的方法
        return new Bean3();
    }
}


