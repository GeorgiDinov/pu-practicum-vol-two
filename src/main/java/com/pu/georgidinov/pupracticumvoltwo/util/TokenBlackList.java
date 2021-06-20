package com.pu.georgidinov.pupracticumvoltwo.util;

import java.util.HashSet;
import java.util.Set;

public final class TokenBlackList {

    private static final Set<String> blackList = new HashSet<>();


    private TokenBlackList() {
    }

    public static TokenBlackList getInstance() {
        return TokenBlackListHelper.INSTANCE;
    }

    private static class TokenBlackListHelper {
        private static final TokenBlackList INSTANCE = new TokenBlackList();
    }


    public void addToken(String token) {
        blackList.add(token);
    }

    public boolean containsToken(String token) {
        return blackList.contains(token);
    }

}