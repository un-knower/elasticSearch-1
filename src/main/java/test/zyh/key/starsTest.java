package test.zyh.key;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhangyihang on 17/4/21.
 */
public class starsTest {

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        //明星关键字提权
        String sens = "古装美女群像,刘亦菲,刘诗诗,周迅,范冰冰,杨蓉,舒畅,梅艳芳,林青霞,王祖贤,张敏,古力娜扎,鞠婧祎,迪丽热巴,杨紫,赵丽颖,王鸥,关晓彤,郑爽,陈乔恩,杨幂";
        Iterator<String> iterator = SensitiveWord.initMap().getSensitiveWord(sens,1).iterator();
        String star = null;
        while (iterator.hasNext()){
            String str= iterator.next();
            star += str+",";
        }
        long endTime = System.currentTimeMillis();
        System.out.print(star.substring(4,star.length()-1));
        System.out.println("程序执行的时间"+(endTime-startTime)+"ms");


    }
}
