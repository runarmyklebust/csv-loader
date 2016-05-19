package com.enonic.xp.loader;

import com.google.common.io.ByteSource;

import com.enonic.xp.content.ContentService;
import com.enonic.xp.loader.format.Format;
import com.enonic.xp.loader.format.FormatMapper;
import com.enonic.xp.loader.format.FormatParser;
import com.enonic.xp.loader.format.FormatScriptValueFactory;
import com.enonic.xp.node.NodeService;
import com.enonic.xp.script.ScriptValue;
import com.enonic.xp.script.bean.BeanContext;
import com.enonic.xp.script.bean.ScriptBean;

public class CSVLoaderBean
    implements ScriptBean
{
    private ContentService contentService;

    private NodeService nodeService;

    private ScriptValue fields;

    private ByteSource source;

    private boolean hasHeader;


    public void setFields( final ScriptValue fields )
    {
        this.fields = fields;
    }

    public void setSource( final ByteSource source )
    {
        this.source = source;
    }

    public void setHasHeader( final boolean hasHeader )
    {
        this.hasHeader = hasHeader;
    }

    public LoadResultMapper load()
    {
        final Format format = FormatScriptValueFactory.create( this.fields );

        final CSVDataLoader csvDataLoader = new CSVDataLoader();

//        return loadAsNode( format, csvDataLoader );
        return loadAsContent( format, csvDataLoader );
    }

    private LoadResultMapper loadAsContent( final Format format, final CSVDataLoader csvDataLoader )
    {
        ContentEntryHandler handler = ContentEntryHandler.create().
            contentService( this.contentService ).
            format( format ).
            build();

        final LoadResultMapper loadResultMapper = doLoad( format, csvDataLoader, handler );

        handler.publish();

        return loadResultMapper;
    }

    private LoadResultMapper loadAsNode( final Format format, final CSVDataLoader csvDataLoader )
    {
        final NodeEntryHandler handler = new NodeEntryHandler( this.nodeService, format );

        return doLoad( format, csvDataLoader, handler );
    }

    private LoadResultMapper doLoad( final Format format, final CSVDataLoader csvDataLoader, final EntryHandler handler )
    {
        final long start = System.currentTimeMillis();

        csvDataLoader.load( LoaderParams.create().
            lineParser( new CSVLineParser( format ) ).
            failOnErrors( true ).
            hasHeaderRow( hasHeader ).
            handler( handler ).
            source( source ).
            build() );

        final long timeUsed = System.currentTimeMillis() - start;

        return new LoadResultMapper( timeUsed, handler.getTotal() );
    }

    public FormatMapper getFormat( final ByteSource source, final String fileName )
    {
        final Format format = FormatParser.parse( source, fileName );

        return new FormatMapper( format );
    }


    @Override
    public void initialize( final BeanContext context )
    {
        this.nodeService = context.getService( NodeService.class ).get();
        this.contentService = context.getService( ContentService.class ).get();
    }

}
