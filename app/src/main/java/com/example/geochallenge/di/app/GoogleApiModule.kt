package com.example.geochallenge.di.app

import com.example.geochallenge.AppDelegate
import com.example.geochallenge.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides


@Module(includes = [AppModule::class])
class GoogleApiModule {

    @Provides
    fun provideGoogleSignInOptions(appDelegate: AppDelegate): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(appDelegate.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    @Provides
    fun provideGoogleSignInClient(
        appDelegate: AppDelegate,
        gso: GoogleSignInOptions
    ): GoogleSignInClient {
        return GoogleSignIn.getClient(appDelegate, gso)
    }

}