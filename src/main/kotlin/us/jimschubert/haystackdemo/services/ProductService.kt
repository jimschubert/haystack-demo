package us.jimschubert.haystackdemo.services

import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import us.jimschubert.haystackdemo.config.DatabaseConfig
import us.jimschubert.haystackdemo.data.Bootstrap
import us.jimschubert.haystackdemo.data.Product
import us.jimschubert.haystackdemo.data.Products
import kotlin.concurrent.thread

@Repository
@Transactional
class ProductService(databaseConfig: DatabaseConfig) {
    private val bs:Bootstrap by lazy { Bootstrap(databaseConfig) }

    init {
        thread(name = "database") {
            bs.database()
        }
    }

    fun list(offset: Int = 0, limit: Int = 10): List<Product> {
        return transaction(bs.db) {
            this.addLogger(StdOutSqlLogger)
            Products.selectAll().limit(limit, offset).map {
                Product(
                    id = it[Products.id],
                    name = it[Products.name],
                    categoryId = it[Products.categoryId]
                )
            }.toList()
        }
    }
}