import com.google.inject.AbstractModule;
import securesocial.core.RuntimeEnvironment;
import services.MyEnvironment;


public class DemoModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(RuntimeEnvironment.class).to(MyEnvironment.class);
    }
}
