package com.example.foundation.authority;

/**
 * @author zhoufan
 * @date 2019/6/3 18:12
 * @description
 */
public final class AuthorityContent {
    private AuthoritySession authoritySession;
    private ThreadLocal<AuthorityContent> holder = ThreadLocal.withInitial(AuthorityContent::new);

    private AuthorityContent get() {
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
