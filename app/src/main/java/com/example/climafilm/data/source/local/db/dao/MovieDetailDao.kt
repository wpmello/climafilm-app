package com.example.climafilm.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.climafilm.data.source.local.db.entity.MovieDetailEntity

@Dao
interface MovieDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movieDetail: MovieDetailEntity)

    @Query("SELECT * FROM movie_detail WHERE id = :id")
    suspend fun getMovieDetailById(id: Int): MovieDetailEntity?

    @Query("DELETE FROM movie_detail WHERE id = :id")
    suspend fun deleteMovieDetailById(id: Int)
}