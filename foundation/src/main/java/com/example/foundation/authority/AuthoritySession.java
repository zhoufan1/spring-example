package com.example.foundation.authority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoufan
 * @date 2019/6/3 18:11
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthoritySession {
    public static final String X_Authority_Header = "X-Authority";
    private Integer Id;
    private String version;
    private String clientIp;
    private Long clientTime;

}
