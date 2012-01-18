/*
 * Copyright (c) 2010, Remko Tron�on.
 * All rights reserved.
 */
/*
 * Copyright (c) 2012, Isode Limited, London, England.
 * All rights reserved.
 */
package com.isode.stroke.elements;

import com.isode.stroke.base.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * disco#info from XEP-0030.
 */
public class DiscoInfo extends Payload {

    public static final String ChatStatesFeature = "http://jabber.org/protocol/chatstates";
    public static final String SecurityLabelsFeature = "urn:xmpp:sec-label:0";
    public static final String SecurityLabelsCatalogFeature = "urn:xmpp:sec-label:catalog:2";
    public static final String JabberSearchFeature = "jabber:iq:search";
    public static final String CommandsFeature = "http://jabber.org/protocol/commands";
    public static final String MessageCorrectionFeature = "urn:xmpp:message-correct:0";
    public static final String JingleFeature = "urn:xmpp:jingle:1";
    public static final String JingleFTFeature = "urn:xmpp:jingle:apps:file-transfer:3";
    public static final String JingleTransportsIBBFeature = "urn:xmpp:jingle:transports:ibb:1";
    public static final String JingleTransportsS5BFeature = "urn:xmpp:jingle:transports:s5b:1";
    public static final String Bytestream = "http://jabber.org/protocol/bytestreams";
    public static final String MessageDeliveryReceiptsFeature = "urn:xmpp:receipts";

    public static class Identity implements Comparable<Identity> {

        /**
         * Identity(name, "client", "pc", "");
         */
        public Identity(String name) {
            this(name, "client");
        }

        /**
         * Identity(name, category, "pc, "");
         */
        public Identity(String name, String category) {
            this(name, category, "pc");
        }

        /**
         * Identity(name, category, type, "");
         */
        public Identity(String name, String category, String type) {
            this(name, category, type, "");
        }

        /**
         *
         * @param name Identity name, notnull.
         * @param category Identity category, notnull.
         * @param type Identity type, notnull.
         * @param lang Identity language, notnull.
         */
        public Identity(String name, String category, String type, String lang) {
            NotNull.exceptIfNull(name, "name");
            NotNull.exceptIfNull(category, "category");
            NotNull.exceptIfNull(type, "type");
            NotNull.exceptIfNull(lang, "lang");
            name_ = name;
            category_ = category;
            type_ = type;
            lang_ = lang;
        }

        /**
         *
         * @return Not null.
         */
        public String getCategory() {
            return category_;
        }

        /**
         *
         * @return Not null.
         */
        public String getType() {
            return type_;
        }

        /**
         *
         * @return Not null.
         */
        public String getLanguage() {
            return lang_;
        }

        /**
         *
         * @return Not null.
         */
        public String getName() {
            return name_;
        }

        // Sorted according to XEP-115 rules
        public int compareTo(Identity other) {
            if (other == null) {
                return -1;
            }
            if (category_.equals(other.category_)) {
                if (type_.equals(other.type_)) {
                    if (lang_.equals(other.lang_)) {
                        return name_.compareTo(other.name_);
                    } else {
                        return lang_.compareTo(other.lang_);
                    }
                } else {
                    return type_.compareTo(other.type_);
                }
            } else {
                return category_.compareTo(other.category_);
            }
        }
            
        private final String name_;
        private final String category_;
        private final String type_;
        private final String lang_;


    }

    public DiscoInfo() {
    }

    /**
     *
     * @return Node, notnull.
     */
    public String getNode() {
        return node_;
    }

    /**
     *
     * @param node Notnull.
     */
    public void setNode(String node) {
        NotNull.exceptIfNull(node, "node");
        node_ = node;
    }

    /**
     *
     * @return Copy of the list of identities. Non-null.
     */
    public List<Identity> getIdentities() {
        return new ArrayList(identities_);
    }

    /**
     *
     * @param identity Non-null.
     */
    public void addIdentity(Identity identity) {
        NotNull.exceptIfNull(identity, "identity");
        identities_.add(identity);
    }

    /**
     *
     * @return Copy of the list of features. Nonnull.
     */
    public List<String> getFeatures() {
        return new ArrayList(features_);
    }

    /**
     *
     * @param feature Non-null.
     */
    public void addFeature(String feature) {
        NotNull.exceptIfNull(feature, "feature");
        features_.add(feature);
    }

    public boolean hasFeature(String feature) {
        return features_.contains(feature);
    }

    /**
     *
     * @param form Non-null.
     */
    public void addExtension(Form form) {
        NotNull.exceptIfNull(form, "form");
        extensions_.add(form);
    }

    /**
     *
     * @return A copy of the list, where the contents are references to the same objects.
     */
    public List<Form> getExtensions() {
        return new ArrayList(extensions_);
    }
    private String node_ = "";
    private final List<Identity> identities_ = new ArrayList<Identity>();
    private final List<String> features_ = new ArrayList<String>();
    private final List<Form> extensions_ = new ArrayList<Form>();
}