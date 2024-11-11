package com.example.sw0b_001.Models

import android.content.Context
import android.util.Base64
import com.afkanerd.smswithoutborders.libsignal_doubleratchet.libsignal.Headers
import com.afkanerd.smswithoutborders.libsignal_doubleratchet.libsignal.States
import com.example.sw0b_001.Security.Cryptography
import java.nio.ByteBuffer
import java.nio.ByteOrder

object Bridges {

    private const val BRIDGE_VALUE: Byte = 0

    enum class SWITCH_TYPE(val value: Byte) {
        AUTH_REQUEST(0),
        AUTH_CODE(1),
        AUTH_CODE_WITH_PAYLOAD(2),
        PAYLOAD_ONLY(3)
    }

    fun authRequest1(authCode: String): ByteArray {
        val bridgeFlag = ByteArray(1)
        bridgeFlag[0] = BRIDGE_VALUE

        val switchValue = ByteArray(1)
        switchValue[0] = SWITCH_TYPE.AUTH_CODE.value

        val pubKeyLen = ByteArray(4)
        ByteBuffer.wrap(pubKeyLen).order(ByteOrder.LITTLE_ENDIAN).putInt(authCode.length)

        return bridgeFlag + switchValue + pubKeyLen + authCode.encodeToByteArray()
    }

    fun authRequest(context: Context): ByteArray {
        val publishPubKey = Cryptography.generateKey(context, Publishers.PUBLISHER_ID_KEYSTORE_ALIAS)

        val bridgeFlag = ByteArray(1)
        bridgeFlag[0] = BRIDGE_VALUE

        val switchValue = ByteArray(1)
        switchValue[0] = SWITCH_TYPE.AUTH_REQUEST.value

        val lenPubKey = ByteArray(4)
        ByteBuffer.wrap(lenPubKey).order(ByteOrder.LITTLE_ENDIAN)
            .putInt(publishPubKey.size)

        return bridgeFlag + switchValue + lenPubKey + publishPubKey
    }

    fun parseAuthResponse(payload: String): Pair<String?, ByteArray?> {
        var authCode = ""
        var responseDetails = ""
        payload.split("\n").apply {
            if(this.size > 1) {
                authCode = this[1].substring(0, 6)
                responseDetails = this[1].substring(6)
            }
        }
        return Pair(authCode, Base64.decode(responseDetails, Base64.DEFAULT))
    }

//    fun processBridgeAuthPayload(context: Context, payload: ByteArray) {
//        val switchType = payload[0].toInt()
//
//        // TODO: validate switchtype
//        val lenPubKey = payload[1].toInt()
//
//        // TODO: validate len against actual payload
//        val pubKey = payload.copyOfRange(2, payload.size)
//        println("Processing payload: $pubKey")
//
//        Publishers.storeArtifacts(context, pubKey)
//    }

    fun publish(payload: ByteArray, bridgeLetter: Byte): ByteArray{
        val bridgeFlag = ByteArray(1)
        bridgeFlag[0] = BRIDGE_VALUE

        val switchValue = ByteArray(1)
        switchValue[0] = SWITCH_TYPE.PAYLOAD_ONLY.value

        val lenPayload = ByteArray(4)
        ByteBuffer.wrap(lenPayload).order(ByteOrder.LITTLE_ENDIAN)
            .putInt(payload.size)

        return bridgeFlag + switchValue + bridgeLetter + lenPayload + payload
    }

    fun formatEmailBridge(to:String, cc:String, bcc:String, subject: String, body: String): String {
        return "$to:$cc:$bcc:$subject:$body"
    }
}