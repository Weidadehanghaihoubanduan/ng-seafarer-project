package com.bestpay.seafarer.core.jmeter.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author dengyancan
 * @Date: 2023-11-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Loop implements Serializable {

    /**
     * 控制器名称
     */
    private String name;

    /**
     * 控制器注释
     */
    private String comment;

    /**
     * 循环次数
     */
    private Integer loops;


    private static final long serialVersionUID = -3275189229678386220L;
}
