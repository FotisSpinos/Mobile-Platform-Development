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
    public static void GetRecordedData(String xmlSource)
    {
        Hashtable<String, RssFeedItem> feedItems = new Hashtable<String, RssFeedItem>();
        String dataTag = "";
        RssFeedItem currentItem = null;
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
                    if (xpp.getName().equalsIgnoreCase("title") && currentItem == null)
                    {
                        String tmp = xpp.nextText();

                        int seperationIndex = tmp.indexOf('-');

                        if (seperationIndex != -1)
                            dataTag = tmp.substring(seperationIndex + 2, tmp.length() - 1);
                        else
                            dataTag =  tmp;
                    }

                    // Check which Tag we have
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        currentItem = new RssFeedItem();
                    }

                    // Check the parser is reading the items
                    if(currentItem == null)
                    {
                        eventType = xpp.next();
                        continue;
                    }
                    else if(xpp.getName().equalsIgnoreCase("title"))
                    {
                        String tmp = xpp.nextText();
                        currentItem.SetRssItemLocation(new RssItemLocation(tmp));
                    }
                    else if (xpp.getName().equalsIgnoreCase("description"))
                    {
                        Description descTmp = new Description(xpp.nextText());
                        currentItem.SetItemDescription(descTmp);
                    }
                    else if (xpp.getName().equalsIgnoreCase("point"))
                    {
                        String temp = xpp.nextText();
                        currentItem.SetPoint(temp);
                    }
                }
                else if(eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        feedItems.put(currentItem.GetPoint().ToString(), currentItem);
                    }
                }

                // Get the next event
                eventType = xpp.next();

            } // End of while

            Log.e("Data stored: ", "thread store rss items is completed!!");
            DataHolder.GetInstance().AddRssData(dataTag, feedItems);

        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }
    }
}