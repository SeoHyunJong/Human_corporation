package com.example.humancorporation

object DataUtil {
    fun getCSStockData(): List<CSStock> {
        return listOf(
            CSStock(
                createdAt = 0,
                open = 222.8F,
                close = 222.9F,
                shadowHigh = 224.0F,
                shadowLow = 222.2F
            ),
            CSStock(
                createdAt = 1,
                open = 222.0F,
                close = 222.2F,
                shadowHigh = 222.4F,
                shadowLow = 222.0F
            ),
            CSStock(
                createdAt = 2,
                open = 222.2F,
                close = 221.9F,
                shadowHigh = 222.5F,
                shadowLow = 221.5F
            ),
            CSStock(
                createdAt = 3,
                open = 222.4F,
                close = 222.3F,
                shadowHigh = 223.7F,
                shadowLow = 222.1F
            ),
            CSStock(
                createdAt = 4,
                open = 221.6F,
                close = 221.9F,
                shadowHigh = 221.9F,
                shadowLow = 221.5F
            ),
            CSStock(
                createdAt = 5,
                open = 221.8F,
                close = 224.9F,
                shadowHigh = 225.0F,
                shadowLow = 221.0F
            ),
            CSStock(
                createdAt = 6,
                open = 225.0F,
                close = 220.2F,
                shadowHigh = 225.4F,
                shadowLow = 219.2F
            ),
            CSStock(
                createdAt = 7,
                open = 222.2F,
                close = 225.9F,
                shadowHigh = 227.5F,
                shadowLow = 222.2F
            ),
            CSStock(
                createdAt = 8,
                open = 226.0F,
                close = 228.1F,
                shadowHigh = 228.1F,
                shadowLow = 225.1F
            ),
            CSStock(
                createdAt = 9,
                open = 227.6F,
                close = 228.9F,
                shadowHigh = 230.9F,
                shadowLow = 226.5F
            ),
            CSStock(
                createdAt = 10,
                open = 228.6F,
                close = 228.6F,
                shadowHigh = 230.9F,
                shadowLow = 228.0F
            )
        )
    }
}