package com.example.sw0b_001

import androidx.test.platform.app.InstrumentationRegistry
import com.example.sw0b_001.Models.Bridges
import com.example.sw0b_001.Modules.Network
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test
import java.util.Base64

class BridgesTest {
    var context = InstrumentationRegistry.getInstrumentation().targetContext
    val gatewayServerUrl = "https://gatewayserver.staging.smswithoutborders.com/v3/publish"

    @Serializable
    data class GatewayClientRequest(val address: String, val text: String)

    @Test
    fun bridgeFlowTest() {
        val authRequest = android.util.Base64.encodeToString(Bridges.authRequest(context),
            android.util.Base64.DEFAULT)
        val payload = Json.encodeToString(GatewayClientRequest("+237123456789", authRequest))
        println("Publishing: $payload")

        // TODO: checks if user already auth, then proceeds to use that information
        // TODO: if not auth, then request for auth sessions to begin
        /**
         * Simulating Gateway clients here, since cannot send the SMS
         */
        val response = Network.jsonRequestPost(gatewayServerUrl, payload)
        println(response.response.responseMessage)
        print(String(response.response.data))
    }
}