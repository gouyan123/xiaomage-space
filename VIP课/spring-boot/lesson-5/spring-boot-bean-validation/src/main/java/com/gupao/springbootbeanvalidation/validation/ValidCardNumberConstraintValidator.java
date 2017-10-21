package com.gupao.springbootbeanvalidation.validation;

import com.gupao.springbootbeanvalidation.validation.constraints.ValidCardNumber;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * {@link ValidCardNumber} {@link ConstraintValidator} 实现
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-21
 **/
public class ValidCardNumberConstraintValidator implements
        ConstraintValidator<ValidCardNumber, String> {

    public void initialize(ValidCardNumber validCardNumber) {
    }

    /**
     * 需求：通过员工的卡号来校验，需要通过工号的前缀和后缀来判断
     * <p>
     * 前缀必须以"GUPAO-"
     * <p>
     * 后缀必须是数字
     * <p>
     * 需要通过 Bean Validator 检验
     *
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // 前半部分和后半部分

        String[] parts = StringUtils.split(value, "-");

        // 为什么不用 String#split 方法，原因在于该方法使用了正则表达式
        // 其次是 NPE 保护不够
        // 如果在依赖中，没有 StringUtils.delimitedListToStringArray API 的话呢，可以使用
        // Apache commons-lang StringUtils
        // JDK 里面 StringTokenizer（不足类似于枚举 Enumeration API）

        if (ArrayUtils.getLength(parts) != 2) {
            return false;
        }

        String prefix = parts[0];
        String suffix = parts[1];

        boolean isValidPrefix = Objects.equals(prefix, "GUPAO");

        boolean isValidInteger = StringUtils.isNumeric(suffix);

        return isValidPrefix && isValidInteger;
    }
}
