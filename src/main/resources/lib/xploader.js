var bean = __.newBean('com.enonic.xp.loader.CSVLoaderBean');

exports.load = function (source, format, hasHeader) {
    var result = bean.load(source, format, hasHeader);
    return __.toNativeObject(result);
};

