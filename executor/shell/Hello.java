/**
 * Created by wenqi on 2018/7/23.
 */
public class Hello {
    public static void main(String[] args){
        System.out.println("hello");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("good");
    }
}
