package com.example.brawlhallahero.data

import com.example.brawlhallahero.model.Brawl
import com.example.brawlhallahero.model.FakeBrawlDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class BrawlRepository {

    private val brawlList = mutableListOf<Brawl>()

    init {
        if (brawlList.isEmpty()) {
            FakeBrawlDataSource.dummyBrawl.forEach {
                brawlList.add(it)
            }
        }
    }

    fun getAllBrawl(): Flow<List<Brawl>> {
        return flowOf(brawlList)
    }

    fun getBrawlById(brawlId: Long): Brawl {
        return brawlList.first {
            it.id == brawlId
        }
    }

    fun updateBrawl(brawlId: Long): Flow<Boolean> {
        val index = brawlList.indexOfFirst { it.id == brawlId }
        val result = if (index >= 0) {
            val brawl = brawlList[index]
            brawlList[index] =
                brawl.copy(isFav = !brawl.isFav)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedBrawl(): Flow<List<Brawl>> {
        return getAllBrawl()
            .map { brawl ->
                brawl.filter { brawlis ->
                    brawlis.isFav
                }
            }
    }

    fun searchHero(query: String): Flow<List<Brawl>> {
        return flowOf(
            brawlList.filter {
                it.name.contains(query, ignoreCase = true)
            }
        )
    }

    companion object {
        @Volatile
        private var instance: BrawlRepository? = null

        fun getInstance(): BrawlRepository =
            instance ?: synchronized(this) {
                BrawlRepository().apply {
                    instance = this
                }
            }
    }
}