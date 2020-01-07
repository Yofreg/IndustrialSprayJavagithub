package com.goockr.pmj.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author yofreg
 * @time 2018/8/6 10:28
 * @desc ${TODD}
 */
public class GetCPUUtils {

    private final static String CurPath = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";//保存当前CPU频率
    //获取当前CPU频率
    public static int getCurCPU(){
        int result = 0;
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr = new FileReader(CurPath);
            br = new BufferedReader(fr);
            String text = br.readLine();
            result = Integer.parseInt(text.trim());
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fr != null)
                try{
                    fr.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            if (br != null)
                try{
                    br.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
        }
        return result;
    }

    private final static String MaxPath = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";//保存CPU可运行最大频率
    //获取CPU可运行最大频率
    public static int getMaxCPU(){
        int result = 0;
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr = new FileReader(MaxPath);
            br = new BufferedReader(fr);
            String text = br.readLine();
            result = Integer.parseInt(text.trim());
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fr != null)
                try{
                    fr.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            if (br != null)
                try{
                    br.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
        }
        return result;
    }

    private final static String MinPath = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq";//保存CPU可运行最小频率
    //获取CPU可运行最小频率
    public static int getMinCPU(){
        int result = 0;
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr = new FileReader(MinPath);
            br = new BufferedReader(fr);
            String text = br.readLine();
            result = Integer.parseInt(text.trim());
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fr != null)
                try{
                    fr.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            if (br != null)
                try{
                    br.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
        }
        return result;
    }
}
