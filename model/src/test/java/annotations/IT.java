package annotations;

import com.epam.esm.configuration.DevelopmentConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DevelopmentConfiguration.class},loader = AnnotationConfigContextLoader.class)
@Retention(RetentionPolicy.RUNTIME)
@Transactional
@ActiveProfiles("dev")
public @interface IT {
}
