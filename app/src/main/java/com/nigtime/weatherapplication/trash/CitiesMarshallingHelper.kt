/*
 * Сreated by Igor Pokrovsky. 2020/4/25
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.trash


import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.nigtime.weatherapplication.db.table.ReferenceCityTable
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import java.io.Reader


class CitiesMarshallingHelper {

    fun readFromReader(reader: Reader): Flowable<ReferenceCityTable> {

        return Flowable.create({ emitter ->
            try {

                val jsonReader = JsonReader(reader)

                jsonReader.isLenient = true
                jsonReader.beginArray()

                while (jsonReader.hasNext() && !emitter.isCancelled) {
                    jsonReader.beginObject()
                    emitter.onNext(makeCity(jsonReader))
                    jsonReader.endObject()
                }

                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            } finally {
                reader.close()
            }
        }, BackpressureStrategy.ERROR)
    }

    private fun makeCity(jsonReader: JsonReader): ReferenceCityTable {
        var id = 0L
        var name = ""
        var stateName = ""
        var countryName = ""

        while (jsonReader.hasNext()) {
            when (jsonReader.nextName()) {
                "id" -> id = jsonReader.nextLong()
                "city_name" -> name = jsonReader.nextString()
                "state_name" -> stateName = getNonNullString(jsonReader)
                "country_name" -> countryName = getNonNullString(jsonReader)
                else -> jsonReader.skipValue()
            }
        }

        return ReferenceCityTable(
            id,
            name,
            stateName,
            countryName
        )
    }

    private fun getNonNullString(jsonReader: com.google.gson.stream.JsonReader): String {
        return when (jsonReader.peek()) {
            JsonToken.NULL -> {
                jsonReader.skipValue()
                ""
            }
            else -> jsonReader.nextString()
        }
    }
}