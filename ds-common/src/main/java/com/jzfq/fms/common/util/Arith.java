package com.jzfq.fms.common.util;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by zhishuo on 11/8/16.
 * 精确计算类
 */
public class Arith {

    private MathContext context = MathContext.DECIMAL128;

    private BigDecimal result = BigDecimal.ZERO;

    private int defaultRound = 2;

    public Arith(String val) {
        this.result = new BigDecimal(val);
    }

    public Arith(BigDecimal val) {
        this.result = val;
    }

    public Arith(int val) {
        this.result = new BigDecimal(val);
    }

    public Arith(long val) {
        this.result = new BigDecimal(val);
    }

    public Arith(double val) {
        this.result = new BigDecimal(val);
    }


    /**
     * 加法
     *
     * @param val
     * @return
     */
    public Arith add(BigDecimal val) {
        this.result = this.result.add(val, context);
        return this;
    }

    /**
     * 减法
     *
     * @param val
     * @return
     */
    public Arith sub(BigDecimal val) {
        this.result = this.result.subtract(val, context);
        return this;
    }

    /**
     * 乘法
     *
     * @param val
     * @return
     */
    public Arith multiply(BigDecimal val) {
        this.result = this.result.multiply(val, context);
        return this;
    }

    /**
     * 除法
     *
     * @param val
     * @return
     */
    public Arith divide(BigDecimal val) {
        this.result = this.result.divide(val, context);
        return this;
    }

    /**
     * 几次方
     *
     * @param pow
     * @return
     */
    public Arith pow(int pow) {
        this.result = this.result.pow(pow, context);
        return this;
    }


    /**
     * 获取4舍5入的结果，默认保留2位小数
     *
     * @return
     */
    public BigDecimal getRound() {
        return this.result.setScale(defaultRound, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 获取指定保留位数，4舍5入的结果
     *
     * @param scale
     * @return
     */
    public BigDecimal getRound(int scale) {
        return this.result.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static boolean eq(BigDecimal val, BigDecimal val1) {
        return val.compareTo(val1) == 0;
    }

    /**
     * 返回 val 值 是否大于val1
     * 如果返回true 证明 val > val1
     *
     * @param val
     * @param val1
     * @return
     */
    public static boolean isBigger(BigDecimal val, BigDecimal val1) {
        return val.compareTo(val1) == 1;
    }

    /**
     * 返回 val 值 是否小于val1
     * 如果返回true 证明 val < val1
     *
     * @param val
     * @param val1
     * @return
     */
    public static boolean isLess(BigDecimal val, BigDecimal val1) {
        return val.compareTo(val1) == -1;
    }


    /**
     * 获取不做任何处理的计算结果
     *
     * @return
     */
    public BigDecimal getResult() {
        return this.result;
    }


    public static void main(String[] args) {

        System.out.println(new Arith("100").pow(2));
        System.out.println(Arith.eq(new BigDecimal("10.01"), new BigDecimal("10.01")));
        System.out.println(Arith.isBigger(new BigDecimal("10.01"), new BigDecimal("10")));
        System.out.println(Arith.isLess(new BigDecimal("10.01"), new BigDecimal("9.01")));
    }

}
