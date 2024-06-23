package com.example.sw0b_001

import android.util.Base64
import com.example.sw0b_001.Modules.Crypto
import com.example.sw0b_001.Modules.Helpers
import com.example.sw0b_001.Modules.Security
import com.example.sw0b_001.Security.SecurityAES
import com.example.sw0b_001.Security.SecurityCurve25519
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.StatusRuntimeException
import okio.ByteString
import org.junit.Before
import org.junit.Test
import vault.v1.EntityGrpc
import vault.v1.EntityGrpc.EntityBlockingStub
import vault.v1.EntityGrpc.EntityFutureStub
import vault.v1.EntityGrpc.EntityStub
import vault.v1.Vault
import vault.v1.Vault.AuthenticateEntityResponse
import java.net.Inet6Address

/**
 * Flow from https://github.com/smswithoutborders/SMSwithoutborders-BE/blob/feature/grpc_api/docs/grpc.md
 * 1. Create Account
 * https://github.com/smswithoutborders/SMSwithoutborders-BE/blob/feature/grpc_api/docs/grpc.md#create-an-entity
 *
 * 2. Complete Account creation
 * https://github.com/smswithoutborders/SMSwithoutborders-BE/blob/feature/grpc_api/docs/grpc.md#complete-creation
 *
 * 3. Authenticate an entity
 * https://github.com/smswithoutborders/SMSwithoutborders-BE/blob/feature/grpc_api/docs/grpc.md#authenticate-an-entity
 *
 * 4. Store entities
 * https://github.com/smswithoutborders/SMSwithoutborders-BE/blob/feature/grpc_api/docs/grpc.md#store-an-entitys-token
 *
 * 5. List stored entities
 * https://github.com/smswithoutborders/SMSwithoutborders-BE/blob/feature/grpc_api/docs/grpc.md#list-an-entitys-stored-tokens
 */
class gRPCTest {
    lateinit var channel: ManagedChannel
    lateinit var entityStub: EntityBlockingStub

    private val globalPhoneNumber = "+23712345673"
    private val globalPassword = "dMd2Kmo9"
    private val deviceIdPubKey = SecurityCurve25519().generateKey()
    private val publishPubKey = SecurityCurve25519().generateKey()

    @Before
    fun init() {
        channel = ManagedChannelBuilder
            .forAddress("staging.smswithoutborders.com", 9050)
            .useTransportSecurity()
            .build()

        entityStub = EntityGrpc.newBlockingStub(channel)
    }

    @Test
    fun vaultTestCreateEntity() {
        /**
         * TODO: Get wait time in here, to be used for
         */
        val createEntityRequest1 = Vault.CreateEntityRequest.newBuilder().apply {
            setPhoneNumber(globalPhoneNumber)
        }.build()

        try {
            val createResponse = entityStub.createEntity(createEntityRequest1)
            assert(createResponse.requiresOwnershipProof)
            println(createResponse.message)
        } catch(e: Exception) {
            if(e is StatusRuntimeException) {
                when(e.status.code.toString()) {
                    "INVALID_ARGUMENT" -> {
                        println(e.message)
                        throw e
                    }
                    "ALREADY_EXISTS" -> {
                        println(e.message)
                    }
                }
            }
        }
    }

    @Test
    fun vaultTestCreateEntity2() {
        /**
         * TODO: grpc
         * Need to be able to delete contacts
         *
         * Public and private keys should go as bytes
         */
//        vaultTestCreateEntity()
        val createEntityRequest2 = Vault.CreateEntityRequest.newBuilder().apply {
            setCountryCode("CM")
            setPhoneNumber(globalPhoneNumber)
            setPassword(globalPassword)
            setClientPublishPubKey(Base64.encodeToString(publishPubKey.publicKey, Base64.DEFAULT))
            setClientDeviceIdPubKey(Base64.encodeToString(deviceIdPubKey.publicKey, Base64.DEFAULT))
            setOwnershipProofResponse("123456")
        }.build()

        try {
            val createResponse = entityStub.createEntity(createEntityRequest2)
            assert(createResponse.requiresOwnershipProof)
            println(createResponse.message)
        } catch(e: Exception) {
            if(e is StatusRuntimeException) {
                when(e.status.code.toString()) {
                    "UNAUTHENTICATED" -> {
                        println(e.message)
                    }
                }
            }
            throw e
        }
    }

    private fun vaultAuthenticateEntity(): AuthenticateEntityResponse {
        val authenticateEntity = Vault.AuthenticateEntityRequest.newBuilder().apply {
            setPhoneNumber(globalPhoneNumber)
            setPassword(globalPassword)
        }.build()

        val createResponse = entityStub.authenticateEntity(authenticateEntity)
        return createResponse
    }


    @Test
    fun vaultTestAuthenticateEntity() {
//        vaultTestCreateEntity()
//        vaultTestCreateEntity2()

        val createResponse = vaultAuthenticateEntity()
        vaultTestAuthenticationEntity2()
    }

    private fun vaultTestAuthenticationEntity2(): AuthenticateEntityResponse {
        /**
         * TODO: grpc
         * Need to be able to delete contacts
         *
         * Public and private keys should go as bytes
         */
//        vaultTestCreateEntity()
        val createEntityRequest2 = Vault.AuthenticateEntityRequest.newBuilder().apply {
            setPhoneNumber(globalPhoneNumber)
            setClientPublishPubKey(Base64.encodeToString(publishPubKey.publicKey, Base64.DEFAULT))
            setClientDeviceIdPubKey(Base64.encodeToString(deviceIdPubKey.publicKey, Base64.DEFAULT))
            setOwnershipProofResponse("123456")
        }.build()

        return entityStub.authenticateEntity(createEntityRequest2)
    }

    @Test
    fun vaultTestListStoredEntityToken() {
        /**
         * https://github.com/smswithoutborders/SMSwithoutborders-BE/blob/feature/grpc_api/docs/grpc.md#authenticate-an-entity
         */

        vaultAuthenticateEntity()
        val createResponse = vaultTestAuthenticationEntity2()

        val sharedKey = SecurityCurve25519().calculateSharedSecret(
            Base64.decode(createResponse.serverDeviceIdPubKey, Base64.DEFAULT), deviceIdPubKey)

        val llt = Crypto.decryptFernet(sharedKey,
            String(Base64.decode(createResponse.longLivedToken, Base64.DEFAULT), Charsets.UTF_8))
        val listEntity = Vault.ListEntityStoredTokenRequest.newBuilder().apply {
            setLongLivedToken(llt)
        }.build()

        val listResponse = entityStub.listEntityStoredTokens(listEntity)
        val listStoredTokens: List<Vault.Token> = listResponse.storedTokensList

        println(listStoredTokens)

        /**
        listStoredTokens.forEach {
            it.accountIdentifier
            it.platform
        }
        **/
    }
}