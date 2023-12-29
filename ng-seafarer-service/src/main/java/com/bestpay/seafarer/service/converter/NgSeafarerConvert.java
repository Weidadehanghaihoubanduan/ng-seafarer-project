package com.bestpay.seafarer.service.converter;

import com.bestpay.seafarer.api.model.request.NgSeafarerDto;
import com.bestpay.seafarer.api.model.result.NgSeafarerVo;
import com.bestpay.seafarer.api.model.bo.NgSeafarerBo;

/**
 * @author dengyancan
 * @Date: 2023-12-28
 */
public class NgSeafarerConvert {

    public static NgSeafarerBo convertNgSeafarerDtoToBo(NgSeafarerDto ngSeafarerDto) {

        NgSeafarerBo seafarerBo = new NgSeafarerBo();

        if(ngSeafarerDto == null) {  return seafarerBo;  }

        seafarerBo.setHanghaijia(ngSeafarerDto.getHanghaijia());
        return seafarerBo;
    }

    public static NgSeafarerVo convertNgSeafarerBoToVo(NgSeafarerBo ngSeafarerBo) {

        NgSeafarerVo ngSeafarerVo = new NgSeafarerVo();

        if(ngSeafarerBo == null) {  return ngSeafarerVo;  }

        ngSeafarerVo.setHanghaijia(ngSeafarerBo.getHanghaijia());
        return ngSeafarerVo;
    }
}
