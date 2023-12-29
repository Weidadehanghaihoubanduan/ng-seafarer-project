package com.bestpay.seafarer.core;

import com.bestpay.seafarer.api.model.bo.NgSeafarerBo;
import com.bestpay.seafarer.manager.NgSeafarerManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author dengyancan
 * @Date: 2023-12-28
 */
@Slf4j
@Service
public class NgSeafarerHandler {

    @Resource private NgSeafarerManager ngSeafarerManager;

    public NgSeafarerBo ngSeafarerHandler(NgSeafarerBo ngSeafarerBo) {

        //其他逻辑
        return ngSeafarerManager.queryNgSeafarer(ngSeafarerBo);

    }
}
