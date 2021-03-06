/*
 * Copyright (c) 2011-2013, Isode Limited, London, England.
 * All rights reserved.
 */
/*
 * Copyright (c) 2011, Kevin Smith.
 * All rights reserved.
 */

package com.isode.stroke.elements;

public class Last extends Payload {
    private int seconds_ = 0;

    public Last() {}

    public Last(final int seconds) {
        setSeconds(seconds);
    }

    public void setSeconds(final int seconds) {
        seconds_ = seconds;
    }

    public int getSeconds() {
        return seconds_;
    }
}
