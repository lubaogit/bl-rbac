package com.bl.rbac.api.kit;

public class UserIdKit {

    /**
     * 雪花算法解析 结构 snowflake的结构如下(每部分用-分开):
     * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
     * 第一位为未使用，接下来的41位为毫秒级时间(41位的长度可以使用69年)，然后是5位datacenterId和5位workerId(10
     * 位的长度最多支持部署1024个节点） ，最后12位是毫秒内的计数（12位的计数顺序号支持每个节点每毫秒产生4096个ID序号）
     *
     * 一共加起来刚好64位，为一个Long型。(转换成字符串长度为18)
     *
     */


    /** 开始时间截 (2015-01-01) */
    public final long twepoch = 1489111610226L;

    /** 机器id所占的位数 */
    public final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    public final long dataCenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    public final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
    public final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

    /** 序列在id中占的位数 */
    public final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    public final long workerIdShift = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
    public final long dataCenterIdShift = sequenceBits + workerIdBits;

    /** 时间截向左移22位(5+5+12) */
    public final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    public final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~31) */
    public long workerId;

    /** 数据中心ID(0~31) */
    public long dataCenterId;

    /** 毫秒内序列(0~4095) */
    public long sequence = 0L;

    /** 上次生成ID的时间截 */
    public long lastTimestamp = -1L;

    /**
     * 构造函数
     * @param workerId 工作ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)
     */

    public UserIdKit(long workerId, long dataCenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("workerId can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("dataCenterId can't be greater than %d or less than 0", maxDataCenterId));
        }
        workerId = workerId;
        dataCenterId = dataCenterId;
    }

    // ==============================Methods==========================================
    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        // 如果是同一时间生成的，则进行毫秒内序列
        // sequenceMask 为啥是4095  2^12 = 4096
        if (lastTimestamp == timestamp) {
            // 每次+1
            sequence = (sequence + 1) & sequenceMask;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        // 时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        // 上次生成ID的时间截
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的ID
        // 为啥时间戳减法向左移动22 位 因为  5位datacenterid
        // 为啥 datCenterID向左移动17位 因为 前面有5位workid  还有12位序列号 就是17位
        //为啥 workerId向左移动12位 因为 前面有12位序列号 就是12位
        System.out.println(((timestamp - twepoch) << timestampLeftShift)
                | (dataCenterId <<dataCenterIdShift)
                | (workerId << workerIdShift)
                | sequence);
        return ((timestamp - twepoch) << timestampLeftShift)
                | (dataCenterId << dataCenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
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
}
