package com.example.geochallenge.data.api

import com.example.geochallenge.data.database.GeoChallengeDao
import com.example.geochallenge.game.CityTask
import com.example.geochallenge.game.Record
import com.example.geochallenge.utils.hasInternetConnection
import io.reactivex.Completable
import io.reactivex.Single
import java.io.IOException
import java.util.concurrent.TimeUnit

object MockClassicRussianGeochallengeApi : GeochallengeApi {

    val records = ArrayList<Record>()
    val mapId = 1
    val mode = "solo"
    lateinit var dao: GeoChallengeDao


    override fun getRandomCityTasksByLevel(
        level: Int,
        limit: Int,
        mapId: Int
    ): Single<List<CityTask>> {

        return hasInternetConnection()
            .delay(1500, TimeUnit.MILLISECONDS)
            .flatMap { yes ->
                if (yes) {
                    dao.getRandomCityTasksByLevel(level, limit)
                } else {
                    Single.error<List<CityTask>>(IOException())
                }
            }
    }

    override fun getCityTaskById(id: Int, mapId: Int): Single<CityTask> {
        return hasInternetConnection()
            .delay(1500, TimeUnit.MILLISECONDS)
            .flatMap { yes ->
                if (yes) {
                    dao.getCityTasksById(id)
                } else {
                    Single.error<CityTask>(IOException())
                }
            }
    }

    override fun postResults(
        mode: String,
        user: String,
        mapId: Int,
        score: Int,
        countTasks: Int
    ): Completable {

        return hasInternetConnection()
            .delay(1500, TimeUnit.MILLISECONDS)
            .flatMapCompletable { yes ->
                if (yes) {
                    records.add(Record(userId = user, score = score, countTasks = countTasks))
                    records.sort()
                    Completable.complete()
                } else {
                    Completable.error(IOException())
                }

            }

    }

    override fun getResults(mode: String, mapId: Int): Single<List<Record>> {
        return hasInternetConnection()
            .delay(1500, TimeUnit.MILLISECONDS)
            .flatMap { yes ->
                if (yes) {
                    Single.just(records)
                } else {
                    Single.error<List<Record>>(IOException())
                }
            }
    }

    override fun getAllCityTasksByLevel(level: Int, mapId: Int): Single<List<CityTask>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}