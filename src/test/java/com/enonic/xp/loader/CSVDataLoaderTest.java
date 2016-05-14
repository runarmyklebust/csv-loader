package com.enonic.xp.loader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;

import com.enonic.xp.loader.format.FormatParser;

public class CSVDataLoaderTest
{
    @Ignore
    @Test
    public void cvs_loader_test_1()
        throws Exception
    {
        final long start = System.currentTimeMillis();

        final ByteSource byteSource = getAsByteSource( "world_cities.csv" );

        new CSVDataLoader().load( LoaderParams.create().
            lineParser( new CSVLineParser( FormatParser.parse( "city,city_ascii,lat,lng,pop,country,iso2,iso3,province", "test.csv" ) ) ).
            hasHeaderRow( true ).
            source( byteSource ).
            handler( new TestEntryHandler() ).
            build() );

        System.out.println( "Time used: " + ( System.currentTimeMillis() - start ) );
    }

    @Ignore
    @Test
    public void cvs_loader_test_2()
        throws Exception
    {
        final long start = System.currentTimeMillis();

        final ByteSource byteSource = getAsByteSource( "cities_with_populations.txt" );

        final TestEntryHandler handler = new TestEntryHandler();
        new CSVDataLoader().load( LoaderParams.create().
            lineParser(
                new CSVLineParser( FormatParser.parse( "Country,City,AccentCity,Region,Population,Latitude,Longitude", "test.csv" ) ) ).
            hasHeaderRow( false ).
            source( byteSource ).
            handler( handler ).
            build() );

        System.out.println( "Time used: " + ( System.currentTimeMillis() - start ) + " on " + handler.total + " entries" );
    }


    @Ignore
    @Test
    public void cvs_loader_test_3()
        throws Exception
    {
        final long start = System.currentTimeMillis();

        final ByteSource byteSource = getAsByteSource( "addresses.csv" );

        final TestEntryHandler handler = new TestEntryHandler();
        new CSVDataLoader().load( LoaderParams.create().
            lineParser( new CSVLineParser( FormatParser.parse(
                "LOKASJONS_ID,GATENAVN,HUSNUMMER,HUSBOKSTAV,OPPGANG,POSTBOKSNUMMER,POSTBOKSANLEGGSNAVN,STED,POSTNUMMER,POSTSTED,KOMMUNE,KOMMUNENR,FYLKE,TYPE,KOORDINATSYSTEM,LATITUDE,LONGITUDE,IKKE_I_OMDELING,HENTESTED,HENTESTEDSNUMMER,DISTRIBUSJONSENHET,GATEKODE,OFFISIELT",
                "test.csv" ) ) ).
            hasHeaderRow( true ).
            source( byteSource ).
            handler( handler ).
            build() );

        System.out.println( "Time used: " + ( System.currentTimeMillis() - start ) + " on " + handler.total + " entries" );
    }

    private class TestEntryHandler
        implements EntryHandler
    {
        long total = 0;

        @Override
        public void handle( final Map<String, String> values )
        {

/*
            for ( final String value : values.keySet() )
            {
                System.out.println( "Value: " + value + " - " + values.get( value ) );
            }
*/
            total++;

            //          System.out.println( "-------------------" );
        }
    }


    private String getAsString( final String fileName )
        throws IOException
    {
        return new String( getAsByteSource( fileName ).read(), StandardCharsets.UTF_8 );
    }

    private ByteSource getAsByteSource( final String fileName )
        throws IOException
    {
        return ByteSource.wrap( ByteStreams.toByteArray( this.getClass().getResourceAsStream( fileName ) ) );
    }
}