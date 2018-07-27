package gtja.taskframework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wenqi on 2018/7/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ShellJobTest {

    @Test
    public void test(){
        String batFilePath="shell\\job.bat";
        String pythonFilePath="shell\\hello.py";
        run_cmd(batFilePath+" wenqi");
        //run_cmd("python "+pythonFilePath);
    }

    public void run_cmd(String strcmd){
        Runtime rt=Runtime.getRuntime();
        Process ps=null;
        try {
            ps=rt.exec(strcmd);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(ps.getInputStream()));
            String line;
            while((line=bufferedReader.readLine())!=null){
                System.out.println(line);
            }
            System.out.println("Waiting for batch file ...");
            int result=ps.waitFor();
            System.out.println("Batch file done."+result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int i=ps.exitValue();
        if(i==0){
            System.out.println("执行完成");
        }else{
            System.out.println("执行失败");
        }

      /*  try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
