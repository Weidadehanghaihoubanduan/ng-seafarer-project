package com.bestpay.seafarer.api;

import com.bestpay.seafarer.api.model.request.NgSeafarerDto;
import com.bestpay.seafarer.api.model.result.NgSeafarerVo;

/**
 * @author dengyancan
 * @Date: 2023-12-28
 */
public interface NgSeafarer {

    /**
     * 层级示例
     */
    NgSeafarerVo ngSeafarer(NgSeafarerDto ngSeafarerDto);
}
