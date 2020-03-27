package com.example.mobileplatformdev;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;

public class RssDataLoaderHelper
{
    // default constructor
    private RssDataLoaderHelper() {
    }

    // Creates a linked list of roadworks from the xml source
    public static Hashtable<String, RssFeedItem> GetRecordedData(String xmlSource, StoreRssItemsAsyncActivity activity)
    {
        RssFeedItem feedItem = null;
        Hashtable <String, RssFeedItem> rssFeedItems = new Hashtable<String, RssFeedItem>();
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
                    if (xpp.getName().equalsIgnoreCase("title") && feedItem == null)
                    {
                        activity.dataTag = xpp.nextText();
                    }

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
                        Description descTmp = new Description(xpp.nextText());
                        feedItem.SetItemDescription(descTmp);
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
                        rssFeedItems.put(feedItem.GetTitle(), feedItem);
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

    public static void StoreDataFromLine(String xmlSource, Hashtable<String, RssFeedItem> collection)
    {
        RssFeedItem feedItem = null;
        //Hashtable <String, RssFeedItem> rssFeedItems = new Hashtable<String, RssFeedItem>();
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
                        Description descTmp = new Description(xpp.nextText());
                        feedItem.SetItemDescription(descTmp);
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
                        collection.put(feedItem.GetTitle(), feedItem);
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
    }
}