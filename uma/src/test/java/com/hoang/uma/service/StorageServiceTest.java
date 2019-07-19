package com.hoang.uma.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * homertruong
 */

@RunWith(SpringRunner.class)
public class StorageServiceTest {

    private StorageService service = new StorageServiceImpl();

    @Test
    public void testStore () {
        service.store(null);
    }

}
