var portalLib = require('/lib/xp/portal');
var contentLib = require('/lib/xp/content');
var xpLoaderLib = require('/lib/xploader');

function parseFormat() {
    var fields = [];
    var i = 0;

    var fieldNameItem = portalLib.getMultipartText("field-name-" + i);

    while (fieldNameItem) {
        var fieldAlias = portalLib.getMultipartText("field-alias-" + i);
        var fieldSkip = portalLib.getMultipartText("field-skip-" + i);
        var isNodeNameElement = portalLib.getMultipartText("field-nodeNameElement-" + i);
        var valueType = portalLib.getMultipartText("field-valueType-" + i);
        var field = {};
        field.name = fieldNameItem;
        field.alias = fieldAlias;
        field.skip = fieldSkip == 'on';
        field.nodeNameElement = isNodeNameElement == 'on';
        field.valueType = valueType;
        fields.push(field);
        i++;
        fieldNameItem = portalLib.getMultipartText("field-name-" + i);
    }

    return fields;
}

exports.post = function (req) {

    var byteSource = portalLib.getMultipartStream("file");
    var file = portalLib.getMultipartItem("file");
    var format = portalLib.getMultipartText('format');
    var header = true;

    var format = parseFormat();

    var result = xpLoaderLib.load(byteSource, format, header);

    var model = {
        total: result.successful,
        timeUsed: result.timeUsed
    };

    return {
        contentType: 'text/plain',
        body: model
    }

};
