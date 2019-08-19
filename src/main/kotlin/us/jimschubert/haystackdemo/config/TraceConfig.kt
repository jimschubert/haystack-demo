package us.jimschubert.haystackdemo.config

import com.expedia.haystack.opentracing.spring.starter.support.TracerCustomizer
import com.expedia.www.haystack.client.Tracer
import org.springframework.context.annotation.Configuration

@Configuration
class TraceConfig : TracerCustomizer {
    override fun customize(builder: Tracer.Builder?) {

    }

}