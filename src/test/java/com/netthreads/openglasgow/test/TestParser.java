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
package com.netthreads.openglasgow.test;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import com.netthreads.openglasgow.parser.StreamParser;
import com.netthreads.openglasgow.parser.StreamParserImpl;
import com.netthreads.openglasgow.parser.data.AirQualityData;
import com.netthreads.openglasgow.parser.data.AirQualityDataFactory;
import com.netthreads.openglasgow.parser.data.AirQualityDataPullParser;

/**
 * Simple test to pull air quality data.
 * 
 * NOTE: The data is fetched from here:
 * 
 * http://www.scottishairquality.co.uk/assets/rss/current_region_levels.xml
 * 
 * Map data to define region data is here:
 * 
 * https://data.glasgow.gov.uk/dataset/air-quality-management-areas
 * 
 */
public class TestParser
{
	private static final String XML_SAMPLE_FILE = "/current_region_levels.xml";
	
	/**
	 * Test Traffic Data.
	 * 
	 * @throws XmlPullParserException
	 * 
	 */
	@Test
	public void testReadAirQualityData() throws XmlPullParserException
	{
		final InputStream entityStream = ClassLoader.class.getResourceAsStream(XML_SAMPLE_FILE);
		
		final List<AirQualityData> list = new LinkedList<AirQualityData>();
		final AirQualityDataFactory dataFactory = new AirQualityDataFactory();
		
		final StreamParser<AirQualityData> streamParser = new StreamParserImpl<AirQualityData>();
		
		streamParser.fetch(entityStream, list, dataFactory, new AirQualityDataPullParser());
		
		org.junit.Assert.assertTrue(list.size() > 0);
		
		dumpAirQualityDataResults(list);
	}
	
	/**
	 * Dump out results so we can take a look at them.
	 * 
	 * @param list
	 */
	private void dumpAirQualityDataResults(final List<AirQualityData> list)
	{
		for (AirQualityData data : list)
		{
			System.out.println(data.toString());
		}
	}
	
}
