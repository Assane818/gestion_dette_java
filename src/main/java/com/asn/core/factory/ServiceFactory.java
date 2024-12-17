package com.asn.core.factory;

import com.asn.data.services.Service;

public interface ServiceFactory<T> {
    Service<T> getService();
}
