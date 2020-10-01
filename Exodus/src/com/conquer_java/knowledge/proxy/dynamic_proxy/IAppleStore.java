package com.conquer_java.knowledge.proxy.dynamic_proxy;

import com.conquer_java.knowledge.proxy.static_proxy.Phone;
import com.conquer_java.knowledge.proxy.static_proxy.PhoneStore;

public interface IAppleStore extends PhoneStore {
    Phone sellPhone();
}
