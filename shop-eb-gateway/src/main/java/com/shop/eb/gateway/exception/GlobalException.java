package com.shop.eb.gateway.exception;

import com.shop.be.common.result.CodeMsg;

/**
 * 全局异常处理器
 *
 * @author noodle
 */
public class GlobalException extends RuntimeException {

    private CodeMsg codeMsg;

    /**
     * 使用构造器接收CodeMsg
     *
     * @param codeMsg
     */
    public GlobalException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
