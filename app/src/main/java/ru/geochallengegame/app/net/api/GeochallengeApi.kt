package ru.geochallengegame.app.net.api

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import ru.geochallengegame.app.game.CityTask
import ru.geochallengegame.app.game.GameMap
import ru.geochallengegame.app.game.PostResultsResponce
import ru.geochallengegame.app.game.Record


interface GeochallengeApi {

    //tasks
    @GET("getCityTasksByLevel")
    fun getAllCityTasksByLevel(
        @Query("level") level: Int,
        @Query("map_id") mapId: Int,
        @Query("lang") lang: String
    ): Single<List<CityTask>>

    @GET("getRandomCityTasksByLevel")
    fun getRandomCityTasksByLevel(
        @Query("level") level: Int,
        @Query("limit") limit: Int,
        @Query("map_id") mapId: Int,
        @Query("lang") lang: String

    ): Single<List<CityTask>>

    @GET("/getCityTasksById")
    fun getCityTaskById(
        @Query("cityid") id: Int,
        @Query("map_id") mapId: Int,
        @Query("lang") lang: String
    ): Single<CityTask>

    //records
    @FormUrlEncoded
    @POST("/postResults")
    fun postResults(
        @Field("mode") mode: String,
        @Field("username") user: String,
        @Field("map_id") mapId: Int,
        @Field("score") score: Int,
        @Field("count_task") countTasks: Int
    ): Single<PostResultsResponce>

    @GET("/getResults")
    fun getResults(
        @Query("mode") mode: String,
        @Query("map_id") mapId: Int
    ): Single<List<Record>>

    //maps
    @GET("/getMaps")
    fun getMaps(): Single<List<GameMap>>

    @GET("/getMaps")
    fun getMaps(@Query("mode") mode: String): Single<List<GameMap>>


    @GET("/gettopafter")
    fun getTopAfter(
        @Query("pos") position: Int,
        @Query("limit") limit: Int,
        @Query("mode") mode: String,
        @Query("map_id") mapId: Int
    ): Single<List<Record>>


    @GET("/gettopbefore")
    fun getTopBefore(
        @Query("pos") position: Int,
        @Query("limit") limit: Int,
        @Query("mode") mode: String,
        @Query("map_id") mapId: Int
    ): Single<List<Record>>

    @GET("/getmyresults")
    fun getMyRecords(
        @Query("username") user: String,
        @Query("mode") mode: String,
        @Query("map_id") mapId: Int

    ): Single<List<Record>>


    @FormUrlEncoded
    @POST("/postStats")
    fun postStats(
        @Field("map_id") mapId: Int?,
        @Field("cname") taskName: String?,
        @Field("distance") distance: Double?,
        @Field("level") level: Int?,
        @Field("seconds") seconds: Int?,
        @Field("username") username: String?,
        @Field("mode") mode: String?

    ): Completable
}