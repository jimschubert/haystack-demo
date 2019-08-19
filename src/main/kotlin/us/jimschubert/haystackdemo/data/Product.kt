package us.jimschubert.haystackdemo.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update

object Products : Table() {
    val id = integer("id").autoIncrement().primaryKey() // Column<Int>
    val name = varchar("name", length = 50) // Column<String>
    val categoryId = (integer("category_id") references Categories.id).nullable() // Column<Int?>
}

object Categories : Table() {
    val id = integer("id").autoIncrement().primaryKey() // Column<Int>
    val name = varchar("name", length = 50) // Column<String>
}

data class Product(val name: String, val categoryId: Int?, val id: Int? = null) {
    fun create(): Product {
        val self = this
        val newId = Products.insert {
            it[name] = self.name
            it[categoryId] = self.categoryId
        } get Products.id
        return this.copy(id = newId)
    }

    fun save(): Int {
        val self = this
        return Products.update({ Products.id eq id!! }) {
            it[name] = self.name
            it[categoryId] = self.categoryId
        }
    }
}


data class Category(val name: String, val id: Int? = null) {
    fun create(): Category {
        val self = this
        val newId = Categories.insert {
            it[name] = self.name
        } get Categories.id

        return this.copy(id = newId)
    }

    fun save(): Int {
        val self = this
        return Categories.update({ Categories.id eq id!! }) {
            it[name] = self.name
        }
    }
}
