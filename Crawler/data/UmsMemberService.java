2
https://raw.githubusercontent.com/gavin-yyj/vhr-/master/security-jwt/src/main/java/com/yyj/security/service/UmsMemberService.java
package com.yyj.security.service;


import com.yyj.security.common.api.CommonResult;

/**
 * 会员管理Service
 * Created by macro on 2018/8/3.
 */
public interface UmsMemberService {

    /**
     * 生成验证码
     */
    CommonResult generateAuthCode(String telephone);

    /**
     * 判断验证码和手机号码是否匹配
     */
    CommonResult verifyAuthCode(String telephone, String authCode);

}
