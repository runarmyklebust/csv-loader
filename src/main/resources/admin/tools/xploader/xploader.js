var mustache = require('/lib/xp/mustache');
var portal = require('/lib/xp/portal');

exports.get = function (req) {

    var view = resolve('xploader.html');

    var model = {
        jsUrl: portal.assetUrl({path: "/js/main.js"}),
        cssUrl: portal.assetUrl({path: "/css/main.css"})
    };

    model.serviceUrl = portal.serviceUrl({
        service: 'csv-loader-service'
    });

    return {
        contentType: 'text/html',
        body: mustache.render(view, model)
    };
};
