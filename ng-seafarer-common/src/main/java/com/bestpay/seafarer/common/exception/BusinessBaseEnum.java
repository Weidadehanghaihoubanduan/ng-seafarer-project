package com.bestpay.seafarer.common.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: dengyancan
 * @Date: 2023/12/15 16:52
 */
@Getter
@AllArgsConstructor
public enum BusinessBaseEnum {

    /**
     * 数据错误
     */
    DB_ERROR(400, "Db Error"),

    /**
     * 重复提交校验
     */
    REPEAT_NOT_SUBMISSION(400, "Do not repeat submission"),

    /**
     * 对象参数不存在
     */
    OBJECT_NOT_FOUND(400,"Object parameters do not exist"),

    /**
     * 参数验证不存在
     */
    INVALID_PARAMS(400,"Parameter validation does not exist"),

    /**
     * 处理成功分片数少于总分片的情况
     */
    ReplicationResponse_ShardInfo(400, "the number of successful slices is less than the total number of slices"),

    /**
     * 处理潜在故障
     */
    ReplicationResponse_ShardInfo_Failure(400, "Details of potential failures");

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 提示信息
     */
    private final String msg;
}
