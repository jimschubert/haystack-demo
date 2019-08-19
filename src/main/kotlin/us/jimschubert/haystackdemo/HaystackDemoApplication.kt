package us.jimschubert.haystackdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["us.jimschubert.haystackdemo"])
class HaystackDemoApplication

fun main(args: Array<String>) {
    runApplication<HaystackDemoApplication>(*args)
}
