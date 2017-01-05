package com.jzfq.fms.common.enums;

/**
 * Created by huyinglin on 2016/12/11.
 */
public enum  JdPayOrderType {

      //实物
      MATERIAL((byte)0),
      //虚拟
      INVENTED((byte)1),
      //门店订单
      STORE_ORDER((byte)10),
      //服务订单
      SERVICE_ORDER((byte)11),
      //美食订单
      FOOD_ORDER((byte)12),
      //厂直类型订单
      FACTORY_DIRECT_ORDER((byte)13),
      //一般自营类订单
      SELF_ORDER((byte)14),
      //开放仓类订单
      WAREHOUSE_ORDER((byte)15);

      private byte type;

      private JdPayOrderType(byte type) {
        this.type = type;
    }

      public byte getType() {
        return type;
    }

}
