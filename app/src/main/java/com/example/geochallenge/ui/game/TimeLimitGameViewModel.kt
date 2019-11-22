package com.example.geochallenge.ui.game

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

 class TimeLimitGameViewModel : SimpleGameViewModel() {

        companion object {
        const val COUNT_TIMER : Long = 30

    }

    val stillHaveTime = MutableLiveData<Long>()
    var stillHaveTimeLong = COUNT_TIMER
    var timerDisposable: Disposable? = null

    override fun newGame() {
        super.newGame()
        startTimerFromCount(COUNT_TIMER)

    }

     override fun clickedPosition(latitude: Double, longitude: Double, distance: Int) {
         super.clickedPosition(latitude, longitude, distance)

         //calculate time
         val timeBonus = getExtraTime(distance)
         val resultTime = stillHaveTimeLong + timeBonus

         if (resultTime <= 0) finishGame()

         startTimerFromCount(resultTime)
     }

     override fun levelFinished() {
         super.levelFinished()
         nextLevel()
     }


    fun startTimerFromCount(count : Long ){
        timerDisposable?.dispose()

        timerDisposable = Observable
            .intervalRange(1, count + 1,1,1, TimeUnit.SECONDS)
            .map { count + 1 - it  }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete(this::finishGame)
            .subscribe{
                stillHaveTime.postValue( it)
                stillHaveTimeLong = it
            }
    }


     private fun getExtraTime(distance: Int) = when {

         distance <= 100 -> 9
         distance <= 200 -> 8
         distance <= 300 -> 7
         distance <= 400 -> 6
         distance <= 500 -> 5
         distance <= 600 -> 0
         distance <= 700 -> -2
         distance <= 800 -> -3
         distance <= 900 -> -4
         distance <= 1000 -> -5
         distance <= 2000 -> -10
         else -> -10

    }

}