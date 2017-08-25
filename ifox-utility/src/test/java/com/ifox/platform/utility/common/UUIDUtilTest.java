package com.ifox.platform.utility.common;

import org.junit.Test;

import java.util.UUID;

public class UUIDUtilTest {

    @Test
    public void testRandomUUID() {
        String randomUUID = UUIDUtil.randomUUID();
        String uuid = UUID.randomUUID().toString();
        System.out.println("randomUUID : " + randomUUID);
        System.out.println("uuid : " + uuid);
    }

}
