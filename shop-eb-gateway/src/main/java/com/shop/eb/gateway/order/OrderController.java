package com.shop.eb.gateway.order;

import com.shop.be.common.api.goods.GoodsServiceApi;
import com.shop.be.common.api.goods.vo.GoodsVo;
import com.shop.be.common.api.order.OrderServiceApi;
import com.shop.be.common.api.order.vo.OrderDetailVo;
import com.shop.be.common.api.user.vo.UserVo;
import com.shop.be.common.domain.OrderInfo;
import com.shop.be.common.result.CodeMsg;
import com.shop.be.common.result.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单服务接口
 *
 * @author noodle
 */
@Controller
@RequestMapping("/order/")
public class OrderController {

    @Reference(interfaceClass = OrderServiceApi.class, timeout=3000)
    OrderServiceApi orderService;

    @Reference(interfaceClass = GoodsServiceApi.class, timeout=3000)
    GoodsServiceApi goodsService;

    /**
     * 获取订单详情
     *
     * @param model
     * @param user
     * @param orderId
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public Result<OrderDetailVo> orderInfo(Model model,
                                           UserVo user,
                                           @RequestParam("orderId") long orderId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        // 获取订单信息
        OrderInfo order = orderService.getOrderById(orderId);
        if (order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }

        // 如果订单存在，则根据订单信息获取商品信息
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setUser(user);// 设置用户信息
        vo.setOrder(order); // 设置订单信息
        vo.setGoods(goods); // 设置商品信息
        return Result.success(vo);
    }
}
