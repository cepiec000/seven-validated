package com.seven.demo;

import com.seven.common.ApiResult;
import com.seven.error.BusinessException;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

/**
 * @Description: TODO
 * @Author chendongdong
 * @Date 2020/4/21 16:26
 * @Version V1.0
 **/
@RestController
@RequestMapping("/user/")
@Validated
public class UserController {

    @GetMapping("get/{id}")
    public ApiResult<UserDO> getAccount(
            @Positive(message = "主键必须大于0")
            @PathVariable("id") Integer id) {
        return ApiResult.success(new UserDO());
    }

    @PostMapping(value = "add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult<Integer> add(@Validated @RequestBody UserDO account) {
        return new ApiResult<>(1);
    }

    @GetMapping("/testBusinessException")
    public ApiResult<?> testBusinessException() {
        throw new BusinessException("对不起，发生了一个业务异常，请稍候重试");
    }

    @GetMapping("/testUnknownException")
    public ApiResult<?> testUnknownException() throws Exception {
        throw new Exception("好好反省");
    }
}
