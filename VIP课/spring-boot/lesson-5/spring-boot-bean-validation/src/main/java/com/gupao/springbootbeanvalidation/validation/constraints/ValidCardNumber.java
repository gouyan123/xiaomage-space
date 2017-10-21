package com.gupao.springbootbeanvalidation.validation.constraints;

import com.gupao.springbootbeanvalidation.validation.ValidCardNumberConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 合法 卡号校验
 * 需求：通过员工的卡号来校验，需要通过工号的前缀和后缀来判断
 * <p>
 * 前缀必须以"GUPAO-"
 * <p>
 * 后缀必须是数字
 * <p>
 * 需要通过 Bean Validator 检验
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-21
 **/
@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ValidCardNumberConstraintValidator.class})
public @interface ValidCardNumber {

    String message() default "{com.gupao.bean.validation.invalid.card.number.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
