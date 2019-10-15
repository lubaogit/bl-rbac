package com.bl.rbac.util.cache;

public class RedisConstant {

    public static final String CACHE_KEY = "bl_rbac";

    public static final String USER_KEY = CACHE_KEY+":user:%s";

    public static final String USER_INFO = USER_KEY+":info";

    public static final String USER_ROLE = USER_KEY+":role";

    public static final String USER_PERMISSION = USER_KEY+":permission";

    public static final String USER_TOKEN = USER_KEY+":token";

    /**
     * 缓存有效期24h
     */
    public static final long REDIS_TIME_OUT =3600*24L;

    public static String format(String template,Object params){return String.format(template,params);};
}
