package com.enonic.xp.loader;

import com.google.common.io.ByteSource;

public class LoaderParams
{
    private final ByteSource source;

    private final LineParser lineParser;

    private final boolean hasHeaderRow;

    private final boolean failOnErrors;

    private final EntryHandler handler;

    private LoaderParams( final Builder builder )
    {
        source = builder.source;
        lineParser = builder.lineParser;
        hasHeaderRow = builder.hasHeaderRow;
        failOnErrors = builder.failOnErrors;
        handler = builder.handler;
    }

    public EntryHandler getHandler()
    {
        return handler;
    }

    public boolean isFailOnErrors()
    {
        return failOnErrors;
    }

    public boolean isHasHeaderRow()
    {
        return hasHeaderRow;
    }

    public ByteSource getSource()
    {
        return source;
    }

    public LineParser getLineParser()
    {
        return lineParser;
    }

    public static Builder create()
    {
        return new Builder();
    }


    public static final class Builder
    {
        private ByteSource source;

        private LineParser lineParser;

        private boolean hasHeaderRow;

        private boolean failOnErrors = true;

        private EntryHandler handler;

        private Builder()
        {
        }

        public Builder source( final ByteSource val )
        {
            source = val;
            return this;
        }

        public Builder lineParser( final LineParser val )
        {
            lineParser = val;
            return this;
        }

        public Builder hasHeaderRow( final boolean val )
        {
            hasHeaderRow = val;
            return this;
        }

        public Builder failOnErrors( final boolean val )
        {
            failOnErrors = val;
            return this;
        }

        public Builder handler( final EntryHandler val )
        {
            handler = val;
            return this;
        }

        public LoaderParams build()
        {
            return new LoaderParams( this );
        }
    }
}