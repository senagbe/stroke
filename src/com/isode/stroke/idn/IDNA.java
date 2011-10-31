/*
 * Copyright (c) 2010 Remko Tronçon
 * Licensed under the GNU General Public License v3.
 * See Documentation/Licenses/GPLv3.txt for more information.
 */
/*
 * Copyright (c) 2011, Isode Limited, London, England.
 * All rights reserved.
 */
package com.isode.stroke.idn;

import java.net.IDN;

public class IDNA {
    public static String getEncoded(String s) {
        return IDN.toASCII(s);
    }
}
