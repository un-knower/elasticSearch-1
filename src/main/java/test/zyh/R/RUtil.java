package test.zyh.R;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

/**
 * Created by zhangyihang on 17/4/19.
 */
public class RUtil {
    private static String R_EXE_PATH = "/Library/Frameworks/R.framework/Versions/3.3/Resources/bin/R.exe";
    private static String R_SCRIPT_PATH = "/Library/Frameworks/R.framework/Versions/3.3/Resources/library/Rserve/libs/rserve.R";

    public static RConnection getRConnection() {
        try {
            RConnection rcon = new RConnection();
            return rcon;
        } catch (RserveException e) {
            System.out.println("Rserve未开启，现在启动RServe……");
            try {
                Runtime rn = Runtime.getRuntime();
                rn.exec(R_EXE_PATH + " CMD BATCH \"" + R_SCRIPT_PATH + "\"");
                Thread.sleep(1000);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return getRConnection();
        }
    }
}
