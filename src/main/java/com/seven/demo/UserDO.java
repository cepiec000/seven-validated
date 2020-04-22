package com.seven.demo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/**
 * @Description: TODO
 * @Author chendongdong
 * @Date 2020/4/21 11:12
 * @Version V1.0
 **/
@Data
public class UserDO {
    /**
     * @NotNull     任何类型                 属性不能为null
     * @NotEmpty    集合                    集合不能为null，且size大于0
     * @NotBlanck   字符串、字符              字符类不能为null，且去掉空格之后长度大于0
     * @AssertTrue  Boolean                 boolean	布尔属性必须是true
     * @Min         数字类型（原子和包装）	    限定数字的最小值（整型）
     * @Max         同@Min	                限定数字的最大值（整型）
     * @DecimalMin  同@Min	                限定数字的最小值（字符串，可以是小数）
     * @DecimalMax  同@Min	                限定数字的最大值（字符串，可以是小数）
     * @Range       数字类型（原子和包装）	    限定数字范围（长整型）
     * @Length      字符串                   限定字符串长度
     * @Size        集合                     限定集合大小
     * @Past        时间、日期	            必须是一个过去的时间或日期
     * @Future      时期、时间	            必须是一个未来的时间或日期
     * @Email       字符串                   必须是一个邮箱格式
     * @Pattern     字符串、字符	            正则匹配字符串
     */


    /**
     * @Max BigDecimal、BigInteger，byte、short、int、long以及包装类	小于或等于	为null有效
     * @Min BigDecimal、BigInteger，byte、short、int、long以及包装类	大于或等于	为null有效
     * @DecimalMax BigDecimal、BigInteger、CharSequence，byte、short、int、long以及包装类	小于或等于	为null有效
     * @DecimalMin BigDecimal、BigInteger、CharSequence，byte、short、int、long以及包装类	大于或等于	为null有效
     * @Negative BigDecimal、BigInteger，byte、short、int、long、float、double以及包装类	负数	为null有效，0无效
     * @NegativeOrZero BigDecimal、BigInteger，byte、short、int、long、float、double以及包装类	负数或零	为null有效
     * @Positive BigDecimal、BigInteger，byte、short、int、long、float、double以及包装类	正数	为null有效，0无效
     * @PositiveOrZero BigDecimal、BigInteger，byte、short、int、long、float、double以及包装类	正数或零	为null有效
     * @Digits(integer = 3, fraction = 2)	BigDecimal、BigInteger、CharSequence，byte、short、int、long以及包装类	整数位数和小数位数上限	为null有效
     *
     * 作者：TurboSnail
     * 链接：https://www.jianshu.com/p/3267689ebf1b
     *
     */
    private static final int MAX_BALANCE = 1_000_000;
    @NotBlank(message = "姓名不能为空")
    private String name;
    @NotNull(message = "地址不能为空")
    private String address;
    @NotNull(message = "年龄不能为空，且18-80岁之间")
    @Range(min = 18,max = 80,message = "年龄不能为空，且18-80岁之间")
    private Integer age;
    @NotNull(message = "密码不能为空，长度为6-9位数")
    @Length(min = 6,max = 10,message = "密码不能为空，长度为6-9位数")
    private String password;
    @NotNull(message = "邮件不能为空")
    @Email(message = "邮件格式有误")
    private String email;
    @NotNull(message = "余额不能为空")
    @Max(value = MAX_BALANCE, message = "余额不能超过 " + MAX_BALANCE)
    @PositiveOrZero(message = "余额必须大于等于0")
    private Double balance;
    @PositiveOrZero(message = "余额必须大于等于0")
    private Integer sex;
}
