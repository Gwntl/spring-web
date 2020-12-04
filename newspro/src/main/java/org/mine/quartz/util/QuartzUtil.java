package org.mine.quartz.util;

import org.mine.aplt.constant.JobConstant;
import org.mine.aplt.util.CommonUtils;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: QuartzUtil
 * @date 2020/12/115:41
 */
public class QuartzUtil {
    /**
     * 判断是否并发
     * @param value
     * @return
     */
    public static boolean isCct(String value) {
        if (value.contains(JobConstant.CCT_FLAG)) {
            String sufValue = value.substring(value.indexOf(JobConstant.CCT_FLAG));
            String cctFlag = sufValue.substring(sufValue.indexOf("=") + 1, sufValue.indexOf(";"));
            if (CommonUtils.equals(cctFlag, JobConstant.CCT_FLAG_1)) {
                return true;
            }
        }
        return false;
    }
}
