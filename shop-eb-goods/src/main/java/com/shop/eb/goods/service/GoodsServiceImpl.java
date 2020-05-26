package com.shop.eb.goods.service;

import com.shop.be.common.api.goods.GoodsServiceApi;
import com.shop.be.common.api.goods.vo.GoodsVo;
import com.shop.be.common.domain.SeckillGoods;
import com.shop.eb.goods.persistence.GoodsMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 商品服务接口实现
 *
 * @author noodle
 */
@Service(interfaceClass = GoodsServiceApi.class,timeout = 600000)
public class GoodsServiceImpl implements GoodsServiceApi {

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> listGoodsVo() {
        return goodsMapper.listGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }

    /**
     * 通过商品的id查出商品的所有信息（包含该商品的秒杀信息）
     *
     * @param goodsId
     * @return
     */
    @Override
    public GoodsVo getGoodsVoByGoodsId(Long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }


    /**
     * 减库存
     *
     * @param goods
     * @return
     */
    @Override
    public boolean reduceStock(GoodsVo goods) {
        SeckillGoods seckillGoods = new SeckillGoods();
        // 秒杀商品的id和商品的id是一样的
        seckillGoods.setGoodsId(goods.getId());
        int ret = goodsMapper.reduceStack(seckillGoods);
        return ret > 0;
    }
}
