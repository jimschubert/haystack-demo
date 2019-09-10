package us.jimschubert.haystackdemo.apis

import io.opentracing.Span
import io.opentracing.Tracer
import io.opentracing.contrib.okhttp3.TracingInterceptor
import io.opentracing.tag.Tags
import io.opentracing.util.GlobalTracer
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import us.jimschubert.haystackdemo.data.Product
import us.jimschubert.haystackdemo.models.ProductResponse
import us.jimschubert.haystackdemo.services.ProductService
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Controller
@Validated
@RequestMapping("\${api.base-path:/v1}")
class ProductApi(val productService: ProductService, private val tracer: Tracer) {

    lateinit var client: OkHttpClient
    init {
        if (!GlobalTracer.isRegistered()) GlobalTracer.register(tracer)
        this.client = TracingInterceptor.addTracing(OkHttpClient.Builder(), tracer)
    }

    @RequestMapping(
        value = ["/products"],
        produces = ["application/json"],
        method = [RequestMethod.GET]
    )
    fun getProductList(
        @Min(1)
        @Max(100)
        @Valid
        @RequestParam(
            value = "limit",
            required = false,
            defaultValue = "20"
        )
        limit: Int,
        @Min(0)
        @Valid
        @RequestParam(value = "offset", required = false, defaultValue = "0")
        offset: Int
    ): ResponseEntity<List<ProductResponse>> {
        var span: Span? = null
        try {
            span = tracer.buildSpan("products").start()

            span.run {
                Tags.COMPONENT.set(this, "haystack-demo")
                this.log("Sample span log message.")
            }

            val products = when (limit) {
                1 -> {
                    val inefficientProductList = mutableListOf<Product>()
                    for (i in 0..10) {
                        inefficientProductList.addAll(
                            productService.list(offset = 2 * i, limit = 2)
                        )
                    }
                    inefficientProductList.map {
                        ProductResponse(it.id!!, it.name, it.categoryId)
                    }
                }
                else -> productService.list(offset = offset, limit = limit).map {
                    ProductResponse(it.id!!, it.name, it.categoryId)
                }
            }
            return ResponseEntity(products, HttpStatus.OK)
        } finally {
            span?.finish()
        }
    }

    @RequestMapping(
        value = ["/other"],
        produces = ["application/json"],
        method = [RequestMethod.GET]
    )
    fun callOther(): ResponseEntity<String> {
        val otherService = try {
            System.getenv("OTHER_SERVICE")
        } catch(e: Throwable) {
            "http://frontend:9090/hello"
        }
        val response = client.newCall(
            Request.Builder()
                .get()
                .url(otherService)
                .build())
                .execute()

        val result = response.use { r ->
             r.body()?.string()
        }

        return ResponseEntity(result, HttpStatus.OK)
    }
}

