package test.zyh.R;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class RserveBegin {
    public static void main(String[] args) {
        try {
            callRserve();
        } catch (RserveException e) {
            e.printStackTrace();
        } catch (REXPMismatchException e) {
            e.printStackTrace();
        }
    }

    static void callRserve() throws RserveException, REXPMismatchException {
        RConnection rConnection = new RConnection("127.0.0.1");

        String rv = rConnection.eval("R.version.string").asString();
        System.out.println(rv);

        double [] arr = rConnection.eval("rnorm(10)").asDoubles();
        for(double d : arr) {
            System.out.println(d);
        }
    }
}