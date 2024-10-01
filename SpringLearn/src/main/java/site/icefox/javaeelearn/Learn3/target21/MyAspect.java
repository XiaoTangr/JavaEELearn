package site.icefox.javaeelearn.Learn3.target21;

public class MyAspect {// 切面类：存在多个通知Advice（增强的方法）
    public static void check_Permissions(){
        System.out.println("模拟检查权限...");		}
    public static void log(){
        System.out.println("模拟记录日志...");		}
}
