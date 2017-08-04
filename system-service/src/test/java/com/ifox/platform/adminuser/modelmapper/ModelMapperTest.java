package com.ifox.platform.adminuser.modelmapper;

import com.ifox.platform.adminuser.dto.AdminUserDTO;
import com.ifox.platform.entity.sys.AdminUserEO;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelMapperTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testModelMapper() {

        AdminUserEO adminUserEO = new AdminUserEO();
        adminUserEO.setLoginName("Yeager");
        adminUserEO.setBuildinSystem(true);
        adminUserEO.setStatus(AdminUserEO.AdminUserEOStatus.ACTIVE);

        ModelMapper modelMapper = new ModelMapper();
        AdminUserDTO adminUserDTO = modelMapper.map(adminUserEO, AdminUserDTO.class);

        logger.info(adminUserDTO.toString());

    }

}
