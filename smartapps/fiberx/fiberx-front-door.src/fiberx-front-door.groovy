/**
 *  FiberX Front Door
 *
 *  Copyright 2016 Tim Hon
 *
 */
definition(
    name: "FiberX Front Door",
    namespace: "fiberx",
    author: "Tim Hon",
    description: "assa abloy door lock relay",
    category: "Convenience",
    iconUrl: "http://cdn.device-icons.smartthings.com/Home/home3-icn.png",
    iconX2Url: "http://cdn.device-icons.smartthings.com/Home/home3-icn@2x.png",
    iconX3Url: "http://cdn.device-icons.smartthings.com/Home/home3-icn@3x.png",
    oauth: [displayName: "FiberX Door", displayLink: ""])


preferences {
	section("Title") {
		// TODO: put inputs here
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
}

// TODO: implement event handlers