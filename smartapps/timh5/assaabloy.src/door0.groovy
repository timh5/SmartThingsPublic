/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Big Turn ON
 *
 *  Author: SmartThings
 */

definition(
    name: "Unlock Front Door2",
    namespace: "timh5.wearefiberx.unlockDoor2",
    author: "SmartThings",
    description: "Unlock your front door1",
    category: "Convenience",
    iconUrl: "http://cdn.device-icons.smartthings.com/Home/home3-icn.png",
    iconX2Url: "http://cdn.device-icons.smartthings.com/Home/home3-icn@2x.png"
)

preferences {
	/*
	section("When I touch the app, unlock...") {
		input "switches", "capability.switch", multiple: true
	}
    */
}

def installed()
{
	subscribe(app, appTouch)
    def hub = location.hubs[0]
    log.debug "id: ${hub.id}"
    log.debug "zigbeeId: ${hub.zigbeeId}"
    log.debug "zigbeeEui: ${hub.zigbeeEui}"
}

def updated()
{
	unsubscribe()
	subscribe(app, appTouch)
}

def appTouch(evt) {
	log.debug "appTouch: $evt"
    log.debug "apiServerUrl: ${apiServerUrl("/my/path")}"
    makeHostBillRequest("unlock")
}


def makeHostBillRequest(evtID) {
    log.debug "loc1 $location.hubs*.id"
    log.debug $location
	def hub = location.hubs[0]
    def hubID = 0
    def hubName = 0
    if ( hub && hub.id != null){
       hubID = hub.id
       hubName = hub.name
    }
    
//    hubID = "xx:${location.hubs*.id}"
   
    def HBAction = "stauto-${evtID}"
    log.debug "hub id is ${hubID}"

	HBAction = "stauto-unlock"
    // TODO -- this url should probably be placed some where else? in settings? how does authenticaiton work?
    // Should this call be digitally signed? ... probably yes.
    def uri="https://csp.fiberindy.com/includes/modules/Other/hdt/api.php?action=$HBAction"
    try {
        httpPost(uri, "id=$hubID&name=$hubName") {resp ->
            log.debug "resp data: ${resp.data}"
            //log.debug "humidity: ${resp.data.main.humidity}"
            log.debug "message is $resp.data.message"
            if( resp.data && resp.data.message ) sendPush(resp.data.message)
        }
        sendPush("Unlocking Door...")
    } catch (e) {
        log.error "error: $e"
    }
}

