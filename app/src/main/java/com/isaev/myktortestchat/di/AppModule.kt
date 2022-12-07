package com.isaev.myktortestchat.di

import com.isaev.myktortestchat.data.remote.apiServices.MessageApiService
import com.isaev.myktortestchat.data.remote.apiServices.MessageWebSocketService
import com.isaev.myktortestchat.data.remote.apiServices.implementation.MessageApiServiceImpl
import com.isaev.myktortestchat.data.remote.apiServices.implementation.MessageWebSocketServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.websocket.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(WebSockets)
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }

    @Provides
    @Singleton
    fun provideMessageService(client: HttpClient): MessageApiService {
        return MessageApiServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideMessageWebSocketService(client: HttpClient): MessageWebSocketService {
        return MessageWebSocketServiceImpl(client)
    }
}