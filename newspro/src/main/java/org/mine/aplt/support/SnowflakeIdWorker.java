package org.mine.aplt.support;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Twitter_Snowflake<br>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。
 * 41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位dataCenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 *
 * 由于SnowFlake强依赖于时间戳, 当时间回调时可能存在ID重复的情况. 后续做更改. 参考百度开源UidGenerator.
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: SnowflakeIdWorker
 * @date 2020/12/214:27
 */
public class SnowflakeIdWorker {
    // ==============================Fields===========================================
    /** 开始时间截 (2015-01-01) */
    private final long beTamp = 1420041600000L;

    /** 机器id所占的位数 */
    private final long workerIDBits = 5L;

    /** 数据标识id所占的位数 */
    private final long dataCenterIDBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerID = -1L ^ (-1L << workerIDBits);

    /** 支持的最大数据标识id，结果是31 */
    private final long maxDataCenterID = -1L ^ (-1L << dataCenterIDBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    private final long workerIDShift = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
    private final long dataCenterIDShift = sequenceBits + workerIDBits;

    /** 时间截向左移22位(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIDBits + dataCenterIDBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~31) */
    private long workerID;

    /** 数据中心ID(0~31) */
    private long dataCenterID;

    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    private static volatile SnowflakeIdWorker snowflakeIdWorker;

    //==============================Singleton method=====================================
    /**
    * 单例方法
    * @param isRandom 是否启动随机数
    * @return: org.mine.aplt.support.SnowflakeIdWorker
    * @Author: wntl
    * @Date: 2020/12/3
    */
    public static SnowflakeIdWorker getSnowflakeIdWorker(boolean isRandom) {
        if (snowflakeIdWorker == null) {
            synchronized (SnowflakeIdWorker.class) {
                if (snowflakeIdWorker == null) {
                    snowflakeIdWorker = new SnowflakeIdWorker(isRandom);
                }
            }
        }
        return snowflakeIdWorker;
    }

    //==============================Constructors=====================================
    /**
     * 构造函数
     * @param isRandom 是否启动随机数
     */
    private SnowflakeIdWorker(boolean isRandom) {
        if (!isRandom) {
            this.workerID = getWorkerId();
            this.dataCenterID = getDataCenterId();
        } else {
            Random rd = new Random(System.currentTimeMillis());
            this.workerID = rd.nextInt((int)maxWorkerID + 1);
            this.dataCenterID = rd.nextInt((int)maxDataCenterID + 1);
        }
        if (workerID > maxWorkerID || workerID < 0) {
            throw new IllegalArgumentException(String.format("worker ID can't be greater than %d or less than 0", maxWorkerID));
        }
        if (dataCenterID > maxDataCenterID || dataCenterID < 0) {
            throw new IllegalArgumentException(String.format("dataCenter ID can't be greater than %d or less than 0", maxDataCenterID));
        }
    }

    /**
     * 构造函数
     * @param workerID 工作ID (0~31)
     * @param dataCenterID 数据中心ID (0~31)
     */
//    public SnowflakeIdWorker(long workerID, long dataCenterID) {
//        if (workerID > maxWorkerID || workerID < 0) {
//            throw new IllegalArgumentException(String.format("worker ID can't be greater than %d or less than 0", maxWorkerID));
//        }
//        if (dataCenterID > maxDataCenterID || dataCenterID < 0) {
//            throw new IllegalArgumentException(String.format("dataCenter ID can't be greater than %d or less than 0", maxDataCenterID));
//        }
//        this.workerID = workerID;
//        this.dataCenterID = dataCenterID;
//    }

    // ==============================Methods==========================================
    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - beTamp) << timestampLeftShift) | (dataCenterID << dataCenterIDShift)
                | (workerID << workerIDShift) | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    protected static Long getDataCenterId() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        try {
            Long binarySum = getBinarySum(InetAddress.getLocalHost().getHostName());
            return binarySum > 0L ? binarySum % 32 : tlr.nextLong(0, 31);
        } catch (UnknownHostException e) {
            return tlr.nextLong(0, 31);
        }
    }

    protected static Long getWorkerId() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        try {
            Long binarySum = getBinarySum(InetAddress.getLocalHost().getHostAddress());
            return binarySum > 0L ? binarySum % 32 : tlr.nextLong(0, 31);
        } catch (UnknownHostException e) {
            return tlr.nextLong(0, 31);
        }
    }

    protected static Long getBinarySum(String str) {
        Long sum = 0L;
        char[] cs = str.toCharArray();
        for (char c : cs) {
            sum += Integer.parseInt(Integer.toString(c, 10));
        }
        return sum;
    }

    /** 测试 */
    public static void main(String[] args) {
//        for (int i = 0; i < 5; i++) {
//            long id = getSnowflakeIdWorker().nextId();
//            System.out.println(id);
//        }

//        for (int i = 0; i < 10; i++) {
//            SnowflakeIdWorker sf = new SnowflakeIdWorker(i, 0, true);
//            for (int j = 0; j < 5; j++) {
//                System.out.println(sf.nextId());
//            }
//            System.out.println("====================");
//        }

//        System.out.println(getWorkerId());
        for (int i = 0; i < 6; i++) {
            Random rm = new Random(System.currentTimeMillis());
            System.out.println(rm.nextInt(32));
            System.out.println(rm.nextInt(32));
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
}
