package com.example.geochallenge.di.activity


import com.example.geochallenge.ui.game.BaseGameMapActivity
import com.example.geochallenge.ui.game.GameMapFragment
import com.example.geochallenge.ui.game.classic.ClassicGameActivity
import com.example.geochallenge.ui.game.multiplayer.MultiplayerGameActivity
import com.example.geochallenge.ui.game.street.StreetGameActivity
import com.example.geochallenge.ui.game.time.TimeLimitGameActivity
import dagger.BindsInstance
import dagger.Subcomponent


@GameActivityScope
@Subcomponent(
    modules = [
        GameActivityModule::class
    ]
)
interface GameActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: BaseGameMapActivity
        ): GameActivityComponent


    }


    fun inject(activity: ClassicGameActivity)
    fun inject(activity: StreetGameActivity)
    fun inject(activity: TimeLimitGameActivity)
    fun inject(activity: MultiplayerGameActivity)
    fun inject(fragment: GameMapFragment)
}