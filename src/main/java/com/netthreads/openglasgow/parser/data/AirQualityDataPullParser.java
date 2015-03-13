/**
 * -----------------------------------------------------------------------
 * Copyright 2015 - Alistair Rutherford - www.netthreads.co.uk
 * -----------------------------------------------------------------------
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.netthreads.openglasgow.parser.data;

import java.util.UUID;

import com.netthreads.openglasgow.parser.PullParser;

/**
 * This is a _simple_ XML Pull parser.
 * 
 * Type: Air Quality Data
 * 
 * Sample URL: http://www.scottishairquality.co.uk/assets/rss/current_region_levels.xml
 * 
 */
public class AirQualityDataPullParser implements PullParser<AirQualityData>
{
	// In tag
	private boolean inItemRecord = false;
	private boolean inTitle = false;
	private boolean inDescription = false;
	private boolean inPubDate = false;
	
	// Record values.
	private String id;
	private String title;
	private String description;
	private String pubDate;
	
	/**
	 * Construct parser.
	 * 
	 */
	public AirQualityDataPullParser()
	{
		reset();
	}
	
	/**
	 * Process start tag
	 * 
	 * @param tag
	 */
	@Override
	public boolean processStartTag(String tag)
	{
		if (tag.equals(AirQualityData.TEXT_ITEM_RECORD))
		{
			inItemRecord = true;

			id = UUID.randomUUID().toString();
		}
		else if (inItemRecord && tag.equals(AirQualityData.TEXT_TITLE))
		{
			inTitle = true;
		}
		else if (inItemRecord && tag.equals(AirQualityData.TEXT_DESCRIPTION))
		{
			inDescription = true;
		}
		else if (inItemRecord && tag.equals(AirQualityData.TEXT_PUB_DATE))
		{
			inPubDate = true;
		}
		
		return false;
	}
	
	/**
	 * Process end tag
	 * 
	 * @param tag
	 */
	@Override
	public boolean processEndTag(String tag)
	{
		boolean ready = false;
		if (tag.equals(AirQualityData.TEXT_ITEM_RECORD))
		{
			inItemRecord = false;
			
			ready = true;
		}
		else if (inItemRecord && tag.equals(AirQualityData.TEXT_TITLE))
		{
			inTitle = false;
		}
		else if (inItemRecord && tag.equals(AirQualityData.TEXT_DESCRIPTION))
		{
			inDescription = false;
		}
		else if (inItemRecord && tag.equals(AirQualityData.TEXT_PUB_DATE))
		{
			inPubDate = false;
		}
		
		return ready;
	}
	
	/**
	 * Collect text values depending on conditions.
	 * 
	 * @param text
	 */
	@Override
	public void processText(String text)
	{
		if (inItemRecord && inDescription)
		{
			description = text;
		}
		else if (inItemRecord && inTitle)
		{
			title = text;
		}
		else if (inItemRecord && inPubDate)
		{
			pubDate = text;
		}
	}
	
	/**
	 * Build record from parsed data.
	 * 
	 */
	@Override
	public void populateRecord(AirQualityData record)
	{
		// Populate record.
		record.setId(id);
		record.setTitle(title);
		record.setDescription(description);
		record.setPubDate(pubDate);
		
		// Reset parser fields.
		reset();
	}
	
	/**
	 * Reset parsed strings.
	 * 
	 */
	@Override
	public void reset()
	{
		id = "";
		title = "";
		description = "";
		pubDate = "";
	}
	
	/**
	 * Inside tag
	 * 
	 * @return True if inside tag.
	 */
	@Override
	public boolean inTarget()
	{
		return inItemRecord || inTitle || inDescription || inPubDate;
	}
	
}
