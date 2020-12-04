package org.mine.aplt.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.RedisZSetCommands.Aggregate;
import org.springframework.data.redis.connection.RedisZSetCommands.Limit;
import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: CustomRedisTemplate
 * @date 2020/11/613:50
 */
@Component
public class CustomRedisTemplate {
    @Autowired
    private RedisTemplate redisTemplate;

    public CustomRedisTemplate() {}

    RedisSerializer keySerializer() {
        return redisTemplate.getKeySerializer();
    }

    RedisSerializer valueSerializer() { return redisTemplate.getValueSerializer(); }

    @SuppressWarnings("unchecked")
    <K> byte[] rawKey(K key) {
        Assert.notNull(key, "non null key required");
        Assert.notNull(keySerializer(), "non null keySerializer required");
        return this.keySerializer() == null && key instanceof byte[] ? (byte[])((byte[])key) : this.keySerializer().serialize(key);
    }

    @SuppressWarnings("unchecked")
    byte[] rawValue(Object value) {
        Assert.notNull(value, "non null value required");
        Assert.notNull(valueSerializer(), "non null valueSerializer required");
        return this.valueSerializer() == null && value instanceof byte[] ? (byte[]) value : this.valueSerializer().serialize(value);
    }

    <K> byte[][] rawKeys(K key, Collection<K> keys) {
        byte[][] rawKeys = new byte[keys.size() + (key != null ? 1 : 0)][];
        int i = 0;
        if (key != null) {
            rawKeys[i++] = this.rawKey(key);
        }
        Object k;
        for(Iterator var5 = keys.iterator(); var5.hasNext(); rawKeys[i++] = this.rawKey(k)) {
            k = var5.next();
        }
        return rawKeys;
    }

    @SuppressWarnings("unchecked")
    <V> Set<TypedTuple<V>> deserializeTupleValues(Collection<Tuple> rawValues) {
        if (rawValues == null) {
            return null;
        } else {
            Set<TypedTuple<V>> set = new LinkedHashSet(rawValues.size());
            Iterator var3 = rawValues.iterator();
            while(var3.hasNext()) {
                Tuple rawValue = (Tuple)var3.next();
                set.add(this.deserializeTuple(rawValue));
            }
            return set;
        }
    }

    @SuppressWarnings("unchecked")
    <V> TypedTuple<V> deserializeTuple(Tuple tuple) {
        Object value = tuple.getValue();
        if (this.valueSerializer() != null) {
            value = this.valueSerializer().deserialize(tuple.getValue());
        }
        return new DefaultTypedTuple(value, tuple.getScore());
    }


    public Boolean selectDB(final int index) {
        return (Boolean)redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) {
                connection.select(index);
                return true;
            }
        }, true);
    }

    public Long dbSize() {
        return (Long)redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) {
                return connection.dbSize();
            }
        }, true);
    }

    public Boolean flushDB() {
        return (Boolean)redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) {
                connection.flushDb();
                return true;
            }
        }, true);
    }

    public Boolean flushAll() {
        return (Boolean)redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) {
                connection.flushAll();
                return true;
            }
        }, true);
    }

    /** 以下为字符串操作 **/
    public Boolean setIfAbsent(String key, Object value, Long timeOut) {
        final byte[] rawKey = rawKey(key);
        final byte[] rawValue = rawValue(value);
        final Expiration expiration = Expiration.seconds(timeOut);
        return (Boolean)redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) {
                connection.set(rawKey, rawValue, expiration, StringRedisConnection.SetOption.ifAbsent());
                return true;
            }
        }, true);
    }


    public Long incr(Object key) {
        final byte[] rawKey = rawKey(key);
        return (Long)redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) {
                return connection.incr(rawKey);
            }
        }, true);
    }

    public Long decr(Object key) {
        final byte[] rawKey = rawKey(key);
        return (Long)redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) {
                return connection.decr(rawKey);
            }
        }, true);
    }

    public Long decrementBy(String k, final Long l) {
        final byte[] rawkey = rawKey(k);
        return (Long)redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) {
                return connection.decrBy(rawkey, l);
            }
        }, true);
    }

    public Long countBit(String key) {
        final byte[] rawkey = rawKey(key);
        return (Long)redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) {
                return connection.bitCount(rawkey);
            }
        }, true);
    }

    public Long countBit(String key, final Long start, final Long end) {
        final byte[] rawkey = rawKey(key);
        return (Long)redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) {
                return connection.bitCount(rawkey, start, end);
            }
        }, true);
    }

    public Long bitOp(final RedisStringCommands.BitOperation operator, String destinationKey, String... keys) {
        int length = keys.length;
        final byte[] destinationRawKey = rawKey(destinationKey);
        final byte[][] bytes= new byte[length][];
        for (int i = 0; i < length; i++) {
            bytes[i] = rawKey(keys[i]);
        }
        return (Long)redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) {
                return connection.bitOp(operator, destinationRawKey, bytes);
            }
        }, true);
    }
    /** 以上为字符串操作 **/


    /***以下为HASH操作**/
    /**以上为HASH操作**/

    /**以下为LIST操作**/
    public Object getValueOnIndex(String key, final int index) {
        final byte[] rawKey = this.rawKey(key);
        return redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                return valueSerializer().deserialize(connection.lIndex(rawKey, index));
            }
        }, true);
    }
    /**以上为LIST操作**/

    /**以下为ZSet操作**/

    /**
    * 返回有序集合内指定分数区间的元素.根据Operation来判断开闭区间.
    * @param key
    * @param input
    * @return: java.util.Set<V>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    @SuppressWarnings("unchecked")
    public <K, V> Set<V> rangeByScore(K key, ZSetOperatorInput input) {
        final byte[] rawKey = this.rawKey(key);
        final Range range = getRange(input.getMin(), input.getMax(), input.getOperation());
        if (range == null) {
            return null;
        }
        final Limit limit = getLimit(input.getOffset(), input.getCount());
        Set<byte[]> rawValues = (Set)redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            public Set<byte[]> doInRedis(RedisConnection connection) {
                return connection.zRangeByScore(rawKey, range, limit);
            }
        }, true);
        return SerializationUtils.deserialize(rawValues, valueSerializer());
    }

    @SuppressWarnings("unchecked")
    public <K, V> Set<V> reverseRangeByScore(K key, ZSetOperatorInput input) {
        final byte[] rawKey = this.rawKey(key);
        final Range range = getRange(input.getMin(), input.getMax(), input.getOperation());
        if (range == null) {
            return null;
        }
        final Limit limit = getLimit(input.getOffset(), input.getCount());
        Set<byte[]> rawValues = (Set)redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            public Set<byte[]> doInRedis(RedisConnection connection) {
                return connection.zRevRangeByScore(rawKey, range, limit);
            }
        }, true);
        return SerializationUtils.deserialize(rawValues, valueSerializer());
    }

    @SuppressWarnings("unchecked")
    public <K, V> Set<TypedTuple<V>> rangeByScoreWithScores(K key, ZSetOperatorInput input) {
        final byte[] rawKey = this.rawKey(key);
        final Range range = getRange(input.getMin(), input.getMax(), input.getOperation());
        if (range == null) {
            return null;
        }
        final Limit limit = getLimit(input.getOffset(), input.getCount());
        Set<Tuple> rawValues = (Set)redisTemplate.execute(new RedisCallback<Set<Tuple>>() {
            public Set<Tuple> doInRedis(RedisConnection connection) {
                return connection.zRangeByScoreWithScores(rawKey, range, limit);
            }
        }, true);
        return this.deserializeTupleValues(rawValues);
    }

    @SuppressWarnings("unchecked")
    public <K,V> Set<TypedTuple<V>> reverseRangeByScoreWithScores(K key, ZSetOperatorInput input) {
        final byte[] rawKey = this.rawKey(key);
        final Range range = getRange(input.getMin(), input.getMax(), input.getOperation());
        if (range == null) {
            return null;
        }
        final Limit limit = getLimit(input.getOffset(), input.getCount());
        Set<Tuple> rawValues = (Set)redisTemplate.execute(new RedisCallback<Set<Tuple>>() {
            public Set<Tuple> doInRedis(RedisConnection connection) {
                return connection.zRevRangeByScoreWithScores(rawKey, range, limit);
            }
        }, true);
        return this.deserializeTupleValues(rawValues);
    }

    Range getRange(Double min, Double max, Integer operation) {
        Range range = null;
        if (operation == Z_SET_RANGE_OPERATION_0) {
            //开区间
            range = Range.range().gt(min).lt(max);
        } else if (operation == Z_SET_RANGE_OPERATION_1) {
            //左开右闭
            range = Range.range().gt(min).lte(max);
        } else if (operation == Z_SET_RANGE_OPERATION_2) {
            //左闭右开
            range = Range.range().gte(min).lt(max);
        }
        return range;
    }

    Limit getLimit(Integer offset, Integer count) {
        if (count != null && offset != null && count > 0 && offset >= 0) {
            return Limit.limit().offset(offset).count(count);
        }
        return null;
    }

    public Long intersectAndStore(String key, Collection<String> otherKeys, String destKey, final int[] weights,
            final Aggregate aggregate) {
        final byte[] rawDestKey = this.rawKey(destKey);
        final byte[][] rawKeys = this.rawKeys(key, otherKeys);
        return (Long)redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) {
                return connection.zInterStore(rawDestKey, aggregate, weights, rawKeys);
            }
        }, true);
    }

    public Long unionAndStore(String key, Collection<String> otherKeys, String destKey, final int[] weights,
            final Aggregate aggregate) {
        final byte[] rawDestKey = this.rawKey(destKey);
        final byte[][] rawKeys = this.rawKeys(key, otherKeys);
        return (Long)redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) {
                return connection.zUnionStore(rawDestKey, aggregate, weights, rawKeys);
            }
        }, true);
    }

    /**以上为ZSet操作**/
    /**
     * 开区间. (a, b)
     */
    public static final int Z_SET_RANGE_OPERATION_0 = 0;
    /**
     * 左开右闭. (a, b]
     */
    public static final int Z_SET_RANGE_OPERATION_1 = 1;
    /**
     * 左闭右开. [a, b)
     */
    public static final int Z_SET_RANGE_OPERATION_2 = 2;

    public static class ZSetOperatorInput {
        private Double min;
        private Double max;
        private Integer offset;
        private Integer count;
        private Integer operation;

        private ZSetOperatorInput() {
            min = null;
            max = null;
            offset = null;
            count = null;
            operation = null;
        }

        public static ZSetOperatorInput getInput() {
            return new ZSetOperatorInput();
        }


        /**
         * @return the min as min
         */
        public double getMin() {
            return min;
        }

        /**
         * @param min the min to set
         */
        public ZSetOperatorInput setMin(Double min) {
            this.min = min;
            return this;
        }

        /**
         * @return the max as max
         */
        public double getMax() {
            return max;
        }

        /**
         * @param max the max to set
         */
        public ZSetOperatorInput setMax(Double max) {
            this.max = max;
            return this;
        }

        /**
         * @return the offset as offset
         */
        public int getOffset() {
            return offset;
        }

        /**
         * @param offset the offset to set
         */
        public ZSetOperatorInput setOffset(Integer offset) {
            this.offset = offset;
            return this;
        }

        /**
         * @return the count as count
         */
        public int getCount() {
            return count;
        }

        /**
         * @param count the count to set
         */
        public ZSetOperatorInput setCount(Integer count) {
            this.count = count;
            return this;
        }

        /**
         * @return the operation as operation
         */
        public int getOperation() {
            return operation;
        }

        /**
         * @param operation the operation to set
         */
        public ZSetOperatorInput setOperation(Integer operation) {
            this.operation = operation;
            return this;
        }

        @Override
        public String toString() {
            return "ZSetOperatorInput{" + "min=" + min + ", max=" + max + ", offset=" + offset +
                    ", count=" + count + ", operation=" + operation + '}';
        }
    }
}
