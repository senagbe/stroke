/*
 * Copyright (c) 2012-2014, Isode Limited, London, England.
 * All rights reserved.
 */
/*
 * Copyright (c) 2010, Kevin Smith
 * All rights reserved.
 */
package com.isode.stroke.serializer.payloadserializers;

import com.isode.stroke.base.DateTime;
import com.isode.stroke.elements.MUCPayload;
import com.isode.stroke.serializer.GenericPayloadSerializer;
import com.isode.stroke.serializer.xml.XMLElement;
import com.isode.stroke.serializer.xml.XMLTextNode;

/**
 * Serializer for {@link MUCPayload} element.
 */
public class MUCPayloadSerializer extends GenericPayloadSerializer<MUCPayload> {

    /**
     * Constructor 
     */
    public MUCPayloadSerializer()  {
        super(MUCPayload.class);
    }

    @Override
    public String serializePayload(MUCPayload muc) {
        XMLElement mucElement = new XMLElement("x", "http://jabber.org/protocol/muc");
        XMLElement historyElement = new XMLElement("history");
        boolean history = false;
        if (muc.getMaxChars() >= 0) {
            historyElement.setAttribute("maxchars", String.valueOf(muc.getMaxChars()));
            history = true;
        }
        if (muc.getMaxStanzas() >= 0) {
            historyElement.setAttribute("maxstanzas", String.valueOf(muc.getMaxStanzas()));
            history = true;
        }
        if (muc.getSeconds() >= 0) {
            historyElement.setAttribute("seconds", String.valueOf(muc.getSeconds()));
            history = true;
        }
        if (muc.getSince() != null) {
            String sinceDateString = DateTime.dateToString(muc.getSince());
            historyElement.setAttribute("since", sinceDateString);
            history = true;
        }
        if (muc.getPassword() != null) {
            String password = muc.getPassword();
            XMLElement passwordElement = new XMLElement("password");
            passwordElement.addNode(new XMLTextNode(password));
            mucElement.addNode(passwordElement);
        }
        if (history) {
            mucElement.addNode(historyElement);
        }
        return mucElement.serialize();
    }
}
