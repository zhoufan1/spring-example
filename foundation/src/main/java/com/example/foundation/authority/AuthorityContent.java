package com.example.foundation.authority;

/**
 * @author zhoufan
 * @date 2019/6/3 18:12
 * @description
 */
public final class AuthorityContent {
    private AuthoritySession authoritySession;
    private static ThreadLocal<AuthorityContent> holder = ThreadLocal.withInitial(AuthorityContent::new);

    public static AuthorityContent get() {
        return holder.get();
    }

    public AuthoritySession getAuthoritySession() {
        return authoritySession;
    }

    public void setAuthoritySession(AuthoritySession authoritySession) {
        this.authoritySession = authoritySession;
    }

    public void clear() {
        holder.remove();
    }

}
