package org.mine.quartz.dto;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: JobOperatorDto
 * @date 2020/9/2816:48
 */
public class JobOperatorDto {
    /**
     * 返回信息
     */
    private String info;
    /**
     * 执行标志
     */
    private boolean flag;

    public JobOperatorDto() {
        this.info = "";
        this.flag = false;
    }

    /**
     * 返回信息
     * @return the info as info
     */
    public String getInfo() {
        return info;
    }

    /**
     * 返回信息
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * 执行标志
     * @return the flag as flag
     */
    public boolean getFlag() {
        return flag;
    }

    /**
     * 执行标志
     * @param flag the flag to set
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "JobOperatorDto{" + "info='" + info + '\'' + ", flag=" + flag + '}';
    }
}
