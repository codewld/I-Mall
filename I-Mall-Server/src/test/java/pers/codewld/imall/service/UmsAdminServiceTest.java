package pers.codewld.imall.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UmsAdminServiceTest {

    @Autowired
    UmsAdminService umsAdminService;

    @Test
    public void testGetBlacklist() {
        List<Long> list = umsAdminService.getBlacklist();
        for (Long aLong : list) {
            System.out.println(aLong);
        }
    }

    @Test
    public void testIsInBlacklist() {
        System.out.println(umsAdminService.isInBlacklist(3L));
        System.out.println(umsAdminService.isInBlacklist(5L));
    }
}
