package com.abhisekm.vehiclerentmanagementlibrary.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*

@Dao
interface BookingDao {
    @Transaction
    @Query("select * from rental_booking_table order by start_time asc")
    fun getAllBooking(): LiveData<List<DatabaseBooking>>

    @Query("select * from rental_booking_table where id = :id")
    fun getBooking(id: Int): DatabaseBooking

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg bookings: DatabaseBooking)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(booking: DatabaseBooking)

    @Query("update rental_booking_table set bike_unlocked = :bikeUnlocked where id = :id")
    fun updateBikeStatus(id: Int, bikeUnlocked: Boolean)

    @Query("select count(*) from rental_booking_table where start_time > :currentTime")
    fun getUpcomingBooking(currentTime: Long = Calendar.getInstance().timeInMillis): LiveData<Int>

    @Query("select count(*) from rental_booking_table where start_time < :currentTime and end_time> :currentTime")
    fun getOngoingBooking(currentTime: Long = Calendar.getInstance().timeInMillis): LiveData<Int>
}

@Dao
interface BikeDao {
    @Query("select * from rental_bike_table")
    fun getAllBikes(): LiveData<List<DatabaseBike>>

    @Query("select count(*) from rental_bike_table")
    fun getAllBikesCount(): Int

    @Query("select * from rental_bike_table where id = :id")
    fun getBike(id: Int): DatabaseBike

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg bikes: DatabaseBike)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bikes: List<DatabaseBike>)
}


@Database(
    entities = [DatabaseBooking::class, DatabaseBike::class],
    version = 1,
    exportSchema = false
)
abstract class RentalDatabase : RoomDatabase() {
    abstract val bookingDao: BookingDao
    abstract val bikeDao: BikeDao
}

private lateinit var INSTANCE: RentalDatabase

fun getDatabase(context: Context): RentalDatabase {
    synchronized(RentalDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                RentalDatabase::class.java,
                "rental"
            )
                .build()
        }

        return INSTANCE
    }
}