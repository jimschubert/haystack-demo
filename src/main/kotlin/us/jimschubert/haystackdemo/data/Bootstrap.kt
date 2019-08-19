package us.jimschubert.haystackdemo.data

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import us.jimschubert.haystackdemo.config.DatabaseConfig

class Bootstrap(@Autowired(required = true) val databaseConfig: DatabaseConfig) {
    private var initialized = false
    val db by lazy {
        Database.connect(
            databaseConfig.url,
            driver = databaseConfig.driver,
            user = databaseConfig.user,
            password = databaseConfig.password
        )
    }

    fun database() {
        if (!initialized) {
            initialized = true
            transaction(db) {
                addLogger(StdOutSqlLogger)
                SchemaUtils.drop(Categories, Products)
                SchemaUtils.create(Categories, Products)

                val existing = Categories.select {
                    Categories.name eq "fruit"
                }.firstOrNull()

                if (existing == null) {

                    val fruit = Category(name = "fruit").create()
                    val vegetable = Category(name = "vegetable").create()

                    listOf(
                        "Apple" to fruit,
                        "Apricot" to fruit,
                        "Avocado" to fruit,
                        "Banana" to fruit,
                        "Blackberry" to fruit,
                        "Blueberry" to fruit,
                        "Cherry" to fruit,
                        "Coconut" to fruit,
                        "Cucumber" to fruit,
                        "Durian" to fruit,
                        "Dragonfruit" to fruit,
                        "Fig" to fruit,
                        "Gooseberry" to fruit,
                        "Grape" to fruit,
                        "Guava" to fruit,
                        "Jackfruit" to fruit,
                        "Plum" to fruit,
                        "Kiwifruit" to fruit,
                        "Kumquat" to fruit,
                        "Lemon" to fruit,
                        "Lime" to fruit,
                        "Mango" to fruit,
                        "Watermelon" to fruit,
                        "Mulberry" to fruit,
                        "Orange" to fruit,
                        "Papaya" to fruit,
                        "Passionfruit" to fruit,
                        "Peach" to fruit,
                        "Pear" to fruit,
                        "Persimmon" to fruit,
                        "Pineapple" to fruit,
                        "Pineberry" to fruit,
                        "Quince" to fruit,
                        "Raspberry" to fruit,
                        "Soursop" to fruit,
                        "Star fruit" to fruit,
                        "Strawberry" to fruit,
                        "Tamarind" to fruit,
                        "Yuzu" to fruit,

                        "Lettuce" to vegetable,
                        "Broccoli" to vegetable,
                        "Potatoes" to vegetable,
                        "Tomatoes" to vegetable,
                        "Sweet corn" to vegetable,
                        "Onions" to vegetable,
                        "Cucumbers" to vegetable,
                        "Bell peppers" to vegetable,
                        "Carrots" to vegetable,
                        "Cabbage" to vegetable,
                        "Green beans" to vegetable,
                        "Sweet potatoes" to vegetable,
                        "Celery" to vegetable,
                        "Pumpkin" to vegetable,
                        "Squash" to vegetable,
                        "Mushrooms" to vegetable,
                        "Spinach" to vegetable,
                        "Garlic" to vegetable,
                        "Cauliflower" to vegetable,
                        "Asparagus" to vegetable,
                        "Eggplant" to vegetable,
                        "Collard greens" to vegetable,
                        "Radishes" to vegetable,
                        "Artichokes" to vegetable,
                        "Turnip greens" to vegetable,
                        "Okra" to vegetable,
                        "Mustard greens" to vegetable,
                        "Brussels sprouts" to vegetable,
                        "Kale" to vegetable,
                        "Endives" to vegetable
                    ).forEach { (name, category) ->
                        Product(
                            name = name,
                            categoryId = category.id
                        ).create()
                    }

                    commit()
                }
            }
        }
    }
}