package org.linlinjava.litemall.admin.util;

import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.domain.LitemallGoodsSpecification;
import org.linlinjava.litemall.db.domain.LitemallGoodsAttribute;
import org.linlinjava.litemall.db.domain.LitemallGoodsProduct;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.linlinjava.litemall.admin.dto.GoodsAllinone;
import org.linlinjava.litemall.admin.service.AdminGoodsService;
import org.linlinjava.litemall.db.domain.LitemallGoodsImport;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

/**
 * @author jijiecong
 * @version 1.0
 * @date 2023/5/28 14:46
 * @description TODO
 */
@Component
public class GoodsExcelListener extends AnalysisEventListener<LitemallGoodsImport> {

    private LitemallGoodsImport litemallGoodsImport;

    private AdminGoodsService adminGoodsService;

    private LinkedList<LitemallGoodsImport> datas = new LinkedList<LitemallGoodsImport>();

    public GoodsExcelListener(){}

    public GoodsExcelListener(LitemallGoodsImport litemallGoodsImport, AdminGoodsService adminGoodsService) {
        this.litemallGoodsImport = litemallGoodsImport;
        this.adminGoodsService = adminGoodsService;
    }

    @Override
    public void invoke(LitemallGoodsImport litemallGoodsImport, AnalysisContext analysisContext) {
        datas.add(litemallGoodsImport);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        datas.forEach(data -> {
            System.out.println("data:" + data);
            //考虑到导入书籍数量可能上十万，此处使用线程池
            // executorService.execute(()->{
            //    bookInfoService.insertByExcel(data);
            // });
            GoodsAllinone goodsAllinone = buildGoodsAllinone(data);
            adminGoodsService.create(goodsAllinone);
        });
    }

    private GoodsAllinone buildGoodsAllinone(LitemallGoodsImport data) {
        GoodsAllinone goodsAllinone = new GoodsAllinone();
        LitemallGoods litemallGoods = new LitemallGoods();
        // todo
//        litemallGoods.setCategoryId(0);
        litemallGoods.setCounterPrice(data.getCounterPrice());
        litemallGoods.setGallery(new String[]{});
        litemallGoods.setGoodsSn(data.getGoodsSn());
        litemallGoods.setIsHot(false);
        litemallGoods.setIsNew(false);
        litemallGoods.setIsOnSale(false);
        litemallGoods.setName(data.getName());
        goodsAllinone.setGoods(litemallGoods);

        LitemallGoodsProduct[] litemallGoodsProducts = new LitemallGoodsProduct[1];
        LitemallGoodsProduct litemallGoodsProduct = new LitemallGoodsProduct();
        litemallGoodsProduct.setSpecifications(new String[]{"标准"});
        litemallGoodsProduct.setPrice(data.getPrice());
        litemallGoodsProduct.setNumber(data.getStock());
        litemallGoodsProducts[0] = litemallGoodsProduct;
        goodsAllinone.setProducts(litemallGoodsProducts);

        LitemallGoodsSpecification[] litemallGoodsSpecifications = new LitemallGoodsSpecification[1];
        LitemallGoodsSpecification litemallGoodsSpecification = new LitemallGoodsSpecification();
        litemallGoodsSpecification.setSpecification("规格");
        litemallGoodsSpecification.setValue("标准");
        litemallGoodsSpecifications[0] = litemallGoodsSpecification;
        goodsAllinone.setSpecifications(litemallGoodsSpecifications);

        goodsAllinone.setAttributes(new LitemallGoodsAttribute[0]);

        return goodsAllinone;
    }
}
