package cn.pinming.microservice.measure.common.component;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by jin on 2020-03-10.
 */
@Slf4j
public abstract class Cache<T> {

    private T tempValue;

    abstract public String key();

    abstract public T getValue();

    abstract public Class<T> clazz();

    public T value() {
        T v = tempValue != null ? tempValue : getValue();
        this.tempValue = v;
        return v;
    }

    public Long timeSecond() {
        return null;
    }

    public boolean reloadValue() {
        boolean success = tempValue != null;
        tempValue = null;
        return success;
    }

    public boolean needCached(T needCachedValue) {
        return true;
    }

}
