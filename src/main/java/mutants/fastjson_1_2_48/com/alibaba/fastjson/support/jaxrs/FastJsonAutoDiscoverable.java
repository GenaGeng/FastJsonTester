package mutants.fastjson_1_2_48.com.alibaba.fastjson.support.jaxrs;

import com.alibaba.fastjson.support.jaxrs.FastJsonFeature;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;

import javax.annotation.Priority;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.FeatureContext;

/**
 * <p>Title: FastJsonAutoDiscoverable</p>
 * <p>Description: FastJsonAutoDiscoverable</p>
 *
 * @author Victor.Zxy
 * @see AutoDiscoverable
 * @since 1.2.37
 */
@Priority(AutoDiscoverable.DEFAULT_PRIORITY - 1)
public class FastJsonAutoDiscoverable implements AutoDiscoverable {

    public volatile static boolean autoDiscover = true;

    @Override
    public void configure(final FeatureContext context) {

        final Configuration config = context.getConfiguration();

        // Register FastJson.
        if (!config.isRegistered(com.alibaba.fastjson.support.jaxrs.FastJsonFeature.class) && autoDiscover) {

            context.register(FastJsonFeature.class);
        }
    }
}
