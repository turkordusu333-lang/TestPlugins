package com.lagradost.cloudstream3.plugins

import com.lagradost.cloudstream3.*

class HDKore : MainAPI() {

    override var mainUrl = "https://hdkore.com"
    override var name = "HDKore"
    override val supportedTypes = setOf(
        TvType.TvSeries,
        TvType.Movie
    )

    override suspend fun search(query: String): List<SearchResponse> {
        val doc = app.get("$mainUrl/?s=$query").document
        return doc.select("article").mapNotNull {
            val title = it.select("h2").text()
            val link = it.select("a").attr("href")
            val poster = it.select("img").attr("src")
            newMovieSearchResponse(title, link, TvType.TvSeries) {
                this.posterUrl = poster
            }
        }
    }
}
