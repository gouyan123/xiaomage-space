package com.gupao.springbootbeanvalidation.domain;



import com.gupao.springbootbeanvalidation.validation.constraints.ValidCardNumber;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 用户模型
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-21
 **/
public class User {

    @Max(value = 10000)
    private long id;

    @NotNull
    //@NotNull
    //@NonNull
    private String name;

    // 卡号 -- GUPAO-123456789
    @NotNull
    @ValidCardNumber
    private String cardNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
