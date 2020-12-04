package org.mine.lock;

import org.mine.aplt.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: SystemIdWin
 * @date 2020/11/2315:12
 */
@Component
public class SystemIdWin {
    private final Logger log = LoggerFactory.getLogger(SystemIdWin.class);
    private static final String SYSTEM_ID = "systemID";
    private static final ConcurrentMap<String, Object> systemInfoMap = new ConcurrentHashMap<>(1 << 8);

    public String systemID() {
        String systemID = (String)systemInfoMap.get(SYSTEM_ID);
        if (CommonUtils.isEmpty(systemID)) {
            try {
                Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
                Scanner sn = new Scanner(process.getInputStream());
                StringBuilder builder = new StringBuilder();
                while (sn.hasNext()) {
                    builder.append(sn.next());
                }
                systemID = DigestUtils.md5DigestAsHex(builder.toString().getBytes(StandardCharsets.UTF_8));
                systemInfoMap.put(SYSTEM_ID, systemID);
                sn.close();
            } catch (IOException e) {
                log.error("get systemID error : ", e);
            }
        }
        return systemID;
    }
}
