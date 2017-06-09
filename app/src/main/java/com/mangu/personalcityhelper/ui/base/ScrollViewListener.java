package com.mangu.personalcityhelper.ui.base;

import com.mangu.personalcityhelper.ui.common.ScrollViewExt;

public interface ScrollViewListener {
    void onScrollChanged(ScrollViewExt scrollView,
                         int x, int y, int oldx, int oldy);
}