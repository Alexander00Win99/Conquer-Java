package com.conque_java.knowledge.proxy.dynamic_proxy;

import com.conque_java.knowledge.proxy.static_proxy.Phone;
import com.conque_java.knowledge.proxy.static_proxy.PhoneStore;

public interface IAppleStore extends PhoneStore {
    Phone sellPhone();
}
