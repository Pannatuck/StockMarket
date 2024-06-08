package dev.pan.stockmarket.data.csv

import com.opencsv.CSVReader
import dev.pan.stockmarket.domain.model.CompanyListing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton


/*Parser library implementation (opencsv) with which we can parse CSV files.
* readAll() method of that reader return data as List of arrays (which can represent columns with it's data).
* drop needed to skip first line (header), that is not needed in UI
* and after we map each instance of array and give it proper values that can be displayed for user or if it's null just drop it */
@Singleton
class CompanyListingsParser @Inject constructor(): CSVParser<CompanyListing> {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull {line ->
                    val symbol = line.getOrNull(0)
                    val name = line.getOrNull(1)
                    val exchange = line.getOrNull(2)
                    CompanyListing(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null
                    )
                }
                .also {
                    csvReader.close()
                }
        }
    }
}