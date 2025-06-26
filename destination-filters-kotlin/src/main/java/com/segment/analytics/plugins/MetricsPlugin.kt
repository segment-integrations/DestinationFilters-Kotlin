package com.segment.analytics.plugins

import com.segment.analytics.kotlin.core.Analytics
import com.segment.analytics.kotlin.core.BaseEvent
import com.segment.analytics.kotlin.core.platform.Plugin
import com.segment.analytics.kotlin.core.utilities.putInContextUnderKey
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import java.util.concurrent.CopyOnWriteArraySet

class MetricsPlugin: Plugin {

    val setOfActiveDestinations = CopyOnWriteArraySet<String>()

    override val type: Plugin.Type = Plugin.Type.Enrichment
    override lateinit var analytics: Analytics

    override fun execute(event: BaseEvent): BaseEvent? {
        event.putInContextUnderKey(
            "plugins",
            "destination-filters",
            buildJsonObject {
                put("version", DestinationFilters.version)
                putJsonArray("active") {
                    for (dest in setOfActiveDestinations) {
                        add(dest)
                    }
                }
            }
        )
        return super.execute(event)
    }
}