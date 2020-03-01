package com.example.mobileplatformdev;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

public class RssFeedItemFactory
{

    private RssFeedItemFactory() {
    }

    public static Insidents CreateRoadWork()
    {
        return null;
    }

    // Creates a linked list of roadworks from the xml source
    public static LinkedList<RssFeedItem> CreateRoadWorks(String xmlSource)
    {
        RssFeedItem feedItem = null;
        LinkedList <RssFeedItem> rssFeedItems = new LinkedList <RssFeedItem>();
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput( new StringReader( xmlSource ) );
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                // Found a start tag
                if(eventType == XmlPullParser.START_TAG)
                {
                    // Check which Tag we have
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        feedItem = new RssFeedItem();
                    }

                    // Check the parser is reading the items
                    if(feedItem == null)
                    {
                        eventType = xpp.next();
                        continue;
                    }
                    else if(xpp.getName().equalsIgnoreCase("title"))
                    {
                        String tmp = xpp.nextText();
                        feedItem.SetTitle(tmp);
                    }
                    else if (xpp.getName().equalsIgnoreCase("description"))
                    {
                       String tmp = xpp.nextText();
                       feedItem.SetItemDescription(tmp);
                    }
                    else if (xpp.getName().equalsIgnoreCase("point"))
                    {
                        String temp = xpp.nextText();
                        feedItem.SetPoint(temp);
                    }
                }
                else if(eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        rssFeedItems.add(feedItem);
                        Log.println(Log.INFO,"Insident Added","Insident added: " +
                                "\n Title: " + feedItem.GetTitle() +
                                "\n Description: "+ feedItem.GetItemDescription().GetDescription() +
                                "\n Get point X: " +  Double.toString(feedItem.GetPoint().GetX()) +
                                "\n Get point Y: " + Double.toString(feedItem.GetPoint().GetY()) +
                                "\n \n"
                        );
                    }
                }

                // Get the next event
                eventType = xpp.next();

            } // End of while

        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }

        Log.println(Log.INFO, "Task Completed","Insidents XML is successfully stored");

        return rssFeedItems;
    }
}


/*
*  public static LinkedList<Insidents> CreateRoadWorks(String xmlSource)
    {
        Insidents currentInsident = null;
        LinkedList <Insidents> insidents = new LinkedList <Insidents>();
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput( new StringReader( xmlSource ) );
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                // Found a start tag
                if(eventType == XmlPullParser.START_TAG)
                {
                    // Check which Tag we have
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        currentInsident = new Insidents();
                    }

                    // Check the parser is reading the items
                    if(currentInsident == null)
                    {
                        eventType = xpp.next();
                        continue;
                    }
                    else if(xpp.getName().equalsIgnoreCase("title"))
                    {
                        String tmp = xpp.nextText();
                        currentInsident.SetTitle(tmp);
                    }
                    else if (xpp.getName().equalsIgnoreCase("description"))
                    {
                       String tmp = xpp.nextText();
                       currentInsident.SetDescription(tmp);
                    }
                    else if (xpp.getName().equalsIgnoreCase("point"))
                    {
                        String temp = xpp.nextText();
                        currentInsident.SetPoint(temp);
                    }
                }
                else if(eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        insidents.add(currentInsident);
                        Log.println(Log.INFO,"Insident Added","Insident added: " +
                                "\n Title: " + currentInsident.GetTitle() +
                                "\n Description: "+ currentInsident.GetDescription() +
                                "\n Get point X: " +  Double.toString(currentInsident.GetPoint().GetX()) +
                                "\n Get point Y: " + Double.toString(currentInsident.GetPoint().GetY()) +
                                "\n \n"
                        );
                    }
                }

                // Get the next event
                eventType = xpp.next();

            } // End of while

        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }

        Log.println(Log.INFO, "Task Completed","Insidents XML is successfully stored");

        return insidents;
    }
* */