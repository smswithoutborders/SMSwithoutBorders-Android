package com.example.sw0b_001.Models

import android.content.Context
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
}