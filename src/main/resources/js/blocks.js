(function (ext) {

    var bulbs = null;  // An array of bulbs
    var bulbNames = [];
    var bulbIds = [];

    /**
     * Pre-fetch bulbs to populate menu with bulb names
     */
    if (bulbs == null) {
        $.ajax({
            url: 'https://127.0.0.1:8443/bulbs',
            dataType: 'json',
            async: false,
            success: function (bulbs_data) {
                console.log(bulbs_data);

                $.each(bulbs_data, function (key, value) {
                    bulbNames.push(value["name"]);
                    bulbIds.push(value["id"]);
                });

                bulbs = bulbs_data;
            }
        });
    }

    /**
     * Block and block menu descriptions
     */
    var descriptor = {
        blocks: [
            ['R', 'name bulb %m.lightIds', 'get_bulb_name', bulbIds[0]],
            ['R', 'get intensity of bulb %m.lights', 'get_bulb_intensity', bulbNames[0]],
            ['R', 'get color of bulb %m.lights', 'get_bulb_color', bulbNames[0]],
            [' ', 'turn all lights %m.lightSwitchState', 'set_all_on_off', 'on'],
            [' ', 'turn light %m.lights %m.lightSwitchState', 'set_on_off', bulbNames[0], 'on'],
            [' ', 'set %m.lights intensity to %n', 'set_intensity', bulbNames[0], 255],
            [' ', 'set %m.lights color to %s', 'set_color', bulbNames[0], "efd275"],
            [' ', 'set %m.lights color to R %n G %n B %n', 'set_color_rgb', bulbNames[0], 254, 100, 100]
        ],
        menus: {
            lightSwitchState: ['on', 'off'],
            lights: bulbNames,
            lightIds: bulbIds
        },
        url: 'https://github.com/steni/lys',
        displayName: 'IKEA Trådfri'
    };

    // Status reporting code
    // Use this to report missing hardware, plugin or unsupported browser
    ext._getStatus = function () {
        return {status: 2, msg: 'Ready'};
    };

    /**
     * get the specific bulb and return its name
     * @param bulbId - the bulb's id
     * @param callback - the function to callback with the name
     */
    ext.get_bulb_name = function (bulbId, callback) {
        get_bulb_data("name", bulbId, callback);
    };

    /**
     * get the specific bulb and return its color
     * @param bulbId - the bulb's id
     * @param callback - the function to callback with the name
     */
    ext.get_bulb_color = function (bulbId, callback) {
        get_bulb_data("color", bulbId, callback);
    };

    /**
     * get the specific bulb and return its intensity
     * @param bulbId - the bulb's id
     * @param callback - the function to callback with the name
     */
    ext.get_bulb_intensity = function (bulbId, callback) {
        get_bulb_data("intensity", bulbId, callback);
    };

    function get_bulb_data(infoElement, bulbId, callback) {
        $.ajax({
            url: 'https://127.0.0.1:8443/bulb/' + bulbId,
            dataType: 'json',
            success: function (bulb_data) {
                callback(bulb_data[infoElement]);
            }
        });
    }

    /**
     * set state (on/off) for one bulb (by name or id)
     * @param bulb - name or id of bulb
     * @param state - on or off
     */
    ext.set_on_off = function (bulb, state) {
        $.ajax({url: "https://127.0.0.1:8443/bulb/" + bulb + "/" + state});
    };

    /**
     * Turn all lights on or off
     * @param state - on / off
     */
    ext.set_all_on_off = function (state) {
        $.ajax({url: "https://127.0.0.1:8443/bulbs/" + state});
    };

    /**
     * set intensity for one bulb (by name or id)
     * @param bulb - name or id of bulb
     * @param intensity - from 0 - 254
     */
    ext.set_intensity = function (bulb, intensity) {
        $.ajax({url: "https://127.0.0.1:8443/bulb/" + bulb + "/intensity/" + intensity});
    };

    /**
     * set color for one bulb (by name or id)
     * @param bulb - name or id of bulb
     * @param color - hex, e.f., f1e0b5
     */
    ext.set_color = function (bulb, color) {
        $.ajax({url: "https://127.0.0.1:8443/bulb/" + bulb + "/color/" + color});
    };

    /**
     * set color for one bulb, using r, g, b numbers (by name or id)
     * @param bulb - name or id of bulb
     * @param r
     * @param g
     * @param b
     */
    ext.set_color_rgb = function (bulb, r, g, b) {
        $.ajax({url: "https://127.0.0.1:8443/bulb/" + bulb + "/color/r/" + r + "/g/" + g + "/b/" + b});
    };


    // Register the extension
    ScratchExtensions.register('IKEA Tradfri', descriptor, ext);

    // Cleanup function when the extension is unloaded
    ext._shutdown = function () {
    };
})({});