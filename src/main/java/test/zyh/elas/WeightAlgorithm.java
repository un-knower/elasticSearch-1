/**
 * Copyright (C) 2015-2020 gome meixin_search Inc.All Rights Reserved.
 * 
 * FileName:WeightAlgorithm.java
 *
 * Description：简要描述本文件的内容
 *
 * History：
 * 版本号           作者                  日期               简要介绍相关操作
 *  1.0  liuyuxin  2016年4月8日
 *
 */
package test.zyh.elas;

/**
 * @author zhangyihang
 * @createTime 2017年04月10日10:48:47
 *
 */
public class WeightAlgorithm {
    /**
     * 
     * @描述：最小最大规范化也叫离差标准化 ,可以对原始数据进行线性变换 ; 假定min和max是最小值和最大值，
     *                    v是该区间中的一个值，将其映射到新的区间[newMin, newMax]中为v' 则有： v' =
     *                    (v-Min)/(max-min)*(newMax-newMin)+newMin
     *                    这种方法有一个缺陷就是当有新数据加入时，可能导致max和min的变化，需要重新定义。
     * @param v
     *            做标准化的样本数据
     * @param min
     *            样本数据最小值
     * @param max
     *            样本数据最大值
     * @param newMin
     *            新的映射区间最小值
     * @param newMax
     *            新的映射区间最大值
     * @return
     * @return double
     * @exception
     * @createTime：2017年04月10日10:48:47
     * @author: zhangyihang
     */
    public static Double normalization(Double v, Double min, Double max, Double newMin, Double newMax) {
        return (v - min) / (max - min) * (newMax - newMin) + newMin;
    }

}
