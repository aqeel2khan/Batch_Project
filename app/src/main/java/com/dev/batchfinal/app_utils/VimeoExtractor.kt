package com.dev.batchfinal.app_utils

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.IOException


class VimeoExtractor {


    interface OnExtractionListener {
        fun onExtractionComplete(videoUrl: String?)
        fun onExtractionFailed(e: Exception?)
    }

    companion object {

        fun extractVideoUrl(vimeoUrl: String?, listener: OnExtractionListener) {
            Thread {
                try {
                    val doc = Jsoup.connect(vimeoUrl).get()
                    val iframe: Element? = doc.selectFirst("iframe[src*=player.vimeo.com]")

                    if (iframe != null) {
                        val iframeSrc: String = iframe.attr("src")
                        val videoId = iframeSrc.substring(iframeSrc.lastIndexOf("/") + 1)
                        val videoUrl = "https://player.vimeo.com/video/$videoId"
                        listener.onExtractionComplete(videoUrl)
                    } else {
                        val scripts = doc.getElementsByTag("script")
                        for (script in scripts) {
                            val scriptData = script.html()
                            if (scriptData.contains("config = ")) {
                                val startIndex =
                                    scriptData.indexOf("config = ") + "config = ".length
                                val endIndex = scriptData.indexOf(";", startIndex)
                                val jsonContent = scriptData.substring(startIndex, endIndex)
                                val videoUrl: String = extractVideoUrlFromJson(jsonContent)
                                if (videoUrl != null) {
                                    listener.onExtractionComplete(videoUrl)
                                    return@Thread
                                }
                            }
                        }
                        throw IOException("Vimeo iframe not found")
                    }
                } catch (e: IOException) {
                    listener.onExtractionFailed(e)
                }
            }.start()
        }

        private fun extractVideoUrlFromJson(jsonContent: String): String {
            // Implement JSON parsing logic to extract video URL
            // Example: Parse the JSON string and extract the video URL

            return jsonContent
        }
    }
}