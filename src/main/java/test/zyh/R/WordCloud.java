package test.zyh.R;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import javax.swing.JFrame;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

/**
 * Created by zhangyihang on 17/4/19.
 */
public class WordCloud extends JFrame {
    private static final long serialVersionUID = -4921997863845086763L;
    static Image img;

    public static void main(String[] args) throws Exception {
        WordCloud wc = new WordCloud();
        REXP xp = wc.getRobj();// 获得R的内容对象
        wc.PlotDemo(xp, wc);
    }

    private REXP getRobj() throws Exception {

        RConnection c = RUtil.getRConnection();
        c.setStringEncoding("utf8");
        REXP Rversion = c.eval("R.version.string");
        System.out.println(Rversion.asString());
        System.out.println("\n--------------画图演示 install.packages(\"wordcloud\");----------------");
        REXP xp = c.parseAndEval("jpeg('test.jpg',quality=90)");
        c.eval("library(wordcloud)");
        c.voidEval("colors=c('red','blue','green','yellow','purple')");
        c.eval("data=read.table('/Users/zhangyihang/Desktop/test.txt',header = F,fileEncoding='UTF-8')");
        c.parseAndEval("par(family=('Heiti TC Light'))");
        c.parseAndEval("wordcloud(data$V2,data$V1,scale=c(5,0.3),min.freq=-Inf,max.words=60,colors=colors,random.order=F,random.color=F,ordered.colors=F);dev.off()");
        xp = c.parseAndEval("r=readBin('test.jpg','raw',2048*2048); unlink('test.jpg'); r");
        return xp;
    }

    public void PlotDemo(REXP xp, JFrame f) throws Exception {
        img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
        MediaTracker mediaTracker = new MediaTracker(this);
        mediaTracker.addImage(img, 0);
        mediaTracker.waitForID(0);
        f.setTitle("java生成一个词云");
        f.setSize(img.getWidth(null), img.getHeight(null));
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

}
