package com.example.geochallenge.ui.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geochallenge.data.GeochallengeService
import com.example.geochallenge.game.GameInfo

import javax.inject.Inject

class RecordsViewModelFactory @Inject constructor(
    private val geochallengeService: GeochallengeService,
    private val gameInfo: GameInfo

) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(
                GeochallengeService::class.java,
                GameInfo::class.java
            )
            .newInstance(
                geochallengeService,
                gameInfo
            )
    }
}