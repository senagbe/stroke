/*
* Copyright (c) 2014, Isode Limited, London, England.
* All rights reserved.
*/
/*
* Copyright (c) 2014, Remko Tronçon.
* All rights reserved.
*/

package com.isode.stroke.parser.payloadparsers;

import com.isode.stroke.parser.AttributeMap;
import com.isode.stroke.parser.GenericPayloadParser;
import com.isode.stroke.parser.PayloadParser;
import com.isode.stroke.parser.PayloadParserFactoryCollection;
import com.isode.stroke.elements.Form;
import com.isode.stroke.parser.payloadparsers.FormParser;
import com.isode.stroke.jid.JID;
import com.isode.stroke.elements.PubSubOptions;

public class PubSubOptionsParser extends GenericPayloadParser<PubSubOptions> {
public PubSubOptionsParser(PayloadParserFactoryCollection parsers) {
	super(new PubSubOptions());

	parsers_ = parsers;
	level_ = 0;
}

public void handleStartElement(String element, String ns, AttributeMap attributes) {
	if (level_ == 0) {
		String attributeValue;
		attributeValue = attributes.getAttribute("subid");
		if (!attributeValue.isEmpty()) {
			getPayloadInternal().setSubscriptionID(attributeValue);
		}
		attributeValue = attributes.getAttribute("node");
		if (!attributeValue.isEmpty()) {
			getPayloadInternal().setNode(attributeValue);
		}
		attributeValue = attributes.getAttribute("jid");
		if (!attributeValue.isEmpty()) {
			getPayloadInternal().setJID(JID.fromString(attributeValue));
		}
	}

	if (level_ == 1) {
		if (element.equals("x") && ns.equals("jabber:x:data")) {
			currentPayloadParser_ = new FormParser();
		}
	}

	if (level_ >= 1 && currentPayloadParser_ != null) {
		currentPayloadParser_.handleStartElement(element, ns, attributes);
	}
	++level_;
}

public void handleEndElement(String element, String ns) {
	--level_;
	if (currentPayloadParser_ != null) {
		if (level_ >= 1) {
			currentPayloadParser_.handleEndElement(element, ns);
		}
		if (level_ != 1) {
			return;
		}
		if (element.equals("x") && ns.equals("jabber:x:data")) {
			getPayloadInternal().setData((Form)(currentPayloadParser_.getPayload()));
		}
		currentPayloadParser_ = null;
	}
}

public void handleCharacterData(String data) {
	if (level_ > 1 && currentPayloadParser_ != null) {
		currentPayloadParser_.handleCharacterData(data);
	}
}

PayloadParserFactoryCollection parsers_;
int level_;
PayloadParser currentPayloadParser_;
}
