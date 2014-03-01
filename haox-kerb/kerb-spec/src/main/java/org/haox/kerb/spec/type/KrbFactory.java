package org.haox.kerb.spec.type;

import org.haox.kerb.spec.*;

public class KrbFactory {
    private static KrbFactory instance = new KrbFactory();

    public static void register(KrbFactory factory) {
        instance = factory;
    }

    private static KrbFactory get() {
        return instance;
    }

    public static <T extends KrbType> T create(Class<T> type) throws KrbException {
        return get().createOf(type);
    }

    protected <T extends KrbType> T createOf(Class<T> type) throws KrbException {
        try {
            Class<? extends KrbType> classType = null;
            if (type.isInterface()) {
                classType = KrbTypes.getImpl(type);
            } else {
                classType = type;
            }
            return (T) classType.newInstance();
        } catch (InstantiationException e) {
            KrbThrow.out(KrbTypeMessageCode.INVALID_KRB_TYPE);
        } catch (IllegalAccessException e) {
            KrbThrow.out(KrbTypeMessageCode.INVALID_KRB_TYPE);
        }

        return null;
    }
}
