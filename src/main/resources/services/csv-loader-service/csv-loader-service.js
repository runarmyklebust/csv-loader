var portalLib = require('/lib/xp/portal');
var contentLib = require('/lib/xp/content');
var xpLoaderLib = require('/lib/xploader');

exports.post = function (req) {

    var byteSource = portalLib.getMultipartStream("file");
    var file = portalLib.getMultipartItem("file");
    var format = portalLib.getMultipartText('format');
    var header = true;

    log.info("Format: %s", format);

    var result = xpLoaderLib.getFormat(byteSource, file.fileName);

    //var result = xpLoaderLib.load(byteSource, format, header);

    log.info("Result: %s", result);

    /*
     var model = {
     total: result.successful,
     timeUsed: result.timeUsed
     }
     */

    var model = {
        result: result
    }

    return {
        contentType: 'text/plain',
        body: model
    }

};

function createImage() {


}