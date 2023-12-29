package com.bestpay.seafarer.service;

import com.bestpay.seafarer.api.NgSeafarer;
import com.bestpay.seafarer.api.model.request.NgSeafarerDto;
import com.bestpay.seafarer.api.model.result.NgSeafarerVo;
import com.bestpay.seafarer.core.NgSeafarerHandler;
import com.bestpay.seafarer.api.model.bo.NgSeafarerBo;
import com.bestpay.seafarer.service.converter.NgSeafarerConvert;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;


/**
 * @author dengyancan
 * @Date: 2023-12-28
 */
@DubboService
public class NgSeafarerImpl implements NgSeafarer {

    @Resource private NgSeafarerHandler ngSeafarerHandler;

    public NgSeafarerVo ngSeafarer(NgSeafarerDto ngSeafarerDto) {

        //其他逻辑
        NgSeafarerBo seafarerBo = NgSeafarerConvert.convertNgSeafarerDtoToBo(ngSeafarerDto);
        NgSeafarerBo seafarer = ngSeafarerHandler.ngSeafarerHandler(seafarerBo);
        return NgSeafarerConvert.convertNgSeafarerBoToVo(seafarer);
    }
}
