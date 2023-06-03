package org.linlinjava.litemall.db.util;

import org.linlinjava.litemall.db.domain.LitemallOrder;

import java.util.ArrayList;
import java.util.List;

/*
 * 订单流程：下单成功－》支付订单－》发货－》收货
 * 订单状态：
 * 101 订单生成，未支付；102，下单未支付用户取消；103，下单未支付超期系统自动取消
 * 201 支付完成，商家未发货；202，订单生产，已付款未发货，用户申请退款；203，管理员执行退款操作，确认退款成功；
 * 301 商家发货，用户未确认；
 * 401 用户确认收货，订单结束； 402 用户没有确认收货，但是快递反馈已收货后，超过一定时间，系统自动确认收货，订单结束。
 *
 * 当101用户未付款时，此时用户可以进行的操作是取消或者付款
 * 当201支付完成而商家未发货时，此时用户可以退款
 * 当301商家已发货时，此时用户可以有确认收货
 * 当401用户确认收货以后，此时用户可以进行的操作是退货、删除、去评价或者再次购买
 * 当402系统自动确认收货以后，此时用户可以删除、去评价、或者再次购买
 */
public class OrderUtil {

    /**
     * 此处实际只需要：201-待发货；102-用户取消订单；104：商家取消订单；401：订单完成；
     */
    public static final Short STATUS_PAY = 201;
    public static final Short STATUS_CANCEL = 102;
    public static final Short STATUS_ADMIN_CANCEL = 104;
    public static final Short STATUS_CONFIRM = 401;

    public static final Short STATUS_CREATE = 101;
    public static final Short STATUS_SHIP = 301;
    public static final Short STATUS_AUTO_CANCEL = 103;
    public static final Short STATUS_REFUND = 202;
    public static final Short STATUS_REFUND_CONFIRM = 203;
    public static final Short STATUS_AUTO_CONFIRM = 402;

    public static String orderStatusText(LitemallOrder order) {
        int status = order.getOrderStatus().intValue();

        if (status == 101) {
            return "待支付";
        }

        if (status == 102) {
            return "已取消";
        }

        if (status == 103) {
            return "已取消(系统)";
        }

        if (status == 104) {
            return "已取消(商家)";
        }

        if (status == 201) {
            return "待发货";
        }

        if (status == 202) {
            return "订单取消，退款中";
        }

        if (status == 203) {
            return "已退款";
        }

        if (status == 204) {
            return "已超时团购";
        }

        if (status == 301) {
            return "已发货";
        }

        if (status == 401) {
            return "已完成";
        }

        if (status == 402) {
            return "已收货(系统)";
        }

        throw new IllegalStateException("orderStatus不支持");
    }


    public static OrderHandleOption build(LitemallOrder order) {
        int status = order.getOrderStatus().intValue();
        OrderHandleOption handleOption = new OrderHandleOption();

        if (status == 102 || status == 104) {
            // 如果订单已经取消或是已完成，则可删除
            handleOption.setDelete(true);
        } else if (status == 201) {
            // 待发货，则可取消
            handleOption.setCancel(true);
        } else if (status == 401) {
            // 如果订单完成，则可删除和再次购买
            handleOption.setDelete(true);
            handleOption.setRebuy(true);
        } else {
            throw new IllegalStateException("status不支持");
        }

        return handleOption;
    }

    public static List<Short> orderStatus(Integer showType) {
        // 全部订单
        if (showType == 0) {
            return null;
        }

        List<Short> status = new ArrayList<Short>(2);

        if (showType.equals(1)) {
            // 待发货订单
            status.add((short) 201);
        } else if (showType.equals(2)) {
            // 已完成订单
            status.add((short) 401);
        } else if (showType.equals(3)) {
            // 已取消订单-包含用户取消和管理员取消
            status.add((short) 102);
            status.add((short) 104);
        }else {
            return null;
        }

        return status;
    }


    public static boolean isCreateStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_CREATE == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean hasPayed(LitemallOrder order) {
        return OrderUtil.STATUS_CREATE != order.getOrderStatus().shortValue()
                && OrderUtil.STATUS_CANCEL != order.getOrderStatus().shortValue()
                && OrderUtil.STATUS_AUTO_CANCEL != order.getOrderStatus().shortValue();
    }

    public static boolean isPayStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_PAY == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isShipStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_SHIP == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isConfirmStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_CONFIRM == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isCancelStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_CANCEL == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isAutoCancelStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_AUTO_CANCEL == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isRefundStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_REFUND == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isRefundConfirmStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_REFUND_CONFIRM == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isAutoConfirmStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_AUTO_CONFIRM == litemallOrder.getOrderStatus().shortValue();
    }
}
