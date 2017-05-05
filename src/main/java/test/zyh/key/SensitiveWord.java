package test.zyh.key;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description 单例加载词语
 * @author zhangyihang
 * @date 2017年04月21日09:44:34
 */
@SuppressWarnings("all")
public class SensitiveWord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7040374110291751685L;
	
	static Map sensitiveWordMap = null;
	
	/**
	 * @Description 构造函数，初始化明星词典库 
	 * @author zhangyihang
	 * @date 2017年04月21日09:44:34
	 * @return
	 */
	public static SensitivewordFilter initMap(){
		 if(sensitiveWordMap == null){
			synchronized(SensitiveWord.class){
				if(sensitiveWordMap == null){
					sensitiveWordMap = new SensitiveWordInit().initKeyWord();
				}
			}
		}
		return new SensitivewordFilter();
	}
	
	/**
	 * @Description 二次加载明星词典 
	 * @author zhangyihang
	 * @date 2017年04月21日09:44:34
	 * @return
	 */
	public static boolean secondInitMap(){
		sensitiveWordMap = null;
		sensitiveWordMap = new SensitiveWordInit().initKeyWord();
		if(sensitiveWordMap!=null&&sensitiveWordMap.size()>0){
			return true;
		}else{
			return false;
		}
	}
}