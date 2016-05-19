exports.load = function (source, format, hasHeader) {

    var bean = __.newBean('com.enonic.xp.loader.CSVLoaderBean');
    bean.source = source;
    bean.hasHeader = hasHeader;
    var nativeFields = __.toScriptValue(format);
    bean.fields = nativeFields;

    var result = bean.load();

    return __.toNativeObject(result);
};

exports.getFormat = function (source, fileName) {
    var bean = __.newBean('com.enonic.xp.loader.CSVLoaderBean');
    var result = bean.getFormat(source, fileName);
    return __.toNativeObject(result);
};
