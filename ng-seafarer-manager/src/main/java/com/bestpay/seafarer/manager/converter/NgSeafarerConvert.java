package com.bestpay.seafarer.manager.converter;


import com.bestpay.seafarer.api.model.bo.NgSeafarerBo;
import com.bestpay.seafarer.api.model.dao.NgSeafarerDo;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
public class NgSeafarerConvert {

    public static NgSeafarerDo convertNgSeafarerBoToDo(NgSeafarerBo ngSeafarerBo) {

        NgSeafarerDo seafarerDo = new NgSeafarerDo();

        if(ngSeafarerBo == null) {  return seafarerDo;  }

        seafarerDo.setHanghaijia(ngSeafarerBo.getHanghaijia());
        return seafarerDo;
    }

    public static NgSeafarerBo convertNgSeafarerDoToBo(NgSeafarerDo ngSeafarerDo) {

        NgSeafarerBo seafarerBo = new NgSeafarerBo();

        if(ngSeafarerDo == null) {  return seafarerBo;  }

        seafarerBo.setHanghaijia(ngSeafarerDo.getHanghaijia());
        return seafarerBo;
    }
}
