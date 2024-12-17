package com.asn.core.factory;

import com.asn.data.views.View;

public interface ViewFactory<T> {
    View<T> getView();
}
