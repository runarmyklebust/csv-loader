var mustache = require('/lib/xp/mustache');
var portal = require('/lib/xp/portal');

exports.get = function (req) {

    var view = resolve('xploader.html');

    var model = {
        jsUrl: portal.assetUrl({path: "/js/main.js"}),
        cssUrl: portal.assetUrl({path: "/css/main.css"})
    };

    model.loaderServiceUrl = portal.serviceUrl({
        service: 'csv-loader-service'
    });

    model.statusServiceUrl = portal.serviceUrl({
        service: 'status-service'
    });

    model.parserServiceUrl = portal.serviceUrl({
        service: 'fileParser'
    });


    return {
        contentType: 'text/html',
        body: mustache.render(view, model)
    };
};
