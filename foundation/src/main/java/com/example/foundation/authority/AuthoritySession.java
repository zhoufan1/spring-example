package com.example.foundation.authority;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhoufan
 * @date 2019/6/3 18:11
 * @description
 */
@Data
@AllArgsConstructor
public class AuthoritySession {
    private String userId;
    private String version;
    private String clientIp;
    private Long clientTime;

}
