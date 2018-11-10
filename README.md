# Lys: Program your IKEA Trådfri lights using ScratchX

**Lys** allows you or your kids to control your IKEA Trådfri lights by programming them, using the visual programming language Scratch.

Lys consists of two parts: a server and a ScratchX Extension.

## The Lys Server
The server connects to your IKEA Tråfri Gateway, and creates *endpoints* the Scratch Extension can use to communicate with your light bulbs.

## The Lys Block Extension
The extension is a small program you will load in to ScratchX; it will create new blocks you can use to query and control your lighting.

## Getting started
Download the server and import the encryption key:
```
git clone git@github.com:steni/lys.git
cd lys
security import src/main/resources/keystore.p12 -P heihei
```

Now you must provide the server with your IKEA Trådfri Gateway's IP and security key:

```
vi src/main/resources/default.properties
```

Then you'll be ready to start the server:

```
mvn spring-boot:run
```

Now you'll have the server up and running, and you should be able to connect to it and see your bulbs at
<a target="_blank" href="https://127.0.0.1:8443/bulbs">https://127.0.0.1:8443/bulbs</a>

The first time you visit that page, your browser will warn you that the site is unsecure, as it has a "self-signed" certificate. 

Different browsers handle this in different ways, but no matter what browser you are using, you have to proceed despite the warnings in order for the browser to accept the Lys server, which again is necessary for the ScratchX Extension to function.

(If the browser asks you if you want to install a certificate, click Yes.)

Thus, please make sure you can access that link, and that you see a list of your bulbs before proceeding.

The list will look somewhat like this:

```
[
  {
    "id": 65552,
    "name": "Jaspers tak",
    "isOn": true,
    "color": "0",
    "intensity": 254,
    "href": "https://127.0.0.1:8443/bulb/65552"
  },
  {
    "id": 65561,
    "name": "stua 1",
    "isOn": true,
    "intensity": 254,
    "href": "https://127.0.0.1:8443/bulb/65561"
  },
 ...
]
```

Now you are ready to go to ScratchX!

In your browser, go to <a target="_blank" href="http://scratchx.org/#scratch">http://scratchx.org/#scratch</a>.

Click the button that says "Load Experimental Extension", and under the heading of "Open an Extension URL", paste the following url:
<a target="_blank" href="https://steni.github.io/scratch/scratchx/lys.js">https://steni.github.io/scratch/scratchx/lys.js</a>.

This will give you several new blocks that can query and control your lighting!

## Credits
The code for <a target="_blank" href="https://github.com/ffleurey/ThingML-Tradfri">communicating with the IKEA Trådfri gateway</a> is written by Franck Fleurey  