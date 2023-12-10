import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
public class WatchVideo {
    /**
     * 生成正态分布随机数
     * @param mean 均值
     * @param std 方差
     * @param min 最小范围
     * @param max 最大范围
     * @param mmin 最小可接受值
     * @param mmax 最大可接受值
     * @return
     */
    public static double genurate(double mean,double std,double min,double max,double mmin,double mmax) {
        double num= clamp(mean + std * new Random().nextGaussian(), min, max);
        if(num>mmin&&num<mmax){
            return num;
        }else {
            return genurate(mean,std,min,max,mmin,mmax);
        }
    }
    /**
     * 将值限制在指定范围内的辅助方法
     * @param value 值
     * @param min 最小值
     * @param max 最大值
     */
    
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * 随机刷短视频
     */
    public static void swip() throws Exception {
        double x1=genurate(600,200,200,1000,0,1080);
        double y1=genurate(2050,150,1800,2300,0,2376);
        double x2=genurate(x1,x1+50,x1-100,x1+100,0,1080);
        double y2=genurate(y1,y1+50,y1-100,y1+100,0,2376);
        double time=genurate(15,2,10,20,0,30);
        doCmd("adb shell input swipe "+x1+" "+y1+" "+x2+" "+y2+" "+(long)time*10);
        double times=genurate(15,2,10,20,0,30);
        Thread.sleep((long)times*1000);
    }

    /**
     * 刷播放量
     * @param args
     * @throws Exception
     */
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
