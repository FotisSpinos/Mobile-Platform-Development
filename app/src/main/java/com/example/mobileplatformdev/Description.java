package com.example.mobileplatformdev;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Description
{
    // the tags supported
    String[] supportedTags;
    String xmlDescriptionData;

    LinkedList<DescriptionEntity> descriptionEntities;

    Description(String[] supportedTags, String xmlDescriptionData)
    {
        this.supportedTags = supportedTags;
        this.xmlDescriptionData = xmlDescriptionData;

        descriptionEntities = new LinkedList<DescriptionEntity>();

        // get the index of the space to seperate the two numbers
        int elementIndex = xmlDescriptionData.indexOf(":");


        if(elementIndex == -1)
        {
            descriptionEntities.add(new DescriptionEntity("Description", xmlDescriptionData));
            return;
        }

        int nextValueIndex = xmlDescriptionData.length() - 1;

        String tag = "";
        Object value;
        for(int i = xmlDescriptionData.length() - 1; i >= 0; i++)
        {
            if(xmlDescriptionData.charAt(i) == ':')
            {
                value = xmlDescriptionData.substring(i + 1, nextValueIndex);
                nextValueIndex = i - 1;
            }
            if(xmlDescriptionData.charAt(i) == '.')
            {
                tag = xmlDescriptionData.substring(i + 2, nextValueIndex);
                nextValueIndex = i - 1;
            }
            else if(xmlDescriptionData.charAt(i) == '<')
            {
                value = xmlDescriptionData.substring(nextValueIndex, i - 1);
                descriptionEntities.add(new DescriptionEntity(tag, value));
            }
            else if(xmlDescriptionData.charAt(i) == '>')
            {
                nextValueIndex = i + 1;
            }
        }
        // Works: Lining Works, Resurfacing Traffic Management: Road Closure.
        //tag     value         value                           tag/value.

        /*
            RULES
            is tag if surrounded between start and :
            < begin > tag < : >

            < '>' > tag < : >

            < ':' > tag < : >

            < '.' > tag <:>
         */
    }


}
