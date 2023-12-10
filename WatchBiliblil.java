/**
 * 自动刷播放量
 * 原理：根据平台规则，每5-6分钟重新点击视频可增加一次播放量，故在此使用随机数模拟人工点击刷播放量。
 * 警告：不保证安全
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
public class WatchBilibili {
    public static double genurate(int mean,int std,int min,int max,int mmin,int mmax) {
        double num= clamp(mean + std * new Random().nextGaussian(), min, max);
        if(num>mmin&&num<mmax){
            return num;
        }else {
            return genurate(mean,std,min,max,mmin,mmax);
        }
    }
    // 将值限制在指定范围内的辅助方法
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 5; i++) {
            double x=genurate(550,200,100,1070,0,1080);
            double y=genurate(850,50,700,1000,680,1060);
            double b=genurate(7,1,6,9,5,10);
            double z=genurate(390,15,370,410,360,420);
            var cmd="adb shell input tap "+x+" "+y;
            System.out.println(cmd);
            doCmd(cmd);
            System.out.println("休眠时间:"+b*1000);
            Thread.sleep((long) b*1000);
            doCmd("adb shell input keyevent 4");
            System.out.println(z*1000);
            Thread.sleep((long)z*1000);
        }
    }
    private static void doCmd(String cmd) throws Exception {
        Process process = Runtime.getRuntime().exec(cmd);
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "GBK");
        BufferedReader br = new BufferedReader(isr);
        String content = br.readLine();
        while (content != null) {
            System.out.println(content);
            content = br.readLine();
        }
    }
}
