package com.blobblob.empty

import com.blobblob.empty.data.AppApiImpl
import com.blobblob.empty.data.AppsRepositoryImpl
import com.blobblob.empty.data.mapper.AppDetailsMapper
import com.blobblob.empty.data.mapper.AppMapper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.system.measureTimeMillis

class RatingsBenchmarkTest {

    @Test
    fun benchmarkRatingsLoading() = runBlocking {
        val dataSource = AppDataSource()
        val appMapper = AppMapper()
        val appDetailsMapper = AppDetailsMapper()
        val api = AppApiImpl(dataSource, appMapper)
        val repository = AppsRepositoryImpl(dataSource, api, appMapper, appDetailsMapper)

        val ids = dataSource.getApps().map { it.id }
        assertEquals(10, ids.size)

        val seqTime = measureTimeMillis {
            val ratings = repository.loadRatingsSequentially(ids)
            assertEquals(10, ratings.size)
        }

        val parTime = measureTimeMillis {
            val ratings = repository.loadRatingsInParallel(ids)
            assertEquals(10, ratings.size)
        }

        println("RESULTS: Sequential = $seqTime ms, Parallel = $parTime ms, Ratio = ${seqTime.toDouble() / parTime}")

        assertTrue(seqTime >= 14000)
        assertTrue(parTime < 3000)
    }
}
