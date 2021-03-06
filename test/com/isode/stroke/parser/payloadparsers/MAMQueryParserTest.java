/*
* Copyright (c) 2014 Kevin Smith and Remko Tronçon
* All rights reserved.
*/

/*
* Copyright (c) 2014, Isode Limited, London, England.
* All rights reserved.
*/

package com.isode.stroke.parser.payloadparsers;

import org.junit.Test;
import com.isode.stroke.elements.FormField;
import com.isode.stroke.elements.MAMQuery;
import com.isode.stroke.elements.ResultSet;
import com.isode.stroke.eventloop.DummyEventLoop;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MAMQueryParserTest {

    @Test
    public void testParse() {
        DummyEventLoop eventLoop = new DummyEventLoop();
        PayloadsParserTester parser = new PayloadsParserTester(eventLoop);
        assertTrue(parser.parse(
            "<query queryid=\"id0\" xmlns=\"urn:xmpp:mam:0\" node=\"node1\">"
          +     "<x type=\"form\" xmlns=\"jabber:x:data\">"
          +        "<field type=\"text-single\" var=\"FORM_TYPE\">"
          +             "<value>urn:xmpp:mam:0</value>"
          +         "</field>"
          +         "<field type=\"text-single\" var=\"start\">"
          +             "<value>2010-08-07T00:00:00Z</value>"
          +         "</field>"
          +     "</x>"
          +     "<set xmlns=\"http://jabber.org/protocol/rsm\">"
          +         "<max>10</max>"
          +     "</set>"
          + "</query>"));

        MAMQuery payload = (MAMQuery)parser.getPayload();
        assertTrue(payload != null);
        assertTrue(payload.getQueryID() != null);
        assertEquals("id0", payload.getQueryID());
        assertTrue(payload.getNode() != null);
        assertEquals("node1",payload.getNode());

        assertTrue(payload.getForm() != null);
        
        FormField fieldType = payload.getForm().getField("FORM_TYPE");
        assertTrue(fieldType != null);
        assertEquals("urn:xmpp:mam:0", fieldType.getTextSingleValue());
        FormField fieldStart = payload.getForm().getField("start");
        assertTrue(fieldStart != null);
        assertEquals("2010-08-07T00:00:00Z", fieldStart.getTextSingleValue());

        assertTrue(payload.getResultSet() != null);
        ResultSet resultSet = payload.getResultSet();
        assertTrue(resultSet.getMaxItems() != null);
        assertEquals(resultSet.getMaxItems(), new Long(10));
    }
    
    @Test
    public void testParse_XEP0313_Example3() {
        DummyEventLoop eventLoop = new DummyEventLoop();
        PayloadsParserTester parser = new PayloadsParserTester(eventLoop);
        assertTrue(parser.parse(
                "<query xmlns='urn:xmpp:mam:0'>"
                    +"<x xmlns='jabber:x:data'>"
                        +"<field var='FORM_TYPE'>"
                           +"<value>urn:xmpp:mam:0</value>"
                       +"</field>"
                       +"<field var='with'>"
                         +"<value>juliet@capulet.lit</value>"
                       +"</field>"
                   +"</x>"
               +"</query>"));
        
        MAMQuery payload = (MAMQuery) parser.getPayload();
        assertTrue(payload != null);
        assertTrue(payload.getForm() != null);
        assertTrue(payload.getForm().getField("FORM_TYPE") != null);
        assertTrue(payload.getForm().getField("with") != null);
        assertEquals("urn:xmpp:mam:0",payload.getForm().getField("FORM_TYPE").getTextSingleValue());
        assertEquals("juliet@capulet.lit",payload.getForm().getField("with").getTextSingleValue()); 
    }
    
    @Test
    public void testParse_XEP0313_Example4() {
        DummyEventLoop eventLoop = new DummyEventLoop();
        PayloadsParserTester parser = new PayloadsParserTester(eventLoop);
        assertTrue(parser.parse(
            "<query xmlns='urn:xmpp:mam:0'>"
                +"<x xmlns='jabber:x:data'>"
                    +"<field var='FORM_TYPE'>"
                        +"<value>urn:xmpp:mam:0</value>"
                    +"</field>"
                    +"<field var='start'>"
                        +"<value>2010-06-07T00:00:00Z</value>"
                    +"</field>"
                    +"<field var='end'>"
                        +"<value>2010-07-07T13:23:54Z</value>"
                    +"</field>"
                +"</x>"
            +"</query>"));
        
        MAMQuery payload = (MAMQuery) parser.getPayload();
        assertTrue(payload != null);
        assertTrue(payload.getForm() != null);
        assertTrue(payload.getForm().getField("FORM_TYPE") != null);
        assertTrue(payload.getForm().getField("start") != null);
        assertTrue(payload.getForm().getField("end") != null);
        assertEquals("urn:xmpp:mam:0",payload.getForm().getField("FORM_TYPE").getTextSingleValue());
        assertEquals("2010-06-07T00:00:00Z",payload.getForm().getField("start").getTextSingleValue());
        assertEquals("2010-07-07T13:23:54Z",payload.getForm().getField("end").getTextSingleValue());
    }

    @Test
    public void testParseEmpty() {
        DummyEventLoop eventLoop = new DummyEventLoop();
        PayloadsParserTester parser = new PayloadsParserTester(eventLoop);
        assertTrue(parser.parse(
            "<query queryid=\"id0\" xmlns=\"urn:xmpp:mam:0\">"
          + "</query>"));

        MAMQuery payload = (MAMQuery)parser.getPayload();
        assertTrue(payload != null);
        assertTrue(payload.getQueryID() != null);
        assertEquals("id0", payload.getQueryID());
        assertTrue(payload.getForm() == null);
        assertTrue(payload.getResultSet() == null);
    }
}
