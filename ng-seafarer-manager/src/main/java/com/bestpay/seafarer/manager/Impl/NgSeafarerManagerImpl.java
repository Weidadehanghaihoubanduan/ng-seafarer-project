package com.bestpay.seafarer.manager.Impl;

import com.bestpay.seafarer.api.model.bo.NgSeafarerBo;
import com.bestpay.seafarer.dao.NgSeafarerDao;
import com.bestpay.seafarer.manager.NgSeafarerManager;
import com.bestpay.seafarer.manager.converter.NgSeafarerConvert;
import com.bestpay.seafarer.api.model.dao.NgSeafarerDo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author dengyancan
 * @Date: 2023-12-28
 */
@Slf4j
@Repository
public class NgSeafarerManagerImpl implements NgSeafarerManager {

    @Resource private NgSeafarerDao ngSeafarerDao;
    public NgSeafarerBo queryNgSeafarer(NgSeafarerBo ngSeafarerBo) {

        //其他逻辑
        NgSeafarerDo seafarerDo = NgSeafarerConvert.convertNgSeafarerBoToDo(ngSeafarerBo);
        NgSeafarerDo ngSeafarer = ngSeafarerDao.queryNgSeafarer(seafarerDo);
        return NgSeafarerConvert.convertNgSeafarerDoToBo(ngSeafarer);
    }
}
